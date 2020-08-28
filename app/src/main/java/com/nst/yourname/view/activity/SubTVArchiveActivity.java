package com.nst.yourname.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.callback.LiveStreamsEpgCallback;
import com.nst.yourname.model.callback.LoginCallback;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.pojo.EpgListingPojo;
import com.nst.yourname.model.pojo.XMLTVProgrammePojo;
import com.nst.yourname.presenter.LoginPresenter;
import com.nst.yourname.presenter.TvArchivePresenter;
import com.nst.yourname.view.adapter.SubTVArchiveCategoriesAdapter;
import com.nst.yourname.view.interfaces.LiveStreamsEpgInterface;
import com.nst.yourname.view.interfaces.LoginInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SubTVArchiveActivity extends AppCompatActivity implements LoginInterface, LiveStreamsEpgInterface, View.OnClickListener {
    int actionBarHeight;
    @BindView(R.id.appbar_toolbar)
    AppBarLayout appbarToolbar;
    TextView clientNameTv;
    public Context context;
    @BindView(R.id.date)
    TextView date;
    LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences loginPreferencesSharedPref;
    private LoginPresenter loginPresenter;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    @BindView(R.id.rl_tv_archive_title)
    RelativeLayout rlTvArchiveTitle;
    @BindView(R.id.sliding_tabs)
    TabLayout slidingTabs;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    TypedValue f25tv;
    private TvArchivePresenter tvArchivePresenter;
    @BindView(R.id.tv_egp_required)
    TextView tvEpgRequired;
    @BindView(R.id.tv_no_record_found)
    TextView tvNoRecordFound;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void magFailedtoLogin(String str, String str2, String str3, Context context2) {
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void magFailedtoLoginMultiDNS(ArrayList<String> arrayList, ArrayList<String> arrayList2, String str, String str2, String str3) {
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void magFailedtoLoginMultiDNS2(ArrayList<String> arrayList, ArrayList<String> arrayList2, String str, String str2, String str3) {
    }

    public void onClick(View view) {
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFailed(String str) {
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
    public void validateloginMultiDNS(LoginCallback loginCallback, String str, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
    }

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_sub_archive);
        ButterKnife.bind(this);
        changeStatusBarColor();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getWindow().setFlags(1024, 1024);
        initialize();
        new Thread(new CountDownRunner()).start();
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.SubTVArchiveActivity.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(SubTVArchiveActivity.this.context);
            }
        });
    }

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.clear();
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    SubTVArchiveActivity.this.doWork();
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
            /* class com.nst.yourname.view.activity.SubTVArchiveActivity.AnonymousClass2 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(SubTVArchiveActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (SubTVArchiveActivity.this.time != null) {
                        SubTVArchiveActivity.this.time.setText(time);
                    }
                    if (SubTVArchiveActivity.this.date != null) {
                        SubTVArchiveActivity.this.date.setText(date2);
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

    private void initialize() {
        int i;
        this.context = this;
        this.slidingTabs.setVisibility(8);
        this.loginPreferencesSharedPref = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        String string = this.loginPreferencesSharedPref.getString("username", "");
        String string2 = this.loginPreferencesSharedPref.getString("password", "");
        String stringExtra = getIntent().getStringExtra("OPENED_STREAM_ID");
        String stringExtra2 = getIntent().getStringExtra("OPENED_NUM");
        String stringExtra3 = getIntent().getStringExtra("OPENED_CHANNEL_ID");
        String stringExtra4 = getIntent().getStringExtra("OPENED_NAME");
        String stringExtra5 = getIntent().getStringExtra("OPENED_STREAM_ICON");
        String stringExtra6 = getIntent().getStringExtra("OPENED_ARCHIVE_DURATION");
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        if (stringExtra3 != null) {
            stringExtra3.equals("");
        }
        this.tvArchivePresenter = new TvArchivePresenter(this, this.context);
        try {
            i = Integer.parseInt(stringExtra);
        } catch (NumberFormatException unused) {
            i = -1;
        }
        this.tvArchivePresenter.getTvArchive(string, string2, i, stringExtra3, stringExtra, stringExtra2, stringExtra4, stringExtra5, stringExtra6);
    }

    public void getEPG(String str, String str2, String str3, String str4, String str5, String str6) {
        ArrayList<XMLTVProgrammePojo> epg;
        int i;
        if (this.liveStreamDBHandler != null && (epg = this.liveStreamDBHandler.getEPG(str)) != null) {
            int size = epg.size();
            if (size != 0) {
                String format = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(new Date());
                ArrayList arrayList = new ArrayList();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                int i2 = 0;
                int i3 = 0;
                while (true) {
                    if (i2 >= size) {
                        i = 0;
                        break;
                    }
                    String format2 = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(Long.valueOf(Utils.epgTimeConverter(epg.get(i2).getStart())));
                    if (Long.valueOf(getDateDiff(simpleDateFormat, format2, format)).longValue() >= 0 && !arrayList.contains(format2)) {
                        arrayList.add(i3, format2);
                        if (format.equals(format2)) {
                            i = i3;
                            break;
                        }
                        i3++;
                    }
                    i2++;
                }
                this.viewpager.setAdapter(new SubTVArchiveCategoriesAdapter(arrayList, epg, str2, str3, str4, str5, str, str6, getSupportFragmentManager(), this));
                this.slidingTabs.setupWithViewPager(this.viewpager);
                this.viewpager.setCurrentItem(i);
                Utils.showToastLong(this.context, getResources().getString(R.string.use_long_press));
            }
            onFinish();
        }
    }

    public static long getDateDiff(SimpleDateFormat simpleDateFormat, String str, String str2) {
        try {
            return TimeUnit.DAYS.convert(simpleDateFormat.parse(str2).getTime() - simpleDateFormat.parse(str).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
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
    public void onResume() {
        super.onResume();
        Utils.appResume(this.context);
        getWindow().setFlags(1024, 1024);
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        if (this.loginPreferencesAfterLogin.getString("username", "").equals("") && this.loginPreferencesAfterLogin.getString("password", "").equals("")) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override // com.nst.yourname.view.interfaces.LiveStreamsEpgInterface
    public void liveStreamsEpg(LiveStreamsEpgCallback liveStreamsEpgCallback, String str, String str2, String str3, String str4, String str5, String str6) {
        String str7;
        if (liveStreamsEpgCallback == null || liveStreamsEpgCallback.getEpgListingPojos() == null || liveStreamsEpgCallback.getEpgListingPojos().size() <= 0) {
            if (this.viewpager != null) {
                this.viewpager.setVisibility(8);
            }
            if (this.slidingTabs != null) {
                this.slidingTabs.setVisibility(8);
            }
            if (this.rlTvArchiveTitle != null) {
                this.rlTvArchiveTitle.setVisibility(0);
            }
            if (this.tvNoRecordFound != null) {
                this.tvNoRecordFound.setVisibility(0);
            }
        } else {
            ArrayList arrayList = new ArrayList();
            new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (EpgListingPojo epgListingPojo : liveStreamsEpgCallback.getEpgListingPojos()) {
                if (epgListingPojo.getHasArchive().intValue() == 1) {
                    arrayList.add(epgListingPojo);
                    XMLTVProgrammePojo xMLTVProgrammePojo = new XMLTVProgrammePojo();
                    xMLTVProgrammePojo.setTitle(epgListingPojo.getTitle());
                    xMLTVProgrammePojo.setStart(epgListingPojo.getStart());
                    xMLTVProgrammePojo.setStop(epgListingPojo.getEnd());
                    xMLTVProgrammePojo.setDesc(epgListingPojo.getDescription());
                    xMLTVProgrammePojo.setChannel(epgListingPojo.getChannelId());
                    xMLTVProgrammePojo.setStartTimeStamp(epgListingPojo.getStartTimestamp());
                    xMLTVProgrammePojo.setEndTimeStamp(epgListingPojo.getStopTimestamp());
                    arrayList2.add(xMLTVProgrammePojo);
                }
            }
            if (this.liveStreamDBHandler != null) {
                int size = arrayList2.size();
                if (size != 0) {
                    String format = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(new Date());
                    ArrayList arrayList3 = new ArrayList();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                    int i = 0;
                    for (int i2 = 0; i2 < size; i2++) {
                        try {
                            str7 = new SimpleDateFormat("dd MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(((XMLTVProgrammePojo) arrayList2.get(i2)).getStart().split("\\s+")[0]));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            str7 = "";
                        }
                        if (Long.valueOf(getDateDiff(simpleDateFormat, str7, format)).longValue() >= 0 && !arrayList3.contains(str7)) {
                            arrayList3.add(i, str7);
                            if (format.equals(str7)) {
                                break;
                            }
                            i++;
                        }
                    }
                    this.viewpager.setAdapter(new SubTVArchiveCategoriesAdapter(arrayList3, arrayList2, str2, str3, str4, str5, str, str6, getSupportFragmentManager(), this));
                    this.slidingTabs.setVisibility(0);
                    this.slidingTabs.setupWithViewPager(this.viewpager);
                    this.viewpager.setCurrentItem(i - 1);
                    Utils.showToastLong(this.context, getResources().getString(R.string.use_long_press));
                } else {
                    if (this.viewpager != null) {
                        this.viewpager.setVisibility(8);
                    }
                    if (this.slidingTabs != null) {
                        this.slidingTabs.setVisibility(8);
                    }
                    if (this.rlTvArchiveTitle != null) {
                        this.rlTvArchiveTitle.setVisibility(0);
                    }
                    if (this.tvNoRecordFound != null) {
                        this.tvNoRecordFound.setVisibility(0);
                    }
                }
                onFinish();
            }
        }
        onFinish();
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void atStart() {
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(0);
        }
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFinish() {
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(4);
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.toolbar.inflateMenu(R.menu.menu_dashboard_logout);
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
                /* class com.nst.yourname.view.activity.SubTVArchiveActivity.AnonymousClass4 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(SubTVArchiveActivity.this.context);
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SubTVArchiveActivity.AnonymousClass3 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void validateLogin(LoginCallback loginCallback, String str) {
        if (loginCallback != null && loginCallback.getUserLoginInfo().getAuth().intValue() == 1) {
            String status = loginCallback.getUserLoginInfo().getStatus();
            if (!status.equals("Active")) {
                Context context2 = this.context;
                Utils.showToast(context2, AppConst.INVALID_STATUS + status);
                if (this.context != null) {
                    Utils.logoutUser(this.context);
                }
            }
        }
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onStop() {
        super.onStop();
        finish();
    }
}
