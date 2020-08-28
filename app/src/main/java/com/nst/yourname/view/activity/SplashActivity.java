package com.nst.yourname.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.stetho.Stetho;
import com.google.android.gms.common.ConnectionResult;
import com.nst.yourname.R;
import com.nst.yourname.WebServiceHandler.RavSharedPrefrences;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.MultiUserDBModel;
import com.nst.yourname.model.callback.LoginCallback;
import com.nst.yourname.model.database.MultiUserDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.interfaces.LoginInterface;
import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity implements LoginInterface {
    ArrayList<MultiUserDBModel> AllUsers;
    public int SPLASH_DISPLAY_LENGTH = ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED;
    AlertDialog alertDialog;
    Context context;
    private String currentVersion = "";
    Boolean flaseFromOnFailed = false;
    ImageView iv_logo;
    ImageView iv_splash_bg;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences loginPreferencesAfterLoginInfBuf;
    private SharedPreferences loginPreferencesAfterLoginLanguage;
    private SharedPreferences.Editor loginPrefsEditor;
    private MultiUserDBHandler multiuserdbhandler;
    int position = 0;
    private SharedPreferences updateversionprefrences;
    private SharedPreferences.Editor updateversionprefrencesEditor;
    VideoView videoView;

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
    public void validateLogin(LoginCallback loginCallback, String str) {
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void validateloginMultiDNS(LoginCallback loginCallback, String str, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
    }

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView((int) R.layout.activity_splash);
        this.context = this;
        this.videoView = (VideoView) findViewById(R.id.video_splash);
        Stetho.initializeWithDefaults(this);
        ((RelativeLayout) findViewById(R.id.main_layout)).setSystemUiVisibility(4871);
        this.iv_splash_bg = (ImageView) findViewById(R.id.iv_splash_bg);
        this.iv_logo = (ImageView) findViewById(R.id.iv_logo);
        this.loginPreferencesAfterLoginLanguage = getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, 0);
        String string = this.loginPreferencesAfterLoginLanguage.getString(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, "");
        if (!string.equals("")) {
            Utils.setLocale(this.context, string);
        }
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
        this.loginPrefsEditor = this.loginPreferencesAfterLogin.edit();
        if (this.loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "").equals("")) {
            this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "ts");
            this.loginPrefsEditor.apply();
        }
        if (SharepreferenceDBHandler.getisAutoPlayVideos(this.context)) {
            SharepreferenceDBHandler.setisAutoPlayVideos(true, this.context);
        }
        if (AppConst.SPLASH_SCREEN_VIDEO.booleanValue()) {
            try {
                videoLayoutVisible();
            } catch (Exception unused) {
                videoLayoutHide();
                this.SPLASH_DISPLAY_LENGTH = ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED;
                initcontrol();
            }
            this.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                /* class com.nst.yourname.view.activity.SplashActivity.AnonymousClass1 */

                public void onCompletion(MediaPlayer mediaPlayer) {
                    int unused = SplashActivity.this.SPLASH_DISPLAY_LENGTH = 0;
                    SplashActivity.this.initcontrol();
                }
            });
            this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                /* class com.nst.yourname.view.activity.SplashActivity.AnonymousClass2 */

                public void onPrepared(MediaPlayer mediaPlayer) {
                    SplashActivity.this.videoView.seekTo(SplashActivity.this.position);
                    if (SplashActivity.this.position == 0) {
                        SplashActivity.this.videoView.start();
                        return;
                    }
                    SplashActivity.this.videoView.pause();
                    SplashActivity.this.initcontrol();
                }
            });
        } else {
            videoLayoutHide();
            this.SPLASH_DISPLAY_LENGTH = ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED;
            initcontrol();
        }
        changeStatusBarColor();
        this.updateversionprefrences = this.context.getSharedPreferences(AppConst.LOGIN_PREF_UPDATE_VERSION, 0);
        RavSharedPrefrences.set_clientkey(this, "K4683dd0d195f8b1f07b3fc2b00786f52");
        RavSharedPrefrences.set_clientNotificationkey(this, "K4683dd0d195f8b1f07b3fc2b00786f52");
        RavSharedPrefrences.set_salt(this, AppConst.SALT);
        this.loginPreferencesAfterLoginLanguage = getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, 0);
        this.multiuserdbhandler = new MultiUserDBHandler(this.context);
    }

    public void initcontrol() {
        new Handler().postDelayed(new Runnable() {
            /* class com.nst.yourname.view.activity.SplashActivity.AnonymousClass3 */

            public void run() {
                SplashActivity.this.intentsForActivities();
            }
        }, (long) this.SPLASH_DISPLAY_LENGTH);
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

    public void videoLayoutVisible() {
        this.videoView.setVisibility(0);
        this.iv_logo.setVisibility(8);
        this.iv_splash_bg.setVisibility(8);
    }

    public void videoLayoutHide() {
        this.videoView.setVisibility(8);
        this.iv_logo.setVisibility(0);
        this.iv_splash_bg.setVisibility(8);
    }

    public void intentsForActivities() {
        if (AppConst.M3U_LINE_ACTIVE.booleanValue() && AppConst.M3U_API_SINGLE_USER.booleanValue()) {
            startActivity(new Intent(this, RoutingActivity.class));
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
            finish();
        } else if (AppConst.MULTIUSER_ACTIVE.booleanValue() && AppConst.MultiDNS_And_MultiUser.booleanValue() && AppConst.M3U_LINE_ACTIVE.booleanValue()) {
            startActivity(new Intent(this, MultiUserActivity.class));
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
            finish();
        } else if (!AppConst.MultiDNS_And_MultiUser.booleanValue()) {
            startActivity(new Intent(this, LoginActivity.class));
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
            finish();
        } else if (new MultiUserDBHandler(this.context).getAllUsersCount()) {
            startActivity(new Intent(this, MultiUserActivity.class));
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
            finish();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
            finish();
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.SplashActivity):void
     arg types: [android.widget.Button, com.nst.yourname.view.activity.SplashActivity]
     candidates:
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.content.Context):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.CheckAppupdateActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.LoginActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.MultiUserActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.NewDashboardActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RateUsActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RecordingActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.VodActivityNewFlowSubCategories):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.SplashActivity):void */
    public void showUpdatePopup() {
        if (this.context != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
            View inflate = LayoutInflater.from(this).inflate((int) R.layout.permission_alertbox, (ViewGroup) null);
            Button button = (Button) inflate.findViewById(R.id.btn_grant);
            button.requestFocus();
            button.setFocusableInTouchMode(true);
            Button button2 = (Button) inflate.findViewById(R.id.btn_cancel);
            Button button3 = (Button) inflate.findViewById(R.id.btn_skip);
            button3.setVisibility(0);
            button3.setTextSize(14.0f);
            button.setTextSize(14.0f);
            button2.setTextSize(14.0f);
            ((TextView) inflate.findViewById(R.id.tv_parental_password)).setText(getResources().getString(R.string.new_update_available));
            ((TextView) inflate.findViewById(R.id.tv_need_permission)).setText(getResources().getString(R.string.app_update_message));
            button.setText(getResources().getString(R.string.app_update));
            button2.setText(getResources().getString(R.string.remember_me_later));
            button.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button, this));
            button2.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button2, this));
            button3.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button3, this));
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.SplashActivity.AnonymousClass4 */

                public void onClick(View view) {
                    String packageName = SplashActivity.this.getApplicationContext().getPackageName();
                    try {
                        SplashActivity.this.startActivityForResult(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)), 101);
                    } catch (Exception unused) {
                        Toast.makeText(SplashActivity.this.context, SplashActivity.this.getResources().getString(R.string.device_not_supported), 0).show();
                    }
                    SplashActivity.this.alertDialog.dismiss();
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.SplashActivity.AnonymousClass5 */

                public void onClick(View view) {
                    int unused = SplashActivity.this.SPLASH_DISPLAY_LENGTH = 0;
                    SplashActivity.this.alertDialog.dismiss();
                    SplashActivity.this.intentsForActivities();
                }
            });
            button3.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.SplashActivity.AnonymousClass6 */

                public void onClick(View view) {
                    SharepreferenceDBHandler.setDontaskagain(true, SplashActivity.this.context);
                    AppConst.isSkip = true;
                    int unused = SplashActivity.this.SPLASH_DISPLAY_LENGTH = 0;
                    SplashActivity.this.alertDialog.dismiss();
                    SplashActivity.this.intentsForActivities();
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
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onPause() {
        super.onPause();
        String string = this.loginPreferencesAfterLoginLanguage.getString(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, "");
        if (!string.equals("")) {
            Utils.setLocale(this.context, string);
        }
    }
}
