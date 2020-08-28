package com.nst.yourname.WHMCSClientapp.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.Clientdatabase.ClientSharepreferenceHandler;
import com.nst.yourname.WHMCSClientapp.commanClassess.AppConstant;
import com.nst.yourname.WHMCSClientapp.interfaces.ApiService;
import com.nst.yourname.WebServiceHandler.Globals;
import com.nst.yourname.WebServiceHandler.Webservices;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.FreeTrailModelClass;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.MultiUserDBHandler;
import com.nst.yourname.model.database.SeriesStreamsDatabaseHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.presenter.LoginPresenter;
import com.nst.yourname.view.activity.LoginActivity;
import com.nst.yourname.view.utility.UtilsMethods;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import okhttp3.OkHttpClient;
import org.achartengine.chart.TimeChart;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FreeTrailActivity extends AppCompatActivity {
    static final boolean $assertionsDisabled = false;
    public static InputFilter EMOJI_FILTER = new InputFilter() {
        /* class com.nst.yourname.WHMCSClientapp.activities.FreeTrailActivity.AnonymousClass4 */

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
    String FirstMdkey;
    private String MacAddress;
    String SecondMdkey;
    Button btn_submit;
    public String confirm_password = "";
    Context context;
    long currentDate = -1;
    public String email = "";
    EditText et_confirm_password;
    EditText et_email;
    EditText et_password;
    EditText et_username;
    private DatabaseHandler favDBHandler;
    Handler handler;
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
    private SharedPreferences loginPreferencesSharedPref_epg_channel_update;
    private SharedPreferences loginPreferencesSharedPref_time_format;
    private SharedPreferences loginPreferencesUpgradeDate;
    private SharedPreferences.Editor loginPrefsEditorBeforeLogin;
    private SharedPreferences.Editor loginPrefsEditorChannels;
    private SharedPreferences.Editor loginPrefsEditorEPG;
    private SharedPreferences.Editor loginPrefsEditorUpgaradeDate;
    private SharedPreferences.Editor loginPrefsEditor_epgchannelupdate;
    private SharedPreferences.Editor loginPrefsEditor_fomat;
    private SharedPreferences.Editor loginPrefsEditor_timefomat;
    private LoginPresenter loginPresenter;
    String model = Build.MODEL;
    private MultiUserDBHandler multiuserdbhandler;
    String newUrl = "";
    String newemail = "";
    String nextDueDate = "";
    public String password = "";
    private ProgressDialog progressDialog;
    int random;
    String reqString;
    @BindView(R.id.rl_already_register)
    RelativeLayout rl_already_register;
    @BindView(R.id.rl_bt_submit)
    RelativeLayout rl_bt_submit;
    @BindView(R.id.rl_confirmpassword)
    RelativeLayout rl_confirmpassword;
    @BindView(R.id.rl_email)
    RelativeLayout rl_email;
    @BindView(R.id.rl_password)
    RelativeLayout rl_password;
    @BindView(R.id.rl_username)
    RelativeLayout rl_username;
    String salt;
    private Boolean saveLogin;
    String savedUrl = "";
    String savedemail = "";
    private SeriesStreamsDatabaseHandler seriesStreamsDatabaseHandler;
    TextView tv_already_register;
    long upgradeDate = -1;
    String urls = "";
    public String username = "";
    String version;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    @SuppressLint({"ResourceType"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Webservices.getWebservices = new Webservices(this);
        setContentView((int) R.layout.activity_free_trail);
        ButterKnife.bind(this);
        appVersionName();
        DEviceVersion();
        getDeviceName();
        GetRandomNumber();
        this.MacAddress = getMacAddr();
        if (this.MacAddress.equalsIgnoreCase("")) {
            this.MacAddress = getmac();
        }
        this.context = this;
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.multiuserdbhandler = new MultiUserDBHandler(this.context);
        this.favDBHandler = new DatabaseHandler(this.context);
        this.seriesStreamsDatabaseHandler = new SeriesStreamsDatabaseHandler(this.context);
        this.et_email = new EditText(this);
        this.et_email.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        this.et_email.setPaddingRelative(35, 0, 35, 0);
        this.et_email.setHint(getResources().getString(R.string.email));
        this.et_email.setHintTextColor(getResources().getColor(R.color.white));
        this.et_email.setHintTextColor(-1);
        this.et_email.setTextSize(22.0f);
        this.et_email.setId(101);
        this.et_email.setBackground(getResources().getDrawable(R.drawable.selector_login_fields));
        this.et_email.setFocusable(true);
        this.et_email.setTypeface(Typeface.SANS_SERIF);
        this.et_email.setInputType(32);
        this.rl_email.addView(this.et_email);
        this.et_username = new EditText(this);
        this.et_username.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        this.et_username.setPaddingRelative(35, 0, 35, 0);
        this.et_username.setHint(getResources().getString(R.string.username));
        this.et_username.setHintTextColor(getResources().getColor(R.color.white));
        this.et_username.setHintTextColor(-1);
        this.et_username.setTextSize(22.0f);
        this.et_username.setId(101);
        this.et_username.setBackground(getResources().getDrawable(R.drawable.selector_login_fields));
        this.et_username.setFocusable(true);
        this.et_username.setTypeface(Typeface.SANS_SERIF);
        this.et_username.setInputType(1);
        this.rl_username.addView(this.et_username);
        this.et_password = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        this.et_password.setPaddingRelative(35, 0, 35, 0);
        this.et_password.setLayoutParams(layoutParams);
        this.et_password.setHint(getResources().getString(R.string.enter_password));
        this.et_password.setHintTextColor(getResources().getColor(R.color.white));
        this.et_password.setHintTextColor(-1);
        this.et_password.setTextSize(22.0f);
        this.et_password.setId(101);
        this.et_password.setBackground(getResources().getDrawable(R.drawable.selector_login_fields));
        this.et_password.setFocusable(true);
        this.et_password.setTypeface(Typeface.SANS_SERIF);
        this.et_password.setInputType(TsExtractor.TS_STREAM_TYPE_AC3);
        this.rl_password.addView(this.et_password);
        this.et_confirm_password = new EditText(this);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -1);
        this.et_confirm_password.setPaddingRelative(35, 0, 35, 0);
        this.et_confirm_password.setLayoutParams(layoutParams2);
        this.et_confirm_password.setHint(getResources().getString(R.string.confirm_password));
        this.et_confirm_password.setHintTextColor(getResources().getColor(R.color.white));
        this.et_confirm_password.setHintTextColor(-1);
        this.et_confirm_password.setTextSize(22.0f);
        this.et_confirm_password.setId(101);
        this.et_confirm_password.setBackground(getResources().getDrawable(R.drawable.selector_login_fields));
        this.et_confirm_password.setFocusable(true);
        this.et_confirm_password.setTypeface(Typeface.SANS_SERIF);
        this.et_confirm_password.setInputType(TsExtractor.TS_STREAM_TYPE_AC3);
        this.rl_confirmpassword.addView(this.et_confirm_password);
        this.btn_submit = new Button(this);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -1);
        this.btn_submit.setPaddingRelative(35, 0, 35, 0);
        this.btn_submit.setLayoutParams(layoutParams3);
        this.btn_submit.setText(getResources().getString(R.string.sign_up));
        this.btn_submit.setTextColor(-16777216);
        this.btn_submit.setTextSize(22.0f);
        this.btn_submit.setId(105);
        this.btn_submit.setBackground(getResources().getDrawable(R.drawable.selector_button));
        this.btn_submit.setFocusable(true);
        this.btn_submit.setGravity(17);
        this.btn_submit.setTypeface(Typeface.SANS_SERIF);
        this.rl_bt_submit.addView(this.btn_submit);
        this.tv_already_register = new TextView(this);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-1, -1);
        this.tv_already_register.setPaddingRelative(35, 0, 35, 0);
        this.tv_already_register.setLayoutParams(layoutParams4);
        this.tv_already_register.setTextColor(-1);
        if ((this.context.getResources().getConfiguration().screenLayout & 15) == 3) {
            this.tv_already_register.setTextSize(22.0f);
        } else {
            this.tv_already_register.setTextSize(15.0f);
        }
        this.tv_already_register.setText(getResources().getString(R.string.already_registered_login));
        this.tv_already_register.setId(105);
        this.tv_already_register.setGravity(16);
        this.tv_already_register.setBackground(getResources().getDrawable(R.drawable.selector_checkbox));
        this.tv_already_register.setFocusable(true);
        this.rl_already_register.addView(this.tv_already_register);
        if (this.context != null) {
            this.progressDialog = new ProgressDialog(this.context);
            this.progressDialog.setMessage("Please wait while we are creating free trial for you");
            this.progressDialog.setCanceledOnTouchOutside(false);
            this.progressDialog.setCancelable(false);
            this.progressDialog.setProgressStyle(0);
        }
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
        this.tv_already_register.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.WHMCSClientapp.activities.FreeTrailActivity.AnonymousClass1 */

            public void onClick(View view) {
                FreeTrailActivity.this.startActivity(new Intent(FreeTrailActivity.this, LoginActivity.class));
                FreeTrailActivity.this.finish();
            }
        });
        UtilsMethods.Block_SpaceInEditText(this.et_password);
        this.et_username.setFilters(new InputFilter[]{EMOJI_FILTER});
        this.btn_submit.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.WHMCSClientapp.activities.FreeTrailActivity.AnonymousClass2 */

            public void onClick(View view) {
                freeTrailLogin();
            }

            private void freeTrailLogin() {
                FreeTrailActivity.this.email = FreeTrailActivity.this.et_email.getText().toString().trim();
                String unused2 = FreeTrailActivity.this.username = FreeTrailActivity.this.et_username.getText().toString().trim();
                String unused3 = FreeTrailActivity.this.password = FreeTrailActivity.this.et_password.getText().toString().trim();
                String unused4 = FreeTrailActivity.this.confirm_password = FreeTrailActivity.this.et_confirm_password.getText().toString().trim();
                if (FreeTrailActivity.this.email.isEmpty()) {
                    Toast.makeText(FreeTrailActivity.this.context, FreeTrailActivity.this.getResources().getString(R.string.please_enter_email), 0).show();
                } else if (!FreeTrailActivity.this.isEmailValid(FreeTrailActivity.this.email)) {
                    Toast.makeText(FreeTrailActivity.this.context, FreeTrailActivity.this.getResources().getString(R.string.wrong_format), 0).show();
                } else if (FreeTrailActivity.this.username.isEmpty()) {
                    Toast.makeText(FreeTrailActivity.this.context, FreeTrailActivity.this.getResources().getString(R.string.please_enter_username), 0).show();
                } else if (FreeTrailActivity.this.password.isEmpty()) {
                    Toast.makeText(FreeTrailActivity.this.context, FreeTrailActivity.this.getResources().getString(R.string.please_enter_password), 0).show();
                } else if (FreeTrailActivity.this.confirm_password.equalsIgnoreCase("")) {
                    Toast.makeText(FreeTrailActivity.this.context, FreeTrailActivity.this.getResources().getString(R.string.please_enter_confirm_password), 0).show();
                } else if (!FreeTrailActivity.this.password.equals(FreeTrailActivity.this.confirm_password)) {
                    Toast.makeText(FreeTrailActivity.this.context, FreeTrailActivity.this.getResources().getString(R.string.password_does_not_matched), 0).show();
                } else {
                    SharepreferenceDBHandler.setUserName("", FreeTrailActivity.this.context);
                    SharepreferenceDBHandler.setUserPassword("", FreeTrailActivity.this.context);
                    FreeTrailActivity.this.atStart();
                    FreeTrailActivity.this.FreeTrailApihit();
                }
            }
        });
    }

    public void FreeTrailApihit() {
        ((ApiService) new Retrofit.Builder().baseUrl("http://tvplushd.com/billing/").client(new OkHttpClient.Builder().connectTimeout(300, TimeUnit.SECONDS).writeTimeout(300, TimeUnit.SECONDS).readTimeout(300, TimeUnit.SECONDS).followRedirects(false).build()).addConverterFactory(GsonConverterFactory.create()).build().create(ApiService.class)).getFreeTrailApi(AppConst.BillingAPI_Username, AppConst.BillingAPI_Password, "freetrail", "yes", this.email, this.username, this.password, this.MacAddress, "com.nst.yourname").enqueue(new Callback<FreeTrailModelClass>() {
            /* class com.nst.yourname.WHMCSClientapp.activities.FreeTrailActivity.AnonymousClass3 */
            static final boolean $assertionsDisabled = false;

            @Override // retrofit2.Callback
            public void onResponse(@NonNull Call<FreeTrailModelClass> call, @NonNull Response<FreeTrailModelClass> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    FreeTrailActivity.this.stopLoader("No Response from server");
                } else if (response.body().getResult().equalsIgnoreCase("success")) {
                    ClientSharepreferenceHandler.setIsFreeTrail("", FreeTrailActivity.this.context);
                    if (AppConst.CODE_ACTIVATOR.booleanValue()) {
                        AppConst.CODE_ACTIVATOR = false;
                    }
                    SharepreferenceDBHandler.setUserName(FreeTrailActivity.this.username, FreeTrailActivity.this.context);
                    SharepreferenceDBHandler.setUserPassword(FreeTrailActivity.this.password, FreeTrailActivity.this.context);
                    Intent intent = new Intent(FreeTrailActivity.this, LoginActivity.class);
                    intent.setAction(AppConstant.ACTION_LOGIN_PERFORM);
                    FreeTrailActivity.this.startActivity(intent);
                    FreeTrailActivity.this.finish();
                } else {
                    FreeTrailActivity.this.stopLoader(response.body().getMessage());
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(@NonNull Call<FreeTrailModelClass> call, @NonNull Throwable th) {
                FreeTrailActivity.this.stopLoader(FreeTrailActivity.this.getResources().getString(R.string.could_not_connect));
            }
        });
    }

    public void stopLoader(String str) {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
        if (!str.equals("")) {
            Utils.showToast(this.context, str);
        } else {
            Utils.showToast(this.context, "Your Account is invalid or expired !");
        }
    }

    public void atStart() {
        if (this.progressDialog != null) {
            this.progressDialog.show();
        }
    }

    public void onFinish() {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
    }

    public void onFailed(String str) {
        if (this.context != null && !str.isEmpty()) {
            Utils.showToast(this.context, str);
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        Utils.appResume(this.context);
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        if (AppConst.ACT_TRIAL.booleanValue()) {
            AppConst.CODE_ACTIVATOR = true;
        }
        finish();
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    public static String getMacAddr() {
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress == null) {
                        return "";
                    }
                    StringBuilder sb = new StringBuilder();
                    int length = hardwareAddress.length;
                    for (int i = 0; i < length; i++) {
                        sb.append(String.format("%02X:", Byte.valueOf(hardwareAddress[i])));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString();
                }
            }
            return "";
        } catch (Exception unused) {
            return "";
        }
    }

    public String getmac() {
        return ((WifiManager) getApplicationContext().getSystemService("wifi")).getConnectionInfo().getMacAddress();
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

    public long getNextDueDateTimeStamp(String str) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return simpleDateFormat.parse(str).getTime() + TimeChart.DAY;
        } catch (ParseException e) {
            e.printStackTrace();
            return Long.parseLong(String.valueOf(-1));
        }
    }

    public boolean isEmailValid(String str) {
        return Pattern.compile("^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$", 2).matcher(str).matches();
    }
}
