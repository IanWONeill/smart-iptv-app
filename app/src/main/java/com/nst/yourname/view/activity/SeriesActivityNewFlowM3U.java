package com.nst.yourname.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
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
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.PasswordStatusDBModel;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.presenter.XMLTVPresenter;
import com.nst.yourname.view.adapter.SeriesAdapterNewFlow;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class SeriesActivityNewFlowM3U extends AppCompatActivity implements View.OnClickListener {
    static final boolean $assertionsDisabled = false;
    private static final String JSON = "";
    static ProgressBar pbPagingLoader1;
    public AsyncTask AsyncTaskSeriesStreams = null;
    private SharedPreferences SharedPreferencesSort;
    private SharedPreferences.Editor SharedPreferencesSortEditor;
    int actionBarHeight;
    @BindView(R.id.main_layout)
    LinearLayout activityLogin;
    @BindView(R.id.adView)
    RelativeLayout adviewLayout;
    public int allCount = 0;
    @BindView(R.id.appbar_toolbar)
    AppBarLayout appbarToolbar;
    @BindView(R.id.bt_explore_all)
    Button bt_explore_all;
    ArrayList<LiveStreamCategoryIdDBModel> categoriesList;
    private ArrayList<PasswordStatusDBModel> categoryWithPasword;
    public PopupWindow changeSortPopUp;
    TextView clientNameTv;
    public Context context;
    int countUncat = -1;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive = new DatabaseUpdatedStatusDBModel();
    @BindView(R.id.fl_frame)
    FrameLayout frameLayout;
    Handler handler;
    @BindView(R.id.home)
    TextView home;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<String> listPassword = new ArrayList<>();
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetail;
    public ArrayList<LiveStreamCategoryIdDBModel> liveListDetailAvailable;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailAvailableForCounter;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailUnlcked;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailUnlckedDetail;
    private LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesAfterLogin;
    @BindView(R.id.logo)
    ImageView logo;
    public SeriesAdapterNewFlow mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    MenuItem menuItemSettings;
    Menu menuSelect;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    @BindView(R.id.pb_paging_loader)
    ProgressBar pbPagingLoader;
    @BindView(R.id.rl_no_arrangement_found)
    RelativeLayout rl_no_arrangement_found;
    @BindView(R.id.rl_vod_layout)
    RelativeLayout rl_vod_layout;
    SearchView searchView;
    ArrayList<LiveStreamCategoryIdDBModel> subCategoryList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    TypedValue f18tv;
    @BindView(R.id.empty_view)
    TextView tvNoRecordFound;
    @BindView(R.id.tv_settings)
    TextView tvSettings;
    private String userName = "";
    private String userPassword = "";
    private XMLTVPresenter xmltvPresenter;

    public void onFinish() {
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_vod_new_flow);
        ButterKnife.bind(this);
        if (this.appbarToolbar != null) {
            this.appbarToolbar.setBackground(getResources().getDrawable(R.drawable.vod_backgound));
        }
        if (this.tvSettings != null) {
            this.tvSettings.setText(getResources().getString(R.string.series));
        }
        changeStatusBarColor();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getWindow().setFlags(1024, 1024);
        this.context = this;
        this.handler = new Handler();
        this.handler.removeCallbacksAndMessages(null);
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(0);
        }
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.SeriesActivityNewFlowM3U.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(SeriesActivityNewFlowM3U.this.context);
            }
        });
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.myRecyclerView.setHasFixedSize(true);
        this.mLayoutManager = new GridLayoutManager(this, 2);
        this.myRecyclerView.setLayoutManager(this.mLayoutManager);
        this.myRecyclerView.setVisibility(0);
        this.AsyncTaskSeriesStreams = new SeriesActivityNewFlowM3UAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
        this.home.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.SeriesActivityNewFlowM3U.AnonymousClass2 */

            public void onClick(View view) {
                SeriesActivityNewFlowM3U.this.context.startActivity(new Intent(SeriesActivityNewFlowM3U.this.context, NewDashboardActivity.class));
            }
        });
        this.frameLayout.setVisibility(8);
    }

    @SuppressLint({"StaticFieldLeak"})
    class SeriesActivityNewFlowM3UAsync extends AsyncTask<String, Void, Boolean> {
        SeriesActivityNewFlowM3UAsync() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public Boolean doInBackground(String... strArr) {
            try {
                return Boolean.valueOf(SeriesActivityNewFlowM3U.this.intiliaze());
            } catch (Exception unused) {
                return false;
            }
        }

        public void onPostExecute(Boolean bool) {
            super.onPostExecute((Boolean) bool);
            SeriesActivityNewFlowM3U.this.bt_explore_all.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesActivityNewFlowM3U.SeriesActivityNewFlowM3UAsync.AnonymousClass1 */

                public void onClick(View view) {
                    SeriesActivityNewFlowM3U.this.startActivity(new Intent(SeriesActivityNewFlowM3U.this.context, PlaylistsCategoriesActivity.class));
                    SeriesActivityNewFlowM3U.this.finish();
                    SeriesActivityNewFlowM3U.this.overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                }
            });
            if (SeriesActivityNewFlowM3U.this.liveListDetailAvailable != null && SeriesActivityNewFlowM3U.this.allCount != 0) {
                SeriesAdapterNewFlow unused = SeriesActivityNewFlowM3U.this.mAdapter = new SeriesAdapterNewFlow(SeriesActivityNewFlowM3U.this.liveListDetailAvailable, SeriesActivityNewFlowM3U.this.context);
                SeriesActivityNewFlowM3U.this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
                SeriesActivityNewFlowM3U.this.myRecyclerView.setAdapter(SeriesActivityNewFlowM3U.this.mAdapter);
                if (SeriesActivityNewFlowM3U.this.pbLoader != null) {
                    SeriesActivityNewFlowM3U.this.pbLoader.setVisibility(8);
                }
            } else if (SeriesActivityNewFlowM3U.this.pbLoader != null) {
                SeriesActivityNewFlowM3U.this.pbLoader.setVisibility(8);
                SeriesActivityNewFlowM3U.this.rl_no_arrangement_found.setVisibility(0);
            }
        }
    }

    public boolean intiliaze() {
        try {
            this.categoriesList = new ArrayList<>();
            this.subCategoryList = new ArrayList<>();
            if (this.context != null) {
                this.liveListDetailUnlcked = new ArrayList<>();
                this.liveListDetailUnlckedDetail = new ArrayList<>();
                this.liveListDetailAvailable = new ArrayList<>();
                this.liveListDetail = new ArrayList<>();
                this.liveListDetailAvailableForCounter = new ArrayList<>();
                this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
                ArrayList<LiveStreamCategoryIdDBModel> allSeriesCategories = this.liveStreamDBHandler.getAllSeriesCategories();
                this.countUncat = this.liveStreamDBHandler.getUncatCountM3UByType("series");
                new ArrayList();
                LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
                LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel2 = new LiveStreamCategoryIdDBModel();
                LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel3 = new LiveStreamCategoryIdDBModel();
                liveStreamCategoryIdDBModel.setLiveStreamCategoryID(AppConst.PASSWORD_UNSET);
                liveStreamCategoryIdDBModel.setLiveStreamCategoryName(getResources().getString(R.string.all));
                liveStreamCategoryIdDBModel2.setLiveStreamCategoryID("-1");
                liveStreamCategoryIdDBModel2.setLiveStreamCategoryName(getResources().getString(R.string.favourites));
                if (this.countUncat != 0 && this.countUncat > 0) {
                    liveStreamCategoryIdDBModel3.setLiveStreamCategoryID("");
                    liveStreamCategoryIdDBModel3.setLiveStreamCategoryName(getResources().getString(R.string.uncategories));
                    allSeriesCategories.add(allSeriesCategories.size(), liveStreamCategoryIdDBModel3);
                }
                allSeriesCategories.add(0, liveStreamCategoryIdDBModel);
                allSeriesCategories.add(1, liveStreamCategoryIdDBModel2);
                this.liveListDetail = allSeriesCategories;
                if (this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context)) <= 0 || this.liveListDetail == null) {
                    this.liveListDetailAvailable = this.liveListDetail;
                } else {
                    this.listPassword = getPasswordSetCategories();
                    this.liveListDetailUnlckedDetail = getUnlockedCategories(allSeriesCategories, this.listPassword);
                    if (this.liveListDetailUnlckedDetail != null) {
                        this.liveListDetailAvailable = this.liveListDetailUnlckedDetail;
                    }
                }
                if (this.liveListDetailAvailable != null && this.liveListDetailAvailable.size() > 0) {
                    int i = 0;
                    int i2 = 0;
                    while (true) {
                        if (i < this.liveListDetailAvailable.size()) {
                            if (this.AsyncTaskSeriesStreams != null && this.AsyncTaskSeriesStreams.isCancelled()) {
                                break;
                            }
                            if (this.liveListDetailAvailable.get(i).getLiveStreamCategoryID().equals(AppConst.PASSWORD_UNSET) || this.liveListDetailAvailable.get(i).getLiveStreamCategoryID().equals("-1")) {
                                int availableCountM3U = this.liveStreamDBHandler.getAvailableCountM3U(this.liveListDetailAvailable.get(i).getLiveStreamCategoryID(), "series");
                                if (this.liveListDetailAvailable.get(i).getLiveStreamCategoryID().equals(AppConst.PASSWORD_UNSET)) {
                                    this.allCount = availableCountM3U;
                                }
                                LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel4 = new LiveStreamCategoryIdDBModel();
                                liveStreamCategoryIdDBModel4.setLiveStreamCounter(availableCountM3U);
                                liveStreamCategoryIdDBModel4.setLiveStreamCategoryName(this.liveListDetailAvailable.get(i).getLiveStreamCategoryName());
                                liveStreamCategoryIdDBModel4.setLiveStreamCategoryID(this.liveListDetailAvailable.get(i).getLiveStreamCategoryID());
                                this.liveListDetailAvailableForCounter.add(i2, liveStreamCategoryIdDBModel4);
                                i2++;
                            } else {
                                int availableCountM3U2 = this.liveStreamDBHandler.getAvailableCountM3U(this.liveListDetailAvailable.get(i).getLiveStreamCategoryID(), "series");
                                if (availableCountM3U2 != 0) {
                                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel5 = new LiveStreamCategoryIdDBModel();
                                    liveStreamCategoryIdDBModel5.setLiveStreamCounter(availableCountM3U2);
                                    liveStreamCategoryIdDBModel5.setLiveStreamCategoryName(this.liveListDetailAvailable.get(i).getLiveStreamCategoryName());
                                    liveStreamCategoryIdDBModel5.setLiveStreamCategoryID(this.liveListDetailAvailable.get(i).getLiveStreamCategoryID());
                                    this.liveListDetailAvailableForCounter.add(i2, liveStreamCategoryIdDBModel5);
                                    i2++;
                                }
                            }
                            i++;
                        } else {
                            break;
                        }
                    }
                }
                if (this.liveListDetailAvailableForCounter != null && this.liveListDetailAvailableForCounter.size() > 0) {
                    this.liveListDetailAvailable = this.liveListDetailAvailableForCounter;
                }
            }
            return true;
        } catch (NullPointerException unused) {
            return false;
        } catch (Exception unused2) {
            return false;
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
        if (this.frameLayout != null) {
            this.frameLayout.setVisibility(8);
        }
        if (this.myRecyclerView != null) {
            this.myRecyclerView.setClickable(true);
        }
        if (!(this.mAdapter == null || pbPagingLoader1 == null)) {
            this.mAdapter.setVisibiltygone(pbPagingLoader1);
        }
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onDestroy() {
        super.onDestroy();
        if (this.AsyncTaskSeriesStreams != null && this.AsyncTaskSeriesStreams.getStatus().equals(AsyncTask.Status.RUNNING)) {
            this.AsyncTaskSeriesStreams.cancel(true);
        }
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
        if (this.frameLayout != null) {
            this.frameLayout.setVisibility(8);
        }
        if (this.mAdapter != null) {
            this.mAdapter.setVisibiltygone(pbPagingLoader1);
            this.mAdapter.notifyDataSetChanged();
        }
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        if (!this.loginPreferencesAfterLogin.getString("username", "").equals("") || !this.loginPreferencesAfterLogin.getString("password", "").equals("")) {
            Context context2 = this.context;
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_bt_up) {
            this.AsyncTaskSeriesStreams = new SeriesActivityNewFlowM3UAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
        } else if (id == R.id.tv_header_title) {
            startActivity(new Intent(this, NewDashboardActivity.class));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.toolbar.inflateMenu(R.menu.menu_search);
        this.menuSelect = menu;
        this.menuItemSettings = menu.getItem(2).getSubMenu().findItem(R.id.empty);
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
        this.menuItemSettings = menuItem;
        this.toolbar.collapseActionView();
        int itemId = menuItem.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(this, NewDashboardActivity.class));
            finish();
        }
        if (itemId == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        if (itemId == R.id.action_logout1 && this.context != null) {
            new AlertDialog.Builder(this.context, R.style.AlertDialogCustom).setTitle(getResources().getString(R.string.logout_title)).setMessage(getResources().getString(R.string.logout_message)).setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesActivityNewFlowM3U.AnonymousClass4 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(SeriesActivityNewFlowM3U.this.context);
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesActivityNewFlowM3U.AnonymousClass3 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
        if (itemId == R.id.menu_load_channels_vod1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(this.context.getResources().getString(R.string.confirm_to_refresh));
            builder.setMessage(this.context.getResources().getString(R.string.do_you_want_toproceed));
            builder.setIcon((int) R.drawable.questionmark);
            builder.setPositiveButton(this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesActivityNewFlowM3U.AnonymousClass5 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadChannelsAndVod(SeriesActivityNewFlowM3U.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesActivityNewFlowM3U.AnonymousClass6 */

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
                /* class com.nst.yourname.view.activity.SeriesActivityNewFlowM3U.AnonymousClass7 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadTvGuid(SeriesActivityNewFlowM3U.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesActivityNewFlowM3U.AnonymousClass8 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder2.show();
        }
        if (itemId == R.id.action_search) {
            this.searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
            this.searchView.setQueryHint(getResources().getString(R.string.search_series_category));
            this.searchView.setIconifiedByDefault(false);
            this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                /* class com.nst.yourname.view.activity.SeriesActivityNewFlowM3U.AnonymousClass9 */

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextSubmit(String str) {
                    return false;
                }

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextChange(String str) {
                    SeriesActivityNewFlowM3U.this.tvNoRecordFound.setVisibility(8);
                    if (SeriesActivityNewFlowM3U.this.mAdapter == null || SeriesActivityNewFlowM3U.this.tvNoRecordFound == null || SeriesActivityNewFlowM3U.this.tvNoRecordFound.getVisibility() == 0) {
                        return false;
                    }
                    SeriesActivityNewFlowM3U.this.mAdapter.filter(str, SeriesActivityNewFlowM3U.this.tvNoRecordFound);
                    return false;
                }
            });
        }
        if (itemId == R.id.menu_sort) {
            showSortPopup(this);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void showSortPopup(final Activity activity) {
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
            radioButton2.setVisibility(8);
            RadioButton radioButton3 = (RadioButton) inflate.findViewById(R.id.rb_atoz);
            RadioButton radioButton4 = (RadioButton) inflate.findViewById(R.id.rb_ztoa);
            RadioButton radioButton5 = (RadioButton) inflate.findViewById(R.id.rb_channel_asc);
            radioButton5.setVisibility(8);
            RadioButton radioButton6 = (RadioButton) inflate.findViewById(R.id.rb_channel_desc);
            radioButton6.setVisibility(8);
            radioButton.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton));
            radioButton2.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton2));
            radioButton3.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton3));
            radioButton4.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton4));
            radioButton5.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton5));
            radioButton6.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton6));
            String str = SharepreferenceDBHandler.getseriesActivitynewFlowSort(activity);
            switch (str.hashCode()) {
                case 49:
                    if (str.equals("1")) {
                        c = 0;
                        break;
                    }
                    break;
                case 50:
                    if (str.equals("2")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    radioButton3.setChecked(true);
                    break;
                case 1:
                    radioButton4.setChecked(true);
                    break;
                default:
                    radioButton.setChecked(true);
                    break;
            }
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesActivityNewFlowM3U.AnonymousClass10 */

                public void onClick(View view) {
                    SeriesActivityNewFlowM3U.this.changeSortPopUp.dismiss();
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesActivityNewFlowM3U.AnonymousClass11 */

                public void onClick(View view) {
                    RadioButton radioButton = (RadioButton) inflate.findViewById(radioGroup.getCheckedRadioButtonId());
                    if (radioButton.getText().toString().equals(SeriesActivityNewFlowM3U.this.getResources().getString(R.string.sort_atoz))) {
                        SharepreferenceDBHandler.setseriesActivitynewFlowSort("1", activity);
                    } else if (radioButton.getText().toString().equals(SeriesActivityNewFlowM3U.this.getResources().getString(R.string.sort_ztoa))) {
                        SharepreferenceDBHandler.setseriesActivitynewFlowSort("2", activity);
                    } else {
                        SharepreferenceDBHandler.setseriesActivitynewFlowSort(AppConst.PASSWORD_UNSET, activity);
                    }
                    AsyncTask unused = SeriesActivityNewFlowM3U.this.AsyncTaskSeriesStreams = new SeriesActivityNewFlowM3UAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                    SeriesActivityNewFlowM3U.this.changeSortPopUp.dismiss();
                }
            });
        } catch (Exception unused) {
        }
    }

    public void progressBar(ProgressBar progressBar) {
        pbPagingLoader1 = progressBar;
    }

    @RequiresApi(api = 19)
    public void hideSystemUi() {
        this.activityLogin.setSystemUiVisibility(4871);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 82) {
            return super.onKeyUp(i, keyEvent);
        }
        if (this.menuSelect == null) {
            return true;
        }
        this.menuSelect.performIdentifierAction(R.id.empty, 0);
        return true;
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        boolean z = keyEvent.getAction() == 0;
        if (keyCode == 82) {
            return z ? onKeyDown(keyCode, keyEvent) : onKeyUp(keyCode, keyEvent);
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    private class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private final View view;

        public OnFocusChangeAccountListener(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            if (z) {
                performScaleXAnimation(1.15f);
                performScaleYAnimation(1.15f);
            } else if (!z) {
                performScaleXAnimation(1.0f);
                performScaleYAnimation(1.0f);
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
}
