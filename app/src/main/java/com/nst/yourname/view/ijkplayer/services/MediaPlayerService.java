package com.nst.yourname.view.ijkplayer.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import tv.danmaku.ijk.media.player.IMediaPlayer;

public class MediaPlayerService extends Service {
    private static IMediaPlayer sMediaPlayer;

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MediaPlayerService.class);
    }

    public static void intentToStart(Context context) {
        context.startService(newIntent(context));
    }

    public static void intentToStop(Context context) {
        context.stopService(newIntent(context));
    }

    public static void setMediaPlayer(IMediaPlayer iMediaPlayer) {
        if (!(sMediaPlayer == null || sMediaPlayer == iMediaPlayer)) {
            if (sMediaPlayer.isPlaying()) {
                sMediaPlayer.stop();
            }
            sMediaPlayer.release();
            sMediaPlayer = null;
        }
        sMediaPlayer = iMediaPlayer;
    }

    public static IMediaPlayer getMediaPlayer() {
        return sMediaPlayer;
    }
}
