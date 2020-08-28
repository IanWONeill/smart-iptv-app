package com.nst.yourname.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.nst.yourname.R;
import com.nst.yourname.WebServiceHandler.Globals;
import com.nst.yourname.WebServiceHandler.MainAsynListener;
import com.nst.yourname.WebServiceHandler.RavSharedPrefrences;
import com.nst.yourname.WebServiceHandler.Webservices;
import com.nst.yourname.miscelleneious.FileChooserDialog;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.MultiUserDBModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.MultiUserDBHandler;
import com.nst.yourname.model.database.SeriesStreamsDatabaseHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.muparse.M3UParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.achartengine.chart.TimeChart;
import org.json.JSONObject;

@SuppressWarnings("ALL")
public class LoginM3uActivity extends AppCompatActivity implements MainAsynListener<String> {
    static final File DEFA = Environment.getExternalStorageDirectory();
    private static final String EXT_INF = "#EXTINF";
    private static final String EXT_M3U = "#EXTM3U";
    private static final String EXT_URL = "http://";
    private static final String EXT_URL_HTTPS = "https://";
    public static final File dir = new File(DEFA.getPath() + "/IPTVSmartersM3u");
    String FirstMdkey;
    String SecondMdkey;
    String action;
    public String anyName = "";
    @BindView(R.id.bt_browse)
    Button bt_browse;
    @BindView(R.id.import_m3u)
    Button bt_import_m3u;
    public Context context = this;
    long currentDate = -1;
    String currentDate1 = currentDateValue();
    long dateDifference = 0;
    @BindView(R.id.et_import_m3u)
    EditText etM3uLine;
    @BindView(R.id.et_import_m3u_file)
    EditText etM3uLineFile;
    @BindView(R.id.et_name)
    EditText etName;
    private DatabaseHandler favDBHandler;
    Handler handler;
    InputStream is;
    String key;
    private LiveStreamDBHandler liveStreamDBHandler;
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
    private SharedPreferences loginPreferencesSharedPref_epg_channel_update;
    private SharedPreferences loginPreferencesSharedPref_multi_dns;
    private SharedPreferences loginPreferencesSharedPref_multi_dns_valid;
    private SharedPreferences loginPreferencesSharedPref_time_format;
    private SharedPreferences loginPreferencesUpgradeDate;
    private SharedPreferences.Editor loginPrefsEditorAutoStart;
    private SharedPreferences.Editor loginPrefsEditorBeforeLogin;
    private SharedPreferences.Editor loginPrefsEditorChannels;
    private SharedPreferences.Editor loginPrefsEditorEPG;
    private SharedPreferences.Editor loginPrefsEditorUpgaradeDate;
    private SharedPreferences.Editor loginPrefsEditor_epgchannelupdate;
    private SharedPreferences.Editor loginPrefsEditor_fomat;
    private SharedPreferences.Editor loginPrefsEditor_multi_dns_valid;
    private SharedPreferences.Editor loginPrefsEditor_timefomat;
    private String loginWith;
    public String m3uline;
    public String m3ulineFile;
    String model = Build.MODEL;
    public MultiUserDBHandler multiuserdbhandler;
    String newUrl = "";
    String newUsername = "";
    String nextDueDate = "";
    final M3UParser parser = new M3UParser();
    private String password;
    private ProgressDialog progressDialog;
    private RadioGroup radioGroup;
    int random;
    @BindView(R.id.rb_file)
    RadioButton rbFile;
    @BindView(R.id.rb_m3u)
    RadioButton rbM3U;
    String reqString;
    @BindView(R.id.rl_bt_refresh)
    Button rl_bt_refresh;
    @BindView(R.id.rl_playlist_name)
    RelativeLayout rl_playlist_name;
    @BindView(R.id.rl_view_log)
    Button rl_view_log;
    String salt;
    private Boolean saveLogin;
    String savedUrl = "";
    String savedUsername = "";
    private String selected_language = "";
    private SeriesStreamsDatabaseHandler seriesStreamsDatabaseHandler;
    private String serverUrl;
    private SharedPreferences sharedPrefMultiDNSServe;
    private SharedPreferences.Editor sharedPrefMultiDNSServeEditor;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    @BindView(R.id.tv_browse_error)
    TextView tv_browse_error;
    @BindView(R.id.tv_file_path)
    TextView tv_file_path;
    long upgradeDate = -1;
    String urls = "";
    private String username;
    String version;
    TextView viewLog;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    @SuppressLint({"RtlHardcoded"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_login_m3u);
        ButterKnife.bind(this);
        isStoragePermissionGranted();
        if (AppConst.M3U_LINE_ACTIVE.booleanValue()) {
            Webservices.getWebservices = new Webservices(this);
            appVersionName();
            DEviceVersion();
            getDeviceName();
            GetRandomNumber();
        }
        initialize();
        changeStatusBarColor();
    }

    private void initialize() {
        try {
            this.context = this;
            if (AppConst.M3U_API_SINGLE_USER.booleanValue()) {
                this.rl_playlist_name.setVisibility(8);
            } else {
                this.rl_playlist_name.setVisibility(0);
            }
            this.multiuserdbhandler = new MultiUserDBHandler(this.context);
            this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
            this.favDBHandler = new DatabaseHandler(this.context);
            this.seriesStreamsDatabaseHandler = new SeriesStreamsDatabaseHandler(this.context);
            this.radioGroup = (RadioGroup) findViewById(R.id.rg_radio);
            this.bt_import_m3u.setNextFocusUpId(R.id.bt_browse);
            this.loginPreferencesSharedPref_allowed_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
            this.bt_browse.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.bt_browse));
            if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
                this.rl_view_log.setVisibility(0);
            } else {
                this.rl_view_log.setVisibility(8);
                this.bt_import_m3u.setText(getResources().getString(R.string.login_user));
            }
            this.rl_view_log.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.LoginM3uActivity.AnonymousClass1 */

                public void onClick(View view) {
                    Intent intent = new Intent(LoginM3uActivity.this.context, MultiUserActivity.class);
                    SharepreferenceDBHandler.setCurrentAPPType(AppConst.TYPE_M3U, LoginM3uActivity.this.context);
                    LoginM3uActivity.this.startActivity(intent);
                    LoginM3uActivity.this.overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                    LoginM3uActivity.this.finish();
                }
            });
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
            this.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                /* class com.nst.yourname.view.activity.LoginM3uActivity.AnonymousClass2 */

                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (i == R.id.rb_file) {
                        LoginM3uActivity.this.bt_import_m3u.setNextFocusUpId(R.id.bt_browse);
                    } else {
                        LoginM3uActivity.this.bt_import_m3u.setNextFocusUpId(R.id.et_import_m3u);
                    }
                }
            });
            this.rl_bt_refresh.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.LoginM3uActivity.AnonymousClass3 */

                public void onClick(View view) {
                    LoginM3uActivity.this.atStart();
                    AppConst.CHECK_STATUS = true;
                    LoginM3uActivity.this.SequrityApi();
                }
            });
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
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
                    f = 1.1f;
                }
                try {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    Log.e("id is", "" + this.view.getTag());
                    if (this.view.getTag().equals("3")) {
                        performScaleXAnimation(f);
                        performScaleYAnimation(f);
                        view2.setBackgroundResource(R.drawable.blue_btn_effect);
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
                if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("3")) {
                    view2.setBackgroundResource(R.drawable.logout_btn);
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
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        Utils.appResume(this.context);
    }

    @OnClick({R.id.import_m3u, R.id.bt_browse})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id != R.id.bt_browse) {
            if (id == R.id.import_m3u) {
                if (isStoragePermissionGranted()) {
                    int checkedRadioButtonId = this.radioGroup.getCheckedRadioButtonId();
                    if (checkedRadioButtonId == R.id.rb_file && checkFieldsFile()) {
                        if (AppConst.M3U_LINE_ACTIVE.booleanValue()) {
                            atStart();
                            if (!AppConst.M3U_API_SINGLE_USER.booleanValue()) {
                                this.anyName = this.etName.getText().toString().trim();
                            }
                            this.m3ulineFile = this.etM3uLineFile.getText().toString().trim();
                            checkLoginStatus();
                        } else {
                            atStart();
                            if (!AppConst.M3U_API_SINGLE_USER.booleanValue()) {
                                this.anyName = this.etName.getText().toString().trim();
                            }
                            this.m3ulineFile = this.etM3uLineFile.getText().toString().trim();
                            new _loadFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.m3ulineFile);
                        }
                    }
                    if (checkedRadioButtonId == R.id.rb_m3u && checkFieldsM3U()) {
                        if (AppConst.M3U_LINE_ACTIVE.booleanValue()) {
                            atStart();
                            if (!AppConst.M3U_API_SINGLE_USER.booleanValue()) {
                                this.anyName = this.etName.getText().toString().trim();
                            }
                            this.m3uline = this.etM3uLine.getText().toString().trim();
                            checkLoginStatus();
                            return;
                        }
                        atStart();
                        this.m3uline = this.etM3uLine.getText().toString().trim();
                        if (!AppConst.M3U_API_SINGLE_USER.booleanValue()) {
                            this.anyName = this.etName.getText().toString().trim();
                        }
                        new _checkNetworkAvailable().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.m3uline);
                        return;
                    }
                    return;
                }
                Toast.makeText(this.context, this.context.getResources().getString(R.string.permission_is_reqd), 1).show();
            }
        } else if (isStoragePermissionGranted()) {
            chooseM3UFile();
        } else {
            Toast.makeText(this.context, this.context.getResources().getString(R.string.permission_is_reqd), 1).show();
        }
    }

    public void SequrityApi() {
        this.FirstMdkey = md5(RavSharedPrefrences.get_clientkey(this) + "*" + RavSharedPrefrences.get_salt(this) + "-" + "playlist" + "-" + Globals.RandomNumber + "-" + this.version + "-unknown-" + getDeviceName() + "-" + this.reqString);
        Webservices.getterList = new ArrayList();
        Webservices.getterList.add(Webservices.P("m", "gu"));
        Webservices.getterList.add(Webservices.P("k", RavSharedPrefrences.get_clientkey(this)));
        Webservices.getterList.add(Webservices.P("sc", this.FirstMdkey));
        Webservices.getterList.add(Webservices.P("u", "playlist"));
        Webservices.getterList.add(Webservices.P("pw", "no_password"));
        Webservices.getterList.add(Webservices.P("r", Globals.RandomNumber));
        Webservices.getterList.add(Webservices.P("av", this.version));
        Webservices.getterList.add(Webservices.P("dt", "unknown"));
        Webservices.getterList.add(Webservices.P("d", getDeviceName()));
        Webservices.getterList.add(Webservices.P("do", this.reqString));
        Webservices.getWebservices.SequrityLink(this);
    }

    public void atStart() {
        Utils.showProgressDialog(this);
    }

    @SuppressLint({"StaticFieldLeak"})
    class _loadFile extends AsyncTask<String, Void, String> {
        _loadFile() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public String doInBackground(String... strArr) {
            try {
                LoginM3uActivity.this.is = new FileInputStream(new File(strArr[0]));
                return LoginM3uActivity.this.parser.parseFileForM3uFile(LoginM3uActivity.this.is, LoginM3uActivity.this.context);
            } catch (Exception unused) {
                return "";
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:23:0x0094 A[Catch:{ Exception -> 0x0181 }] */
        /* JADX WARNING: Removed duplicated region for block: B:31:0x013c A[Catch:{ Exception -> 0x0181 }] */
        public void onPostExecute(String str) {
            //ToDo: initialized...
            boolean z = false;
            super.onPostExecute((String) str);
            try {
                new ArrayList();
                if (str.equals("") || LoginM3uActivity.this.urls == null || LoginM3uActivity.this.urls.isEmpty()) {
                    LoginM3uActivity.this.onFinish();
                    Toast.makeText(LoginM3uActivity.this.context, LoginM3uActivity.this.context.getResources().getString(R.string.unable_to_add_user), 1).show();
                    return;
                }
                ArrayList arrayList = new ArrayList(Arrays.asList(LoginM3uActivity.this.urls.split(",")));
                if (arrayList.size() > 0) {
                    int i = 0;
                    while (true) {
                        if (i >= arrayList.size()) {
                            break;
                        }
                        String str2 = str.split("/")[2];
                        if (str2.contains(":")) {
                            str2 = str2.substring(0, str2.lastIndexOf(":"));
                        }
                        if (((String) arrayList.get(i)).contains(str2)) {
                            z = true;
                            break;
                        }
                        i++;
                    }
                    if (!z) {
                        boolean checkregistration = LoginM3uActivity.this.multiuserdbhandler.checkregistration(LoginM3uActivity.this.anyName, "playlist", "playlist", LoginM3uActivity.this.m3ulineFile, AppConst.TYPE_M3U, "");
                        if (AppConst.M3U_API_SINGLE_USER.booleanValue()) {
                            if (!checkregistration) {
                                LoginM3uActivity.this.multiuserdbhandler.addmultiusersM3U(LoginM3uActivity.this.anyName, "playlist", "playlist", LoginM3uActivity.this.m3ulineFile, AppConst.TYPE_M3U_FILE);
                            }
                            LoginM3uActivity.this.loadM3U();
                            return;
                        } else if (!checkregistration) {
                            LoginM3uActivity.this.multiuserdbhandler.addmultiusersM3U(LoginM3uActivity.this.anyName, "playlist", "playlist", LoginM3uActivity.this.m3ulineFile, AppConst.TYPE_M3U_FILE);
                            LoginM3uActivity.this.loadM3U();
                            return;
                        } else {
                            LoginM3uActivity.this.onFinish();
                            Context access$000 = LoginM3uActivity.this.context;
                            Toast.makeText(access$000, LoginM3uActivity.this.getResources().getString(R.string.already_exist_with_name) + " " + LoginM3uActivity.this.anyName, 0).show();
                            return;
                        }
                    } else {
                        LoginM3uActivity.this.onFinish();
                        Toast.makeText(LoginM3uActivity.this.context, LoginM3uActivity.this.context.getResources().getString(R.string.please_add_correct), 1).show();
                        return;
                    }
                } else {
                    LoginM3uActivity.this.onFinish();
                    Toast.makeText(LoginM3uActivity.this.context, LoginM3uActivity.this.context.getResources().getString(R.string.please_add_correct), 1).show();
                }
                z = false;
                if (!z) {
                }
            } catch (Exception e) {
                Log.e("gknsadg", "gnjsdg" + e);
            }
        }
    }

    public void loadM3U() {
        String str;
        SharedPreferences.Editor edit = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).edit();
        edit.putString("username", "playlist");
        edit.putString("password", "playlist");
        edit.putString(AppConst.LOGIN_PREF_SERVER_PORT, "");
        edit.putString(AppConst.LOGIN_PREF_SERVER_URL, this.m3ulineFile);
        edit.putString(AppConst.LOGIN_PREF_SERVER_M3U_LINE, this.m3ulineFile);
        edit.putString(AppConst.LOGIN_PREF_ANY_NAME, this.anyName);
        edit.apply();
        if (AppConst.M3U_API_SINGLE_USER.booleanValue()) {
            SharedPreferences sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0);
            SharedPreferences.Editor edit2 = sharedPreferences.edit();
            str = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "");
            edit2.putString("name", this.anyName);
            edit2.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, this.m3ulineFile);
            edit2.apply();
        } else {
            str = null;
        }
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
        this.sharedPrefMultiDNSServe = getSharedPreferences(AppConst.LOGIN_PREF_SERVER_URL_DNS, 0);
        if (this.loginPreferencesAfterLoginChannels.getString(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, "").equals("")) {
            this.loginPrefsEditorChannels.putString(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, "checked");
            this.loginPrefsEditorChannels.apply();
        }
        if (this.loginPreferencesAfterLoginEPG.getString(AppConst.LOGIN_PREF_AUTOMATION_EPG, "").equals("")) {
            this.loginPrefsEditorEPG.putString(AppConst.LOGIN_PREF_AUTOMATION_EPG, "checked");
            this.loginPrefsEditorEPG.apply();
        }
        if (this.loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "").equals("")) {
            this.loginPrefsEditor_timefomat.putString(AppConst.LOGIN_PREF_TIME_FORMAT, "HH:mm");
            this.loginPrefsEditor_timefomat.apply();
        }
        if (this.loginPreferencesSharedPref_epg_channel_update.getString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "").equals("")) {
            this.loginPrefsEditor_epgchannelupdate.putString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "all");
            this.loginPrefsEditor_epgchannelupdate.apply();
        }
        this.loginPreferencesRemember = this.context.getSharedPreferences(AppConst.SHARED_PREFERENCE_REMEBER_ME, 0);
        this.loginPrefsEditorBeforeLogin = this.loginPreferencesRemember.edit();
        if (!AppConst.M3U_LINE_ACTIVE.booleanValue() || !AppConst.M3U_API_SINGLE_USER.booleanValue()) {
            this.loginPrefsEditorBeforeLogin.putBoolean(AppConst.PREF_SAVE_LOGIN, false);
        } else {
            this.loginPrefsEditorBeforeLogin.putBoolean(AppConst.PREF_SAVE_LOGIN, true);
        }
        this.loginPrefsEditorBeforeLogin.putString("username", "playlist");
        this.loginPrefsEditorBeforeLogin.putString("password", "playlist");
        this.loginPrefsEditorBeforeLogin.putString(AppConst.LOGIN_PREF_SERVER_PORT, "");
        this.loginPrefsEditorBeforeLogin.putString(AppConst.LOGIN_PREF_SERVER_URL, this.m3ulineFile);
        this.loginPrefsEditorBeforeLogin.putString(AppConst.LOGIN_PREF_SERVER_M3U_LINE, this.m3ulineFile);
        this.loginPrefsEditorBeforeLogin.putString(AppConst.LOGIN_PREF_ANY_NAME, this.anyName);
        this.loginPrefsEditorBeforeLogin.apply();
        SharepreferenceDBHandler.setCurrentAPPType(AppConst.TYPE_M3U, this.context);
        onFinish();
        if (!AppConst.M3U_LINE_ACTIVE.booleanValue() || !AppConst.M3U_API_SINGLE_USER.booleanValue()) {
            Toast.makeText(this.context, getResources().getString(R.string.user_added), 0).show();
            startActivity(new Intent(this.context, MultiUserActivity.class));
            finish();
            return;
        }
        Toast.makeText(this.context, getResources().getString(R.string.logged_in), 0).show();
        SharepreferenceDBHandler.setUserID(this.multiuserdbhandler.getAutoIdLoggedInUser(this.anyName, "playlist", "playlist", this.m3ulineFile, AppConst.TYPE_M3U, ""), this.context);
        if (this.context == null || !this.m3ulineFile.equals(str)) {
            startActivity(new Intent(this.context, ImportM3uActivity.class));
            finish();
            return;
        }
        startActivity(new Intent(this.context, NewDashboardActivity.class));
        finish();
    }

    public void chooseM3UFile() {
        final String[] strArr = {""};
        new FileChooserDialog(this.context, new FileChooserDialog.ChosenDirectoryListener() {
            /* class com.nst.yourname.view.activity.LoginM3uActivity.AnonymousClass4 */

            @Override // com.nst.yourname.miscelleneious.FileChooserDialog.ChosenDirectoryListener
            public void onChosenDir(String str) {
                strArr[0] = str;
                LoginM3uActivity.this.etM3uLineFile.setText(str);
                LoginM3uActivity.this.tv_browse_error.setVisibility(8);
                LoginM3uActivity.this.tv_file_path.setVisibility(0);
                LoginM3uActivity.this.tv_file_path.setText(str);
            }
        }).chooseDirectory("");
    }

    public void onRadioButtonClicked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        int id = view.getId();
        if (id != R.id.rb_file) {
            if (id == R.id.rb_m3u && isChecked) {
                this.tv_file_path.setVisibility(8);
                this.bt_browse.setVisibility(8);
                this.tv_browse_error.setVisibility(8);
                this.etM3uLine.setVisibility(0);
            }
        } else if (isChecked) {
            this.tv_file_path.setVisibility(0);
            this.bt_browse.setVisibility(0);
            this.etM3uLine.setVisibility(8);
            this.tv_browse_error.setVisibility(8);
        }
    }

    public boolean checkFieldsM3U() {
        if (AppConst.M3U_API_SINGLE_USER.booleanValue()) {
            if (this.etM3uLine.getText().toString().trim().length() != 0) {
                return true;
            }
            this.etM3uLine.requestFocus();
            this.etM3uLine.setError(getResources().getString(R.string.enter_m3u_error));
            return false;
        } else if (this.etName.getText().toString().trim().length() == 0) {
            this.etName.requestFocus();
            this.etName.setError(getResources().getString(R.string.enter_any_name));
            return false;
        } else if (this.etM3uLine.getText().toString().trim().length() != 0) {
            return true;
        } else {
            this.etM3uLine.requestFocus();
            this.etM3uLine.setError(getResources().getString(R.string.enter_m3u_error));
            return false;
        }
    }

    public boolean checkFieldsFile() {
        if (AppConst.M3U_API_SINGLE_USER.booleanValue()) {
            if (this.etM3uLineFile.getText().toString().trim().length() != 0) {
                return true;
            }
            this.tv_browse_error.setVisibility(0);
            this.tv_browse_error.requestFocus();
            this.tv_browse_error.setError(this.context.getResources().getString(R.string.choose_any_playlist_file));
            return false;
        } else if (this.etName.getText().toString().trim().length() == 0) {
            this.etName.requestFocus();
            this.etName.setError(getResources().getString(R.string.enter_any_name));
            return false;
        } else if (this.etM3uLineFile.getText().toString().trim().length() != 0) {
            return true;
        } else {
            this.tv_browse_error.setVisibility(0);
            this.tv_browse_error.requestFocus();
            this.tv_browse_error.setError(this.context.getResources().getString(R.string.choose_any_playlist_file));
            return false;
        }
    }

    @SuppressLint({"StaticFieldLeak"})
    private class _checkNetworkAvailable extends AsyncTask<String, Void, Boolean> {
        public void onPreExecute() {
        }

        private _checkNetworkAvailable() {
        }

        public Boolean doInBackground(String... strArr) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(strArr[0]).openConnection();
                httpURLConnection.setInstanceFollowRedirects(true);
                httpURLConnection.setConnectTimeout(30000);
                httpURLConnection.setReadTimeout(30000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                Log.e("Google", String.valueOf(httpURLConnection.getResponseCode() == 200));
                if (httpURLConnection.getResponseCode() != 200) {
                    if (httpURLConnection.getResponseCode() != 405) {
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                Log.e("suman", e.getMessage());
                return false;
            }
        }

        public void onPostExecute(Boolean bool) {
            if (bool.booleanValue()) {
                new Handler().postDelayed(new Runnable() {
                    /* class com.nst.yourname.view.activity.LoginM3uActivity._checkNetworkAvailable.AnonymousClass1 */

                    public void run() {
                        new DwnloadFileFromUrl().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, LoginM3uActivity.this.m3uline);
                    }
                }, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
                return;
            }
            LoginM3uActivity.this.onFinish();
            Utils.showToast(LoginM3uActivity.this.context, LoginM3uActivity.this.context.getResources().getString(R.string.file_url_not_valid));
        }
    }

    @SuppressLint({"StaticFieldLeak"})
    private class DwnloadFileFromUrl extends AsyncTask<String, String, Boolean> {
        public void onProgressUpdate(String... strArr) {
        }

        private DwnloadFileFromUrl() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:27:0x008e, code lost:
            r0 = true;
         */
        public Boolean doInBackground(String... strArr) {
            boolean z = false;
            try {
                URL url = new URL(strArr[0]);
                LoginM3uActivity.this.isStoragePermissionGranted();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                int i = 0;
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    i++;
                    if (i == 10) {
                        break;
                    } else if (!readLine.equals("") && !readLine.contains(LoginM3uActivity.EXT_M3U)) {
                        if (readLine.contains(LoginM3uActivity.EXT_URL)) {
                            try {
                                String str = readLine.substring(readLine.lastIndexOf(LoginM3uActivity.EXT_URL)).replace("\n", "").replace("\r", "").split("/")[2];
                                if (str != null && str.contains(":")) {
                                    if (LoginM3uActivity.this.urls.contains(str.substring(0, str.lastIndexOf(":")))) {
                                        break;
                                    }
                                } else if (LoginM3uActivity.this.urls.contains(str)) {
                                    break;
                                }
                            } catch (Exception unused) {
                                continue;
                            }
                        } else if (readLine.contains(LoginM3uActivity.EXT_URL_HTTPS)) {
                            String str2 = readLine.substring(readLine.lastIndexOf(LoginM3uActivity.EXT_URL_HTTPS)).replace("\n", "").replace("\r", "").split("/")[2];
                            if (str2 != null && str2.contains(":")) {
                                if (LoginM3uActivity.this.urls.contains(str2.substring(0, str2.lastIndexOf(":")))) {
                                    break;
                                }
                            } else if (LoginM3uActivity.this.urls.contains(str2)) {
                                break;
                            }
                        } else {
                            continue;
                        }
                    }
                }
            } catch (MalformedURLException e) {
                Log.d("Suman", "DownloadFileFromUrl " + e.getMessage());
            } catch (IOException e2) {
                Log.d("Suman", "DownloadEXep " + e2.getMessage());
                e2.printStackTrace();
            }
            return Boolean.valueOf(z);
        }

        public void onPostExecute(Boolean bool) {
            Utils.hideProgressDialog();
            if (!bool.booleanValue()) {
                LoginM3uActivity.this.onFinish();
                Utils.showToast(LoginM3uActivity.this.context, LoginM3uActivity.this.context.getResources().getString(R.string.file_url_not_valid));
            } else if (LoginM3uActivity.this.m3uline != null) {
                boolean checkregistration = LoginM3uActivity.this.multiuserdbhandler.checkregistration(LoginM3uActivity.this.anyName, "playlist", "playlist", LoginM3uActivity.this.m3uline, AppConst.TYPE_M3U, "");
                if (AppConst.M3U_API_SINGLE_USER.booleanValue()) {
                    if (!checkregistration) {
                        LoginM3uActivity.this.multiuserdbhandler.addmultiusersM3U(LoginM3uActivity.this.anyName, "playlist", "playlist", LoginM3uActivity.this.m3uline, "url");
                    }
                    LoginM3uActivity.this.sentData();
                } else if (!checkregistration) {
                    LoginM3uActivity.this.multiuserdbhandler.addmultiusersM3U(LoginM3uActivity.this.anyName, "playlist", "playlist", LoginM3uActivity.this.m3uline, "url");
                    LoginM3uActivity.this.sentData();
                } else {
                    Context access$000 = LoginM3uActivity.this.context;
                    Toast.makeText(access$000, LoginM3uActivity.this.getResources().getString(R.string.already_exist_with_name) + " " + LoginM3uActivity.this.anyName, 0).show();
                }
            }
            Utils.hideProgressDialog();
        }
    }

    public void sentData() {
        String str;
        SharedPreferences.Editor edit = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).edit();
        edit.putString("username", "playlist");
        edit.putString("password", "playlist");
        edit.putString(AppConst.LOGIN_PREF_SERVER_PORT, "");
        edit.putString(AppConst.LOGIN_PREF_SERVER_URL, this.m3uline);
        edit.putString(AppConst.LOGIN_PREF_SERVER_M3U_LINE, this.m3uline);
        edit.putString(AppConst.LOGIN_PREF_ANY_NAME, this.anyName);
        edit.apply();
        if (AppConst.M3U_API_SINGLE_USER.booleanValue()) {
            SharedPreferences sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0);
            SharedPreferences.Editor edit2 = sharedPreferences.edit();
            str = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "");
            edit2.putString("name", this.anyName);
            edit2.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, this.m3uline);
            edit2.apply();
        } else {
            str = null;
        }
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
        if (this.loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "").equals("")) {
            this.loginPrefsEditor_timefomat.putString(AppConst.LOGIN_PREF_TIME_FORMAT, "HH:mm");
            this.loginPrefsEditor_timefomat.apply();
        }
        if (this.loginPreferencesSharedPref_epg_channel_update.getString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "").equals("")) {
            this.loginPrefsEditor_epgchannelupdate.putString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "all");
            this.loginPrefsEditor_epgchannelupdate.apply();
        }
        this.loginPreferencesRemember = this.context.getSharedPreferences(AppConst.SHARED_PREFERENCE_REMEBER_ME, 0);
        this.loginPrefsEditorBeforeLogin = this.loginPreferencesRemember.edit();
        if (!AppConst.M3U_LINE_ACTIVE.booleanValue() || !AppConst.M3U_API_SINGLE_USER.booleanValue()) {
            this.loginPrefsEditorBeforeLogin.putBoolean(AppConst.PREF_SAVE_LOGIN, false);
        } else {
            this.loginPrefsEditorBeforeLogin.putBoolean(AppConst.PREF_SAVE_LOGIN, true);
        }
        this.loginPrefsEditorBeforeLogin.putString("username", "playlist");
        this.loginPrefsEditorBeforeLogin.putString("password", "playlist");
        this.loginPrefsEditorBeforeLogin.putString(AppConst.LOGIN_PREF_SERVER_PORT, "");
        this.loginPrefsEditorBeforeLogin.putString(AppConst.LOGIN_PREF_SERVER_URL, this.m3uline);
        this.loginPrefsEditorBeforeLogin.putString(AppConst.LOGIN_PREF_SERVER_M3U_LINE, this.m3uline);
        this.loginPrefsEditorBeforeLogin.putString(AppConst.LOGIN_PREF_ANY_NAME, this.anyName);
        this.loginPrefsEditorBeforeLogin.apply();
        SharepreferenceDBHandler.setCurrentAPPType(AppConst.TYPE_M3U, this.context);
        if (AppConst.M3U_API_SINGLE_USER.booleanValue()) {
            onFinish();
            Toast.makeText(this.context, getResources().getString(R.string.logged_in), 0).show();
            SharepreferenceDBHandler.setUserID(this.multiuserdbhandler.getAutoIdLoggedInUser(this.anyName, "playlist", "playlist", this.m3uline, AppConst.TYPE_M3U, ""), this.context);
            if (this.m3uline == null || !this.m3uline.equals(str)) {
                startActivity(new Intent(this.context, ImportM3uActivity.class));
                finish();
                return;
            }
            startActivity(new Intent(this.context, NewDashboardActivity.class));
            finish();
            return;
        }
        onFinish();
        Toast.makeText(this.context, getResources().getString(R.string.user_added), 0).show();
        startActivity(new Intent(this.context, MultiUserActivity.class));
        finish();
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

    public void onPostSuccess(String str, int i, boolean z) {
        if (!z) {
            onFinish();
            Toast.makeText(this, this.context.getResources().getString(R.string.could_not_connect), 0).show();
        } else if (i == 1) {
            try {
                Globals.jsonObj = new JSONObject(str);
                if (!Globals.jsonObj.getString(NotificationCompat.CATEGORY_STATUS).equalsIgnoreCase("true")) {
                    AppConst.CHECK_STATUS = false;
                    onFinish();
                    Toast.makeText(this, this.context.getResources().getString(R.string.could_not_connect), 0).show();
                } else if (AppConst.CHECK_STATUS.booleanValue()) {
                    onFinish();
                    AppConst.CHECK_STATUS = false;
                    this.urls = Globals.jsonObj.getString("su");
                    this.nextDueDate = Globals.jsonObj.getString("ndd");
                    this.currentDate = System.currentTimeMillis();
                    this.multiuserdbhandler.deleteSaveLogin();
                    this.multiuserdbhandler.saveLoginData(this.urls, this.currentDate1);
                    Toast.makeText(this, this.context.getResources().getString(R.string.refresh_dns_sucess), 0).show();
                } else {
                    this.urls = Globals.jsonObj.getString("su");
                    this.nextDueDate = Globals.jsonObj.getString("ndd");
                    this.currentDate = System.currentTimeMillis();
                    this.multiuserdbhandler.getSaveLoginDate();
                    this.multiuserdbhandler.saveLoginData(this.urls, this.currentDate1);
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
                        if (this.loginPreferencesRemember != null) {
                            this.username = this.loginPreferencesRemember.getString("username", "");
                            this.password = this.loginPreferencesRemember.getString("password", "");
                        } else {
                            this.username = "playlist";
                            this.password = "playlist";
                        }
                        RavSharedPrefrences.set_authurl(this, Globals.jsonObj.optString("su"));
                        this.SecondMdkey = md5(Globals.jsonObj.optString("su") + "*" + RavSharedPrefrences.get_salt(this) + "*" + Globals.RandomNumber);
                        if (Globals.jsonObj.getString("sc").equalsIgnoreCase(this.SecondMdkey)) {
                            int checkedRadioButtonId = this.radioGroup.getCheckedRadioButtonId();
                            if (checkedRadioButtonId == R.id.rb_file) {
                                new _loadFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.m3ulineFile);
                            }
                            if (checkedRadioButtonId == R.id.rb_m3u) {
                                new _checkNetworkAvailable().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.m3uline);
                                return;
                            }
                            return;
                        }
                        onFinish();
                        Toast.makeText(this, this.context.getResources().getString(R.string.could_not_connect), 0).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        onFinish();
                        Toast.makeText(this, this.context.getResources().getString(R.string.could_not_connect), 0).show();
                    }
                }
            } catch (Exception e2) {
                Log.e("Login check", e2.getMessage());
            }
        }
    }

    @Override // com.nst.yourname.WebServiceHandler.MainAsynListener
    public void onPostError(int i) {
        if (this.context != null) {
            AppConst.CHECK_STATUS = false;
            onFinish();
            Toast.makeText(this, this.context.getResources().getString(R.string.could_not_connect), 0).show();
        }
    }

    public void onFinish() {
        Utils.hideProgressDialog();
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
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 1);
            return false;
        }
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

    public void checkLoginStatus() {
        if (this.multiuserdbhandler == null || this.multiuserdbhandler.getSaveLoginDate() == null) {
            SequrityApi();
            return;
        }
        ArrayList<MultiUserDBModel> saveLoginDate = this.multiuserdbhandler.getSaveLoginDate();
        if (saveLoginDate.size() == 0) {
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
            this.urls = saveLoginDate.get(0).getServerUrl();
            int checkedRadioButtonId = this.radioGroup.getCheckedRadioButtonId();
            if (this.loginPreferencesRemember != null) {
                this.username = this.loginPreferencesRemember.getString("username", "");
                this.password = this.loginPreferencesRemember.getString("password", "");
            } else {
                this.username = "playlist";
                this.password = "playlist";
            }
            if (checkedRadioButtonId == R.id.rb_file) {
                new _loadFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.m3ulineFile);
            }
            if (checkedRadioButtonId == R.id.rb_m3u) {
                new _checkNetworkAvailable().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.m3uline);
            }
        }
    }

    public static long getDateDiff(SimpleDateFormat simpleDateFormat2, String str, String str2) {
        try {
            return TimeUnit.DAYS.convert(simpleDateFormat2.parse(str2).getTime() - simpleDateFormat2.parse(str).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String currentDateValue() {
        return Utils.parseDateToddMMyyyy(Calendar.getInstance().getTime().toString());
    }
}
