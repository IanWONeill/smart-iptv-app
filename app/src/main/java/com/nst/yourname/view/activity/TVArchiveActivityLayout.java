package com.nst.yourname.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.PasswordStatusDBModel;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.adapter.LiveStreamsAdapter;
import com.nst.yourname.view.adapter.TVArchiveLiveChannelsAdapterNewFlow;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
public class TVArchiveActivityLayout extends AppCompatActivity implements View.OnClickListener {
    static final boolean $assertionsDisabled = false;
    private SharedPreferences SharedPreferencesSort;
    public SharedPreferences.Editor SharedPreferencesSortEditor;
    int actionBarHeight;
    @BindView(R.id.appbar_toolbar)
    AppBarLayout appbarToolbar;
    private ArrayList<PasswordStatusDBModel> categoryWithPasword;
    public PopupWindow changeSortPopUp;
    TVArchiveLiveChannelsAdapterNewFlow channelsOnVideoAdapter;
    TextView clientNameTv;
    public Context context;
    DatabaseHandler database;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive = new DatabaseUpdatedStatusDBModel();
    @BindView(R.id.date)
    TextView date;
    private SharedPreferences.Editor editor;
    private ArrayList<LiveStreamsDBModel> favouriteStreams = new ArrayList<>();
    private String getActiveLiveStreamCategoryId = "";
    private String getActiveLiveStreamCategoryName = "";
    private GridLayoutManager gridLayoutManager;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> listPassword = new ArrayList<>();
    private ArrayList<LiveStreamsDBModel> liveListDetail;
    private ArrayList<LiveStreamsDBModel> liveListDetailAvailable;
    private ArrayList<LiveStreamsDBModel> liveListDetailAvailableNew;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlcked;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlckedDetail;
    private LiveStreamDBHandler liveStreamDBHandler;
    public LiveStreamsAdapter liveStreamsAdapter;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences loginPreferencesSharedPref;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    public SharedPreferences pref;
    SearchView searchView;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    TypedValue f27tv;
    @BindView(R.id.tv_no_record_found)
    TextView tvNoRecordFound;
    @BindView(R.id.tv_noStream)
    TextView tvNoStream;
    @BindView(R.id.tv_view_provider)
    TextView tvViewProvider;
    private String userName = "";
    private String userPassword = "";

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_tv_archive_new_flow_layout);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        if (this.appbarToolbar != null) {
            this.appbarToolbar.setBackground(getResources().getDrawable(R.drawable.layout_background_tv));
        }
        this.SharedPreferencesSort = getSharedPreferences(AppConst.LOGIN_PREF_SORT, 0);
        this.SharedPreferencesSortEditor = this.SharedPreferencesSort.edit();
        if (this.SharedPreferencesSort.getString(AppConst.LOGIN_PREF_SORT, "").equals("")) {
            this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, AppConst.PASSWORD_UNSET);
            this.SharedPreferencesSortEditor.apply();
        }
        getWindow().setFlags(1024, 1024);
        Intent intent = getIntent();
        if (intent != null) {
            this.getActiveLiveStreamCategoryId = intent.getStringExtra(AppConst.CATEGORY_ID);
            this.getActiveLiveStreamCategoryName = intent.getStringExtra(AppConst.CATEGORY_NAME);
        }
        this.context = this;
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.pref = getSharedPreferences("listgridview", 0);
        AppConst.LIVE_FLAG = this.pref.getInt(AppConst.LIVE_STREAM, 0);
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.TVArchiveActivityLayout.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(TVArchiveActivityLayout.this.context);
            }
        });
        if (AppConst.LIVE_FLAG == 1) {
            initialize();
        } else {
            initialize1();
        }
        if (this.getActiveLiveStreamCategoryId.equals("-1")) {
            getFavourites();
        }
        changeStatusBarColor();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        this.context = this;
        new Thread(new CountDownRunner()).start();
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    TVArchiveActivityLayout.this.doWork();
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
            /* class com.nst.yourname.view.activity.TVArchiveActivityLayout.AnonymousClass2 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(TVArchiveActivityLayout.this.context);
                    String date2 = Utils.getDate(date);
                    if (TVArchiveActivityLayout.this.time != null) {
                        TVArchiveActivityLayout.this.time.setText(time);
                    }
                    if (TVArchiveActivityLayout.this.date != null) {
                        TVArchiveActivityLayout.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.toolbar.inflateMenu(R.menu.menu_search_text_icon);
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
        if (itemId == R.id.action_logout1 && this.context != null) {
            new AlertDialog.Builder(this.context, R.style.AlertDialogCustom).setTitle(getResources().getString(R.string.logout_title)).setMessage(getResources().getString(R.string.logout_message)).setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.TVArchiveActivityLayout.AnonymousClass4 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(TVArchiveActivityLayout.this.context);
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.TVArchiveActivityLayout.AnonymousClass3 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
        if (itemId == R.id.action_search) {
            this.searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
            this.searchView.setQueryHint(getResources().getString(R.string.search_channel));
            this.searchView.setIconifiedByDefault(false);
            this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                /* class com.nst.yourname.view.activity.TVArchiveActivityLayout.AnonymousClass5 */

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextSubmit(String str) {
                    return false;
                }

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextChange(String str) {
                    TVArchiveActivityLayout.this.tvNoRecordFound.setVisibility(8);
                    if (TVArchiveActivityLayout.this.liveStreamsAdapter == null || TVArchiveActivityLayout.this.tvNoStream == null || TVArchiveActivityLayout.this.tvNoStream.getVisibility() == 0) {
                        return false;
                    }
                    TVArchiveActivityLayout.this.liveStreamsAdapter.filter(str, TVArchiveActivityLayout.this.tvNoRecordFound);
                    return false;
                }
            });
            return true;
        }
        if (itemId == R.id.menu_load_channels_vod1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(this.context.getResources().getString(R.string.confirm_to_refresh));
            builder.setMessage(this.context.getResources().getString(R.string.do_you_want_toproceed));
            builder.setIcon((int) R.drawable.questionmark);
            builder.setPositiveButton(this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.TVArchiveActivityLayout.AnonymousClass6 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadChannelsAndVod(TVArchiveActivityLayout.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.TVArchiveActivityLayout.AnonymousClass7 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
        if (itemId == R.id.menu_load_tv_guide1) {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setTitle(this.context.getResources().getString(R.string.confirm_to_refresh));
            builder2.setMessage(this.context.getResources().getString(R.string.do_you_want_toproceed));
            builder2.setIcon((int) R.drawable.questionmark);
            builder2.setPositiveButton(this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.TVArchiveActivityLayout.AnonymousClass8 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadTvGuid(TVArchiveActivityLayout.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.TVArchiveActivityLayout.AnonymousClass9 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder2.show();
        }
        if (itemId == R.id.layout_view_grid) {
            this.editor.putInt(AppConst.LIVE_STREAM, 1);
            this.editor.commit();
            initialize();
        }
        if (itemId == R.id.layout_view_linear) {
            this.editor.putInt(AppConst.LIVE_STREAM, 0);
            this.editor.commit();
            initialize1();
        }
        if (itemId == R.id.menu_sort) {
            showSortPopup(this);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void showSortPopup(Activity activity) {
        try {
            final View inflate = ((LayoutInflater) activity.getSystemService("layout_inflater")).inflate((int) R.layout.sort_layout, (RelativeLayout) activity.findViewById(R.id.rl_password_prompt));
            this.changeSortPopUp = new PopupWindow(activity);
            this.changeSortPopUp.setContentView(inflate);
            char c = 65535;
            this.changeSortPopUp.setWidth(-1);
            this.changeSortPopUp.setHeight(-1);
            this.changeSortPopUp.setFocusable(true);
            this.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
            Button button = (Button) inflate.findViewById(R.id.bt_save_password);
            Button button2 = (Button) inflate.findViewById(R.id.bt_close);
            final RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.rg_radio);
            RadioButton radioButton = (RadioButton) inflate.findViewById(R.id.rb_normal);
            RadioButton radioButton2 = (RadioButton) inflate.findViewById(R.id.rb_lastadded);
            RadioButton radioButton3 = (RadioButton) inflate.findViewById(R.id.rb_atoz);
            RadioButton radioButton4 = (RadioButton) inflate.findViewById(R.id.rb_ztoa);
            String string = this.SharedPreferencesSort.getString(AppConst.LOGIN_PREF_SORT, "");
            switch (string.hashCode()) {
                case 49:
                    if (string.equals("1")) {
                        c = 0;
                        break;
                    }
                    break;
                case 50:
                    if (string.equals("2")) {
                        c = 1;
                        break;
                    }
                    break;
                case 51:
                    if (string.equals("3")) {
                        c = 2;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    radioButton2.setChecked(true);
                    break;
                case 1:
                    radioButton3.setChecked(true);
                    break;
                case 2:
                    radioButton4.setChecked(true);
                    break;
                default:
                    radioButton.setChecked(true);
                    break;
            }
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.TVArchiveActivityLayout.AnonymousClass10 */

                public void onClick(View view) {
                    TVArchiveActivityLayout.this.changeSortPopUp.dismiss();
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.TVArchiveActivityLayout.AnonymousClass11 */

                public void onClick(View view) {
                    RadioButton radioButton = (RadioButton) inflate.findViewById(radioGroup.getCheckedRadioButtonId());
                    if (radioButton.getText().toString().equals(TVArchiveActivityLayout.this.getResources().getString(R.string.sort_last_added))) {
                        TVArchiveActivityLayout.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "1");
                        TVArchiveActivityLayout.this.SharedPreferencesSortEditor.apply();
                    } else if (radioButton.getText().toString().equals(TVArchiveActivityLayout.this.getResources().getString(R.string.sort_atoz))) {
                        TVArchiveActivityLayout.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "2");
                        TVArchiveActivityLayout.this.SharedPreferencesSortEditor.apply();
                    } else if (radioButton.getText().toString().equals(TVArchiveActivityLayout.this.getResources().getString(R.string.sort_ztoa))) {
                        TVArchiveActivityLayout.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "3");
                        TVArchiveActivityLayout.this.SharedPreferencesSortEditor.apply();
                    } else {
                        TVArchiveActivityLayout.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, AppConst.PASSWORD_UNSET);
                        TVArchiveActivityLayout.this.SharedPreferencesSortEditor.apply();
                    }
                    SharedPreferences unused = TVArchiveActivityLayout.this.pref = TVArchiveActivityLayout.this.getSharedPreferences("listgridview", 0);
                    AppConst.LIVE_FLAG = TVArchiveActivityLayout.this.pref.getInt(AppConst.LIVE_STREAM, 0);
                    if (AppConst.LIVE_FLAG == 1) {
                        TVArchiveActivityLayout.this.initialize();
                    } else {
                        TVArchiveActivityLayout.this.initialize1();
                    }
                    TVArchiveActivityLayout.this.changeSortPopUp.dismiss();
                }
            });
        } catch (Exception unused) {
        }
    }

    public void getFavourites() {
        this.favouriteStreams.clear();
        if (this.myRecyclerView != null) {
            this.myRecyclerView.setAdapter(this.liveStreamsAdapter);
        }
        if (this.context != null) {
            this.database = new DatabaseHandler(this.context);
            Iterator<FavouriteDBModel> it = this.database.getAllFavourites("live", SharepreferenceDBHandler.getUserID(this.context)).iterator();
            while (it.hasNext()) {
                FavouriteDBModel next = it.next();
                LiveStreamsDBModel liveStreamFavouriteRow = new LiveStreamDBHandler(this.context).getLiveStreamFavouriteRow(next.getCategoryID(), String.valueOf(next.getStreamID()));
                Log.e("Channel available size", ">>>>>>>>>>> " + liveStreamFavouriteRow.size());
                this.favouriteStreams.add(liveStreamFavouriteRow);
            }
            onFinish();
            if (!(this.myRecyclerView == null || this.favouriteStreams == null || this.favouriteStreams.size() == 0)) {
                this.liveStreamsAdapter = new LiveStreamsAdapter(this.favouriteStreams, this.context);
                this.myRecyclerView.setAdapter(this.liveStreamsAdapter);
                this.liveStreamsAdapter.notifyDataSetChanged();
                this.tvNoStream.setVisibility(4);
            }
            if (this.tvNoStream != null && this.favouriteStreams != null && this.favouriteStreams.size() == 0) {
                if (this.myRecyclerView != null) {
                    this.myRecyclerView.setAdapter(this.liveStreamsAdapter);
                }
                this.tvNoStream.setText(getResources().getString(R.string.no_fav_channel_found));
                this.tvNoStream.setVisibility(0);
            }
        }
    }

    public void initialize() {
        this.context = this;
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        if (this.myRecyclerView != null && this.context != null) {
            this.myRecyclerView.setHasFixedSize(true);
            this.layoutManager = new GridLayoutManager(this.context, Utils.getNumberOfColumns(this.context) + 1);
            this.myRecyclerView.setLayoutManager(this.layoutManager);
            this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
            this.loginPreferencesSharedPref = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            setUpdatabaseResult();
        }
    }

    public void initialize1() {
        this.context = this;
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        if (this.myRecyclerView != null && this.context != null) {
            this.myRecyclerView.setHasFixedSize(true);
            this.layoutManager = new LinearLayoutManager(this.context);
            this.myRecyclerView.setLayoutManager(this.layoutManager);
            this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
            this.loginPreferencesSharedPref = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            setUpdatabaseResult();
        }
    }

    private void setUpdatabaseResult() {
        try {
            if (this.context != null) {
                LiveStreamDBHandler liveStreamDBHandler2 = new LiveStreamDBHandler(this.context);
                this.categoryWithPasword = new ArrayList<>();
                this.liveListDetailUnlcked = new ArrayList<>();
                this.liveListDetailUnlckedDetail = new ArrayList<>();
                this.liveListDetailAvailable = new ArrayList<>();
                this.liveListDetailAvailableNew = new ArrayList<>();
                this.liveListDetail = new ArrayList<>();
                ArrayList<LiveStreamsDBModel> allLiveStreamsArchive = liveStreamDBHandler2.getAllLiveStreamsArchive(this.getActiveLiveStreamCategoryId);
                Log.e("channelAvailable size", " >>>>>>>> " + allLiveStreamsArchive.size());
                Log.e("channelAvailable", " >>>>>>>> " + allLiveStreamsArchive.toString());
                if (liveStreamDBHandler2.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context)) > 0) {
                    this.listPassword = getPasswordSetCategories();
                    if (this.listPassword != null) {
                        this.liveListDetailUnlckedDetail = getUnlockedCategories(allLiveStreamsArchive, this.listPassword);
                    }
                    this.liveListDetailAvailable = this.liveListDetailUnlckedDetail;
                } else {
                    this.liveListDetailAvailable = allLiveStreamsArchive;
                }
                if (this.getActiveLiveStreamCategoryId.equals("-1")) {
                    onFinish();
                } else if (this.liveListDetailAvailable == null || this.myRecyclerView == null || this.liveListDetailAvailable.size() == 0) {
                    onFinish();
                    if (this.tvNoStream != null) {
                        this.tvNoStream.setVisibility(0);
                    }
                } else {
                    onFinish();
                    setChannelListAdapterNew(this.liveListDetailAvailable);
                }
            }
        } catch (Exception unused) {
        }
    }

    public void setChannelListAdapterNew(ArrayList<LiveStreamsDBModel> arrayList) {
        this.channelsOnVideoAdapter = new TVArchiveLiveChannelsAdapterNewFlow(arrayList, this.context);
        this.gridLayoutManager = new GridLayoutManager(this, 1);
        this.myRecyclerView.setLayoutManager(this.gridLayoutManager);
        this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.myRecyclerView.setAdapter(this.channelsOnVideoAdapter);
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(4);
        }
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

    private ArrayList<LiveStreamsDBModel> getUnlockedCategories(ArrayList<LiveStreamsDBModel> arrayList, ArrayList<String> arrayList2) {
        if (arrayList == null) {
            return null;
        }
        Iterator<LiveStreamsDBModel> it = arrayList.iterator();
        while (it.hasNext()) {
            LiveStreamsDBModel next = it.next();
            boolean z = false;
            if (arrayList2 != null) {
                Iterator<String> it2 = arrayList2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    if (next.getCategoryId().equals(it2.next())) {
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
        this.myRecyclerView.setClickable(true);
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
            startActivity(new Intent(this, NewDashboardActivity.class));
        }
    }

    public void onFinish() {
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(4);
        }
    }
}
