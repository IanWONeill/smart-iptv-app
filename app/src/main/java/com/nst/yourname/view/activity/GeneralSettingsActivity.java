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
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.presenter.XMLTVPresenter;
import java.util.Calendar;

public class GeneralSettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private int actionBarHeight;
    @BindView(R.id.subtitles_default)
    CheckBox activesubtitle;
    @BindView(R.id.appbar_toolbar)
    AppBarLayout appbarToolbar;
    @BindView(R.id.auto_start)
    CheckBox autoStart;
    @BindView(R.id.autoplay)
    CheckBox autoplay;
    @BindView(R.id.bt_save_changes)
    Button btSaveChanges;
    @BindView(R.id.btn_back_playerselection)
    Button btnBackPlayerselection;
    Bundle bundle;
    private TextView clientNameTv;
    public Context context;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive = new DatabaseUpdatedStatusDBModel();
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.et_user_agent)
    EditText etUserAgent;
    @BindView(R.id.epg_full)
    CheckBox fullEPG;
    private LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences loginPreferencesAfterLoginChannels;
    private SharedPreferences loginPreferencesAfterLoginEPG;
    private SharedPreferences loginPreferencesAfterLoginLanguage;
    private SharedPreferences loginPreferencesSharedPref_auto_start;
    private SharedPreferences loginPreferencesUserAgent;
    private SharedPreferences.Editor loginPrefsEditor;
    private SharedPreferences.Editor loginPrefsEditorAutoStart;
    private SharedPreferences.Editor loginPrefsEditorChannels;
    private SharedPreferences.Editor loginPrefsEditorEPG;
    private SharedPreferences.Editor loginPrefsEditorLanguage;
    private SharedPreferences.Editor loginPrefsEditorUserAgent;
    @BindView(R.id.logo)
    ImageView logo;
    String oldLanguage;
    @BindView(R.id.language_select)
    Spinner spinnerEPG;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    private TypedValue f6tv;
    @BindView(R.id.tv_useragent)
    TextView tv_useragent;
    private String userName = "";
    private String userPassword = "";
    private XMLTVPresenter xmltvPresenter;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle2) {
        super.onCreate(bundle2);
        loadActivty();
    }

    public void loadActivty() {
        setContentView((int) R.layout.activity_general_settings);
        ButterKnife.bind(this);
        focusInitialize();
        changeStatusBarColor();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getWindow().setFlags(1024, 1024);
        this.context = this;
        this.context = getApplication();
        this.loginPreferencesSharedPref_auto_start = this.context.getSharedPreferences(AppConst.LOGIN_PREF_AUTO_START, 0);
        this.loginPrefsEditorAutoStart = this.loginPreferencesSharedPref_auto_start.edit();
        boolean z = this.loginPreferencesSharedPref_auto_start.getBoolean(AppConst.LOGIN_PREF_AUTO_START, true);
        boolean z2 = this.loginPreferencesSharedPref_auto_start.getBoolean(AppConst.LOGIN_PREF_FULL_EPG, true);
        boolean z3 = this.loginPreferencesSharedPref_auto_start.getBoolean(AppConst.LOGIN_PREF_ACTIVE_SUBTITLES, true);
        boolean z4 = this.loginPreferencesSharedPref_auto_start.getBoolean(AppConst.LOGIN_PREF_AUTOPLAY_VIDEOS, true);
        if (z3) {
            this.activesubtitle.setChecked(true);
        } else {
            this.activesubtitle.setChecked(false);
        }
        if (!z) {
            this.autoStart.setChecked(false);
        } else {
            this.autoStart.setChecked(true);
        }
        if (!z2) {
            this.fullEPG.setChecked(false);
        } else {
            this.fullEPG.setChecked(true);
        }
        if (z4) {
            this.autoplay.setChecked(true);
        } else {
            this.autoplay.setChecked(false);
        }
        initialize();
        new Thread(new CountDownRunner()).start();
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.GeneralSettingsActivity.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(GeneralSettingsActivity.this.context);
            }
        });
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    GeneralSettingsActivity.this.doWork();
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
            /* class com.nst.yourname.view.activity.GeneralSettingsActivity.AnonymousClass2 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(GeneralSettingsActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (GeneralSettingsActivity.this.time != null) {
                        GeneralSettingsActivity.this.time.setText(time);
                    }
                    if (GeneralSettingsActivity.this.date != null) {
                        GeneralSettingsActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    private void focusInitialize() {
        if (this.btnBackPlayerselection != null) {
            this.btnBackPlayerselection.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.btnBackPlayerselection));
        }
        if (this.autoStart != null) {
            this.autoStart.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.autoStart));
        }
        if (this.etUserAgent != null) {
            this.etUserAgent.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.etUserAgent));
        }
        if (this.fullEPG != null) {
            this.fullEPG.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.fullEPG));
        }
        if (this.spinnerEPG != null) {
            this.spinnerEPG.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.spinnerEPG));
        }
        if (this.activesubtitle != null) {
            this.activesubtitle.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.activesubtitle));
        }
        if (this.autoplay != null) {
            this.autoplay.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.autoplay));
        }
        if (this.btSaveChanges != null) {
            this.btSaveChanges.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.btSaveChanges));
            this.btSaveChanges.requestFocus();
            this.btSaveChanges.requestFocusFromTouch();
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
                    performScaleXAnimation(1.03f);
                    performScaleYAnimation(1.03f);
                }
            } else if (!z) {
                if (z) {
                    f = 1.05f;
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

    private void initialize() {
        this.context = this;
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.loginPreferencesAfterLoginChannels = getSharedPreferences(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, 0);
        this.loginPreferencesAfterLoginEPG = getSharedPreferences(AppConst.LOGIN_PREF_AUTOMATION_EPG, 0);
        this.loginPreferencesUserAgent = getSharedPreferences(AppConst.LOGIN_PREF_USER_AGENT, 0);
        String string = this.loginPreferencesAfterLoginChannels.getString(AppConst.LOGIN_PREF_AUTOSTART_ON_BOOT, "");
        String string2 = this.loginPreferencesUserAgent.getString(AppConst.LOGIN_PREF_USER_AGENT, AppConst.USER_AGENT);
        if (string.equals("checked")) {
            this.autoStart.setChecked(true);
        }
        if (this.etUserAgent != null) {
            this.etUserAgent.setText(string2);
        }
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        this.loginPreferencesAfterLoginLanguage = getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, 0);
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        String string3 = this.loginPreferencesAfterLoginLanguage.getString(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, "");
        this.oldLanguage = string3;
        if (string3.equals("English")) {
            string3 = "en";
        } else if (string3.equals("Polish")) {
            string3 = "pl";
        } else if (string3.equals("Portuguese")) {
            string3 = "pt";
        } else if (string3.equals("Turkish")) {
            string3 = "tr";
        } else if (string3.equals("Croatian")) {
            string3 = "hr";
        } else if (string3.equals("Spanish")) {
            string3 = "es";
        } else if (string3.equals("Arabic")) {
            string3 = "ar";
        } else if (string3.equals("French")) {
            string3 = "fr";
        } else if (string3.equals("German")) {
            string3 = "de";
        } else if (string3.equals("Italian")) {
            string3 = "it";
        } else if (string3.equals("Romanian")) {
            string3 = "ro";
        } else if (string3.equals("Hungary")) {
            string3 = "hu";
        } else if (string3.equals("Albanian")) {
            string3 = "sq";
        }
        this.spinnerEPG.setSelection(Utils.getLanguageSelected(string3));
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
        finish();
        startActivity(new Intent(this, SettingsActivity.class));
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        Utils.appResume(this.context);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.tv_header_title) {
            startActivity(new Intent(this, NewDashboardActivity.class));
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        }
    }

    @SuppressLint({"ApplySharedPref", "CommitPrefEdits"})
    @OnClick({R.id.bt_save_changes, R.id.btn_back_playerselection})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.bt_save_changes) {
            this.loginPreferencesAfterLoginChannels = getSharedPreferences(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, 0);
            this.loginPreferencesAfterLoginEPG = getSharedPreferences(AppConst.LOGIN_PREF_AUTOSTART_ON_BOOT, 0);
            this.loginPrefsEditorChannels = this.loginPreferencesAfterLoginChannels.edit();
            this.loginPrefsEditorUserAgent = this.loginPreferencesUserAgent.edit();
            this.loginPrefsEditorEPG = this.loginPreferencesAfterLoginEPG.edit();
            if (this.autoStart.isChecked()) {
                if (this.loginPrefsEditorAutoStart != null) {
                    this.loginPrefsEditorAutoStart.putBoolean(AppConst.LOGIN_PREF_AUTO_START, true);
                }
            } else if (this.loginPrefsEditorAutoStart != null) {
                this.loginPrefsEditorAutoStart.putBoolean(AppConst.LOGIN_PREF_AUTO_START, false);
            }
            if (this.fullEPG.isChecked()) {
                if (this.loginPrefsEditorAutoStart != null) {
                    this.loginPrefsEditorAutoStart.putBoolean(AppConst.LOGIN_PREF_FULL_EPG, true);
                }
            } else if (this.loginPrefsEditorAutoStart != null) {
                this.loginPrefsEditorAutoStart.putBoolean(AppConst.LOGIN_PREF_FULL_EPG, false);
            }
            if (this.activesubtitle.isChecked()) {
                if (this.loginPrefsEditorAutoStart != null) {
                    this.loginPrefsEditorAutoStart.putBoolean(AppConst.LOGIN_PREF_ACTIVE_SUBTITLES, true);
                }
            } else if (this.loginPrefsEditorAutoStart != null) {
                this.loginPrefsEditorAutoStart.putBoolean(AppConst.LOGIN_PREF_ACTIVE_SUBTITLES, false);
            }
            if (this.autoplay.isChecked()) {
                if (this.loginPrefsEditorAutoStart != null) {
                    this.loginPrefsEditorAutoStart.putBoolean(AppConst.LOGIN_PREF_AUTOPLAY_VIDEOS, true);
                }
            } else if (this.loginPrefsEditorAutoStart != null) {
                this.loginPrefsEditorAutoStart.putBoolean(AppConst.LOGIN_PREF_AUTOPLAY_VIDEOS, false);
            }
            if (this.etUserAgent.getText().toString().equals("")) {
                if (this.loginPrefsEditorUserAgent != null) {
                    this.loginPrefsEditorUserAgent.putString(AppConst.LOGIN_PREF_USER_AGENT, AppConst.USER_AGENT);
                }
            } else if (this.loginPrefsEditorUserAgent != null) {
                this.loginPrefsEditorUserAgent.putString(AppConst.LOGIN_PREF_USER_AGENT, this.etUserAgent.getText().toString());
            }
            String str = "";
            this.loginPrefsEditor = this.loginPreferencesAfterLogin.edit();
            this.loginPrefsEditorLanguage = this.loginPreferencesAfterLoginLanguage.edit();
            if (this.loginPrefsEditorLanguage != null) {
                this.spinnerEPG.getSelectedItemPosition();
                str = String.valueOf(this.spinnerEPG.getSelectedItem());
                this.loginPrefsEditorLanguage.putString(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, String.valueOf(this.spinnerEPG.getSelectedItem()));
                SharepreferenceDBHandler.setSelectedLanguage(String.valueOf(this.spinnerEPG.getSelectedItem()), this.context);
                this.loginPrefsEditorLanguage.commit();
                Utils.setLocale(this.context, str);
            }
            this.loginPrefsEditorAutoStart.apply();
            this.loginPrefsEditorChannels.apply();
            this.loginPrefsEditorEPG.apply();
            this.loginPrefsEditorUserAgent.apply();
            if (this.etUserAgent.getText().toString().equals("")) {
                this.etUserAgent.setText(AppConst.USER_AGENT);
                Toast.makeText(this, getResources().getString(R.string.please_enter_useragent_name), 0).show();
            } else if (this.oldLanguage.equalsIgnoreCase(str)) {
                loadActivty();
                Toast.makeText(this, getResources().getString(R.string.player_setting_save), 0).show();
                onBackPressed();
            } else {
                finish();
                startActivity(new Intent(this, NewDashboardActivity.class));
                Toast.makeText(this, getResources().getString(R.string.refreshing_application), 0).show();
            }
        } else if (id == R.id.btn_back_playerselection) {
            onBackPressed();
        }
    }
}
