package com.nst.yourname.view.exoplayer;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.exoplayer2.offline.DownloadAction;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloaderConstructorHelper;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.nst.yourname.BuildConfig;
import com.nst.yourname.miscelleneious.common.AppConst;
import java.io.File;

@SuppressWarnings("ALL")
public class DemoApplication {
    private static final String DOWNLOAD_ACTION_FILE = "actions";
    private static final String DOWNLOAD_CONTENT_DIRECTORY = "downloads";
    private static final String DOWNLOAD_TRACKER_ACTION_FILE = "tracked_actions";
    private static final int MAX_SIMULTANEOUS_DOWNLOADS = 2;
    private static DemoApplication demoApplication;
    private Context context;
    private Cache downloadCache;
    private File downloadDirectory;
    private DownloadManager downloadManager;
    private DownloadTracker downloadTracker;
    private SharedPreferences loginPreferencesUserAgent;
    protected String userAgent;

    public static DemoApplication getInstance(Context context2) {
        if (demoApplication == null) {
            demoApplication = new DemoApplication(context2);
        }
        return demoApplication;
    }

    public DemoApplication(Context context2) {
        this.context = context2;
    }

    public DataSource.Factory buildDataSourceFactory() {
        return buildReadOnlyCacheDataSource(new DefaultDataSourceFactory(this.context, buildHttpDataSourceFactory()), getDownloadCache());
    }

    public HttpDataSource.Factory buildHttpDataSourceFactory() {
        this.loginPreferencesUserAgent = this.context.getSharedPreferences(AppConst.LOGIN_PREF_USER_AGENT, 0);
        this.userAgent = this.loginPreferencesUserAgent.getString(AppConst.LOGIN_PREF_USER_AGENT, AppConst.USER_AGENT);
        if (this.userAgent.equals("")) {
            this.userAgent = AppConst.USER_AGENT;
        }
        return new DefaultHttpDataSourceFactory(this.userAgent);
    }

    public boolean useExtensionRenderers() {
        return "withExtensions".equals(BuildConfig.FLAVOR);
    }

    public DownloadManager getDownloadManager() {
        initDownloadManager();
        return this.downloadManager;
    }

    public DownloadTracker getDownloadTracker() {
        initDownloadManager();
        return this.downloadTracker;
    }

    private synchronized void initDownloadManager() {
        if (this.downloadManager == null) {
            this.downloadManager = new DownloadManager(new DownloaderConstructorHelper(getDownloadCache(), buildHttpDataSourceFactory()), 2, 5, new File(getDownloadDirectory(), DOWNLOAD_ACTION_FILE), new DownloadAction.Deserializer[0]);
            this.downloadTracker = new DownloadTracker(this.context, buildDataSourceFactory(), new File(getDownloadDirectory(), DOWNLOAD_TRACKER_ACTION_FILE), new DownloadAction.Deserializer[0]);
            this.downloadManager.addListener(this.downloadTracker);
        }
    }

    private synchronized Cache getDownloadCache() {
        if (this.downloadCache == null) {
            this.downloadCache = new SimpleCache(new File(getDownloadDirectory(), DOWNLOAD_CONTENT_DIRECTORY), new NoOpCacheEvictor());
        }
        return this.downloadCache;
    }

    private File getDownloadDirectory() {
        if (this.downloadDirectory == null) {
            this.downloadDirectory = this.context.getExternalFilesDir(null);
            if (this.downloadDirectory == null) {
                this.downloadDirectory = this.context.getFilesDir();
            }
        }
        return this.downloadDirectory;
    }

    private static CacheDataSourceFactory buildReadOnlyCacheDataSource(DefaultDataSourceFactory defaultDataSourceFactory, Cache cache) {
        return new CacheDataSourceFactory(cache, defaultDataSourceFactory, new FileDataSourceFactory(), null, 2, null);
    }
}
