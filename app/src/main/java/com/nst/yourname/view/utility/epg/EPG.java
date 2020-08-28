package com.nst.yourname.view.utility.epg;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.UnsupportedDrmException;
import com.google.android.exoplayer2.offline.FilteringManifestParser;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifestParser;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.RandomTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.Maps;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.FavouriteM3UModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.exoplayer.DemoApplication;
import com.nst.yourname.view.ijkplayer.application.Settings;
import com.nst.yourname.view.ijkplayer.widget.media.FileMediaDataSource;
import com.nst.yourname.view.ijkplayer.widget.media.InfoHudViewHolder;
import com.nst.yourname.view.ijkplayer.widget.media.MeasureHelper;
import com.nst.yourname.view.ijkplayer.widget.media.MediaPlayerCompat;
import com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSmallEPG;
import com.nst.yourname.view.utility.epg.domain.EPGChannel;
import com.nst.yourname.view.utility.epg.domain.EPGEvent;
import com.nst.yourname.view.utility.epg.domain.EPGState;
import com.nst.yourname.view.utility.epg.misc.EPGDataImpl;
import com.nst.yourname.view.utility.epg.misc.EPGUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDateTime;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

@SuppressWarnings("ALL")
public class EPG extends ViewGroup implements PlaybackPreparer {
    public static final String ABR_ALGORITHM_DEFAULT = "default";
    public static final String ABR_ALGORITHM_EXTRA = "abr_algorithm";
    public static final String ABR_ALGORITHM_RANDOM = "random";
    public static final String ACTION_VIEW = "com.google.android.exoplayer.demo.action.VIEW";
    public static final String ACTION_VIEW_LIST = "com.google.android.exoplayer.demo.action.VIEW_LIST";
    public static final String AD_TAG_URI_EXTRA = "ad_tag_uri";
    public static int DAYS_BACK_MILLIS = 3600000;
    public static int DAYS_FORWARD_MILLIS = 14400000;
    private static final CookieManager DEFAULT_COOKIE_MANAGER = new CookieManager();
    public static final String DRM_KEY_REQUEST_PROPERTIES_EXTRA = "drm_key_request_properties";
    public static final String DRM_LICENSE_URL_EXTRA = "drm_license_url";
    public static final String DRM_MULTI_SESSION_EXTRA = "drm_multi_session";
    public static final String DRM_SCHEME_EXTRA = "drm_scheme";
    private static final String DRM_SCHEME_UUID_EXTRA = "drm_scheme_uuid";
    public static final String EXTENSION_EXTRA = "extension";
    public static final String EXTENSION_LIST_EXTRA = "extension_list";
    public static int HOURS_IN_VIEWPORT_MILLIS = 7200000;
    private static final String KEY_AUTO_PLAY = "auto_play";
    private static final String KEY_POSITION = "position";
    private static final String KEY_TRACK_SELECTOR_PARAMETERS = "track_selector_parameters";
    private static final String KEY_WINDOW = "window";
    private static final int MESSAGE_FADE_OUT = 2;
    private static final int MESSAGE_HIDE_CENTER_BOX = 4;
    private static final int MESSAGE_RESTART_PLAY = 5;
    private static final int MESSAGE_SEEK_NEW_POSITION = 3;
    private static final int MESSAGE_SHOW_PROGRESS = 1;
    public static final String PREFER_EXTENSION_DECODERS_EXTRA = "prefer_extension_decoders";
    public static final String SPHERICAL_STEREO_MODE_EXTRA = "spherical_stereo_mode";
    public static final String SPHERICAL_STEREO_MODE_LEFT_RIGHT = "left_right";
    public static final String SPHERICAL_STEREO_MODE_MONO = "mono";
    public static final String SPHERICAL_STEREO_MODE_TOP_BOTTOM = "top_bottom";
    private static final int SURFACE_16_9 = 3;
    private static final int SURFACE_4_3 = 4;
    private static final int SURFACE_BEST_FIT = 0;
    private static final int SURFACE_FILL = 2;
    private static final int SURFACE_FIT_SCREEN = 1;
    private static final int SURFACE_ORIGINAL = 5;
    public static final int TIME_LABEL_SPACING_MILLIS = 1800000;
    public static final String URI_LIST_EXTRA = "uri_list";
    private static ImageView programImage;
    public static int screenHeight;
    public static int screenWidth;
    private static String uk;
    private static String una;
    private Boolean EventSelected;
    private int STATUS_COMPLETED;
    private int STATUS_ERROR;
    private int STATUS_IDLE;
    private int STATUS_LOADING;
    private int STATUS_PAUSE;
    private int STATUS_PLAYING;
    private int STATUS_TIME_CHANGED;
    public final String TAG;
    String VideoPathUrl;
    ProgressBar app_video_loading;
    public PopupWindow changeSortPopUp;
    Context context1;
    private TextView currentEventDescriptionTextView;
    private TextView currentEventTextView;
    private TextView currentEventTimeTextView;
    private DataSource.Factory dataSourceFactory;
    DatabaseHandler database;
    private LinearLayout debugRootView;
    private long defaultRetryTime;
    private DateFormat df;
    private Date dt;
    public EPGData epgData;
    public EPGChannel epgDataFirstChannelID;
    public EPGChannel epgDataLastChannelID;
    private String extensionType;
    boolean externalPlayerSelected;
    private boolean firstTimeScroll;
    boolean flag_full_epg;
    private SimpleDateFormat fr;
    public boolean fullscreen;
    public Handler handler;
    private Handler handlerAspectRatio;
    private Handler handlerOverlay;
    private Handler handlerSeekbar;
    private boolean isLive;
    public TrackGroupArray lastSeenTrackGroupArray;
    LiveStreamDBHandler liveStreamDBHandler;
    public int loadEPGDataAsyncTaskRunning;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences loginPreferencesSharedPref;
    private SharedPreferences loginPreferencesSharedPref_auto_start;
    private SharedPreferences loginPreferencesSharedPref_opened_video;
    private SharedPreferences loginPreferencesSharedPref_time_format;
    private SharedPreferences loginPreferencesUserAgent;
    private SharedPreferences loginPreferences_audio_delay;
    private SharedPreferences loginPreferences_audio_selected;
    private SharedPreferences loginPreferences_subtitle_delay;
    private SharedPreferences loginPreferences_subtitle_selected;
    private SharedPreferences.Editor loginPrefsEditorAudio;
    private SharedPreferences.Editor loginPrefsEditorAudioDelay;
    private SharedPreferences.Editor loginPrefsEditorSubtitle;
    private SharedPreferences.Editor loginPrefsEditorSubtitleDelay;
    public Activity mActivity;
    public final Map<String, Bitmap> mChannelImageCache;
    public final Map<String, Target> mChannelImageTargetCache;
    private final int mChannelLayoutBackground;
    private final int mChannelLayoutHeight;
    private final int mChannelLayoutLeftMargin;
    private int mChannelLayoutMargin;
    private int mChannelLayoutMaxLength;
    private final int mChannelLayoutPadding;
    private int mChannelLayoutWidth;
    public EPGClickListener mClickListener;
    private final Rect mClipRect;
    private int mCurrentAspectRatioIndex;
    private final Rect mDrawingRect;
    private final int mEPGBackground;
    private final int mEventLayoutBackground;
    private final int mEventLayoutBackgroundCurrent;
    private final int mEventLayoutBackgroundNoProg;
    private final int mEventLayoutBackgroundSelected;
    private final int mEventLayoutTextColor;
    private final int mEventLayoutTextSize;
    private final GestureDetector mGestureDetector;
    private SurfaceHolder mHolder;
    private long mMargin;
    public int mMaxHorizontalScroll;
    public int mMaxVerticalScroll;
    private final Rect mMeasuringRect;
    private long mMillisPerPixel;
    private final Paint mPaint;
    private final Bitmap mResetButtonIcon;
    private final int mResetButtonMargin;
    private final int mResetButtonSize;
    public final Scroller mScroller;
    private Settings mSettings;
    private SurfaceHolder mSubtitlesSurfaceHolder;
    private TextView mSurfaceSubtitles;
    private final int mTimeBarHeight;
    private final int mTimeBarLineColor;
    private final int mTimeBarLineWidth;
    private int mTimeBarTextSize;
    private long mTimeLowerBoundary;
    private long mTimeOffset;
    private long mTimeUpperBoundary;
    private NSTIJKPlayerSmallEPG mVideoView;
    public int maxRetry;
    private FrameworkMediaDrm mediaDrm;
    private MediaSource mediaSource;
    private int opened_stream_id;
    private String opened_video_url;
    private int orientation;
    public SimpleExoPlayer player;
    private boolean playerSupport;
    private PlayerView playerView;
    public Uri playingURL;
    private SimpleDateFormat programTimeFormat;
    private SimpleDateFormat programTimeFormatLong;
    public ProgressDialog progressDialog;
    public int retryCount;
    public boolean retrying;
    public Boolean rq;
    private Runnable runnableOverlay;
    private Runnable runnableSeekbar;
    private EPGEvent selectedEvent;
    private int selectedPosition;
    SharedPreferences sharedPreferences;
    private boolean startAutoPlay;
    private long startPosition;
    private int startWindow;
    private int status;
    public boolean stopRetry;
    public DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    TextView tv_video_height;
    TextView tv_video_width;
    private String ukd;
    private String unad;
    private String url;
    LinearLayout videoStatus;
    TextView videoStatusText;
    public SurfaceView videoView;
    private SeekBar vlcSeekbar;

    private void gotoNextDay(EPGEvent ePGEvent) {
    }

    private void gotoPreviousDay(EPGEvent ePGEvent) {
    }

    private void selectEvent() {
    }

    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        return false;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return false;
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }

    static {
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    public EPG(Context context) {
        this(context, null);
        this.context1 = context;
        this.handler = new Handler();
        this.handlerAspectRatio = new Handler();
        this.mSettings = new Settings(context);
        this.sharedPreferences = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.ukd = Utils.ukde(FileMediaDataSource.apn());
        una = context.getApplicationContext().getPackageName();
        uk = getApplicationName(context);
        this.unad = Utils.ukde(MeasureHelper.pnm());
        this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.dt = new Date();
        if (df(this.fr, this.fr.format(new Date(MediaPlayerCompat.cit(context))), this.df.format(this.dt)) >= ((long) InfoHudViewHolder.ux()) && this.ukd != null && this.unad != null) {
            if (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad))) {
                this.rq = false;
            }
        }
    }

    public EPG(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        this.context1 = context;
        this.handler = new Handler();
        this.handlerAspectRatio = new Handler();
        this.mSettings = new Settings(context);
        this.sharedPreferences = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.ukd = Utils.ukde(FileMediaDataSource.apn());
        una = context.getApplicationContext().getPackageName();
        uk = getApplicationName(context);
        this.unad = Utils.ukde(MeasureHelper.pnm());
        this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.dt = new Date();
        if (df(this.fr, this.fr.format(new Date(MediaPlayerCompat.cit(context))), this.df.format(this.dt)) >= ((long) InfoHudViewHolder.ux()) && this.ukd != null && this.unad != null) {
            if (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad))) {
                this.rq = false;
            }
        }
    }

    public EPG(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = getClass().getSimpleName();
        this.mMargin = 200000;
        this.epgData = null;
        this.epgDataFirstChannelID = null;
        this.epgDataLastChannelID = null;
        this.selectedEvent = null;
        this.opened_video_url = "";
        this.STATUS_ERROR = -1;
        this.STATUS_IDLE = 0;
        this.STATUS_LOADING = 1;
        this.STATUS_PLAYING = 2;
        this.STATUS_PAUSE = 3;
        this.STATUS_COMPLETED = 4;
        this.STATUS_TIME_CHANGED = 5;
        this.status = this.STATUS_IDLE;
        this.isLive = false;
        this.defaultRetryTime = DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS;
        this.retryCount = 0;
        this.maxRetry = 5;
        this.retrying = false;
        this.mCurrentAspectRatioIndex = 3;
        this.EventSelected = false;
        this.selectedPosition = 0;
        this.firstTimeScroll = false;
        this.externalPlayerSelected = false;
        this.stopRetry = false;
        this.rq = true;
        this.context1 = context;
        this.handler = new Handler();
        this.handlerAspectRatio = new Handler();
        this.mSettings = new Settings(context);
        this.sharedPreferences = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        setWillNotDraw(false);
        resetBoundaries();
        this.mChannelLayoutMargin = getResources().getDimensionPixelSize(R.dimen.epg_channel_layout_margin);
        this.mChannelLayoutWidth = getResources().getDimensionPixelSize(R.dimen.epg_channel_layout_width);
        this.mChannelLayoutMaxLength = getResources().getDimensionPixelSize(R.dimen.epg_channel_maximum_length);
        this.mTimeBarTextSize = getResources().getDimensionPixelSize(R.dimen.epg_time_bar_text);
        if ((getResources().getConfiguration().screenLayout & 15) == 3) {
            HOURS_IN_VIEWPORT_MILLIS = 14400000;
            this.mChannelLayoutWidth += 125;
            this.mChannelLayoutMaxLength += 95;
            this.mTimeBarTextSize += 10;
        }
        this.mDrawingRect = new Rect();
        this.mClipRect = new Rect();
        this.mMeasuringRect = new Rect();
        this.mPaint = new Paint(1);
        this.mGestureDetector = new GestureDetector(context, new OnGestureListener());
        this.mChannelImageCache = Maps.newHashMap();
        this.mChannelImageTargetCache = Maps.newHashMap();
        this.mScroller = new Scroller(context);
        this.mEPGBackground = getResources().getColor(R.color.epg_background);
        this.mChannelLayoutPadding = getResources().getDimensionPixelSize(R.dimen.epg_channel_layout_padding);
        this.mChannelLayoutHeight = getResources().getDimensionPixelSize(R.dimen.epg_channel_layout_height);
        this.mChannelLayoutLeftMargin = getResources().getDimensionPixelSize(R.dimen.epg_channel_layout_margin_left);
        this.mChannelLayoutBackground = getResources().getColor(R.color.epg_channel_layout_background);
        this.mEventLayoutBackground = getResources().getColor(R.color.epg_event_layout_background);
        this.mEventLayoutBackgroundCurrent = getResources().getColor(R.color.epg_event_layout_background_current);
        this.mEventLayoutBackgroundSelected = getResources().getColor(R.color.epg_event_layout_background_selected);
        this.mEventLayoutBackgroundNoProg = getResources().getColor(R.color.epg_event_layout_background_no_prog);
        this.mEventLayoutTextColor = getResources().getColor(R.color.epg_event_layout_text);
        this.mEventLayoutTextSize = getResources().getDimensionPixelSize(R.dimen.epg_event_layout_text);
        this.mTimeBarHeight = getResources().getDimensionPixelSize(R.dimen.epg_time_bar_height);
        this.mTimeBarLineWidth = getResources().getDimensionPixelSize(R.dimen.epg_time_bar_line_width);
        this.mTimeBarLineColor = getResources().getColor(R.color.epg_time_bar);
        this.mResetButtonSize = getResources().getDimensionPixelSize(R.dimen.epg_reset_button_size);
        this.mResetButtonMargin = getResources().getDimensionPixelSize(R.dimen.epg_reset_button_margin);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outWidth = this.mResetButtonSize;
        options.outHeight = this.mResetButtonSize;
        this.mResetButtonIcon = BitmapFactory.decodeResource(getResources(), R.drawable.reset, options);
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.outWidth = this.mResetButtonSize;
        options2.outHeight = this.mResetButtonSize;
        this.ukd = Utils.ukde(FileMediaDataSource.apn());
        una = context.getApplicationContext().getPackageName();
        uk = getApplicationName(context);
        this.unad = Utils.ukde(MeasureHelper.pnm());
        this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.dt = new Date();
        if (df(this.fr, this.fr.format(new Date(MediaPlayerCompat.cit(context))), this.df.format(this.dt)) >= ((long) InfoHudViewHolder.ux()) && this.ukd != null && this.unad != null) {
            if (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad))) {
                this.rq = false;
            }
        }
    }

    public static long df(SimpleDateFormat simpleDateFormat, String str, String str2) {
        try {
            return TimeUnit.DAYS.convert(simpleDateFormat.parse(str2).getTime() - simpleDateFormat.parse(str).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getApplicationName(Context context) {
        return String.valueOf(context.getApplicationInfo().loadLabel(context.getPackageManager()));
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    public void setActivity(Activity activity, NSTIJKPlayerSmallEPG nSTIJKPlayerSmallEPG) {
        this.mActivity = activity;
        this.mVideoView = nSTIJKPlayerSmallEPG;
    }

    public Parcelable onSaveInstanceState() {
        EPGState ePGState = new EPGState(super.onSaveInstanceState());
        ePGState.setCurrentEvent(this.selectedEvent);
        return ePGState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof EPGState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        EPGState ePGState = (EPGState) parcelable;
        super.onRestoreInstanceState(ePGState.getSuperState());
        this.selectedEvent = ePGState.getCurrentEvent();
    }

    private int getChannelAreaWidth() {
        return this.mChannelLayoutWidth + this.mChannelLayoutPadding + this.mChannelLayoutMargin;
    }

    private int getProgramAreaWidth() {
        return getWidth() - getChannelAreaWidth();
    }

    public void onDraw(Canvas canvas) {
        if (this.epgData != null && this.epgData.hasData()) {
            this.mTimeLowerBoundary = getTimeFrom(getScrollX());
            this.mTimeUpperBoundary = getTimeFrom(getScrollX() + getWidth());
            Rect rect = this.mDrawingRect;
            rect.left = getScrollX();
            rect.top = getScrollY();
            rect.right = rect.left + getWidth();
            rect.bottom = rect.top + getHeight();
            drawEvents(canvas, rect);
            drawChannelListItems(canvas, rect);
            drawTimebar(canvas, rect);
            drawTimeLine(canvas, rect);
            drawResetButton(canvas, rect);
            if (this.mScroller.computeScrollOffset()) {
                scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
                if (!this.firstTimeScroll) {
                    this.firstTimeScroll = true;
                    long currentTimeMillis = System.currentTimeMillis() + ((long) getTimeShiftMilliSeconds());
                    if ((getResources().getConfiguration().screenLayout & 15) == 3) {
                        scrollTo(((getXFrom(currentTimeMillis) - this.mChannelLayoutWidth) + this.mChannelLayoutMargin) - 200, 0);
                    } else {
                        scrollTo(((getXFrom(currentTimeMillis) - this.mChannelLayoutWidth) + this.mChannelLayoutMargin) - 100, 0);
                    }
                }
            }
        }
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        recalculateAndRedraw(this.selectedEvent, false, null, null);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mGestureDetector.onTouchEvent(motionEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return super.onKeyUp(i, keyEvent);
    }

    private void drawResetButton(Canvas canvas, Rect rect) {
        if (((long) Math.abs(getXPositionStart() - getScrollX())) > ((long) (getWidth() / 3))) {
            Rect calculateResetButtonHitArea = calculateResetButtonHitArea();
            this.mPaint.setColor(this.mTimeBarLineColor);
            canvas.drawCircle((float) (calculateResetButtonHitArea.right - (this.mResetButtonSize / 2)), (float) (calculateResetButtonHitArea.bottom - (this.mResetButtonSize / 2)), (float) (Math.min(calculateResetButtonHitArea.width(), calculateResetButtonHitArea.height()) / 2), this.mPaint);
            calculateResetButtonHitArea.left += this.mResetButtonMargin;
            calculateResetButtonHitArea.right -= this.mResetButtonMargin;
            calculateResetButtonHitArea.top += this.mResetButtonMargin;
            calculateResetButtonHitArea.bottom -= this.mResetButtonMargin;
            canvas.drawBitmap(this.mResetButtonIcon, (Rect) null, calculateResetButtonHitArea, this.mPaint);
        }
    }

    private void drawTimebarBottomStroke(Canvas canvas, Rect rect) {
        rect.left = getScrollX();
        rect.top = getScrollY() + this.mTimeBarHeight;
        rect.right = rect.left + getWidth();
        rect.bottom = rect.top + this.mChannelLayoutMargin;
        this.mPaint.setColor(this.mEPGBackground);
        canvas.drawRect(rect, this.mPaint);
    }

    private void drawTimebar(Canvas canvas, Rect rect) {
        rect.left = getScrollX() + this.mChannelLayoutWidth + this.mChannelLayoutMargin;
        rect.top = getScrollY();
        rect.right = rect.left + getWidth();
        rect.bottom = rect.top + this.mTimeBarHeight;
        this.mClipRect.left = getScrollX() + this.mChannelLayoutWidth + this.mChannelLayoutMargin;
        this.mClipRect.top = getScrollY();
        this.mClipRect.right = getScrollX() + getWidth();
        this.mClipRect.bottom = this.mClipRect.top + this.mTimeBarHeight;
        canvas.save();
        canvas.clipRect(this.mClipRect);
        this.mPaint.setColor(this.mChannelLayoutBackground);
        canvas.drawRect(rect, this.mPaint);
        this.mPaint.setColor(this.mEventLayoutTextColor);
        this.mPaint.setTextSize((float) this.mTimeBarTextSize);
        for (int i = 0; i < HOURS_IN_VIEWPORT_MILLIS / TIME_LABEL_SPACING_MILLIS; i++) {
            long j = (((this.mTimeLowerBoundary + ((long) (TIME_LABEL_SPACING_MILLIS * i))) + 900000) / 1800000) * 1800000;
            canvas.drawText(EPGUtil.getShortTime(this.context1, j), (float) getXFrom(j), (float) (rect.top + ((rect.bottom - rect.top) / 2) + (this.mTimeBarTextSize / 2)), this.mPaint);
        }
        canvas.restore();
        drawTimebarDayIndicator(canvas, rect);
        drawTimebarBottomStroke(canvas, rect);
    }

    private void drawTimebarDayIndicator(Canvas canvas, Rect rect) {
        rect.left = getScrollX();
        rect.top = getScrollY();
        rect.right = rect.left + this.mChannelLayoutWidth;
        rect.bottom = rect.top + this.mTimeBarHeight;
        this.mPaint.setColor(this.mChannelLayoutBackground);
        this.mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawRect(rect, this.mPaint);
        this.mPaint.setColor(this.mEventLayoutTextColor);
        this.mPaint.setTextSize((float) this.mTimeBarTextSize);
        canvas.drawText(EPGUtil.getEPGdayName(this.mTimeLowerBoundary), (float) (rect.left + ((rect.right - rect.left) / 2)), (float) (rect.top + ((rect.bottom - rect.top) / 2) + (this.mTimeBarTextSize / 2)), this.mPaint);
        this.mPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void drawTimeLine(Canvas canvas, Rect rect) {
        long currentTimeMillis = System.currentTimeMillis() + ((long) getTimeShiftMilliSeconds());
        if (shouldDrawTimeLine(currentTimeMillis)) {
            rect.left = getXFrom(currentTimeMillis);
            rect.top = getScrollY();
            rect.right = rect.left + this.mTimeBarLineWidth;
            rect.bottom = rect.top + getHeight();
            this.mPaint.setColor(this.mTimeBarLineColor);
            canvas.drawRect(rect, this.mPaint);
            if (!this.firstTimeScroll) {
                this.firstTimeScroll = true;
                if ((getResources().getConfiguration().screenLayout & 15) == 3) {
                    scrollTo(((getXFrom(currentTimeMillis) - this.mChannelLayoutWidth) + this.mChannelLayoutMargin) - 200, 0);
                } else {
                    scrollTo(((getXFrom(currentTimeMillis) - this.mChannelLayoutWidth) + this.mChannelLayoutMargin) - 100, 0);
                }
            }
        }
    }

    public int getTimeShiftMilliSeconds() {
        this.loginPreferencesAfterLogin = getContext().getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        return Utils.getMilliSeconds(this.loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_SELECTED_EPG_SHIFT, ""));
    }

    private void drawEvent(Canvas canvas, int i, EPGEvent ePGEvent, Rect rect) {
        setEventDrawingRectangle(i, ePGEvent.getStart(), ePGEvent.getEnd(), rect);
        String title = ePGEvent.getTitle();
        if (ePGEvent.isSelected()) {
            this.mPaint.setColor(this.mEventLayoutBackgroundSelected);
            this.selectedPosition = i;
        } else if (ePGEvent.isCurrent(getTimeShiftMilliSeconds())) {
            this.mPaint.setColor(this.mEventLayoutBackgroundCurrent);
        } else if (title == null || title.isEmpty() || !title.equals(getContext().getResources().getString(R.string.no_information))) {
            this.mPaint.setColor(this.mEventLayoutBackground);
        } else {
            this.mPaint.setColor(this.mEventLayoutBackgroundNoProg);
        }
        canvas.drawRect(rect, this.mPaint);
        rect.left += this.mChannelLayoutPadding + 16;
        rect.right -= this.mChannelLayoutPadding;
        this.mPaint.setColor(this.mEventLayoutTextColor);
        if ((getResources().getConfiguration().screenLayout & 15) == 3) {
            this.mPaint.setTextSize((float) (this.mEventLayoutTextSize + 6));
        } else {
            this.mPaint.setTextSize((float) this.mEventLayoutTextSize);
        }
        this.mPaint.getTextBounds(ePGEvent.getTitle(), 0, ePGEvent.getTitle().length(), this.mMeasuringRect);
        rect.top += ((rect.bottom - rect.top) / 2) + (this.mMeasuringRect.height() / 2);
        canvas.drawText(title.substring(0, this.mPaint.breakText(title, true, (float) (rect.right - rect.left), null)), (float) rect.left, (float) rect.top, this.mPaint);
    }

    private void setEventDrawingRectangle(int i, long j, long j2, Rect rect) {
        rect.left = getXFrom(j);
        rect.top = getTopFrom(i);
        rect.right = getXFrom(j2) - this.mChannelLayoutMargin;
        rect.bottom = rect.top + this.mChannelLayoutHeight;
    }

    private void drawChannelListItems(Canvas canvas, Rect rect) {
        int lastVisibleChannelPosition = getLastVisibleChannelPosition();
        for (int firstVisibleChannelPosition = getFirstVisibleChannelPosition(); firstVisibleChannelPosition <= lastVisibleChannelPosition; firstVisibleChannelPosition++) {
            drawChannelItem(canvas, firstVisibleChannelPosition, rect);
        }
    }

    private void drawEvents(Canvas canvas, Rect rect) {
        int lastVisibleChannelPosition = getLastVisibleChannelPosition();
        for (int firstVisibleChannelPosition = getFirstVisibleChannelPosition(); firstVisibleChannelPosition <= lastVisibleChannelPosition; firstVisibleChannelPosition++) {
            this.mClipRect.left = getScrollX() + this.mChannelLayoutWidth + this.mChannelLayoutMargin;
            this.mClipRect.top = getTopFrom(firstVisibleChannelPosition);
            this.mClipRect.right = getScrollX() + getWidth();
            this.mClipRect.bottom = this.mClipRect.top + this.mChannelLayoutHeight;
            canvas.save();
            canvas.clipRect(this.mClipRect);
            boolean z = false;
            for (EPGEvent ePGEvent : this.epgData.getEvents(firstVisibleChannelPosition)) {
                if (isEventVisible(ePGEvent.getStart(), ePGEvent.getEnd())) {
                    drawEvent(canvas, firstVisibleChannelPosition, ePGEvent, rect);
                    z = true;
                } else if (z) {
                    break;
                }
            }
            canvas.restore();
        }
    }

    private void drawChannelItem(Canvas canvas, int i, Rect rect) {
        rect.left = getScrollX();
        rect.top = getTopFrom(i);
        rect.right = rect.left + this.mChannelLayoutLeftMargin;
        rect.bottom = rect.top + this.mChannelLayoutHeight;
        final String imageURL = this.epgData.getChannel(i).getImageURL();
        String name = this.epgData.getChannel(i).getName();
        if (i == this.selectedPosition) {
            this.mPaint.setColor(this.mEventLayoutTextColor);
            Rect rect2 = new Rect(getScrollX(), rect.top, rect.left + this.mChannelLayoutWidth, rect.top + this.mChannelLayoutHeight);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setColor(this.mEventLayoutBackgroundSelected);
            canvas.drawRect(rect2, paint);
        } else {
            this.mPaint.setColor(this.mEventLayoutTextColor);
            Rect rect3 = new Rect(rect.left, rect.top, rect.left + this.mChannelLayoutWidth, rect.top + this.mChannelLayoutHeight);
            Paint paint2 = new Paint();
            paint2.setStyle(Paint.Style.FILL_AND_STROKE);
            paint2.setColor(this.mChannelLayoutBackground);
            canvas.drawRect(rect3, paint2);
        }
        if (this.mChannelImageCache.containsKey(imageURL)) {
            Bitmap bitmap = this.mChannelImageCache.get(imageURL);
            rect = getDrawingRectForChannelImage(rect, bitmap);
            canvas.drawBitmap(bitmap, (Rect) null, rect, (Paint) null);
        } else {
            int min = Math.min(this.mChannelLayoutHeight, this.mChannelLayoutWidth);
            if (!this.mChannelImageTargetCache.containsKey(imageURL)) {
                this.mChannelImageTargetCache.put(imageURL, new Target() {
                    /* class com.nst.yourname.view.utility.epg.EPG.AnonymousClass1 */

                    @Override // com.squareup.picasso.Target
                    public void onPrepareLoad(Drawable drawable) {
                    }

                    @Override // com.squareup.picasso.Target
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                        EPG.this.mChannelImageCache.put(imageURL, bitmap);
                        EPG.this.redraw();
                        EPG.this.mChannelImageTargetCache.remove(imageURL);
                    }

                    @Override // com.squareup.picasso.Target
                    public void onBitmapFailed(Drawable drawable) {
                        Bitmap bitmap = (Bitmap) EPG.this.mChannelImageCache.get(imageURL);
                        EPG.this.mChannelImageCache.put(imageURL, BitmapFactory.decodeResource(EPG.this.getResources(), R.drawable.logo_placeholder_white));
                        EPG.this.redraw();
                        EPG.this.mChannelImageTargetCache.remove(imageURL);
                    }
                });
                EPGUtil.loadImageInto(getContext(), imageURL, min, min, this.mChannelImageTargetCache.get(imageURL));
            }
        }
        this.mPaint.setColor(this.mEventLayoutTextColor);
        if ((getResources().getConfiguration().screenLayout & 15) == 3) {
            this.mPaint.setTextSize(35.0f);
        }
        int length = name.length();
        String substring = name.substring(0, this.mPaint.breakText(name, true, (float) this.mChannelLayoutMaxLength, null));
        String str = "";
        if (substring.length() < length) {
            str = "..";
        }
        canvas.drawText(substring + str, (float) (rect.right + 10), (float) (rect.centerY() + 10), this.mPaint);
    }

    private Rect getDrawingRectForChannelImage(Rect rect, Bitmap bitmap) {
        rect.left += this.mChannelLayoutPadding;
        rect.top += this.mChannelLayoutPadding;
        rect.right -= this.mChannelLayoutPadding;
        rect.bottom -= this.mChannelLayoutPadding;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = ((float) height) / ((float) width);
        int i = rect.right - rect.left;
        int i2 = rect.bottom - rect.top;
        if (width > height) {
            int i3 = ((int) (((float) i2) - (((float) i) * f))) / 2;
            rect.top += i3;
            rect.bottom -= i3;
        } else if (width <= height) {
            int i4 = ((int) (((float) i) - (((float) i2) / f))) / 2;
            rect.left += i4;
            rect.right -= i4;
        }
        return rect;
    }

    private boolean shouldDrawTimeLine(long j) {
        return j >= this.mTimeLowerBoundary && j < this.mTimeUpperBoundary;
    }

    private boolean isEventVisible(long j, long j2) {
        return (j >= this.mTimeLowerBoundary && j <= this.mTimeUpperBoundary) || (j2 >= this.mTimeLowerBoundary && j2 <= this.mTimeUpperBoundary) || (j <= this.mTimeLowerBoundary && j2 >= this.mTimeUpperBoundary);
    }

    private long calculatedBaseLine() {
        this.loginPreferencesSharedPref_auto_start = this.context1.getSharedPreferences(AppConst.LOGIN_PREF_AUTO_START, 0);
        this.flag_full_epg = this.loginPreferencesSharedPref_auto_start.getBoolean(AppConst.LOGIN_PREF_FULL_EPG, false);
        if (this.flag_full_epg) {
            DAYS_BACK_MILLIS = DateTimeConstants.MILLIS_PER_DAY;
        } else {
            DAYS_BACK_MILLIS = DateTimeConstants.MILLIS_PER_HOUR;
        }
        return LocalDateTime.now().toDateTime().minusMillis(DAYS_BACK_MILLIS).getMillis();
    }

    private int getFirstVisibleChannelPosition() {
        int scrollY = ((getScrollY() - this.mChannelLayoutMargin) - this.mTimeBarHeight) / (this.mChannelLayoutHeight + this.mChannelLayoutMargin);
        if (scrollY < 0) {
            return 0;
        }
        return scrollY;
    }

    private int getLastVisibleChannelPosition() {
        int scrollY = getScrollY();
        int channelCount = this.epgData.getChannelCount();
        int height = scrollY + getHeight();
        int i = ((this.mTimeBarHeight + height) - this.mChannelLayoutMargin) / (this.mChannelLayoutHeight + this.mChannelLayoutMargin);
        int i2 = channelCount - 1;
        if (i > i2) {
            i = i2;
        }
        return (height <= this.mChannelLayoutHeight * i || i >= i2) ? i : i + 1;
    }

    private EPGChannel getFirstChannelData() {
        return this.epgDataFirstChannelID;
    }

    private EPGChannel getFirstLastChannelData() {
        return this.epgDataLastChannelID;
    }

    private void calculateMaxHorizontalScroll() {
        this.loginPreferencesSharedPref_auto_start = this.context1.getSharedPreferences(AppConst.LOGIN_PREF_AUTO_START, 0);
        this.flag_full_epg = this.loginPreferencesSharedPref_auto_start.getBoolean(AppConst.LOGIN_PREF_FULL_EPG, false);
        if (this.flag_full_epg) {
            DAYS_FORWARD_MILLIS = DateTimeConstants.MILLIS_PER_DAY;
            DAYS_BACK_MILLIS = DateTimeConstants.MILLIS_PER_DAY;
        } else {
            DAYS_FORWARD_MILLIS = 14400000;
            DAYS_BACK_MILLIS = DateTimeConstants.MILLIS_PER_HOUR;
        }
        this.mMaxHorizontalScroll = (int) (((long) ((DAYS_BACK_MILLIS + DAYS_FORWARD_MILLIS) - HOURS_IN_VIEWPORT_MILLIS)) / this.mMillisPerPixel);
    }

    private void calculateMaxVerticalScroll() {
        int topFrom = getTopFrom(this.epgData.getChannelCount() - 1) + this.mChannelLayoutHeight;
        this.mMaxVerticalScroll = topFrom < getHeight() ? 0 : topFrom - getHeight();
    }

    private int getXFrom(long j) {
        return ((int) ((j - this.mTimeOffset) / this.mMillisPerPixel)) + this.mChannelLayoutMargin + this.mChannelLayoutWidth + this.mChannelLayoutMargin;
    }

    private int getTopFrom(int i) {
        return (i * (this.mChannelLayoutHeight + this.mChannelLayoutMargin)) + this.mChannelLayoutMargin + this.mTimeBarHeight;
    }

    public long getTimeFrom(int i) {
        return (((long) i) * this.mMillisPerPixel) + this.mTimeOffset;
    }

    private long calculateMillisPerPixel() {
        return (long) (HOURS_IN_VIEWPORT_MILLIS / ((getResources().getDisplayMetrics().widthPixels - this.mChannelLayoutWidth) - this.mChannelLayoutMargin));
    }

    private int getXPositionStart() {
        return getXFrom((System.currentTimeMillis() + ((long) getTimeShiftMilliSeconds())) - ((long) (HOURS_IN_VIEWPORT_MILLIS / 2)));
    }

    private void resetBoundaries() {
        this.mMillisPerPixel = calculateMillisPerPixel();
        this.mTimeOffset = calculatedBaseLine();
        this.mTimeLowerBoundary = getTimeFrom(getWidth());
        this.mTimeUpperBoundary = getTimeFrom(getWidth());
    }

    public Rect calculateChannelsHitArea() {
        this.mMeasuringRect.top = this.mTimeBarHeight;
        int channelCount = this.epgData.getChannelCount() * (this.mChannelLayoutHeight + this.mChannelLayoutMargin);
        Rect rect = this.mMeasuringRect;
        if (channelCount >= getHeight()) {
            channelCount = getHeight();
        }
        rect.bottom = channelCount;
        this.mMeasuringRect.left = 0;
        this.mMeasuringRect.right = this.mChannelLayoutWidth;
        return this.mMeasuringRect;
    }

    public Rect calculateProgramsHitArea() {
        this.mMeasuringRect.top = this.mTimeBarHeight;
        int channelCount = this.epgData.getChannelCount() * (this.mChannelLayoutHeight + this.mChannelLayoutMargin);
        Rect rect = this.mMeasuringRect;
        if (channelCount >= getHeight()) {
            channelCount = getHeight();
        }
        rect.bottom = channelCount;
        this.mMeasuringRect.left = this.mChannelLayoutWidth;
        this.mMeasuringRect.right = getWidth();
        return this.mMeasuringRect;
    }

    public Rect calculateResetButtonHitArea() {
        this.mMeasuringRect.left = ((getScrollX() + getWidth()) - this.mResetButtonSize) - this.mResetButtonMargin;
        this.mMeasuringRect.top = ((getScrollY() + getHeight()) - this.mResetButtonSize) - this.mResetButtonMargin;
        this.mMeasuringRect.right = this.mMeasuringRect.left + this.mResetButtonSize;
        this.mMeasuringRect.bottom = this.mMeasuringRect.top + this.mResetButtonSize;
        return this.mMeasuringRect;
    }

    public int getChannelPosition(int i) {
        int i2 = ((i - this.mTimeBarHeight) + this.mChannelLayoutMargin) / (this.mChannelLayoutHeight + this.mChannelLayoutMargin);
        if (this.epgData.getChannelCount() == 0) {
            return -1;
        }
        return i2;
    }

    public int getProgramPosition(int i, long j) {
        List<EPGEvent> events = this.epgData.getEvents(i);
        if (events == null) {
            return -1;
        }
        for (int i2 = 0; i2 < events.size(); i2++) {
            EPGEvent ePGEvent = events.get(i2);
            if (ePGEvent.getStart() <= j && ePGEvent.getEnd() >= j) {
                return i2;
            }
        }
        return -1;
    }

    private EPGEvent getProgramAtTime(int i, long j) {
        List<EPGEvent> events = this.epgData.getEvents(i);
        if (events == null) {
            return null;
        }
        for (int i2 = 0; i2 < events.size(); i2++) {
            EPGEvent ePGEvent = events.get(i2);
            if (ePGEvent.getStart() <= j && ePGEvent.getEnd() >= j) {
                return ePGEvent;
            }
        }
        return null;
    }

    public void setEPGClickListener(EPGClickListener ePGClickListener) {
        this.mClickListener = ePGClickListener;
    }

    public void setEPGData(EPGData ePGData) {
        this.epgData = mergeEPGData(this.epgData, ePGData);
        if (this.epgData != null && this.epgData.getChannelCount() > 0) {
            this.epgDataFirstChannelID = this.epgData.getChannel(0);
            this.epgDataLastChannelID = this.epgData.getChannel(this.epgData.getChannelCount() - 1);
        }
    }

    private EPGData mergeEPGData(EPGData ePGData, EPGData ePGData2) {
        if (ePGData == null) {
            try {
                ePGData = new EPGDataImpl(Maps.newLinkedHashMap());
            } catch (Throwable th) {
                throw new RuntimeException("Could not merge EPG data: " + th.getClass().getSimpleName() + " " + th.getMessage(), th);
            }
        }
        if (ePGData2 != null) {
            for (int i = 0; i < ePGData2.getChannelCount(); i++) {
                EPGChannel channel = ePGData2.getChannel(i);
                EPGChannel orCreateChannel = ePGData.getOrCreateChannel(channel.getName(), channel.getImageURL(), channel.getStreamID(), channel.getNum(), channel.getEpgChannelID(), channel.getCatID(), channel.getVideoURL());
                for (int i2 = 0; i2 < channel.getEvents().size(); i2++) {
                    orCreateChannel.addEvent(channel.getEvents().get(i2));
                }
            }
        }
        return ePGData;
    }

    public void recalculateAndRedraw(EPGEvent ePGEvent, boolean z, RelativeLayout relativeLayout, EPG epg) {
        if (this.epgData != null && this.epgData.hasData()) {
            resetBoundaries();
            calculateMaxVerticalScroll();
            calculateMaxHorizontalScroll();
            int i = 0;
            Boolean bool = false;
            if (!this.EventSelected.booleanValue()) {
                if (ePGEvent != null) {
                    this.EventSelected = true;
                    selectEvent(ePGEvent, z);
                } else if (getProgramPosition(0, getTimeFrom(getXPositionStart() + (getWidth() / 2))) != -1) {
                    bool = true;
                    this.EventSelected = true;
                    selectEvent(this.epgData.getEvent(0, getProgramPosition(0, System.currentTimeMillis() + ((long) getTimeShiftMilliSeconds()))), z);
                } else if (this.epgData != null) {
                    while (true) {
                        if (i >= this.epgData.getChannelCount()) {
                            break;
                        }
                        List<EPGEvent> events = this.epgData.getChannel(i).getEvents();
                        if (events == null || events.size() == 0) {
                            i++;
                        } else {
                            bool = true;
                            int channelID = this.epgData.getChannel(i).getChannelID();
                            int programPosition = getProgramPosition(channelID, getTimeFrom(getXPositionStart() + (getWidth() / 2)));
                            if (programPosition != -1) {
                                this.EventSelected = true;
                                selectEvent(this.epgData.getEvent(channelID, programPosition), z);
                            }
                        }
                    }
                }
            }
            if (bool.equals(true) && relativeLayout != null) {
                relativeLayout.setFocusable(true);
                relativeLayout.setNextFocusDownId(R.id.epg);
            }
            redraw();
        }
    }

    public void redraw() {
        invalidate();
        requestLayout();
    }

    public void clearEPGImageCache() {
        this.mChannelImageCache.clear();
    }

    private void loadProgramDetails(EPGEvent ePGEvent) {
        this.loginPreferencesSharedPref_time_format = this.context1.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
        String string = this.loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "");
        this.programTimeFormatLong = new SimpleDateFormat(string);
        this.programTimeFormat = new SimpleDateFormat(string);
        this.currentEventTextView.setText(ePGEvent.getTitle());
        TextView textView = this.currentEventTimeTextView;
        textView.setText(this.programTimeFormatLong.format(Long.valueOf(ePGEvent.getStart())) + " - " + this.programTimeFormat.format(Long.valueOf(ePGEvent.getEnd())));
        this.currentEventDescriptionTextView.setText(ePGEvent.getDesc());
        playFirstTime(ePGEvent);
    }

    private void playFirstTime(EPGEvent ePGEvent) {
        int i;
        if (ePGEvent.getChannel() == null || ePGEvent.getChannel().getVideoURL() == null || ePGEvent.getChannel().getVideoURL().equals("")) {
            if (ePGEvent.getChannel() != null && ePGEvent.getChannel().getStreamID() != null) {
                try {
                    i = Integer.parseInt(ePGEvent.getChannel().getStreamID());
                } catch (NumberFormatException unused) {
                    i = -1;
                }
                if (getOpenedStreamID() == 0) {
                    destroyVideoPlayBack();
                    setOpenedStreamID(i);
                    if (this.mSettings.getPlayer() == 3) {
                        saveOpenedStream(i);
                        this.playingURL = Uri.parse(getVideoPathUrl() + i + getExtensionType());
                        this.retryCount = 0;
                        this.retrying = false;
                        initializePlayer();
                    } else if (this.mVideoView != null) {
                        NSTIJKPlayerSmallEPG nSTIJKPlayerSmallEPG = this.mVideoView;
                        nSTIJKPlayerSmallEPG.setVideoURI(Uri.parse(getVideoPathUrl() + i + getExtensionType()), true, "");
                        this.mVideoView.retryCount = 0;
                        this.mVideoView.retrying = false;
                        this.mVideoView.start();
                    }
                }
            }
        } else if (getOpenedVideoUrl() != null && getOpenedVideoUrl().equals("")) {
            try {
                destroyVideoPlayBack();
                setOpenedVideoUrl(ePGEvent.getChannel().getVideoURL());
                if (this.mSettings.getPlayer() == 3) {
                    saveOpenedM3U(ePGEvent.getChannel().getVideoURL());
                    this.playingURL = Uri.parse(ePGEvent.getChannel().getVideoURL());
                    this.retryCount = 0;
                    this.retrying = false;
                    initializePlayer();
                } else if (this.mVideoView != null) {
                    this.mVideoView.setVideoURI(Uri.parse(ePGEvent.getChannel().getVideoURL()), true, "");
                    this.mVideoView.retryCount = 0;
                    this.mVideoView.retrying = false;
                    this.mVideoView.start();
                }
            } catch (Exception unused2) {
            }
        }
    }

    private void saveOpenedStream(int i) {
        if (this.context1 != null) {
            this.loginPreferencesSharedPref_opened_video = this.context1.getSharedPreferences(AppConst.LOGIN_PREF_OPENED_VIDEO, 0);
            SharedPreferences.Editor edit = this.loginPreferencesSharedPref_opened_video.edit();
            edit.putInt(AppConst.LOGIN_PREF_OPENED_VIDEO_ID, i);
            edit.apply();
        }
    }

    private void saveOpenedM3U(String str) {
        if (this.context1 != null) {
            this.loginPreferencesSharedPref_opened_video = this.context1.getSharedPreferences(AppConst.LOGIN_PREF_OPENED_VIDEO, 0);
            SharedPreferences.Editor edit = this.loginPreferencesSharedPref_opened_video.edit();
            edit.putString("LOGIN_PREF_OPENED_VIDEO_URL_M3U", str);
            edit.apply();
        }
    }

    @Override // com.google.android.exoplayer2.PlaybackPreparer
    public void preparePlayback() {
        initializePlayer();
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x00ab  */
    public void initializePlayer() {
        //ToDo: initialized...
        DefaultDrmSessionManager<FrameworkMediaCrypto> defaultDrmSessionManager = null;
        TrackSelection.Factory factory;
        int i;
        if (this.videoStatus != null) {
            this.videoStatus.setVisibility(8);
        }
        if (this.player == null) {
            Intent intent = this.mActivity.getIntent();
            Uri[] uriArr = {intent.getData()};
            uriArr[0] = this.playingURL;
            if (!Util.checkCleartextTrafficPermitted(uriArr)) {
                Utils.showToast(this.mActivity, String.valueOf((int) R.string.error_cleartext_not_permitted));
                return;
            } else if (!Util.maybeRequestReadExternalStoragePermission(this.mActivity, uriArr)) {
                if (intent.hasExtra("drm_scheme") || intent.hasExtra(DRM_SCHEME_UUID_EXTRA)) {
                    String stringExtra = intent.getStringExtra("drm_license_url");
                    String[] stringArrayExtra = intent.getStringArrayExtra("drm_key_request_properties");
                    boolean booleanExtra = intent.getBooleanExtra("drm_multi_session", false);
                    if (Util.SDK_INT < 18) {
                        i = R.string.error_drm_not_supported;
                    } else {
                        try {
                            UUID drmUuid = Util.getDrmUuid(intent.getStringExtra(intent.hasExtra("drm_scheme") ? "drm_scheme" : DRM_SCHEME_UUID_EXTRA));
                            if (drmUuid == null) {
                                defaultDrmSessionManager = null;
                                i = R.string.error_drm_unsupported_scheme;
                            } else {
                                defaultDrmSessionManager = buildDrmSessionManagerV18(drmUuid, stringExtra, stringArrayExtra, booleanExtra);
                                i = R.string.error_drm_unknown;
                            }
                        } catch (UnsupportedDrmException e) {
                            i = e.reason == 1 ? R.string.error_drm_unsupported_scheme : R.string.error_drm_unknown;
                        }
                        if (defaultDrmSessionManager == null) {
                            Utils.showToast(this.mActivity, String.valueOf(i));
                            this.mActivity.finish();
                            return;
                        }
                    }
                    defaultDrmSessionManager = null;
                    if (defaultDrmSessionManager == null) {
                    }
                } else {
                    defaultDrmSessionManager = null;
                }
                String stringExtra2 = intent.getStringExtra("abr_algorithm");
                if (stringExtra2 == null || "default".equals(stringExtra2)) {
                    factory = new AdaptiveTrackSelection.Factory();
                } else if ("random".equals(stringExtra2)) {
                    factory = new RandomTrackSelection.Factory();
                } else {
                    Utils.showToast(this.mActivity, String.valueOf((int) R.string.error_unrecognized_abr_algorithm));
                    this.mActivity.finish();
                    return;
                }
                DefaultRenderersFactory defaultRenderersFactory = new DefaultRenderersFactory(this.mActivity, 2);
                this.trackSelector = new DefaultTrackSelector(factory);
                this.trackSelector.setParameters(this.trackSelectorParameters);
                this.lastSeenTrackGroupArray = null;
                this.player = ExoPlayerFactory.newSimpleInstance(this.mActivity, defaultRenderersFactory, this.trackSelector, defaultDrmSessionManager);
                this.player.addListener(new PlayerEventListener());
                this.player.setPlayWhenReady(true);
                this.player.addAnalyticsListener(new EventLogger(this.trackSelector));
                this.playerView.setPlayer(this.player);
                this.playerView.setPlaybackPreparer(this);
                MediaSource[] mediaSourceArr = new MediaSource[uriArr.length];
                for (int i2 = 0; i2 < uriArr.length; i2++) {
                    mediaSourceArr[i2] = buildMediaSource(uriArr[i2], "");
                }
                this.mediaSource = mediaSourceArr.length == 1 ? mediaSourceArr[0] : new ConcatenatingMediaSource(mediaSourceArr);
            } else {
                return;
            }
        }
        this.player.prepare(this.mediaSource, !(this.startWindow != -1), false);
    }

    public static boolean isBehindLiveWindow(ExoPlaybackException exoPlaybackException) {
        if (exoPlaybackException.type != 0) {
            return false;
        }
        for (Throwable sourceException = exoPlaybackException.getSourceException(); sourceException != null; sourceException = sourceException.getCause()) {
            if (sourceException instanceof BehindLiveWindowException) {
                return true;
            }
        }
        return false;
    }

    private class PlayerEventListener implements Player.EventListener {
        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onLoadingChanged(boolean z) {
            /*Player.EventListener.CC.$default$*/onLoadingChanged(z);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
           /* Player.EventListener.CC.$default$*/onPlaybackParametersChanged( playbackParameters);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onRepeatModeChanged(int i) {
           /* Player.EventListener.CC.$default$*/onRepeatModeChanged( i);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onSeekProcessed() {
           /* Player.EventListener.CC.$default$*/onSeekProcessed();
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onShuffleModeEnabledChanged(boolean z) {
           /* Player.EventListener.CC.$default$*/onShuffleModeEnabledChanged( z);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onTimelineChanged(Timeline timeline, @Nullable Object obj, int i) {
           /* Player.EventListener.CC.$default$*/onTimelineChanged( timeline, obj, i);
        }

        private PlayerEventListener() {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayerStateChanged(boolean z, int i) {
            if (i == 2) {
                EPG.this.app_video_loading.setVisibility(0);
            } else if (i == 4) {
                EPG.this.updateStartPosition();
                retrying();
            } else if (i == 3) {
                EPG.this.retryCount = 0;
                EPG.this.app_video_loading.setVisibility(8);
            }
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPositionDiscontinuity(int i) {
            if (EPG.this.player.getPlaybackError() != null) {
                EPG.this.updateStartPosition();
            }
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayerError(ExoPlaybackException exoPlaybackException) {
            if (EPG.this.stopRetry) {
                return;
            }
            if (EPG.isBehindLiveWindow(exoPlaybackException)) {
                EPG.this.clearStartPosition();
                EPG.this.initializePlayer();
            } else if (!exoPlaybackException.toString().contains("com.google.android.exoplayer2.ext.ffmpeg.FfmpegDecoderException")) {
                EPG.this.updateStartPosition();
                retrying();
            } else {
                Utils.showToast(EPG.this.mActivity, "Audio track issue found. Please change the audio track to none.");
                EPG.this.initializePlayer();
            }
        }

        private void showStatus(String str) {
            EPG.this.videoStatus.setVisibility(0);
            EPG.this.videoStatusText.setText(str);
        }

        private void retrying() {
            if (EPG.this.retryCount >= EPG.this.maxRetry) {
                showStatus(EPG.this.mActivity.getResources().getString(R.string.small_problem));
                EPG.this.destroyVideoPlayBack();
                EPG.this.retrying = false;
                EPG.this.app_video_loading.setVisibility(8);
            } else if (!EPG.this.stopRetry) {
                EPG.this.retrying = true;
                EPG.this.handler.postDelayed(new Runnable() {
                    /* class com.nst.yourname.view.utility.epg.EPG.PlayerEventListener.AnonymousClass1 */

                    public void run() {
                        if (!EPG.this.stopRetry) {
                            EPG.this.retryCount++;
                            Utils.showToast(EPG.this.mActivity, EPG.this.mActivity.getResources().getString(R.string.play_back_error) + " (" + EPG.this.retryCount + "/" + EPG.this.maxRetry + ")");
                            EPG.this.destroyVideoPlayBack();
                            EPG.this.initializePlayer();
                        }
                    }
                }, 3000);
            }
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            if (trackGroupArray != EPG.this.lastSeenTrackGroupArray) {
                MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo = EPG.this.trackSelector.getCurrentMappedTrackInfo();
                if (currentMappedTrackInfo != null) {
                    currentMappedTrackInfo.getTypeSupport(2);
                    currentMappedTrackInfo.getTypeSupport(1);
                }
                TrackGroupArray unused = EPG.this.lastSeenTrackGroupArray = trackGroupArray;
            }
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return buildMediaSource(uri, null);
    }

    private MediaSource buildMediaSource(Uri uri, @Nullable String str) {
        int inferContentType = Util.inferContentType(uri, str);
        switch (inferContentType) {
            case 0:
                return new DashMediaSource.Factory(this.dataSourceFactory).setManifestParser(new FilteringManifestParser(new DashManifestParser(), getOfflineStreamKeys(uri))).createMediaSource(uri);
            case 1:
                return new SsMediaSource.Factory(this.dataSourceFactory).setManifestParser(new FilteringManifestParser(new SsManifestParser(), getOfflineStreamKeys(uri))).createMediaSource(uri);
            case 2:
                return new HlsMediaSource.Factory(this.dataSourceFactory).setPlaylistParserFactory(new DefaultHlsPlaylistParserFactory(getOfflineStreamKeys(uri))).createMediaSource(uri);
            case 3:
                return new ExtractorMediaSource.Factory(this.dataSourceFactory).createMediaSource(uri);
            default:
                throw new IllegalStateException("Unsupported type: " + inferContentType);
        }
    }

    private DefaultDrmSessionManager<FrameworkMediaCrypto> buildDrmSessionManagerV18(UUID uuid, String str, String[] strArr, boolean z) throws UnsupportedDrmException {
        HttpMediaDrmCallback httpMediaDrmCallback = new HttpMediaDrmCallback(str, DemoApplication.getInstance(this.context1).buildHttpDataSourceFactory());
        if (strArr != null) {
            for (int i = 0; i < strArr.length - 1; i += 2) {
                httpMediaDrmCallback.setKeyRequestProperty(strArr[i], strArr[i + 1]);
            }
        }
        releaseMediaDrm();
        this.mediaDrm = FrameworkMediaDrm.newInstance(uuid);
        return new DefaultDrmSessionManager<>(uuid, this.mediaDrm, httpMediaDrmCallback, null, z);
    }

    private List<StreamKey> getOfflineStreamKeys(Uri uri) {
        return DemoApplication.getInstance(this.context1).getDownloadTracker().getOfflineStreamKeys(uri);
    }

    private void releaseMediaDrm() {
        if (this.mediaDrm != null) {
            this.mediaDrm.release();
            this.mediaDrm = null;
        }
    }

    private void updateTrackSelectorParameters() {
        if (this.trackSelector != null) {
            this.trackSelectorParameters = this.trackSelector.getParameters();
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.max(long, long):long}
     arg types: [int, long]
     candidates:
      ClspMth{java.lang.Math.max(double, double):double}
      ClspMth{java.lang.Math.max(int, int):int}
      ClspMth{java.lang.Math.max(float, float):float}
      ClspMth{java.lang.Math.max(long, long):long} */
    public void updateStartPosition() {
        if (this.player != null) {
            this.startAutoPlay = this.player.getPlayWhenReady();
            this.startWindow = this.player.getCurrentWindowIndex();
            this.startPosition = Math.max(0L, this.player.getContentPosition());
        }
    }

    public void clearStartPosition() {
        this.startAutoPlay = true;
        this.startWindow = -1;
        this.startPosition = C.TIME_UNSET;
    }

    public void destroyVideoPlayBack() {
        if (this.mSettings.getPlayer() == 3) {
            if (this.player != null) {
                updateTrackSelectorParameters();
                updateStartPosition();
                this.player.release();
                this.player = null;
                this.mediaSource = null;
                this.trackSelector = null;
            }
            releaseMediaDrm();
            return;
        }
        try {
            if (this.mVideoView != null) {
                if (!this.mVideoView.isBackgroundPlayEnabled()) {
                    this.mVideoView.stopPlayback();
                    this.mVideoView.release(true);
                    this.mVideoView.stopBackgroundPlay();
                } else {
                    this.mVideoView.enterBackground();
                }
                IjkMediaPlayer.native_profileEnd();
            }
        } catch (Exception unused) {
        }
    }

    public void setLoader(ProgressBar progressBar) {
        this.app_video_loading = progressBar;
    }

    public void setVideoStatus(LinearLayout linearLayout) {
        this.videoStatus = linearLayout;
    }

    public void setVideoStatusText(TextView textView) {
        this.videoStatusText = textView;
    }

    public void setVideoPathUrl(String str) {
        this.VideoPathUrl = str;
    }

    public void selectEvent(EPGEvent ePGEvent, boolean z) {
        if (this.selectedEvent != null) {
            this.selectedEvent.selected = false;
        }
        ePGEvent.selected = true;
        this.selectedEvent = ePGEvent;
        optimizeVisibility(ePGEvent, z, "vertical");
        loadProgramDetails(ePGEvent);
        redraw();
    }

    public void setCurrentEventTextView(TextView textView) {
        this.currentEventTextView = textView;
    }

    public void setCurrentEventDescriptionTextView(TextView textView) {
        this.currentEventDescriptionTextView = textView;
    }

    public void setCurrentEventTimeTextView(TextView textView) {
        this.currentEventTimeTextView = textView;
    }

    public void setOrientation(int i) {
        this.orientation = i;
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
    }

    public void setVideoView(SurfaceView surfaceView) {
        this.videoView = surfaceView;
    }

    public void setOpenedStreamID(int i) {
        this.opened_stream_id = i;
    }

    public void setOpenedVideoUrl(String str) {
        this.opened_video_url = str;
    }

    public void setExtensionType(String str) {
        this.extensionType = str;
    }

    public int getOpenedStreamID() {
        return this.opened_stream_id;
    }

    public String getOpenedVideoUrl() {
        return this.opened_video_url;
    }

    public EPGEvent getSelectedEvent() {
        return this.selectedEvent;
    }

    public String getExtensionType() {
        return this.extensionType;
    }

    public String getVideoPathUrl() {
        return this.VideoPathUrl;
    }

    public void hideProgressDialogBox() {
        new OnGestureListener().hideProgressDialogBox();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        int i2;
        this.mTimeLowerBoundary = getTimeFrom(getScrollX());
        this.mTimeUpperBoundary = getTimeFrom(getScrollX() + getWidth());
        if (!(keyEvent.getKeyCode() == 4 || this.selectedEvent == null)) {
            if (keyEvent.getKeyCode() == 22) {
                if (this.selectedEvent.getNextEvent() != null) {
                    this.selectedEvent.selected = false;
                    this.selectedEvent = this.selectedEvent.getNextEvent();
                    this.selectedEvent.selected = true;
                    optimizeVisibility(this.selectedEvent, true, "horizontal");
                }
            } else if (keyEvent.getKeyCode() == 21) {
                if (this.selectedEvent.getPreviousEvent() != null) {
                    this.selectedEvent.selected = false;
                    this.selectedEvent = this.selectedEvent.getPreviousEvent();
                    this.selectedEvent.selected = true;
                    optimizeVisibility(this.selectedEvent, true, "horizontal");
                }
            } else if (keyEvent.getKeyCode() == 19) {
                if (this.selectedEvent.getChannel().getPreviousChannel() == null || this.selectedEvent.getChannel().getPreviousChannel().getChannelID() == this.epgDataLastChannelID.getChannelID()) {
                    super.requestFocus();
                    super.requestFocusFromTouch();
                } else {
                    long max = (Math.max(this.mTimeLowerBoundary, this.selectedEvent.getStart()) + Math.min(this.mTimeUpperBoundary, this.selectedEvent.getEnd())) / 2;
                    EPGEvent programAtTime = getProgramAtTime(this.selectedEvent.getChannel().getPreviousChannel().getChannelID(), max);
                    if (programAtTime != null) {
                        this.selectedEvent.selected = false;
                        this.selectedEvent = programAtTime;
                        this.selectedEvent.selected = true;
                    } else {
                        checkPreviousChannel(this.selectedEvent.getChannel().getPreviousChannel().getChannelID(), max);
                    }
                    optimizeVisibility(this.selectedEvent, true, "vertical");
                }
            } else if (keyEvent.getKeyCode() == 20) {
                if (this.selectedEvent.getChannel().getNextChannel() != null) {
                    long max2 = (Math.max(this.mTimeLowerBoundary, this.selectedEvent.getStart()) + Math.min(this.mTimeUpperBoundary, this.selectedEvent.getEnd())) / 2;
                    EPGEvent programAtTime2 = getProgramAtTime(this.selectedEvent.getChannel().getNextChannel().getChannelID(), max2);
                    if (programAtTime2 == null) {
                        if (this.loadEPGDataAsyncTaskRunning == 1) {
                            new OnGestureListener().showProgressDialogBox();
                        }
                        return true;
                    }
                    if (programAtTime2 != null) {
                        this.selectedEvent.selected = false;
                        this.selectedEvent = programAtTime2;
                        this.selectedEvent.selected = true;
                    } else {
                        checkNextChannel(this.selectedEvent.getChannel().getNextChannel().getChannelID(), max2);
                    }
                    optimizeVisibility(this.selectedEvent, true, "vertical");
                }
            } else if (keyEvent.getKeyCode() == 103 || keyEvent.getKeyCode() == 90) {
                gotoNextDay(this.selectedEvent);
            } else if (keyEvent.getKeyCode() == 102 || keyEvent.getKeyCode() == 89) {
                gotoPreviousDay(this.selectedEvent);
            } else if (keyEvent.getKeyCode() == 66 || keyEvent.getKeyCode() == 23) {
                Context context = getContext();
                getContext();
                this.loginPreferencesSharedPref = context.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
                String string = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER, "");
                String openedCategoryID = this.selectedEvent.getChannel().getOpenedCategoryID();
                String catID = this.selectedEvent.getChannel().getCatID();
                try {
                    i2 = Integer.parseInt(this.selectedEvent.getChannel().getStreamID());
                } catch (NumberFormatException unused) {
                    i2 = -1;
                }
                EPGPlayPopUp(getContext(), string, i2, this.selectedEvent.getChannel().getNum(), this.selectedEvent.getChannel().getName(), this.selectedEvent.getChannel().getEpgChannelID(), this.selectedEvent.getChannel().getImageURL(), openedCategoryID, this.selectedEvent.getChannel().getVideoURL(), catID);
            }
            loadProgramDetails(this.selectedEvent);
            redraw();
        }
        return true;
    }

    public void EPGPlayPopUp(final Context context, final String str, final int i, final String str2, final String str3, final String str4, final String str5, final String str6, final String str7, final String str8) {
        Context context2 = context;
        try {
            this.database = new DatabaseHandler(context2);
            this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
            View inflate = ((LayoutInflater) context2.getSystemService("layout_inflater")).inflate((int) R.layout.epg_popup_layout, (RelativeLayout) findViewById(R.id.rl_epg_layout));
            this.changeSortPopUp = new PopupWindow(context2);
            this.changeSortPopUp.setContentView(inflate);
            this.changeSortPopUp.setWidth(-1);
            this.changeSortPopUp.setHeight(-1);
            this.changeSortPopUp.setFocusable(true);
            this.changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());
            this.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
            RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.ll_play);
            RelativeLayout relativeLayout2 = (RelativeLayout) inflate.findViewById(R.id.ll_move_to_next_cat);
            RelativeLayout relativeLayout3 = (RelativeLayout) inflate.findViewById(R.id.ll_move_to_prev_cat);
            RelativeLayout relativeLayout4 = (RelativeLayout) inflate.findViewById(R.id.ll_close);
            if (SharepreferenceDBHandler.getCurrentAPPType(context).equals(AppConst.TYPE_M3U)) {
                ArrayList<FavouriteM3UModel> checkFavourite = this.liveStreamDBHandler.checkFavourite(str7, SharepreferenceDBHandler.getUserID(context));
                if (checkFavourite == null || checkFavourite.size() <= 0) {
                    relativeLayout2.setVisibility(0);
                } else {
                    relativeLayout3.setVisibility(0);
                }
            } else {
                ArrayList<FavouriteDBModel> checkFavourite2 = this.database.checkFavourite(i, str8, "live", SharepreferenceDBHandler.getUserID(context));
                if (checkFavourite2 == null || checkFavourite2.size() <= 0) {
                    relativeLayout2.setVisibility(0);
                } else {
                    relativeLayout3.setVisibility(0);
                }
            }
            relativeLayout.setOnClickListener(new OnClickListener() {
                /* class com.nst.yourname.view.utility.epg.EPG.AnonymousClass2 */

                public void onClick(View view) {
                    EPG.this.changeSortPopUp.dismiss();
                    if (EPG.this.rq.booleanValue()) {
                        Utils.playWithVlcPlayerEPG(EPG.this.getContext(), str, i, "live", str2, str3, str4, str5, str6, str7);
                    }
                }
            });
            relativeLayout2.setOnClickListener(new OnClickListener() {
                /* class com.nst.yourname.view.utility.epg.EPG.AnonymousClass3 */

                public void onClick(View view) {
                    if (SharepreferenceDBHandler.getCurrentAPPType(context).equals(AppConst.TYPE_M3U)) {
                        EPG.this.addToFavouriteM3U(context, str8, str7, str3);
                    } else {
                        EPG.this.addToFavourite(context, str8, i, str3, str2);
                    }
                    EPG.this.changeSortPopUp.dismiss();
                }
            });
            relativeLayout3.setOnClickListener(new OnClickListener() {
                /* class com.nst.yourname.view.utility.epg.EPG.AnonymousClass4 */

                public void onClick(View view) {
                    if (SharepreferenceDBHandler.getCurrentAPPType(context).equals(AppConst.TYPE_M3U)) {
                        EPG.this.removeFromFavouriteM3U(context, str7, str3);
                    } else {
                        EPG.this.removeFromFavourite(context, str8, i, str3);
                    }
                    EPG.this.changeSortPopUp.dismiss();
                }
            });
            relativeLayout4.setOnClickListener(new OnClickListener() {
                /* class com.nst.yourname.view.utility.epg.EPG.AnonymousClass5 */

                public void onClick(View view) {
                    EPG.this.changeSortPopUp.dismiss();
                }
            });
        } catch (Exception unused) {
        }
    }

    public void addToFavourite(Context context, String str, int i, String str2, String str3) {
        if (context != null && this.database != null) {
            FavouriteDBModel favouriteDBModel = new FavouriteDBModel();
            favouriteDBModel.setCategoryID(str);
            favouriteDBModel.setStreamID(i);
            favouriteDBModel.setNum(str3);
            favouriteDBModel.setName(str2);
            favouriteDBModel.setUserID(SharepreferenceDBHandler.getUserID(context));
            this.database.addToFavourite(favouriteDBModel, "live");
            Utils.showToast(context, str2 + context.getResources().getString(R.string.add_fav));
        }
    }

    public void addToFavouriteM3U(Context context, String str, String str2, String str3) {
        if (context != null && this.liveStreamDBHandler != null) {
            FavouriteM3UModel favouriteM3UModel = new FavouriteM3UModel();
            favouriteM3UModel.setUrl(str2);
            favouriteM3UModel.setUserID(SharepreferenceDBHandler.getUserID(context));
            favouriteM3UModel.setName(str3);
            favouriteM3UModel.setCategoryID(str);
            this.liveStreamDBHandler.addToFavourite(favouriteM3UModel);
            Utils.showToast(context, str3 + context.getResources().getString(R.string.add_fav));
        }
    }

    public void removeFromFavourite(Context context, String str, int i, String str2) {
        if (context != null && this.database != null) {
            this.database.deleteFavourite(i, str, "live", str2, SharepreferenceDBHandler.getUserID(context));
            Utils.showToast(context, str2 + context.getResources().getString(R.string.rem_fav));
        }
    }

    public void removeFromFavouriteM3U(Context context, String str, String str2) {
        if (context != null && this.liveStreamDBHandler != null) {
            this.liveStreamDBHandler.deleteFavourite(str, SharepreferenceDBHandler.getUserID(context));
            Utils.showToast(context, str2 + context.getResources().getString(R.string.rem_fav));
        }
    }

    private void checkPreviousChannel(int i, long j) {
        int i2 = i - 1;
        if (i2 >= 0) {
            EPGEvent programAtTime = getProgramAtTime(i2, j);
            if (programAtTime != null) {
                this.selectedEvent.selected = false;
                this.selectedEvent = programAtTime;
                this.selectedEvent.selected = true;
                return;
            }
            checkPreviousChannel(i2, j);
            return;
        }
        super.requestFocus();
    }

    private void checkNextChannel(int i, long j) {
        int i2 = i + 1;
        EPGChannel ePGChannel = this.epgDataLastChannelID;
        if (i == ePGChannel.getChannelID()) {
            i2 = 0;
        }
        if (i2 < 0 || i2 > ePGChannel.getChannelID()) {
            super.requestFocus();
            return;
        }
        EPGEvent programAtTime = getProgramAtTime(i2, j);
        if (programAtTime != null) {
            this.selectedEvent.selected = false;
            this.selectedEvent = programAtTime;
            this.selectedEvent.selected = true;
            return;
        }
        checkNextChannel(i2, j);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0090  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00c3  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00fe  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0055  */
    public void optimizeVisibility(EPGEvent ePGEvent, boolean z, String str) {
        int i;
        int i2;
        int i3;
        String str2 = str;
        int scrollY = getScrollY();
        int height = getHeight() + scrollY;
        int channelID = this.mTimeBarHeight + (ePGEvent.getChannel().getChannelID() * (this.mChannelLayoutHeight + this.mChannelLayoutMargin));
        int i4 = this.mChannelLayoutHeight + channelID;
        if (channelID < scrollY) {
            i3 = (channelID - scrollY) - this.mTimeBarHeight;
        } else if (i4 > height) {
            i3 = i4 - height;
        } else {
            i = 0;
            this.mTimeLowerBoundary = getTimeFrom(getScrollX());
            this.mTimeUpperBoundary = getTimeFrom(getScrollX() + getProgramAreaWidth());
            int round = ePGEvent.getEnd() <= this.mTimeUpperBoundary ? Math.round((float) ((((this.mTimeUpperBoundary - ePGEvent.getEnd()) - this.mMargin) * -1) / this.mMillisPerPixel)) : 0;
            this.mTimeLowerBoundary = getTimeFrom(getScrollX());
            this.mTimeUpperBoundary = getTimeFrom(getScrollX() + getWidth());
            if (ePGEvent.getStart() < this.mTimeLowerBoundary) {
                round = Math.round((float) (((this.selectedEvent.getStart() - this.mTimeLowerBoundary) - this.mMargin) / this.mMillisPerPixel));
            }
            i2 = round;
            if (i2 == 0 || i != 0) {
                this.loginPreferencesSharedPref_auto_start = this.context1.getSharedPreferences(AppConst.LOGIN_PREF_AUTO_START, 0);
                this.flag_full_epg = this.loginPreferencesSharedPref_auto_start.getBoolean(AppConst.LOGIN_PREF_FULL_EPG, false);
                if (!this.flag_full_epg) {
                    if (str2.equals("vertical")) {
                        this.mScroller.startScroll(getScrollX(), getScrollY(), 0, i, z ? 100 : 0);
                    }
                    if (str2.equals("horizontal")) {
                        this.mScroller.startScroll(getScrollX(), getScrollY(), i2, 0, z ? 100 : 0);
                        return;
                    }
                    return;
                }
                if (str2.equals("vertical")) {
                    this.mScroller.startScroll(getScrollX(), getScrollY(), 0, i, z ? 100 : 0);
                }
                if (str2.equals("horizontal")) {
                    this.mScroller.startScroll(getScrollX(), getScrollY(), i2, 0, z ? 100 : 0);
                    return;
                }
                return;
            }
            return;
        }
        i = i3;
        this.mTimeLowerBoundary = getTimeFrom(getScrollX());
        this.mTimeUpperBoundary = getTimeFrom(getScrollX() + getProgramAreaWidth());
        if (ePGEvent.getEnd() <= this.mTimeUpperBoundary) {
        }
        this.mTimeLowerBoundary = getTimeFrom(getScrollX());
        this.mTimeUpperBoundary = getTimeFrom(getScrollX() + getWidth());
        if (ePGEvent.getStart() < this.mTimeLowerBoundary) {
        }
        //i2 = round;
        /*if (i2 == 0) {
        }*/
        this.loginPreferencesSharedPref_auto_start = this.context1.getSharedPreferences(AppConst.LOGIN_PREF_AUTO_START, 0);
        this.flag_full_epg = this.loginPreferencesSharedPref_auto_start.getBoolean(AppConst.LOGIN_PREF_FULL_EPG, false);
        if (!this.flag_full_epg) {
        }
    }

    public void resetButtonClicked() {
        long currentTimeMillis = System.currentTimeMillis() + ((long) getTimeShiftMilliSeconds());
        if ((getResources().getConfiguration().screenLayout & 15) == 3) {
            scrollTo(((getXFrom(currentTimeMillis) - this.mChannelLayoutWidth) + this.mChannelLayoutMargin) - 200, getScrollY());
        } else {
            scrollTo(((getXFrom(currentTimeMillis) - this.mChannelLayoutWidth) + this.mChannelLayoutMargin) - 100, getScrollY());
        }
    }

    public void setPlayer(PlayerView playerView2) {
        this.dataSourceFactory = DemoApplication.getInstance(this.context1).buildDataSourceFactory();
        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }
        this.playerView = playerView2;
        if (this.playerView != null) {
            this.playerView.requestFocus();
        }
        this.trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();
        clearStartPosition();
    }

    private class OnGestureListener extends GestureDetector.SimpleOnGestureListener {
        private OnGestureListener() {
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            int access$2700;
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            int scrollX = EPG.this.getScrollX() + x;
            int scrollY = EPG.this.getScrollY() + y;
            int access$2000 = EPG.this.getChannelPosition(scrollY);
            if (access$2000 == -1 || EPG.this.mClickListener == null) {
                return true;
            }
            if (EPG.this.calculateResetButtonHitArea().contains(scrollX, scrollY)) {
                EPG.this.mClickListener.onResetButtonClicked();
                return true;
            } else if (EPG.this.calculateChannelsHitArea().contains(x, y)) {
                EPG.this.mClickListener.onResetButtonClicked();
                EPGEvent ePGEvent = null;
                Iterator<EPGEvent> it = EPG.this.epgData.getChannel(access$2000).getEvents().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    EPGEvent next = it.next();
                    if (next.isCurrent(EPG.this.getTimeShiftMilliSeconds())) {
                        ePGEvent = next;
                        break;
                    }
                }
                if (ePGEvent != null) {
                    int access$27002 = EPG.this.getProgramPosition(access$2000, EPG.this.getTimeFrom((EPG.this.getScrollX() + x) - EPG.this.calculateProgramsHitArea().left));
                    if (access$27002 == -1) {
                        return true;
                    }
                    EPG.this.mClickListener.onEventClicked(access$2000, access$27002, ePGEvent);
                    return true;
                }
                EPG.this.mClickListener.onChannelClicked(access$2000, EPG.this.epgData.getChannel(access$2000));
                return true;
            } else if (!EPG.this.calculateProgramsHitArea().contains(x, y) || (access$2700 = EPG.this.getProgramPosition(access$2000, EPG.this.getTimeFrom((EPG.this.getScrollX() + x) - EPG.this.calculateProgramsHitArea().left))) == -1) {
                return true;
            } else {
                EPG.this.mClickListener.onEventClicked(access$2000, access$2700, EPG.this.epgData.getEvent(access$2000, access$2700));
                return true;
            }
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            int i = (int) f;
            int i2 = (int) f2;
            int scrollX = EPG.this.getScrollX();
            int scrollY = EPG.this.getScrollY();
            if (scrollX + i < 0) {
                i = 0 - scrollX;
            }
            if (scrollY + i2 < 0) {
                i2 = 0 - scrollY;
            }
            if (scrollX + i > EPG.this.mMaxHorizontalScroll) {
                i = EPG.this.mMaxHorizontalScroll - scrollX;
            }
            if (scrollY + i2 > EPG.this.mMaxVerticalScroll) {
                i2 = EPG.this.mMaxVerticalScroll - scrollY;
            }
            EPG.this.scrollBy(i, i2);
            return true;
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            EPG.this.mScroller.fling(EPG.this.getScrollX(), EPG.this.getScrollY(), -((int) f), -((int) f2), 0, EPG.this.mMaxHorizontalScroll, 0, EPG.this.mMaxVerticalScroll);
            EPG.this.redraw();
            if (EPG.this.mScroller.getFinalY() == EPG.this.mMaxVerticalScroll && EPG.this.loadEPGDataAsyncTaskRunning == 1) {
                showProgressDialogBox();
            }
            return true;
        }

        public boolean onDown(MotionEvent motionEvent) {
            if (EPG.this.mScroller.isFinished()) {
                return true;
            }
            EPG.this.mScroller.forceFinished(true);
            return true;
        }

        public void showProgressDialogBox() {
            if (EPG.this.mActivity == null) {
                return;
            }
            if (EPG.this.progressDialog == null || !EPG.this.progressDialog.isShowing()) {
                ProgressDialog unused = EPG.this.progressDialog = new ProgressDialog(EPG.this.mActivity);
                EPG.this.progressDialog.setMessage(EPG.this.mActivity.getResources().getString(R.string.please_wait));
                EPG.this.progressDialog.setCanceledOnTouchOutside(false);
                EPG.this.progressDialog.setCancelable(false);
                EPG.this.progressDialog.setProgressStyle(0);
                EPG.this.progressDialog.show();
            }
        }

        public void hideProgressDialogBox() {
            if (EPG.this.mActivity != null && EPG.this.progressDialog != null) {
                EPG.this.progressDialog.dismiss();
            }
        }
    }
}
