package com.nst.yourname.view.ijkplayer.activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.github.tcking.viewquery.ViewQuery;
import com.google.android.exoplayer2.util.Util;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.EpisodesUsingSinglton;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.MoviesUsingSinglton;
import com.nst.yourname.model.PlayerSelectedSinglton;
import com.nst.yourname.model.callback.GetEpisdoeDetailsCallback;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.RecentWatchDBHandler;
import com.nst.yourname.model.database.SeriesRecentWatchDatabase;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.activity.SeriesRecentClass;
import com.nst.yourname.view.ijkplayer.application.Settings;
import com.nst.yourname.view.ijkplayer.widget.media.FileMediaDataSource;
import com.nst.yourname.view.ijkplayer.widget.media.InfoHudViewHolder;
import com.nst.yourname.view.ijkplayer.widget.media.MeasureHelper;
import com.nst.yourname.view.ijkplayer.widget.media.MediaPlayerCompat;
import com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerVOD;
import com.nst.yourname.view.ijkplayer.widget.media.SurfaceRenderView;
import com.nst.yourname.view.ijkplayer.widget.media.TableLayoutBinder;
import com.nst.yourname.view.ijkplayer.widget.preference.IjkListPreference;
import com.nst.yourname.view.inbuiltsmartersplayer.VideoInfo;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.joda.time.DateTimeConstants;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

@SuppressWarnings("ALL")
public class NSTIJKPlayerVODActivity extends AppCompatActivity implements View.OnClickListener {
    static final boolean $assertionsDisabled = false;
    private static boolean onCreate = true;
    private static boolean replayVideo = true;
    private static String uk;
    private static String una;
    private ViewQuery $;
    AlertDialog alertDialog;
    public String allowedFormat;
    LinearLayout app_video_status;
    TextView app_video_status_text;
    private AppCompatImageView btn_cat_back;
    private AppCompatImageView btn_cat_forward;
    private PopupWindow changeSortPopUp;
    public boolean channelZapped = false;
    public Button closedBT;
    String container_extension = "";
    public Context context;
    public String currentAPPType;
    public int currentProgramStreamID;
    ArrayList<File> dataItems = new ArrayList<>();
    TextView date;
    public View decoder_hw;
    public View decoder_sw;
    DateFormat df;
    Date dt;
    String elv;
    public String episode_id = "";
    public View exo_info;
    boolean externalPlayerSelected = false;
    String fmw;
    SimpleDateFormat fr;
    public boolean fullScreen = false;
    Handler handlerHeaderFooter;
    Handler handlerOLD;
    Handler handlerSeekbar;
    Handler handlerSeekbarForwardRewind;
    public ImageView iv_cancel;
    public ArrayList<LiveStreamsDBModel> liveListDetailAvailableChannels;
    public ArrayList<LiveStreamsDBModel> liveListDetailAvailableChannels_Temp;
    public List<GetEpisdoeDetailsCallback> liveListDetailAvailableSeries;
    private ArrayList<File> liveListRecording;
    LiveStreamDBHandler liveStreamDBHandler;
    LinearLayout ll_seek_overlay;
    public LinearLayout ll_seekbar_time;
    private SharedPreferences loginPreferencesAfterLogin;
    public SharedPreferences loginPreferencesAfterLoginSubtitleSize;
    private SharedPreferences loginPreferencesMediaCodec;
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
    SharedPreferences.Editor loginPrefsEditorMediaCodec;
    private SharedPreferences.Editor loginPrefsEditorPosition;
    private SharedPreferences.Editor loginPrefsEditorSeekTime;
    private SharedPreferences.Editor loginPrefsEditorSubtitle;
    public SharedPreferences.Editor loginPrefsEditorSubtitleSize;
    private SharedPreferences.Editor loginPrefsEditorVideo;
    public String m3uVideoURL;
    private int mCurrentAspectRatioIndex = 4;
    public String mFilePath;
    private String mFilePath1 = "";
    private Settings mSettings;
    public NSTIJKPlayerVOD mVideoView;
    private TextView movieIDTV;
    public Button negativeButton;
    TextView no_audio_track;
    TextView no_subtitle_track;
    TextView no_video_track;
    String num = "";
    private int openedStreamId = -1;
    int opened_stream_id = 0;
    ProgressBar progressBar;
    private RecentWatchDBHandler recentWatchDBHandler;
    private SeriesRecentWatchDatabase recentWatchDBHandler1;
    private RelativeLayout rl_next_episode;
    RelativeLayout rl_settings;
    public Boolean rq = true;
    public Button savePasswordBT;
    public int seekBarMilliseconds = 0;
    public SeriesRecentClass seriesRecentClass;
    SharedPreferences.Editor sharedPrefEditor;
    SharedPreferences sharedPreferences;
    Spinner subtitle_font_size;
    TextView time;
    TextView tv_seek_overlay;
    TextView txtDisplay;
    String type = "";
    String typeofStream = "";
    String ukd;
    String unad;
    public String url;
    String videoTitle = "";
    public int video_num = 0;
    SeekBar vlcSeekbar;
    public View vlc_exo_audio;
    public View vlc_exo_subtitle;
    public View vlcaspectRatio;
    public View vlcchannelListButton;
    public View vlcffwdButton;
    public View vlcnextButton;
    public View vlcnrewButton;
    public View vlcpauseButton;
    public View vlcplayButton;
    public View vlcprevButton;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(@Nullable Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        this.context = this;
        PlayerSelectedSinglton.getInstance().setPlayerType(AppConst.VOD);
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Exception | UnsatisfiedLinkError unused) {
        }
        setContentView((int) R.layout.nst_vlc_player_vod);
        initializeVariables();
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        Utils.appResume(this.context);
        if (this.mVideoView != null) {
            this.mVideoView.hideSystemUi();
            if (this.externalPlayerSelected) {
                onCreate = false;
                this.externalPlayerSelected = false;
                if (this.liveListDetailAvailableChannels == null || this.liveListDetailAvailableChannels.size() == 0) {
                    noChannelFound();
                } else {
                    playFirstTime(this.liveListDetailAvailableChannels, this.video_num);
                }
            }
            fullScreenVideoLayout();
        }
    }

    public void fullScreenVideoLayout() {
        this.mVideoView.hideSystemUi();
        if (!(this.mVideoView == null || this.mVideoView.subtitleDisplay == null)) {
            this.mVideoView.subtitleDisplay.setVisibility(0);
        }
        this.rl_settings.setVisibility(8);
        this.mVideoView.fullScreenValue(Boolean.valueOf(this.fullScreen));
        stopHeaderFooterHandler();
        runHeaderFooterHandler();
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onDestroy() {
        super.onDestroy();
        try {
            if (!(this.mVideoView == null || this.mVideoView.cancel_autoplay == null)) {
                this.mVideoView.cancel_autoplay.performClick();
            }
        } catch (Exception e) {
            Log.e("fsgd", "fdfh", e);
        }
        try {
            release();
        } catch (Exception unused) {
        }
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onStop() {
        super.onStop();
        try {
            if (!(this.mVideoView == null || this.mVideoView.cancel_autoplay == null)) {
                this.mVideoView.cancel_autoplay.performClick();
                this.externalPlayerSelected = false;
            }
        } catch (Exception e) {
            Log.e("fsgd", "fdfh", e);
        }
        try {
            release();
        } catch (Exception unused) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:101:0x052b  */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x053d  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x054f  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x0561  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x0573  */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x0585  */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x0597  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x05a9  */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x05b8  */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x05c7  */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x05d6  */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x05e5  */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x0614  */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x0626  */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x0635  */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x0647  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x0659  */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x066b  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x06d9  */
    /* JADX WARNING: Removed duplicated region for block: B:167:0x0701  */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x0713  */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x0737  */
    /* JADX WARNING: Removed duplicated region for block: B:182:0x0791  */
    /* JADX WARNING: Removed duplicated region for block: B:188:0x07a7  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0182  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x01a4  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x01bf  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x01da  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0255  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x02e3  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0308  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0318  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0326  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x036e  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x03a6  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x04fb  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x050d  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x051c  */
    private void initializeVariables() {
        char c;
        int hashCode;
        char c2;
        int hashCode2;
        char c3;
        this.$ = new ViewQuery(this);
        this.mVideoView = (NSTIJKPlayerVOD) this.$.id(R.id.video_view).view();
        this.recentWatchDBHandler = new RecentWatchDBHandler(this.context);
        this.recentWatchDBHandler1 = new SeriesRecentWatchDatabase(this.context);
        this.seriesRecentClass = new SeriesRecentClass(this.context);
        this.mSettings = new Settings(this.context);
        this.loginPreferencesSharedPref = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.loginPreferencesAfterLogin = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.loginPreferencesMediaCodec = this.context.getSharedPreferences(AppConst.LOGIN_PREF_MEDIA_CODEC, 0);
        this.loginPreferencesSharedPref_allowed_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
        this.loginPreferencesSharedPref_currently_playing_video = getSharedPreferences(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, 0);
        this.loginPrefsEditor = this.loginPreferencesSharedPref_currently_playing_video.edit();
        this.loginPreferencesSharedPref_currently_playing_video_position = getSharedPreferences(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, 0);
        this.loginPrefsEditorPosition = this.loginPreferencesSharedPref_currently_playing_video_position.edit();
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
        this.allowedFormat = this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
        String string3 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_URL, "");
        String string4 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_PROTOCOL, "");
        String string5 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_HTTPS_PORT, "");
        String string6 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_PORT, "");
        String string7 = this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SERVER_RTMP_PORT, "");
        int hashCode3 = string4.hashCode();
        if (hashCode3 != 3213448) {
            if (hashCode3 != 3504631) {
                if (hashCode3 == 99617003 && string4.equals(AppConst.HTTPS)) {
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
                    String stringExtra = getIntent().getStringExtra("VIDEO_TITLE");
                    int intExtra = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
                    this.openedStreamId = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
                    this.m3uVideoURL = getIntent().getStringExtra("VIDEO_URL");
                    this.video_num = getIntent().getIntExtra("VIDEO_NUM", 0);
                    String stringExtra2 = getIntent().getStringExtra("STREAM_START_TIME");
                    String stringExtra3 = getIntent().getStringExtra("STREAM_STOP_TIME");
                    this.type = getIntent().getStringExtra("type");
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
                    SharepreferenceDBHandler.setType(this.type, this.context);
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
                                            this.m3uVideoURL = getIntent().getStringExtra("VIDEO_URL");
                                            this.mFilePath1 = string3 + ":" + string6 + "/series/" + string + "/" + string2 + "/";
                                            break;
                                        case 2:
                                            this.video_num = getIntent().getIntExtra("VIDEO_NUM", 0);
                                            this.mFilePath1 = string3 + ":" + string6 + "/timeshift/" + string + "/" + string2 + "/" + stringExtra3 + "/" + stringExtra2 + "/";
                                            break;
                                        case 3:
                                            this.mFilePath1 = getIntent().getStringExtra("VIDEO_PATH");
                                            break;
                                    }
                                    this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
                                    this.liveStreamDBHandler = new LiveStreamDBHandler(this);
                                    this.handlerOLD = new Handler();
                                    this.handlerHeaderFooter = new Handler();
                                    this.handlerSeekbarForwardRewind = new Handler();
                                    this.handlerSeekbar = new Handler();
                                    this.vlcSeekbar = (SeekBar) findViewById(R.id.vlcSeekbar);
                                    this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
                                    this.ll_seek_overlay = (LinearLayout) findViewById(R.id.ll_seek_overlay);
                                    this.tv_seek_overlay = (TextView) findViewById(R.id.tv_seek_overlay);
                                    this.mVideoView.setActivity(this, this.mVideoView, this.vlcSeekbar, this.txtDisplay);
                                    this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
                                    this.ll_seekbar_time = (LinearLayout) findViewById(R.id.ll_seekbar_time);
                                    this.btn_cat_back = (AppCompatImageView) findViewById(R.id.btn_category_back);
                                    this.btn_cat_forward = (AppCompatImageView) findViewById(R.id.btn_category_fwd);
                                    this.rl_settings = (RelativeLayout) findViewById(R.id.rl_settings);
                                    this.unad = Utils.ukde(MeasureHelper.pnm());
                                    this.dt = new Date();
                                    this.date = (TextView) findViewById(R.id.date);
                                    this.time = (TextView) findViewById(R.id.time);
                                    this.vlcSeekbar = (SeekBar) findViewById(R.id.app_video_seekBar);
                                    this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
                                    this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
                                    this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
                                    this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                                    this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                                    findViewById(R.id.exo_next).setOnClickListener(this);
                                    findViewById(R.id.exo_prev).setOnClickListener(this);
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
                                    this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
                                    this.vlcffwdButton = findViewById(R.id.exo_ffwd);
                                    if (this.vlcffwdButton != null) {
                                        this.vlcffwdButton.setOnClickListener(this);
                                    }
                                    this.vlcnrewButton = findViewById(R.id.exo_rew);
                                    if (this.vlcnrewButton != null) {
                                        this.vlcnrewButton.setOnClickListener(this);
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
                                    this.vlc_exo_audio = findViewById(R.id.vlc_exo_audio);
                                    if (this.vlc_exo_audio != null) {
                                        this.vlc_exo_audio.setOnClickListener(this);
                                    }
                                    this.ukd = Utils.ukde(FileMediaDataSource.apn());
                                    uk = getApplicationName(this.context);
                                    una = getApplicationContext().getPackageName();
                                    this.liveListDetailAvailableChannels = new ArrayList<>();
                                    this.liveListDetailAvailableChannels_Temp = new ArrayList<>();
                                    this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
                                    this.liveListDetailAvailableChannels = (ArrayList) MoviesUsingSinglton.getInstance().getMoviesList();
                                    onCreate = true;
                                    this.rl_next_episode = (RelativeLayout) findViewById(R.id.rl_next_episode);
                                    String str2 = this.type;
                                    hashCode2 = str2.hashCode();
                                    if (hashCode2 == -1068259517) {
                                        if (hashCode2 != -905838985) {
                                            if (hashCode2 != 48678559) {
                                                if (hashCode2 == 993558001 && str2.equals("recording")) {
                                                    c3 = 3;
                                                    switch (c3) {
                                                        case 0:
                                                            if (this.liveListDetailAvailableChannels != null && this.liveListDetailAvailableChannels.size() != 0) {
                                                                playFirstTime(this.liveListDetailAvailableChannels, this.video_num);
                                                               // break;
                                                            } else {
                                                                noChannelFound();
                                                               // break;
                                                            }
                                                            break;
                                                        case 1:
                                                            if (this.liveListDetailAvailableSeries != null && this.liveListDetailAvailableSeries.size() != 0) {
                                                                playFirstTimeSeries(this.liveListDetailAvailableSeries, intExtra);
                                                               // break;
                                                            } else {
                                                                noChannelFound();
                                                               // break;
                                                            }
                                                            break;
                                                        case 2:
                                                            if (this.mVideoView == null) {
                                                                noChannelFound();
                                                               // break;
                                                            } else {
                                                                this.vlcprevButton.setVisibility(8);
                                                                this.vlcnextButton.setVisibility(8);
                                                                this.$.id(R.id.app_video_title).text(stringExtra);
                                                                release();
                                                                this.mVideoView.setTitle(stringExtra);
                                                                this.mVideoView.setVideoPath(String.valueOf(Uri.parse(this.mFilePath + intExtra + this.allowedFormat)), this.fullScreen, "", 0, 0, "", null, 0, 0, " ");
                                                               // break;
                                                            }
                                                        case 3:
                                                            this.liveListRecording = new ArrayList<>();
                                                            allChannelsRecording();
                                                            if (this.liveListRecording != null && this.liveListRecording.size() != 0) {
                                                                playFirstTimeRecording(this.liveListRecording, this.video_num);
                                                              //  break;
                                                            } else {
                                                                noChannelFound();
                                                               // break;
                                                            }
                                                    }
                                                    this.loginPreferences_seek_time = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
                                                    this.loginPrefsEditorSeekTime = this.loginPreferences_seek_time.edit();
                                                    this.loginPrefsEditorSeekTime.putString(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, AppConst.PASSWORD_UNSET);
                                                    this.loginPrefsEditorSeekTime.apply();
                                                }
                                            } else if (str2.equals("catch_up")) {
                                                c3 = 2;
                                                switch (c3) {
                                                }
                                                this.loginPreferences_seek_time = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
                                                this.loginPrefsEditorSeekTime = this.loginPreferences_seek_time.edit();
                                                this.loginPrefsEditorSeekTime.putString(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, AppConst.PASSWORD_UNSET);
                                                this.loginPrefsEditorSeekTime.apply();
                                            }
                                        } else if (str2.equals("series")) {
                                            c3 = 1;
                                            switch (c3) {
                                            }
                                            this.loginPreferences_seek_time = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
                                            this.loginPrefsEditorSeekTime = this.loginPreferences_seek_time.edit();
                                            this.loginPrefsEditorSeekTime.putString(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, AppConst.PASSWORD_UNSET);
                                            this.loginPrefsEditorSeekTime.apply();
                                        }
                                    } else if (str2.equals("movies")) {
                                        c3 = 0;
                                        switch (c3) {
                                        }
                                        this.loginPreferences_seek_time = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
                                        this.loginPrefsEditorSeekTime = this.loginPreferences_seek_time.edit();
                                        this.loginPrefsEditorSeekTime.putString(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, AppConst.PASSWORD_UNSET);
                                        this.loginPrefsEditorSeekTime.apply();
                                    }
                                    c3 = 65535;
                                    switch (c3) {
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
                                this.liveStreamDBHandler = new LiveStreamDBHandler(this);
                                this.handlerOLD = new Handler();
                                this.handlerHeaderFooter = new Handler();
                                this.handlerSeekbarForwardRewind = new Handler();
                                this.handlerSeekbar = new Handler();
                                this.vlcSeekbar = (SeekBar) findViewById(R.id.vlcSeekbar);
                                this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
                                this.ll_seek_overlay = (LinearLayout) findViewById(R.id.ll_seek_overlay);
                                this.tv_seek_overlay = (TextView) findViewById(R.id.tv_seek_overlay);
                                this.mVideoView.setActivity(this, this.mVideoView, this.vlcSeekbar, this.txtDisplay);
                                this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
                                this.ll_seekbar_time = (LinearLayout) findViewById(R.id.ll_seekbar_time);
                                this.btn_cat_back = (AppCompatImageView) findViewById(R.id.btn_category_back);
                                this.btn_cat_forward = (AppCompatImageView) findViewById(R.id.btn_category_fwd);
                                this.rl_settings = (RelativeLayout) findViewById(R.id.rl_settings);
                                this.unad = Utils.ukde(MeasureHelper.pnm());
                                this.dt = new Date();
                                this.date = (TextView) findViewById(R.id.date);
                                this.time = (TextView) findViewById(R.id.time);
                                this.vlcSeekbar = (SeekBar) findViewById(R.id.app_video_seekBar);
                                this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
                                this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
                                this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
                                this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                                this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                                findViewById(R.id.exo_next).setOnClickListener(this);
                                findViewById(R.id.exo_prev).setOnClickListener(this);
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
                                this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
                                this.vlcffwdButton = findViewById(R.id.exo_ffwd);
                                if (this.vlcffwdButton != null) {
                                }
                                this.vlcnrewButton = findViewById(R.id.exo_rew);
                                if (this.vlcnrewButton != null) {
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
                                this.vlc_exo_audio = findViewById(R.id.vlc_exo_audio);
                                if (this.vlc_exo_audio != null) {
                                }
                                this.ukd = Utils.ukde(FileMediaDataSource.apn());
                                uk = getApplicationName(this.context);
                                una = getApplicationContext().getPackageName();
                                this.liveListDetailAvailableChannels = new ArrayList<>();
                                this.liveListDetailAvailableChannels_Temp = new ArrayList<>();
                                this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
                                this.liveListDetailAvailableChannels = (ArrayList) MoviesUsingSinglton.getInstance().getMoviesList();
                                onCreate = true;
                                this.rl_next_episode = (RelativeLayout) findViewById(R.id.rl_next_episode);
                                String str22 = this.type;
                                hashCode2 = str22.hashCode();
                                if (hashCode2 == -1068259517) {
                                }
                                c3 = 65535;
                                switch (c3) {
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
                            this.liveStreamDBHandler = new LiveStreamDBHandler(this);
                            this.handlerOLD = new Handler();
                            this.handlerHeaderFooter = new Handler();
                            this.handlerSeekbarForwardRewind = new Handler();
                            this.handlerSeekbar = new Handler();
                            this.vlcSeekbar = (SeekBar) findViewById(R.id.vlcSeekbar);
                            this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
                            this.ll_seek_overlay = (LinearLayout) findViewById(R.id.ll_seek_overlay);
                            this.tv_seek_overlay = (TextView) findViewById(R.id.tv_seek_overlay);
                            this.mVideoView.setActivity(this, this.mVideoView, this.vlcSeekbar, this.txtDisplay);
                            this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
                            this.ll_seekbar_time = (LinearLayout) findViewById(R.id.ll_seekbar_time);
                            this.btn_cat_back = (AppCompatImageView) findViewById(R.id.btn_category_back);
                            this.btn_cat_forward = (AppCompatImageView) findViewById(R.id.btn_category_fwd);
                            this.rl_settings = (RelativeLayout) findViewById(R.id.rl_settings);
                            this.unad = Utils.ukde(MeasureHelper.pnm());
                            this.dt = new Date();
                            this.date = (TextView) findViewById(R.id.date);
                            this.time = (TextView) findViewById(R.id.time);
                            this.vlcSeekbar = (SeekBar) findViewById(R.id.app_video_seekBar);
                            this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
                            this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
                            this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
                            this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                            this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                            findViewById(R.id.exo_next).setOnClickListener(this);
                            findViewById(R.id.exo_prev).setOnClickListener(this);
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
                            this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
                            this.vlcffwdButton = findViewById(R.id.exo_ffwd);
                            if (this.vlcffwdButton != null) {
                            }
                            this.vlcnrewButton = findViewById(R.id.exo_rew);
                            if (this.vlcnrewButton != null) {
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
                            this.vlc_exo_audio = findViewById(R.id.vlc_exo_audio);
                            if (this.vlc_exo_audio != null) {
                            }
                            this.ukd = Utils.ukde(FileMediaDataSource.apn());
                            uk = getApplicationName(this.context);
                            una = getApplicationContext().getPackageName();
                            this.liveListDetailAvailableChannels = new ArrayList<>();
                            this.liveListDetailAvailableChannels_Temp = new ArrayList<>();
                            this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
                            this.liveListDetailAvailableChannels = (ArrayList) MoviesUsingSinglton.getInstance().getMoviesList();
                            onCreate = true;
                            this.rl_next_episode = (RelativeLayout) findViewById(R.id.rl_next_episode);
                            String str222 = this.type;
                            hashCode2 = str222.hashCode();
                            if (hashCode2 == -1068259517) {
                            }
                            c3 = 65535;
                            switch (c3) {
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
                        this.liveStreamDBHandler = new LiveStreamDBHandler(this);
                        this.handlerOLD = new Handler();
                        this.handlerHeaderFooter = new Handler();
                        this.handlerSeekbarForwardRewind = new Handler();
                        this.handlerSeekbar = new Handler();
                        this.vlcSeekbar = (SeekBar) findViewById(R.id.vlcSeekbar);
                        this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
                        this.ll_seek_overlay = (LinearLayout) findViewById(R.id.ll_seek_overlay);
                        this.tv_seek_overlay = (TextView) findViewById(R.id.tv_seek_overlay);
                        this.mVideoView.setActivity(this, this.mVideoView, this.vlcSeekbar, this.txtDisplay);
                        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
                        this.ll_seekbar_time = (LinearLayout) findViewById(R.id.ll_seekbar_time);
                        this.btn_cat_back = (AppCompatImageView) findViewById(R.id.btn_category_back);
                        this.btn_cat_forward = (AppCompatImageView) findViewById(R.id.btn_category_fwd);
                        this.rl_settings = (RelativeLayout) findViewById(R.id.rl_settings);
                        this.unad = Utils.ukde(MeasureHelper.pnm());
                        this.dt = new Date();
                        this.date = (TextView) findViewById(R.id.date);
                        this.time = (TextView) findViewById(R.id.time);
                        this.vlcSeekbar = (SeekBar) findViewById(R.id.app_video_seekBar);
                        this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
                        this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
                        this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
                        this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                        this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                        findViewById(R.id.exo_next).setOnClickListener(this);
                        findViewById(R.id.exo_prev).setOnClickListener(this);
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
                        this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
                        this.vlcffwdButton = findViewById(R.id.exo_ffwd);
                        if (this.vlcffwdButton != null) {
                        }
                        this.vlcnrewButton = findViewById(R.id.exo_rew);
                        if (this.vlcnrewButton != null) {
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
                        this.vlc_exo_audio = findViewById(R.id.vlc_exo_audio);
                        if (this.vlc_exo_audio != null) {
                        }
                        this.ukd = Utils.ukde(FileMediaDataSource.apn());
                        uk = getApplicationName(this.context);
                        una = getApplicationContext().getPackageName();
                        this.liveListDetailAvailableChannels = new ArrayList<>();
                        this.liveListDetailAvailableChannels_Temp = new ArrayList<>();
                        this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
                        this.liveListDetailAvailableChannels = (ArrayList) MoviesUsingSinglton.getInstance().getMoviesList();
                        onCreate = true;
                        this.rl_next_episode = (RelativeLayout) findViewById(R.id.rl_next_episode);
                        String str2222 = this.type;
                        hashCode2 = str2222.hashCode();
                        if (hashCode2 == -1068259517) {
                        }
                        c3 = 65535;
                        switch (c3) {
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
                    this.liveStreamDBHandler = new LiveStreamDBHandler(this);
                    this.handlerOLD = new Handler();
                    this.handlerHeaderFooter = new Handler();
                    this.handlerSeekbarForwardRewind = new Handler();
                    this.handlerSeekbar = new Handler();
                    this.vlcSeekbar = (SeekBar) findViewById(R.id.vlcSeekbar);
                    this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
                    this.ll_seek_overlay = (LinearLayout) findViewById(R.id.ll_seek_overlay);
                    this.tv_seek_overlay = (TextView) findViewById(R.id.tv_seek_overlay);
                    this.mVideoView.setActivity(this, this.mVideoView, this.vlcSeekbar, this.txtDisplay);
                    this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    this.ll_seekbar_time = (LinearLayout) findViewById(R.id.ll_seekbar_time);
                    this.btn_cat_back = (AppCompatImageView) findViewById(R.id.btn_category_back);
                    this.btn_cat_forward = (AppCompatImageView) findViewById(R.id.btn_category_fwd);
                    this.rl_settings = (RelativeLayout) findViewById(R.id.rl_settings);
                    this.unad = Utils.ukde(MeasureHelper.pnm());
                    this.dt = new Date();
                    this.date = (TextView) findViewById(R.id.date);
                    this.time = (TextView) findViewById(R.id.time);
                    this.vlcSeekbar = (SeekBar) findViewById(R.id.app_video_seekBar);
                    this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
                    this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
                    this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
                    this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                    this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                    findViewById(R.id.exo_next).setOnClickListener(this);
                    findViewById(R.id.exo_prev).setOnClickListener(this);
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
                    this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
                    this.vlcffwdButton = findViewById(R.id.exo_ffwd);
                    if (this.vlcffwdButton != null) {
                    }
                    this.vlcnrewButton = findViewById(R.id.exo_rew);
                    if (this.vlcnrewButton != null) {
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
                    this.vlc_exo_audio = findViewById(R.id.vlc_exo_audio);
                    if (this.vlc_exo_audio != null) {
                    }
                    this.ukd = Utils.ukde(FileMediaDataSource.apn());
                    uk = getApplicationName(this.context);
                    una = getApplicationContext().getPackageName();
                    this.liveListDetailAvailableChannels = new ArrayList<>();
                    this.liveListDetailAvailableChannels_Temp = new ArrayList<>();
                    this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
                    this.liveListDetailAvailableChannels = (ArrayList) MoviesUsingSinglton.getInstance().getMoviesList();
                    onCreate = true;
                    this.rl_next_episode = (RelativeLayout) findViewById(R.id.rl_next_episode);
                    String str22222 = this.type;
                    hashCode2 = str22222.hashCode();
                    if (hashCode2 == -1068259517) {
                    }
                    c3 = 65535;
                    switch (c3) {
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
                String stringExtra4 = getIntent().getStringExtra("VIDEO_TITLE");
                int intExtra2 = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
                this.openedStreamId = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
                this.m3uVideoURL = getIntent().getStringExtra("VIDEO_URL");
                this.video_num = getIntent().getIntExtra("VIDEO_NUM", 0);
                String stringExtra22 = getIntent().getStringExtra("STREAM_START_TIME");
                String stringExtra32 = getIntent().getStringExtra("STREAM_STOP_TIME");
                this.type = getIntent().getStringExtra("type");
                if (this.type.equals("catch_up")) {
                }
                SharepreferenceDBHandler.setType(this.type, this.context);
                String str3 = this.type;
                hashCode = str3.hashCode();
                if (hashCode == -1068259517) {
                }
                c2 = 65535;
                switch (c2) {
                }
                this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
                this.liveStreamDBHandler = new LiveStreamDBHandler(this);
                this.handlerOLD = new Handler();
                this.handlerHeaderFooter = new Handler();
                this.handlerSeekbarForwardRewind = new Handler();
                this.handlerSeekbar = new Handler();
                this.vlcSeekbar = (SeekBar) findViewById(R.id.vlcSeekbar);
                this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
                this.ll_seek_overlay = (LinearLayout) findViewById(R.id.ll_seek_overlay);
                this.tv_seek_overlay = (TextView) findViewById(R.id.tv_seek_overlay);
                this.mVideoView.setActivity(this, this.mVideoView, this.vlcSeekbar, this.txtDisplay);
                this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
                this.ll_seekbar_time = (LinearLayout) findViewById(R.id.ll_seekbar_time);
                this.btn_cat_back = (AppCompatImageView) findViewById(R.id.btn_category_back);
                this.btn_cat_forward = (AppCompatImageView) findViewById(R.id.btn_category_fwd);
                this.rl_settings = (RelativeLayout) findViewById(R.id.rl_settings);
                this.unad = Utils.ukde(MeasureHelper.pnm());
                this.dt = new Date();
                this.date = (TextView) findViewById(R.id.date);
                this.time = (TextView) findViewById(R.id.time);
                this.vlcSeekbar = (SeekBar) findViewById(R.id.app_video_seekBar);
                this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
                this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
                this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
                this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                findViewById(R.id.exo_next).setOnClickListener(this);
                findViewById(R.id.exo_prev).setOnClickListener(this);
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
                this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
                this.vlcffwdButton = findViewById(R.id.exo_ffwd);
                if (this.vlcffwdButton != null) {
                }
                this.vlcnrewButton = findViewById(R.id.exo_rew);
                if (this.vlcnrewButton != null) {
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
                this.vlc_exo_audio = findViewById(R.id.vlc_exo_audio);
                if (this.vlc_exo_audio != null) {
                }
                this.ukd = Utils.ukde(FileMediaDataSource.apn());
                uk = getApplicationName(this.context);
                una = getApplicationContext().getPackageName();
                this.liveListDetailAvailableChannels = new ArrayList<>();
                this.liveListDetailAvailableChannels_Temp = new ArrayList<>();
                this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
                this.liveListDetailAvailableChannels = (ArrayList) MoviesUsingSinglton.getInstance().getMoviesList();
                onCreate = true;
                this.rl_next_episode = (RelativeLayout) findViewById(R.id.rl_next_episode);
                String str222222 = this.type;
                hashCode2 = str222222.hashCode();
                if (hashCode2 == -1068259517) {
                }
                c3 = 65535;
                switch (c3) {
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
            String stringExtra42 = getIntent().getStringExtra("VIDEO_TITLE");
            int intExtra22 = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
            this.openedStreamId = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
            this.m3uVideoURL = getIntent().getStringExtra("VIDEO_URL");
            this.video_num = getIntent().getIntExtra("VIDEO_NUM", 0);
            String stringExtra222 = getIntent().getStringExtra("STREAM_START_TIME");
            String stringExtra322 = getIntent().getStringExtra("STREAM_STOP_TIME");
            this.type = getIntent().getStringExtra("type");
            if (this.type.equals("catch_up")) {
            }
            SharepreferenceDBHandler.setType(this.type, this.context);
            String str32 = this.type;
            hashCode = str32.hashCode();
            if (hashCode == -1068259517) {
            }
            c2 = 65535;
            switch (c2) {
            }
            this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
            this.liveStreamDBHandler = new LiveStreamDBHandler(this);
            this.handlerOLD = new Handler();
            this.handlerHeaderFooter = new Handler();
            this.handlerSeekbarForwardRewind = new Handler();
            this.handlerSeekbar = new Handler();
            this.vlcSeekbar = (SeekBar) findViewById(R.id.vlcSeekbar);
            this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
            this.ll_seek_overlay = (LinearLayout) findViewById(R.id.ll_seek_overlay);
            this.tv_seek_overlay = (TextView) findViewById(R.id.tv_seek_overlay);
            this.mVideoView.setActivity(this, this.mVideoView, this.vlcSeekbar, this.txtDisplay);
            this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
            this.ll_seekbar_time = (LinearLayout) findViewById(R.id.ll_seekbar_time);
            this.btn_cat_back = (AppCompatImageView) findViewById(R.id.btn_category_back);
            this.btn_cat_forward = (AppCompatImageView) findViewById(R.id.btn_category_fwd);
            this.rl_settings = (RelativeLayout) findViewById(R.id.rl_settings);
            this.unad = Utils.ukde(MeasureHelper.pnm());
            this.dt = new Date();
            this.date = (TextView) findViewById(R.id.date);
            this.time = (TextView) findViewById(R.id.time);
            this.vlcSeekbar = (SeekBar) findViewById(R.id.app_video_seekBar);
            this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
            this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
            this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
            this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
            this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
            findViewById(R.id.exo_next).setOnClickListener(this);
            findViewById(R.id.exo_prev).setOnClickListener(this);
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
            this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
            this.vlcffwdButton = findViewById(R.id.exo_ffwd);
            if (this.vlcffwdButton != null) {
            }
            this.vlcnrewButton = findViewById(R.id.exo_rew);
            if (this.vlcnrewButton != null) {
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
            this.vlc_exo_audio = findViewById(R.id.vlc_exo_audio);
            if (this.vlc_exo_audio != null) {
            }
            this.ukd = Utils.ukde(FileMediaDataSource.apn());
            uk = getApplicationName(this.context);
            una = getApplicationContext().getPackageName();
            this.liveListDetailAvailableChannels = new ArrayList<>();
            this.liveListDetailAvailableChannels_Temp = new ArrayList<>();
            this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
            this.liveListDetailAvailableChannels = (ArrayList) MoviesUsingSinglton.getInstance().getMoviesList();
            onCreate = true;
            this.rl_next_episode = (RelativeLayout) findViewById(R.id.rl_next_episode);
            String str2222222 = this.type;
            hashCode2 = str2222222.hashCode();
            if (hashCode2 == -1068259517) {
            }
            c3 = 65535;
            switch (c3) {
            }
            this.loginPreferences_seek_time = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
            this.loginPrefsEditorSeekTime = this.loginPreferences_seek_time.edit();
            this.loginPrefsEditorSeekTime.putString(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, AppConst.PASSWORD_UNSET);
            this.loginPrefsEditorSeekTime.apply();
        }
        c = 65535;
        switch (c) {
        }
        String stringExtra422 = getIntent().getStringExtra("VIDEO_TITLE");
        int intExtra222 = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
        this.openedStreamId = getIntent().getIntExtra("OPENED_STREAM_ID", 0);
        this.m3uVideoURL = getIntent().getStringExtra("VIDEO_URL");
        this.video_num = getIntent().getIntExtra("VIDEO_NUM", 0);
        String stringExtra2222 = getIntent().getStringExtra("STREAM_START_TIME");
        String stringExtra3222 = getIntent().getStringExtra("STREAM_STOP_TIME");
        this.type = getIntent().getStringExtra("type");
        if (this.type.equals("catch_up")) {
        }
        SharepreferenceDBHandler.setType(this.type, this.context);
        String str322 = this.type;
        hashCode = str322.hashCode();
        if (hashCode == -1068259517) {
        }
        c2 = 65535;
        switch (c2) {
        }
        this.mFilePath = Utils.getFormattedUrl(this.mFilePath1);
        this.liveStreamDBHandler = new LiveStreamDBHandler(this);
        this.handlerOLD = new Handler();
        this.handlerHeaderFooter = new Handler();
        this.handlerSeekbarForwardRewind = new Handler();
        this.handlerSeekbar = new Handler();
        this.vlcSeekbar = (SeekBar) findViewById(R.id.vlcSeekbar);
        this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
        this.ll_seek_overlay = (LinearLayout) findViewById(R.id.ll_seek_overlay);
        this.tv_seek_overlay = (TextView) findViewById(R.id.tv_seek_overlay);
        this.mVideoView.setActivity(this, this.mVideoView, this.vlcSeekbar, this.txtDisplay);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.ll_seekbar_time = (LinearLayout) findViewById(R.id.ll_seekbar_time);
        this.btn_cat_back = (AppCompatImageView) findViewById(R.id.btn_category_back);
        this.btn_cat_forward = (AppCompatImageView) findViewById(R.id.btn_category_fwd);
        this.rl_settings = (RelativeLayout) findViewById(R.id.rl_settings);
        this.unad = Utils.ukde(MeasureHelper.pnm());
        this.dt = new Date();
        this.date = (TextView) findViewById(R.id.date);
        this.time = (TextView) findViewById(R.id.time);
        this.vlcSeekbar = (SeekBar) findViewById(R.id.app_video_seekBar);
        this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
        this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
        this.txtDisplay = (TextView) findViewById(R.id.txtDisplay);
        this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        findViewById(R.id.exo_next).setOnClickListener(this);
        findViewById(R.id.exo_prev).setOnClickListener(this);
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
        this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + IjkListPreference.mw());
        this.vlcffwdButton = findViewById(R.id.exo_ffwd);
        if (this.vlcffwdButton != null) {
        }
        this.vlcnrewButton = findViewById(R.id.exo_rew);
        if (this.vlcnrewButton != null) {
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
        this.vlc_exo_audio = findViewById(R.id.vlc_exo_audio);
        if (this.vlc_exo_audio != null) {
        }
        this.ukd = Utils.ukde(FileMediaDataSource.apn());
        uk = getApplicationName(this.context);
        una = getApplicationContext().getPackageName();
        this.liveListDetailAvailableChannels = new ArrayList<>();
        this.liveListDetailAvailableChannels_Temp = new ArrayList<>();
        this.elv = Utils.ukde(IjkListPreference.pZGV() + IjkListPreference.mv());
        this.liveListDetailAvailableChannels = (ArrayList) MoviesUsingSinglton.getInstance().getMoviesList();
        onCreate = true;
        this.rl_next_episode = (RelativeLayout) findViewById(R.id.rl_next_episode);
        String str22222222 = this.type;
        hashCode2 = str22222222.hashCode();
        if (hashCode2 == -1068259517) {
        }
        c3 = 65535;
        switch (c3) {
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
        hideTitleBarAndFooter();
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

    private void playFirstTimeRecording(ArrayList<File> arrayList, int i) {
        if (arrayList != null && arrayList.size() > 0) {
            String name = arrayList.get(i).getName();
            String ukde = Utils.ukde(TableLayoutBinder.aW5nIGl() + TableLayoutBinder.mu());
            if (df(this.fr, this.fr.format(new Date(MediaPlayerCompat.cit(this.context))), this.df.format(this.dt)) >= ((long) InfoHudViewHolder.ux()) && this.ukd != null && this.unad != null && (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad)))) {
                this.rq = false;
                this.$.id(R.id.app_video_status).visible();
                ViewQuery id = this.$.id(R.id.app_video_status_text);
                id.text(ukde + this.elv + this.fmw);
            }
            if (this.rq.booleanValue()) {
                VideoInfo.getInstance().setCurrentWindowIndex(i);
                this.mVideoView.setTitle(name);
                if (this.mVideoView.getFullScreenValue().booleanValue()) {
                    this.fullScreen = this.mVideoView.getFullScreenValue().booleanValue();
                } else {
                    this.fullScreen = false;
                }
                this.mVideoView.removeHandlerCallback();
                release();
                this.mVideoView.setVideoPath(this.mFilePath, this.fullScreen, name, 0, 0, "", null, 0, 0, this.currentAPPType);
                if (this.mVideoView != null) {
                    this.mVideoView.retryCount = 0;
                    this.mVideoView.retrying = false;
                    this.mVideoView.resuming = true;
                    this.mVideoView.setMaxTime = false;
                    this.mVideoView.start();
                }
            }
            hideTitleBarAndFooter();
        }
    }

    private void playFirstTime(ArrayList<LiveStreamsDBModel> arrayList, int i) {
        int i2;
        boolean z;
        boolean z2;
        ArrayList<LiveStreamsDBModel> arrayList2 = arrayList;
        if (arrayList2 != null && arrayList.size() > 0) {
            int indexOfMovies = getIndexOfMovies(arrayList, i);
            String name = arrayList2.get(indexOfMovies).getName();
            String num2 = arrayList2.get(indexOfMovies).getNum();
            String ukde = Utils.ukde(com.nst.yourname.view.inbuiltsmartersplayer.trackselector.TableLayoutBinder.aW5nIGl() + com.nst.yourname.view.inbuiltsmartersplayer.trackselector.TableLayoutBinder.mu());
            String streamType = arrayList2.get(indexOfMovies).getStreamType();
            int parseIntMinusOne = Utils.parseIntMinusOne(arrayList2.get(indexOfMovies).getStreamId());
            String contaiinerExtension = arrayList2.get(indexOfMovies).getContaiinerExtension();
            VideoInfo.getInstance().setCurrentWindowIndex(indexOfMovies);
            if (this.type.equals("movies")) {
                this.m3uVideoURL = arrayList2.get(indexOfMovies).getUrl();
                if (this.loginPrefsEditor != null) {
                    this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableChannels.get(indexOfMovies).getStreamId()));
                    this.loginPrefsEditor.apply();
                }
            }
            if (this.loginPrefsEditorPosition != null) {
                this.loginPrefsEditorPosition.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, String.valueOf(indexOfMovies));
                this.loginPrefsEditorPosition.apply();
            }
            if (df(this.fr, this.fr.format(new Date(MediaPlayerCompat.cit(this.context))), this.df.format(this.dt)) >= ((long) InfoHudViewHolder.ux()) && this.ukd != null && this.unad != null && (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad)))) {
                this.rq = false;
                this.$.id(R.id.app_video_status).visible();
                ViewQuery id = this.$.id(R.id.app_video_status_text);
                id.text(ukde + this.elv + this.fmw);
            }
            this.currentProgramStreamID = parseIntMinusOne;
            int parseIntZero = Utils.parseIntZero(num2);
            NSTIJKPlayerVOD nSTIJKPlayerVOD = this.mVideoView;
            nSTIJKPlayerVOD.setTitle(parseIntZero + " - " + name);
            ViewQuery id2 = this.$.id(R.id.app_video_title);
            id2.text(parseIntZero + " - " + name);
            if (this.mVideoView.getFullScreenValue().booleanValue()) {
                this.fullScreen = this.mVideoView.getFullScreenValue().booleanValue();
            } else {
                this.fullScreen = false;
            }
            this.mVideoView.removeHandlerCallback();
            this.mVideoView.retryCount = 0;
            this.mVideoView.retrying = false;
            this.mVideoView.isVODPlayer = true;
            if (this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                i2 = streamCheckFunM3U(String.valueOf(Uri.parse(this.m3uVideoURL)), SharepreferenceDBHandler.getUserID(this.context));
            } else {
                i2 = streamCheckFun(parseIntMinusOne, SharepreferenceDBHandler.getUserID(this.context));
            }
            this.sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            if (!onCreate) {
                i2 = 0;
            }
            if (i2 == 0) {
                if (this.rq.booleanValue()) {
                    release();
                    if (this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                        this.mVideoView.setVideoPath(this.m3uVideoURL, this.fullScreen, name, 0, 0, "", null, 0, 0, this.currentAPPType);
                    } else {
                        this.mVideoView.setVideoPath(String.valueOf(Uri.parse(this.mFilePath + parseIntMinusOne + "." + contaiinerExtension)), this.fullScreen, name, 0, parseIntMinusOne, streamType, this.liveListDetailAvailableChannels_Temp, indexOfMovies, parseIntZero, this.currentAPPType);
                    }
                    VideoInfo.getInstance().setstreamid(parseIntMinusOne);
                    VideoInfo.getInstance().setAvailableChannels(arrayList2);
                    VideoInfo.getInstance().setCurrentWindowIndex(indexOfMovies);
                    if (this.mVideoView != null) {
                        this.mVideoView.retryCount = 0;
                        this.mVideoView.retrying = false;
                        this.mVideoView.resuming = true;
                        this.mVideoView.setMaxTime = false;
                        this.mVideoView.start();
                    }
                    hideTitleBarAndFooter();
                }
            } else if (i2 > 0) {
                if (isFinishing() || !this.rq.booleanValue()) {
                    z2 = true;
                    z = false;
                } else {
                    this.loginPreferences_seek_time = getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
                    hideTitleBarAndFooter();
                    z2 = true;
                    z = false;
                    passwordConfirmationPopUp(this, parseIntMinusOne, contaiinerExtension, name, parseIntZero, streamType, arrayList, indexOfMovies, this.m3uVideoURL);
                }
                if (this.mVideoView != null) {
                    this.mVideoView.retryCount = z ? 1 : 0;
                    this.mVideoView.retrying = z;
                    this.mVideoView.resuming = z2;
                    this.mVideoView.setMaxTime = z;
                    this.mVideoView.start();
                }
            }
            hideTitleBarAndFooter();
        }
    }

    private void playFirstTimeSeries(List<GetEpisdoeDetailsCallback> list, int i) {
        boolean z;
        boolean z2;
        List<GetEpisdoeDetailsCallback> list2 = list;
        if (list2 != null && list.size() > 0) {
            int indexOfSeries = getIndexOfSeries(list, i);
            String title = list2.get(indexOfSeries).getTitle();
            String ukde = Utils.ukde(TableLayoutBinder.aW5nIGl() + TableLayoutBinder.mu());
            String id = list2.get(indexOfSeries).getId();
            int parseIntMinusOne = Utils.parseIntMinusOne(list2.get(indexOfSeries).getId());
            String containerExtension = list2.get(indexOfSeries).getContainerExtension();
            list2.get(indexOfSeries).getCategoryId();
            this.episode_id = list2.get(indexOfSeries).getId();
            VideoInfo.getInstance().setCurrentWindowIndex(indexOfSeries);
            int streamCheckSeries = this.seriesRecentClass.streamCheckSeries(this.episode_id, SharepreferenceDBHandler.getUserID(this.context));
            if (this.loginPrefsEditor != null) {
                this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(list2.get(indexOfSeries).getId()));
                this.loginPrefsEditor.apply();
            }
            if (this.type.equals("series") && this.loginPrefsEditor != null) {
                this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableSeries.get(indexOfSeries).getId()));
                this.loginPrefsEditor.apply();
            }
            if (this.loginPrefsEditorPosition != null) {
                this.loginPrefsEditorPosition.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, String.valueOf(indexOfSeries));
                this.loginPrefsEditorPosition.apply();
            }
            if (df(this.fr, this.fr.format(new Date(MediaPlayerCompat.cit(this.context))), this.df.format(this.dt)) >= ((long) InfoHudViewHolder.ux()) && this.ukd != null && this.unad != null && (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad)))) {
                this.rq = false;
                this.$.id(R.id.app_video_status).visible();
                ViewQuery id2 = this.$.id(R.id.app_video_status_text);
                id2.text(ukde + this.elv + this.fmw);
            }
            this.currentProgramStreamID = parseIntMinusOne;
            NSTIJKPlayerVOD nSTIJKPlayerVOD = this.mVideoView;
            nSTIJKPlayerVOD.setTitle(id + " - " + title);
            if (streamCheckSeries == 0 && this.rq.booleanValue()) {
                release();
                EpisodesUsingSinglton.getInstance().setEpisodeList(list2);
                if (this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                    VideoInfo.getInstance().setAPPType(AppConst.TYPE_M3U);
                    this.mVideoView.setVideoPath(this.m3uVideoURL, this.fullScreen, title, 0, 0, "", null, indexOfSeries, 0, this.currentAPPType);
                } else {
                    this.mVideoView.setVideoPath(String.valueOf(Uri.parse(this.mFilePath + parseIntMinusOne + "." + containerExtension)), this.fullScreen, title, 0, parseIntMinusOne, "", this.liveListDetailAvailableChannels_Temp, indexOfSeries, this.video_num, this.currentAPPType);
                }
                EpisodesUsingSinglton.getInstance().setEpisodeList(list2);
                VideoInfo.getInstance().setEpisodeId(this.episode_id);
                VideoInfo.getInstance().setstreamid(parseIntMinusOne);
                VideoInfo.getInstance().setAvailableSeries(list2);
                this.mVideoView.retryCount = 0;
                this.mVideoView.retrying = false;
                this.mVideoView.resuming = true;
                this.mVideoView.setMaxTime = false;
                this.mVideoView.start();
            }
            if (streamCheckSeries > 0) {
                if (this.seriesRecentClass.checkRecentWatch(this.episode_id) > 0) {
                    String elapsed_time = list2.get(indexOfSeries).getElapsed_time();
                    hideTitleBarAndFooter();
                    z = false;
                    z2 = true;
                    showRecentWatchPopup(this.context, this.episode_id, containerExtension, title, this.episode_id, "series", list, indexOfSeries, null, elapsed_time);
                } else {
                    z2 = true;
                    z = false;
                }
                if (this.mVideoView != null) {
                    this.mVideoView.retryCount = z ? 1 : 0;
                    this.mVideoView.retrying = z;
                    this.mVideoView.resuming = z2;
                    this.mVideoView.setMaxTime = z;
                    this.mVideoView.start();
                }
            }
            hideTitleBarAndFooter();
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

    private void passwordConfirmationPopUp(final NSTIJKPlayerVODActivity nSTIJKPlayerVODActivity, final int i, final String str, String str2, final int i2, final String str3, final ArrayList<LiveStreamsDBModel> arrayList, final int i3, final String str4) {
        final String name = arrayList.get(i3).getName();
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogbox);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass1 */

            public void onDismiss(DialogInterface dialogInterface) {
                if (NSTIJKPlayerVODActivity.this.mVideoView != null) {
                    NSTIJKPlayerVODActivity.this.mVideoView.hideSystemUi();
                }
            }
        });
        View inflate = LayoutInflater.from(this).inflate((int) R.layout.layout_resume_player, (ViewGroup) null);
        this.movieIDTV = (TextView) inflate.findViewById(R.id.tv_movie_id);
        this.savePasswordBT = (Button) inflate.findViewById(R.id.bt_resume);
        this.iv_cancel = (ImageView) inflate.findViewById(R.id.iv_cancel);
        this.iv_cancel.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass2 */

            public void onClick(View view) {
                NSTIJKPlayerVODActivity.this.alertDialog.setCancelable(true);
                NSTIJKPlayerVODActivity.this.onBackPressed();
                NSTIJKPlayerVODActivity.this.onBackPressed();
                NSTIJKPlayerVODActivity.this.alertDialog.dismiss();
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
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass3 */

            public void onClick(View view) {
                LiveStreamsDBModel liveStreamsDBModel;
                String name = ((LiveStreamsDBModel) arrayList.get(i3)).getName();
                new LiveStreamsDBModel();
                if (NSTIJKPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                    liveStreamsDBModel = NSTIJKPlayerVODActivity.this.getStreamStatusM3U(String.valueOf(Uri.parse(str4)), SharepreferenceDBHandler.getUserID(nSTIJKPlayerVODActivity));
                } else {
                    liveStreamsDBModel = NSTIJKPlayerVODActivity.this.getStreamStatus(i, SharepreferenceDBHandler.getUserID(nSTIJKPlayerVODActivity));
                }
                long movieElapsedTime = liveStreamsDBModel.getMovieElapsedTime();
                if (NSTIJKPlayerVODActivity.this.mVideoView != null && NSTIJKPlayerVODActivity.this.rq.booleanValue()) {
                    NSTIJKPlayerVODActivity.this.release();
                    NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(String.valueOf(Uri.parse(NSTIJKPlayerVODActivity.this.mFilePath + i + "." + str)), NSTIJKPlayerVODActivity.this.fullScreen, name, 0, i, str3, NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels_Temp, i3, i2, NSTIJKPlayerVODActivity.this.currentAPPType);
                    VideoInfo.getInstance().setstreamid(i);
                    VideoInfo.getInstance().setAvailableChannels(arrayList);
                    VideoInfo.getInstance().setCurrentWindowIndex(i3);
                    NSTIJKPlayerVODActivity.this.mVideoView.setCurrentPositionSeekbar((int) movieElapsedTime);
                    NSTIJKPlayerVODActivity.this.mVideoView.setProgress(true);
                    if (NSTIJKPlayerVODActivity.this.mVideoView != null) {
                        NSTIJKPlayerVODActivity.this.mVideoView.retryCount = 0;
                        NSTIJKPlayerVODActivity.this.mVideoView.retrying = false;
                        NSTIJKPlayerVODActivity.this.mVideoView.resuming = true;
                        NSTIJKPlayerVODActivity.this.mVideoView.setMaxTime = false;
                        NSTIJKPlayerVODActivity.this.mVideoView.start();
                    }
                }
                NSTIJKPlayerVODActivity.this.alertDialog.dismiss();
            }
        });
        this.closedBT.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass4 */

            public void onClick(View view) {
                NSTIJKPlayerVODActivity.this.release();
                NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(String.valueOf(Uri.parse(NSTIJKPlayerVODActivity.this.mFilePath + i + "." + str)), NSTIJKPlayerVODActivity.this.fullScreen, name, 0, i, str3, NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels_Temp, i3, i2, NSTIJKPlayerVODActivity.this.currentAPPType);
                if (NSTIJKPlayerVODActivity.this.mVideoView != null) {
                    VideoInfo.getInstance().setstreamid(i);
                    VideoInfo.getInstance().setAvailableChannels(arrayList);
                    VideoInfo.getInstance().setCurrentWindowIndex(i3);
                    if (NSTIJKPlayerVODActivity.this.mVideoView != null) {
                        NSTIJKPlayerVODActivity.this.mVideoView.isTimeElapsed = true;
                        NSTIJKPlayerVODActivity.this.mVideoView.isVODPlayer = true;
                        NSTIJKPlayerVODActivity.this.mVideoView.timeElapsed = 0;
                        NSTIJKPlayerVODActivity.this.mVideoView.resuming = true;
                        NSTIJKPlayerVODActivity.this.mVideoView.setMaxTime = false;
                        NSTIJKPlayerVODActivity.this.mVideoView.retryCount = 0;
                        NSTIJKPlayerVODActivity.this.mVideoView.retrying = false;
                        NSTIJKPlayerVODActivity.this.mVideoView.start();
                    }
                }
                NSTIJKPlayerVODActivity.this.alertDialog.dismiss();
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

    public LiveStreamsDBModel getStreamStatusM3U(String str, int i) {
        return this.liveStreamDBHandler.getStreamStatus(str, i);
    }

    private int streamCheckFun(int i, int i2) {
        return this.recentWatchDBHandler.isStreamAvailable(String.valueOf(i), i2);
    }

    private int streamCheckFunM3U(String str, int i) {
        return this.liveStreamDBHandler.isStreamAvailable(str, i);
    }

    private void stopHeaderFooterHandler() {
        this.handlerHeaderFooter.removeCallbacksAndMessages(null);
    }

    private void playerPauseIconsUpdate() {
        this.vlcpauseButton.setVisibility(8);
        this.vlcplayButton.setVisibility(0);
        if (AppConst.WATER_MARK.booleanValue()) {
            findViewById(R.id.watrmrk).setVisibility(0);
        }
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

    private void runHeaderFooterHandler() {
        this.handlerHeaderFooter.postDelayed(new Runnable() {
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass5 */

            public void run() {
                NSTIJKPlayerVODActivity.this.hideTitleBarAndFooter();
                if (AppConst.WATER_MARK.booleanValue()) {
                    NSTIJKPlayerVODActivity.this.findViewById(R.id.watrmrk).setVisibility(0);
                }
            }
        }, 7000);
    }

    private void playerStartIconsUpdate() {
        this.vlcplayButton.setVisibility(8);
        this.vlcpauseButton.setVisibility(0);
        if (AppConst.WATER_MARK.booleanValue()) {
            findViewById(R.id.watrmrk).setVisibility(0);
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        if (findViewById(R.id.app_video_top_box).getVisibility() == 0) {
            hideTitleBarAndFooter();
            return;
        }
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    /* JADX WARNING: Removed duplicated region for block: B:131:0x0499 A[Catch:{ Exception -> 0x072a }] */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x049b A[Catch:{ Exception -> 0x072a }] */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x04ed A[Catch:{ Exception -> 0x072a }] */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x057b A[Catch:{ Exception -> 0x072a }] */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x0654 A[Catch:{ Exception -> 0x072a }] */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x066c A[Catch:{ Exception -> 0x072a }] */
    /* JADX WARNING: Removed duplicated region for block: B:177:0x067a A[Catch:{ Exception -> 0x072a }] */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x067c A[Catch:{ Exception -> 0x072a }] */
    /* JADX WARNING: Removed duplicated region for block: B:181:0x06af A[Catch:{ Exception -> 0x072a }] */
    /* JADX WARNING: Removed duplicated region for block: B:184:0x06e2 A[Catch:{ Exception -> 0x072a }] */
    /* JADX WARNING: Removed duplicated region for block: B:189:0x0718 A[Catch:{ Exception -> 0x072a }] */
    /* JADX WARNING: Removed duplicated region for block: B:259:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:269:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x016b A[Catch:{ Exception -> 0x03cb }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x016d A[Catch:{ Exception -> 0x03cb }] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x01bb A[Catch:{ Exception -> 0x03cb }] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0249 A[Catch:{ Exception -> 0x03cb }] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0378 A[Catch:{ Exception -> 0x03cb }] */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x03ae A[Catch:{ Exception -> 0x03cb }] */
    public void onClick(View view) {
        char c;
        int hashCode;
        char c2 = 2;
        switch (view.getId()) {
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
                    Log.e("NSTIJPLAYERVODACTIVTY", "exection " + e);
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
                                this.loginPrefsEditorMediaCodec.putString(AppConst.LOGIN_PREF_MEDIA_CODEC, getResources().getString(R.string.software_decoder));
                                this.loginPrefsEditorMediaCodec.apply();
                            }
                            int currentPosition = this.mVideoView.getCurrentPosition();
                            release();
                            this.mVideoView.openVideo();
                            this.mVideoView.start();
                            this.mVideoView.setCurrentPositionSeekbar(currentPosition);
                            this.mVideoView.setProgress(true);
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
                } catch (Exception e2) {
                    Log.e("NSTIJPLAYERVODACTIVTY", "exection " + e2);
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
                                this.loginPrefsEditorMediaCodec.putString(AppConst.LOGIN_PREF_MEDIA_CODEC, getResources().getString(R.string.hardware_decoder));
                                this.loginPrefsEditorMediaCodec.apply();
                            }
                            int currentPosition2 = this.mVideoView.getCurrentPosition();
                            release();
                            this.mVideoView.openVideo();
                            this.mVideoView.start();
                            this.mVideoView.setCurrentPositionSeekbar(currentPosition2);
                            this.mVideoView.setProgress(true);
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
                } catch (Exception e3) {
                    Log.e("NSTIJPLAYERVODACTIVTY", "exection " + e3);
                    return;
                }
            case R.id.exo_ffwd:
                try {
                    if (findViewById(R.id.controls).getVisibility() == 0) {
                        stopHeaderFooterHandler();
                        showTitleBarAndFooter();
                        runHeaderFooterHandler();
                        if (this.mVideoView != null) {
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
                                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass8 */

                                public void run() {
                                    NSTIJKPlayerVODActivity.this.mVideoView.seekTo(NSTIJKPlayerVODActivity.this.mVideoView.getCurrentPosition() + NSTIJKPlayerVODActivity.this.seekBarMilliseconds);
                                    NSTIJKPlayerVODActivity.this.handlerSeekbar.removeCallbacksAndMessages(null);
                                    NSTIJKPlayerVODActivity.this.handlerSeekbar.postDelayed(new Runnable() {
                                        /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass8.AnonymousClass1 */

                                        public void run() {
                                            int unused = NSTIJKPlayerVODActivity.this.seekBarMilliseconds = 0;
                                            NSTIJKPlayerVODActivity.this.ll_seek_overlay.setVisibility(8);
                                        }
                                    }, 3000);
                                }
                            }, 1000);
                            return;
                        }
                        return;
                    }
                    stopHeaderFooterHandler();
                    showTitleBarAndFooter();
                    runHeaderFooterHandler();
                    return;
                } catch (Exception e4) {
                    Log.e("NSTIJPLAYERVODACTIVTY", "exection " + e4);
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
                } catch (Exception e5) {
                    Log.e("NSTIJPLAYERVODACTIVTY", "exection " + e5);
                    return;
                }
            case R.id.exo_next:
                try {
                    stopHeaderFooterHandler();
                    showTitleBarAndFooter();
                    runHeaderFooterHandler();
                    if (this.mVideoView != null) {
                        this.vlcnextButton.requestFocus();
                        this.handlerOLD.removeCallbacksAndMessages(null);
                        this.channelZapped = true;
                        next();
                        final int currentWindowIndex = VideoInfo.getInstance().getCurrentWindowIndex();
                        String str = this.type;
                        int hashCode2 = str.hashCode();
                        if (hashCode2 != -1068259517) {
                            if (hashCode2 != -905838985) {
                                if (hashCode2 == 993558001) {
                                    if (str.equals("recording")) {
                                        c = 2;
                                        switch (c) {
                                            case 0:
                                                if (this.liveListDetailAvailableChannels != null && this.liveListDetailAvailableChannels.size() > 1 && currentWindowIndex <= this.liveListDetailAvailableChannels.size() - 1) {
                                                    this.videoTitle = this.liveListDetailAvailableChannels.get(currentWindowIndex).getName();
                                                    this.num = this.liveListDetailAvailableChannels.get(currentWindowIndex).getNum();
                                                    this.typeofStream = this.liveListDetailAvailableChannels.get(currentWindowIndex).getStreamType();
                                                    this.container_extension = this.liveListDetailAvailableChannels.get(currentWindowIndex).getContaiinerExtension();
                                                    this.m3uVideoURL = this.liveListDetailAvailableChannels.get(currentWindowIndex).getUrl();
                                                    this.opened_stream_id = Utils.parseIntMinusOne(this.liveListDetailAvailableChannels.get(currentWindowIndex).getStreamId());
                                                    this.$.id(R.id.app_video_title).text(this.num + " - " + this.videoTitle);
                                                    this.video_num = Utils.parseIntZero(this.num);
                                                    replayVideo = true;
                                                    break;
                                                } else {
                                                    replayVideo = false;
                                                    break;
                                                }
                                            case 1:
                                                if (this.liveListDetailAvailableSeries != null && this.liveListDetailAvailableSeries.size() > 1 && currentWindowIndex <= this.liveListDetailAvailableSeries.size() - 1) {
                                                    this.videoTitle = this.liveListDetailAvailableSeries.get(currentWindowIndex).getTitle();
                                                    this.container_extension = this.liveListDetailAvailableSeries.get(currentWindowIndex).getContainerExtension();
                                                    this.num = this.liveListDetailAvailableSeries.get(currentWindowIndex).getId();
                                                    this.video_num = Utils.parseIntZero(this.num);
                                                    this.opened_stream_id = Utils.parseIntMinusOne(this.num);
                                                    this.episode_id = this.liveListDetailAvailableSeries.get(currentWindowIndex).getId();
                                                    this.$.id(R.id.app_video_title).text(this.num + " - " + this.videoTitle);
                                                    this.video_num = Utils.parseIntZero(this.num);
                                                    replayVideo = true;
                                                    break;
                                                } else {
                                                    replayVideo = false;
                                                    break;
                                                }
                                            case 2:
                                                if (this.liveListRecording != null && this.liveListRecording.size() > 1 && currentWindowIndex <= this.liveListRecording.size() - 1) {
                                                    this.videoTitle = this.liveListRecording.get(currentWindowIndex).getName();
                                                    this.video_num = currentWindowIndex;
                                                    this.mFilePath = this.liveListRecording.get(currentWindowIndex).getAbsolutePath();
                                                    this.video_num = Utils.parseIntZero(String.valueOf(this.video_num));
                                                    this.$.id(R.id.app_video_title).text(this.videoTitle);
                                                    replayVideo = true;
                                                    break;
                                                } else {
                                                    replayVideo = false;
                                                    break;
                                                }
                                        }
                                        this.loginPrefsEditorAudio.clear();
                                        this.loginPrefsEditorAudio.apply();
                                        this.loginPrefsEditorVideo.clear();
                                        this.loginPrefsEditorVideo.apply();
                                        this.loginPrefsEditorSubtitle.clear();
                                        this.loginPrefsEditorSubtitle.apply();
                                        if (replayVideo && this.rq.booleanValue()) {
                                            this.handlerOLD.postDelayed(new Runnable() {
                                                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass7 */

                                                public void run() {
                                                    NSTIJKPlayerVODActivity.this.release();
                                                    if (NSTIJKPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U) && !NSTIJKPlayerVODActivity.this.type.equals("recording")) {
                                                        NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(NSTIJKPlayerVODActivity.this.m3uVideoURL, NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, 0, "", null, currentWindowIndex, 0, NSTIJKPlayerVODActivity.this.currentAPPType);
                                                        VideoInfo.getInstance().setAPPType(AppConst.TYPE_M3U);
                                                    } else if (NSTIJKPlayerVODActivity.this.type.equals("recording")) {
                                                        NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(NSTIJKPlayerVODActivity.this.mFilePath, NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, 0, "", null, 0, 0, NSTIJKPlayerVODActivity.this.currentAPPType);
                                                    } else {
                                                        NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(String.valueOf(Uri.parse(NSTIJKPlayerVODActivity.this.mFilePath + NSTIJKPlayerVODActivity.this.opened_stream_id + "." + NSTIJKPlayerVODActivity.this.container_extension)), NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, NSTIJKPlayerVODActivity.this.opened_stream_id, NSTIJKPlayerVODActivity.this.typeofStream, NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels_Temp, currentWindowIndex, NSTIJKPlayerVODActivity.this.video_num, NSTIJKPlayerVODActivity.this.currentAPPType);
                                                    }
                                                    NSTIJKPlayerVODActivity.this.mVideoView.retryCount = 0;
                                                    NSTIJKPlayerVODActivity.this.mVideoView.retrying = false;
                                                    NSTIJKPlayerVODActivity.this.mVideoView.resuming = false;
                                                    NSTIJKPlayerVODActivity.this.mVideoView.setProgress(false);
                                                    NSTIJKPlayerVODActivity.this.mVideoView.start();
                                                    if (NSTIJKPlayerVODActivity.this.type.equals("movies")) {
                                                        VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex)).getStreamId()));
                                                        VideoInfo.getInstance().setAvailableChannels(NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels);
                                                    } else if (NSTIJKPlayerVODActivity.this.type.equals("series")) {
                                                        VideoInfo.getInstance().setEpisodeId(NSTIJKPlayerVODActivity.this.episode_id);
                                                        EpisodesUsingSinglton.getInstance().setEpisodeList(NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries);
                                                        VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex)).getId()));
                                                        VideoInfo.getInstance().setAvailableSeries(NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries);
                                                    }
                                                    VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex);
                                                    NSTIJKPlayerVODActivity.this.mVideoView.setProgress(false);
                                                }
                                            }, 200);
                                        }
                                        String str2 = this.type;
                                        hashCode = str2.hashCode();
                                        if (hashCode == -1068259517) {
                                            if (hashCode != -905838985) {
                                                if (hashCode == 993558001) {
                                                    if (str2.equals("recording")) {
                                                        switch (c2) {
                                                            case 0:
                                                                this.currentProgramStreamID = Utils.parseIntMinusOne(this.liveListDetailAvailableChannels.get(currentWindowIndex).getStreamId());
                                                                if (this.loginPrefsEditor != null) {
                                                                    this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableChannels.get(currentWindowIndex).getStreamId()));
                                                                    this.loginPrefsEditor.apply();
                                                                    break;
                                                                }
                                                                break;
                                                            case 1:
                                                                this.currentProgramStreamID = Utils.parseIntMinusOne(this.liveListDetailAvailableSeries.get(currentWindowIndex).getId());
                                                                if (this.loginPrefsEditor != null) {
                                                                    this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableSeries.get(currentWindowIndex).getId()));
                                                                    this.loginPrefsEditor.apply();
                                                                    break;
                                                                }
                                                                break;
                                                            case 2:
                                                                this.currentProgramStreamID = Utils.parseIntMinusOne(this.liveListRecording.get(currentWindowIndex).getAbsolutePath());
                                                                if (this.loginPrefsEditor != null) {
                                                                    this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListRecording.get(currentWindowIndex).getAbsolutePath()));
                                                                    this.loginPrefsEditor.apply();
                                                                    break;
                                                                }
                                                                break;
                                                        }
                                                        if (this.loginPrefsEditorPosition != null) {
                                                            this.loginPrefsEditorPosition.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, String.valueOf(currentWindowIndex));
                                                            this.loginPrefsEditorPosition.apply();
                                                            return;
                                                        }
                                                        return;
                                                    }
                                                }
                                            } else if (str2.equals("series")) {
                                                c2 = 1;
                                                switch (c2) {
                                                }
                                                if (this.loginPrefsEditorPosition != null) {
                                                }
                                            }
                                        } else if (str2.equals("movies")) {
                                            c2 = 0;
                                            switch (c2) {
                                            }
                                            if (this.loginPrefsEditorPosition != null) {
                                            }
                                        }
                                        c2 = 65535;
                                        switch (c2) {
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
                                    /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass7 */

                                    public void run() {
                                        NSTIJKPlayerVODActivity.this.release();
                                        if (NSTIJKPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U) && !NSTIJKPlayerVODActivity.this.type.equals("recording")) {
                                            NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(NSTIJKPlayerVODActivity.this.m3uVideoURL, NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, 0, "", null, currentWindowIndex, 0, NSTIJKPlayerVODActivity.this.currentAPPType);
                                            VideoInfo.getInstance().setAPPType(AppConst.TYPE_M3U);
                                        } else if (NSTIJKPlayerVODActivity.this.type.equals("recording")) {
                                            NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(NSTIJKPlayerVODActivity.this.mFilePath, NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, 0, "", null, 0, 0, NSTIJKPlayerVODActivity.this.currentAPPType);
                                        } else {
                                            NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(String.valueOf(Uri.parse(NSTIJKPlayerVODActivity.this.mFilePath + NSTIJKPlayerVODActivity.this.opened_stream_id + "." + NSTIJKPlayerVODActivity.this.container_extension)), NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, NSTIJKPlayerVODActivity.this.opened_stream_id, NSTIJKPlayerVODActivity.this.typeofStream, NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels_Temp, currentWindowIndex, NSTIJKPlayerVODActivity.this.video_num, NSTIJKPlayerVODActivity.this.currentAPPType);
                                        }
                                        NSTIJKPlayerVODActivity.this.mVideoView.retryCount = 0;
                                        NSTIJKPlayerVODActivity.this.mVideoView.retrying = false;
                                        NSTIJKPlayerVODActivity.this.mVideoView.resuming = false;
                                        NSTIJKPlayerVODActivity.this.mVideoView.setProgress(false);
                                        NSTIJKPlayerVODActivity.this.mVideoView.start();
                                        if (NSTIJKPlayerVODActivity.this.type.equals("movies")) {
                                            VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex)).getStreamId()));
                                            VideoInfo.getInstance().setAvailableChannels(NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels);
                                        } else if (NSTIJKPlayerVODActivity.this.type.equals("series")) {
                                            VideoInfo.getInstance().setEpisodeId(NSTIJKPlayerVODActivity.this.episode_id);
                                            EpisodesUsingSinglton.getInstance().setEpisodeList(NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries);
                                            VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex)).getId()));
                                            VideoInfo.getInstance().setAvailableSeries(NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries);
                                        }
                                        VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex);
                                        NSTIJKPlayerVODActivity.this.mVideoView.setProgress(false);
                                    }
                                }, 200);
                                String str22 = this.type;
                                hashCode = str22.hashCode();
                                if (hashCode == -1068259517) {
                                }
                                c2 = 65535;
                                switch (c2) {
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
                                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass7 */

                                public void run() {
                                    NSTIJKPlayerVODActivity.this.release();
                                    if (NSTIJKPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U) && !NSTIJKPlayerVODActivity.this.type.equals("recording")) {
                                        NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(NSTIJKPlayerVODActivity.this.m3uVideoURL, NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, 0, "", null, currentWindowIndex, 0, NSTIJKPlayerVODActivity.this.currentAPPType);
                                        VideoInfo.getInstance().setAPPType(AppConst.TYPE_M3U);
                                    } else if (NSTIJKPlayerVODActivity.this.type.equals("recording")) {
                                        NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(NSTIJKPlayerVODActivity.this.mFilePath, NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, 0, "", null, 0, 0, NSTIJKPlayerVODActivity.this.currentAPPType);
                                    } else {
                                        NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(String.valueOf(Uri.parse(NSTIJKPlayerVODActivity.this.mFilePath + NSTIJKPlayerVODActivity.this.opened_stream_id + "." + NSTIJKPlayerVODActivity.this.container_extension)), NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, NSTIJKPlayerVODActivity.this.opened_stream_id, NSTIJKPlayerVODActivity.this.typeofStream, NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels_Temp, currentWindowIndex, NSTIJKPlayerVODActivity.this.video_num, NSTIJKPlayerVODActivity.this.currentAPPType);
                                    }
                                    NSTIJKPlayerVODActivity.this.mVideoView.retryCount = 0;
                                    NSTIJKPlayerVODActivity.this.mVideoView.retrying = false;
                                    NSTIJKPlayerVODActivity.this.mVideoView.resuming = false;
                                    NSTIJKPlayerVODActivity.this.mVideoView.setProgress(false);
                                    NSTIJKPlayerVODActivity.this.mVideoView.start();
                                    if (NSTIJKPlayerVODActivity.this.type.equals("movies")) {
                                        VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex)).getStreamId()));
                                        VideoInfo.getInstance().setAvailableChannels(NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels);
                                    } else if (NSTIJKPlayerVODActivity.this.type.equals("series")) {
                                        VideoInfo.getInstance().setEpisodeId(NSTIJKPlayerVODActivity.this.episode_id);
                                        EpisodesUsingSinglton.getInstance().setEpisodeList(NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries);
                                        VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex)).getId()));
                                        VideoInfo.getInstance().setAvailableSeries(NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries);
                                    }
                                    VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex);
                                    NSTIJKPlayerVODActivity.this.mVideoView.setProgress(false);
                                }
                            }, 200);
                            String str222 = this.type;
                            hashCode = str222.hashCode();
                            if (hashCode == -1068259517) {
                            }
                            c2 = 65535;
                            switch (c2) {
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
                            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass7 */

                            public void run() {
                                NSTIJKPlayerVODActivity.this.release();
                                if (NSTIJKPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U) && !NSTIJKPlayerVODActivity.this.type.equals("recording")) {
                                    NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(NSTIJKPlayerVODActivity.this.m3uVideoURL, NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, 0, "", null, currentWindowIndex, 0, NSTIJKPlayerVODActivity.this.currentAPPType);
                                    VideoInfo.getInstance().setAPPType(AppConst.TYPE_M3U);
                                } else if (NSTIJKPlayerVODActivity.this.type.equals("recording")) {
                                    NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(NSTIJKPlayerVODActivity.this.mFilePath, NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, 0, "", null, 0, 0, NSTIJKPlayerVODActivity.this.currentAPPType);
                                } else {
                                    NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(String.valueOf(Uri.parse(NSTIJKPlayerVODActivity.this.mFilePath + NSTIJKPlayerVODActivity.this.opened_stream_id + "." + NSTIJKPlayerVODActivity.this.container_extension)), NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, NSTIJKPlayerVODActivity.this.opened_stream_id, NSTIJKPlayerVODActivity.this.typeofStream, NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels_Temp, currentWindowIndex, NSTIJKPlayerVODActivity.this.video_num, NSTIJKPlayerVODActivity.this.currentAPPType);
                                }
                                NSTIJKPlayerVODActivity.this.mVideoView.retryCount = 0;
                                NSTIJKPlayerVODActivity.this.mVideoView.retrying = false;
                                NSTIJKPlayerVODActivity.this.mVideoView.resuming = false;
                                NSTIJKPlayerVODActivity.this.mVideoView.setProgress(false);
                                NSTIJKPlayerVODActivity.this.mVideoView.start();
                                if (NSTIJKPlayerVODActivity.this.type.equals("movies")) {
                                    VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex)).getStreamId()));
                                    VideoInfo.getInstance().setAvailableChannels(NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels);
                                } else if (NSTIJKPlayerVODActivity.this.type.equals("series")) {
                                    VideoInfo.getInstance().setEpisodeId(NSTIJKPlayerVODActivity.this.episode_id);
                                    EpisodesUsingSinglton.getInstance().setEpisodeList(NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries);
                                    VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex)).getId()));
                                    VideoInfo.getInstance().setAvailableSeries(NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries);
                                }
                                VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex);
                                NSTIJKPlayerVODActivity.this.mVideoView.setProgress(false);
                            }
                        }, 200);
                        String str2222 = this.type;
                        hashCode = str2222.hashCode();
                        if (hashCode == -1068259517) {
                        }
                        c2 = 65535;
                        switch (c2) {
                        }
                        if (this.loginPrefsEditorPosition != null) {
                        }
                    } else {
                        return;
                    }
                } catch (Exception e6) {
                    Log.e("NSTIJPLAYERVODACTIVTY", "exection " + e6);
                    return;
                }
                break;
            case R.id.exo_pause:
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
            case R.id.exo_play:
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
            case R.id.exo_prev:
                try {
                    if (findViewById(R.id.controls).getVisibility() == 0) {
                        stopHeaderFooterHandler();
                        showTitleBarAndFooter();
                        runHeaderFooterHandler();
                        if (this.mVideoView != null) {
                            this.vlcprevButton.requestFocus();
                            this.handlerOLD.removeCallbacksAndMessages(null);
                            previous();
                            this.channelZapped = true;
                            final int currentWindowIndex2 = VideoInfo.getInstance().getCurrentWindowIndex();
                            String str3 = this.type;
                            int hashCode3 = str3.hashCode();
                            if (hashCode3 != -1068259517) {
                                if (hashCode3 != -905838985) {
                                    if (hashCode3 == 993558001) {
                                        if (str3.equals("recording")) {
                                            switch (c2) {
                                                case 0:
                                                    if (this.liveListDetailAvailableChannels != null && this.liveListDetailAvailableChannels.size() > 1 && currentWindowIndex2 <= this.liveListDetailAvailableChannels.size() - 1) {
                                                        this.videoTitle = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getName();
                                                        this.num = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getNum();
                                                        this.typeofStream = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getStreamType();
                                                        this.container_extension = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getContaiinerExtension();
                                                        this.m3uVideoURL = this.liveListDetailAvailableChannels.get(currentWindowIndex2).getUrl();
                                                        this.opened_stream_id = Utils.parseIntMinusOne(this.liveListDetailAvailableChannels.get(currentWindowIndex2).getStreamId());
                                                        this.$.id(R.id.app_video_title).text(this.num + " - " + this.videoTitle);
                                                        this.video_num = Utils.parseIntZero(this.num);
                                                        replayVideo = true;
                                                       // break;
                                                    } else {
                                                        replayVideo = false;
                                                       // break;
                                                    }
                                                    break;
                                                case 1:
                                                    if (this.liveListDetailAvailableSeries != null && this.liveListDetailAvailableSeries.size() > 1 && currentWindowIndex2 <= this.liveListDetailAvailableSeries.size() - 1) {
                                                        this.videoTitle = this.liveListDetailAvailableSeries.get(currentWindowIndex2).getTitle();
                                                        this.container_extension = this.liveListDetailAvailableSeries.get(currentWindowIndex2).getContainerExtension();
                                                        this.num = this.liveListDetailAvailableSeries.get(currentWindowIndex2).getId();
                                                        this.video_num = Utils.parseIntZero(this.num);
                                                        this.opened_stream_id = Utils.parseIntMinusOne(this.num);
                                                        this.episode_id = this.liveListDetailAvailableSeries.get(currentWindowIndex2).getId();
                                                        this.$.id(R.id.app_video_title).text(this.num + " - " + this.videoTitle);
                                                        this.video_num = Utils.parseIntZero(this.num);
                                                        replayVideo = true;
                                                        break;
                                                    } else {
                                                        replayVideo = false;
                                                        break;
                                                    }
                                                case 2:
                                                    if (this.liveListRecording != null && this.liveListRecording.size() > 1 && currentWindowIndex2 <= this.liveListRecording.size() - 1) {
                                                        String name = this.liveListRecording.get(currentWindowIndex2).getName();
                                                        this.video_num = currentWindowIndex2;
                                                        this.mFilePath = this.liveListRecording.get(currentWindowIndex2).getAbsolutePath();
                                                        this.video_num = Utils.parseIntZero(String.valueOf(this.video_num));
                                                        this.$.id(R.id.app_video_title).text(name);
                                                        replayVideo = true;
                                                        break;
                                                    } else {
                                                        replayVideo = false;
                                                        break;
                                                    }
                                            }
                                            this.loginPrefsEditorAudio.clear();
                                            this.loginPrefsEditorAudio.apply();
                                            this.loginPrefsEditorVideo.clear();
                                            this.loginPrefsEditorVideo.apply();
                                            this.loginPrefsEditorSubtitle.clear();
                                            this.loginPrefsEditorSubtitle.apply();
                                            if (replayVideo && this.rq.booleanValue()) {
                                                this.handlerOLD.postDelayed(new Runnable() {
                                                    /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass6 */

                                                    public void run() {
                                                        NSTIJKPlayerVODActivity.this.release();
                                                        if (NSTIJKPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U) && !NSTIJKPlayerVODActivity.this.type.equals("recording")) {
                                                            VideoInfo.getInstance().setAPPType(AppConst.TYPE_M3U);
                                                            NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(NSTIJKPlayerVODActivity.this.m3uVideoURL, NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, 0, "", null, currentWindowIndex2, 0, NSTIJKPlayerVODActivity.this.currentAPPType);
                                                        } else if (NSTIJKPlayerVODActivity.this.type.equals("recording")) {
                                                            NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(NSTIJKPlayerVODActivity.this.mFilePath, NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, 0, "", null, 0, 0, NSTIJKPlayerVODActivity.this.currentAPPType);
                                                        } else {
                                                            NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(String.valueOf(Uri.parse(NSTIJKPlayerVODActivity.this.mFilePath + NSTIJKPlayerVODActivity.this.opened_stream_id + "." + NSTIJKPlayerVODActivity.this.container_extension)), NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, NSTIJKPlayerVODActivity.this.opened_stream_id, NSTIJKPlayerVODActivity.this.typeofStream, NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels_Temp, currentWindowIndex2, NSTIJKPlayerVODActivity.this.video_num, NSTIJKPlayerVODActivity.this.currentAPPType);
                                                        }
                                                        if (NSTIJKPlayerVODActivity.this.type.equals("movies")) {
                                                            VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId()));
                                                            VideoInfo.getInstance().setAvailableChannels(NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels);
                                                            NSTIJKPlayerVODActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId());
                                                        } else if (NSTIJKPlayerVODActivity.this.type.equals("series")) {
                                                            VideoInfo.getInstance().setEpisodeId(NSTIJKPlayerVODActivity.this.episode_id);
                                                            EpisodesUsingSinglton.getInstance().setEpisodeList(NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries);
                                                            VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex2)).getId()));
                                                            VideoInfo.getInstance().setAvailableSeries(NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries);
                                                            NSTIJKPlayerVODActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex2)).getId());
                                                        }
                                                        VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                                        NSTIJKPlayerVODActivity.this.mVideoView.setProgress(false);
                                                        if (NSTIJKPlayerVODActivity.this.mVideoView != null) {
                                                            NSTIJKPlayerVODActivity.this.mVideoView.retryCount = 0;
                                                            NSTIJKPlayerVODActivity.this.mVideoView.retrying = false;
                                                            NSTIJKPlayerVODActivity.this.mVideoView.resuming = true;
                                                            NSTIJKPlayerVODActivity.this.mVideoView.setMaxTime = false;
                                                            NSTIJKPlayerVODActivity.this.mVideoView.start();
                                                        }
                                                    }
                                                }, 200);
                                            }
                                            if (this.type.equals("movies") && this.loginPrefsEditor != null) {
                                                this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableChannels.get(currentWindowIndex2).getStreamId()));
                                                this.loginPrefsEditor.apply();
                                            }
                                            if (this.type.equals("series") && this.loginPrefsEditor != null) {
                                                this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableSeries.get(currentWindowIndex2).getId()));
                                                this.loginPrefsEditor.apply();
                                            }
                                            if (this.type.equals("recording")) {
                                                this.currentProgramStreamID = Utils.parseIntMinusOne(this.liveListRecording.get(currentWindowIndex2).getAbsolutePath());
                                                if (this.loginPrefsEditor != null) {
                                                    this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListRecording.get(currentWindowIndex2).getAbsolutePath()));
                                                    this.loginPrefsEditor.apply();
                                                }
                                            }
                                            if (this.loginPrefsEditorPosition == null) {
                                                this.loginPrefsEditorPosition.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_POSITION, String.valueOf(currentWindowIndex2));
                                                this.loginPrefsEditorPosition.apply();
                                                return;
                                            }
                                            return;
                                        }
                                    }
                                } else if (str3.equals("series")) {
                                    c2 = 1;
                                    switch (c2) {
                                    }
                                    this.loginPrefsEditorAudio.clear();
                                    this.loginPrefsEditorAudio.apply();
                                    this.loginPrefsEditorVideo.clear();
                                    this.loginPrefsEditorVideo.apply();
                                    this.loginPrefsEditorSubtitle.clear();
                                    this.loginPrefsEditorSubtitle.apply();
                                    this.handlerOLD.postDelayed(new Runnable() {
                                        /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass6 */

                                        public void run() {
                                            NSTIJKPlayerVODActivity.this.release();
                                            if (NSTIJKPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U) && !NSTIJKPlayerVODActivity.this.type.equals("recording")) {
                                                VideoInfo.getInstance().setAPPType(AppConst.TYPE_M3U);
                                                NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(NSTIJKPlayerVODActivity.this.m3uVideoURL, NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, 0, "", null, currentWindowIndex2, 0, NSTIJKPlayerVODActivity.this.currentAPPType);
                                            } else if (NSTIJKPlayerVODActivity.this.type.equals("recording")) {
                                                NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(NSTIJKPlayerVODActivity.this.mFilePath, NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, 0, "", null, 0, 0, NSTIJKPlayerVODActivity.this.currentAPPType);
                                            } else {
                                                NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(String.valueOf(Uri.parse(NSTIJKPlayerVODActivity.this.mFilePath + NSTIJKPlayerVODActivity.this.opened_stream_id + "." + NSTIJKPlayerVODActivity.this.container_extension)), NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, NSTIJKPlayerVODActivity.this.opened_stream_id, NSTIJKPlayerVODActivity.this.typeofStream, NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels_Temp, currentWindowIndex2, NSTIJKPlayerVODActivity.this.video_num, NSTIJKPlayerVODActivity.this.currentAPPType);
                                            }
                                            if (NSTIJKPlayerVODActivity.this.type.equals("movies")) {
                                                VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId()));
                                                VideoInfo.getInstance().setAvailableChannels(NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels);
                                                NSTIJKPlayerVODActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId());
                                            } else if (NSTIJKPlayerVODActivity.this.type.equals("series")) {
                                                VideoInfo.getInstance().setEpisodeId(NSTIJKPlayerVODActivity.this.episode_id);
                                                EpisodesUsingSinglton.getInstance().setEpisodeList(NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries);
                                                VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex2)).getId()));
                                                VideoInfo.getInstance().setAvailableSeries(NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries);
                                                NSTIJKPlayerVODActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex2)).getId());
                                            }
                                            VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                            NSTIJKPlayerVODActivity.this.mVideoView.setProgress(false);
                                            if (NSTIJKPlayerVODActivity.this.mVideoView != null) {
                                                NSTIJKPlayerVODActivity.this.mVideoView.retryCount = 0;
                                                NSTIJKPlayerVODActivity.this.mVideoView.retrying = false;
                                                NSTIJKPlayerVODActivity.this.mVideoView.resuming = true;
                                                NSTIJKPlayerVODActivity.this.mVideoView.setMaxTime = false;
                                                NSTIJKPlayerVODActivity.this.mVideoView.start();
                                            }
                                        }
                                    }, 200);
                                    this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableChannels.get(currentWindowIndex2).getStreamId()));
                                    this.loginPrefsEditor.apply();
                                    this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableSeries.get(currentWindowIndex2).getId()));
                                    this.loginPrefsEditor.apply();
                                    if (this.type.equals("recording")) {
                                    }
                                    if (this.loginPrefsEditorPosition == null) {
                                    }
                                }
                            } else if (str3.equals("movies")) {
                                c2 = 0;
                                switch (c2) {
                                }
                                this.loginPrefsEditorAudio.clear();
                                this.loginPrefsEditorAudio.apply();
                                this.loginPrefsEditorVideo.clear();
                                this.loginPrefsEditorVideo.apply();
                                this.loginPrefsEditorSubtitle.clear();
                                this.loginPrefsEditorSubtitle.apply();
                                this.handlerOLD.postDelayed(new Runnable() {
                                    /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass6 */

                                    public void run() {
                                        NSTIJKPlayerVODActivity.this.release();
                                        if (NSTIJKPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U) && !NSTIJKPlayerVODActivity.this.type.equals("recording")) {
                                            VideoInfo.getInstance().setAPPType(AppConst.TYPE_M3U);
                                            NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(NSTIJKPlayerVODActivity.this.m3uVideoURL, NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, 0, "", null, currentWindowIndex2, 0, NSTIJKPlayerVODActivity.this.currentAPPType);
                                        } else if (NSTIJKPlayerVODActivity.this.type.equals("recording")) {
                                            NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(NSTIJKPlayerVODActivity.this.mFilePath, NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, 0, "", null, 0, 0, NSTIJKPlayerVODActivity.this.currentAPPType);
                                        } else {
                                            NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(String.valueOf(Uri.parse(NSTIJKPlayerVODActivity.this.mFilePath + NSTIJKPlayerVODActivity.this.opened_stream_id + "." + NSTIJKPlayerVODActivity.this.container_extension)), NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, NSTIJKPlayerVODActivity.this.opened_stream_id, NSTIJKPlayerVODActivity.this.typeofStream, NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels_Temp, currentWindowIndex2, NSTIJKPlayerVODActivity.this.video_num, NSTIJKPlayerVODActivity.this.currentAPPType);
                                        }
                                        if (NSTIJKPlayerVODActivity.this.type.equals("movies")) {
                                            VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId()));
                                            VideoInfo.getInstance().setAvailableChannels(NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels);
                                            NSTIJKPlayerVODActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId());
                                        } else if (NSTIJKPlayerVODActivity.this.type.equals("series")) {
                                            VideoInfo.getInstance().setEpisodeId(NSTIJKPlayerVODActivity.this.episode_id);
                                            EpisodesUsingSinglton.getInstance().setEpisodeList(NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries);
                                            VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex2)).getId()));
                                            VideoInfo.getInstance().setAvailableSeries(NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries);
                                            NSTIJKPlayerVODActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex2)).getId());
                                        }
                                        VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                        NSTIJKPlayerVODActivity.this.mVideoView.setProgress(false);
                                        if (NSTIJKPlayerVODActivity.this.mVideoView != null) {
                                            NSTIJKPlayerVODActivity.this.mVideoView.retryCount = 0;
                                            NSTIJKPlayerVODActivity.this.mVideoView.retrying = false;
                                            NSTIJKPlayerVODActivity.this.mVideoView.resuming = true;
                                            NSTIJKPlayerVODActivity.this.mVideoView.setMaxTime = false;
                                            NSTIJKPlayerVODActivity.this.mVideoView.start();
                                        }
                                    }
                                }, 200);
                                this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableChannels.get(currentWindowIndex2).getStreamId()));
                                this.loginPrefsEditor.apply();
                                this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableSeries.get(currentWindowIndex2).getId()));
                                this.loginPrefsEditor.apply();
                                if (this.type.equals("recording")) {
                                }
                                if (this.loginPrefsEditorPosition == null) {
                                }
                            }
                            c2 = 65535;
                            switch (c2) {
                            }
                            this.loginPrefsEditorAudio.clear();
                            this.loginPrefsEditorAudio.apply();
                            this.loginPrefsEditorVideo.clear();
                            this.loginPrefsEditorVideo.apply();
                            this.loginPrefsEditorSubtitle.clear();
                            this.loginPrefsEditorSubtitle.apply();
                            this.handlerOLD.postDelayed(new Runnable() {
                                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass6 */

                                public void run() {
                                    NSTIJKPlayerVODActivity.this.release();
                                    if (NSTIJKPlayerVODActivity.this.currentAPPType.equals(AppConst.TYPE_M3U) && !NSTIJKPlayerVODActivity.this.type.equals("recording")) {
                                        VideoInfo.getInstance().setAPPType(AppConst.TYPE_M3U);
                                        NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(NSTIJKPlayerVODActivity.this.m3uVideoURL, NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, 0, "", null, currentWindowIndex2, 0, NSTIJKPlayerVODActivity.this.currentAPPType);
                                    } else if (NSTIJKPlayerVODActivity.this.type.equals("recording")) {
                                        NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(NSTIJKPlayerVODActivity.this.mFilePath, NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, 0, "", null, 0, 0, NSTIJKPlayerVODActivity.this.currentAPPType);
                                    } else {
                                        NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(String.valueOf(Uri.parse(NSTIJKPlayerVODActivity.this.mFilePath + NSTIJKPlayerVODActivity.this.opened_stream_id + "." + NSTIJKPlayerVODActivity.this.container_extension)), NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, NSTIJKPlayerVODActivity.this.opened_stream_id, NSTIJKPlayerVODActivity.this.typeofStream, NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels_Temp, currentWindowIndex2, NSTIJKPlayerVODActivity.this.video_num, NSTIJKPlayerVODActivity.this.currentAPPType);
                                    }
                                    if (NSTIJKPlayerVODActivity.this.type.equals("movies")) {
                                        VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId()));
                                        VideoInfo.getInstance().setAvailableChannels(NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels);
                                        NSTIJKPlayerVODActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(((LiveStreamsDBModel) NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels.get(currentWindowIndex2)).getStreamId());
                                    } else if (NSTIJKPlayerVODActivity.this.type.equals("series")) {
                                        VideoInfo.getInstance().setEpisodeId(NSTIJKPlayerVODActivity.this.episode_id);
                                        EpisodesUsingSinglton.getInstance().setEpisodeList(NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries);
                                        VideoInfo.getInstance().setstreamid(Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex2)).getId()));
                                        VideoInfo.getInstance().setAvailableSeries(NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries);
                                        NSTIJKPlayerVODActivity.this.currentProgramStreamID = Utils.parseIntMinusOne(((GetEpisdoeDetailsCallback) NSTIJKPlayerVODActivity.this.liveListDetailAvailableSeries.get(currentWindowIndex2)).getId());
                                    }
                                    VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex2);
                                    NSTIJKPlayerVODActivity.this.mVideoView.setProgress(false);
                                    if (NSTIJKPlayerVODActivity.this.mVideoView != null) {
                                        NSTIJKPlayerVODActivity.this.mVideoView.retryCount = 0;
                                        NSTIJKPlayerVODActivity.this.mVideoView.retrying = false;
                                        NSTIJKPlayerVODActivity.this.mVideoView.resuming = true;
                                        NSTIJKPlayerVODActivity.this.mVideoView.setMaxTime = false;
                                        NSTIJKPlayerVODActivity.this.mVideoView.start();
                                    }
                                }
                            }, 200);
                            this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableChannels.get(currentWindowIndex2).getStreamId()));
                            this.loginPrefsEditor.apply();
                            this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, String.valueOf(this.liveListDetailAvailableSeries.get(currentWindowIndex2).getId()));
                            this.loginPrefsEditor.apply();
                            if (this.type.equals("recording")) {
                            }
                            if (this.loginPrefsEditorPosition == null) {
                            }
                        } else {
                            return;
                        }
                    } else {
                        stopHeaderFooterHandler();
                        showTitleBarAndFooter();
                        runHeaderFooterHandler();
                        return;
                    }
                } catch (Exception e7) {
                    Log.e("NSTIJPLAYERVODACTIVTY", "exection " + e7);
                    return;
                }
                break;
            case R.id.exo_rew:
                try {
                    if (findViewById(R.id.controls).getVisibility() == 0) {
                        stopHeaderFooterHandler();
                        showTitleBarAndFooter();
                        runHeaderFooterHandler();
                        if (this.mVideoView != null && this.mVideoView.isPlaying()) {
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
                                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass9 */

                                public void run() {
                                    if (NSTIJKPlayerVODActivity.this.mVideoView.getCurrentPosition() + NSTIJKPlayerVODActivity.this.seekBarMilliseconds > 0) {
                                        NSTIJKPlayerVODActivity.this.mVideoView.seekTo(NSTIJKPlayerVODActivity.this.mVideoView.getCurrentPosition() + NSTIJKPlayerVODActivity.this.seekBarMilliseconds);
                                    } else {
                                        NSTIJKPlayerVODActivity.this.mVideoView.seekTo(0);
                                    }
                                    NSTIJKPlayerVODActivity.this.handlerSeekbar.removeCallbacksAndMessages(null);
                                    NSTIJKPlayerVODActivity.this.handlerSeekbar.postDelayed(new Runnable() {
                                        /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass9.AnonymousClass1 */

                                        public void run() {
                                            int unused = NSTIJKPlayerVODActivity.this.seekBarMilliseconds = 0;
                                            NSTIJKPlayerVODActivity.this.ll_seek_overlay.setVisibility(8);
                                        }
                                    }, 3000);
                                }
                            }, 1000);
                            return;
                        }
                        return;
                    }
                    stopHeaderFooterHandler();
                    showTitleBarAndFooter();
                    runHeaderFooterHandler();
                    return;
                } catch (Exception e8) {
                    Log.e("NSTIJPLAYERVODACTIVTY", "exection " + e8);
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
                } catch (Exception e9) {
                    Log.e("NSTIJPLAYERVODACTIVTY", "exection " + e9);
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
        int currentWindowIndex = VideoInfo.getInstance().getCurrentWindowIndex();
        String str = this.type;
        int hashCode = str.hashCode();
        if (hashCode != -1068259517) {
            if (hashCode != -905838985) {
                if (hashCode == 993558001 && str.equals("recording")) {
                    c = 2;
                    switch (c) {
                        case 0:
                            if (currentWindowIndex == 0) {
                                VideoInfo.getInstance().setCurrentWindowIndex(this.liveListDetailAvailableChannels.size() - 1);
                                return;
                            }
                            break;
                        case 1:
                            if (currentWindowIndex == 0) {
                                VideoInfo.getInstance().setCurrentWindowIndex(this.liveListDetailAvailableSeries.size() - 1);
                                return;
                            }
                            break;
                        case 2:
                            if (currentWindowIndex == 0) {
                                VideoInfo.getInstance().setCurrentWindowIndex(this.liveListRecording.size() - 1);
                                return;
                            }
                            break;
                    }
                    VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex - 1);
                }
            } else if (str.equals("series")) {
                c = 1;
                switch (c) {
                }
                VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex - 1);
            }
        } else if (str.equals("movies")) {
            c = 0;
            switch (c) {
            }
            VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex - 1);
        }
        c = 65535;
        switch (c) {
        }
        VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex - 1);
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0065  */
    private void next() {
        char c;
        int currentWindowIndex = VideoInfo.getInstance().getCurrentWindowIndex();
        String str = this.type;
        int hashCode = str.hashCode();
        if (hashCode != -1068259517) {
            if (hashCode != -905838985) {
                if (hashCode == 993558001 && str.equals("recording")) {
                    c = 2;
                    switch (c) {
                        case 0:
                            if (currentWindowIndex == this.liveListDetailAvailableChannels.size() - 1) {
                                VideoInfo.getInstance().setCurrentWindowIndex(0);
                                return;
                            }
                            break;
                        case 1:
                            if (currentWindowIndex == this.liveListDetailAvailableSeries.size() - 1) {
                                VideoInfo.getInstance().setCurrentWindowIndex(0);
                                return;
                            }
                            break;
                        case 2:
                            if (currentWindowIndex == this.liveListRecording.size() - 1) {
                                VideoInfo.getInstance().setCurrentWindowIndex(0);
                                return;
                            }
                            break;
                    }
                    VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex + 1);
                }
            } else if (str.equals("series")) {
                c = 1;
                switch (c) {
                }
                VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex + 1);
            }
        } else if (str.equals("movies")) {
            c = 0;
            switch (c) {
            }
            VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex + 1);
        }
        c = 65535;
        switch (c) {
        }
        VideoInfo.getInstance().setCurrentWindowIndex(currentWindowIndex + 1);
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
                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass10 */

                public void onDismiss() {
                    NSTIJKPlayerVODActivity.this.hideSystemUi();
                }
            });
            RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.rl_subtitle_layout);
            RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.subtitle_radio_group);
            RadioGroup radioGroup2 = (RadioGroup) inflate.findViewById(R.id.audio_radio_group);
            RadioGroup radioGroup3 = (RadioGroup) inflate.findViewById(R.id.video_radio_group);
            this.no_audio_track = (TextView) inflate.findViewById(R.id.tv_no_audio_track);
            this.no_subtitle_track = (TextView) inflate.findViewById(R.id.tv_no_subtitle_track);
            this.no_video_track = (TextView) inflate.findViewById(R.id.tv_no_video_track);
            this.subtitle_font_size = (Spinner) inflate.findViewById(R.id.subtitle_font_size);
            try {
                this.loginPreferencesAfterLoginSubtitleSize = context2.getSharedPreferences(AppConst.LOGIN_PREF_SUB_FONT_SIZE, 0);
                this.subtitle_font_size.setSelection(((ArrayAdapter) this.subtitle_font_size.getAdapter()).getPosition(this.loginPreferencesAfterLoginSubtitleSize.getString(AppConst.LOGIN_PREF_SUB_FONT_SIZE, "20")));
            } catch (Exception unused) {
            }
            if (this.subtitle_font_size != null) {
                this.subtitle_font_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass11 */

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        String obj = NSTIJKPlayerVODActivity.this.subtitle_font_size.getItemAtPosition(i).toString();
                        SharedPreferences unused = NSTIJKPlayerVODActivity.this.loginPreferencesAfterLoginSubtitleSize = NSTIJKPlayerVODActivity.this.getSharedPreferences(AppConst.LOGIN_PREF_SUB_FONT_SIZE, 0);
                        SharedPreferences.Editor unused2 = NSTIJKPlayerVODActivity.this.loginPrefsEditorSubtitleSize = NSTIJKPlayerVODActivity.this.loginPreferencesAfterLoginSubtitleSize.edit();
                        if (NSTIJKPlayerVODActivity.this.loginPrefsEditorSubtitleSize != null) {
                            NSTIJKPlayerVODActivity.this.loginPrefsEditorSubtitleSize.putString(AppConst.LOGIN_PREF_SUB_FONT_SIZE, obj);
                            NSTIJKPlayerVODActivity.this.loginPrefsEditorSubtitleSize.apply();
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

    public void hideSystemUi() {
        try {
            if (this.mVideoView != null) {
                this.mVideoView.setSystemUiVisibility(4871);
            }
        } catch (Exception unused) {
        }
    }

    public void keyMethod() {
        stopHeaderFooterHandler();
        showTitleBarAndFooter();
        runHeaderFooterHandler();
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        boolean z = keyEvent.getRepeatCount() == 0;
        if (this.rl_next_episode != null && this.rl_next_episode.getVisibility() == 0) {
            return super.onKeyUp(i, keyEvent);
        }
        switch (i) {
            case 21:
                stopHeaderFooterHandler();
                runHeaderFooterHandler();
                return true;
            case 22:
                stopHeaderFooterHandler();
                runHeaderFooterHandler();
                return true;
            case 23:
            case 66:
                if (findViewById(R.id.app_video_top_box).getVisibility() == 0) {
                    hideTitleBarAndFooter();
                } else {
                    keyMethod();
                    if (this.mVideoView.isPlaying()) {
                        this.mVideoView.show(DateTimeConstants.MILLIS_PER_HOUR);
                        this.vlcpauseButton.requestFocus();
                    } else {
                        this.vlcplayButton.requestFocus();
                    }
                }
                return true;
            case 62:
            case 79:
            case 85:
                if (!z || this.mVideoView.isPlaying()) {
                    keyMethod();
                    this.mVideoView.pause();
                    playerPauseIconsUpdate();
                    this.vlcplayButton.requestFocus();
                } else {
                    keyMethod();
                    this.mVideoView.start();
                    playerStartIconsUpdate();
                    this.vlcpauseButton.requestFocus();
                }
                return true;
            case 86:
            case 127:
                if (z && this.mVideoView.isPlaying()) {
                    keyMethod();
                    this.mVideoView.pause();
                    playerPauseIconsUpdate();
                    this.vlcplayButton.requestFocus();
                }
                return true;
            case 89:
            case 275:
                keyMethod();
                findViewById(R.id.exo_rew).performClick();
                return true;
            case 90:
            case 274:
                keyMethod();
                findViewById(R.id.exo_ffwd).performClick();
                return true;
            case 126:
                if (z && !this.mVideoView.isPlaying()) {
                    keyMethod();
                    this.mVideoView.start();
                    playerStartIconsUpdate();
                    this.vlcpauseButton.requestFocus();
                }
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
                if (this.type.equals("catch_up")) {
                    return false;
                }
                keyMethod();
                findViewById(R.id.exo_next).performClick();
                return true;
            case 20:
            case 167:
                if (this.type.equals("catch_up")) {
                    return false;
                }
                keyMethod();
                findViewById(R.id.exo_prev).performClick();
                return true;
            case 89:
            case 275:
                keyMethod();
                findViewById(R.id.exo_rew).performClick();
                return true;
            case 90:
            case 274:
                keyMethod();
                findViewById(R.id.exo_ffwd).performClick();
                return true;
            default:
                return super.onKeyDown(i, keyEvent);
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
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("1") || NSTIJKPlayerVODActivity.this.negativeButton == null)) {
                        NSTIJKPlayerVODActivity.this.negativeButton.setBackgroundResource(R.drawable.back_btn_effect);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("9"))) {
                        NSTIJKPlayerVODActivity.this.savePasswordBT.setBackgroundResource(R.drawable.back_btn_effect);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID))) {
                        NSTIJKPlayerVODActivity.this.closedBT.setBackgroundResource(R.drawable.logout_btn_effect);
                    }
                    if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("15")) {
                        NSTIJKPlayerVODActivity.this.iv_cancel.setImageDrawable(NSTIJKPlayerVODActivity.this.getResources().getDrawable(R.drawable.ic_cancel_focus));
                        NSTIJKPlayerVODActivity.this.savePasswordBT.setBackgroundResource(R.drawable.black_button_dark);
                        NSTIJKPlayerVODActivity.this.closedBT.setBackgroundResource(R.drawable.black_button_dark);
                        return;
                    }
                    return;
                }
                view2.setBackground(NSTIJKPlayerVODActivity.this.getResources().getDrawable(R.drawable.selector_checkbox));
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
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("1") || NSTIJKPlayerVODActivity.this.negativeButton == null)) {
                    NSTIJKPlayerVODActivity.this.negativeButton.setBackgroundResource(R.drawable.black_button_dark);
                }
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("9"))) {
                    NSTIJKPlayerVODActivity.this.savePasswordBT.setBackgroundResource(R.drawable.black_button_dark);
                }
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID))) {
                    NSTIJKPlayerVODActivity.this.closedBT.setBackgroundResource(R.drawable.black_button_dark);
                }
                if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("15")) {
                    NSTIJKPlayerVODActivity.this.iv_cancel.setImageDrawable(NSTIJKPlayerVODActivity.this.getResources().getDrawable(R.drawable.ic_cancel));
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

    public void release() {
        try {
            if (this.mVideoView != null) {
                this.loginPreferences_seek_time = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
                long currentPosition = (long) this.mVideoView.getCurrentPosition();
                this.loginPreferences_seek_time = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, 0);
                this.loginPrefsEditorSeekTime = this.loginPreferences_seek_time.edit();
                this.loginPrefsEditorSeekTime.putString(AppConst.LOGIN_PREF_CURRENT_SEEK_TIME, String.valueOf(currentPosition));
                this.loginPrefsEditorSeekTime.apply();
                if (!(this.mVideoView == null || currentPosition == -1 || currentPosition == 0)) {
                    this.mVideoView.setCurrentPositionSeekbar(this.mVideoView.getCurrentPosition());
                    this.mVideoView.setProgress(true);
                    this.mVideoView.released(true);
                }
                if (this.type.equals("movies")) {
                    if (!this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                        if (!(this.mVideoView == null || VideoInfo.getInstance().getStreamid() == -1 || currentPosition == -1 || currentPosition == 0)) {
                            if (VideoInfo.getInstance().getRecentlyFinishedStreamID() == VideoInfo.getInstance().getStreamid()) {
                                updateMovieElapsedStatus(VideoInfo.getInstance().getStreamid(), 0);
                                VideoInfo.getInstance().setRecentlyFinishedStreamID(0);
                            } else {
                                updateMovieElapsedStatus(VideoInfo.getInstance().getStreamid(), currentPosition);
                            }
                        }
                    }
                } else if (this.type.equals("series")) {
                    if (this.mVideoView == null || VideoInfo.getInstance().getAPPType() == null || !this.currentAPPType.equals(AppConst.TYPE_M3U)) {
                        if (!(VideoInfo.getInstance() == null || VideoInfo.getInstance().getEpisodeId() == null || currentPosition == -1 || currentPosition == 0)) {
                            if (VideoInfo.getInstance().getRecentlyFinishedStreamID() == Integer.parseInt(VideoInfo.getInstance().getEpisodeId())) {
                                this.seriesRecentClass.updateSeriesElapsedStatus(VideoInfo.getInstance().getEpisodeId(), 0);
                                VideoInfo.getInstance().setRecentlyFinishedStreamID(0);
                            } else {
                                this.seriesRecentClass.updateSeriesElapsedStatus(VideoInfo.getInstance().getEpisodeId(), currentPosition);
                            }
                        }
                    }
                }
            }
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

    private int checkRecentWatch(String str) {
        return this.recentWatchDBHandler1.isStreamAvailable(str);
    }

    private void showRecentWatchPopup(final Context context2, final String str, final String str2, String str3, String str4, final String str5, final List<GetEpisdoeDetailsCallback> list, final int i, String str6, String str7) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogbox);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass12 */

            public void onDismiss(DialogInterface dialogInterface) {
                NSTIJKPlayerVODActivity.this.hideSystemUi();
            }
        });
        View inflate = LayoutInflater.from(this).inflate((int) R.layout.layout_resume_player, (ViewGroup) null);
        this.movieIDTV = (TextView) inflate.findViewById(R.id.tv_movie_id);
        this.savePasswordBT = (Button) inflate.findViewById(R.id.bt_resume);
        this.iv_cancel = (ImageView) inflate.findViewById(R.id.iv_cancel);
        this.iv_cancel.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass13 */

            public void onClick(View view) {
                try {
                    NSTIJKPlayerVODActivity.this.alertDialog.setCancelable(true);
                    NSTIJKPlayerVODActivity.this.onBackPressed();
                    NSTIJKPlayerVODActivity.this.onBackPressed();
                    NSTIJKPlayerVODActivity.this.alertDialog.dismiss();
                } catch (Exception unused) {
                }
            }
        });
        this.closedBT = (Button) inflate.findViewById(R.id.bt_start_over);
        if (this.movieIDTV != null) {
            TextView textView = this.movieIDTV;
            textView.setText(str4 + "-" + str3);
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
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass14 */

            public void onClick(View view) {
                long j;
                try {
                    j = new SeriesRecentWatchDatabase(context2).gettimeElapsed(str).longValue();
                } catch (Exception unused) {
                    j = 0;
                }
                try {
                    if (NSTIJKPlayerVODActivity.this.mVideoView != null && NSTIJKPlayerVODActivity.this.rq.booleanValue()) {
                        NSTIJKPlayerVODActivity.this.release();
                        NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(String.valueOf(Uri.parse(NSTIJKPlayerVODActivity.this.mFilePath + str + "." + str2)), NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, NSTIJKPlayerVODActivity.this.opened_stream_id, str5, NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels_Temp, i, 0, NSTIJKPlayerVODActivity.this.currentAPPType);
                        VideoInfo.getInstance().setAPPType(AppConst.TYPE_API);
                        EpisodesUsingSinglton.getInstance().setEpisodeList(list);
                        VideoInfo.getInstance().setEpisodeId(str);
                        VideoInfo.getInstance().settimeElapsed(j);
                        NSTIJKPlayerVODActivity.this.mVideoView.setProgress(true);
                        NSTIJKPlayerVODActivity.this.mVideoView.setCurrentPositionSeekbar((int) j);
                        NSTIJKPlayerVODActivity.this.mVideoView.start();
                    }
                    NSTIJKPlayerVODActivity.this.alertDialog.dismiss();
                } catch (Exception unused2) {
                }
            }
        });
        this.closedBT.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity.AnonymousClass15 */

            public void onClick(View view) {
                try {
                    NSTIJKPlayerVODActivity.this.release();
                    NSTIJKPlayerVODActivity.this.mVideoView.setVideoPath(String.valueOf(Uri.parse(NSTIJKPlayerVODActivity.this.mFilePath + str + "." + str2)), NSTIJKPlayerVODActivity.this.fullScreen, NSTIJKPlayerVODActivity.this.videoTitle, 0, NSTIJKPlayerVODActivity.this.opened_stream_id, str5, NSTIJKPlayerVODActivity.this.liveListDetailAvailableChannels_Temp, i, 0, NSTIJKPlayerVODActivity.this.currentAPPType);
                    EpisodesUsingSinglton.getInstance().setEpisodeList(list);
                    VideoInfo.getInstance().setEpisodeId(str);
                    NSTIJKPlayerVODActivity.this.mVideoView.start();
                    NSTIJKPlayerVODActivity.this.alertDialog.dismiss();
                } catch (Exception unused) {
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

    @Override // android.support.v4.app.FragmentActivity
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            release();
        }
    }
}
