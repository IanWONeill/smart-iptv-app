package com.nst.yourname.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.Clientdatabase.ClientSharepreferenceHandler;
import com.nst.yourname.WHMCSClientapp.activities.ServicesDashboardActivity;
import com.nst.yourname.WHMCSClientapp.interfaces.ApiService;
import com.nst.yourname.WHMCSClientapp.interfaces.ApiclientRetrofit;
import com.nst.yourname.WHMCSClientapp.modelclassess.LoginWHMCSModelClass;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.ExtendedAppInfo;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.MultiUserDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountInfoActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.logout)
    Button Logout;
    int actionBarHeight;
    @BindView(R.id.appbar_toolbar)
    AppBarLayout appbarToolbar;
    @BindView(R.id.btn_back_account_info)
    Button btnBackAccountInfo;
    @BindView(R.id.btn_buy_now)
    Button btn_buy_now;
    public Context context;
    @BindView(R.id.date)
    TextView date;
    private LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesAfterLogin;
    @BindView(R.id.logo)
    ImageView logo;
    ProgressDialog progressDialog;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_active_conn)
    TextView tvActiveConn;
    @BindView(R.id.tv_created_at)
    TextView tvCreatedAt;
    @BindView(R.id.tv_expiry_date)
    TextView tvExpiryDate;
    @BindView(R.id.tv_is_trial)
    TextView tvIsTrial;
    @BindView(R.id.tv_max_connections)
    TextView tvMaxConnections;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @Nullable
    @BindView (R.id.tv_my_reseller)
    TextView tvmyReseller;
    private String userName = "";
    private String userPassword = "";
    public MultiUserDBHandler multiuserdbhandler;
    ArrayList<ExtendedAppInfo> extendedAppInfos = new ArrayList<> ();
    private String myReseller;
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
     arg types: [android.widget.Button, com.nst.yourname.view.activity.AccountInfoActivity]
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
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_account_info);
        ButterKnife.bind(this);
        changeStatusBarColor();
        focusInitialize();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getWindow().setFlags(1024, 1024);
        initialize();
        new Thread(new CountDownRunner()).start();
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.AccountInfoActivity.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(AccountInfoActivity.this.context);
            }
        });
        this.btnBackAccountInfo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.AccountInfoActivity.AnonymousClass2 */

            public void onClick(View view) {
                AccountInfoActivity.this.onBackPressed();
            }
        });
        this.Logout.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.AccountInfoActivity.AnonymousClass3 */

            public void onClick(View view) {
                new AlertDialog.Builder(AccountInfoActivity.this, R.style.AlertDialogCustom).setTitle(AccountInfoActivity.this.getResources().getString(R.string.logout_title)).setMessage(AccountInfoActivity.this.getResources().getString(R.string.logout_message)).setPositiveButton(AccountInfoActivity.this.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    /* class com.nst.yourname.view.activity.AccountInfoActivity.AnonymousClass3.AnonymousClass2 */

                    public void onClick(DialogInterface dialogInterface, int i) {
                        Utils.logoutUser(AccountInfoActivity.this.context);
                    }
                }).setNegativeButton(AccountInfoActivity.this.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    /* class com.nst.yourname.view.activity.AccountInfoActivity.AnonymousClass3.AnonymousClass1 */

                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        this.btn_buy_now.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) this.btn_buy_now, (Activity) this));
        this.btn_buy_now.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.AccountInfoActivity.AnonymousClass4 */

            public void onClick(View view) {
                AccountInfoActivity.this.HitAPIForCheckServices();
            }
        });
    }

    public void HitAPIForCheckServices() {
        Utils.showProgressDialog(this);
        ((ApiService) ApiclientRetrofit.getApiRetrofit().create(ApiService.class)).AuthcheckServices(AppConst.BillingAPI_Username, AppConst.BillingAPI_Password, "validateCustomLogin", "yes", SharepreferenceDBHandler.getUserName(this.context), SharepreferenceDBHandler.getUserPassword(this.context)).enqueue(new Callback<LoginWHMCSModelClass>() {
            /* class com.nst.yourname.view.activity.AccountInfoActivity.AnonymousClass5 */

            @Override // retrofit2.Callback
            public void onResponse(@NonNull Call<LoginWHMCSModelClass> call, @NonNull Response<LoginWHMCSModelClass> response) {
                Utils.hideProgressDialog();
                if (!response.isSuccessful()) {
                    Toast.makeText(AccountInfoActivity.this.context, "", 0).show();
                } else if (response.body() == null || !response.body().getResult().equalsIgnoreCase("success")) {
                    Context access$000 = AccountInfoActivity.this.context;
                    Toast.makeText(access$000, "" + response.body().getMessage(), 0).show();
                } else {
                    ClientSharepreferenceHandler.setClientId(response.body().getData().getClientid().intValue(), AccountInfoActivity.this.context);
                    ClientSharepreferenceHandler.setUserEmaiId(response.body().getData().getEmail(), AccountInfoActivity.this.context);
                    ClientSharepreferenceHandler.setUserPrefix(response.body().getData().getPrefix(), AccountInfoActivity.this.context);
                    ClientSharepreferenceHandler.setUserSuffix(response.body().getData().getSuffix(), AccountInfoActivity.this.context);
                    AccountInfoActivity.this.startActivity(new Intent(AccountInfoActivity.this, ServicesDashboardActivity.class));
                    Toast.makeText(AccountInfoActivity.this.context, "successfully Login", 0).show();
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(@NonNull Call<LoginWHMCSModelClass> call, @NonNull Throwable th) {
                Utils.hideProgressDialog();
                Toast.makeText(AccountInfoActivity.this.context, "error", 0).show();
            }
        });
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    AccountInfoActivity.this.doWork();
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
            /* class com.nst.yourname.view.activity.AccountInfoActivity.AnonymousClass6 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(AccountInfoActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (AccountInfoActivity.this.time != null) {
                        AccountInfoActivity.this.time.setText(time);
                    }
                    if (AccountInfoActivity.this.date != null) {
                        AccountInfoActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    private void focusInitialize() {
        if (this.btnBackAccountInfo != null) {
            this.btnBackAccountInfo.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.btnBackAccountInfo));
            this.btnBackAccountInfo.requestFocus();
            this.btnBackAccountInfo.requestFocusFromTouch();
        }
        if (this.Logout != null) {
            this.Logout.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.Logout));
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
                    f = 1.05f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                Log.e("id is", "" + this.view.getTag());
                if (this.view.getTag().equals("1")) {
                    this.view.setBackgroundResource(R.drawable.back_btn_effect);
                } else if (this.view.getTag().equals("2")) {
                    this.view.setBackgroundResource(R.drawable.logout_btn_effect);
                }
            } else if (!z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                if (this.view.getTag().equals("1")) {
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
                } else if (this.view.getTag().equals("2")) {
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
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
    }

    private void initialize() {
        int i;
        int i2;
        this.context = this;
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.multiuserdbhandler = new MultiUserDBHandler(this.context);
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        String string = this.loginPreferencesAfterLogin.getString("username", "");
        String string2 = this.loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_EXP_DATE, "");
        String string3 = this.loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_IS_TRIAL, "");
        String string4 = this.loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_ACTIVE_CONS, "");
        String string5 = this.loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_CREATE_AT, "");
        String string6 = this.loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_MAX_CONNECTIONS, "");
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            String string7 = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0).getString("name", "");
            if (this.tvUsername != null) {
                this.tvUsername.setText(string7);
            }
        } else if (this.tvUsername != null) {
            if (!string.isEmpty()) {
                this.tvUsername.setText(string);
            } else {
                this.tvUsername.setText("--");
            }
        }
        if (!(this.tvStatus == null || this.context == null)) {
            this.tvStatus.setText(this.context.getResources().getString(R.string.active));
        }
        if (this.tvExpiryDate != null) {
            if (!string2.isEmpty()) {
                try {
                    i2 = Integer.parseInt(string2);
                } catch (NumberFormatException unused) {
                    i2 = 1;
                }
                this.tvExpiryDate.setText(new SimpleDateFormat("MMMM d, yyyy", Locale.US).format(new Date(((long) i2) * 1000)));
            } else if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                if (this.context != null) {
                    this.tvExpiryDate.setText("--");
                }
            } else if (this.context != null) {
                this.tvExpiryDate.setText(this.context.getResources().getString(R.string.unlimited));
            }
        }
        if (this.tvIsTrial != null) {
            if (string3.isEmpty()) {
                this.tvIsTrial.setText("--");
            } else if (string3.equals(AppConst.PASSWORD_UNSET)) {
                if (this.context != null) {
                    if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                        this.tvIsTrial.setText("--");
                    } else {
                        this.tvIsTrial.setText(this.context.getResources().getString(R.string.no));
                    }
                }
            } else if (string3.equals("1") && this.context != null) {
                if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                    this.tvIsTrial.setText("--");
                } else {
                    this.tvIsTrial.setText(this.context.getResources().getString(R.string.yes));
                }
            }
        }
        if (this.tvActiveConn != null) {
            if (string4.isEmpty()) {
                this.tvActiveConn.setText("--");
            } else if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                this.tvActiveConn.setText("--");
            } else {
                this.tvActiveConn.setText(string4);
            }
        }
        if (this.tvCreatedAt != null) {
            if (!string5.isEmpty()) {
                try {
                    i = Integer.parseInt(string5);
                } catch (NumberFormatException unused2) {
                    i = 1;
                }
                String format = new SimpleDateFormat("MMMM d, yyyy", Locale.US).format(new Date(((long) i) * 1000));
                if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                    this.tvCreatedAt.setText("--");
                } else {
                    this.tvCreatedAt.setText(format);
                }
            } else {
                this.tvCreatedAt.setText("--");
            }
        }
        if (this.tvMaxConnections == null) {
            return;
        }
        if (string6.isEmpty()) {
            this.tvMaxConnections.setText("--");
        } else if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            this.tvMaxConnections.setText("--");
        } else {
            this.tvMaxConnections.setText(string6);
        }
        extendedAppInfos = this.multiuserdbhandler.getAllAppExtras();

        for (int is = 0; is < extendedAppInfos.size (); is++)
        {
            myReseller = extendedAppInfos.get (is).getResellerInfo ();
        }
        tvmyReseller.setText (myReseller);
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

    public void startTvGuideActivity() {
        startActivity(new Intent(this, NewEPGActivity.class));
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        finish();
    }

    public void startImportTvGuideActivity() {
        startActivity(new Intent(this, ImportEPGActivity.class));
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        finish();
    }

    public static long getDateDiff(SimpleDateFormat simpleDateFormat, String str, String str2) {
        try {
            return TimeUnit.DAYS.convert(simpleDateFormat.parse(str2).getTime() - simpleDateFormat.parse(str).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
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

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.toolbar.inflateMenu(R.menu.menu_text_icon);
        TypedValue typedValue = new TypedValue();
        if (getTheme().resolveAttribute(16843499, typedValue, true)) {
            TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics());
        }
        for (int i = 0; i < this.toolbar.getChildCount(); i++) {
            if (this.toolbar.getChildAt(i) instanceof ActionMenuView) {
                ((Toolbar.LayoutParams) this.toolbar.getChildAt(i).getLayoutParams()).gravity = 16;
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(this, NewDashboardActivity.class));
            finish();
        }
        if (itemId == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            finish();
        }
        if (itemId == R.id.action_logout && this.context != null) {
            new AlertDialog.Builder(this.context, R.style.AlertDialogCustom).setTitle(getResources().getString(R.string.logout_title)).setMessage(getResources().getString(R.string.logout_message)).setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.AccountInfoActivity.AnonymousClass8 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(AccountInfoActivity.this.context);
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.AccountInfoActivity.AnonymousClass7 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
        if (itemId == R.id.menu_load_channels_vod) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(this.context.getResources().getString(R.string.confirm_to_refresh));
            builder.setMessage(this.context.getResources().getString(R.string.do_you_want_toproceed));
            builder.setIcon((int) R.drawable.questionmark);
            builder.setPositiveButton(this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.AccountInfoActivity.AnonymousClass9 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadChannelsAndVod(AccountInfoActivity.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.AccountInfoActivity.AnonymousClass10 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
        if (itemId == R.id.menu_load_tv_guide) {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setTitle(this.context.getResources().getString(R.string.confirm_to_refresh));
            builder2.setMessage(this.context.getResources().getString(R.string.do_you_want_toproceed));
            builder2.setIcon((int) R.drawable.questionmark);
            builder2.setPositiveButton(this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.AccountInfoActivity.AnonymousClass11 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadTvGuid(AccountInfoActivity.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.AccountInfoActivity.AnonymousClass12 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder2.show();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        Utils.appResume(this.context);
        getWindow().setFlags(1024, 1024);
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        if (this.loginPreferencesAfterLogin.getString("username", "").equals("") && this.loginPreferencesAfterLogin.getString("password", "").equals("")) {
            startActivity(new Intent(this, LoginActivity.class));
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        } else if (this.context != null) {
            onFinish();
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.tv_header_title) {
            startActivity(new Intent(this, NewDashboardActivity.class));
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        }
    }

    public void onFinish() {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
    }
}
