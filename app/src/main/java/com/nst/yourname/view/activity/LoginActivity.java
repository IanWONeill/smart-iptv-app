package com.nst.yourname.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.activities.FreeTrailActivity;
import com.nst.yourname.WHMCSClientapp.commanClassess.AppConstant;
import com.nst.yourname.WebServiceHandler.Globals;
import com.nst.yourname.WebServiceHandler.MainAsynListener;
import com.nst.yourname.WebServiceHandler.RavSharedPrefrences;
import com.nst.yourname.WebServiceHandler.Webservices;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.ExtendedAppInfo;
import com.nst.yourname.model.MultiUserDBModel;
import com.nst.yourname.model.callback.ActivationCallBack;
import com.nst.yourname.model.callback.LoginCallback;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.MultiUserDBHandler;
import com.nst.yourname.model.database.SeriesStreamsDatabaseHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.presenter.ActivationPresenter;
import com.nst.yourname.presenter.LoginPresenter;
import com.nst.yourname.view.interfaces.ActivationInterface;
import com.nst.yourname.view.interfaces.LoginInterface;
import com.nst.yourname.view.utility.UtilsMethods;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.achartengine.chart.TimeChart;
import org.json.JSONObject;

@SuppressWarnings("ALL")
public class LoginActivity extends AppCompatActivity implements LoginInterface, MainAsynListener<String>, ActivationInterface {
    public static InputFilter EMOJI_FILTER = new InputFilter() {
        /* class com.nst.yourname.view.activity.LoginActivity.AnonymousClass1 */

        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            while (i < i2) {
                if (Character.getType(charSequence.charAt(i)) == 19) {
                    return "";
                }
                i++;
            }
            return null;
        }
    };
    private static final int TIME_INTERVAL = 2000;
    static int urlCountValue;
    String FirstMdkey;
    TextView RefreshBT;
    String SecondMdkey;
    String action;
    private ActivationPresenter activationPresenter;
    @BindView(R.id.activity_login)
    LinearLayout activityLogin;
    @BindView(R.id.btn_free_trail)
    Button btn_free_trail;
    CheckBox cbRememberMe;
    int click = -1;
    public Context context = this;
    long currentDate = -1;
    String currentDate1 = currentDateValue();
    long dateDifference = 0;
    public boolean doubleBackToExitPressedOnce = false;
    EditText emailIdET;
    EditText etName;
    @BindView(R.id.eyeicon)
    ImageView eyepass;
    private DatabaseHandler favDBHandler;
    Handler handler;
    String key;
    @BindView(R.id.linearlayout)
    LinearLayout linearLayout;
    @BindView(R.id.link_transform)
    TextView link_transform;
    private LiveStreamDBHandler liveStreamDBHandler;
    LinearLayout llEditText;
    Button loginBT;
    private SharedPreferences loginPreferences;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences loginPreferencesAfterLoginChannels;
    private SharedPreferences loginPreferencesAfterLoginEPG;
    private SharedPreferences loginPreferencesAfterLoginLanguage;
    private SharedPreferences.Editor loginPreferencesEditor;
    private SharedPreferences loginPreferencesRemember;
    private SharedPreferences loginPreferencesServerURl;
    private SharedPreferences.Editor loginPreferencesServerURlPut;
    private SharedPreferences loginPreferencesSharedPref_allowed_format;
    private SharedPreferences loginPreferencesSharedPref_auto_start;
    private SharedPreferences loginPreferencesSharedPref_epg_channel_auto_update;
    private SharedPreferences loginPreferencesSharedPref_epg_channel_update;
    private SharedPreferences loginPreferencesSharedPref_multi_dns;
    private SharedPreferences loginPreferencesSharedPref_multi_dns_valid;
    private SharedPreferences loginPreferencesSharedPref_time_format;
    private SharedPreferences loginPreferencesUpgradeDate;
    private  SharedPreferences applicationModedData;
    private SharedPreferences.Editor loginPrefsEditorAutoStart;
    private SharedPreferences.Editor loginPrefsEditorBeforeLogin;
    private SharedPreferences.Editor loginPrefsEditorChannels;
    private SharedPreferences.Editor loginPrefsEditorEPG;
    private SharedPreferences.Editor loginPrefsEditorUpgaradeDate;
    private SharedPreferences.Editor loginPrefsEditor_epgchannelautoupdate;
    private SharedPreferences.Editor loginPrefsEditor_epgchannelupdate;
    private SharedPreferences.Editor loginPrefsEditor_fomat;
    private SharedPreferences.Editor loginPrefsEditor_multi_dns;
    private SharedPreferences.Editor loginPrefsEditor_multi_dns_valid;
    private SharedPreferences.Editor loginPrefsEditor_timefomat;
    private SharedPreferences.Editor applicationModedDataEditor;

    public LoginPresenter loginPresenter;
    @BindView(R.id.tv_enter_credentials)
    TextView loginTV;
    private String loginWith;
    private long mBackPressed;
    String model = Build.MODEL;
    private MultiUserDBHandler multiuserdbhandler;
    String name;
    String newUrl = "";
    String newUsername = "";
    String nextDueDate = "";
    public String password;
    EditText passwordET;
    @BindView(R.id.password_ful)
    LinearLayout password_full;
    private ProgressDialog progressDialog;
    int random;
    String reqString;
    @BindView(R.id.rl_bt_refresh)
    Button rl_bt_refresh;
    @BindView(R.id.rl_bt_submit)
    RelativeLayout rl_bt_submit;
    @BindView(R.id.rl_email)
    RelativeLayout rl_email;
    @BindView(R.id.rl_name)
    RelativeLayout rl_name;
    @BindView(R.id.rl_password)
    RelativeLayout rl_password;
    @BindView(R.id.rl_remember_me)
    RelativeLayout rl_remember_me;
    @BindView(R.id.rl_server_url)
    RelativeLayout rl_server_url;
    @BindView(R.id.rl_view_log)
    RelativeLayout rl_view_log;
    String salt;
    private Boolean saveLogin;
    String savedUrl = "";
    String savedUsername = "";
    private String selected_language = "";
    private SeriesStreamsDatabaseHandler seriesStreamsDatabaseHandler;
    ArrayList<String> serverList = new ArrayList<>();
    ArrayList<String> serverList_panel = new ArrayList<>();
    ArrayList<ExtendedAppInfo> extendedAppInfos = new ArrayList<> ();
    EditText serverURLET;
    private String serverUrl;
    private SharedPreferences sharedPrefMultiDNSServe;
    private SharedPreferences.Editor sharedPrefMultiDNSServeEditor;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    long upgradeDate = -1;
    String urls = "";
    String isVodApp = "";
    public String username;
    String version;
    TextView viewLog;
    @BindView(R.id.iv_logo)
    ImageView yourLogioTV;
    String resellerInfo ="";
    String selectedTheme = "";

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    @SuppressLint({"RtlHardcoded"})
    public void onCreate(@Nullable Bundle bundle) {
        Webservices.getWebservices = new Webservices(this);
        super.onCreate(bundle);
        setContentView((int) R.layout.login_new);
        ButterKnife.bind(this);
        appVersionName();
        DEviceVersion();
        getDeviceName();
        GetRandomNumber();
        Utils.setXtream1_06(this.context);
        createLayout();
        initialize();
        changeStatusBarColor();
        this.selected_language = this.context.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, 0).getString(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, "English");
        if (this.selected_language.equalsIgnoreCase("Arabic")) {
            this.passwordET.setGravity(21);
        } else {
            this.passwordET.setGravity(19);
        }
        if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
            this.etName.requestFocus();
        } else {
            this.emailIdET.requestFocus();
        }
        UtilsMethods.Block_SpaceInEditText(this.passwordET);
        this.emailIdET.setFilters(new InputFilter[]{EMOJI_FILTER});
        this.action = getIntent().getAction();
        if (this.action != null && this.action.equalsIgnoreCase(AppConstant.ACTION_LOGIN_PERFORM)) {
            this.emailIdET.setText(SharepreferenceDBHandler.getUserName(this.context));
            this.passwordET.setText(SharepreferenceDBHandler.getUserPassword(this.context));
            if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
                this.etName.setText("Free Trial");
            }
            this.loginBT.performClick();
            if (this.cbRememberMe != null) {
                this.cbRememberMe.setChecked(true);
            }
        }
    }

    @SuppressLint({"ResourceType"})
    public void createLayout() {
        setTVLayout();
        this.loginBT.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.LoginActivity.AnonymousClass2 */

            public void onClick(View view) {
                LoginActivity.this.submit();
            }
        });
        this.rl_bt_refresh.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.LoginActivity.AnonymousClass3 */

            public void onClick(View view) {
                AppConst.REFRESH_BUTTON = true;
                LoginActivity.this.SequrityApi();
                LoginActivity.this.atStart();
            }
        });
        this.viewLog.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.LoginActivity.AnonymousClass4 */

            public void onClick(View view) {
                if (AppConst.ACTIVE_STATUS.booleanValue()) {
                    LoginActivity.this.startMutiUSerActivity();
                } else {
                    Toast.makeText(LoginActivity.this, LoginActivity.this.context.getResources().getString(R.string.status_suspend), 0).show();
                }
            }
        });
        if (AppConst.GET_FREETRIAL.booleanValue()) {
            this.btn_free_trail.setVisibility(0);
        } else {
            this.btn_free_trail.setVisibility(8);
        }
        this.btn_free_trail.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.LoginActivity.AnonymousClass5 */

            public void onClick(View view) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, FreeTrailActivity.class));
            }
        });
        this.link_transform.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.LoginActivity.AnonymousClass6 */

            public void onClick(View view) {
                if (AppConst.CODE_ACTIVATOR.booleanValue()) {
                    AppConst.CODE_ACTIVATOR = false;
                    LoginActivity.this.initialize();
                    return;
                }
                AppConst.CODE_ACTIVATOR = true;
                LoginActivity.this.initialize();
            }
        });
    }

    @SuppressLint({"ResourceType"})
    private void setTVLayout() {
        this.etName = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        this.etName.setPaddingRelative(35, 0, 35, 0);
        this.etName.setLayoutParams(layoutParams);
        this.etName.setHint(getResources().getString(R.string.your_name));
        this.etName.setHintTextColor(getResources().getColor(R.color.white));
        this.etName.setHintTextColor(-1);
        this.etName.setTextSize(22.0f);
        this.etName.setId(101);
        this.etName.setBackground(getResources().getDrawable(R.drawable.selector_login_fields));
        this.etName.setFocusable(true);
        this.etName.setTypeface(Typeface.SANS_SERIF);
        this.etName.setInputType(161);
        this.rl_name.addView(this.etName);
        this.emailIdET = new EditText(this);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -1);
        this.emailIdET.setPaddingRelative(35, 0, 35, 0);
        this.emailIdET.setLayoutParams(layoutParams2);
        if (AppConst.ACT_TRIAL.booleanValue()) {
            this.linearLayout.setGravity(16);
        }
        this.emailIdET.setHint(getResources().getString(R.string.username));
        this.emailIdET.setHintTextColor(getResources().getColor(R.color.white));
        this.emailIdET.setHintTextColor(-1);
        this.emailIdET.setTextSize(22.0f);
        this.emailIdET.setId(102);
        this.emailIdET.setFocusable(true);
        this.emailIdET.setBackground(getResources().getDrawable(R.drawable.selector_login_fields));
        this.emailIdET.setTypeface(Typeface.SANS_SERIF);
        this.emailIdET.setInputType(161);
        this.rl_email.addView(this.emailIdET);
        this.passwordET = new EditText(this);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -1);
        this.passwordET.setPaddingRelative(35, 0, 35, 0);
        this.passwordET.setLayoutParams(layoutParams3);
        this.passwordET.setHint(getResources().getString(R.string.password));
        this.passwordET.setHintTextColor(getResources().getColor(R.color.white));
        this.passwordET.setHintTextColor(-1);
        this.passwordET.setTextSize(22.0f);
        this.passwordET.setId(103);
        this.passwordET.setBackground(getResources().getDrawable(R.drawable.selector_login_fields));
        this.passwordET.setFocusable(true);
        this.passwordET.setTypeface(Typeface.SANS_SERIF);
        this.passwordET.setInputType(TsExtractor.TS_STREAM_TYPE_AC3);
        this.rl_password.addView(this.passwordET);
        this.eyepass.setId(110);
        this.eyepass.setFocusable(true);
        this.passwordET.setNextFocusDownId(104);
        this.passwordET.setNextFocusUpId(102);
        this.eyepass.setNextFocusDownId(104);
        this.eyepass.setNextFocusUpId(102);
        this.eyepass.setBackground(getResources().getDrawable(R.drawable.selector_login_fields));
        this.eyepass.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.LoginActivity.AnonymousClass7 */

            public void onClick(View view) {
                if (LoginActivity.this.click == -1) {
                    LoginActivity.this.passwordET.setInputType(145);
                    LoginActivity.this.eyepass.setImageResource(R.drawable.showpassword);
                    LoginActivity.this.click++;
                    return;
                }
                LoginActivity loginActivity = LoginActivity.this;
                loginActivity.click--;
                LoginActivity.this.passwordET.setInputType(TsExtractor.TS_STREAM_TYPE_AC3);
                LoginActivity.this.eyepass.setImageResource(R.drawable.hidepassword);
            }
        });
        this.loginBT = new Button(this);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-1, -1);
        this.loginBT.setPaddingRelative(35, 0, 35, 0);
        this.loginBT.setLayoutParams(layoutParams4);
        this.loginBT.setText(getResources().getString(R.string.submit));
        this.loginBT.setTextColor(-16777216);
        this.loginBT.setTextSize(22.0f);
        this.loginBT.setId(105);
        this.loginBT.setBackground(getResources().getDrawable(R.drawable.selector_button));
        this.loginBT.setFocusable(true);
        this.loginBT.setGravity(17);
        this.loginBT.setTypeface(Typeface.SANS_SERIF);
        this.rl_bt_submit.addView(this.loginBT);
        this.cbRememberMe = new CheckBox(this);
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-1, -1);
        this.cbRememberMe.setPaddingRelative(0, 0, 35, 0);
        this.cbRememberMe.setLayoutParams(layoutParams5);
        this.cbRememberMe.setText(getResources().getString(R.string.remember_me));
        this.cbRememberMe.setTextColor(-1);
        this.cbRememberMe.setId(106);
        if (Build.VERSION.SDK_INT >= 21) {
            this.cbRememberMe.setButtonTintList(new ColorStateList(new int[][]{new int[]{-16842912}, new int[]{16842912}}, new int[]{Color.parseColor("#ffffff"), Color.parseColor("#ffffff")}));
        }
        this.cbRememberMe.setBackground(getResources().getDrawable(R.drawable.selector_checkbox));
        this.cbRememberMe.setFocusable(true);
        this.cbRememberMe.setChecked(true);
        this.cbRememberMe.setTextSize(22.0f);
        this.cbRememberMe.setTypeface(Typeface.SANS_SERIF);
        this.rl_remember_me.addView(this.cbRememberMe);
        this.viewLog = new TextView(this);
        this.viewLog.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        this.viewLog.setText(getResources().getString(R.string.list_users));
        this.viewLog.setCompoundDrawablesWithIntrinsicBounds((int) R.drawable.manage_user, 0, 0, 0);
        this.viewLog.setTextSize(18.0f);
        this.viewLog.setPaddingRelative(20, 0, 0, 0);
        this.viewLog.setTypeface(this.viewLog.getTypeface(), 1);
        this.viewLog.setTextColor(-1);
        this.viewLog.setBackground(getResources().getDrawable(R.drawable.selector_checkbox));
        this.viewLog.setTextSize(22.0f);
        this.viewLog.setId(107);
        this.viewLog.setFocusable(true);
        this.viewLog.setGravity(16);
        this.viewLog.setCompoundDrawablePadding(40);
        this.rl_view_log.addView(this.viewLog);
        if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
            this.etName.requestFocus();
            this.etName.requestFocusFromTouch();
            return;
        }
        this.emailIdET.requestFocus();
        this.emailIdET.requestFocusFromTouch();
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 19) {
            return super.onKeyUp(i, keyEvent);
        }
        return true;
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

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        if (AppConst.M3U_LINE_ACTIVE.booleanValue()) {
            super.onBackPressed();
        } else if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
        } else {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getResources().getString(R.string.press_back_to_exit), 0).show();
            new Handler().postDelayed(new Runnable() {
                /* class com.nst.yourname.view.activity.LoginActivity.AnonymousClass8 */

                public void run() {
                    boolean unused = LoginActivity.this.doubleBackToExitPressedOnce = false;
                }
            }, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
        }
    }

    public void initialize() {
        try {
            this.context = this;
            this.multiuserdbhandler = new MultiUserDBHandler(this.context);
            this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
            this.seriesStreamsDatabaseHandler = new SeriesStreamsDatabaseHandler(this.context);
            if (AppConst.ACT_TRIAL.booleanValue()) {
                this.link_transform.setVisibility(0);
            }
            if (AppConst.CODE_ACTIVATOR.booleanValue()) {
                this.emailIdET.setHint((CharSequence) null);
                this.emailIdET.setHint("");
                this.link_transform.setText(getResources().getString(R.string.with_act_code));
                this.loginTV.setText(getResources().getString(R.string.enter_act_code));
                this.rl_server_url.setVisibility(8);
                this.password_full.setVisibility(8);
                this.emailIdET.setVisibility(8);
                this.emailIdET.setVisibility(0);
                this.emailIdET.setHint(getResources().getString(R.string.act_code));
                this.rl_remember_me.setVisibility(0);
                this.loginBT.setText(getResources().getString(R.string.code_act));
                this.cbRememberMe.setChecked(true);
                if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
                    this.rl_name.setVisibility(0);
                } else {
                    this.rl_name.setVisibility(8);
                }
            } else if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
                this.emailIdET.setHint((CharSequence) null);
                this.emailIdET.setHint("");
                this.link_transform.setText(getResources().getString(R.string.with_user_pass));
                this.loginBT.setText(getResources().getString(R.string.add_user));
                this.emailIdET.setVisibility(8);
                this.emailIdET.setVisibility(0);
                this.emailIdET.setHint(getResources().getString(R.string.username));
                this.password_full.setVisibility(0);
                this.rl_remember_me.setVisibility(8);
                this.cbRememberMe.setChecked(false);
                this.rl_name.setVisibility(0);
                this.rl_server_url.setVisibility(8);
                if (this.multiuserdbhandler.getAllUsers().size() > 0) {
                    this.rl_view_log.setVisibility(0);
                } else if (AppConst.M3U_LINE_ACTIVE.booleanValue()) {
                    this.rl_view_log.setVisibility(0);
                } else {
                    this.rl_view_log.setVisibility(8);
                }
            } else {
                this.emailIdET.setHint((CharSequence) null);
                this.emailIdET.setHint("");
                this.link_transform.setText(getResources().getString(R.string.with_user_pass));
                this.loginTV.setText(getResources().getString(R.string.credential_detail));
                this.emailIdET.setVisibility(8);
                this.emailIdET.setVisibility(0);
                this.password_full.setVisibility(0);
                this.emailIdET.setHint(getResources().getString(R.string.username));
                this.loginBT.setText(getResources().getString(R.string.submit));
                this.rl_remember_me.setVisibility(0);
                this.cbRememberMe.setChecked(true);
                this.rl_server_url.setVisibility(8);
                this.rl_name.setVisibility(8);
                this.viewLog.setVisibility(8);
            }
            this.etName.setError(null);
            this.emailIdET.setError(null);
            this.passwordET.setError(null);
            this.favDBHandler = new DatabaseHandler(this.context);
            if (this.context != null) {
                this.progressDialog = new ProgressDialog(this.context);
                if (this.action != null && this.action.equalsIgnoreCase(AppConstant.ACTION_LOGIN_PERFORM)) {
                    this.progressDialog.setMessage("Auto Login");
                } else if (AppConst.CODE_ACTIVATOR.booleanValue()) {
                    this.progressDialog.setMessage("");
                    this.progressDialog.setMessage(getResources().getString(R.string.please_wait_act));
                } else {
                    this.progressDialog.setMessage("");
                    this.progressDialog.setMessage(getResources().getString(R.string.please_wait));
                }
                this.progressDialog.setCanceledOnTouchOutside(false);
                this.progressDialog.setCancelable(false);
                this.progressDialog.setProgressStyle(0);
            }
            this.username = this.emailIdET.getText().toString();
            this.password = this.passwordET.getText().toString();
            this.loginPresenter = new LoginPresenter(this, this.context);
            this.loginPreferences = getSharedPreferences(AppConst.SHARED_PREFERENCE, 0);
            this.loginPreferencesRemember = getSharedPreferences(AppConst.SHARED_PREFERENCE_REMEBER_ME, 0);
            this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            this.loginPreferencesAfterLoginLanguage = getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, 0);
            this.loginPreferencesServerURl = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0);
            this.loginPreferencesServerURlPut = this.loginPreferencesServerURl.edit();
            this.loginPreferencesUpgradeDate = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_UPGREDAE_DATE, 0);
            this.loginPrefsEditorUpgaradeDate = this.loginPreferencesUpgradeDate.edit();
            this.loginPrefsEditorBeforeLogin = this.loginPreferencesRemember.edit();
            this.loginPreferencesEditor = this.loginPreferences.edit();
            this.saveLogin = Boolean.valueOf(this.loginPreferencesRemember.getBoolean(AppConst.PREF_SAVE_LOGIN, false));
            this.loginPreferencesSharedPref_multi_dns = getSharedPreferences(AppConst.LOGIN_PREF_MULTI_DNS, 0);
            this.loginPrefsEditor_multi_dns = this.loginPreferencesSharedPref_multi_dns.edit();
            this.loginPreferencesSharedPref_multi_dns_valid = getSharedPreferences(AppConst.LOGIN_PREF_MULTI_DNS_VALID, 0);
            this.loginPrefsEditor_multi_dns_valid = this.loginPreferencesSharedPref_multi_dns_valid.edit();
            this.sharedPrefMultiDNSServe = getSharedPreferences(AppConst.LOGIN_PREF_SERVER_URL_DNS, 0);
            checkLoginStatus();
            if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
                this.emailIdET.setText("");
                this.passwordET.setText("");
                this.cbRememberMe.setChecked(false);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loginCheck() {
        this.loginWith = this.loginPreferencesRemember.getString(AppConst.PREF_LOGIN_WITH, "");
        if (this.loginWith.equals(AppConst.LOGIN_WITH_DETAILS)) {
            if (!this.saveLogin.booleanValue()) {
                this.username = this.loginPreferencesRemember.getString("username", "");
                this.password = this.loginPreferencesRemember.getString("password", "");
                if (this.username != null && !this.username.equalsIgnoreCase("playlist")) {
                    this.emailIdET.setText(this.username);
                }
                if (this.password != null && !this.password.equalsIgnoreCase("playlist")) {
                    this.passwordET.setText(this.password);
                }
                this.cbRememberMe.setChecked(true);
            } else if (!AppConst.ACTIVE_STATUS.booleanValue()) {
                onFinish();
                if (AppConst.CODE_ACTIVATOR.booleanValue()) {
                    this.emailIdET.setText(SharepreferenceDBHandler.getActCode(this.context));
                    this.passwordET.setText("");
                    return;
                }
                this.username = this.loginPreferencesRemember.getString("username", "");
                this.password = this.loginPreferencesRemember.getString("password", "");
                if (this.username != null && !this.username.equalsIgnoreCase("playlist")) {
                    this.emailIdET.setText(this.username);
                }
                if (this.password != null && !this.password.equalsIgnoreCase("playlist")) {
                    this.passwordET.setText(this.password);
                }
                this.cbRememberMe.setChecked(false);
            } else if (this.loginPreferencesAfterLogin.getString("username", "").equals("") || this.loginPreferencesAfterLogin.getString("password", "").equals("")) {
                onFinish();
                if (AppConst.CODE_ACTIVATOR.booleanValue()) {
                    this.emailIdET.setText(SharepreferenceDBHandler.getActCode(this.context));
                    this.passwordET.setText("");
                    return;
                }
                this.emailIdET.setText(this.loginPreferencesRemember.getString("username", ""));
                this.passwordET.setText(this.loginPreferencesRemember.getString("password", ""));
                this.cbRememberMe.setChecked(true);
            } else {
                this.currentDate = System.currentTimeMillis();
                if (this.loginPreferencesUpgradeDate != null) {
                    this.upgradeDate = this.loginPreferencesUpgradeDate.getLong(AppConst.LOGIN_PREF_UPGRADE_DATE, -1);
                }
                if (this.context != null && this.liveStreamDBHandler != null && this.liveStreamDBHandler.getAvailableChannelsCount() > 0) {
                    onFinish();
                    startActivity(new Intent(this, NewDashboardActivity.class));
                    finish();
                } else if (this.context != null) {
                    onFinish();
                    startActivity(new Intent(this, ImportStreamsActivity.class));
                    finish();
                }
            }
        } else if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
            this.etName.requestFocus();
            this.etName.requestFocusFromTouch();
            onFinish();
        } else {
            this.emailIdET.requestFocus();
            this.emailIdET.requestFocusFromTouch();
            onFinish();
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        Utils.appResume(this.context);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void atStart() {
        if (this.progressDialog != null) {
            this.progressDialog.show();
        }
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFinish() {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFailed(String str) {
        if (this.context != null && !str.isEmpty()) {
            Utils.showToast(this.context, str);
        }
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void validateLogin(LoginCallback loginCallback, String str) {
        if (loginCallback == null || loginCallback.getUserLoginInfo() == null) {
            onFinish();
            onFailed(getResources().getString(R.string.invalid_server_response));
        } else if (loginCallback.getUserLoginInfo().getAuth().intValue() == 1) {
            String status = loginCallback.getUserLoginInfo().getStatus();
            if (status.equals("Active")) {
                String username2 = loginCallback.getUserLoginInfo().getUsername();
                String password2 = loginCallback.getUserLoginInfo().getPassword();
                String port = loginCallback.getServerInfo().getPort();
                String url = loginCallback.getServerInfo().getUrl();
                String expDate = loginCallback.getUserLoginInfo().getExpDate();
                String isTrial = loginCallback.getUserLoginInfo().getIsTrial();
                String activeCons = loginCallback.getUserLoginInfo().getActiveCons();
                String createdAt = loginCallback.getUserLoginInfo().getCreatedAt();
                String timeCurrent = loginCallback.getServerInfo().getTimeCurrent();
                String maxConnections = loginCallback.getUserLoginInfo().getMaxConnections();
                List<String> allowedOutputFormats = loginCallback.getUserLoginInfo().getAllowedOutputFormats();
                String serverProtocal = loginCallback.getServerInfo().getServerProtocal();
                String httpsPort = loginCallback.getServerInfo().getHttpsPort();
                String rtmpPort = loginCallback.getServerInfo().getRtmpPort();
                if (allowedOutputFormats.size() != 0) {
                    allowedOutputFormats.get(0);
                }
                this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0).getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "").toLowerCase();
                SharedPreferences.Editor edit = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).edit();
                edit.putString("username", username2);
                edit.putString("password", password2);
                edit.putString(AppConst.LOGIN_PREF_SERVER_PORT, port);
                edit.putString(AppConst.LOGIN_PREF_SERVER_URL, url);
                edit.putString(AppConst.LOGIN_PREF_EXP_DATE, expDate);
                edit.putString(AppConst.LOGIN_PREF_IS_TRIAL, isTrial);
                edit.putString(AppConst.LOGIN_PREF_CRT_DATE, timeCurrent);
                edit.putString(AppConst.LOGIN_PREF_ACTIVE_CONS, activeCons);
                edit.putString(AppConst.LOGIN_PREF_CREATE_AT, createdAt);
                edit.putString(AppConst.LOGIN_PREF_MAX_CONNECTIONS, maxConnections);
                String str2 = AppConst.LOGIN_PREF_SERVER_URL_MAG;
                edit.putString(str2, url + ":" + port);
                edit.putString(AppConst.LOGIN_PREF_SERVER_PROTOCOL, serverProtocal);
                edit.putString(AppConst.LOGIN_PREF_SERVER_HTTPS_PORT, httpsPort);
                edit.putString(AppConst.LOGIN_PREF_SERVER_RTMP_PORT, rtmpPort);
                edit.apply();
                this.loginPreferencesSharedPref_allowed_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
                this.loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
                this.loginPreferencesSharedPref_epg_channel_update = this.context.getSharedPreferences(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, 0);
                this.loginPreferencesAfterLoginChannels = this.context.getSharedPreferences(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, 0);
                this.loginPreferencesAfterLoginEPG = this.context.getSharedPreferences(AppConst.LOGIN_PREF_AUTOMATION_EPG, 0);
                this.loginPrefsEditor_fomat = this.loginPreferencesSharedPref_allowed_format.edit();
                this.loginPrefsEditor_timefomat = this.loginPreferencesSharedPref_time_format.edit();
                this.loginPrefsEditor_epgchannelupdate = this.loginPreferencesSharedPref_epg_channel_update.edit();
                this.loginPrefsEditorChannels = this.loginPreferencesAfterLoginChannels.edit();
                this.loginPrefsEditorEPG = this.loginPreferencesAfterLoginEPG.edit();
                this.loginPreferencesSharedPref_auto_start = this.context.getSharedPreferences(AppConst.LOGIN_PREF_AUTO_START, 0);
                this.loginPrefsEditorAutoStart = this.loginPreferencesSharedPref_auto_start.edit();
                if (this.loginPrefsEditorAutoStart != null) {
                    this.loginPrefsEditorAutoStart.putBoolean(AppConst.LOGIN_PREF_FULL_EPG, true);
                    this.loginPrefsEditorAutoStart.apply();
                }
                if (this.loginPreferencesAfterLoginChannels.getString(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, "").equals("")) {
                    this.loginPrefsEditorChannels.putString(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, "checked");
                    this.loginPrefsEditorChannels.apply();
                }
                if (this.loginPreferencesAfterLoginEPG.getString(AppConst.LOGIN_PREF_AUTOMATION_EPG, "").equals("")) {
                    this.loginPrefsEditorEPG.putString(AppConst.LOGIN_PREF_AUTOMATION_EPG, "checked");
                    this.loginPrefsEditorEPG.apply();
                }
                AppConst.FROM_MULTIUSER_TO_LOGIN = false;
                if (this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "").equals("")) {
                    if (Utils.getIsXtream1_06(this.context)) {
                        this.loginPrefsEditor_fomat.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "ts");
                    } else {
                        this.loginPrefsEditor_fomat.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "default");
                    }
                    this.loginPrefsEditor_fomat.apply();
                }
                if (this.loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "").equals("")) {
                    this.loginPrefsEditor_timefomat.putString(AppConst.LOGIN_PREF_TIME_FORMAT, "HH:mm");
                    this.loginPrefsEditor_timefomat.apply();
                }
                if (this.loginPreferencesSharedPref_epg_channel_update.getString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "").equals("")) {
                    this.loginPrefsEditor_epgchannelupdate.putString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "all");
                    this.loginPrefsEditor_epgchannelupdate.apply();
                }
                if (this.liveStreamDBHandler != null && this.liveStreamDBHandler.getMagportal(url) == 0) {
                    this.liveStreamDBHandler.deleteAndRecreateAllTables();
                    this.seriesStreamsDatabaseHandler.deleteAndRecreateAllVSeriesTables();
                    DatabaseHandler databaseHandler = this.favDBHandler;
                    this.liveStreamDBHandler.addMagPortal(url);
                }
                if (!AppConst.MultiDNS_And_MultiUser.booleanValue() && this.cbRememberMe.isChecked()) {
                    this.loginPrefsEditorBeforeLogin.putBoolean(AppConst.PREF_SAVE_LOGIN, true);
                    this.loginPrefsEditorBeforeLogin.apply();
                }
                this.multiuserdbhandler = new MultiUserDBHandler(this.context);
                if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
                    AppConst.FROM_LOGIN_TO_MULTIUSER = true;
                    SharepreferenceDBHandler.setCurrentAPPType(AppConst.TYPE_API, this.context);
                    if (!Boolean.valueOf(this.multiuserdbhandler.checkregistration(this.name, username2, password2, AppConst.SERVER_URL_FOR_MULTI_USER, AppConst.TYPE_API, url)).booleanValue()) {
                        this.multiuserdbhandler.addmultiusers(this.name, username2, password2, AppConst.SERVER_URL_FOR_MULTI_USER, url);
                        Toast.makeText(this, getResources().getString(R.string.user_added), 0).show();
                    } else {
                        onFinish();
                        Toast.makeText(this, getString(R.string.already_exist_with_name) + this.name + getString(R.string.username_with_cllon) + username2 + getString(R.string.and_portal) + AppConst.SERVER_URL_FOR_MULTI_USER, 0).show();
                    }
                    onFinish();
                    startActivity(new Intent(this, MultiUserActivity.class));
                    finish();
                } else if (this.action != null && this.action.equalsIgnoreCase(AppConstant.ACTION_LOGIN_PERFORM) && this.context != null) {
                    onFinish();
                    startActivity(new Intent(this, ImportStreamsActivity.class));
                    finish();
                } else if (this.context != null && this.liveStreamDBHandler != null && this.liveStreamDBHandler.getAvailableChannelsCount() > 0) {
                    onFinish();
                    startActivity(new Intent(this, NewDashboardActivity.class));
                    finish();
                } else if (this.context != null) {
                    onFinish();
                    startActivity(new Intent(this, ImportStreamsActivity.class));
                    finish();
                }
            } else {
                onFinish();
                Toast.makeText(this, getResources().getString(R.string.invalid_status) + status, 0).show();
            }
        } else if (str.equals(AppConst.VALIDATE_LOGIN)) {
            onFinish();
            Toast.makeText(this, getResources().getString(R.string.invalid_details), 0).show();
        }
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void stopLoader(String str) {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
            Toast.makeText(this, this.context.getResources().getString(R.string.invalid_server_url), 0).show();
        }
    }

    public void submit() {
        AppConst.isXTREAM_1_0_6 = false;
        this.username = this.emailIdET.getText().toString().trim();
        this.password = this.passwordET.getText().toString().trim();
        this.name = this.etName.getText().toString().trim();
        this.loginPreferencesServerURlPut = this.loginPreferencesServerURl.edit();
        if (!AppConst.CODE_ACTIVATOR.booleanValue() && checkFields()) {
            atStart();
            if (this.cbRememberMe.isChecked()) {
                checkLoginData();
                this.loginPrefsEditorBeforeLogin.putString("username", this.username);
                this.loginPrefsEditorBeforeLogin.putString("password", this.password);
                this.loginPrefsEditorBeforeLogin.putString("activationCode", "");
                this.loginPrefsEditorBeforeLogin.putString(AppConst.PREF_LOGIN_WITH, AppConst.LOGIN_WITH_DETAILS);
                this.loginPrefsEditorBeforeLogin.apply();
            } else {
                checkLoginData();
                this.loginPrefsEditorBeforeLogin.clear();
                this.loginPrefsEditorBeforeLogin.putString(AppConst.PREF_LOGIN_WITH, AppConst.LOGIN_WITH_DETAILS);
                this.loginPrefsEditorBeforeLogin.apply();
            }
            this.loginPreferencesServerURlPut.apply();
        } else if (AppConst.CODE_ACTIVATOR.booleanValue() && checkFields()) {
            if (this.cbRememberMe.isChecked()) {
                SharepreferenceDBHandler.setActivationCode(this.context, this.username);
            } else {
                SharepreferenceDBHandler.setActivationCode(this.context, "");
            }
            atStart();
            this.name = this.etName.getText().toString().trim();
            this.activationPresenter = new ActivationPresenter(this, this.context);
            this.activationPresenter.validateAct(this.username);
        }
    }

    public void startMutiUSerActivity() {
        SharepreferenceDBHandler.setCurrentAPPType(AppConst.TYPE_API, this.context);
        startActivity(new Intent(this, MultiUserActivity.class));
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        finish();
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void magFailedtoLogin(String str, String str2, String str3, Context context2) {
        onFinish();
        if (!Utils.getIsXtream1_06(context2)) {
            AppConst.isXTREAM_1_0_6 = true;
            Utils.setXtream1_06(context2);
            this.loginPresenter.validateLoginUsingPanelAPI(str2, str3);
        } else if (!Utils.getIsXtream1_06(context2)) {
        } else {
            if (!str.equals("")) {
                Utils.showToast(context2, context2.getResources().getString(R.string.invalid_detail));
            } else {
                Utils.showToast(context2, "Your Account is invalid or expired !");
            }
        }
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void validateloginMultiDNS(LoginCallback loginCallback, String str, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
        String str2 = str;
        if (loginCallback.getUserLoginInfo() == null || loginCallback.getServerInfo() == null) {
            if (this.liveStreamDBHandler.getMagportal().equals("")) {
                if (arrayList.size() == 0) {
                    onFinish();
                    Utils.showToast(this.context, "Your Account is invalid or has expired !");
                }
                if (arrayList.size() > 0) {
                    storeServerUrls(arrayList, this.urls);
                }
            } else if (arrayList.size() > 0) {
                if (Utils.getIsXtream1_06(this.context)) {
                    this.loginPresenter.reValidateLoginUisngPanelApi(this.username, this.password, arrayList, urlCountValue, arrayList2);
                } else {
                    this.loginPresenter.reValidateLogin(this.username, this.password, arrayList, urlCountValue, arrayList2);
                }
            }
        }
        if (this.context == null) {
            return;
        }
        if (Utils.getIsXtream1_06(this.context)) {
            if (loginCallback == null || loginCallback.getUserLoginInfo() == null || loginCallback.getServerInfo() == null) {
                if (loginCallback.getUserLoginInfo() != null && loginCallback.getServerInfo() != null) {
                    onFinish();
                    onFailed(getResources().getString(R.string.invalid_server_response));
                }
            } else if (loginCallback.getUserLoginInfo().getAuth().intValue() == 1) {
                String status = loginCallback.getUserLoginInfo().getStatus();
                if (status.equals("Active")) {
                    currentDateValue();
                    String username2 = loginCallback.getUserLoginInfo().getUsername();
                    String password2 = loginCallback.getUserLoginInfo().getPassword();
                    String port = loginCallback.getServerInfo().getPort();
                    String url = loginCallback.getServerInfo().getUrl();
                    String expDate = loginCallback.getUserLoginInfo().getExpDate();
                    String timeCurrent = loginCallback.getServerInfo().getTimeCurrent();
                    String isTrial = loginCallback.getUserLoginInfo().getIsTrial();
                    String activeCons = loginCallback.getUserLoginInfo().getActiveCons();
                    String createdAt = loginCallback.getUserLoginInfo().getCreatedAt();
                    String maxConnections = loginCallback.getUserLoginInfo().getMaxConnections();
                    List<String> allowedOutputFormats = loginCallback.getUserLoginInfo().getAllowedOutputFormats();
                    if (allowedOutputFormats.size() != 0) {
                        allowedOutputFormats.get(0);
                    }
                    this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0).getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "").toLowerCase();
                    SharedPreferences.Editor edit = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).edit();
                    edit.putString("username", username2);
                    edit.putString("password", password2);
                    edit.putString(AppConst.LOGIN_PREF_SERVER_PORT, port);
                    edit.putString(AppConst.LOGIN_PREF_SERVER_URL, url);
                    edit.putString(AppConst.LOGIN_PREF_EXP_DATE, expDate);
                    edit.putString(AppConst.LOGIN_PREF_CRT_DATE, timeCurrent);
                    edit.putString(AppConst.LOGIN_PREF_IS_TRIAL, isTrial);
                    edit.putString(AppConst.LOGIN_PREF_ACTIVE_CONS, activeCons);
                    edit.putString(AppConst.LOGIN_PREF_CREATE_AT, createdAt);
                    edit.putString(AppConst.LOGIN_PREF_MAX_CONNECTIONS, maxConnections);
                    String str3 = AppConst.LOGIN_PREF_SERVER_URL_MAG;
                    edit.putString(str3, url + ":" + port);
                    edit.apply();
                    this.loginPreferencesSharedPref_allowed_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
                    this.loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
                    this.loginPreferencesSharedPref_epg_channel_update = this.context.getSharedPreferences(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, 0);
                    this.loginPreferencesAfterLoginChannels = this.context.getSharedPreferences(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, 0);
                    this.loginPreferencesAfterLoginEPG = this.context.getSharedPreferences(AppConst.LOGIN_PREF_AUTOMATION_EPG, 0);
                    this.loginPrefsEditor_fomat = this.loginPreferencesSharedPref_allowed_format.edit();
                    this.loginPrefsEditor_timefomat = this.loginPreferencesSharedPref_time_format.edit();
                    this.loginPrefsEditor_epgchannelupdate = this.loginPreferencesSharedPref_epg_channel_update.edit();
                    this.loginPrefsEditorChannels = this.loginPreferencesAfterLoginChannels.edit();
                    this.loginPrefsEditorEPG = this.loginPreferencesAfterLoginEPG.edit();
                    if (this.loginPreferencesAfterLoginChannels.getString(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, "").equals("")) {
                        this.loginPrefsEditorChannels.putString(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, "checked");
                        this.loginPrefsEditorChannels.apply();
                    }
                    if (this.loginPreferencesAfterLoginEPG.getString(AppConst.LOGIN_PREF_AUTOMATION_EPG, "").equals("")) {
                        this.loginPrefsEditorEPG.putString(AppConst.LOGIN_PREF_AUTOMATION_EPG, "checked");
                        this.loginPrefsEditorEPG.apply();
                    }
                    AppConst.FROM_MULTIUSER_TO_LOGIN = false;
                    String string = this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
                    if (string != null && string.equals("")) {
                        if (Utils.getIsXtream1_06(this.context)) {
                            this.loginPrefsEditor_fomat.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "ts");
                        } else {
                            this.loginPrefsEditor_fomat.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "default");
                        }
                        this.loginPrefsEditor_fomat.apply();
                    }
                    if (this.loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "").equals("")) {
                        this.loginPrefsEditor_timefomat.putString(AppConst.LOGIN_PREF_TIME_FORMAT, "HH:mm");
                        this.loginPrefsEditor_timefomat.apply();
                    }
                    this.loginPreferencesSharedPref_auto_start = this.context.getSharedPreferences(AppConst.LOGIN_PREF_AUTO_START, 0);
                    this.loginPrefsEditorAutoStart = this.loginPreferencesSharedPref_auto_start.edit();
                    if (this.loginPrefsEditorAutoStart != null) {
                        this.loginPrefsEditorAutoStart.putBoolean(AppConst.LOGIN_PREF_FULL_EPG, true);
                        this.loginPrefsEditorAutoStart.apply();
                    }
                    if (this.loginPreferencesSharedPref_epg_channel_update.getString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "").equals("")) {
                        this.loginPrefsEditor_epgchannelupdate.putString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "all");
                        this.loginPrefsEditor_epgchannelupdate.apply();
                    }
                    if (this.liveStreamDBHandler != null && this.liveStreamDBHandler.getMagportal(url) == 0) {
                        this.liveStreamDBHandler.deleteAndRecreateAllTables();
                        this.seriesStreamsDatabaseHandler.deleteAndRecreateAllVSeriesTables();
                        DatabaseHandler databaseHandler = this.favDBHandler;
                        this.liveStreamDBHandler.addMagPortal(url);
                    }
                    if (!AppConst.MultiDNS_And_MultiUser.booleanValue() && this.cbRememberMe.isChecked()) {
                        this.loginPrefsEditorBeforeLogin.putBoolean(AppConst.PREF_SAVE_LOGIN, true);
                        this.loginPrefsEditorBeforeLogin.apply();
                    }
                    this.multiuserdbhandler = new MultiUserDBHandler(this.context);
                    if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
                        AppConst.FROM_LOGIN_TO_MULTIUSER = true;
                        if (!Boolean.valueOf(this.multiuserdbhandler.checkregistration(this.name, username2, password2, AppConst.SERVER_URL_FOR_MULTI_USER, AppConst.TYPE_API, url)).booleanValue()) {
                            this.multiuserdbhandler.addmultiusers(this.name, username2, password2, AppConst.SERVER_URL_FOR_MULTI_USER, url);
                            Toast.makeText(this, getResources().getString(R.string.user_added), 0).show();
                        } else {
                            Toast.makeText(this, "LOG Already Exists with NAME: " + this.name + ",Username: " + username2 + " and Portal " + AppConst.SERVER_URL_FOR_MULTI_USER, 0).show();
                            onFinish();
                        }
                        onFinish();
                        startActivity(new Intent(this, MultiUserActivity.class));
                        finish();
                        return;
                    }
                    if (!Boolean.valueOf(this.multiuserdbhandler.checkregistration("", username2, "", AppConst.SERVER_URL_FOR_MULTI_USER, AppConst.TYPE_API, url)).booleanValue()) {
                        this.multiuserdbhandler.addmultiusers("", username2, "", AppConst.SERVER_URL_FOR_MULTI_USER, url);
                    }
                    if (this.context != null) {
                        this.multiuserdbhandler = new MultiUserDBHandler(this.context);
                        SharepreferenceDBHandler.setUserID(this.multiuserdbhandler.getAutoIdLoggedInUser(this.name, username2, password2, AppConst.SERVER_URL_FOR_MULTI_USER, "", ""), this.context);
                    }
                    if (this.context != null && this.liveStreamDBHandler != null && this.liveStreamDBHandler.getAvailableChannelsCount() > 0) {
                        onFinish();
                        startActivity(new Intent(this, NewDashboardActivity.class));
                        finish();
                    } else if (this.context != null) {
                        onFinish();
                        startActivity(new Intent(this, ImportStreamsActivity.class));
                        finish();
                    }
                } else {
                    onFinish();
                    Toast.makeText(this, getResources().getString(R.string.invalid_status) + status, 0).show();
                }
            } else if (str2.equals(AppConst.VALIDATE_LOGIN)) {
                onFinish();
                Toast.makeText(this, getResources().getString(R.string.invalid_details), 0).show();
            }
        } else if (loginCallback == null || loginCallback.getUserLoginInfo() == null || loginCallback.getServerInfo() == null) {
            if (loginCallback.getUserLoginInfo() != null && loginCallback.getServerInfo() != null) {
                onFinish();
                onFailed(getResources().getString(R.string.invalid_server_response));
            }
        } else if (loginCallback.getUserLoginInfo().getAuth().intValue() == 1) {
            String status2 = loginCallback.getUserLoginInfo().getStatus();
            if (status2.equals("Active")) {
                currentDateValue();
                String username3 = loginCallback.getUserLoginInfo().getUsername();
                String password3 = loginCallback.getUserLoginInfo().getPassword();
                String port2 = loginCallback.getServerInfo().getPort();
                String url2 = loginCallback.getServerInfo().getUrl();
                String expDate2 = loginCallback.getUserLoginInfo().getExpDate();
                String timeCurrent2 = loginCallback.getServerInfo().getTimeCurrent();
                String isTrial2 = loginCallback.getUserLoginInfo().getIsTrial();
                String activeCons2 = loginCallback.getUserLoginInfo().getActiveCons();
                String createdAt2 = loginCallback.getUserLoginInfo().getCreatedAt();
                String maxConnections2 = loginCallback.getUserLoginInfo().getMaxConnections();
                List<String> allowedOutputFormats2 = loginCallback.getUserLoginInfo().getAllowedOutputFormats();
                if (allowedOutputFormats2.size() != 0) {
                    allowedOutputFormats2.get(0);
                }
                this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0).getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "").toLowerCase();
                SharedPreferences.Editor edit2 = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).edit();
                edit2.putString("username", username3);
                edit2.putString("password", password3);
                edit2.putString(AppConst.LOGIN_PREF_SERVER_PORT, port2);
                edit2.putString(AppConst.LOGIN_PREF_SERVER_URL, url2);
                edit2.putString(AppConst.LOGIN_PREF_EXP_DATE, expDate2);
                edit2.putString(AppConst.LOGIN_PREF_CRT_DATE, timeCurrent2);
                edit2.putString(AppConst.LOGIN_PREF_IS_TRIAL, isTrial2);
                edit2.putString(AppConst.LOGIN_PREF_ACTIVE_CONS, activeCons2);
                edit2.putString(AppConst.LOGIN_PREF_CREATE_AT, createdAt2);
                edit2.putString(AppConst.LOGIN_PREF_MAX_CONNECTIONS, maxConnections2);
                String str4 = AppConst.LOGIN_PREF_SERVER_URL_MAG;
                edit2.putString(str4, url2 + ":" + port2);
                edit2.apply();
                this.loginPreferencesSharedPref_allowed_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
                this.loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
                this.loginPreferencesSharedPref_epg_channel_update = this.context.getSharedPreferences(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, 0);
                this.loginPreferencesAfterLoginChannels = this.context.getSharedPreferences(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, 0);
                this.loginPreferencesAfterLoginEPG = this.context.getSharedPreferences(AppConst.LOGIN_PREF_AUTOMATION_EPG, 0);
                this.loginPrefsEditor_fomat = this.loginPreferencesSharedPref_allowed_format.edit();
                this.loginPrefsEditor_timefomat = this.loginPreferencesSharedPref_time_format.edit();
                this.loginPrefsEditor_epgchannelupdate = this.loginPreferencesSharedPref_epg_channel_update.edit();
                this.loginPrefsEditorChannels = this.loginPreferencesAfterLoginChannels.edit();
                this.loginPrefsEditorEPG = this.loginPreferencesAfterLoginEPG.edit();
                if (this.loginPreferencesAfterLoginChannels.getString(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, "").equals("")) {
                    this.loginPrefsEditorChannels.putString(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, "checked");
                    this.loginPrefsEditorChannels.apply();
                }
                if (this.loginPreferencesAfterLoginEPG.getString(AppConst.LOGIN_PREF_AUTOMATION_EPG, "").equals("")) {
                    this.loginPrefsEditorEPG.putString(AppConst.LOGIN_PREF_AUTOMATION_EPG, "checked");
                    this.loginPrefsEditorEPG.apply();
                }
                AppConst.FROM_MULTIUSER_TO_LOGIN = false;
                String string2 = this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
                if (string2 != null && string2.equals("")) {
                    if (Utils.getIsXtream1_06(this.context)) {
                        this.loginPrefsEditor_fomat.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "ts");
                    } else {
                        this.loginPrefsEditor_fomat.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "default");
                    }
                    this.loginPrefsEditor_fomat.apply();
                }
                if (this.loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "").equals("")) {
                    this.loginPrefsEditor_timefomat.putString(AppConst.LOGIN_PREF_TIME_FORMAT, "HH:mm");
                    this.loginPrefsEditor_timefomat.apply();
                }
                this.loginPreferencesSharedPref_auto_start = this.context.getSharedPreferences(AppConst.LOGIN_PREF_AUTO_START, 0);
                this.loginPrefsEditorAutoStart = this.loginPreferencesSharedPref_auto_start.edit();
                if (this.loginPrefsEditorAutoStart != null) {
                    this.loginPrefsEditorAutoStart.putBoolean(AppConst.LOGIN_PREF_FULL_EPG, true);
                    this.loginPrefsEditorAutoStart.apply();
                }
                if (this.loginPreferencesSharedPref_epg_channel_update.getString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "").equals("")) {
                    this.loginPrefsEditor_epgchannelupdate.putString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "all");
                    this.loginPrefsEditor_epgchannelupdate.apply();
                }
                if (this.liveStreamDBHandler != null && this.liveStreamDBHandler.getMagportal(url2) == 0) {
                    this.liveStreamDBHandler.deleteAndRecreateAllTables();
                    this.seriesStreamsDatabaseHandler.deleteAndRecreateAllVSeriesTables();
                    DatabaseHandler databaseHandler2 = this.favDBHandler;
                    this.liveStreamDBHandler.addMagPortal(url2);
                }
                if (!AppConst.MultiDNS_And_MultiUser.booleanValue() && this.cbRememberMe.isChecked()) {
                    this.loginPrefsEditorBeforeLogin.putBoolean(AppConst.PREF_SAVE_LOGIN, true);
                    this.loginPrefsEditorBeforeLogin.apply();
                }
                this.multiuserdbhandler = new MultiUserDBHandler(this.context);
                if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
                    AppConst.FROM_LOGIN_TO_MULTIUSER = true;
                    if (!Boolean.valueOf(this.multiuserdbhandler.checkregistration(this.name, username3, password3, AppConst.SERVER_URL_FOR_MULTI_USER, AppConst.TYPE_API, url2)).booleanValue()) {
                        this.multiuserdbhandler.addmultiusers(this.name, username3, password3, AppConst.SERVER_URL_FOR_MULTI_USER, url2);
                        Toast.makeText(this, getResources().getString(R.string.user_added), 0).show();
                    } else {
                        Toast.makeText(this, "LOG Already Exists with NAME: " + this.name + ",Username: " + username3 + " and Portal " + AppConst.SERVER_URL_FOR_MULTI_USER, 0).show();
                        onFinish();
                    }
                    onFinish();
                    startActivity(new Intent(this, MultiUserActivity.class));
                    finish();
                    return;
                }
                if (!Boolean.valueOf(this.multiuserdbhandler.checkregistration("", username3, "", AppConst.SERVER_URL_FOR_MULTI_USER, AppConst.TYPE_API, url2)).booleanValue()) {
                    this.multiuserdbhandler.addmultiusers("", username3, "", AppConst.SERVER_URL_FOR_MULTI_USER, url2);
                }
                if (this.context != null) {
                    this.multiuserdbhandler = new MultiUserDBHandler(this.context);
                    SharepreferenceDBHandler.setUserID(this.multiuserdbhandler.getAutoIdLoggedInUser(this.name, username3, password3, AppConst.SERVER_URL_FOR_MULTI_USER, "", ""), this.context);
                }
                if (this.context != null && this.liveStreamDBHandler != null && this.liveStreamDBHandler.getAvailableChannelsCount() > 0) {
                    onFinish();
                    startActivity(new Intent(this, NewDashboardActivity.class));
                    finish();
                } else if (this.context != null) {
                    onFinish();
                    startActivity(new Intent(this, ImportStreamsActivity.class));
                    finish();
                }
            } else {
                onFinish();
                Toast.makeText(this, getResources().getString(R.string.invalid_status) + status2, 0).show();
            }
        } else if (str2.equals(AppConst.VALIDATE_LOGIN)) {
            onFinish();
            Toast.makeText(this, getResources().getString(R.string.invalid_details), 0).show();
        }
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void reValidateLogin(LoginCallback loginCallback, String str, int i, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
        if (loginCallback.getServerInfo() == null) {
            if (this.liveStreamDBHandler.getMagportal().equals("")) {
                if (arrayList.size() > 0) {
                    storeServerUrls(arrayList, this.urls);
                }
                if (arrayList.size() == 0) {
                    onFinish();
                    Utils.showToast(this.context, "Your Account is invalid or has expired !");
                }
            } else if (arrayList.size() > 0) {
                if (Utils.getIsXtream1_06(this.context)) {
                    this.loginPresenter.reValidateLoginUisngPanelApi(this.username, this.password, arrayList, urlCountValue, arrayList2);
                } else {
                    this.loginPresenter.reValidateLogin(this.username, this.password, arrayList, urlCountValue, arrayList2);
                }
            }
        }
        if (this.context == null) {
            return;
        }
        if (Utils.getIsXtream1_06(this.context)) {
            if (loginCallback != null && loginCallback.getUserLoginInfo() != null && loginCallback.getServerInfo() != null) {
                if (loginCallback.getUserLoginInfo().getAuth().intValue() == 1) {
                    String status = loginCallback.getUserLoginInfo().getStatus();
                    if (status.equals("Active")) {
                        String username2 = loginCallback.getUserLoginInfo().getUsername();
                        String password2 = loginCallback.getUserLoginInfo().getPassword();
                        String port = loginCallback.getServerInfo().getPort();
                        String url = loginCallback.getServerInfo().getUrl();
                        String expDate = loginCallback.getUserLoginInfo().getExpDate();
                        String isTrial = loginCallback.getUserLoginInfo().getIsTrial();
                        String activeCons = loginCallback.getUserLoginInfo().getActiveCons();
                        String createdAt = loginCallback.getUserLoginInfo().getCreatedAt();
                        String maxConnections = loginCallback.getUserLoginInfo().getMaxConnections();
                        List<String> allowedOutputFormats = loginCallback.getUserLoginInfo().getAllowedOutputFormats();
                        if (allowedOutputFormats.size() != 0) {
                            allowedOutputFormats.get(0);
                        }
                        SharedPreferences.Editor edit = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).edit();
                        edit.putString("username", username2);
                        edit.putString("password", password2);
                        edit.putString(AppConst.LOGIN_PREF_SERVER_PORT, port);
                        edit.putString(AppConst.LOGIN_PREF_EXP_DATE, expDate);
                        edit.putString(AppConst.LOGIN_PREF_IS_TRIAL, isTrial);
                        edit.putString(AppConst.LOGIN_PREF_ACTIVE_CONS, activeCons);
                        edit.putString(AppConst.LOGIN_PREF_CREATE_AT, createdAt);
                        edit.putString(AppConst.LOGIN_PREF_MAX_CONNECTIONS, maxConnections);
                        edit.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, url);
                        String string = this.loginPreferencesSharedPref_multi_dns.getString(AppConst.LOGIN_PREF_MULTI_DNS, "");
                        edit.putString(AppConst.LOGIN_PREF_SERVER_URL, string);
                        edit.commit();
                        this.loginPreferencesSharedPref_allowed_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
                        this.loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
                        this.loginPreferencesSharedPref_epg_channel_update = this.context.getSharedPreferences(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, 0);
                        this.loginPrefsEditor_fomat = this.loginPreferencesSharedPref_allowed_format.edit();
                        this.loginPrefsEditor_timefomat = this.loginPreferencesSharedPref_time_format.edit();
                        this.loginPrefsEditor_epgchannelupdate = this.loginPreferencesSharedPref_epg_channel_update.edit();
                        String string2 = this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
                        if (string2 != null && string2.equals("")) {
                            this.loginPrefsEditor_fomat.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "ts");
                            this.loginPrefsEditor_fomat.commit();
                        }
                        String string3 = this.loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "");
                        if (string3 != null && string3.equals("")) {
                            this.loginPrefsEditor_timefomat.putString(AppConst.LOGIN_PREF_TIME_FORMAT, "HH:mm");
                            this.loginPrefsEditor_timefomat.commit();
                        }
                        String string4 = this.loginPreferencesSharedPref_epg_channel_update.getString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "");
                        if (string4 != null && string4.equals("")) {
                            this.loginPrefsEditor_epgchannelupdate.putString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "withepg");
                            this.loginPrefsEditor_epgchannelupdate.commit();
                        }
                        String string5 = this.loginPreferencesSharedPref_epg_channel_auto_update.getString(AppConst.LOGIN_PREF_AUTOUPDATE, "");
                        if (string5 != null && string5.equals("")) {
                            this.loginPrefsEditor_epgchannelautoupdate.putString(AppConst.LOGIN_PREF_AUTOUPDATE, getResources().getString(R.string.disable));
                            this.loginPrefsEditor_epgchannelautoupdate.commit();
                        }
                        if (this.liveStreamDBHandler != null && this.liveStreamDBHandler.getMagportal(string) == 0) {
                            this.liveStreamDBHandler.deleteAndRecreateAllTables();
                            this.seriesStreamsDatabaseHandler.deleteAndRecreateAllVSeriesTables();
                            this.liveStreamDBHandler.addMagPortal(string);
                        }
                        onFinish();
                        String str2 = url;
                        if (!Boolean.valueOf(this.multiuserdbhandler.checkregistration(this.name, username2, password2, AppConst.SERVER_URL_FOR_MULTI_USER, AppConst.TYPE_API, str2)).booleanValue()) {
                            this.multiuserdbhandler.addmultiusers(this.name, username2, password2, AppConst.SERVER_URL_FOR_MULTI_USER, str2);
                            Toast.makeText(this, getResources().getString(R.string.user_added), 0).show();
                            if (this.context != null) {
                                this.multiuserdbhandler = new MultiUserDBHandler(this.context);
                                SharepreferenceDBHandler.setUserID(this.multiuserdbhandler.getAutoIdLoggedInUser(this.name, username2, username2, password2, AppConst.SERVER_URL_FOR_MULTI_USER, ""), this.context);
                            }
                            Toast.makeText(this, getResources().getString(R.string.logged_in), 0).show();
                            if (this.context != null && this.liveStreamDBHandler != null && this.liveStreamDBHandler.getAvailableChannelsCount() > 0) {
                                startActivity(new Intent(this, NewDashboardActivity.class));
                            } else if (this.context != null) {
                                startActivity(new Intent(this, ImportStreamsActivity.class));
                            }
                        } else {
                            onFinish();
                            Toast.makeText(this, getResources().getString(R.string.invalid_status) + status, 0).show();
                        }
                    }
                } else if (loginCallback.getUserLoginInfo() != null && loginCallback.getServerInfo() != null) {
                    onFinish();
                    onFailed(getResources().getString(R.string.invalid_server_response));
                }
            }
        } else if (loginCallback != null && loginCallback.getUserLoginInfo() != null && loginCallback.getServerInfo() != null) {
            if (loginCallback.getUserLoginInfo().getAuth().intValue() == 1) {
                String status2 = loginCallback.getUserLoginInfo().getStatus();
                if (status2.equals("Active")) {
                    String username3 = loginCallback.getUserLoginInfo().getUsername();
                    String password3 = loginCallback.getUserLoginInfo().getPassword();
                    String port2 = loginCallback.getServerInfo().getPort();
                    String url2 = loginCallback.getServerInfo().getUrl();
                    String expDate2 = loginCallback.getUserLoginInfo().getExpDate();
                    String isTrial2 = loginCallback.getUserLoginInfo().getIsTrial();
                    String activeCons2 = loginCallback.getUserLoginInfo().getActiveCons();
                    String createdAt2 = loginCallback.getUserLoginInfo().getCreatedAt();
                    String maxConnections2 = loginCallback.getUserLoginInfo().getMaxConnections();
                    List<String> allowedOutputFormats2 = loginCallback.getUserLoginInfo().getAllowedOutputFormats();
                    if (allowedOutputFormats2.size() != 0) {
                        allowedOutputFormats2.get(0);
                    }
                    SharedPreferences.Editor edit2 = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).edit();
                    edit2.putString("username", username3);
                    edit2.putString("password", password3);
                    edit2.putString(AppConst.LOGIN_PREF_SERVER_PORT, port2);
                    edit2.putString(AppConst.LOGIN_PREF_EXP_DATE, expDate2);
                    edit2.putString(AppConst.LOGIN_PREF_IS_TRIAL, isTrial2);
                    edit2.putString(AppConst.LOGIN_PREF_ACTIVE_CONS, activeCons2);
                    edit2.putString(AppConst.LOGIN_PREF_CREATE_AT, createdAt2);
                    edit2.putString(AppConst.LOGIN_PREF_MAX_CONNECTIONS, maxConnections2);
                    edit2.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, url2);
                    String string6 = this.loginPreferencesSharedPref_multi_dns.getString(AppConst.LOGIN_PREF_MULTI_DNS, "");
                    edit2.putString(AppConst.LOGIN_PREF_SERVER_URL, string6);
                    edit2.commit();
                    this.loginPreferencesSharedPref_allowed_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
                    this.loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
                    this.loginPreferencesSharedPref_epg_channel_update = this.context.getSharedPreferences(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, 0);
                    this.loginPrefsEditor_fomat = this.loginPreferencesSharedPref_allowed_format.edit();
                    this.loginPrefsEditor_timefomat = this.loginPreferencesSharedPref_time_format.edit();
                    this.loginPrefsEditor_epgchannelupdate = this.loginPreferencesSharedPref_epg_channel_update.edit();
                    String string7 = this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
                    if (string7 != null && string7.equals("")) {
                        this.loginPrefsEditor_fomat.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "ts");
                        this.loginPrefsEditor_fomat.commit();
                    }
                    String string8 = this.loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "");
                    if (string8 != null && string8.equals("")) {
                        this.loginPrefsEditor_timefomat.putString(AppConst.LOGIN_PREF_TIME_FORMAT, "HH:mm");
                        this.loginPrefsEditor_timefomat.commit();
                    }
                    String string9 = this.loginPreferencesSharedPref_epg_channel_update.getString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "");
                    if (string9 != null && string9.equals("")) {
                        this.loginPrefsEditor_epgchannelupdate.putString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "withepg");
                        this.loginPrefsEditor_epgchannelupdate.commit();
                    }
                    String string10 = this.loginPreferencesSharedPref_epg_channel_auto_update.getString(AppConst.LOGIN_PREF_AUTOUPDATE, "");
                    if (string10 != null && string10.equals("")) {
                        this.loginPrefsEditor_epgchannelautoupdate.putString(AppConst.LOGIN_PREF_AUTOUPDATE, getResources().getString(R.string.disable));
                        this.loginPrefsEditor_epgchannelautoupdate.commit();
                    }
                    if (this.liveStreamDBHandler != null && this.liveStreamDBHandler.getMagportal(string6) == 0) {
                        this.liveStreamDBHandler.deleteAndRecreateAllTables();
                        this.seriesStreamsDatabaseHandler.deleteAndRecreateAllVSeriesTables();
                        this.liveStreamDBHandler.addMagPortal(string6);
                    }
                    onFinish();
                    if (!Boolean.valueOf(this.multiuserdbhandler.checkregistration(this.name, username3, password3, AppConst.SERVER_URL_FOR_MULTI_USER, AppConst.TYPE_API, url2)).booleanValue()) {
                        this.multiuserdbhandler.addmultiusers(this.name, username3, password3, AppConst.SERVER_URL_FOR_MULTI_USER, url2);
                        Toast.makeText(this, getResources().getString(R.string.user_added), 0).show();
                        if (this.context != null) {
                            this.multiuserdbhandler = new MultiUserDBHandler(this.context);
                            SharepreferenceDBHandler.setUserID(this.multiuserdbhandler.getAutoIdLoggedInUser(this.name, username3, username3, password3, AppConst.SERVER_URL_FOR_MULTI_USER, ""), this.context);
                        }
                        Toast.makeText(this, getResources().getString(R.string.logged_in), 0).show();
                        if (this.context != null && this.liveStreamDBHandler != null && this.liveStreamDBHandler.getAvailableChannelsCount() > 0) {
                            startActivity(new Intent(this, NewDashboardActivity.class));
                        } else if (this.context != null) {
                            startActivity(new Intent(this, ImportStreamsActivity.class));
                        }
                    } else {
                        onFinish();
                        Toast.makeText(this, getResources().getString(R.string.invalid_status) + status2, 0).show();
                    }
                }
            } else if (loginCallback.getUserLoginInfo() != null && loginCallback.getServerInfo() != null) {
                onFinish();
                onFailed(getResources().getString(R.string.invalid_server_response));
            }
        }
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void stopLoaderMultiDNS(ArrayList<String> arrayList, String str) {
        if (this.progressDialog != null && arrayList.size() == 0) {
            this.progressDialog.dismiss();
            Toast.makeText(this, this.context.getResources().getString(R.string.error_code_2) + getResources().getString(R.string.network_error), 0).show();
        }
        if (arrayList.size() > 0) {
            storeServerUrls(arrayList, this.urls);
        }
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void magFailedtoLoginMultiDNS(ArrayList<String> arrayList, ArrayList<String> arrayList2, String str, String str2, String str3) {
        arrayList.size();
        arrayList2.size();
        if (arrayList.size() == 0 && arrayList2.size() == 0) {
            onFinish();
            if (!str.equals("")) {
                Utils.showToast(this.context, str);
            } else {
                Utils.showToast(this.context, "Your Account is invalid or expired !");
            }
        }
        if (arrayList.size() > 0) {
            storeServerUrls(arrayList, this.urls);
        } else if (arrayList.size() == 0 && arrayList2.size() > 0) {
            AppConst.isXTREAM_1_0_6 = false;
            Utils.setXtream1_06(this.context);
            storeServerUrls_panel(arrayList2, this.urls);
        }
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void magFailedtoLoginMultiDNS2(ArrayList<String> arrayList, ArrayList<String> arrayList2, String str, String str2, String str3) {
        int size = arrayList.size() + 1;
        if (arrayList.size() == 0) {
            onFinish();
            if (!str.equals("")) {
                Utils.showToast(this.context, str);
            } else {
                Utils.showToast(this.context, "Your Account is invalid or expired !");
            }
        }
        if (arrayList.size() <= 0) {
            return;
        }
        if (size - 1 == 0) {
            AppConst.isXTREAM_1_0_6 = false;
            Utils.setXtream1_06(this.context);
            storeServerUrls_panel(arrayList, this.urls);
            return;
        }
        storeServerUrls(arrayList, this.urls);
    }

    @Override // com.nst.yourname.view.interfaces.ActivationInterface
    public void validateActiveLogin(ActivationCallBack activationCallBack, String str) {
        this.password = SharepreferenceDBHandler.getUserPassword(this.context);
        checkLoginData();
    }

    @Override // com.nst.yourname.view.interfaces.ActivationInterface
    public void msgFailedtoLogin(String str) {
        if (str != null) {
            onFinish();
            Utils.showToast(this.context, str);
            return;
        }
        onFinish();
        Utils.showToast(this.context, "Your Activation code is not invalid");
    }

    private class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private final View view;

        public OnFocusChangeAccountListener(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            float f = 1.0f;
            if (z) {
                if (z) {
                    f = 1.15f;
                }
                try {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    Log.e("id is", "" + this.view.getTag());
                    if (this.view.getTag().equals("1")) {
                        LoginActivity.this.emailIdET.setSelection(LoginActivity.this.emailIdET.length());
                    } else if (this.view.getTag().equals("2")) {
                        LoginActivity.this.passwordET.setSelection(LoginActivity.this.passwordET.length());
                    } else if (this.view.getTag().equals("3")) {
                        LoginActivity.this.serverURLET.setSelection(LoginActivity.this.serverURLET.length());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (!z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
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

    public void appVersionName() {
        try {
            this.version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void DEviceVersion() {
        this.reqString = Build.VERSION.RELEASE + " " + Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName();
    }

    public static String getDeviceName() {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        if (str2.startsWith(str)) {
            return capitalize(str2);
        }
        return capitalize(str) + " " + str2;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] charArray = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (char c : charArray) {
            if (!z || !Character.isLetter(c)) {
                if (Character.isWhitespace(c)) {
                    z = true;
                }
                sb.append(c);
            } else {
                sb.append(Character.toUpperCase(c));
                z = false;
            }
        }
        return sb.toString();
    }

    public void GetRandomNumber() {
        this.random = new Random().nextInt(8378600) + 10000;
        Globals.RandomNumber = String.valueOf(this.random);
    }

    public static String md5(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                String hexString = Integer.toHexString(b & -1);
                while (hexString.length() < 2) {
                    hexString = AppConst.PASSWORD_UNSET + hexString;
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean checkFields() {
        if (AppConst.MultiDNS_And_MultiUser.booleanValue() && this.etName.getText().toString().trim().length() == 0) {
            this.etName.requestFocus();
            this.etName.setError(getResources().getString(R.string.enter_any_name));
            return false;
        } else if (this.emailIdET.getText().toString().trim().length() == 0) {
            this.emailIdET.requestFocus();
            if (AppConst.CODE_ACTIVATOR.booleanValue()) {
                this.emailIdET.setError(getResources().getString(R.string.enter_act_code));
            } else {
                this.emailIdET.setError(getResources().getString(R.string.enter_username_error));
            }
            return false;
        } else if (AppConst.CODE_ACTIVATOR.booleanValue() || this.passwordET.getText().toString().trim().length() != 0) {
            return true;
        } else {
            this.passwordET.requestFocus();
            this.passwordET.setError(getResources().getString(R.string.enter_password_error));
            return false;
        }
    }

    public void SequrityApi() {
        this.FirstMdkey = md5(RavSharedPrefrences.get_clientkey(this) + "*" + RavSharedPrefrences.get_salt(this) + "-" + this.username + "-" + Globals.RandomNumber + "-" + this.version + "-unknown-" + getDeviceName() + "-" + this.reqString);
        Webservices.getterList = new ArrayList();
        Webservices.getterList.add(Webservices.P("m", "gu"));
        Webservices.getterList.add(Webservices.P("k", RavSharedPrefrences.get_clientkey(this)));
        Webservices.getterList.add(Webservices.P("sc", this.FirstMdkey));
        Webservices.getterList.add(Webservices.P("u", this.username));
        Webservices.getterList.add(Webservices.P("pw", "no_password"));
        Webservices.getterList.add(Webservices.P("r", Globals.RandomNumber));
        Webservices.getterList.add(Webservices.P("av", this.version));
        Webservices.getterList.add(Webservices.P("dt", "unknown"));
        Webservices.getterList.add(Webservices.P("d", getDeviceName()));
        Webservices.getterList.add(Webservices.P("do", this.reqString));
        Webservices.getWebservices.SequrityLink(this);
    }

    public void onPostSuccess(String str, int i, boolean z) {
        Log.e("result post Success ><><><>", "" + i);
        if (!z) {
            onFinish();
            Toast.makeText(this, this.context.getResources().getString(R.string.could_not_connect), 0).show();
        } else if (i == 1) {
            try {
                Globals.jsonObj = new JSONObject(str);
                if (Globals.jsonObj.getString(NotificationCompat.CATEGORY_STATUS).equalsIgnoreCase("true")) {
                    AppConst.ACTIVE_STATUS = true;
                    if (AppConst.CHECK_STATUS.booleanValue()) {
                        Log.e("result post Success == true ><><><>", "" + AppConst.CHECK_STATUS.booleanValue());
                        this.urls = Globals.jsonObj.getString("su");
                        Log.e("result SU ><><><>", "" + urls);
                        isVodApp = Globals.jsonObj.getString ("isVod");
                        resellerInfo =Globals.jsonObj.getString ("reseller_email");
                        selectedTheme = Globals.jsonObj.getString ("theme");

                        Log.e("result VOD? ><><><>", "" + isVodApp);
                        this.multiuserdbhandler.saveLoginData(this.urls, this.currentDate1);
                        this.multiuserdbhandler.addAppInfos (this.resellerInfo,this.isVodApp,this.selectedTheme);
                        //this.multiuserdbhandler.saveLoginDataMods (this.urls,this.currentDate1,this.isVodApp);
                        AppConst.CHECK_STATUS = false;
                        if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
                            this.emailIdET.setText("");
                            this.passwordET.setText("");
                            this.etName.setText("");
                        } else {
                            this.emailIdET.setText(this.loginPreferencesRemember.getString("username", ""));
                            this.passwordET.setText(this.loginPreferencesRemember.getString("password", ""));
                        }
                        loginCheck();
                        return;
                    }
                    this.urls = Globals.jsonObj.getString("su");
                    this.nextDueDate = Globals.jsonObj.getString("ndd");
                    this.currentDate = System.currentTimeMillis();
                    if (!AppConst.REFRESH_BUTTON.booleanValue()) {
                        try {
                            this.upgradeDate = getNextDueDateTimeStamp(this.nextDueDate + " 00:00:00.000");
                            if (this.upgradeDate == -1 || this.loginPreferencesUpgradeDate == null) {
                                this.loginPrefsEditorUpgaradeDate.putLong(AppConst.LOGIN_PREF_UPGRADE_DATE, -1);
                                this.loginPrefsEditorUpgaradeDate.apply();
                            } else {
                                this.loginPrefsEditorUpgaradeDate.putLong(AppConst.LOGIN_PREF_UPGRADE_DATE, this.upgradeDate);
                                this.loginPrefsEditorUpgaradeDate.apply();
                            }
                            if (this.loginPreferences != null) {
                                this.savedUsername = this.loginPreferences.getString("username", "");
                                this.savedUrl = this.loginPreferences.getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "");
                            }
                            if (this.loginPreferencesRemember != null && this.cbRememberMe.isChecked()) {
                                this.username = this.loginPreferencesRemember.getString("username", "");
                                this.password = this.loginPreferencesRemember.getString("password", "");
                            } else if (AppConst.CODE_ACTIVATOR.booleanValue()) {
                                this.username = SharepreferenceDBHandler.getUserName(this.context);
                                this.password = SharepreferenceDBHandler.getUserPassword(this.context);
                            } else {
                                this.username = this.emailIdET.getText().toString().trim();
                                this.password = this.passwordET.getText().toString().trim();
                            }
                            if (!this.savedUrl.equals("")) {
                                this.newUsername = this.emailIdET.getText().toString().trim();
                                this.newUrl = Globals.jsonObj.optString("su");
                                Log.e("JSON","Response serverURL new " + newUrl);
                                if (!this.savedUsername.equals(this.newUsername) || !this.savedUrl.equals(this.newUrl)) {
                                    if (this.liveStreamDBHandler != null) {
                                        this.liveStreamDBHandler.deleteAndRecreateAllTables();
                                    }
                                    DatabaseHandler databaseHandler = this.favDBHandler;
                                    if (this.seriesStreamsDatabaseHandler != null) {
                                        this.seriesStreamsDatabaseHandler.deleteAndRecreateAllVSeriesTables();
                                    }
                                }
                            }
                            RavSharedPrefrences.set_authurl(this, Globals.jsonObj.optString("su"));
                            this.SecondMdkey = md5(Globals.jsonObj.optString("su") + "*" + RavSharedPrefrences.get_salt(this) + "*" + Globals.RandomNumber);
                            if (Globals.jsonObj.getString("sc").equalsIgnoreCase(this.SecondMdkey)) {
                                this.loginPreferencesServerURlPut.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, RavSharedPrefrences.get_authurl(this));
                                this.loginPreferencesServerURlPut.apply();
                                this.loginPreferencesEditor.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, RavSharedPrefrences.get_authurl(this));
                                this.loginPreferencesEditor.putString("username", this.username);
                                this.loginPreferencesEditor.apply();
                                if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
                                    urlCountValue = 0;
                                    String str2 = RavSharedPrefrences.get_authurl(this);
                                    this.serverList = new ArrayList<>(Arrays.asList(str2.substring(1, str2.length() - 1).replaceAll("\"", "").split(",")));
                                    Log.e("JSON","Response serverURL " + serverUrl);
                                    storeServerUrls(this.serverList, this.urls.toLowerCase());
                                } else if (Utils.getIsXtream1_06(this.context)) {
                                    try {
                                        this.loginPresenter.validateLoginUsingPanelAPI(this.username, this.password);
                                    } catch (Exception unused) {
                                    }
                                } else {
                                    this.loginPresenter.validateLogin(this.username, this.password);
                                }
                            } else {
                                onFinish();
                                Toast.makeText(this, this.context.getResources().getString(R.string.could_not_connect), 0).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        AppConst.REFRESH_BUTTON = false;
                        this.multiuserdbhandler.deleteSaveLogin();
                        RavSharedPrefrences.set_authurl(this, Globals.jsonObj.optString("su"));
                        this.SecondMdkey = md5(Globals.jsonObj.optString("su") + "*" + RavSharedPrefrences.get_salt(this) + "*" + Globals.RandomNumber);

                        this.loginPreferencesServerURl = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0);
                        this.loginPreferencesServerURlPut = this.loginPreferencesServerURl.edit();
                        this.loginPreferencesServerURlPut.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, RavSharedPrefrences.get_authurl(this));
                        this.loginPreferencesServerURlPut.apply();
                        this.multiuserdbhandler.saveLoginData(RavSharedPrefrences.get_authurl(this), currentDateValue());
                        if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
                            urlCountValue = 0;
                            String str3 = RavSharedPrefrences.get_authurl(this);
                            this.serverList = new ArrayList<>(Arrays.asList(str3.substring(1, str3.length() - 1).replaceAll("\"", "").split(",")));
                        }
                        onFinish();
                        Toast.makeText(this, this.context.getResources().getString(R.string.refresh_dns_sucess), 0).show();
                    }
                } else {
                    AppConst.REFRESH_BUTTON = false;
                    AppConst.CHECK_STATUS = false;
                    AppConst.ACTIVE_STATUS = false;
                    this.multiuserdbhandler.deleteSaveLogin();
                    onFinish();
                    Toast.makeText(this, this.context.getResources().getString(R.string.status_suspend), 0).show();
                }
            } catch (Exception e2) {
                Log.e("Login check", e2.getMessage());
            }
        }
    }

    @Override // com.nst.yourname.WebServiceHandler.MainAsynListener
    public void onPostError(int i) {
        if (this.context != null) {
            onFinish();
            Toast.makeText(this, this.context.getResources().getString(R.string.could_not_connect), 0).show();
        }
    }

    public long getNextDueDateTimeStamp(String str) throws Exception {
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return simpleDateFormat2.parse(str).getTime() + TimeChart.DAY;
        } catch (ParseException e) {
            e.printStackTrace();
            return Long.parseLong(String.valueOf(-1));
        }
    }

    private String currentDateValue() {
        return Utils.parseDateToddMMyyyy(Calendar.getInstance().getTime().toString());
    }

    public static long getDateDiff(SimpleDateFormat simpleDateFormat2, String str, String str2) {
        try {
            return TimeUnit.DAYS.convert(simpleDateFormat2.parse(str2).getTime() - simpleDateFormat2.parse(str).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void storeServerUrls(ArrayList<String> arrayList, String str) {
        if (this.liveStreamDBHandler != null) {
            this.liveStreamDBHandler.deleteAndRecreateAllTables();
        }
        if (this.seriesStreamsDatabaseHandler != null) {
            this.seriesStreamsDatabaseHandler.deleteAndRecreateAllVSeriesTables();
        }
        if (str != null && !str.equals("") && !str.isEmpty()) {
            if (urlCountValue == 0) {
                urlCountValue++;
                this.serverList = new ArrayList<>(Arrays.asList(str.split(",")));
                this.serverList_panel = new ArrayList<>(Arrays.asList(str.split(",")));
            } else {
                this.serverList = arrayList;
            }
        }
        if (this.serverList != null && this.serverList.size() >= 2) {
            this.loginPreferencesServerURlPut.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, this.serverList.get(0).trim());
            this.loginPreferencesServerURlPut.commit();
            this.serverList.remove(0);
            if (Utils.getIsXtream1_06(this.context)) {
                try {
                    this.loginPresenter.validateLoginMultiDnsUsingPanelapi(this.username, this.password, this.serverList, this.serverList_panel);
                } catch (Exception unused) {
                }
            } else {
                try {
                    this.loginPresenter.validateLoginMultiDns(this.username, this.password, this.serverList, this.serverList_panel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (this.serverList != null && this.serverList.size() == 1) {
            this.loginPreferencesServerURlPut.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, this.serverList.get(0).trim());
            this.loginPreferencesServerURlPut.commit();
            this.serverList.remove(0);
            if (Utils.getIsXtream1_06(this.context)) {
                try {
                    this.loginPresenter.validateLoginMultiDnsUsingPanelapi(this.username, this.password, this.serverList, this.serverList_panel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    this.loginPresenter.validateLoginMultiDns(this.username, this.password, this.serverList, this.serverList_panel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (this.serverList != null && this.serverList.size() == 0) {
            onFinish();
            urlCountValue = 0;
            Toast.makeText(this, this.context.getResources().getString(R.string.please_check_portal), 0).show();
        }
    }

    private void storeServerUrls_panel(ArrayList<String> arrayList, String str) {
        if (this.liveStreamDBHandler != null) {
            this.liveStreamDBHandler.deleteAndRecreateAllTables();
        }
        if (this.seriesStreamsDatabaseHandler != null) {
            this.seriesStreamsDatabaseHandler.deleteAndRecreateAllVSeriesTables();
        }
        if (str != null && !str.equals("") && !str.isEmpty()) {
            if (urlCountValue == 0) {
                urlCountValue++;
                this.serverList_panel = new ArrayList<>(Arrays.asList(str.split(",")));
            } else {
                this.serverList_panel = arrayList;
            }
        }
        if (this.serverList_panel != null && this.serverList_panel.size() >= 2) {
            this.loginPreferencesServerURlPut.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, this.serverList_panel.get(0).trim());
            this.loginPreferencesServerURlPut.commit();
            this.serverList_panel.remove(0);
            if (Utils.getIsXtream1_06(this.context)) {
                try {
                    this.loginPresenter.validateLoginMultiDnsUsingPanelapi(this.username, this.password, this.serverList, this.serverList_panel);
                } catch (Exception unused) {
                }
            } else {
                try {
                    this.loginPresenter.validateLoginMultiDns(this.username, this.password, this.serverList_panel, this.serverList_panel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (this.serverList_panel != null && this.serverList_panel.size() == 1) {
            this.loginPreferencesServerURlPut.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, this.serverList_panel.get(0).trim());
            this.loginPreferencesServerURlPut.commit();
            this.serverList_panel.remove(0);
            if (Utils.getIsXtream1_06(this.context)) {
                try {
                    this.loginPresenter.validateLoginMultiDnsUsingPanelapi(this.username, this.password, this.serverList, this.serverList_panel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    this.loginPresenter.validateLoginMultiDns(this.username, this.password, this.serverList, this.serverList_panel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (this.serverList_panel != null && this.serverList_panel.size() == 0) {
            onFinish();
            urlCountValue = 0;
            Toast.makeText(this, this.context.getResources().getString(R.string.please_check_portal), 0).show();
        }
    }

    public void checkLoginData() {
        if (this.multiuserdbhandler.getSaveLoginDate() == null || this.multiuserdbhandler.getSaveLoginDate().size() == 0) {
            SequrityApi();
            return;
        }
        ArrayList<MultiUserDBModel> saveLoginDate = this.multiuserdbhandler.getSaveLoginDate();
        if (saveLoginDate.get(0).getDate() != null) {
            String date = saveLoginDate.get(0).getDate();
            if (date != null) {
                this.dateDifference = getDateDiff(this.simpleDateFormat, date, this.currentDate1);
            }
            String serverUrl2 = saveLoginDate.get(0).getServerUrl();
            if (!this.cbRememberMe.isChecked()) {
                if (AppConst.CODE_ACTIVATOR.booleanValue()) {
                    SharepreferenceDBHandler.setActivationCode(this.context, "");
                }
                this.loginPrefsEditorBeforeLogin.clear();
                this.loginPrefsEditorBeforeLogin.apply();
            } else if (AppConst.CODE_ACTIVATOR.booleanValue()) {
                SharepreferenceDBHandler.setActivationCode(this.context, this.username);
                this.loginPrefsEditorBeforeLogin.putString(AppConst.PREF_LOGIN_WITH, AppConst.LOGIN_WITH_DETAILS);
                this.loginPrefsEditorBeforeLogin.apply();
            } else {
                this.loginPrefsEditorBeforeLogin.putString("username", this.username);
                this.loginPrefsEditorBeforeLogin.putString("password", this.password);
            }
            this.loginPrefsEditorBeforeLogin.apply();
            this.loginPreferencesServerURl = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0);
            this.loginPreferencesServerURlPut = this.loginPreferencesServerURl.edit();
            this.loginPreferencesServerURlPut.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, serverUrl2);
            this.loginPreferencesServerURlPut.apply();
            if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
                urlCountValue = 0;
                storeServerUrls(this.serverList, serverUrl2.toLowerCase());
            } else if (AppConst.GET_FREETRIAL.booleanValue()) {
                new Handler().postDelayed(new Runnable() {
                    /* class com.nst.yourname.view.activity.LoginActivity.AnonymousClass9 */

                    public void run() {
                        try {
                            if (Utils.getIsXtream1_06(LoginActivity.this.context)) {
                                LoginActivity.this.loginPresenter.validateLoginUsingPanelAPI(LoginActivity.this.username, LoginActivity.this.password);
                            } else {
                                LoginActivity.this.loginPresenter.validateLogin(LoginActivity.this.username, LoginActivity.this.password);
                            }
                        } catch (Exception unused) {
                        }
                    }
                }, 1000);
            } else {
                if (AppConst.CODE_ACTIVATOR.booleanValue()) {
                    this.username = SharepreferenceDBHandler.getUserName(this.context);
                    this.password = SharepreferenceDBHandler.getUserPassword(this.context);
                }
                if (Utils.getIsXtream1_06(this.context)) {
                    try {
                        this.loginPresenter.validateLoginUsingPanelAPI(this.username, this.password);
                    } catch (Exception unused) {
                    }
                } else {
                    try {
                        this.loginPresenter.validateLogin(this.username, this.password);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            SequrityApi();
        }
    }

    public void checkLoginStatus() {
        if (this.multiuserdbhandler != null && this.multiuserdbhandler.getSaveLoginDate() != null) {
            ArrayList<MultiUserDBModel> saveLoginDate = this.multiuserdbhandler.getSaveLoginDate();
            if (saveLoginDate.size() == 0) {
                AppConst.CHECK_STATUS = true;
                SequrityApi();
            } else if (saveLoginDate.get(0).getDate() != null) {
                String date = saveLoginDate.get(0).getDate();
                String serverUrl2 = saveLoginDate.get(0).getServerUrl();
                this.dateDifference = getDateDiff(this.simpleDateFormat, date, this.currentDate1);
                if (this.dateDifference > 14) {
                    this.multiuserdbhandler.deleteSaveLogin();
                    this.multiuserdbhandler.saveLoginData(serverUrl2, this.currentDate1);
                    AppConst.CHECK_STATUS = true;
                    atStart();
                    SequrityApi();
                    return;
                }
                AppConst.ACTIVE_STATUS = true;
                this.action = getIntent().getAction();
                if (this.action == null || !this.action.equalsIgnoreCase(AppConstant.ACTION_LOGIN_PERFORM)) {
                    loginCheck();
                }
            }
        }
    }
}
