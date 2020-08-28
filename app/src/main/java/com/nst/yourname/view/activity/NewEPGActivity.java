package com.nst.yourname.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.PasswordStatusDBModel;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.presenter.LoginPresenter;
import com.nst.yourname.view.adapter.NewEPGCategoriesAdapter;
import com.nst.yourname.view.ijkplayer.application.Settings;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class NewEPGActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String JSON = "";
    int actionBarHeight;
    @BindView(R.id.appbar_toolbar)
    AppBarLayout appbarToolbar;
    private ArrayList<PasswordStatusDBModel> categoryWithPasword;
    TextView clientNameTv;
    public Context context;
    DatabaseHandler database;
    @BindView(R.id.date)
    TextView date;
    private String getActiveLiveStreamCategoryId = "";
    private String getActiveLiveStreamCategoryName = "";
    private boolean keycodePressed = false;
    private ArrayList<String> listPassword = new ArrayList<>();
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetail;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailAvailable;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailUnlcked;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailUnlckedDetail;
    private LiveStreamDBHandler liveStreamDBHandler;
    @BindView(R.id.ll_header)
    LinearLayout ll_header;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences loginPreferencesSharedPref;
    private LoginPresenter loginPresenter;
    @BindView(R.id.logo)
    ImageView logo;
    Settings mSettings;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    private ProgressDialog progressDialog;
    SearchView searchView;
    @BindView(R.id.sliding_tabs)
    TabLayout slidingTabs;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    TypedValue f8tv;
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_cat_title)
    TextView tv_cat_title;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_new_epg);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        changeStatusBarColor();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getWindow().setFlags(1024, 1024);
        Intent intent = getIntent();
        if (intent != null) {
            this.getActiveLiveStreamCategoryId = intent.getStringExtra(AppConst.CATEGORY_ID);
            this.getActiveLiveStreamCategoryName = intent.getStringExtra(AppConst.CATEGORY_NAME);
            if (this.tv_cat_title != null) {
                this.tv_cat_title.setText(this.getActiveLiveStreamCategoryName);
            }
        }
        this.context = this;
        this.mSettings = new Settings(this.context);
        try {
            if (this.mSettings.getPlayer() != 3) {
                IjkMediaPlayer.loadLibrariesOnce(null);
                IjkMediaPlayer.native_profileBegin("libijkplayer.so");
            }
        } catch (Exception | UnsatisfiedLinkError unused) {
        }
        new Thread(new CountDownRunner()).start();
        setUpDatabaseResults();
        this.tvHeaderTitle.setOnClickListener(this);
        this.tvHeaderTitle.sendAccessibilityEvent(8);
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.NewEPGActivity.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(NewEPGActivity.this.context);
            }
        });
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    NewEPGActivity.this.doWork();
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
            /* class com.nst.yourname.view.activity.NewEPGActivity.AnonymousClass2 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(NewEPGActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (NewEPGActivity.this.time != null) {
                        NewEPGActivity.this.time.setText(time);
                    }
                    if (NewEPGActivity.this.date != null) {
                        NewEPGActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
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

    /* JADX WARNING: Removed duplicated region for block: B:10:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0052  */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int action = keyEvent.getAction();
        switch (keyEvent.getKeyCode()) {
            case 20:
                if (!this.keycodePressed) {
                    this.ll_header.requestFocus();
                    this.ll_header.setBackground(ContextCompat.getDrawable(this.context, R.color.colorPrimaryFocus));
                    this.keycodePressed = true;
                    return true;
                }
                if (action == 0) {
                    this.ll_header.setBackground(ContextCompat.getDrawable(this.context, R.drawable.tranparentdark));
                }
                if (!this.keycodePressed) {
                    this.ll_header.requestFocus();
                    this.ll_header.setBackground(ContextCompat.getDrawable(this.context, R.color.colorPrimaryFocus));
                    this.keycodePressed = true;
                    return true;
                }
                if (!this.keycodePressed) {
                    this.ll_header.requestFocus();
                    this.ll_header.setBackground(ContextCompat.getDrawable(this.context, R.color.colorPrimaryFocus));
                    this.keycodePressed = true;
                    return true;
                }
                return super.dispatchKeyEvent(keyEvent);
            case 21:
                if (!this.keycodePressed) {
                }
                if (!this.keycodePressed) {
                }
                return super.dispatchKeyEvent(keyEvent);
            case 22:
                if (!this.keycodePressed) {
                }
                return super.dispatchKeyEvent(keyEvent);
            default:
                return super.dispatchKeyEvent(keyEvent);
        }
    }

    private void setUpDatabaseResults() {
        if (this.context != null) {
            this.liveListDetailAvailable = new ArrayList<>();
            LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
            liveStreamCategoryIdDBModel.setLiveStreamCategoryID(this.getActiveLiveStreamCategoryId);
            liveStreamCategoryIdDBModel.setLiveStreamCategoryName(this.getActiveLiveStreamCategoryName);
            this.liveListDetailAvailable.add(liveStreamCategoryIdDBModel);
            if (this.liveListDetailAvailable != null && this.viewpager != null && this.slidingTabs != null) {
                this.viewpager.setAdapter(new NewEPGCategoriesAdapter(this.liveListDetailAvailable, getSupportFragmentManager(), this));
            }
        }
    }

    private ArrayList<LiveStreamCategoryIdDBModel> getUnlockedCategories(ArrayList<LiveStreamCategoryIdDBModel> arrayList, ArrayList<String> arrayList2) {
        if (arrayList == null) {
            return null;
        }
        Iterator<LiveStreamCategoryIdDBModel> it = arrayList.iterator();
        while (it.hasNext()) {
            LiveStreamCategoryIdDBModel next = it.next();
            boolean z = false;
            if (arrayList2 != null) {
                Iterator<String> it2 = arrayList2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    if (next.getLiveStreamCategoryID().equals(it2.next())) {
                        z = true;
                        break;
                    }
                }
                if (!z && this.liveListDetailUnlcked != null) {
                    this.liveListDetailUnlcked.add(next);
                }
            }
        }
        return this.liveListDetailUnlcked;
    }

    private ArrayList<String> getPasswordSetCategories() {
        this.categoryWithPasword = this.liveStreamDBHandler.getAllPasswordStatus(SharepreferenceDBHandler.getUserID(this.context));
        if (this.categoryWithPasword != null) {
            Iterator<PasswordStatusDBModel> it = this.categoryWithPasword.iterator();
            while (it.hasNext()) {
                PasswordStatusDBModel next = it.next();
                if (next.getPasswordStatus().equals("1")) {
                    this.listPassword.add(next.getPasswordStatusCategoryId());
                }
            }
        }
        return this.listPassword;
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
        getWindow().setFlags(1024, 1024);
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        if (this.loginPreferencesAfterLogin.getString("username", "").equals("") && this.loginPreferencesAfterLogin.getString("password", "").equals("")) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (this.context != null) {
            onFinish();
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.tv_header_title) {
            startDashboardActitivity();
        }
    }

    private void startDashboardActitivity() {
        startActivity(new Intent(this, NewDashboardActivity.class));
    }

    public void onFinish() {
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(4);
        }
    }
}
