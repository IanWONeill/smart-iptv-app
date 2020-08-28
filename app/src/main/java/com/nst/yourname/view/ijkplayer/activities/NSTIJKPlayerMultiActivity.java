package com.nst.yourname.view.ijkplayer.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.PasswordStatusDBModel;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.adapter.MultiPlayerCategoriesAdapter;
import com.nst.yourname.view.ijkplayer.application.Settings;
import com.nst.yourname.view.ijkplayer.widget.media.FileMediaDataSource;
import com.nst.yourname.view.ijkplayer.widget.media.InfoHudViewHolder;
import com.nst.yourname.view.ijkplayer.widget.media.MeasureHelper;
import com.nst.yourname.view.ijkplayer.widget.media.MediaPlayerCompat;
import com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerMulti1;
import com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerMulti2;
import com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerMulti3;
import com.nst.yourname.view.ijkplayer.widget.media.NSTIJKPlayerMulti4;
import com.nst.yourname.view.ijkplayer.widget.media.SurfaceRenderView;
import com.nst.yourname.view.ijkplayer.widget.media.TableLayoutBinder;
import com.nst.yourname.view.ijkplayer.widget.media.TextureRenderView;
import com.nst.yourname.view.interfaces.MultiPlayerInterface;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

@SuppressWarnings("ALL")
public class NSTIJKPlayerMultiActivity extends AppCompatActivity implements MultiPlayerInterface, View.OnClickListener, View.OnLongClickListener {
    static final boolean $assertionsDisabled = false;
    private static String uk;
    private static String una;
    AsyncTask AsyncTaskLiveActivityNewFlow = null;
    public Activity activity;
    MultiPlayerCategoriesAdapter adapter;
    public int allCount = 0;
    private ProgressBar app_video_loading_1;
    private ProgressBar app_video_loading_2;
    private ProgressBar app_video_loading_3;
    private ProgressBar app_video_loading_4;
    LinearLayout app_video_status;
    LinearLayout app_video_status_2;
    LinearLayout app_video_status_3;
    LinearLayout app_video_status_4;
    TextView app_video_status_text;
    TextView app_video_status_text_2;
    TextView app_video_status_text_3;
    TextView app_video_status_text_4;
    public LinearLayout app_video_top_box;
    public LinearLayout app_video_top_box_1;
    public LinearLayout app_video_top_box_2;
    public LinearLayout app_video_top_box_3;
    public LinearLayout app_video_top_box_4;
    AppBarLayout appbarToolbar;
    public PopupWindow categoriesPopUpWindow;
    String categoryID_1 = "";
    String categoryID_2 = "";
    String categoryID_3 = "";
    String categoryID_4 = "";
    private ArrayList<PasswordStatusDBModel> categoryWithPasword;
    private String channelNum1 = "";
    private String channelNum2 = "";
    private String channelNum3 = "";
    private String channelNum4 = "";
    public Context context;
    int count_playing_channels = 0;
    private String currentAPPType;
    String currentChannelLogo;
    String currentChannelLogo_1;
    String currentChannelLogo_2;
    String currentChannelLogo_3;
    String currentChannelLogo_4;
    String currentEpgChannelID;
    String currentEpgChannelID_1;
    String currentEpgChannelID_2;
    String currentEpgChannelID_3;
    String currentEpgChannelID_4;
    public int currentlyActiveBox = 0;
    TextView date;
    ImageView defaults;
    DateFormat df;
    Date dt;
    String elv;
    String fmw;
    SimpleDateFormat fr;
    FrameLayout frameLayout;
    public GridLayoutManager gridLayoutManager;
    Handler handlerSeekbarSeekto;
    Boolean is_playing_Box_1 = false;
    Boolean is_playing_Box_2 = false;
    Boolean is_playing_Box_3 = false;
    Boolean is_playing_Box_4 = false;
    private ArrayList<String> listPassword = new ArrayList<>();
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetail;
    public ArrayList<LiveStreamCategoryIdDBModel> liveListDetailAvailable;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailAvailableForCounter;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailUnlcked;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailUnlckedDetail;
    LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesAfterLogin;
    public String mFilePath;
    private String mFilePath1 = "";
    private String mFilePath2 = "";
    private String mFilePath3 = "";
    private String mFilePath4 = "";
    private Settings mSettings;
    private NSTIJKPlayerMulti1 mVideoView1;
    private NSTIJKPlayerMulti2 mVideoView2;
    private NSTIJKPlayerMulti3 mVideoView3;
    private NSTIJKPlayerMulti4 mVideoView4;
    int maxConnections;
    Boolean multiplayedfromlive = false;
    Boolean multiscreen_edit = false;
    RecyclerView myRecyclerView;
    private Button negativeButton;
    boolean no_channel = false;
    ProgressBar pbLoader;
    RelativeLayout rl_no_arrangement_found;
    private Boolean rq = true;
    public Runnable runnableSeekbar;
    ImageView screen1;
    ImageView screen2;
    ImageView screen3;
    ImageView screen4;
    ImageView screen5;
    public String screenChoice;
    View screenLayout = null;
    SharedPreferences.Editor sharedPrefEditor;
    SharedPreferences sharedPreferences;
    public PopupWindow sreenPopupWindow;
    final boolean[] stopRunnable = {false};
    private int streamID_1 = 0;
    private int streamID_2 = 0;
    private int streamID_3 = 0;
    private int streamID_4 = 0;
    TextView time;
    TextView tv_settings;
    String ukd;
    String unad;
    String videoTitle1 = "";
    String videoTitle2 = "";
    String videoTitle3 = "";
    String videoTitle4 = "";
    String videoURL_1 = "";
    String videoURL_2 = "";
    String videoURL_3 = "";
    String videoURL_4 = "";

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        this.context = this;
        requestWindowFeature(1);
        super.onCreate(bundle);
        this.screenChoice = SharepreferenceDBHandler.getScreenTYPE(this.context);
        setLayout();
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.maxConnections = Integer.parseInt(this.loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_MAX_CONNECTIONS, ""));
        if (SharepreferenceDBHandler.getShowPopup(this.context)) {
            new Handler().postDelayed(new Runnable() {
                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerMultiActivity.AnonymousClass1 */

                public void run() {
                    NSTIJKPlayerMultiActivity.this.screenPopup(NSTIJKPlayerMultiActivity.this.context);
                }
            }, 100);
            showPopup();
        }
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        this.mFilePath1 = getIntent().getStringExtra("url");
        this.channelNum1 = String.valueOf(getIntent().getIntExtra("CHANNEL_NUM", 0));
        this.multiplayedfromlive = Boolean.valueOf(getIntent().getBooleanExtra("Multiplayedwfromlive", false));
        if (this.multiplayedfromlive.booleanValue()) {
            this.count_playing_channels = 1;
            this.is_playing_Box_1 = true;
        } else {
            this.count_playing_channels = 0;
            this.is_playing_Box_1 = false;
        }
        if (this.channelNum1.equals(AppConst.PASSWORD_UNSET) || this.mFilePath1.equals("")) {
            requestfocus(1, false, false);
        } else {
            playFirstTime();
        }
    }

    private void showPopup() {
        try {
            this.handlerSeekbarSeekto = new Handler();
            this.runnableSeekbar = new Runnable() {
                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerMultiActivity.AnonymousClass2 */

                public void run() {
                    if (NSTIJKPlayerMultiActivity.this.sreenPopupWindow == null) {
                        NSTIJKPlayerMultiActivity.this.stopRunnable[0] = false;
                        NSTIJKPlayerMultiActivity.this.handlerSeekbarSeekto.postDelayed(NSTIJKPlayerMultiActivity.this.runnableSeekbar, 100);
                    } else if (NSTIJKPlayerMultiActivity.this.sreenPopupWindow.isShowing()) {
                        NSTIJKPlayerMultiActivity.this.stopRunnable[0] = true;
                        NSTIJKPlayerMultiActivity.this.handlerSeekbarSeekto.removeCallbacks(NSTIJKPlayerMultiActivity.this.runnableSeekbar);
                    } else {
                        NSTIJKPlayerMultiActivity.this.screenPopup(NSTIJKPlayerMultiActivity.this.context);
                    }
                    if (!NSTIJKPlayerMultiActivity.this.stopRunnable[0]) {
                        NSTIJKPlayerMultiActivity.this.handlerSeekbarSeekto.postDelayed(NSTIJKPlayerMultiActivity.this.runnableSeekbar, 100);
                    }
                }
            };
            if (!this.stopRunnable[0]) {
                this.runnableSeekbar.run();
            }
        } catch (Exception e) {
            Log.d("", "" + e);
        }
    }

    @SuppressLint({"ResourceType"})
    public void screenPopup(Context context2) {
        this.screenLayout = ((LayoutInflater) context2.getSystemService("layout_inflater")).inflate((int) R.layout.screen_selector_popup, (LinearLayout) findViewById(R.id.screenPopup));
        this.defaults = (ImageView) this.screenLayout.findViewById(R.id.deafult);
        this.screen1 = (ImageView) this.screenLayout.findViewById(R.id.screen1);
        this.screen2 = (ImageView) this.screenLayout.findViewById(R.id.screen2);
        this.screen3 = (ImageView) this.screenLayout.findViewById(R.id.screen3);
        this.screen4 = (ImageView) this.screenLayout.findViewById(R.id.screen4);
        this.screen5 = (ImageView) this.screenLayout.findViewById(R.id.screen5);
        this.sreenPopupWindow = new PopupWindow(context2);
        this.sreenPopupWindow.setContentView(this.screenLayout);
        this.sreenPopupWindow.setWidth(-1);
        this.sreenPopupWindow.setHeight(-1);
        this.sreenPopupWindow.setFocusable(true);
        this.sreenPopupWindow.showAtLocation(this.screenLayout, 0, 0, 0);
        this.defaults.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerMultiActivity.AnonymousClass3 */

            public void onClick(View view) {
                NSTIJKPlayerMultiActivity.this.screenChoice = "default";
                NSTIJKPlayerMultiActivity.this.setLayout();
                NSTIJKPlayerMultiActivity.this.sreenPopupWindow.dismiss();
            }
        });
        this.screen1.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerMultiActivity.AnonymousClass4 */

            public void onClick(View view) {
                NSTIJKPlayerMultiActivity.this.screenChoice = "screen1";
                NSTIJKPlayerMultiActivity.this.setLayout();
                NSTIJKPlayerMultiActivity.this.sreenPopupWindow.dismiss();
            }
        });
        this.screen2.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerMultiActivity.AnonymousClass5 */

            public void onClick(View view) {
                NSTIJKPlayerMultiActivity.this.screenChoice = "screen2";
                NSTIJKPlayerMultiActivity.this.setLayout();
                NSTIJKPlayerMultiActivity.this.sreenPopupWindow.dismiss();
            }
        });
        this.screen3.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerMultiActivity.AnonymousClass6 */

            public void onClick(View view) {
                NSTIJKPlayerMultiActivity.this.screenChoice = "screen3";
                NSTIJKPlayerMultiActivity.this.setLayout();
                NSTIJKPlayerMultiActivity.this.sreenPopupWindow.dismiss();
            }
        });
        this.screen4.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerMultiActivity.AnonymousClass7 */

            public void onClick(View view) {
                NSTIJKPlayerMultiActivity.this.screenChoice = "screen4";
                NSTIJKPlayerMultiActivity.this.setLayout();
                NSTIJKPlayerMultiActivity.this.sreenPopupWindow.dismiss();
            }
        });
        this.screen5.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerMultiActivity.AnonymousClass8 */

            public void onClick(View view) {
                NSTIJKPlayerMultiActivity.this.screenChoice = "screen5";
                NSTIJKPlayerMultiActivity.this.setLayout();
                NSTIJKPlayerMultiActivity.this.sreenPopupWindow.dismiss();
            }
        });
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public void setLayout() {
        char c;
        String str = this.screenChoice;
        switch (str.hashCode()) {
            case 1926384965:
                if (str.equals("screen1")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1926384966:
                if (str.equals("screen2")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1926384967:
                if (str.equals("screen3")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1926384968:
                if (str.equals("screen4")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1926384969:
                if (str.equals("screen5")) {
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
                setContentView((int) R.layout.activity_vlcplayer_multiplayer1);
                break;
            case 1:
                setContentView((int) R.layout.activity_vlcplayer_multiplayer2);
                break;
            case 2:
                setContentView((int) R.layout.activity_vlcplayer_multiplayer3);
                break;
            case 3:
                setContentView((int) R.layout.activity_vlcplayer_multiplayer4);
                break;
            case 4:
                setContentView((int) R.layout.activity_vlcplayer_multiplayer5);
                break;
            default:
                setContentView((int) R.layout.activity_vlcplayer_multiplayer);
                break;
        }
        initializeVariables();
        if (this.channelNum1.equals(AppConst.PASSWORD_UNSET) || this.mFilePath1.equals("")) {
            requestfocus(1, false, false);
        } else {
            playFirstTime();
        }
    }

    private void initializeVariables() {
        this.liveListDetailAvailable = new ArrayList<>();
        this.liveListDetail = new ArrayList<>();
        this.liveListDetailUnlckedDetail = new ArrayList<>();
        this.liveListDetailUnlcked = new ArrayList<>();
        this.categoryWithPasword = new ArrayList<>();
        this.liveListDetailAvailableForCounter = new ArrayList<>();
        this.sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.mSettings = new Settings(this.context);
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            this.currentAPPType = AppConst.TYPE_M3U;
        } else {
            this.currentAPPType = AppConst.TYPE_API;
        }
        this.liveStreamDBHandler = new LiveStreamDBHandler(this);
        this.elv = Utils.ukde(TextureRenderView.pZGV() + TextureRenderView.mv());
        this.mVideoView1 = (NSTIJKPlayerMulti1) findViewById(R.id.video_view_1);
        this.mVideoView2 = (NSTIJKPlayerMulti2) findViewById(R.id.video_view_2);
        this.mVideoView3 = (NSTIJKPlayerMulti3) findViewById(R.id.video_view_3);
        this.mVideoView4 = (NSTIJKPlayerMulti4) findViewById(R.id.video_view_4);
        this.mVideoView1.setLiveStreamDBHandler(this.liveStreamDBHandler);
        this.mVideoView2.setLiveStreamDBHandler(this.liveStreamDBHandler);
        this.mVideoView3.setLiveStreamDBHandler(this.liveStreamDBHandler);
        this.mVideoView4.setLiveStreamDBHandler(this.liveStreamDBHandler);
        this.mVideoView1.setActivity(this, this.mVideoView1);
        this.mVideoView2.setActivity(this, this.mVideoView2);
        this.mVideoView3.setActivity(this, this.mVideoView3);
        this.mVideoView4.setActivity(this, this.mVideoView4);
        this.app_video_top_box_1 = (LinearLayout) findViewById(R.id.app_video_top_box);
        this.app_video_top_box_2 = (LinearLayout) findViewById(R.id.app_video_top_box_2);
        this.app_video_top_box_3 = (LinearLayout) findViewById(R.id.app_video_top_box_3);
        this.app_video_top_box_4 = (LinearLayout) findViewById(R.id.app_video_top_box_4);
        this.unad = Utils.ukde(MeasureHelper.pnm());
        this.dt = new Date();
        this.date = (TextView) findViewById(R.id.date);
        this.time = (TextView) findViewById(R.id.time);
        this.app_video_status = (LinearLayout) findViewById(R.id.app_video_status);
        this.app_video_status_2 = (LinearLayout) findViewById(R.id.app_video_status_2);
        this.app_video_status_3 = (LinearLayout) findViewById(R.id.app_video_status_3);
        this.app_video_status_4 = (LinearLayout) findViewById(R.id.app_video_status_4);
        this.app_video_status_text = (TextView) findViewById(R.id.app_video_status_text);
        this.app_video_status_text_2 = (TextView) findViewById(R.id.app_video_status_text_2);
        this.app_video_status_text_3 = (TextView) findViewById(R.id.app_video_status_text_3);
        this.app_video_status_text_4 = (TextView) findViewById(R.id.app_video_status_text_4);
        this.app_video_loading_1 = (ProgressBar) findViewById(R.id.app_video_loading_1);
        this.app_video_loading_2 = (ProgressBar) findViewById(R.id.app_video_loading_2);
        this.app_video_loading_3 = (ProgressBar) findViewById(R.id.app_video_loading_3);
        this.app_video_loading_4 = (ProgressBar) findViewById(R.id.app_video_loading_4);
        this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Exception | UnsatisfiedLinkError unused) {
        }
        this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        findViewById(R.id.app_video_box_1).setOnClickListener(this);
        findViewById(R.id.app_video_box_1).setOnLongClickListener(this);
        findViewById(R.id.app_video_box_2).setOnClickListener(this);
        findViewById(R.id.app_video_box_2).setOnLongClickListener(this);
        findViewById(R.id.app_video_box_3).setOnClickListener(this);
        findViewById(R.id.app_video_box_3).setOnLongClickListener(this);
        findViewById(R.id.app_video_box_4).setOnClickListener(this);
        findViewById(R.id.app_video_box_4).setOnLongClickListener(this);
        this.ukd = Utils.ukde(FileMediaDataSource.apn());
        uk = getApplicationName(this.context);
        una = getApplicationContext().getPackageName();
        this.fmw = Utils.ukde(SurfaceRenderView.bCBhdXR() + SurfaceRenderView.mw());
    }

    private ArrayList<LiveStreamCategoryIdDBModel> getUnlockedCategories(ArrayList<LiveStreamCategoryIdDBModel> arrayList, ArrayList<String> arrayList2) {
        if (arrayList == null) {
            return null;
        }
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

    public void noChannelFound() {
        this.app_video_status.setVisibility(0);
        this.app_video_status_text.setText(this.context.getResources().getString(R.string.no_channel_found));
    }

    public static String getApplicationName(Context context2) {
        return String.valueOf(context2.getApplicationInfo().loadLabel(context2.getPackageManager()));
    }

    @SuppressLint({"SetTextI18n"})
    private void playFirstTime() {
        requestfocus(1, true, true);
        if (avu2jnkv()) {
            ArrayList<LiveStreamsDBModel> channelDetails = this.liveStreamDBHandler.getChannelDetails(this.channelNum1, "live");
            if (!(channelDetails == null || channelDetails.size() == 0)) {
                this.videoTitle1 = channelDetails.get(0).getName();
                this.currentEpgChannelID_1 = channelDetails.get(0).getEpgChannelId();
                this.currentChannelLogo_1 = channelDetails.get(0).getStreamIcon();
                this.categoryID_1 = channelDetails.get(0).getCategoryId();
                this.videoURL_1 = channelDetails.get(0).getUrl();
                try {
                    this.streamID_1 = Integer.parseInt(channelDetails.get(0).getStreamId());
                } catch (NumberFormatException unused) {
                    this.streamID_1 = -1;
                }
            }
            this.mVideoView1.removeHandlerCallback();
            if (this.rq.booleanValue()) {
                this.mVideoView1.setVideoURI(Uri.parse(this.mFilePath1), true, this.videoTitle1);
                this.mVideoView1.retryCount = 0;
                this.mVideoView1.retrying = false;
                this.mVideoView1.start();
                return;
            }
            return;
        }
        setStatus(1);
    }

    @SuppressLint({"SetTextI18n"})
    public void playFirstTime2() {
        requestfocus(2, true, true);
        if (avu2jnkv()) {
            ArrayList<LiveStreamsDBModel> channelDetails = this.liveStreamDBHandler.getChannelDetails(this.channelNum2, "live");
            if (!(channelDetails == null || channelDetails.size() == 0)) {
                this.videoTitle2 = channelDetails.get(0).getName();
                this.currentEpgChannelID_2 = channelDetails.get(0).getEpgChannelId();
                this.currentChannelLogo_2 = channelDetails.get(0).getStreamIcon();
                this.categoryID_2 = channelDetails.get(0).getCategoryId();
                this.videoURL_2 = channelDetails.get(0).getUrl();
                try {
                    this.streamID_2 = Integer.parseInt(channelDetails.get(0).getStreamId());
                } catch (NumberFormatException unused) {
                    this.streamID_2 = -1;
                }
            }
            this.mVideoView2.removeHandlerCallback();
            if (this.rq.booleanValue()) {
                this.mVideoView2.setVideoURI(Uri.parse(this.mFilePath2), true, this.videoTitle2);
                this.mVideoView2.retryCount = 0;
                this.mVideoView2.retrying = false;
                this.mVideoView2.start();
                return;
            }
            return;
        }
        setStatus(2);
    }

    @SuppressLint({"SetTextI18n"})
    public void playFirstTime3() {
        requestfocus(3, true, true);
        if (avu2jnkv()) {
            ArrayList<LiveStreamsDBModel> channelDetails = this.liveStreamDBHandler.getChannelDetails(this.channelNum3, "live");
            if (!(channelDetails == null || channelDetails.size() == 0)) {
                this.videoTitle3 = channelDetails.get(0).getName();
                this.currentEpgChannelID_3 = channelDetails.get(0).getEpgChannelId();
                this.currentChannelLogo_3 = channelDetails.get(0).getStreamIcon();
                this.categoryID_3 = channelDetails.get(0).getCategoryId();
                this.videoURL_3 = channelDetails.get(0).getUrl();
                try {
                    this.streamID_3 = Integer.parseInt(channelDetails.get(0).getStreamId());
                } catch (NumberFormatException unused) {
                    this.streamID_3 = -1;
                }
            }
            this.mVideoView3.removeHandlerCallback();
            if (this.rq.booleanValue()) {
                this.mVideoView3.setVideoURI(Uri.parse(this.mFilePath3), true, this.videoTitle3);
                this.mVideoView3.retryCount = 0;
                this.mVideoView3.retrying = false;
                this.mVideoView3.start();
                return;
            }
            return;
        }
        setStatus(3);
    }

    @SuppressLint({"SetTextI18n"})
    public void playFirstTime4() {
        requestfocus(4, true, true);
        if (avu2jnkv()) {
            ArrayList<LiveStreamsDBModel> channelDetails = this.liveStreamDBHandler.getChannelDetails(this.channelNum4, "live");
            if (!(channelDetails == null || channelDetails.size() == 0)) {
                this.videoTitle4 = channelDetails.get(0).getName();
                this.currentEpgChannelID_4 = channelDetails.get(0).getEpgChannelId();
                this.currentChannelLogo_4 = channelDetails.get(0).getStreamIcon();
                this.categoryID_4 = channelDetails.get(0).getCategoryId();
                this.videoURL_4 = channelDetails.get(0).getUrl();
                try {
                    this.streamID_4 = Integer.parseInt(channelDetails.get(0).getStreamId());
                } catch (NumberFormatException unused) {
                    this.streamID_4 = -1;
                }
            }
            this.mVideoView4.removeHandlerCallback();
            if (this.rq.booleanValue()) {
                this.mVideoView4.setVideoURI(Uri.parse(this.mFilePath4), true, this.videoTitle4);
                this.mVideoView4.retryCount = 0;
                this.mVideoView4.retrying = false;
                this.mVideoView4.start();
                return;
            }
            return;
        }
        setStatus(4);
    }

    private boolean avu2jnkv() {
        if (df(this.fr, this.fr.format(new Date(MediaPlayerCompat.cit(this.context))), this.df.format(this.dt)) >= ((long) InfoHudViewHolder.ux()) && this.ukd != null && this.unad != null && (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad)))) {
            this.rq = false;
        }
        return this.rq.booleanValue();
    }

    private void setStatus(int i) {
        String ukde = Utils.ukde(TableLayoutBinder.aW5nIGl() + TableLayoutBinder.mu());
        if (i == 1) {
            this.mVideoView1.setVisibility(8);
            this.app_video_status.setVisibility(0);
            TextView textView = this.app_video_status_text;
            textView.setText(ukde + this.elv + this.fmw);
        } else if (i == 2) {
            this.mVideoView2.setVisibility(8);
            this.app_video_status_2.setVisibility(0);
            TextView textView2 = this.app_video_status_text_2;
            textView2.setText(ukde + this.elv + this.fmw);
        } else if (i == 3) {
            this.mVideoView3.setVisibility(8);
            this.app_video_status_3.setVisibility(0);
            TextView textView3 = this.app_video_status_text_3;
            textView3.setText(ukde + this.elv + this.fmw);
        } else if (i == 4) {
            this.mVideoView4.setVisibility(8);
            this.app_video_status_4.setVisibility(0);
            TextView textView4 = this.app_video_status_text_4;
            textView4.setText(ukde + this.elv + this.fmw);
        }
    }

    private void requestfocus(int i, boolean z, boolean z2) {
        removeBackgroundFocus();
        if (i == 1) {
            if ((z && this.app_video_status.getVisibility() != 0) || (z && z2)) {
                this.mVideoView1.setVisibility(0);
            }
            findViewById(R.id.app_video_box_1).setFocusable(true);
            findViewById(R.id.app_video_box_1).requestFocus();
            findViewById(R.id.app_video_box_1).setBackground(getResources().getDrawable(R.drawable.shape_checkbox_focused));
            findViewById(R.id.app_video_box_2).setFocusable(false);
            findViewById(R.id.app_video_box_3).setFocusable(false);
            findViewById(R.id.app_video_box_4).setFocusable(false);
        } else if (i == 2) {
            if ((z && this.app_video_status_2.getVisibility() != 0) || (z && z2)) {
                this.mVideoView2.setVisibility(0);
            }
            findViewById(R.id.app_video_box_2).setFocusable(true);
            findViewById(R.id.app_video_box_2).requestFocus();
            findViewById(R.id.app_video_box_2).setBackground(getResources().getDrawable(R.drawable.shape_checkbox_focused));
            findViewById(R.id.app_video_box_1).setFocusable(false);
            findViewById(R.id.app_video_box_3).setFocusable(false);
            findViewById(R.id.app_video_box_4).setFocusable(false);
        } else if (i == 3) {
            if ((z && this.app_video_status_3.getVisibility() != 0) || (z && z2)) {
                this.mVideoView3.setVisibility(0);
            }
            findViewById(R.id.app_video_box_3).setFocusable(true);
            findViewById(R.id.app_video_box_3).requestFocus();
            findViewById(R.id.app_video_box_3).setBackground(getResources().getDrawable(R.drawable.shape_checkbox_focused));
            findViewById(R.id.app_video_box_1).setFocusable(false);
            findViewById(R.id.app_video_box_2).setFocusable(false);
            findViewById(R.id.app_video_box_4).setFocusable(false);
        } else if (i == 4) {
            if ((z && this.app_video_status_4.getVisibility() != 0) || (z && z2)) {
                this.mVideoView4.setVisibility(0);
            }
            findViewById(R.id.app_video_box_4).setFocusable(true);
            findViewById(R.id.app_video_box_4).requestFocus();
            findViewById(R.id.app_video_box_4).setBackground(getResources().getDrawable(R.drawable.shape_checkbox_focused));
            findViewById(R.id.app_video_box_1).setFocusable(false);
            findViewById(R.id.app_video_box_2).setFocusable(false);
            findViewById(R.id.app_video_box_3).setFocusable(false);
        }
    }

    public void muteUnmute(int i) {
        if (i == 1) {
            this.mVideoView1.unMutePlayer();
            this.mVideoView2.mutePlayer();
            this.mVideoView3.mutePlayer();
            this.mVideoView4.mutePlayer();
        } else if (i == 2) {
            this.mVideoView2.unMutePlayer();
            this.mVideoView1.mutePlayer();
            this.mVideoView3.mutePlayer();
            this.mVideoView4.mutePlayer();
        } else if (i == 3) {
            this.mVideoView3.unMutePlayer();
            this.mVideoView1.mutePlayer();
            this.mVideoView2.mutePlayer();
            this.mVideoView4.mutePlayer();
        } else if (i == 4) {
            this.mVideoView4.unMutePlayer();
            this.mVideoView1.mutePlayer();
            this.mVideoView2.mutePlayer();
            this.mVideoView3.mutePlayer();
        }
    }

    private void removeBackgroundFocus() {
        findViewById(R.id.app_video_box_1).setBackground(getResources().getDrawable(R.drawable.selector_checkbox));
        findViewById(R.id.app_video_box_2).setBackground(getResources().getDrawable(R.drawable.selector_checkbox));
        findViewById(R.id.app_video_box_3).setBackground(getResources().getDrawable(R.drawable.selector_checkbox));
        findViewById(R.id.app_video_box_4).setBackground(getResources().getDrawable(R.drawable.selector_checkbox));
    }

    public static long df(SimpleDateFormat simpleDateFormat, String str, String str2) {
        try {
            return TimeUnit.DAYS.convert(simpleDateFormat.parse(str2).getTime() - simpleDateFormat.parse(str).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        Utils.appResume(this.context);
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onDestroy() {
        super.onDestroy();
        try {
            release(0);
        } catch (Exception unused) {
        }
    }

    public void release(int i) {
        Boolean bool = false;
        if (i == 0) {
            bool = true;
        }
        if ((bool.booleanValue() || i == 1) && this.mVideoView1 != null) {
            this.mVideoView1.pause();
        }
        if ((bool.booleanValue() || i == 2) && this.mVideoView2 != null) {
            this.mVideoView2.pause();
        }
        if ((bool.booleanValue() || i == 3) && this.mVideoView3 != null) {
            this.mVideoView3.pause();
        }
        if ((bool.booleanValue() || i == 4) && this.mVideoView4 != null) {
            this.mVideoView4.pause();
        }
        try {
            if ((bool.booleanValue() || i == 1) && this.mVideoView1 != null) {
                if (!this.mVideoView1.isBackgroundPlayEnabled()) {
                    this.mVideoView1.stopPlayback();
                    this.mVideoView1.release(true);
                    this.mVideoView1.stopBackgroundPlay();
                } else {
                    this.mVideoView1.enterBackground();
                }
                IjkMediaPlayer.native_profileEnd();
                if (i == 1) {
                    this.mFilePath1 = "";
                    this.mVideoView1.mUri = null;
                    this.app_video_loading_1.setVisibility(8);
                }
            }
            if ((bool.booleanValue() || i == 2) && this.mVideoView2 != null) {
                if (!this.mVideoView2.isBackgroundPlayEnabled()) {
                    this.mVideoView2.stopPlayback();
                    this.mVideoView2.release(true);
                    this.mVideoView2.stopBackgroundPlay();
                } else {
                    this.mVideoView2.enterBackground();
                }
                IjkMediaPlayer.native_profileEnd();
                if (i == 2) {
                    this.mFilePath2 = "";
                    this.mVideoView2.mUri = null;
                    this.app_video_loading_2.setVisibility(8);
                }
            }
            if ((bool.booleanValue() || i == 3) && this.mVideoView3 != null) {
                if (!this.mVideoView3.isBackgroundPlayEnabled()) {
                    this.mVideoView3.stopPlayback();
                    this.mVideoView3.release(true);
                    this.mVideoView3.stopBackgroundPlay();
                } else {
                    this.mVideoView3.enterBackground();
                }
                IjkMediaPlayer.native_profileEnd();
                if (i == 3) {
                    this.mFilePath3 = "";
                    this.mVideoView3.mUri = null;
                    this.app_video_loading_3.setVisibility(8);
                }
            }
            if ((bool.booleanValue() || i == 4) && this.mVideoView4 != null) {
                if (!this.mVideoView4.isBackgroundPlayEnabled()) {
                    this.mVideoView4.stopPlayback();
                    this.mVideoView4.release(true);
                    this.mVideoView4.stopBackgroundPlay();
                } else {
                    this.mVideoView4.enterBackground();
                }
                IjkMediaPlayer.native_profileEnd();
                if (i == 4) {
                    this.mFilePath4 = "";
                    this.mVideoView4.mUri = null;
                    this.app_video_loading_4.setVisibility(8);
                }
            }
        } catch (Exception unused) {
        }
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onStop() {
        super.onStop();
        try {
            release(0);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public void showFocusbox(String str) {
        char c;
        switch (str.hashCode()) {
            case 49:
                if (str.equals("1")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (str.equals("2")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (str.equals("3")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 52:
                if (str.equals("4")) {
                    c = 3;
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
                removeBackgroundFocus();
                this.currentlyActiveBox = 1;
                muteUnmute(1);
                findViewById(R.id.app_video_box_1).setFocusable(true);
                findViewById(R.id.app_video_box_1).requestFocus();
                findViewById(R.id.app_video_box_1).setBackground(getResources().getDrawable(R.drawable.shape_checkbox_focused));
                findViewById(R.id.app_video_box_2).setFocusable(false);
                findViewById(R.id.app_video_box_3).setFocusable(false);
                findViewById(R.id.app_video_box_4).setFocusable(false);
                return;
            case 1:
                removeBackgroundFocus();
                this.currentlyActiveBox = 2;
                muteUnmute(2);
                findViewById(R.id.app_video_box_2).setFocusable(true);
                findViewById(R.id.app_video_box_2).requestFocus();
                findViewById(R.id.app_video_box_2).setBackground(getResources().getDrawable(R.drawable.shape_checkbox_focused));
                findViewById(R.id.app_video_box_1).setFocusable(false);
                findViewById(R.id.app_video_box_3).setFocusable(false);
                findViewById(R.id.app_video_box_4).setFocusable(false);
                return;
            case 2:
                removeBackgroundFocus();
                this.currentlyActiveBox = 3;
                muteUnmute(3);
                findViewById(R.id.app_video_box_3).setFocusable(true);
                findViewById(R.id.app_video_box_3).requestFocus();
                findViewById(R.id.app_video_box_3).setBackground(getResources().getDrawable(R.drawable.shape_checkbox_focused));
                findViewById(R.id.app_video_box_1).setFocusable(false);
                findViewById(R.id.app_video_box_2).setFocusable(false);
                findViewById(R.id.app_video_box_4).setFocusable(false);
                return;
            case 3:
                removeBackgroundFocus();
                this.currentlyActiveBox = 4;
                muteUnmute(4);
                findViewById(R.id.app_video_box_4).setFocusable(true);
                findViewById(R.id.app_video_box_4).requestFocus();
                findViewById(R.id.app_video_box_4).setBackground(getResources().getDrawable(R.drawable.shape_checkbox_focused));
                findViewById(R.id.app_video_box_1).setFocusable(false);
                findViewById(R.id.app_video_box_2).setFocusable(false);
                findViewById(R.id.app_video_box_3).setFocusable(false);
                return;
            default:
                removeBackgroundFocus();
                this.currentlyActiveBox = 1;
                muteUnmute(1);
                findViewById(R.id.app_video_box_1).setFocusable(true);
                findViewById(R.id.app_video_box_1).requestFocus();
                findViewById(R.id.app_video_box_1).setBackground(getResources().getDrawable(R.drawable.shape_checkbox_focused));
                findViewById(R.id.app_video_box_2).setFocusable(false);
                findViewById(R.id.app_video_box_3).setFocusable(false);
                findViewById(R.id.app_video_box_4).setFocusable(false);
                return;
        }
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return super.onKeyUp(i, keyEvent);
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
        switch (i) {
            case 19:
                if (findViewById(R.id.app_video_box_2).isFocusable() && !this.screenChoice.equals("screen3")) {
                    showFocusbox("1");
                } else if (findViewById(R.id.app_video_box_4).isFocusable()) {
                    if (this.screenChoice.equals("screen1")) {
                        showFocusbox("1");
                    } else {
                        showFocusbox("3");
                    }
                } else if (findViewById(R.id.app_video_box_3).isFocusable() && !this.screenChoice.equals("screen4") && this.screenChoice.equals("screen5")) {
                    showFocusbox("1");
                } else if (findViewById(R.id.app_video_box_3).isFocusable() && !this.screenChoice.equals("screen4") && this.screenChoice.equals("screen3")) {
                    showFocusbox("1");
                } else if (findViewById(R.id.app_video_box_3).isFocusable() && !this.screenChoice.equals("screen4") && this.screenChoice.equals("screen2")) {
                    showFocusbox("1");
                }
                return true;
            case 20:
                if (!findViewById(R.id.app_video_box_1).isFocusable() || this.screenChoice.equals("screen4")) {
                    if (findViewById(R.id.app_video_box_3).isFocusable() && !this.screenChoice.equals("screen1") && !this.screenChoice.equals("screen3") && !this.screenChoice.equals("screen2") && !this.screenChoice.equals("screen4") && !this.screenChoice.equals("screen5")) {
                        showFocusbox("4");
                    } else if (findViewById(R.id.app_video_box_2).isFocusable() && this.screenChoice.equals("screen3")) {
                        showFocusbox("3");
                    }
                } else if (this.screenChoice.equals("screen3") || this.screenChoice.equals("screen5")) {
                    showFocusbox("3");
                } else {
                    showFocusbox("2");
                }
                return true;
            case 21:
                if (!findViewById(R.id.app_video_box_3).isFocusable() || this.screenChoice.equals("screen3") || this.screenChoice.equals("screen5")) {
                    if (findViewById(R.id.app_video_box_4).isFocusable()) {
                        if (this.screenChoice.equals("screen1")) {
                            showFocusbox("3");
                        } else {
                            showFocusbox("2");
                        }
                    } else if (findViewById(R.id.app_video_box_2).isFocusable() && this.screenChoice.equals("screen3")) {
                        showFocusbox("1");
                    }
                } else if (this.screenChoice.equals("screen1") || this.screenChoice.equals("screen2")) {
                    showFocusbox("2");
                } else {
                    showFocusbox("1");
                }
                return true;
            case 22:
                if (!findViewById(R.id.app_video_box_1).isFocusable() || this.screenChoice.equals("screen1") || this.screenChoice.equals("screen2") || this.screenChoice.equals("screen5")) {
                    if (!findViewById(R.id.app_video_box_2).isFocusable() || this.screenChoice.equals("screen3")) {
                        if (findViewById(R.id.app_video_box_3).isFocusable() && this.screenChoice.equals("screen1") && !this.screenChoice.equals("screen4") && !this.screenChoice.equals("screen3") && !this.screenChoice.equals("screen5")) {
                            showFocusbox("4");
                        }
                    } else if (this.screenChoice.equals("screen1") || this.screenChoice.equals("screen2")) {
                        showFocusbox("3");
                    } else {
                        showFocusbox("4");
                    }
                } else if (!this.screenChoice.equals("screen3") || this.screenChoice.equals("screen4")) {
                    showFocusbox("3");
                } else {
                    showFocusbox("2");
                }
                return true;
            default:
                return super.onKeyDown(i, keyEvent);
        }
    }

    private void popmenu(final int i, View view) {
        if (this.context != null) {
            this.currentlyActiveBox = i;
            PopupMenu popupMenu = new PopupMenu(this, view);
            popupMenu.getMenuInflater().inflate(R.menu.menu_multiplayer, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerMultiActivity.AnonymousClass9 */

                public boolean onMenuItemClick(MenuItem menuItem) {
                    int itemId = menuItem.getItemId();
                    if (itemId == R.id.nav_edit_channel) {
                        NSTIJKPlayerMultiActivity.this.multiscreen_edit = true;
                        NSTIJKPlayerMultiActivity.this.categoriesPopup(NSTIJKPlayerMultiActivity.this.context);
                    } else if (itemId == R.id.nav_remove_channel) {
                        NSTIJKPlayerMultiActivity.this.release(i);
                        NSTIJKPlayerMultiActivity.this.hideVideoView(i);
                        if (NSTIJKPlayerMultiActivity.this.multiscreen_edit.booleanValue()) {
                            if (NSTIJKPlayerMultiActivity.this.count_playing_channels > 0) {
                                NSTIJKPlayerMultiActivity.this.count_playing_channels--;
                            }
                            if (i == 1) {
                                NSTIJKPlayerMultiActivity.this.is_playing_Box_1 = false;
                            } else if (i == 2) {
                                NSTIJKPlayerMultiActivity.this.is_playing_Box_2 = false;
                            } else if (i == 3) {
                                NSTIJKPlayerMultiActivity.this.is_playing_Box_3 = false;
                            } else if (i == 4) {
                                NSTIJKPlayerMultiActivity.this.is_playing_Box_4 = false;
                            }
                            NSTIJKPlayerMultiActivity.this.multiscreen_edit = false;
                        } else {
                            if (NSTIJKPlayerMultiActivity.this.count_playing_channels > 0) {
                                NSTIJKPlayerMultiActivity.this.count_playing_channels--;
                            }
                            if (i == 1) {
                                NSTIJKPlayerMultiActivity.this.is_playing_Box_1 = false;
                            } else if (i == 2) {
                                NSTIJKPlayerMultiActivity.this.is_playing_Box_2 = false;
                            } else if (i == 3) {
                                NSTIJKPlayerMultiActivity.this.is_playing_Box_3 = false;
                            } else if (i == 4) {
                                NSTIJKPlayerMultiActivity.this.is_playing_Box_4 = false;
                            }
                        }
                    }
                    return false;
                }
            });
            popupMenu.show();
        }
    }

    public void hideVideoView(int i) {
        switch (i) {
            case 1:
                this.mVideoView1.setVisibility(8);
                this.app_video_status.setVisibility(8);
                return;
            case 2:
                this.mVideoView2.setVisibility(8);
                this.app_video_status_2.setVisibility(8);
                return;
            case 3:
                this.mVideoView3.setVisibility(8);
                this.app_video_status_3.setVisibility(8);
                return;
            case 4:
                this.mVideoView4.setVisibility(8);
                this.app_video_status_4.setVisibility(8);
                return;
            default:
                return;
        }
    }

    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.app_video_box_1:
                try {
                    if (!this.mFilePath1.equals("")) {
                        muteUnmute(1);
                        requestfocus(1, true, false);
                        popmenu(1, view);
                    }
                    return true;
                } catch (Exception unused) {
                    return true;
                }
            case R.id.app_video_box_2:
                try {
                    if (!this.mFilePath2.equals("")) {
                        muteUnmute(2);
                        requestfocus(2, true, false);
                        popmenu(2, view);
                    }
                    return true;
                } catch (Exception unused2) {
                    return true;
                }
            case R.id.app_video_box_3:
                try {
                    if (!this.mFilePath3.equals("")) {
                        muteUnmute(3);
                        requestfocus(3, true, false);
                        popmenu(3, view);
                    }
                    return true;
                } catch (Exception unused3) {
                    return true;
                }
            case R.id.app_video_box_4:
                try {
                    if (!this.mFilePath4.equals("")) {
                        muteUnmute(4);
                        requestfocus(4, true, false);
                        popmenu(4, view);
                    }
                    return true;
                } catch (Exception unused4) {
                    return true;
                }
            default:
                return true;
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.app_video_box_1:
                try {
                    if (this.maxConnections == 0) {
                        fullScreenVideoLayout(1);
                        return;
                    } else if (this.is_playing_Box_1.booleanValue()) {
                        fullScreenVideoLayout(1);
                        return;
                    } else if (this.maxConnections <= 0) {
                        return;
                    } else {
                        if (this.maxConnections - this.count_playing_channels > 0) {
                            fullScreenVideoLayout(1);
                            return;
                        }
                        Context context2 = this.context;
                        Utils.showcustomToastLong(context2, "You have only " + this.maxConnections + " max Connections");
                        return;
                    }
                } catch (Exception unused) {
                    Context context3 = this.context;
                    Utils.showcustomToastLong(context3, "You have only " + this.maxConnections + " max Connections");
                    return;
                }
            case R.id.app_video_box_2:
                try {
                    if (this.maxConnections == 0) {
                        fullScreenVideoLayout(2);
                        return;
                    } else if (this.is_playing_Box_2.booleanValue()) {
                        fullScreenVideoLayout(2);
                        return;
                    } else if (this.maxConnections <= 0) {
                        return;
                    } else {
                        if (this.maxConnections - this.count_playing_channels > 0) {
                            fullScreenVideoLayout(2);
                            return;
                        }
                        Context context4 = this.context;
                        Utils.showcustomToastLong(context4, "You have only " + this.maxConnections + " max Connections");
                        return;
                    }
                } catch (Exception unused2) {
                    Context context5 = this.context;
                    Utils.showcustomToastLong(context5, "You have only " + this.maxConnections + " max Connections");
                    return;
                }
            case R.id.app_video_box_3:
                try {
                    if (this.maxConnections == 0) {
                        fullScreenVideoLayout(3);
                        return;
                    } else if (this.is_playing_Box_3.booleanValue()) {
                        fullScreenVideoLayout(3);
                        return;
                    } else if (this.maxConnections <= 0) {
                        return;
                    } else {
                        if (this.maxConnections - this.count_playing_channels > 0) {
                            fullScreenVideoLayout(3);
                            return;
                        }
                        Context context6 = this.context;
                        Utils.showcustomToastLong(context6, "You have only " + this.maxConnections + " max Connections");
                        return;
                    }
                } catch (Exception unused3) {
                    Context context7 = this.context;
                    Utils.showcustomToastLong(context7, "You have only " + this.maxConnections + " max Connections");
                    return;
                }
            case R.id.app_video_box_4:
                try {
                    if (this.maxConnections == 0) {
                        fullScreenVideoLayout(4);
                        return;
                    } else if (this.is_playing_Box_4.booleanValue()) {
                        fullScreenVideoLayout(4);
                        return;
                    } else if (this.maxConnections <= 0) {
                        return;
                    } else {
                        if (this.maxConnections - this.count_playing_channels > 0) {
                            fullScreenVideoLayout(4);
                            return;
                        }
                        Context context8 = this.context;
                        Utils.showcustomToastLong(context8, "You have only " + this.maxConnections + " max Connections");
                        return;
                    }
                } catch (Exception unused4) {
                    Context context9 = this.context;
                    Utils.showcustomToastLong(context9, "You have only " + this.maxConnections + " max Connections");
                    return;
                }
            default:
                return;
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    public void fullScreenVideoLayout(int i) {
        this.currentlyActiveBox = i;
        removeBackgroundFocus();
        if (this.currentlyActiveBox == 1) {
            muteUnmute(1);
            if (!findViewById(R.id.app_video_box_1).isFocusable()) {
                findViewById(R.id.app_video_box_1).setFocusable(true);
                if (this.mVideoView1.getVisibility() != 0) {
                    categoriesPopup(this.context);
                }
            } else if (this.mVideoView1.getVisibility() == 0) {
                Utils.playWithMultiPlayer(this.context, this.streamID_1, "live", this.channelNum1, this.videoTitle1, this.currentEpgChannelID_1, this.currentChannelLogo_1, this.categoryID_1, this.videoURL_1);
            } else {
                categoriesPopup(this.context);
            }
            requestfocus(1, false, false);
        } else if (this.currentlyActiveBox == 2) {
            muteUnmute(2);
            if (!findViewById(R.id.app_video_box_2).isFocusable()) {
                findViewById(R.id.app_video_box_2).setFocusable(true);
                if (this.mVideoView2.getVisibility() != 0) {
                    categoriesPopup(this.context);
                }
            } else if (this.mVideoView2.getVisibility() == 0) {
                Utils.playWithMultiPlayer(this.context, this.streamID_2, "live", this.channelNum2, this.videoTitle2, this.currentEpgChannelID_2, this.currentChannelLogo_2, this.categoryID_2, this.videoURL_2);
            } else {
                categoriesPopup(this.context);
            }
            requestfocus(2, false, false);
        } else if (this.currentlyActiveBox == 3) {
            muteUnmute(3);
            if (!findViewById(R.id.app_video_box_3).isFocusable()) {
                findViewById(R.id.app_video_box_3).setFocusable(true);
                if (this.mVideoView3.getVisibility() != 0) {
                    categoriesPopup(this.context);
                }
            } else if (this.mVideoView3.getVisibility() == 0) {
                Utils.playWithMultiPlayer(this.context, this.streamID_3, "live", this.channelNum3, this.videoTitle3, this.currentEpgChannelID_3, this.currentChannelLogo_3, this.categoryID_3, this.videoURL_3);
            } else {
                categoriesPopup(this.context);
            }
            requestfocus(3, false, false);
        } else if (this.currentlyActiveBox == 4) {
            muteUnmute(4);
            if (!findViewById(R.id.app_video_box_4).isFocusable()) {
                findViewById(R.id.app_video_box_4).setFocusable(true);
                if (this.mVideoView4.getVisibility() != 0) {
                    categoriesPopup(this.context);
                }
            } else if (this.mVideoView4.getVisibility() == 0) {
                Utils.playWithMultiPlayer(this.context, this.streamID_4, "live", this.channelNum4, this.videoTitle4, this.currentEpgChannelID_4, this.currentChannelLogo_4, this.categoryID_4, this.videoURL_4);
            } else {
                categoriesPopup(this.context);
            }
            requestfocus(4, false, false);
        }
    }

    @SuppressLint({"ResourceType"})
    public void categoriesPopup(Context context2) {
        View inflate = ((LayoutInflater) context2.getSystemService("layout_inflater")).inflate((int) R.layout.categories_popup, (LinearLayout) findViewById(R.id.ll_root));
        this.pbLoader = (ProgressBar) inflate.findViewById(R.id.pb_loader);
        this.myRecyclerView = (RecyclerView) inflate.findViewById(R.id.my_recycler_view);
        this.rl_no_arrangement_found = (RelativeLayout) inflate.findViewById(R.id.rl_no_arrangement_found);
        this.appbarToolbar = (AppBarLayout) inflate.findViewById(R.id.appbar_toolbar);
        this.tv_settings = (TextView) inflate.findViewById(R.id.tv_settings);
        this.tv_settings.setText(context2.getResources().getString(R.string.categories));
        this.categoriesPopUpWindow = new PopupWindow(context2);
        this.categoriesPopUpWindow.setContentView(inflate);
        this.categoriesPopUpWindow.setWidth(-1);
        this.categoriesPopUpWindow.setHeight(-1);
        this.categoriesPopUpWindow.setFocusable(true);
        this.categoriesPopUpWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            /* class com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerMultiActivity.AnonymousClass10 */

            public void onDismiss() {
            }
        });
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(0);
        }
        if (this.appbarToolbar != null) {
            this.appbarToolbar.setBackground(getResources().getDrawable(R.color.tl));
        }
        this.AsyncTaskLiveActivityNewFlow = new MultiPlayerCategoriesAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
        this.categoriesPopUpWindow.showAtLocation(inflate, 1, 0, 0);
    }

    public boolean intiliaze() {
        try {
            if (this.context != null) {
                if (!(this.liveListDetailAvailable == null || this.liveListDetailUnlcked == null)) {
                    this.liveListDetailAvailable.clear();
                    this.liveListDetailUnlcked.clear();
                }
                this.liveListDetail = this.liveStreamDBHandler.getAllliveCategories();
                LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
                LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel2 = new LiveStreamCategoryIdDBModel();
                new LiveStreamCategoryIdDBModel();
                liveStreamCategoryIdDBModel.setLiveStreamCategoryID(AppConst.PASSWORD_UNSET);
                liveStreamCategoryIdDBModel.setLiveStreamCategoryName(getResources().getString(R.string.all));
                liveStreamCategoryIdDBModel2.setLiveStreamCategoryID("-1");
                liveStreamCategoryIdDBModel2.setLiveStreamCategoryName(getResources().getString(R.string.favourites));
                if (this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context)) <= 0 || this.liveListDetail == null) {
                    this.liveListDetail.add(0, liveStreamCategoryIdDBModel);
                    this.liveListDetail.add(1, liveStreamCategoryIdDBModel2);
                    this.liveListDetailAvailable = this.liveListDetail;
                } else {
                    this.listPassword = getPasswordSetCategories();
                    this.liveListDetailUnlckedDetail = getUnlockedCategories(this.liveListDetail, this.listPassword);
                    if (this.liveListDetailUnlcked != null) {
                        this.liveListDetailUnlcked.add(0, liveStreamCategoryIdDBModel);
                        this.liveListDetailUnlcked.add(1, liveStreamCategoryIdDBModel2);
                    }
                    if (this.liveListDetailUnlckedDetail != null) {
                        this.liveListDetailAvailable = this.liveListDetailUnlckedDetail;
                    }
                }
                if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                    this.liveListDetailAvailableForCounter.clear();
                    if (this.liveListDetailAvailable != null && this.liveListDetailAvailable.size() > 0) {
                        int i = 0;
                        int i2 = 0;
                        while (true) {
                            if (i < this.liveListDetailAvailable.size()) {
                                if (this.AsyncTaskLiveActivityNewFlow != null && this.AsyncTaskLiveActivityNewFlow.isCancelled()) {
                                    break;
                                }
                                if (this.liveListDetailAvailable.get(i).getLiveStreamCategoryID().equals(AppConst.PASSWORD_UNSET) || this.liveListDetailAvailable.get(i).getLiveStreamCategoryID().equals("-1")) {
                                    int availableCountM3U = this.liveStreamDBHandler.getAvailableCountM3U(this.liveListDetailAvailable.get(i).getLiveStreamCategoryID(), "live");
                                    if (this.liveListDetailAvailable.get(i).getLiveStreamCategoryID().equals(AppConst.PASSWORD_UNSET)) {
                                        this.allCount = availableCountM3U;
                                    }
                                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel3 = new LiveStreamCategoryIdDBModel();
                                    liveStreamCategoryIdDBModel3.setLiveStreamCounter(availableCountM3U);
                                    liveStreamCategoryIdDBModel3.setLiveStreamCategoryName(this.liveListDetailAvailable.get(i).getLiveStreamCategoryName());
                                    liveStreamCategoryIdDBModel3.setLiveStreamCategoryID(this.liveListDetailAvailable.get(i).getLiveStreamCategoryID());
                                    this.liveListDetailAvailableForCounter.add(i2, liveStreamCategoryIdDBModel3);
                                    i2++;
                                } else {
                                    int availableCountM3U2 = this.liveStreamDBHandler.getAvailableCountM3U(this.liveListDetailAvailable.get(i).getLiveStreamCategoryID(), "live");
                                    if (availableCountM3U2 != 0) {
                                        LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel4 = new LiveStreamCategoryIdDBModel();
                                        liveStreamCategoryIdDBModel4.setLiveStreamCounter(availableCountM3U2);
                                        liveStreamCategoryIdDBModel4.setLiveStreamCategoryName(this.liveListDetailAvailable.get(i).getLiveStreamCategoryName());
                                        liveStreamCategoryIdDBModel4.setLiveStreamCategoryID(this.liveListDetailAvailable.get(i).getLiveStreamCategoryID());
                                        this.liveListDetailAvailableForCounter.add(i2, liveStreamCategoryIdDBModel4);
                                        i2++;
                                    }
                                }
                                i++;
                            } else {
                                break;
                            }
                        }
                    }
                    if (this.liveListDetailAvailableForCounter != null && this.liveListDetailAvailableForCounter.size() > 0) {
                        this.liveListDetailAvailable = this.liveListDetailAvailableForCounter;
                    }
                } else {
                    if (this.liveListDetailAvailable != null && this.liveListDetailAvailable.size() > 0) {
                        int i3 = 0;
                        int i4 = 0;
                        while (true) {
                            if (i3 < this.liveListDetailAvailable.size()) {
                                if (this.AsyncTaskLiveActivityNewFlow != null && this.AsyncTaskLiveActivityNewFlow.isCancelled()) {
                                    break;
                                }
                                if (this.liveListDetailAvailable.get(i3).getLiveStreamCategoryID().equals(AppConst.PASSWORD_UNSET) || this.liveListDetailAvailable.get(i3).getLiveStreamCategoryID().equals("-1")) {
                                    int subCatMovieCount = this.liveStreamDBHandler.getSubCatMovieCount(this.liveListDetailAvailable.get(i3).getLiveStreamCategoryID(), "live");
                                    if (this.liveListDetailAvailable.get(i3).getLiveStreamCategoryID().equals(AppConst.PASSWORD_UNSET)) {
                                        this.allCount = subCatMovieCount;
                                    }
                                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel5 = new LiveStreamCategoryIdDBModel();
                                    liveStreamCategoryIdDBModel5.setLiveStreamCounter(subCatMovieCount);
                                    liveStreamCategoryIdDBModel5.setLiveStreamCategoryName(this.liveListDetailAvailable.get(i3).getLiveStreamCategoryName());
                                    liveStreamCategoryIdDBModel5.setLiveStreamCategoryID(this.liveListDetailAvailable.get(i3).getLiveStreamCategoryID());
                                    this.liveListDetailAvailableForCounter.add(i4, liveStreamCategoryIdDBModel5);
                                    i4++;
                                } else {
                                    int subCatMovieCount2 = this.liveStreamDBHandler.getSubCatMovieCount(this.liveListDetailAvailable.get(i3).getLiveStreamCategoryID(), "live");
                                    if (subCatMovieCount2 != 0) {
                                        LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel6 = new LiveStreamCategoryIdDBModel();
                                        liveStreamCategoryIdDBModel6.setLiveStreamCounter(subCatMovieCount2);
                                        liveStreamCategoryIdDBModel6.setLiveStreamCategoryName(this.liveListDetailAvailable.get(i3).getLiveStreamCategoryName());
                                        liveStreamCategoryIdDBModel6.setLiveStreamCategoryID(this.liveListDetailAvailable.get(i3).getLiveStreamCategoryID());
                                        this.liveListDetailAvailableForCounter.add(i4, liveStreamCategoryIdDBModel6);
                                        i4++;
                                    }
                                }
                                i3++;
                            } else {
                                break;
                            }
                        }
                    }
                    if (this.liveListDetailAvailableForCounter != null && this.liveListDetailAvailableForCounter.size() > 0) {
                        this.liveListDetailAvailable = this.liveListDetailAvailableForCounter;
                    }
                }
            }
            return true;
        } catch (NullPointerException unused) {
            return false;
        } catch (Exception unused2) {
            return false;
        }
    }

    @Override // com.nst.yourname.view.interfaces.MultiPlayerInterface
    public void selectedChannelDetails(String str, String str2) {
        if (this.currentlyActiveBox == 1) {
            if (this.mVideoView1 != null) {
                this.mFilePath1 = str;
                this.channelNum1 = str2;
                findViewById(R.id.app_video_box_1).setFocusable(true);
                if (!this.multiscreen_edit.booleanValue()) {
                    this.count_playing_channels++;
                    this.is_playing_Box_1 = true;
                }
                playFirstTime();
            }
        } else if (this.currentlyActiveBox == 2) {
            if (this.mVideoView2 != null) {
                this.mFilePath2 = str;
                this.channelNum2 = str2;
                findViewById(R.id.app_video_box_2).setFocusable(true);
                if (!this.multiscreen_edit.booleanValue()) {
                    this.count_playing_channels++;
                    this.is_playing_Box_2 = true;
                }
                playFirstTime2();
            }
        } else if (this.currentlyActiveBox == 3) {
            if (this.mVideoView3 != null) {
                this.mFilePath3 = str;
                this.channelNum3 = str2;
                findViewById(R.id.app_video_box_3).setFocusable(true);
                if (!this.multiscreen_edit.booleanValue()) {
                    this.count_playing_channels++;
                    this.is_playing_Box_3 = true;
                }
                playFirstTime3();
            }
        } else if (this.currentlyActiveBox == 4 && this.mVideoView4 != null) {
            this.mFilePath4 = str;
            this.channelNum4 = str2;
            findViewById(R.id.app_video_box_4).setFocusable(true);
            if (!this.multiscreen_edit.booleanValue()) {
                this.count_playing_channels++;
                this.is_playing_Box_4 = true;
            }
            playFirstTime4();
        }
    }

    @SuppressLint({"StaticFieldLeak"})
    class MultiPlayerCategoriesAsync extends AsyncTask<String, Void, Boolean> {
        MultiPlayerCategoriesAsync() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public Boolean doInBackground(String... strArr) {
            try {
                return Boolean.valueOf(NSTIJKPlayerMultiActivity.this.intiliaze());
            } catch (Exception unused) {
                return false;
            }
        }

        public void onPostExecute(Boolean bool) {
            super.onPostExecute((Boolean) bool);
            if (SharepreferenceDBHandler.getCurrentAPPType(NSTIJKPlayerMultiActivity.this.context).equals(AppConst.TYPE_M3U)) {
                if (NSTIJKPlayerMultiActivity.this.liveListDetailAvailable != null && NSTIJKPlayerMultiActivity.this.allCount != 0) {
                    NSTIJKPlayerMultiActivity.this.adapter = new MultiPlayerCategoriesAdapter(NSTIJKPlayerMultiActivity.this.liveListDetailAvailable, NSTIJKPlayerMultiActivity.this.context, (MultiPlayerInterface) NSTIJKPlayerMultiActivity.this.context, NSTIJKPlayerMultiActivity.this.categoriesPopUpWindow, NSTIJKPlayerMultiActivity.this.currentlyActiveBox);
                    if ((NSTIJKPlayerMultiActivity.this.getResources().getConfiguration().screenLayout & 15) == 3) {
                        GridLayoutManager unused = NSTIJKPlayerMultiActivity.this.gridLayoutManager = new GridLayoutManager(NSTIJKPlayerMultiActivity.this.context, 2);
                    } else {
                        GridLayoutManager unused2 = NSTIJKPlayerMultiActivity.this.gridLayoutManager = new GridLayoutManager(NSTIJKPlayerMultiActivity.this.context, 2);
                    }
                    NSTIJKPlayerMultiActivity.this.myRecyclerView.setLayoutManager(NSTIJKPlayerMultiActivity.this.gridLayoutManager);
                    NSTIJKPlayerMultiActivity.this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    NSTIJKPlayerMultiActivity.this.myRecyclerView.setAdapter(NSTIJKPlayerMultiActivity.this.adapter);
                    if (NSTIJKPlayerMultiActivity.this.pbLoader != null) {
                        NSTIJKPlayerMultiActivity.this.pbLoader.setVisibility(8);
                    }
                } else if (NSTIJKPlayerMultiActivity.this.pbLoader != null) {
                    NSTIJKPlayerMultiActivity.this.pbLoader.setVisibility(8);
                    NSTIJKPlayerMultiActivity.this.rl_no_arrangement_found.setVisibility(0);
                }
            } else if (NSTIJKPlayerMultiActivity.this.liveListDetailAvailable != null) {
                NSTIJKPlayerMultiActivity.this.adapter = new MultiPlayerCategoriesAdapter(NSTIJKPlayerMultiActivity.this.liveListDetailAvailable, NSTIJKPlayerMultiActivity.this.context, (MultiPlayerInterface) NSTIJKPlayerMultiActivity.this.context, NSTIJKPlayerMultiActivity.this.categoriesPopUpWindow, NSTIJKPlayerMultiActivity.this.currentlyActiveBox);
                if ((NSTIJKPlayerMultiActivity.this.getResources().getConfiguration().screenLayout & 15) == 3) {
                    GridLayoutManager unused3 = NSTIJKPlayerMultiActivity.this.gridLayoutManager = new GridLayoutManager(NSTIJKPlayerMultiActivity.this.context, 2);
                } else {
                    GridLayoutManager unused4 = NSTIJKPlayerMultiActivity.this.gridLayoutManager = new GridLayoutManager(NSTIJKPlayerMultiActivity.this.context, 2);
                }
                NSTIJKPlayerMultiActivity.this.myRecyclerView.setLayoutManager(NSTIJKPlayerMultiActivity.this.gridLayoutManager);
                NSTIJKPlayerMultiActivity.this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
                NSTIJKPlayerMultiActivity.this.myRecyclerView.setAdapter(NSTIJKPlayerMultiActivity.this.adapter);
                if (NSTIJKPlayerMultiActivity.this.pbLoader != null) {
                    NSTIJKPlayerMultiActivity.this.pbLoader.setVisibility(8);
                }
            }
        }
    }
}
