package com.nst.yourname.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.CheckAppUpdate;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.PasswordDBModel;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.presenter.XMLTVPresenter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    static final boolean $assertionsDisabled = false;
    private static SharedPreferences loginPreferencesSharedPref_time_format;
    int actionBarHeight;
    @BindView(R.id.card_app_update)
    CardView cardAppUpdate;
    @BindView(R.id.card_Rateus)
    CardView cardRateus;
    public PopupWindow changeSortPopUp;
    private TextView clientNameTv;
    Button closedBT;
    public Context context;
    @BindView(R.id.cv_external_players)
    CardView cvExteranlPlayers;
    @BindView(R.id.cv_player_settings)
    CardView cvplayersettingscard;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive = new DatabaseUpdatedStatusDBModel();
    @BindView(R.id.date)
    TextView date;
    public LiveStreamDBHandler liveStreamDBHandler;
    @BindView(R.id.ll_app_update)
    LinearLayout llAppUpdate;
    @BindView(R.id.ll_external_player)
    LinearLayout llExternalPlayer;
    @BindView(R.id.ll_player_selection)
    LinearLayout llPlayer;
    @BindView(R.id.ll_Rateus)
    LinearLayout ll_Rateus;
    @Nullable
    @BindView(R.id.ll_lastlayout)
    LinearLayout ll_lastlayout;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences.Editor loginPrefsEditor;
    @BindView(R.id.logo)
    ImageView logo;
    LinearLayout multi_inner;
    CardView multiscreen;
    @BindView(R.id.rl_automation)
    LinearLayout rlAutomation;
    @BindView(R.id.rl_automation_card)
    CardView rlAutomationCard;
    @BindView(R.id.rl_epg_shift)
    LinearLayout rlEPGShift;
    @BindView(R.id.rl_epg_shift_card)
    CardView rlEPGShiftCard;
    @BindView(R.id.rl_general_settings)
    LinearLayout rlGeneralSettings;
    @BindView(R.id.rl_general_settings_card)
    CardView rlGeneralSettingsCard;
    @BindView(R.id.rl_epg_channel_update_card)
    CardView rlLayoutEPGCard;
    @BindView(R.id.rl_parental)
    LinearLayout rlParental;
    @BindView(R.id.rl_parental_card)
    CardView rlParentalCard;
    @BindView(R.id.cv_player_card_)
    CardView rlPlayerCard;
    @BindView(R.id.rl_player_settings)
    LinearLayout rlPlayerSettings;
    @BindView(R.id.rl_epg_channel_update)
    LinearLayout rlRlEpgChannelUpdate;
    @BindView(R.id.rl_stream_format_card)
    CardView rlStreamCard;
    @BindView(R.id.rl_stream_format)
    LinearLayout rlStreamFormat;
    @BindView(R.id.rl_time_format_card)
    CardView rlTimeCard;
    @BindView(R.id.rl_time_format)
    LinearLayout rlTimeFormat;
    Button savePasswordBT;
    LinearLayout speed_test;
    CardView speedtest;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    private TypedValue f23tv;
    private String userName = "";
    private String userPassword = "";
    private XMLTVPresenter xmltvPresenter;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.context = this;
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            if (AppConst.New_MultiScreen.booleanValue() && AppConst.Speed_Test.booleanValue()) {
                setContentView((int) R.layout.activity_settings_m3u_multiscreen_speedtest);
                this.multiscreen = (CardView) findViewById(R.id.multiscreen);
                this.speedtest = (CardView) findViewById(R.id.speedtest);
                this.multi_inner = (LinearLayout) findViewById(R.id.multi_inner);
                this.speed_test = (LinearLayout) findViewById(R.id.speed_test);
            } else if (AppConst.New_MultiScreen.booleanValue()) {
                setContentView((int) R.layout.activity_settings_m3u_multiscreen);
                this.multiscreen = (CardView) findViewById(R.id.multiscreen);
                this.multi_inner = (LinearLayout) findViewById(R.id.multi_inner);
            } else if (AppConst.Speed_Test.booleanValue()) {
                setContentView((int) R.layout.activity_settings_m3u_speed_test);
                this.speedtest = (CardView) findViewById(R.id.speedtest);
                this.speed_test = (LinearLayout) findViewById(R.id.speed_test);
            } else {
                setContentView((int) R.layout.activity_settings_m3u);
            }
        } else if (AppConst.New_MultiScreen.booleanValue() && AppConst.Speed_Test.booleanValue()) {
            setContentView((int) R.layout.activity_settings);
            this.multiscreen = (CardView) findViewById(R.id.multiscreen);
            this.speedtest = (CardView) findViewById(R.id.speedtest);
            this.multi_inner = (LinearLayout) findViewById(R.id.multi_inner);
            this.speed_test = (LinearLayout) findViewById(R.id.speed_test);
        } else if (AppConst.New_MultiScreen.booleanValue()) {
            setContentView((int) R.layout.activity_settingsmscreen);
            this.multiscreen = (CardView) findViewById(R.id.multiscreen);
            this.multi_inner = (LinearLayout) findViewById(R.id.multi_inner);
        } else if (AppConst.Speed_Test.booleanValue()) {
            setContentView((int) R.layout.activity_settingspeedtest);
            this.speedtest = (CardView) findViewById(R.id.speedtest);
            this.speed_test = (LinearLayout) findViewById(R.id.speed_test);
        } else {
            setContentView((int) R.layout.activity_settings2);
        }
        ButterKnife.bind(this);
        if (AppConst.New_MultiScreen.booleanValue() && AppConst.Speed_Test.booleanValue()) {
            if (this.cvExteranlPlayers != null) {
                this.cvExteranlPlayers.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.cvExteranlPlayers));
            }
            if (this.multiscreen != null) {
                this.multiscreen.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.multiscreen));
            }
            if (this.speedtest != null) {
                this.speedtest.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.speedtest));
            }
            if (this.rlPlayerCard != null) {
                this.rlPlayerCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlPlayerCard));
            }
            if (this.rlParentalCard != null) {
                this.rlParentalCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlParentalCard));
            }
            if (this.rlEPGShiftCard != null) {
                this.rlEPGShiftCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlEPGShiftCard));
            }
            if (this.rlStreamCard != null) {
                this.rlStreamCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlStreamCard));
            }
            if (this.rlTimeCard != null) {
                this.rlTimeCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlTimeCard));
            }
            if (this.rlLayoutEPGCard != null) {
                this.rlLayoutEPGCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlLayoutEPGCard));
            }
            if (this.rlAutomationCard != null) {
                this.rlAutomationCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlAutomationCard));
            }
            if (this.rlGeneralSettingsCard != null) {
                this.rlGeneralSettingsCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlGeneralSettingsCard));
                if ((getResources().getConfiguration().screenLayout & 15) == 3) {
                    this.rlGeneralSettingsCard.requestFocus();
                    this.rlGeneralSettingsCard.setFocusableInTouchMode(true);
                }
                this.rlGeneralSettingsCard.requestFocus();
            }
            if (this.cvplayersettingscard != null) {
                this.cvplayersettingscard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.cvplayersettingscard));
            }
        } else if (AppConst.New_MultiScreen.booleanValue()) {
            if (this.cvExteranlPlayers != null) {
                this.cvExteranlPlayers.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.cvExteranlPlayers));
            }
            if (this.multiscreen != null) {
                this.multiscreen.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.multiscreen));
            }
            if (this.rlPlayerCard != null) {
                this.rlPlayerCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlPlayerCard));
            }
            if (this.rlParentalCard != null) {
                this.rlParentalCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlParentalCard));
            }
            if (this.rlEPGShiftCard != null) {
                this.rlEPGShiftCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlEPGShiftCard));
            }
            if (this.rlStreamCard != null) {
                this.rlStreamCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlStreamCard));
            }
            if (this.rlTimeCard != null) {
                this.rlTimeCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlTimeCard));
            }
            if (this.rlLayoutEPGCard != null) {
                this.rlLayoutEPGCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlLayoutEPGCard));
            }
            if (this.rlAutomationCard != null) {
                this.rlAutomationCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlAutomationCard));
            }
            if (this.rlGeneralSettingsCard != null) {
                this.rlGeneralSettingsCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlGeneralSettingsCard));
                if ((getResources().getConfiguration().screenLayout & 15) == 3) {
                    this.rlGeneralSettingsCard.requestFocus();
                    this.rlGeneralSettingsCard.setFocusableInTouchMode(true);
                }
                this.rlGeneralSettingsCard.requestFocus();
            }
            if (this.cvplayersettingscard != null) {
                this.cvplayersettingscard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.cvplayersettingscard));
            }
        } else if (AppConst.Speed_Test.booleanValue()) {
            if (this.cvExteranlPlayers != null) {
                this.cvExteranlPlayers.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.cvExteranlPlayers));
            }
            if (this.speedtest != null) {
                this.speedtest.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.speedtest));
            }
            if (this.rlPlayerCard != null) {
                this.rlPlayerCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlPlayerCard));
            }
            if (this.rlParentalCard != null) {
                this.rlParentalCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlParentalCard));
            }
            if (this.rlEPGShiftCard != null) {
                this.rlEPGShiftCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlEPGShiftCard));
            }
            if (this.rlStreamCard != null) {
                this.rlStreamCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlStreamCard));
            }
            if (this.rlTimeCard != null) {
                this.rlTimeCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlTimeCard));
            }
            if (this.rlLayoutEPGCard != null) {
                this.rlLayoutEPGCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlLayoutEPGCard));
            }
            if (this.rlAutomationCard != null) {
                this.rlAutomationCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlAutomationCard));
            }
            if (this.rlGeneralSettingsCard != null) {
                this.rlGeneralSettingsCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlGeneralSettingsCard));
                if ((getResources().getConfiguration().screenLayout & 15) == 3) {
                    this.rlGeneralSettingsCard.requestFocus();
                    this.rlGeneralSettingsCard.setFocusableInTouchMode(true);
                }
                this.rlGeneralSettingsCard.requestFocus();
            }
            if (this.cvplayersettingscard != null) {
                this.cvplayersettingscard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.cvplayersettingscard));
            }
        } else {
            if (this.cvExteranlPlayers != null) {
                this.cvExteranlPlayers.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.cvExteranlPlayers));
            }
            if (this.rlPlayerCard != null) {
                this.rlPlayerCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlPlayerCard));
            }
            if (this.rlParentalCard != null) {
                this.rlParentalCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlParentalCard));
            }
            if (this.rlEPGShiftCard != null) {
                this.rlEPGShiftCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlEPGShiftCard));
            }
            if (this.rlStreamCard != null) {
                this.rlStreamCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlStreamCard));
            }
            if (this.rlTimeCard != null) {
                this.rlTimeCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlTimeCard));
            }
            if (this.rlLayoutEPGCard != null) {
                this.rlLayoutEPGCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlLayoutEPGCard));
            }
            if (this.rlAutomationCard != null) {
                this.rlAutomationCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlAutomationCard));
            }
            if (this.rlGeneralSettingsCard != null) {
                this.rlGeneralSettingsCard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.rlGeneralSettingsCard));
                if ((getResources().getConfiguration().screenLayout & 15) == 3) {
                    this.rlGeneralSettingsCard.requestFocus();
                    this.rlGeneralSettingsCard.setFocusableInTouchMode(true);
                }
            }
            if (this.cvplayersettingscard != null) {
                this.cvplayersettingscard.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.cvplayersettingscard));
            }
        }
        boolean isAppExistOnPlayStore = SharepreferenceDBHandler.getIsAppExistOnPlayStore(this.context);
        if (!isAppExistOnPlayStore) {
            new CheckAppUpdate(this).execute(new Void[0]);
        }
        if (isAppExistOnPlayStore) {
            this.cardRateus.setVisibility(0);
            this.ll_lastlayout.setVisibility(0);
            this.cardAppUpdate.setVisibility(0);
            this.cardAppUpdate.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.cardAppUpdate));
            this.cardRateus.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.cardRateus));
        }
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.SettingsActivity.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(SettingsActivity.this.context);
            }
        });
        getWindow().setFlags(1024, 1024);
        changeStatusBarColor();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        makeButtonsClickable();
        initialize();
        new Thread(new CountDownRunner()).start();
    }

    private void makeButtonsClickable() {
        if (AppConst.New_MultiScreen.booleanValue() && AppConst.Speed_Test.booleanValue()) {
            this.rlPlayerCard.setOnClickListener(this);
            this.rlPlayerSettings.setOnClickListener(this);
            this.cvplayersettingscard.setOnClickListener(this);
            this.rlParental.setOnClickListener(this);
            this.rlParentalCard.setOnClickListener(this);
            this.rlEPGShift.setOnClickListener(this);
            this.rlEPGShiftCard.setOnClickListener(this);
            this.rlStreamFormat.setOnClickListener(this);
            this.rlStreamCard.setOnClickListener(this);
            this.rlTimeFormat.setOnClickListener(this);
            this.rlTimeCard.setOnClickListener(this);
            this.rlRlEpgChannelUpdate.setOnClickListener(this);
            this.rlLayoutEPGCard.setOnClickListener(this);
            this.rlAutomation.setOnClickListener(this);
            this.rlAutomationCard.setOnClickListener(this);
            this.rlGeneralSettings.setOnClickListener(this);
            this.rlGeneralSettingsCard.setOnClickListener(this);
            this.llAppUpdate.setOnClickListener(this);
            this.ll_Rateus.setOnClickListener(this);
            this.cardAppUpdate.setOnClickListener(this);
            this.cardRateus.setOnClickListener(this);
            this.cvExteranlPlayers.setOnClickListener(this);
            this.llExternalPlayer.setOnClickListener(this);
            this.multiscreen.setOnClickListener(this);
            this.multi_inner.setOnClickListener(this);
            this.speed_test.setOnClickListener(this);
            this.speedtest.setOnClickListener(this);
        } else if (AppConst.New_MultiScreen.booleanValue()) {
            this.rlPlayerCard.setOnClickListener(this);
            this.rlPlayerSettings.setOnClickListener(this);
            this.cvplayersettingscard.setOnClickListener(this);
            this.rlParental.setOnClickListener(this);
            this.rlParentalCard.setOnClickListener(this);
            this.rlEPGShift.setOnClickListener(this);
            this.rlEPGShiftCard.setOnClickListener(this);
            this.rlStreamFormat.setOnClickListener(this);
            this.rlStreamCard.setOnClickListener(this);
            this.rlTimeFormat.setOnClickListener(this);
            this.rlTimeCard.setOnClickListener(this);
            this.rlRlEpgChannelUpdate.setOnClickListener(this);
            this.rlLayoutEPGCard.setOnClickListener(this);
            this.rlAutomation.setOnClickListener(this);
            this.rlAutomationCard.setOnClickListener(this);
            this.rlGeneralSettings.setOnClickListener(this);
            this.rlGeneralSettingsCard.setOnClickListener(this);
            this.llAppUpdate.setOnClickListener(this);
            this.ll_Rateus.setOnClickListener(this);
            this.cardAppUpdate.setOnClickListener(this);
            this.cardRateus.setOnClickListener(this);
            this.cvExteranlPlayers.setOnClickListener(this);
            this.llExternalPlayer.setOnClickListener(this);
            this.multiscreen.setOnClickListener(this);
            this.multi_inner.setOnClickListener(this);
        } else if (AppConst.Speed_Test.booleanValue()) {
            this.rlPlayerCard.setOnClickListener(this);
            this.rlPlayerSettings.setOnClickListener(this);
            this.cvplayersettingscard.setOnClickListener(this);
            this.rlParental.setOnClickListener(this);
            this.rlParentalCard.setOnClickListener(this);
            this.rlEPGShift.setOnClickListener(this);
            this.rlEPGShiftCard.setOnClickListener(this);
            this.rlStreamFormat.setOnClickListener(this);
            this.rlStreamCard.setOnClickListener(this);
            this.rlTimeFormat.setOnClickListener(this);
            this.rlTimeCard.setOnClickListener(this);
            this.rlRlEpgChannelUpdate.setOnClickListener(this);
            this.rlLayoutEPGCard.setOnClickListener(this);
            this.rlAutomation.setOnClickListener(this);
            this.rlAutomationCard.setOnClickListener(this);
            this.rlGeneralSettings.setOnClickListener(this);
            this.rlGeneralSettingsCard.setOnClickListener(this);
            this.llAppUpdate.setOnClickListener(this);
            this.ll_Rateus.setOnClickListener(this);
            this.cardAppUpdate.setOnClickListener(this);
            this.cardRateus.setOnClickListener(this);
            this.cvExteranlPlayers.setOnClickListener(this);
            this.llExternalPlayer.setOnClickListener(this);
            this.speed_test.setOnClickListener(this);
            this.speedtest.setOnClickListener(this);
        } else {
            this.rlPlayerCard.setOnClickListener(this);
            this.rlPlayerSettings.setOnClickListener(this);
            this.cvplayersettingscard.setOnClickListener(this);
            this.rlParental.setOnClickListener(this);
            this.rlParentalCard.setOnClickListener(this);
            this.rlEPGShift.setOnClickListener(this);
            this.rlEPGShiftCard.setOnClickListener(this);
            this.rlStreamFormat.setOnClickListener(this);
            this.rlStreamCard.setOnClickListener(this);
            this.rlTimeFormat.setOnClickListener(this);
            this.rlTimeCard.setOnClickListener(this);
            this.rlRlEpgChannelUpdate.setOnClickListener(this);
            this.rlLayoutEPGCard.setOnClickListener(this);
            this.rlAutomation.setOnClickListener(this);
            this.rlAutomationCard.setOnClickListener(this);
            this.rlGeneralSettings.setOnClickListener(this);
            this.rlGeneralSettingsCard.setOnClickListener(this);
            this.llAppUpdate.setOnClickListener(this);
            this.ll_Rateus.setOnClickListener(this);
            this.cardAppUpdate.setOnClickListener(this);
            this.cardRateus.setOnClickListener(this);
            this.cvExteranlPlayers.setOnClickListener(this);
            this.llExternalPlayer.setOnClickListener(this);
        }
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    SettingsActivity.this.doWork();
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
            /* class com.nst.yourname.view.activity.SettingsActivity.AnonymousClass2 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(SettingsActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (SettingsActivity.this.time != null) {
                        SettingsActivity.this.time.setText(time);
                    }
                    if (SettingsActivity.this.date != null) {
                        SettingsActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    private void initialize() {
        this.context = this;
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
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
                    f = 1.12f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                Log.e("id is", "" + this.view.getTag());
                if (AppConst.New_MultiScreen.booleanValue() && AppConst.Speed_Test.booleanValue()) {
                    if (this.view.getTag() != null && this.view.getTag().equals("2")) {
                        SettingsActivity.this.rlParental.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("3")) {
                        SettingsActivity.this.rlEPGShift.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (!SharepreferenceDBHandler.getCurrentAPPType(SettingsActivity.this.context).equals(AppConst.TYPE_M3U) && this.view.getTag() != null && this.view.getTag().equals("5")) {
                        SettingsActivity.this.rlStreamFormat.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("6")) {
                        SettingsActivity.this.rlTimeFormat.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_CAT_ID)) {
                        SettingsActivity.this.rlRlEpgChannelUpdate.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID)) {
                        SettingsActivity.this.savePasswordBT.setBackgroundResource(R.drawable.back_btn_effect);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("9")) {
                        SettingsActivity.this.closedBT.setBackgroundResource(R.drawable.logout_btn_effect);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("10")) {
                        SettingsActivity.this.rlAutomation.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("11")) {
                        SettingsActivity.this.rlGeneralSettings.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("12")) {
                        SettingsActivity.this.llPlayer.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("13")) {
                        SettingsActivity.this.rlPlayerSettings.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("14")) {
                        SettingsActivity.this.ll_Rateus.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("15")) {
                        SettingsActivity.this.llAppUpdate.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("16")) {
                        SettingsActivity.this.llExternalPlayer.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("18")) {
                        SettingsActivity.this.multi_inner.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("19")) {
                        SettingsActivity.this.speed_test.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                } else if (AppConst.New_MultiScreen.booleanValue()) {
                    if (this.view.getTag() != null && this.view.getTag().equals("2")) {
                        SettingsActivity.this.rlParental.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("3")) {
                        SettingsActivity.this.rlEPGShift.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (!SharepreferenceDBHandler.getCurrentAPPType(SettingsActivity.this.context).equals(AppConst.TYPE_M3U) && this.view.getTag() != null && this.view.getTag().equals("5")) {
                        SettingsActivity.this.rlStreamFormat.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("6")) {
                        SettingsActivity.this.rlTimeFormat.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_CAT_ID)) {
                        SettingsActivity.this.rlRlEpgChannelUpdate.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID)) {
                        SettingsActivity.this.savePasswordBT.setBackgroundResource(R.drawable.back_btn_effect);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("9")) {
                        SettingsActivity.this.closedBT.setBackgroundResource(R.drawable.logout_btn_effect);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("10")) {
                        SettingsActivity.this.rlAutomation.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("11")) {
                        SettingsActivity.this.rlGeneralSettings.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("12")) {
                        SettingsActivity.this.llPlayer.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("13")) {
                        SettingsActivity.this.rlPlayerSettings.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("14")) {
                        SettingsActivity.this.ll_Rateus.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("15")) {
                        SettingsActivity.this.llAppUpdate.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("16")) {
                        SettingsActivity.this.llExternalPlayer.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("18")) {
                        SettingsActivity.this.multi_inner.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                } else if (AppConst.Speed_Test.booleanValue()) {
                    if (this.view.getTag() != null && this.view.getTag().equals("2")) {
                        SettingsActivity.this.rlParental.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("3")) {
                        SettingsActivity.this.rlEPGShift.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (!SharepreferenceDBHandler.getCurrentAPPType(SettingsActivity.this.context).equals(AppConst.TYPE_M3U) && this.view.getTag() != null && this.view.getTag().equals("5")) {
                        SettingsActivity.this.rlStreamFormat.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("6")) {
                        SettingsActivity.this.rlTimeFormat.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_CAT_ID)) {
                        SettingsActivity.this.rlRlEpgChannelUpdate.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID)) {
                        SettingsActivity.this.savePasswordBT.setBackgroundResource(R.drawable.back_btn_effect);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("9")) {
                        SettingsActivity.this.closedBT.setBackgroundResource(R.drawable.logout_btn_effect);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("10")) {
                        SettingsActivity.this.rlAutomation.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("11")) {
                        SettingsActivity.this.rlGeneralSettings.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("12")) {
                        SettingsActivity.this.llPlayer.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("13")) {
                        SettingsActivity.this.rlPlayerSettings.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("14")) {
                        SettingsActivity.this.ll_Rateus.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("15")) {
                        SettingsActivity.this.llAppUpdate.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("16")) {
                        SettingsActivity.this.llExternalPlayer.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("19")) {
                        SettingsActivity.this.speed_test.setBackgroundResource(R.drawable.setting_checkbox_focused);
                    }
                } else {
                    if (this.view.getTag() != null && this.view.getTag().equals("2")) {
                        SettingsActivity.this.rlParental.setBackgroundResource(R.drawable.shape_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("3")) {
                        SettingsActivity.this.rlEPGShift.setBackgroundResource(R.drawable.shape_checkbox_focused);
                    }
                    if (!SharepreferenceDBHandler.getCurrentAPPType(SettingsActivity.this.context).equals(AppConst.TYPE_M3U) && this.view.getTag() != null && this.view.getTag().equals("5")) {
                        SettingsActivity.this.rlStreamFormat.setBackgroundResource(R.drawable.shape_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("6")) {
                        SettingsActivity.this.rlTimeFormat.setBackgroundResource(R.drawable.shape_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_CAT_ID)) {
                        SettingsActivity.this.rlRlEpgChannelUpdate.setBackgroundResource(R.drawable.shape_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID)) {
                        SettingsActivity.this.savePasswordBT.setBackgroundResource(R.drawable.back_btn_effect);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("9")) {
                        SettingsActivity.this.closedBT.setBackgroundResource(R.drawable.logout_btn_effect);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("10")) {
                        SettingsActivity.this.rlAutomation.setBackgroundResource(R.drawable.shape_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("11")) {
                        SettingsActivity.this.rlGeneralSettings.setBackgroundResource(R.drawable.shape_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("12")) {
                        SettingsActivity.this.llPlayer.setBackgroundResource(R.drawable.shape_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("13")) {
                        SettingsActivity.this.rlPlayerSettings.setBackgroundResource(R.drawable.shape_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("14")) {
                        SettingsActivity.this.llAppUpdate.setBackgroundResource(R.drawable.shape_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("15")) {
                        SettingsActivity.this.ll_Rateus.setBackgroundResource(R.drawable.shape_checkbox_focused);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("16")) {
                        SettingsActivity.this.llExternalPlayer.setBackgroundResource(R.drawable.shape_checkbox_focused);
                    }
                }
            } else if (!z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                performAlphaAnimation(z);
                if (AppConst.New_MultiScreen.booleanValue() && AppConst.Speed_Test.booleanValue()) {
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("2"))) {
                        SettingsActivity.this.rlParental.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("3"))) {
                        SettingsActivity.this.rlEPGShift.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!SharepreferenceDBHandler.getCurrentAPPType(SettingsActivity.this.context).equals(AppConst.TYPE_M3U) && this.view != null && this.view.getTag() != null && this.view.getTag().equals("5")) {
                        SettingsActivity.this.rlStreamFormat.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("6"))) {
                        SettingsActivity.this.rlTimeFormat.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_CAT_ID))) {
                        SettingsActivity.this.rlRlEpgChannelUpdate.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID))) {
                        SettingsActivity.this.savePasswordBT.setBackgroundResource(R.drawable.black_button_dark);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("9"))) {
                        SettingsActivity.this.closedBT.setBackgroundResource(R.drawable.black_button_dark);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("10"))) {
                        SettingsActivity.this.rlAutomation.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("11"))) {
                        SettingsActivity.this.rlGeneralSettings.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("12"))) {
                        SettingsActivity.this.llPlayer.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("13"))) {
                        SettingsActivity.this.rlPlayerSettings.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("14"))) {
                        SettingsActivity.this.ll_Rateus.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("15"))) {
                        SettingsActivity.this.llAppUpdate.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("16")) {
                        SettingsActivity.this.llExternalPlayer.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("18")) {
                        SettingsActivity.this.multi_inner.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("19")) {
                        SettingsActivity.this.speed_test.setBackgroundResource(R.drawable.ripple_white);
                    }
                } else if (AppConst.New_MultiScreen.booleanValue()) {
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("2"))) {
                        SettingsActivity.this.rlParental.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("3"))) {
                        SettingsActivity.this.rlEPGShift.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!SharepreferenceDBHandler.getCurrentAPPType(SettingsActivity.this.context).equals(AppConst.TYPE_M3U) && this.view != null && this.view.getTag() != null && this.view.getTag().equals("5")) {
                        SettingsActivity.this.rlStreamFormat.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("6"))) {
                        SettingsActivity.this.rlTimeFormat.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_CAT_ID))) {
                        SettingsActivity.this.rlRlEpgChannelUpdate.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID))) {
                        SettingsActivity.this.savePasswordBT.setBackgroundResource(R.drawable.black_button_dark);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("9"))) {
                        SettingsActivity.this.closedBT.setBackgroundResource(R.drawable.black_button_dark);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("10"))) {
                        SettingsActivity.this.rlAutomation.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("11"))) {
                        SettingsActivity.this.rlGeneralSettings.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("12"))) {
                        SettingsActivity.this.llPlayer.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("13"))) {
                        SettingsActivity.this.rlPlayerSettings.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("14"))) {
                        SettingsActivity.this.ll_Rateus.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("15"))) {
                        SettingsActivity.this.llAppUpdate.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("16")) {
                        SettingsActivity.this.llExternalPlayer.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("18")) {
                        SettingsActivity.this.multi_inner.setBackgroundResource(R.drawable.ripple_white);
                    }
                } else if (AppConst.Speed_Test.booleanValue()) {
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("2"))) {
                        SettingsActivity.this.rlParental.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("3"))) {
                        SettingsActivity.this.rlEPGShift.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!SharepreferenceDBHandler.getCurrentAPPType(SettingsActivity.this.context).equals(AppConst.TYPE_M3U) && this.view != null && this.view.getTag() != null && this.view.getTag().equals("5")) {
                        SettingsActivity.this.rlStreamFormat.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("6"))) {
                        SettingsActivity.this.rlTimeFormat.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_CAT_ID))) {
                        SettingsActivity.this.rlRlEpgChannelUpdate.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID))) {
                        SettingsActivity.this.savePasswordBT.setBackgroundResource(R.drawable.black_button_dark);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("9"))) {
                        SettingsActivity.this.closedBT.setBackgroundResource(R.drawable.black_button_dark);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("10"))) {
                        SettingsActivity.this.rlAutomation.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("11"))) {
                        SettingsActivity.this.rlGeneralSettings.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("12"))) {
                        SettingsActivity.this.llPlayer.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("13"))) {
                        SettingsActivity.this.rlPlayerSettings.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("14"))) {
                        SettingsActivity.this.ll_Rateus.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("15"))) {
                        SettingsActivity.this.llAppUpdate.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("16")) {
                        SettingsActivity.this.llExternalPlayer.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("19")) {
                        SettingsActivity.this.speed_test.setBackgroundResource(R.drawable.ripple_white);
                    }
                } else {
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("2"))) {
                        SettingsActivity.this.rlParental.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("3"))) {
                        SettingsActivity.this.rlEPGShift.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!SharepreferenceDBHandler.getCurrentAPPType(SettingsActivity.this.context).equals(AppConst.TYPE_M3U) && this.view != null && this.view.getTag() != null && this.view.getTag().equals("5")) {
                        SettingsActivity.this.rlStreamFormat.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("6"))) {
                        SettingsActivity.this.rlTimeFormat.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_CAT_ID))) {
                        SettingsActivity.this.rlRlEpgChannelUpdate.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID))) {
                        SettingsActivity.this.savePasswordBT.setBackgroundResource(R.drawable.black_button_dark);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("9"))) {
                        SettingsActivity.this.closedBT.setBackgroundResource(R.drawable.black_button_dark);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("10"))) {
                        SettingsActivity.this.rlAutomation.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("11"))) {
                        SettingsActivity.this.rlGeneralSettings.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("12"))) {
                        SettingsActivity.this.llPlayer.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("13"))) {
                        SettingsActivity.this.rlPlayerSettings.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("14"))) {
                        SettingsActivity.this.llAppUpdate.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("15"))) {
                        SettingsActivity.this.ll_Rateus.setBackgroundResource(R.drawable.ripple_white);
                    }
                    if (this.view.getTag() != null && this.view.getTag().equals("16")) {
                        SettingsActivity.this.llExternalPlayer.setBackgroundResource(R.drawable.ripple_white);
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

    @SuppressLint({"InlinedApi"})
    private void changeStatusBarColor() {
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        finish();
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    public void startTvGuideActivity() {
        startActivity(new Intent(this, NewEPGActivity.class));
        finish();
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
                /* class com.nst.yourname.view.activity.SettingsActivity.AnonymousClass4 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(SettingsActivity.this.context);
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SettingsActivity.AnonymousClass3 */

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
                /* class com.nst.yourname.view.activity.SettingsActivity.AnonymousClass5 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadChannelsAndVod(SettingsActivity.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SettingsActivity.AnonymousClass6 */

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
                /* class com.nst.yourname.view.activity.SettingsActivity.AnonymousClass7 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadTvGuid(SettingsActivity.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SettingsActivity.AnonymousClass8 */

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
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        Utils.appResume(this.context);
        new Thread(new CountDownRunner()).start();
        getWindow().setFlags(1024, 1024);
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        if (!this.loginPreferencesAfterLogin.getString("username", "").equals("") || !this.loginPreferencesAfterLogin.getString("password", "").equals("")) {
            Context context2 = this.context;
            this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
            if (this.loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER, getResources().getString(R.string.ijk_player)).equals(getResources().getString(R.string.ijk_player))) {
                if (this.cvplayersettingscard != null) {
                    this.cvplayersettingscard.setVisibility(0);
                }
            } else if (this.cvplayersettingscard != null) {
                this.cvplayersettingscard.setVisibility(0);
            }
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        loginPreferencesSharedPref_time_format = getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
        loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "");
    }

    private void logoutUser() {
        Toast.makeText(this, getResources().getString(R.string.logged_out), 0).show();
        Intent intent = new Intent(this, LoginActivity.class);
        SharedPreferences.Editor edit = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).edit();
        edit.clear();
        edit.apply();
        startActivity(intent);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_Rateus:
            case R.id.ll_Rateus:
                startActivity(new Intent(this, RateUsActivity.class));
                return;
            case R.id.card_app_update:
            case R.id.ll_app_update:
                startActivity(new Intent(this, CheckAppupdateActivity.class));
                return;
            case R.id.cv_external_players:
            case R.id.ll_external_player:
                startActivity(new Intent(this, AddedExternalPlayerActivity.class));
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            case R.id.cv_player_card_:
            case R.id.rl_player:
                startActivity(new Intent(this, PlayerSelectionActivity.class));
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            case R.id.cv_player_settings:
            case R.id.rl_player_settings:
                startActivity(new Intent(this, PlayerSettingsActivity.class));
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            case R.id.multi_inner:
            case R.id.multiscreen:
                startActivity(new Intent(this, MultiSettingActivity.class));
                return;
            case R.id.rl_automation:
            case R.id.rl_automation_card:
                startActivity(new Intent(this, AutomationActivity.class));
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            case R.id.rl_epg_channel_update:
            case R.id.rl_epg_channel_update_card:
                startActivity(new Intent(this, EPGChannelUpdateActivity.class));
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            case R.id.rl_epg_shift:
            case R.id.rl_epg_shift_card:
                startActivity(new Intent(this, EPGTimeShiftActivity.class));
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            case R.id.rl_general_settings:
            case R.id.rl_general_settings_card:
                finish();
                startActivity(new Intent(this, GeneralSettingsActivity.class));
                return;
            case R.id.rl_parental:
            case R.id.rl_parental_card:
                String string = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).getString("username", "");
                this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
                ArrayList<PasswordDBModel> allPassword = this.liveStreamDBHandler.getAllPassword(SharepreferenceDBHandler.getUserID(this.context));
                String str = "";
                String str2 = "";
                if (allPassword != null) {
                    Iterator<PasswordDBModel> it = allPassword.iterator();
                    while (it.hasNext()) {
                        PasswordDBModel next = it.next();
                        if (next.getUserDetail().equals(string) && !next.getUserPassword().isEmpty()) {
                            str = next.getUserDetail();
                            str2 = next.getUserPassword();
                        }
                    }
                }
                String str3 = str;
                String str4 = str2;
                if (str3 != null && !str3.equals("") && !str3.isEmpty()) {
                    passwordConfirmationPopUp(this, 100, string, str3, str4);
                    return;
                } else if (!string.isEmpty() && !string.equals("")) {
                    showSortPopup(this, 100, string);
                    return;
                } else {
                    return;
                }
            case R.id.rl_stream_format:
            case R.id.rl_stream_format_card:
                startActivity(new Intent(this, StreamFormatActivity.class));
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            case R.id.rl_time_format:
            case R.id.rl_time_format_card:
                startActivity(new Intent(this, TimeFormatActivity.class));
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            case R.id.speed_test:
            case R.id.speedtest:
                startActivity(new Intent(this, SpeedTestActivity.class));
                return;
            default:
                return;
        }
    }

    private void passwordConfirmationPopUp(final SettingsActivity settingsActivity, int i, String str, String str2, final String str3) {
        View inflate = ((LayoutInflater) settingsActivity.getSystemService("layout_inflater")).inflate((int) R.layout.view_password_verification, (RelativeLayout) settingsActivity.findViewById(R.id.rl_password_verification));
        this.changeSortPopUp = new PopupWindow(settingsActivity);
        this.changeSortPopUp.setContentView(inflate);
        this.changeSortPopUp.setWidth(-1);
        this.changeSortPopUp.setHeight(-1);
        this.changeSortPopUp.setFocusable(true);
        this.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
        this.savePasswordBT = (Button) inflate.findViewById(R.id.bt_save_password);
        this.closedBT = (Button) inflate.findViewById(R.id.bt_close);
        if (this.savePasswordBT != null) {
            this.savePasswordBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.savePasswordBT));
        }
        if (this.closedBT != null) {
            this.closedBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.closedBT));
        }
        final EditText editText = (EditText) inflate.findViewById(R.id.et_password);
        final String[] strArr = new String[1];
        editText.requestFocus();
        this.closedBT.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.SettingsActivity.AnonymousClass9 */

            public void onClick(View view) {
                SettingsActivity.this.changeSortPopUp.dismiss();
            }
        });
        this.savePasswordBT.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.SettingsActivity.AnonymousClass10 */

            public void onClick(View view) {
                if (fieldsCheck()) {
                    passwordValidation(comparePassword(str3));
                }
            }

            private void passwordValidation(boolean z) {
                if (z) {
                    SettingsActivity.this.changeSortPopUp.dismiss();
                    startActity();
                    return;
                }
                if (settingsActivity != null) {
                    Toast.makeText(settingsActivity, SettingsActivity.this.getResources().getString(R.string.parental_invalid_password), 1).show();
                }
                editText.getText().clear();
            }

            private boolean fieldsCheck() {
                strArr[0] = String.valueOf(editText.getText());
                if (strArr != null && strArr[0].equals("")) {
                    Toast.makeText(settingsActivity, SettingsActivity.this.getResources().getString(R.string.enter_password_error), 1).show();
                    return false;
                } else if (strArr == null || strArr[0].equals("")) {
                    return false;
                } else {
                    return true;
                }
            }

            private boolean comparePassword(String str) {
                strArr[0] = String.valueOf(editText.getText());
                if (strArr[0] == null || strArr[0].equals("") || strArr[0].isEmpty() || str == null || str.isEmpty() || str.equals("") || !strArr[0].equals(str)) {
                    return false;
                }
                return true;
            }

            private void startActity() {
                SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, ParentalControlActivitity.class));
            }
        });
    }

    @SuppressLint({"RtlHardcoded"})
    private void showSortPopup(final Activity activity, int i, final String str) {
        try {
            View inflate = ((LayoutInflater) activity.getSystemService("layout_inflater")).inflate((int) R.layout.view_password_prompt, (RelativeLayout) activity.findViewById(R.id.rl_password_prompt));
            this.changeSortPopUp = new PopupWindow(activity);
            this.changeSortPopUp.setContentView(inflate);
            this.changeSortPopUp.setWidth(-1);
            this.changeSortPopUp.setHeight(-1);
            this.changeSortPopUp.setFocusable(true);
            this.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
            this.savePasswordBT = (Button) inflate.findViewById(R.id.bt_save_password);
            this.closedBT = (Button) inflate.findViewById(R.id.bt_close);
            if (this.savePasswordBT != null) {
                this.savePasswordBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.savePasswordBT));
            }
            if (this.closedBT != null) {
                this.closedBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.closedBT));
            }
            final EditText editText = (EditText) inflate.findViewById(R.id.tv_password);
            final EditText editText2 = (EditText) inflate.findViewById(R.id.tv_confirm_password);
            if (activity.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, 0).getString(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, "English").equalsIgnoreCase("Arabic")) {
                editText.setGravity(21);
                editText2.setGravity(21);
            }
            final String[] strArr = new String[1];
            final String[] strArr2 = new String[1];
            this.closedBT.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.SettingsActivity.AnonymousClass11 */

                public void onClick(View view) {
                    SettingsActivity.this.changeSortPopUp.dismiss();
                }
            });
            this.savePasswordBT.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.SettingsActivity.AnonymousClass12 */

                public void onClick(View view) {
                    if (fieldsCheck()) {
                        setPassword(comparePassword());
                    }
                }

                private boolean fieldsCheck() {
                    strArr[0] = String.valueOf(editText.getText());
                    strArr2[0] = String.valueOf(editText2.getText());
                    if (strArr != null && strArr[0].equals("")) {
                        Toast.makeText(activity, SettingsActivity.this.getResources().getString(R.string.enter_password_error), 1).show();
                        return false;
                    } else if (strArr != null && !strArr[0].equals("") && strArr2 != null && strArr2[0].equals("")) {
                        Toast.makeText(activity, SettingsActivity.this.getResources().getString(R.string.parental_confirm_password), 1).show();
                        return false;
                    } else if (strArr == null || strArr2 == null || strArr[0].equals("") || strArr2[0].equals("")) {
                        return false;
                    } else {
                        return true;
                    }
                }

                private void setPassword(boolean z) {
                    if (z) {
                        PasswordDBModel passwordDBModel = new PasswordDBModel();
                        passwordDBModel.setUserPassword(String.valueOf(strArr[0]));
                        passwordDBModel.setUserDetail(str);
                        passwordDBModel.setUserId(SharepreferenceDBHandler.getUserID(activity));
                        if (SettingsActivity.this.liveStreamDBHandler != null) {
                            SettingsActivity.this.liveStreamDBHandler.addPassword(passwordDBModel);
                            SettingsActivity.this.changeSortPopUp.dismiss();
                            startActity();
                            return;
                        }
                        return;
                    }
                    if (activity != null) {
                        Toast.makeText(activity, SettingsActivity.this.getResources().getString(R.string.parental_password_confirm_password_match_error), 1).show();
                    }
                    editText.getText().clear();
                    editText2.getText().clear();
                }

                private boolean comparePassword() {
                    strArr[0] = String.valueOf(editText.getText());
                    strArr2[0] = String.valueOf(editText2.getText());
                    if (strArr == null || strArr[0].equals("") || strArr2 == null || strArr2[0].equals("") || !strArr[0].equals(strArr2[0])) {
                        return false;
                    }
                    return true;
                }

                private void startActity() {
                    SettingsActivity.this.startActivity(new Intent(activity, ParentalControlActivitity.class));
                }
            });
        } catch (Exception unused) {
        }
    }
}
