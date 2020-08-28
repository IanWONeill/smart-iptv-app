package com.nst.yourname.view.ijkplayer.activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.folderselector.FileChooserDialog;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.CastStateListener;
import com.google.android.gms.cast.framework.IntroductoryOverlay;
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
import com.nst.yourname.model.callback.LiveStreamsCallback;
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
import com.nst.yourname.view.adapter.ChannelsOnVideoAdapter;
import com.nst.yourname.view.adapter.SearchableAdapter;
import com.nst.yourname.view.ijkplayer.application.Settings;
import com.nst.yourname.view.ijkplayer.widget.media.FileMediaDataSource;
import com.nst.yourname.view.ijkplayer.widget.media.InfoHudViewHolder;
import com.nst.yourname.view.ijkplayer.widget.media.MeasureHelper;
import com.nst.yourname.view.ijkplayer.widget.media.MediaPlayerCompat;
import com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSky;
import com.nst.yourname.view.ijkplayer.widget.media.SurfaceRenderView;
import com.nst.yourname.view.ijkplayer.widget.media.TableLayoutBinder;
import com.nst.yourname.view.ijkplayer.widget.media.TextureRenderView;
import com.nst.yourname.view.utility.epg.domain.EPGEvent;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

@SuppressWarnings("ALL")
public class NSTIJKPlayerSkyActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, FileChooserDialog.FileCallback {
    static final boolean $assertionsDisabled = false;
    private static final int RECORDING_REQUEST_CODE = 101;
    public static SharedPreferences loginPreferencesDownloadStatus;
    private static SharedPreferences loginPreferencesSharedPref_time_format;
    private static final int[] s_allAspectRatio = {0, 1, 2, 3, 4, 5};
    private static String uk;
    private static String una;
    private AsyncTask AsyncTaskInitialize = null;
    private AsyncTask AsyncTaskNSTIJKPlayerSkyActivity = null;
    public AsyncTask AsyncTaskUpdateEPG = null;
    private ArrayList<LiveStreamsDBModel> AvailableChannelsFirstOpenedCat;
    Boolean InterADs = true;
    private SharedPreferences SharedPreferencesSort;
    private SharedPreferences.Editor SharedPreferencesSortEditor;
    public Activity activity;
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
    private Boolean audioTrackFound = false;
    View audio_delay_minus;
    TextView audio_delay_ms;
    View audio_delay_plus;
    Button bt_browse_subtitle;
    private AppCompatImageView btn_cat_back;
    private AppCompatImageView btn_cat_forward;
    private CastSession castSession;
    String catID = "";
    String catName = "";
    private ArrayList<PasswordStatusDBModel> categoryWithPasword;
    public PopupWindow changeSortPopUp;
    public boolean channelJumping = false;
    public ImageView channelLogo;
    String channelNumJumping;
    public boolean channelZapped = false;
    ChannelsOnVideoAdapter channelsOnVideoAdapter;
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
    private List<LiveStreamsCallback> dataSet;
    public DatabaseHandler database;
    TextView date;
    public View decoder_hw;
    public View decoder_sw;
    public long defaultRetryTime = 2500;
    DateFormat df;
    private AlertDialog dirsDialog;
    private Boolean disableAudioTrack = false;
    private Boolean disableSubTitleTrack = false;
    private Boolean disableVideoTrack = false;
    Date dt;
    String elv;
    public EditText et_search;
    public View exo_info;
    public View exo_multiplayer;
    ArrayList<ExternalPlayerModelClass> externalPlayerList;
    boolean externalPlayerSelected = false;
    private ArrayList<FavouriteDBModel> favliveListDetailAvailable;
    private ArrayList<FavouriteDBModel> favliveListDetailUnlcked;
    private ArrayList<FavouriteDBModel> favliveListDetailUnlckedDetail;
    private ArrayList<FavouriteM3UModel> favliveListDetailUnlckedDetailM3U;
    String finalM3uVideoURL;
    int finalStreamID;
    String finalStreamNameWithUnderscore = "";
    public int firstplayedChannelNumber = 0;
    String fmw;
    SimpleDateFormat fr;
    public boolean fullScreen = false;
    public boolean fullScreenOnly = true;
    private GridLayoutManager gridLayoutManager;
    Handler handler;
    private Handler handlerAspectRatio;
    Handler handlerHeaderFooter;
    Handler handlerJumpChannel;
    Handler handlerShowEPG;
    Handler handlerUpdateEPGData;
    public boolean hideEPGData = true;
    private ChannelsOnVideoAdapter.MyViewHolder holder;
    boolean isInitializeVlc = false;
    StringBuilder jumpToChannel = new StringBuilder();
    public ListView listChannels;
    public ArrayList<String> listPassword = new ArrayList<>();
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetail;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailAvailable;
    public ArrayList<LiveStreamsDBModel> liveListDetailAvailableChannels;
    private ArrayList<LiveStreamsDBModel> liveListDetailAvailableChannels_Temp;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailAvailableForCounter;
    private ArrayList<LiveStreamsDBModel> liveListDetailAvailableNewChannels;
    private ArrayList<LiveStreamsDBModel> liveListDetailChannels;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailUnlcked;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlckedChannels;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailUnlckedDetail;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlckedDetailChannels;
    LiveStreamDBHandler liveStreamDBHandler;
    LinearLayout ll_channel_jumping;
    LinearLayout ll_epg1_box;
    LinearLayout ll_epg2_box;
    LinearLayout ll_epg3_box;
    LinearLayout ll_epg4_box;
    LinearLayout ll_epg_loader;
    LinearLayout ll_layout_to_hide1;
    LinearLayout ll_layout_to_hide4;
    LinearLayout ll_no_guide;
    public LinearLayout ll_seekbar_time;
    private SharedPreferences loginPreferencesAfterLogin;
    public SharedPreferences loginPreferencesAfterLoginSubtitleSize;
    private SharedPreferences loginPreferencesMediaCodec;
    private SharedPreferences loginPreferencesSharedPref;
    private SharedPreferences loginPreferencesSharedPref_allowed_format;
    private SharedPreferences loginPreferencesSharedPref_currently_playing_video;
    private SharedPreferences loginPreferencesSharedPref_currently_playing_video_position;
    private SharedPreferences loginPreferencesUserAgent;
    private SharedPreferences loginPreferences_audio_delay;
    private SharedPreferences loginPreferences_audio_selected;
    private SharedPreferences loginPreferences_subtitle_delay;
    private SharedPreferences loginPreferences_subtitle_selected;
    private SharedPreferences loginPreferences_video_selected;
    public SharedPreferences.Editor loginPrefsEditor;
    private SharedPreferences.Editor loginPrefsEditorAudio;
    private SharedPreferences.Editor loginPrefsEditorAudioDelay;
    SharedPreferences.Editor loginPrefsEditorMediaCodec;
    public SharedPreferences.Editor loginPrefsEditorPosition;
    private SharedPreferences.Editor loginPrefsEditorSubtitle;
    private SharedPreferences.Editor loginPrefsEditorSubtitleDelay;
    public SharedPreferences.Editor loginPrefsEditorSubtitleSize;
    private SharedPreferences.Editor loginPrefsEditorVideo;
    @BindView(R.id.logo)
    ImageView logo;
    public boolean longKeyPressed = true;
    private String m3uVideoURL;
    private CastContext mCastContext;
    public CastSession mCastSession;
    private CastStateListener mCastStateListener;
    private int mCurrentAspectRatio = s_allAspectRatio[0];
    private int mCurrentAspectRatioIndex = 4;
    public String mFilePath;
    private String mFilePath1 = "";
    public Handler mHandler;
    private TextView mInfo;
    private IntroductoryOverlay mIntroductoryOverlay;
    private MenuItem mQueueMenuItem;
    private final SessionManagerListener<CastSession> mSessionManagerListener = new MySessionManagerListener();
    private Settings mSettings;
    private Toolbar mToolbar;
    public NSTIJKPlayerSky mVideoView;
    private MenuItem mediaRouteMenuItem;
    MenuItem menuItemSettings;
    Menu menuSelect;
    RecyclerView myRecyclerView;
    public Button negativeButton;
    public TextView nextProgram;
    public TextView nextProgramTime;
    TextView no_audio_track;
    boolean no_channel = false;
    boolean no_channel_found = false;
    TextView no_subtitle_track;
    TextView no_video_track;
    private String opened_cat_id = "";
    private int opened_stream_id = 0;
    public int opened_vlc_id = 0;
    boolean outfromtoolbar = true;
    ProgressBar pbLoader;
    ProgressBar pb_listview_loader;
    private Boolean playFirstTimeLoaded = false;
    public Boolean playedFirstTime = false;
    private int position;
    public int positionToSelectAfterJumping = 0;
    private SimpleDateFormat programTimeFormat;
    ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private boolean retrySetAdapter = false;
    @BindView(R.id.rl_toolbar)
    RelativeLayout rlToolbar;
    public RelativeLayout rl_categories_view;
    RelativeLayout rl_footer_info;
    RelativeLayout rl_layout_to_hide5;
    RelativeLayout rl_layout_to_hide_6;
    public RelativeLayout rl_middle;
    RelativeLayout rl_nst_player_sky_layout;
    RelativeLayout rl_settings;
    RelativeLayout rl_video_box;
    public Boolean rq = true;
    public String scaleType;
    SearchView searchView;
    private EPGEvent selectedEvent = null;
    private String selected_language = "";
    Boolean sentToSettings = false;
    SharedPreferences.Editor sharedPrefEditor;
    SharedPreferences sharedPreferences;
    public boolean showNavIcon = true;
    private int streamID = -1;
    private int streamIdToExtPlayer;
    String streamNameWithUnderscore;
    private Boolean subtitleTrackFound = false;
    View subtitle_delay_minus;
    TextView subtitle_delay_ms;
    View subtitle_delay_plus;
    Spinner subtitle_font_size;
    boolean text_from_toolbar = false;
    TextView time;
    public String title;
    Toolbar toolbar;
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
    TextView tv_video_height;
    TextView tv_video_margin_right;
    TextView tv_video_width;
    TextView txtDisplay;
    String ukd;
    String unad;
    public String url;
    private Boolean videoTrackFound = false;
    SeekBar vlcSeekbar;
    public View vlc_exo_audio;
    public View vlc_exo_subtitle;
    public View vlcaspectRatio;
    public View vlcchannelListButton;
    public View vlcforwardButton;
    public View vlcnextButton;
    public View vlcpauseButton;
    public View vlcplayButton;
    public View vlcprevButton;
    public View vlcrewindButton;

    public void delayAudio(long j) {
    }

    public void delaySubs(long j) {
    }

    public void intialAdids() {
    }

    @Override // com.afollestad.materialdialogs.folderselector.FileChooserDialog.FileCallback
    public void onFileChooserDismissed(@NonNull FileChooserDialog fileChooserDialog) {
    }

    @Override // android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    private class MySessionManagerListener implements SessionManagerListener<CastSession> {
        private MySessionManagerListener() {
        }

        public void onSessionEnded(CastSession castSession, int i) {
            Log.e("seson mangement ", "onSessionEnded()");
            if (castSession == NSTIJKPlayerSkyActivity.this.mCastSession) {
                CastSession unused = NSTIJKPlayerSkyActivity.this.mCastSession = null;
            }
            NSTIJKPlayerSkyActivity.this.invalidateOptionsMenu();
        }

        public void onSessionResumed(CastSession castSession, boolean z) {
            Log.e("seson mangement ", "onSessionResumed()");
            CastSession unused = NSTIJKPlayerSkyActivity.this.mCastSession = castSession;
            NSTIJKPlayerSkyActivity.this.invalidateOptionsMenu();
        }

        public void onSessionStarted(CastSession castSession, String str) {
            Log.e("seson mangement ", "onSessionStarted()");
            CastSession unused = NSTIJKPlayerSkyActivity.this.mCastSession = castSession;
            NSTIJKPlayerSkyActivity.this.invalidateOptionsMenu();
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

    public static void play(Activity activity2, String... strArr) {
        Intent intent = new Intent(activity2, NSTIJKPlayerSkyActivity.class);
        intent.putExtra("url", strArr[0]);
        if (strArr.length > 1) {
            intent.putExtra("title", strArr[1]);
        }
        activity2.startActivity(intent);
    }

    public static Config configPlayer(Activity activity2) {
        return new Config(activity2);
    }

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        this.context = this;
        requestWindowFeature(1);
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_vlcplayer_sky);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        try {
            this.mCastStateListener = new CastStateListener() {
                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass1 */

                @Override // com.google.android.gms.cast.framework.CastStateListener
                public void onCastStateChanged(int i) {
                }
            };
            this.mCastContext = CastContext.getSharedInstance(this);
        } catch (Exception unused) {
        }
        initializeVariables();
        hideTitleBarAndFooter();
        if (this.rl_nst_player_sky_layout != null) {
            this.rl_nst_player_sky_layout.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass2 */

                public void onClick(View view) {
                    NSTIJKPlayerSkyActivity.this.fullScreenVideoLayout();
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
        this.AsyncTaskInitialize = new InitializeAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
    }

    /* JADX WARNING: Removed duplicated region for block: B:102:0x0714  */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x071a  */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0725  */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x0741  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0753  */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x0765  */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x0777  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x080e  */
    /* JADX WARNING: Removed duplicated region for block: B:123:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x021a  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x023c  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0257  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0272  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x02e1  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x030d  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0613  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0698  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x06aa  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x06b9  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x06c8  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x06da  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x06ec  */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x06fe  */
    private void initializeVariables() {
        char c;
        this.sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.mCurrentAspectRatioIndex = this.sharedPreferences.getInt(AppConst.ASPECT_RATIO, this.mCurrentAspectRatioIndex);
        this.handlerAspectRatio = new Handler();
        this.mSettings = new Settings(this.context);
        this.loginPreferencesSharedPref = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.loginPreferencesAfterLogin = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.loginPreferencesMediaCodec = this.context.getSharedPreferences(AppConst.LOGIN_PREF_MEDIA_CODEC, 0);
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
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass3 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(NSTIJKPlayerSkyActivity.this.context);
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
                    getIntent().getStringExtra("STREAM_TYPE");
                    getIntent().getStringExtra("VIDEO_TITLE");
                    getIntent().getStringExtra("EPG_CHANNEL_ID");
                    getIntent().getStringExtra("EPG_CHANNEL_LOGO");
                    if (!this.allowedFormat.equals("")) {
                        this.mFilePath1 = string3 + ":" + string6 + "/" + string + "/" + string2 + "/";
                    } else {
                        this.mFilePath1 = string3 + ":" + string6 + "/live/" + string + "/" + string2 + "/";
                    }
                    this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
                    this.liveStreamDBHandler = new LiveStreamDBHandler(this);
                    this.database = new DatabaseHandler(this.context);
                    this.handler = new Handler();
                    this.handlerHeaderFooter = new Handler();
                    this.handlerShowEPG = new Handler();
                    this.handlerUpdateEPGData = new Handler();
                    this.handlerJumpChannel = new Handler();
                    this.elv = Utils.ukde(TextureRenderView.pZGV() + TextureRenderView.mv());
                    this.mVideoView = (NSTIJKPlayerSky) findViewById(R.id.video_view);
                    this.mVideoView.setLiveStreamDBHandler(this.liveStreamDBHandler);
                    this.mVideoView.setActivity(this, this.mVideoView);
                    this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    this.ll_seekbar_time = (LinearLayout) findViewById(R.id.ll_seekbar_time);
                    this.channelLogo = (ImageView) findViewById(R.id.iv_channel_logo);
                    this.currentProgram = (TextView) findViewById(R.id.tv_current_program);
                    this.currentProgramTime = (TextView) findViewById(R.id.tv_current_time);
                    this.nextProgram = (TextView) findViewById(R.id.tv_next_program);
                    this.nextProgramTime = (TextView) findViewById(R.id.tv_next_program_time);
                    this.btn_cat_back = (AppCompatImageView) findViewById(R.id.btn_category_back);
                    this.btn_cat_forward = (AppCompatImageView) findViewById(R.id.btn_category_fwd);
                    this.tv_categories_view = (TextView) findViewById(R.id.tv_categories_view);
                    this.myRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                    this.pbLoader = (ProgressBar) findViewById(R.id.pb_loader);
                    this.toolbar = (Toolbar) findViewById(R.id.toolbar);
                    this.tvNoStream = (TextView) findViewById(R.id.tv_noStream);
                    this.tvNoRecordFound = (TextView) findViewById(R.id.tv_no_record_found);
                    this.appbarToolbar = (AppBarLayout) findViewById(R.id.appbar_toolbar);
                    this.rl_settings = (RelativeLayout) findViewById(R.id.rl_settings);
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
                    this.mInfo = (TextView) findViewById(R.id.player_overlay_info);
                    this.ll_channel_jumping = (LinearLayout) findViewById(R.id.ll_channel_jumping);
                    this.app_channel_jumping_text = (TextView) findViewById(R.id.app_channel_jumping_text);
                    this.vlcSeekbar = (SeekBar) findViewById(R.id.vlcSeekbar);
                    this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
                    this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
                    this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
                    this.pb_listview_loader = (ProgressBar) findViewById(R.id.pb_listview_loader);
                    this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                    IjkMediaPlayer.loadLibrariesOnce(null);
                    IjkMediaPlayer.native_profileBegin("libijkplayer.so");
                    this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                    this.toolbar.inflateMenu(R.menu.menu_search);
                    setSupportActionBar(this.toolbar);
                    new Thread(new CountDownRunner()).start();
                    if (SharepreferenceDBHandler.getSelectedLanguage(this.context).equalsIgnoreCase("Arabic")) {
                        this.btn_cat_back.setImageResource(R.drawable.right_icon_cat);
                        this.btn_cat_forward.setImageResource(R.drawable.left_icon_cat);
                    }
                    loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
                    this.programTimeFormat = new SimpleDateFormat(loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, ""), Locale.US);
                    findViewById(R.id.exo_next).setOnClickListener(this);
                    findViewById(R.id.exo_prev).setOnClickListener(this);
                    this.fullScreen = false;
                    findViewById(R.id.app_video_box).setOnClickListener(this);
                    this.listChannels = (ListView) findViewById(R.id.lv_ch);
                    this.et_search = (EditText) findViewById(R.id.et_search);
                    this.rl_categories_view = (RelativeLayout) findViewById(R.id.rl_categories_view);
                    this.btn_cat_back.setOnClickListener(this);
                    this.btn_cat_forward.setOnClickListener(this);
                    this.vlcplayButton = findViewById(R.id.exo_play);
                    if (this.vlcplayButton != null) {
                        this.vlcplayButton.setOnClickListener(this);
                    }
                    this.vlcpauseButton = findViewById(R.id.exo_pause);
                    if (this.vlcpauseButton != null) {
                        this.vlcpauseButton.setOnClickListener(this);
                    }
                    this.vlcprevButton = findViewById(R.id.exo_prev);
                    if (this.vlcprevButton != null) {
                        this.vlcprevButton.setOnClickListener(this);
                    }
                    this.vlcnextButton = findViewById(R.id.exo_next);
                    if (this.vlcnextButton != null) {
                        this.vlcnextButton.setOnClickListener(this);
                    }
                    this.vlcchannelListButton = findViewById(R.id.btn_list);
                    if (this.vlcchannelListButton != null) {
                        this.vlcchannelListButton.setOnClickListener(this);
                    }
                    this.vlcaspectRatio = findViewById(R.id.btn_aspect_ratio);
                    if (this.vlcaspectRatio != null) {
                        this.vlcaspectRatio.setOnClickListener(this);
                    }
                    this.vlc_exo_subtitle = findViewById(R.id.exo_subtitle);
                    if (this.vlc_exo_subtitle != null) {
                        this.vlc_exo_subtitle.setOnClickListener(this);
                    }
                    this.exo_multiplayer = findViewById(R.id.exo_multiplayer);
                    if (!AppConst.New_MultiScreen.booleanValue()) {
                        this.exo_multiplayer.setVisibility(0);
                    } else {
                        this.exo_multiplayer.setVisibility(8);
                    }
                    if (this.exo_multiplayer != null) {
                        this.exo_multiplayer.setOnClickListener(this);
                    }
                    this.ukd = Utils.ukde(FileMediaDataSource.apn());
                    this.decoder_sw = findViewById(R.id.exo_decoder_sw);
                    if (this.decoder_sw != null) {
                        this.decoder_sw.setOnClickListener(this);
                    }
                    this.decoder_hw = findViewById(R.id.exo_decoder_hw);
                    if (this.decoder_hw != null) {
                        this.decoder_hw.setOnClickListener(this);
                    }
                    this.exo_info = findViewById(R.id.exo_info);
                    if (this.exo_info != null) {
                        this.exo_info.setOnClickListener(this);
                    }
                    this.vlc_exo_audio = findViewById(R.id.vlc_exo_audio);
                    if (this.vlc_exo_audio != null) {
                        this.vlc_exo_audio.setOnClickListener(this);
                    }
                    this.rl_middle = (RelativeLayout) findViewById(R.id.middle);
                    uk = getApplicationName(this.context);
                    this.liveListDetailUnlcked = new ArrayList<>();
                    this.liveListDetailUnlckedDetail = new ArrayList<>();
                    this.liveListDetailAvailable = new ArrayList<>();
                    this.liveListDetailAvailableForCounter = new ArrayList<>();
                    this.liveListDetail = new ArrayList<>();
                    this.liveListDetailUnlckedChannels = new ArrayList<>();
                    this.liveListDetailUnlckedDetailChannels = new ArrayList<>();
                    this.liveListDetailAvailableChannels = new ArrayList<>();
                    this.liveListDetailAvailableChannels_Temp = new ArrayList<>();
                    una = getApplicationContext().getPackageName();
                    this.AvailableChannelsFirstOpenedCat = new ArrayList<>();
                    this.liveListDetailAvailableNewChannels = new ArrayList<>();
                    this.liveListDetailChannels = new ArrayList<>();
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
                getIntent().getStringExtra("STREAM_TYPE");
                getIntent().getStringExtra("VIDEO_TITLE");
                getIntent().getStringExtra("EPG_CHANNEL_ID");
                getIntent().getStringExtra("EPG_CHANNEL_LOGO");
                if (!this.allowedFormat.equals("")) {
                }
                this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
                this.liveStreamDBHandler = new LiveStreamDBHandler(this);
                this.database = new DatabaseHandler(this.context);
                this.handler = new Handler();
                this.handlerHeaderFooter = new Handler();
                this.handlerShowEPG = new Handler();
                this.handlerUpdateEPGData = new Handler();
                this.handlerJumpChannel = new Handler();
                this.elv = Utils.ukde(TextureRenderView.pZGV() + TextureRenderView.mv());
                this.mVideoView = (NSTIJKPlayerSky) findViewById(R.id.video_view);
                this.mVideoView.setLiveStreamDBHandler(this.liveStreamDBHandler);
                this.mVideoView.setActivity(this, this.mVideoView);
                this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
                this.ll_seekbar_time = (LinearLayout) findViewById(R.id.ll_seekbar_time);
                this.channelLogo = (ImageView) findViewById(R.id.iv_channel_logo);
                this.currentProgram = (TextView) findViewById(R.id.tv_current_program);
                this.currentProgramTime = (TextView) findViewById(R.id.tv_current_time);
                this.nextProgram = (TextView) findViewById(R.id.tv_next_program);
                this.nextProgramTime = (TextView) findViewById(R.id.tv_next_program_time);
                this.btn_cat_back = (AppCompatImageView) findViewById(R.id.btn_category_back);
                this.btn_cat_forward = (AppCompatImageView) findViewById(R.id.btn_category_fwd);
                this.tv_categories_view = (TextView) findViewById(R.id.tv_categories_view);
                this.myRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                this.pbLoader = (ProgressBar) findViewById(R.id.pb_loader);
                this.toolbar = (Toolbar) findViewById(R.id.toolbar);
                this.tvNoStream = (TextView) findViewById(R.id.tv_noStream);
                this.tvNoRecordFound = (TextView) findViewById(R.id.tv_no_record_found);
                this.appbarToolbar = (AppBarLayout) findViewById(R.id.appbar_toolbar);
                this.rl_settings = (RelativeLayout) findViewById(R.id.rl_settings);
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
                this.mInfo = (TextView) findViewById(R.id.player_overlay_info);
                this.ll_channel_jumping = (LinearLayout) findViewById(R.id.ll_channel_jumping);
                this.app_channel_jumping_text = (TextView) findViewById(R.id.app_channel_jumping_text);
                this.vlcSeekbar = (SeekBar) findViewById(R.id.vlcSeekbar);
                this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
                this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
                this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
                this.pb_listview_loader = (ProgressBar) findViewById(R.id.pb_listview_loader);
                this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                IjkMediaPlayer.loadLibrariesOnce(null);
                IjkMediaPlayer.native_profileBegin("libijkplayer.so");
                this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                this.toolbar.inflateMenu(R.menu.menu_search);
                setSupportActionBar(this.toolbar);
                new Thread(new CountDownRunner()).start();
                if (SharepreferenceDBHandler.getSelectedLanguage(this.context).equalsIgnoreCase("Arabic")) {
                }
                loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
                this.programTimeFormat = new SimpleDateFormat(loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, ""), Locale.US);
                findViewById(R.id.exo_next).setOnClickListener(this);
                findViewById(R.id.exo_prev).setOnClickListener(this);
                this.fullScreen = false;
                findViewById(R.id.app_video_box).setOnClickListener(this);
                this.listChannels = (ListView) findViewById(R.id.lv_ch);
                this.et_search = (EditText) findViewById(R.id.et_search);
                this.rl_categories_view = (RelativeLayout) findViewById(R.id.rl_categories_view);
                this.btn_cat_back.setOnClickListener(this);
                this.btn_cat_forward.setOnClickListener(this);
                this.vlcplayButton = findViewById(R.id.exo_play);
                if (this.vlcplayButton != null) {
                }
                this.vlcpauseButton = findViewById(R.id.exo_pause);
                if (this.vlcpauseButton != null) {
                }
                this.vlcprevButton = findViewById(R.id.exo_prev);
                if (this.vlcprevButton != null) {
                }
                this.vlcnextButton = findViewById(R.id.exo_next);
                if (this.vlcnextButton != null) {
                }
                this.vlcchannelListButton = findViewById(R.id.btn_list);
                if (this.vlcchannelListButton != null) {
                }
                this.vlcaspectRatio = findViewById(R.id.btn_aspect_ratio);
                if (this.vlcaspectRatio != null) {
                }
                this.vlc_exo_subtitle = findViewById(R.id.exo_subtitle);
                if (this.vlc_exo_subtitle != null) {
                }
                this.exo_multiplayer = findViewById(R.id.exo_multiplayer);
                if (!AppConst.New_MultiScreen.booleanValue()) {
                }
                if (this.exo_multiplayer != null) {
                }
                this.ukd = Utils.ukde(FileMediaDataSource.apn());
                this.decoder_sw = findViewById(R.id.exo_decoder_sw);
                if (this.decoder_sw != null) {
                }
                this.decoder_hw = findViewById(R.id.exo_decoder_hw);
                if (this.decoder_hw != null) {
                }
                this.exo_info = findViewById(R.id.exo_info);
                if (this.exo_info != null) {
                }
                this.vlc_exo_audio = findViewById(R.id.vlc_exo_audio);
                if (this.vlc_exo_audio != null) {
                }
                this.rl_middle = (RelativeLayout) findViewById(R.id.middle);
                uk = getApplicationName(this.context);
                this.liveListDetailUnlcked = new ArrayList<>();
                this.liveListDetailUnlckedDetail = new ArrayList<>();
                this.liveListDetailAvailable = new ArrayList<>();
                this.liveListDetailAvailableForCounter = new ArrayList<>();
                this.liveListDetail = new ArrayList<>();
                this.liveListDetailUnlckedChannels = new ArrayList<>();
                this.liveListDetailUnlckedDetailChannels = new ArrayList<>();
                this.liveListDetailAvailableChannels = new ArrayList<>();
                this.liveListDetailAvailableChannels_Temp = new ArrayList<>();
                una = getApplicationContext().getPackageName();
                this.AvailableChannelsFirstOpenedCat = new ArrayList<>();
                this.liveListDetailAvailableNewChannels = new ArrayList<>();
                this.liveListDetailChannels = new ArrayList<>();
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
            getIntent().getStringExtra("STREAM_TYPE");
            getIntent().getStringExtra("VIDEO_TITLE");
            getIntent().getStringExtra("EPG_CHANNEL_ID");
            getIntent().getStringExtra("EPG_CHANNEL_LOGO");
            if (!this.allowedFormat.equals("")) {
            }
            this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
            this.liveStreamDBHandler = new LiveStreamDBHandler(this);
            this.database = new DatabaseHandler(this.context);
            this.handler = new Handler();
            this.handlerHeaderFooter = new Handler();
            this.handlerShowEPG = new Handler();
            this.handlerUpdateEPGData = new Handler();
            this.handlerJumpChannel = new Handler();
            this.elv = Utils.ukde(TextureRenderView.pZGV() + TextureRenderView.mv());
            this.mVideoView = (NSTIJKPlayerSky) findViewById(R.id.video_view);
            this.mVideoView.setLiveStreamDBHandler(this.liveStreamDBHandler);
            this.mVideoView.setActivity(this, this.mVideoView);
            this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
            this.ll_seekbar_time = (LinearLayout) findViewById(R.id.ll_seekbar_time);
            this.channelLogo = (ImageView) findViewById(R.id.iv_channel_logo);
            this.currentProgram = (TextView) findViewById(R.id.tv_current_program);
            this.currentProgramTime = (TextView) findViewById(R.id.tv_current_time);
            this.nextProgram = (TextView) findViewById(R.id.tv_next_program);
            this.nextProgramTime = (TextView) findViewById(R.id.tv_next_program_time);
            this.btn_cat_back = (AppCompatImageView) findViewById(R.id.btn_category_back);
            this.btn_cat_forward = (AppCompatImageView) findViewById(R.id.btn_category_fwd);
            this.tv_categories_view = (TextView) findViewById(R.id.tv_categories_view);
            this.myRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
            this.pbLoader = (ProgressBar) findViewById(R.id.pb_loader);
            this.toolbar = (Toolbar) findViewById(R.id.toolbar);
            this.tvNoStream = (TextView) findViewById(R.id.tv_noStream);
            this.tvNoRecordFound = (TextView) findViewById(R.id.tv_no_record_found);
            this.appbarToolbar = (AppBarLayout) findViewById(R.id.appbar_toolbar);
            this.rl_settings = (RelativeLayout) findViewById(R.id.rl_settings);
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
            this.mInfo = (TextView) findViewById(R.id.player_overlay_info);
            this.ll_channel_jumping = (LinearLayout) findViewById(R.id.ll_channel_jumping);
            this.app_channel_jumping_text = (TextView) findViewById(R.id.app_channel_jumping_text);
            this.vlcSeekbar = (SeekBar) findViewById(R.id.vlcSeekbar);
            this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
            this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
            this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
            this.pb_listview_loader = (ProgressBar) findViewById(R.id.pb_listview_loader);
            this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
            this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
            this.toolbar.inflateMenu(R.menu.menu_search);
            setSupportActionBar(this.toolbar);
            new Thread(new CountDownRunner()).start();
            if (SharepreferenceDBHandler.getSelectedLanguage(this.context).equalsIgnoreCase("Arabic")) {
            }
            loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
            this.programTimeFormat = new SimpleDateFormat(loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, ""), Locale.US);
            findViewById(R.id.exo_next).setOnClickListener(this);
            findViewById(R.id.exo_prev).setOnClickListener(this);
            this.fullScreen = false;
            findViewById(R.id.app_video_box).setOnClickListener(this);
            this.listChannels = (ListView) findViewById(R.id.lv_ch);
            this.et_search = (EditText) findViewById(R.id.et_search);
            this.rl_categories_view = (RelativeLayout) findViewById(R.id.rl_categories_view);
            this.btn_cat_back.setOnClickListener(this);
            this.btn_cat_forward.setOnClickListener(this);
            this.vlcplayButton = findViewById(R.id.exo_play);
            if (this.vlcplayButton != null) {
            }
            this.vlcpauseButton = findViewById(R.id.exo_pause);
            if (this.vlcpauseButton != null) {
            }
            this.vlcprevButton = findViewById(R.id.exo_prev);
            if (this.vlcprevButton != null) {
            }
            this.vlcnextButton = findViewById(R.id.exo_next);
            if (this.vlcnextButton != null) {
            }
            this.vlcchannelListButton = findViewById(R.id.btn_list);
            if (this.vlcchannelListButton != null) {
            }
            this.vlcaspectRatio = findViewById(R.id.btn_aspect_ratio);
            if (this.vlcaspectRatio != null) {
            }
            this.vlc_exo_subtitle = findViewById(R.id.exo_subtitle);
            if (this.vlc_exo_subtitle != null) {
            }
            this.exo_multiplayer = findViewById(R.id.exo_multiplayer);
            if (!AppConst.New_MultiScreen.booleanValue()) {
            }
            if (this.exo_multiplayer != null) {
            }
            this.ukd = Utils.ukde(FileMediaDataSource.apn());
            this.decoder_sw = findViewById(R.id.exo_decoder_sw);
            if (this.decoder_sw != null) {
            }
            this.decoder_hw = findViewById(R.id.exo_decoder_hw);
            if (this.decoder_hw != null) {
            }
            this.exo_info = findViewById(R.id.exo_info);
            if (this.exo_info != null) {
            }
            this.vlc_exo_audio = findViewById(R.id.vlc_exo_audio);
            if (this.vlc_exo_audio != null) {
            }
            this.rl_middle = (RelativeLayout) findViewById(R.id.middle);
            uk = getApplicationName(this.context);
            this.liveListDetailUnlcked = new ArrayList<>();
            this.liveListDetailUnlckedDetail = new ArrayList<>();
            this.liveListDetailAvailable = new ArrayList<>();
            this.liveListDetailAvailableForCounter = new ArrayList<>();
            this.liveListDetail = new ArrayList<>();
            this.liveListDetailUnlckedChannels = new ArrayList<>();
            this.liveListDetailUnlckedDetailChannels = new ArrayList<>();
            this.liveListDetailAvailableChannels = new ArrayList<>();
            this.liveListDetailAvailableChannels_Temp = new ArrayList<>();
            una = getApplicationContext().getPackageName();
            this.AvailableChannelsFirstOpenedCat = new ArrayList<>();
            this.liveListDetailAvailableNewChannels = new ArrayList<>();
            this.liveListDetailChannels = new ArrayList<>();
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
        getIntent().getStringExtra("STREAM_TYPE");
        getIntent().getStringExtra("VIDEO_TITLE");
        getIntent().getStringExtra("EPG_CHANNEL_ID");
        getIntent().getStringExtra("EPG_CHANNEL_LOGO");
        if (!this.allowedFormat.equals("")) {
        }
        this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
        this.liveStreamDBHandler = new LiveStreamDBHandler(this);
        this.database = new DatabaseHandler(this.context);
        this.handler = new Handler();
        this.handlerHeaderFooter = new Handler();
        this.handlerShowEPG = new Handler();
        this.handlerUpdateEPGData = new Handler();
        this.handlerJumpChannel = new Handler();
        this.elv = Utils.ukde(TextureRenderView.pZGV() + TextureRenderView.mv());
        this.mVideoView = (NSTIJKPlayerSky) findViewById(R.id.video_view);
        this.mVideoView.setLiveStreamDBHandler(this.liveStreamDBHandler);
        this.mVideoView.setActivity(this, this.mVideoView);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.ll_seekbar_time = (LinearLayout) findViewById(R.id.ll_seekbar_time);
        this.channelLogo = (ImageView) findViewById(R.id.iv_channel_logo);
        this.currentProgram = (TextView) findViewById(R.id.tv_current_program);
        this.currentProgramTime = (TextView) findViewById(R.id.tv_current_time);
        this.nextProgram = (TextView) findViewById(R.id.tv_next_program);
        this.nextProgramTime = (TextView) findViewById(R.id.tv_next_program_time);
        this.btn_cat_back = (AppCompatImageView) findViewById(R.id.btn_category_back);
        this.btn_cat_forward = (AppCompatImageView) findViewById(R.id.btn_category_fwd);
        this.tv_categories_view = (TextView) findViewById(R.id.tv_categories_view);
        this.myRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        this.pbLoader = (ProgressBar) findViewById(R.id.pb_loader);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.tvNoStream = (TextView) findViewById(R.id.tv_noStream);
        this.tvNoRecordFound = (TextView) findViewById(R.id.tv_no_record_found);
        this.appbarToolbar = (AppBarLayout) findViewById(R.id.appbar_toolbar);
        this.rl_settings = (RelativeLayout) findViewById(R.id.rl_settings);
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
        this.mInfo = (TextView) findViewById(R.id.player_overlay_info);
        this.ll_channel_jumping = (LinearLayout) findViewById(R.id.ll_channel_jumping);
        this.app_channel_jumping_text = (TextView) findViewById(R.id.app_channel_jumping_text);
        this.vlcSeekbar = (SeekBar) findViewById(R.id.vlcSeekbar);
        this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
        this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
        this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
        this.pb_listview_loader = (ProgressBar) findViewById(R.id.pb_listview_loader);
        this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Exception | UnsatisfiedLinkError unused) {
        }
        this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.toolbar.inflateMenu(R.menu.menu_search);
        setSupportActionBar(this.toolbar);
        new Thread(new CountDownRunner()).start();
        if (SharepreferenceDBHandler.getSelectedLanguage(this.context).equalsIgnoreCase("Arabic")) {
        }
        loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
        this.programTimeFormat = new SimpleDateFormat(loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, ""), Locale.US);
        findViewById(R.id.exo_next).setOnClickListener(this);
        findViewById(R.id.exo_prev).setOnClickListener(this);
        this.fullScreen = false;
        findViewById(R.id.app_video_box).setOnClickListener(this);
        this.listChannels = (ListView) findViewById(R.id.lv_ch);
        this.et_search = (EditText) findViewById(R.id.et_search);
        this.rl_categories_view = (RelativeLayout) findViewById(R.id.rl_categories_view);
        this.btn_cat_back.setOnClickListener(this);
        this.btn_cat_forward.setOnClickListener(this);
        this.vlcplayButton = findViewById(R.id.exo_play);
        if (this.vlcplayButton != null) {
        }
        this.vlcpauseButton = findViewById(R.id.exo_pause);
        if (this.vlcpauseButton != null) {
        }
        this.vlcprevButton = findViewById(R.id.exo_prev);
        if (this.vlcprevButton != null) {
        }
        this.vlcnextButton = findViewById(R.id.exo_next);
        if (this.vlcnextButton != null) {
        }
        this.vlcchannelListButton = findViewById(R.id.btn_list);
        if (this.vlcchannelListButton != null) {
        }
        this.vlcaspectRatio = findViewById(R.id.btn_aspect_ratio);
        if (this.vlcaspectRatio != null) {
        }
        this.vlc_exo_subtitle = findViewById(R.id.exo_subtitle);
        if (this.vlc_exo_subtitle != null) {
        }
        this.exo_multiplayer = findViewById(R.id.exo_multiplayer);
        if (!AppConst.New_MultiScreen.booleanValue()) {
        }
        if (this.exo_multiplayer != null) {
        }
        this.ukd = Utils.ukde(FileMediaDataSource.apn());
        this.decoder_sw = findViewById(R.id.exo_decoder_sw);
        if (this.decoder_sw != null) {
        }
        this.decoder_hw = findViewById(R.id.exo_decoder_hw);
        if (this.decoder_hw != null) {
        }
        this.exo_info = findViewById(R.id.exo_info);
        if (this.exo_info != null) {
        }
        this.vlc_exo_audio = findViewById(R.id.vlc_exo_audio);
        if (this.vlc_exo_audio != null) {
        }
        this.rl_middle = (RelativeLayout) findViewById(R.id.middle);
        uk = getApplicationName(this.context);
        this.liveListDetailUnlcked = new ArrayList<>();
        this.liveListDetailUnlckedDetail = new ArrayList<>();
        this.liveListDetailAvailable = new ArrayList<>();
        this.liveListDetailAvailableForCounter = new ArrayList<>();
        this.liveListDetail = new ArrayList<>();
        this.liveListDetailUnlckedChannels = new ArrayList<>();
        this.liveListDetailUnlckedDetailChannels = new ArrayList<>();
        this.liveListDetailAvailableChannels = new ArrayList<>();
        this.liveListDetailAvailableChannels_Temp = new ArrayList<>();
        una = getApplicationContext().getPackageName();
        this.AvailableChannelsFirstOpenedCat = new ArrayList<>();
        this.liveListDetailAvailableNewChannels = new ArrayList<>();
        this.liveListDetailChannels = new ArrayList<>();
        this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + SurfaceRenderView.mw());
        if (this.tv_categories_view == null) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x0130 A[Catch:{ Exception -> 0x01a1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0133 A[Catch:{ Exception -> 0x01a1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0155 A[Catch:{ Exception -> 0x01a1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0168 A[Catch:{ Exception -> 0x01a1 }] */
    public boolean initialize() {
        //ToDo: initialized...
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
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity):void
     arg types: [android.widget.Button, com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity]
     candidates:
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.content.Context):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.CheckAppupdateActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.LoginActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.MultiUserActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.NewDashboardActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RateUsActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RecordingActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.SplashActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.VodActivityNewFlowSubCategories):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity):void */
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
                button.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button, this));
                button2.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button2, this));
                button.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass4 */

                    public void onClick(View view) {
                        try {
                            NSTIJKPlayerSkyActivity.this.sentToSettings = true;
                            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.fromParts("package", NSTIJKPlayerSkyActivity.this.getPackageName(), null));
                            NSTIJKPlayerSkyActivity.this.startActivityForResult(intent, 101);
                            Toast.makeText(NSTIJKPlayerSkyActivity.this, NSTIJKPlayerSkyActivity.this.context.getResources().getString(R.string.grant_the_permission), 1).show();
                        } catch (Exception unused) {
                        }
                        NSTIJKPlayerSkyActivity.this.alertDialog.dismiss();
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass5 */

                    public void onClick(View view) {
                        NSTIJKPlayerSkyActivity.this.alertDialog.dismiss();
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
        this.no_channel_found = true;
        hideTitleBarAndFooter();
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
    class NSTIJKPlayerSkyActivityAsync extends AsyncTask<String, Void, String> {
        NSTIJKPlayerSkyActivityAsync() {
        }

        public void onPreExecute() {
            super.onPreExecute();
            NSTIJKPlayerSkyActivity.this.showLoader();
            if (NSTIJKPlayerSkyActivity.this.tvNoRecordFound != null) {
                NSTIJKPlayerSkyActivity.this.tvNoRecordFound.setVisibility(8);
            }
            if (NSTIJKPlayerSkyActivity.this.tv_categories_view != null) {
                NSTIJKPlayerSkyActivity.this.tv_categories_view.setText(NSTIJKPlayerSkyActivity.this.catName);
                NSTIJKPlayerSkyActivity.this.tv_categories_view.setSelected(true);
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
                                        return NSTIJKPlayerSkyActivity.this.allChannelsAsync();
                                    case 1:
                                        return NSTIJKPlayerSkyActivity.this.allChannelsWithCatAsync();
                                    case 2:
                                        return NSTIJKPlayerSkyActivity.this.getFavouritesAsync();
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
            //ToDo: return statement...
            return null;
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(5:16|17|18|19|20) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x00a1 */
        public void onPostExecute(String str) {
            super.onPostExecute((String) str);
            try {
                if (NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels != null) {
                    if (!NSTIJKPlayerSkyActivity.this.playedFirstTime.booleanValue()) {
                        Boolean unused = NSTIJKPlayerSkyActivity.this.playedFirstTime = true;
                        if (NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.size() != 0) {
                            NSTIJKPlayerSkyActivity.this.playFirstTime(NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels);
                        } else {
                            AppConst.SETPLAYERLISTENER = false;
                            NSTIJKPlayerSkyActivity.this.noChannelFound();
                            NSTIJKPlayerSkyActivity.this.mVideoView.setVisibility(8);
                            NSTIJKPlayerSkyActivity.this.app_video_status.setVisibility(0);
                            NSTIJKPlayerSkyActivity.this.app_video_status_text.setText(NSTIJKPlayerSkyActivity.this.getResources().getString(R.string.no_channel_found));
                        }
                    }
                    if (NSTIJKPlayerSkyActivity.this.channelZapped && NSTIJKPlayerSkyActivity.this.channelJumping && !NSTIJKPlayerSkyActivity.this.channelNumJumping.equals("")) {
                        int unused2 = NSTIJKPlayerSkyActivity.this.positionToSelectAfterJumping = 0;
                        int unused3 = NSTIJKPlayerSkyActivity.this.positionToSelectAfterJumping = NSTIJKPlayerSkyActivity.this.getIndexOfStreams(NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels, Utils.parseIntZero(NSTIJKPlayerSkyActivity.this.channelNumJumping));
                        NSTIJKPlayerSkyActivity.this.channelZapped = false;
                        NSTIJKPlayerSkyActivity.this.channelJumping = false;
                    }
                    NSTIJKPlayerSkyActivity.this.setChannelListAdapter(NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels);
                }
                NSTIJKPlayerSkyActivity.this.hideLoader();
            } catch (Exception unused4) {
            }
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
            try {
                NSTIJKPlayerSkyActivity.this.loadingEPGData();
                if (NSTIJKPlayerSkyActivity.this.ll_epg1_box != null) {
                    NSTIJKPlayerSkyActivity.this.ll_epg1_box.setVisibility(8);
                }
                if (NSTIJKPlayerSkyActivity.this.ll_epg2_box != null) {
                    NSTIJKPlayerSkyActivity.this.ll_epg2_box.setVisibility(8);
                }
                if (NSTIJKPlayerSkyActivity.this.ll_epg3_box != null) {
                    NSTIJKPlayerSkyActivity.this.ll_epg3_box.setVisibility(8);
                }
                if (NSTIJKPlayerSkyActivity.this.ll_epg4_box != null) {
                    NSTIJKPlayerSkyActivity.this.ll_epg4_box.setVisibility(8);
                }
                if (NSTIJKPlayerSkyActivity.this.ll_no_guide != null) {
                    NSTIJKPlayerSkyActivity.this.ll_no_guide.setVisibility(8);
                }
                if (NSTIJKPlayerSkyActivity.this.ll_epg_loader != null) {
                    NSTIJKPlayerSkyActivity.this.ll_epg_loader.setVisibility(0);
                }
            } catch (Exception unused) {
            }
        }

        public HashMap<String, ArrayList<String>> doInBackground(String... strArr) {
            try {
                return NSTIJKPlayerSkyActivity.this.showEPGAsync(this.currentEpgChannelID);
            } catch (Exception unused) {
                return new HashMap<>();
            }
        }

        public void onPostExecute(HashMap<String, ArrayList<String>> hashMap) {
            super.onPostExecute((HashMap<String, ArrayList<String>>) hashMap);
            try {
                if (hashMap.get("2") != null) {
                    ArrayList<String> arrayList = hashMap.get("2");
                    if (arrayList.get(0) == null || !arrayList.get(0).equals("installed")) {
                        NSTIJKPlayerSkyActivity.this.ll_no_guide.setVisibility(0);
                        NSTIJKPlayerSkyActivity.this.ll_epg_loader.setVisibility(8);
                    } else if (hashMap.get(AppConst.PASSWORD_UNSET) != null) {
                        ArrayList<String> arrayList2 = hashMap.get(AppConst.PASSWORD_UNSET);
                        if (arrayList2 == null || arrayList2.size() <= 0) {
                            NSTIJKPlayerSkyActivity.this.ll_epg_loader.setVisibility(8);
                        } else {
                            if (NSTIJKPlayerSkyActivity.this.ll_epg_loader != null) {
                                NSTIJKPlayerSkyActivity.this.ll_epg_loader.setVisibility(8);
                            }
                            if (!(hashMap.get("3") == null || NSTIJKPlayerSkyActivity.this.progressBar == null)) {
                                ArrayList<String> arrayList3 = hashMap.get("3");
                                if (arrayList3.get(0) != null) {
                                    NSTIJKPlayerSkyActivity.this.progressBar.setProgress(Integer.parseInt(arrayList3.get(0)));
                                } else {
                                    NSTIJKPlayerSkyActivity.this.progressBar.setProgress(0);
                                }
                            }
                            if (arrayList2.get(0) != null) {
                                NSTIJKPlayerSkyActivity.this.currentProgram.setText(arrayList2.get(0));
                            } else {
                                NSTIJKPlayerSkyActivity.this.currentProgram.setText(NSTIJKPlayerSkyActivity.this.context.getResources().getString(R.string.now_program_found));
                            }
                            if (arrayList2.get(1) != null) {
                                NSTIJKPlayerSkyActivity.this.currentProgramTime.setText(arrayList2.get(1));
                            } else {
                                NSTIJKPlayerSkyActivity.this.currentProgramTime.setText(NSTIJKPlayerSkyActivity.this.context.getResources().getString(R.string.now_program_found));
                            }
                            if (arrayList2.get(2) != null) {
                                NSTIJKPlayerSkyActivity.this.nextProgram.setText(arrayList2.get(2));
                            } else {
                                NSTIJKPlayerSkyActivity.this.nextProgram.setText(NSTIJKPlayerSkyActivity.this.context.getResources().getString(R.string.now_program_found));
                            }
                            if (arrayList2.get(3) != null) {
                                NSTIJKPlayerSkyActivity.this.nextProgramTime.setText(arrayList2.get(3));
                            } else {
                                NSTIJKPlayerSkyActivity.this.nextProgramTime.setText(NSTIJKPlayerSkyActivity.this.context.getResources().getString(R.string.now_program_found));
                            }
                        }
                    } else {
                        NSTIJKPlayerSkyActivity.this.ll_epg_loader.setVisibility(8);
                    }
                } else {
                    NSTIJKPlayerSkyActivity.this.ll_no_guide.setVisibility(0);
                    NSTIJKPlayerSkyActivity.this.ll_epg_loader.setVisibility(8);
                }
                if (hashMap.get("1") != null) {
                    ArrayList<String> arrayList4 = hashMap.get("1");
                    if (arrayList4 == null || arrayList4.size() <= 0) {
                        NSTIJKPlayerSkyActivity.this.hideEPGData();
                        return;
                    }
                    if (arrayList4.get(0) != null) {
                        if (NSTIJKPlayerSkyActivity.this.tv_epg1_date != null) {
                            NSTIJKPlayerSkyActivity.this.tv_epg1_date.setText(arrayList4.get(0));
                        }
                    } else if (NSTIJKPlayerSkyActivity.this.tv_epg1_date != null) {
                        NSTIJKPlayerSkyActivity.this.tv_epg1_date.setText("");
                    }
                    if (arrayList4.get(1) != null) {
                        if (NSTIJKPlayerSkyActivity.this.tv_epg1_program != null) {
                            NSTIJKPlayerSkyActivity.this.tv_epg1_program.setText(arrayList4.get(1));
                        }
                    } else if (NSTIJKPlayerSkyActivity.this.tv_epg1_program != null) {
                        NSTIJKPlayerSkyActivity.this.tv_epg1_program.setText("");
                    }
                    if (NSTIJKPlayerSkyActivity.this.tv_epg1_program != null) {
                        NSTIJKPlayerSkyActivity.this.tv_epg1_program.setSelected(true);
                    }
                    if (NSTIJKPlayerSkyActivity.this.ll_epg1_box != null) {
                        NSTIJKPlayerSkyActivity.this.ll_epg1_box.setVisibility(0);
                    }
                    if (arrayList4.get(2) != null) {
                        if (NSTIJKPlayerSkyActivity.this.tv_epg2_date != null) {
                            NSTIJKPlayerSkyActivity.this.tv_epg2_date.setText(arrayList4.get(2));
                        }
                    } else if (NSTIJKPlayerSkyActivity.this.tv_epg2_date != null) {
                        NSTIJKPlayerSkyActivity.this.tv_epg2_date.setText("");
                    }
                    if (arrayList4.get(3) != null) {
                        if (NSTIJKPlayerSkyActivity.this.tv_epg2_program != null) {
                            NSTIJKPlayerSkyActivity.this.tv_epg2_program.setText(arrayList4.get(3));
                        }
                    } else if (NSTIJKPlayerSkyActivity.this.tv_epg2_program != null) {
                        NSTIJKPlayerSkyActivity.this.tv_epg2_program.setText("");
                    }
                    if (NSTIJKPlayerSkyActivity.this.tv_epg2_program != null) {
                        NSTIJKPlayerSkyActivity.this.tv_epg2_program.setSelected(true);
                    }
                    if (NSTIJKPlayerSkyActivity.this.ll_epg2_box != null) {
                        NSTIJKPlayerSkyActivity.this.ll_epg2_box.setVisibility(0);
                    }
                    if (arrayList4.get(4) != null) {
                        if (NSTIJKPlayerSkyActivity.this.tv_epg3_date != null) {
                            NSTIJKPlayerSkyActivity.this.tv_epg3_date.setText(arrayList4.get(4));
                        }
                    } else if (NSTIJKPlayerSkyActivity.this.tv_epg3_date != null) {
                        NSTIJKPlayerSkyActivity.this.tv_epg3_date.setText("");
                    }
                    if (arrayList4.get(5) != null) {
                        if (NSTIJKPlayerSkyActivity.this.tv_epg3_program != null) {
                            NSTIJKPlayerSkyActivity.this.tv_epg3_program.setText(arrayList4.get(5));
                        }
                    } else if (NSTIJKPlayerSkyActivity.this.tv_epg3_program != null) {
                        NSTIJKPlayerSkyActivity.this.tv_epg3_program.setText("");
                    }
                    if (NSTIJKPlayerSkyActivity.this.tv_epg3_program != null) {
                        NSTIJKPlayerSkyActivity.this.tv_epg3_program.setSelected(true);
                    }
                    if (NSTIJKPlayerSkyActivity.this.ll_epg3_box != null) {
                        NSTIJKPlayerSkyActivity.this.ll_epg3_box.setVisibility(0);
                    }
                    if (arrayList4.get(6) != null) {
                        if (NSTIJKPlayerSkyActivity.this.tv_epg4_date != null) {
                            NSTIJKPlayerSkyActivity.this.tv_epg4_date.setText(arrayList4.get(6));
                        }
                    } else if (NSTIJKPlayerSkyActivity.this.tv_epg4_date != null) {
                        NSTIJKPlayerSkyActivity.this.tv_epg4_date.setText("");
                    }
                    if (arrayList4.get(7) != null) {
                        if (NSTIJKPlayerSkyActivity.this.tv_epg4_program != null) {
                            NSTIJKPlayerSkyActivity.this.tv_epg4_program.setText(arrayList4.get(7));
                        }
                    } else if (NSTIJKPlayerSkyActivity.this.tv_epg4_program != null) {
                        NSTIJKPlayerSkyActivity.this.tv_epg4_program.setText("");
                    }
                    if (NSTIJKPlayerSkyActivity.this.tv_epg4_program != null) {
                        NSTIJKPlayerSkyActivity.this.tv_epg4_program.setSelected(true);
                    }
                    if (NSTIJKPlayerSkyActivity.this.ll_epg4_box != null) {
                        NSTIJKPlayerSkyActivity.this.ll_epg4_box.setVisibility(0);
                        return;
                    }
                    return;
                }
                NSTIJKPlayerSkyActivity.this.hideEPGData();
            } catch (Exception unused) {
            }
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
                return Boolean.valueOf(NSTIJKPlayerSkyActivity.this.initialize());
            } catch (Exception unused) {
                return false;
            }
        }

        public void onPostExecute(Boolean bool) {
            super.onPostExecute((Boolean) bool);
            if (NSTIJKPlayerSkyActivity.this.mVideoView != null) {
                NSTIJKPlayerSkyActivity.this.mVideoView.setEPGHandler(NSTIJKPlayerSkyActivity.this.handlerUpdateEPGData);
                NSTIJKPlayerSkyActivity.this.mVideoView.setContext(NSTIJKPlayerSkyActivity.this.context);
            }
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
        if (this.AsyncTaskNSTIJKPlayerSkyActivity == null || !this.AsyncTaskNSTIJKPlayerSkyActivity.getStatus().equals(AsyncTask.Status.RUNNING)) {
            SharepreferenceDBHandler.setAsyncTaskStatus(0, this.context);
            return;
        }
        SharepreferenceDBHandler.setAsyncTaskStatus(1, this.context);
        this.AsyncTaskNSTIJKPlayerSkyActivity.cancel(true);
    }

    public void allChannels() {
        checkIfAsyncTaskRunning();
        this.AsyncTaskNSTIJKPlayerSkyActivity = new NSTIJKPlayerSkyActivityAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "all_channels");
    }

    public void allFavourites() {
        checkIfAsyncTaskRunning();
        this.AsyncTaskNSTIJKPlayerSkyActivity = new NSTIJKPlayerSkyActivityAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "get_fav");
    }

    public void allChannelsWithCat() {
        checkIfAsyncTaskRunning();
        this.AsyncTaskNSTIJKPlayerSkyActivity = new NSTIJKPlayerSkyActivityAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "all_channels_with_cat");
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
                    NSTIJKPlayerSkyActivity.this.doWork();
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
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass6 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(NSTIJKPlayerSkyActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (NSTIJKPlayerSkyActivity.this.time != null) {
                        NSTIJKPlayerSkyActivity.this.time.setText(time);
                    }
                    if (NSTIJKPlayerSkyActivity.this.date != null) {
                        NSTIJKPlayerSkyActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    @SuppressLint({"SetTextI18n"})
    public void playFirstTime(ArrayList<LiveStreamsDBModel> arrayList) {
        if (arrayList != null && arrayList.size() > 0 && this.opened_vlc_id < arrayList.size()) {
            int parseIntZero = Utils.parseIntZero(arrayList.get(this.opened_vlc_id).getNum());
            this.firstplayedChannelNumber = parseIntZero;
            String name = arrayList.get(this.opened_vlc_id).getName();
            int parseIntMinusOne = Utils.parseIntMinusOne(arrayList.get(this.opened_vlc_id).getStreamId());
            String epgChannelId = arrayList.get(this.opened_vlc_id).getEpgChannelId();
            String streamIcon = arrayList.get(this.opened_vlc_id).getStreamIcon();
            arrayList.get(this.opened_vlc_id).getNum();
            this.m3uVideoURL = arrayList.get(this.opened_vlc_id).getUrl();
            String ukde = Utils.ukde(TableLayoutBinder.aW5nIGl() + TableLayoutBinder.mu());
            if (streamIcon.equals("") || streamIcon.isEmpty()) {
                this.channelLogo.setImageDrawable(this.context.getResources().getDrawable(R.drawable.logo_placeholder_white));
            } else {
                Picasso.with(this.context).load(streamIcon).placeholder((int) R.drawable.logo_placeholder_white).error((int) R.drawable.logo_placeholder_white).into(this.channelLogo);
            }
            this.mVideoView.setCurrentWindowIndex(this.opened_vlc_id);
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
                this.mVideoView.setVisibility(8);
                this.app_video_status.setVisibility(0);
                TextView textView = this.app_video_status_text;
                textView.setText(ukde + this.elv + this.fmw);
            }
            this.currentProgramStreamID = parseIntMinusOne;
            this.currentProgramChanneID = epgChannelId;
            this.currentProgramM3UURL = this.m3uVideoURL;
            NSTIJKPlayerSky nSTIJKPlayerSky = this.mVideoView;
            nSTIJKPlayerSky.setTitle(parseIntZero + " - " + name);
            this.mVideoView.removeHandlerCallback();
            if (!this.fullScreen) {
                if (this.mVideoView.getFullScreenValue().booleanValue()) {
                    this.fullScreen = this.mVideoView.getFullScreenValue().booleanValue();
                } else {
                    this.fullScreen = false;
                }
            }
            if (this.rq.booleanValue()) {
                if (this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                    this.mVideoView.setVideoURI(Uri.parse(this.m3uVideoURL), this.fullScreen, name);
                } else {
                    NSTIJKPlayerSky nSTIJKPlayerSky2 = this.mVideoView;
                    nSTIJKPlayerSky2.setVideoURI(Uri.parse(this.mFilePath + parseIntMinusOne + this.allowedFormat), this.fullScreen, name);
                }
                this.mVideoView.retryCount = 0;
                this.mVideoView.retrying = false;
                this.mVideoView.start();
            }
            this.handlerUpdateEPGData.removeCallbacksAndMessages(null);
            this.currentEpgChannelID = epgChannelId;
            this.currentChannelLogo = streamIcon;
            this.mVideoView.setCurrentEpgChannelID(this.currentEpgChannelID);
            this.mVideoView.setCurrentChannelLogo(this.currentChannelLogo);
            updateChannelLogo(this.currentChannelLogo);
            this.AsyncTaskUpdateEPG = new UpdateEPGAsyncTask(this.currentEpgChannelID).execute(new String[0]);
            if (this.listChannels != null) {
                this.listChannels.requestFocus();
            }
            this.playFirstTimeLoaded = true;
            hideTitleBarAndFooter();
        }
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
                        /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass7 */

                        @Override // android.widget.AdapterView.OnItemSelectedListener
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }

                        @Override // android.widget.AdapterView.OnItemSelectedListener
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                            if (NSTIJKPlayerSkyActivity.this.AsyncTaskUpdateEPG != null && NSTIJKPlayerSkyActivity.this.AsyncTaskUpdateEPG.getStatus().equals(AsyncTask.Status.RUNNING)) {
                                NSTIJKPlayerSkyActivity.this.AsyncTaskUpdateEPG.cancel(true);
                            }
                            ArrayList<LiveStreamsDBModel> filteredData = NSTIJKPlayerSkyActivity.this.adapter.getFilteredData();
                            if (filteredData != null && filteredData.size() > 0) {
                                String epgChannelId = filteredData.get(i).getEpgChannelId();
                                NSTIJKPlayerSkyActivity.this.handlerShowEPG.removeCallbacksAndMessages(null);
                                AsyncTask unused = NSTIJKPlayerSkyActivity.this.AsyncTaskUpdateEPG = new UpdateEPGAsyncTask(epgChannelId).execute(new String[0]);
                            } else if (NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels != null && NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.size() > 0) {
                                String epgChannelId2 = ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getEpgChannelId();
                                NSTIJKPlayerSkyActivity.this.handlerShowEPG.removeCallbacksAndMessages(null);
                                AsyncTask unused2 = NSTIJKPlayerSkyActivity.this.AsyncTaskUpdateEPG = new UpdateEPGAsyncTask(epgChannelId2).execute(new String[0]);
                            }
                        }
                    });
                    this.listChannels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass8 */

                        @Override // android.widget.AdapterView.OnItemClickListener
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                            if (NSTIJKPlayerSkyActivity.this.AsyncTaskUpdateEPG != null && NSTIJKPlayerSkyActivity.this.AsyncTaskUpdateEPG.getStatus().equals(AsyncTask.Status.RUNNING)) {
                                NSTIJKPlayerSkyActivity.this.AsyncTaskUpdateEPG.cancel(true);
                            }
                            AppConst.SETPLAYERLISTENER = true;
                            ArrayList<LiveStreamsDBModel> filteredData = NSTIJKPlayerSkyActivity.this.adapter.getFilteredData();
                            if (filteredData != null && filteredData.size() > 0) {
                                int parseIntZero = Utils.parseIntZero(filteredData.get(i).getNum());
                                String epgChannelId = filteredData.get(i).getEpgChannelId();
                                NSTIJKPlayerSkyActivity.this.getIndexOfStreams(NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels, parseIntZero);
                                String url = filteredData.get(i).getUrl();
                                if (NSTIJKPlayerSkyActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                    if (!NSTIJKPlayerSkyActivity.this.currentProgramM3UURL.equals(filteredData.get(i).getUrl())) {
                                        NSTIJKPlayerSkyActivity.this.mVideoView.setCurrentWindowIndex(i);
                                        int unused = NSTIJKPlayerSkyActivity.this.firstplayedChannelNumber = Utils.parseIntZero(filteredData.get(i).getNum());
                                        NSTIJKPlayerSky access$700 = NSTIJKPlayerSkyActivity.this.mVideoView;
                                        access$700.setTitle(filteredData.get(i).getNum() + " - " + filteredData.get(i).getName());
                                        NSTIJKPlayerSkyActivity.this.currentProgramM3UURL = filteredData.get(i).getUrl();
                                        if (!NSTIJKPlayerSkyActivity.this.catID.equals("-1") && !NSTIJKPlayerSkyActivity.this.catID.equals(AppConst.PASSWORD_UNSET)) {
                                            NSTIJKPlayerSkyActivity.this.catID = filteredData.get(i).getCategoryId();
                                            NSTIJKPlayerSkyActivity.this.catName = filteredData.get(i).getCategoryName();
                                            if (NSTIJKPlayerSkyActivity.this.catName.isEmpty()) {
                                                NSTIJKPlayerSkyActivity.this.catName = NSTIJKPlayerSkyActivity.this.catName(NSTIJKPlayerSkyActivity.this.catID);
                                            }
                                        }
                                        String streamIcon = filteredData.get(i).getStreamIcon();
                                        if (streamIcon.equals("") || streamIcon.isEmpty()) {
                                            NSTIJKPlayerSkyActivity.this.channelLogo.setImageDrawable(NSTIJKPlayerSkyActivity.this.context.getResources().getDrawable(R.drawable.logo_placeholder_white));
                                        } else {
                                            Picasso.with(NSTIJKPlayerSkyActivity.this.context).load(streamIcon).placeholder((int) R.drawable.logo_placeholder_white).error((int) R.drawable.logo_placeholder_white).into(NSTIJKPlayerSkyActivity.this.channelLogo);
                                        }
                                        NSTIJKPlayerSkyActivity.this.handlerUpdateEPGData.removeCallbacksAndMessages(null);
                                        NSTIJKPlayerSkyActivity.this.currentEpgChannelID = epgChannelId;
                                        NSTIJKPlayerSkyActivity.this.currentChannelLogo = streamIcon;
                                        NSTIJKPlayerSkyActivity.this.mVideoView.setCurrentEpgChannelID(NSTIJKPlayerSkyActivity.this.currentEpgChannelID);
                                        NSTIJKPlayerSkyActivity.this.mVideoView.setCurrentChannelLogo(NSTIJKPlayerSkyActivity.this.currentChannelLogo);
                                        NSTIJKPlayerSkyActivity.this.updateChannelLogo(NSTIJKPlayerSkyActivity.this.currentChannelLogo);
                                        AsyncTask unused2 = NSTIJKPlayerSkyActivity.this.AsyncTaskUpdateEPG = new UpdateEPGAsyncTask(NSTIJKPlayerSkyActivity.this.currentEpgChannelID).execute(new String[0]);
                                        NSTIJKPlayerSkyActivity.this.handlerShowEPG.removeCallbacksAndMessages(null);
                                        int unused3 = NSTIJKPlayerSkyActivity.this.opened_vlc_id = i;
                                        NSTIJKPlayerSkyActivity.this.mVideoView.removeHandlerCallback();
                                        if (NSTIJKPlayerSkyActivity.this.rq.booleanValue()) {
                                            NSTIJKPlayerSkyActivity.this.mVideoView.setVideoURI(Uri.parse(url), NSTIJKPlayerSkyActivity.this.fullScreen, filteredData.get(i).getName());
                                            NSTIJKPlayerSkyActivity.this.mVideoView.retryCount = 0;
                                            NSTIJKPlayerSkyActivity.this.mVideoView.retrying = false;
                                            NSTIJKPlayerSkyActivity.this.mVideoView.start();
                                        }
                                        NSTIJKPlayerSkyActivity.this.showTitleBar();
                                        if (NSTIJKPlayerSkyActivity.this.loginPrefsEditor != null) {
                                            NSTIJKPlayerSkyActivity.this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(filteredData.get(i).getStreamId()));
                                            NSTIJKPlayerSkyActivity.this.loginPrefsEditor.putString("LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_M3U", String.valueOf(filteredData.get(i).getUrl()));
                                            NSTIJKPlayerSkyActivity.this.loginPrefsEditor.apply();
                                        }
                                        if (NSTIJKPlayerSkyActivity.this.loginPrefsEditorPosition != null) {
                                            NSTIJKPlayerSkyActivity.this.loginPrefsEditorPosition.putInt(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, i);
                                            NSTIJKPlayerSkyActivity.this.loginPrefsEditorPosition.apply();
                                        }
                                        NSTIJKPlayerSkyActivity.this.adapter.notifyDataSetChanged();
                                        return;
                                    }
                                    NSTIJKPlayerSkyActivity.this.fullScreenVideoLayout();
                                } else if (NSTIJKPlayerSkyActivity.this.currentProgramStreamID != Utils.parseIntMinusOne(filteredData.get(i).getStreamId())) {
                                    NSTIJKPlayerSkyActivity.this.mVideoView.setCurrentWindowIndex(i);
                                    int unused4 = NSTIJKPlayerSkyActivity.this.firstplayedChannelNumber = Utils.parseIntZero(filteredData.get(i).getNum());
                                    NSTIJKPlayerSky access$7002 = NSTIJKPlayerSkyActivity.this.mVideoView;
                                    access$7002.setTitle(filteredData.get(i).getNum() + " - " + filteredData.get(i).getName());
                                    NSTIJKPlayerSkyActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(filteredData.get(i).getStreamId());
                                    if (!NSTIJKPlayerSkyActivity.this.catID.equals("-1") && !NSTIJKPlayerSkyActivity.this.catID.equals(AppConst.PASSWORD_UNSET)) {
                                        NSTIJKPlayerSkyActivity.this.catID = filteredData.get(i).getCategoryId();
                                        NSTIJKPlayerSkyActivity.this.catName = filteredData.get(i).getCategoryName();
                                        if (NSTIJKPlayerSkyActivity.this.catName.isEmpty()) {
                                            NSTIJKPlayerSkyActivity.this.catName = NSTIJKPlayerSkyActivity.this.catName(NSTIJKPlayerSkyActivity.this.catID);
                                        }
                                    }
                                    String streamIcon2 = filteredData.get(i).getStreamIcon();
                                    if (streamIcon2.equals("") || streamIcon2.isEmpty()) {
                                        NSTIJKPlayerSkyActivity.this.channelLogo.setImageDrawable(NSTIJKPlayerSkyActivity.this.context.getResources().getDrawable(R.drawable.logo_placeholder_white));
                                    } else {
                                        Picasso.with(NSTIJKPlayerSkyActivity.this.context).load(streamIcon2).placeholder((int) R.drawable.logo_placeholder_white).error((int) R.drawable.logo_placeholder_white).into(NSTIJKPlayerSkyActivity.this.channelLogo);
                                    }
                                    NSTIJKPlayerSkyActivity.this.handlerUpdateEPGData.removeCallbacksAndMessages(null);
                                    NSTIJKPlayerSkyActivity.this.currentEpgChannelID = epgChannelId;
                                    NSTIJKPlayerSkyActivity.this.currentChannelLogo = streamIcon2;
                                    NSTIJKPlayerSkyActivity.this.mVideoView.setCurrentEpgChannelID(NSTIJKPlayerSkyActivity.this.currentEpgChannelID);
                                    NSTIJKPlayerSkyActivity.this.mVideoView.setCurrentChannelLogo(NSTIJKPlayerSkyActivity.this.currentChannelLogo);
                                    NSTIJKPlayerSkyActivity.this.updateChannelLogo(NSTIJKPlayerSkyActivity.this.currentChannelLogo);
                                    AsyncTask unused5 = NSTIJKPlayerSkyActivity.this.AsyncTaskUpdateEPG = new UpdateEPGAsyncTask(NSTIJKPlayerSkyActivity.this.currentEpgChannelID).execute(new String[0]);
                                    NSTIJKPlayerSkyActivity.this.handlerShowEPG.removeCallbacksAndMessages(null);
                                    int unused6 = NSTIJKPlayerSkyActivity.this.opened_vlc_id = i;
                                    NSTIJKPlayerSkyActivity.this.mVideoView.removeHandlerCallback();
                                    if (NSTIJKPlayerSkyActivity.this.rq.booleanValue()) {
                                        NSTIJKPlayerSky access$7003 = NSTIJKPlayerSkyActivity.this.mVideoView;
                                        access$7003.setVideoURI(Uri.parse(NSTIJKPlayerSkyActivity.this.mFilePath + Utils.parseIntMinusOne(filteredData.get(i).getStreamId()) + NSTIJKPlayerSkyActivity.this.allowedFormat), NSTIJKPlayerSkyActivity.this.fullScreen, filteredData.get(i).getName());
                                        NSTIJKPlayerSkyActivity.this.mVideoView.retryCount = 0;
                                        NSTIJKPlayerSkyActivity.this.mVideoView.retrying = false;
                                        NSTIJKPlayerSkyActivity.this.mVideoView.start();
                                    }
                                    NSTIJKPlayerSkyActivity.this.showTitleBar();
                                    if (NSTIJKPlayerSkyActivity.this.loginPrefsEditor != null) {
                                        NSTIJKPlayerSkyActivity.this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(filteredData.get(i).getStreamId()));
                                        NSTIJKPlayerSkyActivity.this.loginPrefsEditor.putString("LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_M3U", String.valueOf(filteredData.get(i).getUrl()));
                                        NSTIJKPlayerSkyActivity.this.loginPrefsEditor.apply();
                                    }
                                    if (NSTIJKPlayerSkyActivity.this.loginPrefsEditorPosition != null) {
                                        NSTIJKPlayerSkyActivity.this.loginPrefsEditorPosition.putInt(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, i);
                                        NSTIJKPlayerSkyActivity.this.loginPrefsEditorPosition.apply();
                                    }
                                    NSTIJKPlayerSkyActivity.this.adapter.notifyDataSetChanged();
                                } else {
                                    NSTIJKPlayerSkyActivity.this.fullScreenVideoLayout();
                                }
                            } else if (NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels != null && NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.size() > 0) {
                                Utils.parseIntZero(((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getNum());
                                String epgChannelId2 = ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getEpgChannelId();
                                String streamIcon3 = ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getStreamIcon();
                                String url2 = ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getUrl();
                                if (NSTIJKPlayerSkyActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                    if (!NSTIJKPlayerSkyActivity.this.currentProgramM3UURL.equals(((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getUrl())) {
                                        NSTIJKPlayerSkyActivity.this.mVideoView.setCurrentWindowIndex(i);
                                        int unused7 = NSTIJKPlayerSkyActivity.this.firstplayedChannelNumber = Utils.parseIntZero(((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getNum());
                                        NSTIJKPlayerSky access$7004 = NSTIJKPlayerSkyActivity.this.mVideoView;
                                        access$7004.setTitle(((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getNum() + " - " + ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getName());
                                        NSTIJKPlayerSkyActivity.this.currentProgramM3UURL = ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getUrl();
                                        if (!NSTIJKPlayerSkyActivity.this.catID.equals("-1") && !NSTIJKPlayerSkyActivity.this.catID.equals(AppConst.PASSWORD_UNSET)) {
                                            NSTIJKPlayerSkyActivity.this.catID = ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getCategoryId();
                                            NSTIJKPlayerSkyActivity.this.catName = ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getCategoryName();
                                            if (NSTIJKPlayerSkyActivity.this.catName.isEmpty()) {
                                                NSTIJKPlayerSkyActivity.this.catName = NSTIJKPlayerSkyActivity.this.catName(NSTIJKPlayerSkyActivity.this.catID);
                                            }
                                        }
                                        int unused8 = NSTIJKPlayerSkyActivity.this.opened_vlc_id = i;
                                        NSTIJKPlayerSkyActivity.this.mVideoView.removeHandlerCallback();
                                        if (NSTIJKPlayerSkyActivity.this.rq.booleanValue()) {
                                            NSTIJKPlayerSkyActivity.this.mVideoView.setVideoURI(Uri.parse(url2), NSTIJKPlayerSkyActivity.this.fullScreen, ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getName());
                                            NSTIJKPlayerSkyActivity.this.mVideoView.retryCount = 0;
                                            NSTIJKPlayerSkyActivity.this.mVideoView.retrying = false;
                                            NSTIJKPlayerSkyActivity.this.mVideoView.start();
                                        }
                                        NSTIJKPlayerSkyActivity.this.handlerUpdateEPGData.removeCallbacksAndMessages(null);
                                        NSTIJKPlayerSkyActivity.this.currentEpgChannelID = epgChannelId2;
                                        NSTIJKPlayerSkyActivity.this.currentChannelLogo = streamIcon3;
                                        NSTIJKPlayerSkyActivity.this.mVideoView.setCurrentEpgChannelID(NSTIJKPlayerSkyActivity.this.currentEpgChannelID);
                                        NSTIJKPlayerSkyActivity.this.mVideoView.setCurrentChannelLogo(NSTIJKPlayerSkyActivity.this.currentChannelLogo);
                                        NSTIJKPlayerSkyActivity.this.updateChannelLogo(NSTIJKPlayerSkyActivity.this.currentChannelLogo);
                                        AsyncTask unused9 = NSTIJKPlayerSkyActivity.this.AsyncTaskUpdateEPG = new UpdateEPGAsyncTask(NSTIJKPlayerSkyActivity.this.currentEpgChannelID).execute(new String[0]);
                                        NSTIJKPlayerSkyActivity.this.handlerShowEPG.removeCallbacksAndMessages(null);
                                        NSTIJKPlayerSkyActivity.this.showTitleBar();
                                        if (NSTIJKPlayerSkyActivity.this.loginPrefsEditor != null) {
                                            NSTIJKPlayerSkyActivity.this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getStreamId()));
                                            NSTIJKPlayerSkyActivity.this.loginPrefsEditor.putString("LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_M3U", String.valueOf(((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getUrl()));
                                            NSTIJKPlayerSkyActivity.this.loginPrefsEditor.apply();
                                        }
                                        if (NSTIJKPlayerSkyActivity.this.loginPrefsEditorPosition != null) {
                                            NSTIJKPlayerSkyActivity.this.loginPrefsEditorPosition.putInt(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, i);
                                            NSTIJKPlayerSkyActivity.this.loginPrefsEditorPosition.apply();
                                        }
                                        NSTIJKPlayerSkyActivity.this.adapter.notifyDataSetChanged();
                                        return;
                                    }
                                    NSTIJKPlayerSkyActivity.this.fullScreenVideoLayout();
                                } else if (NSTIJKPlayerSkyActivity.this.currentProgramStreamID != Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getStreamId())) {
                                    NSTIJKPlayerSkyActivity.this.mVideoView.setCurrentWindowIndex(i);
                                    int unused10 = NSTIJKPlayerSkyActivity.this.firstplayedChannelNumber = Utils.parseIntZero(((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getNum());
                                    NSTIJKPlayerSky access$7005 = NSTIJKPlayerSkyActivity.this.mVideoView;
                                    access$7005.setTitle(((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getNum() + " - " + ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getName());
                                    NSTIJKPlayerSkyActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getStreamId());
                                    if (!NSTIJKPlayerSkyActivity.this.catID.equals("-1") && !NSTIJKPlayerSkyActivity.this.catID.equals(AppConst.PASSWORD_UNSET)) {
                                        NSTIJKPlayerSkyActivity.this.catID = ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getCategoryId();
                                        NSTIJKPlayerSkyActivity.this.catName = ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getCategoryName();
                                        if (NSTIJKPlayerSkyActivity.this.catName.isEmpty()) {
                                            NSTIJKPlayerSkyActivity.this.catName = NSTIJKPlayerSkyActivity.this.catName(NSTIJKPlayerSkyActivity.this.catID);
                                        }
                                    }
                                    int unused11 = NSTIJKPlayerSkyActivity.this.opened_vlc_id = i;
                                    NSTIJKPlayerSkyActivity.this.mVideoView.removeHandlerCallback();
                                    if (NSTIJKPlayerSkyActivity.this.rq.booleanValue()) {
                                        NSTIJKPlayerSky access$7006 = NSTIJKPlayerSkyActivity.this.mVideoView;
                                        access$7006.setVideoURI(Uri.parse(NSTIJKPlayerSkyActivity.this.mFilePath + Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getStreamId()) + NSTIJKPlayerSkyActivity.this.allowedFormat), NSTIJKPlayerSkyActivity.this.fullScreen, ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getName());
                                        NSTIJKPlayerSkyActivity.this.mVideoView.retryCount = 0;
                                        NSTIJKPlayerSkyActivity.this.mVideoView.retrying = false;
                                        NSTIJKPlayerSkyActivity.this.mVideoView.start();
                                    }
                                    NSTIJKPlayerSkyActivity.this.handlerUpdateEPGData.removeCallbacksAndMessages(null);
                                    NSTIJKPlayerSkyActivity.this.currentEpgChannelID = epgChannelId2;
                                    NSTIJKPlayerSkyActivity.this.currentChannelLogo = streamIcon3;
                                    NSTIJKPlayerSkyActivity.this.mVideoView.setCurrentEpgChannelID(NSTIJKPlayerSkyActivity.this.currentEpgChannelID);
                                    NSTIJKPlayerSkyActivity.this.mVideoView.setCurrentChannelLogo(NSTIJKPlayerSkyActivity.this.currentChannelLogo);
                                    NSTIJKPlayerSkyActivity.this.updateChannelLogo(NSTIJKPlayerSkyActivity.this.currentChannelLogo);
                                    AsyncTask unused12 = NSTIJKPlayerSkyActivity.this.AsyncTaskUpdateEPG = new UpdateEPGAsyncTask(NSTIJKPlayerSkyActivity.this.currentEpgChannelID).execute(new String[0]);
                                    NSTIJKPlayerSkyActivity.this.handlerShowEPG.removeCallbacksAndMessages(null);
                                    NSTIJKPlayerSkyActivity.this.showTitleBar();
                                    if (NSTIJKPlayerSkyActivity.this.loginPrefsEditor != null) {
                                        NSTIJKPlayerSkyActivity.this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getStreamId()));
                                        NSTIJKPlayerSkyActivity.this.loginPrefsEditor.putString("LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_M3U", String.valueOf(((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i)).getUrl()));
                                        NSTIJKPlayerSkyActivity.this.loginPrefsEditor.apply();
                                    }
                                    if (NSTIJKPlayerSkyActivity.this.loginPrefsEditorPosition != null) {
                                        NSTIJKPlayerSkyActivity.this.loginPrefsEditorPosition.putInt(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, i);
                                        NSTIJKPlayerSkyActivity.this.loginPrefsEditorPosition.apply();
                                    }
                                    NSTIJKPlayerSkyActivity.this.adapter.notifyDataSetChanged();
                                } else {
                                    NSTIJKPlayerSkyActivity.this.fullScreenVideoLayout();
                                }
                            }
                        }
                    });
                    this.listChannels.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass9 */

                        @Override // android.widget.AdapterView.OnItemLongClickListener
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                            final String str;
                            final String str2;
                            final String str3;
                            int i2;
                            int i3 = i;
                            ArrayList<LiveStreamsDBModel> filteredData = NSTIJKPlayerSkyActivity.this.adapter.getFilteredData();
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
                            } else if (NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels == null || NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.size() <= 0) {
                                str = "";
                                str2 = "";
                                str3 = "";
                                i2 = 0;
                            } else {
                                String categoryId2 = ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i3)).getCategoryId();
                                i2 = Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i3)).getStreamId());
                                String name2 = ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i3)).getName();
                                str5 = ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i3)).getUrl();
                                str4 = ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i3)).getNum();
                                str = ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(i3)).getStreamIcon();
                                str2 = name2;
                                str3 = categoryId2;
                            }
                            PopupMenu popupMenu = new PopupMenu(NSTIJKPlayerSkyActivity.this, view);
                            popupMenu.getMenuInflater().inflate(R.menu.menu_players_selection_with_fav, popupMenu.getMenu());
                            if (NSTIJKPlayerSkyActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                ArrayList<FavouriteM3UModel> checkFavourite = NSTIJKPlayerSkyActivity.this.liveStreamDBHandler.checkFavourite(str5, SharepreferenceDBHandler.getUserID(NSTIJKPlayerSkyActivity.this.context));
                                if (checkFavourite == null || checkFavourite.size() <= 0) {
                                    popupMenu.getMenu().getItem(2).setVisible(true);
                                } else {
                                    popupMenu.getMenu().getItem(3).setVisible(true);
                                }
                            } else {
                                ArrayList<FavouriteDBModel> checkFavourite2 = NSTIJKPlayerSkyActivity.this.database.checkFavourite(i2, str3, "live", SharepreferenceDBHandler.getUserID(NSTIJKPlayerSkyActivity.this.context));
                                if (checkFavourite2 == null || checkFavourite2.size() <= 0) {
                                    popupMenu.getMenu().getItem(2).setVisible(true);
                                } else {
                                    popupMenu.getMenu().getItem(3).setVisible(true);
                                }
                            }
                            try {
                                CastSession unused = NSTIJKPlayerSkyActivity.this.mCastSession = CastContext.getSharedInstance(NSTIJKPlayerSkyActivity.this.context).getSessionManager().getCurrentCastSession();
                                if (NSTIJKPlayerSkyActivity.this.mCastSession == null || !NSTIJKPlayerSkyActivity.this.mCastSession.isConnected()) {
                                    popupMenu.getMenu().getItem(5).setVisible(false);
                                } else {
                                    popupMenu.getMenu().getItem(5).setVisible(true);
                                }
                            } catch (Exception unused2) {
                            }
                            NSTIJKPlayerSkyActivity.this.externalPlayerList = new ArrayList<>();
                            ExternalPlayerDataBase externalPlayerDataBase = new ExternalPlayerDataBase(NSTIJKPlayerSkyActivity.this.context);
                            NSTIJKPlayerSkyActivity.this.externalPlayerList = externalPlayerDataBase.getExternalPlayer();
                            try {
                                if (NSTIJKPlayerSkyActivity.this.externalPlayerList != null && NSTIJKPlayerSkyActivity.this.externalPlayerList.size() > 0) {
                                    for (int i4 = 0; i4 < NSTIJKPlayerSkyActivity.this.externalPlayerList.size(); i4++) {
                                        popupMenu.getMenu().add(0, i4, i4, NSTIJKPlayerSkyActivity.this.externalPlayerList.get(i4).getAppname());
                                    }
                                }
                            } catch (Exception unused3) {
                            }
                            NSTIJKPlayerSkyActivity.this.finalStreamID = i2;
                            NSTIJKPlayerSkyActivity.this.finalM3uVideoURL = str5;
                            NSTIJKPlayerSkyActivity.this.streamNameWithUnderscore = str2.replaceAll(" ", "_").toLowerCase();
                            NSTIJKPlayerSkyActivity.this.streamNameWithUnderscore = NSTIJKPlayerSkyActivity.this.streamNameWithUnderscore.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
                            final String str6 = NSTIJKPlayerSkyActivity.this.streamNameWithUnderscore;
                            String finalStr = str4;
                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass9.AnonymousClass1 */
                                final String val$finalStreamName;

                                {
                                    this.val$finalStreamName = str2;
                                }

                                public boolean onMenuItemClick(MenuItem menuItem) {
                                    try {
                                        if (NSTIJKPlayerSkyActivity.this.rq.booleanValue() && NSTIJKPlayerSkyActivity.this.externalPlayerList != null && NSTIJKPlayerSkyActivity.this.externalPlayerList.size() > 0) {
                                            int i = 0;
                                            while (true) {
                                                if (i >= NSTIJKPlayerSkyActivity.this.externalPlayerList.size()) {
                                                    break;
                                                } else if (menuItem.getItemId() == i) {
                                                    NSTIJKPlayerSkyActivity.this.externalPlayerSelected = true;
                                                    NSTIJKPlayerSkyActivity.this.release();
                                                    if (NSTIJKPlayerSkyActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                                        NSTIJKPlayerSkyActivity.this.url = NSTIJKPlayerSkyActivity.this.finalM3uVideoURL;
                                                    } else {
                                                        NSTIJKPlayerSkyActivity.this.url = Utils.getUrlLive(NSTIJKPlayerSkyActivity.this.context, NSTIJKPlayerSkyActivity.this.finalStreamID, NSTIJKPlayerSkyActivity.this.allowedFormat, "live");
                                                    }
                                                    Intent intent = new Intent(NSTIJKPlayerSkyActivity.this.context, PlayExternalPlayerActivity.class);
                                                    intent.putExtra("url", NSTIJKPlayerSkyActivity.this.url);
                                                    intent.putExtra(AppConst.APP_NAME, NSTIJKPlayerSkyActivity.this.externalPlayerList.get(i).getAppname());
                                                    intent.putExtra(AppConst.PACKAGE_NAME, NSTIJKPlayerSkyActivity.this.externalPlayerList.get(i).getPackagename());
                                                    NSTIJKPlayerSkyActivity.this.context.startActivity(intent);
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
                                                        if (NSTIJKPlayerSkyActivity.this.allowedFormat != null && (NSTIJKPlayerSkyActivity.this.allowedFormat.equalsIgnoreCase(".ts") || NSTIJKPlayerSkyActivity.this.allowedFormat.equalsIgnoreCase(""))) {
                                                            Toast.makeText(NSTIJKPlayerSkyActivity.this.context, NSTIJKPlayerSkyActivity.this.getResources().getString(R.string.wraning_cast_on_live), 1).show();
                                                        }
                                                        MediaMetadata mediaMetadata = new MediaMetadata(1);
                                                        if (str2 != null && !str2.isEmpty()) {
                                                            mediaMetadata.putString(MediaMetadata.KEY_TITLE, str2);
                                                        }
                                                        if (str != null && !str.isEmpty()) {
                                                            mediaMetadata.addImage(new WebImage(Uri.parse(str)));
                                                        }
                                                        String str = NSTIJKPlayerSkyActivity.this.mFilePath + NSTIJKPlayerSkyActivity.this.finalStreamID + NSTIJKPlayerSkyActivity.this.allowedFormat;
                                                        if (NSTIJKPlayerSkyActivity.this.mVideoView != null) {
                                                            NSTIJKPlayerSkyActivity.this.release();
                                                            NSTIJKPlayerSkyActivity.this.externalPlayerSelected = true;
                                                        }
                                                        if (NSTIJKPlayerSkyActivity.this.rq.booleanValue()) {
                                                            ChromeCastUtilClass.castHLS(NSTIJKPlayerSkyActivity.this.mHandler, NSTIJKPlayerSkyActivity.this.mCastSession.getRemoteMediaClient(), str, mediaMetadata, NSTIJKPlayerSkyActivity.this.context);
                                                        }
                                                    } catch (Exception unused2) {
                                                    }
                                                }
                                            } else if (NSTIJKPlayerSkyActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                                NSTIJKPlayerSkyActivity.this.removeFromFavouriteM3U(NSTIJKPlayerSkyActivity.this.finalM3uVideoURL, this.val$finalStreamName);
                                            } else {
                                                NSTIJKPlayerSkyActivity.this.removeFromFavourite(str3, NSTIJKPlayerSkyActivity.this.finalStreamID, this.val$finalStreamName);
                                            }
                                        } else if (NSTIJKPlayerSkyActivity.this.isStoragePermissionGranted()) {
                                            SharedPreferences unused3 = NSTIJKPlayerSkyActivity.loginPreferencesDownloadStatus = NSTIJKPlayerSkyActivity.this.getSharedPreferences(AppConst.LOGIN_PREF_DOWNLOAD_STATUS, 0);
                                            String string = NSTIJKPlayerSkyActivity.loginPreferencesDownloadStatus.getString(AppConst.LOGIN_PREF_DOWNLOAD_STATUS, "");
                                            Utils utils = new Utils();
                                            if (string.equals("processing")) {
                                                utils.showDownloadRunningPopUP(NSTIJKPlayerSkyActivity.this);
                                            } else {
                                                utils.showRecordingPopUP(NSTIJKPlayerSkyActivity.this, str6, NSTIJKPlayerSkyActivity.this.allowedFormat, NSTIJKPlayerSkyActivity.this.mFilePath, NSTIJKPlayerSkyActivity.this.finalStreamID, NSTIJKPlayerSkyActivity.this.finalM3uVideoURL);
                                            }
                                        }
                                    } else if (NSTIJKPlayerSkyActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                        NSTIJKPlayerSkyActivity.this.addToFavouriteM3U(str3, NSTIJKPlayerSkyActivity.this.finalM3uVideoURL, this.val$finalStreamName);
                                    } else {
                                        NSTIJKPlayerSkyActivity.this.addToFavourite(str3, NSTIJKPlayerSkyActivity.this.finalStreamID, this.val$finalStreamName, finalStr);
                                    }
                                    return false;
                                }
                            });
                            popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass9.AnonymousClass2 */

                                public void onDismiss(PopupMenu popupMenu) {
                                }
                            });
                            popupMenu.show();
                            return true;
                        }
                    });
                    this.et_search.addTextChangedListener(new TextWatcher() {
                        /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass10 */

                        public void afterTextChanged(Editable editable) {
                        }

                        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        }

                        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                            if (NSTIJKPlayerSkyActivity.this.adapter != null) {
                                NSTIJKPlayerSkyActivity.this.adapter.getFilter().filter(charSequence.toString());
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
        this.AsyncTaskUpdateEPG = new UpdateEPGAsyncTask(str).execute(new String[0]);
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

    public void hideEPGData() {
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

    public void loadingEPGData() {
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
                playFirstTime(this.liveListDetailAvailableChannels);
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
            checkIfAsyncTaskRunning();
            release();
            CastContext.getSharedInstance(this.context).getSessionManager().removeSessionManagerListener(this.mSessionManagerListener, CastSession.class);
        } catch (Exception unused) {
        }
    }

    public void release() {
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

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0012 */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onStop() {
        super.onStop();
        release();
        if (this.mVideoView != null) {
            this.externalPlayerSelected = true;
            this.mVideoView.pause();
        }
        try {
            CastContext.getSharedInstance(this.context).getSessionManager().removeSessionManagerListener(this.mSessionManagerListener, CastSession.class);
        } catch (Exception unused) {
        }
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

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
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x000a
            r0 = 1
            goto L_0x000b
        L_0x000a:
            r0 = 0
        L_0x000b:
            r3 = 62
            if (r6 == r3) goto L_0x01b4
            r3 = 66
            r4 = 0
            if (r6 == r3) goto L_0x0165
            r3 = 79
            if (r6 == r3) goto L_0x01b4
            r3 = 82
            if (r6 == r3) goto L_0x0158
            switch(r6) {
                case 7: goto L_0x00c9;
                case 8: goto L_0x00c9;
                case 9: goto L_0x00c9;
                case 10: goto L_0x00c9;
                case 11: goto L_0x00c9;
                case 12: goto L_0x00c9;
                case 13: goto L_0x00c9;
                case 14: goto L_0x00c9;
                case 15: goto L_0x00c9;
                case 16: goto L_0x00c9;
                default: goto L_0x001f;
            }
        L_0x001f:
            switch(r6) {
                case 19: goto L_0x00ba;
                case 20: goto L_0x00a7;
                case 21: goto L_0x0093;
                case 22: goto L_0x007f;
                case 23: goto L_0x0165;
                default: goto L_0x0022;
            }
        L_0x0022:
            switch(r6) {
                case 85: goto L_0x01b4;
                case 86: goto L_0x0056;
                default: goto L_0x0025;
            }
        L_0x0025:
            switch(r6) {
                case 126: goto L_0x002d;
                case 127: goto L_0x0056;
                default: goto L_0x0028;
            }
        L_0x0028:
            boolean r6 = super.onKeyUp(r6, r7)
            return r6
        L_0x002d:
            java.lang.Boolean r6 = r5.rq
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x0055
            if (r0 == 0) goto L_0x0055
            com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSky r6 = r5.mVideoView
            boolean r6 = r6.isPlaying()
            if (r6 != 0) goto L_0x0055
            r5.stopHeaderFooterHandler()
            r5.showTitleBarAndFooter()
            r5.runHeaderFooterHandler()
            com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSky r6 = r5.mVideoView
            r6.start()
            r5.playerStartIconsUpdate()
            android.view.View r6 = r5.vlcpauseButton
            r6.requestFocus()
        L_0x0055:
            return r2
        L_0x0056:
            java.lang.Boolean r6 = r5.rq
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x007e
            if (r0 == 0) goto L_0x007e
            com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSky r6 = r5.mVideoView
            boolean r6 = r6.isPlaying()
            if (r6 == 0) goto L_0x007e
            r5.stopHeaderFooterHandler()
            r5.showTitleBarAndFooter()
            r5.runHeaderFooterHandler()
            com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSky r6 = r5.mVideoView
            r6.pause()
            r5.playerPauseIconsUpdate()
            android.view.View r6 = r5.vlcplayButton
            r6.requestFocus()
        L_0x007e:
            return r2
        L_0x007f:
            boolean r6 = r5.outfromtoolbar
            if (r6 != 0) goto L_0x0084
            goto L_0x0092
        L_0x0084:
            boolean r6 = r5.fullScreen
            if (r6 == 0) goto L_0x008f
            r5.stopHeaderFooterHandler()
            r5.runHeaderFooterHandler()
            goto L_0x0092
        L_0x008f:
            r5.nextbutton()
        L_0x0092:
            return r2
        L_0x0093:
            boolean r6 = r5.outfromtoolbar
            if (r6 != 0) goto L_0x0098
            goto L_0x00a6
        L_0x0098:
            boolean r6 = r5.fullScreen
            if (r6 == 0) goto L_0x00a3
            r5.stopHeaderFooterHandler()
            r5.runHeaderFooterHandler()
            goto L_0x00a6
        L_0x00a3:
            r5.backbutton()
        L_0x00a6:
            return r2
        L_0x00a7:
            boolean r6 = r5.fullScreen
            if (r6 != 0) goto L_0x00b9
            boolean r6 = r5.outfromtoolbar
            if (r6 != 0) goto L_0x00b2
            r5.hideToobar()
        L_0x00b2:
            r5.outfromtoolbar = r2
            android.widget.ListView r6 = r5.listChannels
            r6.requestFocus()
        L_0x00b9:
            return r2
        L_0x00ba:
            boolean r6 = r5.fullScreen
            if (r6 != 0) goto L_0x00c8
            android.support.v7.widget.Toolbar r6 = r5.toolbar
            boolean r6 = r6.hasFocus()
            if (r6 == 0) goto L_0x00c8
            r5.outfromtoolbar = r1
        L_0x00c8:
            return r2
        L_0x00c9:
            boolean r7 = r5.fullScreen
            if (r7 != 0) goto L_0x00cf
            goto L_0x0157
        L_0x00cf:
            android.os.Handler r7 = r5.handlerJumpChannel
            r7.removeCallbacksAndMessages(r4)
            r7 = 7
            if (r6 != r7) goto L_0x00dd
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r6.append(r1)
            goto L_0x013b
        L_0x00dd:
            r0 = 8
            if (r6 != r0) goto L_0x00e7
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r6.append(r2)
            goto L_0x013b
        L_0x00e7:
            r3 = 9
            if (r6 != r3) goto L_0x00f2
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r7 = 2
            r6.append(r7)
            goto L_0x013b
        L_0x00f2:
            r4 = 10
            if (r6 != r4) goto L_0x00fd
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r7 = 3
            r6.append(r7)
            goto L_0x013b
        L_0x00fd:
            r4 = 11
            if (r6 != r4) goto L_0x0108
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r7 = 4
            r6.append(r7)
            goto L_0x013b
        L_0x0108:
            r4 = 12
            if (r6 != r4) goto L_0x0113
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r7 = 5
            r6.append(r7)
            goto L_0x013b
        L_0x0113:
            r4 = 13
            if (r6 != r4) goto L_0x011e
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r7 = 6
            r6.append(r7)
            goto L_0x013b
        L_0x011e:
            r4 = 14
            if (r6 != r4) goto L_0x0128
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r6.append(r7)
            goto L_0x013b
        L_0x0128:
            r7 = 15
            if (r6 != r7) goto L_0x0132
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r6.append(r0)
            goto L_0x013b
        L_0x0132:
            r7 = 16
            if (r6 != r7) goto L_0x013b
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r6.append(r3)
        L_0x013b:
            android.widget.TextView r6 = r5.app_channel_jumping_text
            java.lang.StringBuilder r7 = r5.jumpToChannel
            java.lang.String r7 = r7.toString()
            r6.setText(r7)
            android.widget.LinearLayout r6 = r5.ll_channel_jumping
            r6.setVisibility(r1)
            android.os.Handler r6 = r5.handlerJumpChannel
            com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity$11 r7 = new com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity$11
            r7.<init>()
            r0 = 3000(0xbb8, double:1.482E-320)
            r6.postDelayed(r7, r0)
        L_0x0157:
            return r2
        L_0x0158:
            android.view.Menu r6 = r5.menuSelect
            if (r6 == 0) goto L_0x0164
            android.view.Menu r6 = r5.menuSelect
            r7 = 2131362161(0x7f0a0171, float:1.8344095E38)
            r6.performIdentifierAction(r7, r1)
        L_0x0164:
            return r2
        L_0x0165:
            boolean r6 = r5.fullScreen
            if (r6 != 0) goto L_0x016a
            goto L_0x01b3
        L_0x016a:
            r6 = 2131361935(0x7f0a008f, float:1.8343636E38)
            android.view.View r6 = r5.findViewById(r6)
            int r6 = r6.getVisibility()
            if (r6 != 0) goto L_0x017b
            r5.hideTitleBarAndFooter()
            goto L_0x01b3
        L_0x017b:
            r5.stopHeaderFooterHandler()
            r5.showTitleBarAndFooter()
            java.lang.String r6 = r5.currentChannelLogo
            if (r6 == 0) goto L_0x018a
            java.lang.String r6 = r5.currentChannelLogo
            r5.updateChannelLogo(r6)
        L_0x018a:
            java.lang.String r6 = r5.currentEpgChannelID
            if (r6 == 0) goto L_0x019d
            com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity$UpdateEPGAsyncTask r6 = new com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity$UpdateEPGAsyncTask
            java.lang.String r7 = r5.currentEpgChannelID
            r6.<init>(r7)
            java.lang.String[] r7 = new java.lang.String[r1]
            android.os.AsyncTask r6 = r6.execute(r7)
            r5.AsyncTaskUpdateEPG = r6
        L_0x019d:
            r5.runHeaderFooterHandler()
            com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSky r6 = r5.mVideoView
            boolean r6 = r6.isPlaying()
            if (r6 == 0) goto L_0x01ae
            android.view.View r6 = r5.vlcpauseButton
            r6.requestFocus()
            goto L_0x01b3
        L_0x01ae:
            android.view.View r6 = r5.vlcplayButton
            r6.requestFocus()
        L_0x01b3:
            return r2
        L_0x01b4:
            java.lang.Boolean r6 = r5.rq
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x01f3
            if (r0 == 0) goto L_0x01dd
            com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSky r6 = r5.mVideoView
            boolean r6 = r6.isPlaying()
            if (r6 != 0) goto L_0x01dd
            r5.stopHeaderFooterHandler()
            r5.showTitleBarAndFooter()
            r5.runHeaderFooterHandler()
            com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSky r6 = r5.mVideoView
            r6.start()
            r5.playerStartIconsUpdate()
            android.view.View r6 = r5.vlcpauseButton
            r6.requestFocus()
            goto L_0x01f3
        L_0x01dd:
            r5.stopHeaderFooterHandler()
            r5.showTitleBarAndFooter()
            r5.runHeaderFooterHandler()
            com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerSky r6 = r5.mVideoView
            r6.pause()
            r5.playerPauseIconsUpdate()
            android.view.View r6 = r5.vlcplayButton
            r6.requestFocus()
        L_0x01f3:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.onKeyUp(int, android.view.KeyEvent):boolean");
    }

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        boolean z = keyEvent.getAction() == 0;
        if (keyCode == 82) {
            if (!z) {
                return onKeyUp(keyCode, keyEvent);
            }
            try {
                return onKeyDown(keyCode, keyEvent);
            } catch (Exception unused) {
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.support.v7.app.AppCompatActivity
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        keyEvent.getRepeatCount();
        keyEvent.getAction();
        if (this.fullScreen) {
            if (i == 20) {
                if (this.rq.booleanValue()) {
                    stopHeaderFooterHandler();
                    showTitleBarAndFooter();
                    runHeaderFooterHandler();
                    findViewById(R.id.exo_prev).performClick();
                }
                return true;
            } else if (i == 19) {
                if (this.rq.booleanValue()) {
                    stopHeaderFooterHandler();
                    showTitleBarAndFooter();
                    runHeaderFooterHandler();
                    findViewById(R.id.exo_next).performClick();
                }
                return true;
            }
        }
        switch (i) {
            case 166:
                if (this.fullScreen && this.rq.booleanValue()) {
                    stopHeaderFooterHandler();
                    showTitleBarAndFooter();
                    runHeaderFooterHandler();
                    findViewById(R.id.exo_next).performClick();
                }
                return true;
            case 167:
                if (this.fullScreen && this.rq.booleanValue()) {
                    stopHeaderFooterHandler();
                    showTitleBarAndFooter();
                    runHeaderFooterHandler();
                    findViewById(R.id.exo_prev).performClick();
                }
                return true;
            default:
                return super.onKeyDown(i, keyEvent);
        }
    }

    public void runHeaderFooterHandler() {
        this.handlerHeaderFooter.postDelayed(new Runnable() {
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass12 */

            public void run() {
                NSTIJKPlayerSkyActivity.this.hideTitleBarAndFooter();
                if (AppConst.WATER_MARK.booleanValue()) {
                    NSTIJKPlayerSkyActivity.this.findViewById(R.id.watrmrk).setVisibility(0);
                }
            }
        }, 7000);
    }

    public void stopHeaderFooterHandler() {
        this.handlerHeaderFooter.removeCallbacksAndMessages(null);
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
                        String title2 = epg.get(i).getTitle();
                        Long valueOf = Long.valueOf(Utils.epgTimeConverter(start));
                        Long valueOf2 = Long.valueOf(Utils.epgTimeConverter(stop));
                        if (Utils.isEventVisible(valueOf.longValue(), valueOf2.longValue(), this.context)) {
                            int percentageLeft = Utils.getPercentageLeft(valueOf.longValue(), valueOf2.longValue(), this.context);
                            if (!(percentageLeft == 0 || (percentageLeft = 100 - percentageLeft) == 0 || title2 == null || title2.equals(""))) {
                                arrayList4.add(0, String.valueOf(percentageLeft));
                                arrayList.add(0, this.context.getResources().getString(R.string.now) + " " + title2);
                                arrayList.add(1, this.programTimeFormat.format(valueOf) + " - " + this.programTimeFormat.format(valueOf2));
                            }
                            arrayList2.add(0, this.programTimeFormat.format(valueOf) + " - " + this.programTimeFormat.format(valueOf2));
                            arrayList2.add(1, title2);
                            int i2 = i + 1;
                            if (i2 < epg.size()) {
                                String start2 = epg.get(i2).getStart();
                                String stop2 = epg.get(i2).getStop();
                                String title3 = epg.get(i2).getTitle();
                                Long valueOf3 = Long.valueOf(Utils.epgTimeConverter(start2));
                                Long valueOf4 = Long.valueOf(Utils.epgTimeConverter(stop2));
                                if (!(percentageLeft == 0 || 100 - percentageLeft == 0 || title2 == null || title2.equals(""))) {
                                    arrayList.add(2, this.context.getResources().getString(R.string.next) + " " + title3);
                                    arrayList.add(3, this.programTimeFormat.format(valueOf3) + " - " + this.programTimeFormat.format(valueOf4));
                                }
                                arrayList2.add(2, this.programTimeFormat.format(valueOf3) + " - " + this.programTimeFormat.format(valueOf4));
                                arrayList2.add(3, title3);
                            }
                            int i3 = i + 2;
                            if (i3 < epg.size()) {
                                String start3 = epg.get(i3).getStart();
                                String stop3 = epg.get(i3).getStop();
                                String title4 = epg.get(i3).getTitle();
                                Long valueOf5 = Long.valueOf(Utils.epgTimeConverter(start3));
                                Long valueOf6 = Long.valueOf(Utils.epgTimeConverter(stop3));
                                arrayList2.add(4, this.programTimeFormat.format(valueOf5) + " - " + this.programTimeFormat.format(valueOf6));
                                arrayList2.add(5, title4);
                            }
                            int i4 = i + 3;
                            if (i4 < epg.size()) {
                                String start4 = epg.get(i4).getStart();
                                String stop4 = epg.get(i4).getStop();
                                String title5 = epg.get(i4).getTitle();
                                Long valueOf7 = Long.valueOf(Utils.epgTimeConverter(start4));
                                Long valueOf8 = Long.valueOf(Utils.epgTimeConverter(stop4));
                                arrayList2.add(6, this.programTimeFormat.format(valueOf7) + " - " + this.programTimeFormat.format(valueOf8));
                                arrayList2.add(7, title5);
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

    private void playerStartIconsUpdate() {
        this.vlcplayButton.setVisibility(8);
        this.vlcpauseButton.setVisibility(0);
    }

    private void playerPauseIconsUpdate() {
        this.vlcpauseButton.setVisibility(8);
        this.vlcplayButton.setVisibility(0);
    }

    public void showTitleBarAndFooter() {
        findViewById(R.id.app_video_top_box).setVisibility(0);
        findViewById(R.id.controls).setVisibility(0);
        findViewById(R.id.ll_seekbar_time).setVisibility(0);
        if (AppConst.WATER_MARK.booleanValue()) {
            findViewById(R.id.watrmrk).setVisibility(0);
        }
    }

    public void showTitleBar() {
        findViewById(R.id.ll_seekbar_time).setVisibility(8);
        if (this.fullScreen) {
            findViewById(R.id.app_video_top_box).setVisibility(0);
        }
        if (AppConst.WATER_MARK.booleanValue()) {
            findViewById(R.id.watrmrk).setVisibility(0);
        }
    }

    public void hideTitleBarAndFooter() {
        findViewById(R.id.app_video_top_box).setVisibility(8);
        findViewById(R.id.controls).setVisibility(8);
        findViewById(R.id.ll_seekbar_time).setVisibility(8);
        if (AppConst.WATER_MARK.booleanValue()) {
            findViewById(R.id.watrmrk).setVisibility(0);
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.content.Intent.putExtra(java.lang.String, boolean):android.content.Intent}
     arg types: [java.lang.String, int]
     candidates:
      ClspMth{android.content.Intent.putExtra(java.lang.String, int):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.String[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, int[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, double):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, char):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, boolean[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, byte):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, android.os.Bundle):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, float):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.CharSequence[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.CharSequence):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, long[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, long):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, short):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, android.os.Parcelable[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.io.Serializable):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, double[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, android.os.Parcelable):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, float[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, byte[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.String):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, short[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, char[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, boolean):android.content.Intent} */
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
                    if (findViewById(R.id.controls).getVisibility() == 0) {
                        stopHeaderFooterHandler();
                        showTitleBarAndFooter();
                        runHeaderFooterHandler();
                        if (this.mVideoView != null) {
                            this.mVideoView.toggleAspectRatio();
                            return;
                        }
                        return;
                    }
                    stopHeaderFooterHandler();
                    showTitleBarAndFooter();
                    runHeaderFooterHandler();
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
            case R.id.btn_list:
                try {
                    if (this.appbarToolbar != null) {
                        toggleView(this.appbarToolbar);
                        this.appbarToolbar.requestFocusFromTouch();
                        return;
                    }
                    return;
                } catch (Exception e4) {
                    Log.e("NSTIJPLAYER", "exection " + e4);
                    return;
                }
            case R.id.exo_decoder_hw:
                try {
                    if (findViewById(R.id.controls).getVisibility() == 0) {
                        this.decoder_sw.setVisibility(0);
                        this.decoder_hw.setVisibility(8);
                        if (this.mVideoView != null) {
                            this.decoder_sw.requestFocus();
                            this.loginPreferencesMediaCodec = getSharedPreferences(AppConst.LOGIN_PREF_MEDIA_CODEC, 0);
                            this.loginPrefsEditorMediaCodec = this.loginPreferencesMediaCodec.edit();
                            if (this.loginPrefsEditorMediaCodec != null) {
                                this.loginPrefsEditorMediaCodec.putString(AppConst.LOGIN_PREF_MEDIA_CODEC, this.context.getResources().getString(R.string.software_decoder));
                                this.loginPrefsEditorMediaCodec.apply();
                            }
                            this.mVideoView.openVideo();
                            this.mVideoView.start();
                            stopHeaderFooterHandler();
                            showTitleBarAndFooter();
                            runHeaderFooterHandler();
                            return;
                        }
                        return;
                    }
                    stopHeaderFooterHandler();
                    showTitleBarAndFooter();
                    runHeaderFooterHandler();
                    return;
                } catch (Exception e5) {
                    Log.e("NSTIJPLAYER", "exection " + e5);
                    return;
                }
            case R.id.exo_decoder_sw:
                try {
                    if (findViewById(R.id.controls).getVisibility() == 0) {
                        this.decoder_hw.setVisibility(0);
                        this.decoder_sw.setVisibility(8);
                        if (this.mVideoView != null) {
                            this.decoder_hw.requestFocus();
                            this.loginPreferencesMediaCodec = getSharedPreferences(AppConst.LOGIN_PREF_MEDIA_CODEC, 0);
                            this.loginPrefsEditorMediaCodec = this.loginPreferencesMediaCodec.edit();
                            if (this.loginPrefsEditorMediaCodec != null) {
                                this.loginPrefsEditorMediaCodec.putString(AppConst.LOGIN_PREF_MEDIA_CODEC, this.context.getResources().getString(R.string.hardware_decoder));
                                this.loginPrefsEditorMediaCodec.apply();
                            }
                            this.mVideoView.openVideo();
                            this.mVideoView.start();
                            stopHeaderFooterHandler();
                            showTitleBarAndFooter();
                            runHeaderFooterHandler();
                            return;
                        }
                        return;
                    }
                    stopHeaderFooterHandler();
                    showTitleBarAndFooter();
                    runHeaderFooterHandler();
                    return;
                } catch (Exception e6) {
                    Log.e("NSTIJPLAYER", "exection " + e6);
                    return;
                }
            case R.id.exo_info:
                try {
                    if (findViewById(R.id.controls).getVisibility() != 0) {
                        stopHeaderFooterHandler();
                        showTitleBarAndFooter();
                        runHeaderFooterHandler();
                        return;
                    } else if (this.mVideoView != null) {
                        this.mVideoView.showMediaInfo();
                        return;
                    } else {
                        return;
                    }
                } catch (Exception e7) {
                    Log.e("NSTIJPLAYER", "exection " + e7);
                    return;
                }
            case R.id.exo_multiplayer:
                if (!this.no_channel_found) {
                    release();
                    if (!this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                        if (this.currentProgramStreamID != -1 && this.rq.booleanValue()) {
                            String urlLive = Utils.getUrlLive(this.context, this.currentProgramStreamID, this.allowedFormat, "live");
                            Intent intent = new Intent(this.context, NSTIJKPlayerMultiActivity.class);
                            intent.putExtra("url", urlLive);
                            intent.putExtra("Multiplayedwfromlive", true);
                            intent.putExtra("CHANNEL_NUM", this.firstplayedChannelNumber);
                            this.context.startActivity(intent);
                            break;
                        }
                    } else if (this.rq.booleanValue()) {
                        Intent intent2 = new Intent(this.context, NSTIJKPlayerMultiActivity.class);
                        intent2.putExtra("url", this.currentProgramM3UURL);
                        intent2.putExtra("CHANNEL_NUM", this.firstplayedChannelNumber);
                        this.context.startActivity(intent2);
                        break;
                    }
                }
                break;
            case R.id.exo_next:
                try {
                    if (findViewById(R.id.controls).getVisibility() == 0) {
                        if (this.AsyncTaskUpdateEPG != null && this.AsyncTaskUpdateEPG.getStatus().equals(AsyncTask.Status.RUNNING)) {
                            this.AsyncTaskUpdateEPG.cancel(true);
                        }
                        stopHeaderFooterHandler();
                        showTitleBarAndFooter();
                        runHeaderFooterHandler();
                        if (this.mVideoView != null) {
                            this.vlcnextButton.requestFocus();
                            this.handler.removeCallbacksAndMessages(null);
                            this.channelZapped = true;
                            next();
                            final int currentWindowIndex = this.mVideoView.getCurrentWindowIndex();
                            if (this.liveListDetailAvailableChannels != null && this.liveListDetailAvailableChannels.size() > 1 && currentWindowIndex <= this.liveListDetailAvailableChannels.size() - 1 && currentWindowIndex > -1) {
                                final String name = this.liveListDetailAvailableChannels.get(currentWindowIndex).getName();
                                final String url2 = this.liveListDetailAvailableChannels.get(currentWindowIndex).getUrl();
                                this.currentProgramM3UURL = url2;
                                String num = this.liveListDetailAvailableChannels.get(currentWindowIndex).getNum();
                                this.firstplayedChannelNumber = Utils.parseIntZero(this.liveListDetailAvailableChannels.get(currentWindowIndex).getNum());
                                String streamIcon = this.liveListDetailAvailableChannels.get(currentWindowIndex).getStreamIcon();
                                if (streamIcon.equals("") || streamIcon.isEmpty()) {
                                    this.channelLogo.setImageDrawable(this.context.getResources().getDrawable(R.drawable.logo_placeholder_white));
                                } else {
                                    Picasso.with(this.context).load(streamIcon).placeholder((int) R.drawable.logo_placeholder_white).error((int) R.drawable.logo_placeholder_white).resize(80, 55).into(this.channelLogo);
                                }
                                NSTIJKPlayerSky nSTIJKPlayerSky = this.mVideoView;
                                nSTIJKPlayerSky.setTitle(num + " - " + name);
                                this.opened_vlc_id = currentWindowIndex;
                                this.loginPrefsEditorAudio.clear();
                                this.loginPrefsEditorAudio.apply();
                                this.loginPrefsEditorVideo.clear();
                                this.loginPrefsEditorVideo.apply();
                                this.loginPrefsEditorSubtitle.clear();
                                this.loginPrefsEditorSubtitle.apply();
                                if (this.rq.booleanValue()) {
                                    this.handler.postDelayed(new Runnable() {
                                        /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass14 */

                                        public void run() {
                                            if (NSTIJKPlayerSkyActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                                NSTIJKPlayerSkyActivity.this.mVideoView.setVideoURI(Uri.parse(url2), NSTIJKPlayerSkyActivity.this.fullScreen, name);
                                            } else {
                                                NSTIJKPlayerSky access$700 = NSTIJKPlayerSkyActivity.this.mVideoView;
                                                access$700.setVideoURI(Uri.parse(NSTIJKPlayerSkyActivity.this.mFilePath + Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex)).getStreamId()) + NSTIJKPlayerSkyActivity.this.allowedFormat), NSTIJKPlayerSkyActivity.this.fullScreen, name);
                                            }
                                            NSTIJKPlayerSkyActivity.this.mVideoView.retryCount = 0;
                                            NSTIJKPlayerSkyActivity.this.mVideoView.retrying = false;
                                            NSTIJKPlayerSkyActivity.this.mVideoView.start();
                                            NSTIJKPlayerSkyActivity.this.handlerUpdateEPGData.removeCallbacksAndMessages(null);
                                            NSTIJKPlayerSkyActivity.this.currentEpgChannelID = ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex)).getEpgChannelId();
                                            NSTIJKPlayerSkyActivity.this.currentChannelLogo = ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex)).getStreamIcon();
                                            NSTIJKPlayerSkyActivity.this.mVideoView.setCurrentEpgChannelID(NSTIJKPlayerSkyActivity.this.currentEpgChannelID);
                                            NSTIJKPlayerSkyActivity.this.mVideoView.setCurrentChannelLogo(NSTIJKPlayerSkyActivity.this.currentChannelLogo);
                                            NSTIJKPlayerSkyActivity.this.updateChannelLogo(NSTIJKPlayerSkyActivity.this.currentChannelLogo);
                                            AsyncTask unused = NSTIJKPlayerSkyActivity.this.AsyncTaskUpdateEPG = new UpdateEPGAsyncTask(NSTIJKPlayerSkyActivity.this.currentEpgChannelID).execute(new String[0]);
                                        }
                                    }, 200);
                                }
                                this.handlerShowEPG.removeCallbacksAndMessages(null);
                                this.currentProgramStreamID = Utils.parseIntMinusOne(this.liveListDetailAvailableChannels.get(currentWindowIndex).getStreamId());
                                if (this.loginPrefsEditor != null) {
                                    this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableChannels.get(currentWindowIndex).getStreamId()));
                                    this.loginPrefsEditor.putString("LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_M3U", this.liveListDetailAvailableChannels.get(currentWindowIndex).getUrl());
                                    this.loginPrefsEditor.apply();
                                }
                                if (this.loginPrefsEditorPosition != null) {
                                    this.loginPrefsEditorPosition.putInt(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, currentWindowIndex);
                                    this.loginPrefsEditorPosition.apply();
                                }
                                if (this.positionToSelectAfterJumping != 0) {
                                    this.positionToSelectAfterJumping = currentWindowIndex;
                                }
                                this.adapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    stopHeaderFooterHandler();
                    showTitleBarAndFooter();
                    runHeaderFooterHandler();
                    return;
                } catch (Exception e8) {
                    Log.e("NSTIJPLAYER", "exection " + e8);
                    return;
                }
            case R.id.exo_pause:
                try {
                    if (findViewById(R.id.controls).getVisibility() != 0) {
                        stopHeaderFooterHandler();
                        showTitleBarAndFooter();
                        runHeaderFooterHandler();
                        return;
                    } else if (this.rq.booleanValue()) {
                        stopHeaderFooterHandler();
                        showTitleBarAndFooter();
                        runHeaderFooterHandler();
                        if (this.vlcpauseButton != null) {
                            this.mVideoView.pause();
                            playerPauseIconsUpdate();
                            this.vlcplayButton.requestFocus();
                            return;
                        }
                        return;
                    } else {
                        return;
                    }
                } catch (Exception e9) {
                    Log.e("NSTIJPLAYERACTIVTY", "exection " + e9);
                    return;
                }
            case R.id.exo_play:
                break;
            case R.id.exo_prev:
                try {
                    if (findViewById(R.id.controls).getVisibility() == 0) {
                        if (this.AsyncTaskUpdateEPG != null && this.AsyncTaskUpdateEPG.getStatus().equals(AsyncTask.Status.RUNNING)) {
                            this.AsyncTaskUpdateEPG.cancel(true);
                        }
                        stopHeaderFooterHandler();
                        showTitleBarAndFooter();
                        runHeaderFooterHandler();
                        if (this.mVideoView != null) {
                            this.vlcprevButton.requestFocus();
                            this.handler.removeCallbacksAndMessages(null);
                            previous();
                            this.channelZapped = true;
                            final int currentWindowIndex2 = this.mVideoView.getCurrentWindowIndex();
                            if (this.liveListDetailAvailableChannels != null && this.liveListDetailAvailableChannels.size() > 1 && currentWindowIndex2 <= this.liveListDetailAvailableChannels.size() - 1 && currentWindowIndex2 > -1) {
                                final String name2 = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getName();
                                String num2 = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getNum();
                                this.firstplayedChannelNumber = Utils.parseIntZero(this.liveListDetailAvailableChannels.get(currentWindowIndex2).getNum());
                                final String url3 = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getUrl();
                                this.currentProgramM3UURL = url3;
                                String streamIcon2 = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getStreamIcon();
                                if (streamIcon2.equals("") || streamIcon2.isEmpty()) {
                                    this.channelLogo.setImageDrawable(this.context.getResources().getDrawable(R.drawable.logo_placeholder_white));
                                } else {
                                    Picasso.with(this.context).load(streamIcon2).placeholder((int) R.drawable.logo_placeholder_white).error((int) R.drawable.logo_placeholder_white).resize(80, 55).into(this.channelLogo);
                                }
                                NSTIJKPlayerSky nSTIJKPlayerSky2 = this.mVideoView;
                                nSTIJKPlayerSky2.setTitle(num2 + " - " + name2);
                                this.opened_vlc_id = currentWindowIndex2;
                                this.loginPrefsEditorAudio.clear();
                                this.loginPrefsEditorAudio.apply();
                                this.loginPrefsEditorVideo.clear();
                                this.loginPrefsEditorVideo.apply();
                                this.loginPrefsEditorSubtitle.clear();
                                this.loginPrefsEditorSubtitle.apply();
                                if (this.rq.booleanValue()) {
                                    this.handler.postDelayed(new Runnable() {
                                        /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass13 */

                                        public void run() {
                                            if (NSTIJKPlayerSkyActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                                NSTIJKPlayerSkyActivity.this.mVideoView.setVideoURI(Uri.parse(url3), NSTIJKPlayerSkyActivity.this.fullScreen, name2);
                                            } else {
                                                NSTIJKPlayerSky access$700 = NSTIJKPlayerSkyActivity.this.mVideoView;
                                                access$700.setVideoURI(Uri.parse(NSTIJKPlayerSkyActivity.this.mFilePath + Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId()) + NSTIJKPlayerSkyActivity.this.allowedFormat), NSTIJKPlayerSkyActivity.this.fullScreen, name2);
                                            }
                                            NSTIJKPlayerSkyActivity.this.mVideoView.retryCount = 0;
                                            NSTIJKPlayerSkyActivity.this.mVideoView.retrying = false;
                                            NSTIJKPlayerSkyActivity.this.mVideoView.start();
                                            NSTIJKPlayerSkyActivity.this.handlerUpdateEPGData.removeCallbacksAndMessages(null);
                                            NSTIJKPlayerSkyActivity.this.currentEpgChannelID = ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getEpgChannelId();
                                            NSTIJKPlayerSkyActivity.this.currentChannelLogo = ((LiveStreamsDBModel) NSTIJKPlayerSkyActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamIcon();
                                            NSTIJKPlayerSkyActivity.this.mVideoView.setCurrentEpgChannelID(NSTIJKPlayerSkyActivity.this.currentEpgChannelID);
                                            NSTIJKPlayerSkyActivity.this.mVideoView.setCurrentChannelLogo(NSTIJKPlayerSkyActivity.this.currentChannelLogo);
                                            NSTIJKPlayerSkyActivity.this.updateChannelLogo(NSTIJKPlayerSkyActivity.this.currentChannelLogo);
                                            AsyncTask unused = NSTIJKPlayerSkyActivity.this.AsyncTaskUpdateEPG = new UpdateEPGAsyncTask(NSTIJKPlayerSkyActivity.this.currentEpgChannelID).execute(new String[0]);
                                        }
                                    }, 200);
                                }
                                this.handlerShowEPG.removeCallbacksAndMessages(null);
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
                                this.adapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    stopHeaderFooterHandler();
                    showTitleBarAndFooter();
                    runHeaderFooterHandler();
                    return;
                } catch (Exception e10) {
                    Log.e("NSTIJPLAYERskyACTIVTY", "exection " + e10);
                    return;
                }
            case R.id.exo_subtitle:
                try {
                    if (findViewById(R.id.controls).getVisibility() == 0) {
                        stopHeaderFooterHandler();
                        showTitleBarAndFooter();
                        runHeaderFooterHandler();
                        SubTitlePopup(this.context);
                        return;
                    }
                    stopHeaderFooterHandler();
                    showTitleBarAndFooter();
                    runHeaderFooterHandler();
                    return;
                } catch (Exception e11) {
                    Log.e("NSTIJPLAYER", "exection " + e11);
                    return;
                }
            case R.id.vlc_exo_audio:
            default:
                return;
        }
        try {
            if (findViewById(R.id.controls).getVisibility() != 0) {
                stopHeaderFooterHandler();
                showTitleBarAndFooter();
                runHeaderFooterHandler();
            } else if (this.rq.booleanValue()) {
                stopHeaderFooterHandler();
                showTitleBarAndFooter();
                runHeaderFooterHandler();
                if (this.vlcplayButton != null) {
                    this.mVideoView.start();
                    playerStartIconsUpdate();
                    this.vlcpauseButton.requestFocus();
                }
            }
        } catch (Exception e12) {
            Log.e("NSTIJPLAYER", "exection " + e12);
        }
    }

    public void hidePopup() {
        if (this.changeSortPopUp != null) {
            this.changeSortPopUp.dismiss();
        }
    }

    @SuppressLint({"ResourceType"})
    private void SubTitlePopup(Context context2) {
        if (this.mVideoView != null) {
            View inflate = ((LayoutInflater) context2.getSystemService("layout_inflater")).inflate((int) R.layout.subtitle_layout, (RadioGroup) findViewById(R.id.subtitle_radio_group));
            this.changeSortPopUp = new PopupWindow(context2);
            this.changeSortPopUp.setContentView(inflate);
            this.changeSortPopUp.setWidth(-1);
            this.changeSortPopUp.setHeight(-1);
            this.changeSortPopUp.setFocusable(true);
            this.changeSortPopUp.setOnDismissListener(new PopupWindow.OnDismissListener() {
                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass15 */

                public void onDismiss() {
                    if (NSTIJKPlayerSkyActivity.this.mVideoView != null) {
                        NSTIJKPlayerSkyActivity.this.hideSystemUi();
                    }
                }
            });
            RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.rl_subtitle_layout);
            RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.subtitle_radio_group);
            RadioGroup radioGroup2 = (RadioGroup) inflate.findViewById(R.id.audio_radio_group);
            RadioGroup radioGroup3 = (RadioGroup) inflate.findViewById(R.id.video_radio_group);
            this.no_audio_track = (TextView) inflate.findViewById(R.id.tv_no_audio_track);
            this.no_subtitle_track = (TextView) inflate.findViewById(R.id.tv_no_subtitle_track);
            this.no_video_track = (TextView) inflate.findViewById(R.id.tv_no_video_track);
            this.subtitle_delay_plus = inflate.findViewById(R.id.subtitle_delay_plus);
            this.subtitle_delay_ms = (TextView) inflate.findViewById(R.id.subtitle_delay_ms);
            this.subtitle_delay_minus = inflate.findViewById(R.id.subtitle_delay_minus);
            this.bt_browse_subtitle = (Button) inflate.findViewById(R.id.bt_browse_subtitle);
            this.subtitle_font_size = (Spinner) inflate.findViewById(R.id.subtitle_font_size);
            try {
                this.loginPreferencesAfterLoginSubtitleSize = context2.getSharedPreferences(AppConst.LOGIN_PREF_SUB_FONT_SIZE, 0);
                this.subtitle_font_size.setSelection(((ArrayAdapter) this.subtitle_font_size.getAdapter()).getPosition(this.loginPreferencesAfterLoginSubtitleSize.getString(AppConst.LOGIN_PREF_SUB_FONT_SIZE, "20")));
            } catch (Exception unused) {
            }
            if (this.subtitle_font_size != null) {
                this.subtitle_font_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass16 */

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        String obj = NSTIJKPlayerSkyActivity.this.subtitle_font_size.getItemAtPosition(i).toString();
                        SharedPreferences unused = NSTIJKPlayerSkyActivity.this.loginPreferencesAfterLoginSubtitleSize = NSTIJKPlayerSkyActivity.this.getSharedPreferences(AppConst.LOGIN_PREF_SUB_FONT_SIZE, 0);
                        SharedPreferences.Editor unused2 = NSTIJKPlayerSkyActivity.this.loginPrefsEditorSubtitleSize = NSTIJKPlayerSkyActivity.this.loginPreferencesAfterLoginSubtitleSize.edit();
                        if (NSTIJKPlayerSkyActivity.this.loginPrefsEditorSubtitleSize != null) {
                            NSTIJKPlayerSkyActivity.this.loginPrefsEditorSubtitleSize.putString(AppConst.LOGIN_PREF_SUB_FONT_SIZE, obj);
                            NSTIJKPlayerSkyActivity.this.loginPrefsEditorSubtitleSize.apply();
                        }
                    }
                });
            }
            if (this.mVideoView != null) {
                this.mVideoView.showSubTitle(radioGroup3, radioGroup2, radioGroup, this.changeSortPopUp, this.no_video_track, this.no_audio_track, this.no_subtitle_track);
            }
            this.changeSortPopUp.showAtLocation(inflate, 1, 0, 0);
        }
    }

    public void chooseSubtitleDialog() {
        if (isStoragePermissionGranted()) {
            new FileChooserDialog.Builder(this.context).initialPath(Environment.getExternalStorageDirectory().getAbsolutePath()).tag("optional-identifier").show(this);
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
        int currentWindowIndex = this.mVideoView.getCurrentWindowIndex();
        if (currentWindowIndex == 0) {
            this.mVideoView.setCurrentWindowIndex(this.liveListDetailAvailableChannels.size() - 1);
        } else {
            this.mVideoView.setCurrentWindowIndex(currentWindowIndex - 1);
        }
    }

    private void next() {
        int currentWindowIndex = this.mVideoView.getCurrentWindowIndex();
        if (currentWindowIndex == this.liveListDetailAvailableChannels.size() - 1) {
            this.mVideoView.setCurrentWindowIndex(0);
        } else {
            this.mVideoView.setCurrentWindowIndex(currentWindowIndex + 1);
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        if (!this.fullScreen) {
            super.onBackPressed();
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        } else if (findViewById(R.id.app_video_top_box).getVisibility() == 0) {
            hideTitleBarAndFooter();
        } else {
            if (this.rlToolbar != null) {
                this.rlToolbar.setVisibility(0);
            }
            smallScreenVideoLayoutForTv();
            hideTitleBarAndFooter();
        }
    }

    public void fullScreenVideoLayout() {
        this.fullScreen = true;
        try {
            if (this.mVideoView != null) {
                hideSystemUi();
            }
        } catch (Exception unused) {
        }
        String livePlayerAppName = SharepreferenceDBHandler.getLivePlayerAppName(this.context);
        if (!SharepreferenceDBHandler.getLivePlayerPkgName(this.context).equals("default") && !new ExternalPlayerDataBase(this.context).CheckPlayerExistense(livePlayerAppName)) {
            SharepreferenceDBHandler.setLivePlayer("default", "default", this.context);
        }
        String livePlayerPkgName = SharepreferenceDBHandler.getLivePlayerPkgName(this.context);
        if (livePlayerPkgName != null && !livePlayerPkgName.equalsIgnoreCase("default")) {
            release();
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
            if (this.mVideoView != null) {
                this.mVideoView.fullScreenValue(false);
            }
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
            stopHeaderFooterHandler();
            showTitleBarAndFooter();
            runHeaderFooterHandler();
            if (this.mVideoView == null || !this.mVideoView.isPlaying()) {
                this.vlcplayButton.requestFocus();
            } else {
                this.vlcpauseButton.requestFocus();
            }
        }
    }

    private void smallScreenVideoLayoutForTv() {
        try {
            if (AppConst.SETPLAYERLISTENER.booleanValue()) {
                findViewById(R.id.app_video_box).setOnClickListener(this);
            }
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
            showTitleBar();
        } catch (Exception e) {
            Log.e("exection", "" + e);
        }
    }

    public static class Config implements Parcelable {
        public static final Creator<Config> CREATOR = new Creator<Config>() {
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.Config.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public Config createFromParcel(Parcel parcel) {
                return new Config(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public Config[] newArray(int i) {
                return new Config[i];
            }
        };
        private static boolean debug = true;
        private Activity activity;
        private long defaultRetryTime;
        private boolean fullScreenOnly;
        private String scaleType;
        private boolean showNavIcon;
        private String title;
        private String url;

        public int describeContents() {
            return 0;
        }

        public Config(Activity activity2) {
            this.fullScreenOnly = true;
            this.defaultRetryTime = 2500;
            this.showNavIcon = true;
            this.activity = activity2;
        }

        private Config(Parcel parcel) {
            boolean z = true;
            this.fullScreenOnly = true;
            this.defaultRetryTime = 2500;
            this.showNavIcon = true;
            this.scaleType = parcel.readString();
            this.fullScreenOnly = parcel.readByte() != 0;
            this.defaultRetryTime = parcel.readLong();
            this.title = parcel.readString();
            this.url = parcel.readString();
            this.showNavIcon = parcel.readByte() == 0 ? false : z;
        }

        public static boolean isDebug() {
            return debug;
        }

        public Config debug(boolean z) {
            debug = z;
            return this;
        }

        public Config setTitle(String str) {
            this.title = str;
            return this;
        }

        public Config setDefaultRetryTime(long j) {
            this.defaultRetryTime = j;
            return this;
        }

        public void play(String str) {
            this.url = str;
            Intent intent = new Intent(this.activity, NSTIJKPlayerSkyActivity.class);
            intent.putExtra("config", this);
            this.activity.startActivity(intent);
        }

        public Config setScaleType(String str) {
            this.scaleType = str;
            return this;
        }

        public Config setFullScreenOnly(boolean z) {
            this.fullScreenOnly = z;
            return this;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.scaleType);
            parcel.writeByte(this.fullScreenOnly ? (byte) 1 : 0);
            parcel.writeLong(this.defaultRetryTime);
            parcel.writeString(this.title);
            parcel.writeString(this.url);
            parcel.writeByte(this.showNavIcon ? (byte) 1 : 0);
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
                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass18 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(NSTIJKPlayerSkyActivity.this.context);
                }
            }).setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass17 */

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
                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass19 */

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextSubmit(String str) {
                    return false;
                }

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextChange(String str) {
                    if (NSTIJKPlayerSkyActivity.this.text_from_toolbar) {
                        NSTIJKPlayerSkyActivity.this.text_from_toolbar = false;
                        return false;
                    }
                    NSTIJKPlayerSkyActivity.this.tvNoRecordFound.setVisibility(8);
                    if (!(NSTIJKPlayerSkyActivity.this.adapter == null || NSTIJKPlayerSkyActivity.this.tvNoStream == null || NSTIJKPlayerSkyActivity.this.tvNoStream.getVisibility() == 0)) {
                        NSTIJKPlayerSkyActivity.this.adapter.getFilter().filter(str);
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
                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass20 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    NSTIJKPlayerSkyActivity.this.release();
                    Utils.loadChannelsAndVod(NSTIJKPlayerSkyActivity.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass21 */

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
                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass22 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    NSTIJKPlayerSkyActivity.this.release();
                    Utils.loadTvGuid(NSTIJKPlayerSkyActivity.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass23 */

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

    private void showSortPopup(Activity activity2) {
        char c;
        final Activity activity3 = activity2;
        try {
            final View inflate = ((LayoutInflater) activity3.getSystemService("layout_inflater")).inflate((int) R.layout.sort_layout, (RelativeLayout) activity3.findViewById(R.id.rl_password_prompt));
            this.changeSortPopUp = new PopupWindow(activity3);
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
            String liveSubCatSort = SharepreferenceDBHandler.getLiveSubCatSort(activity2);
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
                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass24 */

                public void onClick(View view) {
                    NSTIJKPlayerSkyActivity.this.changeSortPopUp.dismiss();
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity.AnonymousClass25 */

                public void onClick(View view) {
                    RadioButton radioButton = (RadioButton) inflate.findViewById(radioGroup.getCheckedRadioButtonId());
                    if (radioButton.getText().toString().equals(activity3.getResources().getString(R.string.sort_last_added))) {
                        SharepreferenceDBHandler.setLiveSubCatSort("1", activity3);
                    } else if (radioButton.getText().toString().equals(activity3.getResources().getString(R.string.sort_atoz))) {
                        SharepreferenceDBHandler.setLiveSubCatSort("2", activity3);
                    } else if (radioButton.getText().toString().equals(activity3.getResources().getString(R.string.sort_ztoa))) {
                        SharepreferenceDBHandler.setLiveSubCatSort("3", activity3);
                    } else if (radioButton.getText().toString().equals(activity3.getResources().getString(R.string.sort_channel_number_asc))) {
                        SharepreferenceDBHandler.setLiveSubCatSort("4", activity3);
                    } else if (radioButton.getText().toString().equals(activity3.getResources().getString(R.string.sort_channel_number_decs))) {
                        SharepreferenceDBHandler.setLiveSubCatSort("5", activity3);
                    } else {
                        SharepreferenceDBHandler.setLiveSubCatSort(AppConst.PASSWORD_UNSET, activity3);
                    }
                    if (NSTIJKPlayerSkyActivity.this.catID.equals(AppConst.PASSWORD_UNSET)) {
                        NSTIJKPlayerSkyActivity.this.allChannels();
                    } else if (NSTIJKPlayerSkyActivity.this.catID == null || NSTIJKPlayerSkyActivity.this.catID.equals("") || !NSTIJKPlayerSkyActivity.this.catID.equals("-1")) {
                        NSTIJKPlayerSkyActivity.this.allChannelsWithCat();
                    } else {
                        NSTIJKPlayerSkyActivity.this.allFavourites();
                    }
                    NSTIJKPlayerSkyActivity.this.changeSortPopUp.dismiss();
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
            if (this.mVideoView != null) {
                this.mVideoView.setSystemUiVisibility(4871);
            }
        } catch (Exception unused) {
        }
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
                    if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("1") && NSTIJKPlayerSkyActivity.this.negativeButton != null) {
                        NSTIJKPlayerSkyActivity.this.negativeButton.setBackgroundResource(R.drawable.back_btn_effect);
                        return;
                    }
                    return;
                }
                view2.setBackground(NSTIJKPlayerSkyActivity.this.getResources().getDrawable(R.drawable.selector_checkbox));
            } else if (!z) {
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("1") || NSTIJKPlayerSkyActivity.this.negativeButton == null)) {
                    NSTIJKPlayerSkyActivity.this.negativeButton.setBackgroundResource(R.drawable.black_button_dark);
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
}
