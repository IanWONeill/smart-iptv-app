package com.nst.yourname.view.ijkplayer.widget.media;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.view.View;
import android.widget.TableLayout;
import com.nst.yourname.R;
import java.util.Locale;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.MediaPlayerProxy;

public class InfoHudViewHolder {
    private static final int MSG_UPDATE_HUD = 1;
    @SuppressLint({"HandlerLeak"})
    public Handler mHandler = new Handler() {
        /* class com.nst.yourname.view.ijkplayer.widget.media.InfoHudViewHolder.AnonymousClass1 */

        public void handleMessage(Message message) {
            IMediaPlayer internalMediaPlayer;
            if (message.what == 1) {
                InfoHudViewHolder infoHudViewHolder = InfoHudViewHolder.this;
                IjkMediaPlayer ijkMediaPlayer = null;
                if (InfoHudViewHolder.this.mMediaPlayer != null) {
                    if (InfoHudViewHolder.this.mMediaPlayer instanceof IjkMediaPlayer) {
                        ijkMediaPlayer = (IjkMediaPlayer) InfoHudViewHolder.this.mMediaPlayer;
                    } else if ((InfoHudViewHolder.this.mMediaPlayer instanceof MediaPlayerProxy) && (internalMediaPlayer = ((MediaPlayerProxy) InfoHudViewHolder.this.mMediaPlayer).getInternalMediaPlayer()) != null && (internalMediaPlayer instanceof IjkMediaPlayer)) {
                        ijkMediaPlayer = (IjkMediaPlayer) internalMediaPlayer;
                    }
                    if (ijkMediaPlayer != null) {
                        switch (ijkMediaPlayer.getVideoDecoder()) {
                            case 1:
                                InfoHudViewHolder.this.setRowValue(R.string.vdec, "avcodec");
                                break;
                            case 2:
                                InfoHudViewHolder.this.setRowValue(R.string.vdec, "MediaCodec");
                                break;
                            default:
                                InfoHudViewHolder.this.setRowValue(R.string.vdec, "");
                                break;
                        }
                        float videoOutputFramesPerSecond = ijkMediaPlayer.getVideoOutputFramesPerSecond();
                        InfoHudViewHolder.this.setRowValue(R.string.fps, String.format(Locale.US, "%.2f / %.2f", Float.valueOf(ijkMediaPlayer.getVideoDecodeFramesPerSecond()), Float.valueOf(videoOutputFramesPerSecond)));
                        long videoCachedDuration = ijkMediaPlayer.getVideoCachedDuration();
                        long audioCachedDuration = ijkMediaPlayer.getAudioCachedDuration();
                        long videoCachedBytes = ijkMediaPlayer.getVideoCachedBytes();
                        long audioCachedBytes = ijkMediaPlayer.getAudioCachedBytes();
                        long tcpSpeed = ijkMediaPlayer.getTcpSpeed();
                        long bitRate = ijkMediaPlayer.getBitRate();
                        long seekLoadDuration = ijkMediaPlayer.getSeekLoadDuration();
                        InfoHudViewHolder.this.setRowValue(R.string.v_cache, String.format(Locale.US, "%s, %s", InfoHudViewHolder.formatedDurationMilli(videoCachedDuration), InfoHudViewHolder.formatedSize(videoCachedBytes)));
                        InfoHudViewHolder.this.setRowValue(R.string.a_cache, String.format(Locale.US, "%s, %s", InfoHudViewHolder.formatedDurationMilli(audioCachedDuration), InfoHudViewHolder.formatedSize(audioCachedBytes)));
                        InfoHudViewHolder.this.setRowValue(R.string.load_cost, String.format(Locale.US, "%d ms", Long.valueOf(InfoHudViewHolder.this.mLoadCost)));
                        InfoHudViewHolder.this.setRowValue(R.string.seek_cost, String.format(Locale.US, "%d ms", Long.valueOf(InfoHudViewHolder.this.mSeekCost)));
                        InfoHudViewHolder.this.setRowValue(R.string.seek_load_cost, String.format(Locale.US, "%d ms", Long.valueOf(seekLoadDuration)));
                        InfoHudViewHolder.this.setRowValue(R.string.tcp_speed, String.format(Locale.US, "%s", InfoHudViewHolder.formatedSpeed(tcpSpeed, 1000)));
                        InfoHudViewHolder.this.setRowValue(R.string.bit_rate, String.format(Locale.US, "%.2f kbs", Float.valueOf(((float) bitRate) / 1000.0f)));
                        InfoHudViewHolder.this.mHandler.removeMessages(1);
                        InfoHudViewHolder.this.mHandler.sendEmptyMessageDelayed(1, 500);
                    }
                }
            }
        }
    };
    public long mLoadCost = 0;
    public IMediaPlayer mMediaPlayer;
    private SparseArray<View> mRowMap = new SparseArray<>();
    public long mSeekCost = 0;
    private TableLayoutBinder mTableLayoutBinder;

    public static int ux() {
        return 7;
    }

    public InfoHudViewHolder(Context context, TableLayout tableLayout) {
        this.mTableLayoutBinder = new TableLayoutBinder(context, tableLayout);
    }

    private void appendSection(int i) {
        this.mTableLayoutBinder.appendSection(i);
    }

    private void appendRow(int i) {
        this.mRowMap.put(i, this.mTableLayoutBinder.appendRow2(i, (String) null));
    }

    public void setRowValue(int i, String str) {
        View view = this.mRowMap.get(i);
        if (view == null) {
            this.mRowMap.put(i, this.mTableLayoutBinder.appendRow2(i, str));
            return;
        }
        this.mTableLayoutBinder.setValueText(view, str);
    }

    public void setMediaPlayer(IMediaPlayer iMediaPlayer) {
        this.mMediaPlayer = iMediaPlayer;
        if (this.mMediaPlayer != null) {
            this.mHandler.sendEmptyMessageDelayed(1, 500);
        } else {
            this.mHandler.removeMessages(1);
        }
    }

    public static String formatedDurationMilli(long j) {
        if (j >= 1000) {
            return String.format(Locale.US, "%.2f sec", Float.valueOf(((float) j) / 1000.0f));
        }
        return String.format(Locale.US, "%d msec", Long.valueOf(j));
    }

    public static String formatedSpeed(long j, long j2) {
        if (j2 <= 0 || j <= 0) {
            return "0 B/s";
        }
        float f = (((float) j) * 1000.0f) / ((float) j2);
        if (f >= 1000000.0f) {
            return String.format(Locale.US, "%.2f MB/s", Float.valueOf((f / 1000.0f) / 1000.0f));
        } else if (f >= 1000.0f) {
            return String.format(Locale.US, "%.1f KB/s", Float.valueOf(f / 1000.0f));
        } else {
            return String.format(Locale.US, "%d B/s", Long.valueOf((long) f));
        }
    }

    public void updateLoadCost(long j) {
        this.mLoadCost = j;
    }

    public void updateSeekCost(long j) {
        this.mSeekCost = j;
    }

    public static String formatedSize(long j) {
        if (j >= 100000) {
            return String.format(Locale.US, "%.2f MB", Float.valueOf((((float) j) / 1000.0f) / 1000.0f));
        } else if (j >= 100) {
            return String.format(Locale.US, "%.1f KB", Float.valueOf(((float) j) / 1000.0f));
        } else {
            return String.format(Locale.US, "%d B", Long.valueOf(j));
        }
    }
}
