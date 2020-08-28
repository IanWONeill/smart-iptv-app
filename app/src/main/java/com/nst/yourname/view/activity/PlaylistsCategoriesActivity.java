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
import com.nst.yourname.view.adapter.PlaylistsCategoriesAdapter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class PlaylistsCategoriesActivity extends AppCompatActivity implements View.OnClickListener {
    static final boolean $assertionsDisabled = false;
    private static final String JSON = "";
    static ProgressBar pbPagingLoader1;
    public AsyncTask AsyncTaskPlaylistCategories = null;
    private SharedPreferences SharedPreferencesSort;
    private SharedPreferences.Editor SharedPreferencesSortEditor;
    int TOTAL_PAGES = 1;
    int actionBarHeight;
    PlaylistsCategoriesAdapter adapter;
    @BindView(R.id.adView)
    RelativeLayout adviewLayout;
    @BindView(R.id.appbar_toolbar)
    AppBarLayout appbarToolbar;
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
    private PlaylistsCategoriesAdapter mAdapter;
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
    SearchView searchView;
    int startValue = 0;
    ArrayList<LiveStreamCategoryIdDBModel> subCategoryList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    TypedValue f14tv;
    @BindView(R.id.tv_no_record_found)
    TextView tvNoRecordFound;
    @BindView(R.id.tv_noStream)
    TextView tvNoStream;
    @BindView(R.id.tv_settings)
    TextView tv_settings;
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
        if (this.tv_settings != null) {
            this.tv_settings.setText(getResources().getString(R.string.all));
        }
        changeStatusBarColor();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        this.context = this;
        this.handler = new Handler();
        this.handler.removeCallbacksAndMessages(null);
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(0);
        }
        this.AsyncTaskPlaylistCategories = new playlistCategories().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
        this.frameLayout.setVisibility(8);
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.PlaylistsCategoriesActivity.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(PlaylistsCategoriesActivity.this.context);
            }
        });
    }

    @SuppressLint({"StaticFieldLeak"})
    class playlistCategories extends AsyncTask<String, Void, Boolean> {
        playlistCategories() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public Boolean doInBackground(String... strArr) {
            try {
                return Boolean.valueOf(PlaylistsCategoriesActivity.this.intiliaze());
            } catch (Exception unused) {
                return false;
            }
        }

        public void onPostExecute(Boolean bool) {
            super.onPostExecute((Boolean) bool);
            if (PlaylistsCategoriesActivity.this.liveListDetailAvailable != null) {
                PlaylistsCategoriesActivity.this.adapter = new PlaylistsCategoriesAdapter(PlaylistsCategoriesActivity.this.liveListDetailAvailable, PlaylistsCategoriesActivity.this.context, PlaylistsCategoriesActivity.this);
                if ((PlaylistsCategoriesActivity.this.getResources().getConfiguration().screenLayout & 15) == 3) {
                    GridLayoutManager unused = PlaylistsCategoriesActivity.this.gridLayoutManager = new GridLayoutManager(PlaylistsCategoriesActivity.this.context, 2);
                } else {
                    GridLayoutManager unused2 = PlaylistsCategoriesActivity.this.gridLayoutManager = new GridLayoutManager(PlaylistsCategoriesActivity.this.context, 2);
                }
                PlaylistsCategoriesActivity.this.myRecyclerView.setLayoutManager(PlaylistsCategoriesActivity.this.gridLayoutManager);
                PlaylistsCategoriesActivity.this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
                PlaylistsCategoriesActivity.this.myRecyclerView.setAdapter(PlaylistsCategoriesActivity.this.adapter);
            }
            if (PlaylistsCategoriesActivity.this.pbLoader != null) {
                PlaylistsCategoriesActivity.this.pbLoader.setVisibility(8);
            }
        }
    }

    public boolean intiliaze() {
        int i;
        try {
            if (this.context != null) {
                this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
                this.categoryWithPasword = new ArrayList<>();
                this.liveListDetailUnlcked = new ArrayList<>();
                this.liveListDetailUnlckedDetail = new ArrayList<>();
                this.liveListDetailAvailable = new ArrayList<>();
                this.liveListDetailAvailableForCounter = new ArrayList<>();
                this.liveListDetail = new ArrayList<>();
                this.liveListDetail = this.liveStreamDBHandler.getAllliveCategories();
                ArrayList<LiveStreamCategoryIdDBModel> allm3uCategories = this.liveStreamDBHandler.getAllm3uCategories();
                this.countUncat = this.liveStreamDBHandler.getUncatCountM3UALL(true);
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
                    allm3uCategories.add(allm3uCategories.size(), liveStreamCategoryIdDBModel3);
                }
                if (this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context)) <= 0 || allm3uCategories == null) {
                    allm3uCategories.add(0, liveStreamCategoryIdDBModel);
                    allm3uCategories.add(1, liveStreamCategoryIdDBModel2);
                    this.liveListDetailAvailable = allm3uCategories;
                } else {
                    this.listPassword = getPasswordSetCategories();
                    this.liveListDetailUnlckedDetail = getUnlockedCategories(allm3uCategories, this.listPassword);
                    if (this.liveListDetailUnlcked != null) {
                        this.liveListDetailUnlcked.add(0, liveStreamCategoryIdDBModel);
                        this.liveListDetailUnlcked.add(1, liveStreamCategoryIdDBModel2);
                    }
                    if (this.liveListDetailUnlckedDetail != null) {
                        this.liveListDetailAvailable = this.liveListDetailUnlckedDetail;
                    }
                }
                if (this.liveListDetailAvailable != null && this.liveListDetailAvailable.size() > 0) {
                    int i2 = 0;
                    int i3 = 0;
                    while (true) {
                        if (i2 < this.liveListDetailAvailable.size()) {
                            if (this.AsyncTaskPlaylistCategories != null && this.AsyncTaskPlaylistCategories.isCancelled()) {
                                break;
                            }
                            if (this.liveListDetailAvailable.get(i2).getLiveStreamCategoryID().equals(AppConst.PASSWORD_UNSET) || this.liveListDetailAvailable.get(i2).getLiveStreamCategoryID().equals("-1") || this.liveListDetailAvailable.get(i2).getLiveStreamCategoryID().equals("")) {
                                LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel4 = new LiveStreamCategoryIdDBModel();
                                if (this.liveListDetailAvailable.get(i2).getLiveStreamCategoryID().equals("")) {
                                    i = this.countUncat;
                                } else {
                                    i = this.liveStreamDBHandler.getM3UCount(this.liveListDetailAvailable.get(i2).getLiveStreamCategoryID());
                                }
                                if (i != 0) {
                                    liveStreamCategoryIdDBModel4.setLiveStreamCounter(i);
                                } else {
                                    liveStreamCategoryIdDBModel4.setLiveStreamCounter(0);
                                }
                                liveStreamCategoryIdDBModel4.setLiveStreamCategoryName(this.liveListDetailAvailable.get(i2).getLiveStreamCategoryName());
                                liveStreamCategoryIdDBModel4.setLiveStreamCategoryID(this.liveListDetailAvailable.get(i2).getLiveStreamCategoryID());
                                this.liveListDetailAvailableForCounter.add(i3, liveStreamCategoryIdDBModel4);
                                i3++;
                            } else {
                                int m3UCount = this.liveStreamDBHandler.getM3UCount(this.liveListDetailAvailable.get(i2).getLiveStreamCategoryID());
                                if (m3UCount != 0) {
                                    LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel5 = new LiveStreamCategoryIdDBModel();
                                    liveStreamCategoryIdDBModel5.setLiveStreamCounter(m3UCount);
                                    liveStreamCategoryIdDBModel5.setLiveStreamCategoryName(this.liveListDetailAvailable.get(i2).getLiveStreamCategoryName());
                                    liveStreamCategoryIdDBModel5.setLiveStreamCategoryID(this.liveListDetailAvailable.get(i2).getLiveStreamCategoryID());
                                    this.liveListDetailAvailableForCounter.add(i3, liveStreamCategoryIdDBModel5);
                                    i3++;
                                }
                            }
                            i2++;
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

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onDestroy() {
        super.onDestroy();
        if (this.AsyncTaskPlaylistCategories != null && this.AsyncTaskPlaylistCategories.getStatus().equals(AsyncTask.Status.RUNNING)) {
            this.AsyncTaskPlaylistCategories.cancel(true);
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
            this.adapter.setVisibiltygone(pbPagingLoader1);
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
            this.AsyncTaskPlaylistCategories = new playlistCategories().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
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
                /* class com.nst.yourname.view.activity.PlaylistsCategoriesActivity.AnonymousClass3 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(PlaylistsCategoriesActivity.this.context);
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.PlaylistsCategoriesActivity.AnonymousClass2 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
        if (itemId == R.id.action_search) {
            this.searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
            this.searchView.setQueryHint(getResources().getString(R.string.search));
            this.searchView.setIconifiedByDefault(false);
            this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                /* class com.nst.yourname.view.activity.PlaylistsCategoriesActivity.AnonymousClass4 */

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextSubmit(String str) {
                    return false;
                }

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextChange(String str) {
                    PlaylistsCategoriesActivity.this.tvNoRecordFound.setVisibility(8);
                    if (PlaylistsCategoriesActivity.this.adapter == null || PlaylistsCategoriesActivity.this.tvNoStream == null || PlaylistsCategoriesActivity.this.tvNoStream.getVisibility() == 0) {
                        return false;
                    }
                    PlaylistsCategoriesActivity.this.adapter.filter(str, PlaylistsCategoriesActivity.this.tvNoRecordFound);
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
                /* class com.nst.yourname.view.activity.PlaylistsCategoriesActivity.AnonymousClass5 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadChannelsAndVod(PlaylistsCategoriesActivity.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.PlaylistsCategoriesActivity.AnonymousClass6 */

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
                /* class com.nst.yourname.view.activity.PlaylistsCategoriesActivity.AnonymousClass7 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadTvGuid(PlaylistsCategoriesActivity.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.PlaylistsCategoriesActivity.AnonymousClass8 */

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
            String playListCategorySort = SharepreferenceDBHandler.getPlayListCategorySort(activity);
            if (playListCategorySort.equals("2")) {
                radioButton3.setChecked(true);
            } else if (playListCategorySort.equals("3")) {
                radioButton4.setChecked(true);
            } else {
                radioButton.setChecked(true);
            }
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.PlaylistsCategoriesActivity.AnonymousClass9 */

                public void onClick(View view) {
                    PlaylistsCategoriesActivity.this.changeSortPopUp.dismiss();
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.PlaylistsCategoriesActivity.AnonymousClass10 */

                public void onClick(View view) {
                    RadioButton radioButton = (RadioButton) inflate.findViewById(radioGroup.getCheckedRadioButtonId());
                    if (radioButton.getText().toString().equals(PlaylistsCategoriesActivity.this.getResources().getString(R.string.sort_atoz))) {
                        SharepreferenceDBHandler.setPlayListCategorySort("2", activity);
                    } else if (radioButton.getText().toString().equals(PlaylistsCategoriesActivity.this.getResources().getString(R.string.sort_ztoa))) {
                        SharepreferenceDBHandler.setPlayListCategorySort("3", activity);
                    } else {
                        SharepreferenceDBHandler.setPlayListCategorySort(AppConst.PASSWORD_UNSET, activity);
                    }
                    AsyncTask unused = PlaylistsCategoriesActivity.this.AsyncTaskPlaylistCategories = new playlistCategories().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                    PlaylistsCategoriesActivity.this.changeSortPopUp.dismiss();
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
