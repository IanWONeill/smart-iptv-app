package com.nst.yourname.view.ijkplayer.widget.media;

import android.content.Context;
import android.content.pm.PackageManager;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.MediaPlayerProxy;
import tv.danmaku.ijk.media.player.TextureMediaPlayer;

public class MediaPlayerCompat {
    public static String getName(IMediaPlayer iMediaPlayer) {
        if (iMediaPlayer == null) {
            return "null";
        }
        if (!(iMediaPlayer instanceof TextureMediaPlayer)) {
            return iMediaPlayer.getClass().getSimpleName();
        }
        StringBuilder sb = new StringBuilder("TextureMediaPlayer <");
        IMediaPlayer internalMediaPlayer = ((TextureMediaPlayer) iMediaPlayer).getInternalMediaPlayer();
        if (internalMediaPlayer == null) {
            sb.append("null>");
        } else {
            sb.append(internalMediaPlayer.getClass().getSimpleName());
            sb.append(">");
        }
        return sb.toString();
    }

    public static long cit(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static IjkMediaPlayer getIjkMediaPlayer(IMediaPlayer iMediaPlayer) {
        if (iMediaPlayer == null) {
            return null;
        }
        if (iMediaPlayer instanceof IjkMediaPlayer) {
            return (IjkMediaPlayer) iMediaPlayer;
        }
        if (!(iMediaPlayer instanceof MediaPlayerProxy)) {
            return null;
        }
        MediaPlayerProxy mediaPlayerProxy = (MediaPlayerProxy) iMediaPlayer;
        if (mediaPlayerProxy.getInternalMediaPlayer() instanceof IjkMediaPlayer) {
            return (IjkMediaPlayer) mediaPlayerProxy.getInternalMediaPlayer();
        }
        return null;
    }

    public static void selectTrack(IMediaPlayer iMediaPlayer, int i) {
        IjkMediaPlayer ijkMediaPlayer = getIjkMediaPlayer(iMediaPlayer);
        if (ijkMediaPlayer != null) {
            ijkMediaPlayer.selectTrack(i);
        }
    }

    public static void deselectTrack(IMediaPlayer iMediaPlayer, int i) {
        IjkMediaPlayer ijkMediaPlayer = getIjkMediaPlayer(iMediaPlayer);
        if (ijkMediaPlayer != null) {
            ijkMediaPlayer.deselectTrack(i);
        }
    }

    public static int getSelectedTrack(IMediaPlayer iMediaPlayer, int i) {
        IjkMediaPlayer ijkMediaPlayer = getIjkMediaPlayer(iMediaPlayer);
        if (ijkMediaPlayer == null) {
            return -1;
        }
        return ijkMediaPlayer.getSelectedTrack(i);
    }
}
