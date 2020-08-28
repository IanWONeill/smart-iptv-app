package com.nst.yourname.view.exoplayer;

import android.app.Notification;
import android.content.Context;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.scheduler.PlatformScheduler;
import com.google.android.exoplayer2.ui.DownloadNotificationUtil;
import com.google.android.exoplayer2.util.NotificationUtil;
import com.google.android.exoplayer2.util.Util;
import com.nst.yourname.R;

public class DemoDownloadService extends DownloadService {
    private static final String CHANNEL_ID = "download_channel";
    private static final int FOREGROUND_NOTIFICATION_ID = 1;
    private static final int JOB_ID = 1;
    Context context = this;

    public DemoDownloadService() {
        super(1, 1000, CHANNEL_ID, R.string.exo_download_notification_channel_name);
    }

    @Override // com.google.android.exoplayer2.offline.DownloadService
    public DownloadManager getDownloadManager() {
        return DemoApplication.getInstance(this.context).getDownloadManager();
    }

    @Override // com.google.android.exoplayer2.offline.DownloadService
    public PlatformScheduler getScheduler() {
        if (Util.SDK_INT >= 21) {
            return new PlatformScheduler(this, 1);
        }
        return null;
    }

    @Override // com.google.android.exoplayer2.offline.DownloadService
    public Notification getForegroundNotification(DownloadManager.TaskState[] taskStateArr) {
        return DownloadNotificationUtil.buildProgressNotification(this, R.drawable.exo_controls_play, CHANNEL_ID, null, null, taskStateArr);
    }

    @Override // com.google.android.exoplayer2.offline.DownloadService
    public void onTaskStateChanged(DownloadManager.TaskState taskState) {
        if (!taskState.action.isRemoveAction) {
            Notification notification = null;
            if (taskState.state == 2) {
                notification = DownloadNotificationUtil.buildDownloadCompletedNotification(this, R.drawable.exo_controls_play, CHANNEL_ID, null, Util.fromUtf8Bytes(taskState.action.data));
            } else if (taskState.state == 4) {
                notification = DownloadNotificationUtil.buildDownloadFailedNotification(this, R.drawable.exo_controls_play, CHANNEL_ID, null, Util.fromUtf8Bytes(taskState.action.data));
            }
            NotificationUtil.setNotification(this, taskState.taskId + 2, notification);
        }
    }
}
