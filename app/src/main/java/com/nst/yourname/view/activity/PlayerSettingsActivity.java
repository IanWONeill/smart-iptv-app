package com.nst.yourname.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.nst.yourname.presenter.XMLTVPresenter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class PlayerSettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private int actionBarHeight;
    @BindView(R.id.appbar_toolbar)
    AppBarLayout appbarToolbar;
    @BindView(R.id.bt_save_changes)
    Button btSaveChanges;
    @BindView(R.id.btn_back_playerselection)
    Button btnBackPlayerselection;
    @BindView(R.id.cb_infbuf)
    CheckBox cbInfBuf;
    @BindView(R.id.cb_opengl)
    CheckBox cbOpenGL;
    @BindView(R.id.cb_opensl_es)
    CheckBox cbOpenSLES;
    private TextView clientNameTv;
    public Context context;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive = new DatabaseUpdatedStatusDBModel();
    @BindView(R.id.date)
    TextView date;
    private LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences loginPreferencesAfterLoginInfBuf;
    private SharedPreferences loginPreferencesAfterLoginOPENGL;
    private SharedPreferences loginPreferencesAfterLoginOPENSL_ES;
    private SharedPreferences.Editor loginPrefsEditor;
    private SharedPreferences.Editor loginPrefsEditorInfBuf;
    private SharedPreferences.Editor loginPrefsEditorOPENGL;
    private SharedPreferences.Editor loginPrefsEditorOpenSLES;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.rb_hardware_decoder)
    RadioButton rbHardwareDecoder;
    @BindView(R.id.rb_native)
    RadioButton rbNative;
    @BindView(R.id.rb_software_decoder)
    RadioButton rbSoftwareDecoder;
    @BindView(R.id.rg_radio)
    RadioGroup rgRadio;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    private TypedValue f12tv;
    private String userName = "";
    private String userPassword = "";
    private XMLTVPresenter xmltvPresenter;

    public void startTvGuideActivity() {
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_player_settings);
        ButterKnife.bind(this);
        focusInitialize();
        changeStatusBarColor();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getWindow().setFlags(1024, 1024);
        initialize();
        new Thread(new CountDownRunner()).start();
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.PlayerSettingsActivity.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(PlayerSettingsActivity.this.context);
            }
        });
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    PlayerSettingsActivity.this.doWork();
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
            /* class com.nst.yourname.view.activity.PlayerSettingsActivity.AnonymousClass2 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(PlayerSettingsActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (PlayerSettingsActivity.this.time != null) {
                        PlayerSettingsActivity.this.time.setText(time);
                    }
                    if (PlayerSettingsActivity.this.date != null) {
                        PlayerSettingsActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    private void focusInitialize() {
        if (this.btSaveChanges != null) {
            this.btSaveChanges.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.btSaveChanges));
        }
        if (this.btnBackPlayerselection != null) {
            this.btnBackPlayerselection.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.btnBackPlayerselection));
        }
        if (this.rbNative != null) {
            this.rbNative.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rbNative));
        }
        if (this.rbHardwareDecoder != null) {
            this.rbHardwareDecoder.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rbHardwareDecoder));
        }
        if (this.rbSoftwareDecoder != null) {
            this.rbSoftwareDecoder.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rbSoftwareDecoder));
        }
        if (this.cbOpenSLES != null) {
            this.cbOpenSLES.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.cbOpenSLES));
        }
        if (this.cbOpenGL != null) {
            this.cbOpenGL.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.cbOpenGL));
        }
        if (this.cbInfBuf != null) {
            this.cbInfBuf.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.cbInfBuf));
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
        this.loginPreferencesAfterLoginOPENSL_ES = getSharedPreferences(AppConst.LOGIN_PREF_OPENSL_ES, 0);
        this.loginPreferencesAfterLoginOPENGL = getSharedPreferences(AppConst.LOGIN_PREF_OPENGL, 0);
        this.loginPreferencesAfterLoginInfBuf = getSharedPreferences(AppConst.LOGIN_PREF_INF_BUF, 0);
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_PREF_MEDIA_CODEC, 0);
        String string = this.loginPreferencesAfterLoginOPENSL_ES.getString(AppConst.LOGIN_PREF_OPENSL_ES, "");
        String string2 = this.loginPreferencesAfterLoginOPENGL.getString(AppConst.LOGIN_PREF_OPENGL, "");
        String string3 = this.loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_MEDIA_CODEC, "");
        String string4 = this.loginPreferencesAfterLoginInfBuf.getString(AppConst.LOGIN_PREF_INF_BUF, "unchecked");
        if (string3.equals(getResources().getString(R.string.native_decoder))) {
            this.rbNative.setChecked(true);
            this.rbNative.requestFocus();
        } else if (string3.equals(getResources().getString(R.string.hardware_decoder))) {
            this.rbHardwareDecoder.setChecked(true);
            this.rbHardwareDecoder.requestFocus();
        } else if (string3.equals(getResources().getString(R.string.software_decoder))) {
            this.rbSoftwareDecoder.setChecked(true);
            this.rbSoftwareDecoder.requestFocus();
        } else {
            this.rbSoftwareDecoder.setChecked(true);
            this.rbSoftwareDecoder.requestFocus();
        }
        if (string.equals("checked")) {
            this.cbOpenSLES.setChecked(true);
        }
        if (string2.equals("checked")) {
            this.cbOpenGL.setChecked(true);
        }
        if (string4.equals("checked")) {
            this.cbInfBuf.setChecked(true);
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

    public void startImportTvGuideActivity() {
        startActivity(new Intent(this, ImportEPGActivity.class));
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
                /* class com.nst.yourname.view.activity.PlayerSettingsActivity.AnonymousClass4 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(PlayerSettingsActivity.this.context);
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.PlayerSettingsActivity.AnonymousClass3 */

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
                /* class com.nst.yourname.view.activity.PlayerSettingsActivity.AnonymousClass5 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadChannelsAndVod(PlayerSettingsActivity.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.PlayerSettingsActivity.AnonymousClass6 */

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
                /* class com.nst.yourname.view.activity.PlayerSettingsActivity.AnonymousClass7 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadTvGuid(PlayerSettingsActivity.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.PlayerSettingsActivity.AnonymousClass8 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder2.show();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private boolean getChannelVODUpdateStatus() {
        if (!(this.liveStreamDBHandler == null || this.databaseUpdatedStatusDBModelLive == null)) {
            this.databaseUpdatedStatusDBModelLive = this.liveStreamDBHandler.getdateDBStatus(AppConst.DB_CHANNELS, "1");
            if (this.databaseUpdatedStatusDBModelLive != null) {
                if (this.databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState() != null && !this.databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FINISH) && !this.databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FAILED)) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    private void loadTvGuid() {
        if (this.context == null) {
            return;
        }
        if (getEPGUpdateStatus()) {
            SharedPreferences sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            if (edit != null) {
                edit.putString(AppConst.SKIP_BUTTON_PREF, "autoLoad");
                edit.apply();
                sharedPreferences.getString(AppConst.SKIP_BUTTON_PREF, "");
                new LiveStreamDBHandler(this.context).makeEmptyEPG();
                startActivity(new Intent(this.context, ImportEPGActivity.class));
            }
        } else if (this.context != null) {
            Utils.showToast(this.context, getResources().getString(R.string.upadating_tv_guide));
        }
    }

    private boolean getEPGUpdateStatus() {
        if (!(this.liveStreamDBHandler == null || this.databaseUpdatedStatusDBModelEPG == null)) {
            this.databaseUpdatedStatusDBModelEPG = this.liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, "2");
            if (this.databaseUpdatedStatusDBModelEPG != null) {
                if (this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState() == null || this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FINISH) || this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FAILED) || this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState() == null || this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals("")) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        Utils.appResume(this.context);
        getWindow().setFlags(1024, 1024);
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        if (!this.loginPreferencesAfterLogin.getString("username", "").equals("") || !this.loginPreferencesAfterLogin.getString("password", "").equals("")) {
            Context context2 = this.context;
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.tv_header_title) {
            startActivity(new Intent(this, NewDashboardActivity.class));
        }
    }

    @OnClick({R.id.bt_save_changes, R.id.btn_back_playerselection})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.bt_save_changes) {
            this.loginPreferencesAfterLoginOPENSL_ES = getSharedPreferences(AppConst.LOGIN_PREF_OPENSL_ES, 0);
            this.loginPreferencesAfterLoginOPENGL = getSharedPreferences(AppConst.LOGIN_PREF_OPENGL, 0);
            this.loginPreferencesAfterLoginInfBuf = getSharedPreferences(AppConst.LOGIN_PREF_INF_BUF, 0);
            this.loginPrefsEditorOpenSLES = this.loginPreferencesAfterLoginOPENSL_ES.edit();
            this.loginPrefsEditorOPENGL = this.loginPreferencesAfterLoginOPENGL.edit();
            this.loginPrefsEditorInfBuf = this.loginPreferencesAfterLoginInfBuf.edit();
            if (this.cbOpenSLES.isChecked()) {
                if (this.loginPrefsEditorOpenSLES != null) {
                    this.loginPrefsEditorOpenSLES.putString(AppConst.LOGIN_PREF_OPENSL_ES, "checked");
                }
            } else if (this.loginPrefsEditorOpenSLES != null) {
                this.loginPrefsEditorOpenSLES.putString(AppConst.LOGIN_PREF_OPENSL_ES, "");
            }
            if (this.cbOpenGL.isChecked()) {
                if (this.loginPrefsEditorOPENGL != null) {
                    this.loginPrefsEditorOPENGL.putString(AppConst.LOGIN_PREF_OPENGL, "checked");
                }
            } else if (this.loginPrefsEditorOPENGL != null) {
                this.loginPrefsEditorOPENGL.putString(AppConst.LOGIN_PREF_OPENGL, "");
            }
            if (this.cbInfBuf.isChecked()) {
                if (this.loginPrefsEditorInfBuf != null) {
                    this.loginPrefsEditorInfBuf.putString(AppConst.LOGIN_PREF_INF_BUF, "checked");
                }
            } else if (this.loginPrefsEditorInfBuf != null) {
                this.loginPrefsEditorInfBuf.putString(AppConst.LOGIN_PREF_INF_BUF, "unchecked");
            }
            this.loginPrefsEditorOpenSLES.apply();
            this.loginPrefsEditorOPENGL.apply();
            this.loginPrefsEditorInfBuf.apply();
            int checkedRadioButtonId = this.rgRadio.getCheckedRadioButtonId();
            this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_PREF_MEDIA_CODEC, 0);
            RadioButton radioButton = (RadioButton) findViewById(checkedRadioButtonId);
            this.loginPrefsEditor = this.loginPreferencesAfterLogin.edit();
            if (this.loginPrefsEditor != null) {
                this.loginPrefsEditor.putString(AppConst.LOGIN_PREF_MEDIA_CODEC, radioButton.getText().toString());
                this.loginPrefsEditor.apply();
                Toast.makeText(this, getResources().getString(R.string.player_setting_save), 0).show();
                finish();
                return;
            }
            Toast.makeText(this, getResources().getString(R.string.player_setting_error), 0).show();
        } else if (id == R.id.btn_back_playerselection) {
            finish();
        }
    }
}
