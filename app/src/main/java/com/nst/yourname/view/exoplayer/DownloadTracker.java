package com.nst.yourname.view.exoplayer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.exoplayer2.offline.ActionFile;
import com.google.android.exoplayer2.offline.DownloadAction;
import com.google.android.exoplayer2.offline.DownloadHelper;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.offline.ProgressiveDownloadHelper;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.offline.TrackKey;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.offline.DashDownloadHelper;
import com.google.android.exoplayer2.source.hls.offline.HlsDownloadHelper;
import com.google.android.exoplayer2.source.smoothstreaming.offline.SsDownloadHelper;
import com.google.android.exoplayer2.ui.DefaultTrackNameProvider;
import com.google.android.exoplayer2.ui.TrackNameProvider;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.Util;
import com.nst.yourname.R;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

@SuppressWarnings("ALL")
public class DownloadTracker implements DownloadManager.Listener {
    private static final String TAG = "DownloadTracker";
    private final ActionFile actionFile;
    private final Handler actionFileWriteHandler;
    public final Context context;
    private final DataSource.Factory dataSourceFactory;
    private final CopyOnWriteArraySet<Listener> listeners = new CopyOnWriteArraySet<>();
    public final TrackNameProvider trackNameProvider;
    private final HashMap<Uri, DownloadAction> trackedDownloadStates = new HashMap<>();

    public interface Listener {
        void onDownloadsChanged();
    }

    @Override // com.google.android.exoplayer2.offline.DownloadManager.Listener
    public void onIdle(DownloadManager downloadManager) {
    }

    @Override // com.google.android.exoplayer2.offline.DownloadManager.Listener
    public void onInitialized(DownloadManager downloadManager) {
    }

    public DownloadTracker(Context context2, DataSource.Factory factory, File file, DownloadAction.Deserializer... deserializerArr) {
        this.context = context2.getApplicationContext();
        this.dataSourceFactory = factory;
        this.actionFile = new ActionFile(file);
        this.trackNameProvider = new DefaultTrackNameProvider(context2.getResources());
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        this.actionFileWriteHandler = new Handler(handlerThread.getLooper());
        loadTrackedActions(deserializerArr.length <= 0 ? DownloadAction.getDefaultDeserializers() : deserializerArr);
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }

    public boolean isDownloaded(Uri uri) {
        return this.trackedDownloadStates.containsKey(uri);
    }

    public List<StreamKey> getOfflineStreamKeys(Uri uri) {
        if (!this.trackedDownloadStates.containsKey(uri)) {
            return Collections.emptyList();
        }
        return this.trackedDownloadStates.get(uri).getKeys();
    }

    public void toggleDownload(Activity activity, String str, Uri uri, String str2) {
        if (isDownloaded(uri)) {
            startServiceWithAction(getDownloadHelper(uri, str2).getRemoveAction(Util.getUtf8Bytes(str)));
        } else {
            new StartDownloadDialogHelper(activity, getDownloadHelper(uri, str2), str).prepare();
        }
    }

    @Override // com.google.android.exoplayer2.offline.DownloadManager.Listener
    public void onTaskStateChanged(DownloadManager downloadManager, DownloadManager.TaskState taskState) {
        DownloadAction downloadAction = taskState.action;
        Uri uri = downloadAction.uri;
        if (((downloadAction.isRemoveAction && taskState.state == 2) || (!downloadAction.isRemoveAction && taskState.state == 4)) && this.trackedDownloadStates.remove(uri) != null) {
            handleTrackedDownloadStatesChanged();
        }
    }

    private void loadTrackedActions(DownloadAction.Deserializer[] deserializerArr) {
        try {
            DownloadAction[] load = this.actionFile.load(deserializerArr);
            for (DownloadAction downloadAction : load) {
                this.trackedDownloadStates.put(downloadAction.uri, downloadAction);
            }
        } catch (IOException e) {
            Log.e(TAG, "Failed to load tracked actions", e);
        }
    }

    private void handleTrackedDownloadStatesChanged() {
        Iterator<Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onDownloadsChanged();
        }
        this.actionFileWriteHandler.post(new Runnable(/*(DownloadAction[]) this.trackedDownloadStates.values().toArray(new DownloadAction[0])*/) {
            /* class com.nst.yourname.view.exoplayer.$$Lambda$DownloadTracker$sR04RnHgLv2Fq2sqqMmRKB6KBz0 */
            private final DownloadAction[] f$1;

            {
                this.f$1 = (DownloadAction[]) trackedDownloadStates.values().toArray(new DownloadAction[0]);
            }

            public final void run() {
                DownloadTracker.lambda$handleTrackedDownloadStatesChanged$0(DownloadTracker.this, this.f$1);
            }
        });
    }

    public static void lambda$handleTrackedDownloadStatesChanged$0(DownloadTracker downloadTracker, DownloadAction[] downloadActionArr) {
        try {
            downloadTracker.actionFile.store(downloadActionArr);
        } catch (IOException e) {
            Log.e(TAG, "Failed to store tracked actions", e);
        }
    }

    public void startDownload(DownloadAction downloadAction) {
        if (!this.trackedDownloadStates.containsKey(downloadAction.uri)) {
            this.trackedDownloadStates.put(downloadAction.uri, downloadAction);
            handleTrackedDownloadStatesChanged();
            startServiceWithAction(downloadAction);
        }
    }

    private void startServiceWithAction(DownloadAction downloadAction) {
        DownloadService.startWithAction(this.context, DemoDownloadService.class, downloadAction, false);
    }

    private DownloadHelper getDownloadHelper(Uri uri, String str) {
        int inferContentType = Util.inferContentType(uri, str);
        switch (inferContentType) {
            case 0:
                return new DashDownloadHelper(uri, this.dataSourceFactory);
            case 1:
                return new SsDownloadHelper(uri, this.dataSourceFactory);
            case 2:
                return new HlsDownloadHelper(uri, this.dataSourceFactory);
            case 3:
                return new ProgressiveDownloadHelper(uri);
            default:
                throw new IllegalStateException("Unsupported type: " + inferContentType);
        }
    }

    private final class StartDownloadDialogHelper implements DownloadHelper.Callback, DialogInterface.OnClickListener {
        private AlertDialog.Builder builder;
        private final View dialogView = LayoutInflater.from(this.builder.getContext()).inflate((int) R.layout.start_download_dialog, (ViewGroup) null);
        private final DownloadHelper downloadHelper;
        private final String name;
        private final ListView representationList = ((ListView) this.dialogView.findViewById(R.id.representation_list));
        private final List<TrackKey> trackKeys = new ArrayList();
        private final ArrayAdapter<String> trackTitles = new ArrayAdapter<>(this.builder.getContext(), 17367056);

        public StartDownloadDialogHelper(Activity activity, DownloadHelper downloadHelper2, String str) {
            this.downloadHelper = downloadHelper2;
            this.name = str;
            this.builder = new AlertDialog.Builder(activity).setTitle((int) R.string.exo_download_description).setPositiveButton(17039370, this).setNegativeButton(17039360, (DialogInterface.OnClickListener) null);
            this.representationList.setChoiceMode(2);
            this.representationList.setAdapter((ListAdapter) this.trackTitles);
        }

        public void prepare() {
            this.downloadHelper.prepare(this);
        }

        @Override // com.google.android.exoplayer2.offline.DownloadHelper.Callback
        public void onPrepared(DownloadHelper downloadHelper2) {
            for (int i = 0; i < this.downloadHelper.getPeriodCount(); i++) {
                TrackGroupArray trackGroups = this.downloadHelper.getTrackGroups(i);
                for (int i2 = 0; i2 < trackGroups.length; i2++) {
                    TrackGroup trackGroup = trackGroups.get(i2);
                    for (int i3 = 0; i3 < trackGroup.length; i3++) {
                        this.trackKeys.add(new TrackKey(i, i2, i3));
                        this.trackTitles.add(DownloadTracker.this.trackNameProvider.getTrackName(trackGroup.getFormat(i3)));
                    }
                }
            }
            if (!this.trackKeys.isEmpty()) {
                this.builder.setView(this.dialogView);
            }
            this.builder.create().show();
        }

        @Override // com.google.android.exoplayer2.offline.DownloadHelper.Callback
        public void onPrepareError(DownloadHelper downloadHelper2, IOException iOException) {
            Toast.makeText(DownloadTracker.this.context.getApplicationContext(), (int) R.string.download_start_error, 1).show();
            Log.e(DownloadTracker.TAG, "Failed to start download", iOException);
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.representationList.getChildCount(); i2++) {
                if (this.representationList.isItemChecked(i2)) {
                    arrayList.add(this.trackKeys.get(i2));
                }
            }
            if (!arrayList.isEmpty() || this.trackKeys.isEmpty()) {
                DownloadTracker.this.startDownload(this.downloadHelper.getDownloadAction(Util.getUtf8Bytes(this.name), arrayList));
            }
        }
    }
}
