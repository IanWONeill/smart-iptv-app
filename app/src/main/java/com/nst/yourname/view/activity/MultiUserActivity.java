package com.nst.yourname.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.MultiUserDBModel;
import com.nst.yourname.model.database.MultiUserDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.adapter.MultiUserAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

public class MultiUserActivity extends AppCompatActivity implements MainAsynListener<String> {
    static final boolean $assertionsDisabled = false;
    private static final int TIME_INTERVAL = 2000;
    String FirstMdkey;
    String SecondMdkey;
    MultiUserAdapter adapter;
    @BindView(R.id.iv_add_more)
    ImageView addmore;
    Context context;
    String currentDate1 = currentDateValue();
    long dateDifference = 0;
    public Dialog dialog;
    private boolean doubleBackToExitPressedOnce = false;
    @BindView(R.id.empty_view)
    TextView emptyView;
    @BindView(R.id.fl_frame)
    FrameLayout frameLayout;
    private GridLayoutManager gridLayoutManager;
    Handler handler;
    Intent intent;
    @BindView(R.id.iv_bt_up)
    ImageView ivBTUP;
    String key;
    @BindView(R.id.ll_add_new_user)
    LinearLayout ll_add_new_user;
    @BindView(R.id.ll_add_user)
    LinearLayout ll_add_user;
    private SharedPreferences loginPreferencesRemember;
    private SharedPreferences loginPreferencesServerURl;
    private SharedPreferences.Editor loginPreferencesServerURlPut;
    private long mBackPressed;
    private MultiUserDBHandler multiUserDBHandler;
    MultiUserDBHandler multiuserdbhandler;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    private ProgressDialog progressDialog;
    int random;
    String reqString;
    String salt;
    private Boolean saveLogin;
    ArrayList<String> serverList = new ArrayList<>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    @BindView(R.id.tv_no_record_found)
    TextView tvNoRecordFound;
    @BindView(R.id.tv_noStream)
    TextView tvNoStream;
    @BindView(R.id.tv_list_options)
    TextView tv_list_options;
    String urls = "";
    String version;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        setContentView((int) R.layout.activity_multi_user);
        ButterKnife.bind(this);
        this.intent = getIntent();
        getWindow().setFlags(1024, 1024);
        this.context = this;
        this.progressDialog = new ProgressDialog(this.context);
        this.progressDialog.setMessage(this.context.getResources().getString(R.string.please_wait));
        this.progressDialog.setCanceledOnTouchOutside(false);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setProgressStyle(0);
        appVersionName();
        DEviceVersion();
        getDeviceName();
        GetRandomNumber();
        changeStatusBarColor();
        this.multiuserdbhandler = new MultiUserDBHandler(this.context);
        Webservices.getWebservices = new Webservices(this.context);
        getSharedPreferences(AppConst.ACCEPTCLICKED, 0).getString(AppConst.ACCEPTCLICKED, "");
        this.loginPreferencesServerURl = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0);
        this.loginPreferencesRemember = getSharedPreferences(AppConst.SHARED_PREFERENCE_REMEBER_ME, 0);
        this.saveLogin = Boolean.valueOf(this.loginPreferencesRemember.getBoolean(AppConst.PREF_SAVE_LOGIN, false));
        this.addmore.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.addmore));
        this.multiUserDBHandler = new MultiUserDBHandler(this.context);
        if (!this.saveLogin.booleanValue()) {
            displayUsers();
        } else if (AppConst.M3U_LINE_ACTIVE.booleanValue() && SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            getUserDetails(AppConst.TYPE_M3U);
        } else if (!AppConst.MultiDNS_And_MultiUser.booleanValue() || !SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_API)) {
            dashboardRedirect();
        } else {
            getUserDetails(AppConst.TYPE_API);
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    public void getUserDetails(String str) {
        ArrayList<MultiUserDBModel> userDetailsAPI;
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0);
        String string = sharedPreferences.getString("name", "");
        String string2 = sharedPreferences.getString("username", "");
        String string3 = sharedPreferences.getString("password", "");
        String string4 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "");
        String string5 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_URL, "");
        if (this.multiUserDBHandler != null) {
            if (str.equals(AppConst.TYPE_M3U)) {
                userDetailsAPI = this.multiUserDBHandler.getUserDetailsM3U(string, string2, string3, string4);
            } else {
                userDetailsAPI = this.multiUserDBHandler.getUserDetailsAPI(string, string2, string3, string4, string5);
            }
            ArrayList<MultiUserDBModel> arrayList = userDetailsAPI;
            if (arrayList == null || arrayList.size() <= 0) {
                displayUsers();
                return;
            }
            int autoIdLoggedInUser = this.multiUserDBHandler.getAutoIdLoggedInUser(string, string2, string3, string4, str, string5);
            String str2 = arrayList.get(0).getusername();
            SharepreferenceDBHandler.setUserID(autoIdLoggedInUser, this.context);
            ArrayList<MultiUserDBModel> saveLoginDate = this.multiUserDBHandler.getSaveLoginDate();
            if (saveLoginDate.size() == 0) {
                AppConst.ACTIVE_STATUS = true;
                startActivity(new Intent(this, LoginActivity.class));
            } else if (saveLoginDate.get(0).getDate() != null) {
                String date = saveLoginDate.get(0).getDate();
                saveLoginDate.get(0).getServerUrl();
                if (getDateDiff(this.simpleDateFormat, date, this.currentDate1) > 14) {
                    displayUsers();
                    atStart();
                    SequrityApi(str2);
                    return;
                }
                dashboardRedirect();
            } else {
                displayUsers();
            }
        } else {
            displayUsers();
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

    public void dashboardRedirect() {
        Intent intent2 = new Intent(this, NewDashboardActivity.class);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        startActivity(intent2);
        finish();
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

    public void displayUsers() {
        this.handler = new Handler();
        this.handler.removeCallbacksAndMessages(null);
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(0);
        }
        initialise();
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(8);
        }
    }

    public void initialise() {
        ArrayList<MultiUserDBModel> allUsersM3U = this.multiUserDBHandler.getAllUsersM3U();
        allUsersM3U.addAll(this.multiUserDBHandler.getAllUsers());
        if (allUsersM3U.size() > 0) {
            this.tv_list_options.setVisibility(0);
            this.adapter = new MultiUserAdapter(this, allUsersM3U, this.context, this.ll_add_new_user, this.tv_list_options);
            this.gridLayoutManager = new GridLayoutManager(this, 2);
            this.myRecyclerView.setLayoutManager(this.gridLayoutManager);
            this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
            this.myRecyclerView.setAdapter(this.adapter);
        } else {
            this.ll_add_new_user.setVisibility(0);
        }
        AppConst.FROM_LOGIN_TO_MULTIUSER = false;
    }

    @OnClick({R.id.ll_add_new_user, R.id.ll_add_user})
    public void onViewClicked(View view) {
        Intent intent2;
        int id = view.getId();
        if (id == R.id.ll_add_new_user || id == R.id.ll_add_user) {
            if (AppConst.M3U_LINE_ACTIVE.booleanValue()) {
                intent2 = new Intent(this, RoutingActivity.class);
            } else {
                intent2 = new Intent(this, LoginActivity.class);
            }
            startActivity(intent2);
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
            AppConst.FROM_MULTIUSER_TO_LOGIN = true;
        }
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

    private class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private final View view;

        public OnFocusChangeAccountListener(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            float f = 1.0f;
            if (z) {
                Log.e("id is", "" + this.view.getTag());
                if (this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_CAT_ID)) {
                    if (z) {
                        f = 2.0f;
                    }
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                }
            } else if (!z && this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_CAT_ID)) {
                if (z) {
                    f = 2.0f;
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

    @SuppressLint({"SetJavaScriptEnabled"})
    private void getDialog() {
        this.dialog = new Dialog(this, 16973840);
        this.dialog.requestWindowFeature(1);
        this.dialog.setContentView((int) R.layout.activity_terms_condition_page);
        this.dialog.setCancelable(false);
        Window window = this.dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 17;
        window.setAttributes(attributes);
        this.dialog.getWindow().setLayout(-1, -1);
        this.dialog.show();
        this.dialog.setCanceledOnTouchOutside(false);
        WebView webView = (WebView) this.dialog.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/terms.html");
        ((Button) this.dialog.findViewById(R.id.accept)).setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.MultiUserActivity.AnonymousClass1 */

            @SuppressLint({"ApplySharedPref"})
            public void onClick(View view) {
                MultiUserActivity.this.getSharedPreferences(AppConst.ACCEPTCLICKED, 0).edit().putString(AppConst.ACCEPTCLICKED, "true").apply();
                MultiUserActivity.this.dialog.dismiss();
            }
        });
        this.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            /* class com.nst.yourname.view.activity.MultiUserActivity.AnonymousClass2 */

            public void onDismiss(DialogInterface dialogInterface) {
            }
        });
        this.dialog.show();
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void getDemoAppDialog() {
        this.dialog = new Dialog(this, 16973840);
        this.dialog.requestWindowFeature(1);
        this.dialog.setContentView((int) R.layout.activity_terms_condition_page);
        this.dialog.setCancelable(false);
        Window window = this.dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 17;
        window.setAttributes(attributes);
        this.dialog.getWindow().setLayout(-1, -1);
        this.dialog.show();
        this.dialog.setCanceledOnTouchOutside(false);
        WebView webView = (WebView) this.dialog.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/demo.html");
        ((TextView) this.dialog.findViewById(R.id.tv_warning)).setVisibility(0);
        ((TextView) this.dialog.findViewById(R.id.tv_title)).setText("IPTV Smarters Pro Demo v2.0 Features");
        Button button = (Button) this.dialog.findViewById(R.id.accept);
        button.setText(getResources().getString(R.string.set_ok));
        button.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.MultiUserActivity.AnonymousClass3 */

            public void onClick(View view) {
                SharepreferenceDBHandler.setDemoAppMessageShow(true, MultiUserActivity.this.context);
                MultiUserActivity.this.dialog.dismiss();
            }
        });
        this.dialog.show();
    }

    public void SequrityApi(String str) {
        this.FirstMdkey = md5(RavSharedPrefrences.get_clientkey(this.context) + "*" + RavSharedPrefrences.get_salt(this.context) + "-" + str + "-" + Globals.RandomNumber + "-" + this.version + "-unknown-" + getDeviceName() + "-" + this.reqString);
        Webservices.getterList = new ArrayList();
        Webservices.getterList.add(Webservices.P("m", "gu"));
        Webservices.getterList.add(Webservices.P("k", RavSharedPrefrences.get_clientkey(this.context)));
        Webservices.getterList.add(Webservices.P("sc", this.FirstMdkey));
        Webservices.getterList.add(Webservices.P("u", str));
        Webservices.getterList.add(Webservices.P("pw", "no_password"));
        Webservices.getterList.add(Webservices.P("r", Globals.RandomNumber));
        Webservices.getterList.add(Webservices.P("av", this.version));
        Webservices.getterList.add(Webservices.P("dt", "unknown"));
        Webservices.getterList.add(Webservices.P("d", getDeviceName()));
        Webservices.getterList.add(Webservices.P("do", this.reqString));
        Webservices.getWebservices.SequrityLink(this);
    }

    public void appVersionName() {
        try {
            this.version = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0).versionName;
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

    public void onPostSuccess(String str, int i, boolean z) {
        if (z && i == 1) {
            try {
                Globals.jsonObj = new JSONObject(str);
                if (Globals.jsonObj.getString(NotificationCompat.CATEGORY_STATUS).equalsIgnoreCase("true")) {
                    this.urls = Globals.jsonObj.getString("su");
                    this.multiuserdbhandler.deleteSaveLogin();
                    RavSharedPrefrences.set_authurl(this.context, Globals.jsonObj.optString("su"));
                    this.SecondMdkey = md5(Globals.jsonObj.optString("su") + "*" + RavSharedPrefrences.get_salt(this.context) + "*" + Globals.RandomNumber);
                    this.loginPreferencesServerURl = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0);
                    this.loginPreferencesServerURlPut = this.loginPreferencesServerURl.edit();
                    this.loginPreferencesServerURlPut.apply();
                    this.multiuserdbhandler.saveLoginData(RavSharedPrefrences.get_authurl(this.context), currentDateValue());
                    onFinish();
                    dashboardRedirect();
                    return;
                }
                AppConst.REFRESH_BUTTON = false;
                AppConst.CHECK_STATUS = false;
                AppConst.ACTIVE_STATUS = false;
                onFinish();
                this.multiuserdbhandler.deleteSaveLogin();
                Toast.makeText(this.context, this.context.getResources().getString(R.string.status_suspend), 0).show();
                displayUsers();
            } catch (Exception unused) {
            }
        }
    }

    @Override // com.nst.yourname.WebServiceHandler.MainAsynListener
    public void onPostError(int i) {
        onFinish();
        Toast.makeText(this.context, this.context.getResources().getString(R.string.status_suspend), 0).show();
    }
}
