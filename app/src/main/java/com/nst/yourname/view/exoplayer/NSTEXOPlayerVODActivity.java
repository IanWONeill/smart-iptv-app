package com.nst.yourname.view.exoplayer;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
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
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.TrackSelectionView;
//import com.google.android.exoplayer2.ui.model.PlayerSelectedSinglton;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.EpisodesUsingSinglton;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.MoviesUsingSinglton;
import com.nst.yourname.model.PlayerSelectedSinglton;
import com.nst.yourname.model.callback.GetEpisdoeDetailsCallback;
import com.nst.yourname.model.callback.VodInfoCallback;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.RecentWatchDBHandler;
import com.nst.yourname.model.database.SeriesRecentWatchDatabase;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.model.pojo.PanelAvailableChannelsPojo;
import com.nst.yourname.presenter.VodPresenter;
import com.nst.yourname.view.activity.SeriesRecentClass;
import com.nst.yourname.view.ijkplayer.application.Settings;
import com.nst.yourname.view.ijkplayer.widget.media.FileMediaDataSource;
import com.nst.yourname.view.ijkplayer.widget.media.InfoHudViewHolder;
import com.nst.yourname.view.ijkplayer.widget.media.MeasureHelper;
import com.nst.yourname.view.ijkplayer.widget.media.MediaPlayerCompat;
import com.nst.yourname.view.ijkplayer.widget.media.SurfaceRenderView;
import com.nst.yourname.view.ijkplayer.widget.preference.IjkListPreference;
import com.nst.yourname.view.inbuiltsmartersplayer.VideoInfo;
import com.nst.yourname.view.inbuiltsmartersplayer.trackselector.TableLayoutBinder;
import com.nst.yourname.view.interfaces.VodInterface;
import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import mbanje.kurt.fabbutton.FabButton;
import org.joda.time.DateTimeConstants;

@SuppressWarnings("ALL")
public class NSTEXOPlayerVODActivity extends AppCompatActivity implements View.OnClickListener, PlaybackPreparer, PlayerControlView.VisibilityListener, VodInterface {
    static final boolean $assertionsDisabled = false;
    public static final String ABR_ALGORITHM_DEFAULT = "default";
    public static final String ABR_ALGORITHM_EXTRA = "abr_algorithm";
    public static final String ABR_ALGORITHM_RANDOM = "random";
    public static final String ACTION_VIEW = "com.google.android.exoplayer.demo.action.VIEW";
    public static final String ACTION_VIEW_LIST = "com.google.android.exoplayer.demo.action.VIEW_LIST";
    public static final String AD_TAG_URI_EXTRA = "ad_tag_uri";
    private static final CookieManager DEFAULT_COOKIE_MANAGER = new CookieManager();
    public static final String DRM_KEY_REQUEST_PROPERTIES_EXTRA = "drm_key_request_properties";
    public static final String DRM_LICENSE_URL_EXTRA = "drm_license_url";
    public static final String DRM_MULTI_SESSION_EXTRA = "drm_multi_session";
    public static final String DRM_SCHEME_EXTRA = "drm_scheme";
    private static final String DRM_SCHEME_UUID_EXTRA = "drm_scheme_uuid";
    public static final String EXTENSION_EXTRA = "extension";
    public static final String EXTENSION_LIST_EXTRA = "extension_list";
    private static final String KEY_AUTO_PLAY = "auto_play";
    private static final String KEY_POSITION = "position";
    private static final String KEY_TRACK_SELECTOR_PARAMETERS = "track_selector_parameters";
    private static final String KEY_WINDOW = "window";
    public static final String PREFER_EXTENSION_DECODERS_EXTRA = "prefer_extension_decoders";
    public static final String SPHERICAL_STEREO_MODE_EXTRA = "spherical_stereo_mode";
    public static final String SPHERICAL_STEREO_MODE_LEFT_RIGHT = "left_right";
    public static final String SPHERICAL_STEREO_MODE_MONO = "mono";
    public static final String SPHERICAL_STEREO_MODE_TOP_BOTTOM = "top_bottom";
    public static final String URI_LIST_EXTRA = "uri_list";
    private static boolean onCreate = true;
    private static boolean replayVideo = true;
    private static final int[] s_allAspectRatio = {0, 1, 2, 3, 4};
    private static String uk;
    private static String una;
    public Activity activity;
    AlertDialog alertDialog;
    public String allowedFormat;
    LinearLayout app_video_status;
    TextView app_video_status_text;
    public RelativeLayout audio_tracks;
    public Handler autoPlayHandler;
    private PopupWindow autoPlayNextPopup;
    public Runnable autoPlayRunnable;
    ProgressBar bufferingloader;
    public Button cancel_autoplay;
    private PopupWindow changeSortPopUp;
    int check = 0;
    public Button closedBT;
    String container_extension = "";
    public Context context;
    public String currentAPPType;
    public int currentProgramStreamID;
    public int currentProgress = 0;
    private int currentWindowIndex = 0;
    ArrayList<File> dataItems = new ArrayList<>();
    private DataSource.Factory dataSourceFactory;
    private LinearLayout debugRootView;
    DateFormat df;
    Date dt;
    String elv;
    public String episode_id = "";
    boolean externalPlayerSelected = false;
    public FabButton fabButton;
    String fmw;
    SimpleDateFormat fr;
    private Handler handlerAspectRatio;
    Handler handlerOLD;
    Handler handlerSeekbar;
    Handler handlerSeekbarForwardRewind;
    public ImageView iv_cancel;
    public TrackGroupArray lastSeenTrackGroupArray;
    public ArrayList<LiveStreamsDBModel> liveListDetailAvailableChannels;
    public List<GetEpisdoeDetailsCallback> liveListDetailAvailableSeries;
    private ArrayList<File> liveListRecording;
    LiveStreamDBHandler liveStreamDBHandler;
    public RelativeLayout ll_close;
    LinearLayout ll_seek_overlay;
    public SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences loginPreferencesSharedPref;
    private SharedPreferences loginPreferencesSharedPref_allowed_format;
    private SharedPreferences loginPreferencesSharedPref_currently_playing_video;
    private SharedPreferences loginPreferencesSharedPref_currently_playing_video_position;
    private SharedPreferences loginPreferencesUserAgent;
    private SharedPreferences loginPreferences_audio_selected;
    private SharedPreferences loginPreferences_seek_time;
    private SharedPreferences loginPreferences_subtitle_selected;
    private SharedPreferences loginPreferences_video_selected;
    private SharedPreferences.Editor loginPrefsEditor;
    private SharedPreferences.Editor loginPrefsEditorAudio;
    private SharedPreferences.Editor loginPrefsEditorPosition;
    private SharedPreferences.Editor loginPrefsEditorSeekTime;
    private SharedPreferences.Editor loginPrefsEditorSubtitle;
    private SharedPreferences.Editor loginPrefsEditorVideo;
    public String m3uVideoURL;
    Activity mActivity;
    private int mCurrentAspectRatio = s_allAspectRatio[0];
    private int mCurrentAspectRatioIndex = 0;
    public String mFilePath;
    private String mFilePath1 = "";
    private Settings mSettings;
    public int maxRetry = 5;
    private FrameworkMediaDrm mediaDrm;
    private MediaSource mediaSource;
    private TextView movieIDTV;
    public Button negativeButton;
    RelativeLayout next_background;
    String num = "";
    int opened_stream_id = 0;
    public SimpleExoPlayer player;
    public PlayerView playerView;
    public Uri playingURL;
    public RecentWatchDBHandler recentWatchDBHandler;
    private SeriesRecentWatchDatabase recentWatchDBHandler1;
    public boolean recordingRelease = false;
    public int retryCount = 0;
    public boolean retrying = false;
    public RelativeLayout rl_epg_layout;
    private RelativeLayout rl_next_episode;
    public Boolean rq = true;
    public Button savePasswordBT;
    public int seekBarMilliseconds = 0;
    public SeriesRecentClass seriesRecentClass;
    SharedPreferences.Editor sharedPrefEditor;
    SharedPreferences sharedPreferences;
    private boolean startAutoPlay;
    private long startPosition;
    private int startWindow;
    public boolean stopRetry = false;
    public RelativeLayout subtitle_tracks;
    public DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    TextView tv_seek_overlay;
    String type = "";
    String typeofStream = "";
    String ukd;
    String unad;
    String videoTitle = "";
    public int video_num = 0;
    public RelativeLayout video_tracks;
    public View vlc_exo_subtitle;
    public View vlcaspectRatio;
    public View vlcffwdButton;
    public View vlcnextButton;
    public View vlcnrewButton;
    public View vlcprevButton;
    VodPresenter vodPresenter;

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void atStart() {
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFailed(String str) {
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFinish() {
    }

    @Override // com.nst.yourname.view.interfaces.VodInterface
    public void vodInfoError(String str) {
    }

    static {
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onCreate(@Nullable Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        this.context = this;
        this.mActivity = this;
        PlayerSelectedSinglton.getInstance().setPlayerType(AppConst.VOD);
        PlayerSelectedSinglton.getInstance().setPlayedWithExternalPlayer(false);
        setContentView((int) R.layout.nst_exo_player_vod);
        this.dataSourceFactory = buildDataSourceFactory();
        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }
        findViewById(R.id.root).setOnClickListener(this);
        this.debugRootView = (LinearLayout) findViewById(R.id.controls_root);
        this.debugRootView.setVisibility(8);
        this.playerView = (PlayerView) findViewById(R.id.player_view);
        this.playerView.setControllerVisibilityListener(this);
        this.playerView.requestFocus();
        this.vodPresenter = new VodPresenter(this, this.context);
        if (bundle != null) {
            this.trackSelectorParameters = (DefaultTrackSelector.Parameters) bundle.getParcelable(KEY_TRACK_SELECTOR_PARAMETERS);
            this.startAutoPlay = bundle.getBoolean(KEY_AUTO_PLAY);
            this.startWindow = bundle.getInt(KEY_WINDOW);
            this.startPosition = bundle.getLong(KEY_POSITION);
        } else {
            this.trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();
            clearStartPosition();
        }
        this.check = 1;
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        initializeVariables();
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0065 A[Catch:{ Exception -> 0x0112 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0067 A[Catch:{ Exception -> 0x0112 }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x008b A[Catch:{ Exception -> 0x0112 }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00e3 A[Catch:{ Exception -> 0x0112 }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00fb A[Catch:{ Exception -> 0x0112 }] */
    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        char c = 0;
        try {
            this.stopRetry = false;
            if (this.context != null) {
                Utils.appResume(this.context);
            }
            this.check++;
            if (this.check == 2) {
                this.check = 0;
            }
            this.externalPlayerSelected = false;
            String str = this.type;
            int hashCode = str.hashCode();
            if (hashCode != -1068259517) {
                if (hashCode != -905838985) {
                    if (hashCode != 48678559) {
                        if (hashCode == 993558001) {
                            if (str.equals("recording")) {
                                c = 3;
                                switch (c) {
                                    case 0:
                                        if (this.liveListDetailAvailableChannels != null && this.liveListDetailAvailableChannels.size() != 0) {
                                            playFirstTime(this.liveListDetailAvailableChannels, this.video_num);
                                            break;
                                        } else {
                                            noChannelFound();
                                            break;
                                        }
                                        //break;
                                    case 1:
                                        if (this.liveListDetailAvailableSeries != null && this.liveListDetailAvailableSeries.size() != 0) {
                                            playFirstTimeSeries(this.liveListDetailAvailableSeries, this.opened_stream_id);
                                            break;
                                        } else {
                                            noChannelFound();
                                            break;
                                        }
                                       // break;
                                    case 2:
                                        if (this.mFilePath == null) {
                                            noChannelFound();
                                            break;
                                        } else {
                                            setTitle(this.videoTitle);
                                            String.valueOf(Uri.parse(this.mFilePath + this.opened_stream_id + this.allowedFormat));
                                            this.opened_stream_id = VideoInfo.getInstance().getStreamid();
                                            this.playingURL = Uri.parse(this.mFilePath + this.opened_stream_id + this.allowedFormat);
                                            initializePlayer();
                                            break;
                                        }
                                    case 3:
                                        this.liveListRecording = new ArrayList<>();
                                        allChannelsRecording();
                                        if (this.liveListRecording != null && this.liveListRecording.size() != 0) {
                                            playFirstTimeRecording(this.liveListRecording, this.video_num);
                                            break;
                                        } else {
                                            noChannelFound();
                                            break;
                                        }
                                       // break;
                                }
                                hideSystemUi();
                            }
                        }
                    } else if (str.equals("catch_up")) {
                        c = 2;
                        switch (c) {
                        }
                        hideSystemUi();
                    }
                } else if (str.equals("series")) {
                    c = 1;
                    switch (c) {
                    }
                    hideSystemUi();
                }
            } else if (str.equals("movies")) {
                switch (c) {
                }
                hideSystemUi();
            }
            c = 65535;
            switch (c) {
            }
        } catch (Exception unused) {
        }
        hideSystemUi();
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onDestroy() {
        super.onDestroy();
        try {
            if (!(this.autoPlayHandler == null || this.autoPlayRunnable == null)) {
                this.autoPlayHandler.removeCallbacks(this.autoPlayRunnable);
            }
            if (!(this.autoPlayNextPopup == null || !this.autoPlayNextPopup.isShowing() || this.cancel_autoplay == null)) {
                this.cancel_autoplay.performClick();
            }
            this.stopRetry = true;
            release();
            VideoInfo.getInstance().setNextOrPrevPressed(false);
            VideoInfo.getInstance().setCurrentPositionSeekbar(0);
            VideoInfo.getInstance().setEpisodeId("");
            VideoInfo.getInstance().setAvailableChannels(null);
            VideoInfo.getInstance().setAvailableSeries(null);
            VideoInfo.getInstance().setstreamid(0);
            VideoInfo.getInstance().setCurrentWindowIndex(0);
        } catch (Exception unused) {
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23 && this.playerView != null) {
            this.playerView.onPause();
        }
        release();
        this.stopRetry = true;
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onStop() {
        super.onStop();
        try {
            this.externalPlayerSelected = true;
            if (!(this.autoPlayHandler == null || this.autoPlayRunnable == null)) {
                this.autoPlayHandler.removeCallbacks(this.autoPlayRunnable);
            }
            if (!(this.autoPlayNextPopup == null || !this.autoPlayNextPopup.isShowing() || this.cancel_autoplay == null)) {
                this.cancel_autoplay.performClick();
            }
            if (Util.SDK_INT > 23 && this.playerView != null) {
                this.playerView.onPause();
            }
            release();
            this.stopRetry = true;
        } catch (Exception unused) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x045e  */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x0470  */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x049f  */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x04b1  */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x0524  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0197  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x01b9  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x01d4  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x01ef  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x026d  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x02ee  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x0319  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0329  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0337  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x037f  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x03ab  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x043d  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x044c  */
    private void initializeVariables() {
        char c;
        int hashCode;
        char c2;
        this.recentWatchDBHandler = new RecentWatchDBHandler(this.context);
        this.recentWatchDBHandler1 = new SeriesRecentWatchDatabase(this.context);
        this.handlerAspectRatio = new Handler();
        this.seriesRecentClass = new SeriesRecentClass(this.context);
        this.mSettings = new Settings(this.context);
        this.liveStreamDBHandler = new LiveStreamDBHandler(this);
        this.handlerOLD = new Handler();
        this.handlerSeekbarForwardRewind = new Handler();
        this.handlerSeekbar = new Handler();
        this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.dt = new Date();
        this.loginPreferencesSharedPref = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.loginPreferencesSharedPref_currently_playing_video = getSharedPreferences(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, 0);
        this.loginPrefsEditor = this.loginPreferencesSharedPref_currently_playing_video.edit();
        this.loginPreferencesSharedPref_currently_playing_video_position = getSharedPreferences(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, 0);
        this.loginPrefsEditorPosition = this.loginPreferencesSharedPref_currently_playing_video_position.edit();
        this.loginPreferencesSharedPref_allowed_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
        this.loginPreferences_subtitle_selected = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SUBTITLE_TRACK, 0);
        this.loginPreferences_audio_selected = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_AUDIO_TRACK, 0);
        this.loginPreferences_video_selected = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_VIDEO_TRACK, 0);
        this.loginPrefsEditorAudio = this.loginPreferences_audio_selected.edit();
        this.loginPrefsEditorVideo = this.loginPreferences_video_selected.edit();
        this.loginPrefsEditorSubtitle = this.loginPreferences_subtitle_selected.edit();
        this.loginPrefsEditorAudio.clear();
        this.loginPrefsEditorAudio.apply();
        this.loginPrefsEditorVideo.clear();
        this.loginPrefsEditorVideo.apply();
        this.loginPrefsEditorSubtitle.clear();
        this.loginPrefsEditorSubtitle.apply();
        this.liveListDetailAvailableSeries = EpisodesUsingSinglton.getInstance().getEpisodeList();
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            this.currentAPPType = AppConst.TYPE_M3U;
        } else {
            this.currentAPPType = AppConst.TYPE_API;
        }
        String string = this.loginPreferencesSharedPref.getString("username", "");
        String string2 = this.loginPreferencesSharedPref.getString("password", "");
        String string3 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_URL, "");
        String string4 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_PROTOCOL, "");
        String string5 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_HTTPS_PORT, "");
        String string6 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_PORT, "");
        String string7 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_RTMP_PORT, "");
        this.allowedFormat = this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
        int hashCode2 = string4.hashCode();
        char c3 = 65535;
        if (hashCode2 != 3213448) {
            if (hashCode2 != 3504631) {
                if (hashCode2 == 99617003 && string4.equals(AppConst.HTTPS)) {
                    c = 1;
                    switch (c) {
                        case 0:
                            if (!string3.startsWith("http://")) {
                                string3 = "http://" + string3;
                                break;
                            }
                            break;
                        case 1:
                            if (!string3.startsWith("https://")) {
                                string3 = "https://" + string3;
                            }
                            string6 = string5;
                            break;
                        case 2:
                            if (!string3.startsWith("rmtp://")) {
                                string3 = "rmtp://" + string3;
                            }
                            string6 = string7;
                            break;
                        default:
                            if (!string3.startsWith("http://") && !string3.startsWith("https://")) {
                                string3 = "http://" + string3;
                                break;
                            }
                    }
                    this.rl_epg_layout = (RelativeLayout) findViewById(R.id.rl_epg_layout);
                    this.video_num = getIntent().getIntExtra("VIDEO_NUM", 0);
                    this.opened_stream_id = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
                    this.type = getIntent().getStringExtra("type");
                    this.m3uVideoURL = getIntent().getStringExtra("VIDEO_URL");
                    String stringExtra = getIntent().getStringExtra("STREAM_STOP_TIME");
                    String stringExtra2 = getIntent().getStringExtra("STREAM_START_TIME");
                    this.videoTitle = getIntent().getStringExtra("VIDEO_TITLE");
                    if (this.type.equals("catch_up")) {
                        if (this.allowedFormat != null && !this.allowedFormat.isEmpty() && !this.allowedFormat.equals("") && this.allowedFormat.equals("default")) {
                            this.allowedFormat = ".ts";
                        } else if (this.allowedFormat != null && !this.allowedFormat.isEmpty() && !this.allowedFormat.equals("") && this.allowedFormat.equals("ts")) {
                            this.allowedFormat = ".ts";
                        } else if (this.allowedFormat == null || this.allowedFormat.isEmpty() || this.allowedFormat.equals("") || !this.allowedFormat.equals("m3u8")) {
                            this.allowedFormat = ".ts";
                        } else {
                            this.allowedFormat = ".m3u8";
                        }
                    }
                    String str = this.type;
                    hashCode = str.hashCode();
                    if (hashCode == -1068259517) {
                        if (hashCode != -905838985) {
                            if (hashCode != 48678559) {
                                if (hashCode == 993558001 && str.equals("recording")) {
                                    c2 = 3;
                                    switch (c2) {
                                        case 0:
                                            this.mFilePath1 = string3 + ":" + string6 + "/movie/" + string + "/" + string2 + "/";
                                            break;
                                        case 1:
                                            this.mFilePath1 = string3 + ":" + string6 + "/series/" + string + "/" + string2 + "/";
                                            break;
                                        case 2:
                                            this.video_num = getIntent().getIntExtra("VIDEO_NUM", 0);
                                            this.mFilePath1 = string3 + ":" + string6 + "/timeshift/" + string + "/" + string2 + "/" + stringExtra + "/" + stringExtra2 + "/";
                                            break;
                                        case 3:
                                            this.mFilePath1 = getIntent().getStringExtra("VIDEO_PATH");
                                            break;
                                    }
                                    this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
                                    this.ll_seek_overlay = (LinearLayout) findViewById(R.id.ll_seek_overlay);
                                    this.tv_seek_overlay = (TextView) findViewById(R.id.tv_seek_overlay);
                                    this.bufferingloader = (ProgressBar) findViewById(R.id.app_video_loading);
                                    this.unad = Utils.ukde(MeasureHelper.pnm());
                                    this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
                                    this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
                                    findViewById(R.id.exo_nextt).setOnClickListener(this);
                                    findViewById(R.id.exo_prevv).setOnClickListener(this);
                                    this.vlcprevButton = findViewById(R.id.exo_prevv);
                                    if (this.vlcprevButton != null) {
                                        this.vlcprevButton.setOnClickListener(this);
                                    }
                                    this.vlcnextButton = findViewById(R.id.exo_nextt);
                                    if (this.vlcnextButton != null) {
                                        this.vlcnextButton.setOnClickListener(this);
                                    }
                                    this.vlcaspectRatio = findViewById(R.id.btn_aspect_ratio);
                                    if (this.vlcaspectRatio != null) {
                                        this.vlcaspectRatio.setOnClickListener(this);
                                    }
                                    this.vlc_exo_subtitle = findViewById(R.id.exo_subtitlee);
                                    if (this.vlc_exo_subtitle != null) {
                                        this.vlc_exo_subtitle.setOnClickListener(this);
                                    }
                                    this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
                                    this.vlcffwdButton = findViewById(R.id.exo_ffwdd);
                                    if (this.vlcffwdButton != null) {
                                        this.vlcffwdButton.setOnClickListener(this);
                                    }
                                    this.vlcnrewButton = findViewById(R.id.exo_reww);
                                    if (this.vlcnrewButton != null) {
                                        this.vlcnrewButton.setOnClickListener(this);
                                    }
                                    this.ukd = Utils.ukde(FileMediaDataSource.apn());
                                    uk = getApplicationName(this.context);
                                    una = getApplicationContext().getPackageName();
                                    this.liveListDetailAvailableChannels = new ArrayList<>();
                                    this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
                                    this.liveListDetailAvailableChannels = (ArrayList) MoviesUsingSinglton.getInstance().getMoviesList();
                                    onCreate = true;
                                    this.rl_next_episode = (RelativeLayout) findViewById(R.id.rl_next_episode);
                                    String str2 = this.type;
                                    if (str2.hashCode() == 993558001 && str2.equals("recording")) {
                                        c3 = 0;
                                    }
                                    if (c3 == 0) {
                                        this.liveListRecording = new ArrayList<>();
                                        allChannelsRecording();
                                        if (this.liveListRecording == null || this.liveListRecording.size() == 0) {
                                            noChannelFound();
                                        } else {
                                            playFirstTimeRecording(this.liveListRecording, this.video_num);
                                        }
                                    }
                                    this.loginPreferences_seek_time = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
                                    this.loginPrefsEditorSeekTime = this.loginPreferences_seek_time.edit();
                                    this.loginPrefsEditorSeekTime.putString(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, AppConst.PASSWORD_UNSET);
                                    this.loginPrefsEditorSeekTime.apply();
                                }
                            } else if (str.equals("catch_up")) {
                                c2 = 2;
                                switch (c2) {
                                }
                                this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
                                this.ll_seek_overlay = (LinearLayout) findViewById(R.id.ll_seek_overlay);
                                this.tv_seek_overlay = (TextView) findViewById(R.id.tv_seek_overlay);
                                this.bufferingloader = (ProgressBar) findViewById(R.id.app_video_loading);
                                this.unad = Utils.ukde(MeasureHelper.pnm());
                                this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
                                this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
                                findViewById(R.id.exo_nextt).setOnClickListener(this);
                                findViewById(R.id.exo_prevv).setOnClickListener(this);
                                this.vlcprevButton = findViewById(R.id.exo_prevv);
                                if (this.vlcprevButton != null) {
                                }
                                this.vlcnextButton = findViewById(R.id.exo_nextt);
                                if (this.vlcnextButton != null) {
                                }
                                this.vlcaspectRatio = findViewById(R.id.btn_aspect_ratio);
                                if (this.vlcaspectRatio != null) {
                                }
                                this.vlc_exo_subtitle = findViewById(R.id.exo_subtitlee);
                                if (this.vlc_exo_subtitle != null) {
                                }
                                this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
                                this.vlcffwdButton = findViewById(R.id.exo_ffwdd);
                                if (this.vlcffwdButton != null) {
                                }
                                this.vlcnrewButton = findViewById(R.id.exo_reww);
                                if (this.vlcnrewButton != null) {
                                }
                                this.ukd = Utils.ukde(FileMediaDataSource.apn());
                                uk = getApplicationName(this.context);
                                una = getApplicationContext().getPackageName();
                                this.liveListDetailAvailableChannels = new ArrayList<>();
                                this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
                                this.liveListDetailAvailableChannels = (ArrayList) MoviesUsingSinglton.getInstance().getMoviesList();
                                onCreate = true;
                                this.rl_next_episode = (RelativeLayout) findViewById(R.id.rl_next_episode);
                                String str22 = this.type;
                                c3 = 0;
                                if (c3 == 0) {
                                }
                                this.loginPreferences_seek_time = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
                                this.loginPrefsEditorSeekTime = this.loginPreferences_seek_time.edit();
                                this.loginPrefsEditorSeekTime.putString(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, AppConst.PASSWORD_UNSET);
                                this.loginPrefsEditorSeekTime.apply();
                            }
                        } else if (str.equals("series")) {
                            c2 = 1;
                            switch (c2) {
                            }
                            this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
                            this.ll_seek_overlay = (LinearLayout) findViewById(R.id.ll_seek_overlay);
                            this.tv_seek_overlay = (TextView) findViewById(R.id.tv_seek_overlay);
                            this.bufferingloader = (ProgressBar) findViewById(R.id.app_video_loading);
                            this.unad = Utils.ukde(MeasureHelper.pnm());
                            this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
                            this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
                            findViewById(R.id.exo_nextt).setOnClickListener(this);
                            findViewById(R.id.exo_prevv).setOnClickListener(this);
                            this.vlcprevButton = findViewById(R.id.exo_prevv);
                            if (this.vlcprevButton != null) {
                            }
                            this.vlcnextButton = findViewById(R.id.exo_nextt);
                            if (this.vlcnextButton != null) {
                            }
                            this.vlcaspectRatio = findViewById(R.id.btn_aspect_ratio);
                            if (this.vlcaspectRatio != null) {
                            }
                            this.vlc_exo_subtitle = findViewById(R.id.exo_subtitlee);
                            if (this.vlc_exo_subtitle != null) {
                            }
                            this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
                            this.vlcffwdButton = findViewById(R.id.exo_ffwdd);
                            if (this.vlcffwdButton != null) {
                            }
                            this.vlcnrewButton = findViewById(R.id.exo_reww);
                            if (this.vlcnrewButton != null) {
                            }
                            this.ukd = Utils.ukde(FileMediaDataSource.apn());
                            uk = getApplicationName(this.context);
                            una = getApplicationContext().getPackageName();
                            this.liveListDetailAvailableChannels = new ArrayList<>();
                            this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
                            this.liveListDetailAvailableChannels = (ArrayList) MoviesUsingSinglton.getInstance().getMoviesList();
                            onCreate = true;
                            this.rl_next_episode = (RelativeLayout) findViewById(R.id.rl_next_episode);
                            String str222 = this.type;
                            c3 = 0;
                            if (c3 == 0) {
                            }
                            this.loginPreferences_seek_time = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
                            this.loginPrefsEditorSeekTime = this.loginPreferences_seek_time.edit();
                            this.loginPrefsEditorSeekTime.putString(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, AppConst.PASSWORD_UNSET);
                            this.loginPrefsEditorSeekTime.apply();
                        }
                    } else if (str.equals("movies")) {
                        c2 = 0;
                        switch (c2) {
                        }
                        this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
                        this.ll_seek_overlay = (LinearLayout) findViewById(R.id.ll_seek_overlay);
                        this.tv_seek_overlay = (TextView) findViewById(R.id.tv_seek_overlay);
                        this.bufferingloader = (ProgressBar) findViewById(R.id.app_video_loading);
                        this.unad = Utils.ukde(MeasureHelper.pnm());
                        this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
                        this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
                        findViewById(R.id.exo_nextt).setOnClickListener(this);
                        findViewById(R.id.exo_prevv).setOnClickListener(this);
                        this.vlcprevButton = findViewById(R.id.exo_prevv);
                        if (this.vlcprevButton != null) {
                        }
                        this.vlcnextButton = findViewById(R.id.exo_nextt);
                        if (this.vlcnextButton != null) {
                        }
                        this.vlcaspectRatio = findViewById(R.id.btn_aspect_ratio);
                        if (this.vlcaspectRatio != null) {
                        }
                        this.vlc_exo_subtitle = findViewById(R.id.exo_subtitlee);
                        if (this.vlc_exo_subtitle != null) {
                        }
                        this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
                        this.vlcffwdButton = findViewById(R.id.exo_ffwdd);
                        if (this.vlcffwdButton != null) {
                        }
                        this.vlcnrewButton = findViewById(R.id.exo_reww);
                        if (this.vlcnrewButton != null) {
                        }
                        this.ukd = Utils.ukde(FileMediaDataSource.apn());
                        uk = getApplicationName(this.context);
                        una = getApplicationContext().getPackageName();
                        this.liveListDetailAvailableChannels = new ArrayList<>();
                        this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
                        this.liveListDetailAvailableChannels = (ArrayList) MoviesUsingSinglton.getInstance().getMoviesList();
                        onCreate = true;
                        this.rl_next_episode = (RelativeLayout) findViewById(R.id.rl_next_episode);
                        String str2222 = this.type;
                        c3 = 0;
                        if (c3 == 0) {
                        }
                        this.loginPreferences_seek_time = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
                        this.loginPrefsEditorSeekTime = this.loginPreferences_seek_time.edit();
                        this.loginPrefsEditorSeekTime.putString(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, AppConst.PASSWORD_UNSET);
                        this.loginPrefsEditorSeekTime.apply();
                    }
                    c2 = 65535;
                    switch (c2) {
                    }
                    this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
                    this.ll_seek_overlay = (LinearLayout) findViewById(R.id.ll_seek_overlay);
                    this.tv_seek_overlay = (TextView) findViewById(R.id.tv_seek_overlay);
                    this.bufferingloader = (ProgressBar) findViewById(R.id.app_video_loading);
                    this.unad = Utils.ukde(MeasureHelper.pnm());
                    this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
                    this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
                    findViewById(R.id.exo_nextt).setOnClickListener(this);
                    findViewById(R.id.exo_prevv).setOnClickListener(this);
                    this.vlcprevButton = findViewById(R.id.exo_prevv);
                    if (this.vlcprevButton != null) {
                    }
                    this.vlcnextButton = findViewById(R.id.exo_nextt);
                    if (this.vlcnextButton != null) {
                    }
                    this.vlcaspectRatio = findViewById(R.id.btn_aspect_ratio);
                    if (this.vlcaspectRatio != null) {
                    }
                    this.vlc_exo_subtitle = findViewById(R.id.exo_subtitlee);
                    if (this.vlc_exo_subtitle != null) {
                    }
                    this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
                    this.vlcffwdButton = findViewById(R.id.exo_ffwdd);
                    if (this.vlcffwdButton != null) {
                    }
                    this.vlcnrewButton = findViewById(R.id.exo_reww);
                    if (this.vlcnrewButton != null) {
                    }
                    this.ukd = Utils.ukde(FileMediaDataSource.apn());
                    uk = getApplicationName(this.context);
                    una = getApplicationContext().getPackageName();
                    this.liveListDetailAvailableChannels = new ArrayList<>();
                    this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
                    this.liveListDetailAvailableChannels = (ArrayList) MoviesUsingSinglton.getInstance().getMoviesList();
                    onCreate = true;
                    this.rl_next_episode = (RelativeLayout) findViewById(R.id.rl_next_episode);
                    String str22222 = this.type;
                    c3 = 0;
                    if (c3 == 0) {
                    }
                    this.loginPreferences_seek_time = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
                    this.loginPrefsEditorSeekTime = this.loginPreferences_seek_time.edit();
                    this.loginPrefsEditorSeekTime.putString(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, AppConst.PASSWORD_UNSET);
                    this.loginPrefsEditorSeekTime.apply();
                }
            } else if (string4.equals(AppConst.RMTP)) {
                c = 2;
                switch (c) {
                }
                this.rl_epg_layout = (RelativeLayout) findViewById(R.id.rl_epg_layout);
                this.video_num = getIntent().getIntExtra("VIDEO_NUM", 0);
                this.opened_stream_id = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
                this.type = getIntent().getStringExtra("type");
                this.m3uVideoURL = getIntent().getStringExtra("VIDEO_URL");
                String stringExtra3 = getIntent().getStringExtra("STREAM_STOP_TIME");
                String stringExtra22 = getIntent().getStringExtra("STREAM_START_TIME");
                this.videoTitle = getIntent().getStringExtra("VIDEO_TITLE");
                if (this.type.equals("catch_up")) {
                }
                String str3 = this.type;
                hashCode = str3.hashCode();
                if (hashCode == -1068259517) {
                }
                c2 = 65535;
                switch (c2) {
                }
                this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
                this.ll_seek_overlay = (LinearLayout) findViewById(R.id.ll_seek_overlay);
                this.tv_seek_overlay = (TextView) findViewById(R.id.tv_seek_overlay);
                this.bufferingloader = (ProgressBar) findViewById(R.id.app_video_loading);
                this.unad = Utils.ukde(MeasureHelper.pnm());
                this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
                this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
                findViewById(R.id.exo_nextt).setOnClickListener(this);
                findViewById(R.id.exo_prevv).setOnClickListener(this);
                this.vlcprevButton = findViewById(R.id.exo_prevv);
                if (this.vlcprevButton != null) {
                }
                this.vlcnextButton = findViewById(R.id.exo_nextt);
                if (this.vlcnextButton != null) {
                }
                this.vlcaspectRatio = findViewById(R.id.btn_aspect_ratio);
                if (this.vlcaspectRatio != null) {
                }
                this.vlc_exo_subtitle = findViewById(R.id.exo_subtitlee);
                if (this.vlc_exo_subtitle != null) {
                }
                this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
                this.vlcffwdButton = findViewById(R.id.exo_ffwdd);
                if (this.vlcffwdButton != null) {
                }
                this.vlcnrewButton = findViewById(R.id.exo_reww);
                if (this.vlcnrewButton != null) {
                }
                this.ukd = Utils.ukde(FileMediaDataSource.apn());
                uk = getApplicationName(this.context);
                una = getApplicationContext().getPackageName();
                this.liveListDetailAvailableChannels = new ArrayList<>();
                this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
                this.liveListDetailAvailableChannels = (ArrayList) MoviesUsingSinglton.getInstance().getMoviesList();
                onCreate = true;
                this.rl_next_episode = (RelativeLayout) findViewById(R.id.rl_next_episode);
                String str222222 = this.type;
                c3 = 0;
                if (c3 == 0) {
                }
                this.loginPreferences_seek_time = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
                this.loginPrefsEditorSeekTime = this.loginPreferences_seek_time.edit();
                this.loginPrefsEditorSeekTime.putString(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, AppConst.PASSWORD_UNSET);
                this.loginPrefsEditorSeekTime.apply();
            }
        } else if (string4.equals(AppConst.HTTP)) {
            c = 0;
            switch (c) {
            }
            this.rl_epg_layout = (RelativeLayout) findViewById(R.id.rl_epg_layout);
            this.video_num = getIntent().getIntExtra("VIDEO_NUM", 0);
            this.opened_stream_id = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
            this.type = getIntent().getStringExtra("type");
            this.m3uVideoURL = getIntent().getStringExtra("VIDEO_URL");
            String stringExtra32 = getIntent().getStringExtra("STREAM_STOP_TIME");
            String stringExtra222 = getIntent().getStringExtra("STREAM_START_TIME");
            this.videoTitle = getIntent().getStringExtra("VIDEO_TITLE");
            if (this.type.equals("catch_up")) {
            }
            String str32 = this.type;
            hashCode = str32.hashCode();
            if (hashCode == -1068259517) {
            }
            c2 = 65535;
            switch (c2) {
            }
            this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
            this.ll_seek_overlay = (LinearLayout) findViewById(R.id.ll_seek_overlay);
            this.tv_seek_overlay = (TextView) findViewById(R.id.tv_seek_overlay);
            this.bufferingloader = (ProgressBar) findViewById(R.id.app_video_loading);
            this.unad = Utils.ukde(MeasureHelper.pnm());
            this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
            this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
            findViewById(R.id.exo_nextt).setOnClickListener(this);
            findViewById(R.id.exo_prevv).setOnClickListener(this);
            this.vlcprevButton = findViewById(R.id.exo_prevv);
            if (this.vlcprevButton != null) {
            }
            this.vlcnextButton = findViewById(R.id.exo_nextt);
            if (this.vlcnextButton != null) {
            }
            this.vlcaspectRatio = findViewById(R.id.btn_aspect_ratio);
            if (this.vlcaspectRatio != null) {
            }
            this.vlc_exo_subtitle = findViewById(R.id.exo_subtitlee);
            if (this.vlc_exo_subtitle != null) {
            }
            this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
            this.vlcffwdButton = findViewById(R.id.exo_ffwdd);
            if (this.vlcffwdButton != null) {
            }
            this.vlcnrewButton = findViewById(R.id.exo_reww);
            if (this.vlcnrewButton != null) {
            }
            this.ukd = Utils.ukde(FileMediaDataSource.apn());
            uk = getApplicationName(this.context);
            una = getApplicationContext().getPackageName();
            this.liveListDetailAvailableChannels = new ArrayList<>();
            this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
            this.liveListDetailAvailableChannels = (ArrayList) MoviesUsingSinglton.getInstance().getMoviesList();
            onCreate = true;
            this.rl_next_episode = (RelativeLayout) findViewById(R.id.rl_next_episode);
            String str2222222 = this.type;
            c3 = 0;
            if (c3 == 0) {
            }
            this.loginPreferences_seek_time = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
            this.loginPrefsEditorSeekTime = this.loginPreferences_seek_time.edit();
            this.loginPrefsEditorSeekTime.putString(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, AppConst.PASSWORD_UNSET);
            this.loginPrefsEditorSeekTime.apply();
        }
        c = 65535;
        switch (c) {
        }
        this.rl_epg_layout = (RelativeLayout) findViewById(R.id.rl_epg_layout);
        this.video_num = getIntent().getIntExtra("VIDEO_NUM", 0);
        this.opened_stream_id = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
        this.type = getIntent().getStringExtra("type");
        this.m3uVideoURL = getIntent().getStringExtra("VIDEO_URL");
        String stringExtra322 = getIntent().getStringExtra("STREAM_STOP_TIME");
        String stringExtra2222 = getIntent().getStringExtra("STREAM_START_TIME");
        this.videoTitle = getIntent().getStringExtra("VIDEO_TITLE");
        if (this.type.equals("catch_up")) {
        }
        String str322 = this.type;
        hashCode = str322.hashCode();
        if (hashCode == -1068259517) {
        }
        c2 = 65535;
        switch (c2) {
        }
        this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
        this.ll_seek_overlay = (LinearLayout) findViewById(R.id.ll_seek_overlay);
        this.tv_seek_overlay = (TextView) findViewById(R.id.tv_seek_overlay);
        this.bufferingloader = (ProgressBar) findViewById(R.id.app_video_loading);
        this.unad = Utils.ukde(MeasureHelper.pnm());
        this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
        this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
        findViewById(R.id.exo_nextt).setOnClickListener(this);
        findViewById(R.id.exo_prevv).setOnClickListener(this);
        this.vlcprevButton = findViewById(R.id.exo_prevv);
        if (this.vlcprevButton != null) {
        }
        this.vlcnextButton = findViewById(R.id.exo_nextt);
        if (this.vlcnextButton != null) {
        }
        this.vlcaspectRatio = findViewById(R.id.btn_aspect_ratio);
        if (this.vlcaspectRatio != null) {
        }
        this.vlc_exo_subtitle = findViewById(R.id.exo_subtitlee);
        if (this.vlc_exo_subtitle != null) {
        }
        this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
        this.vlcffwdButton = findViewById(R.id.exo_ffwdd);
        if (this.vlcffwdButton != null) {
        }
        this.vlcnrewButton = findViewById(R.id.exo_reww);
        if (this.vlcnrewButton != null) {
        }
        this.ukd = Utils.ukde(FileMediaDataSource.apn());
        uk = getApplicationName(this.context);
        una = getApplicationContext().getPackageName();
        this.liveListDetailAvailableChannels = new ArrayList<>();
        this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
        this.liveListDetailAvailableChannels = (ArrayList) MoviesUsingSinglton.getInstance().getMoviesList();
        onCreate = true;
        this.rl_next_episode = (RelativeLayout) findViewById(R.id.rl_next_episode);
        String str22222222 = this.type;
        c3 = 0;
        if (c3 == 0) {
        }
        this.loginPreferences_seek_time = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
        this.loginPrefsEditorSeekTime = this.loginPreferences_seek_time.edit();
        this.loginPrefsEditorSeekTime.putString(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, AppConst.PASSWORD_UNSET);
        this.loginPrefsEditorSeekTime.apply();
    }

    public void allChannelsRecording() {
        File[] recordedFiles = Utils.getRecordedFiles(this.context);
        for (File file : recordedFiles) {
            if (file.toString().endsWith(".ts")) {
                Arrays.asList(file);
            }
        }
        if (recordedFiles != null && recordedFiles.length > 0) {
            for (File file2 : recordedFiles) {
                if (file2.toString().endsWith(".ts")) {
                    this.dataItems.addAll(Arrays.asList(file2));
                }
            }
            Collections.reverse(this.dataItems);
            this.liveListRecording = this.dataItems;
        }
    }

    public void noChannelFound() {
        this.app_video_status.setVisibility(0);
        this.app_video_status_text.setText(getResources().getString(R.string.no_channel_found));
        if (AppConst.WATER_MARK.booleanValue()) {
            findViewById(R.id.watrmrk).setVisibility(8);
        }
    }

    public static String getApplicationName(Context context2) {
        return String.valueOf(context2.getApplicationInfo().loadLabel(context2.getPackageManager()));
    }

    public int getIndexOfMovies(ArrayList<LiveStreamsDBModel> arrayList, int i) {
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (Utils.parseIntZero(arrayList.get(i2).getNum()) == i) {
                return i2;
            }
        }
        return 0;
    }

    private void setTitle(String str) {
        TextView textView = new TextView(this);
        textView.setText(str);
        textView.setTextSize(24.0f);
        textView.setPadding(18, 8, 8, 8);
        textView.setTextColor(-1);
        this.debugRootView.removeAllViews();
        this.debugRootView.addView(textView);
    }

    private void playFirstTimeSeries(List<GetEpisdoeDetailsCallback> list, int i) {
        if (list != null && list.size() > 0) {
            this.currentWindowIndex = getIndexOfSeries(list, i);
            this.episode_id = list.get(this.currentWindowIndex).getId();
            String title = list.get(this.currentWindowIndex).getTitle();
            String id = list.get(this.currentWindowIndex).getId();
            VideoInfo.getInstance().setEpisodeId(this.episode_id);
            String ukde = Utils.ukde(TableLayoutBinder.aW5nIGl() + TableLayoutBinder.mu());
            int parseIntMinusOne = Utils.parseIntMinusOne(list.get(this.currentWindowIndex).getId());
            String containerExtension = list.get(this.currentWindowIndex).getContainerExtension();
            setTitle(id + " - " + title);
            if (this.loginPrefsEditor != null) {
                this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(list.get(this.currentWindowIndex).getId()));
                this.loginPrefsEditor.apply();
            }
            if (this.loginPrefsEditorPosition != null) {
                this.loginPrefsEditorPosition.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, String.valueOf(this.currentWindowIndex));
                this.loginPrefsEditorPosition.apply();
            }
            if (df(this.fr, this.fr.format(new Date(MediaPlayerCompat.cit(this.context))), this.df.format(this.dt)) >= ((long) InfoHudViewHolder.ux()) && this.ukd != null && this.unad != null && (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad)))) {
                this.rq = false;
                this.app_video_status.setVisibility(0);
                TextView textView = this.app_video_status_text;
                textView.setText(ukde + this.elv + this.fmw);
            }
            this.currentProgramStreamID = parseIntMinusOne;
            int streamCheckSeries = this.seriesRecentClass.streamCheckSeries(this.episode_id, SharepreferenceDBHandler.getUserID(this.context));
            this.sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            this.mCurrentAspectRatioIndex = this.sharedPreferences.getInt(AppConst.ASPECT_RATIO, this.mCurrentAspectRatioIndex);
            if (this.check == 1) {
                this.check = 0;
                streamCheckSeries = 0;
            }
            if (streamCheckSeries == 0) {
                if (this.rq.booleanValue()) {
                    release();
                    EpisodesUsingSinglton.getInstance().setEpisodeList(list);
                    if (this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                        this.playingURL = Uri.parse(this.m3uVideoURL);
                    } else {
                        this.playingURL = Uri.parse(this.mFilePath + parseIntMinusOne + "." + containerExtension);
                    }
                    this.retryCount = 0;
                    this.retrying = false;
                    VideoInfo.getInstance().setstreamid(parseIntMinusOne);
                    VideoInfo.getInstance().settypeofstream("");
                    VideoInfo.getInstance().setAvailableSeries(list);
                    VideoInfo.getInstance().setCurrentWindowIndex(this.currentWindowIndex);
                    VideoInfo.getInstance().setvideoNum(this.video_num);
                    VideoInfo.getInstance().settimeElapsed(0);
                    VideoInfo.getInstance().setresuming(true);
                    VideoInfo.getInstance().setisVODPlayer(true);
                    VideoInfo.getInstance().setMaxTime(false);
                    initializePlayer();
                }
            } else if (streamCheckSeries > 0 && !isFinishing() && this.rq.booleanValue()) {
                if (this.alertDialog != null) {
                    this.alertDialog.dismiss();
                }
                this.loginPreferences_seek_time = getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
                VideoInfo.getInstance().setAvailableSeries(list);
                EpisodesUsingSinglton.getInstance().setEpisodeList(list);
                if (this.seriesRecentClass.checkRecentWatch(this.episode_id) > 0) {
                    showRecentWatchPopup(this.context, this.episode_id, containerExtension, title, this.video_num, "series", list, this.currentWindowIndex, null, list.get(this.currentWindowIndex).getElapsed_time());
                }
            }
        }
    }

    private void playFirstTimeRecording(ArrayList<File> arrayList, int i) {
        if (arrayList != null && arrayList.size() > 0) {
            String name = arrayList.get(i).getName();
            String ukde = Utils.ukde(com.nst.yourname.view.ijkplayer.widget.media.TableLayoutBinder.aW5nIGl() + com.nst.yourname.view.ijkplayer.widget.media.TableLayoutBinder.mu());
            if (df(this.fr, this.fr.format(new Date(MediaPlayerCompat.cit(this.context))), this.df.format(this.dt)) >= ((long) InfoHudViewHolder.ux()) && this.ukd != null && this.unad != null && (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad)))) {
                this.rq = false;
                this.app_video_status.setVisibility(0);
                TextView textView = this.app_video_status_text;
                textView.setText(ukde + this.elv + this.fmw);
            }
            VideoInfo.getInstance().setCurrentWindowIndex(i);
            setTitle(name);
            release();
            if (this.rq.booleanValue()) {
                this.playingURL = Uri.parse(this.mFilePath);
                this.retryCount = 0;
                this.retrying = false;
                VideoInfo.getInstance().setstreamid(this.opened_stream_id);
                VideoInfo.getInstance().settypeofstream(this.typeofStream);
                VideoInfo.getInstance().setCurrentWindowIndex(this.currentWindowIndex);
                VideoInfo.getInstance().setvideoNum(i);
                VideoInfo.getInstance().settimeElapsed(0);
                VideoInfo.getInstance().setresuming(true);
                VideoInfo.getInstance().setisVODPlayer(true);
                VideoInfo.getInstance().setMaxTime(false);
                initializePlayer();
            }
        }
    }

    public int getIndexOfSeries(List<GetEpisdoeDetailsCallback> list, int i) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (Utils.parseIntZero(list.get(i2).getId()) == i) {
                return i2;
            }
        }
        return 0;
    }

    private void playFirstTime(ArrayList<LiveStreamsDBModel> arrayList, int i) {
        if (arrayList != null && arrayList.size() > 0) {
            this.currentWindowIndex = getIndexOfMovies(arrayList, i);
            String name = arrayList.get(this.currentWindowIndex).getName();
            String num2 = arrayList.get(this.currentWindowIndex).getNum();
            String ukde = Utils.ukde(TableLayoutBinder.aW5nIGl() + TableLayoutBinder.mu());
            String streamType = arrayList.get(this.currentWindowIndex).getStreamType();
            int parseIntMinusOne = Utils.parseIntMinusOne(arrayList.get(this.currentWindowIndex).getStreamId());
            String url = arrayList.get(this.currentWindowIndex).getUrl();
            String contaiinerExtension = arrayList.get(this.currentWindowIndex).getContaiinerExtension();
            setTitle(num2 + " - " + name);
            if (this.loginPrefsEditor != null) {
                this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(arrayList.get(this.currentWindowIndex).getStreamId()));
                this.loginPrefsEditor.apply();
            }
            if (this.loginPrefsEditorPosition != null) {
                this.loginPrefsEditorPosition.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, String.valueOf(this.currentWindowIndex));
                this.loginPrefsEditorPosition.apply();
            }
            if (df(this.fr, this.fr.format(new Date(MediaPlayerCompat.cit(this.context))), this.df.format(this.dt)) >= ((long) InfoHudViewHolder.ux()) && this.ukd != null && this.unad != null && (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad)))) {
                this.rq = false;
                this.app_video_status.setVisibility(0);
                TextView textView = this.app_video_status_text;
                textView.setText(ukde + this.elv + this.fmw);
            }
            this.currentProgramStreamID = parseIntMinusOne;
            int streamCheckFun = streamCheckFun(parseIntMinusOne, SharepreferenceDBHandler.getUserID(this.context));
            this.sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            this.mCurrentAspectRatioIndex = this.sharedPreferences.getInt(AppConst.ASPECT_RATIO, this.mCurrentAspectRatioIndex);
            if (streamCheckFun == 0) {
                if (this.rq.booleanValue()) {
                    release();
                    if (this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                        this.playingURL = Uri.parse(url);
                    } else {
                        this.playingURL = Uri.parse(this.mFilePath + parseIntMinusOne + "." + contaiinerExtension);
                    }
                    this.retryCount = 0;
                    this.retrying = false;
                    VideoInfo.getInstance().setstreamid(parseIntMinusOne);
                    VideoInfo.getInstance().settypeofstream(streamType);
                    VideoInfo.getInstance().setAvailableChannels(arrayList);
                    VideoInfo.getInstance().setCurrentWindowIndex(this.currentWindowIndex);
                    VideoInfo.getInstance().setvideoNum(i);
                    VideoInfo.getInstance().settimeElapsed(0);
                    VideoInfo.getInstance().setresuming(true);
                    VideoInfo.getInstance().setisVODPlayer(true);
                    VideoInfo.getInstance().setMaxTime(false);
                    initializePlayer();
                }
            } else if (streamCheckFun > 0 && !isFinishing() && this.rq.booleanValue()) {
                this.loginPreferences_seek_time = getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
                VideoInfo.getInstance().setAvailableChannels(arrayList);
                passwordConfirmationPopUp(this, parseIntMinusOne, contaiinerExtension, name, i, streamType, this.currentWindowIndex, url);
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

    private void updateMovieElapsedStatus(int i, long j) {
        if (this.recentWatchDBHandler != null) {
            this.recentWatchDBHandler.updateResumePlayerStatus(String.valueOf(i), AppConst.EVENT_TYPE_MOVIE, false, j);
        }
    }

    private void updateMovieElapsedStatusM3U(String str, long j) {
        if (this.liveStreamDBHandler != null) {
            this.liveStreamDBHandler.updateResumePlayerStatus(str, AppConst.EVENT_TYPE_MOVIE, false, j);
        }
    }

    private void passwordConfirmationPopUp(final NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity, final int i, final String str, final String str2, final int i2, final String str3, final int i3, final String str4) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogbox);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass1 */

            public void onDismiss(DialogInterface dialogInterface) {
                NSTEXOPlayerVODActivity.this.hideSystemUi();
            }
        });
        View inflate = LayoutInflater.from(this).inflate((int) R.layout.layout_resume_player, (ViewGroup) null);
        this.movieIDTV = (TextView) inflate.findViewById(R.id.tv_movie_id);
        this.savePasswordBT = (Button) inflate.findViewById(R.id.bt_resume);
        this.iv_cancel = (ImageView) inflate.findViewById(R.id.iv_cancel);
        this.iv_cancel.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass2 */

            public void onClick(View view) {
                NSTEXOPlayerVODActivity.this.alertDialog.setCancelable(true);
                NSTEXOPlayerVODActivity.this.onBackPressed();
                NSTEXOPlayerVODActivity.this.onBackPressed();
                NSTEXOPlayerVODActivity.this.alertDialog.dismiss();
            }
        });
        this.closedBT = (Button) inflate.findViewById(R.id.bt_start_over);
        if (this.movieIDTV != null) {
            TextView textView = this.movieIDTV;
            textView.setText(i2 + "-" + str2);
        }
        if (this.savePasswordBT != null) {
            this.savePasswordBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.savePasswordBT));
        }
        if (this.closedBT != null) {
            this.closedBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.closedBT));
        }
        if (this.iv_cancel != null) {
            this.iv_cancel.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.iv_cancel));
        }
        this.savePasswordBT.requestFocus();
        this.savePasswordBT.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass3 */

            public void onClick(View view) {
                LiveStreamsDBModel liveStreamsDBModel;
                new LiveStreamsDBModel();
                if (NSTEXOPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                    liveStreamsDBModel = NSTEXOPlayerVODActivity.this.getStreamStatusM3U(String.valueOf(Uri.parse(str4)), SharepreferenceDBHandler.getUserID(nSTEXOPlayerVODActivity));
                } else {
                    liveStreamsDBModel = NSTEXOPlayerVODActivity.this.getStreamStatus(i, SharepreferenceDBHandler.getUserID(nSTEXOPlayerVODActivity));
                }
                long movieElapsedTime = liveStreamsDBModel.getMovieElapsedTime();
                if (VideoInfo.getInstance() != null && NSTEXOPlayerVODActivity.this.rq.booleanValue()) {
                    NSTEXOPlayerVODActivity.this.release();
                    if (NSTEXOPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                        NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(str4);
                    } else {
                        NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity = NSTEXOPlayerVODActivity.this;
                        nSTEXOPlayerVODActivity.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + i + "." + str);
                    }
                    VideoInfo instance = VideoInfo.getInstance();
                    instance.setTitle(i2 + " - " + str2);
                    VideoInfo.getInstance().setstreamid(i);
                    VideoInfo.getInstance().settypeofstream(str3);
                    VideoInfo.getInstance().setCurrentWindowIndex(i3);
                    VideoInfo.getInstance().setvideoNum(i2);
                    VideoInfo.getInstance().setisTimeElapsed(true);
                    VideoInfo.getInstance().settimeElapsed(movieElapsedTime);
                    VideoInfo.getInstance().setisVODPlayer(true);
                    VideoInfo.getInstance().setresuming(true);
                    VideoInfo.getInstance().setMaxTime(false);
                    VideoInfo.getInstance().setseeked(true);
                    NSTEXOPlayerVODActivity.this.retryCount = 0;
                    NSTEXOPlayerVODActivity.this.retrying = false;
                    VideoInfo.getInstance().setCurrentPositionSeekbar((int) movieElapsedTime);
                    VideoInfo.getInstance().setProgress(true);
                    NSTEXOPlayerVODActivity.this.initializePlayer();
                }
                NSTEXOPlayerVODActivity.this.alertDialog.dismiss();
            }
        });
        this.closedBT.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass4 */

            public void onClick(View view) {
                NSTEXOPlayerVODActivity.this.release();
                if (NSTEXOPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                    NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(str4);
                } else {
                    NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity = NSTEXOPlayerVODActivity.this;
                    nSTEXOPlayerVODActivity.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + i + "." + str);
                }
                new LiveStreamsDBModel();
                NSTEXOPlayerVODActivity.this.getStreamStatus(i, SharepreferenceDBHandler.getUserID(nSTEXOPlayerVODActivity)).getMovieElapsedTime();
                if (VideoInfo.getInstance() != null) {
                    VideoInfo instance = VideoInfo.getInstance();
                    instance.setTitle(i2 + " - " + str2);
                    VideoInfo.getInstance().setstreamid(i);
                    VideoInfo.getInstance().settypeofstream(str3);
                    VideoInfo.getInstance().setCurrentWindowIndex(i3);
                    VideoInfo.getInstance().setvideoNum(i2);
                    VideoInfo.getInstance().setisTimeElapsed(true);
                    VideoInfo.getInstance().setisVODPlayer(true);
                    VideoInfo.getInstance().settimeElapsed(0);
                    VideoInfo.getInstance().setresuming(true);
                    VideoInfo.getInstance().setMaxTime(false);
                    NSTEXOPlayerVODActivity.this.retryCount = 0;
                    NSTEXOPlayerVODActivity.this.retrying = false;
                    NSTEXOPlayerVODActivity.this.initializePlayer();
                }
                NSTEXOPlayerVODActivity.this.alertDialog.dismiss();
            }
        });
        builder.setView(inflate);
        this.alertDialog = builder.create();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(this.alertDialog.getWindow().getAttributes());
        layoutParams.width = -1;
        layoutParams.height = -2;
        this.alertDialog.show();
        this.alertDialog.getWindow().setAttributes(layoutParams);
        this.alertDialog.setCancelable(false);
        this.alertDialog.show();
    }

    public LiveStreamsDBModel getStreamStatus(int i, int i2) {
        return this.recentWatchDBHandler.getStreamStatus(String.valueOf(i), i2);
    }

    private Long getStreamSeries(String str) {
        return this.recentWatchDBHandler1.gettimeElapsed(str);
    }

    public LiveStreamsDBModel getStreamStatusM3U(String str, int i) {
        return this.liveStreamDBHandler.getStreamStatus(str, i);
    }

    public int streamCheckFun(int i, int i2) {
        return this.recentWatchDBHandler.isStreamAvailable(String.valueOf(i), i2);
    }

    private int streamCheckFunM3U(String str, int i) {
        return this.liveStreamDBHandler.isStreamAvailable(str, i);
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    /* JADX WARNING: Removed duplicated region for block: B:121:0x03e7 A[Catch:{ Exception -> 0x0676 }] */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x03e9 A[Catch:{ Exception -> 0x0676 }] */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x0435 A[Catch:{ Exception -> 0x0676 }] */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x04bd A[Catch:{ Exception -> 0x0676 }] */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x058e A[Catch:{ Exception -> 0x0676 }] */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x05a6 A[Catch:{ Exception -> 0x0676 }] */
    /* JADX WARNING: Removed duplicated region for block: B:167:0x05b4 A[Catch:{ Exception -> 0x0676 }] */
    /* JADX WARNING: Removed duplicated region for block: B:168:0x05b6 A[Catch:{ Exception -> 0x0676 }] */
    /* JADX WARNING: Removed duplicated region for block: B:171:0x05e9 A[Catch:{ Exception -> 0x0676 }] */
    /* JADX WARNING: Removed duplicated region for block: B:174:0x062e A[Catch:{ Exception -> 0x0676 }] */
    /* JADX WARNING: Removed duplicated region for block: B:179:0x0664 A[Catch:{ Exception -> 0x0676 }] */
    /* JADX WARNING: Removed duplicated region for block: B:208:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:211:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0115 A[Catch:{ Exception -> 0x0384 }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0117 A[Catch:{ Exception -> 0x0384 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x015f A[Catch:{ Exception -> 0x0384 }] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x01e7 A[Catch:{ Exception -> 0x0384 }] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x02c0 A[Catch:{ Exception -> 0x0384 }] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x02d8 A[Catch:{ Exception -> 0x0384 }] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x02e6 A[Catch:{ Exception -> 0x0384 }] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x02e8 A[Catch:{ Exception -> 0x0384 }] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x031b A[Catch:{ Exception -> 0x0384 }] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x034e A[Catch:{ Exception -> 0x0384 }] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0372 A[Catch:{ Exception -> 0x0384 }] */
    public void onClick(View view) {
        char c;
        int hashCode;
        char c2;
        int hashCode2;
        char c3 = 2;
        switch (view.getId()) {
            case R.id.btn_aspect_ratio:
                try {
                    toggleAspectRatio();
                    return;
                } catch (Exception e) {
                    Log.e("NSTIJPLAYER", "exection " + e);
                    return;
                }
            case R.id.exo_ffwdd:
                try {
                    if (VideoInfo.getInstance() != null && this.player != null) {
                        this.handlerSeekbarForwardRewind.removeCallbacksAndMessages(null);
                        if (this.type.equals("catch_up")) {
                            this.seekBarMilliseconds += DateTimeConstants.MILLIS_PER_MINUTE;
                        } else {
                            this.seekBarMilliseconds += 10000;
                        }
                        if (this.seekBarMilliseconds > 0) {
                            this.tv_seek_overlay.setText("+" + (this.seekBarMilliseconds / 1000) + "s");
                        } else {
                            this.tv_seek_overlay.setText((this.seekBarMilliseconds / 1000) + "s");
                        }
                        this.ll_seek_overlay.setVisibility(0);
                        this.handlerSeekbarForwardRewind.postDelayed(new Runnable() {
                            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass7 */

                            public void run() {
                                NSTEXOPlayerVODActivity.this.player.seekTo(NSTEXOPlayerVODActivity.this.player.getCurrentPosition() + ((long) NSTEXOPlayerVODActivity.this.seekBarMilliseconds));
                                NSTEXOPlayerVODActivity.this.handlerSeekbar.removeCallbacksAndMessages(null);
                                NSTEXOPlayerVODActivity.this.handlerSeekbar.postDelayed(new Runnable() {
                                    /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass7.AnonymousClass1 */

                                    public void run() {
                                        int unused = NSTEXOPlayerVODActivity.this.seekBarMilliseconds = 0;
                                        NSTEXOPlayerVODActivity.this.ll_seek_overlay.setVisibility(8);
                                    }
                                }, 3000);
                            }
                        }, 1000);
                        return;
                    }
                    return;
                } catch (Exception e2) {
                    Log.e("NSTIJPLAYERVODACTIVTY", "exection " + e2);
                    return;
                }
            case R.id.exo_nextt:
                try {
                    if (VideoInfo.getInstance() != null) {
                        this.vlcnextButton.requestFocus();
                        this.handlerOLD.removeCallbacksAndMessages(null);
                        next();
                        final int currentWindowIndex2 = VideoInfo.getInstance().getCurrentWindowIndex();
                        String str = this.type;
                        int hashCode3 = str.hashCode();
                        if (hashCode3 != -1068259517) {
                            if (hashCode3 != -905838985) {
                                if (hashCode3 == 993558001) {
                                    if (str.equals("recording")) {
                                        c = 2;
                                        switch (c) {
                                            case 0:
                                                if (this.liveListDetailAvailableChannels != null && this.liveListDetailAvailableChannels.size() > 1 && currentWindowIndex2 <= this.liveListDetailAvailableChannels.size() - 1) {
                                                    this.videoTitle = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getName();
                                                    this.num = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getNum();
                                                    this.typeofStream = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getStreamType();
                                                    this.container_extension = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getContaiinerExtension();
                                                    this.m3uVideoURL = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getUrl();
                                                    this.opened_stream_id = Utils.parseIntMinusOne(this.liveListDetailAvailableChannels.get(currentWindowIndex2).getStreamId());
                                                    setTitle(this.num + " - " + this.videoTitle);
                                                    this.video_num = Utils.parseIntZero(this.num);
                                                    replayVideo = true;
                                                    break;
                                                } else {
                                                    replayVideo = false;
                                                    break;
                                                }
                                            case 1:
                                                if (this.liveListDetailAvailableSeries != null && this.liveListDetailAvailableSeries.size() > 1 && currentWindowIndex2 <= this.liveListDetailAvailableSeries.size() - 1) {
                                                    this.videoTitle = this.liveListDetailAvailableSeries.get(currentWindowIndex2).getTitle();
                                                    this.container_extension = this.liveListDetailAvailableSeries.get(currentWindowIndex2).getContainerExtension();
                                                    this.num = this.liveListDetailAvailableSeries.get(currentWindowIndex2).getId();
                                                    this.video_num = Utils.parseIntZero(this.num);
                                                    this.opened_stream_id = Utils.parseIntMinusOne(this.num);
                                                    setTitle(this.num + " - " + this.videoTitle);
                                                    this.episode_id = this.liveListDetailAvailableSeries.get(currentWindowIndex2).getId();
                                                    this.video_num = Utils.parseIntZero(this.num);
                                                    replayVideo = true;
                                                    break;
                                                } else {
                                                    replayVideo = false;
                                                    break;
                                                }
                                                //break;
                                            case 2:
                                                if (this.liveListRecording != null && this.liveListRecording.size() > 1 && currentWindowIndex2 <= this.liveListRecording.size() - 1) {
                                                    this.videoTitle = this.liveListRecording.get(currentWindowIndex2).getName();
                                                    this.video_num = currentWindowIndex2;
                                                    this.mFilePath = this.liveListRecording.get(currentWindowIndex2).getAbsolutePath();
                                                    this.video_num = Utils.parseIntZero(String.valueOf(this.video_num));
                                                    setTitle(this.videoTitle);
                                                    replayVideo = true;
                                                    break;
                                                } else {
                                                    replayVideo = false;
                                                    break;
                                                }
                                                //break;
                                        }
                                        this.loginPrefsEditorAudio.clear();
                                        this.loginPrefsEditorAudio.apply();
                                        this.loginPrefsEditorVideo.clear();
                                        this.loginPrefsEditorVideo.apply();
                                        this.loginPrefsEditorSubtitle.clear();
                                        this.loginPrefsEditorSubtitle.apply();
                                        if (replayVideo && this.rq.booleanValue()) {
                                            this.handlerOLD.postDelayed(new Runnable() {
                                                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass6 */

                                                /* JADX WARNING: Removed duplicated region for block: B:17:0x004a  */
                                                /* JADX WARNING: Removed duplicated region for block: B:18:0x0058  */
                                                /* JADX WARNING: Removed duplicated region for block: B:19:0x00c1  */
                                                public void run() {
                                                    char c;
                                                    VideoInfo.getInstance().setStopHandler(false);
                                                    NSTEXOPlayerVODActivity.this.release();
                                                    String str = NSTEXOPlayerVODActivity.this.type;
                                                    int hashCode = str.hashCode();
                                                    if (hashCode != -1068259517) {
                                                        if (hashCode != -905838985) {
                                                            if (hashCode == 993558001 && str.equals("recording")) {
                                                                c = 2;
                                                                switch (c) {
                                                                    case 0:
                                                                        if (NSTEXOPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                                                            NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.m3uVideoURL);
                                                                        } else {
                                                                            NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity = NSTEXOPlayerVODActivity.this;
                                                                            nSTEXOPlayerVODActivity.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId()) + "." + NSTEXOPlayerVODActivity.this.container_extension);
                                                                        }
                                                                        VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId()));
                                                                        VideoInfo.getInstance().setAvailableChannels(NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels);
                                                                        break;
                                                                    case 1:
                                                                        NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity2 = NSTEXOPlayerVODActivity.this;
                                                                        nSTEXOPlayerVODActivity2.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex2)).getId()) + "." + NSTEXOPlayerVODActivity.this.container_extension);
                                                                        VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex2)).getId()));
                                                                        VideoInfo.getInstance().setAvailableSeries(NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries);
                                                                        break;
                                                                    case 2:
                                                                        NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath);
                                                                        break;
                                                                }
                                                                VideoInfo instance = VideoInfo.getInstance();
                                                                instance.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                                                VideoInfo.getInstance().settimeElapsed(0);
                                                                VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                                                VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                                                VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                                                NSTEXOPlayerVODActivity.this.retryCount = 0;
                                                                NSTEXOPlayerVODActivity.this.retrying = false;
                                                                VideoInfo.getInstance().setisVODPlayer(true);
                                                                VideoInfo.getInstance().setProgress(false);
                                                                VideoInfo.getInstance().setNextOrPrevPressed(true);
                                                                NSTEXOPlayerVODActivity.this.initializePlayer();
                                                            }
                                                        } else if (str.equals("series")) {
                                                            c = 1;
                                                            switch (c) {
                                                            }
                                                            VideoInfo instance2 = VideoInfo.getInstance();
                                                            instance2.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                                            VideoInfo.getInstance().settimeElapsed(0);
                                                            VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                                            VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                                            VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                                            NSTEXOPlayerVODActivity.this.retryCount = 0;
                                                            NSTEXOPlayerVODActivity.this.retrying = false;
                                                            VideoInfo.getInstance().setisVODPlayer(true);
                                                            VideoInfo.getInstance().setProgress(false);
                                                            VideoInfo.getInstance().setNextOrPrevPressed(true);
                                                            NSTEXOPlayerVODActivity.this.initializePlayer();
                                                        }
                                                    } else if (str.equals("movies")) {
                                                        c = 0;
                                                        switch (c) {
                                                        }
                                                        VideoInfo instance22 = VideoInfo.getInstance();
                                                        instance22.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                                        VideoInfo.getInstance().settimeElapsed(0);
                                                        VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                                        VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                                        VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                                        NSTEXOPlayerVODActivity.this.retryCount = 0;
                                                        NSTEXOPlayerVODActivity.this.retrying = false;
                                                        VideoInfo.getInstance().setisVODPlayer(true);
                                                        VideoInfo.getInstance().setProgress(false);
                                                        VideoInfo.getInstance().setNextOrPrevPressed(true);
                                                        NSTEXOPlayerVODActivity.this.initializePlayer();
                                                    }
                                                    c = 65535;
                                                    switch (c) {
                                                    }
                                                    VideoInfo instance222 = VideoInfo.getInstance();
                                                    instance222.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                                    VideoInfo.getInstance().settimeElapsed(0);
                                                    VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                                    VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                                    VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                                    NSTEXOPlayerVODActivity.this.retryCount = 0;
                                                    NSTEXOPlayerVODActivity.this.retrying = false;
                                                    VideoInfo.getInstance().setisVODPlayer(true);
                                                    VideoInfo.getInstance().setProgress(false);
                                                    VideoInfo.getInstance().setNextOrPrevPressed(true);
                                                    NSTEXOPlayerVODActivity.this.initializePlayer();
                                                }
                                            }, 200);
                                        }
                                        String str2 = this.type;
                                        hashCode = str2.hashCode();
                                        if (hashCode == -1068259517) {
                                            if (hashCode != -905838985) {
                                                if (hashCode == 993558001) {
                                                    if (str2.equals("recording")) {
                                                        switch (c3) {
                                                            case 0:
                                                                this.currentProgramStreamID = Utils.parseIntMinusOne(this.liveListDetailAvailableChannels.get(currentWindowIndex2).getStreamId());
                                                                if (this.loginPrefsEditor != null) {
                                                                    this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableChannels.get(currentWindowIndex2).getStreamId()));
                                                                    this.loginPrefsEditor.apply();
                                                                    break;
                                                                }
                                                                break;
                                                            case 1:
                                                                VideoInfo.getInstance().setEpisodeId(this.episode_id);
                                                                EpisodesUsingSinglton.getInstance().setEpisodeList(this.liveListDetailAvailableSeries);
                                                                this.currentProgramStreamID = Utils.parseIntMinusOne(this.liveListDetailAvailableSeries.get(currentWindowIndex2).getId());
                                                                if (this.loginPrefsEditor != null) {
                                                                    this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableSeries.get(currentWindowIndex2).getId()));
                                                                    this.loginPrefsEditor.apply();
                                                                    break;
                                                                }
                                                                break;
                                                            case 2:
                                                                this.currentProgramStreamID = Utils.parseIntMinusOne(this.liveListRecording.get(currentWindowIndex2).getAbsolutePath());
                                                                if (this.loginPrefsEditor != null) {
                                                                    this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListRecording.get(currentWindowIndex2).getAbsolutePath()));
                                                                    this.loginPrefsEditor.apply();
                                                                    break;
                                                                }
                                                                break;
                                                        }
                                                        if (this.loginPrefsEditorPosition != null) {
                                                            this.loginPrefsEditorPosition.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, String.valueOf(currentWindowIndex2));
                                                            this.loginPrefsEditorPosition.apply();
                                                            return;
                                                        }
                                                        return;
                                                    }
                                                }
                                            } else if (str2.equals("series")) {
                                                c3 = 1;
                                                switch (c3) {
                                                }
                                                if (this.loginPrefsEditorPosition != null) {
                                                }
                                            }
                                        } else if (str2.equals("movies")) {
                                            c3 = 0;
                                            switch (c3) {
                                            }
                                            if (this.loginPrefsEditorPosition != null) {
                                            }
                                        }
                                        c3 = 65535;
                                        switch (c3) {
                                        }
                                        if (this.loginPrefsEditorPosition != null) {
                                        }
                                    }
                                }
                            } else if (str.equals("series")) {
                                c = 1;
                                switch (c) {
                                }
                                this.loginPrefsEditorAudio.clear();
                                this.loginPrefsEditorAudio.apply();
                                this.loginPrefsEditorVideo.clear();
                                this.loginPrefsEditorVideo.apply();
                                this.loginPrefsEditorSubtitle.clear();
                                this.loginPrefsEditorSubtitle.apply();
                                this.handlerOLD.postDelayed(new Runnable() {
                                    /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass6 */

                                    /* JADX WARNING: Removed duplicated region for block: B:17:0x004a  */
                                    /* JADX WARNING: Removed duplicated region for block: B:18:0x0058  */
                                    /* JADX WARNING: Removed duplicated region for block: B:19:0x00c1  */
                                    public void run() {
                                        char c;
                                        VideoInfo.getInstance().setStopHandler(false);
                                        NSTEXOPlayerVODActivity.this.release();
                                        String str = NSTEXOPlayerVODActivity.this.type;
                                        int hashCode = str.hashCode();
                                        if (hashCode != -1068259517) {
                                            if (hashCode != -905838985) {
                                                if (hashCode == 993558001 && str.equals("recording")) {
                                                    c = 2;
                                                    switch (c) {
                                                        case 0:
                                                            if (NSTEXOPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                                                NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.m3uVideoURL);
                                                            } else {
                                                                NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity = NSTEXOPlayerVODActivity.this;
                                                                nSTEXOPlayerVODActivity.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId()) + "." + NSTEXOPlayerVODActivity.this.container_extension);
                                                            }
                                                            VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId()));
                                                            VideoInfo.getInstance().setAvailableChannels(NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels);
                                                            break;
                                                        case 1:
                                                            NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity2 = NSTEXOPlayerVODActivity.this;
                                                            nSTEXOPlayerVODActivity2.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex2)).getId()) + "." + NSTEXOPlayerVODActivity.this.container_extension);
                                                            VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex2)).getId()));
                                                            VideoInfo.getInstance().setAvailableSeries(NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries);
                                                            break;
                                                        case 2:
                                                            NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath);
                                                            break;
                                                    }
                                                    VideoInfo instance222 = VideoInfo.getInstance();
                                                    instance222.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                                    VideoInfo.getInstance().settimeElapsed(0);
                                                    VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                                    VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                                    VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                                    NSTEXOPlayerVODActivity.this.retryCount = 0;
                                                    NSTEXOPlayerVODActivity.this.retrying = false;
                                                    VideoInfo.getInstance().setisVODPlayer(true);
                                                    VideoInfo.getInstance().setProgress(false);
                                                    VideoInfo.getInstance().setNextOrPrevPressed(true);
                                                    NSTEXOPlayerVODActivity.this.initializePlayer();
                                                }
                                            } else if (str.equals("series")) {
                                                c = 1;
                                                switch (c) {
                                                }
                                                VideoInfo instance2222 = VideoInfo.getInstance();
                                                instance2222.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                                VideoInfo.getInstance().settimeElapsed(0);
                                                VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                                VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                                VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                                NSTEXOPlayerVODActivity.this.retryCount = 0;
                                                NSTEXOPlayerVODActivity.this.retrying = false;
                                                VideoInfo.getInstance().setisVODPlayer(true);
                                                VideoInfo.getInstance().setProgress(false);
                                                VideoInfo.getInstance().setNextOrPrevPressed(true);
                                                NSTEXOPlayerVODActivity.this.initializePlayer();
                                            }
                                        } else if (str.equals("movies")) {
                                            c = 0;
                                            switch (c) {
                                            }
                                            VideoInfo instance22222 = VideoInfo.getInstance();
                                            instance22222.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                            VideoInfo.getInstance().settimeElapsed(0);
                                            VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                            VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                            VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                            NSTEXOPlayerVODActivity.this.retryCount = 0;
                                            NSTEXOPlayerVODActivity.this.retrying = false;
                                            VideoInfo.getInstance().setisVODPlayer(true);
                                            VideoInfo.getInstance().setProgress(false);
                                            VideoInfo.getInstance().setNextOrPrevPressed(true);
                                            NSTEXOPlayerVODActivity.this.initializePlayer();
                                        }
                                        c = 65535;
                                        switch (c) {
                                        }
                                        VideoInfo instance222222 = VideoInfo.getInstance();
                                        instance222222.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                        VideoInfo.getInstance().settimeElapsed(0);
                                        VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                        VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                        VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                        NSTEXOPlayerVODActivity.this.retryCount = 0;
                                        NSTEXOPlayerVODActivity.this.retrying = false;
                                        VideoInfo.getInstance().setisVODPlayer(true);
                                        VideoInfo.getInstance().setProgress(false);
                                        VideoInfo.getInstance().setNextOrPrevPressed(true);
                                        NSTEXOPlayerVODActivity.this.initializePlayer();
                                    }
                                }, 200);
                                String str22 = this.type;
                                hashCode = str22.hashCode();
                                if (hashCode == -1068259517) {
                                }
                                c3 = 65535;
                                switch (c3) {
                                }
                                if (this.loginPrefsEditorPosition != null) {
                                }
                            }
                        } else if (str.equals("movies")) {
                            c = 0;
                            switch (c) {
                            }
                            this.loginPrefsEditorAudio.clear();
                            this.loginPrefsEditorAudio.apply();
                            this.loginPrefsEditorVideo.clear();
                            this.loginPrefsEditorVideo.apply();
                            this.loginPrefsEditorSubtitle.clear();
                            this.loginPrefsEditorSubtitle.apply();
                            this.handlerOLD.postDelayed(new Runnable() {
                                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass6 */

                                /* JADX WARNING: Removed duplicated region for block: B:17:0x004a  */
                                /* JADX WARNING: Removed duplicated region for block: B:18:0x0058  */
                                /* JADX WARNING: Removed duplicated region for block: B:19:0x00c1  */
                                public void run() {
                                    char c;
                                    VideoInfo.getInstance().setStopHandler(false);
                                    NSTEXOPlayerVODActivity.this.release();
                                    String str = NSTEXOPlayerVODActivity.this.type;
                                    int hashCode = str.hashCode();
                                    if (hashCode != -1068259517) {
                                        if (hashCode != -905838985) {
                                            if (hashCode == 993558001 && str.equals("recording")) {
                                                c = 2;
                                                switch (c) {
                                                    case 0:
                                                        if (NSTEXOPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                                            NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.m3uVideoURL);
                                                        } else {
                                                            NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity = NSTEXOPlayerVODActivity.this;
                                                            nSTEXOPlayerVODActivity.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId()) + "." + NSTEXOPlayerVODActivity.this.container_extension);
                                                        }
                                                        VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId()));
                                                        VideoInfo.getInstance().setAvailableChannels(NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels);
                                                        break;
                                                    case 1:
                                                        NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity2 = NSTEXOPlayerVODActivity.this;
                                                        nSTEXOPlayerVODActivity2.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex2)).getId()) + "." + NSTEXOPlayerVODActivity.this.container_extension);
                                                        VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex2)).getId()));
                                                        VideoInfo.getInstance().setAvailableSeries(NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries);
                                                        break;
                                                    case 2:
                                                        NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath);
                                                        break;
                                                }
                                                VideoInfo instance222222 = VideoInfo.getInstance();
                                                instance222222.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                                VideoInfo.getInstance().settimeElapsed(0);
                                                VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                                VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                                VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                                NSTEXOPlayerVODActivity.this.retryCount = 0;
                                                NSTEXOPlayerVODActivity.this.retrying = false;
                                                VideoInfo.getInstance().setisVODPlayer(true);
                                                VideoInfo.getInstance().setProgress(false);
                                                VideoInfo.getInstance().setNextOrPrevPressed(true);
                                                NSTEXOPlayerVODActivity.this.initializePlayer();
                                            }
                                        } else if (str.equals("series")) {
                                            c = 1;
                                            switch (c) {
                                            }
                                            VideoInfo instance2222222 = VideoInfo.getInstance();
                                            instance2222222.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                            VideoInfo.getInstance().settimeElapsed(0);
                                            VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                            VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                            VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                            NSTEXOPlayerVODActivity.this.retryCount = 0;
                                            NSTEXOPlayerVODActivity.this.retrying = false;
                                            VideoInfo.getInstance().setisVODPlayer(true);
                                            VideoInfo.getInstance().setProgress(false);
                                            VideoInfo.getInstance().setNextOrPrevPressed(true);
                                            NSTEXOPlayerVODActivity.this.initializePlayer();
                                        }
                                    } else if (str.equals("movies")) {
                                        c = 0;
                                        switch (c) {
                                        }
                                        VideoInfo instance22222222 = VideoInfo.getInstance();
                                        instance22222222.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                        VideoInfo.getInstance().settimeElapsed(0);
                                        VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                        VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                        VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                        NSTEXOPlayerVODActivity.this.retryCount = 0;
                                        NSTEXOPlayerVODActivity.this.retrying = false;
                                        VideoInfo.getInstance().setisVODPlayer(true);
                                        VideoInfo.getInstance().setProgress(false);
                                        VideoInfo.getInstance().setNextOrPrevPressed(true);
                                        NSTEXOPlayerVODActivity.this.initializePlayer();
                                    }
                                    c = 65535;
                                    switch (c) {
                                    }
                                    VideoInfo instance222222222 = VideoInfo.getInstance();
                                    instance222222222.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                    VideoInfo.getInstance().settimeElapsed(0);
                                    VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                    VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                    VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                    NSTEXOPlayerVODActivity.this.retryCount = 0;
                                    NSTEXOPlayerVODActivity.this.retrying = false;
                                    VideoInfo.getInstance().setisVODPlayer(true);
                                    VideoInfo.getInstance().setProgress(false);
                                    VideoInfo.getInstance().setNextOrPrevPressed(true);
                                    NSTEXOPlayerVODActivity.this.initializePlayer();
                                }
                            }, 200);
                            String str222 = this.type;
                            hashCode = str222.hashCode();
                            if (hashCode == -1068259517) {
                            }
                            c3 = 65535;
                            switch (c3) {
                            }
                            if (this.loginPrefsEditorPosition != null) {
                            }
                        }
                        c = 65535;
                        switch (c) {
                        }
                        this.loginPrefsEditorAudio.clear();
                        this.loginPrefsEditorAudio.apply();
                        this.loginPrefsEditorVideo.clear();
                        this.loginPrefsEditorVideo.apply();
                        this.loginPrefsEditorSubtitle.clear();
                        this.loginPrefsEditorSubtitle.apply();
                        this.handlerOLD.postDelayed(new Runnable() {
                            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass6 */

                            /* JADX WARNING: Removed duplicated region for block: B:17:0x004a  */
                            /* JADX WARNING: Removed duplicated region for block: B:18:0x0058  */
                            /* JADX WARNING: Removed duplicated region for block: B:19:0x00c1  */
                            public void run() {
                                char c;
                                VideoInfo.getInstance().setStopHandler(false);
                                NSTEXOPlayerVODActivity.this.release();
                                String str = NSTEXOPlayerVODActivity.this.type;
                                int hashCode = str.hashCode();
                                if (hashCode != -1068259517) {
                                    if (hashCode != -905838985) {
                                        if (hashCode == 993558001 && str.equals("recording")) {
                                            c = 2;
                                            switch (c) {
                                                case 0:
                                                    if (NSTEXOPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                                        NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.m3uVideoURL);
                                                    } else {
                                                        NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity = NSTEXOPlayerVODActivity.this;
                                                        nSTEXOPlayerVODActivity.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId()) + "." + NSTEXOPlayerVODActivity.this.container_extension);
                                                    }
                                                    VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId()));
                                                    VideoInfo.getInstance().setAvailableChannels(NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels);
                                                    break;
                                                case 1:
                                                    NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity2 = NSTEXOPlayerVODActivity.this;
                                                    nSTEXOPlayerVODActivity2.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex2)).getId()) + "." + NSTEXOPlayerVODActivity.this.container_extension);
                                                    VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex2)).getId()));
                                                    VideoInfo.getInstance().setAvailableSeries(NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries);
                                                    break;
                                                case 2:
                                                    NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath);
                                                    break;
                                            }
                                            VideoInfo instance222222222 = VideoInfo.getInstance();
                                            instance222222222.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                            VideoInfo.getInstance().settimeElapsed(0);
                                            VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                            VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                            VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                            NSTEXOPlayerVODActivity.this.retryCount = 0;
                                            NSTEXOPlayerVODActivity.this.retrying = false;
                                            VideoInfo.getInstance().setisVODPlayer(true);
                                            VideoInfo.getInstance().setProgress(false);
                                            VideoInfo.getInstance().setNextOrPrevPressed(true);
                                            NSTEXOPlayerVODActivity.this.initializePlayer();
                                        }
                                    } else if (str.equals("series")) {
                                        c = 1;
                                        switch (c) {
                                        }
                                        VideoInfo instance2222222222 = VideoInfo.getInstance();
                                        instance2222222222.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                        VideoInfo.getInstance().settimeElapsed(0);
                                        VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                        VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                        VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                        NSTEXOPlayerVODActivity.this.retryCount = 0;
                                        NSTEXOPlayerVODActivity.this.retrying = false;
                                        VideoInfo.getInstance().setisVODPlayer(true);
                                        VideoInfo.getInstance().setProgress(false);
                                        VideoInfo.getInstance().setNextOrPrevPressed(true);
                                        NSTEXOPlayerVODActivity.this.initializePlayer();
                                    }
                                } else if (str.equals("movies")) {
                                    c = 0;
                                    switch (c) {
                                    }
                                    VideoInfo instance22222222222 = VideoInfo.getInstance();
                                    instance22222222222.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                    VideoInfo.getInstance().settimeElapsed(0);
                                    VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                    VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                    VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                    NSTEXOPlayerVODActivity.this.retryCount = 0;
                                    NSTEXOPlayerVODActivity.this.retrying = false;
                                    VideoInfo.getInstance().setisVODPlayer(true);
                                    VideoInfo.getInstance().setProgress(false);
                                    VideoInfo.getInstance().setNextOrPrevPressed(true);
                                    NSTEXOPlayerVODActivity.this.initializePlayer();
                                }
                                c = 65535;
                                switch (c) {
                                }
                                VideoInfo instance222222222222 = VideoInfo.getInstance();
                                instance222222222222.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                VideoInfo.getInstance().settimeElapsed(0);
                                VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                NSTEXOPlayerVODActivity.this.retryCount = 0;
                                NSTEXOPlayerVODActivity.this.retrying = false;
                                VideoInfo.getInstance().setisVODPlayer(true);
                                VideoInfo.getInstance().setProgress(false);
                                VideoInfo.getInstance().setNextOrPrevPressed(true);
                                NSTEXOPlayerVODActivity.this.initializePlayer();
                            }
                        }, 200);
                        String str2222 = this.type;
                        hashCode = str2222.hashCode();
                        if (hashCode == -1068259517) {
                        }
                        c3 = 65535;
                        switch (c3) {
                        }
                        if (this.loginPrefsEditorPosition != null) {
                        }
                    } else {
                        return;
                    }
                } catch (Exception e3) {
                    Log.e("NSTIJPLAYERVODACTIVTY", "exection " + e3);
                    return;
                }
                break;
            case R.id.exo_prevv:
                try {
                    if (VideoInfo.getInstance() != null) {
                        this.vlcprevButton.requestFocus();
                        this.handlerOLD.removeCallbacksAndMessages(null);
                        previous();
                        final int currentWindowIndex3 = VideoInfo.getInstance().getCurrentWindowIndex();
                        String str3 = this.type;
                        int hashCode4 = str3.hashCode();
                        if (hashCode4 != -1068259517) {
                            if (hashCode4 != -905838985) {
                                if (hashCode4 == 993558001) {
                                    if (str3.equals("recording")) {
                                        c2 = 2;
                                        switch (c2) {
                                            case 0:
                                                if (this.liveListDetailAvailableChannels != null && this.liveListDetailAvailableChannels.size() > 1 && currentWindowIndex3 <= this.liveListDetailAvailableChannels.size() - 1) {
                                                    this.videoTitle = this.liveListDetailAvailableChannels.get(currentWindowIndex3).getName();
                                                    this.num = this.liveListDetailAvailableChannels.get(currentWindowIndex3).getNum();
                                                    this.typeofStream = this.liveListDetailAvailableChannels.get(currentWindowIndex3).getStreamType();
                                                    this.container_extension = this.liveListDetailAvailableChannels.get(currentWindowIndex3).getContaiinerExtension();
                                                    this.m3uVideoURL = this.liveListDetailAvailableChannels.get(currentWindowIndex3).getUrl();
                                                    this.opened_stream_id = Utils.parseIntMinusOne(this.liveListDetailAvailableChannels.get(currentWindowIndex3).getStreamId());
                                                    setTitle(this.num + " - " + this.videoTitle);
                                                    this.video_num = Utils.parseIntZero(this.num);
                                                    replayVideo = true;
                                                    break;
                                                } else {
                                                    replayVideo = false;
                                                    break;
                                                }
                                                //break;
                                            case 1:
                                                if (this.liveListDetailAvailableSeries != null && this.liveListDetailAvailableSeries.size() > 1 && currentWindowIndex3 <= this.liveListDetailAvailableSeries.size() - 1) {
                                                    this.videoTitle = this.liveListDetailAvailableSeries.get(currentWindowIndex3).getTitle();
                                                    this.container_extension = this.liveListDetailAvailableSeries.get(currentWindowIndex3).getContainerExtension();
                                                    this.num = this.liveListDetailAvailableSeries.get(currentWindowIndex3).getId();
                                                    this.video_num = Utils.parseIntZero(this.num);
                                                    this.opened_stream_id = Utils.parseIntMinusOne(this.num);
                                                    setTitle(this.num + " - " + this.videoTitle);
                                                    this.episode_id = this.liveListDetailAvailableSeries.get(currentWindowIndex3).getId();
                                                    this.video_num = Utils.parseIntZero(this.num);
                                                    replayVideo = true;
                                                    break;
                                                } else {
                                                    replayVideo = false;
                                                    break;
                                                }
                                               // break;
                                            case 2:
                                                if (this.liveListRecording != null && this.liveListRecording.size() > 1 && currentWindowIndex3 <= this.liveListRecording.size() - 1) {
                                                    String name = this.liveListRecording.get(currentWindowIndex3).getName();
                                                    this.video_num = currentWindowIndex3;
                                                    this.mFilePath = this.liveListRecording.get(currentWindowIndex3).getAbsolutePath();
                                                    this.video_num = Utils.parseIntZero(String.valueOf(this.video_num));
                                                    setTitle(name);
                                                    replayVideo = true;
                                                    break;
                                                } else {
                                                    replayVideo = false;
                                                    break;
                                                }
                                               // break;
                                        }
                                        this.video_num = Utils.parseIntZero(this.num);
                                        this.loginPrefsEditorAudio.clear();
                                        this.loginPrefsEditorAudio.apply();
                                        this.loginPrefsEditorVideo.clear();
                                        this.loginPrefsEditorVideo.apply();
                                        this.loginPrefsEditorSubtitle.clear();
                                        this.loginPrefsEditorSubtitle.apply();
                                        if (replayVideo && this.rq.booleanValue()) {
                                            this.handlerOLD.postDelayed(new Runnable() {
                                                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass5 */

                                                public void run() {
                                                    VideoInfo.getInstance().setStopHandler(false);
                                                    NSTEXOPlayerVODActivity.this.release();
                                                    if (NSTEXOPlayerVODActivity.this.type.equals("movies")) {
                                                        if (NSTEXOPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                                            NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.m3uVideoURL);
                                                        } else {
                                                            NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity = NSTEXOPlayerVODActivity.this;
                                                            nSTEXOPlayerVODActivity.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex3)).getStreamId()) + "." + NSTEXOPlayerVODActivity.this.container_extension);
                                                        }
                                                        VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex3)).getStreamId()));
                                                        VideoInfo.getInstance().setAvailableChannels(NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels);
                                                        NSTEXOPlayerVODActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex3)).getStreamId());
                                                    } else if (NSTEXOPlayerVODActivity.this.type.equals("series")) {
                                                        NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity2 = NSTEXOPlayerVODActivity.this;
                                                        nSTEXOPlayerVODActivity2.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex3)).getId()) + "." + NSTEXOPlayerVODActivity.this.container_extension);
                                                        VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex3)).getId()));
                                                        VideoInfo.getInstance().setAvailableSeries(NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries);
                                                        NSTEXOPlayerVODActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex3)).getId());
                                                    } else if (NSTEXOPlayerVODActivity.this.type.equals("recording")) {
                                                        NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath);
                                                    }
                                                    VideoInfo instance = VideoInfo.getInstance();
                                                    instance.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                                    VideoInfo.getInstance().settimeElapsed(0);
                                                    VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                                    VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex3);
                                                    VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                                    NSTEXOPlayerVODActivity.this.retryCount = 0;
                                                    NSTEXOPlayerVODActivity.this.retrying = false;
                                                    VideoInfo.getInstance().setisVODPlayer(true);
                                                    VideoInfo.getInstance().setProgress(false);
                                                    VideoInfo.getInstance().setNextOrPrevPressed(true);
                                                    NSTEXOPlayerVODActivity.this.initializePlayer();
                                                }
                                            }, 200);
                                        }
                                        String str4 = this.type;
                                        hashCode2 = str4.hashCode();
                                        if (hashCode2 == -1068259517) {
                                            if (hashCode2 != -905838985) {
                                                if (hashCode2 == 993558001) {
                                                    if (str4.equals("recording")) {
                                                        switch (c3) {
                                                            case 0:
                                                                if (this.loginPrefsEditor != null) {
                                                                    this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableChannels.get(currentWindowIndex3).getStreamId()));
                                                                    this.loginPrefsEditor.apply();
                                                                    break;
                                                                }
                                                                break;
                                                            case 1:
                                                                VideoInfo.getInstance().setEpisodeId(this.episode_id);
                                                                EpisodesUsingSinglton.getInstance().setEpisodeList(this.liveListDetailAvailableSeries);
                                                                if (this.loginPrefsEditor != null) {
                                                                    this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableSeries.get(currentWindowIndex3).getId()));
                                                                    this.loginPrefsEditor.apply();
                                                                    break;
                                                                }
                                                                break;
                                                            case 2:
                                                                this.currentProgramStreamID = Utils.parseIntMinusOne(this.liveListRecording.get(currentWindowIndex3).getAbsolutePath());
                                                                if (this.loginPrefsEditor != null) {
                                                                    this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListRecording.get(currentWindowIndex3).getAbsolutePath()));
                                                                    this.loginPrefsEditor.apply();
                                                                    break;
                                                                }
                                                                break;
                                                        }
                                                        if (this.loginPrefsEditorPosition != null) {
                                                            this.loginPrefsEditorPosition.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, String.valueOf(currentWindowIndex3));
                                                            this.loginPrefsEditorPosition.apply();
                                                            return;
                                                        }
                                                        return;
                                                    }
                                                }
                                            } else if (str4.equals("series")) {
                                                c3 = 1;
                                                switch (c3) {
                                                }
                                                if (this.loginPrefsEditorPosition != null) {
                                                }
                                            }
                                        } else if (str4.equals("movies")) {
                                            c3 = 0;
                                            switch (c3) {
                                            }
                                            if (this.loginPrefsEditorPosition != null) {
                                            }
                                        }
                                        c3 = 65535;
                                        switch (c3) {
                                        }
                                        if (this.loginPrefsEditorPosition != null) {
                                        }
                                    }
                                }
                            } else if (str3.equals("series")) {
                                c2 = 1;
                                switch (c2) {
                                }
                                this.video_num = Utils.parseIntZero(this.num);
                                this.loginPrefsEditorAudio.clear();
                                this.loginPrefsEditorAudio.apply();
                                this.loginPrefsEditorVideo.clear();
                                this.loginPrefsEditorVideo.apply();
                                this.loginPrefsEditorSubtitle.clear();
                                this.loginPrefsEditorSubtitle.apply();
                                this.handlerOLD.postDelayed(new Runnable() {
                                    /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass5 */

                                    public void run() {
                                        VideoInfo.getInstance().setStopHandler(false);
                                        NSTEXOPlayerVODActivity.this.release();
                                        if (NSTEXOPlayerVODActivity.this.type.equals("movies")) {
                                            if (NSTEXOPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                                NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.m3uVideoURL);
                                            } else {
                                                NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity = NSTEXOPlayerVODActivity.this;
                                                nSTEXOPlayerVODActivity.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex3)).getStreamId()) + "." + NSTEXOPlayerVODActivity.this.container_extension);
                                            }
                                            VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex3)).getStreamId()));
                                            VideoInfo.getInstance().setAvailableChannels(NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels);
                                            NSTEXOPlayerVODActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex3)).getStreamId());
                                        } else if (NSTEXOPlayerVODActivity.this.type.equals("series")) {
                                            NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity2 = NSTEXOPlayerVODActivity.this;
                                            nSTEXOPlayerVODActivity2.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex3)).getId()) + "." + NSTEXOPlayerVODActivity.this.container_extension);
                                            VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex3)).getId()));
                                            VideoInfo.getInstance().setAvailableSeries(NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries);
                                            NSTEXOPlayerVODActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex3)).getId());
                                        } else if (NSTEXOPlayerVODActivity.this.type.equals("recording")) {
                                            NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath);
                                        }
                                        VideoInfo instance = VideoInfo.getInstance();
                                        instance.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                        VideoInfo.getInstance().settimeElapsed(0);
                                        VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                        VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex3);
                                        VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                        NSTEXOPlayerVODActivity.this.retryCount = 0;
                                        NSTEXOPlayerVODActivity.this.retrying = false;
                                        VideoInfo.getInstance().setisVODPlayer(true);
                                        VideoInfo.getInstance().setProgress(false);
                                        VideoInfo.getInstance().setNextOrPrevPressed(true);
                                        NSTEXOPlayerVODActivity.this.initializePlayer();
                                    }
                                }, 200);
                                String str42 = this.type;
                                hashCode2 = str42.hashCode();
                                if (hashCode2 == -1068259517) {
                                }
                                c3 = 65535;
                                switch (c3) {
                                }
                                if (this.loginPrefsEditorPosition != null) {
                                }
                            }
                        } else if (str3.equals("movies")) {
                            c2 = 0;
                            switch (c2) {
                            }
                            this.video_num = Utils.parseIntZero(this.num);
                            this.loginPrefsEditorAudio.clear();
                            this.loginPrefsEditorAudio.apply();
                            this.loginPrefsEditorVideo.clear();
                            this.loginPrefsEditorVideo.apply();
                            this.loginPrefsEditorSubtitle.clear();
                            this.loginPrefsEditorSubtitle.apply();
                            this.handlerOLD.postDelayed(new Runnable() {
                                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass5 */

                                public void run() {
                                    VideoInfo.getInstance().setStopHandler(false);
                                    NSTEXOPlayerVODActivity.this.release();
                                    if (NSTEXOPlayerVODActivity.this.type.equals("movies")) {
                                        if (NSTEXOPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                            NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.m3uVideoURL);
                                        } else {
                                            NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity = NSTEXOPlayerVODActivity.this;
                                            nSTEXOPlayerVODActivity.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex3)).getStreamId()) + "." + NSTEXOPlayerVODActivity.this.container_extension);
                                        }
                                        VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex3)).getStreamId()));
                                        VideoInfo.getInstance().setAvailableChannels(NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels);
                                        NSTEXOPlayerVODActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex3)).getStreamId());
                                    } else if (NSTEXOPlayerVODActivity.this.type.equals("series")) {
                                        NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity2 = NSTEXOPlayerVODActivity.this;
                                        nSTEXOPlayerVODActivity2.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex3)).getId()) + "." + NSTEXOPlayerVODActivity.this.container_extension);
                                        VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex3)).getId()));
                                        VideoInfo.getInstance().setAvailableSeries(NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries);
                                        NSTEXOPlayerVODActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex3)).getId());
                                    } else if (NSTEXOPlayerVODActivity.this.type.equals("recording")) {
                                        NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath);
                                    }
                                    VideoInfo instance = VideoInfo.getInstance();
                                    instance.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                    VideoInfo.getInstance().settimeElapsed(0);
                                    VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                    VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex3);
                                    VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                    NSTEXOPlayerVODActivity.this.retryCount = 0;
                                    NSTEXOPlayerVODActivity.this.retrying = false;
                                    VideoInfo.getInstance().setisVODPlayer(true);
                                    VideoInfo.getInstance().setProgress(false);
                                    VideoInfo.getInstance().setNextOrPrevPressed(true);
                                    NSTEXOPlayerVODActivity.this.initializePlayer();
                                }
                            }, 200);
                            String str422 = this.type;
                            hashCode2 = str422.hashCode();
                            if (hashCode2 == -1068259517) {
                            }
                            c3 = 65535;
                            switch (c3) {
                            }
                            if (this.loginPrefsEditorPosition != null) {
                            }
                        }
                        c2 = 65535;
                        switch (c2) {
                        }
                        this.video_num = Utils.parseIntZero(this.num);
                        this.loginPrefsEditorAudio.clear();
                        this.loginPrefsEditorAudio.apply();
                        this.loginPrefsEditorVideo.clear();
                        this.loginPrefsEditorVideo.apply();
                        this.loginPrefsEditorSubtitle.clear();
                        this.loginPrefsEditorSubtitle.apply();
                        this.handlerOLD.postDelayed(new Runnable() {
                            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass5 */

                            public void run() {
                                VideoInfo.getInstance().setStopHandler(false);
                                NSTEXOPlayerVODActivity.this.release();
                                if (NSTEXOPlayerVODActivity.this.type.equals("movies")) {
                                    if (NSTEXOPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                        NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.m3uVideoURL);
                                    } else {
                                        NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity = NSTEXOPlayerVODActivity.this;
                                        nSTEXOPlayerVODActivity.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex3)).getStreamId()) + "." + NSTEXOPlayerVODActivity.this.container_extension);
                                    }
                                    VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex3)).getStreamId()));
                                    VideoInfo.getInstance().setAvailableChannels(NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels);
                                    NSTEXOPlayerVODActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex3)).getStreamId());
                                } else if (NSTEXOPlayerVODActivity.this.type.equals("series")) {
                                    NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity2 = NSTEXOPlayerVODActivity.this;
                                    nSTEXOPlayerVODActivity2.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex3)).getId()) + "." + NSTEXOPlayerVODActivity.this.container_extension);
                                    VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex3)).getId()));
                                    VideoInfo.getInstance().setAvailableSeries(NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries);
                                    NSTEXOPlayerVODActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTEXOPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex3)).getId());
                                } else if (NSTEXOPlayerVODActivity.this.type.equals("recording")) {
                                    NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath);
                                }
                                VideoInfo instance = VideoInfo.getInstance();
                                instance.setTitle(NSTEXOPlayerVODActivity.this.num + " - " + NSTEXOPlayerVODActivity.this.videoTitle);
                                VideoInfo.getInstance().settimeElapsed(0);
                                VideoInfo.getInstance().settypeofstream(NSTEXOPlayerVODActivity.this.typeofStream);
                                VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex3);
                                VideoInfo.getInstance().setvideoNum(NSTEXOPlayerVODActivity.this.video_num);
                                NSTEXOPlayerVODActivity.this.retryCount = 0;
                                NSTEXOPlayerVODActivity.this.retrying = false;
                                VideoInfo.getInstance().setisVODPlayer(true);
                                VideoInfo.getInstance().setProgress(false);
                                VideoInfo.getInstance().setNextOrPrevPressed(true);
                                NSTEXOPlayerVODActivity.this.initializePlayer();
                            }
                        }, 200);
                        String str4222 = this.type;
                        hashCode2 = str4222.hashCode();
                        if (hashCode2 == -1068259517) {
                        }
                        c3 = 65535;
                        switch (c3) {
                        }
                        if (this.loginPrefsEditorPosition != null) {
                        }
                    } else {
                        return;
                    }
                } catch (Exception e4) {
                    Log.e("NSTIJPLAYERVODACTIVTY", "exection " + e4);
                    return;
                }
                break;
            case R.id.exo_reww:
                try {
                    if (VideoInfo.getInstance() != null && this.player != null) {
                        this.handlerSeekbarForwardRewind.removeCallbacksAndMessages(null);
                        if (this.type.equals("catch_up")) {
                            this.seekBarMilliseconds -= DateTimeConstants.MILLIS_PER_MINUTE;
                        } else {
                            this.seekBarMilliseconds -= 10000;
                        }
                        if (this.seekBarMilliseconds > 0) {
                            this.tv_seek_overlay.setText("+" + (this.seekBarMilliseconds / 1000) + "s");
                        } else {
                            this.tv_seek_overlay.setText((this.seekBarMilliseconds / 1000) + "s");
                        }
                        this.ll_seek_overlay.setVisibility(0);
                        this.handlerSeekbarForwardRewind.postDelayed(new Runnable() {
                            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass8 */

                            public void run() {
                                if (NSTEXOPlayerVODActivity.this.player.getCurrentPosition() + ((long) NSTEXOPlayerVODActivity.this.seekBarMilliseconds) > 0) {
                                    NSTEXOPlayerVODActivity.this.player.seekTo(NSTEXOPlayerVODActivity.this.player.getCurrentPosition() + ((long) NSTEXOPlayerVODActivity.this.seekBarMilliseconds));
                                } else {
                                    NSTEXOPlayerVODActivity.this.player.seekTo(0);
                                }
                                NSTEXOPlayerVODActivity.this.handlerSeekbar.removeCallbacksAndMessages(null);
                                NSTEXOPlayerVODActivity.this.handlerSeekbar.postDelayed(new Runnable() {
                                    /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass8.AnonymousClass1 */

                                    public void run() {
                                        int unused = NSTEXOPlayerVODActivity.this.seekBarMilliseconds = 0;
                                        NSTEXOPlayerVODActivity.this.ll_seek_overlay.setVisibility(8);
                                    }
                                }, 3000);
                            }
                        }, 1000);
                        return;
                    }
                    return;
                } catch (Exception e5) {
                    Log.e("NSTIJPLAYERVODACTIVTY", "exection " + e5);
                    return;
                }
            case R.id.exo_subtitlee:
                try {
                    SubTitlePopup(this.context);
                    return;
                } catch (Exception e6) {
                    Log.e("NSTIJPLAYER", "exection " + e6);
                    return;
                }
            default:
                return;
        }
    }

    public void toggleView(View view) {
        if (view.getVisibility() == 8) {
            view.setVisibility(0);
        } else if (view.getVisibility() == 0) {
            view.setVisibility(8);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0064  */
    private void previous() {
        char c;
        int currentWindowIndex2 = VideoInfo.getInstance().getCurrentWindowIndex();
        String str = this.type;
        int hashCode = str.hashCode();
        if (hashCode != -1068259517) {
            if (hashCode != -905838985) {
                if (hashCode == 993558001 && str.equals("recording")) {
                    c = 2;
                    switch (c) {
                        case 0:
                            if (currentWindowIndex2 == 0) {
                                VideoInfo.getInstance().setCurrentWindowIndex(this.liveListDetailAvailableChannels.size() - 1);
                                return;
                            }
                            break;
                        case 1:
                            if (currentWindowIndex2 == 0) {
                                VideoInfo.getInstance().setCurrentWindowIndex(this.liveListDetailAvailableSeries.size() - 1);
                                return;
                            }
                            break;
                        case 2:
                            if (currentWindowIndex2 == 0) {
                                VideoInfo.getInstance().setCurrentWindowIndex(this.liveListRecording.size() - 1);
                                return;
                            }
                            break;
                    }
                    VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2 - 1);
                }
            } else if (str.equals("series")) {
                c = 1;
                switch (c) {
                }
                VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2 - 1);
            }
        } else if (str.equals("movies")) {
            c = 0;
            switch (c) {
            }
            VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2 - 1);
        }
        c = 65535;
        switch (c) {
        }
        VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2 - 1);
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0065  */
    private void next() {
        char c;
        int currentWindowIndex2 = VideoInfo.getInstance().getCurrentWindowIndex();
        String str = this.type;
        int hashCode = str.hashCode();
        if (hashCode != -1068259517) {
            if (hashCode != -905838985) {
                if (hashCode == 993558001 && str.equals("recording")) {
                    c = 2;
                    switch (c) {
                        case 0:
                            if (currentWindowIndex2 == this.liveListDetailAvailableChannels.size() - 1) {
                                VideoInfo.getInstance().setCurrentWindowIndex(0);
                                return;
                            }
                            break;
                        case 1:
                            if (currentWindowIndex2 == this.liveListDetailAvailableSeries.size() - 1) {
                                VideoInfo.getInstance().setCurrentWindowIndex(0);
                                return;
                            }
                            break;
                        case 2:
                            if (currentWindowIndex2 == this.liveListRecording.size() - 1) {
                                VideoInfo.getInstance().setCurrentWindowIndex(0);
                                return;
                            }
                            break;
                    }
                    VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2 + 1);
                }
            } else if (str.equals("series")) {
                c = 1;
                switch (c) {
                }
                VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2 + 1);
            }
        } else if (str.equals("movies")) {
            c = 0;
            switch (c) {
            }
            VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2 + 1);
        }
        c = 65535;
        switch (c) {
        }
        VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2 + 1);
    }

    public int toggleAspectRatio() {
        this.mCurrentAspectRatioIndex++;
        this.sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.sharedPrefEditor = this.sharedPreferences.edit();
        this.mCurrentAspectRatioIndex %= s_allAspectRatio.length;
        this.mCurrentAspectRatio = s_allAspectRatio[this.mCurrentAspectRatioIndex];
        if (this.playerView != null) {
            final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_aspect_ratio);
            TextView textView = (TextView) findViewById(R.id.app_aspect_ratio_text);
            this.playerView.setResizeMode(this.mCurrentAspectRatio);
            if (this.mCurrentAspectRatioIndex == 0) {
                textView.setText(getResources().getString(R.string.exo_fit));
            } else if (this.mCurrentAspectRatioIndex == 1) {
                textView.setText(getResources().getString(R.string.exo_fixed_width));
            } else if (this.mCurrentAspectRatioIndex == 2) {
                textView.setText(getResources().getString(R.string.exo_fixed_height));
            } else if (this.mCurrentAspectRatioIndex == 3) {
                textView.setText(getResources().getString(R.string.exo_fill));
            } else if (this.mCurrentAspectRatioIndex == 4) {
                textView.setText(getResources().getString(R.string.exo_zoom));
            }
            this.sharedPrefEditor.putInt(AppConst.ASPECT_RATIO, this.mCurrentAspectRatioIndex);
            this.sharedPrefEditor.apply();
            linearLayout.setVisibility(0);
            this.handlerAspectRatio.removeCallbacksAndMessages(null);
            this.handlerAspectRatio.postDelayed(new Runnable() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass9 */

                public void run() {
                    linearLayout.setVisibility(8);
                }
            }, 3000);
        }
        return this.mCurrentAspectRatio;
    }

    public void hidePopup() {
        if (this.changeSortPopUp != null) {
            this.changeSortPopUp.dismiss();
        }
    }

    @SuppressLint({"ResourceType"})
    public void AutoPlayNextPopup(Context context2, String str) {
        View inflate = ((LayoutInflater) context2.getSystemService("layout_inflater")).inflate((int) R.layout.auto_play_popup_layout, this.rl_epg_layout);
        this.autoPlayNextPopup = new PopupWindow(context2);
        this.autoPlayNextPopup.setContentView(inflate);
        this.autoPlayNextPopup.setWidth(-1);
        this.autoPlayNextPopup.setHeight(-1);
        this.autoPlayNextPopup.setFocusable(true);
        this.autoPlayNextPopup.setOutsideTouchable(false);
        ((TextView) inflate.findViewById(R.id.tv_episode_title)).setText(str);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.ll_determinate);
        linearLayout.requestFocus();
        this.fabButton = (FabButton) inflate.findViewById(R.id.determinate);
        this.cancel_autoplay = (Button) inflate.findViewById(R.id.cancel_autoplay);
        this.next_background = (RelativeLayout) inflate.findViewById(R.id.rl_next_episode);
        this.autoPlayHandler = new Handler();
        this.fabButton.showShadow(false);
        this.currentProgress = 0;
        this.fabButton.showProgress(true);
        this.fabButton.setProgress((float) this.currentProgress);
        getRunnable();
        this.autoPlayNextPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass10 */

            public void onDismiss() {
                NSTEXOPlayerVODActivity.this.cancel_autoplay.performClick();
                NSTEXOPlayerVODActivity.this.hideSystemUi();
            }
        });
        this.cancel_autoplay.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass11 */

            public void onClick(View view) {
                NSTEXOPlayerVODActivity.this.hideAutoPlayScreen();
            }
        });
        this.fabButton.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass12 */

            public void onClick(View view) {
                NSTEXOPlayerVODActivity.this.playNextEpisode();
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass13 */

            public void onClick(View view) {
                NSTEXOPlayerVODActivity.this.playNextEpisode();
            }
        });
        this.autoPlayNextPopup.showAtLocation(inflate, 1, 0, 0);
    }

    private void getRunnable() {
        boolean[] zArr = {false};
        this.autoPlayRunnable = new Runnable() {
            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass14 */

            public void run() {
                int unused = NSTEXOPlayerVODActivity.this.currentProgress = NSTEXOPlayerVODActivity.this.currentProgress + 1;
                NSTEXOPlayerVODActivity.this.fabButton.setProgress((float) NSTEXOPlayerVODActivity.this.currentProgress);
                if (NSTEXOPlayerVODActivity.this.currentProgress <= 140) {
                    NSTEXOPlayerVODActivity.this.autoPlayHandler.postDelayed(NSTEXOPlayerVODActivity.this.autoPlayRunnable, 70);
                }
                if (NSTEXOPlayerVODActivity.this.currentProgress == 120) {
                    NSTEXOPlayerVODActivity.this.playNextEpisode();
                }
            }
        };
        if (!zArr[0]) {
            this.autoPlayRunnable.run();
        }
    }

    public void playNextEpisode() {
        hideAutoPlayScreen();
        updateStartPosition();
        this.mActivity.findViewById(R.id.exo_nextt).performClick();
    }

    public void hideAutoPlayScreen() {
        if (!(this.autoPlayHandler == null || this.autoPlayRunnable == null)) {
            this.autoPlayHandler.removeCallbacks(this.autoPlayRunnable);
        }
        if (this.autoPlayNextPopup != null) {
            this.autoPlayNextPopup.dismiss();
        }
        if (this.playerView != null) {
            this.playerView.showController();
        }
    }

    @SuppressLint({"ResourceType"})
    private void SubTitlePopup(Context context2) {
        View inflate = ((LayoutInflater) context2.getSystemService("layout_inflater")).inflate((int) R.layout.subtitle_popup_layout, this.rl_epg_layout);
        this.changeSortPopUp = new PopupWindow(context2);
        this.changeSortPopUp.setContentView(inflate);
        this.changeSortPopUp.setWidth(-1);
        this.changeSortPopUp.setHeight(-1);
        this.changeSortPopUp.setFocusable(true);
        this.changeSortPopUp.setOnDismissListener(new PopupWindow.OnDismissListener() {
            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass15 */

            public void onDismiss() {
                NSTEXOPlayerVODActivity.this.hideSystemUi();
            }
        });
        MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo = this.trackSelector.getCurrentMappedTrackInfo();
        if (currentMappedTrackInfo != null) {
            this.audio_tracks = (RelativeLayout) inflate.findViewById(R.id.audio_tracks);
            this.video_tracks = (RelativeLayout) inflate.findViewById(R.id.video_tracks);
            this.subtitle_tracks = (RelativeLayout) inflate.findViewById(R.id.subtitle_tracks);
            this.ll_close = (RelativeLayout) inflate.findViewById(R.id.ll_close);
            for (int i = 0; i < currentMappedTrackInfo.getRendererCount(); i++) {
                if (currentMappedTrackInfo.getTrackGroups(i).length != 0) {
                    switch (this.player.getRendererType(i)) {
                        case 1:
                            this.audio_tracks.setTag(Integer.valueOf(i));
                            continue;
                        case 2:
                            this.video_tracks.setTag(Integer.valueOf(i));
                            continue;
                        case 3:
                            this.subtitle_tracks.setTag(Integer.valueOf(i));
                            continue;
                    }
                }
            }
            this.video_tracks.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass16 */

                public void onClick(View view) {
                    NSTEXOPlayerVODActivity.this.showTracks(view);
                }
            });
            this.audio_tracks.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass17 */

                public void onClick(View view) {
                    NSTEXOPlayerVODActivity.this.showTracks(view);
                }
            });
            this.subtitle_tracks.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass18 */

                public void onClick(View view) {
                    NSTEXOPlayerVODActivity.this.showTracks(view);
                }
            });
            this.ll_close.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass19 */

                public void onClick(View view) {
                    NSTEXOPlayerVODActivity.this.hidePopup();
                    NSTEXOPlayerVODActivity.this.hideSystemUi();
                }
            });
            this.changeSortPopUp.showAtLocation(inflate, 1, 0, 0);
        }
    }

    public void hideSystemUi() {
        try {
            if (this.playerView != null) {
                this.playerView.setSystemUiVisibility(4871);
            }
        } catch (Exception unused) {
        }
    }

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        boolean z = keyEvent.getAction() == 0;
        if (keyCode != 82) {
            if (this.player != null) {
                if (!this.playerView.isControllerVisible() && !z && (keyCode == 23 || keyCode == 66)) {
                    if (this.player != null) {
                        if (this.playerView.isControllerVisible()) {
                            this.playerView.hideController();
                        } else {
                            this.playerView.showController();
                            findViewById(R.id.exo_pause).requestFocus();
                            findViewById(R.id.exo_play).requestFocus();
                        }
                    }
                    return true;
                } else if (z) {
                    if (keyCode != 274) {
                        if (keyCode != 90) {
                            if (keyCode == 275 || keyCode == 89) {
                                seekbackward();
                                return true;
                            }
                        }
                    }
                    seekforward();
                    return true;
                }
            }
            return super.dispatchKeyEvent(keyEvent);
        } else if (!z) {
            return onKeyUp(keyCode, keyEvent);
        } else {
            try {
                return onKeyDown(keyCode, keyEvent);
            } catch (Exception unused) {
            }
        }
        //ToDo: return...
        return z;
    }

    private void seekforward() {
        try {
            if (this.rq.booleanValue() && this.player != null) {
                this.playerView.showController();
                findViewById(R.id.exo_ffwdd).requestFocus();
                findViewById(R.id.exo_ffwdd).performClick();
            }
        } catch (Exception unused) {
        }
    }

    private void seekbackward() {
        try {
            if (this.rq.booleanValue() && this.player != null) {
                this.playerView.showController();
                findViewById(R.id.exo_reww).requestFocus();
                findViewById(R.id.exo_reww).performClick();
            }
        } catch (Exception unused) {
        }
    }

    private void togglePlayPause() {
        try {
            if (this.rq.booleanValue() && this.player != null) {
                if (this.playerView.isControllerVisible()) {
                    this.playerView.hideController();
                    return;
                }
                this.playerView.showController();
                if (this.player.getPlayWhenReady()) {
                    findViewById(R.id.exo_pause).requestFocus();
                    findViewById(R.id.exo_pause).performClick();
                    return;
                }
                findViewById(R.id.exo_play).requestFocus();
                findViewById(R.id.exo_play).performClick();
            }
        } catch (Exception unused) {
        }
    }

    private void play() {
        try {
            if (this.rq.booleanValue() && this.player != null) {
                if (this.playerView.isControllerVisible()) {
                    this.playerView.hideController();
                    return;
                }
                this.playerView.showController();
                findViewById(R.id.exo_play).requestFocus();
                findViewById(R.id.exo_play).performClick();
            }
        } catch (Exception unused) {
        }
    }

    private void pause() {
        try {
            if (this.rq.booleanValue() && this.player != null) {
                if (this.playerView.isControllerVisible()) {
                    this.playerView.hideController();
                    return;
                }
                this.playerView.showController();
                findViewById(R.id.exo_pause).requestFocus();
                findViewById(R.id.exo_pause).performClick();
            }
        } catch (Exception unused) {
        }
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        keyEvent.getRepeatCount();
        if (this.rl_next_episode != null && this.rl_next_episode.getVisibility() == 0) {
            return super.onKeyUp(i, keyEvent);
        }
        switch (i) {
            case 62:
            case 79:
            case 85:
                togglePlayPause();
                return true;
            case 86:
            case 127:
                pause();
                return true;
            case 126:
                play();
                return true;
            default:
                return super.onKeyUp(i, keyEvent);
        }
    }

    @Override // android.support.v7.app.AppCompatActivity
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        keyEvent.getRepeatCount();
        keyEvent.getAction();
        if (this.rl_next_episode != null && this.rl_next_episode.getVisibility() == 0) {
            return super.onKeyDown(i, keyEvent);
        }
        switch (i) {
            case 19:
            case 166:
                findViewById(R.id.exo_nextt).performClick();
                return true;
            case 20:
            case 167:
                findViewById(R.id.exo_prevv).performClick();
                return true;
            default:
                return super.onKeyDown(i, keyEvent);
        }
    }

    public void showTracks(View view) {
        MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo = this.trackSelector.getCurrentMappedTrackInfo();
        if (currentMappedTrackInfo != null) {
            String str = "";
            if (view.getTag() != null) {
                int intValue = ((Integer) view.getTag()).intValue();
                int rendererType = currentMappedTrackInfo.getRendererType(intValue);
                boolean z = rendererType == 2 || (rendererType == 1 && currentMappedTrackInfo.getTypeSupport(2) == 0);
                if (view.getId() == R.id.video_tracks) {
                    str = getResources().getString(R.string.video_tracks);
                } else if (view.getId() == R.id.audio_tracks) {
                    str = getResources().getString(R.string.audio_tracks);
                } else if (view.getId() == R.id.subtitle_tracks) {
                    str = getResources().getString(R.string.subtitles_tracks);
                }
                Pair<android.app.AlertDialog, TrackSelectionView> dialog = TrackSelectionView.getDialog(this.mActivity, str, this.trackSelector, intValue);
                ((TrackSelectionView) dialog.second).setShowDisableOption(true);
                ((TrackSelectionView) dialog.second).setAllowAdaptiveSelections(z);
                ((android.app.AlertDialog) dialog.first).show();
            }
        }
    }

    @Override // com.nst.yourname.view.interfaces.VodInterface
    public void vodInfo(VodInfoCallback vodInfoCallback) {
        List<String> backdropPath = vodInfoCallback.getInfo().getBackdropPath();
        if (backdropPath != null && backdropPath.size() > 0) {
            try {
                Glide.with(getApplicationContext()).load(backdropPath.get(new Random().nextInt(backdropPath.size()))).asBitmap().into(new SimpleTarget<Bitmap>() {
                    /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass20 */

                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                        if (NSTEXOPlayerVODActivity.this.next_background != null) {
                            NSTEXOPlayerVODActivity.this.next_background.setBackground(bitmapDrawable);
                        }
                    }
                });
            } catch (Exception unused) {
            }
        }
    }

    private class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private final View view;

        public OnFocusChangeAccountListener(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            float f = 1.04f;
            float f2 = 1.02f;
            if (z) {
                Log.e("id is", "" + this.view.getTag());
                if (this.view == null || this.view.getTag() == null || !this.view.getTag().equals("2")) {
                    if (this.view == null || !this.view.getTag().equals("15")) {
                        if (!z) {
                            f2 = 1.0f;
                        }
                        performScaleXAnimation(f2);
                        performScaleYAnimation(f2);
                    } else {
                        if (!z) {
                            f = 1.0f;
                        }
                        performScaleXAnimation(f);
                        performScaleYAnimation(f);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("1") || NSTEXOPlayerVODActivity.this.negativeButton == null)) {
                        NSTEXOPlayerVODActivity.this.negativeButton.setBackgroundResource(R.drawable.back_btn_effect);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("9"))) {
                        NSTEXOPlayerVODActivity.this.savePasswordBT.setBackgroundResource(R.drawable.back_btn_effect);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID))) {
                        NSTEXOPlayerVODActivity.this.closedBT.setBackgroundResource(R.drawable.logout_btn_effect);
                    }
                    if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("15")) {
                        NSTEXOPlayerVODActivity.this.iv_cancel.setImageDrawable(NSTEXOPlayerVODActivity.this.getResources().getDrawable(R.drawable.ic_cancel_focus));
                        NSTEXOPlayerVODActivity.this.savePasswordBT.setBackgroundResource(R.drawable.black_button_dark);
                        NSTEXOPlayerVODActivity.this.closedBT.setBackgroundResource(R.drawable.black_button_dark);
                        return;
                    }
                    return;
                }
                view2.setBackground(NSTEXOPlayerVODActivity.this.getResources().getDrawable(R.drawable.selector_checkbox));
            } else if (!z) {
                if (this.view.getTag().equals("15")) {
                    if (!z) {
                        f = 1.0f;
                    }
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    performAlphaAnimation(z);
                } else {
                    if (!z) {
                        f2 = 1.0f;
                    }
                    performScaleXAnimation(f2);
                    performScaleYAnimation(f2);
                    performAlphaAnimation(z);
                }
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("1") || NSTEXOPlayerVODActivity.this.negativeButton == null)) {
                    NSTEXOPlayerVODActivity.this.negativeButton.setBackgroundResource(R.drawable.black_button_dark);
                }
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("9"))) {
                    NSTEXOPlayerVODActivity.this.savePasswordBT.setBackgroundResource(R.drawable.black_button_dark);
                }
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID))) {
                    NSTEXOPlayerVODActivity.this.closedBT.setBackgroundResource(R.drawable.black_button_dark);
                }
                if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("15")) {
                    NSTEXOPlayerVODActivity.this.iv_cancel.setImageDrawable(NSTEXOPlayerVODActivity.this.getResources().getDrawable(R.drawable.ic_cancel));
                }
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

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        updateTrackSelectorParameters();
        updateStartPosition();
        bundle.putParcelable(KEY_TRACK_SELECTOR_PARAMETERS, this.trackSelectorParameters);
        bundle.putBoolean(KEY_AUTO_PLAY, this.startAutoPlay);
        bundle.putInt(KEY_WINDOW, this.startWindow);
        bundle.putLong(KEY_POSITION, this.startPosition);
    }

    @Override // com.google.android.exoplayer2.PlaybackPreparer
    public void preparePlayback() {
        initializePlayer();
    }

    @Override // com.google.android.exoplayer2.ui.PlayerControlView.VisibilityListener
    public void onVisibilityChange(int i) {
        this.debugRootView.setVisibility(i);
    }

    public void initializePlayer() {
        //ToDo: initialized....
        DefaultDrmSessionManager<FrameworkMediaCrypto> defaultDrmSessionManager = null;
        TrackSelection.Factory factory;
        int i;
        if (this.app_video_status != null) {
            this.app_video_status.setVisibility(8);
        }
        if (this.player == null) {
            Intent intent = getIntent();
            intent.getAction();
            Uri[] uriArr = {intent.getData()};
            uriArr[0] = this.playingURL;
            //new String[1][0] = intent.getStringExtra("extension");
            if (!Util.checkCleartextTrafficPermitted(uriArr)) {
                showToast((int) R.string.error_cleartext_not_permitted);
                return;
            } else if (!Util.maybeRequestReadExternalStoragePermission(this, uriArr)) {
                if (intent.hasExtra("drm_scheme") || intent.hasExtra(DRM_SCHEME_UUID_EXTRA)) {
                    String stringExtra = intent.getStringExtra("drm_license_url");
                    String[] stringArrayExtra = intent.getStringArrayExtra("drm_key_request_properties");
                    boolean booleanExtra = intent.getBooleanExtra("drm_multi_session", false);
                    if (Util.SDK_INT < 18) {
                        defaultDrmSessionManager = null;
                        i = R.string.error_drm_not_supported;
                    } else {
                        i = R.string.error_drm_unsupported_scheme;
                        try {
                            UUID drmUuid = Util.getDrmUuid(intent.getStringExtra(intent.hasExtra("drm_scheme") ? "drm_scheme" : DRM_SCHEME_UUID_EXTRA));
                            if (drmUuid == null) {
                                defaultDrmSessionManager = null;
                            } else {
                                defaultDrmSessionManager = buildDrmSessionManagerV18(drmUuid, stringExtra, stringArrayExtra, booleanExtra);
                                i = R.string.error_drm_unknown;
                            }
                        } catch (UnsupportedDrmException e) {
                            i = e.reason == 1 ? R.string.error_drm_unsupported_scheme : R.string.error_drm_unknown;
                        }
                    }
                    if (defaultDrmSessionManager == null) {
                        showToast(i);
                        finish();
                        return;
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
                    showToast((int) R.string.error_unrecognized_abr_algorithm);
                    finish();
                    return;
                }
                DefaultRenderersFactory defaultRenderersFactory = new DefaultRenderersFactory(this, 2);
                this.trackSelector = new DefaultTrackSelector(factory);
                this.trackSelector.setParameters(this.trackSelectorParameters);
                this.lastSeenTrackGroupArray = null;
                this.player = ExoPlayerFactory.newSimpleInstance(this, defaultRenderersFactory, this.trackSelector, defaultDrmSessionManager);
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
        boolean z = this.startWindow != -1;
        if (z) {
            this.player.seekTo(this.startWindow, this.startPosition);
        }
        this.player.prepare(this.mediaSource, !z, false);
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

    private DataSource.Factory buildDataSourceFactory() {
        return DemoApplication.getInstance(this.context).buildDataSourceFactory();
    }

    private List<StreamKey> getOfflineStreamKeys(Uri uri) {
        return getOfflineStreamKeys(uri);
    }

    private DefaultDrmSessionManager<FrameworkMediaCrypto> buildDrmSessionManagerV18(UUID uuid, String str, String[] strArr, boolean z) throws UnsupportedDrmException {
        HttpMediaDrmCallback httpMediaDrmCallback = new HttpMediaDrmCallback(str, DemoApplication.getInstance(this.context).buildHttpDataSourceFactory());
        if (strArr != null) {
            for (int i = 0; i < strArr.length - 1; i += 2) {
                httpMediaDrmCallback.setKeyRequestProperty(strArr[i], strArr[i + 1]);
            }
        }
        releaseMediaDrm();
        this.mediaDrm = FrameworkMediaDrm.newInstance(uuid);
        return new DefaultDrmSessionManager<>(uuid, this.mediaDrm, httpMediaDrmCallback, null, z);
    }

    public void release() {
        if (this.player != null) {
            this.loginPreferences_seek_time = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
            long currentPosition = this.player.getCurrentPosition();
            this.loginPreferences_seek_time = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
            this.loginPrefsEditorSeekTime = this.loginPreferences_seek_time.edit();
            this.loginPrefsEditorSeekTime.putString(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, String.valueOf(currentPosition));
            this.loginPrefsEditorSeekTime.apply();
            if (!(VideoInfo.getInstance() == null || currentPosition == -1 || currentPosition == 0)) {
                VideoInfo.getInstance().setCurrentPositionSeekbar((int) this.player.getCurrentPosition());
                VideoInfo.getInstance().setProgress(true);
            }
            if (this.type.equals("movies")) {
                if (!((VideoInfo.getInstance() != null && VideoInfo.getInstance().getAPPType() != null && this.currentAPPType.equals(AppConst.TYPE_M3U)) || VideoInfo.getInstance() == null || VideoInfo.getInstance().getStreamid() == -1 || currentPosition == -1 || currentPosition == 0)) {
                    if (VideoInfo.getInstance().getRecentlyFinishedStreamID() == VideoInfo.getInstance().getStreamid()) {
                        updateMovieElapsedStatus(VideoInfo.getInstance().getStreamid(), 0);
                        VideoInfo.getInstance().setRecentlyFinishedStreamID(0);
                    } else {
                        updateMovieElapsedStatus(VideoInfo.getInstance().getStreamid(), currentPosition);
                    }
                }
            } else if (this.type.equals("series") && !((VideoInfo.getInstance() != null && VideoInfo.getInstance().getAPPType() != null && this.currentAPPType.equals(AppConst.TYPE_M3U)) || VideoInfo.getInstance() == null || VideoInfo.getInstance().getEpisodeId() == null || currentPosition == -1 || currentPosition == 0)) {
                if (VideoInfo.getInstance().getRecentlyFinishedStreamID() == Integer.parseInt(VideoInfo.getInstance().getEpisodeId())) {
                    this.seriesRecentClass.updateSeriesElapsedStatus(VideoInfo.getInstance().getEpisodeId(), 0);
                    VideoInfo.getInstance().setRecentlyFinishedStreamID(0);
                } else {
                    this.seriesRecentClass.updateSeriesElapsedStatus(VideoInfo.getInstance().getEpisodeId(), currentPosition);
                }
            }
        }
        if (this.player != null) {
            this.player.seekTo(0);
            updateTrackSelectorParameters();
            updateStartPosition();
            this.player.release();
            this.player = null;
            this.mediaSource = null;
            this.trackSelector = null;
        }
        if (this.type.equals("recording") && this.recordingRelease) {
            this.recordingRelease = false;
            VideoInfo instance = VideoInfo.getInstance();
            instance.getClass();
            instance.setCurrentPositionSeekbar(0);
        }
        releaseMediaDrm();
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

    private void showToast(int i) {
        showToast(getString(i));
    }

    private void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, 1).show();
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
           /* Player.EventListener.CC.$default$*/onLoadingChanged(/*this,*/ z);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            /*Player.EventListener.CC.$default$*/onPlaybackParametersChanged(/*this,*/ playbackParameters);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onRepeatModeChanged(int i) {
            /*Player.EventListener.CC.$default$*/onRepeatModeChanged(/*this,*/ i);
        }

        //ToDo: SeekBar
        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onSeekProcessed() {
            /*Player.EventListener.CC.$default$*/onSeekProcessed(/*this*/);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        //ToDo: onShuffleModeEnabledChanged...
        public void onShuffleModeEnabledChanged(boolean z) {
            /*Player.EventListener.CC.$default$*/onShuffleModeEnabledChanged(/*this,*/ z);
        }

        //ToDo: onTimelineChanged...
        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onTimelineChanged(Timeline timeline, @Nullable Object obj, int i) {
            /*Player.EventListener.CC.$default$*/onTimelineChanged(/*this,*/ timeline, obj, i);
        }

        private PlayerEventListener() {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayerStateChanged(boolean z, int i) {
            int i2;
            int i3 = 0;
            if (i == 2) {
                NSTEXOPlayerVODActivity.this.bufferingloader.setVisibility(0);
            } else if (i == 4) {
                if (NSTEXOPlayerVODActivity.this.mActivity != null && SharepreferenceDBHandler.getisAutoPlayVideos(NSTEXOPlayerVODActivity.this.mActivity)) {
                    int currentWindowIndex = VideoInfo.getInstance().getCurrentWindowIndex();
                    if (NSTEXOPlayerVODActivity.this.type.equals("movies")) {
                        VideoInfo.getInstance().setRecentlyFinishedStreamID(VideoInfo.getInstance().getStreamid());
                        ArrayList arrayList = (ArrayList) MoviesUsingSinglton.getInstance().getMoviesList();
                        int i4 = currentWindowIndex == arrayList.size() - 1 ? 0 : currentWindowIndex + 1;
                        if (arrayList.size() > 1 && i4 <= arrayList.size() - 1) {
                            String name = ((LiveStreamsDBModel) arrayList.get(i4)).getName();
                            try {
                                i2 = Integer.parseInt(((LiveStreamsDBModel) arrayList.get(i4)).getStreamId());
                            } catch (Exception unused) {
                                i2 = 0;
                            }
                            if (!(NSTEXOPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U) || i2 == -1 || i2 == 0)) {
                                SharedPreferences unused2 = NSTEXOPlayerVODActivity.this.loginPreferencesAfterLogin = NSTEXOPlayerVODActivity.this.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                                NSTEXOPlayerVODActivity.this.vodPresenter.vodInfo(NSTEXOPlayerVODActivity.this.loginPreferencesAfterLogin.getString("username", ""), NSTEXOPlayerVODActivity.this.loginPreferencesAfterLogin.getString("password", ""), i2);
                            }
                            setListData(name);
                        } else if (NSTEXOPlayerVODActivity.this.playerView != null) {
                            NSTEXOPlayerVODActivity.this.playerView.showController();
                        }
                    } else if (NSTEXOPlayerVODActivity.this.type.equals("series")) {
                        VideoInfo.getInstance().setRecentlyFinishedStreamID(Integer.parseInt(VideoInfo.getInstance().getEpisodeId()));
                        List<GetEpisdoeDetailsCallback> episodeList = EpisodesUsingSinglton.getInstance().getEpisodeList();
                        if (currentWindowIndex != episodeList.size() - 1) {
                            i3 = currentWindowIndex + 1;
                        }
                        if (episodeList.size() > 1 && i3 <= episodeList.size() - 1) {
                            String title = episodeList.get(i3).getTitle();
                            if (episodeList.get(i3).getMovieImage() != null) {
                                try {
                                    Glide.with(NSTEXOPlayerVODActivity.this.getApplicationContext()).load(episodeList.get(i3).getMovieImage()).asBitmap().into(new SimpleTarget<Bitmap>() {
                                        /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.PlayerEventListener.AnonymousClass1 */

                                        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                                            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                                            if (NSTEXOPlayerVODActivity.this.next_background != null) {
                                                NSTEXOPlayerVODActivity.this.next_background.setBackground(bitmapDrawable);
                                            }
                                        }
                                    });
                                } catch (Exception unused3) {
                                }
                            }
                            setListData(title);
                        } else if (NSTEXOPlayerVODActivity.this.playerView != null) {
                            NSTEXOPlayerVODActivity.this.playerView.showController();
                        }
                    } else if (NSTEXOPlayerVODActivity.this.type.equals("recording") || NSTEXOPlayerVODActivity.this.type.equals("catch_up")) {
                        if (NSTEXOPlayerVODActivity.this.playerView != null) {
                            NSTEXOPlayerVODActivity.this.playerView.showController();
                        }
                        NSTEXOPlayerVODActivity.this.recordingRelease = true;
                        NSTEXOPlayerVODActivity.this.initializePlayer();
                    }
                } else if (NSTEXOPlayerVODActivity.this.playerView != null) {
                    NSTEXOPlayerVODActivity.this.playerView.showController();
                }
            } else if (i == 3) {
                NSTEXOPlayerVODActivity.this.retryCount = 0;
                if (VideoInfo.getInstance() != null && !VideoInfo.getInstance().getNextOrPrevPressed() && VideoInfo.getInstance().getProgress()) {
                    VideoInfo.getInstance().setProgress(false);
                    try {
                        NSTEXOPlayerVODActivity.this.player.seekTo((long) ((int) ((long) VideoInfo.getInstance().getCurrentPositionSeekbar())));
                    } catch (Exception unused4) {
                    }
                } else if (VideoInfo.getInstance() != null) {
                    VideoInfo.getInstance().setProgress(false);
                    VideoInfo.getInstance().setNextOrPrevPressed(false);
                }
                NSTEXOPlayerVODActivity.this.bufferingloader.setVisibility(8);
                if (NSTEXOPlayerVODActivity.this.type.equals("movies") && !NSTEXOPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                    setMovieWatched();
                } else if (NSTEXOPlayerVODActivity.this.type.equals("series") && !NSTEXOPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                    NSTEXOPlayerVODActivity.this.seriesRecentClass.setSeriesRecentWatched();
                }
            }
        }

        private void setListData(String str) {
            NSTEXOPlayerVODActivity.this.AutoPlayNextPopup(NSTEXOPlayerVODActivity.this.context, str);
        }

        private void setMovieWatched() {
            int access$2900;
            if (VideoInfo.getInstance() != null && !NSTEXOPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U) && (access$2900 = NSTEXOPlayerVODActivity.this.streamCheckFun(VideoInfo.getInstance().getStreamid(), SharepreferenceDBHandler.getUserID(NSTEXOPlayerVODActivity.this.context))) <= 0 && access$2900 == 0) {
                setRecentWatchDB(NSTEXOPlayerVODActivity.this.context, VideoInfo.getInstance().getAvailableChannels(), VideoInfo.getInstance().getCurrentWindowIndex());
            }
        }

        private void setRecentWatchDB(Context context, ArrayList<LiveStreamsDBModel> arrayList, int i) {
            if (NSTEXOPlayerVODActivity.this.recentWatchDBHandler.getRecentwatchmoviesCount(SharepreferenceDBHandler.getUserID(context)) >= 100) {
                new ArrayList();
                ArrayList<LiveStreamsDBModel> allLiveStreasWithCategoryId = NSTEXOPlayerVODActivity.this.recentWatchDBHandler.getAllLiveStreasWithCategoryId(AppConst.EVENT_TYPE_MOVIE, SharepreferenceDBHandler.getUserID(context), "getOnedata");
                if (allLiveStreasWithCategoryId.isEmpty()) {
                    NSTEXOPlayerVODActivity.this.recentWatchDBHandler.deleteRecentwatch(Integer.parseInt(allLiveStreasWithCategoryId.get(0).getStreamId()), AppConst.EVENT_TYPE_MOVIE);
                }
                setDataIntoRecentWatchDB(context, arrayList, i);
                return;
            }
            setDataIntoRecentWatchDB(context, arrayList, i);
        }

        public void setDataIntoRecentWatchDB(Context context, ArrayList<LiveStreamsDBModel> arrayList, int i) {
            if (arrayList != null) {
                String num = arrayList.get(i).getNum();
                String name = arrayList.get(i).getName();
                String streamType = arrayList.get(i).getStreamType();
                String streamId = arrayList.get(i).getStreamId();
                String streamIcon = arrayList.get(i).getStreamIcon();
                String epgChannelId = arrayList.get(i).getEpgChannelId();
                String added = arrayList.get(i).getAdded();
                String categoryId = arrayList.get(i).getCategoryId();
                String customSid = arrayList.get(i).getCustomSid();
                arrayList.get(i).getTvArchive();
                String directSource = arrayList.get(i).getDirectSource();
                String tvArchiveDuration = arrayList.get(i).getTvArchiveDuration();
                String typeName = arrayList.get(i).getTypeName();
                String categoryName = arrayList.get(i).getCategoryName();
                String seriesNo = arrayList.get(i).getSeriesNo();
                String live = arrayList.get(i).getLive();
                String contaiinerExtension = arrayList.get(i).getContaiinerExtension();
                PanelAvailableChannelsPojo panelAvailableChannelsPojo = new PanelAvailableChannelsPojo();
                panelAvailableChannelsPojo.setNum(Integer.valueOf(num));
                panelAvailableChannelsPojo.setName(name);
                panelAvailableChannelsPojo.setStreamType(streamType);
                panelAvailableChannelsPojo.setStreamId(streamId);
                panelAvailableChannelsPojo.setStreamIcon(streamIcon);
                panelAvailableChannelsPojo.setEpgChannelId(epgChannelId);
                panelAvailableChannelsPojo.setAdded(added);
                panelAvailableChannelsPojo.setCategoryId(categoryId);
                panelAvailableChannelsPojo.setCustomSid(customSid);
                panelAvailableChannelsPojo.setTvArchive(0);
                panelAvailableChannelsPojo.setDirectSource(directSource);
                panelAvailableChannelsPojo.setTvArchiveDuration(tvArchiveDuration);
                panelAvailableChannelsPojo.setTypeName(typeName);
                panelAvailableChannelsPojo.setCategoryName(categoryName);
                panelAvailableChannelsPojo.setSeriesNo(seriesNo);
                panelAvailableChannelsPojo.setLive(live);
                panelAvailableChannelsPojo.setContainerExtension(contaiinerExtension);
                panelAvailableChannelsPojo.setUserIdReferred(SharepreferenceDBHandler.getUserID(context));
                panelAvailableChannelsPojo.setMovieElapsedTime(0);
                panelAvailableChannelsPojo.setMovieDuration(0);
                NSTEXOPlayerVODActivity.this.recentWatchDBHandler.addAllAvailableChannel(panelAvailableChannelsPojo);
            }
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPositionDiscontinuity(int i) {
            if (NSTEXOPlayerVODActivity.this.player != null && NSTEXOPlayerVODActivity.this.player.getPlaybackError() != null) {
                NSTEXOPlayerVODActivity.this.updateStartPosition();
            }
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayerError(ExoPlaybackException exoPlaybackException) {
            if (NSTEXOPlayerVODActivity.this.stopRetry) {
                return;
            }
            if (NSTEXOPlayerVODActivity.isBehindLiveWindow(exoPlaybackException)) {
                NSTEXOPlayerVODActivity.this.clearStartPosition();
                NSTEXOPlayerVODActivity.this.initializePlayer();
            } else if (!exoPlaybackException.toString().contains("com.google.android.exoplayer2.ext.ffmpeg.FfmpegDecoderException")) {
                NSTEXOPlayerVODActivity.this.updateStartPosition();
                retrying();
            } else {
                Utils.showToast(NSTEXOPlayerVODActivity.this.mActivity, "Audio track issue found. Please change the audio track to none.");
                NSTEXOPlayerVODActivity.this.initializePlayer();
            }
        }

        private void showStatus(String str) {
            NSTEXOPlayerVODActivity.this.app_video_status.setVisibility(0);
            NSTEXOPlayerVODActivity.this.app_video_status_text.setText(str);
        }

        private void retrying() {
            if (NSTEXOPlayerVODActivity.this.retryCount >= NSTEXOPlayerVODActivity.this.maxRetry) {
                showStatus(NSTEXOPlayerVODActivity.this.mActivity.getResources().getString(R.string.small_problem));
                NSTEXOPlayerVODActivity.this.release();
                NSTEXOPlayerVODActivity.this.retrying = false;
                NSTEXOPlayerVODActivity.this.bufferingloader.setVisibility(8);
            } else if (!NSTEXOPlayerVODActivity.this.stopRetry) {
                NSTEXOPlayerVODActivity.this.retrying = true;
                NSTEXOPlayerVODActivity.this.handlerOLD.postDelayed(new Runnable() {
                    /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.PlayerEventListener.AnonymousClass2 */

                    public void run() {
                        if (!NSTEXOPlayerVODActivity.this.stopRetry) {
                            NSTEXOPlayerVODActivity.this.retryCount++;
                            Utils.showToast(NSTEXOPlayerVODActivity.this.mActivity, NSTEXOPlayerVODActivity.this.mActivity.getResources().getString(R.string.play_back_error) + " (" + NSTEXOPlayerVODActivity.this.retryCount + "/" + NSTEXOPlayerVODActivity.this.maxRetry + ")");
                            NSTEXOPlayerVODActivity.this.release();
                            NSTEXOPlayerVODActivity.this.initializePlayer();
                        }
                    }
                }, 3000);
            }
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            if (trackGroupArray != NSTEXOPlayerVODActivity.this.lastSeenTrackGroupArray) {
                MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo = NSTEXOPlayerVODActivity.this.trackSelector.getCurrentMappedTrackInfo();
                if (currentMappedTrackInfo != null) {
                    currentMappedTrackInfo.getTypeSupport(2);
                    currentMappedTrackInfo.getTypeSupport(1);
                }
                TrackGroupArray unused = NSTEXOPlayerVODActivity.this.lastSeenTrackGroupArray = trackGroupArray;
            }
        }
    }

    private void showRecentWatchPopup(final Context context2, final String str, final String str2, final String str3, final int i, final String str4, List<GetEpisdoeDetailsCallback> list, final int i2, final String str5, String str6) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogbox);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass21 */

            public void onDismiss(DialogInterface dialogInterface) {
                NSTEXOPlayerVODActivity.this.hideSystemUi();
            }
        });
        View inflate = LayoutInflater.from(this).inflate((int) R.layout.layout_resume_player, (ViewGroup) null);
        this.movieIDTV = (TextView) inflate.findViewById(R.id.tv_movie_id);
        this.savePasswordBT = (Button) inflate.findViewById(R.id.bt_resume);
        this.iv_cancel = (ImageView) inflate.findViewById(R.id.iv_cancel);
        this.iv_cancel.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass22 */

            public void onClick(View view) {
                try {
                    NSTEXOPlayerVODActivity.this.alertDialog.setCancelable(true);
                    NSTEXOPlayerVODActivity.this.onBackPressed();
                    NSTEXOPlayerVODActivity.this.onBackPressed();
                    NSTEXOPlayerVODActivity.this.alertDialog.dismiss();
                } catch (Exception unused) {
                }
            }
        });
        this.closedBT = (Button) inflate.findViewById(R.id.bt_start_over);
        if (this.movieIDTV != null) {
            TextView textView = this.movieIDTV;
            textView.setText(str + "-" + str3);
        }
        if (this.savePasswordBT != null) {
            this.savePasswordBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.savePasswordBT));
        }
        if (this.closedBT != null) {
            this.closedBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.closedBT));
        }
        if (this.iv_cancel != null) {
            this.iv_cancel.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.iv_cancel));
        }
        this.savePasswordBT.requestFocus();
        this.savePasswordBT.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass23 */

            public void onClick(View view) {
                long j;
                try {
                    j = new SeriesRecentWatchDatabase(context2).gettimeElapsed(str).longValue();
                } catch (Exception unused) {
                    j = 0;
                }
                try {
                    if (VideoInfo.getInstance() != null && NSTEXOPlayerVODActivity.this.rq.booleanValue()) {
                        NSTEXOPlayerVODActivity.this.release();
                        if (NSTEXOPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                            NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(str5);
                        } else {
                            NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity = NSTEXOPlayerVODActivity.this;
                            nSTEXOPlayerVODActivity.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + str + "." + str2);
                        }
                        VideoInfo.getInstance().setAPPType(AppConst.TYPE_API);
                        VideoInfo instance = VideoInfo.getInstance();
                        instance.setTitle(i + " - " + str3);
                        VideoInfo.getInstance().setstreamid(NSTEXOPlayerVODActivity.this.opened_stream_id);
                        VideoInfo.getInstance().settypeofstream(str4);
                        VideoInfo.getInstance().setCurrentWindowIndex(i2);
                        VideoInfo.getInstance().setvideoNum(i);
                        VideoInfo.getInstance().setisTimeElapsed(true);
                        VideoInfo.getInstance().settimeElapsed(j);
                        VideoInfo.getInstance().setisVODPlayer(true);
                        VideoInfo.getInstance().setresuming(true);
                        VideoInfo.getInstance().setMaxTime(false);
                        VideoInfo.getInstance().setseeked(true);
                        NSTEXOPlayerVODActivity.this.retryCount = 0;
                        NSTEXOPlayerVODActivity.this.retrying = false;
                        VideoInfo.getInstance().setCurrentPositionSeekbar((int) j);
                        VideoInfo.getInstance().setProgress(true);
                        NSTEXOPlayerVODActivity.this.initializePlayer();
                    }
                    NSTEXOPlayerVODActivity.this.alertDialog.dismiss();
                } catch (Exception unused2) {
                }
            }
        });
        this.closedBT.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity.AnonymousClass24 */

            public void onClick(View view) {
                try {
                    NSTEXOPlayerVODActivity.this.release();
                    if (NSTEXOPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                        NSTEXOPlayerVODActivity.this.playingURL = Uri.parse(str5);
                    } else {
                        NSTEXOPlayerVODActivity nSTEXOPlayerVODActivity = NSTEXOPlayerVODActivity.this;
                        nSTEXOPlayerVODActivity.playingURL = Uri.parse(NSTEXOPlayerVODActivity.this.mFilePath + str + "." + str2);
                    }
                    new LiveStreamsDBModel();
                    LiveStreamsDBModel unused = NSTEXOPlayerVODActivity.this.getStreamStatus(NSTEXOPlayerVODActivity.this.opened_stream_id, SharepreferenceDBHandler.getUserID(context2));
                    if (VideoInfo.getInstance() != null) {
                        VideoInfo instance = VideoInfo.getInstance();
                        instance.setTitle(i + " - " + str3);
                        VideoInfo.getInstance().setstreamid(NSTEXOPlayerVODActivity.this.opened_stream_id);
                        VideoInfo.getInstance().settypeofstream(str4);
                        VideoInfo.getInstance().setCurrentWindowIndex(i2);
                        VideoInfo.getInstance().setvideoNum(i);
                        VideoInfo.getInstance().setisTimeElapsed(true);
                        VideoInfo.getInstance().setisVODPlayer(true);
                        VideoInfo.getInstance().settimeElapsed(0);
                        VideoInfo.getInstance().setresuming(true);
                        VideoInfo.getInstance().setMaxTime(false);
                        NSTEXOPlayerVODActivity.this.retryCount = 0;
                        NSTEXOPlayerVODActivity.this.retrying = false;
                        NSTEXOPlayerVODActivity.this.initializePlayer();
                    }
                    NSTEXOPlayerVODActivity.this.alertDialog.dismiss();
                } catch (Exception unused2) {
                }
            }
        });
        builder.setView(inflate);
        this.alertDialog = builder.create();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(this.alertDialog.getWindow().getAttributes());
        layoutParams.width = -1;
        layoutParams.height = -2;
        this.alertDialog.show();
        this.alertDialog.getWindow().setAttributes(layoutParams);
        this.alertDialog.setCancelable(false);
        this.alertDialog.show();
    }
}
