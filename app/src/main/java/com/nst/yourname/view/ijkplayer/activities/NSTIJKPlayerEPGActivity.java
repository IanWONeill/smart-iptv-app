package com.nst.yourname.view.ijkplayer.activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import com.afollestad.materialdialogs.folderselector.FileChooserDialog;
import com.github.tcking.viewquery.ViewQuery;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.FavouriteM3UModel;
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.PasswordStatusDBModel;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.model.pojo.XMLTVProgrammePojo;
import com.nst.yourname.view.adapter.ChannelsOnVideoAdapter;
import com.nst.yourname.view.adapter.SearchableAdapter;
import com.nst.yourname.view.ijkplayer.application.Settings;
import com.nst.yourname.view.ijkplayer.content.RecentMediaStorage;
import com.nst.yourname.view.ijkplayer.widget.media.FileMediaDataSource;
import com.nst.yourname.view.ijkplayer.widget.media.MeasureHelper;
import com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerEPG;
import com.nst.yourname.view.ijkplayer.widget.media.SurfaceRenderView;
import com.nst.yourname.view.ijkplayer.widget.media.TableLayoutBinder;
import com.nst.yourname.view.ijkplayer.widget.preference.IjkListPreference;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.joda.time.LocalDateTime;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

@SuppressWarnings("ALL")
public class NSTIJKPlayerEPGActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, FileChooserDialog.FileCallback {
    static final boolean $assertionsDisabled = false;
    private static final int RECORDING_REQUEST_CODE = 101;
    private static SharedPreferences loginPreferencesAfterLogin;
    private static SharedPreferences loginPreferencesDownloadStatus;
    private static SharedPreferences loginPreferencesSharedPref_time_format;
    private static String uk;
    private static String una;
    public ViewQuery $;
    private ArrayList<LiveStreamsDBModel> AvailableChannelsFirstOpenedCat;
    private String MultiPlayerType;
    private SharedPreferences SharedPreferencesSort;
    private SharedPreferences.Editor SharedPreferencesSortEditor;
    public Activity activity;
    SearchableAdapter adapter;
    private AlertDialog alertDialog;
    private ArrayList<LiveStreamCategoryIdDBModel> allLiveCategories;
    public ArrayList<LiveStreamsDBModel> allStreams;
    public ArrayList<LiveStreamsDBModel> allStreams_with_cat;
    public String allowedFormat;
    TextView app_channel_jumping_text;
    public RelativeLayout app_video_box;
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
    String catID = "";
    String catName = "";
    private ArrayList<PasswordStatusDBModel> categoryWithPasword;
    private PopupWindow changeSortPopUp;
    public boolean channelJumping = false;
    public ImageView channelLogo;
    String channelNumJumping;
    public boolean channelZapped = false;
    ChannelsOnVideoAdapter channelsOnVideoAdapter;
    public Context context = this;
    int countUncat = -1;
    public String currentAPPType;
    private int currentCategoryIndex = 0;
    String currentChannelLogo;
    String currentEpgChannelID;
    public TextView currentProgram;
    public String currentProgramChanneID;
    public int currentProgramStreamID;
    public TextView currentProgramTime;
    private DatabaseHandler database;
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
    public View exo_info;
    private ArrayList<FavouriteDBModel> favliveListDetailAvailable;
    private ArrayList<FavouriteDBModel> favliveListDetailUnlcked;
    private ArrayList<FavouriteDBModel> favliveListDetailUnlckedDetail;
    String fmw;
    SimpleDateFormat fr;
    public boolean fullScreen = false;
    public boolean fullScreenOnly = true;
    private GridLayoutManager gridLayoutManager;
    Handler handler;
    private Handler handlerAspectRatio;
    Handler handlerHeaderFooter;
    Handler handlerJumpChannel;
    Handler handlerUpdateEPGData;
    public boolean hideEPGData = true;
    boolean isInitializeVlc = false;
    StringBuilder jumpToChannel = new StringBuilder();
    public ArrayList<String> listPassword = new ArrayList<>();
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetail;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailAvailable;
    public ArrayList<LiveStreamsDBModel> liveListDetailAvailableChannels;
    private ArrayList<LiveStreamsDBModel> liveListDetailAvailableChannels_Temp;
    private ArrayList<LiveStreamsDBModel> liveListDetailAvailableNewChannels;
    private ArrayList<LiveStreamsDBModel> liveListDetailChannels;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailUnlcked;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlckedChannels;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailUnlckedDetail;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlckedDetailChannels;
    LiveStreamDBHandler liveStreamDBHandler;
    LinearLayout ll_channel_jumping;
    LinearLayout ll_layout_to_hide1;
    LinearLayout ll_layout_to_hide4;
    public LinearLayout ll_seekbar_time;
    public SharedPreferences loginPreferencesAfterLoginSubtitleSize;
    private SharedPreferences loginPreferencesMediaCodec;
    private SharedPreferences loginPreferencesSharedPref;
    private SharedPreferences loginPreferencesSharedPref_allowed_format;
    private SharedPreferences loginPreferencesSharedPref_currently_playing_video;
    private SharedPreferences loginPreferencesSharedPref_currently_playing_video_position;
    public SharedPreferences loginPreferencesSharedPref_opened_video;
    private SharedPreferences loginPreferencesUserAgent;
    private SharedPreferences loginPreferences_audio_delay;
    private SharedPreferences loginPreferences_audio_selected;
    private SharedPreferences loginPreferences_subtitle_delay;
    private SharedPreferences loginPreferences_subtitle_selected;
    private SharedPreferences loginPreferences_video_selected;
    public SharedPreferences.Editor loginPrefsEditor;
    public SharedPreferences.Editor loginPrefsEditorAudio;
    private SharedPreferences.Editor loginPrefsEditorAudioDelay;
    SharedPreferences.Editor loginPrefsEditorMediaCodec;
    private SharedPreferences.Editor loginPrefsEditorPosition;
    public SharedPreferences.Editor loginPrefsEditorSubtitle;
    private SharedPreferences.Editor loginPrefsEditorSubtitleDelay;
    public SharedPreferences.Editor loginPrefsEditorSubtitleSize;
    public SharedPreferences.Editor loginPrefsEditorVideo;
    public boolean longKeyPressed = true;
    private String m3uVideoURL;
    public String mFilePath;
    private String mFilePath1 = "";
    private TextView mInfo;
    private Settings mSettings;
    public NSTIJKPlayerEPG mVideoView;
    MenuItem menuItemSettings;
    Menu menuSelect;
    public View multi;
    RecyclerView myRecyclerView;
    public Button negativeButton;
    public TextView nextProgram;
    public TextView nextProgramTime;
    TextView no_audio_track;
    boolean no_channel = false;
    TextView no_subtitle_track;
    TextView no_video_track;
    private int opened_vlc_id = 0;
    boolean outfromtoolbar = true;
    ProgressBar pbLoader;
    ProgressBar pb_listview_loader;
    private Boolean playFirstTimeLoaded = false;
    private SimpleDateFormat programTimeFormat;
    ProgressBar progressBar;
    public RelativeLayout rl_categories_view;
    RelativeLayout rl_footer_info;
    RelativeLayout rl_layout_to_hide5;
    RelativeLayout rl_layout_to_hide_6;
    public RelativeLayout rl_middle;
    RelativeLayout rl_nst_player_sky_layout;
    RelativeLayout rl_settings;
    RelativeLayout rl_video_box;
    private Boolean rq = true;
    public String scaleType;
    SearchView searchView;
    private String selected_language = "";
    Boolean sentToSettings = false;
    SharedPreferences.Editor sharedPrefEditor;
    SharedPreferences sharedPreferences;
    public boolean showNavIcon = true;
    private Boolean subtitleTrackFound = false;
    View subtitle_delay_minus;
    TextView subtitle_delay_ms;
    View subtitle_delay_plus;
    Spinner subtitle_font_size;
    boolean text_from_toolbar = false;
    TextView time;
    private Long timeShift;
    public String title;
    Toolbar toolbar;
    TextView tvNoRecordFound;
    TextView tvNoStream;
    public TextView tv_categories_view;
    TextView tv_video_height;
    TextView tv_video_margin_right;
    TextView tv_video_width;
    TextView txtDisplay;
    String ukd;
    String unad;
    public String url;
    private Boolean videoTrackFound = false;
    private int video_num = 0;
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

    public static void play(Activity activity2, String... strArr) {
        Intent intent = new Intent(activity2, NSTIJKPlayerEPGActivity.class);
        intent.putExtra("url", strArr[0]);
        if (strArr.length > 1) {
            intent.putExtra("title", strArr[1]);
        }
        activity2.startActivity(intent);
    }

    public static Config configPlayer(Activity activity2) {
        return new Config(activity2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:139:0x0809, code lost:
        if (r7.equals("-1") != false) goto L_0x0817;
     */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x065b  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x0664  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x0680  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x06a8  */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x06ba  */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x06dd  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x06ef  */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x0701  */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x0713  */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x07fe  */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x080c  */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x081a  */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x081d  */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x0822  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0166  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0188  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x01a3  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x01be  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x01e7  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x020c  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0231  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x02ea  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0316  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x054c  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x05d8  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x05f4  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x060d  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0625  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x063a  */
    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        char c;
        int hashCode;
        char c2 = 1;
        requestWindowFeature(1);
        super.onCreate(bundle);
        this.mSettings = new Settings(this.context);
        this.sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.$ = new ViewQuery(this);
        this.handlerAspectRatio = new Handler();
        setContentView((int) R.layout.activity_vlcplayer_epg);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        this.loginPreferencesSharedPref = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        loginPreferencesAfterLogin = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.loginPreferencesSharedPref_allowed_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
        this.loginPreferencesSharedPref_currently_playing_video = getSharedPreferences(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, 0);
        this.loginPrefsEditor = this.loginPreferencesSharedPref_currently_playing_video.edit();
        this.loginPreferencesSharedPref_currently_playing_video_position = getSharedPreferences(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, 0);
        this.loginPrefsEditorPosition = this.loginPreferencesSharedPref_currently_playing_video_position.edit();
        this.loginPreferencesSharedPref_opened_video = this.context.getSharedPreferences(AppConst.LOGIN_PREF_OPENED_VIDEO, 0);
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
        String string = this.loginPreferencesSharedPref.getString("username", "");
        String string2 = this.loginPreferencesSharedPref.getString("password", "");
        String string3 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_URL, "");
        String string4 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_PROTOCOL, "");
        String string5 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_HTTPS_PORT, "");
        String string6 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_PORT, "");
        String string7 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_RTMP_PORT, "");
        int hashCode2 = string4.hashCode();
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
                    this.allowedFormat = this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
                    if (this.allowedFormat == null && !this.allowedFormat.isEmpty() && !this.allowedFormat.equals("") && this.allowedFormat.equals("default")) {
                        this.allowedFormat = "";
                    } else if (this.allowedFormat == null && !this.allowedFormat.isEmpty() && !this.allowedFormat.equals("") && this.allowedFormat.equals("ts")) {
                        this.allowedFormat = ".ts";
                    } else if (this.allowedFormat != null || this.allowedFormat.isEmpty() || this.allowedFormat.equals("") || !this.allowedFormat.equals("m3u8")) {
                        this.allowedFormat = "";
                    } else {
                        this.allowedFormat = ".m3u8";
                    }
                    int intExtra = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
                    this.m3uVideoURL = getIntent().getStringExtra("VIDEO_URL");
                    this.video_num = getIntent().getIntExtra("VIDEO_NUM", 0);
                    getIntent().getStringExtra("STREAM_TYPE");
                    String stringExtra = getIntent().getStringExtra("VIDEO_TITLE");
                    String stringExtra2 = getIntent().getStringExtra("OPENED_CAT_ID");
                    String stringExtra3 = getIntent().getStringExtra("EPG_CHANNEL_ID");
                    this.MultiPlayerType = getIntent().getStringExtra("MultiPlayer");
                    String stringExtra4 = getIntent().getStringExtra("EPG_CHANNEL_LOGO");
                    SharedPreferences.Editor edit = this.loginPreferencesSharedPref_opened_video.edit();
                    edit.putInt(AppConst.LOGIN_PREF_OPENED_VIDEO_ID, intExtra);
                    edit.putString("LOGIN_PREF_OPENED_VIDEO_URL_M3U", this.m3uVideoURL);
                    edit.putString("name", stringExtra);
                    edit.putInt(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_NUM, this.video_num);
                    edit.putString(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_EPG_CHANNEL_ID, stringExtra3);
                    edit.putString(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_CHANNEL_LOGO, stringExtra4);
                    edit.apply();
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
                    this.handlerUpdateEPGData = new Handler();
                    this.handlerJumpChannel = new Handler();
                    this.mVideoView = (NSTIJKPlayerEPG) findViewById(R.id.video_view);
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
                    this.rl_nst_player_sky_layout = (RelativeLayout) findViewById(R.id.rl_nst_player_sky_layout);
                    this.ll_layout_to_hide1 = (LinearLayout) findViewById(R.id.ll_layout_to_hide_1);
                    this.ll_layout_to_hide4 = (LinearLayout) findViewById(R.id.ll_layout_to_hide_4);
                    this.rl_layout_to_hide5 = (RelativeLayout) findViewById(R.id.rl_layout_to_hide_5);
                    this.rl_layout_to_hide_6 = (RelativeLayout) findViewById(R.id.rl_layout_to_hide_6);
                    this.rl_footer_info = (RelativeLayout) findViewById(R.id.rl_footer_info);
                    this.rl_video_box = (RelativeLayout) findViewById(R.id.rl_video_box);
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
                    IjkMediaPlayer.loadLibrariesOnce(null);
                    IjkMediaPlayer.native_profileBegin("libijkplayer.so");
                    this.toolbar.inflateMenu(R.menu.menu_search);
                    this.appbarToolbar.requestFocusFromTouch();
                    setSupportActionBar(this.toolbar);
                    this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
                    if (SharepreferenceDBHandler.getSelectedLanguage(this.context).equalsIgnoreCase("Arabic")) {
                        this.btn_cat_back.setImageResource(R.drawable.right_icon_cat);
                        this.btn_cat_forward.setImageResource(R.drawable.left_icon_cat);
                    }
                    loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
                    this.programTimeFormat = new SimpleDateFormat(loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, ""), Locale.US);
                    this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
                    findViewById(R.id.exo_next).setOnClickListener(this);
                    findViewById(R.id.exo_prev).setOnClickListener(this);
                    findViewById(R.id.exo_multiplayer).setOnClickListener(this);
                    this.fullScreen = true;
                    this.rl_categories_view = (RelativeLayout) findViewById(R.id.rl_categories_view);
                    this.btn_cat_back.setOnClickListener(this);
                    this.btn_cat_forward.setOnClickListener(this);
                    this.vlcplayButton = findViewById(R.id.exo_play);
                    if (this.vlcplayButton != null) {
                        this.vlcplayButton.setOnClickListener(this);
                    }
                    this.unad = Utils.ukde(MeasureHelper.pnm());
                    this.vlcpauseButton = findViewById(R.id.exo_pause);
                    if (this.vlcpauseButton != null) {
                        this.vlcpauseButton.setOnClickListener(this);
                    }
                    uk = getApplicationName(this.context);
                    this.vlcprevButton = findViewById(R.id.exo_prev);
                    if (this.MultiPlayerType != null || !this.MultiPlayerType.equals("true")) {
                        if (this.vlcprevButton != null) {
                            this.vlcprevButton.setOnClickListener(this);
                        }
                    } else if (this.vlcprevButton != null) {
                        this.vlcprevButton.setVisibility(8);
                    }
                    this.vlcnextButton = findViewById(R.id.exo_next);
                    this.multi = findViewById(R.id.exo_multiplayer);
                    if (this.MultiPlayerType != null || !this.MultiPlayerType.equals("true")) {
                        if (this.vlcnextButton != null) {
                            this.vlcnextButton.setOnClickListener(this);
                        }
                        if (this.multi != null) {
                            this.multi.setOnClickListener(this);
                        }
                    } else {
                        if (this.vlcnextButton != null) {
                            this.vlcnextButton.setVisibility(8);
                        }
                        if (this.multi != null) {
                            this.multi.setVisibility(0);
                        }
                    }
                    una = getApplicationContext().getPackageName();
                    this.vlcchannelListButton = findViewById(R.id.btn_list);
                    if (this.vlcchannelListButton != null) {
                        this.vlcchannelListButton.setOnClickListener(this);
                    }
                    this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                    this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                    this.vlcaspectRatio = findViewById(R.id.btn_aspect_ratio);
                    if (this.vlcaspectRatio != null) {
                        this.vlcaspectRatio.setOnClickListener(this);
                    }
                    this.vlc_exo_subtitle = findViewById(R.id.exo_subtitle);
                    if (this.vlc_exo_subtitle != null) {
                        this.vlc_exo_subtitle.setOnClickListener(this);
                    }
                    this.ukd = Utils.ukde(FileMediaDataSource.apn());
                    this.dt = new Date();
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
                    this.liveListDetailUnlcked = new ArrayList<>();
                    this.liveListDetailUnlckedDetail = new ArrayList<>();
                    this.liveListDetailAvailable = new ArrayList<>();
                    this.liveListDetail = new ArrayList<>();
                    this.liveListDetailUnlckedChannels = new ArrayList<>();
                    this.liveListDetailUnlckedDetailChannels = new ArrayList<>();
                    this.liveListDetailAvailableChannels = new ArrayList<>();
                    this.liveListDetailAvailableChannels_Temp = new ArrayList<>();
                    this.AvailableChannelsFirstOpenedCat = new ArrayList<>();
                    this.liveListDetailAvailableNewChannels = new ArrayList<>();
                    this.liveListDetailChannels = new ArrayList<>();
                    this.allLiveCategories = this.liveStreamDBHandler.getAllliveCategories();
                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel2 = new LiveStreamCategoryIdDBModel();
                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel3 = new LiveStreamCategoryIdDBModel();
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryID(AppConst.PASSWORD_UNSET);
                    liveStreamCategoryIdDBModel.setLiveStreamCategoryName(this.context.getResources().getString(R.string.all));
                    liveStreamCategoryIdDBModel2.setLiveStreamCategoryID("-1");
                    liveStreamCategoryIdDBModel2.setLiveStreamCategoryName(this.context.getResources().getString(R.string.favourites));
                    this.countUncat = this.liveStreamDBHandler.getUncatCount("-2", "live");
                    if (this.countUncat != 0 && this.countUncat > 0) {
                        liveStreamCategoryIdDBModel3.setLiveStreamCategoryID("-2");
                        liveStreamCategoryIdDBModel3.setLiveStreamCategoryName(this.context.getResources().getString(R.string.uncategories));
                        this.allLiveCategories.add(this.allLiveCategories.size(), liveStreamCategoryIdDBModel3);
                    }
                    this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context));
                    this.listPassword = getPasswordSetCategories();
                    hashCode = stringExtra2.hashCode();
                    if (hashCode == 48) {
                        if (hashCode == 1444) {
                        }
                    } else if (stringExtra2.equals(AppConst.PASSWORD_UNSET)) {
                        c2 = 0;
                        switch (c2) {
                            case 0:
                                this.catID = AppConst.PASSWORD_UNSET;
                                break;
                            case 1:
                                this.catID = "-1";
                                break;
                            default:
                                this.catID = stringExtra2;
                                break;
                        }
                        this.mVideoView.setEPGHandler(this.handlerUpdateEPGData);
                        this.mVideoView.setContext(this.context);
                        this.timeShift = Long.valueOf(getTimeShiftMilliSeconds(this.context));
                        this.mVideoView.setTimeShift(getTimeShiftMilliSeconds(this.context));
                    }
                    c2 = 65535;
                    switch (c2) {
                    }
                    this.mVideoView.setEPGHandler(this.handlerUpdateEPGData);
                    this.mVideoView.setContext(this.context);
                    this.timeShift = Long.valueOf(getTimeShiftMilliSeconds(this.context));
                    this.mVideoView.setTimeShift(getTimeShiftMilliSeconds(this.context));
                }
            } else if (string4.equals(AppConst.RMTP)) {
                c = 2;
                switch (c) {
                }
                this.allowedFormat = this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
                if (this.allowedFormat == null) {
                }
                if (this.allowedFormat == null) {
                }
                if (this.allowedFormat != null) {
                }
                this.allowedFormat = "";
                int intExtra2 = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
                this.m3uVideoURL = getIntent().getStringExtra("VIDEO_URL");
                this.video_num = getIntent().getIntExtra("VIDEO_NUM", 0);
                getIntent().getStringExtra("STREAM_TYPE");
                String stringExtra5 = getIntent().getStringExtra("VIDEO_TITLE");
                String stringExtra22 = getIntent().getStringExtra("OPENED_CAT_ID");
                String stringExtra32 = getIntent().getStringExtra("EPG_CHANNEL_ID");
                this.MultiPlayerType = getIntent().getStringExtra("MultiPlayer");
                String stringExtra42 = getIntent().getStringExtra("EPG_CHANNEL_LOGO");
                SharedPreferences.Editor edit2 = this.loginPreferencesSharedPref_opened_video.edit();
                edit2.putInt(AppConst.LOGIN_PREF_OPENED_VIDEO_ID, intExtra2);
                edit2.putString("LOGIN_PREF_OPENED_VIDEO_URL_M3U", this.m3uVideoURL);
                edit2.putString("name", stringExtra5);
                edit2.putInt(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_NUM, this.video_num);
                edit2.putString(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_EPG_CHANNEL_ID, stringExtra32);
                edit2.putString(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_CHANNEL_LOGO, stringExtra42);
                edit2.apply();
                if (!this.allowedFormat.equals("")) {
                }
                this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
                this.liveStreamDBHandler = new LiveStreamDBHandler(this);
                this.database = new DatabaseHandler(this.context);
                this.handler = new Handler();
                this.handlerHeaderFooter = new Handler();
                this.handlerUpdateEPGData = new Handler();
                this.handlerJumpChannel = new Handler();
                this.mVideoView = (NSTIJKPlayerEPG) findViewById(R.id.video_view);
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
                this.rl_nst_player_sky_layout = (RelativeLayout) findViewById(R.id.rl_nst_player_sky_layout);
                this.ll_layout_to_hide1 = (LinearLayout) findViewById(R.id.ll_layout_to_hide_1);
                this.ll_layout_to_hide4 = (LinearLayout) findViewById(R.id.ll_layout_to_hide_4);
                this.rl_layout_to_hide5 = (RelativeLayout) findViewById(R.id.rl_layout_to_hide_5);
                this.rl_layout_to_hide_6 = (RelativeLayout) findViewById(R.id.rl_layout_to_hide_6);
                this.rl_footer_info = (RelativeLayout) findViewById(R.id.rl_footer_info);
                this.rl_video_box = (RelativeLayout) findViewById(R.id.rl_video_box);
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
                IjkMediaPlayer.loadLibrariesOnce(null);
                IjkMediaPlayer.native_profileBegin("libijkplayer.so");
                this.toolbar.inflateMenu(R.menu.menu_search);
                this.appbarToolbar.requestFocusFromTouch();
                setSupportActionBar(this.toolbar);
                this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
                if (SharepreferenceDBHandler.getSelectedLanguage(this.context).equalsIgnoreCase("Arabic")) {
                }
                loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
                this.programTimeFormat = new SimpleDateFormat(loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, ""), Locale.US);
                this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
                findViewById(R.id.exo_next).setOnClickListener(this);
                findViewById(R.id.exo_prev).setOnClickListener(this);
                findViewById(R.id.exo_multiplayer).setOnClickListener(this);
                this.fullScreen = true;
                this.rl_categories_view = (RelativeLayout) findViewById(R.id.rl_categories_view);
                this.btn_cat_back.setOnClickListener(this);
                this.btn_cat_forward.setOnClickListener(this);
                this.vlcplayButton = findViewById(R.id.exo_play);
                if (this.vlcplayButton != null) {
                }
                this.unad = Utils.ukde(MeasureHelper.pnm());
                this.vlcpauseButton = findViewById(R.id.exo_pause);
                if (this.vlcpauseButton != null) {
                }
                uk = getApplicationName(this.context);
                this.vlcprevButton = findViewById(R.id.exo_prev);
                if (this.MultiPlayerType != null) {
                }
                if (this.vlcprevButton != null) {
                }
                this.vlcnextButton = findViewById(R.id.exo_next);
                this.multi = findViewById(R.id.exo_multiplayer);
                if (this.MultiPlayerType != null) {
                }
                if (this.vlcnextButton != null) {
                }
                if (this.multi != null) {
                }
                una = getApplicationContext().getPackageName();
                this.vlcchannelListButton = findViewById(R.id.btn_list);
                if (this.vlcchannelListButton != null) {
                }
                this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                this.vlcaspectRatio = findViewById(R.id.btn_aspect_ratio);
                if (this.vlcaspectRatio != null) {
                }
                this.vlc_exo_subtitle = findViewById(R.id.exo_subtitle);
                if (this.vlc_exo_subtitle != null) {
                }
                this.ukd = Utils.ukde(FileMediaDataSource.apn());
                this.dt = new Date();
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
                this.liveListDetailUnlcked = new ArrayList<>();
                this.liveListDetailUnlckedDetail = new ArrayList<>();
                this.liveListDetailAvailable = new ArrayList<>();
                this.liveListDetail = new ArrayList<>();
                this.liveListDetailUnlckedChannels = new ArrayList<>();
                this.liveListDetailUnlckedDetailChannels = new ArrayList<>();
                this.liveListDetailAvailableChannels = new ArrayList<>();
                this.liveListDetailAvailableChannels_Temp = new ArrayList<>();
                this.AvailableChannelsFirstOpenedCat = new ArrayList<>();
                this.liveListDetailAvailableNewChannels = new ArrayList<>();
                this.liveListDetailChannels = new ArrayList<>();
                this.allLiveCategories = this.liveStreamDBHandler.getAllliveCategories();
                LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel4 = new LiveStreamCategoryIdDBModel();
                LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel22 = new LiveStreamCategoryIdDBModel();
                LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel32 = new LiveStreamCategoryIdDBModel();
                liveStreamCategoryIdDBModel4.setLiveStreamCategoryID(AppConst.PASSWORD_UNSET);
                liveStreamCategoryIdDBModel4.setLiveStreamCategoryName(this.context.getResources().getString(R.string.all));
                liveStreamCategoryIdDBModel22.setLiveStreamCategoryID("-1");
                liveStreamCategoryIdDBModel22.setLiveStreamCategoryName(this.context.getResources().getString(R.string.favourites));
                this.countUncat = this.liveStreamDBHandler.getUncatCount("-2", "live");
                liveStreamCategoryIdDBModel32.setLiveStreamCategoryID("-2");
                liveStreamCategoryIdDBModel32.setLiveStreamCategoryName(this.context.getResources().getString(R.string.uncategories));
                this.allLiveCategories.add(this.allLiveCategories.size(), liveStreamCategoryIdDBModel32);
                this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context));
                this.listPassword = getPasswordSetCategories();
                hashCode = stringExtra22.hashCode();
                if (hashCode == 48) {
                }
                c2 = 65535;
                switch (c2) {
                }
                this.mVideoView.setEPGHandler(this.handlerUpdateEPGData);
                this.mVideoView.setContext(this.context);
                this.timeShift = Long.valueOf(getTimeShiftMilliSeconds(this.context));
                this.mVideoView.setTimeShift(getTimeShiftMilliSeconds(this.context));
            }
        } else if (string4.equals(AppConst.HTTP)) {
            c = 0;
            switch (c) {
            }
            this.allowedFormat = this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
            if (this.allowedFormat == null) {
            }
            if (this.allowedFormat == null) {
            }
            if (this.allowedFormat != null) {
            }
            this.allowedFormat = "";
            int intExtra22 = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
            this.m3uVideoURL = getIntent().getStringExtra("VIDEO_URL");
            this.video_num = getIntent().getIntExtra("VIDEO_NUM", 0);
            getIntent().getStringExtra("STREAM_TYPE");
            String stringExtra52 = getIntent().getStringExtra("VIDEO_TITLE");
            String stringExtra222 = getIntent().getStringExtra("OPENED_CAT_ID");
            String stringExtra322 = getIntent().getStringExtra("EPG_CHANNEL_ID");
            this.MultiPlayerType = getIntent().getStringExtra("MultiPlayer");
            String stringExtra422 = getIntent().getStringExtra("EPG_CHANNEL_LOGO");
            SharedPreferences.Editor edit22 = this.loginPreferencesSharedPref_opened_video.edit();
            edit22.putInt(AppConst.LOGIN_PREF_OPENED_VIDEO_ID, intExtra22);
            edit22.putString("LOGIN_PREF_OPENED_VIDEO_URL_M3U", this.m3uVideoURL);
            edit22.putString("name", stringExtra52);
            edit22.putInt(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_NUM, this.video_num);
            edit22.putString(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_EPG_CHANNEL_ID, stringExtra322);
            edit22.putString(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_CHANNEL_LOGO, stringExtra422);
            edit22.apply();
            if (!this.allowedFormat.equals("")) {
            }
            this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
            this.liveStreamDBHandler = new LiveStreamDBHandler(this);
            this.database = new DatabaseHandler(this.context);
            this.handler = new Handler();
            this.handlerHeaderFooter = new Handler();
            this.handlerUpdateEPGData = new Handler();
            this.handlerJumpChannel = new Handler();
            this.mVideoView = (NSTIJKPlayerEPG) findViewById(R.id.video_view);
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
            this.rl_nst_player_sky_layout = (RelativeLayout) findViewById(R.id.rl_nst_player_sky_layout);
            this.ll_layout_to_hide1 = (LinearLayout) findViewById(R.id.ll_layout_to_hide_1);
            this.ll_layout_to_hide4 = (LinearLayout) findViewById(R.id.ll_layout_to_hide_4);
            this.rl_layout_to_hide5 = (RelativeLayout) findViewById(R.id.rl_layout_to_hide_5);
            this.rl_layout_to_hide_6 = (RelativeLayout) findViewById(R.id.rl_layout_to_hide_6);
            this.rl_footer_info = (RelativeLayout) findViewById(R.id.rl_footer_info);
            this.rl_video_box = (RelativeLayout) findViewById(R.id.rl_video_box);
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
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
            this.toolbar.inflateMenu(R.menu.menu_search);
            this.appbarToolbar.requestFocusFromTouch();
            setSupportActionBar(this.toolbar);
            this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
            if (SharepreferenceDBHandler.getSelectedLanguage(this.context).equalsIgnoreCase("Arabic")) {
            }
            loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
            this.programTimeFormat = new SimpleDateFormat(loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, ""), Locale.US);
            this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
            findViewById(R.id.exo_next).setOnClickListener(this);
            findViewById(R.id.exo_prev).setOnClickListener(this);
            findViewById(R.id.exo_multiplayer).setOnClickListener(this);
            this.fullScreen = true;
            this.rl_categories_view = (RelativeLayout) findViewById(R.id.rl_categories_view);
            this.btn_cat_back.setOnClickListener(this);
            this.btn_cat_forward.setOnClickListener(this);
            this.vlcplayButton = findViewById(R.id.exo_play);
            if (this.vlcplayButton != null) {
            }
            this.unad = Utils.ukde(MeasureHelper.pnm());
            this.vlcpauseButton = findViewById(R.id.exo_pause);
            if (this.vlcpauseButton != null) {
            }
            uk = getApplicationName(this.context);
            this.vlcprevButton = findViewById(R.id.exo_prev);
            if (this.MultiPlayerType != null) {
            }
            if (this.vlcprevButton != null) {
            }
            this.vlcnextButton = findViewById(R.id.exo_next);
            this.multi = findViewById(R.id.exo_multiplayer);
            if (this.MultiPlayerType != null) {
            }
            if (this.vlcnextButton != null) {
            }
            if (this.multi != null) {
            }
            una = getApplicationContext().getPackageName();
            this.vlcchannelListButton = findViewById(R.id.btn_list);
            if (this.vlcchannelListButton != null) {
            }
            this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
            this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
            this.vlcaspectRatio = findViewById(R.id.btn_aspect_ratio);
            if (this.vlcaspectRatio != null) {
            }
            this.vlc_exo_subtitle = findViewById(R.id.exo_subtitle);
            if (this.vlc_exo_subtitle != null) {
            }
            this.ukd = Utils.ukde(FileMediaDataSource.apn());
            this.dt = new Date();
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
            this.liveListDetailUnlcked = new ArrayList<>();
            this.liveListDetailUnlckedDetail = new ArrayList<>();
            this.liveListDetailAvailable = new ArrayList<>();
            this.liveListDetail = new ArrayList<>();
            this.liveListDetailUnlckedChannels = new ArrayList<>();
            this.liveListDetailUnlckedDetailChannels = new ArrayList<>();
            this.liveListDetailAvailableChannels = new ArrayList<>();
            this.liveListDetailAvailableChannels_Temp = new ArrayList<>();
            this.AvailableChannelsFirstOpenedCat = new ArrayList<>();
            this.liveListDetailAvailableNewChannels = new ArrayList<>();
            this.liveListDetailChannels = new ArrayList<>();
            this.allLiveCategories = this.liveStreamDBHandler.getAllliveCategories();
            LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel42 = new LiveStreamCategoryIdDBModel();
            LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel222 = new LiveStreamCategoryIdDBModel();
            LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel322 = new LiveStreamCategoryIdDBModel();
            liveStreamCategoryIdDBModel42.setLiveStreamCategoryID(AppConst.PASSWORD_UNSET);
            liveStreamCategoryIdDBModel42.setLiveStreamCategoryName(this.context.getResources().getString(R.string.all));
            liveStreamCategoryIdDBModel222.setLiveStreamCategoryID("-1");
            liveStreamCategoryIdDBModel222.setLiveStreamCategoryName(this.context.getResources().getString(R.string.favourites));
            this.countUncat = this.liveStreamDBHandler.getUncatCount("-2", "live");
            liveStreamCategoryIdDBModel322.setLiveStreamCategoryID("-2");
            liveStreamCategoryIdDBModel322.setLiveStreamCategoryName(this.context.getResources().getString(R.string.uncategories));
            this.allLiveCategories.add(this.allLiveCategories.size(), liveStreamCategoryIdDBModel322);
            this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context));
            this.listPassword = getPasswordSetCategories();
            hashCode = stringExtra222.hashCode();
            if (hashCode == 48) {
            }
            c2 = 65535;
            switch (c2) {
            }
            this.mVideoView.setEPGHandler(this.handlerUpdateEPGData);
            this.mVideoView.setContext(this.context);
            this.timeShift = Long.valueOf(getTimeShiftMilliSeconds(this.context));
            this.mVideoView.setTimeShift(getTimeShiftMilliSeconds(this.context));
        }
        c = 65535;
        switch (c) {
        }
        this.allowedFormat = this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
        if (this.allowedFormat == null) {
        }
        if (this.allowedFormat == null) {
        }
        if (this.allowedFormat != null) {
        }
        this.allowedFormat = "";
        int intExtra222 = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
        this.m3uVideoURL = getIntent().getStringExtra("VIDEO_URL");
        this.video_num = getIntent().getIntExtra("VIDEO_NUM", 0);
        getIntent().getStringExtra("STREAM_TYPE");
        String stringExtra522 = getIntent().getStringExtra("VIDEO_TITLE");
        String stringExtra2222 = getIntent().getStringExtra("OPENED_CAT_ID");
        String stringExtra3222 = getIntent().getStringExtra("EPG_CHANNEL_ID");
        this.MultiPlayerType = getIntent().getStringExtra("MultiPlayer");
        String stringExtra4222 = getIntent().getStringExtra("EPG_CHANNEL_LOGO");
        SharedPreferences.Editor edit222 = this.loginPreferencesSharedPref_opened_video.edit();
        edit222.putInt(AppConst.LOGIN_PREF_OPENED_VIDEO_ID, intExtra222);
        edit222.putString("LOGIN_PREF_OPENED_VIDEO_URL_M3U", this.m3uVideoURL);
        edit222.putString("name", stringExtra522);
        edit222.putInt(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_NUM, this.video_num);
        edit222.putString(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_EPG_CHANNEL_ID, stringExtra3222);
        edit222.putString(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_CHANNEL_LOGO, stringExtra4222);
        edit222.apply();
        if (!this.allowedFormat.equals("")) {
        }
        this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
        this.liveStreamDBHandler = new LiveStreamDBHandler(this);
        this.database = new DatabaseHandler(this.context);
        this.handler = new Handler();
        this.handlerHeaderFooter = new Handler();
        this.handlerUpdateEPGData = new Handler();
        this.handlerJumpChannel = new Handler();
        this.mVideoView = (NSTIJKPlayerEPG) findViewById(R.id.video_view);
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
        this.rl_nst_player_sky_layout = (RelativeLayout) findViewById(R.id.rl_nst_player_sky_layout);
        this.ll_layout_to_hide1 = (LinearLayout) findViewById(R.id.ll_layout_to_hide_1);
        this.ll_layout_to_hide4 = (LinearLayout) findViewById(R.id.ll_layout_to_hide_4);
        this.rl_layout_to_hide5 = (RelativeLayout) findViewById(R.id.rl_layout_to_hide_5);
        this.rl_layout_to_hide_6 = (RelativeLayout) findViewById(R.id.rl_layout_to_hide_6);
        this.rl_footer_info = (RelativeLayout) findViewById(R.id.rl_footer_info);
        this.rl_video_box = (RelativeLayout) findViewById(R.id.rl_video_box);
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
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Exception | UnsatisfiedLinkError unused) {
        }
        this.toolbar.inflateMenu(R.menu.menu_search);
        this.appbarToolbar.requestFocusFromTouch();
        setSupportActionBar(this.toolbar);
        this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
        if (SharepreferenceDBHandler.getSelectedLanguage(this.context).equalsIgnoreCase("Arabic")) {
        }
        loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
        this.programTimeFormat = new SimpleDateFormat(loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, ""), Locale.US);
        this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
        findViewById(R.id.exo_next).setOnClickListener(this);
        findViewById(R.id.exo_prev).setOnClickListener(this);
        findViewById(R.id.exo_multiplayer).setOnClickListener(this);
        this.fullScreen = true;
        this.rl_categories_view = (RelativeLayout) findViewById(R.id.rl_categories_view);
        this.btn_cat_back.setOnClickListener(this);
        this.btn_cat_forward.setOnClickListener(this);
        this.vlcplayButton = findViewById(R.id.exo_play);
        if (this.vlcplayButton != null) {
        }
        this.unad = Utils.ukde(MeasureHelper.pnm());
        this.vlcpauseButton = findViewById(R.id.exo_pause);
        if (this.vlcpauseButton != null) {
        }
        uk = getApplicationName(this.context);
        this.vlcprevButton = findViewById(R.id.exo_prev);
        if (this.MultiPlayerType != null) {
        }
        if (this.vlcprevButton != null) {
        }
        this.vlcnextButton = findViewById(R.id.exo_next);
        this.multi = findViewById(R.id.exo_multiplayer);
        if (this.MultiPlayerType != null) {
        }
        if (this.vlcnextButton != null) {
        }
        if (this.multi != null) {
        }
        una = getApplicationContext().getPackageName();
        this.vlcchannelListButton = findViewById(R.id.btn_list);
        if (this.vlcchannelListButton != null) {
        }
        this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.vlcaspectRatio = findViewById(R.id.btn_aspect_ratio);
        if (this.vlcaspectRatio != null) {
        }
        this.vlc_exo_subtitle = findViewById(R.id.exo_subtitle);
        if (this.vlc_exo_subtitle != null) {
        }
        this.ukd = Utils.ukde(FileMediaDataSource.apn());
        this.dt = new Date();
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
        this.liveListDetailUnlcked = new ArrayList<>();
        this.liveListDetailUnlckedDetail = new ArrayList<>();
        this.liveListDetailAvailable = new ArrayList<>();
        this.liveListDetail = new ArrayList<>();
        this.liveListDetailUnlckedChannels = new ArrayList<>();
        this.liveListDetailUnlckedDetailChannels = new ArrayList<>();
        this.liveListDetailAvailableChannels = new ArrayList<>();
        this.liveListDetailAvailableChannels_Temp = new ArrayList<>();
        this.AvailableChannelsFirstOpenedCat = new ArrayList<>();
        this.liveListDetailAvailableNewChannels = new ArrayList<>();
        this.liveListDetailChannels = new ArrayList<>();
        this.allLiveCategories = this.liveStreamDBHandler.getAllliveCategories();
        LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel422 = new LiveStreamCategoryIdDBModel();
        LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel2222 = new LiveStreamCategoryIdDBModel();
        LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel3222 = new LiveStreamCategoryIdDBModel();
        liveStreamCategoryIdDBModel422.setLiveStreamCategoryID(AppConst.PASSWORD_UNSET);
        liveStreamCategoryIdDBModel422.setLiveStreamCategoryName(this.context.getResources().getString(R.string.all));
        liveStreamCategoryIdDBModel2222.setLiveStreamCategoryID("-1");
        liveStreamCategoryIdDBModel2222.setLiveStreamCategoryName(this.context.getResources().getString(R.string.favourites));
        this.countUncat = this.liveStreamDBHandler.getUncatCount("-2", "live");
        liveStreamCategoryIdDBModel3222.setLiveStreamCategoryID("-2");
        liveStreamCategoryIdDBModel3222.setLiveStreamCategoryName(this.context.getResources().getString(R.string.uncategories));
        this.allLiveCategories.add(this.allLiveCategories.size(), liveStreamCategoryIdDBModel3222);
        this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context));
        this.listPassword = getPasswordSetCategories();
        hashCode = stringExtra2222.hashCode();
        if (hashCode == 48) {
        }
        c2 = 65535;
        switch (c2) {
        }
        this.mVideoView.setEPGHandler(this.handlerUpdateEPGData);
        this.mVideoView.setContext(this.context);
        this.timeShift = Long.valueOf(getTimeShiftMilliSeconds(this.context));
        this.mVideoView.setTimeShift(getTimeShiftMilliSeconds(this.context));
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

    public void noChannelFound() {
        hideTitleBarAndFooter();
        this.app_video_status.setVisibility(0);
        this.app_video_status_text.setText(this.context.getResources().getString(R.string.no_channel_found));
        if (AppConst.WATER_MARK.booleanValue()) {
            findViewById(R.id.watrmrk).setVisibility(8);
        }
    }

    public void allChannels() {
        int parentalStatusCount = this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context));
        this.allStreams = this.liveStreamDBHandler.getAllLiveStreasWithSkyId(AppConst.PASSWORD_UNSET, "live");
        if (parentalStatusCount <= 0 || this.allStreams == null) {
            this.liveListDetailAvailableChannels = this.allStreams;
            return;
        }
        if (this.listPassword != null) {
            this.liveListDetailUnlckedDetailChannels = getUnlockedChannels(this.allStreams, this.listPassword);
        }
        this.liveListDetailAvailableChannels = this.liveListDetailUnlckedDetailChannels;
    }

    public void allFavourites() {
        this.allStreams_with_cat = new ArrayList<>();
        getFavourites();
        this.liveListDetailAvailableChannels = this.allStreams_with_cat;
    }

    public void allChannelsWithCat(String str) {
        if (this.liveStreamDBHandler != null) {
            this.AvailableChannelsFirstOpenedCat = this.liveStreamDBHandler.getAllLiveStreasWithSkyId(str, "live");
        }
        this.liveListDetailAvailableChannels = this.AvailableChannelsFirstOpenedCat;
    }

    @Override // com.afollestad.materialdialogs.folderselector.FileChooserDialog.FileCallback
    public void onFileSelection(@NonNull FileChooserDialog fileChooserDialog, @NonNull File file) {
        fileChooserDialog.getTag();
    }

    public int getIndexOfMovies(ArrayList<LiveStreamsDBModel> arrayList, int i) {
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (Utils.parseIntZero(arrayList.get(i2).getNum()) == i) {
                return i2;
            }
        }
        return 0;
    }

    private void playFirstTime(ArrayList<LiveStreamsDBModel> arrayList, int i) {
        if (arrayList != null && arrayList.size() > 0 && this.opened_vlc_id < arrayList.size()) {
            if (this.opened_vlc_id == 0) {
                this.opened_vlc_id = getIndexOfMovies(arrayList, i);
            }
            String name = arrayList.get(this.opened_vlc_id).getName();
            String num = arrayList.get(this.opened_vlc_id).getNum();
            int parseIntMinusOne = Utils.parseIntMinusOne(arrayList.get(this.opened_vlc_id).getStreamId());
            String url2 = arrayList.get(this.opened_vlc_id).getUrl();
            String ukde = Utils.ukde(TableLayoutBinder.aW5nIGl() + TableLayoutBinder.mu());
            final String epgChannelId = arrayList.get(this.opened_vlc_id).getEpgChannelId();
            final String streamIcon = arrayList.get(this.opened_vlc_id).getStreamIcon();
            if (streamIcon.equals("") || streamIcon.isEmpty()) {
                this.channelLogo.setImageDrawable(this.context.getResources().getDrawable(R.drawable.logo_placeholder_white));
            } else {
                Picasso.with(this.context).load(streamIcon).placeholder((int) R.drawable.logo_placeholder_white).error((int) R.drawable.logo_placeholder_white).into(this.channelLogo);
            }
            if (this.loginPrefsEditor != null) {
                this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(arrayList.get(this.opened_vlc_id).getStreamId()));
                this.loginPrefsEditor.putString("LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_M3U", String.valueOf(arrayList.get(this.opened_vlc_id).getUrl()));
                this.loginPrefsEditor.apply();
            }
            if (this.loginPrefsEditorPosition != null) {
                this.loginPrefsEditorPosition.putInt(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, this.opened_vlc_id);
                this.loginPrefsEditorPosition.apply();
            }
            this.currentProgramStreamID = parseIntMinusOne;
            NSTIJKPlayerEPG nSTIJKPlayerEPG = this.mVideoView;
            nSTIJKPlayerEPG.setTitle(num + " - " + name);
            this.mVideoView.setCurrentWindowIndex(this.opened_vlc_id);
            if (df(this.fr, this.fr.format(new Date(RecentMediaStorage.cit(this.context))), this.df.format(this.dt)) >= ((long) RecentMediaStorage.ux()) && this.ukd != null && this.unad != null && (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad)))) {
                this.rq = false;
                this.$.id(R.id.app_video_status).visible();
                ViewQuery id = this.$.id(R.id.app_video_status_text);
                id.text(ukde + this.elv + this.fmw);
            }
            try {
                if (this.rq.booleanValue()) {
                    if (this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                        this.mVideoView.setVideoURI(Uri.parse(url2), this.fullScreen, name);
                    } else {
                        NSTIJKPlayerEPG nSTIJKPlayerEPG2 = this.mVideoView;
                        nSTIJKPlayerEPG2.setVideoURI(Uri.parse(this.mFilePath + parseIntMinusOne + this.allowedFormat), this.fullScreen, name);
                    }
                    this.mVideoView.retryCount = 0;
                    this.mVideoView.retrying = false;
                    this.handlerUpdateEPGData.removeCallbacksAndMessages(null);
                    loadingEPGData();
                    this.handlerUpdateEPGData.postDelayed(new Runnable() {
                        /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerEPGActivity.AnonymousClass1 */

                        public void run() {
                            NSTIJKPlayerEPGActivity.this.currentEpgChannelID = epgChannelId;
                            NSTIJKPlayerEPGActivity.this.currentChannelLogo = streamIcon;
                            NSTIJKPlayerEPGActivity.this.mVideoView.setCurrentEpgChannelID(NSTIJKPlayerEPGActivity.this.currentEpgChannelID);
                            NSTIJKPlayerEPGActivity.this.mVideoView.setCurrentChannelLogo(NSTIJKPlayerEPGActivity.this.currentChannelLogo);
                            NSTIJKPlayerEPGActivity.this.updateEPGData(NSTIJKPlayerEPGActivity.this.currentEpgChannelID, NSTIJKPlayerEPGActivity.this.currentChannelLogo);
                        }
                    }, 300);
                }
                hideTitleBarAndFooter();
            } catch (Exception unused) {
            }
            hideTitleBarAndFooter();
        }
    }

    public static String getApplicationName(Context context2) {
        return String.valueOf(context2.getApplicationInfo().loadLabel(context2.getPackageManager()));
    }

    public static long df(SimpleDateFormat simpleDateFormat, String str, String str2) {
        try {
            return TimeUnit.DAYS.convert(simpleDateFormat.parse(str2).getTime() - simpleDateFormat.parse(str).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
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

    private ArrayList<LiveStreamsDBModel> getUnlockedChannels(ArrayList<LiveStreamsDBModel> arrayList, ArrayList<String> arrayList2) {
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
        return this.liveListDetailUnlckedChannels;
    }

    public boolean getUnlockedChannelsBoolean(ArrayList<LiveStreamsDBModel> arrayList, ArrayList<String> arrayList2) {
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
    }

    public void getFavourites() {
        if (this.currentAPPType.equals(AppConst.TYPE_M3U)) {
            if (this.liveStreamDBHandler != null) {
                ArrayList<FavouriteM3UModel> favouriteM3U = this.liveStreamDBHandler.getFavouriteM3U("live");
                ArrayList<LiveStreamsDBModel> arrayList = new ArrayList<>();
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
                }
            }
        } else if (this.database != null) {
            ArrayList<FavouriteDBModel> allFavourites = this.database.getAllFavourites("live", SharepreferenceDBHandler.getUserID(this.context));
            ArrayList<LiveStreamsDBModel> arrayList2 = new ArrayList<>();
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
    }

    public void updateEPG(String str, String str2, LiveStreamDBHandler liveStreamDBHandler2, Context context2, ProgressBar progressBar2, TextView textView, TextView textView2, TextView textView3, TextView textView4, ImageView imageView, Long l) {
        this.liveStreamDBHandler = liveStreamDBHandler2;
        this.context = context2;
        this.progressBar = progressBar2;
        this.currentProgram = textView;
        this.currentProgramTime = textView2;
        this.nextProgram = textView3;
        this.nextProgramTime = textView4;
        this.channelLogo = imageView;
        this.timeShift = l;
        loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
        this.programTimeFormat = new SimpleDateFormat(loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, ""), Locale.US);
        updateEPGData(str, str2);
    }

    public long getTimeShiftMilliSeconds(Context context2) {
        if (context2 == null) {
            return 0;
        }
        loginPreferencesAfterLogin = context2.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        if (loginPreferencesAfterLogin != null) {
            return (long) Utils.getMilliSeconds(loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_SELECTED_EPG_SHIFT, ""));
        }
        return 0;
    }

    public boolean isEventVisible(long j, long j2, Context context2) {
        if (context2 == null) {
            return false;
        }
        long millis = LocalDateTime.now().toDateTime().getMillis();
        if (this.timeShift != null) {
            millis += this.timeShift.longValue();
        }
        if (j > millis || j2 < millis) {
            return false;
        }
        return true;
    }

    public int getPercentageLeft(long j, long j2, Context context2) {
        if (context2 == null) {
            return 0;
        }
        long millis = LocalDateTime.now().toDateTime().getMillis();
        if (this.timeShift != null) {
            millis += this.timeShift.longValue();
        }
        if (j >= j2 || millis >= j2) {
            return 0;
        }
        if (millis <= j) {
            return 100;
        }
        return (int) (((j2 - millis) * 100) / (j2 - j));
    }

    public void updateEPGData(String str, String str2) {
        int percentageLeft;
        this.hideEPGData = true;
        if (str2 != null && !str2.equals("")) {
            Picasso.with(this.context).load(str2).resize(80, 55).placeholder((int) R.drawable.logo_placeholder_white).into(this.channelLogo);
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.channelLogo.setImageDrawable(this.context.getResources().getDrawable(R.drawable.logo_placeholder_white, null));
        }
        if (this.liveStreamDBHandler != null) {
            if (str == null || str.equals("")) {
                this.hideEPGData = true;
            } else {
                ArrayList<XMLTVProgrammePojo> epg = this.liveStreamDBHandler.getEPG(str);
                if (epg != null) {
                    int i = 0;
                    while (true) {
                        if (i >= epg.size()) {
                            break;
                        }
                        String start = epg.get(i).getStart();
                        String stop = epg.get(i).getStop();
                        String title2 = epg.get(i).getTitle();
                        Long valueOf = Long.valueOf(Utils.epgTimeConverter(start));
                        Long valueOf2 = Long.valueOf(Utils.epgTimeConverter(stop));
                        if (!isEventVisible(valueOf.longValue(), valueOf2.longValue(), this) || (percentageLeft = getPercentageLeft(valueOf.longValue(), valueOf2.longValue(), this)) == 0) {
                            i++;
                        } else {
                            int i2 = 100 - percentageLeft;
                            if (i2 == 0 || title2 == null || title2.equals("")) {
                                this.hideEPGData = true;
                            } else {
                                this.hideEPGData = false;
                                this.progressBar.setProgress(i2);
                                TextView textView = this.currentProgram;
                                textView.setText(this.context.getResources().getString(R.string.now) + " " + title2);
                                TextView textView2 = this.currentProgramTime;
                                textView2.setText(this.programTimeFormat.format(valueOf) + " - " + this.programTimeFormat.format(valueOf2));
                                int i3 = i + 1;
                                if (i3 < epg.size()) {
                                    String start2 = epg.get(i3).getStart();
                                    String stop2 = epg.get(i3).getStop();
                                    String title3 = epg.get(i3).getTitle();
                                    Long valueOf3 = Long.valueOf(Utils.epgTimeConverter(start2));
                                    Long valueOf4 = Long.valueOf(Utils.epgTimeConverter(stop2));
                                    TextView textView3 = this.nextProgramTime;
                                    textView3.setText(this.programTimeFormat.format(valueOf3) + " - " + this.programTimeFormat.format(valueOf4));
                                    TextView textView4 = this.nextProgram;
                                    textView4.setText(this.context.getResources().getString(R.string.next) + " " + title3);
                                }
                            }
                        }
                    }
                } else {
                    this.hideEPGData = true;
                }
            }
        }
        if (this.hideEPGData) {
            hideEPGData();
        }
    }

    private void hideEPGData() {
        this.progressBar.setProgress(0);
        this.currentProgram.setText(this.context.getResources().getString(R.string.now_program_found));
        this.currentProgramTime.setText("");
        this.nextProgram.setText(this.context.getResources().getString(R.string.next_program_found));
        this.nextProgramTime.setText("");
    }

    public void loadingEPGData() {
        this.progressBar.setProgress(0);
        this.currentProgram.setText(this.context.getResources().getString(R.string.now_loading));
        this.currentProgramTime.setText("");
        this.nextProgram.setText(this.context.getResources().getString(R.string.next_loading));
        this.nextProgramTime.setText("");
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onPause() {
        super.onPause();
    }

    public void hideSystemUi() {
        try {
            if (this.mVideoView != null) {
                this.mVideoView.setSystemUiVisibility(4871);
            }
        } catch (Exception unused) {
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        if (this.mVideoView != null) {
            this.mVideoView.hideSystemUi();
        }
        if (this.catID != null && !this.catID.equals("") && this.catID.equals(AppConst.PASSWORD_UNSET)) {
            allChannels();
            if (this.liveListDetailAvailableChannels == null || this.liveListDetailAvailableChannels.size() == 0) {
                noChannelFound();
            } else {
                playFirstTime(this.liveListDetailAvailableChannels, this.video_num);
            }
        } else if (this.catID == null || this.catID.equals("") || !this.catID.equals("-1")) {
            allChannelsWithCat(this.catID);
            if (this.liveListDetailAvailableChannels == null || this.liveListDetailAvailableChannels.size() == 0) {
                noChannelFound();
            } else {
                playFirstTime(this.liveListDetailAvailableChannels, this.video_num);
            }
        } else {
            allFavourites();
            if (this.liveListDetailAvailableChannels == null || this.liveListDetailAvailableChannels.size() == 0) {
                noChannelFound();
            } else {
                playFirstTime(this.liveListDetailAvailableChannels, this.video_num);
            }
        }
        fullScreenVideoLayout();
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onDestroy() {
        super.onDestroy();
        try {
            if (this.mVideoView != null) {
                release();
            }
        } catch (Exception unused) {
        }
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onStop() {
        super.onStop();
        try {
            if (this.mVideoView != null) {
                this.mVideoView.fullScreenValue(Boolean.valueOf(this.fullScreen));
                release();
            }
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
            if (r6 == r3) goto L_0x015a
            r3 = 66
            if (r6 == r3) goto L_0x0127
            r3 = 79
            if (r6 == r3) goto L_0x015a
            r3 = 82
            if (r6 == r3) goto L_0x011a
            switch(r6) {
                case 7: goto L_0x007c;
                case 8: goto L_0x007c;
                case 9: goto L_0x007c;
                case 10: goto L_0x007c;
                case 11: goto L_0x007c;
                case 12: goto L_0x007c;
                case 13: goto L_0x007c;
                case 14: goto L_0x007c;
                case 15: goto L_0x007c;
                case 16: goto L_0x007c;
                default: goto L_0x001e;
            }
        L_0x001e:
            switch(r6) {
                case 21: goto L_0x0075;
                case 22: goto L_0x006e;
                case 23: goto L_0x0127;
                default: goto L_0x0021;
            }
        L_0x0021:
            switch(r6) {
                case 85: goto L_0x015a;
                case 86: goto L_0x004d;
                default: goto L_0x0024;
            }
        L_0x0024:
            switch(r6) {
                case 126: goto L_0x002c;
                case 127: goto L_0x004d;
                default: goto L_0x0027;
            }
        L_0x0027:
            boolean r6 = super.onKeyUp(r6, r7)
            return r6
        L_0x002c:
            if (r0 == 0) goto L_0x004c
            com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerEPG r6 = r5.mVideoView
            boolean r6 = r6.isPlaying()
            if (r6 != 0) goto L_0x004c
            r5.stopHeaderFooterHandler()
            r5.showTitleBarAndFooter()
            r5.runHeaderFooterHandler()
            com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerEPG r6 = r5.mVideoView
            r6.start()
            r5.playerStartIconsUpdate()
            android.view.View r6 = r5.vlcpauseButton
            r6.requestFocus()
        L_0x004c:
            return r2
        L_0x004d:
            if (r0 == 0) goto L_0x006d
            com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerEPG r6 = r5.mVideoView
            boolean r6 = r6.isPlaying()
            if (r6 == 0) goto L_0x006d
            r5.stopHeaderFooterHandler()
            r5.showTitleBarAndFooter()
            r5.runHeaderFooterHandler()
            com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerEPG r6 = r5.mVideoView
            r6.pause()
            r5.playerPauseIconsUpdate()
            android.view.View r6 = r5.vlcplayButton
            r6.requestFocus()
        L_0x006d:
            return r2
        L_0x006e:
            r5.stopHeaderFooterHandler()
            r5.runHeaderFooterHandler()
            return r2
        L_0x0075:
            r5.stopHeaderFooterHandler()
            r5.runHeaderFooterHandler()
            return r2
        L_0x007c:
            java.lang.String r7 = r5.MultiPlayerType
            if (r7 == 0) goto L_0x0119
            java.lang.String r7 = r5.MultiPlayerType
            java.lang.String r0 = "false"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L_0x0119
            boolean r7 = r5.fullScreen
            if (r7 != 0) goto L_0x0090
            goto L_0x0119
        L_0x0090:
            android.os.Handler r7 = r5.handlerJumpChannel
            r0 = 0
            r7.removeCallbacksAndMessages(r0)
            r7 = 7
            if (r6 != r7) goto L_0x009f
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r6.append(r1)
            goto L_0x00fd
        L_0x009f:
            r0 = 8
            if (r6 != r0) goto L_0x00a9
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r6.append(r2)
            goto L_0x00fd
        L_0x00a9:
            r3 = 9
            if (r6 != r3) goto L_0x00b4
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r7 = 2
            r6.append(r7)
            goto L_0x00fd
        L_0x00b4:
            r4 = 10
            if (r6 != r4) goto L_0x00bf
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r7 = 3
            r6.append(r7)
            goto L_0x00fd
        L_0x00bf:
            r4 = 11
            if (r6 != r4) goto L_0x00ca
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r7 = 4
            r6.append(r7)
            goto L_0x00fd
        L_0x00ca:
            r4 = 12
            if (r6 != r4) goto L_0x00d5
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r7 = 5
            r6.append(r7)
            goto L_0x00fd
        L_0x00d5:
            r4 = 13
            if (r6 != r4) goto L_0x00e0
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r7 = 6
            r6.append(r7)
            goto L_0x00fd
        L_0x00e0:
            r4 = 14
            if (r6 != r4) goto L_0x00ea
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r6.append(r7)
            goto L_0x00fd
        L_0x00ea:
            r7 = 15
            if (r6 != r7) goto L_0x00f4
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r6.append(r0)
            goto L_0x00fd
        L_0x00f4:
            r7 = 16
            if (r6 != r7) goto L_0x00fd
            java.lang.StringBuilder r6 = r5.jumpToChannel
            r6.append(r3)
        L_0x00fd:
            android.widget.TextView r6 = r5.app_channel_jumping_text
            java.lang.StringBuilder r7 = r5.jumpToChannel
            java.lang.String r7 = r7.toString()
            r6.setText(r7)
            android.widget.LinearLayout r6 = r5.ll_channel_jumping
            r6.setVisibility(r1)
            android.os.Handler r6 = r5.handlerJumpChannel
            com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerEPGActivity$2 r7 = new com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerEPGActivity$2
            r7.<init>()
            r0 = 3000(0xbb8, double:1.482E-320)
            r6.postDelayed(r7, r0)
        L_0x0119:
            return r2
        L_0x011a:
            android.view.Menu r6 = r5.menuSelect
            if (r6 == 0) goto L_0x0126
            android.view.Menu r6 = r5.menuSelect
            r7 = 2131362161(0x7f0a0171, float:1.8344095E38)
            r6.performIdentifierAction(r7, r1)
        L_0x0126:
            return r2
        L_0x0127:
            boolean r6 = r5.fullScreen
            if (r6 != 0) goto L_0x012c
            goto L_0x0159
        L_0x012c:
            r6 = 2131361935(0x7f0a008f, float:1.8343636E38)
            android.view.View r6 = r5.findViewById(r6)
            int r6 = r6.getVisibility()
            if (r6 != 0) goto L_0x013d
            r5.hideTitleBarAndFooter()
            goto L_0x0159
        L_0x013d:
            r5.stopHeaderFooterHandler()
            r5.showTitleBarAndFooter()
            r5.runHeaderFooterHandler()
            com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerEPG r6 = r5.mVideoView
            boolean r6 = r6.isPlaying()
            if (r6 == 0) goto L_0x0154
            android.view.View r6 = r5.vlcpauseButton
            r6.requestFocus()
            goto L_0x0159
        L_0x0154:
            android.view.View r6 = r5.vlcplayButton
            r6.requestFocus()
        L_0x0159:
            return r2
        L_0x015a:
            if (r0 == 0) goto L_0x017b
            com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerEPG r6 = r5.mVideoView
            boolean r6 = r6.isPlaying()
            if (r6 != 0) goto L_0x017b
            r5.stopHeaderFooterHandler()
            r5.showTitleBarAndFooter()
            r5.runHeaderFooterHandler()
            com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerEPG r6 = r5.mVideoView
            r6.start()
            r5.playerStartIconsUpdate()
            android.view.View r6 = r5.vlcpauseButton
            r6.requestFocus()
            goto L_0x0191
        L_0x017b:
            r5.stopHeaderFooterHandler()
            r5.showTitleBarAndFooter()
            r5.runHeaderFooterHandler()
            com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerEPG r6 = r5.mVideoView
            r6.pause()
            r5.playerPauseIconsUpdate()
            android.view.View r6 = r5.vlcplayButton
            r6.requestFocus()
        L_0x0191:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerEPGActivity.onKeyUp(int, android.view.KeyEvent):boolean");
    }

    @Override // android.support.v7.app.AppCompatActivity
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        keyEvent.getRepeatCount();
        keyEvent.getAction();
        switch (i) {
            case 19:
            case 166:
                stopHeaderFooterHandler();
                showTitleBarAndFooter();
                runHeaderFooterHandler();
                findViewById(R.id.exo_next).performClick();
                return true;
            case 20:
            case 167:
                stopHeaderFooterHandler();
                showTitleBarAndFooter();
                runHeaderFooterHandler();
                findViewById(R.id.exo_prev).performClick();
                return true;
            default:
                return super.onKeyDown(i, keyEvent);
        }
    }

    public void runHeaderFooterHandler() {
        this.handlerHeaderFooter.postDelayed(new Runnable() {
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerEPGActivity.AnonymousClass3 */

            public void run() {
                NSTIJKPlayerEPGActivity.this.hideTitleBarAndFooter();
                if (AppConst.WATER_MARK.booleanValue()) {
                    NSTIJKPlayerEPGActivity.this.findViewById(R.id.watrmrk).setVisibility(0);
                }
            }
        }, 7000);
    }

    public void stopHeaderFooterHandler() {
        this.handlerHeaderFooter.removeCallbacksAndMessages(null);
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.app_video_box:
                try {
                    fullScreenVideoLayout();
                    return;
                } catch (Exception e) {
                    Log.e("NSTIJPLAYERACTIVTY", "exection " + e);
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
                } catch (Exception e2) {
                    Log.e("NSTIJPLAYERACTIVTY", "exection " + e2);
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
                } catch (Exception e3) {
                    Log.e("NSTIJPLAYERACTIVTY", "exection " + e3);
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
                } catch (Exception e4) {
                    Log.e("NSTIJPLAYERACTIVTY", "exection " + e4);
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
                } catch (Exception e5) {
                    Log.e("NSTIJPLAYERACTIVTY", "exection " + e5);
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
                } catch (Exception e6) {
                    Log.e("NSTIJPLAYERACTIVTY", "exection " + e6);
                    return;
                }
            case R.id.exo_multiplayer:
                findViewById(R.id.app_video_top_box).setVisibility(8);
                onBackPressed();
                return;
            case R.id.exo_next:
                try {
                    if (this.MultiPlayerType != null && this.MultiPlayerType.equals("false")) {
                        if (findViewById(R.id.controls).getVisibility() == 0) {
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
                                    final String num = this.liveListDetailAvailableChannels.get(currentWindowIndex).getNum();
                                    final String url2 = this.liveListDetailAvailableChannels.get(currentWindowIndex).getUrl();
                                    NSTIJKPlayerEPG nSTIJKPlayerEPG = this.mVideoView;
                                    nSTIJKPlayerEPG.setTitle(num + " - " + name);
                                    this.opened_vlc_id = currentWindowIndex;
                                    this.loginPrefsEditorAudio.clear();
                                    this.loginPrefsEditorAudio.apply();
                                    this.loginPrefsEditorVideo.clear();
                                    this.loginPrefsEditorVideo.apply();
                                    this.loginPrefsEditorSubtitle.clear();
                                    this.loginPrefsEditorSubtitle.apply();
                                    if (this.rq.booleanValue()) {
                                        this.handler.postDelayed(new Runnable() {
                                            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerEPGActivity.AnonymousClass5 */

                                            public void run() {
                                                NSTIJKPlayerEPGActivity.this.release();
                                                NSTIJKPlayerEPGActivity.this.$.id(R.id.exo_pause).gone();
                                                NSTIJKPlayerEPGActivity.this.$.id(R.id.exo_play).visible();
                                                SharedPreferences.Editor edit = NSTIJKPlayerEPGActivity.this.loginPreferencesSharedPref_opened_video.edit();
                                                if (NSTIJKPlayerEPGActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                                    edit.putString("LOGIN_PREF_OPENED_VIDEO_URL_M3U", url2);
                                                    NSTIJKPlayerEPGActivity.this.mVideoView.setVideoURI(Uri.parse(url2), NSTIJKPlayerEPGActivity.this.fullScreen, name);
                                                    NSTIJKPlayerEPG access$000 = NSTIJKPlayerEPGActivity.this.mVideoView;
                                                    access$000.setTitle(num + " - " + name);
                                                } else {
                                                    edit.putInt(AppConst.LOGIN_PREF_OPENED_VIDEO_ID, Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerEPGActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex)).getStreamId()));
                                                    NSTIJKPlayerEPG access$0002 = NSTIJKPlayerEPGActivity.this.mVideoView;
                                                    access$0002.setVideoURI(Uri.parse(NSTIJKPlayerEPGActivity.this.mFilePath + Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerEPGActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex)).getStreamId()) + NSTIJKPlayerEPGActivity.this.allowedFormat), NSTIJKPlayerEPGActivity.this.fullScreen, name);
                                                    NSTIJKPlayerEPG access$0003 = NSTIJKPlayerEPGActivity.this.mVideoView;
                                                    access$0003.setTitle(num + " - " + name);
                                                }
                                                edit.apply();
                                                NSTIJKPlayerEPGActivity.this.mVideoView.retryCount = 0;
                                                NSTIJKPlayerEPGActivity.this.mVideoView.retrying = false;
                                                NSTIJKPlayerEPGActivity.this.mVideoView.start();
                                                NSTIJKPlayerEPGActivity.this.handlerUpdateEPGData.removeCallbacksAndMessages(null);
                                                NSTIJKPlayerEPGActivity.this.loadingEPGData();
                                                NSTIJKPlayerEPGActivity.this.currentEpgChannelID = ((LiveStreamsDBModel) NSTIJKPlayerEPGActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex)).getEpgChannelId();
                                                NSTIJKPlayerEPGActivity.this.currentChannelLogo = ((LiveStreamsDBModel) NSTIJKPlayerEPGActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex)).getStreamIcon();
                                                NSTIJKPlayerEPGActivity.this.mVideoView.setCurrentEpgChannelID(NSTIJKPlayerEPGActivity.this.currentEpgChannelID);
                                                NSTIJKPlayerEPGActivity.this.mVideoView.setCurrentChannelLogo(NSTIJKPlayerEPGActivity.this.currentChannelLogo);
                                                NSTIJKPlayerEPGActivity.this.updateEPGData(NSTIJKPlayerEPGActivity.this.currentEpgChannelID, NSTIJKPlayerEPGActivity.this.currentChannelLogo);
                                            }
                                        }, 200);
                                    }
                                    this.currentProgramStreamID = Utils.parseIntMinusOne(this.liveListDetailAvailableChannels.get(currentWindowIndex).getStreamId());
                                    if (this.loginPrefsEditor != null) {
                                        this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableChannels.get(currentWindowIndex).getStreamId()));
                                        this.loginPrefsEditor.apply();
                                    }
                                    if (this.loginPrefsEditorPosition != null) {
                                        this.loginPrefsEditorPosition.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, String.valueOf(currentWindowIndex));
                                        this.loginPrefsEditorPosition.apply();
                                        return;
                                    }
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
                    }
                    return;
                } catch (Exception e7) {
                    Log.e("NSTIJPLAYERACTIVTY", "exection " + e7);
                    return;
                }
            case R.id.exo_pause:
                try {
                    if (findViewById(R.id.controls).getVisibility() == 0) {
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
                    }
                    stopHeaderFooterHandler();
                    showTitleBarAndFooter();
                    runHeaderFooterHandler();
                    return;
                } catch (Exception e8) {
                    Log.e("NSTIJPLAYERACTIVTY", "exection " + e8);
                    return;
                }
            case R.id.exo_play:
                try {
                    if (findViewById(R.id.controls).getVisibility() == 0) {
                        stopHeaderFooterHandler();
                        showTitleBarAndFooter();
                        runHeaderFooterHandler();
                        if (this.vlcplayButton != null) {
                            this.mVideoView.start();
                            playerStartIconsUpdate();
                            this.vlcpauseButton.requestFocus();
                            return;
                        }
                        return;
                    }
                    stopHeaderFooterHandler();
                    showTitleBarAndFooter();
                    runHeaderFooterHandler();
                    return;
                } catch (Exception e9) {
                    Log.e("NSTIJPLAYERACTIVTY", "exection " + e9);
                    return;
                }
            case R.id.exo_prev:
                try {
                    if (this.MultiPlayerType != null && this.MultiPlayerType.equals("false")) {
                        if (findViewById(R.id.controls).getVisibility() == 0) {
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
                                    final String num2 = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getNum();
                                    final String url3 = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getUrl();
                                    NSTIJKPlayerEPG nSTIJKPlayerEPG2 = this.mVideoView;
                                    nSTIJKPlayerEPG2.setTitle(num2 + " - " + name2);
                                    this.opened_vlc_id = currentWindowIndex2;
                                    if (this.rq.booleanValue()) {
                                        this.handler.postDelayed(new Runnable() {
                                            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerEPGActivity.AnonymousClass4 */

                                            public void run() {
                                                NSTIJKPlayerEPGActivity.this.release();
                                                NSTIJKPlayerEPGActivity.this.$.id(R.id.exo_pause).gone();
                                                NSTIJKPlayerEPGActivity.this.$.id(R.id.exo_play).visible();
                                                NSTIJKPlayerEPGActivity.this.loginPrefsEditorAudio.clear();
                                                NSTIJKPlayerEPGActivity.this.loginPrefsEditorAudio.apply();
                                                NSTIJKPlayerEPGActivity.this.loginPrefsEditorVideo.clear();
                                                NSTIJKPlayerEPGActivity.this.loginPrefsEditorVideo.apply();
                                                NSTIJKPlayerEPGActivity.this.loginPrefsEditorSubtitle.clear();
                                                NSTIJKPlayerEPGActivity.this.loginPrefsEditorSubtitle.apply();
                                                SharedPreferences.Editor edit = NSTIJKPlayerEPGActivity.this.loginPreferencesSharedPref_opened_video.edit();
                                                if (NSTIJKPlayerEPGActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                                                    edit.putString("LOGIN_PREF_OPENED_VIDEO_URL_M3U", url3);
                                                    NSTIJKPlayerEPGActivity.this.mVideoView.setVideoURI(Uri.parse(url3), NSTIJKPlayerEPGActivity.this.fullScreen, name2);
                                                    NSTIJKPlayerEPG access$000 = NSTIJKPlayerEPGActivity.this.mVideoView;
                                                    access$000.setTitle(num2 + " - " + name2);
                                                } else {
                                                    edit.putInt(AppConst.LOGIN_PREF_OPENED_VIDEO_ID, Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerEPGActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId()));
                                                    NSTIJKPlayerEPG access$0002 = NSTIJKPlayerEPGActivity.this.mVideoView;
                                                    access$0002.setVideoURI(Uri.parse(NSTIJKPlayerEPGActivity.this.mFilePath + Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerEPGActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId()) + NSTIJKPlayerEPGActivity.this.allowedFormat), NSTIJKPlayerEPGActivity.this.fullScreen, name2);
                                                    NSTIJKPlayerEPG access$0003 = NSTIJKPlayerEPGActivity.this.mVideoView;
                                                    access$0003.setTitle(num2 + " - " + name2);
                                                }
                                                edit.apply();
                                                NSTIJKPlayerEPGActivity.this.mVideoView.retryCount = 0;
                                                NSTIJKPlayerEPGActivity.this.mVideoView.retrying = false;
                                                NSTIJKPlayerEPGActivity.this.mVideoView.start();
                                                NSTIJKPlayerEPGActivity.this.handlerUpdateEPGData.removeCallbacksAndMessages(null);
                                                NSTIJKPlayerEPGActivity.this.loadingEPGData();
                                                NSTIJKPlayerEPGActivity.this.currentEpgChannelID = ((LiveStreamsDBModel) NSTIJKPlayerEPGActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getEpgChannelId();
                                                NSTIJKPlayerEPGActivity.this.currentChannelLogo = ((LiveStreamsDBModel) NSTIJKPlayerEPGActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamIcon();
                                                NSTIJKPlayerEPGActivity.this.mVideoView.setCurrentEpgChannelID(NSTIJKPlayerEPGActivity.this.currentEpgChannelID);
                                                NSTIJKPlayerEPGActivity.this.mVideoView.setCurrentChannelLogo(NSTIJKPlayerEPGActivity.this.currentChannelLogo);
                                                NSTIJKPlayerEPGActivity.this.updateEPGData(NSTIJKPlayerEPGActivity.this.currentEpgChannelID, NSTIJKPlayerEPGActivity.this.currentChannelLogo);
                                            }
                                        }, 200);
                                    }
                                    this.currentProgramStreamID = Utils.parseIntMinusOne(this.liveListDetailAvailableChannels.get(currentWindowIndex2).getStreamId());
                                    if (this.loginPrefsEditor != null) {
                                        this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableChannels.get(currentWindowIndex2).getStreamId()));
                                        this.loginPrefsEditor.apply();
                                    }
                                    if (this.loginPrefsEditorPosition != null) {
                                        this.loginPrefsEditorPosition.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, String.valueOf(currentWindowIndex2));
                                        this.loginPrefsEditorPosition.apply();
                                        return;
                                    }
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
                    }
                    return;
                } catch (Exception e10) {
                    Log.e("NSTIJPLAYERACTIVTY", "exection " + e10);
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
                    Log.e("NSTIJPLAYERACTIVTY", "exection " + e11);
                    return;
                }
            case R.id.vlc_exo_audio:
            default:
                return;
        }
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
        switch (i) {
            case 1:
                return this.context.getString(R.string.TrackType_video);
            case 2:
                return this.context.getString(R.string.TrackType_audio);
            case 3:
                return this.context.getString(R.string.TrackType_timedtext);
            case 4:
                return this.context.getString(R.string.TrackType_subtitle);
            case 5:
                return this.context.getString(R.string.TrackType_metadata);
            default:
                return this.context.getString(R.string.TrackType_unknown);
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
                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerEPGActivity.AnonymousClass6 */

                public void onDismiss() {
                    if (NSTIJKPlayerEPGActivity.this.mVideoView != null) {
                        NSTIJKPlayerEPGActivity.this.hideSystemUi();
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
                    /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerEPGActivity.AnonymousClass7 */

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        String obj = NSTIJKPlayerEPGActivity.this.subtitle_font_size.getItemAtPosition(i).toString();
                        SharedPreferences unused = NSTIJKPlayerEPGActivity.this.loginPreferencesAfterLoginSubtitleSize = NSTIJKPlayerEPGActivity.this.getSharedPreferences(AppConst.LOGIN_PREF_SUB_FONT_SIZE, 0);
                        SharedPreferences.Editor unused2 = NSTIJKPlayerEPGActivity.this.loginPrefsEditorSubtitleSize = NSTIJKPlayerEPGActivity.this.loginPreferencesAfterLoginSubtitleSize.edit();
                        if (NSTIJKPlayerEPGActivity.this.loginPrefsEditorSubtitleSize != null) {
                            NSTIJKPlayerEPGActivity.this.loginPrefsEditorSubtitleSize.putString(AppConst.LOGIN_PREF_SUB_FONT_SIZE, obj);
                            NSTIJKPlayerEPGActivity.this.loginPrefsEditorSubtitleSize.apply();
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
        if (findViewById(R.id.app_video_top_box).getVisibility() == 0) {
            hideTitleBarAndFooter();
            if (AppConst.WATER_MARK.booleanValue()) {
                findViewById(R.id.watrmrk).setVisibility(0);
                return;
            }
            return;
        }
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    public void fullScreenVideoLayout() {
        if (this.mVideoView != null) {
            hideSystemUi();
        }
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

    public static class Config implements Parcelable {
        public static final Creator<Config> CREATOR = new Creator<Config>() {
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerEPGActivity.Config.AnonymousClass1 */

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
            Intent intent = new Intent(this.activity, NSTIJKPlayerEPGActivity.class);
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
                    if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("1") && NSTIJKPlayerEPGActivity.this.negativeButton != null) {
                        NSTIJKPlayerEPGActivity.this.negativeButton.setBackgroundResource(R.drawable.back_btn_effect);
                        return;
                    }
                    return;
                }
                view2.setBackground(NSTIJKPlayerEPGActivity.this.getResources().getDrawable(R.drawable.selector_checkbox));
            } else if (!z) {
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("1") || NSTIJKPlayerEPGActivity.this.negativeButton == null)) {
                    NSTIJKPlayerEPGActivity.this.negativeButton.setBackgroundResource(R.drawable.black_button_dark);
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
}
