package com.nst.yourname.view.exoplayer;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.folderselector.FileChooserDialog;
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
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.CastStateListener;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.common.images.WebImage;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.chromecastfeature.ChromeCastUtilClass;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.FavouriteM3UModel;
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.PlayerSelectedSinglton;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.ExternalPlayerDataBase;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.PasswordStatusDBModel;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.model.pojo.ExternalPlayerModelClass;
import com.nst.yourname.model.pojo.XMLTVProgrammePojo;
import com.nst.yourname.view.activity.NewDashboardActivity;
import com.nst.yourname.view.activity.PlayExternalPlayerActivity;
import com.nst.yourname.view.activity.SettingsActivity;
import com.nst.yourname.view.adapter.SearchableAdapter;
import com.nst.yourname.view.ijkplayer.application.Settings;
import com.nst.yourname.view.ijkplayer.widget.media.FileMediaDataSource;
import com.nst.yourname.view.ijkplayer.widget.media.InfoHudViewHolder;
import com.nst.yourname.view.ijkplayer.widget.media.MeasureHelper;
import com.nst.yourname.view.ijkplayer.widget.media.MediaPlayerCompat;
import com.nst.yourname.view.ijkplayer.widget.media.SurfaceRenderView;
import com.nst.yourname.view.ijkplayer.widget.media.TableLayoutBinder;
import com.nst.yourname.view.ijkplayer.widget.media.TextureRenderView;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class NSTEXOPlayerSkyActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, FileChooserDialog.FileCallback, PlaybackPreparer, PlayerControlView.VisibilityListener {
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
    private static final int RECORDING_REQUEST_CODE = 101;
    public static final String SPHERICAL_STEREO_MODE_EXTRA = "spherical_stereo_mode";
    public static final String SPHERICAL_STEREO_MODE_LEFT_RIGHT = "left_right";
    public static final String SPHERICAL_STEREO_MODE_MONO = "mono";
    public static final String SPHERICAL_STEREO_MODE_TOP_BOTTOM = "top_bottom";
    public static final String URI_LIST_EXTRA = "uri_list";
    public static SharedPreferences loginPreferencesDownloadStatus;
    private static SharedPreferences loginPreferencesSharedPref_time_format;
    private static final int[] s_allAspectRatio = {0, 1, 2, 3, 4};
    private static String uk;
    private static String una;
    private AsyncTask AsyncTaskInitialize = null;
    private AsyncTask AsyncTaskNSTEXOPlayerSkyActivity = null;
    public AsyncTask AsyncTaskUpdateEPG = null;
    private ArrayList<LiveStreamsDBModel> AvailableChannelsFirstOpenedCat;
    String PlayerType;
    private SharedPreferences SharedPreferencesSort;
    private SharedPreferences.Editor SharedPreferencesSortEditor;
    SearchableAdapter adapter;
    public AlertDialog alertDialog;
    private ArrayList<LiveStreamCategoryIdDBModel> allLiveCategories;
    public ArrayList<LiveStreamsDBModel> allStreams;
    public ArrayList<LiveStreamsDBModel> allStreams_with_cat;
    public String allowedFormat;
    TextView app_channel_jumping_text;
    public LinearLayout app_video_box;
    LinearLayout app_video_status;
    TextView app_video_status_text;
    AppBarLayout appbarToolbar;
    public RelativeLayout audio_tracks;
    private AppCompatImageView btn_cat_back;
    private AppCompatImageView btn_cat_forward;
    ProgressBar bufferingloader;
    String catID = "";
    String catName = "";
    private ArrayList<PasswordStatusDBModel> categoryWithPasword;
    public PopupWindow changeSortPopUp;
    public boolean channelJumping = false;
    public ImageView channelLogo;
    String channelNumJumping;
    public boolean channelZapped = false;
    public Context context;
    int countUncat = -1;
    public String currentAPPType;
    public int currentCategoryIndex = 0;
    String currentChannelLogo;
    String currentEpgChannelID;
    public TextView currentProgram;
    public String currentProgramChanneID;
    public String currentProgramM3UURL = "";
    public int currentProgramStreamID;
    public TextView currentProgramTime;
    private int currentWindowIndex = 0;
    private DataSource.Factory dataSourceFactory;
    public DatabaseHandler database;
    TextView date;
    private LinearLayout debugRootView;
    DateFormat df;
    Date dt;
    String elv;
    public EditText et_search;
    ArrayList<ExternalPlayerModelClass> externalPlayerList;
    boolean externalPlayerSelected = false;
    private ArrayList<FavouriteDBModel> favliveListDetailUnlckedDetail;
    private ArrayList<FavouriteM3UModel> favliveListDetailUnlckedDetailM3U;
    String finalM3uVideoURL;
    int finalStreamID;
    String fmw;
    SimpleDateFormat fr;
    public boolean fullScreen = false;
    Handler handler;
    private Handler handlerAspectRatio;
    Handler handlerJumpChannel;
    public boolean hideEPGData = true;
    StringBuilder jumpToChannel = new StringBuilder();
    public TrackGroupArray lastSeenTrackGroupArray;
    public ListView listChannels;
    public ArrayList<String> listPassword = new ArrayList<>();
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailAvailable;
    public ArrayList<LiveStreamsDBModel> liveListDetailAvailableChannels;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailUnlcked;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlckedChannels;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailUnlckedDetail;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlckedDetailChannels;
    LiveStreamDBHandler liveStreamDBHandler;
    private LinearLayout ll_allcontrols;
    LinearLayout ll_channel_jumping;
    public RelativeLayout ll_close;
    LinearLayout ll_epg1_box;
    LinearLayout ll_epg2_box;
    LinearLayout ll_epg3_box;
    LinearLayout ll_epg4_box;
    LinearLayout ll_epg_loader;
    LinearLayout ll_layout_to_hide1;
    LinearLayout ll_layout_to_hide4;
    LinearLayout ll_no_guide;
    private SharedPreferences loginPreferencesSharedPref;
    private SharedPreferences loginPreferencesSharedPref_allowed_format;
    private SharedPreferences loginPreferencesSharedPref_currently_playing_video;
    private SharedPreferences loginPreferencesSharedPref_currently_playing_video_position;
    public SharedPreferences loginPreferencesSharedPref_opened_video;
    private SharedPreferences loginPreferences_audio_selected;
    private SharedPreferences loginPreferences_subtitle_selected;
    private SharedPreferences loginPreferences_video_selected;
    public SharedPreferences.Editor loginPrefsEditor;
    private SharedPreferences.Editor loginPrefsEditorAudio;
    public SharedPreferences.Editor loginPrefsEditorPosition;
    private SharedPreferences.Editor loginPrefsEditorSubtitle;
    private SharedPreferences.Editor loginPrefsEditorVideo;
    @BindView(R.id.logo)
    ImageView logo;
    public boolean longKeyPressed = true;
    private String m3uVideoURL;
    Activity mActivity;
    private CastContext mCastContext;
    public CastSession mCastSession;
    private CastStateListener mCastStateListener;
    private int mCurrentAspectRatio = s_allAspectRatio[0];
    private int mCurrentAspectRatioIndex = 0;
    public String mFilePath;
    private String mFilePath1 = "";
    public Handler mHandler;
    private final SessionManagerListener<CastSession> mSessionManagerListener = new MySessionManagerListener();
    private Settings mSettings;
    public int maxRetry = 5;
    private FrameworkMediaDrm mediaDrm;
    private MenuItem mediaRouteMenuItem;
    private MediaSource mediaSource;
    MenuItem menuItemSettings;
    Menu menuSelect;
    public Button negativeButton;
    public TextView nextProgram;
    public TextView nextProgramTime;
    boolean no_channel = false;
    private String opened_cat_id = "";
    public int opened_vlc_id = 0;
    boolean outfromtoolbar = true;
    ProgressBar pb_listview_loader;
    public Boolean playedFirstTime = false;
    public SimpleExoPlayer player;
    private PlayerView playerView;
    public Uri playingURL;
    public int positionToSelectAfterJumping = 0;
    private SimpleDateFormat programTimeFormat;
    ProgressBar progressBar;
    public int retryCount = 0;
    private boolean retrySetAdapter = false;
    public boolean retrying = false;
    @BindView(R.id.rl_toolbar)
    RelativeLayout rlToolbar;
    public RelativeLayout rl_epg_layout;
    RelativeLayout rl_footer_info;
    RelativeLayout rl_layout_to_hide5;
    RelativeLayout rl_layout_to_hide_6;
    public RelativeLayout rl_middle;
    RelativeLayout rl_nst_player_sky_layout;
    RelativeLayout rl_settings;
    RelativeLayout rl_video_box;
    public Boolean rq = true;
    SearchView searchView;
    SharedPreferences.Editor sharedPrefEditor;
    SharedPreferences sharedPreferences;
    private boolean startAutoPlay;
    private long startPosition;
    private int startWindow;
    public boolean stopRetry = false;
    private int streamID = -1;
    String streamNameWithUnderscore;
    public RelativeLayout subtitle_tracks;
    boolean text_from_toolbar = false;
    TextView time;
    Toolbar toolbar;
    public DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    TextView tvNoRecordFound;
    TextView tvNoStream;
    public TextView tv_categories_view;
    TextView tv_epg1_date;
    TextView tv_epg1_program;
    TextView tv_epg2_date;
    TextView tv_epg2_program;
    TextView tv_epg3_date;
    TextView tv_epg3_program;
    TextView tv_epg4_date;
    TextView tv_epg4_program;
    String ukd;
    String unad;
    public String url;
    public int video_num = 0;
    public RelativeLayout video_tracks;
    public View vlc_exo_subtitle;
    public View vlcaspectRatio;
    public View vlcnextButton;
    public View vlcprevButton;

    @Override // com.afollestad.materialdialogs.folderselector.FileChooserDialog.FileCallback
    public void onFileChooserDismissed(@NonNull FileChooserDialog fileChooserDialog) {
    }

    @Override // android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    static {
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    private class MySessionManagerListener implements SessionManagerListener<CastSession> {
        private MySessionManagerListener() {
        }

        public void onSessionEnded(CastSession castSession, int i) {
            Log.e("seson mangement ", "onSessionEnded()");
            if (castSession == NSTEXOPlayerSkyActivity.this.mCastSession) {
                CastSession unused = NSTEXOPlayerSkyActivity.this.mCastSession = null;
            }
            NSTEXOPlayerSkyActivity.this.invalidateOptionsMenu();
        }

        public void onSessionResumed(CastSession castSession, boolean z) {
            Log.e("seson mangement ", "onSessionResumed()");
            CastSession unused = NSTEXOPlayerSkyActivity.this.mCastSession = castSession;
            NSTEXOPlayerSkyActivity.this.invalidateOptionsMenu();
        }

        public void onSessionStarted(CastSession castSession, String str) {
            Log.e("seson mangement ", "onSessionStarted()");
            CastSession unused = NSTEXOPlayerSkyActivity.this.mCastSession = castSession;
            NSTEXOPlayerSkyActivity.this.invalidateOptionsMenu();
        }

        public void onSessionStarting(CastSession castSession) {
            Log.e("seson mangement ", "onSessionStarting()");
        }

        public void onSessionStartFailed(CastSession castSession, int i) {
            Log.e("seson mangement ", "onSessionStartFailed()");
        }

        public void onSessionEnding(CastSession castSession) {
            Log.e("seson mangement ", "onSessionEnding()");
        }

        public void onSessionResuming(CastSession castSession, String str) {
            Log.e("seson mangement ", "onSessionResuming()");
        }

        public void onSessionResumeFailed(CastSession castSession, int i) {
            Log.e("seson mangement ", "onSessionResumeFailed()");
        }

        public void onSessionSuspended(CastSession castSession, int i) {
            Log.e("seson mangement ", "onSessionSuspended()");
        }
    }

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        this.context = this;
        this.mActivity = this;
        requestWindowFeature(1);
        super.onCreate(bundle);
        this.dataSourceFactory = buildDataSourceFactory();
        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }
        this.PlayerType = getIntent().getStringExtra("PlayerType");
        if (this.PlayerType == null || !this.PlayerType.equals("large_epg")) {
            PlayerSelectedSinglton.getInstance().setPlayerType("live");
            String livePlayerAppName = SharepreferenceDBHandler.getLivePlayerAppName(this.context);
            if (!SharepreferenceDBHandler.getLivePlayerPkgName(this.context).equals("default") && !new ExternalPlayerDataBase(this.context).CheckPlayerExistense(livePlayerAppName)) {
                SharepreferenceDBHandler.setLivePlayer("default", "default", this.context);
            }
            String livePlayerPkgName = SharepreferenceDBHandler.getLivePlayerPkgName(this.context);
            if (livePlayerPkgName == null || livePlayerPkgName.equalsIgnoreCase("default")) {
                PlayerSelectedSinglton.getInstance().setPlayedWithExternalPlayer(false);
            } else {
                PlayerSelectedSinglton.getInstance().setPlayedWithExternalPlayer(true);
            }
            setContentView((int) R.layout.activity_exoplayer_sky);
            this.fullScreen = false;
        } else {
            PlayerSelectedSinglton.getInstance().setPlayerType("large_epg");
            setContentView((int) R.layout.nst_exo_player_sky);
            int intExtra = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
            this.m3uVideoURL = getIntent().getStringExtra("VIDEO_URL");
            this.video_num = getIntent().getIntExtra("VIDEO_NUM", 0);
            this.loginPreferencesSharedPref_opened_video = this.context.getSharedPreferences(AppConst.LOGIN_PREF_OPENED_VIDEO, 0);
            SharedPreferences.Editor edit = this.loginPreferencesSharedPref_opened_video.edit();
            edit.putInt(AppConst.LOGIN_PREF_OPENED_VIDEO_ID, intExtra);
            edit.putString("LOGIN_PREF_OPENED_VIDEO_URL_M3U", this.m3uVideoURL);
            edit.apply();
            this.fullScreen = true;
        }
        ButterKnife.bind(this);
        View findViewById = findViewById(R.id.root);
        //ToDo: player selected boolean issue...
        if (/*PlayerSelectedSinglton.getInstance().getPlayedWithExternalPlayer() != null &&*/ PlayerSelectedSinglton.getInstance().getPlayedWithExternalPlayer()) {
            findViewById.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass1 */

                public void onClick(View view) {
                    NSTEXOPlayerSkyActivity.this.fullScreenVideoLayout();
                }
            });
        }
        this.debugRootView = (LinearLayout) findViewById(R.id.controls_root);
        this.ll_allcontrols = (LinearLayout) findViewById(R.id.ll_allcontrols);
        this.playerView = (PlayerView) findViewById(R.id.player_view);
        this.playerView.setControllerVisibilityListener(this);
        this.playerView.requestFocus();
        if (this.PlayerType == null || !this.PlayerType.equals("large_epg")) {
            this.ll_allcontrols.setVisibility(8);
        } else {
            this.ll_allcontrols.setVisibility(0);
        }
        this.debugRootView.setVisibility(8);
        if (bundle != null) {
            this.trackSelectorParameters = (DefaultTrackSelector.Parameters) bundle.getParcelable(KEY_TRACK_SELECTOR_PARAMETERS);
            this.startAutoPlay = bundle.getBoolean(KEY_AUTO_PLAY);
            this.startWindow = bundle.getInt(KEY_WINDOW);
            this.startPosition = bundle.getLong(KEY_POSITION);
        } else {
            this.trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();
            clearStartPosition();
        }
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        try {
            this.mCastStateListener = new CastStateListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass2 */

                @Override // com.google.android.gms.cast.framework.CastStateListener
                public void onCastStateChanged(int i) {
                }
            };
            this.mCastContext = CastContext.getSharedInstance(this);
        } catch (Exception unused) {
        }
        initializeVariables();
        if (this.rl_nst_player_sky_layout != null) {
            this.rl_nst_player_sky_layout.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass3 */

                public void onClick(View view) {
                    NSTEXOPlayerSkyActivity.this.fullScreenVideoLayout();
                }
            });
        }
        this.listPassword = getPasswordSetCategories();
        if (this.catID != null && !this.catID.equals("") && this.catID.equals(AppConst.PASSWORD_UNSET)) {
            allChannels();
        } else if (this.catID == null || this.catID.equals("") || !this.catID.equals("-1")) {
            allChannelsWithCat();
        } else {
            allFavourites();
        }
        if (this.PlayerType != null && this.PlayerType.equals("live")) {
            this.AsyncTaskInitialize = new InitializeAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x0206  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0228  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0243  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x025e  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x02a9  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x02d5  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0589  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x05fe  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x060d  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x061f  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0631  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x06af  */
    /* JADX WARNING: Removed duplicated region for block: B:96:? A[RETURN, SYNTHETIC] */
    private void initializeVariables() {
        char c;
        this.sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.mCurrentAspectRatioIndex = this.sharedPreferences.getInt(AppConst.ASPECT_RATIO, this.mCurrentAspectRatioIndex);
        this.handlerAspectRatio = new Handler();
        this.mSettings = new Settings(this.context);
        this.loginPreferencesSharedPref = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.loginPreferencesSharedPref_allowed_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
        this.loginPreferencesSharedPref_currently_playing_video = getSharedPreferences(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, 0);
        this.loginPrefsEditor = this.loginPreferencesSharedPref_currently_playing_video.edit();
        this.loginPreferencesSharedPref_currently_playing_video_position = getSharedPreferences(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, 0);
        this.loginPrefsEditorPosition = this.loginPreferencesSharedPref_currently_playing_video_position.edit();
        this.SharedPreferencesSort = getSharedPreferences(AppConst.LOGIN_PREF_SORT_LIVE_SKY, 0);
        this.SharedPreferencesSortEditor = this.SharedPreferencesSort.edit();
        if (this.SharedPreferencesSort.getString(AppConst.LOGIN_PREF_SORT, "").equals("")) {
            this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, AppConst.PASSWORD_UNSET);
            this.SharedPreferencesSortEditor.apply();
        }
        this.mHandler = new Handler(Looper.getMainLooper());
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
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            this.currentAPPType = AppConst.TYPE_M3U;
        } else {
            this.currentAPPType = AppConst.TYPE_API;
        }
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass4 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(NSTEXOPlayerSkyActivity.this.context);
            }
        });
        String string = this.loginPreferencesSharedPref.getString("username", "");
        String string2 = this.loginPreferencesSharedPref.getString("password", "");
        this.allowedFormat = this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
        if (this.allowedFormat != null && !this.allowedFormat.isEmpty() && !this.allowedFormat.equals("") && this.allowedFormat.equals("default")) {
            this.allowedFormat = "";
        } else if (this.allowedFormat != null && !this.allowedFormat.isEmpty() && !this.allowedFormat.equals("") && this.allowedFormat.equals("ts")) {
            this.allowedFormat = ".ts";
        } else if (this.allowedFormat == null || this.allowedFormat.isEmpty() || this.allowedFormat.equals("") || !this.allowedFormat.equals("m3u8")) {
            this.allowedFormat = "";
        } else {
            this.allowedFormat = ".m3u8";
        }
        String string3 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_URL, "");
        String string4 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_PROTOCOL, "");
        String string5 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_HTTPS_PORT, "");
        String string6 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_PORT, "");
        String string7 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_RTMP_PORT, "");
        int hashCode = string4.hashCode();
        if (hashCode != 3213448) {
            if (hashCode != 3504631) {
                if (hashCode == 99617003 && string4.equals(AppConst.HTTPS)) {
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
                    this.opened_cat_id = getIntent().getStringExtra("OPENED_CAT_ID");
                    this.catName = getIntent().getStringExtra("OPENED_CAT_NAME");
                    this.catID = this.opened_cat_id;
                    this.m3uVideoURL = getIntent().getStringExtra("VIDEO_URL");
                    if (!this.allowedFormat.equals("")) {
                        this.mFilePath1 = string3 + ":" + string6 + "/" + string + "/" + string2 + "/";
                    } else {
                        this.mFilePath1 = string3 + ":" + string6 + "/live/" + string + "/" + string2 + "/";
                    }
                    this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
                    this.liveStreamDBHandler = new LiveStreamDBHandler(this);
                    this.database = new DatabaseHandler(this.context);
                    this.handler = new Handler();
                    this.handlerJumpChannel = new Handler();
                    this.elv = Utils.ukde(TextureRenderView.pZGV() + TextureRenderView.mv());
                    this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    this.channelLogo = (ImageView) findViewById(R.id.iv_channel_logo);
                    this.currentProgram = (TextView) findViewById(R.id.tv_current_program);
                    this.currentProgramTime = (TextView) findViewById(R.id.tv_current_time);
                    this.nextProgram = (TextView) findViewById(R.id.tv_next_program);
                    this.nextProgramTime = (TextView) findViewById(R.id.tv_next_program_time);
                    this.btn_cat_back = (AppCompatImageView) findViewById(R.id.btn_category_back);
                    this.btn_cat_forward = (AppCompatImageView) findViewById(R.id.btn_category_fwd);
                    this.tv_categories_view = (TextView) findViewById(R.id.tv_categories_view);
                    this.toolbar = (Toolbar) findViewById(R.id.toolbar);
                    this.tvNoStream = (TextView) findViewById(R.id.tv_noStream);
                    this.tvNoRecordFound = (TextView) findViewById(R.id.tv_no_record_found);
                    this.appbarToolbar = (AppBarLayout) findViewById(R.id.appbar_toolbar);
                    this.rl_settings = (RelativeLayout) findViewById(R.id.rl_settings);
                    this.bufferingloader = (ProgressBar) findViewById(R.id.app_video_loading);
                    this.unad = Utils.ukde(MeasureHelper.pnm());
                    this.rl_nst_player_sky_layout = (RelativeLayout) findViewById(R.id.rl_nst_player_sky_layout);
                    this.ll_layout_to_hide1 = (LinearLayout) findViewById(R.id.ll_layout_to_hide_1);
                    this.ll_layout_to_hide4 = (LinearLayout) findViewById(R.id.ll_layout_to_hide_4);
                    this.rl_layout_to_hide5 = (RelativeLayout) findViewById(R.id.rl_layout_to_hide_5);
                    this.rl_layout_to_hide_6 = (RelativeLayout) findViewById(R.id.rl_layout_to_hide_6);
                    this.rl_footer_info = (RelativeLayout) findViewById(R.id.rl_footer_info);
                    this.rl_video_box = (RelativeLayout) findViewById(R.id.rl_video_box);
                    this.ll_epg1_box = (LinearLayout) findViewById(R.id.ll_epg1_box);
                    this.ll_epg2_box = (LinearLayout) findViewById(R.id.ll_epg2_box);
                    this.ll_epg3_box = (LinearLayout) findViewById(R.id.ll_epg3_box);
                    this.ll_epg4_box = (LinearLayout) findViewById(R.id.ll_epg4_box);
                    this.ll_no_guide = (LinearLayout) findViewById(R.id.ll_no_guide);
                    this.ll_epg_loader = (LinearLayout) findViewById(R.id.ll_epg_loader);
                    this.tv_epg1_date = (TextView) findViewById(R.id.tv_epg1_date);
                    this.tv_epg2_date = (TextView) findViewById(R.id.tv_epg2_date);
                    this.tv_epg3_date = (TextView) findViewById(R.id.tv_epg3_date);
                    this.tv_epg4_date = (TextView) findViewById(R.id.tv_epg4_date);
                    this.tv_epg1_program = (TextView) findViewById(R.id.tv_epg1_program);
                    this.tv_epg2_program = (TextView) findViewById(R.id.tv_epg2_program);
                    this.tv_epg3_program = (TextView) findViewById(R.id.tv_epg3_program);
                    this.tv_epg4_program = (TextView) findViewById(R.id.tv_epg4_program);
                    this.dt = new Date();
                    this.date = (TextView) findViewById(R.id.date);
                    this.time = (TextView) findViewById(R.id.time);
                    this.ll_channel_jumping = (LinearLayout) findViewById(R.id.ll_channel_jumping);
                    this.app_channel_jumping_text = (TextView) findViewById(R.id.app_channel_jumping_text);
                    this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
                    this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
                    this.rl_epg_layout = (RelativeLayout) findViewById(R.id.rl_epg_layout);
                    this.pb_listview_loader = (ProgressBar) findViewById(R.id.pb_listview_loader);
                    this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                    this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                    this.toolbar.inflateMenu(R.menu.menu_search);
                    this.appbarToolbar.requestFocusFromTouch();
                    setSupportActionBar(this.toolbar);
                    if (this.PlayerType != null && this.PlayerType.equals("live")) {
                        new Thread(new CountDownRunner()).start();
                    }
                    if (SharepreferenceDBHandler.getSelectedLanguage(this.context).equalsIgnoreCase("Arabic")) {
                        this.btn_cat_back.setImageResource(R.drawable.right_icon_cat);
                        this.btn_cat_forward.setImageResource(R.drawable.left_icon_cat);
                    }
                    loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
                    this.programTimeFormat = new SimpleDateFormat(loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, ""), Locale.US);
                    findViewById(R.id.exo_nextt).setOnClickListener(this);
                    findViewById(R.id.exo_prevv).setOnClickListener(this);
                    findViewById(R.id.app_video_box).setOnClickListener(this);
                    this.listChannels = (ListView) findViewById(R.id.lv_ch);
                    this.et_search = (EditText) findViewById(R.id.et_search);
                    this.btn_cat_back.setOnClickListener(this);
                    this.btn_cat_forward.setOnClickListener(this);
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
                    this.ukd = Utils.ukde(FileMediaDataSource.apn());
                    this.rl_middle = (RelativeLayout) findViewById(R.id.middle);
                    uk = getApplicationName(this.context);
                    this.liveListDetailUnlcked = new ArrayList<>();
                    this.liveListDetailUnlckedDetail = new ArrayList<>();
                    this.liveListDetailAvailable = new ArrayList<>();
                    this.liveListDetailUnlckedChannels = new ArrayList<>();
                    this.liveListDetailUnlckedDetailChannels = new ArrayList<>();
                    this.liveListDetailAvailableChannels = new ArrayList<>();
                    una = getApplicationContext().getPackageName();
                    this.AvailableChannelsFirstOpenedCat = new ArrayList<>();
                    this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + SurfaceRenderView.mw());
                    if (this.tv_categories_view == null) {
                        this.tv_categories_view.setText(this.catName);
                        this.tv_categories_view.setSelected(true);
                        return;
                    }
                    return;
                }
            } else if (string4.equals(AppConst.RMTP)) {
                c = 2;
                switch (c) {
                }
                this.opened_cat_id = getIntent().getStringExtra("OPENED_CAT_ID");
                this.catName = getIntent().getStringExtra("OPENED_CAT_NAME");
                this.catID = this.opened_cat_id;
                this.m3uVideoURL = getIntent().getStringExtra("VIDEO_URL");
                if (!this.allowedFormat.equals("")) {
                }
                this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
                this.liveStreamDBHandler = new LiveStreamDBHandler(this);
                this.database = new DatabaseHandler(this.context);
                this.handler = new Handler();
                this.handlerJumpChannel = new Handler();
                this.elv = Utils.ukde(TextureRenderView.pZGV() + TextureRenderView.mv());
                this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
                this.channelLogo = (ImageView) findViewById(R.id.iv_channel_logo);
                this.currentProgram = (TextView) findViewById(R.id.tv_current_program);
                this.currentProgramTime = (TextView) findViewById(R.id.tv_current_time);
                this.nextProgram = (TextView) findViewById(R.id.tv_next_program);
                this.nextProgramTime = (TextView) findViewById(R.id.tv_next_program_time);
                this.btn_cat_back = (AppCompatImageView) findViewById(R.id.btn_category_back);
                this.btn_cat_forward = (AppCompatImageView) findViewById(R.id.btn_category_fwd);
                this.tv_categories_view = (TextView) findViewById(R.id.tv_categories_view);
                this.toolbar = (Toolbar) findViewById(R.id.toolbar);
                this.tvNoStream = (TextView) findViewById(R.id.tv_noStream);
                this.tvNoRecordFound = (TextView) findViewById(R.id.tv_no_record_found);
                this.appbarToolbar = (AppBarLayout) findViewById(R.id.appbar_toolbar);
                this.rl_settings = (RelativeLayout) findViewById(R.id.rl_settings);
                this.bufferingloader = (ProgressBar) findViewById(R.id.app_video_loading);
                this.unad = Utils.ukde(MeasureHelper.pnm());
                this.rl_nst_player_sky_layout = (RelativeLayout) findViewById(R.id.rl_nst_player_sky_layout);
                this.ll_layout_to_hide1 = (LinearLayout) findViewById(R.id.ll_layout_to_hide_1);
                this.ll_layout_to_hide4 = (LinearLayout) findViewById(R.id.ll_layout_to_hide_4);
                this.rl_layout_to_hide5 = (RelativeLayout) findViewById(R.id.rl_layout_to_hide_5);
                this.rl_layout_to_hide_6 = (RelativeLayout) findViewById(R.id.rl_layout_to_hide_6);
                this.rl_footer_info = (RelativeLayout) findViewById(R.id.rl_footer_info);
                this.rl_video_box = (RelativeLayout) findViewById(R.id.rl_video_box);
                this.ll_epg1_box = (LinearLayout) findViewById(R.id.ll_epg1_box);
                this.ll_epg2_box = (LinearLayout) findViewById(R.id.ll_epg2_box);
                this.ll_epg3_box = (LinearLayout) findViewById(R.id.ll_epg3_box);
                this.ll_epg4_box = (LinearLayout) findViewById(R.id.ll_epg4_box);
                this.ll_no_guide = (LinearLayout) findViewById(R.id.ll_no_guide);
                this.ll_epg_loader = (LinearLayout) findViewById(R.id.ll_epg_loader);
                this.tv_epg1_date = (TextView) findViewById(R.id.tv_epg1_date);
                this.tv_epg2_date = (TextView) findViewById(R.id.tv_epg2_date);
                this.tv_epg3_date = (TextView) findViewById(R.id.tv_epg3_date);
                this.tv_epg4_date = (TextView) findViewById(R.id.tv_epg4_date);
                this.tv_epg1_program = (TextView) findViewById(R.id.tv_epg1_program);
                this.tv_epg2_program = (TextView) findViewById(R.id.tv_epg2_program);
                this.tv_epg3_program = (TextView) findViewById(R.id.tv_epg3_program);
                this.tv_epg4_program = (TextView) findViewById(R.id.tv_epg4_program);
                this.dt = new Date();
                this.date = (TextView) findViewById(R.id.date);
                this.time = (TextView) findViewById(R.id.time);
                this.ll_channel_jumping = (LinearLayout) findViewById(R.id.ll_channel_jumping);
                this.app_channel_jumping_text = (TextView) findViewById(R.id.app_channel_jumping_text);
                this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
                this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
                this.rl_epg_layout = (RelativeLayout) findViewById(R.id.rl_epg_layout);
                this.pb_listview_loader = (ProgressBar) findViewById(R.id.pb_listview_loader);
                this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                this.toolbar.inflateMenu(R.menu.menu_search);
                this.appbarToolbar.requestFocusFromTouch();
                setSupportActionBar(this.toolbar);
                new Thread(new CountDownRunner()).start();
                if (SharepreferenceDBHandler.getSelectedLanguage(this.context).equalsIgnoreCase("Arabic")) {
                }
                loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
                this.programTimeFormat = new SimpleDateFormat(loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, ""), Locale.US);
                findViewById(R.id.exo_nextt).setOnClickListener(this);
                findViewById(R.id.exo_prevv).setOnClickListener(this);
                findViewById(R.id.app_video_box).setOnClickListener(this);
                this.listChannels = (ListView) findViewById(R.id.lv_ch);
                this.et_search = (EditText) findViewById(R.id.et_search);
                this.btn_cat_back.setOnClickListener(this);
                this.btn_cat_forward.setOnClickListener(this);
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
                this.ukd = Utils.ukde(FileMediaDataSource.apn());
                this.rl_middle = (RelativeLayout) findViewById(R.id.middle);
                uk = getApplicationName(this.context);
                this.liveListDetailUnlcked = new ArrayList<>();
                this.liveListDetailUnlckedDetail = new ArrayList<>();
                this.liveListDetailAvailable = new ArrayList<>();
                this.liveListDetailUnlckedChannels = new ArrayList<>();
                this.liveListDetailUnlckedDetailChannels = new ArrayList<>();
                this.liveListDetailAvailableChannels = new ArrayList<>();
                una = getApplicationContext().getPackageName();
                this.AvailableChannelsFirstOpenedCat = new ArrayList<>();
                this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + SurfaceRenderView.mw());
                if (this.tv_categories_view == null) {
                }
            }
        } else if (string4.equals(AppConst.HTTP)) {
            c = 0;
            switch (c) {
            }
            this.opened_cat_id = getIntent().getStringExtra("OPENED_CAT_ID");
            this.catName = getIntent().getStringExtra("OPENED_CAT_NAME");
            this.catID = this.opened_cat_id;
            this.m3uVideoURL = getIntent().getStringExtra("VIDEO_URL");
            if (!this.allowedFormat.equals("")) {
            }
            this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
            this.liveStreamDBHandler = new LiveStreamDBHandler(this);
            this.database = new DatabaseHandler(this.context);
            this.handler = new Handler();
            this.handlerJumpChannel = new Handler();
            this.elv = Utils.ukde(TextureRenderView.pZGV() + TextureRenderView.mv());
            this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
            this.channelLogo = (ImageView) findViewById(R.id.iv_channel_logo);
            this.currentProgram = (TextView) findViewById(R.id.tv_current_program);
            this.currentProgramTime = (TextView) findViewById(R.id.tv_current_time);
            this.nextProgram = (TextView) findViewById(R.id.tv_next_program);
            this.nextProgramTime = (TextView) findViewById(R.id.tv_next_program_time);
            this.btn_cat_back = (AppCompatImageView) findViewById(R.id.btn_category_back);
            this.btn_cat_forward = (AppCompatImageView) findViewById(R.id.btn_category_fwd);
            this.tv_categories_view = (TextView) findViewById(R.id.tv_categories_view);
            this.toolbar = (Toolbar) findViewById(R.id.toolbar);
            this.tvNoStream = (TextView) findViewById(R.id.tv_noStream);
            this.tvNoRecordFound = (TextView) findViewById(R.id.tv_no_record_found);
            this.appbarToolbar = (AppBarLayout) findViewById(R.id.appbar_toolbar);
            this.rl_settings = (RelativeLayout) findViewById(R.id.rl_settings);
            this.bufferingloader = (ProgressBar) findViewById(R.id.app_video_loading);
            this.unad = Utils.ukde(MeasureHelper.pnm());
            this.rl_nst_player_sky_layout = (RelativeLayout) findViewById(R.id.rl_nst_player_sky_layout);
            this.ll_layout_to_hide1 = (LinearLayout) findViewById(R.id.ll_layout_to_hide_1);
            this.ll_layout_to_hide4 = (LinearLayout) findViewById(R.id.ll_layout_to_hide_4);
            this.rl_layout_to_hide5 = (RelativeLayout) findViewById(R.id.rl_layout_to_hide_5);
            this.rl_layout_to_hide_6 = (RelativeLayout) findViewById(R.id.rl_layout_to_hide_6);
            this.rl_footer_info = (RelativeLayout) findViewById(R.id.rl_footer_info);
            this.rl_video_box = (RelativeLayout) findViewById(R.id.rl_video_box);
            this.ll_epg1_box = (LinearLayout) findViewById(R.id.ll_epg1_box);
            this.ll_epg2_box = (LinearLayout) findViewById(R.id.ll_epg2_box);
            this.ll_epg3_box = (LinearLayout) findViewById(R.id.ll_epg3_box);
            this.ll_epg4_box = (LinearLayout) findViewById(R.id.ll_epg4_box);
            this.ll_no_guide = (LinearLayout) findViewById(R.id.ll_no_guide);
            this.ll_epg_loader = (LinearLayout) findViewById(R.id.ll_epg_loader);
            this.tv_epg1_date = (TextView) findViewById(R.id.tv_epg1_date);
            this.tv_epg2_date = (TextView) findViewById(R.id.tv_epg2_date);
            this.tv_epg3_date = (TextView) findViewById(R.id.tv_epg3_date);
            this.tv_epg4_date = (TextView) findViewById(R.id.tv_epg4_date);
            this.tv_epg1_program = (TextView) findViewById(R.id.tv_epg1_program);
            this.tv_epg2_program = (TextView) findViewById(R.id.tv_epg2_program);
            this.tv_epg3_program = (TextView) findViewById(R.id.tv_epg3_program);
            this.tv_epg4_program = (TextView) findViewById(R.id.tv_epg4_program);
            this.dt = new Date();
            this.date = (TextView) findViewById(R.id.date);
            this.time = (TextView) findViewById(R.id.time);
            this.ll_channel_jumping = (LinearLayout) findViewById(R.id.ll_channel_jumping);
            this.app_channel_jumping_text = (TextView) findViewById(R.id.app_channel_jumping_text);
            this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
            this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
            this.rl_epg_layout = (RelativeLayout) findViewById(R.id.rl_epg_layout);
            this.pb_listview_loader = (ProgressBar) findViewById(R.id.pb_listview_loader);
            this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
            this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
            this.toolbar.inflateMenu(R.menu.menu_search);
            this.appbarToolbar.requestFocusFromTouch();
            setSupportActionBar(this.toolbar);
            new Thread(new CountDownRunner()).start();
            if (SharepreferenceDBHandler.getSelectedLanguage(this.context).equalsIgnoreCase("Arabic")) {
            }
            loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
            this.programTimeFormat = new SimpleDateFormat(loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, ""), Locale.US);
            findViewById(R.id.exo_nextt).setOnClickListener(this);
            findViewById(R.id.exo_prevv).setOnClickListener(this);
            findViewById(R.id.app_video_box).setOnClickListener(this);
            this.listChannels = (ListView) findViewById(R.id.lv_ch);
            this.et_search = (EditText) findViewById(R.id.et_search);
            this.btn_cat_back.setOnClickListener(this);
            this.btn_cat_forward.setOnClickListener(this);
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
            this.ukd = Utils.ukde(FileMediaDataSource.apn());
            this.rl_middle = (RelativeLayout) findViewById(R.id.middle);
            uk = getApplicationName(this.context);
            this.liveListDetailUnlcked = new ArrayList<>();
            this.liveListDetailUnlckedDetail = new ArrayList<>();
            this.liveListDetailAvailable = new ArrayList<>();
            this.liveListDetailUnlckedChannels = new ArrayList<>();
            this.liveListDetailUnlckedDetailChannels = new ArrayList<>();
            this.liveListDetailAvailableChannels = new ArrayList<>();
            una = getApplicationContext().getPackageName();
            this.AvailableChannelsFirstOpenedCat = new ArrayList<>();
            this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + SurfaceRenderView.mw());
            if (this.tv_categories_view == null) {
            }
        }
        c = 65535;
        switch (c) {
        }
        this.opened_cat_id = getIntent().getStringExtra("OPENED_CAT_ID");
        this.catName = getIntent().getStringExtra("OPENED_CAT_NAME");
        this.catID = this.opened_cat_id;
        this.m3uVideoURL = getIntent().getStringExtra("VIDEO_URL");
        if (!this.allowedFormat.equals("")) {
        }
        this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
        this.liveStreamDBHandler = new LiveStreamDBHandler(this);
        this.database = new DatabaseHandler(this.context);
        this.handler = new Handler();
        this.handlerJumpChannel = new Handler();
        this.elv = Utils.ukde(TextureRenderView.pZGV() + TextureRenderView.mv());
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.channelLogo = (ImageView) findViewById(R.id.iv_channel_logo);
        this.currentProgram = (TextView) findViewById(R.id.tv_current_program);
        this.currentProgramTime = (TextView) findViewById(R.id.tv_current_time);
        this.nextProgram = (TextView) findViewById(R.id.tv_next_program);
        this.nextProgramTime = (TextView) findViewById(R.id.tv_next_program_time);
        this.btn_cat_back = (AppCompatImageView) findViewById(R.id.btn_category_back);
        this.btn_cat_forward = (AppCompatImageView) findViewById(R.id.btn_category_fwd);
        this.tv_categories_view = (TextView) findViewById(R.id.tv_categories_view);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.tvNoStream = (TextView) findViewById(R.id.tv_noStream);
        this.tvNoRecordFound = (TextView) findViewById(R.id.tv_no_record_found);
        this.appbarToolbar = (AppBarLayout) findViewById(R.id.appbar_toolbar);
        this.rl_settings = (RelativeLayout) findViewById(R.id.rl_settings);
        this.bufferingloader = (ProgressBar) findViewById(R.id.app_video_loading);
        this.unad = Utils.ukde(MeasureHelper.pnm());
        this.rl_nst_player_sky_layout = (RelativeLayout) findViewById(R.id.rl_nst_player_sky_layout);
        this.ll_layout_to_hide1 = (LinearLayout) findViewById(R.id.ll_layout_to_hide_1);
        this.ll_layout_to_hide4 = (LinearLayout) findViewById(R.id.ll_layout_to_hide_4);
        this.rl_layout_to_hide5 = (RelativeLayout) findViewById(R.id.rl_layout_to_hide_5);
        this.rl_layout_to_hide_6 = (RelativeLayout) findViewById(R.id.rl_layout_to_hide_6);
        this.rl_footer_info = (RelativeLayout) findViewById(R.id.rl_footer_info);
        this.rl_video_box = (RelativeLayout) findViewById(R.id.rl_video_box);
        this.ll_epg1_box = (LinearLayout) findViewById(R.id.ll_epg1_box);
        this.ll_epg2_box = (LinearLayout) findViewById(R.id.ll_epg2_box);
        this.ll_epg3_box = (LinearLayout) findViewById(R.id.ll_epg3_box);
        this.ll_epg4_box = (LinearLayout) findViewById(R.id.ll_epg4_box);
        this.ll_no_guide = (LinearLayout) findViewById(R.id.ll_no_guide);
        this.ll_epg_loader = (LinearLayout) findViewById(R.id.ll_epg_loader);
        this.tv_epg1_date = (TextView) findViewById(R.id.tv_epg1_date);
        this.tv_epg2_date = (TextView) findViewById(R.id.tv_epg2_date);
        this.tv_epg3_date = (TextView) findViewById(R.id.tv_epg3_date);
        this.tv_epg4_date = (TextView) findViewById(R.id.tv_epg4_date);
        this.tv_epg1_program = (TextView) findViewById(R.id.tv_epg1_program);
        this.tv_epg2_program = (TextView) findViewById(R.id.tv_epg2_program);
        this.tv_epg3_program = (TextView) findViewById(R.id.tv_epg3_program);
        this.tv_epg4_program = (TextView) findViewById(R.id.tv_epg4_program);
        this.dt = new Date();
        this.date = (TextView) findViewById(R.id.date);
        this.time = (TextView) findViewById(R.id.time);
        this.ll_channel_jumping = (LinearLayout) findViewById(R.id.ll_channel_jumping);
        this.app_channel_jumping_text = (TextView) findViewById(R.id.app_channel_jumping_text);
        this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
        this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
        this.rl_epg_layout = (RelativeLayout) findViewById(R.id.rl_epg_layout);
        this.pb_listview_loader = (ProgressBar) findViewById(R.id.pb_listview_loader);
        this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.toolbar.inflateMenu(R.menu.menu_search);
        this.appbarToolbar.requestFocusFromTouch();
        setSupportActionBar(this.toolbar);
        new Thread(new CountDownRunner()).start();
        if (SharepreferenceDBHandler.getSelectedLanguage(this.context).equalsIgnoreCase("Arabic")) {
        }
        loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
        this.programTimeFormat = new SimpleDateFormat(loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, ""), Locale.US);
        findViewById(R.id.exo_nextt).setOnClickListener(this);
        findViewById(R.id.exo_prevv).setOnClickListener(this);
        findViewById(R.id.app_video_box).setOnClickListener(this);
        this.listChannels = (ListView) findViewById(R.id.lv_ch);
        this.et_search = (EditText) findViewById(R.id.et_search);
        this.btn_cat_back.setOnClickListener(this);
        this.btn_cat_forward.setOnClickListener(this);
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
        this.ukd = Utils.ukde(FileMediaDataSource.apn());
        this.rl_middle = (RelativeLayout) findViewById(R.id.middle);
        uk = getApplicationName(this.context);
        this.liveListDetailUnlcked = new ArrayList<>();
        this.liveListDetailUnlckedDetail = new ArrayList<>();
        this.liveListDetailAvailable = new ArrayList<>();
        this.liveListDetailUnlckedChannels = new ArrayList<>();
        this.liveListDetailUnlckedDetailChannels = new ArrayList<>();
        this.liveListDetailAvailableChannels = new ArrayList<>();
        una = getApplicationContext().getPackageName();
        this.AvailableChannelsFirstOpenedCat = new ArrayList<>();
        this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + SurfaceRenderView.mw());
        if (this.tv_categories_view == null) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x0168 A[Catch:{ Exception -> 0x01a1 }] */
    public boolean initialize() {
        char c = 0;
        try {
            this.allLiveCategories = this.liveStreamDBHandler.getAllliveCategories();
            LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
            LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel2 = new LiveStreamCategoryIdDBModel();
            LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel3 = new LiveStreamCategoryIdDBModel();
            liveStreamCategoryIdDBModel.setLiveStreamCategoryID(AppConst.PASSWORD_UNSET);
            liveStreamCategoryIdDBModel.setLiveStreamCategoryName(this.context.getResources().getString(R.string.all));
            liveStreamCategoryIdDBModel2.setLiveStreamCategoryID("-1");
            liveStreamCategoryIdDBModel2.setLiveStreamCategoryName(this.context.getResources().getString(R.string.favourites));
            if (this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                this.countUncat = this.liveStreamDBHandler.getUncatCountM3UByType("live");
                if (this.countUncat != 0 && this.countUncat > 0) {
                    liveStreamCategoryIdDBModel3.setLiveStreamCategoryID("");
                    liveStreamCategoryIdDBModel3.setLiveStreamCategoryName(this.context.getResources().getString(R.string.uncategories));
                    this.allLiveCategories.add(this.allLiveCategories.size(), liveStreamCategoryIdDBModel3);
                }
            } else {
                this.countUncat = this.liveStreamDBHandler.getUncatCount("-2", "live");
                if (this.countUncat != 0 && this.countUncat > 0) {
                    liveStreamCategoryIdDBModel3.setLiveStreamCategoryID("-2");
                    liveStreamCategoryIdDBModel3.setLiveStreamCategoryName(this.context.getResources().getString(R.string.uncategories));
                    this.allLiveCategories.add(this.allLiveCategories.size(), liveStreamCategoryIdDBModel3);
                }
            }
            if (this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context)) > 0 && this.allLiveCategories != null) {
                if (this.listPassword != null) {
                    this.liveListDetailUnlckedDetail = getUnlockedCategories(this.allLiveCategories, this.listPassword);
                }
                this.liveListDetailUnlcked.add(0, liveStreamCategoryIdDBModel);
                this.liveListDetailUnlcked.add(1, liveStreamCategoryIdDBModel2);
                this.liveListDetailAvailable = this.liveListDetailUnlckedDetail;
            } else if (this.allLiveCategories != null) {
                this.allLiveCategories.add(0, liveStreamCategoryIdDBModel);
                this.allLiveCategories.add(1, liveStreamCategoryIdDBModel2);
                this.liveListDetailAvailable = this.allLiveCategories;
            }
            String str = this.opened_cat_id;
            int hashCode = str.hashCode();
            if (hashCode != 0) {
                if (hashCode != 48) {
                    switch (hashCode) {
                        case 1444:
                            if (str.equals("-1")) {
                                c = 1;
                                break;
                            }
                            break;
                        case 1445:
                            if (str.equals("-2")) {
                                c = 2;
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            this.catID = AppConst.PASSWORD_UNSET;
                            this.catName = this.context.getResources().getString(R.string.all);
                            this.currentCategoryIndex = 0;
                            break;
                        case 1:
                            this.catID = "-1";
                            this.catName = this.context.getResources().getString(R.string.favourites);
                            this.currentCategoryIndex = 1;
                            break;
                        case 2:
                        case 3:
                            if (this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                this.catID = "";
                            } else {
                                this.catID = "-2";
                            }
                            this.catName = this.context.getResources().getString(R.string.uncategories);
                            this.currentCategoryIndex = 2;
                            break;
                        default:
                            if (this.liveListDetailAvailable != null) {
                                int i = 0;
                                while (true) {
                                    if (i >= this.liveListDetailAvailable.size()) {
                                        break;
                                    } else if (this.liveListDetailAvailable.get(i).getLiveStreamCategoryID().equals(this.catID)) {
                                        this.currentCategoryIndex = i;
                                        break;
                                    } else {
                                        i++;
                                    }
                                }
                            }
                            break;
                    }
                    return true;
                } else if (str.equals(AppConst.PASSWORD_UNSET)) {
                    c = 0;
                    switch (c) {
                    }
                    return true;
                }
            } else if (str.equals("")) {
                c = 3;
                switch (c) {
                }
                return true;
            }
            c = 65535;
            switch (c) {
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public String catName(String str) {
        if (this.liveListDetailAvailable != null && this.liveListDetailAvailable.size() > 0) {
            Iterator<LiveStreamCategoryIdDBModel> it = this.liveListDetailAvailable.iterator();
            while (it.hasNext()) {
                LiveStreamCategoryIdDBModel next = it.next();
                if (next.getLiveStreamCategoryID() != null && !next.getLiveStreamCategoryID().isEmpty() && next.getLiveStreamCategoryID().equalsIgnoreCase(str)) {
                    this.catName = next.getLiveStreamCategoryName();
                }
            }
        }
        return this.catName;
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT < 23) {
            Log.v("TAG", "Permission is granted");
            return true;
        } else if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
            Log.v("TAG", "Permission is granted");
            return true;
        } else {
            Log.v("TAG", "Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 101);
            return false;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
     arg types: [android.widget.Button, com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity]
     candidates:
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.content.Context):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.CheckAppupdateActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.LoginActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.MultiUserActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.NewDashboardActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RateUsActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RecordingActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.SplashActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.VodActivityNewFlowSubCategories):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void */
    @Override // android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback, android.support.v4.app.FragmentActivity
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i == 101) {
            if (iArr.length > 0 && iArr[0] == 0) {
                loginPreferencesDownloadStatus = getSharedPreferences(AppConst.LOGIN_PREF_DOWNLOAD_STATUS, 0);
                String string = loginPreferencesDownloadStatus.getString(AppConst.LOGIN_PREF_DOWNLOAD_STATUS, "");
                Utils utils = new Utils();
                if (string.equals("processing")) {
                    utils.showDownloadRunningPopUP(this);
                } else {
                    utils.showRecordingPopUP(this, this.streamNameWithUnderscore, this.allowedFormat, this.mFilePath, this.finalStreamID, this.finalM3uVideoURL);
                }
            } else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(strArr[0])) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
                View inflate = LayoutInflater.from(this).inflate((int) R.layout.permission_alertbox, (ViewGroup) null);
                Button button = (Button) inflate.findViewById(R.id.btn_grant);
                Button button2 = (Button) inflate.findViewById(R.id.btn_cancel);
                button.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button, (Activity) this));
                button2.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button2, (Activity) this));
                button.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass5 */

                    public void onClick(View view) {
                        try {
                            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.fromParts("package", NSTEXOPlayerSkyActivity.this.getPackageName(), null));
                            NSTEXOPlayerSkyActivity.this.startActivityForResult(intent, 101);
                            Toast.makeText(NSTEXOPlayerSkyActivity.this, NSTEXOPlayerSkyActivity.this.context.getResources().getString(R.string.grant_the_permission), 1).show();
                        } catch (Exception unused) {
                        }
                        NSTEXOPlayerSkyActivity.this.alertDialog.dismiss();
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass6 */

                    public void onClick(View view) {
                        NSTEXOPlayerSkyActivity.this.alertDialog.dismiss();
                    }
                });
                builder.setView(inflate);
                this.alertDialog = builder.create();
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                Window window = this.alertDialog.getWindow();
                window.getClass();
                layoutParams.copyFrom(window.getAttributes());
                layoutParams.width = -1;
                layoutParams.height = -2;
                this.alertDialog.show();
                this.alertDialog.getWindow().setAttributes(layoutParams);
                this.alertDialog.setCancelable(false);
                this.alertDialog.show();
            }
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 101 && isStoragePermissionGranted()) {
            loginPreferencesDownloadStatus = getSharedPreferences(AppConst.LOGIN_PREF_DOWNLOAD_STATUS, 0);
            String string = loginPreferencesDownloadStatus.getString(AppConst.LOGIN_PREF_DOWNLOAD_STATUS, "");
            Utils utils = new Utils();
            if (string.equals("processing")) {
                utils.showDownloadRunningPopUP(this);
            } else {
                utils.showRecordingPopUP(this, this.streamNameWithUnderscore, this.allowedFormat, this.mFilePath, this.finalStreamID, this.finalM3uVideoURL);
            }
        }
    }

    public void noChannelFound() {
        AppConst.SETPLAYERLISTENER = true;
        this.app_video_status.setVisibility(0);
        this.app_video_status_text.setText(this.context.getResources().getString(R.string.no_channel_found));
        this.ll_epg_loader.setVisibility(8);
        if (AppConst.WATER_MARK.booleanValue()) {
            findViewById(R.id.watrmrk).setVisibility(8);
        }
    }

    public static String getApplicationName(Context context2) {
        return String.valueOf(context2.getApplicationInfo().loadLabel(context2.getPackageManager()));
    }

    @SuppressLint({"StaticFieldLeak"})
    class NSTEXOPlayerSkyActivityAsync extends AsyncTask<String, Void, String> {
        NSTEXOPlayerSkyActivityAsync() {
        }

        public void onPreExecute() {
            super.onPreExecute();
            NSTEXOPlayerSkyActivity.this.showLoader();
            if (NSTEXOPlayerSkyActivity.this.tvNoRecordFound != null) {
                NSTEXOPlayerSkyActivity.this.tvNoRecordFound.setVisibility(8);
            }
            if (NSTEXOPlayerSkyActivity.this.tv_categories_view != null) {
                NSTEXOPlayerSkyActivity.this.tv_categories_view.setText(NSTEXOPlayerSkyActivity.this.catName);
                NSTEXOPlayerSkyActivity.this.tv_categories_view.setSelected(true);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:20:0x0039 A[Catch:{ Exception -> 0x0051 }] */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x003a A[Catch:{ Exception -> 0x0051 }] */
        /* JADX WARNING: Removed duplicated region for block: B:23:0x0041 A[Catch:{ Exception -> 0x0051 }] */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x0048 A[Catch:{ Exception -> 0x0051 }] */
        public String doInBackground(String... strArr) {
            char c = 0;
            try {
                String str = strArr[0];
                int hashCode = str.hashCode();
                if (hashCode != -74797390) {
                    if (hashCode != 47612238) {
                        if (hashCode == 613425326) {
                            if (str.equals("all_channels")) {
                                switch (c) {
                                    case 0:
                                        return NSTEXOPlayerSkyActivity.this.allChannelsAsync();
                                    case 1:
                                        return NSTEXOPlayerSkyActivity.this.allChannelsWithCatAsync();
                                    case 2:
                                        return NSTEXOPlayerSkyActivity.this.getFavouritesAsync();
                                    default:
                                        return null;
                                }
                            }
                        }
                    } else if (str.equals("all_channels_with_cat")) {
                        c = 1;
                        switch (c) {
                        }
                    }
                } else if (str.equals("get_fav")) {
                    c = 2;
                    switch (c) {
                    }
                }
                c = 65535;
                switch (c) {
                }
            } catch (Exception unused) {
                return "error";
            }
            //ToDo: return null...
            return null;
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(5:16|17|18|19|20) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0081 */
        public void onPostExecute(String str) {
            super.onPostExecute((String) str);
            try {
                if (NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels != null) {
                    if (!NSTEXOPlayerSkyActivity.this.playedFirstTime.booleanValue()) {
                        Boolean unused = NSTEXOPlayerSkyActivity.this.playedFirstTime = true;
                        if (NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.size() != 0) {
                            NSTEXOPlayerSkyActivity.this.playFirstTime(NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels, NSTEXOPlayerSkyActivity.this.video_num);
                        } else {
                            AppConst.SETPLAYERLISTENER = false;
                            NSTEXOPlayerSkyActivity.this.noChannelFound();
                        }
                    }
                    if (NSTEXOPlayerSkyActivity.this.channelZapped && NSTEXOPlayerSkyActivity.this.channelJumping && !NSTEXOPlayerSkyActivity.this.channelNumJumping.equals("")) {
                        int unused2 = NSTEXOPlayerSkyActivity.this.positionToSelectAfterJumping = 0;
                        int unused3 = NSTEXOPlayerSkyActivity.this.positionToSelectAfterJumping = NSTEXOPlayerSkyActivity.this.getIndexOfStreams(NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels, Utils.parseIntZero(NSTEXOPlayerSkyActivity.this.channelNumJumping));
                        NSTEXOPlayerSkyActivity.this.channelZapped = false;
                        NSTEXOPlayerSkyActivity.this.channelJumping = false;
                    }
                    if (NSTEXOPlayerSkyActivity.this.PlayerType != null && NSTEXOPlayerSkyActivity.this.PlayerType.equals("live")) {
                        NSTEXOPlayerSkyActivity.this.setChannelListAdapter(NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels);
                    }
                }
                NSTEXOPlayerSkyActivity.this.hideLoader();
            } catch (Exception unused4) {
            }
        }
    }

    public void epgBeforeLoading() {
        try {
            loadingEPGData();
            if (this.ll_epg1_box != null) {
                this.ll_epg1_box.setVisibility(8);
            }
            if (this.ll_epg2_box != null) {
                this.ll_epg2_box.setVisibility(8);
            }
            if (this.ll_epg3_box != null) {
                this.ll_epg3_box.setVisibility(8);
            }
            if (this.ll_epg4_box != null) {
                this.ll_epg4_box.setVisibility(8);
            }
            if (this.ll_no_guide != null) {
                this.ll_no_guide.setVisibility(8);
            }
            if (this.ll_epg_loader != null) {
                this.ll_epg_loader.setVisibility(0);
            }
        } catch (Exception unused) {
        }
    }

    public void epgAfterLoading(HashMap<String, ArrayList<String>> hashMap) {
        try {
            if (hashMap.get("2") != null) {
                ArrayList<String> arrayList = hashMap.get("2");
                if (arrayList.get(0) == null || !arrayList.get(0).equals("installed")) {
                    this.ll_no_guide.setVisibility(0);
                    this.ll_epg_loader.setVisibility(8);
                } else if (hashMap.get(AppConst.PASSWORD_UNSET) != null) {
                    ArrayList<String> arrayList2 = hashMap.get(AppConst.PASSWORD_UNSET);
                    if (arrayList2 == null || arrayList2.size() <= 0) {
                        this.ll_epg_loader.setVisibility(8);
                    } else {
                        if (this.ll_epg_loader != null) {
                            this.ll_epg_loader.setVisibility(8);
                        }
                        if (!(hashMap.get("3") == null || this.progressBar == null)) {
                            ArrayList<String> arrayList3 = hashMap.get("3");
                            if (arrayList3.get(0) != null) {
                                this.progressBar.setProgress(Integer.parseInt(arrayList3.get(0)));
                            } else {
                                this.progressBar.setProgress(0);
                            }
                        }
                        if (arrayList2.get(0) != null) {
                            this.currentProgram.setText(arrayList2.get(0));
                        } else {
                            this.currentProgram.setText(this.context.getResources().getString(R.string.now_program_found));
                        }
                        if (arrayList2.get(1) != null) {
                            this.currentProgramTime.setText(arrayList2.get(1));
                        } else {
                            this.currentProgramTime.setText(this.context.getResources().getString(R.string.now_program_found));
                        }
                        if (arrayList2.get(2) != null) {
                            this.nextProgram.setText(arrayList2.get(2));
                        } else {
                            this.nextProgram.setText(this.context.getResources().getString(R.string.now_program_found));
                        }
                        if (arrayList2.get(3) != null) {
                            this.nextProgramTime.setText(arrayList2.get(3));
                        } else {
                            this.nextProgramTime.setText(this.context.getResources().getString(R.string.now_program_found));
                        }
                    }
                } else {
                    this.ll_epg_loader.setVisibility(8);
                }
            } else {
                this.ll_no_guide.setVisibility(0);
                this.ll_epg_loader.setVisibility(8);
            }
            if (hashMap.get("1") != null) {
                ArrayList<String> arrayList4 = hashMap.get("1");
                if (arrayList4 == null || arrayList4.size() <= 0) {
                    hideEPGData();
                    return;
                }
                if (arrayList4.get(0) != null) {
                    if (this.tv_epg1_date != null) {
                        this.tv_epg1_date.setText(arrayList4.get(0));
                    }
                } else if (this.tv_epg1_date != null) {
                    this.tv_epg1_date.setText("");
                }
                if (arrayList4.get(1) != null) {
                    if (this.tv_epg1_program != null) {
                        this.tv_epg1_program.setText(arrayList4.get(1));
                    }
                } else if (this.tv_epg1_program != null) {
                    this.tv_epg1_program.setText("");
                }
                if (this.tv_epg1_program != null) {
                    this.tv_epg1_program.setSelected(true);
                }
                if (this.ll_epg1_box != null) {
                    this.ll_epg1_box.setVisibility(0);
                }
                if (arrayList4.get(2) != null) {
                    if (this.tv_epg2_date != null) {
                        this.tv_epg2_date.setText(arrayList4.get(2));
                    }
                } else if (this.tv_epg2_date != null) {
                    this.tv_epg2_date.setText("");
                }
                if (arrayList4.get(3) != null) {
                    if (this.tv_epg2_program != null) {
                        this.tv_epg2_program.setText(arrayList4.get(3));
                    }
                } else if (this.tv_epg2_program != null) {
                    this.tv_epg2_program.setText("");
                }
                if (this.tv_epg2_program != null) {
                    this.tv_epg2_program.setSelected(true);
                }
                if (this.ll_epg2_box != null) {
                    this.ll_epg2_box.setVisibility(0);
                }
                if (arrayList4.get(4) != null) {
                    if (this.tv_epg3_date != null) {
                        this.tv_epg3_date.setText(arrayList4.get(4));
                    }
                } else if (this.tv_epg3_date != null) {
                    this.tv_epg3_date.setText("");
                }
                if (arrayList4.get(5) != null) {
                    if (this.tv_epg3_program != null) {
                        this.tv_epg3_program.setText(arrayList4.get(5));
                    }
                } else if (this.tv_epg3_program != null) {
                    this.tv_epg3_program.setText("");
                }
                if (this.tv_epg3_program != null) {
                    this.tv_epg3_program.setSelected(true);
                }
                if (this.ll_epg3_box != null) {
                    this.ll_epg3_box.setVisibility(0);
                }
                if (arrayList4.get(6) != null) {
                    if (this.tv_epg4_date != null) {
                        this.tv_epg4_date.setText(arrayList4.get(6));
                    }
                } else if (this.tv_epg4_date != null) {
                    this.tv_epg4_date.setText("");
                }
                if (arrayList4.get(7) != null) {
                    if (this.tv_epg4_program != null) {
                        this.tv_epg4_program.setText(arrayList4.get(7));
                    }
                } else if (this.tv_epg4_program != null) {
                    this.tv_epg4_program.setText("");
                }
                if (this.tv_epg4_program != null) {
                    this.tv_epg4_program.setSelected(true);
                }
                if (this.ll_epg4_box != null) {
                    this.ll_epg4_box.setVisibility(0);
                    return;
                }
                return;
            }
            hideEPGData();
        } catch (Exception unused) {
        }
    }

    @SuppressLint({"StaticFieldLeak"})
    class UpdateEPGAsyncTask extends AsyncTask<String, Void, HashMap<String, ArrayList<String>>> {
        private String currentEpgChannelID;

        private UpdateEPGAsyncTask(String str) {
            this.currentEpgChannelID = str;
        }

        public void onPreExecute() {
            super.onPreExecute();
            NSTEXOPlayerSkyActivity.this.epgBeforeLoading();
        }

        public HashMap<String, ArrayList<String>> doInBackground(String... strArr) {
            try {
                return NSTEXOPlayerSkyActivity.this.showEPGAsync(this.currentEpgChannelID);
            } catch (Exception unused) {
                return new HashMap<>();
            }
        }

        public void onPostExecute(HashMap<String, ArrayList<String>> hashMap) {
            super.onPostExecute((HashMap<String, ArrayList<String>>) hashMap);
            NSTEXOPlayerSkyActivity.this.epgAfterLoading(hashMap);
        }
    }

    @SuppressLint({"StaticFieldLeak"})
    class InitializeAsync extends AsyncTask<String, Void, Boolean> {
        InitializeAsync() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public Boolean doInBackground(String... strArr) {
            try {
                return Boolean.valueOf(NSTEXOPlayerSkyActivity.this.initialize());
            } catch (Exception unused) {
                return false;
            }
        }

        public void onPostExecute(Boolean bool) {
            super.onPostExecute((Boolean) bool);
        }
    }

    public String allChannelsAsync() {
        try {
            if (!(this.liveListDetailAvailableChannels == null || this.liveListDetailUnlckedChannels == null)) {
                this.liveListDetailAvailableChannels.clear();
                this.liveListDetailUnlckedChannels.clear();
            }
            int parentalStatusCount = this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context));
            this.allStreams = this.liveStreamDBHandler.getAllLiveStreasWithCategoryId(AppConst.PASSWORD_UNSET, "live");
            if (parentalStatusCount <= 0 || this.allStreams == null) {
                this.liveListDetailAvailableChannels = this.allStreams;
                return "all_channels";
            }
            if (this.listPassword != null) {
                this.liveListDetailUnlckedDetailChannels = getUnlockedChannels(this.allStreams, this.listPassword);
            }
            this.liveListDetailAvailableChannels = this.liveListDetailUnlckedDetailChannels;
            return "all_channels";
        } catch (Exception unused) {
            return "all_channels";
        }
    }

    public String allChannelsWithCatAsync() {
        try {
            if (!(this.liveListDetailAvailableChannels == null || this.liveListDetailUnlckedChannels == null)) {
                this.liveListDetailAvailableChannels.clear();
                this.liveListDetailUnlckedChannels.clear();
            }
            if (this.liveStreamDBHandler != null) {
                this.AvailableChannelsFirstOpenedCat = this.liveStreamDBHandler.getAllLiveStreasWithCategoryId(this.catID, "live");
            }
            this.liveListDetailAvailableChannels = this.AvailableChannelsFirstOpenedCat;
            return "all_channels_with_cat";
        } catch (Exception unused) {
            return "all_channels_with_cat";
        }
    }

    public String getFavouritesAsync() {
        try {
            if (!(this.liveListDetailAvailableChannels == null || this.liveListDetailUnlckedChannels == null)) {
                this.liveListDetailAvailableChannels.clear();
                this.liveListDetailUnlckedChannels.clear();
            }
            this.allStreams_with_cat = new ArrayList<>();
            getFavourites();
            this.liveListDetailAvailableChannels = this.allStreams_with_cat;
            return "get_fav";
        } catch (Exception unused) {
            return "get_fav";
        }
    }

    public void showLoader() {
        if (this.pb_listview_loader != null) {
            this.pb_listview_loader.setVisibility(0);
        }
    }

    public void hideLoader() {
        if (this.pb_listview_loader != null) {
            this.pb_listview_loader.setVisibility(8);
        }
    }

    private void checkIfAsyncTaskRunning() {
        if (this.AsyncTaskNSTEXOPlayerSkyActivity == null || !this.AsyncTaskNSTEXOPlayerSkyActivity.getStatus().equals(AsyncTask.Status.RUNNING)) {
            SharepreferenceDBHandler.setAsyncTaskStatus(0, this.context);
            return;
        }
        SharepreferenceDBHandler.setAsyncTaskStatus(1, this.context);
        this.AsyncTaskNSTEXOPlayerSkyActivity.cancel(true);
    }

    public void allChannels() {
        checkIfAsyncTaskRunning();
        this.AsyncTaskNSTEXOPlayerSkyActivity = new NSTEXOPlayerSkyActivityAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "all_channels");
    }

    public void allFavourites() {
        checkIfAsyncTaskRunning();
        this.AsyncTaskNSTEXOPlayerSkyActivity = new NSTEXOPlayerSkyActivityAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "get_fav");
    }

    public void allChannelsWithCat() {
        checkIfAsyncTaskRunning();
        this.AsyncTaskNSTEXOPlayerSkyActivity = new NSTEXOPlayerSkyActivityAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "all_channels_with_cat");
    }

    @Override // com.afollestad.materialdialogs.folderselector.FileChooserDialog.FileCallback
    public void onFileSelection(@NonNull FileChooserDialog fileChooserDialog, @NonNull File file) {
        fileChooserDialog.getTag();
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    NSTEXOPlayerSkyActivity.this.doWork();
                    Thread.sleep(500);
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                } catch (Exception unused2) {
                }
            }
        }
    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass7 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(NSTEXOPlayerSkyActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (NSTEXOPlayerSkyActivity.this.time != null) {
                        NSTEXOPlayerSkyActivity.this.time.setText(time);
                    }
                    if (NSTEXOPlayerSkyActivity.this.date != null) {
                        NSTEXOPlayerSkyActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    public int getIndexOfMovies(ArrayList<LiveStreamsDBModel> arrayList, int i) {
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (Utils.parseIntZero(arrayList.get(i2).getNum()) == i) {
                return i2;
            }
        }
        return 0;
    }

    @SuppressLint({"SetTextI18n"})
    public void playFirstTime(ArrayList<LiveStreamsDBModel> arrayList, int i) {
        if (arrayList != null && arrayList.size() > 0 && this.opened_vlc_id < arrayList.size()) {
            if (this.PlayerType != null && this.PlayerType.equals("large_epg") && this.opened_vlc_id == 0) {
                this.opened_vlc_id = getIndexOfMovies(arrayList, i);
            }
            int parseIntZero = Utils.parseIntZero(arrayList.get(this.opened_vlc_id).getNum());
            String name = arrayList.get(this.opened_vlc_id).getName();
            int parseIntMinusOne = Utils.parseIntMinusOne(arrayList.get(this.opened_vlc_id).getStreamId());
            String epgChannelId = arrayList.get(this.opened_vlc_id).getEpgChannelId();
            String streamIcon = arrayList.get(this.opened_vlc_id).getStreamIcon();
            this.m3uVideoURL = arrayList.get(this.opened_vlc_id).getUrl();
            String ukde = Utils.ukde(TableLayoutBinder.aW5nIGl() + TableLayoutBinder.mu());
            if (streamIcon.equals("") || streamIcon.isEmpty()) {
                this.channelLogo.setImageDrawable(this.context.getResources().getDrawable(R.drawable.logo_placeholder_white));
            } else {
                Picasso.with(this.context).load(streamIcon).placeholder((int) R.drawable.logo_placeholder_white).error((int) R.drawable.logo_placeholder_white).into(this.channelLogo);
            }
            setCurrentWindowIndex(this.opened_vlc_id);
            if (this.loginPrefsEditor != null) {
                this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(arrayList.get(this.opened_vlc_id).getStreamId()));
                this.loginPrefsEditor.putString("LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_M3U", String.valueOf(arrayList.get(this.opened_vlc_id).getUrl()));
                this.loginPrefsEditor.apply();
            }
            if (this.loginPrefsEditorPosition != null) {
                this.loginPrefsEditorPosition.putInt(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, this.opened_vlc_id);
                this.loginPrefsEditorPosition.apply();
            }
            if (df(this.fr, this.fr.format(new Date(MediaPlayerCompat.cit(this.context))), this.df.format(this.dt)) >= ((long) InfoHudViewHolder.ux()) && this.ukd != null && this.unad != null && (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad)))) {
                this.rq = false;
                this.app_video_status.setVisibility(0);
                TextView textView = this.app_video_status_text;
                textView.setText(ukde + this.elv + this.fmw);
            }
            this.currentProgramStreamID = parseIntMinusOne;
            this.currentProgramChanneID = epgChannelId;
            this.currentProgramM3UURL = this.m3uVideoURL;
            setTitle(parseIntZero + " - " + name);
            if (this.rq.booleanValue()) {
                if (this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                    this.playingURL = Uri.parse(this.m3uVideoURL);
                } else {
                    this.playingURL = Uri.parse(this.mFilePath + parseIntMinusOne + this.allowedFormat);
                }
                initializePlayer();
                this.retryCount = 0;
                this.retrying = false;
            }
            this.currentEpgChannelID = epgChannelId;
            this.currentChannelLogo = streamIcon;
            updateChannelLogo(this.currentChannelLogo);
            epgDisplay(this.currentEpgChannelID);
            if (this.listChannels != null) {
                this.listChannels.requestFocus();
            }
        }
    }

    public void epgDisplay(String str) {
        if (this.PlayerType == null || !this.PlayerType.equals("large_epg")) {
            this.AsyncTaskUpdateEPG = new UpdateEPGAsyncTask(str).execute(new String[0]);
            return;
        }
        epgBeforeLoading();
        epgAfterLoading(showEPGAsync(str));
    }

    public void setTitle(String str) {
        TextView textView = new TextView(this);
        textView.setText(str);
        textView.setTextSize(24.0f);
        textView.setPadding(18, 8, 8, 8);
        textView.setTextColor(-1);
        this.debugRootView.removeAllViews();
        this.debugRootView.addView(textView);
    }

    @SuppressLint({"NewApi"})
    public void setChannelListAdapter(ArrayList<LiveStreamsDBModel> arrayList) {
        if (arrayList != null) {
            try {
                this.no_channel = false;
                this.tvNoRecordFound.setVisibility(8);
                if (arrayList.size() == 0) {
                    if (!this.retrySetAdapter) {
                        this.retrySetAdapter = true;
                        if (!(this.liveListDetailAvailableChannels == null || this.liveListDetailUnlckedChannels == null)) {
                            this.liveListDetailAvailableChannels.clear();
                            this.liveListDetailUnlckedChannels.clear();
                        }
                        int parentalStatusCount = this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context));
                        ArrayList<LiveStreamsDBModel> allLiveStreasWithCategoryId = this.liveStreamDBHandler.getAllLiveStreasWithCategoryId(this.catID, "live");
                        if (parentalStatusCount <= 0 || allLiveStreasWithCategoryId == null) {
                            this.liveListDetailAvailableChannels = allLiveStreasWithCategoryId;
                        } else {
                            if (this.listPassword != null) {
                                this.liveListDetailUnlckedDetailChannels = getUnlockedChannels(allLiveStreasWithCategoryId, this.listPassword);
                            }
                            this.liveListDetailAvailableChannels = this.liveListDetailUnlckedDetailChannels;
                        }
                        if (this.liveListDetailAvailableChannels != null && this.liveListDetailAvailableChannels.size() > 0) {
                            setChannelListAdapter(this.liveListDetailAvailableChannels);
                        } else if (this.tvNoRecordFound != null) {
                            this.no_channel = true;
                            this.tvNoRecordFound.setFocusable(true);
                            this.tvNoRecordFound.requestFocus();
                            this.tvNoRecordFound.setVisibility(0);
                        }
                    } else if (this.tvNoRecordFound != null) {
                        this.no_channel = true;
                        this.tvNoRecordFound.setFocusable(true);
                        this.tvNoRecordFound.requestFocus();
                        this.tvNoRecordFound.setVisibility(0);
                    }
                }
                this.adapter = new SearchableAdapter(this, arrayList);
                this.adapter.settesxtviewvisibility(this.tvNoRecordFound);
                if (this.listChannels != null) {
                    this.listChannels.setAdapter((ListAdapter) this.adapter);
                    if (this.positionToSelectAfterJumping != 0) {
                        this.listChannels.setSelection(this.positionToSelectAfterJumping);
                    }
                    this.listChannels.requestFocus();
                    this.adapter.notifyDataSetChanged();
                    this.listChannels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass8 */

                        @Override // android.widget.AdapterView.OnItemSelectedListener
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }

                        @Override // android.widget.AdapterView.OnItemSelectedListener
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                            if (NSTEXOPlayerSkyActivity.this.AsyncTaskUpdateEPG != null && NSTEXOPlayerSkyActivity.this.AsyncTaskUpdateEPG.getStatus().equals(AsyncTask.Status.RUNNING)) {
                                NSTEXOPlayerSkyActivity.this.AsyncTaskUpdateEPG.cancel(true);
                            }
                            ArrayList<LiveStreamsDBModel> filteredData = NSTEXOPlayerSkyActivity.this.adapter.getFilteredData();
                            if (filteredData != null && filteredData.size() > 0) {
                                NSTEXOPlayerSkyActivity.this.epgDisplay(filteredData.get(i).getEpgChannelId());
                            } else if (NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels != null && NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.size() > 0) {
                                NSTEXOPlayerSkyActivity.this.epgDisplay(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getEpgChannelId());
                            }
                        }
                    });
                    this.listChannels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass9 */

                        @Override // android.widget.AdapterView.OnItemClickListener
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                            if (NSTEXOPlayerSkyActivity.this.AsyncTaskUpdateEPG != null && NSTEXOPlayerSkyActivity.this.AsyncTaskUpdateEPG.getStatus().equals(AsyncTask.Status.RUNNING)) {
                                NSTEXOPlayerSkyActivity.this.AsyncTaskUpdateEPG.cancel(true);
                            }
                            AppConst.SETPLAYERLISTENER = true;
                            ArrayList<LiveStreamsDBModel> filteredData = NSTEXOPlayerSkyActivity.this.adapter.getFilteredData();
                            if (filteredData != null && filteredData.size() > 0) {
                                int parseIntZero = Utils.parseIntZero(filteredData.get(i).getNum());
                                String epgChannelId = filteredData.get(i).getEpgChannelId();
                                NSTEXOPlayerSkyActivity.this.getIndexOfStreams(NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels, parseIntZero);
                                String url = filteredData.get(i).getUrl();
                                if (NSTEXOPlayerSkyActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                    if (!NSTEXOPlayerSkyActivity.this.currentProgramM3UURL.equals(filteredData.get(i).getUrl())) {
                                        NSTEXOPlayerSkyActivity.this.setCurrentWindowIndex(i);
                                        NSTEXOPlayerSkyActivity nSTEXOPlayerSkyActivity = NSTEXOPlayerSkyActivity.this;
                                        nSTEXOPlayerSkyActivity.setTitle(filteredData.get(i).getNum() + " - " + filteredData.get(i).getName());
                                        NSTEXOPlayerSkyActivity.this.currentProgramM3UURL = filteredData.get(i).getUrl();
                                        if (!NSTEXOPlayerSkyActivity.this.catID.equals("-1") && !NSTEXOPlayerSkyActivity.this.catID.equals(AppConst.PASSWORD_UNSET)) {
                                            NSTEXOPlayerSkyActivity.this.catID = filteredData.get(i).getCategoryId();
                                            NSTEXOPlayerSkyActivity.this.catName = filteredData.get(i).getCategoryName();
                                            if (NSTEXOPlayerSkyActivity.this.catName.isEmpty()) {
                                                NSTEXOPlayerSkyActivity.this.catName = NSTEXOPlayerSkyActivity.this.catName(NSTEXOPlayerSkyActivity.this.catID);
                                            }
                                        }
                                        String streamIcon = filteredData.get(i).getStreamIcon();
                                        if (streamIcon.equals("") || streamIcon.isEmpty()) {
                                            NSTEXOPlayerSkyActivity.this.channelLogo.setImageDrawable(NSTEXOPlayerSkyActivity.this.context.getResources().getDrawable(R.drawable.logo_placeholder_white));
                                        } else {
                                            Picasso.with(NSTEXOPlayerSkyActivity.this.context).load(streamIcon).placeholder((int) R.drawable.logo_placeholder_white).error((int) R.drawable.logo_placeholder_white).into(NSTEXOPlayerSkyActivity.this.channelLogo);
                                        }
                                        NSTEXOPlayerSkyActivity.this.currentEpgChannelID = epgChannelId;
                                        NSTEXOPlayerSkyActivity.this.currentChannelLogo = streamIcon;
                                        NSTEXOPlayerSkyActivity.this.updateChannelLogo(NSTEXOPlayerSkyActivity.this.currentChannelLogo);
                                        NSTEXOPlayerSkyActivity.this.epgDisplay(NSTEXOPlayerSkyActivity.this.currentEpgChannelID);
                                        int unused = NSTEXOPlayerSkyActivity.this.opened_vlc_id = i;
                                        if (NSTEXOPlayerSkyActivity.this.rq.booleanValue()) {
                                            NSTEXOPlayerSkyActivity.this.releasePlayer();
                                            NSTEXOPlayerSkyActivity.this.retryCount = 0;
                                            NSTEXOPlayerSkyActivity.this.retrying = false;
                                            NSTEXOPlayerSkyActivity.this.playingURL = Uri.parse(url);
                                            NSTEXOPlayerSkyActivity.this.initializePlayer();
                                        }
                                        if (NSTEXOPlayerSkyActivity.this.loginPrefsEditor != null) {
                                            NSTEXOPlayerSkyActivity.this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(filteredData.get(i).getStreamId()));
                                            NSTEXOPlayerSkyActivity.this.loginPrefsEditor.putString("LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_M3U", String.valueOf(filteredData.get(i).getUrl()));
                                            NSTEXOPlayerSkyActivity.this.loginPrefsEditor.apply();
                                        }
                                        if (NSTEXOPlayerSkyActivity.this.loginPrefsEditorPosition != null) {
                                            NSTEXOPlayerSkyActivity.this.loginPrefsEditorPosition.putInt(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, i);
                                            NSTEXOPlayerSkyActivity.this.loginPrefsEditorPosition.apply();
                                        }
                                        if (NSTEXOPlayerSkyActivity.this.PlayerType != null && NSTEXOPlayerSkyActivity.this.PlayerType.equals("live")) {
                                            NSTEXOPlayerSkyActivity.this.adapter.notifyDataSetChanged();
                                            return;
                                        }
                                        return;
                                    }
                                    NSTEXOPlayerSkyActivity.this.fullScreenVideoLayout();
                                } else if (NSTEXOPlayerSkyActivity.this.currentProgramStreamID != Utils.parseIntMinusOne(filteredData.get(i).getStreamId())) {
                                    NSTEXOPlayerSkyActivity.this.setCurrentWindowIndex(i);
                                    NSTEXOPlayerSkyActivity nSTEXOPlayerSkyActivity2 = NSTEXOPlayerSkyActivity.this;
                                    nSTEXOPlayerSkyActivity2.setTitle(filteredData.get(i).getNum() + " - " + filteredData.get(i).getName());
                                    NSTEXOPlayerSkyActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(filteredData.get(i).getStreamId());
                                    if (!NSTEXOPlayerSkyActivity.this.catID.equals("-1") && !NSTEXOPlayerSkyActivity.this.catID.equals(AppConst.PASSWORD_UNSET)) {
                                        NSTEXOPlayerSkyActivity.this.catID = filteredData.get(i).getCategoryId();
                                        NSTEXOPlayerSkyActivity.this.catName = filteredData.get(i).getCategoryName();
                                        if (NSTEXOPlayerSkyActivity.this.catName.isEmpty()) {
                                            NSTEXOPlayerSkyActivity.this.catName = NSTEXOPlayerSkyActivity.this.catName(NSTEXOPlayerSkyActivity.this.catID);
                                        }
                                    }
                                    String streamIcon2 = filteredData.get(i).getStreamIcon();
                                    if (streamIcon2.equals("") || streamIcon2.isEmpty()) {
                                        NSTEXOPlayerSkyActivity.this.channelLogo.setImageDrawable(NSTEXOPlayerSkyActivity.this.context.getResources().getDrawable(R.drawable.logo_placeholder_white));
                                    } else {
                                        Picasso.with(NSTEXOPlayerSkyActivity.this.context).load(streamIcon2).placeholder((int) R.drawable.logo_placeholder_white).error((int) R.drawable.logo_placeholder_white).into(NSTEXOPlayerSkyActivity.this.channelLogo);
                                    }
                                    NSTEXOPlayerSkyActivity.this.currentEpgChannelID = epgChannelId;
                                    NSTEXOPlayerSkyActivity.this.currentChannelLogo = streamIcon2;
                                    NSTEXOPlayerSkyActivity.this.updateChannelLogo(NSTEXOPlayerSkyActivity.this.currentChannelLogo);
                                    NSTEXOPlayerSkyActivity.this.epgDisplay(NSTEXOPlayerSkyActivity.this.currentEpgChannelID);
                                    int unused2 = NSTEXOPlayerSkyActivity.this.opened_vlc_id = i;
                                    if (NSTEXOPlayerSkyActivity.this.rq.booleanValue()) {
                                        NSTEXOPlayerSkyActivity.this.releasePlayer();
                                        NSTEXOPlayerSkyActivity nSTEXOPlayerSkyActivity3 = NSTEXOPlayerSkyActivity.this;
                                        nSTEXOPlayerSkyActivity3.playingURL = Uri.parse(NSTEXOPlayerSkyActivity.this.mFilePath + Utils.parseIntMinusOne(filteredData.get(i).getStreamId()) + NSTEXOPlayerSkyActivity.this.allowedFormat);
                                        NSTEXOPlayerSkyActivity.this.initializePlayer();
                                        NSTEXOPlayerSkyActivity.this.retryCount = 0;
                                        NSTEXOPlayerSkyActivity.this.retrying = false;
                                    }
                                    if (NSTEXOPlayerSkyActivity.this.loginPrefsEditor != null) {
                                        NSTEXOPlayerSkyActivity.this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(filteredData.get(i).getStreamId()));
                                        NSTEXOPlayerSkyActivity.this.loginPrefsEditor.putString("LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_M3U", String.valueOf(filteredData.get(i).getUrl()));
                                        NSTEXOPlayerSkyActivity.this.loginPrefsEditor.apply();
                                    }
                                    if (NSTEXOPlayerSkyActivity.this.loginPrefsEditorPosition != null) {
                                        NSTEXOPlayerSkyActivity.this.loginPrefsEditorPosition.putInt(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, i);
                                        NSTEXOPlayerSkyActivity.this.loginPrefsEditorPosition.apply();
                                    }
                                    if (NSTEXOPlayerSkyActivity.this.PlayerType != null && NSTEXOPlayerSkyActivity.this.PlayerType.equals("live")) {
                                        NSTEXOPlayerSkyActivity.this.adapter.notifyDataSetChanged();
                                    }
                                } else {
                                    NSTEXOPlayerSkyActivity.this.fullScreenVideoLayout();
                                }
                            } else if (NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels != null && NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.size() > 0) {
                                Utils.parseIntZero(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getNum());
                                String epgChannelId2 = ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getEpgChannelId();
                                String streamIcon3 = ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getStreamIcon();
                                String url2 = ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getUrl();
                                if (NSTEXOPlayerSkyActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                    if (!NSTEXOPlayerSkyActivity.this.currentProgramM3UURL.equals(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getUrl())) {
                                        NSTEXOPlayerSkyActivity.this.setCurrentWindowIndex(i);
                                        NSTEXOPlayerSkyActivity nSTEXOPlayerSkyActivity4 = NSTEXOPlayerSkyActivity.this;
                                        nSTEXOPlayerSkyActivity4.setTitle(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getNum() + " - " + ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getName());
                                        NSTEXOPlayerSkyActivity.this.currentProgramM3UURL = ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getUrl();
                                        if (!NSTEXOPlayerSkyActivity.this.catID.equals("-1") && !NSTEXOPlayerSkyActivity.this.catID.equals(AppConst.PASSWORD_UNSET)) {
                                            NSTEXOPlayerSkyActivity.this.catID = ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getCategoryId();
                                            NSTEXOPlayerSkyActivity.this.catName = ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getCategoryName();
                                            if (NSTEXOPlayerSkyActivity.this.catName.isEmpty()) {
                                                NSTEXOPlayerSkyActivity.this.catName = NSTEXOPlayerSkyActivity.this.catName(NSTEXOPlayerSkyActivity.this.catID);
                                            }
                                        }
                                        int unused3 = NSTEXOPlayerSkyActivity.this.opened_vlc_id = i;
                                        if (NSTEXOPlayerSkyActivity.this.rq.booleanValue()) {
                                            NSTEXOPlayerSkyActivity.this.releasePlayer();
                                            NSTEXOPlayerSkyActivity.this.playingURL = Uri.parse(url2);
                                            NSTEXOPlayerSkyActivity.this.retryCount = 0;
                                            NSTEXOPlayerSkyActivity.this.retrying = false;
                                            NSTEXOPlayerSkyActivity.this.initializePlayer();
                                        }
                                        NSTEXOPlayerSkyActivity.this.currentEpgChannelID = epgChannelId2;
                                        NSTEXOPlayerSkyActivity.this.currentChannelLogo = streamIcon3;
                                        NSTEXOPlayerSkyActivity.this.updateChannelLogo(NSTEXOPlayerSkyActivity.this.currentChannelLogo);
                                        NSTEXOPlayerSkyActivity.this.epgDisplay(NSTEXOPlayerSkyActivity.this.currentEpgChannelID);
                                        if (NSTEXOPlayerSkyActivity.this.loginPrefsEditor != null) {
                                            NSTEXOPlayerSkyActivity.this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getStreamId()));
                                            NSTEXOPlayerSkyActivity.this.loginPrefsEditor.putString("LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_M3U", String.valueOf(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getUrl()));
                                            NSTEXOPlayerSkyActivity.this.loginPrefsEditor.apply();
                                        }
                                        if (NSTEXOPlayerSkyActivity.this.loginPrefsEditorPosition != null) {
                                            NSTEXOPlayerSkyActivity.this.loginPrefsEditorPosition.putInt(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, i);
                                            NSTEXOPlayerSkyActivity.this.loginPrefsEditorPosition.apply();
                                        }
                                        if (NSTEXOPlayerSkyActivity.this.PlayerType != null && NSTEXOPlayerSkyActivity.this.PlayerType.equals("live")) {
                                            NSTEXOPlayerSkyActivity.this.adapter.notifyDataSetChanged();
                                            return;
                                        }
                                        return;
                                    }
                                    NSTEXOPlayerSkyActivity.this.fullScreenVideoLayout();
                                } else if (NSTEXOPlayerSkyActivity.this.currentProgramStreamID != Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getStreamId())) {
                                    NSTEXOPlayerSkyActivity.this.setCurrentWindowIndex(i);
                                    NSTEXOPlayerSkyActivity nSTEXOPlayerSkyActivity5 = NSTEXOPlayerSkyActivity.this;
                                    nSTEXOPlayerSkyActivity5.setTitle(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getNum() + " - " + ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getName());
                                    NSTEXOPlayerSkyActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getStreamId());
                                    if (!NSTEXOPlayerSkyActivity.this.catID.equals("-1") && !NSTEXOPlayerSkyActivity.this.catID.equals(AppConst.PASSWORD_UNSET)) {
                                        NSTEXOPlayerSkyActivity.this.catID = ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getCategoryId();
                                        NSTEXOPlayerSkyActivity.this.catName = ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getCategoryName();
                                        if (NSTEXOPlayerSkyActivity.this.catName.isEmpty()) {
                                            NSTEXOPlayerSkyActivity.this.catName = NSTEXOPlayerSkyActivity.this.catName(NSTEXOPlayerSkyActivity.this.catID);
                                        }
                                    }
                                    int unused4 = NSTEXOPlayerSkyActivity.this.opened_vlc_id = i;
                                    if (NSTEXOPlayerSkyActivity.this.rq.booleanValue()) {
                                        NSTEXOPlayerSkyActivity.this.retryCount = 0;
                                        NSTEXOPlayerSkyActivity.this.retrying = false;
                                        NSTEXOPlayerSkyActivity.this.releasePlayer();
                                        NSTEXOPlayerSkyActivity nSTEXOPlayerSkyActivity6 = NSTEXOPlayerSkyActivity.this;
                                        nSTEXOPlayerSkyActivity6.playingURL = Uri.parse(NSTEXOPlayerSkyActivity.this.mFilePath + Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getStreamId()) + NSTEXOPlayerSkyActivity.this.allowedFormat);
                                        NSTEXOPlayerSkyActivity.this.initializePlayer();
                                    }
                                    NSTEXOPlayerSkyActivity.this.currentEpgChannelID = epgChannelId2;
                                    NSTEXOPlayerSkyActivity.this.currentChannelLogo = streamIcon3;
                                    NSTEXOPlayerSkyActivity.this.updateChannelLogo(NSTEXOPlayerSkyActivity.this.currentChannelLogo);
                                    NSTEXOPlayerSkyActivity.this.epgDisplay(NSTEXOPlayerSkyActivity.this.currentEpgChannelID);
                                    if (NSTEXOPlayerSkyActivity.this.loginPrefsEditor != null) {
                                        NSTEXOPlayerSkyActivity.this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getStreamId()));
                                        NSTEXOPlayerSkyActivity.this.loginPrefsEditor.putString("LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_M3U", String.valueOf(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getUrl()));
                                        NSTEXOPlayerSkyActivity.this.loginPrefsEditor.apply();
                                    }
                                    if (NSTEXOPlayerSkyActivity.this.loginPrefsEditorPosition != null) {
                                        NSTEXOPlayerSkyActivity.this.loginPrefsEditorPosition.putInt(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, i);
                                        NSTEXOPlayerSkyActivity.this.loginPrefsEditorPosition.apply();
                                    }
                                    if (NSTEXOPlayerSkyActivity.this.PlayerType != null && NSTEXOPlayerSkyActivity.this.PlayerType.equals("live")) {
                                        NSTEXOPlayerSkyActivity.this.adapter.notifyDataSetChanged();
                                    }
                                } else {
                                    NSTEXOPlayerSkyActivity.this.fullScreenVideoLayout();
                                }
                            }
                        }
                    });
                    this.listChannels.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass10 */

                        @Override // android.widget.AdapterView.OnItemLongClickListener
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                            final String str;
                            final String str2;
                            final String str3;
                            int i2;
                            int i3 = i;
                            ArrayList<LiveStreamsDBModel> filteredData = NSTEXOPlayerSkyActivity.this.adapter.getFilteredData();
                            String str4 = "";
                            String str5 = "";
                            if (filteredData != null && filteredData.size() > 0) {
                                String categoryId = filteredData.get(i3).getCategoryId();
                                int parseIntMinusOne = Utils.parseIntMinusOne(filteredData.get(i3).getStreamId());
                                str5 = filteredData.get(i3).getUrl();
                                String name = filteredData.get(i3).getName();
                                String num = filteredData.get(i3).getNum();
                                str = filteredData.get(i3).getStreamIcon();
                                str3 = categoryId;
                                i2 = parseIntMinusOne;
                                str2 = name;
                                str4 = num;
                            } else if (NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels == null || NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.size() <= 0) {
                                str = "";
                                str2 = "";
                                str3 = "";
                                i2 = 0;
                            } else {
                                String categoryId2 = ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i3)).getCategoryId();
                                i2 = Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i3)).getStreamId());
                                String name2 = ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i3)).getName();
                                str5 = ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i3)).getUrl();


                                str4 = ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i3)).getNum();
                                str = ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i3)).getStreamIcon();
                                str2 = name2;
                                str3 = categoryId2;
                            }
                            PopupMenu popupMenu = new PopupMenu(NSTEXOPlayerSkyActivity.this, view);
                            popupMenu.getMenuInflater().inflate(R.menu.menu_players_selection_with_fav, popupMenu.getMenu());
                            if (NSTEXOPlayerSkyActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                ArrayList<FavouriteM3UModel> checkFavourite = NSTEXOPlayerSkyActivity.this.liveStreamDBHandler.checkFavourite(str5, SharepreferenceDBHandler.getUserID(NSTEXOPlayerSkyActivity.this.context));
                                if (checkFavourite == null || checkFavourite.size() <= 0) {
                                    popupMenu.getMenu().getItem(2).setVisible(true);
                                } else {
                                    popupMenu.getMenu().getItem(3).setVisible(true);
                                }
                            } else {
                                ArrayList<FavouriteDBModel> checkFavourite2 = NSTEXOPlayerSkyActivity.this.database.checkFavourite(i2, str3, "live", SharepreferenceDBHandler.getUserID(NSTEXOPlayerSkyActivity.this.context));
                                if (checkFavourite2 == null || checkFavourite2.size() <= 0) {
                                    popupMenu.getMenu().getItem(2).setVisible(true);
                                } else {
                                    popupMenu.getMenu().getItem(3).setVisible(true);
                                }
                            }
                            try {
                                CastSession unused = NSTEXOPlayerSkyActivity.this.mCastSession = CastContext.getSharedInstance(NSTEXOPlayerSkyActivity.this.context).getSessionManager().getCurrentCastSession();
                                if (NSTEXOPlayerSkyActivity.this.mCastSession == null || !NSTEXOPlayerSkyActivity.this.mCastSession.isConnected()) {
                                    popupMenu.getMenu().getItem(5).setVisible(false);
                                } else {
                                    popupMenu.getMenu().getItem(5).setVisible(true);
                                }
                            } catch (Exception unused2) {
                            }
                            NSTEXOPlayerSkyActivity.this.externalPlayerList = new ArrayList<>();
                            ExternalPlayerDataBase externalPlayerDataBase = new ExternalPlayerDataBase(NSTEXOPlayerSkyActivity.this.context);
                            NSTEXOPlayerSkyActivity.this.externalPlayerList = externalPlayerDataBase.getExternalPlayer();
                            try {
                                if (NSTEXOPlayerSkyActivity.this.externalPlayerList != null && NSTEXOPlayerSkyActivity.this.externalPlayerList.size() > 0) {
                                    for (int i4 = 0; i4 < NSTEXOPlayerSkyActivity.this.externalPlayerList.size(); i4++) {
                                        popupMenu.getMenu().add(0, i4, i4, NSTEXOPlayerSkyActivity.this.externalPlayerList.get(i4).getAppname());
                                    }
                                }
                            } catch (Exception unused3) {
                            }
                            NSTEXOPlayerSkyActivity.this.finalStreamID = i2;
                            NSTEXOPlayerSkyActivity.this.finalM3uVideoURL = str5;
                            NSTEXOPlayerSkyActivity.this.streamNameWithUnderscore = str2.replaceAll(" ", "_").toLowerCase();
                            NSTEXOPlayerSkyActivity.this.streamNameWithUnderscore = NSTEXOPlayerSkyActivity.this.streamNameWithUnderscore.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
                            final String str6 = NSTEXOPlayerSkyActivity.this.streamNameWithUnderscore;
                            String finalStr = str4;
                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass10.AnonymousClass1 */
                                final String val$finalStreamName;

                                {
                                    this.val$finalStreamName = str2;
                                }

                                public boolean onMenuItemClick(MenuItem menuItem) {
                                    try {
                                        if (NSTEXOPlayerSkyActivity.this.rq.booleanValue() && NSTEXOPlayerSkyActivity.this.externalPlayerList != null && NSTEXOPlayerSkyActivity.this.externalPlayerList.size() > 0) {
                                            int i = 0;
                                            while (true) {
                                                if (i >= NSTEXOPlayerSkyActivity.this.externalPlayerList.size()) {
                                                    break;
                                                } else if (menuItem.getItemId() == i) {
                                                    NSTEXOPlayerSkyActivity.this.externalPlayerSelected = true;
                                                    NSTEXOPlayerSkyActivity.this.releasePlayer();
                                                    if (NSTEXOPlayerSkyActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                                        NSTEXOPlayerSkyActivity.this.url = NSTEXOPlayerSkyActivity.this.finalM3uVideoURL;
                                                    } else {
                                                        NSTEXOPlayerSkyActivity.this.url = Utils.getUrlLive(NSTEXOPlayerSkyActivity.this.context, NSTEXOPlayerSkyActivity.this.finalStreamID, NSTEXOPlayerSkyActivity.this.allowedFormat, "live");
                                                    }
                                                    Intent intent = new Intent(NSTEXOPlayerSkyActivity.this.context, PlayExternalPlayerActivity.class);
                                                    intent.putExtra("url", NSTEXOPlayerSkyActivity.this.url);
                                                    intent.putExtra(AppConst.APP_NAME, NSTEXOPlayerSkyActivity.this.externalPlayerList.get(i).getAppname());
                                                    intent.putExtra(AppConst.PACKAGE_NAME, NSTEXOPlayerSkyActivity.this.externalPlayerList.get(i).getPackagename());
                                                    NSTEXOPlayerSkyActivity.this.context.startActivity(intent);
                                                } else {
                                                    i++;
                                                }
                                            }
                                        }
                                    } catch (Exception unused) {
                                    }
                                    int itemId = menuItem.getItemId();
                                    if (itemId != R.id.nav_add_to_fav) {
                                        if (itemId != R.id.nav_recording) {
                                            if (itemId != R.id.nav_remove_from_fav) {
                                                if (itemId == R.id.play_with_cast) {
                                                    try {
                                                        if (NSTEXOPlayerSkyActivity.this.allowedFormat != null && (NSTEXOPlayerSkyActivity.this.allowedFormat.equalsIgnoreCase(".ts") || NSTEXOPlayerSkyActivity.this.allowedFormat.equalsIgnoreCase(""))) {
                                                            Toast.makeText(NSTEXOPlayerSkyActivity.this.context, NSTEXOPlayerSkyActivity.this.getResources().getString(R.string.wraning_cast_on_live), 1).show();
                                                        }
                                                        MediaMetadata mediaMetadata = new MediaMetadata(1);
                                                        if (str2 != null && !str2.isEmpty()) {
                                                            mediaMetadata.putString(MediaMetadata.KEY_TITLE, str2);
                                                        }
                                                        if (str != null && !str.isEmpty()) {
                                                            mediaMetadata.addImage(new WebImage(Uri.parse(str)));
                                                        }
                                                        String str = NSTEXOPlayerSkyActivity.this.mFilePath + NSTEXOPlayerSkyActivity.this.finalStreamID + NSTEXOPlayerSkyActivity.this.allowedFormat;
                                                        NSTEXOPlayerSkyActivity.this.releasePlayer();
                                                        NSTEXOPlayerSkyActivity.this.externalPlayerSelected = true;
                                                        if (NSTEXOPlayerSkyActivity.this.rq.booleanValue()) {
                                                            ChromeCastUtilClass.castHLS(NSTEXOPlayerSkyActivity.this.mHandler, NSTEXOPlayerSkyActivity.this.mCastSession.getRemoteMediaClient(), str, mediaMetadata, NSTEXOPlayerSkyActivity.this.context);
                                                        }
                                                    } catch (Exception unused2) {
                                                    }
                                                }
                                            } else if (NSTEXOPlayerSkyActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                                NSTEXOPlayerSkyActivity.this.removeFromFavouriteM3U(NSTEXOPlayerSkyActivity.this.finalM3uVideoURL, this.val$finalStreamName);
                                            } else {
                                                NSTEXOPlayerSkyActivity.this.removeFromFavourite(str3, NSTEXOPlayerSkyActivity.this.finalStreamID, this.val$finalStreamName);
                                            }
                                        } else if (NSTEXOPlayerSkyActivity.this.isStoragePermissionGranted()) {
                                            SharedPreferences unused3 = NSTEXOPlayerSkyActivity.loginPreferencesDownloadStatus = NSTEXOPlayerSkyActivity.this.getSharedPreferences(AppConst.LOGIN_PREF_DOWNLOAD_STATUS, 0);
                                            String string = NSTEXOPlayerSkyActivity.loginPreferencesDownloadStatus.getString(AppConst.LOGIN_PREF_DOWNLOAD_STATUS, "");
                                            Utils utils = new Utils();
                                            if (string.equals("processing")) {
                                                utils.showDownloadRunningPopUP(NSTEXOPlayerSkyActivity.this);
                                            } else {
                                                utils.showRecordingPopUP(NSTEXOPlayerSkyActivity.this, str6, NSTEXOPlayerSkyActivity.this.allowedFormat, NSTEXOPlayerSkyActivity.this.mFilePath, NSTEXOPlayerSkyActivity.this.finalStreamID, NSTEXOPlayerSkyActivity.this.finalM3uVideoURL);
                                            }
                                        }
                                    } else if (NSTEXOPlayerSkyActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                        NSTEXOPlayerSkyActivity.this.addToFavouriteM3U(str3, NSTEXOPlayerSkyActivity.this.finalM3uVideoURL, this.val$finalStreamName);
                                    } else {
                                        NSTEXOPlayerSkyActivity.this.addToFavourite(str3, NSTEXOPlayerSkyActivity.this.finalStreamID, this.val$finalStreamName, finalStr);
                                    }
                                    return false;
                                }
                            });
                            popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass10.AnonymousClass2 */

                                public void onDismiss(PopupMenu popupMenu) {
                                }
                            });
                            popupMenu.show();
                            return true;
                        }
                    });
                    this.et_search.addTextChangedListener(new TextWatcher() {
                        /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass11 */

                        public void afterTextChanged(Editable editable) {
                        }

                        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        }

                        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                            if (NSTEXOPlayerSkyActivity.this.adapter != null) {
                                NSTEXOPlayerSkyActivity.this.adapter.getFilter().filter(charSequence.toString());
                            }
                        }
                    });
                }
            } catch (Exception unused) {
            }
        } else {
            this.allStreams_with_cat = new ArrayList<>();
            this.adapter = new SearchableAdapter(this, this.allStreams_with_cat);
            if (this.listChannels != null) {
                this.listChannels.setAdapter((ListAdapter) this.adapter);
                this.adapter.notifyDataSetChanged();
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

    public void addToFavourite(String str, int i, String str2, String str3) {
        if (this.context != null && this.adapter != null && this.database != null) {
            FavouriteDBModel favouriteDBModel = new FavouriteDBModel();
            favouriteDBModel.setCategoryID(str);
            favouriteDBModel.setStreamID(i);
            favouriteDBModel.setNum(str3);
            favouriteDBModel.setName(str2);
            favouriteDBModel.setUserID(SharepreferenceDBHandler.getUserID(this.context));
            this.database.addToFavourite(favouriteDBModel, "live");
            this.adapter.notifyDataSetChanged();
            Context context2 = this.context;
            Utils.showToast(context2, str2 + this.context.getResources().getString(R.string.add_fav));
        }
    }

    public void addToFavouriteM3U(String str, String str2, String str3) {
        if (this.context != null && this.adapter != null && this.liveStreamDBHandler != null) {
            FavouriteM3UModel favouriteM3UModel = new FavouriteM3UModel();
            favouriteM3UModel.setUrl(str2);
            favouriteM3UModel.setUserID(SharepreferenceDBHandler.getUserID(this.context));
            favouriteM3UModel.setName(str3);
            favouriteM3UModel.setCategoryID(str);
            this.liveStreamDBHandler.addToFavourite(favouriteM3UModel);
            this.adapter.notifyDataSetChanged();
            Context context2 = this.context;
            Utils.showToast(context2, str3 + this.context.getResources().getString(R.string.add_fav));
        }
    }

    public void removeFromFavourite(String str, int i, String str2) {
        if (this.context != null && this.adapter != null && this.database != null) {
            this.database.deleteFavourite(i, str, "live", str2, SharepreferenceDBHandler.getUserID(this.context));
            this.adapter.notifyDataSetChanged();
            Context context2 = this.context;
            Utils.showToast(context2, str2 + this.context.getResources().getString(R.string.rem_fav));
        }
    }

    public void removeFromFavouriteM3U(String str, String str2) {
        if (this.context != null && this.adapter != null && this.liveStreamDBHandler != null) {
            this.liveStreamDBHandler.deleteFavourite(str, SharepreferenceDBHandler.getUserID(this.context));
            this.adapter.notifyDataSetChanged();
            Context context2 = this.context;
            Utils.showToast(context2, str2 + this.context.getResources().getString(R.string.rem_fav));
        }
    }

    private ArrayList<String> getPasswordSetCategories() {
        this.categoryWithPasword = this.liveStreamDBHandler.getAllPasswordStatus(SharepreferenceDBHandler.getUserID(this.context));
        if (this.categoryWithPasword != null) {
            Iterator<PasswordStatusDBModel> it = this.categoryWithPasword.iterator();
            while (it.hasNext()) {
                PasswordStatusDBModel next = it.next();
                if (next.getPasswordStatus().equals("1")) {
                    this.listPassword.add(next.getPasswordStatusCategoryId());
                }
            }
        }
        return this.listPassword;
    }

    private ArrayList<LiveStreamCategoryIdDBModel> getUnlockedCategories(ArrayList<LiveStreamCategoryIdDBModel> arrayList, ArrayList<String> arrayList2) {
        if (arrayList == null) {
            return null;
        }
        try {
            Iterator<LiveStreamCategoryIdDBModel> it = arrayList.iterator();
            while (it.hasNext()) {
                LiveStreamCategoryIdDBModel next = it.next();
                boolean z = false;
                if (arrayList2 != null) {
                    Iterator<String> it2 = arrayList2.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        if (next.getLiveStreamCategoryID().equals(it2.next())) {
                            z = true;
                            break;
                        }
                    }
                    if (!z && this.liveListDetailUnlcked != null) {
                        this.liveListDetailUnlcked.add(next);
                    }
                }
            }
            return this.liveListDetailUnlcked;
        } catch (Exception unused) {
            return null;
        }
    }

    private ArrayList<LiveStreamsDBModel> getUnlockedChannels(ArrayList<LiveStreamsDBModel> arrayList, ArrayList<String> arrayList2) {
        try {
            Iterator<LiveStreamsDBModel> it = arrayList.iterator();
            while (it.hasNext()) {
                LiveStreamsDBModel next = it.next();
                boolean z = false;
                Iterator<String> it2 = arrayList2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    if (next.getCategoryId().equals(it2.next())) {
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    this.liveListDetailUnlckedChannels.add(next);
                }
            }
        } catch (Exception unused) {
        }
        return this.liveListDetailUnlckedChannels;
    }

    public boolean getUnlockedChannelsBoolean(ArrayList<LiveStreamsDBModel> arrayList, ArrayList<String> arrayList2) {
        try {
            Iterator<LiveStreamsDBModel> it = arrayList.iterator();
            while (it.hasNext()) {
                LiveStreamsDBModel next = it.next();
                Iterator<String> it2 = arrayList2.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (next.getCategoryId().equals(it2.next())) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    public void backbutton() {
        hideToobar();
        this.retrySetAdapter = false;
        this.positionToSelectAfterJumping = 0;
        if (this.AsyncTaskInitialize != null && this.AsyncTaskInitialize.getStatus().equals(AsyncTask.Status.RUNNING)) {
            return;
        }
        if (this.liveListDetailAvailable == null || this.liveListDetailAvailable.size() <= 0) {
            allChannels();
            return;
        }
        if (this.currentCategoryIndex != 0) {
            this.currentCategoryIndex--;
        } else {
            this.currentCategoryIndex = this.liveListDetailAvailable.size() - 1;
        }
        if (this.currentCategoryIndex != 0 || this.liveListDetailAvailableChannels == null) {
            if (this.liveListDetailAvailable != null && this.liveListDetailAvailable.size() > 0 && this.currentCategoryIndex < this.liveListDetailAvailable.size()) {
                String liveStreamCategoryID = this.liveListDetailAvailable.get(this.currentCategoryIndex).getLiveStreamCategoryID();
                String liveStreamCategoryName = this.liveListDetailAvailable.get(this.currentCategoryIndex).getLiveStreamCategoryName();
                this.catID = liveStreamCategoryID;
                this.catName = liveStreamCategoryName;
                if (this.catID == null || this.catID.equals("") || !this.catID.equals("-1")) {
                    allChannelsWithCat();
                } else {
                    allFavourites();
                }
            }
        } else if (!this.catID.equals(AppConst.PASSWORD_UNSET)) {
            this.catID = AppConst.PASSWORD_UNSET;
            this.catName = this.context.getResources().getString(R.string.all);
            allChannels();
        }
    }

    public void nextbutton() {
        hideToobar();
        this.retrySetAdapter = false;
        this.positionToSelectAfterJumping = 0;
        if (this.AsyncTaskInitialize != null && this.AsyncTaskInitialize.getStatus().equals(AsyncTask.Status.RUNNING)) {
            return;
        }
        if (this.liveListDetailAvailable == null || this.liveListDetailAvailable.size() <= 0) {
            allChannels();
            return;
        }
        if (this.currentCategoryIndex != this.liveListDetailAvailable.size() - 1) {
            this.currentCategoryIndex++;
        } else {
            this.currentCategoryIndex = 0;
        }
        if (this.currentCategoryIndex == 0 && this.liveListDetailAvailableChannels != null) {
            this.catID = AppConst.PASSWORD_UNSET;
            this.catName = this.context.getResources().getString(R.string.all);
            allChannels();
        } else if (this.liveListDetailAvailable != null && this.liveListDetailAvailable.size() > 0 && this.currentCategoryIndex < this.liveListDetailAvailable.size()) {
            String liveStreamCategoryID = this.liveListDetailAvailable.get(this.currentCategoryIndex).getLiveStreamCategoryID();
            String liveStreamCategoryName = this.liveListDetailAvailable.get(this.currentCategoryIndex).getLiveStreamCategoryName();
            this.catID = liveStreamCategoryID;
            this.catName = liveStreamCategoryName;
            if (this.catID == null || this.catID.equals("") || !this.catID.equals("-1")) {
                allChannelsWithCat();
            } else {
                allFavourites();
            }
        }
    }

    public void getFavourites() {
        try {
            if (this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                new ArrayList();
                if (this.liveStreamDBHandler != null) {
                    ArrayList<FavouriteM3UModel> favouriteM3U = this.liveStreamDBHandler.getFavouriteM3U("live");
                    ArrayList<LiveStreamsDBModel> arrayList = new ArrayList<>();
                    if (this.listPassword != null && this.listPassword.size() > 0 && favouriteM3U != null && favouriteM3U.size() > 0) {
                        favouriteM3U = getfavUnlovkedM3U(favouriteM3U, this.listPassword);
                    }
                    Iterator<FavouriteM3UModel> it = favouriteM3U.iterator();
                    while (it.hasNext()) {
                        FavouriteM3UModel next = it.next();
                        ArrayList<LiveStreamsDBModel> m3UFavouriteRow = this.liveStreamDBHandler.getM3UFavouriteRow(next.getCategoryID(), next.getUrl());
                        if (m3UFavouriteRow != null && m3UFavouriteRow.size() > 0) {
                            arrayList.add(m3UFavouriteRow.get(0));
                        }
                    }
                    if (arrayList.size() != 0) {
                        this.allStreams_with_cat = arrayList;
                        return;
                    }
                    return;
                }
                return;
            }
            new ArrayList();
            if (this.database != null) {
                ArrayList<FavouriteDBModel> allFavourites = this.database.getAllFavourites("live", SharepreferenceDBHandler.getUserID(this.context));
                ArrayList<LiveStreamsDBModel> arrayList2 = new ArrayList<>();
                if (this.listPassword != null && this.listPassword.size() > 0 && allFavourites != null && allFavourites.size() > 0) {
                    allFavourites = getfavUnlovked(allFavourites, this.listPassword);
                }
                Iterator<FavouriteDBModel> it2 = allFavourites.iterator();
                while (it2.hasNext()) {
                    FavouriteDBModel next2 = it2.next();
                    LiveStreamsDBModel liveStreamFavouriteRow = new LiveStreamDBHandler(this.context).getLiveStreamFavouriteRow(next2.getCategoryID(), String.valueOf(next2.getStreamID()));
                    if (liveStreamFavouriteRow != null) {
                        arrayList2.add(liveStreamFavouriteRow);
                    }
                }
                if (arrayList2.size() != 0) {
                    this.allStreams_with_cat = arrayList2;
                }
            }
        } catch (Exception unused) {
        }
    }

    private ArrayList<FavouriteDBModel> getfavUnlovked(ArrayList<FavouriteDBModel> arrayList, ArrayList<String> arrayList2) {
        try {
            this.favliveListDetailUnlckedDetail = new ArrayList<>();
            Iterator<FavouriteDBModel> it = arrayList.iterator();
            while (it.hasNext()) {
                FavouriteDBModel next = it.next();
                boolean z = false;
                Iterator<String> it2 = arrayList2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    if (next.getCategoryID().equals(it2.next())) {
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    this.favliveListDetailUnlckedDetail.add(next);
                }
            }
        } catch (Exception unused) {
        }
        return this.favliveListDetailUnlckedDetail;
    }

    private ArrayList<FavouriteM3UModel> getfavUnlovkedM3U(ArrayList<FavouriteM3UModel> arrayList, ArrayList<String> arrayList2) {
        this.favliveListDetailUnlckedDetailM3U = new ArrayList<>();
        if (arrayList == null) {
            return null;
        }
        try {
            Iterator<FavouriteM3UModel> it = arrayList.iterator();
            while (it.hasNext()) {
                FavouriteM3UModel next = it.next();
                boolean z = false;
                if (arrayList2 != null) {
                    Iterator<String> it2 = arrayList2.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        if (next.getCategoryID().equals(it2.next())) {
                            z = true;
                            break;
                        }
                    }
                    if (!z) {
                        this.favliveListDetailUnlckedDetailM3U.add(next);
                    }
                }
            }
            return this.favliveListDetailUnlckedDetailM3U;
        } catch (Exception unused) {
            return null;
        }
    }

    public void updateEPG(String str, String str2, LiveStreamDBHandler liveStreamDBHandler2, Context context2, ProgressBar progressBar2, TextView textView, TextView textView2, TextView textView3, TextView textView4, ImageView imageView, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, LinearLayout linearLayout4, LinearLayout linearLayout5, LinearLayout linearLayout6, TextView textView5, TextView textView6, TextView textView7, TextView textView8, TextView textView9, TextView textView10, TextView textView11, TextView textView12) {
        this.liveStreamDBHandler = liveStreamDBHandler2;
        this.context = context2;
        this.progressBar = progressBar2;
        this.currentProgram = textView;
        this.currentProgramTime = textView2;
        this.nextProgram = textView3;
        this.nextProgramTime = textView4;
        this.channelLogo = imageView;
        this.ll_epg1_box = linearLayout;
        this.ll_epg2_box = linearLayout2;
        this.ll_epg3_box = linearLayout3;
        this.ll_epg4_box = linearLayout4;
        this.ll_no_guide = linearLayout5;
        this.ll_epg_loader = linearLayout6;
        this.tv_epg1_date = textView5;
        this.tv_epg2_date = textView6;
        this.tv_epg3_date = textView7;
        this.tv_epg4_date = textView8;
        this.tv_epg1_program = textView9;
        this.tv_epg2_program = textView10;
        this.tv_epg3_program = textView11;
        this.tv_epg4_program = textView12;
        loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
        this.programTimeFormat = new SimpleDateFormat(loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, ""), Locale.US);
        updateChannelLogo(str2);
        epgDisplay(str);
    }

    public void updateChannelLogo(String str) {
        if (str == null || str.equals("")) {
            if (Build.VERSION.SDK_INT >= 21 && this.channelLogo != null) {
                this.channelLogo.setImageDrawable(this.context.getResources().getDrawable(R.drawable.logo_placeholder_white, null));
            }
        } else if (this.channelLogo != null) {
            Picasso.with(this.context).load(str).resize(80, 55).placeholder((int) R.drawable.logo_placeholder_white).into(this.channelLogo);
        }
    }

    private void hideEPGData() {
        if (this.progressBar != null) {
            this.progressBar.setProgress(0);
        }
        if (this.currentProgram != null) {
            this.currentProgram.setText(this.context.getResources().getString(R.string.now_program_found));
        }
        if (this.currentProgramTime != null) {
            this.currentProgramTime.setText("");
        }
        if (this.nextProgram != null) {
            this.nextProgram.setText(this.context.getResources().getString(R.string.next_program_found));
        }
        if (this.nextProgramTime != null) {
            this.nextProgramTime.setText("");
        }
    }

    private void loadingEPGData() {
        if (this.progressBar != null) {
            this.progressBar.setProgress(0);
        }
        if (this.currentProgram != null) {
            this.currentProgram.setText(this.context.getResources().getString(R.string.now_loading));
        }
        if (this.currentProgramTime != null) {
            this.currentProgramTime.setText("");
        }
        if (this.nextProgram != null) {
            this.nextProgram.setText(this.context.getResources().getString(R.string.next_loading));
        }
        if (this.nextProgramTime != null) {
            this.nextProgramTime.setText("");
        }
    }

    public int getIndexOfStreams(ArrayList<LiveStreamsDBModel> arrayList, int i) {
        int i2 = 0;
        while (i2 < arrayList.size()) {
            try {
                if (Utils.parseIntZero(arrayList.get(i2).getNum()) == i) {
                    return i2;
                }
                i2++;
            } catch (Exception unused) {
                return 0;
            }
        }
        return 0;
    }

    public String getSelectedPlayer() {
        Context context2 = this.context;
        Context context3 = this.context;
        this.loginPreferencesSharedPref = context2.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
        return this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER, "");
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        Utils.appResume(this.context);
        try {
            this.stopRetry = false;
            this.mCastContext.addCastStateListener(this.mCastStateListener);
            this.mCastContext.getSessionManager().addSessionManagerListener(this.mSessionManagerListener, CastSession.class);
            if (this.mCastSession == null) {
                this.mCastSession = CastContext.getSharedInstance(this).getSessionManager().getCurrentCastSession();
            }
        } catch (Exception unused) {
        }
        hideToobar();
        hideSystemUi();
        if (this.externalPlayerSelected) {
            this.externalPlayerSelected = false;
            if (this.liveListDetailAvailableChannels == null || this.liveListDetailAvailableChannels.size() == 0) {
                AppConst.SETPLAYERLISTENER = false;
                noChannelFound();
            } else {
                playFirstTime(this.liveListDetailAvailableChannels, this.video_num);
            }
            if (this.adapter != null) {
                this.adapter.notifyDataSetChanged();
            }
        }
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onDestroy() {
        super.onDestroy();
        try {
            if (this.playerView != null) {
                this.playerView.onPause();
            }
            this.stopRetry = true;
            checkIfAsyncTaskRunning();
            releasePlayer();
            CastContext.getSharedInstance(this.context).getSessionManager().removeSessionManagerListener(this.mSessionManagerListener, CastSession.class);
        } catch (Exception unused) {
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onPause() {
        super.onPause();
        try {
            if (Util.SDK_INT <= 23 && this.playerView != null) {
                this.playerView.onPause();
            }
            this.stopRetry = true;
            releasePlayer();
        } catch (Exception unused) {
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|(1:6)|7|8|9|11) */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x001a */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onStop() {
        super.onStop();
        this.externalPlayerSelected = true;
        if (Util.SDK_INT > 23 && this.playerView != null) {
            this.playerView.onPause();
        }
        this.stopRetry = true;
        releasePlayer();
        CastContext.getSharedInstance(this.context).getSessionManager().removeSessionManagerListener(this.mSessionManagerListener, CastSession.class);
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block
        	at jadx.core.dex.visitors.regions.RegionMaker.processSwitch(RegionMaker.java:787)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:130)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:88)
        	at jadx.core.dex.visitors.regions.RegionMaker.processSwitch(RegionMaker.java:826)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:130)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:88)
        	at jadx.core.dex.visitors.regions.RegionMaker.processSwitch(RegionMaker.java:826)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:130)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:88)
        	at jadx.core.dex.visitors.regions.RegionMaker.processSwitch(RegionMaker.java:826)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:130)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:88)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:696)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:125)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:88)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:696)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:125)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:88)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:50)
        */
    public boolean onKeyUp(int r6, KeyEvent r7) {
        /*
            r5 = this;
            int r0 = r7.getRepeatCount()
            r0 = 62
            r1 = 1
            if (r6 == r0) goto L_0x010a
            r0 = 79
            if (r6 == r0) goto L_0x010a
            r0 = 82
            r2 = 0
            if (r6 == r0) goto L_0x00fd
            switch(r6) {
                case 7: goto L_0x0069;
                case 8: goto L_0x0069;
                case 9: goto L_0x0069;
                case 10: goto L_0x0069;
                case 11: goto L_0x0069;
                case 12: goto L_0x0069;
                case 13: goto L_0x0069;
                case 14: goto L_0x0069;
                case 15: goto L_0x0069;
                case 16: goto L_0x0069;
                default: goto L_0x0015;
            }
        L_0x0015:
            switch(r6) {
                case 19: goto L_0x005a;
                case 20: goto L_0x0047;
                case 21: goto L_0x0039;
                case 22: goto L_0x002b;
                default: goto L_0x0018;
            }
        L_0x0018:
            switch(r6) {
                case 85: goto L_0x010a;
                case 86: goto L_0x0027;
                default: goto L_0x001b;
            }
        L_0x001b:
            switch(r6) {
                case 126: goto L_0x0023;
                case 127: goto L_0x0027;
                default: goto L_0x001e;
            }
        L_0x001e:
            boolean r6 = super.onKeyUp(r6, r7)
            return r6
        L_0x0023:
            r5.play()
            return r1
        L_0x0027:
            r5.pause()
            return r1
        L_0x002b:
            boolean r6 = r5.outfromtoolbar
            if (r6 != 0) goto L_0x0030
            goto L_0x0038
        L_0x0030:
            boolean r6 = r5.fullScreen
            if (r6 == 0) goto L_0x0035
            goto L_0x0038
        L_0x0035:
            r5.nextbutton()
        L_0x0038:
            return r1
        L_0x0039:
            boolean r6 = r5.outfromtoolbar
            if (r6 != 0) goto L_0x003e
            goto L_0x0046
        L_0x003e:
            boolean r6 = r5.fullScreen
            if (r6 == 0) goto L_0x0043
            goto L_0x0046
        L_0x0043:
            r5.backbutton()
        L_0x0046:
            return r1
        L_0x0047:
            boolean r6 = r5.fullScreen
            if (r6 != 0) goto L_0x0059
            boolean r6 = r5.outfromtoolbar
            if (r6 != 0) goto L_0x0052
            r5.hideToobar()
        L_0x0052:
            r5.outfromtoolbar = r1
            android.widget.ListView r6 = r5.listChannels
            r6.requestFocus()
        L_0x0059:
            return r1
        L_0x005a:
            boolean r6 = r5.fullScreen
            if (r6 != 0) goto L_0x0068
            android.support.v7.widget.Toolbar r6 = r5.toolbar
            boolean r6 = r6.hasFocus()
            if (r6 == 0) goto L_0x0068
            r5.outfromtoolbar = r2
        L_0x0068:
            return r1
        L_0x0069:
            android.widget.LinearLayout r7 = r5.ll_layout_to_hide1
            int r7 = r7.getVisibility()
            if (r7 != 0) goto L_0x0073
            goto L_0x00fc
        L_0x0073:
            android.os.Handler r7 = r5.handlerJumpChannel
            r0 = 0
            r7.removeCallbacksAndMessages(r0)
            r7 = 7
            if (r6 != r7) goto L_0x0082
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r6.append(r2)
            goto L_0x00e0
        L_0x0082:
            r0 = 8
            if (r6 != r0) goto L_0x008c
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r6.append(r1)
            goto L_0x00e0
        L_0x008c:
            r3 = 9
            if (r6 != r3) goto L_0x0097
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r7 = 2
            r6.append(r7)
            goto L_0x00e0
        L_0x0097:
            r4 = 10
            if (r6 != r4) goto L_0x00a2
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r7 = 3
            r6.append(r7)
            goto L_0x00e0
        L_0x00a2:
            r4 = 11
            if (r6 != r4) goto L_0x00ad
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r7 = 4
            r6.append(r7)
            goto L_0x00e0
        L_0x00ad:
            r4 = 12
            if (r6 != r4) goto L_0x00b8
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r7 = 5
            r6.append(r7)
            goto L_0x00e0
        L_0x00b8:
            r4 = 13
            if (r6 != r4) goto L_0x00c3
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r7 = 6
            r6.append(r7)
            goto L_0x00e0
        L_0x00c3:
            r4 = 14
            if (r6 != r4) goto L_0x00cd
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r6.append(r7)
            goto L_0x00e0
        L_0x00cd:
            r7 = 15
            if (r6 != r7) goto L_0x00d7
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r6.append(r0)
            goto L_0x00e0
        L_0x00d7:
            r7 = 16
            if (r6 != r7) goto L_0x00e0
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r6.append(r3)
        L_0x00e0:
            android.widget.TextView r6 = r5.app_channel_jumping_text
            java.lang.StringBuilder r7 = r5.jumpToChannel
            java.lang.String r7 = r7.toString()
            r6.setText(r7)
            android.widget.LinearLayout r6 = r5.ll_channel_jumping
            r6.setVisibility(r2)
            android.os.Handler r6 = r5.handlerJumpChannel
            com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity$12 r7 = new com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity$12
            r7.<init>()
            r2 = 3000(0xbb8, double:1.482E-320)
            r6.postDelayed(r7, r2)
        L_0x00fc:
            return r1
        L_0x00fd:
            android.view.Menu r6 = r5.menuSelect
            if (r6 == 0) goto L_0x0109
            android.view.Menu r6 = r5.menuSelect
            r7 = 2131362161(0x7f0a0171, float:1.8344095E38)
            r6.performIdentifierAction(r7, r2)
        L_0x0109:
            return r1
        L_0x010a:
            r5.togglePlayPause()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.onKeyUp(int, android.view.KeyEvent):boolean");
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

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        boolean z = keyEvent.getAction() == 0;
        if (keyCode != 82) {
            if (this.player != null && !this.playerView.isControllerVisible() && !z) {
                if (this.fullScreen) {
                    if (keyCode == 23) {
                        if (findViewById(R.id.ll_layout_to_hide_1).getVisibility() != 0) {
                            if (this.player != null) {
                                if (this.playerView.isControllerVisible()) {
                                    this.playerView.hideController();
                                } else {
                                    this.playerView.showController();
                                    findViewById(R.id.exo_pause).requestFocus();
                                    findViewById(R.id.exo_play).requestFocus();
                                }
                            }
                        }
                        return true;
                    }
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
        //ToDo: return added...
        return z;
    }

    @Override // android.support.v7.app.AppCompatActivity
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        keyEvent.getRepeatCount();
        keyEvent.getAction();
        if (this.fullScreen) {
            if (i == 20) {
                if (this.rq.booleanValue()) {
                    findViewById(R.id.exo_prevv).performClick();
                }
                return true;
            } else if (i == 19) {
                if (this.rq.booleanValue()) {
                    findViewById(R.id.exo_nextt).performClick();
                }
                return true;
            }
        }
        switch (i) {
            case 166:
                if (this.fullScreen && this.rq.booleanValue()) {
                    findViewById(R.id.exo_nextt).performClick();
                }
                return true;
            case 167:
                if (this.fullScreen && this.rq.booleanValue()) {
                    findViewById(R.id.exo_prevv).performClick();
                }
                return true;
            default:
                return super.onKeyDown(i, keyEvent);
        }
    }

    @SuppressLint({"SetTextI18n"})
    public HashMap<String, ArrayList<String>> showEPGAsync(String str) {
        ArrayList<XMLTVProgrammePojo> epg;
        String str2 = str;
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        ArrayList<String> arrayList3 = new ArrayList<>();
        ArrayList<String> arrayList4 = new ArrayList<>();
        HashMap<String, ArrayList<String>> hashMap = new HashMap<>();
        if (this.liveStreamDBHandler != null) {
            if (this.liveStreamDBHandler.getEPGCount() != 0) {
                arrayList3.add(0, "installed");
                if (str2 != null && !str2.equals("") && this.liveStreamDBHandler != null && (epg = this.liveStreamDBHandler.getEPG(str2)) != null) {
                    int i = 0;
                    while (true) {
                        if (i >= epg.size() || (this.AsyncTaskUpdateEPG != null && this.AsyncTaskUpdateEPG.isCancelled())) {
                            break;
                        }
                        String start = epg.get(i).getStart();
                        String stop = epg.get(i).getStop();
                        String title = epg.get(i).getTitle();
                        Long valueOf = Long.valueOf(Utils.epgTimeConverter(start));
                        Long valueOf2 = Long.valueOf(Utils.epgTimeConverter(stop));
                        if (Utils.isEventVisible(valueOf.longValue(), valueOf2.longValue(), this.context)) {
                            int percentageLeft = Utils.getPercentageLeft(valueOf.longValue(), valueOf2.longValue(), this.context);
                            if (!(percentageLeft == 0 || (percentageLeft = 100 - percentageLeft) == 0 || title == null || title.equals(""))) {
                                arrayList4.add(0, String.valueOf(percentageLeft));
                                arrayList.add(0, this.context.getResources().getString(R.string.now) + " " + title);
                                arrayList.add(1, this.programTimeFormat.format(valueOf) + " - " + this.programTimeFormat.format(valueOf2));
                            }
                            arrayList2.add(0, this.programTimeFormat.format(valueOf) + " - " + this.programTimeFormat.format(valueOf2));
                            arrayList2.add(1, title);
                            int i2 = i + 1;
                            if (i2 < epg.size()) {
                                String start2 = epg.get(i2).getStart();
                                String stop2 = epg.get(i2).getStop();
                                String title2 = epg.get(i2).getTitle();
                                Long valueOf3 = Long.valueOf(Utils.epgTimeConverter(start2));
                                Long valueOf4 = Long.valueOf(Utils.epgTimeConverter(stop2));
                                if (!(percentageLeft == 0 || 100 - percentageLeft == 0 || title == null || title.equals(""))) {
                                    arrayList.add(2, this.context.getResources().getString(R.string.next) + " " + title2);
                                    arrayList.add(3, this.programTimeFormat.format(valueOf3) + " - " + this.programTimeFormat.format(valueOf4));
                                }
                                arrayList2.add(2, this.programTimeFormat.format(valueOf3) + " - " + this.programTimeFormat.format(valueOf4));
                                arrayList2.add(3, title2);
                            }
                            int i3 = i + 2;
                            if (i3 < epg.size()) {
                                String start3 = epg.get(i3).getStart();
                                String stop3 = epg.get(i3).getStop();
                                String title3 = epg.get(i3).getTitle();
                                Long valueOf5 = Long.valueOf(Utils.epgTimeConverter(start3));
                                Long valueOf6 = Long.valueOf(Utils.epgTimeConverter(stop3));
                                arrayList2.add(4, this.programTimeFormat.format(valueOf5) + " - " + this.programTimeFormat.format(valueOf6));
                                arrayList2.add(5, title3);
                            }
                            int i4 = i + 3;
                            if (i4 < epg.size()) {
                                String start4 = epg.get(i4).getStart();
                                String stop4 = epg.get(i4).getStop();
                                String title4 = epg.get(i4).getTitle();
                                Long valueOf7 = Long.valueOf(Utils.epgTimeConverter(start4));
                                Long valueOf8 = Long.valueOf(Utils.epgTimeConverter(stop4));
                                arrayList2.add(6, this.programTimeFormat.format(valueOf7) + " - " + this.programTimeFormat.format(valueOf8));
                                arrayList2.add(7, title4);
                            }
                        } else {
                            i++;
                        }
                    }
                }
            } else {
                arrayList3.add(0, "not_installed");
            }
        }
        hashMap.put(AppConst.PASSWORD_UNSET, arrayList);
        hashMap.put("1", arrayList2);
        hashMap.put("2", arrayList3);
        hashMap.put("3", arrayList4);
        return hashMap;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.app_video_box:
                try {
                    fullScreenVideoLayout();
                    return;
                } catch (Exception unused) {
                    return;
                }
            case R.id.btn_aspect_ratio:
                try {
                    toggleAspectRatio();
                    return;
                } catch (Exception e) {
                    Log.e("NSTIJPLAYER", "exection " + e);
                    return;
                }
            case R.id.btn_category_back:
                try {
                    backbutton();
                    return;
                } catch (Exception e2) {
                    Log.e("NSTIJPLAYER", "exection " + e2);
                    return;
                }
            case R.id.btn_category_fwd:
                try {
                    nextbutton();
                    return;
                } catch (Exception e3) {
                    Log.e("NSTIJPLAYER", "exection " + e3);
                    return;
                }
            case R.id.exo_nextt:
                try {
                    if (this.AsyncTaskUpdateEPG != null && this.AsyncTaskUpdateEPG.getStatus().equals(AsyncTask.Status.RUNNING)) {
                        this.AsyncTaskUpdateEPG.cancel(true);
                    }
                    this.ll_allcontrols.setVisibility(0);
                    this.debugRootView.setVisibility(0);
                    this.vlcnextButton.requestFocus();
                    this.handler.removeCallbacksAndMessages(null);
                    this.channelZapped = true;
                    next();
                    final int currentWindowIndex2 = getCurrentWindowIndex();
                    if (this.liveListDetailAvailableChannels != null && this.liveListDetailAvailableChannels.size() > 1 && currentWindowIndex2 <= this.liveListDetailAvailableChannels.size() - 1 && currentWindowIndex2 > -1) {
                        String name = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getName();
                        final String url2 = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getUrl();
                        this.currentProgramM3UURL = url2;
                        String num = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getNum();
                        String streamIcon = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getStreamIcon();
                        if (streamIcon.equals("") || streamIcon.isEmpty()) {
                            this.channelLogo.setImageDrawable(this.context.getResources().getDrawable(R.drawable.logo_placeholder_white));
                        } else {
                            Picasso.with(this.context).load(streamIcon).placeholder((int) R.drawable.logo_placeholder_white).error((int) R.drawable.logo_placeholder_white).resize(80, 55).into(this.channelLogo);
                        }
                        setTitle(num + " - " + name);
                        this.opened_vlc_id = currentWindowIndex2;
                        this.loginPrefsEditorAudio.clear();
                        this.loginPrefsEditorAudio.apply();
                        this.loginPrefsEditorVideo.clear();
                        this.loginPrefsEditorVideo.apply();
                        this.loginPrefsEditorSubtitle.clear();
                        this.loginPrefsEditorSubtitle.apply();
                        if (this.rq.booleanValue()) {
                            this.handler.postDelayed(new Runnable() {
                                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass14 */

                                public void run() {
                                    NSTEXOPlayerSkyActivity.this.releasePlayer();
                                    if (NSTEXOPlayerSkyActivity.this.PlayerType != null && NSTEXOPlayerSkyActivity.this.PlayerType.equals("large_epg")) {
                                        SharedPreferences.Editor edit = NSTEXOPlayerSkyActivity.this.loginPreferencesSharedPref_opened_video.edit();
                                        if (NSTEXOPlayerSkyActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                            edit.putString("LOGIN_PREF_OPENED_VIDEO_URL_M3U", url2);
                                            NSTEXOPlayerSkyActivity.this.playingURL = Uri.parse(url2);
                                        } else {
                                            edit.putInt(AppConst.LOGIN_PREF_OPENED_VIDEO_ID, Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId()));
                                            NSTEXOPlayerSkyActivity nSTEXOPlayerSkyActivity = NSTEXOPlayerSkyActivity.this;
                                            nSTEXOPlayerSkyActivity.playingURL = Uri.parse(NSTEXOPlayerSkyActivity.this.mFilePath + Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId()) + NSTEXOPlayerSkyActivity.this.allowedFormat);
                                        }
                                        edit.apply();
                                    } else if (NSTEXOPlayerSkyActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                        NSTEXOPlayerSkyActivity.this.playingURL = Uri.parse(url2);
                                    } else {
                                        NSTEXOPlayerSkyActivity nSTEXOPlayerSkyActivity2 = NSTEXOPlayerSkyActivity.this;
                                        nSTEXOPlayerSkyActivity2.playingURL = Uri.parse(NSTEXOPlayerSkyActivity.this.mFilePath + Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId()) + NSTEXOPlayerSkyActivity.this.allowedFormat);
                                    }
                                    NSTEXOPlayerSkyActivity.this.retryCount = 0;
                                    NSTEXOPlayerSkyActivity.this.retrying = false;
                                    NSTEXOPlayerSkyActivity.this.initializePlayer();
                                    NSTEXOPlayerSkyActivity.this.currentEpgChannelID = ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getEpgChannelId();
                                    NSTEXOPlayerSkyActivity.this.currentChannelLogo = ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamIcon();
                                    NSTEXOPlayerSkyActivity.this.updateChannelLogo(NSTEXOPlayerSkyActivity.this.currentChannelLogo);
                                    NSTEXOPlayerSkyActivity.this.epgDisplay(NSTEXOPlayerSkyActivity.this.currentEpgChannelID);
                                }
                            }, 200);
                        }
                        this.currentProgramStreamID = Utils.parseIntMinusOne(this.liveListDetailAvailableChannels.get(currentWindowIndex2).getStreamId());
                        if (this.loginPrefsEditor != null) {
                            this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableChannels.get(currentWindowIndex2).getStreamId()));
                            this.loginPrefsEditor.putString("LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_M3U", this.liveListDetailAvailableChannels.get(currentWindowIndex2).getUrl());
                            this.loginPrefsEditor.apply();
                        }
                        if (this.loginPrefsEditorPosition != null) {
                            this.loginPrefsEditorPosition.putInt(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, currentWindowIndex2);
                            this.loginPrefsEditorPosition.apply();
                        }
                        if (this.positionToSelectAfterJumping != 0) {
                            this.positionToSelectAfterJumping = currentWindowIndex2;
                        }
                        if (this.PlayerType != null && this.PlayerType.equals("live")) {
                            this.adapter.notifyDataSetChanged();
                            return;
                        }
                        return;
                    }
                    return;
                } catch (Exception e4) {
                    Log.e("NSTIJPLAYER", "exection " + e4);
                    return;
                }
            case R.id.exo_prevv:
                try {
                    if (this.AsyncTaskUpdateEPG != null && this.AsyncTaskUpdateEPG.getStatus().equals(AsyncTask.Status.RUNNING)) {
                        this.AsyncTaskUpdateEPG.cancel(true);
                    }
                    this.vlcprevButton.requestFocus();
                    this.handler.removeCallbacksAndMessages(null);
                    previous();
                    this.channelZapped = true;
                    final int currentWindowIndex3 = getCurrentWindowIndex();
                    if (this.liveListDetailAvailableChannels != null && this.liveListDetailAvailableChannels.size() > 1 && currentWindowIndex3 <= this.liveListDetailAvailableChannels.size() - 1 && currentWindowIndex3 > -1) {
                        String name2 = this.liveListDetailAvailableChannels.get(currentWindowIndex3).getName();
                        String num2 = this.liveListDetailAvailableChannels.get(currentWindowIndex3).getNum();
                        final String url3 = this.liveListDetailAvailableChannels.get(currentWindowIndex3).getUrl();
                        this.currentProgramM3UURL = url3;
                        String streamIcon2 = this.liveListDetailAvailableChannels.get(currentWindowIndex3).getStreamIcon();
                        if (streamIcon2.equals("") || streamIcon2.isEmpty()) {
                            this.channelLogo.setImageDrawable(this.context.getResources().getDrawable(R.drawable.logo_placeholder_white));
                        } else {
                            Picasso.with(this.context).load(streamIcon2).placeholder((int) R.drawable.logo_placeholder_white).error((int) R.drawable.logo_placeholder_white).resize(80, 55).into(this.channelLogo);
                        }
                        setTitle(num2 + " - " + name2);
                        this.opened_vlc_id = currentWindowIndex3;
                        this.loginPrefsEditorAudio.clear();
                        this.loginPrefsEditorAudio.apply();
                        this.loginPrefsEditorVideo.clear();
                        this.loginPrefsEditorVideo.apply();
                        this.loginPrefsEditorSubtitle.clear();
                        this.loginPrefsEditorSubtitle.apply();
                        if (this.rq.booleanValue()) {
                            this.handler.postDelayed(new Runnable() {
                                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass13 */

                                public void run() {
                                    NSTEXOPlayerSkyActivity.this.releasePlayer();
                                    if (NSTEXOPlayerSkyActivity.this.PlayerType != null && NSTEXOPlayerSkyActivity.this.PlayerType.equals("large_epg")) {
                                        SharedPreferences.Editor edit = NSTEXOPlayerSkyActivity.this.loginPreferencesSharedPref_opened_video.edit();
                                        if (NSTEXOPlayerSkyActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                            edit.putString("LOGIN_PREF_OPENED_VIDEO_URL_M3U", url3);
                                            NSTEXOPlayerSkyActivity.this.playingURL = Uri.parse(url3);
                                        } else {
                                            edit.putInt(AppConst.LOGIN_PREF_OPENED_VIDEO_ID, Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex3)).getStreamId()));
                                            NSTEXOPlayerSkyActivity nSTEXOPlayerSkyActivity = NSTEXOPlayerSkyActivity.this;
                                            nSTEXOPlayerSkyActivity.playingURL = Uri.parse(NSTEXOPlayerSkyActivity.this.mFilePath + Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex3)).getStreamId()) + NSTEXOPlayerSkyActivity.this.allowedFormat);
                                        }
                                        edit.apply();
                                    } else if (NSTEXOPlayerSkyActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                        NSTEXOPlayerSkyActivity.this.playingURL = Uri.parse(url3);
                                    } else {
                                        NSTEXOPlayerSkyActivity nSTEXOPlayerSkyActivity2 = NSTEXOPlayerSkyActivity.this;
                                        nSTEXOPlayerSkyActivity2.playingURL = Uri.parse(NSTEXOPlayerSkyActivity.this.mFilePath + Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex3)).getStreamId()) + NSTEXOPlayerSkyActivity.this.allowedFormat);
                                    }
                                    NSTEXOPlayerSkyActivity.this.retryCount = 0;
                                    NSTEXOPlayerSkyActivity.this.retrying = false;
                                    NSTEXOPlayerSkyActivity.this.initializePlayer();
                                    NSTEXOPlayerSkyActivity.this.currentEpgChannelID = ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex3)).getEpgChannelId();
                                    NSTEXOPlayerSkyActivity.this.currentChannelLogo = ((LiveStreamsDBModel) NSTEXOPlayerSkyActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex3)).getStreamIcon();
                                    NSTEXOPlayerSkyActivity.this.updateChannelLogo(NSTEXOPlayerSkyActivity.this.currentChannelLogo);
                                    NSTEXOPlayerSkyActivity.this.epgDisplay(NSTEXOPlayerSkyActivity.this.currentEpgChannelID);
                                }
                            }, 200);
                        }
                        this.currentProgramStreamID = Utils.parseIntMinusOne(this.liveListDetailAvailableChannels.get(currentWindowIndex3).getStreamId());
                        if (this.loginPrefsEditor != null) {
                            this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableChannels.get(currentWindowIndex3).getStreamId()));
                            this.loginPrefsEditor.putString("LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_M3U", this.liveListDetailAvailableChannels.get(currentWindowIndex3).getUrl());
                            this.loginPrefsEditor.apply();
                        }
                        if (this.loginPrefsEditorPosition != null) {
                            this.loginPrefsEditorPosition.putInt(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, currentWindowIndex3);
                            this.loginPrefsEditorPosition.apply();
                        }
                        if (this.positionToSelectAfterJumping != 0) {
                            this.positionToSelectAfterJumping = currentWindowIndex3;
                        }
                        if (this.PlayerType != null && this.PlayerType.equals("live")) {
                            this.adapter.notifyDataSetChanged();
                            return;
                        }
                        return;
                    }
                    return;
                } catch (Exception e5) {
                    Log.e("NSTIJPLAYERskyACTIVTY", "exection " + e5);
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
            case R.id.rl_nst_player_sky_layout:
                fullScreenVideoLayout();
                return;
            default:
                return;
        }
    }

    public void hidePopup() {
        if (this.changeSortPopUp != null) {
            this.changeSortPopUp.dismiss();
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
            /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass15 */

            public void onDismiss() {
                NSTEXOPlayerSkyActivity.this.hideSystemUi();
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
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass16 */

                public void onClick(View view) {
                    NSTEXOPlayerSkyActivity.this.showTracks(view);
                }
            });
            this.audio_tracks.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass17 */

                public void onClick(View view) {
                    NSTEXOPlayerSkyActivity.this.showTracks(view);
                }
            });
            this.subtitle_tracks.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass18 */

                public void onClick(View view) {
                    NSTEXOPlayerSkyActivity.this.showTracks(view);
                }
            });
            this.ll_close.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass19 */

                public void onClick(View view) {
                    NSTEXOPlayerSkyActivity.this.hidePopup();
                    NSTEXOPlayerSkyActivity.this.hideSystemUi();
                }
            });
            this.changeSortPopUp.showAtLocation(inflate, 1, 0, 0);
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
                    str = "VIDEO TRACKS";
                } else if (view.getId() == R.id.audio_tracks) {
                    str = "AUDIO TRACKS";
                } else if (view.getId() == R.id.subtitle_tracks) {
                    str = "SUBTITLE TRACKS";
                }
                Pair<android.app.AlertDialog, TrackSelectionView> dialog = TrackSelectionView.getDialog(this.mActivity, str, this.trackSelector, intValue);
                ((TrackSelectionView) dialog.second).setShowDisableOption(true);
                ((TrackSelectionView) dialog.second).setAllowAdaptiveSelections(z);
                ((android.app.AlertDialog) dialog.first).show();
            }
        }
    }

    public void toggleView(View view) {
        if (view.getVisibility() == 8) {
            view.setVisibility(0);
        } else if (view.getVisibility() == 0) {
            view.setVisibility(8);
        }
    }

    private void previous() {
        int currentWindowIndex2 = getCurrentWindowIndex();
        if (currentWindowIndex2 == 0) {
            setCurrentWindowIndex(this.liveListDetailAvailableChannels.size() - 1);
        } else {
            setCurrentWindowIndex(currentWindowIndex2 - 1);
        }
    }

    private void next() {
        int currentWindowIndex2 = getCurrentWindowIndex();
        if (currentWindowIndex2 == this.liveListDetailAvailableChannels.size() - 1) {
            setCurrentWindowIndex(0);
        } else {
            setCurrentWindowIndex(currentWindowIndex2 + 1);
        }
    }

    public void setCurrentWindowIndex(int i) {
        this.currentWindowIndex = i;
    }

    public int getCurrentWindowIndex() {
        return this.currentWindowIndex;
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        if (this.PlayerType == null || !this.PlayerType.equals("live") || this.ll_layout_to_hide1.getVisibility() != 8) {
            super.onBackPressed();
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
            return;
        }
        if (this.rlToolbar != null) {
            this.rlToolbar.setVisibility(0);
        }
        smallScreenVideoLayoutForTv();
    }

    public void fullScreenVideoLayout() {
        this.fullScreen = true;
        this.ll_allcontrols.setVisibility(0);
        String livePlayerAppName = SharepreferenceDBHandler.getLivePlayerAppName(this.context);
        if (!SharepreferenceDBHandler.getLivePlayerPkgName(this.context).equals("default") && !new ExternalPlayerDataBase(this.context).CheckPlayerExistense(livePlayerAppName)) {
            SharepreferenceDBHandler.setLivePlayer("default", "default", this.context);
        }
        String livePlayerPkgName = SharepreferenceDBHandler.getLivePlayerPkgName(this.context);
        if (livePlayerPkgName != null && !livePlayerPkgName.equalsIgnoreCase("default")) {
            releasePlayer();
            this.externalPlayerSelected = true;
            this.loginPreferencesSharedPref_currently_playing_video_position = getSharedPreferences(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, 0);
            int i = this.loginPreferencesSharedPref_currently_playing_video_position.getInt(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, 0);
            ArrayList<LiveStreamsDBModel> filteredData = this.adapter.getFilteredData();
            this.opened_vlc_id = i;
            if (filteredData != null && filteredData.size() > 0 && this.opened_vlc_id < filteredData.size()) {
                this.streamID = Utils.parseIntZero(filteredData.get(this.opened_vlc_id).getStreamId());
                this.m3uVideoURL = filteredData.get(this.opened_vlc_id).getUrl();
            } else if (this.liveListDetailAvailableChannels != null && this.liveListDetailAvailableChannels.size() > 0 && this.opened_vlc_id < this.liveListDetailAvailableChannels.size()) {
                this.streamID = Utils.parseIntZero(this.liveListDetailAvailableChannels.get(this.opened_vlc_id).getStreamId());
                this.m3uVideoURL = this.liveListDetailAvailableChannels.get(this.opened_vlc_id).getUrl();
                this.liveListDetailAvailableChannels.get(this.opened_vlc_id).getName();
            }
            this.finalStreamID = this.streamID;
            this.finalM3uVideoURL = this.m3uVideoURL;
            this.fullScreen = false;
            if (this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                if (this.rq.booleanValue()) {
                    String livePlayerAppName2 = SharepreferenceDBHandler.getLivePlayerAppName(this.context);
                    Intent intent = new Intent(this.context, PlayExternalPlayerActivity.class);
                    intent.putExtra("url", this.finalM3uVideoURL);
                    intent.putExtra(AppConst.PACKAGE_NAME, livePlayerPkgName);
                    intent.putExtra(AppConst.APP_NAME, livePlayerAppName2);
                    this.context.startActivity(intent);
                }
            } else if (this.streamID != -1 && this.rq.booleanValue()) {
                String urlLive = Utils.getUrlLive(this.context, this.streamID, this.allowedFormat, "live");
                String livePlayerAppName3 = SharepreferenceDBHandler.getLivePlayerAppName(this.context);
                Intent intent2 = new Intent(this.context, PlayExternalPlayerActivity.class);
                intent2.putExtra("url", urlLive);
                intent2.putExtra(AppConst.PACKAGE_NAME, livePlayerPkgName);
                intent2.putExtra(AppConst.APP_NAME, livePlayerAppName3);
                this.context.startActivity(intent2);
            }
        } else if (AppConst.SETPLAYERLISTENER.booleanValue()) {
            if (this.rlToolbar != null) {
                this.rlToolbar.setVisibility(8);
            }
            findViewById(R.id.app_video_box).setOnClickListener(null);
            this.ll_layout_to_hide1.setVisibility(8);
            this.ll_layout_to_hide4.setVisibility(8);
            this.rl_layout_to_hide5.setVisibility(8);
            this.rl_layout_to_hide_6.setVisibility(8);
            this.rl_footer_info.setVisibility(8);
            getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.rl_video_box.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -1;
            this.rl_video_box.setLayoutParams(layoutParams);
            getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
            this.rl_settings.setVisibility(8);
        }
    }

    private void smallScreenVideoLayoutForTv() {
        try {
            if (AppConst.SETPLAYERLISTENER.booleanValue()) {
                findViewById(R.id.app_video_box).setOnClickListener(this);
            }
            this.ll_allcontrols.setVisibility(8);
            this.debugRootView.setVisibility(8);
            this.ll_layout_to_hide1.setVisibility(0);
            this.ll_layout_to_hide4.setVisibility(0);
            this.rl_layout_to_hide5.setVisibility(0);
            this.rl_layout_to_hide_6.setVisibility(0);
            this.rl_footer_info.setVisibility(0);
            this.rl_settings.setVisibility(0);
            getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.rl_video_box.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = 0;
            this.rl_video_box.setLayoutParams(layoutParams);
            getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
            this.fullScreen = false;
            if (this.channelZapped) {
                if (!this.channelJumping || this.channelNumJumping.equals("")) {
                    this.loginPreferencesSharedPref_currently_playing_video_position = getSharedPreferences(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, 0);
                    this.listChannels.setSelection(this.loginPreferencesSharedPref_currently_playing_video_position.getInt(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, 0));
                    this.channelZapped = false;
                    this.channelJumping = false;
                } else if (this.catID != null && !this.catID.equals("") && this.catID.equals(AppConst.PASSWORD_UNSET)) {
                    allChannels();
                }
                if (this.listChannels != null) {
                    this.listChannels.requestFocus();
                }
            } else if (this.listChannels != null) {
                this.listChannels.requestFocus();
            }
        } catch (Exception e) {
            Log.e("exection", "" + e);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.toolbar.inflateMenu(R.menu.menu_search);
        this.menuSelect = menu;
        try {
            this.mediaRouteMenuItem = CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), menu, (int) R.id.media_route_menu_item);
        } catch (Exception unused) {
        }
        this.menuItemSettings = menu.getItem(2).getSubMenu().findItem(R.id.empty);
        TypedValue typedValue = new TypedValue();
        if (getTheme().resolveAttribute(16843499, typedValue, true)) {
            TypedValue.complexToDimensionPixelSize(typedValue.data, this.context.getResources().getDisplayMetrics());
        }
        for (int i = 0; i < this.toolbar.getChildCount(); i++) {
            if (this.toolbar.getChildAt(i) instanceof ActionMenuView) {
                ((Toolbar.LayoutParams) this.toolbar.getChildAt(i).getLayoutParams()).gravity = 16;
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        hideToobar();
        this.menuItemSettings = menuItem;
        int itemId = menuItem.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(this, NewDashboardActivity.class));
            finish();
        }
        if (itemId == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        if (itemId == R.id.action_logout1 && this.context != null) {
            new AlertDialog.Builder(this.context, R.style.AlertDialogCustom).setTitle(this.context.getResources().getString(R.string.logout_title)).setMessage(this.context.getResources().getString(R.string.logout_message)).setPositiveButton(this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass21 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(NSTEXOPlayerSkyActivity.this.context);
                }
            }).setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass20 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
        if (itemId == R.id.action_search) {
            this.searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
            this.searchView.setQueryHint(this.context.getResources().getString(R.string.search_channel));
            this.searchView.setIconifiedByDefault(false);
            this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass22 */

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextSubmit(String str) {
                    return false;
                }

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextChange(String str) {
                    if (NSTEXOPlayerSkyActivity.this.text_from_toolbar) {
                        NSTEXOPlayerSkyActivity.this.text_from_toolbar = false;
                        return false;
                    }
                    NSTEXOPlayerSkyActivity.this.tvNoRecordFound.setVisibility(8);
                    if (!(NSTEXOPlayerSkyActivity.this.adapter == null || NSTEXOPlayerSkyActivity.this.tvNoStream == null || NSTEXOPlayerSkyActivity.this.tvNoStream.getVisibility() == 0)) {
                        NSTEXOPlayerSkyActivity.this.adapter.getFilter().filter(str);
                    }
                    return false;
                }
            });
            return true;
        }
        if (itemId == R.id.menu_load_channels_vod1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(this.context.getResources().getString(R.string.confirm_to_refresh));
            builder.setMessage(this.context.getResources().getString(R.string.do_you_want_toproceed));
            builder.setIcon((int) R.drawable.questionmark);
            builder.setPositiveButton(this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass23 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    NSTEXOPlayerSkyActivity.this.releasePlayer();
                    Utils.loadChannelsAndVod(NSTEXOPlayerSkyActivity.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass24 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
        if (itemId == R.id.menu_load_tv_guide1) {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setTitle(this.context.getResources().getString(R.string.confirm_to_refresh));
            builder2.setMessage(this.context.getResources().getString(R.string.do_you_want_toproceed));
            builder2.setIcon((int) R.drawable.questionmark);
            builder2.setPositiveButton(this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass25 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    NSTEXOPlayerSkyActivity.this.releasePlayer();
                    Utils.loadTvGuid(NSTEXOPlayerSkyActivity.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass26 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder2.show();
        }
        if (itemId == R.id.menu_sort) {
            showSortPopup(this);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void showSortPopup(Activity activity) {
        char c;
        final Activity activity2 = activity;
        try {
            final View inflate = ((LayoutInflater) activity2.getSystemService("layout_inflater")).inflate((int) R.layout.sort_layout, (RelativeLayout) activity2.findViewById(R.id.rl_password_prompt));
            this.changeSortPopUp = new PopupWindow(activity2);
            this.changeSortPopUp.setContentView(inflate);
            this.changeSortPopUp.setWidth(-1);
            this.changeSortPopUp.setHeight(-1);
            this.changeSortPopUp.setFocusable(true);
            this.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
            Button button = (Button) inflate.findViewById(R.id.bt_save_password);
            Button button2 = (Button) inflate.findViewById(R.id.bt_close);
            final RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.rg_radio);
            RadioButton radioButton = (RadioButton) inflate.findViewById(R.id.rb_normal);
            RadioButton radioButton2 = (RadioButton) inflate.findViewById(R.id.rb_lastadded);
            RadioButton radioButton3 = (RadioButton) inflate.findViewById(R.id.rb_atoz);
            RadioButton radioButton4 = (RadioButton) inflate.findViewById(R.id.rb_ztoa);
            RadioButton radioButton5 = (RadioButton) inflate.findViewById(R.id.rb_channel_asc);
            RadioButton radioButton6 = (RadioButton) inflate.findViewById(R.id.rb_channel_desc);
            radioButton5.setVisibility(0);
            radioButton6.setVisibility(0);
            radioButton.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton));
            radioButton2.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton2));
            radioButton3.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton3));
            radioButton4.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton4));
            radioButton5.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton5));
            radioButton6.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton6));
            if (this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                radioButton2.setVisibility(8);
            }
            if (this.catID.equals("-1")) {
                radioButton2.setVisibility(8);
            }
            String liveSubCatSort = SharepreferenceDBHandler.getLiveSubCatSort(activity);
            switch (liveSubCatSort.hashCode()) {
                case 49:
                    if (liveSubCatSort.equals("1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 50:
                    if (liveSubCatSort.equals("2")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 51:
                    if (liveSubCatSort.equals("3")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 52:
                    if (liveSubCatSort.equals("4")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 53:
                    if (liveSubCatSort.equals("5")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    radioButton2.setChecked(true);
                    break;
                case 1:
                    radioButton3.setChecked(true);
                    break;
                case 2:
                    radioButton4.setChecked(true);
                    break;
                case 3:
                    radioButton5.setChecked(true);
                    break;
                case 4:
                    radioButton6.setChecked(true);
                    break;
                default:
                    radioButton.setChecked(true);
                    break;
            }
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass27 */

                public void onClick(View view) {
                    NSTEXOPlayerSkyActivity.this.changeSortPopUp.dismiss();
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass28 */

                public void onClick(View view) {
                    RadioButton radioButton = (RadioButton) inflate.findViewById(radioGroup.getCheckedRadioButtonId());
                    if (radioButton.getText().toString().equals(activity2.getResources().getString(R.string.sort_last_added))) {
                        SharepreferenceDBHandler.setLiveSubCatSort("1", activity2);
                    } else if (radioButton.getText().toString().equals(activity2.getResources().getString(R.string.sort_atoz))) {
                        SharepreferenceDBHandler.setLiveSubCatSort("2", activity2);
                    } else if (radioButton.getText().toString().equals(activity2.getResources().getString(R.string.sort_ztoa))) {
                        SharepreferenceDBHandler.setLiveSubCatSort("3", activity2);
                    } else if (radioButton.getText().toString().equals(activity2.getResources().getString(R.string.sort_channel_number_asc))) {
                        SharepreferenceDBHandler.setLiveSubCatSort("4", activity2);
                    } else if (radioButton.getText().toString().equals(activity2.getResources().getString(R.string.sort_channel_number_decs))) {
                        SharepreferenceDBHandler.setLiveSubCatSort("5", activity2);
                    } else {
                        SharepreferenceDBHandler.setLiveSubCatSort(AppConst.PASSWORD_UNSET, activity2);
                    }
                    if (NSTEXOPlayerSkyActivity.this.catID.equals(AppConst.PASSWORD_UNSET)) {
                        NSTEXOPlayerSkyActivity.this.allChannels();
                    } else if (NSTEXOPlayerSkyActivity.this.catID == null || NSTEXOPlayerSkyActivity.this.catID.equals("") || !NSTEXOPlayerSkyActivity.this.catID.equals("-1")) {
                        NSTEXOPlayerSkyActivity.this.allChannelsWithCat();
                    } else {
                        NSTEXOPlayerSkyActivity.this.allFavourites();
                    }
                    NSTEXOPlayerSkyActivity.this.changeSortPopUp.dismiss();
                }
            });
        } catch (Exception unused) {
        }
    }

    private void hideToobar() {
        this.text_from_toolbar = true;
        this.toolbar.collapseActionView();
        if (this.tvNoRecordFound != null && !this.no_channel) {
            this.tvNoRecordFound.setVisibility(8);
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
                /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.AnonymousClass29 */

                public void run() {
                    linearLayout.setVisibility(8);
                }
            }, 3000);
        }
        return this.mCurrentAspectRatio;
    }

    private class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private final View view;

        public OnFocusChangeAccountListener(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            if (z) {
                if (this.view == null || this.view.getTag() == null || !this.view.getTag().equals("2")) {
                    performScaleXAnimation(1.15f);
                    performScaleYAnimation(1.15f);
                    if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("1") && NSTEXOPlayerSkyActivity.this.negativeButton != null) {
                        NSTEXOPlayerSkyActivity.this.negativeButton.setBackgroundResource(R.drawable.back_btn_effect);
                        return;
                    }
                    return;
                }
                view2.setBackground(NSTEXOPlayerSkyActivity.this.getResources().getDrawable(R.drawable.selector_checkbox));
            } else if (!z) {
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("1") || NSTEXOPlayerSkyActivity.this.negativeButton == null)) {
                    NSTEXOPlayerSkyActivity.this.negativeButton.setBackgroundResource(R.drawable.black_button_dark);
                }
                performScaleXAnimation(1.0f);
                performScaleYAnimation(1.0f);
                performAlphaAnimation(z);
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

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onStart() {
        super.onStart();
        try {
            CastContext.getSharedInstance(this.context).getSessionManager().addSessionManagerListener(this.mSessionManagerListener, CastSession.class);
        } catch (Exception unused) {
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

    /* JADX WARNING: Removed duplicated region for block: B:38:0x00a0  */
    public void initializePlayer() {
        //ToDo: initialized...
        DefaultDrmSessionManager<FrameworkMediaCrypto> defaultDrmSessionManager = null;
        TrackSelection.Factory factory;
        int i;
        if (this.app_video_status != null) {
            this.app_video_status.setVisibility(8);
        }
        if (this.player == null) {
            Intent intent = getIntent();
            Uri[] uriArr = {intent.getData()};
            uriArr[0] = this.playingURL;
            if (!Util.checkCleartextTrafficPermitted(uriArr)) {
                showToast((int) R.string.error_cleartext_not_permitted);
                return;
            } else if (!Util.maybeRequestReadExternalStoragePermission(this, uriArr)) {
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
                            showToast(i);
                            finish();
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
        this.player.prepare(this.mediaSource, !(this.startWindow != -1), false);
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

    private List<StreamKey> getOfflineStreamKeys(Uri uri) {
        return DemoApplication.getInstance(this.context).getDownloadTracker().getOfflineStreamKeys(uri);
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

    public void releasePlayer() {
        if (this.player != null) {
            updateTrackSelectorParameters();
            updateStartPosition();
            this.player.release();
            this.player = null;
            this.mediaSource = null;
            this.trackSelector = null;
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

    private DataSource.Factory buildDataSourceFactory() {
        return DemoApplication.getInstance(this.context).buildDataSourceFactory();
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

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onSeekProcessed() {
            /*Player.EventListener.CC.$default$*/onSeekProcessed(/*this*/);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onShuffleModeEnabledChanged(boolean z) {
            /*Player.EventListener.CC.$default$*/onShuffleModeEnabledChanged(/*this,*/ z);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onTimelineChanged(Timeline timeline, @Nullable Object obj, int i) {
            /*Player.EventListener.CC.$default$*/onTimelineChanged(/*this,*/ timeline, obj, i);
        }

        private PlayerEventListener() {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayerStateChanged(boolean z, int i) {
            if (i == 2) {
                NSTEXOPlayerSkyActivity.this.bufferingloader.setVisibility(0);
            } else if (i == 4) {
                NSTEXOPlayerSkyActivity.this.updateStartPosition();
                retrying();
            } else if (i == 3) {
                NSTEXOPlayerSkyActivity.this.retryCount = 0;
                NSTEXOPlayerSkyActivity.this.bufferingloader.setVisibility(8);
            }
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPositionDiscontinuity(int i) {
            if (NSTEXOPlayerSkyActivity.this.player.getPlaybackError() != null) {
                NSTEXOPlayerSkyActivity.this.updateStartPosition();
            }
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayerError(ExoPlaybackException exoPlaybackException) {
            if (NSTEXOPlayerSkyActivity.this.stopRetry) {
                return;
            }
            if (NSTEXOPlayerSkyActivity.isBehindLiveWindow(exoPlaybackException)) {
                NSTEXOPlayerSkyActivity.this.clearStartPosition();
                NSTEXOPlayerSkyActivity.this.initializePlayer();
            } else if (!exoPlaybackException.toString().contains("com.google.android.exoplayer2.ext.ffmpeg.FfmpegDecoderException")) {
                NSTEXOPlayerSkyActivity.this.updateStartPosition();
                retrying();
            } else {
                Utils.showToast(NSTEXOPlayerSkyActivity.this.mActivity, "Audio track issue found. Please change the audio track to none.");
                NSTEXOPlayerSkyActivity.this.initializePlayer();
            }
        }

        private void showStatus(String str) {
            NSTEXOPlayerSkyActivity.this.app_video_status.setVisibility(0);
            NSTEXOPlayerSkyActivity.this.app_video_status_text.setText(str);
        }

        private void retrying() {
            if (NSTEXOPlayerSkyActivity.this.retryCount >= NSTEXOPlayerSkyActivity.this.maxRetry) {
                showStatus(NSTEXOPlayerSkyActivity.this.mActivity.getResources().getString(R.string.small_problem));
                NSTEXOPlayerSkyActivity.this.releasePlayer();
                NSTEXOPlayerSkyActivity.this.retrying = false;
                NSTEXOPlayerSkyActivity.this.bufferingloader.setVisibility(8);
            } else if (!NSTEXOPlayerSkyActivity.this.stopRetry) {
                NSTEXOPlayerSkyActivity.this.retrying = true;
                NSTEXOPlayerSkyActivity.this.handler.postDelayed(new Runnable() {
                    /* class com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity.PlayerEventListener.AnonymousClass1 */

                    public void run() {
                        if (!NSTEXOPlayerSkyActivity.this.stopRetry) {
                            NSTEXOPlayerSkyActivity.this.retryCount++;
                            Utils.showToast(NSTEXOPlayerSkyActivity.this.mActivity, NSTEXOPlayerSkyActivity.this.mActivity.getResources().getString(R.string.play_back_error) + " (" + NSTEXOPlayerSkyActivity.this.retryCount + "/" + NSTEXOPlayerSkyActivity.this.maxRetry + ")");
                            NSTEXOPlayerSkyActivity.this.releasePlayer();
                            NSTEXOPlayerSkyActivity.this.initializePlayer();
                        }
                    }
                }, 3000);
            }
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            if (trackGroupArray != NSTEXOPlayerSkyActivity.this.lastSeenTrackGroupArray) {
                MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo = NSTEXOPlayerSkyActivity.this.trackSelector.getCurrentMappedTrackInfo();
                if (currentMappedTrackInfo != null) {
                    currentMappedTrackInfo.getTypeSupport(2);
                    currentMappedTrackInfo.getTypeSupport(1);
                }
                TrackGroupArray unused = NSTEXOPlayerSkyActivity.this.lastSeenTrackGroupArray = trackGroupArray;
            }
        }
    }
}
