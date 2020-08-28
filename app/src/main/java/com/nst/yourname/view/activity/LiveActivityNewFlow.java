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
import com.nst.yourname.view.adapter.LiveAdapterNewFlow;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class LiveActivityNewFlow extends AppCompatActivity implements View.OnClickListener {
    static final boolean $assertionsDisabled = false;
    private static final String JSON = "";
    static ProgressBar pbPagingLoader1;
    public AsyncTask AsyncTaskLiveActivityNewFlow = null;
    private SharedPreferences SharedPreferencesSort;
    private SharedPreferences.Editor SharedPreferencesSortEditor;
    int TOTAL_PAGES = 1;
    int actionBarHeight;
    LiveAdapterNewFlow adapter;
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
    int currentPage = -1;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive = new DatabaseUpdatedStatusDBModel();
    @BindView(R.id.empty_view)
    TextView emptyView;
    int endValue = 20;
    @BindView(R.id.fl_frame)
    FrameLayout frameLayout;
    public GridLayoutManager gridLayoutManager;
    Handler handler;
    boolean isLastPage = false;
    boolean isLoading = false;
    @BindView(R.id.iv_bt_up)
    ImageView ivBTUP;
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
    private LiveAdapterNewFlow mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    MenuItem menuItemSettings;
    Menu menuSelect;
    ArrayList<LiveStreamCategoryIdDBModel> movies2 = new ArrayList<>();
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    @BindView(R.id.pb_paging_loader)
    ProgressBar pbPagingLoader;
    @BindView(R.id.rl_no_arrangement_found)
    RelativeLayout rl_no_arrangement_found;
    SearchView searchView;
    int startValue = 0;
    ArrayList<LiveStreamCategoryIdDBModel> subCategoryList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    TypedValue f7tv;
    @BindView(R.id.tv_no_record_found)
    TextView tvNoRecordFound;
    @BindView(R.id.tv_noStream)
    TextView tvNoStream;
    private String userName = "";
    private String userPassword = "";
    private XMLTVPresenter xmltvPresenter;

    public void onFinish() {
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_live_new_flow);
        ButterKnife.bind(this);
        if (this.appbarToolbar != null) {
            this.appbarToolbar.setBackground(getResources().getDrawable(R.drawable.layout_background_tv));
        }
        changeStatusBarColor();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        this.context = this;
        this.handler = new Handler();
        this.handler.removeCallbacksAndMessages(null);
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(0);
        }
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.LiveActivityNewFlow.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(LiveActivityNewFlow.this.context);
            }
        });
        this.AsyncTaskLiveActivityNewFlow = new LiveActivityNewFlowAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
        this.frameLayout.setVisibility(8);
    }

    @SuppressLint({"StaticFieldLeak"})
    class LiveActivityNewFlowAsync extends AsyncTask<String, Void, Boolean> {
        LiveActivityNewFlowAsync() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public Boolean doInBackground(String... strArr) {
            try {
                return Boolean.valueOf(LiveActivityNewFlow.this.intiliaze());
            } catch (Exception unused) {
                return false;
            }
        }

        public void onPostExecute(Boolean bool) {
            super.onPostExecute((Boolean) bool);
            if (SharepreferenceDBHandler.getCurrentAPPType(LiveActivityNewFlow.this.context).equals(AppConst.TYPE_M3U)) {
                if (LiveActivityNewFlow.this.liveListDetailAvailable != null && LiveActivityNewFlow.this.allCount != 0) {
                    LiveActivityNewFlow.this.adapter = new LiveAdapterNewFlow(LiveActivityNewFlow.this.liveListDetailAvailable, LiveActivityNewFlow.this.context);
                    if ((LiveActivityNewFlow.this.getResources().getConfiguration().screenLayout & 15) == 3) {
                        GridLayoutManager unused = LiveActivityNewFlow.this.gridLayoutManager = new GridLayoutManager(LiveActivityNewFlow.this.context, 2);
                    } else {
                        GridLayoutManager unused2 = LiveActivityNewFlow.this.gridLayoutManager = new GridLayoutManager(LiveActivityNewFlow.this.context, 2);
                    }
                    LiveActivityNewFlow.this.myRecyclerView.setLayoutManager(LiveActivityNewFlow.this.gridLayoutManager);
                    LiveActivityNewFlow.this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    LiveActivityNewFlow.this.myRecyclerView.setAdapter(LiveActivityNewFlow.this.adapter);
                    if (LiveActivityNewFlow.this.pbLoader != null) {
                        LiveActivityNewFlow.this.pbLoader.setVisibility(8);
                    }
                } else if (LiveActivityNewFlow.this.pbLoader != null) {
                    LiveActivityNewFlow.this.pbLoader.setVisibility(8);
                    LiveActivityNewFlow.this.rl_no_arrangement_found.setVisibility(0);
                }
            } else if (LiveActivityNewFlow.this.liveListDetailAvailable != null) {
                LiveActivityNewFlow.this.adapter = new LiveAdapterNewFlow(LiveActivityNewFlow.this.liveListDetailAvailable, LiveActivityNewFlow.this.context);
                if ((LiveActivityNewFlow.this.getResources().getConfiguration().screenLayout & 15) == 3) {
                    GridLayoutManager unused3 = LiveActivityNewFlow.this.gridLayoutManager = new GridLayoutManager(LiveActivityNewFlow.this.context, 2);
                } else {
                    GridLayoutManager unused4 = LiveActivityNewFlow.this.gridLayoutManager = new GridLayoutManager(LiveActivityNewFlow.this.context, 2);
                }
                LiveActivityNewFlow.this.myRecyclerView.setLayoutManager(LiveActivityNewFlow.this.gridLayoutManager);
                LiveActivityNewFlow.this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
                LiveActivityNewFlow.this.myRecyclerView.setAdapter(LiveActivityNewFlow.this.adapter);
                if (LiveActivityNewFlow.this.pbLoader != null) {
                    LiveActivityNewFlow.this.pbLoader.setVisibility(8);
                }
            }
        }
    }

    public boolean intiliaze() {
        try {
            if (this.context != null) {
                this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
                this.categoryWithPasword = new ArrayList<>();
                this.liveListDetailUnlcked = new ArrayList<>();
                this.liveListDetailUnlckedDetail = new ArrayList<>();
                this.liveListDetailAvailable = new ArrayList<>();
                this.liveListDetail = new ArrayList<>();
                this.liveListDetailAvailableForCounter = new ArrayList<>();
                this.liveListDetail = this.liveStreamDBHandler.getAllliveCategories();
                LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
                LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel2 = new LiveStreamCategoryIdDBModel();
                LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel3 = new LiveStreamCategoryIdDBModel();
                liveStreamCategoryIdDBModel.setLiveStreamCategoryID(AppConst.PASSWORD_UNSET);
                liveStreamCategoryIdDBModel.setLiveStreamCategoryName(getResources().getString(R.string.all));
                liveStreamCategoryIdDBModel2.setLiveStreamCategoryID("-1");
                liveStreamCategoryIdDBModel2.setLiveStreamCategoryName(getResources().getString(R.string.favourites));
                if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                    this.countUncat = this.liveStreamDBHandler.getUncatCountM3UByType("live");
                    if (this.countUncat != 0 && this.countUncat > 0) {
                        liveStreamCategoryIdDBModel3.setLiveStreamCategoryID("");
                        liveStreamCategoryIdDBModel3.setLiveStreamCategoryName(getResources().getString(R.string.uncategories));
                        this.liveListDetail.add(this.liveListDetail.size(), liveStreamCategoryIdDBModel3);
                    }
                } else {
                    this.countUncat = this.liveStreamDBHandler.getUncatCount("-2", "live");
                    if (this.countUncat != 0 && this.countUncat > 0) {
                        liveStreamCategoryIdDBModel3.setLiveStreamCategoryID("-2");
                        liveStreamCategoryIdDBModel3.setLiveStreamCategoryName(getResources().getString(R.string.uncategories));
                        this.liveListDetail.add(this.liveListDetail.size(), liveStreamCategoryIdDBModel3);
                    }
                }
                if (this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context)) <= 0 || this.liveListDetail == null) {
                    this.liveListDetail.add(0, liveStreamCategoryIdDBModel);
                    this.liveListDetail.add(1, liveStreamCategoryIdDBModel2);
                    this.liveListDetailAvailable = this.liveListDetail;
                } else {
                    this.listPassword = getPasswordSetCategories();
                    this.liveListDetailUnlckedDetail = getUnlockedCategories(this.liveListDetail, this.listPassword);
                    if (this.liveListDetailUnlcked != null) {
                        this.liveListDetailUnlcked.add(0, liveStreamCategoryIdDBModel);
                        this.liveListDetailUnlcked.add(1, liveStreamCategoryIdDBModel2);
                    }
                    if (this.liveListDetailUnlckedDetail != null) {
                        this.liveListDetailAvailable = this.liveListDetailUnlckedDetail;
                    }
                }
                if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                    this.bt_explore_all.setOnClickListener(new View.OnClickListener() {
                        /* class com.nst.yourname.view.activity.LiveActivityNewFlow.AnonymousClass2 */

                        public void onClick(View view) {
                            LiveActivityNewFlow.this.startActivity(new Intent(LiveActivityNewFlow.this.context, PlaylistsCategoriesActivity.class));
                            LiveActivityNewFlow.this.finish();
                            LiveActivityNewFlow.this.overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                        }
                    });
                    if (this.liveListDetailAvailable != null && this.liveListDetailAvailable.size() > 0) {
                        int i = 0;
                        int i2 = 0;
                        while (true) {
                            if (i < this.liveListDetailAvailable.size()) {
                                if (this.AsyncTaskLiveActivityNewFlow != null && this.AsyncTaskLiveActivityNewFlow.isCancelled()) {
                                    break;
                                }
                                if (this.liveListDetailAvailable.get(i).getLiveStreamCategoryID().equals(AppConst.PASSWORD_UNSET) || this.liveListDetailAvailable.get(i).getLiveStreamCategoryID().equals("-1")) {
                                    int availableCountM3U = this.liveStreamDBHandler.getAvailableCountM3U(this.liveListDetailAvailable.get(i).getLiveStreamCategoryID(), "live");
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
                                    int availableCountM3U2 = this.liveStreamDBHandler.getAvailableCountM3U(this.liveListDetailAvailable.get(i).getLiveStreamCategoryID(), "live");
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
                } else {
                    if (this.liveListDetailAvailable != null && this.liveListDetailAvailable.size() > 0) {
                        int i3 = 0;
                        int i4 = 0;
                        while (true) {
                            if (i3 < this.liveListDetailAvailable.size()) {
                                if (this.AsyncTaskLiveActivityNewFlow != null && this.AsyncTaskLiveActivityNewFlow.isCancelled()) {
                                    break;
                                }
                                if (this.liveListDetailAvailable.get(i3).getLiveStreamCategoryID().equals(AppConst.PASSWORD_UNSET) || this.liveListDetailAvailable.get(i3).getLiveStreamCategoryID().equals("-1")) {
                                    int subCatMovieCount = this.liveStreamDBHandler.getSubCatMovieCount(this.liveListDetailAvailable.get(i3).getLiveStreamCategoryID(), "live");
                                    if (this.liveListDetailAvailable.get(i3).getLiveStreamCategoryID().equals(AppConst.PASSWORD_UNSET)) {
                                        this.allCount = subCatMovieCount;
                                    }
                                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel6 = new LiveStreamCategoryIdDBModel();
                                    liveStreamCategoryIdDBModel6.setLiveStreamCounter(subCatMovieCount);
                                    liveStreamCategoryIdDBModel6.setLiveStreamCategoryName(this.liveListDetailAvailable.get(i3).getLiveStreamCategoryName());
                                    liveStreamCategoryIdDBModel6.setLiveStreamCategoryID(this.liveListDetailAvailable.get(i3).getLiveStreamCategoryID());
                                    this.liveListDetailAvailableForCounter.add(i4, liveStreamCategoryIdDBModel6);
                                    i4++;
                                } else {
                                    int subCatMovieCount2 = this.liveStreamDBHandler.getSubCatMovieCount(this.liveListDetailAvailable.get(i3).getLiveStreamCategoryID(), "live");
                                    if (subCatMovieCount2 != 0) {
                                        LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel7 = new LiveStreamCategoryIdDBModel();
                                        liveStreamCategoryIdDBModel7.setLiveStreamCounter(subCatMovieCount2);
                                        liveStreamCategoryIdDBModel7.setLiveStreamCategoryName(this.liveListDetailAvailable.get(i3).getLiveStreamCategoryName());
                                        liveStreamCategoryIdDBModel7.setLiveStreamCategoryID(this.liveListDetailAvailable.get(i3).getLiveStreamCategoryID());
                                        this.liveListDetailAvailableForCounter.add(i4, liveStreamCategoryIdDBModel7);
                                        i4++;
                                    }
                                }
                                i3++;
                            } else {
                                break;
                            }
                        }
                    }
                    if (this.liveListDetailAvailableForCounter != null && this.liveListDetailAvailableForCounter.size() > 0) {
                        this.liveListDetailAvailable = this.liveListDetailAvailableForCounter;
                    }
                }
            }
            return true;
        } catch (NullPointerException unused) {
            return false;
        } catch (Exception unused2) {
            return false;
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

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        if (this.myRecyclerView != null) {
            this.myRecyclerView.setClickable(true);
        }
        if (!(this.adapter == null || pbPagingLoader1 == null)) {
            this.adapter.setVisibiltygone(pbPagingLoader1);
            this.adapter.notifyDataSetChanged();
        }
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onDestroy() {
        super.onDestroy();
        if (this.AsyncTaskLiveActivityNewFlow != null && this.AsyncTaskLiveActivityNewFlow.getStatus().equals(AsyncTask.Status.RUNNING)) {
            this.AsyncTaskLiveActivityNewFlow.cancel(true);
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
        if (this.adapter != null) {
            this.adapter.notifyDataSetChanged();
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
            this.AsyncTaskLiveActivityNewFlow = new LiveActivityNewFlowAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
        } else if (id == R.id.logo) {
            Utils.Redrirect_to_Home(this.context);
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
                /* class com.nst.yourname.view.activity.LiveActivityNewFlow.AnonymousClass4 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(LiveActivityNewFlow.this.context);
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.LiveActivityNewFlow.AnonymousClass3 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
        if (itemId == R.id.action_search) {
            this.searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
            this.searchView.setQueryHint(getResources().getString(R.string.search_live_categories));
            this.searchView.setIconifiedByDefault(false);
            this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                /* class com.nst.yourname.view.activity.LiveActivityNewFlow.AnonymousClass5 */

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextSubmit(String str) {
                    return false;
                }

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextChange(String str) {
                    LiveActivityNewFlow.this.tvNoRecordFound.setVisibility(8);
                    if (LiveActivityNewFlow.this.adapter == null || LiveActivityNewFlow.this.tvNoStream == null || LiveActivityNewFlow.this.tvNoStream.getVisibility() == 0) {
                        return false;
                    }
                    LiveActivityNewFlow.this.adapter.filter(str, LiveActivityNewFlow.this.tvNoRecordFound);
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
                /* class com.nst.yourname.view.activity.LiveActivityNewFlow.AnonymousClass6 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadChannelsAndVod(LiveActivityNewFlow.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.LiveActivityNewFlow.AnonymousClass7 */

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
                /* class com.nst.yourname.view.activity.LiveActivityNewFlow.AnonymousClass8 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadTvGuid(LiveActivityNewFlow.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.LiveActivityNewFlow.AnonymousClass9 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder2.show();
        }
        if (itemId == R.id.menu_sort) {
            this.tvNoRecordFound.setVisibility(8);
            showSortPopup(this);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void showSortPopup(final Activity activity) {
        try {
            final View inflate = ((LayoutInflater) activity.getSystemService("layout_inflater")).inflate((int) R.layout.sort_layout, (RelativeLayout) activity.findViewById(R.id.rl_password_prompt));
            this.changeSortPopUp = new PopupWindow(activity);
            this.changeSortPopUp.setContentView(inflate);
            this.changeSortPopUp.setWidth(-1);
            this.changeSortPopUp.setHeight(-1);
            this.changeSortPopUp.setFocusable(true);
            this.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
            Button button = (Button) inflate.findViewById(R.id.bt_save_password);
            Button button2 = (Button) inflate.findViewById(R.id.bt_close);
            final RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.rg_radio);
            RadioButton radioButton = (RadioButton) inflate.findViewById(R.id.rb_normal);
            radioButton.requestFocus();
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
            String liveActivitynewFlowSort = SharepreferenceDBHandler.getLiveActivitynewFlowSort(activity);
            if (liveActivitynewFlowSort.equals("1")) {
                radioButton3.setChecked(true);
            } else if (liveActivitynewFlowSort.equals("2")) {
                radioButton4.setChecked(true);
            } else {
                radioButton.setChecked(true);
            }
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.LiveActivityNewFlow.AnonymousClass10 */

                public void onClick(View view) {
                    LiveActivityNewFlow.this.changeSortPopUp.dismiss();
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.LiveActivityNewFlow.AnonymousClass11 */

                public void onClick(View view) {
                    RadioButton radioButton = (RadioButton) inflate.findViewById(radioGroup.getCheckedRadioButtonId());
                    if (radioButton.getText().toString().equals(LiveActivityNewFlow.this.getResources().getString(R.string.sort_atoz))) {
                        SharepreferenceDBHandler.setLiveActivitynewFlowSort("1", activity);
                    } else if (radioButton.getText().toString().equals(LiveActivityNewFlow.this.getResources().getString(R.string.sort_ztoa))) {
                        SharepreferenceDBHandler.setLiveActivitynewFlowSort("2", activity);
                    } else {
                        SharepreferenceDBHandler.setLiveActivitynewFlowSort(AppConst.PASSWORD_UNSET, activity);
                    }
                    AsyncTask unused = LiveActivityNewFlow.this.AsyncTaskLiveActivityNewFlow = new LiveActivityNewFlowAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                    LiveActivityNewFlow.this.changeSortPopUp.dismiss();
                }
            });
        } catch (Exception unused) {
        }
    }

    public void progressBar(ProgressBar progressBar) {
        pbPagingLoader1 = progressBar;
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
