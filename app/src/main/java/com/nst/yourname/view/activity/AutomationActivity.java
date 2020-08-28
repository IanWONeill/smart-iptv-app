package com.nst.yourname.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import java.util.Calendar;

public class AutomationActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.appbar_toolbar)
    AppBarLayout appbarToolbar;
    @BindView(R.id.bt_save_changes)
    Button btSaveChanges;
    @BindView(R.id.btn_back_playerselection)
    Button btnBackPlayerselection;
    @BindView(R.id.cb_automation_epg)
    CheckBox cbAutomationEPG;
    @BindView(R.id.cb_automation_live_vod)
    CheckBox cbAutomationLiveVod;
    public Context context;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive = new DatabaseUpdatedStatusDBModel();
    @BindView(R.id.date)
    TextView date;
    private LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesAfterLoginChannels;
    private SharedPreferences loginPreferencesAfterLoginEPG;
    private SharedPreferences.Editor loginPrefsEditorChannels;
    private SharedPreferences.Editor loginPrefsEditorEPG;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_automation);
        ButterKnife.bind(this);
        focusInitialize();
        changeStatusBarColor();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getWindow().setFlags(1024, 1024);
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.AutomationActivity.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(AutomationActivity.this.context);
            }
        });
        initialize();
        new Thread(new CountDownRunner()).start();
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    AutomationActivity.this.doWork();
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
            /* class com.nst.yourname.view.activity.AutomationActivity.AnonymousClass2 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(AutomationActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (AutomationActivity.this.time != null) {
                        AutomationActivity.this.time.setText(time);
                    }
                    if (AutomationActivity.this.date != null) {
                        AutomationActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    private void focusInitialize() {
        if (this.btSaveChanges != null) {
            this.btSaveChanges.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.btSaveChanges));
            this.btSaveChanges.requestFocus();
            this.btSaveChanges.requestFocusFromTouch();
        }
        if (this.btnBackPlayerselection != null) {
            this.btnBackPlayerselection.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.btnBackPlayerselection));
        }
        if (this.cbAutomationLiveVod != null) {
            this.cbAutomationLiveVod.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.cbAutomationLiveVod));
        }
        if (this.cbAutomationEPG != null) {
            this.cbAutomationEPG.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.cbAutomationEPG));
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
                Log.e("id is", "" + this.view.getTag());
                if (this.view.getTag().equals("1")) {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    this.view.setBackgroundResource(R.drawable.back_btn_effect);
                } else if (this.view.getTag().equals("2")) {
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                    this.view.setBackgroundResource(R.drawable.logout_btn_effect);
                } else {
                    performScaleXAnimation(1.12f);
                    performScaleYAnimation(1.12f);
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

        private void performAlphaAnimation(boolean z) {
            if (z) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "alpha", z ? 0.6f : 0.5f);
                ofFloat.setDuration(150L);
                ofFloat.start();
            }
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

    private void initialize() {
        this.context = this;
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.loginPreferencesAfterLoginChannels = getSharedPreferences(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, 0);
        this.loginPreferencesAfterLoginEPG = getSharedPreferences(AppConst.LOGIN_PREF_AUTOMATION_EPG, 0);
        String string = this.loginPreferencesAfterLoginChannels.getString(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, "");
        String string2 = this.loginPreferencesAfterLoginEPG.getString(AppConst.LOGIN_PREF_AUTOMATION_EPG, "");
        this.loginPrefsEditorChannels = this.loginPreferencesAfterLoginChannels.edit();
        this.loginPrefsEditorEPG = this.loginPreferencesAfterLoginEPG.edit();
        if (string.equalsIgnoreCase("checked")) {
            this.cbAutomationLiveVod.setChecked(true);
        } else if (string.equalsIgnoreCase("unchecked")) {
            this.cbAutomationLiveVod.setChecked(false);
        } else {
            this.loginPrefsEditorChannels.putString(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, "checked");
            this.loginPrefsEditorChannels.apply();
            this.cbAutomationLiveVod.setChecked(true);
        }
        if (string2.equalsIgnoreCase("checked")) {
            this.cbAutomationEPG.setChecked(true);
        } else if (string2.equalsIgnoreCase("unchecked")) {
            this.cbAutomationEPG.setChecked(false);
        } else {
            this.loginPrefsEditorEPG.putString(AppConst.LOGIN_PREF_AUTOMATION_EPG, "checked");
            this.loginPrefsEditorEPG.apply();
            this.cbAutomationEPG.setChecked(true);
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

    public void onClick(View view) {
        if (view.getId() == R.id.tv_header_title) {
            startActivity(new Intent(this, NewDashboardActivity.class));
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        }
    }

    @OnClick({R.id.bt_save_changes, R.id.btn_back_playerselection})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.bt_save_changes) {
            this.loginPreferencesAfterLoginChannels = getSharedPreferences(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, 0);
            this.loginPreferencesAfterLoginEPG = getSharedPreferences(AppConst.LOGIN_PREF_AUTOMATION_EPG, 0);
            this.loginPrefsEditorChannels = this.loginPreferencesAfterLoginChannels.edit();
            this.loginPrefsEditorEPG = this.loginPreferencesAfterLoginEPG.edit();
            if (this.cbAutomationLiveVod.isChecked()) {
                if (this.loginPrefsEditorChannels != null) {
                    this.loginPrefsEditorChannels.putString(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, "checked");
                }
            } else if (this.loginPrefsEditorChannels != null) {
                this.loginPrefsEditorChannels.putString(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, "unchecked");
            }
            if (this.cbAutomationEPG.isChecked()) {
                if (this.loginPrefsEditorEPG != null) {
                    this.loginPrefsEditorEPG.putString(AppConst.LOGIN_PREF_AUTOMATION_EPG, "checked");
                }
            } else if (this.loginPrefsEditorEPG != null) {
                this.loginPrefsEditorEPG.putString(AppConst.LOGIN_PREF_AUTOMATION_EPG, "unchecked");
            }
            this.loginPrefsEditorChannels.apply();
            this.loginPrefsEditorEPG.apply();
            Toast.makeText(this, getResources().getString(R.string.player_setting_save), 0).show();
            finish();
        } else if (id == R.id.btn_back_playerselection) {
            finish();
        }
    }
}
