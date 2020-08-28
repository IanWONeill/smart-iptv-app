package com.nst.yourname.view.ijkplayer.widget.media;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.database.PlayerResumeDBHandler;
import com.nst.yourname.model.database.RecentWatchDBHandler;
import com.nst.yourname.view.ijkplayer.application.Settings;
import com.nst.yourname.view.ijkplayer.services.MediaPlayerService;
import com.nst.yourname.view.ijkplayer.widget.media.IRenderView;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.joda.time.DateTimeConstants;
import tv.danmaku.ijk.media.player.AndroidMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;
import tv.danmaku.ijk.media.player.TextureMediaPlayer;
import tv.danmaku.ijk.media.player.misc.IMediaFormat;
import tv.danmaku.ijk.media.player.misc.ITrackInfo;
import tv.danmaku.ijk.media.player.misc.IjkMediaFormat;

@SuppressWarnings("ALL")
public class NSTIJKPlayerSeries extends FrameLayout implements MediaController.MediaPlayerControl {
    static final boolean $assertionsDisabled = false;
    private static final int FADE_OUT = 1;
    private static final int MESSAGE_FADE_OUT = 2;
    private static final int MESSAGE_HIDE_CENTER_BOX = 4;
    private static final int MESSAGE_RESTART_PLAY = 5;
    private static final int MESSAGE_SEEK_NEW_POSITION = 3;
    private static final int MESSAGE_SHOW_PROGRESS = 1;
    public static final int RENDER_NONE = 0;
    public static final int RENDER_SURFACE_VIEW = 1;
    public static final int RENDER_TEXTURE_VIEW = 2;
    private static final int SHOW_PROGRESS = 2;
    private static final int STATE_BUFFERING_END = 6;
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PREPARING = 1;
    private static final int sDefaultTimeout = 3000;
    private static final int[] s_allAspectRatio = {0, 1, 2, 3, 4, 5};
    public Query $;
    public String TAG = "NSTIJKPlayerForSeries";
    private AudioManager audioManager;
    private Boolean audioTrackFound = false;
    private float brightness = -1.0f;
    private int currentWindowIndex = 0;
    public int defaultTimeout = 7000;
    public AlertDialog dirsDialog;
    private Boolean disableAudioTrack = false;
    private Boolean disableSubTitleTrack = false;
    private Boolean disableVideoTrack = false;
    private boolean fullScreenOnly;
    private boolean fullscreen;
    public GestureDetector gestureDetector;
    private Handler handler;
    private Handler handler1 = new Handler(Looper.getMainLooper()) {
        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass5 */

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 2) {
                NSTIJKPlayerSeries.this.hide(false);
            } else if (i == 4) {
                NSTIJKPlayerSeries.this.$.id(R.id.app_video_volume_box).gone();
                NSTIJKPlayerSeries.this.$.id(R.id.app_video_brightness_box).gone();
                NSTIJKPlayerSeries.this.$.id(R.id.app_video_fastForward_box).gone();
            }
        }
    };
    private Handler handlerAspectRatio;
    public Handler handlerSeekbar;
    public boolean hideEPGData = true;
    public boolean isHWOrSW = false;
    private boolean isLive = false;
    public boolean isShowing;
    public boolean isTimeElapsed = false;
    public boolean isVODPlayer = false;
    private View liveBox;
    ArrayList<LiveStreamsDBModel> liveListDetailAvailableChannels_temp;
    SharedPreferences loginPreferencesAfterLogin;
    SharedPreferences loginPreferencesAfterLoginOPENGL;
    SharedPreferences loginPreferencesAfterLoginOPENSL_ES;
    private SharedPreferences loginPreferencesUserAgent;
    public SharedPreferences loginPreferences_audio_selected;
    private SharedPreferences loginPreferences_check_subtitle;
    public SharedPreferences loginPreferences_seek_time;
    public SharedPreferences loginPreferences_subtitle_selected;
    public SharedPreferences loginPreferences_video_selected;
    public SharedPreferences.Editor loginPrefsEditorAudio;
    SharedPreferences.Editor loginPrefsEditorSeekTime;
    public SharedPreferences.Editor loginPrefsEditorSubtitle;
    public SharedPreferences.Editor loginPrefsEditorVideo;
    public Activity mActivity;
    private List<Integer> mAllRenders = new ArrayList();
    private Context mAppContext;
    private IMediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass12 */

        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnBufferingUpdateListener
        public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
            int unused = NSTIJKPlayerSeries.this.mCurrentBufferPercentage = i;
        }
    };
    private boolean mCanPause = true;
    private boolean mCanSeekBack = true;
    private boolean mCanSeekForward = true;
    private IMediaPlayer.OnCompletionListener mCompletionListener = new IMediaPlayer.OnCompletionListener() {
        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass9 */

        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnCompletionListener
        public void onCompletion(IMediaPlayer iMediaPlayer) {
            int unused = NSTIJKPlayerSeries.this.mCurrentState = 5;
            int unused2 = NSTIJKPlayerSeries.this.mTargetState = 5;
            if (NSTIJKPlayerSeries.this.mMediaController != null) {
                NSTIJKPlayerSeries.this.mMediaController.hide();
            }
            NSTIJKPlayerSeries.this.mActivity.findViewById(R.id.controls).setVisibility(0);
            NSTIJKPlayerSeries.this.mActivity.findViewById(R.id.exo_next).performClick();
            if (NSTIJKPlayerSeries.this.mOnCompletionListener != null) {
                NSTIJKPlayerSeries.this.mOnCompletionListener.onCompletion(NSTIJKPlayerSeries.this.mMediaPlayer);
            }
        }
    };
    private int mCurrentAspectRatio = s_allAspectRatio[0];
    private int mCurrentAspectRatioIndex = 4;
    public int mCurrentBufferPercentage;
    private int mCurrentRender = 0;
    private int mCurrentRenderIndex = 0;
    public int mCurrentState = 0;
    public boolean mDragging;
    private boolean mEnableBackgroundPlay = false;
    private IMediaPlayer.OnErrorListener mErrorListener = new IMediaPlayer.OnErrorListener() {
        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass11 */

        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnErrorListener
        public boolean onError(IMediaPlayer iMediaPlayer, int i, int i2) {
            String access$3900 = NSTIJKPlayerSeries.this.TAG;
            Log.d(access$3900, "Error: " + i + "," + i2);
            int unused = NSTIJKPlayerSeries.this.mCurrentState = -1;
            int unused2 = NSTIJKPlayerSeries.this.mTargetState = -1;
            if (NSTIJKPlayerSeries.this.mMediaController != null) {
                NSTIJKPlayerSeries.this.mMediaController.hide();
            }
            NSTIJKPlayerSeries.this.statusChange(-1);
            return (NSTIJKPlayerSeries.this.mOnErrorListener == null || NSTIJKPlayerSeries.this.mOnErrorListener.onError(NSTIJKPlayerSeries.this.mMediaPlayer, i, i2)) ? true : true;
        }
    };
    StringBuilder mFormatBuilder;
    Formatter mFormatter;
    public Handler mHandler = new MessageHandler(this);
    private Map<String, String> mHeaders;
    private IMediaPlayer.OnInfoListener mInfoListener = new IMediaPlayer.OnInfoListener() {
        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass10 */

        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnInfoListener
        public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i2) {
            if (NSTIJKPlayerSeries.this.mOnInfoListener != null) {
                NSTIJKPlayerSeries.this.mOnInfoListener.onInfo(iMediaPlayer, i, i2);
            }
            switch (i) {
                case 3:
                    NSTIJKPlayerSeries.this.statusChange(2);
                    Log.d(NSTIJKPlayerSeries.this.TAG, "MEDIA_INFO_VIDEO_RENDERING_START:");
                    break;
                case 700:
                    Log.d(NSTIJKPlayerSeries.this.TAG, "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                    break;
                case IMediaPlayer.MEDIA_INFO_BUFFERING_START /*701*/:
                    NSTIJKPlayerSeries.this.statusChange(1);
                    Log.d(NSTIJKPlayerSeries.this.TAG, "MEDIA_INFO_BUFFERING_START:");
                    break;
                case IMediaPlayer.MEDIA_INFO_BUFFERING_END /*702*/:
                    NSTIJKPlayerSeries.this.statusChange(6);
                    Log.d(NSTIJKPlayerSeries.this.TAG, "MEDIA_INFO_BUFFERING_END:");
                    break;
                case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH /*703*/:
                    String access$3900 = NSTIJKPlayerSeries.this.TAG;
                    Log.d(access$3900, "MEDIA_INFO_NETWORK_BANDWIDTH: " + i2);
                    break;
                case 800:
                    Log.d(NSTIJKPlayerSeries.this.TAG, "MEDIA_INFO_BAD_INTERLEAVING:");
                    break;
                case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE /*801*/:
                    Log.d(NSTIJKPlayerSeries.this.TAG, "MEDIA_INFO_NOT_SEEKABLE:");
                    break;
                case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE /*802*/:
                    Log.d(NSTIJKPlayerSeries.this.TAG, "MEDIA_INFO_METADATA_UPDATE:");
                    break;
                case IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE /*901*/:
                    Log.d(NSTIJKPlayerSeries.this.TAG, "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                    break;
                case IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT /*902*/:
                    Log.d(NSTIJKPlayerSeries.this.TAG, "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                    break;
                case 10001:
                    int unused = NSTIJKPlayerSeries.this.mVideoRotationDegree = i2;
                    String access$39002 = NSTIJKPlayerSeries.this.TAG;
                    Log.d(access$39002, "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + i2);
                    if (NSTIJKPlayerSeries.this.mRenderView != null) {
                        NSTIJKPlayerSeries.this.mRenderView.setVideoRotation(i2);
                        break;
                    }
                    break;
                case 10002:
                    NSTIJKPlayerSeries.this.statusChange(2);
                    Log.d(NSTIJKPlayerSeries.this.TAG, "MEDIA_INFO_AUDIO_RENDERING_START:");
                    break;
                case IMediaPlayer.MEDIA_INFO_OPEN_INPUT /*10005*/:
                    NSTIJKPlayerSeries.this.statusChange(1);
                    Log.d(NSTIJKPlayerSeries.this.TAG, "MEDIA_INFO_OPEN_INPUT:");
                    break;
            }
            return true;
        }
    };
    private int mMaxVolume;
    public IMediaController mMediaController;
    public IMediaPlayer mMediaPlayer = null;
    public IMediaPlayer.OnCompletionListener mOnCompletionListener;
    public IMediaPlayer.OnErrorListener mOnErrorListener;
    public IMediaPlayer.OnInfoListener mOnInfoListener;
    public IMediaPlayer.OnPreparedListener mOnPreparedListener;
    private IMediaPlayer.OnTimedTextListener mOnTimedTextListener = new IMediaPlayer.OnTimedTextListener() {
        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass14 */

        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnTimedTextListener
        public void onTimedText(IMediaPlayer iMediaPlayer, IjkTimedText ijkTimedText) {
            if (ijkTimedText != null) {
                NSTIJKPlayerSeries.this.subtitleDisplay.setText(ijkTimedText.getText().replace("{\\b1}", "").replace("{\\b0}", "").replace("{\\i1}", "").replace("{\\i0}", ""));
            }
        }
    };
    public long mPrepareEndTime = 0;
    private long mPrepareStartTime = 0;
    IMediaPlayer.OnPreparedListener mPreparedListener = new IMediaPlayer.OnPreparedListener() {
        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass8 */

        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnPreparedListener
        public void onPrepared(IMediaPlayer iMediaPlayer) {
            long unused = NSTIJKPlayerSeries.this.mPrepareEndTime = System.currentTimeMillis();
            int unused2 = NSTIJKPlayerSeries.this.mCurrentState = 2;
            if (NSTIJKPlayerSeries.this.mOnPreparedListener != null) {
                NSTIJKPlayerSeries.this.mOnPreparedListener.onPrepared(NSTIJKPlayerSeries.this.mMediaPlayer);
            }
            if (NSTIJKPlayerSeries.this.mMediaController != null) {
                NSTIJKPlayerSeries.this.mMediaController.setEnabled(true);
            }
            int unused3 = NSTIJKPlayerSeries.this.mVideoWidth = iMediaPlayer.getVideoWidth();
            int unused4 = NSTIJKPlayerSeries.this.mVideoHeight = iMediaPlayer.getVideoHeight();
            int access$3300 = NSTIJKPlayerSeries.this.mSeekWhenPrepared;
            if (access$3300 != 0) {
                NSTIJKPlayerSeries.this.seekTo(access$3300);
            }
            if (NSTIJKPlayerSeries.this.mVideoWidth == 0 || NSTIJKPlayerSeries.this.mVideoHeight == 0) {
                if (NSTIJKPlayerSeries.this.mTargetState == 3) {
                    NSTIJKPlayerSeries.this.start();
                }
            } else if (NSTIJKPlayerSeries.this.mRenderView != null) {
                NSTIJKPlayerSeries.this.mRenderView.setVideoSize(NSTIJKPlayerSeries.this.mVideoWidth, NSTIJKPlayerSeries.this.mVideoHeight);
                NSTIJKPlayerSeries.this.mRenderView.setVideoSampleAspectRatio(NSTIJKPlayerSeries.this.mVideoSarNum, NSTIJKPlayerSeries.this.mVideoSarDen);
                if (NSTIJKPlayerSeries.this.mRenderView.shouldWaitForResize() && (NSTIJKPlayerSeries.this.mSurfaceWidth != NSTIJKPlayerSeries.this.mVideoWidth || NSTIJKPlayerSeries.this.mSurfaceHeight != NSTIJKPlayerSeries.this.mVideoHeight)) {
                    return;
                }
                if (NSTIJKPlayerSeries.this.mTargetState == 3) {
                    NSTIJKPlayerSeries.this.start();
                    if (NSTIJKPlayerSeries.this.mMediaController != null) {
                        NSTIJKPlayerSeries.this.mMediaController.show();
                    }
                } else if (NSTIJKPlayerSeries.this.isPlaying()) {
                } else {
                    if ((access$3300 != 0 || NSTIJKPlayerSeries.this.getCurrentPosition() > 0) && NSTIJKPlayerSeries.this.mMediaController != null) {
                        NSTIJKPlayerSeries.this.mMediaController.show(0);
                    }
                }
            }
        }
    };
    public IRenderView mRenderView;
    IRenderView.IRenderCallback mSHCallback = new IRenderView.IRenderCallback() {
        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass15 */

        @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView.IRenderCallback
        public void onSurfaceChanged(@NonNull IRenderView.ISurfaceHolder iSurfaceHolder, int i, int i2, int i3) {
            if (iSurfaceHolder.getRenderView() != NSTIJKPlayerSeries.this.mRenderView) {
                Log.e(NSTIJKPlayerSeries.this.TAG, "onSurfaceChanged: unmatched render callback\n");
                return;
            }
            int unused = NSTIJKPlayerSeries.this.mSurfaceWidth = i2;
            int unused2 = NSTIJKPlayerSeries.this.mSurfaceHeight = i3;
            boolean z = false;
            boolean z2 = NSTIJKPlayerSeries.this.mTargetState == 3;
            if (!NSTIJKPlayerSeries.this.mRenderView.shouldWaitForResize() || (NSTIJKPlayerSeries.this.mVideoWidth == i2 && NSTIJKPlayerSeries.this.mVideoHeight == i3)) {
                z = true;
            }
            if (NSTIJKPlayerSeries.this.mMediaPlayer != null && z2 && z) {
                if (NSTIJKPlayerSeries.this.mSeekWhenPrepared != 0) {
                    NSTIJKPlayerSeries.this.seekTo(NSTIJKPlayerSeries.this.mSeekWhenPrepared);
                }
                NSTIJKPlayerSeries.this.start();
            }
        }

        @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView.IRenderCallback
        public void onSurfaceCreated(@NonNull IRenderView.ISurfaceHolder iSurfaceHolder, int i, int i2) {
            if (iSurfaceHolder.getRenderView() != NSTIJKPlayerSeries.this.mRenderView) {
                Log.e(NSTIJKPlayerSeries.this.TAG, "onSurfaceCreated: unmatched render callback\n");
                return;
            }
            IRenderView.ISurfaceHolder unused = NSTIJKPlayerSeries.this.mSurfaceHolder = iSurfaceHolder;
            if (NSTIJKPlayerSeries.this.mMediaPlayer != null) {
                NSTIJKPlayerSeries.this.bindSurfaceHolder(NSTIJKPlayerSeries.this.mMediaPlayer, iSurfaceHolder);
            } else {
                NSTIJKPlayerSeries.this.openVideo();
            }
        }

        @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView.IRenderCallback
        public void onSurfaceDestroyed(@NonNull IRenderView.ISurfaceHolder iSurfaceHolder) {
            if (iSurfaceHolder.getRenderView() != NSTIJKPlayerSeries.this.mRenderView) {
                Log.e(NSTIJKPlayerSeries.this.TAG, "onSurfaceDestroyed: unmatched render callback\n");
                return;
            }
            IRenderView.ISurfaceHolder unused = NSTIJKPlayerSeries.this.mSurfaceHolder = null;
            NSTIJKPlayerSeries.this.releaseWithoutStop();
        }
    };
    private IMediaPlayer.OnSeekCompleteListener mSeekCompleteListener = new IMediaPlayer.OnSeekCompleteListener() {
        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass13 */

        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnSeekCompleteListener
        public void onSeekComplete(IMediaPlayer iMediaPlayer) {
            long unused = NSTIJKPlayerSeries.this.mSeekEndTime = System.currentTimeMillis();
        }
    };
    public long mSeekEndTime = 0;
    private SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {
        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass2 */

        public void onStartTrackingTouch(SeekBar seekBar) {
            NSTIJKPlayerSeries.this.show(DateTimeConstants.MILLIS_PER_HOUR);
            boolean unused = NSTIJKPlayerSeries.this.mDragging = true;
            NSTIJKPlayerSeries.this.mHandler.removeMessages(2);
        }

        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (NSTIJKPlayerSeries.this.mMediaPlayer != null && z) {
                long duration = NSTIJKPlayerSeries.this.mMediaPlayer.getDuration();
                int i2 = (int) ((((long) i) * duration) / 1000);
                NSTIJKPlayerSeries.this.mMediaPlayer.seekTo((long) i2);
                TextView access$1100 = NSTIJKPlayerSeries.this.txtDisplay;
                access$1100.setText(NSTIJKPlayerSeries.this.stringForTime(i2) + "/" + NSTIJKPlayerSeries.this.stringForTime((int) duration));
            }
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            boolean unused = NSTIJKPlayerSeries.this.mDragging = false;
            int unused2 = NSTIJKPlayerSeries.this.setProgress();
            NSTIJKPlayerSeries.this.updatePausePlay();
            NSTIJKPlayerSeries.this.show(3000);
            NSTIJKPlayerSeries.this.mHandler.sendEmptyMessage(2);
        }
    };
    private long mSeekStartTime = 0;
    public int mSeekWhenPrepared;
    private Settings mSettings;
    public boolean mShowing;
    IMediaPlayer.OnVideoSizeChangedListener mSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() {
        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass7 */

        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnVideoSizeChangedListener
        public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i2, int i3, int i4) {
            int unused = NSTIJKPlayerSeries.this.mVideoWidth = iMediaPlayer.getVideoWidth();
            int unused2 = NSTIJKPlayerSeries.this.mVideoHeight = iMediaPlayer.getVideoHeight();
            int unused3 = NSTIJKPlayerSeries.this.mVideoSarNum = iMediaPlayer.getVideoSarNum();
            int unused4 = NSTIJKPlayerSeries.this.mVideoSarDen = iMediaPlayer.getVideoSarDen();
            if (NSTIJKPlayerSeries.this.mVideoWidth != 0 && NSTIJKPlayerSeries.this.mVideoHeight != 0) {
                if (NSTIJKPlayerSeries.this.mRenderView != null) {
                    NSTIJKPlayerSeries.this.mRenderView.setVideoSize(NSTIJKPlayerSeries.this.mVideoWidth, NSTIJKPlayerSeries.this.mVideoHeight);
                    NSTIJKPlayerSeries.this.mRenderView.setVideoSampleAspectRatio(NSTIJKPlayerSeries.this.mVideoSarNum, NSTIJKPlayerSeries.this.mVideoSarDen);
                }
                NSTIJKPlayerSeries.this.requestLayout();
            }
        }
    };
    public int mSurfaceHeight;
    public IRenderView.ISurfaceHolder mSurfaceHolder = null;
    public int mSurfaceWidth;
    public int mTargetState = 0;
    private Uri mUri;
    public int mVideoHeight;
    public int mVideoRotationDegree;
    public int mVideoSarDen;
    public int mVideoSarNum;
    private String mVideoTitle;
    public NSTIJKPlayerSeries mVideoView;
    public int mVideoWidth;
    public int maxRetry = 5;
    public Button negativeButton;
    private long newPosition = -1;
    private int openedStreamId;
    private PlayerResumeDBHandler playerResumeDBHandler;
    private RecentWatchDBHandler recentWatchDBHandler;
    public boolean resuming = false;
    public int retryCount = 0;
    public boolean retrying = false;
    public Runnable runnableSeekbar;
    public int screenWidthPixels;
    public boolean seeked = false;
    public boolean setMaxTime = false;
    SharedPreferences.Editor sharedPrefEditor;
    SharedPreferences sharedPreferences;
    public int status = 0;
    public TextView subtitleDisplay;
    private Boolean subtitleTrackFound = false;
    public long timeElapsed;
    public TextView txtDisplay;
    String typeofStream;
    private int userIdReferred;
    private Boolean videoTrackFound = false;
    int video_num;
    public SeekBar vlcSeekbar;
    private int volume = -1;

    public int getAudioSessionId() {
        return 0;
    }

    public void setHudView(TableLayout tableLayout) {
    }

    public NSTIJKPlayerSeries(Context context) {
        super(context);
        initVideoView(context);
    }

    public NSTIJKPlayerSeries(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initVideoView(context);
    }

    public NSTIJKPlayerSeries(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initVideoView(context);
    }

    @TargetApi(21)
    public NSTIJKPlayerSeries(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initVideoView(context);
    }

    private void initVideoView(Context context) {
        this.mAppContext = context.getApplicationContext();
        this.mSettings = new Settings(this.mAppContext);
        initBackground();
        initRenders();
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.subtitleDisplay = new TextView(context);
        this.subtitleDisplay.setTextSize(24.0f);
        this.subtitleDisplay.setTextColor(context.getResources().getColor(R.color.white));
        this.subtitleDisplay.setGravity(17);
        addView(this.subtitleDisplay, new LayoutParams(-1, -2, 80));
    }

    public void setRenderView(IRenderView iRenderView) {
        if (this.mRenderView != null) {
            if (this.mMediaPlayer != null) {
                this.mMediaPlayer.setDisplay(null);
            }
            View view = this.mRenderView.getView();
            this.mRenderView.removeRenderCallback(this.mSHCallback);
            this.mRenderView = null;
            removeView(view);
        }
        if (iRenderView != null) {
            this.mRenderView = iRenderView;
            this.sharedPreferences = this.mAppContext.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            this.mCurrentAspectRatioIndex = this.sharedPreferences.getInt(AppConst.ASPECT_RATIO, 4);
            iRenderView.setAspectRatio(this.mCurrentAspectRatioIndex);
            if (this.mVideoWidth > 0 && this.mVideoHeight > 0) {
                iRenderView.setVideoSize(this.mVideoWidth, this.mVideoHeight);
            }
            if (this.mVideoSarNum > 0 && this.mVideoSarDen > 0) {
                iRenderView.setVideoSampleAspectRatio(this.mVideoSarNum, this.mVideoSarDen);
            }
            View view2 = this.mRenderView.getView();
            view2.setLayoutParams(new LayoutParams(-2, -2, 17));
            addView(view2);
            this.mRenderView.addRenderCallback(this.mSHCallback);
            this.mRenderView.setVideoRotation(this.mVideoRotationDegree);
        }
    }

    public void setRender(int i) {
        switch (i) {
            case 0:
                setRenderView(null);
                return;
            case 1:
                setRenderView(new SurfaceRenderView(getContext()));
                return;
            case 2:
                TextureRenderView textureRenderView = new TextureRenderView(getContext());
                if (this.mMediaPlayer != null) {
                    textureRenderView.getSurfaceHolder().bindToMediaPlayer(this.mMediaPlayer);
                    textureRenderView.setVideoSize(this.mMediaPlayer.getVideoWidth(), this.mMediaPlayer.getVideoHeight());
                    textureRenderView.setVideoSampleAspectRatio(this.mMediaPlayer.getVideoSarNum(), this.mMediaPlayer.getVideoSarDen());
                    textureRenderView.setAspectRatio(this.mCurrentAspectRatio);
                }
                setRenderView(textureRenderView);
                return;
            default:
                Log.e(this.TAG, String.format(Locale.getDefault(), "invalid render %d\n", Integer.valueOf(i)));
                return;
        }
    }

    public void setVideoPath(String str, boolean z, String str2, long j, int i, String str3, ArrayList<LiveStreamsDBModel> arrayList, int i2, int i3) {
        setVideoURI(Uri.parse(str), z, str2);
    }

    public void setVideoURI(Uri uri, boolean z, String str) {
        setVideoURI(uri, null, z, str);
    }

    private void setVideoURI(Uri uri, Map<String, String> map, boolean z, String str) {
        this.mUri = uri;
        this.mVideoTitle = str;
        this.mHeaders = map;
        this.mSeekWhenPrepared = 0;
        this.fullscreen = z;
        stopPlayback();
        openVideo();
        requestLayout();
        invalidate();
    }

    public void setActivity(Activity activity, NSTIJKPlayerSeries nSTIJKPlayerSeries, SeekBar seekBar, TextView textView) {
        this.mActivity = activity;
        this.mVideoView = nSTIJKPlayerSeries;
        this.handler = new Handler();
        this.vlcSeekbar = seekBar;
        this.txtDisplay = textView;
        this.handlerAspectRatio = new Handler();
        this.recentWatchDBHandler = new RecentWatchDBHandler(activity);
        this.$ = new Query(activity);
    }

    class Query {
        private final Activity activity;
        private View view;

        public Query(Activity activity2) {
            this.activity = activity2;
        }

        public Query id(int i) {
            this.view = this.activity.findViewById(i);
            return this;
        }

        public Query image(int i) {
            if (this.view instanceof ImageView) {
                ((ImageView) this.view).setImageResource(i);
            }
            return this;
        }

        public Query visible() {
            if (this.view != null) {
                this.view.setVisibility(0);
            }
            return this;
        }

        public Query requestFocus() {
            if (this.view != null) {
                this.view.requestFocus();
            }
            return this;
        }

        public Query gone() {
            if (this.view != null) {
                this.view.setVisibility(8);
            }
            return this;
        }

        public Query invisible() {
            if (this.view != null) {
                this.view.setVisibility(4);
            }
            return this;
        }

        public Query clicked(OnClickListener onClickListener) {
            if (this.view != null) {
                this.view.setOnClickListener(onClickListener);
            }
            return this;
        }

        public Query text(CharSequence charSequence) {
            if (this.view != null && (this.view instanceof TextView)) {
                ((TextView) this.view).setText(charSequence);
            }
            return this;
        }

        public Query visibility(int i) {
            if (this.view != null) {
                this.view.setVisibility(i);
            }
            return this;
        }

        private void size(boolean z, int i, boolean z2) {
            if (this.view != null) {
                ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
                if (i > 0 && z2) {
                    i = dip2pixel(this.activity, (float) i);
                }
                if (z) {
                    layoutParams.width = i;
                } else {
                    layoutParams.height = i;
                }
                this.view.setLayoutParams(layoutParams);
            }
        }

        public void height(int i, boolean z) {
            size(false, i, z);
        }

        public int dip2pixel(Context context, float f) {
            return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
        }

        public float pixel2dip(Context context, float f) {
            return f / (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f);
        }
    }

    public void stopPlayback() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            this.mTargetState = 0;
            ((AudioManager) this.mAppContext.getSystemService("audio")).abandonAudioFocus(null);
        }
    }

    @TargetApi(23)
    public void openVideo() {
        if (this.mUri != null && this.mSurfaceHolder != null) {
            release(false);
            ((AudioManager) this.mAppContext.getSystemService("audio")).requestAudioFocus(null, 3, 1);
            try {
                this.mMediaPlayer = createPlayer(this.mSettings.getPlayer());
                getContext();
                this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
                this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
                this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
                this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
                this.mMediaPlayer.setOnInfoListener(this.mInfoListener);
                this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
                this.mMediaPlayer.setOnSeekCompleteListener(this.mSeekCompleteListener);
                this.mMediaPlayer.setOnTimedTextListener(this.mOnTimedTextListener);
                this.mCurrentBufferPercentage = 0;
                String scheme = this.mUri.getScheme();
                if (Build.VERSION.SDK_INT >= 23 && this.mSettings.getUsingMediaDataSource() && (TextUtils.isEmpty(scheme) || scheme.equalsIgnoreCase(AppConst.TYPE_M3U_FILE))) {
                    this.mMediaPlayer.setDataSource(new FileMediaDataSource(new File(this.mUri.toString())));
                } else if (Build.VERSION.SDK_INT >= 14) {
                    this.mMediaPlayer.setDataSource(this.mAppContext, this.mUri, this.mHeaders);
                } else {
                    this.mMediaPlayer.setDataSource(this.mUri.toString());
                }
                bindSurfaceHolder(this.mMediaPlayer, this.mSurfaceHolder);
                this.mMediaPlayer.setAudioStreamType(3);
                this.mMediaPlayer.setScreenOnWhilePlaying(true);
                this.mPrepareStartTime = System.currentTimeMillis();
                this.mMediaPlayer.prepareAsync();
                this.screenWidthPixels = this.mActivity.getResources().getDisplayMetrics().widthPixels;
                this.audioManager = (AudioManager) this.mActivity.getSystemService("audio");
                this.mMaxVolume = this.audioManager.getStreamMaxVolume(3);
                this.gestureDetector = new GestureDetector(this.mActivity, new PlayerGestureListener());
                touchListner();
                this.handlerSeekbar = new Handler();
                this.runnableSeekbar = new Runnable() {
                    /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass1 */
                    static final boolean $assertionsDisabled = false;

                    public void run() {
                        if (NSTIJKPlayerSeries.this.mMediaPlayer != null && NSTIJKPlayerSeries.this.mMediaPlayer.isPlaying()) {
                            int currentPosition = (int) NSTIJKPlayerSeries.this.mMediaPlayer.getCurrentPosition();
                            int duration = (int) NSTIJKPlayerSeries.this.mMediaPlayer.getDuration();
                            if (NSTIJKPlayerSeries.this.vlcSeekbar != null && duration > 0 && (((long) currentPosition) * 1000) / ((long) duration) == 0 && (NSTIJKPlayerSeries.this.retrying || NSTIJKPlayerSeries.this.isHWOrSW)) {
                                SharedPreferences unused = NSTIJKPlayerSeries.this.loginPreferences_seek_time = NSTIJKPlayerSeries.this.mActivity.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
                                NSTIJKPlayerSeries.this.mMediaPlayer.seekTo(Long.parseLong(NSTIJKPlayerSeries.this.loginPreferences_seek_time.getString(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, AppConst.PASSWORD_UNSET)));
                                NSTIJKPlayerSeries.this.isHWOrSW = false;
                            }
                            if (!NSTIJKPlayerSeries.this.setMaxTime) {
                                if (Build.VERSION.SDK_INT >= 26) {
                                    NSTIJKPlayerSeries.this.vlcSeekbar.setMin(0);
                                }
                                NSTIJKPlayerSeries.this.vlcSeekbar.setMax(1000);
                                NSTIJKPlayerSeries.this.setMaxTime = true;
                            }
                            int unused2 = NSTIJKPlayerSeries.this.setProgress();
                        }
                        NSTIJKPlayerSeries.this.handlerSeekbar.postDelayed(NSTIJKPlayerSeries.this.runnableSeekbar, 1000);
                    }
                };
                this.runnableSeekbar.run();
                if (this.vlcSeekbar != null) {
                    this.vlcSeekbar.setOnSeekBarChangeListener(this.mSeekListener);
                    this.vlcSeekbar.setMax(1000);
                }
                this.mFormatBuilder = new StringBuilder();
                this.mFormatter = new Formatter(this.mFormatBuilder, Locale.getDefault());
                this.mCurrentState = 1;
            } catch (IOException e) {
                String str = this.TAG;
                Log.w(str, "Unable to open content: " + this.mUri, e);
                this.mCurrentState = -1;
                this.mTargetState = -1;
                this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
            } catch (IllegalArgumentException e2) {
                String str2 = this.TAG;
                Log.w(str2, "Unable to open content: " + this.mUri, e2);
                this.mCurrentState = -1;
                this.mTargetState = -1;
                this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
            } catch (NullPointerException e3) {
                String str3 = this.TAG;
                Log.w(str3, "Unable to open content: " + this.mUri, e3);
                this.mCurrentState = -1;
                this.mTargetState = -1;
                this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
            } catch (Exception e4) {
                String str4 = this.TAG;
                Log.w(str4, "Unable to open content: " + this.mUri, e4);
                this.mCurrentState = -1;
                this.mTargetState = -1;
                this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
            }
        }
    }

    private static class MessageHandler extends Handler {
        private final WeakReference<NSTIJKPlayerSeries> mView;

        MessageHandler(NSTIJKPlayerSeries nSTIJKPlayerSeries) {
            this.mView = new WeakReference<>(nSTIJKPlayerSeries);
        }

        public void handleMessage(Message message) {
            NSTIJKPlayerSeries nSTIJKPlayerSeries = this.mView.get();
            if (nSTIJKPlayerSeries != null && nSTIJKPlayerSeries.mMediaPlayer != null) {
                switch (message.what) {
                    case 1:
                        nSTIJKPlayerSeries.hide(true);
                        return;
                    case 2:
                        int access$400 = nSTIJKPlayerSeries.setProgress();
                        if (!nSTIJKPlayerSeries.mDragging && nSTIJKPlayerSeries.mShowing && nSTIJKPlayerSeries.mMediaPlayer.isPlaying()) {
                            sendMessageDelayed(obtainMessage(2), (long) (1000 - (access$400 % 1000)));
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public String stringForTime(int i) {
        int i2 = i / 1000;
        int i3 = i2 % 60;
        int i4 = (i2 / 60) % 60;
        int i5 = i2 / DateTimeConstants.SECONDS_PER_HOUR;
        this.mFormatBuilder.setLength(0);
        if (i5 > 0) {
            return this.mFormatter.format("%d:%02d:%02d", Integer.valueOf(i5), Integer.valueOf(i4), Integer.valueOf(i3)).toString();
        }
        return this.mFormatter.format("%02d:%02d", Integer.valueOf(i4), Integer.valueOf(i3)).toString();
    }

    public int setProgress() {
        if (this.mMediaPlayer == null || this.mDragging) {
            return 0;
        }
        int currentPosition = (int) this.mMediaPlayer.getCurrentPosition();
        int duration = (int) this.mMediaPlayer.getDuration();
        if (this.vlcSeekbar != null && duration > 0) {
            this.vlcSeekbar.setProgress((int) ((((long) currentPosition) * 1000) / ((long) duration)));
        }
        TextView textView = this.txtDisplay;
        textView.setText(stringForTime(currentPosition) + "/" + stringForTime(duration));
        return currentPosition;
    }

    public void hideEPGData(Boolean bool) {
        this.hideEPGData = bool.booleanValue();
    }

    public void fullScreenValue(Boolean bool) {
        this.fullscreen = bool.booleanValue();
    }

    public Boolean getFullScreenValue() {
        return Boolean.valueOf(this.fullscreen);
    }

    @SuppressLint({"InlinedApi"})
    public void hideSystemUi() {
        this.mVideoView.setSystemUiVisibility(4871);
    }

    public void setCurrentWindowIndex(int i) {
        this.currentWindowIndex = i;
    }

    public int getCurrentWindowIndex() {
        return this.currentWindowIndex;
    }

    public void removeHandlerCallback() {
        if (this.handler != null) {
            this.handler.removeCallbacksAndMessages(null);
        }
    }

    private void touchListner() {
        this.liveBox = this.mActivity.findViewById(R.id.app_video_box);
        this.liveBox.setClickable(true);
        this.liveBox.setOnTouchListener(new OnTouchListener() {
            /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass3 */

            @SuppressLint({"ClickableViewAccessibility"})
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent == null) {
                    return false;
                }
                if (NSTIJKPlayerSeries.this.gestureDetector.onTouchEvent(motionEvent)) {
                    return true;
                }
                if ((motionEvent.getAction() & 255) != 1) {
                    return false;
                }
                NSTIJKPlayerSeries.this.endGesture();
                return false;
            }
        });
    }

    public void onBrightnessSlide(float f) {
        if (this.mActivity != null) {
            if (this.brightness < 0.0f) {
                this.brightness = this.mActivity.getWindow().getAttributes().screenBrightness;
                if (this.brightness <= 0.0f) {
                    this.brightness = 0.5f;
                } else if (this.brightness < 0.01f) {
                    this.brightness = 0.01f;
                }
            }
            String simpleName = getClass().getSimpleName();
            Log.d(simpleName, "brightness:" + this.brightness + ",percent:" + f);
            this.$.id(R.id.app_video_volume_box).gone();
            this.$.id(R.id.app_video_brightness_box).visible();
            WindowManager.LayoutParams attributes = this.mActivity.getWindow().getAttributes();
            attributes.screenBrightness = this.brightness + f;
            if (attributes.screenBrightness > 1.0f) {
                attributes.screenBrightness = 1.0f;
            } else if (attributes.screenBrightness < 0.01f) {
                attributes.screenBrightness = 0.01f;
            }
            Query id = this.$.id(R.id.app_video_brightness);
            id.text(((int) (attributes.screenBrightness * 100.0f)) + "%");
            this.mActivity.getWindow().setAttributes(attributes);
        }
    }

    public void onVolumeSlide(float f) {
        try {
            if (this.audioManager != null) {
                if (this.volume == -1) {
                    this.volume = this.audioManager.getStreamVolume(3);
                    if (this.volume < 0) {
                        this.volume = 0;
                    }
                }
                hide(true);
                int i = ((int) (f * ((float) this.mMaxVolume))) + this.volume;
                if (i > this.mMaxVolume) {
                    i = this.mMaxVolume;
                } else if (i < 0) {
                    i = 0;
                }
                this.audioManager.setStreamVolume(3, i, 0);
                double d = (double) i;
                Double.isNaN(d);
                double d2 = d * 1.0d;
                double d3 = (double) this.mMaxVolume;
                Double.isNaN(d3);
                int i2 = (int) ((d2 / d3) * 100.0d);
                String str = i2 + "%";
                if (i2 == 0) {
                    str = "off";
                }
                this.$.id(R.id.app_video_volume_icon).image(i2 == 0 ? R.drawable.ic_volume_off_white_36dp : R.drawable.ic_volume_up_white_36dp);
                this.$.id(R.id.app_video_brightness_box).gone();
                this.$.id(R.id.app_video_volume_box).visible();
                this.$.id(R.id.app_video_volume).text(str).visible();
            }
        } catch (Exception unused) {
        }
    }

    public void endGesture() {
        this.volume = -1;
        this.brightness = -1.0f;
        long j = this.newPosition;
        this.handler1.removeMessages(4);
        this.handler1.sendEmptyMessageDelayed(4, 500);
    }

    public void updatePausePlay() {
        if (this.mMediaPlayer == null || !this.mMediaPlayer.isPlaying()) {
            this.$.id(R.id.exo_pause).gone();
            this.$.id(R.id.exo_play).visible();
            this.$.id(R.id.exo_play).requestFocus();
            return;
        }
        this.$.id(R.id.exo_play).gone();
        this.$.id(R.id.exo_pause).visible();
        this.$.id(R.id.exo_pause).requestFocus();
    }

    public void showBottomControl(boolean z) {
        int i = 8;
        this.$.id(R.id.app_video_currentTime).visibility(z ? 0 : 8);
        this.$.id(R.id.app_video_endTime).visibility(z ? 0 : 8);
        Query id = this.$.id(R.id.app_video_seekBar);
        if (z) {
            i = 0;
        }
        id.visibility(i);
    }

    public void showAll() {
        this.isShowing = true;
        this.$.id(R.id.app_video_top_box).visible();
        this.$.id(R.id.ll_seekbar_time).visible();
        this.$.id(R.id.controls).visible();
        showBottomControl(true);
        show(this.defaultTimeout);
        this.handler.removeMessages(2);
    }

    public void show(int i) {
        this.$.id(R.id.app_video_top_box).visible();
        this.$.id(R.id.controls).visible();
        this.$.id(R.id.ll_seekbar_time).visible();
        if (!this.isLive) {
            showBottomControl(true);
        }
        if (!this.fullScreenOnly) {
            this.$.id(R.id.app_video_fullscreen).visible();
        }
        ((LinearLayout) this.mActivity.findViewById(R.id.ll_categories_view)).setVisibility(0);
        this.handler.postDelayed(new Runnable() {
            /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass4 */

            public void run() {
                NSTIJKPlayerSeries.this.hideTitleBarAndFooter();
            }
        }, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
        updatePausePlay();
    }

    private void showStatus(String str) {
        this.$.id(R.id.video_view).gone();
        this.$.id(R.id.app_video_status).visible();
        this.$.id(R.id.app_video_status_text).text(str);
    }

    public void statusChange(int i) {
        this.status = i;
        if (i == -1) {
            boolean z = this.fullscreen;
            if (this.retryCount >= this.maxRetry) {
                hideAll();
                if (AppConst.WATER_MARK.booleanValue()) {
                    findViewById(R.id.watrmrk).setVisibility(8);
                }
                showStatus(this.mActivity.getResources().getString(R.string.small_problem));
                stopPlayback();
                this.retrying = false;
                return;
            }
            long currentPosition = this.mMediaPlayer.getCurrentPosition();
            this.loginPreferences_seek_time = this.mActivity.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
            this.loginPrefsEditorSeekTime = this.loginPreferences_seek_time.edit();
            this.loginPrefsEditorSeekTime.putString(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, String.valueOf(currentPosition));
            this.loginPrefsEditorSeekTime.apply();
            this.retrying = true;
            this.handler.postDelayed(new Runnable() {
                /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass6 */

                public void run() {
                    NSTIJKPlayerSeries.this.retryCount++;
                    NSTIJKPlayerSeries.this.hideAll();
                    Utils.showToast(NSTIJKPlayerSeries.this.mActivity, NSTIJKPlayerSeries.this.mActivity.getResources().getString(R.string.play_back_error) + " (" + NSTIJKPlayerSeries.this.retryCount + "/" + NSTIJKPlayerSeries.this.maxRetry + ")");
                    NSTIJKPlayerSeries.this.openVideo();
                    NSTIJKPlayerSeries.this.start();
                }
            }, 3000);
        } else if (i == 1) {
            this.$.id(R.id.app_video_loading).visible();
        } else if (i == 3) {
            this.retryCount = 0;
            this.$.id(R.id.exo_play).gone();
            this.$.id(R.id.exo_pause).visible();
            if (this.fullscreen) {
                this.$.id(R.id.exo_pause).requestFocus();
            }
            this.$.id(R.id.app_video_loading).gone();
        } else if (i == 6) {
            this.retryCount = 0;
            this.$.id(R.id.app_video_status).gone();
            this.$.id(R.id.video_view).visible();
            this.$.id(R.id.exo_play).gone();
            this.$.id(R.id.exo_pause).visible();
            this.$.id(R.id.app_video_loading).gone();
        } else if (i == 2) {
            int selectedTrack = MediaPlayerCompat.getSelectedTrack(this.mMediaPlayer, 1);
            int selectedTrack2 = MediaPlayerCompat.getSelectedTrack(this.mMediaPlayer, 2);
            int selectedTrack3 = MediaPlayerCompat.getSelectedTrack(this.mMediaPlayer, 3);
            this.loginPreferences_check_subtitle = this.mActivity.getSharedPreferences(AppConst.LOGIN_PREF_AUTO_START, 0);
            boolean z2 = this.loginPreferences_check_subtitle.getBoolean(AppConst.LOGIN_PREF_ACTIVE_SUBTITLES, true);
            this.loginPreferences_subtitle_selected = this.mActivity.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SUBTITLE_TRACK, 0);
            int i2 = this.loginPreferences_subtitle_selected.getInt(AppConst.LOGIN_PREF_CURRENT_SUBTITLE_TRACK, -2);
            if (z2) {
                if (!(i2 == -2 || i2 == selectedTrack3)) {
                    if (i2 == -1) {
                        deselectTrack(selectedTrack3);
                    } else {
                        selectTrack(i2);
                    }
                }
            } else if (i2 == -2) {
                if (selectedTrack3 != -1) {
                    deselectTrack(selectedTrack3);
                }
            } else if (i2 != selectedTrack3) {
                if (i2 == -1) {
                    deselectTrack(selectedTrack3);
                } else {
                    selectTrack(i2);
                }
            }
            this.loginPreferences_audio_selected = this.mActivity.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_AUDIO_TRACK, 0);
            int i3 = this.loginPreferences_audio_selected.getInt(AppConst.LOGIN_PREF_CURRENT_AUDIO_TRACK, -2);
            if (!(i3 == -2 || i3 == selectedTrack2)) {
                if (i3 == -1) {
                    deselectTrack(selectedTrack2);
                } else {
                    selectTrack(i3);
                }
            }
            this.loginPreferences_video_selected = this.mActivity.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_VIDEO_TRACK, 0);
            int i4 = this.loginPreferences_video_selected.getInt(AppConst.LOGIN_PREF_CURRENT_VIDEO_TRACK, -2);
            if (!(i4 == -2 || i4 == selectedTrack)) {
                if (i4 == -1) {
                    deselectTrack(selectedTrack);
                } else {
                    selectTrack(i4);
                }
            }
            this.retryCount = 0;
            this.$.id(R.id.app_video_status).gone();
            this.$.id(R.id.video_view).visible();
            this.$.id(R.id.exo_play).gone();
            this.$.id(R.id.exo_pause).visible();
            this.$.id(R.id.app_video_loading).gone();
        } else if (i == 4) {
            this.$.id(R.id.exo_play).visible();
            this.$.id(R.id.exo_pause).gone();
            if (this.fullscreen) {
                this.$.id(R.id.exo_play).requestFocus();
            }
            show(this.defaultTimeout);
        }
    }

    public void hideAll() {
        this.$.id(R.id.app_video_loading).gone();
        this.$.id(R.id.app_video_status).gone();
        showBottomControl(false);
        hideTitleBarAndFooter();
    }

    public void hide(boolean z) {
        if (z || this.isShowing) {
            showBottomControl(false);
            this.$.id(R.id.app_video_top_box).gone();
            this.$.id(R.id.controls).gone();
            this.$.id(R.id.app_video_fullscreen).invisible();
            this.$.id(R.id.ll_seekbar_time).gone();
            this.isShowing = false;
            hideTitleBarAndFooter();
        }
    }

    public void hideTitleBarAndFooter() {
        this.$.id(R.id.app_video_top_box).gone();
        this.$.id(R.id.controls).gone();
        this.$.id(R.id.ll_seekbar_time).gone();
        this.isShowing = false;
        this.handler.removeCallbacksAndMessages(null);
    }

    public void setTitle(CharSequence charSequence) {
        this.$.id(R.id.app_video_title).text(charSequence);
    }

    public class PlayerGestureListener extends GestureDetector.SimpleOnGestureListener {
        private boolean firstTouch;
        private boolean toSeek;
        private boolean volumeControl;

        public boolean onDoubleTap(MotionEvent motionEvent) {
            return true;
        }

        public PlayerGestureListener() {
        }

        public boolean onDown(MotionEvent motionEvent) {
            this.firstTouch = true;
            return super.onDown(motionEvent);
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (motionEvent == null || motionEvent2 == null) {
                return Boolean.parseBoolean(null);
            }
            float x = motionEvent.getX();
            float y = motionEvent.getY() - motionEvent2.getY();
            motionEvent2.getX();
            if (this.firstTouch) {
                boolean z = true;
                this.toSeek = Math.abs(f) >= Math.abs(f2);
                if (x <= ((float) NSTIJKPlayerSeries.this.screenWidthPixels) * 0.5f) {
                    z = false;
                }
                this.volumeControl = z;
                this.firstTouch = false;
            }
            if (!this.toSeek) {
                float height = y / ((float) NSTIJKPlayerSeries.this.mVideoView.getHeight());
                if (this.volumeControl) {
                    NSTIJKPlayerSeries.this.onVolumeSlide(height);
                } else {
                    NSTIJKPlayerSeries.this.onBrightnessSlide(height);
                }
            }
            return super.onScroll(motionEvent, motionEvent2, f, f2);
        }

        public void hide(boolean z) {
            if (z || NSTIJKPlayerSeries.this.isShowing) {
                NSTIJKPlayerSeries.this.showBottomControl(false);
                NSTIJKPlayerSeries.this.$.id(R.id.app_video_top_box).gone();
                NSTIJKPlayerSeries.this.$.id(R.id.controls).gone();
                NSTIJKPlayerSeries.this.$.id(R.id.app_video_fullscreen).invisible();
                NSTIJKPlayerSeries.this.$.id(R.id.ll_seekbar_time).gone();
                NSTIJKPlayerSeries.this.hideTitleBarAndFooter();
            }
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if (NSTIJKPlayerSeries.this.isShowing) {
                hide(false);
            } else {
                LinearLayout linearLayout = (LinearLayout) NSTIJKPlayerSeries.this.mActivity.findViewById(R.id.app_video_top_box);
                LinearLayout linearLayout2 = (LinearLayout) NSTIJKPlayerSeries.this.mActivity.findViewById(R.id.controls);
                LinearLayout linearLayout3 = (LinearLayout) NSTIJKPlayerSeries.this.mActivity.findViewById(R.id.ll_seekbar_time);
                if (linearLayout.getVisibility() == 0) {
                    linearLayout.setVisibility(8);
                    linearLayout2.setVisibility(8);
                    linearLayout3.setVisibility(8);
                    return true;
                }
                NSTIJKPlayerSeries.this.show(NSTIJKPlayerSeries.this.defaultTimeout);
            }
            return true;
        }
    }

    public void setMediaController(IMediaController iMediaController) {
        if (this.mMediaController != null) {
            this.mMediaController.hide();
        }
        this.mMediaController = iMediaController;
        attachMediaController();
    }

    private void attachMediaController() {
        if (this.mMediaPlayer != null && this.mMediaController != null) {
            this.mMediaController.setMediaPlayer(this);
            this.mMediaController.setAnchorView(getParent() instanceof View ? (View) getParent() : this);
            this.mMediaController.setEnabled(isInPlaybackState());
        }
    }

    public void setOnPreparedListener(IMediaPlayer.OnPreparedListener onPreparedListener) {
        this.mOnPreparedListener = onPreparedListener;
    }

    public void setOnCompletionListener(IMediaPlayer.OnCompletionListener onCompletionListener) {
        this.mOnCompletionListener = onCompletionListener;
    }

    public void setOnErrorListener(IMediaPlayer.OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnInfoListener(IMediaPlayer.OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    public void bindSurfaceHolder(IMediaPlayer iMediaPlayer, IRenderView.ISurfaceHolder iSurfaceHolder) {
        if (iMediaPlayer != null) {
            if (iSurfaceHolder == null) {
                iMediaPlayer.setDisplay(null);
            } else {
                iSurfaceHolder.bindToMediaPlayer(iMediaPlayer);
            }
        }
    }

    public void releaseWithoutStop() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setDisplay(null);
        }
    }

    public void release(boolean z) {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            if (z) {
                this.mTargetState = 0;
            }
            ((AudioManager) this.mAppContext.getSystemService("audio")).abandonAudioFocus(null);
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean z = (i == 4 || i == 24 || i == 25 || i == 164 || i == 82 || i == 5 || i == 6) ? false : true;
        if (isInPlaybackState() && z && this.mMediaController != null) {
            if (i == 79 || i == 85) {
                if (this.mMediaPlayer == null || !this.mMediaPlayer.isPlaying()) {
                    start();
                    this.mMediaController.hide();
                } else {
                    pause();
                    this.mMediaController.show();
                }
                return true;
            } else if (i == 126) {
                if (this.mMediaPlayer != null && !this.mMediaPlayer.isPlaying()) {
                    start();
                    this.mMediaController.hide();
                }
                return true;
            } else if (i == 86 || i == 127) {
                if (this.mMediaPlayer != null && this.mMediaPlayer.isPlaying()) {
                    pause();
                    this.mMediaController.show();
                }
                return true;
            } else {
                toggleMediaControlsVisiblity();
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    private void toggleMediaControlsVisiblity() {
        if (this.mMediaController.isShowing()) {
            this.mMediaController.hide();
        } else {
            this.mMediaController.show();
        }
    }

    public void start() {
        if (isInPlaybackState()) {
            this.mMediaPlayer.start();
            this.mCurrentState = 3;
        }
        this.mTargetState = 3;
    }

    public void pause() {
        if (isInPlaybackState() && this.mMediaPlayer != null && this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.pause();
            this.mCurrentState = 4;
        }
        this.mTargetState = 4;
    }

    public void suspend() {
        release(false);
    }

    public void resume() {
        openVideo();
    }

    public int getDuration() {
        if (isInPlaybackState()) {
            return (int) this.mMediaPlayer.getDuration();
        }
        return -1;
    }

    public int getCurrentPosition() {
        if (isInPlaybackState()) {
            return (int) this.mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public void seekTo(int i) {
        if (isInPlaybackState()) {
            this.mSeekStartTime = System.currentTimeMillis();
            this.mMediaPlayer.seekTo((long) i);
            this.mSeekWhenPrepared = 0;
            return;
        }
        this.mSeekWhenPrepared = i;
    }

    public boolean isPlaying() {
        return isInPlaybackState() && this.mMediaPlayer != null && this.mMediaPlayer.isPlaying();
    }

    public int getBufferPercentage() {
        if (this.mMediaPlayer != null) {
            return this.mCurrentBufferPercentage;
        }
        return 0;
    }

    private boolean isInPlaybackState() {
        return (this.mMediaPlayer == null || this.mCurrentState == -1 || this.mCurrentState == 0 || this.mCurrentState == 1) ? false : true;
    }

    public boolean canPause() {
        return this.mCanPause;
    }

    public boolean canSeekBackward() {
        return this.mCanSeekBack;
    }

    public boolean canSeekForward() {
        return this.mCanSeekForward;
    }

    public int toggleAspectRatio() {
        this.mCurrentAspectRatioIndex++;
        this.sharedPreferences = this.mActivity.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.sharedPrefEditor = this.sharedPreferences.edit();
        this.mCurrentAspectRatioIndex %= s_allAspectRatio.length;
        this.mCurrentAspectRatio = s_allAspectRatio[this.mCurrentAspectRatioIndex];
        if (this.mRenderView != null) {
            final LinearLayout linearLayout = (LinearLayout) this.mActivity.findViewById(R.id.ll_aspect_ratio);
            TextView textView = (TextView) this.mActivity.findViewById(R.id.app_aspect_ratio_text);
            this.mRenderView.setAspectRatio(this.mCurrentAspectRatio);
            if (this.mCurrentAspectRatioIndex == 0) {
                textView.setText(getResources().getString(R.string.fit_parent));
            } else if (this.mCurrentAspectRatioIndex == 1) {
                textView.setText(getResources().getString(R.string.fill_parent));
            } else if (this.mCurrentAspectRatioIndex == 2) {
                textView.setText(getResources().getString(R.string.wrap_parent));
            } else if (this.mCurrentAspectRatioIndex == 3) {
                textView.setText(getResources().getString(R.string.match_parent));
            } else if (this.mCurrentAspectRatioIndex == 4) {
                textView.setText("16:9");
            } else if (this.mCurrentAspectRatioIndex == 5) {
                textView.setText("4:3");
            }
            this.sharedPrefEditor.putInt(AppConst.ASPECT_RATIO, this.mCurrentAspectRatioIndex);
            this.sharedPrefEditor.apply();
            linearLayout.setVisibility(0);
            this.handlerAspectRatio.removeCallbacksAndMessages(null);
            this.handlerAspectRatio.postDelayed(new Runnable() {
                /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass16 */

                public void run() {
                    linearLayout.setVisibility(8);
                }
            }, 3000);
        }
        return this.mCurrentAspectRatio;
    }

    private void initRenders() {
        this.mAllRenders.clear();
        if (this.mSettings.getEnableSurfaceView()) {
            this.mAllRenders.add(1);
        }
        if (this.mSettings.getEnableTextureView() && Build.VERSION.SDK_INT >= 14) {
            this.mAllRenders.add(2);
        }
        if (this.mSettings.getEnableNoView()) {
            this.mAllRenders.add(0);
        }
        if (this.mAllRenders.isEmpty()) {
            this.mAllRenders.add(1);
        }
        this.mCurrentRender = this.mAllRenders.get(this.mCurrentRenderIndex).intValue();
        setRender(this.mCurrentRender);
    }

    public int toggleRender() {
        this.mCurrentRenderIndex++;
        this.mCurrentRenderIndex %= this.mAllRenders.size();
        this.mCurrentRender = this.mAllRenders.get(this.mCurrentRenderIndex).intValue();
        setRender(this.mCurrentRender);
        return this.mCurrentRender;
    }

    @NonNull
    public static String getRenderText(Context context, int i) {
        switch (i) {
            case 0:
                return context.getString(R.string.VideoView_render_none);
            case 1:
                return context.getString(R.string.VideoView_render_surface_view);
            case 2:
                return context.getString(R.string.VideoView_render_texture_view);
            default:
                return context.getString(R.string.N_A);
        }
    }

    public int togglePlayer() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.release();
        }
        if (this.mRenderView != null) {
            this.mRenderView.getView().invalidate();
        }
        openVideo();
        return this.mSettings.getPlayer();
    }

    @NonNull
    public static String getPlayerText(Context context, int i) {
        switch (i) {
            case 1:
                return context.getString(R.string.VideoView_player_AndroidMediaPlayer);
            case 2:
                return context.getString(R.string.VideoView_player_IjkMediaPlayer);
            case 3:
                return context.getString(R.string.VideoView_player_IjkExoMediaPlayer);
            default:
                return context.getString(R.string.N_A);
        }
    }

    /* JADX INFO: additional move instructions added (1) to help type inference */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v1, resolved type: tv.danmaku.ijk.media.player.AndroidMediaPlayer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v6, resolved type: tv.danmaku.ijk.media.player.AndroidMediaPlayer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v7, resolved type: tv.danmaku.ijk.media.player.IjkMediaPlayer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: tv.danmaku.ijk.media.player.AndroidMediaPlayer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v9, resolved type: tv.danmaku.ijk.media.player.AndroidMediaPlayer} */
    /* JADX WARNING: Multi-variable type inference failed */
    public IMediaPlayer createPlayer(int i) {
        AndroidMediaPlayer androidMediaPlayer;
        if (i != 1) {
            this.$.id(R.id.app_video_loading).visible();
            androidMediaPlayer = null;
            if (this.mUri != null) {
                IjkMediaPlayer ijkMediaPlayer = new IjkMediaPlayer();
                IjkMediaPlayer.native_setLogLevel(3);
                if (this.mSettings.getUsingMediaCodec()) {
                    this.$.id(R.id.exo_decoder_sw).gone();
                    this.$.id(R.id.exo_decoder_hw).visible();
                    ijkMediaPlayer.setOption(4, "mediacodec", 1);
                    if (this.mSettings.getUsingMediaCodecAutoRotate()) {
                        ijkMediaPlayer.setOption(4, "mediacodec-auto-rotate", 1);
                    } else {
                        ijkMediaPlayer.setOption(4, "mediacodec-auto-rotate", 0);
                    }
                    if (this.mSettings.getMediaCodecHandleResolutionChange()) {
                        ijkMediaPlayer.setOption(4, "mediacodec-handle-resolution-change", 1);
                    } else {
                        ijkMediaPlayer.setOption(4, "mediacodec-handle-resolution-change", 0);
                    }
                } else {
                    this.$.id(R.id.exo_decoder_sw).visible();
                    this.$.id(R.id.exo_decoder_hw).gone();
                    ijkMediaPlayer.setOption(4, "mediacodec", 0);
                }
                ijkMediaPlayer.setOption(4, "subtitle", 1);
                if (this.mSettings.getUsingOpenSLES()) {
                    ijkMediaPlayer.setOption(4, "opensles", 1);
                } else {
                    ijkMediaPlayer.setOption(4, "opensles", 0);
                }
                if (TextUtils.isEmpty(this.mSettings.getPixelFormat())) {
                    ijkMediaPlayer.setOption(4, "overlay-format", 842225234);
                } else {
                    ijkMediaPlayer.setOption(4, "overlay-format", "fcc-_es2");
                }
                ijkMediaPlayer.setOption(4, "framedrop", 1);
                ijkMediaPlayer.setOption(4, "start-on-prepared", 1);
                this.loginPreferencesUserAgent = this.mActivity.getSharedPreferences(AppConst.LOGIN_PREF_USER_AGENT, 0);
                String string = this.loginPreferencesUserAgent.getString(AppConst.LOGIN_PREF_USER_AGENT, AppConst.USER_AGENT);
                if (!string.equals("")) {
                    ijkMediaPlayer.setOption(1, AppConst.LOGIN_PREF_USER_AGENT, string);
                } else {
                    ijkMediaPlayer.setOption(1, AppConst.LOGIN_PREF_USER_AGENT, AppConst.USER_AGENT);
                }
                ijkMediaPlayer.setOption(2, "skip_loop_filter", 48);
                //ToDo: Player...
                //androidMediaPlayer = ijkMediaPlayer;
            }
        } else {
            androidMediaPlayer = new AndroidMediaPlayer();
        }
        return this.mSettings.getEnableDetachedSurfaceTextureView() ? new TextureMediaPlayer(androidMediaPlayer) : androidMediaPlayer;
    }

    private void initBackground() {
        this.mEnableBackgroundPlay = this.mSettings.getEnableBackgroundPlay();
        if (this.mEnableBackgroundPlay) {
            MediaPlayerService.intentToStart(getContext());
            this.mMediaPlayer = MediaPlayerService.getMediaPlayer();
        }
    }

    public boolean isBackgroundPlayEnabled() {
        return this.mEnableBackgroundPlay;
    }

    public void enterBackground() {
        MediaPlayerService.setMediaPlayer(this.mMediaPlayer);
    }

    public void stopBackgroundPlay() {
        MediaPlayerService.setMediaPlayer(null);
    }

    @SuppressLint({"SetTextI18n", "ResourceType"})
    public void showSubTitle(RadioGroup radioGroup, RadioGroup radioGroup2, RadioGroup radioGroup3, PopupWindow popupWindow, TextView textView, TextView textView2, TextView textView3) {
        int i;
        int i2;
        ITrackInfo[] iTrackInfoArr;
        RadioGroup radioGroup4 = radioGroup;
        RadioGroup radioGroup5 = radioGroup2;
        RadioGroup radioGroup6 = radioGroup3;
        final PopupWindow popupWindow2 = popupWindow;
        TextView textView4 = textView;
        TextView textView5 = textView2;
        TextView textView6 = textView3;
        if (this.mMediaPlayer != null) {
            int i3 = 0;
            this.subtitleTrackFound = false;
            this.audioTrackFound = false;
            this.videoTrackFound = false;
            this.disableAudioTrack = false;
            this.disableVideoTrack = false;
            this.disableSubTitleTrack = false;
            final int selectedTrack = MediaPlayerCompat.getSelectedTrack(this.mMediaPlayer, 1);
            final int selectedTrack2 = MediaPlayerCompat.getSelectedTrack(this.mMediaPlayer, 2);
            final int selectedTrack3 = MediaPlayerCompat.getSelectedTrack(this.mMediaPlayer, 3);
            ITrackInfo[] trackInfo = this.mMediaPlayer.getTrackInfo();
            if (trackInfo == null || trackInfo.length <= 0) {
                textView4.setVisibility(0);
                textView5.setVisibility(0);
                textView6.setVisibility(0);
                return;
            }
            int length = trackInfo.length;
            int i4 = -1;
            while (i3 < length) {
                ITrackInfo iTrackInfo = trackInfo[i3];
                int i5 = i4 + 1;
                int trackType = iTrackInfo.getTrackType();
                IMediaFormat format = iTrackInfo.getFormat();
                if (format != null && (format instanceof IjkMediaFormat)) {
                    switch (trackType) {
                        case 1:
                            iTrackInfoArr = trackInfo;
                            i2 = length;
                            this.videoTrackFound = true;
                            if (!this.disableVideoTrack.booleanValue()) {
                                this.disableVideoTrack = true;
                                RadioButton radioButton = new RadioButton(this.mActivity);
                                radioButton.setText(getResources().getString(R.string.disable));
                                radioButton.setTextColor(getResources().getColor(R.color.black));
                                radioButton.setTextSize(18.0f);
                                radioButton.setId(11111111);
                                radioButton.setPadding(10, 10, 15, 10);
                                radioButton.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton));
                                if (selectedTrack == -1) {
                                    radioButton.setChecked(true);
                                    radioGroup4.setOnCheckedChangeListener(null);
                                }
                                radioButton.setTag("2");
                                radioGroup4.addView(radioButton);
                            }
                            RadioButton radioButton2 = new RadioButton(this.mActivity);
                            iTrackInfo.getInfoInline();
                            radioButton2.setText(i5 + ", " + iTrackInfo.getInfoInline());
                            if (i5 == -1) {
                                radioButton2.setId(111);
                            } else {
                                radioButton2.setId(i5);
                            }
                            radioButton2.setTextColor(getResources().getColor(R.color.black));
                            radioButton2.setTextSize(18.0f);
                            radioButton2.setPadding(10, 10, 15, 10);
                            radioButton2.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton2));
                            if (i5 == selectedTrack) {
                                radioButton2.setChecked(true);
                                radioGroup4.setOnCheckedChangeListener(null);
                            }
                            radioButton2.setTag("2");
                            radioGroup4.addView(radioButton2);
                            radioGroup4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass17 */

                                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                    SharedPreferences.Editor unused = NSTIJKPlayerSeries.this.loginPrefsEditorVideo = NSTIJKPlayerSeries.this.loginPreferences_video_selected.edit();
                                    if (i == 111 || i == 11111111) {
                                        NSTIJKPlayerSeries.this.loginPrefsEditorVideo.putInt(AppConst.LOGIN_PREF_CURRENT_VIDEO_TRACK, -1);
                                        NSTIJKPlayerSeries.this.deselectTrack(selectedTrack);
                                    } else {
                                        NSTIJKPlayerSeries.this.loginPrefsEditorVideo.putInt(AppConst.LOGIN_PREF_CURRENT_VIDEO_TRACK, i);
                                        NSTIJKPlayerSeries.this.selectTrack(i);
                                    }
                                    NSTIJKPlayerSeries.this.loginPrefsEditorVideo.apply();
                                    new Handler().postDelayed(new Runnable() {
                                        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass17.AnonymousClass1 */

                                        public void run() {
                                            popupWindow2.dismiss();
                                        }
                                    }, 500);
                                }
                            });
                            //continue;
                            i3++;
                            i4 = i5;
                            trackInfo = iTrackInfoArr;
                            length = i2;
                        case 2:
                            iTrackInfoArr = trackInfo;
                            i2 = length;
                            this.audioTrackFound = true;
                            if (!this.disableAudioTrack.booleanValue()) {
                                this.disableAudioTrack = true;
                                RadioButton radioButton3 = new RadioButton(this.mActivity);
                                radioButton3.setText(getResources().getString(R.string.disable));
                                radioButton3.setTextColor(getResources().getColor(R.color.black));
                                radioButton3.setTextSize(18.0f);
                                radioButton3.setId(1111111);
                                radioButton3.setPadding(10, 10, 15, 10);
                                radioButton3.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton3));
                                if (selectedTrack2 == -1) {
                                    radioButton3.setChecked(true);
                                    radioGroup5.setOnCheckedChangeListener(null);
                                }
                                radioButton3.setTag("2");
                                radioGroup5.addView(radioButton3);
                            }
                            RadioButton radioButton4 = new RadioButton(this.mActivity);
                            radioButton4.setText(i5 + ", " + iTrackInfo.getInfoInline() + ", " + buildLanguage(iTrackInfo.getLanguage()));
                            radioButton4.setTextColor(getResources().getColor(R.color.black));
                            radioButton4.setTextSize(18.0f);
                            if (i5 == -1) {
                                radioButton4.setId(1111);
                            } else {
                                radioButton4.setId(i5);
                            }
                            radioButton4.setPadding(10, 10, 15, 10);
                            radioButton4.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton4));
                            if (i5 == selectedTrack2) {
                                radioButton4.setChecked(true);
                                radioGroup5.setOnCheckedChangeListener(null);
                            }
                            radioButton4.setTag("2");
                            radioGroup5.addView(radioButton4);
                            radioGroup5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass18 */

                                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                    SharedPreferences.Editor unused = NSTIJKPlayerSeries.this.loginPrefsEditorAudio = NSTIJKPlayerSeries.this.loginPreferences_audio_selected.edit();
                                    if (i == 1111 || i == 1111111) {
                                        NSTIJKPlayerSeries.this.loginPrefsEditorAudio.putInt(AppConst.LOGIN_PREF_CURRENT_AUDIO_TRACK, -1);
                                        NSTIJKPlayerSeries.this.deselectTrack(selectedTrack2);
                                    } else {
                                        NSTIJKPlayerSeries.this.loginPrefsEditorAudio.putInt(AppConst.LOGIN_PREF_CURRENT_AUDIO_TRACK, i);
                                        int currentPosition = (int) NSTIJKPlayerSeries.this.mMediaPlayer.getCurrentPosition();
                                        NSTIJKPlayerSeries.this.selectTrack(i);
                                        NSTIJKPlayerSeries.this.mMediaPlayer.seekTo(Long.parseLong(String.valueOf(currentPosition)));
                                    }
                                    NSTIJKPlayerSeries.this.loginPrefsEditorAudio.apply();
                                    new Handler().postDelayed(new Runnable() {
                                        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass18.AnonymousClass1 */

                                        public void run() {
                                            popupWindow2.dismiss();
                                        }
                                    }, 500);
                                }
                            });
                            //continue;
                            i3++;
                            i4 = i5;
                            trackInfo = iTrackInfoArr;
                            length = i2;
                        case 3:
                            this.subtitleTrackFound = true;
                            if (!this.disableSubTitleTrack.booleanValue()) {
                                this.disableSubTitleTrack = true;
                                iTrackInfoArr = trackInfo;
                                RadioButton radioButton5 = new RadioButton(this.mActivity);
                                i2 = length;
                                radioButton5.setText(getResources().getString(R.string.disable));
                                radioButton5.setTextColor(getResources().getColor(R.color.black));
                                radioButton5.setTextSize(18.0f);
                                radioButton5.setId(111111);
                                radioButton5.setPadding(10, 10, 15, 10);
                                radioButton5.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton5));
                                if (selectedTrack3 == -1) {
                                    radioButton5.setChecked(true);
                                    radioGroup6.setOnCheckedChangeListener(null);
                                }
                                radioButton5.setTag("2");
                                radioGroup6.addView(radioButton5);
                            } else {
                                iTrackInfoArr = trackInfo;
                                i2 = length;
                            }
                            RadioButton radioButton6 = new RadioButton(this.mActivity);
                            radioButton6.setText(i5 + ", " + iTrackInfo.getInfoInline());
                            radioButton6.setTextColor(getResources().getColor(R.color.black));
                            radioButton6.setTextSize(18.0f);
                            if (i5 == -1) {
                                radioButton6.setId(11111);
                            } else {
                                radioButton6.setId(i5);
                            }
                            radioButton6.setPadding(10, 10, 15, 10);
                            radioButton6.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton6));
                            if (i5 == selectedTrack3) {
                                radioButton6.setChecked(true);
                                radioGroup6.setOnCheckedChangeListener(null);
                            }
                            radioButton6.setTag("2");
                            radioGroup6.addView(radioButton6);
                            radioGroup6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass19 */

                                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                    SharedPreferences.Editor unused = NSTIJKPlayerSeries.this.loginPrefsEditorSubtitle = NSTIJKPlayerSeries.this.loginPreferences_subtitle_selected.edit();
                                    if (i == 11111 || i == 111111) {
                                        NSTIJKPlayerSeries.this.loginPrefsEditorSubtitle.putInt(AppConst.LOGIN_PREF_CURRENT_SUBTITLE_TRACK, -1);
                                        NSTIJKPlayerSeries.this.deselectTrack(selectedTrack3);
                                    } else {
                                        NSTIJKPlayerSeries.this.loginPrefsEditorSubtitle.putInt(AppConst.LOGIN_PREF_CURRENT_SUBTITLE_TRACK, i);
                                        NSTIJKPlayerSeries.this.selectTrack(i);
                                    }
                                    NSTIJKPlayerSeries.this.loginPrefsEditorSubtitle.apply();
                                    new Handler().postDelayed(new Runnable() {
                                        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass19.AnonymousClass1 */

                                        public void run() {
                                            popupWindow2.dismiss();
                                        }
                                    }, 500);
                                }
                            });
                            //continue;
                            i3++;
                            i4 = i5;
                            trackInfo = iTrackInfoArr;
                            length = i2;
                    }
                }
                iTrackInfoArr = trackInfo;
                i2 = length;
                i3++;
                i4 = i5;
                trackInfo = iTrackInfoArr;
                length = i2;
            }
            if (!this.videoTrackFound.booleanValue()) {
                i = 0;
                textView4.setVisibility(0);
            } else {
                i = 0;
            }
            if (!this.audioTrackFound.booleanValue()) {
                textView5.setVisibility(i);
            }
            if (!this.subtitleTrackFound.booleanValue()) {
                textView6.setVisibility(i);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0164  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0198  */
    public void showMediaInfo() {
        if (this.mMediaPlayer != null) {
            int selectedTrack = MediaPlayerCompat.getSelectedTrack(this.mMediaPlayer, 1);
            int selectedTrack2 = MediaPlayerCompat.getSelectedTrack(this.mMediaPlayer, 2);
            int selectedTrack3 = MediaPlayerCompat.getSelectedTrack(this.mMediaPlayer, 3);
            TableLayoutBinder tableLayoutBinder = new TableLayoutBinder(getContext());
            tableLayoutBinder.appendSection((int) R.string.mi_media);
            tableLayoutBinder.appendRow2((int) R.string.name, this.mVideoTitle);
            tableLayoutBinder.appendRow2((int) R.string.mi_resolution, buildResolution(this.mVideoWidth, this.mVideoHeight, this.mVideoSarNum, this.mVideoSarDen));
            tableLayoutBinder.appendRow2((int) R.string.mi_length, buildTimeMilli(this.mMediaPlayer.getDuration()));
            ITrackInfo[] trackInfo = this.mMediaPlayer.getTrackInfo();
            if (trackInfo != null) {
                int length = trackInfo.length;
                char c = 0;
                int i = 0;
                int i2 = -1;
                while (i < length) {
                    ITrackInfo iTrackInfo = trackInfo[i];
                    i2++;
                    int trackType = iTrackInfo.getTrackType();
                    if (i2 == selectedTrack) {
                        StringBuilder sb = new StringBuilder();
                        Context context = getContext();
                        Object[] objArr = new Object[1];
                        objArr[c] = Integer.valueOf(i2);
                        sb.append(context.getString(R.string.mi_stream_fmt1, objArr));
                        sb.append(" ");
                        sb.append(getContext().getString(R.string.mi__selected_video_track));
                        tableLayoutBinder.appendSection(sb.toString());
                    } else if (i2 == selectedTrack2) {
                        tableLayoutBinder.appendSection(getContext().getString(R.string.mi_stream_fmt1, Integer.valueOf(i2)) + " " + getContext().getString(R.string.mi__selected_audio_track));
                    } else if (i2 == selectedTrack3) {
                        tableLayoutBinder.appendSection(getContext().getString(R.string.mi_stream_fmt1, Integer.valueOf(i2)) + " " + getContext().getString(R.string.mi__selected_subtitle_track));
                    } else {
                        tableLayoutBinder.appendSection(getContext().getString(R.string.mi_stream_fmt1, Integer.valueOf(i2)));
                        tableLayoutBinder.appendRow2((int) R.string.mi_type, buildTrackType(trackType));
                        tableLayoutBinder.appendRow2((int) R.string.mi_language, buildLanguage(iTrackInfo.getLanguage()));
                        IMediaFormat format = iTrackInfo.getFormat();
                        if (format != null && (format instanceof IjkMediaFormat)) {
                            switch (trackType) {
                                case 1:
                                    tableLayoutBinder.appendRow2((int) R.string.mi_codec, format.getString(IjkMediaFormat.KEY_IJK_CODEC_LONG_NAME_UI));
                                    tableLayoutBinder.appendRow2((int) R.string.mi_profile_level, format.getString(IjkMediaFormat.KEY_IJK_CODEC_PROFILE_LEVEL_UI));
                                    tableLayoutBinder.appendRow2((int) R.string.mi_pixel_format, format.getString(IjkMediaFormat.KEY_IJK_CODEC_PIXEL_FORMAT_UI));
                                    tableLayoutBinder.appendRow2((int) R.string.mi_resolution, format.getString(IjkMediaFormat.KEY_IJK_RESOLUTION_UI));
                                    tableLayoutBinder.appendRow2((int) R.string.mi_frame_rate, format.getString(IjkMediaFormat.KEY_IJK_FRAME_RATE_UI));
                                    tableLayoutBinder.appendRow2((int) R.string.mi_bit_rate, format.getString(IjkMediaFormat.KEY_IJK_BIT_RATE_UI));
                                    //continue;
                                    //continue;
                                    i++;
                                    c = 0;
                                case 2:
                                    tableLayoutBinder.appendRow2((int) R.string.mi_codec, format.getString(IjkMediaFormat.KEY_IJK_CODEC_LONG_NAME_UI));
                                    tableLayoutBinder.appendRow2((int) R.string.mi_profile_level, format.getString(IjkMediaFormat.KEY_IJK_CODEC_PROFILE_LEVEL_UI));
                                    tableLayoutBinder.appendRow2((int) R.string.mi_sample_rate, format.getString(IjkMediaFormat.KEY_IJK_SAMPLE_RATE_UI));
                                    tableLayoutBinder.appendRow2((int) R.string.mi_channels, format.getString(IjkMediaFormat.KEY_IJK_CHANNEL_UI));
                                    tableLayoutBinder.appendRow2((int) R.string.mi_bit_rate, format.getString(IjkMediaFormat.KEY_IJK_BIT_RATE_UI));
                                    break;
                            }
                        }
                        i++;
                        c = 0;
                    }
                    tableLayoutBinder.appendRow2((int) R.string.mi_type, buildTrackType(trackType));
                    tableLayoutBinder.appendRow2((int) R.string.mi_language, buildLanguage(iTrackInfo.getLanguage()));
                    IMediaFormat format2 = iTrackInfo.getFormat();
                    switch (trackType) {
                    }
                    i++;
                    c = 0;
                }
            }
            AlertDialog.Builder buildAlertDialogBuilder = tableLayoutBinder.buildAlertDialogBuilder();
            buildAlertDialogBuilder.setTitle((int) R.string.media_information);
            buildAlertDialogBuilder.setNegativeButton((int) R.string.close, (DialogInterface.OnClickListener) null);
            this.dirsDialog = buildAlertDialogBuilder.create();
            this.dirsDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass20 */

                public void onDismiss(DialogInterface dialogInterface) {
                    if (NSTIJKPlayerSeries.this.mVideoView != null) {
                        NSTIJKPlayerSeries.this.mVideoView.hideSystemUi();
                    }
                }
            });
            this.dirsDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSeries.AnonymousClass21 */

                public void onShow(DialogInterface dialogInterface) {
                    Button unused = NSTIJKPlayerSeries.this.negativeButton = NSTIJKPlayerSeries.this.dirsDialog.getButton(-2);
                    NSTIJKPlayerSeries.this.negativeButton.setTag("1");
                    NSTIJKPlayerSeries.this.negativeButton.setOnFocusChangeListener(new OnFocusChangeAccountListener(NSTIJKPlayerSeries.this.negativeButton));
                    NSTIJKPlayerSeries.this.negativeButton.setTextColor(NSTIJKPlayerSeries.this.mActivity.getResources().getColor(R.color.white));
                    NSTIJKPlayerSeries.this.negativeButton.setTextSize(16.0f);
                    MarginLayoutParams marginLayoutParams = (MarginLayoutParams) NSTIJKPlayerSeries.this.negativeButton.getLayoutParams();
                    marginLayoutParams.setMargins(0, 10, 50, 10);
                    if ((NSTIJKPlayerSeries.this.mActivity.getResources().getConfiguration().screenLayout & 15) == 3) {
                        NSTIJKPlayerSeries.this.negativeButton.setBackground(NSTIJKPlayerSeries.this.mActivity.getResources().getDrawable(R.drawable.black_button_dark));
                        marginLayoutParams.width = PsExtractor.VIDEO_STREAM_MASK;
                        marginLayoutParams.height = TsExtractor.TS_STREAM_TYPE_E_AC3;
                        return;
                    }
                    NSTIJKPlayerSeries.this.negativeButton.setBackground(NSTIJKPlayerSeries.this.mActivity.getResources().getDrawable(R.drawable.back_btn_effect));
                    marginLayoutParams.width = PsExtractor.VIDEO_STREAM_MASK;
                    marginLayoutParams.height = TsExtractor.TS_STREAM_TYPE_E_AC3;
                }
            });
            this.dirsDialog.show();
        }
    }

    private class OnFocusChangeAccountListener implements OnFocusChangeListener {
        private final View view;

        public OnFocusChangeAccountListener(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            float f = 1.0f;
            if (z) {
                if (this.view == null || this.view.getTag() == null || !this.view.getTag().equals("2")) {
                    if (z) {
                        f = 1.12f;
                    }
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("1") && NSTIJKPlayerSeries.this.negativeButton != null) {
                        NSTIJKPlayerSeries.this.negativeButton.setBackgroundResource(R.drawable.back_btn_effect);
                        return;
                    }
                    return;
                }
                view2.setBackground(NSTIJKPlayerSeries.this.getResources().getDrawable(R.drawable.selector_checkbox));
            } else if (!z) {
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("1") || NSTIJKPlayerSeries.this.negativeButton == null)) {
                    NSTIJKPlayerSeries.this.negativeButton.setBackgroundResource(R.drawable.black_button_dark);
                }
                performScaleXAnimation(1.0f);
                performScaleYAnimation(1.0f);
            }
        }

        private void performScaleXAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleX", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        private void performScaleYAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleY", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        private void performAlphaAnimation(boolean z) {
            if (z) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "alpha", z ? 0.6f : 0.5f);
                ofFloat.setDuration(150L);
                ofFloat.start();
            }
        }
    }

    private String buildResolution(int i, int i2, int i3, int i4) {
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(" x ");
        sb.append(i2);
        if (i3 > 1 || i4 > 1) {
            sb.append("[");
            sb.append(i3);
            sb.append(":");
            sb.append(i4);
            sb.append("]");
        }
        return sb.toString();
    }

    private String buildTimeMilli(long j) {
        long j2 = j / 1000;
        long j3 = j2 / 3600;
        long j4 = (j2 % 3600) / 60;
        long j5 = j2 % 60;
        if (j <= 0) {
            return "--:--";
        }
        if (j3 >= 100) {
            return String.format(Locale.US, "%d:%02d:%02d", Long.valueOf(j3), Long.valueOf(j4), Long.valueOf(j5));
        } else if (j3 > 0) {
            return String.format(Locale.US, "%02d:%02d:%02d", Long.valueOf(j3), Long.valueOf(j4), Long.valueOf(j5));
        } else {
            return String.format(Locale.US, "%02d:%02d", Long.valueOf(j4), Long.valueOf(j5));
        }
    }

    private String buildTrackType(int i) {
        Context context = getContext();
        switch (i) {
            case 1:
                return context.getString(R.string.TrackType_video);
            case 2:
                return context.getString(R.string.TrackType_audio);
            case 3:
                return context.getString(R.string.TrackType_timedtext);
            case 4:
                return context.getString(R.string.TrackType_subtitle);
            case 5:
                return context.getString(R.string.TrackType_metadata);
            default:
                return context.getString(R.string.TrackType_unknown);
        }
    }

    private String buildLanguage(String str) {
        return TextUtils.isEmpty(str) ? C.LANGUAGE_UNDETERMINED : str;
    }

    public ITrackInfo[] getTrackInfo() {
        if (this.mMediaPlayer == null) {
            return null;
        }
        return this.mMediaPlayer.getTrackInfo();
    }

    public void selectTrack(int i) {
        MediaPlayerCompat.selectTrack(this.mMediaPlayer, i);
    }

    public void deselectTrack(int i) {
        MediaPlayerCompat.deselectTrack(this.mMediaPlayer, i);
    }

    public int getSelectedTrack(int i) {
        return MediaPlayerCompat.getSelectedTrack(this.mMediaPlayer, i);
    }
}
