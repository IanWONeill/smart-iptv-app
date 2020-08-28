package com.nst.yourname.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.Clientdatabase.ClientSharepreferenceHandler;
import com.nst.yourname.WHMCSClientapp.activities.ServicesDashboardActivity;
import com.nst.yourname.WHMCSClientapp.interfaces.ApiService;
import com.nst.yourname.WHMCSClientapp.interfaces.ApiclientRetrofit;
import com.nst.yourname.WHMCSClientapp.modelclassess.LoginWHMCSModelClass;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.EpisodesUsingSinglton;
import com.nst.yourname.model.ExtendedAppInfo;
import com.nst.yourname.model.MoviesUsingSinglton;
import com.nst.yourname.model.callback.LoginCallback;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.MultiUserDBHandler;
import com.nst.yourname.model.database.SeriesStreamsDatabaseHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.model.pojo.XMLTVProgrammePojo;
import com.nst.yourname.presenter.LoginPresenter;
import com.nst.yourname.presenter.SearchMoviesPresenter;
import com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerMultiActivity;
import com.nst.yourname.view.interfaces.LoginInterface;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class NewDashboardActivity extends AppCompatActivity implements View.OnClickListener, LoginInterface {
    static final boolean $assertionsDisabled = false;
    private static final int TIME_INTERVAL = 2000;
    public static PopupWindow logoutPopUp;
    public String EPGLine;
    public String EPGLineType;
    public String myReseller;
    public String themeSelected;
    public String isOnlyVod;
    @BindView(R.id.account_info)
    ImageView account_info;
    AlertDialog alertDialog;
    @BindView(R.id.catch_up)
    LinearLayout catch_up;
    public PopupWindow changeSortPopUp;
    Button closedBT;
    public Context context = this;
    String currentDate = "";
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.epg)
    LinearLayout epg;
    @BindView(R.id.tv_epg)
    TextView epgTV;
    private DatabaseHandler favDBHandler;
    InputStream is;
    @BindView(R.id.switch_user)
    ImageView ivSwitchUser;
    @BindView(R.id.iv_arrow)
    ImageView iv_arrow;
    @BindView(R.id.iv_notification)
    ImageView iv_notification;
    @BindView(R.id.ll_loggedin_user_multiuser)
    LinearLayout linearLayoutLoggedinUser;
    private LiveStreamDBHandler liveStreamDBHandler;
    @BindView(R.id.live_tv)
    LinearLayout live_tv;
    LinearLayout llMultiscreen;
    LinearLayout llRecording;
    @BindView(R.id.ll_billing)
    LinearLayout ll_billing;
    @BindView(R.id.ll_exp)
    LinearLayout ll_exp;
    @BindView(R.id.ll_purchase_add_free_version)
    LinearLayout ll_purchase_add_free_version;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences loginPreferencesAfterLoginChannels;
    private SharedPreferences loginPreferencesAfterLoginEPG;
    private SharedPreferences loginPreferencesSharedPref;
    private SharedPreferences.Editor loginPrefsEditorChannels;
    private SharedPreferences.Editor loginPrefsEditorEPG;
    private LoginPresenter loginPresenter;
    private long mBackPressed;
    @BindView(R.id.main_layout)
    LinearLayout main_layout;
    public MultiUserDBHandler multiuserdbhandler;
    @BindView(R.id.on_demand)
    LinearLayout on_demand;
    ImageView recordingsIV;
    Button savePasswordBT;
    private SearchMoviesPresenter searchMoviesPresenter;
    private String selected_language = "";
    private SeriesStreamsDatabaseHandler seriesStreamsDatabaseHandler;
    private String serverurl;
    @BindView(R.id.settings_new)
    ImageView settingsIV;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.tv_account_info_button)
    TextView tvAccountinfoButton;
    @BindView(R.id.expiration_date)
    TextView tvExpiryDate;
    @BindView(R.id.loggedin_user)
    TextView tvLoggedinUser;
    TextView tvRecordingsButton;
    @BindView(R.id.tv_settings_button)
    TextView tvSettingsButton;
    @BindView(R.id.tv_switch_user_button)
    TextView tvSwitchUserButton;
    @BindView(R.id.tv_billing_subscription)
    TextView tv_billing_subscription;
    @BindView(R.id.tv_notification)
    TextView tv_notification;
    private String userName = "";
    private String userPassword = "";
    ArrayList<XMLTVProgrammePojo> xmltvProgrammePojos;
    ArrayList<ExtendedAppInfo> extendedAppInfos = new ArrayList<> ();

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void atStart() {
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void magFailedtoLogin(String str, String str2, String str3, Context context2) {
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void magFailedtoLoginMultiDNS(ArrayList<String> arrayList, ArrayList<String> arrayList2, String str, String str2, String str3) {
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void magFailedtoLoginMultiDNS2(ArrayList<String> arrayList, ArrayList<String> arrayList2, String str, String str2, String str3) {
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFailed(String str) {
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFinish() {
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void reValidateLogin(LoginCallback loginCallback, String str, int i, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void stopLoader(String str) {
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void stopLoaderMultiDNS(ArrayList<String> arrayList, String str) {
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void validateloginMultiDNS(LoginCallback loginCallback, String str, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
    }

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    @SuppressLint({"SetTextI18n"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        int i = 1;
        requestWindowFeature(1);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        if (AppConst.GET_FREETRIAL.booleanValue()) {
            if (AppConst.New_MultiScreen.booleanValue()) {
                if (!Utils.getIsXtream1_06(this.context)) {
                    setContentView((int) R.layout.new_dashboard_layout_multiscreen);
                } else if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                    setContentView((int) R.layout.new_dashboard_layout_multiscreen);
                } else {
                    setContentView((int) R.layout.new_dashboard_layout_without_freetrail_xc106);
                }
                this.llMultiscreen = (LinearLayout) findViewById(R.id.multiscreen);
                this.tvRecordingsButton = (TextView) findViewById(R.id.tv_recording_button);
                this.recordingsIV = (ImageView) findViewById(R.id.iv_recording);
            } else if (!Utils.getIsXtream1_06(this.context)) {
                setContentView((int) R.layout.new_dashboard_layout);
            } else if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                setContentView((int) R.layout.new_dashboard_layout);
            } else {
                setContentView((int) R.layout.new_dashboard_layout_without_freetrail_xc106);
            }
        } else if (AppConst.New_MultiScreen.booleanValue()) {
            if (!Utils.getIsXtream1_06(this.context)) {
                setContentView((int) R.layout.new_dashboard_layout_without_freetrail);
            } else if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                setContentView((int) R.layout.new_dashboard_layout_without_freetrail);
            } else {
                setContentView((int) R.layout.new_dashboard_layout_without_freetrail_xc106);
            }
            this.llMultiscreen = (LinearLayout) findViewById(R.id.multiscreen);
            this.tvRecordingsButton = (TextView) findViewById(R.id.tv_recording_button);
            this.recordingsIV = (ImageView) findViewById(R.id.iv_recording);
        } else {
            if (!Utils.getIsXtream1_06(this.context)) {
                setContentView((int) R.layout.new_dashboard_layout_without_freetrail2);
            } else if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                setContentView((int) R.layout.new_dashboard_layout_without_freetrail2);
            } else {
                setContentView((int) R.layout.new_dashboard_layout_without_freetrail_without_multiscreen_xc106);
            }
            this.llRecording = (LinearLayout) findViewById(R.id.recording);
        }
        ButterKnife.bind(this);
        this.context = this;
        if ((getResources().getConfiguration().screenLayout & 15) == 3 && this.ll_purchase_add_free_version != null) {
            this.ll_purchase_add_free_version.setVisibility(4);
        }
        changeStatusBarColor();
        hideSystemUi();
        MoviesUsingSinglton.getInstance().setMoviesList(null);
        EpisodesUsingSinglton.getInstance().setEpisodeList(null);
        new Thread(new CountDownRunner()).start();
        makeButtonsClickable();
        if ((getResources().getConfiguration().screenLayout & 15) != 3) {
            this.selected_language = this.context.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, 0).getString(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, "English");
            if (this.selected_language.equalsIgnoreCase("Arabic")) {
                this.time.setGravity(19);
                this.date.setGravity(21);
            }
        }
        if (SharepreferenceDBHandler.getSelectedLanguage(this.context).equalsIgnoreCase("Arabic")) {
            this.iv_arrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_billing_left_arrow));
        } else {
            this.iv_arrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow));
        }
        if (!SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            Utils.getIsXtream1_06(this.context);
        }
        this.multiuserdbhandler = new MultiUserDBHandler(this.context);
        if (!AppConst.MULTIUSER_ACTIVE.booleanValue()) {
            this.tvSwitchUserButton.setText(getResources().getString(R.string.menu_logout));
        } else if (!SharepreferenceDBHandler.getRateUsDontaskagain(this.context)) {
            SharepreferenceDBHandler.setRateUsCount(SharepreferenceDBHandler.getRateUsCount(this.context) + 1, this.context);
            SharepreferenceDBHandler.getRateUsCount(this.context);
        }
        if (AppConst.New_MultiScreen.booleanValue()) {
            this.live_tv.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.live_tv));
            this.account_info.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.account_info));
            this.epg.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.epg));
            this.on_demand.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.on_demand));
            this.catch_up.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.catch_up));
            this.settingsIV.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.settingsIV));
            this.recordingsIV.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.recordingsIV));
            this.ivSwitchUser.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.ivSwitchUser));
            this.llMultiscreen.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.llMultiscreen));
            this.ll_billing.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.ll_billing));
        } else {
            this.live_tv.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.live_tv));
            this.account_info.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.account_info));
            this.epg.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.epg));
            this.on_demand.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.on_demand));
            this.catch_up.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.catch_up));
            this.settingsIV.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.settingsIV));
            this.ivSwitchUser.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.ivSwitchUser));
            this.llRecording.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.llRecording));
            this.ll_billing.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.ll_billing));
        }
        String userEPG = this.multiuserdbhandler.getUserEPG(SharepreferenceDBHandler.getUserID(this.context));

        if (userEPG != null && !userEPG.equals("") && SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            this.epg.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.activity.NewDashboardActivity.AnonymousClass1 */

                public boolean onLongClick(View view) {
                    NewDashboardActivity.this.editEPGPopup(NewDashboardActivity.this.epg);
                    return false;
                }
            });
        }
        this.iv_notification.setVisibility(0);
        this.iv_notification.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.iv_notification));
        this.live_tv.requestFocus();
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.seriesStreamsDatabaseHandler = new SeriesStreamsDatabaseHandler(this.context);
        this.favDBHandler = new DatabaseHandler(this.context);
        loadEPG(this.liveStreamDBHandler);
        String string = this.loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_EXP_DATE, "");
        if (AppConst.MULTIUSER_ACTIVE.booleanValue()) {
            if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                this.linearLayoutLoggedinUser.setVisibility(8);
            } else {
                this.linearLayoutLoggedinUser.setVisibility(0);
            }
            this.ivSwitchUser.setVisibility(0);
            SharedPreferences sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0);
            String string2 = sharedPreferences.getString("name", "");
            this.serverurl = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "");
            if (string2.length() > 15) {
                String substring = string2.substring(0, 14);
                TextView textView = this.tvLoggedinUser;
                textView.setText(substring + "..");
            } else {
                this.tvLoggedinUser.setText(string2);
            }
        } else {
            this.ivSwitchUser.setImageResource(R.drawable.logout_user);
            this.tvExpiryDate.setGravity(17);
            this.linearLayoutLoggedinUser.setVisibility(8);
        }
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            this.tvExpiryDate.setVisibility(8);
        } else {
            this.tvExpiryDate.setVisibility(0);
            this.tvExpiryDate.setGravity(17);
            this.ll_purchase_add_free_version.setVisibility(8);
        }
        if (this.tvExpiryDate != null) {
            if (!string.isEmpty()) {
                try {
                    i = Integer.parseInt(string);
                } catch (NumberFormatException unused) {
                }
                String format = new SimpleDateFormat("MMMM d, yyyy").format(new Date(((long) i) * 1000));
                TextView textView2 = this.tvExpiryDate;
                textView2.setText(getResources().getString(R.string.expiration) + " " + format);
            } else {
                TextView textView3 = this.tvExpiryDate;
                textView3.setText(getResources().getString(R.string.expiration) + " " + getResources().getString(R.string.unlimited));
            }
        }
        getExpirationDate();
    }

    private void getExpirationDate() {
        if (this.context != null) {
            this.loginPresenter = new LoginPresenter(this, this.context);
            SharedPreferences sharedPreferences = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            sharedPreferences.edit();
            String string = sharedPreferences.getString("username", "");
            String string2 = sharedPreferences.getString("password", "");
            try {
                if (this.loginPresenter != null && string != null && !string.isEmpty() && !string.equals("") && string2 != null && !string2.isEmpty() && !string2.equals("")) {
                    this.loginPresenter.validateLogin(string, string2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadEPG(LiveStreamDBHandler liveStreamDBHandler2) {
        int ePGCount = liveStreamDBHandler2 != null ? liveStreamDBHandler2.getEPGCount() : -1;
        if (ePGCount == -1 || ePGCount <= 0) {
            this.epgTV.setText(getResources().getString(R.string.Install_EPG));
        } else {
            this.epgTV.setText(getResources().getString(R.string.epg_live));
        }
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            String userEPG = this.multiuserdbhandler.getUserEPG(SharepreferenceDBHandler.getUserID(this.context));
            if (userEPG == null || userEPG.equals("")) {
                this.epgTV.setText(getResources().getString(R.string.Install_EPG));
            } else {
                this.epgTV.setText(getResources().getString(R.string.epg_live));
            }
        }
    }

    private void startImportForLiveUpdate() {
        if (checkChannelsAutomation()) {
            launchImportChannels("live");
        } else {
            startActivity(new Intent(this.context, LiveActivityNewFlow.class));
        }
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    NewDashboardActivity.this.doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                } catch (Exception unused2) {
                }
            }
        }
    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            /* class com.nst.yourname.view.activity.NewDashboardActivity.AnonymousClass2 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(NewDashboardActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (NewDashboardActivity.this.time != null) {
                        NewDashboardActivity.this.time.setText(time);
                    }
                    if (NewDashboardActivity.this.date != null) {
                        NewDashboardActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        Utils.appResume(this.context);
        loadEPG(this.liveStreamDBHandler);
        hideSystemUi();
        MoviesUsingSinglton.getInstance().setMoviesList(null);
        EpisodesUsingSinglton.getInstance().setEpisodeList(null);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        if ((getResources().getConfiguration().screenLayout & 15) == 3 && this.ll_purchase_add_free_version != null) {
            this.ll_purchase_add_free_version.setVisibility(8);
        }
        new Thread(new CountDownRunner()).start();
        SharepreferenceDBHandler.getSelectedLanguage(this.context).equalsIgnoreCase("Arabic");
        super.onResume();
    }

    private void makeButtonsClickable() {
        if (AppConst.New_MultiScreen.booleanValue()) {
            this.live_tv.setOnClickListener(this);
            this.on_demand.setOnClickListener(this);
            this.catch_up.setOnClickListener(this);
            this.epg.setOnClickListener(this);
            this.account_info.setOnClickListener(this);
            this.settingsIV.setOnClickListener(this);
            this.recordingsIV.setOnClickListener(this);
            this.ivSwitchUser.setOnClickListener(this);
            this.llMultiscreen.setOnClickListener(this);
            this.iv_notification.setOnClickListener(this);
            this.ll_billing.setOnClickListener(this);
            this.ll_purchase_add_free_version.setOnClickListener(this);
            return;
        }
        this.live_tv.setOnClickListener(this);
        this.on_demand.setOnClickListener(this);
        this.catch_up.setOnClickListener(this);
        this.epg.setOnClickListener(this);
        this.account_info.setOnClickListener(this);
        this.settingsIV.setOnClickListener(this);
        this.ivSwitchUser.setOnClickListener(this);
        this.llRecording.setOnClickListener(this);
        this.iv_notification.setOnClickListener(this);
        this.ll_billing.setOnClickListener(this);
        this.ll_purchase_add_free_version.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.account_info:
                startActivity(new Intent(this, AccountInfoActivity.class));
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            case R.id.catch_up:
                if (Utils.getIsXtream1_06(this.context)) {
                    if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                        if (checkChannelsAutomation()) {
                            launchImportChannels("series");
                        } else {
                            startActivity(new Intent(this, SeriesActivityNewFlowM3U.class));
                        }
                        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                        return;
                    }
                    if (checkChannelsAutomation()) {
                        launchImportChannels("series");
                    } else {
                        startActivity(new Intent(this, SeriesActivtyNewFlow.class));
                    }
                    overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                    return;
                } else if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                    if (checkChannelsAutomation()) {
                        launchImportChannels("series");
                    } else {
                        startActivity(new Intent(this, SeriesActivityNewFlowM3U.class));
                    }
                    overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                    return;
                } else {
                    if (checkChannelsAutomation()) {
                        launchImportChannels("series");
                    } else {
                        startActivity(new Intent(this, SeriesActivtyNewFlow.class));
                    }
                    overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                    return;
                }
            case R.id.epg:
                if (checkEPGAutomation()) {
                    launchTvGuide("epg");
                } else {
                    startActivity(new Intent(this, NewEPGCategoriesActivity.class));
                }
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            case R.id.iv_notification:
                startActivity(new Intent(this, AnnouncementsActivity.class));
                return;
            case R.id.iv_recording:
            case R.id.recording:
                startActivity(new Intent(this, RecordingActivity.class));
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            case R.id.live_tv:
                if (checkChannelsAutomation()) {
                    if (checkEPGAutomation()) {
                        int ePGCount = this.liveStreamDBHandler.getEPGCount();
                        long channnelLastUpdated = channnelLastUpdated();
                        long epgLastUpdated = epgLastUpdated();
                        if (ePGCount > 0 && channnelLastUpdated > 0 && epgLastUpdated > 0) {
                            startImportTvGuideActivityFromLive("epgexpired");
                        } else if (ePGCount > 0 && channnelLastUpdated == 0 && epgLastUpdated > 0) {
                            launchTvGuide("liveautoupdatedisable");
                        } else if (ePGCount > 0 && channnelLastUpdated > 0 && epgLastUpdated == 0) {
                            launchImportChannels("live");
                        } else if ((ePGCount == 0 || ePGCount > 0) && channnelLastUpdated >= 0) {
                            launchImportChannels("live");
                        } else {
                            startActivity(new Intent(this.context, LiveActivityNewFlow.class));
                        }
                    } else if (!checkEPGAutomation()) {
                        launchImportChannels("live");
                    } else {
                        startActivity(new Intent(this.context, LiveActivityNewFlow.class));
                    }
                } else if (checkChannelsAutomation()) {
                    startActivity(new Intent(this.context, LiveActivityNewFlow.class));
                } else if (checkEPGAutomation()) {
                    int ePGCount2 = this.liveStreamDBHandler.getEPGCount();
                    long channnelLastUpdated2 = channnelLastUpdated();
                    long epgLastUpdated2 = epgLastUpdated();
                    if (ePGCount2 > 0 && channnelLastUpdated2 > 0) {
                        launchTvGuide("liveautoupdatedisable");
                    } else if (ePGCount2 > 0 && channnelLastUpdated2 == 0 && epgLastUpdated2 > 0) {
                        launchTvGuide("liveautoupdatedisable");
                    } else if ((ePGCount2 == 0 || ePGCount2 > 0) && channnelLastUpdated2 >= 0) {
                        startActivity(new Intent(this.context, LiveActivityNewFlow.class));
                    } else {
                        startActivity(new Intent(this.context, LiveActivityNewFlow.class));
                    }
                } else if (!checkEPGAutomation()) {
                    startActivity(new Intent(this.context, LiveActivityNewFlow.class));
                } else {
                    startActivity(new Intent(this.context, LiveActivityNewFlow.class));
                }
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            case R.id.ll_billing:
                HitAPIForCheckServices();
                return;
            case R.id.ll_purchase_add_free_version:
                startActivity(new Intent(this, APPInPurchaseActivity.class));
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            case R.id.multiscreen:
                Intent intent = new Intent(this.context, NSTIJKPlayerMultiActivity.class);
                intent.putExtra("url", "");
                intent.putExtra("CHANNEL_NUM", 0);
                this.context.startActivity(intent);
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            case R.id.on_demand:
                if (checkChannelsAutomation()) {
                    launchImportChannels(AppConst.VOD);
                } else {
                    startActivity(new Intent(this, VodActivityNewFlow.class));
                }
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            case R.id.settings_new:
                AppConst.FROM_DASHBOARD_TO_SETTINGS = true;
                startActivity(new Intent(this, SettingsActivity.class));
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            case R.id.switch_user:
                if (AppConst.MULTIUSER_ACTIVE.booleanValue() && SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_API)) {
                    AppConst.SWITCH_FROM_DASHBOARD_TO_MULTIUSER = true;
                    Utils.logoutUser(this.context);
                    finish();
                    overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                    return;
                } else if (!AppConst.M3U_LINE_ACTIVE.booleanValue() || !SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                    showLogoutPopup();
                    return;
                } else {
                    AppConst.SWITCH_FROM_DASHBOARD_TO_MULTIUSER = true;
                    if (!AppConst.MULTIUSER_ACTIVE.booleanValue()) {
                        showLogoutPopup();
                        return;
                    }
                    Utils.logoutUser(this.context);
                    finish();
                    overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                    return;
                }
            default:
                return;
        }
    }

    public void addEPGPopUp(NewDashboardActivity newDashboardActivity) {
        if (newDashboardActivity != null) {
            View inflate = ((LayoutInflater) newDashboardActivity.getSystemService("layout_inflater")).inflate((int) R.layout.epg_m3u_popup, (RelativeLayout) newDashboardActivity.findViewById(R.id.rl_password_verification));
            this.changeSortPopUp = new PopupWindow(newDashboardActivity);
            this.changeSortPopUp.setContentView(inflate);
            this.changeSortPopUp.setWidth(-1);
            this.changeSortPopUp.setHeight(-1);
            this.changeSortPopUp.setFocusable(true);
            Button button = (Button) inflate.findViewById(R.id.bt_add_epg);
            Button button2 = (Button) inflate.findViewById(R.id.bt_close);
            final EditText editText = (EditText) inflate.findViewById(R.id.et_epg_link);
            if (button != null) {
                button.setOnFocusChangeListener(new OnFocusChangeAccountListenerPopUp(button, newDashboardActivity));
            }
            if (button2 != null) {
                button2.setOnFocusChangeListener(new OnFocusChangeAccountListenerPopUp(button2, newDashboardActivity));
            }
            editText.requestFocus();
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.NewDashboardActivity.AnonymousClass3 */

                public void onClick(View view) {
                    NewDashboardActivity.this.changeSortPopUp.dismiss();
                }
            });
            if (button != null) {
                button.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.activity.NewDashboardActivity.AnonymousClass4 */

                    public void onClick(View view) {
                        if (fieldsCheck()) {
                            NewDashboardActivity.this.EPGLine = editText.getText().toString();
                            String unused2 = NewDashboardActivity.this.EPGLineType = "add";
                            new _checkNetworkAvailable().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, NewDashboardActivity.this.EPGLine);
                        }
                    }

                    private boolean fieldsCheck() {
                        if (editText.getText().toString().trim().length() != 0) {
                            return true;
                        }
                        Toast.makeText(NewDashboardActivity.this.context, NewDashboardActivity.this.getResources().getString(R.string.enter_epg_link), 1).show();
                        return false;
                    }
                });
            }
            this.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
        }
    }

    public void editEPGPopUp(NewDashboardActivity newDashboardActivity) {
        String userEPG;
        if (newDashboardActivity != null) {
            View inflate = ((LayoutInflater) newDashboardActivity.getSystemService("layout_inflater")).inflate((int) R.layout.epg_edit_m3u_popup, (RelativeLayout) newDashboardActivity.findViewById(R.id.rl_password_verification));
            this.changeSortPopUp = new PopupWindow(newDashboardActivity);
            this.changeSortPopUp.setContentView(inflate);
            this.changeSortPopUp.setWidth(-1);
            this.changeSortPopUp.setHeight(-1);
            this.changeSortPopUp.setFocusable(true);
            Button button = (Button) inflate.findViewById(R.id.bt_add_epg);
            Button button2 = (Button) inflate.findViewById(R.id.bt_close);
            final EditText editText = (EditText) inflate.findViewById(R.id.et_epg_link);
            if (button != null) {
                button.setOnFocusChangeListener(new OnFocusChangeAccountListenerPopUp(button, newDashboardActivity));
            }
            if (button2 != null) {
                button2.setOnFocusChangeListener(new OnFocusChangeAccountListenerPopUp(button2, newDashboardActivity));
            }
            if (!(this.multiuserdbhandler == null || (userEPG = this.multiuserdbhandler.getUserEPG(SharepreferenceDBHandler.getUserID(this.context))) == null || userEPG.equals(""))) {
                editText.setText(userEPG);
            }
            editText.requestFocus();
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.NewDashboardActivity.AnonymousClass5 */

                public void onClick(View view) {
                    NewDashboardActivity.this.changeSortPopUp.dismiss();
                }
            });
            if (button != null) {
                button.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.activity.NewDashboardActivity.AnonymousClass6 */

                    public void onClick(View view) {
                        if (fieldsCheck()) {
                            NewDashboardActivity.this.EPGLine = editText.getText().toString();
                            String unused2 = NewDashboardActivity.this.EPGLineType = "edit";
                            new _checkNetworkAvailable().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, NewDashboardActivity.this.EPGLine);
                        }
                    }

                    private boolean fieldsCheck() {
                        if (editText.getText().toString().trim().length() != 0) {
                            return true;
                        }
                        Toast.makeText(NewDashboardActivity.this.context, NewDashboardActivity.this.getResources().getString(R.string.enter_epg_link), 1).show();
                        return false;
                    }
                });
            }
            this.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
        }
    }

    @SuppressLint({"StaticFieldLeak"})
    private class _checkNetworkAvailable extends AsyncTask<String, Void, Boolean> {
        private _checkNetworkAvailable() {
        }

        public void onPreExecute() {
            Utils.showProgressDialog(NewDashboardActivity.this);
        }

        public Boolean doInBackground(String... strArr) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(strArr[0]).openConnection();
                boolean z = true;
                httpURLConnection.setInstanceFollowRedirects(true);
                httpURLConnection.setConnectTimeout(30000);
                httpURLConnection.setReadTimeout(30000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode != 200) {
                    if (responseCode != 405) {
                        z = false;
                    }
                }
                return Boolean.valueOf(z);
            } catch (Exception e) {
                Log.e("Google", e.toString());
                return false;
            }
        }

        public void onPostExecute(Boolean bool) {
            boolean booleanValue = bool.booleanValue();
            Utils.hideProgressDialog();
            if (!booleanValue) {
                Toast.makeText(NewDashboardActivity.this.context, NewDashboardActivity.this.getResources().getString(R.string.file_url_not_valid), 1).show();
            } else if (NewDashboardActivity.this.EPGLine != null) {
                if (NewDashboardActivity.this.changeSortPopUp != null) {
                    NewDashboardActivity.this.changeSortPopUp.dismiss();
                }
                if (NewDashboardActivity.this.EPGLineType.equals("edit")) {
                    NewDashboardActivity.this.multiuserdbhandler.editmultiusersEPG(String.valueOf(SharepreferenceDBHandler.getUserID(NewDashboardActivity.this.context)), NewDashboardActivity.this.EPGLine);
                } else {
                    NewDashboardActivity.this.multiuserdbhandler.addmultiusersEPG(String.valueOf(SharepreferenceDBHandler.getUserID(NewDashboardActivity.this.context)), NewDashboardActivity.this.EPGLine);
                }
                NewDashboardActivity.this.startActivity(new Intent(NewDashboardActivity.this.context, ImportEPGXMLActivity.class));
                NewDashboardActivity.this.overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
            }
        }
    }

    public static class OnFocusChangeAccountListenerPopUp implements View.OnFocusChangeListener {
        private final Activity activity;
        private final View view;

        public OnFocusChangeAccountListenerPopUp(View view2, NewDashboardActivity newDashboardActivity) {
            this.view = view2;
            this.activity = newDashboardActivity;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            if (this.activity != null) {
                float f = 1.0f;
                if (z) {
                    if (z) {
                        f = 1.12f;
                    }
                    if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("1")) {
                        performScaleXAnimation(f);
                        performScaleYAnimation(f);
                        view2.setBackgroundResource(R.drawable.back_btn_effect);
                    } else if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("2")) {
                        performScaleXAnimation(f);
                        performScaleYAnimation(f);
                        view2.setBackgroundResource(R.drawable.logout_btn_effect);
                    } else if (this.view == null || this.view.getTag() == null || !this.view.getTag().equals("3")) {
                        view2.setBackground(this.activity.getResources().getDrawable(R.drawable.selector_checkbox));
                    } else {
                        performScaleXAnimation(f);
                        performScaleYAnimation(f);
                        view2.setBackgroundResource(R.drawable.blue_btn_effect);
                    }
                } else if (!z) {
                    performScaleXAnimation(1.0f);
                    performScaleYAnimation(1.0f);
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("1"))) {
                        view2.setBackgroundResource(R.drawable.black_button_dark);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("2"))) {
                        view2.setBackgroundResource(R.drawable.black_button_dark);
                    }
                    if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("3")) {
                        view2.setBackgroundResource(R.drawable.black_button_dark);
                    }
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

    private long epgLastUpdated() {
        this.currentDate = currentDateValue();
        String str = "";
        this.liveStreamDBHandler.getEPGCount();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.databaseUpdatedStatusDBModelEPG = this.liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, "2");
        if (this.databaseUpdatedStatusDBModelEPG != null) {
            this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState();
            str = this.databaseUpdatedStatusDBModelEPG.getDbLastUpdatedDate();
        }
        if (str != null) {
            return getDateDiff(simpleDateFormat, str, this.currentDate);
        }
        return 0;
    }

    private long channnelLastUpdated() {
        this.currentDate = currentDateValue();
        String str = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.databaseUpdatedStatusDBModelEPG = this.liveStreamDBHandler.getdateDBStatus(AppConst.DB_CHANNELS, "1");
        if (this.databaseUpdatedStatusDBModelEPG != null) {
            this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState();
            str = this.databaseUpdatedStatusDBModelEPG.getDbLastUpdatedDate();
        }
        if (str != null) {
            return getDateDiff(simpleDateFormat, str, this.currentDate);
        }
        return 0;
    }

    private String epgEPGLastUpdatedate() {
        new SimpleDateFormat("dd/MM/yyyy");
        this.databaseUpdatedStatusDBModelEPG = this.liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, "2");
        if (this.databaseUpdatedStatusDBModelEPG == null) {
            return "";
        }
        this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState();
        return this.databaseUpdatedStatusDBModelEPG.getDbLastUpdatedDate();
    }

    private void changeStatusBarColor() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= 19) {
            window.clearFlags(67108864);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(Integer.MIN_VALUE);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    private void GetCurrentDateTime() {
        String date2 = Calendar.getInstance().getTime().toString();
        String time2 = Utils.getTime(this.context);
        String date3 = Utils.getDate(date2);
        if (this.time != null) {
            this.time.setText(time2);
        }
        if (this.date != null) {
            this.date.setText(date3);
        }
    }

    public void hideSystemUi() {
        this.main_layout.setSystemUiVisibility(4871);
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        if (this.mBackPressed + AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
            finish();
        } else {
            Toast.makeText(getBaseContext(), getResources().getString(R.string.press_back_to_exit), 0).show();
        }
        this.mBackPressed = System.currentTimeMillis();
    }

    public static String parseDateToddMMyyyy(String str) {
        try {
            return new SimpleDateFormat(" MMMM dd,yyyy").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US).parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String currentDateValue() {
        return Utils.parseDateToddMMyyyy(Calendar.getInstance().getTime().toString());
    }

    private void launchImportChannels(String str) {
        this.currentDate = currentDateValue();
        startImportChannels(getUserName(), getUserPassword(), this.currentDate, str);
    }

    public void launchTvGuide(String str) {
        this.currentDate = currentDateValue();
        startXMLTV(getUserName(), getUserPassword(), this.currentDate, str);
    }

    public String getUserName() {
        if (this.context == null) {
            return this.userName;
        }
        this.loginPreferencesAfterLogin = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        return this.loginPreferencesAfterLogin.getString("username", "");
    }

    public String getUserPassword() {
        if (this.context == null) {
            return this.userPassword;
        }
        this.loginPreferencesAfterLogin = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        return this.loginPreferencesAfterLogin.getString("password", "");
    }

    private void startImportChannels(String str, String str2, String str3, String str4) {
        int i;
        String str5 = "";
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            i = this.liveStreamDBHandler.getAvailableChannelsCountM3U();
        } else {
            i = this.liveStreamDBHandler.getAvailableChannelsCount();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.databaseUpdatedStatusDBModelEPG = this.liveStreamDBHandler.getdateDBStatus(AppConst.DB_CHANNELS, "1");
        if (this.databaseUpdatedStatusDBModelEPG != null) {
            this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState();
            str5 = this.databaseUpdatedStatusDBModelEPG.getDbLastUpdatedDate();
        }
        long dateDiff = getDateDiff(simpleDateFormat, str5, str3);
        if (i == 0 || dateDiff >= 1) {
            if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                startImportLiveActivityM3U(str4);
            } else {
                startImportLiveActivity(str4);
            }
        } else if (str4.equals("live")) {
            startActivity(new Intent(this, LiveActivityNewFlow.class));
        } else if (str4.equals(AppConst.VOD)) {
            startActivity(new Intent(this, VodActivityNewFlow.class));
        } else if (str4.equals("series")) {
            if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                startActivity(new Intent(this, SeriesActivityNewFlowM3U.class));
            } else {
                startActivity(new Intent(this, SeriesActivtyNewFlow.class));
            }
        } else if (!str4.equals("catchup")) {
        } else {
            if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                startActivity(new Intent(this, PlaylistsCategoriesActivity.class));
            } else {
                startActivity(new Intent(this, TVArchiveActivityNewFlow.class));
            }
        }
    }

    public void startXMLTV(String str, String str2, String str3, String str4) {
        String str5 = "";
        int ePGCount = this.liveStreamDBHandler.getEPGCount();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.databaseUpdatedStatusDBModelEPG = this.liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, "2");
        if (this.databaseUpdatedStatusDBModelEPG != null) {
            this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState();
            str5 = this.databaseUpdatedStatusDBModelEPG.getDbLastUpdatedDate();
        }
        long dateDiff = getDateDiff(simpleDateFormat, str5, str3);
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            String userEPG = this.multiuserdbhandler.getUserEPG(SharepreferenceDBHandler.getUserID(this.context));
            if (dateDiff > 0) {
                if (userEPG == null || userEPG.equals("")) {
                    addEPGPopUp(this);
                } else {
                    startImportTvGuideActivity(str4);
                }
            } else if (dateDiff != 0) {
                startImportTvGuideActivity(str4);
            } else if (userEPG == null || userEPG.equals("")) {
                addEPGPopUp(this);
            } else {
                startTvGuideActivity(str4);
            }
        } else if (ePGCount == 0) {
            startImportTvGuideActivity(str4);
        } else if (dateDiff > 0) {
            startImportTvGuideActivity(str4);
        } else if (dateDiff == 0) {
            startTvGuideActivity(str4);
        } else if (dateDiff >= 2) {
            startImportTvGuideActivity(str4);
        } else {
            startTvGuideActivity(str4);
        }
    }

    public static long getDateDiff(SimpleDateFormat simpleDateFormat, String str, String str2) {
        try {
            return TimeUnit.DAYS.convert(simpleDateFormat.parse(str2).getTime() - simpleDateFormat.parse(str).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void startImportTvGuideActivity(String str) {
        if (this.liveStreamDBHandler != null) {
            this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_EPG, "2", AppConst.DB_UPDATED_STATUS_PROCESSING, this.currentDate);
            if (!AppConst.M3U_LINE_ACTIVE.booleanValue() || !SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                Intent intent = new Intent(this, ImportEPGActivity.class);
                if (str.equals("epg")) {
                    intent.setAction("redirect_epg_category");
                } else if (str.equals("liveautoupdatedisable")) {
                    intent.setAction("redirect_live_tv_update_disabls");
                }
                startActivity(intent);
            } else {
                Intent intent2 = new Intent(this, ImportEPGXMLActivity.class);
                if (str.equals("epg")) {
                    intent2.setAction("redirect_epg_category");
                } else if (str.equals("liveautoupdatedisable")) {
                    intent2.setAction("redirect_live_tv_update_disabls");
                }
                startActivity(intent2);
            }
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        }
    }

    public void startImportTvGuideActivityFromLive(String str) {
        if (this.liveStreamDBHandler != null) {
            if (!AppConst.M3U_LINE_ACTIVE.booleanValue() || !SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_PROCESSING, this.currentDate);
                this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_EPG, "2", AppConst.DB_UPDATED_STATUS_PROCESSING, this.currentDate);
                Intent intent = new Intent(this, ImportStreamsActivity.class);
                if (str.equals("live")) {
                    intent.setAction("redirect_live_tv");
                } else if (str.equals(AppConst.VOD)) {
                    intent.setAction("redirect_vod");
                } else if (str.equals("catchup")) {
                    intent.setAction("redirect_catchup");
                } else if (str.equals("epgexpired")) {
                    intent.setAction("redirect_live_tv_epg_expired");
                } else {
                    intent.setAction("redirect_series");
                }
                startActivity(intent);
            } else {
                this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_PROCESSING, this.currentDate);
                this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_EPG, "2", AppConst.DB_UPDATED_STATUS_PROCESSING, this.currentDate);
                Intent intent2 = new Intent(this, ImportM3uActivity.class);
                if (str.equals("live")) {
                    intent2.setAction("redirect_live_tv");
                } else if (str.equals(AppConst.VOD)) {
                    intent2.setAction("redirect_vod");
                } else if (str.equals("catchup")) {
                    intent2.setAction("redirect_catchup");
                } else if (str.equals("epgexpired")) {
                    intent2.setAction("redirect_live_tv_epg_expired");
                } else {
                    intent2.setAction("redirect_series");
                }
                startActivity(intent2);
            }
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        }
    }

    public void startImportLiveActivity(String str) {
        if (this.liveStreamDBHandler != null) {
            this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_PROCESSING, this.currentDate);
            Intent intent = new Intent(this, ImportStreamsActivity.class);
            if (str.equals("live")) {
                intent.setAction("redirect_live_tv");
            } else if (str.equals(AppConst.VOD)) {
                intent.setAction("redirect_vod");
            } else if (str.equals("catchup")) {
                intent.setAction("redirect_catchup");
            } else {
                intent.setAction("redirect_series");
            }
            startActivity(intent);
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        }
    }

    public void startImportLiveActivityM3U(String str) {
        if (this.liveStreamDBHandler != null) {
            this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_PROCESSING, this.currentDate);
            if (this.liveStreamDBHandler != null) {
                this.liveStreamDBHandler.makeEmptyLiveCategory();
                this.liveStreamDBHandler.makeEmptyLiveStreams();
                this.liveStreamDBHandler.makeEmptyMovieCategory();
                this.liveStreamDBHandler.makeEmptyVODStreams();
            }
            if (this.seriesStreamsDatabaseHandler != null) {
                this.seriesStreamsDatabaseHandler.deleteAndRecreateSeriesCategories();
                this.seriesStreamsDatabaseHandler.deleteAndRecreateSeriesStreams();
            }
            Intent intent = new Intent(this, ImportM3uActivity.class);
            intent.putExtra("M3U_LINE", this.serverurl);
            if (str.equals("live")) {
                intent.setAction("redirect_live_tv");
            } else if (str.equals(AppConst.VOD)) {
                intent.setAction("redirect_vod");
            } else if (str.equals("catchup")) {
                intent.setAction("redirect_catchup");
            } else {
                intent.setAction("redirect_series");
            }
            startActivity(intent);
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        }
    }

    public boolean checkChannelsAutomation() {
        this.loginPreferencesAfterLoginChannels = getSharedPreferences(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, 0);
        return this.loginPreferencesAfterLoginChannels.getString(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, "").equals("checked");
    }

    public boolean checkEPGAutomation() {
        this.loginPreferencesAfterLoginEPG = getSharedPreferences(AppConst.LOGIN_PREF_AUTOMATION_EPG, 0);
        return this.loginPreferencesAfterLoginEPG.getString(AppConst.LOGIN_PREF_AUTOMATION_EPG, "").equals("checked");
    }

    public void startTvGuideActivity(String str) {
        if (str.equals("liveautoupdatedisable")) {
            startActivity(new Intent(this, LiveActivityNewFlow.class));
        } else {
            startActivity(new Intent(this, NewEPGCategoriesActivity.class));
        }
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    private class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private final View view;

        public OnFocusChangeAccountListener(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            if (AppConst.New_MultiScreen.booleanValue()) {
                if (NewDashboardActivity.this.settingsIV.hasFocus()) {
                    NewDashboardActivity.this.tvSettingsButton.setVisibility(0);
                    NewDashboardActivity.this.tvSwitchUserButton.setVisibility(8);
                    NewDashboardActivity.this.tvAccountinfoButton.setVisibility(8);
                    NewDashboardActivity.this.tv_notification.setVisibility(8);
                    NewDashboardActivity.this.tvRecordingsButton.setVisibility(8);
                } else if (NewDashboardActivity.this.ivSwitchUser.hasFocus()) {
                    NewDashboardActivity.this.tvSwitchUserButton.setVisibility(0);
                    NewDashboardActivity.this.tvSettingsButton.setVisibility(8);
                    NewDashboardActivity.this.tvAccountinfoButton.setVisibility(8);
                    NewDashboardActivity.this.tv_notification.setVisibility(8);
                    NewDashboardActivity.this.tvRecordingsButton.setVisibility(8);
                } else if (NewDashboardActivity.this.account_info.hasFocus()) {
                    NewDashboardActivity.this.tvAccountinfoButton.setVisibility(0);
                    NewDashboardActivity.this.tvSwitchUserButton.setVisibility(8);
                    NewDashboardActivity.this.tvSettingsButton.setVisibility(8);
                    NewDashboardActivity.this.tv_notification.setVisibility(8);
                    NewDashboardActivity.this.tvRecordingsButton.setVisibility(8);
                } else if (NewDashboardActivity.this.iv_notification.hasFocus()) {
                    NewDashboardActivity.this.tv_notification.setVisibility(0);
                    NewDashboardActivity.this.tvAccountinfoButton.setVisibility(8);
                    NewDashboardActivity.this.tvSwitchUserButton.setVisibility(8);
                    NewDashboardActivity.this.tvSettingsButton.setVisibility(8);
                    NewDashboardActivity.this.tvRecordingsButton.setVisibility(8);
                } else if (NewDashboardActivity.this.recordingsIV.hasFocus()) {
                    NewDashboardActivity.this.tvRecordingsButton.setVisibility(0);
                    NewDashboardActivity.this.tvAccountinfoButton.setVisibility(8);
                    NewDashboardActivity.this.tvSwitchUserButton.setVisibility(8);
                    NewDashboardActivity.this.tvSettingsButton.setVisibility(8);
                    NewDashboardActivity.this.tv_notification.setVisibility(8);
                } else {
                    NewDashboardActivity.this.tvAccountinfoButton.setVisibility(8);
                    NewDashboardActivity.this.tvSwitchUserButton.setVisibility(8);
                    NewDashboardActivity.this.tvSettingsButton.setVisibility(8);
                    NewDashboardActivity.this.tv_notification.setVisibility(8);
                    NewDashboardActivity.this.tvRecordingsButton.setVisibility(8);
                }
            } else if (NewDashboardActivity.this.settingsIV.hasFocus()) {
                NewDashboardActivity.this.tvSettingsButton.setVisibility(0);
                NewDashboardActivity.this.tvSwitchUserButton.setVisibility(8);
                NewDashboardActivity.this.tvAccountinfoButton.setVisibility(8);
                NewDashboardActivity.this.tv_notification.setVisibility(8);
            } else if (NewDashboardActivity.this.ivSwitchUser.hasFocus()) {
                NewDashboardActivity.this.tvSwitchUserButton.setVisibility(0);
                NewDashboardActivity.this.tvSettingsButton.setVisibility(8);
                NewDashboardActivity.this.tvAccountinfoButton.setVisibility(8);
                NewDashboardActivity.this.tv_notification.setVisibility(8);
            } else if (NewDashboardActivity.this.account_info.hasFocus()) {
                NewDashboardActivity.this.tvAccountinfoButton.setVisibility(0);
                NewDashboardActivity.this.tvSwitchUserButton.setVisibility(8);
                NewDashboardActivity.this.tvSettingsButton.setVisibility(8);
                NewDashboardActivity.this.tv_notification.setVisibility(8);
            } else if (NewDashboardActivity.this.iv_notification.hasFocus()) {
                NewDashboardActivity.this.tvAccountinfoButton.setVisibility(8);
                NewDashboardActivity.this.tvSwitchUserButton.setVisibility(8);
                NewDashboardActivity.this.tvSettingsButton.setVisibility(8);
                NewDashboardActivity.this.tv_notification.setVisibility(0);
            } else {
                NewDashboardActivity.this.tvAccountinfoButton.setVisibility(8);
                NewDashboardActivity.this.tvSwitchUserButton.setVisibility(8);
                NewDashboardActivity.this.tvSettingsButton.setVisibility(8);
                NewDashboardActivity.this.tv_notification.setVisibility(8);
            }
            float f = 1.09f;
            float f2 = 1.04f;
            float f3 = 2.0f;
            if (z) {
                Log.e("id is", "" + this.view.getTag());
                if (this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_CAT_ID)) {
                    if (!z) {
                        f3 = 1.0f;
                    }
                    performScaleXAnimation(f3);
                    performScaleYAnimation(f3);
                } else if (this.view.getTag().equals("15")) {
                    if (!z) {
                        f2 = 1.0f;
                    }
                    performScaleXAnimation(f2);
                    performScaleYAnimation(f2);
                    this.view.setBackgroundResource(R.drawable.billing_background_box_focused);
                } else {
                    if (!z) {
                        f = 1.0f;
                    }
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    if (this.view.getTag().equals("1")) {
                        this.view.setBackgroundResource(R.drawable.livetv_focused);
                    } else if (this.view.getTag().equals("2")) {
                        this.view.setBackgroundResource(R.drawable.ondemand_focused);
                    } else if (this.view.getTag().equals("3")) {
                        this.view.setBackgroundResource(R.drawable.catch_up_focused);
                    } else if (this.view.getTag().equals("4")) {
                        this.view.setBackgroundResource(R.drawable.green_focused);
                    } else if (this.view.getTag().equals("5")) {
                        this.view.setBackgroundResource(R.drawable.green_focused);
                    } else if (this.view.getTag().equals("6")) {
                        this.view.setBackgroundResource(R.drawable.green_focused);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID))) {
                        NewDashboardActivity.this.savePasswordBT.setBackgroundResource(R.drawable.back_btn_effect);
                    }
                    if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("9")) {
                        NewDashboardActivity.this.closedBT.setBackgroundResource(R.drawable.logout_btn_effect);
                    }
                }
            } else if (z) {
            } else {
                if (this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_CAT_ID)) {
                    if (!z) {
                        f3 = 1.0f;
                    }
                    performScaleXAnimation(f3);
                    performScaleYAnimation(f3);
                    performAlphaAnimation(z);
                } else if (this.view.getTag().equals("15")) {
                    if (!z) {
                        f2 = 1.0f;
                    }
                    performScaleXAnimation(f2);
                    performScaleYAnimation(f2);
                    this.view.setBackgroundResource(R.drawable.billing_background_box);
                } else {
                    if (!z) {
                        f = 1.0f;
                    }
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    performAlphaAnimation(z);
                    if (this.view.getTag().equals("1")) {
                        this.view.setBackgroundResource(R.drawable.live_tv_background);
                    } else if (this.view.getTag().equals("2")) {
                        this.view.setBackgroundResource(R.drawable.on_demand_background);
                    } else if (this.view.getTag().equals("3")) {
                        this.view.setBackgroundResource(R.drawable.catch_background);
                    } else if (this.view.getTag().equals("4")) {
                        this.view.setBackgroundResource(R.drawable.green_background);
                    } else if (this.view.getTag().equals("5")) {
                        this.view.setBackgroundResource(R.drawable.green_background);
                    } else if (this.view.getTag().equals("6")) {
                        this.view.setBackgroundResource(R.drawable.green_background);
                    } else if (this.view.getTag().equals("15")) {
                        this.view.setBackgroundResource(R.drawable.billing_background_box);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID))) {
                        NewDashboardActivity.this.savePasswordBT.setBackgroundResource(R.drawable.black_button_dark);
                    }
                    if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("9")) {
                        NewDashboardActivity.this.closedBT.setBackgroundResource(R.drawable.black_button_dark);
                    }
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

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.NewDashboardActivity):void
     arg types: [android.widget.Button, com.nst.yourname.view.activity.NewDashboardActivity]
     candidates:
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.content.Context):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.CheckAppupdateActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.LoginActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.MultiUserActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RateUsActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RecordingActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.SplashActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.VodActivityNewFlowSubCategories):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.NewDashboardActivity):void */
    public void showLogoutPopup() {
        if (this.context != null) {
            View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate((int) R.layout.delete_recording_popup, (RelativeLayout) findViewById(R.id.rl_password_verification));
            logoutPopUp = new PopupWindow(this);
            logoutPopUp.setContentView(inflate);
            logoutPopUp.setWidth(-1);
            logoutPopUp.setHeight(-1);
            logoutPopUp.setFocusable(true);
            logoutPopUp.setBackgroundDrawable(new BitmapDrawable());
            logoutPopUp.showAtLocation(inflate, 17, 0, 0);
            TextView textView = (TextView) inflate.findViewById(R.id.tv_parental_password);
            TextView textView2 = (TextView) inflate.findViewById(R.id.tv_delete_recording);
            Button button = (Button) inflate.findViewById(R.id.bt_start_recording);
            Button button2 = (Button) inflate.findViewById(R.id.bt_close);
            if (textView != null) {
                textView.setText(getResources().getString(R.string.logout_title));
            }
            if (textView2 != null) {
                textView2.setText(getResources().getString(R.string.logout_message));
            }
            if (button != null) {
                button.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button, this));
            }
            if (button2 != null) {
                button2.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button2, this));
            }
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.NewDashboardActivity.AnonymousClass7 */

                public void onClick(View view) {
                    NewDashboardActivity.logoutPopUp.dismiss();
                }
            });
            if (button != null) {
                button.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.activity.NewDashboardActivity.AnonymousClass8 */

                    public void onClick(View view) {
                        NewDashboardActivity.this.finish();
                        Utils.logoutUser(NewDashboardActivity.this.context);
                        NewDashboardActivity.this.overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                    }
                });
            }
        }
    }

    public void editEPGPopup(LinearLayout linearLayout) {
        if (this.context != null) {
            PopupMenu popupMenu = new PopupMenu(this.context, linearLayout);
            popupMenu.inflate(R.menu.menu_edit_epg);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                /* class com.nst.yourname.view.activity.NewDashboardActivity.AnonymousClass9 */

                public boolean onMenuItemClick(MenuItem menuItem) {
                    int itemId = menuItem.getItemId();
                    if (itemId == R.id.nav_edit_url) {
                        NewDashboardActivity.this.editEPGPopUp(NewDashboardActivity.this);
                        return false;
                    } else if (itemId != R.id.nav_open) {
                        return false;
                    } else {
                        openCategories();
                        return false;
                    }
                }

                private void openCategories() {
                    if (NewDashboardActivity.this.checkEPGAutomation()) {
                        NewDashboardActivity.this.launchTvGuide("epg");
                    } else {
                        NewDashboardActivity.this.startActivity(new Intent(NewDashboardActivity.this.context, NewEPGCategoriesActivity.class));
                    }
                    NewDashboardActivity.this.overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                }
            });
            popupMenu.show();
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onPause() {
        Utils.appResume(this.context);
        super.onPause();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.NewDashboardActivity):void
     arg types: [android.widget.Button, com.nst.yourname.view.activity.NewDashboardActivity]
     candidates:
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.content.Context):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.CheckAppupdateActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.LoginActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.MultiUserActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RateUsActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RecordingActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.SplashActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.VodActivityNewFlowSubCategories):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.NewDashboardActivity):void */
    @TargetApi(26)
    public void showRateUsPopup() {
        if (this.context != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
            View inflate = LayoutInflater.from(this).inflate((int) R.layout.rate_us_alert_box, (ViewGroup) null);
            RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.rl_Rateus);
            Button button = (Button) inflate.findViewById(R.id.btn_rateus);
            Button button2 = (Button) inflate.findViewById(R.id.btn_cancel);
            button.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button, this));
            button.requestFocus();
            button.setFocusableInTouchMode(true);
            button2.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button2, this));
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.NewDashboardActivity.AnonymousClass10 */

                public void onClick(View view) {
                    String packageName = NewDashboardActivity.this.getApplicationContext().getPackageName();
                    try {
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                        SharepreferenceDBHandler.setRateUsDontaskagain(true, NewDashboardActivity.this.context);
                        NewDashboardActivity.this.startActivity(intent);
                    } catch (Exception unused) {
                        Toast.makeText(NewDashboardActivity.this.context, NewDashboardActivity.this.getResources().getString(R.string.device_not_supported), 0).show();
                    }
                    NewDashboardActivity.this.alertDialog.dismiss();
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.NewDashboardActivity.AnonymousClass11 */

                public void onClick(View view) {
                    SharepreferenceDBHandler.setRateUsCount(0, NewDashboardActivity.this.context);
                    NewDashboardActivity.this.alertDialog.dismiss();
                }
            });
            builder.setView(inflate);
            this.alertDialog = builder.create();
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(this.alertDialog.getWindow().getAttributes());
            layoutParams.width = -1;
            layoutParams.height = -2;
            this.alertDialog.show();
            this.alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            this.alertDialog.getWindow().setAttributes(layoutParams);
            this.alertDialog.setCancelable(false);
        }
    }

    private void HitAPIForCheckServices() {
        Utils.showProgressDialog(this);
        ((ApiService) ApiclientRetrofit.getApiRetrofit().create(ApiService.class)).AuthcheckServices(AppConst.BillingAPI_Username, AppConst.BillingAPI_Password, "validateCustomLogin", "yes", SharepreferenceDBHandler.getUserName(this.context), SharepreferenceDBHandler.getUserPassword(this.context)).enqueue(new Callback<LoginWHMCSModelClass>() {
            /* class com.nst.yourname.view.activity.NewDashboardActivity.AnonymousClass12 */

            @Override // retrofit2.Callback
            public void onResponse(@NonNull Call<LoginWHMCSModelClass> call, @NonNull Response<LoginWHMCSModelClass> response) {
                Utils.hideProgressDialog();
                if (!response.isSuccessful()) {
                    Toast.makeText(NewDashboardActivity.this.context, "", 0).show();
                } else if (response.body() == null || response.body().getResult() == null || !response.body().getResult().equalsIgnoreCase("success")) {
                    Toast.makeText(NewDashboardActivity.this.context, NewDashboardActivity.this.getResources().getString(R.string.something_wrong), 0).show();
                } else {
                    NewDashboardActivity.this.HitAddUserAPI(response.body().getData().getClientid().intValue());
                    ClientSharepreferenceHandler.setFreeTrialTime(response.body().getData().getExp_time(), NewDashboardActivity.this.context);
                    ClientSharepreferenceHandler.setClientId(response.body().getData().getClientid().intValue(), NewDashboardActivity.this.context);
                    ClientSharepreferenceHandler.setUserEmaiId(response.body().getData().getEmail(), NewDashboardActivity.this.context);
                    ClientSharepreferenceHandler.setUserPrefix(response.body().getData().getPrefix(), NewDashboardActivity.this.context);
                    ClientSharepreferenceHandler.setUserSuffix(response.body().getData().getSuffix(), NewDashboardActivity.this.context);
                    NewDashboardActivity.this.startActivity(new Intent(NewDashboardActivity.this, ServicesDashboardActivity.class));
                    Toast.makeText(NewDashboardActivity.this.context, NewDashboardActivity.this.getResources().getString(R.string.logged_in), 0).show();
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(@NonNull Call<LoginWHMCSModelClass> call, @NonNull Throwable th) {
                Utils.hideProgressDialog();
                Toast.makeText(NewDashboardActivity.this.context, NewDashboardActivity.this.getResources().getString(R.string.something_wrong), 0).show();
            }
        });
    }

    public void HitAddUserAPI(int i) {
        ((ApiService) ApiclientRetrofit.getApiRetrofit().create(ApiService.class)).getAdduser(AppConst.BillingAPI_Username, AppConst.BillingAPI_Password, "addusr", i, FirebaseInstanceId.getInstance().getToken(), "yes").enqueue(new Callback<HashMap>() {
            /* class com.nst.yourname.view.activity.NewDashboardActivity.AnonymousClass13 */

            @Override // retrofit2.Callback
            public void onFailure(@NonNull Call<HashMap> call, @NonNull Throwable th) {
            }

            @Override // retrofit2.Callback
            public void onResponse(@NonNull Call<HashMap> call, @NonNull Response<HashMap> response) {
                response.isSuccessful();
            }
        });
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void validateLogin(LoginCallback loginCallback, String str) {
        int i;
        if (loginCallback != null && loginCallback.getUserLoginInfo() != null && loginCallback.getUserLoginInfo().getAuth().intValue() == 1 && loginCallback.getUserLoginInfo().getStatus().equals("Active")) {
            String expDate = loginCallback.getUserLoginInfo().getExpDate();
            if (this.tvExpiryDate == null) {
                return;
            }
            if (expDate == null || expDate.isEmpty()) {
                TextView textView = this.tvExpiryDate;
                textView.setText(getResources().getString(R.string.expiration) + " " + getResources().getString(R.string.unlimited));
                return;
            }
            try {
                i = Integer.parseInt(expDate);
            } catch (NumberFormatException unused) {
                i = 1;
            }
            String format = new SimpleDateFormat("MMMM d, yyyy").format(new Date(((long) i) * 1000));
            TextView textView2 = this.tvExpiryDate;
            textView2.setText(getResources().getString(R.string.expiration) + " " + format);
        }
    }
}
