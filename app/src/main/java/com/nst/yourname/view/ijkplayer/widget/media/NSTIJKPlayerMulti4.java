package com.nst.yourname.view.ijkplayer.widget.media;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.view.ijkplayer.application.Settings;
import com.nst.yourname.view.ijkplayer.services.MediaPlayerService;
import com.nst.yourname.view.ijkplayer.widget.media.IRenderView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import tv.danmaku.ijk.media.player.AndroidMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.TextureMediaPlayer;

@SuppressWarnings("ALL")
public class NSTIJKPlayerMulti4 extends FrameLayout implements MediaController.MediaPlayerControl {
    static final boolean $assertionsDisabled = false;
    public static final int RENDER_NONE = 0;
    public static final int RENDER_SURFACE_VIEW = 1;
    public static final int RENDER_TEXTURE_VIEW = 2;
    private static final int STATE_BUFFERING_END = 6;
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PREPARING = 1;
    private Query $;
    Handler EPGHandler;
    public String TAG = "NSTIJKPlayerMulti4";
    private AudioManager audioManager;
    Context context;
    public String currentChannelLogo;
    public String currentEpgChannelID;
    private boolean fullscreen;
    private Handler handler;
    LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesUserAgent;
    public Activity mActivity;
    private List<Integer> mAllRenders = new ArrayList();
    private Context mAppContext;
    private IMediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerMulti4.AnonymousClass7 */

        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnBufferingUpdateListener
        public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
            int unused = NSTIJKPlayerMulti4.this.mCurrentBufferPercentage = i;
        }
    };
    private boolean mCanPause = true;
    private boolean mCanSeekBack = true;
    private boolean mCanSeekForward = true;
    private IMediaPlayer.OnCompletionListener mCompletionListener = new IMediaPlayer.OnCompletionListener() {
        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerMulti4.AnonymousClass4 */

        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnCompletionListener
        public void onCompletion(IMediaPlayer iMediaPlayer) {
            int unused = NSTIJKPlayerMulti4.this.mCurrentState = 5;
            int unused2 = NSTIJKPlayerMulti4.this.mTargetState = 5;
            if (NSTIJKPlayerMulti4.this.mMediaController != null) {
                NSTIJKPlayerMulti4.this.mMediaController.hide();
            }
            NSTIJKPlayerMulti4.this.statusChange(-1);
            if (NSTIJKPlayerMulti4.this.mOnCompletionListener != null) {
                NSTIJKPlayerMulti4.this.mOnCompletionListener.onCompletion(NSTIJKPlayerMulti4.this.mMediaPlayer);
            }
        }
    };
    public int mCurrentBufferPercentage;
    private int mCurrentRender = 0;
    private int mCurrentRenderIndex = 0;
    public int mCurrentState = 0;
    private boolean mEnableBackgroundPlay = false;
    private IMediaPlayer.OnErrorListener mErrorListener = new IMediaPlayer.OnErrorListener() {
        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerMulti4.AnonymousClass6 */

        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnErrorListener
        public boolean onError(IMediaPlayer iMediaPlayer, int i, int i2) {
            String access$1800 = NSTIJKPlayerMulti4.this.TAG;
            Log.d(access$1800, "Error: " + i + "," + i2);
            int unused = NSTIJKPlayerMulti4.this.mCurrentState = -1;
            int unused2 = NSTIJKPlayerMulti4.this.mTargetState = -1;
            if (NSTIJKPlayerMulti4.this.mMediaController != null) {
                NSTIJKPlayerMulti4.this.mMediaController.hide();
            }
            NSTIJKPlayerMulti4.this.statusChange(-1);
            return (NSTIJKPlayerMulti4.this.mOnErrorListener == null || NSTIJKPlayerMulti4.this.mOnErrorListener.onError(NSTIJKPlayerMulti4.this.mMediaPlayer, i, i2)) ? true : true;
        }
    };
    private Map<String, String> mHeaders;
    private IMediaPlayer.OnInfoListener mInfoListener = new IMediaPlayer.OnInfoListener() {
        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerMulti4.AnonymousClass5 */

        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnInfoListener
        public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i2) {
            if (NSTIJKPlayerMulti4.this.mOnInfoListener != null) {
                NSTIJKPlayerMulti4.this.mOnInfoListener.onInfo(iMediaPlayer, i, i2);
            }
            switch (i) {
                case 3:
                    NSTIJKPlayerMulti4.this.statusChange(2);
                    Log.d(NSTIJKPlayerMulti4.this.TAG, "MEDIA_INFO_VIDEO_RENDERING_START:");
                    break;
                case 700:
                    Log.d(NSTIJKPlayerMulti4.this.TAG, "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                    break;
                case IMediaPlayer.MEDIA_INFO_BUFFERING_START /*701*/:
                    NSTIJKPlayerMulti4.this.statusChange(1);
                    Log.d(NSTIJKPlayerMulti4.this.TAG, "MEDIA_INFO_BUFFERING_START:");
                    break;
                case IMediaPlayer.MEDIA_INFO_BUFFERING_END /*702*/:
                    NSTIJKPlayerMulti4.this.statusChange(6);
                    Log.d(NSTIJKPlayerMulti4.this.TAG, "MEDIA_INFO_BUFFERING_END:");
                    break;
                case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH /*703*/:
                    String access$1800 = NSTIJKPlayerMulti4.this.TAG;
                    Log.d(access$1800, "MEDIA_INFO_NETWORK_BANDWIDTH: " + i2);
                    break;
                case 800:
                    Log.d(NSTIJKPlayerMulti4.this.TAG, "MEDIA_INFO_BAD_INTERLEAVING:");
                    break;
                case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE /*801*/:
                    Log.d(NSTIJKPlayerMulti4.this.TAG, "MEDIA_INFO_NOT_SEEKABLE:");
                    break;
                case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE /*802*/:
                    Log.d(NSTIJKPlayerMulti4.this.TAG, "MEDIA_INFO_METADATA_UPDATE:");
                    break;
                case IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE /*901*/:
                    Log.d(NSTIJKPlayerMulti4.this.TAG, "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                    break;
                case IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT /*902*/:
                    Log.d(NSTIJKPlayerMulti4.this.TAG, "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                    break;
                case 10001:
                    int unused = NSTIJKPlayerMulti4.this.mVideoRotationDegree = i2;
                    String access$18002 = NSTIJKPlayerMulti4.this.TAG;
                    Log.d(access$18002, "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + i2);
                    if (NSTIJKPlayerMulti4.this.mRenderView != null) {
                        NSTIJKPlayerMulti4.this.mRenderView.setVideoRotation(i2);
                        break;
                    }
                    break;
                case 10002:
                    NSTIJKPlayerMulti4.this.statusChange(2);
                    Log.d(NSTIJKPlayerMulti4.this.TAG, "MEDIA_INFO_AUDIO_RENDERING_START:");
                    break;
                case IMediaPlayer.MEDIA_INFO_OPEN_INPUT /*10005*/:
                    NSTIJKPlayerMulti4.this.statusChange(1);
                    Log.d(NSTIJKPlayerMulti4.this.TAG, "MEDIA_INFO_OPEN_INPUT:");
                    break;
            }
            return true;
        }
    };
    public IMediaController mMediaController;
    public IMediaPlayer mMediaPlayer = null;
    public IMediaPlayer.OnCompletionListener mOnCompletionListener;
    public IMediaPlayer.OnErrorListener mOnErrorListener;
    public IMediaPlayer.OnInfoListener mOnInfoListener;
    public IMediaPlayer.OnPreparedListener mOnPreparedListener;
    IMediaPlayer.OnPreparedListener mPreparedListener = new IMediaPlayer.OnPreparedListener() {
        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerMulti4.AnonymousClass3 */

        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnPreparedListener
        public void onPrepared(IMediaPlayer iMediaPlayer) {
            int unused = NSTIJKPlayerMulti4.this.mCurrentState = 2;
            if (NSTIJKPlayerMulti4.this.mOnPreparedListener != null) {
                NSTIJKPlayerMulti4.this.mOnPreparedListener.onPrepared(NSTIJKPlayerMulti4.this.mMediaPlayer);
            }
            if (NSTIJKPlayerMulti4.this.mMediaController != null) {
                NSTIJKPlayerMulti4.this.mMediaController.setEnabled(true);
            }
            int unused2 = NSTIJKPlayerMulti4.this.mVideoWidth = iMediaPlayer.getVideoWidth();
            int unused3 = NSTIJKPlayerMulti4.this.mVideoHeight = iMediaPlayer.getVideoHeight();
            if (NSTIJKPlayerMulti4.this.mVideoWidth == 0 || NSTIJKPlayerMulti4.this.mVideoHeight == 0) {
                if (NSTIJKPlayerMulti4.this.mTargetState == 3) {
                    NSTIJKPlayerMulti4.this.start();
                }
            } else if (NSTIJKPlayerMulti4.this.mRenderView != null) {
                NSTIJKPlayerMulti4.this.mRenderView.setVideoSize(NSTIJKPlayerMulti4.this.mVideoWidth, NSTIJKPlayerMulti4.this.mVideoHeight);
                NSTIJKPlayerMulti4.this.mRenderView.setVideoSampleAspectRatio(NSTIJKPlayerMulti4.this.mVideoSarNum, NSTIJKPlayerMulti4.this.mVideoSarDen);
                if ((!NSTIJKPlayerMulti4.this.mRenderView.shouldWaitForResize() || (NSTIJKPlayerMulti4.this.mSurfaceWidth == NSTIJKPlayerMulti4.this.mVideoWidth && NSTIJKPlayerMulti4.this.mSurfaceHeight == NSTIJKPlayerMulti4.this.mVideoHeight)) && NSTIJKPlayerMulti4.this.mTargetState == 3) {
                    NSTIJKPlayerMulti4.this.start();
                    if (NSTIJKPlayerMulti4.this.mMediaController != null) {
                        NSTIJKPlayerMulti4.this.mMediaController.show();
                    }
                }
            }
        }
    };
    public IRenderView mRenderView;
    IRenderView.IRenderCallback mSHCallback = new IRenderView.IRenderCallback() {
        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerMulti4.AnonymousClass8 */

        @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView.IRenderCallback
        public void onSurfaceChanged(@NonNull IRenderView.ISurfaceHolder iSurfaceHolder, int i, int i2, int i3) {
            if (iSurfaceHolder.getRenderView() != NSTIJKPlayerMulti4.this.mRenderView) {
                Log.e(NSTIJKPlayerMulti4.this.TAG, "onSurfaceChanged: unmatched render callback\n");
                return;
            }
            int unused = NSTIJKPlayerMulti4.this.mSurfaceWidth = i2;
            int unused2 = NSTIJKPlayerMulti4.this.mSurfaceHeight = i3;
            boolean z = false;
            boolean z2 = NSTIJKPlayerMulti4.this.mTargetState == 3;
            if (!NSTIJKPlayerMulti4.this.mRenderView.shouldWaitForResize() || (NSTIJKPlayerMulti4.this.mVideoWidth == i2 && NSTIJKPlayerMulti4.this.mVideoHeight == i3)) {
                z = true;
            }
            if (NSTIJKPlayerMulti4.this.mMediaPlayer != null && z2 && z) {
                if (NSTIJKPlayerMulti4.this.mSeekWhenPrepared != 0) {
                    NSTIJKPlayerMulti4.this.seekTo(NSTIJKPlayerMulti4.this.mSeekWhenPrepared);
                }
                NSTIJKPlayerMulti4.this.start();
            }
        }

        @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView.IRenderCallback
        public void onSurfaceCreated(@NonNull IRenderView.ISurfaceHolder iSurfaceHolder, int i, int i2) {
            if (iSurfaceHolder.getRenderView() != NSTIJKPlayerMulti4.this.mRenderView) {
                Log.e(NSTIJKPlayerMulti4.this.TAG, "onSurfaceCreated: unmatched render callback\n");
                return;
            }
            IRenderView.ISurfaceHolder unused = NSTIJKPlayerMulti4.this.mSurfaceHolder = iSurfaceHolder;
            if (NSTIJKPlayerMulti4.this.mMediaPlayer != null) {
                NSTIJKPlayerMulti4.this.bindSurfaceHolder(NSTIJKPlayerMulti4.this.mMediaPlayer, iSurfaceHolder);
            } else {
                NSTIJKPlayerMulti4.this.openVideo();
            }
        }

        @Override // com.nst.yourname.view.ijkplayer.widget.media.IRenderView.IRenderCallback
        public void onSurfaceDestroyed(@NonNull IRenderView.ISurfaceHolder iSurfaceHolder) {
            if (iSurfaceHolder.getRenderView() != NSTIJKPlayerMulti4.this.mRenderView) {
                Log.e(NSTIJKPlayerMulti4.this.TAG, "onSurfaceDestroyed: unmatched render callback\n");
                return;
            }
            IRenderView.ISurfaceHolder unused = NSTIJKPlayerMulti4.this.mSurfaceHolder = null;
            NSTIJKPlayerMulti4.this.releaseWithoutStop();
        }
    };
    public int mSeekWhenPrepared;
    private Settings mSettings;
    IMediaPlayer.OnVideoSizeChangedListener mSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() {
        /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerMulti4.AnonymousClass2 */

        @Override // tv.danmaku.ijk.media.player.IMediaPlayer.OnVideoSizeChangedListener
        public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i2, int i3, int i4) {
            int unused = NSTIJKPlayerMulti4.this.mVideoWidth = iMediaPlayer.getVideoWidth();
            int unused2 = NSTIJKPlayerMulti4.this.mVideoHeight = iMediaPlayer.getVideoHeight();
            int unused3 = NSTIJKPlayerMulti4.this.mVideoSarNum = iMediaPlayer.getVideoSarNum();
            int unused4 = NSTIJKPlayerMulti4.this.mVideoSarDen = iMediaPlayer.getVideoSarDen();
            if (NSTIJKPlayerMulti4.this.mVideoWidth != 0 && NSTIJKPlayerMulti4.this.mVideoHeight != 0) {
                if (NSTIJKPlayerMulti4.this.mRenderView != null) {
                    NSTIJKPlayerMulti4.this.mRenderView.setVideoSize(NSTIJKPlayerMulti4.this.mVideoWidth, NSTIJKPlayerMulti4.this.mVideoHeight);
                    NSTIJKPlayerMulti4.this.mRenderView.setVideoSampleAspectRatio(NSTIJKPlayerMulti4.this.mVideoSarNum, NSTIJKPlayerMulti4.this.mVideoSarDen);
                }
                NSTIJKPlayerMulti4.this.requestLayout();
            }
        }
    };
    public int mSurfaceHeight;
    public IRenderView.ISurfaceHolder mSurfaceHolder = null;
    public int mSurfaceWidth;
    public int mTargetState = 0;
    public Uri mUri;
    public int mVideoHeight;
    public int mVideoRotationDegree;
    public int mVideoSarDen;
    public int mVideoSarNum;
    private NSTIJKPlayerMulti4 mVideoView4;
    public int mVideoWidth;
    public int maxRetry = 5;
    public View renderView;
    public int retryCount = 0;
    public boolean retrying = false;
    SharedPreferences sharedPreferences;
    public int status = 0;
    public TextView subtitleDisplay;

    public int getAudioSessionId() {
        return 0;
    }

    public NSTIJKPlayerMulti4(Context context2) {
        super(context2);
        initVideoView(context2);
    }

    public NSTIJKPlayerMulti4(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        initVideoView(context2);
    }

    public NSTIJKPlayerMulti4(Context context2, AttributeSet attributeSet, int i) {
        super(context2, attributeSet, i);
        initVideoView(context2);
    }

    private void initVideoView(Context context2) {
        this.mAppContext = context2.getApplicationContext();
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
                }
                setRenderView(textureRenderView);
                return;
            default:
                Log.e(this.TAG, String.format(Locale.getDefault(), "invalid render %d\n", Integer.valueOf(i)));
                return;
        }
    }

    public void setVideoPath(String str, boolean z, String str2) {
        setVideoURI(Uri.parse(str), z, str2);
    }

    public void setVideoURI(Uri uri, boolean z, String str) {
        setVideoURI(uri, null, z, str);
    }

    private void setVideoURI(Uri uri, Map<String, String> map, boolean z, String str) {
        this.mUri = uri;
        this.mHeaders = map;
        this.mSeekWhenPrepared = 0;
        this.fullscreen = z;
        stopPlayback();
        openVideo();
        requestLayout();
        invalidate();
    }

    public void setActivity(Activity activity, NSTIJKPlayerMulti4 nSTIJKPlayerMulti4) {
        this.mActivity = activity;
        this.mVideoView4 = nSTIJKPlayerMulti4;
        this.handler = new Handler();
        this.$ = new Query(activity);
    }

    public void setLiveStreamDBHandler(LiveStreamDBHandler liveStreamDBHandler2) {
        this.liveStreamDBHandler = liveStreamDBHandler2;
    }

    public void setContext(Context context2) {
        this.context = context2;
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
            if (this.mSurfaceHolder != null && this.mSurfaceHolder.getSurfaceHolder() != null) {
                this.mSurfaceHolder.getSurfaceHolder().setFormat(-2);
                this.mSurfaceHolder.getSurfaceHolder().setFormat(-1);
            }
        }
    }

    @TargetApi(23)
    public void openVideo() {
        if (this.mUri != null && this.mSurfaceHolder != null) {
            release(false);
            ((AudioManager) this.mAppContext.getSystemService("audio")).requestAudioFocus(null, 3, 1);
            this.$.id(R.id.app_video_status_4).gone();
            try {
                this.mMediaPlayer = createPlayer(this.mSettings.getPlayer());
                this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
                this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
                this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
                this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
                this.mMediaPlayer.setOnInfoListener(this.mInfoListener);
                this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
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
                this.mMediaPlayer.prepareAsync();
                this.audioManager = (AudioManager) this.mActivity.getSystemService("audio");
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

    public void removeHandlerCallback() {
        if (this.handler != null) {
            this.handler.removeCallbacksAndMessages(null);
        }
    }

    private void showStatus(String str) {
        this.$.id(R.id.video_view_4).gone();
        this.$.id(R.id.app_video_status_4).visible();
        this.$.id(R.id.app_video_status_text_4).text(str);
    }

    public void mutePlayer() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setVolume(0.0f, 0.0f);
        }
    }

    public void unMutePlayer() {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setVolume(1.0f, 1.0f);
        }
    }

    public void statusChange(int i) {
        this.status = i;
        if (i == -1) {
            boolean z = this.fullscreen;
            if (this.retryCount >= this.maxRetry) {
                hideAll();
                this.mUri = null;
                showStatus(this.mActivity.getResources().getString(R.string.small_problem));
                stopPlayback();
                this.retrying = false;
                return;
            }
            this.retrying = true;
            this.handler.postDelayed(new Runnable() {
                /* class com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerMulti4.AnonymousClass1 */

                public void run() {
                    NSTIJKPlayerMulti4.this.retryCount++;
                    NSTIJKPlayerMulti4.this.hideAll();
                    Utils.showToast(NSTIJKPlayerMulti4.this.mActivity, NSTIJKPlayerMulti4.this.mActivity.getResources().getString(R.string.play_back_error) + " (" + NSTIJKPlayerMulti4.this.retryCount + "/" + NSTIJKPlayerMulti4.this.maxRetry + ")");
                    NSTIJKPlayerMulti4.this.openVideo();
                    NSTIJKPlayerMulti4.this.start();
                }
            }, 3000);
        } else if (i == 1) {
            this.$.id(R.id.app_video_loading_4).visible();
        } else if (i == 3) {
            this.retryCount = 0;
            this.$.id(R.id.exo_play).gone();
            this.$.id(R.id.exo_pause).visible();
            if (this.fullscreen) {
                this.$.id(R.id.exo_pause).requestFocus();
            }
            this.$.id(R.id.app_video_loading_4).gone();
        } else if (i == 6) {
            this.retryCount = 0;
            this.$.id(R.id.app_video_status_4).gone();
            this.$.id(R.id.video_view_4).visible();
            this.$.id(R.id.exo_play).gone();
            this.$.id(R.id.exo_pause).visible();
            this.$.id(R.id.app_video_loading_4).gone();
        } else if (i == 2) {
            this.retryCount = 0;
            this.$.id(R.id.app_video_status_4).gone();
            this.$.id(R.id.video_view_4).visible();
            this.$.id(R.id.exo_play).gone();
            this.$.id(R.id.exo_pause).visible();
            this.$.id(R.id.app_video_loading_4).gone();
            if (!this.mActivity.findViewById(R.id.app_video_box_4).isFocusable()) {
                mutePlayer();
            }
        } else if (i == 4) {
            this.$.id(R.id.exo_play).visible();
            this.$.id(R.id.exo_pause).gone();
            if (this.fullscreen) {
                this.$.id(R.id.exo_play).requestFocus();
            }
        }
    }

    public void hideAll() {
        this.$.id(R.id.app_video_loading_4).gone();
        this.$.id(R.id.app_video_status_4).gone();
        hideTitleBarAndFooter();
    }

    public void hideTitleBarAndFooter() {
        this.$.id(R.id.app_video_top_box_4).gone();
        this.$.id(R.id.controls_4).gone();
        this.$.id(R.id.ll_seekbar_time_4).gone();
        this.handler.removeCallbacksAndMessages(null);
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
            }
        }
        return super.onKeyDown(i, keyEvent);
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

    @NonNull
    public static String getRenderText(Context context2, int i) {
        switch (i) {
            case 0:
                return context2.getString(R.string.VideoView_render_none);
            case 1:
                return context2.getString(R.string.VideoView_render_surface_view);
            case 2:
                return context2.getString(R.string.VideoView_render_texture_view);
            default:
                return context2.getString(R.string.N_A);
        }
    }

    @NonNull
    public static String getPlayerText(Context context2, int i) {
        switch (i) {
            case 1:
                return context2.getString(R.string.VideoView_player_AndroidMediaPlayer);
            case 2:
                return context2.getString(R.string.VideoView_player_IjkMediaPlayer);
            case 3:
                return context2.getString(R.string.VideoView_player_IjkExoMediaPlayer);
            default:
                return context2.getString(R.string.N_A);
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
            this.$.id(R.id.app_video_loading_4).visible();
            androidMediaPlayer = null;
            if (this.mUri != null) {
                IjkMediaPlayer ijkMediaPlayer = new IjkMediaPlayer();
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
                ijkMediaPlayer.setOption(4, "mediacodec-hevc", 1);
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
}
