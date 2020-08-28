package com.nst.yourname.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.AdapterSectionRecycler;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.model.callback.GetEpisdoeDetailsCallback;
import com.nst.yourname.model.callback.SeriesDBModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.PasswordStatusDBModel;
import com.nst.yourname.model.database.SeriesRecentWatchDatabase;
import com.nst.yourname.model.database.SeriesStreamsDatabaseHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.presenter.XMLTVPresenter;
import com.nst.yourname.view.adapter.EpisodeDetailAdapter;
import com.nst.yourname.view.adapter.SeriesAdapter;
import com.nst.yourname.view.adapter.VodAdapterNewFlow;
import com.nst.yourname.view.adapter.VodSubCatAdpaterNew;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public class SeriesActivitNewFlowSubCat extends AppCompatActivity implements View.OnClickListener, EpisodeDetailAdapter.EpisodeItemClickListener {
    static final boolean $assertionsDisabled = false;
    private static final String JSON = "";
    static ProgressBar pbPagingLoader1;
    private static ArrayList<LiveStreamCategoryIdDBModel> subCategoryList = new ArrayList<>();
    private static ArrayList<LiveStreamCategoryIdDBModel> subCategoryListFinal = new ArrayList<>();
    private static ArrayList<LiveStreamCategoryIdDBModel> subCategoryListFinal_menu = new ArrayList<>();
    ArrayList<GetEpisdoeDetailsCallback> GetEpisdoeDetailsCallbackNew = new ArrayList<>();
    private SharedPreferences SharedPreferencesSort;
    private SharedPreferences.Editor SharedPreferencesSortEditor;
    private String action;
    int actionBarHeight;
    private AdapterSectionRecycler adapterRecycler;
    @BindView(R.id.appbar_toolbar)
    AppBarLayout appbarToolbar;
    ArrayList<LiveStreamCategoryIdDBModel> categoriesList;
    private ArrayList<PasswordStatusDBModel> categoryWithPasword;
    public PopupWindow changeSortPopUp;
    TextView clientNameTv;
    public Context context;
    DatabaseHandler database;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive = new DatabaseUpdatedStatusDBModel();
    private SharedPreferences.Editor editor;
    private ArrayList<GetEpisdoeDetailsCallback> episdoeDetailsCallbacksList = new ArrayList<>();
    public List<GetEpisdoeDetailsCallback> episdoeDetailsList = new ArrayList();
    public EpisodeDetailAdapter episodeDetailAdapter;
    private ArrayList<FavouriteDBModel> favliveListDetailUnlckedDetail;
    private ArrayList<SeriesDBModel> favouriteStreams = new ArrayList<>();
    public String getActiveLiveStreamCategoryId = "";
    private String getActiveLiveStreamCategoryName = "";
    GridLayoutManager gridLayoutManager;
    Handler handler;
    private boolean isSubcaetgroy = false;
    boolean isSubcaetgroyAvail = false;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> listPassword = new ArrayList<>();
    private ArrayList<SeriesDBModel> liveListDetailAvailable;
    private ArrayList<SeriesDBModel> liveListDetailUnlcked;
    private ArrayList<SeriesDBModel> liveListDetailUnlckedDetail;
    ArrayList<GetEpisdoeDetailsCallback> liveListDetailUnlckedDetailNew = new ArrayList<>();
    private LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences loginPreferencesSharedPref;
    @BindView(R.id.logo)
    ImageView logo;
    private VodAdapterNewFlow mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    MenuItem menuItemSettings;
    Menu menuSelect;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    public SharedPreferences pref;
    private ProgressDialog progressDialog;
    @BindView(R.id.rl_sub_cat)
    RelativeLayout rl_sub_cat;
    SearchView searchView;
    ArrayList<SeriesDBModel> seriesDBModels = new ArrayList<>();
    private SeriesRecentWatchDatabase seriesRecentWatchDatabase;
    SeriesStreamsDatabaseHandler seriesStreamsDatabaseHandle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    TypedValue f17tv;
    @BindView(R.id.tv_no_record_found)
    TextView tvNoRecordFound;
    @BindView(R.id.tv_noStream)
    TextView tvNoStream;
    @BindView(R.id.tv_view_provider)
    TextView tvViewProvider;
    Map<String, String> uniqueCategories = new HashMap();
    Map<String, String> uniqueSeasons = new HashMap();
    private String userName = "";
    private String userPassword = "";
    public SeriesAdapter vodAdapter;
    @BindView(R.id.tv_settings)
    TextView vodCategoryName;
    public VodSubCatAdpaterNew vodSubCatAdpaterNew;
    private XMLTVPresenter xmltvPresenter;

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_series_activit_new_flow_sub_cat);
        getWindow().setFlags(1024, 1024);
        this.context = this;
        Intent intent = getIntent();
        if (intent != null) {
            this.getActiveLiveStreamCategoryId = intent.getStringExtra(AppConst.CATEGORY_ID);
            this.getActiveLiveStreamCategoryName = intent.getStringExtra(AppConst.CATEGORY_NAME);
        }
        this.categoryWithPasword = new ArrayList<>();
        this.liveListDetailUnlckedDetail = new ArrayList<>();
        this.liveListDetailUnlcked = new ArrayList<>();
        this.liveListDetailAvailable = new ArrayList<>();
        this.mAdapter = new VodAdapterNewFlow();
        this.database = new DatabaseHandler(this.context);
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.handler = new Handler();
        setData();
        this.action = this.action;
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.SeriesActivitNewFlowSubCat.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(SeriesActivitNewFlowSubCat.this.context);
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00f7  */
    /* JADX WARNING: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    public void setData() {
        char c;
        String str = this.getActiveLiveStreamCategoryId;
        int hashCode = str.hashCode();
        if (hashCode != 48) {
            if (hashCode == 1444 && str.equals("-1")) {
                c = 0;
                switch (c) {
                    case 0:
                        setContentView((int) R.layout.activity_series_layout);
                        ButterKnife.bind(this);
                        atStart();
                        this.handler.removeCallbacksAndMessages(null);
                        if (this.pbLoader != null) {
                            this.pbLoader.setVisibility(0);
                        }
                        setLayout();
                        break;
                    case 1:
                        setContentView((int) R.layout.activity_series_layout);
                        ButterKnife.bind(this);
                        atStart();
                        this.handler.removeCallbacksAndMessages(null);
                        if (this.pbLoader != null) {
                            this.pbLoader.setVisibility(0);
                        }
                        setLayout();
                        break;
                    default:
                        subCategoryListFinal = this.liveStreamDBHandler.getAllMovieCategoriesHavingParentIdNotZero(this.getActiveLiveStreamCategoryId);
                        if (subCategoryListFinal != null && subCategoryListFinal.size() == 0) {
                            setContentView((int) R.layout.activity_series_layout);
                            ButterKnife.bind(this);
                            atStart();
                            this.handler.removeCallbacksAndMessages(null);
                            if (this.pbLoader != null) {
                                this.pbLoader.setVisibility(0);
                            }
                            setLayout();
                            break;
                        } else {
                            setContentView((int) R.layout.activity_vod_new_flow_subcategories);
                            ButterKnife.bind(this);
                            atStart();
                            this.isSubcaetgroyAvail = true;
                            this.handler.removeCallbacksAndMessages(null);
                            if (this.pbLoader != null) {
                                this.pbLoader.setVisibility(0);
                            }
                            setSubCategoryLayout(subCategoryListFinal);
                            if (this.pbLoader != null) {
                                this.pbLoader.setVisibility(8);
                                break;
                            }
                        }
                        break;
                }
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                if (this.appbarToolbar != null) {
                    this.appbarToolbar.setBackground(getResources().getDrawable(R.drawable.vod_backgound));
                }
                changeStatusBarColor();
                setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
                this.context = this;
                if (!this.getActiveLiveStreamCategoryName.isEmpty()) {
                    this.vodCategoryName.setText(this.getActiveLiveStreamCategoryName);
                    return;
                }
                return;
            }
        } else if (str.equals(AppConst.PASSWORD_UNSET)) {
            c = 1;
            switch (c) {
            }
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
            if (this.appbarToolbar != null) {
            }
            changeStatusBarColor();
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            this.context = this;
            if (!this.getActiveLiveStreamCategoryName.isEmpty()) {
            }
        }
        c = 65535;
        switch (c) {
        }
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        if (this.appbarToolbar != null) {
        }
        changeStatusBarColor();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        this.context = this;
        if (!this.getActiveLiveStreamCategoryName.isEmpty()) {
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (this.isSubcaetgroyAvail) {
            this.toolbar.inflateMenu(R.menu.menu_search);
        } else {
            this.toolbar.inflateMenu(R.menu.menu_search_text_icon);
        }
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
        if (this.getActiveLiveStreamCategoryId.equalsIgnoreCase("-4")) {
            this.menuItemSettings = menu.getItem(2).getSubMenu().findItem(R.id.layout_view_linear).setVisible(true);
            this.menuItemSettings = menu.getItem(2).getSubMenu().findItem(R.id.layout_view_grid).setVisible(true);
            if (this.seriesRecentWatchDatabase == null) {
                this.seriesRecentWatchDatabase = new SeriesRecentWatchDatabase(this.context);
            }
            if (this.seriesRecentWatchDatabase.getSeriesRecentwatchmCount() > 0) {
                menu.getItem(2).getSubMenu().findItem(R.id.nav_delete_all).setVisible(true);
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
            finish();
        }
        if (itemId == R.id.action_logout1 && this.context != null) {
            new AlertDialog.Builder(this.context, R.style.AlertDialogCustom).setTitle(getResources().getString(R.string.logout_title)).setMessage(getResources().getString(R.string.logout_message)).setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesActivitNewFlowSubCat.AnonymousClass3 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(SeriesActivitNewFlowSubCat.this.context);
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesActivitNewFlowSubCat.AnonymousClass2 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
        if (this.isSubcaetgroyAvail) {
            if (itemId == R.id.action_search) {
                this.searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
                this.searchView.setQueryHint(getResources().getString(R.string.search_sub_cat));
                this.searchView.setIconifiedByDefault(false);
                this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    /* class com.nst.yourname.view.activity.SeriesActivitNewFlowSubCat.AnonymousClass4 */

                    @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                    public boolean onQueryTextSubmit(String str) {
                        return false;
                    }

                    @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                    public boolean onQueryTextChange(String str) {
                        SeriesActivitNewFlowSubCat.this.tvNoRecordFound.setVisibility(8);
                        SeriesActivitNewFlowSubCat.this.tvNoStream.setVisibility(8);
                        if (SeriesActivitNewFlowSubCat.this.getActiveLiveStreamCategoryId.equalsIgnoreCase("-4")) {
                            if (SeriesActivitNewFlowSubCat.this.episodeDetailAdapter == null || SeriesActivitNewFlowSubCat.this.tvNoStream == null || SeriesActivitNewFlowSubCat.this.tvNoStream.getVisibility() == 0) {
                                return false;
                            }
                            SeriesActivitNewFlowSubCat.this.episodeDetailAdapter.filter(str, SeriesActivitNewFlowSubCat.this.tvNoStream);
                            return false;
                        } else if (SeriesActivitNewFlowSubCat.this.vodSubCatAdpaterNew == null || SeriesActivitNewFlowSubCat.this.tvNoStream == null || SeriesActivitNewFlowSubCat.this.tvNoStream.getVisibility() == 0) {
                            return false;
                        } else {
                            SeriesActivitNewFlowSubCat.this.vodSubCatAdpaterNew.filter(str, SeriesActivitNewFlowSubCat.this.tvNoRecordFound);
                            return false;
                        }
                    }
                });
                return true;
            }
        } else if (itemId == R.id.action_search) {
            this.searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
            if (this.getActiveLiveStreamCategoryId.equalsIgnoreCase("-4")) {
                this.searchView.setQueryHint(getResources().getString(R.string.search_episodes));
            } else {
                this.searchView.setQueryHint(getResources().getString(R.string.search_series));
            }
            this.searchView.setIconifiedByDefault(false);
            this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                /* class com.nst.yourname.view.activity.SeriesActivitNewFlowSubCat.AnonymousClass5 */

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextSubmit(String str) {
                    return false;
                }

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextChange(String str) {
                    SeriesActivitNewFlowSubCat.this.tvNoRecordFound.setVisibility(8);
                    SeriesActivitNewFlowSubCat.this.tvNoStream.setVisibility(8);
                    if (SeriesActivitNewFlowSubCat.this.getActiveLiveStreamCategoryId.equalsIgnoreCase("-4")) {
                        if (SeriesActivitNewFlowSubCat.this.episodeDetailAdapter == null || SeriesActivitNewFlowSubCat.this.tvNoStream == null || SeriesActivitNewFlowSubCat.this.tvNoStream.getVisibility() == 0) {
                            SeriesActivitNewFlowSubCat.this.tvNoStream.setVisibility(0);
                        } else {
                            SeriesActivitNewFlowSubCat.this.tvNoStream.setText(SeriesActivitNewFlowSubCat.this.getResources().getString(R.string.no_episode_found));
                            SeriesActivitNewFlowSubCat.this.episodeDetailAdapter.filter(str, SeriesActivitNewFlowSubCat.this.tvNoStream);
                        }
                    } else if (SeriesActivitNewFlowSubCat.this.vodAdapter == null) {
                        SeriesActivitNewFlowSubCat.this.tvNoRecordFound.setVisibility(0);
                    } else if (!(SeriesActivitNewFlowSubCat.this.tvNoStream == null || SeriesActivitNewFlowSubCat.this.tvNoStream.getVisibility() == 0)) {
                        SeriesActivitNewFlowSubCat.this.vodAdapter.filter(str, SeriesActivitNewFlowSubCat.this.tvNoRecordFound);
                    }
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
                /* class com.nst.yourname.view.activity.SeriesActivitNewFlowSubCat.AnonymousClass6 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadChannelsAndVod(SeriesActivitNewFlowSubCat.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesActivitNewFlowSubCat.AnonymousClass7 */

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
                /* class com.nst.yourname.view.activity.SeriesActivitNewFlowSubCat.AnonymousClass8 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadTvGuid(SeriesActivitNewFlowSubCat.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesActivitNewFlowSubCat.AnonymousClass9 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder2.show();
        }
        if (itemId == R.id.layout_view_grid) {
            if (this.getActiveLiveStreamCategoryId.equals(AppConst.PASSWORD_UNSET)) {
                this.editor.putInt("series", 0);
                this.editor.commit();
                initialize();
            } else if (this.getActiveLiveStreamCategoryId.equals("-1")) {
                this.editor.putInt("series", 0);
                this.editor.commit();
                initialize();
                if (!(this.favouriteStreams == null || this.favouriteStreams.size() == 0)) {
                    Utils.showToastLong(this.context, getResources().getString(R.string.use_long_press));
                }
            } else {
                subCategoryListFinal_menu.clear();
                subCategoryListFinal_menu = this.liveStreamDBHandler.getAllMovieCategoriesHavingParentIdNotZero(this.getActiveLiveStreamCategoryId);
                if (subCategoryListFinal_menu.size() <= 0) {
                    this.editor.putInt("series", 0);
                    this.editor.commit();
                    initialize();
                }
            }
        }
        if (itemId == R.id.layout_view_linear) {
            if (this.getActiveLiveStreamCategoryId.equals(AppConst.PASSWORD_UNSET)) {
                this.editor.putInt("series", 1);
                this.editor.commit();
                initialize1();
            } else if (this.getActiveLiveStreamCategoryId.equals("-1")) {
                this.editor.putInt("series", 1);
                this.editor.commit();
                initialize1();
                if (!(this.favouriteStreams == null || this.favouriteStreams.size() == 0)) {
                    Utils.showToastLong(this.context, getResources().getString(R.string.use_long_press));
                }
            } else {
                subCategoryListFinal_menu = this.liveStreamDBHandler.getAllMovieCategoriesHavingParentIdNotZero(this.getActiveLiveStreamCategoryId);
                if (subCategoryListFinal_menu.size() <= 0) {
                    this.editor.putInt("series", 1);
                    this.editor.commit();
                    initialize1();
                }
            }
        }
        if (itemId == R.id.menu_sort) {
            showSortPopup(this);
            onFinish();
        }
        if (itemId == R.id.nav_delete_all && this.episdoeDetailsList != null && this.episdoeDetailsList.size() > 0) {
            showDeleteallRecentwatch();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void showSortPopup(Activity activity) {
        char c;
        final Activity activity2 = activity;
        try {
            final View inflate = ((LayoutInflater) activity2.getSystemService("layout_inflater")).inflate((int) R.layout.sort_layout, (RelativeLayout) activity2.findViewById(R.id.rl_password_prompt));
            this.changeSortPopUp = new PopupWindow(activity2);
            this.changeSortPopUp.setContentView(inflate);
            this.changeSortPopUp.setWidth(-1);
            this.changeSortPopUp.setHeight(-1);
            this.changeSortPopUp.setFocusable(true);
            this.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
            String seriesSubCatSort = SharepreferenceDBHandler.getSeriesSubCatSort(activity);
            Button button = (Button) inflate.findViewById(R.id.bt_save_password);
            Button button2 = (Button) inflate.findViewById(R.id.bt_close);
            final RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.rg_radio);
            RadioButton radioButton = (RadioButton) inflate.findViewById(R.id.rb_normal);
            RadioButton radioButton2 = (RadioButton) inflate.findViewById(R.id.rb_lastadded);
            if (this.getActiveLiveStreamCategoryId.equalsIgnoreCase("-2")) {
                radioButton2.setVisibility(8);
                if (seriesSubCatSort.equalsIgnoreCase("1")) {
                    SharepreferenceDBHandler.setSeriesSubCatSort(AppConst.PASSWORD_UNSET, activity2);
                }
            } else {
                radioButton2.setVisibility(0);
            }
            RadioButton radioButton3 = (RadioButton) inflate.findViewById(R.id.rb_channel_asc);
            radioButton3.setVisibility(8);
            RadioButton radioButton4 = (RadioButton) inflate.findViewById(R.id.rb_channel_desc);
            radioButton4.setVisibility(8);
            RadioButton radioButton5 = (RadioButton) inflate.findViewById(R.id.rb_atoz);
            RadioButton radioButton6 = (RadioButton) inflate.findViewById(R.id.rb_ztoa);
            radioButton.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton));
            radioButton2.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton2));
            radioButton5.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton5));
            radioButton6.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton6));
            radioButton3.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton3));
            radioButton4.setOnFocusChangeListener(new OnFocusChangeAccountListener(radioButton4));
            switch (seriesSubCatSort.hashCode()) {
                case 49:
                    if (seriesSubCatSort.equals("1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 50:
                    if (seriesSubCatSort.equals("2")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 51:
                    if (seriesSubCatSort.equals("3")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    radioButton2.setChecked(true);
                    break;
                case 1:
                    radioButton5.setChecked(true);
                    break;
                case 2:
                    radioButton6.setChecked(true);
                    break;
                default:
                    radioButton.setChecked(true);
                    break;
            }
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesActivitNewFlowSubCat.AnonymousClass10 */

                public void onClick(View view) {
                    SeriesActivitNewFlowSubCat.this.changeSortPopUp.dismiss();
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesActivitNewFlowSubCat.AnonymousClass11 */

                public void onClick(View view) {
                    RadioButton radioButton = (RadioButton) inflate.findViewById(radioGroup.getCheckedRadioButtonId());
                    if (radioButton.getText().toString().equals(SeriesActivitNewFlowSubCat.this.getResources().getString(R.string.sort_last_added))) {
                        SharepreferenceDBHandler.setSeriesSubCatSort("1", activity2);
                    } else if (radioButton.getText().toString().equals(SeriesActivitNewFlowSubCat.this.getResources().getString(R.string.sort_atoz))) {
                        SharepreferenceDBHandler.setSeriesSubCatSort("2", activity2);
                    } else if (radioButton.getText().toString().equals(SeriesActivitNewFlowSubCat.this.getResources().getString(R.string.sort_ztoa))) {
                        SharepreferenceDBHandler.setSeriesSubCatSort("3", activity2);
                    } else {
                        SharepreferenceDBHandler.setSeriesSubCatSort(AppConst.PASSWORD_UNSET, activity2);
                    }
                    SharedPreferences unused = SeriesActivitNewFlowSubCat.this.pref = SeriesActivitNewFlowSubCat.this.getSharedPreferences("listgridview", 0);
                    AppConst.LIVE_FLAG_SERIES = SeriesActivitNewFlowSubCat.this.pref.getInt("series", 0);
                    if (AppConst.LIVE_FLAG_SERIES == 1) {
                        SeriesActivitNewFlowSubCat.this.initialize1();
                    } else {
                        SeriesActivitNewFlowSubCat.this.initialize();
                    }
                    SeriesActivitNewFlowSubCat.this.changeSortPopUp.dismiss();
                }
            });
        } catch (Exception unused) {
        }
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

    private void setSubCategoryLayout(ArrayList<LiveStreamCategoryIdDBModel> arrayList) {
        initializeSubCat(arrayList);
    }

    private void initializeSubCat(ArrayList<LiveStreamCategoryIdDBModel> arrayList) {
        if (this.myRecyclerView != null && this.context != null) {
            this.myRecyclerView.setHasFixedSize(true);
            this.gridLayoutManager = new GridLayoutManager(this, 2);
            this.myRecyclerView.setLayoutManager(this.gridLayoutManager);
            this.myRecyclerView.setHasFixedSize(true);
            onFinish();
            this.vodSubCatAdpaterNew = new VodSubCatAdpaterNew(arrayList, this.context, this.liveStreamDBHandler);
            this.myRecyclerView.setAdapter(this.vodSubCatAdpaterNew);
        }
    }

    private void setLayout() {
        this.pref = getSharedPreferences("listgridview", 0);
        this.editor = this.pref.edit();
        AppConst.LIVE_FLAG_SERIES = this.pref.getInt("series", 0);
        if (AppConst.LIVE_FLAG_SERIES == 1) {
            initialize1();
        } else {
            initialize();
        }
    }

    public void getAllMovies() {
        try {
            atStart();
            this.pref = getSharedPreferences("listgridview", 0);
            this.editor = this.pref.edit();
            AppConst.LIVE_FLAG_SERIES = this.pref.getInt("series", 0);
            if (AppConst.LIVE_FLAG_SERIES == 1) {
                this.context = this;
                this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
                if (!(this.myRecyclerView == null || this.context == null)) {
                    this.myRecyclerView.setHasFixedSize(true);
                    this.layoutManager = new LinearLayoutManager(this.context);
                    this.myRecyclerView.setLayoutManager(this.layoutManager);
                    this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
                }
            } else {
                this.context = this;
                this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
                if (!(this.myRecyclerView == null || this.context == null)) {
                    this.myRecyclerView.setHasFixedSize(true);
                    this.layoutManager = new GridLayoutManager(this.context, Utils.getNumberOfColumns(this.context) + 1);
                    this.myRecyclerView.setLayoutManager(this.layoutManager);
                    this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
                }
            }
            if (this.context != null) {
                LiveStreamDBHandler liveStreamDBHandler2 = new LiveStreamDBHandler(this.context);
                ArrayList<SeriesDBModel> allSeriesStreamsWithCategoryId = new SeriesStreamsDatabaseHandler(this.context).getAllSeriesStreamsWithCategoryId(this.getActiveLiveStreamCategoryId);
                this.categoryWithPasword = new ArrayList<>();
                this.liveListDetailUnlckedDetail = new ArrayList<>();
                this.liveListDetailUnlcked = new ArrayList<>();
                this.liveListDetailAvailable = new ArrayList<>();
                if (liveStreamDBHandler2.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context)) <= 0 || allSeriesStreamsWithCategoryId == null) {
                    this.liveListDetailAvailable = allSeriesStreamsWithCategoryId;
                } else {
                    this.listPassword = getPasswordSetCategories();
                    if (this.listPassword != null) {
                        this.liveListDetailUnlckedDetail = getUnlockedCategories(allSeriesStreamsWithCategoryId, this.listPassword);
                    }
                    this.liveListDetailAvailable = this.liveListDetailUnlckedDetail;
                }
                onFinish();
                if (this.liveListDetailAvailable == null || this.myRecyclerView == null || this.liveListDetailAvailable.size() == 0) {
                    if (this.progressDialog != null) {
                        this.progressDialog.dismiss();
                    }
                    if (this.tvNoStream != null) {
                        this.tvNoStream.setVisibility(0);
                    }
                } else {
                    if (this.progressDialog != null) {
                        this.progressDialog.dismiss();
                    }
                    this.vodAdapter = new SeriesAdapter(allSeriesStreamsWithCategoryId, this.context);
                    this.myRecyclerView.setAdapter(this.vodAdapter);
                }
            }
            if (this.progressDialog != null) {
                this.progressDialog.dismiss();
            }
        } catch (Exception unused) {
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

    public String getFavouritesAsync() {
        try {
            new ArrayList();
            this.favouriteStreams.clear();
            ArrayList<FavouriteDBModel> allFavourites = this.database.getAllFavourites("series", SharepreferenceDBHandler.getUserID(this.context));
            if (this.listPassword != null) {
                this.listPassword = getPasswordSetCategories();
            }
            if (this.listPassword != null && this.listPassword.size() > 0 && allFavourites != null && allFavourites.size() > 0) {
                allFavourites = getfavUnlovked(allFavourites, this.listPassword);
            }
            Iterator<FavouriteDBModel> it = allFavourites.iterator();
            while (it.hasNext()) {
                FavouriteDBModel next = it.next();
                SeriesDBModel seriesStreamsWithSeriesId = new SeriesStreamsDatabaseHandler(this.context).getSeriesStreamsWithSeriesId(String.valueOf(next.getStreamID()));
                String.valueOf(next.getStreamID());
                if (seriesStreamsWithSeriesId != null) {
                    this.favouriteStreams.add(seriesStreamsWithSeriesId);
                }
            }
            if (!SharepreferenceDBHandler.getSeriesSubCatSort(this.context).equalsIgnoreCase("1")) {
                return "get_fav";
            }
            Collections.sort(this.favouriteStreams, new Comparator<SeriesDBModel>() {
                /* class com.nst.yourname.view.activity.SeriesActivitNewFlowSubCat.AnonymousClass12 */

                public int compare(SeriesDBModel seriesDBModel, SeriesDBModel seriesDBModel2) {
                    return seriesDBModel2.getlast_modified().compareTo(seriesDBModel.getlast_modified());
                }
            });
            return "get_fav";
        } catch (Exception unused) {
            return "get_fav";
        }
    }

    public String getALLAsync(String str) {
        try {
            this.categoryWithPasword = new ArrayList<>();
            this.liveListDetailUnlckedDetail = new ArrayList<>();
            this.liveListDetailUnlcked = new ArrayList<>();
            this.liveListDetailAvailable = new ArrayList<>();
            ArrayList<SeriesDBModel> allSeriesStreamsWithCategoryId = new SeriesStreamsDatabaseHandler(this.context).getAllSeriesStreamsWithCategoryId(str);
            if (this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context)) <= 0 || allSeriesStreamsWithCategoryId == null) {
                this.liveListDetailAvailable = allSeriesStreamsWithCategoryId;
                return "get_all";
            }
            this.listPassword = getPasswordSetCategories();
            if (this.listPassword != null) {
                this.liveListDetailUnlckedDetail = getUnlockedCategories(allSeriesStreamsWithCategoryId, this.listPassword);
            }
            this.liveListDetailAvailable = this.liveListDetailUnlckedDetail;
            return "get_all";
        } catch (Exception unused) {
            return "get_all";
        }
    }

    public void favAdapterSet() {
        try {
            if (!(this.myRecyclerView == null || this.favouriteStreams == null || this.favouriteStreams.size() == 0)) {
                this.vodAdapter = new SeriesAdapter(this.favouriteStreams, this.context);
                this.myRecyclerView.setAdapter(this.vodAdapter);
                this.vodAdapter.notifyDataSetChanged();
                Utils.showToastLong(this.context, getResources().getString(R.string.use_long_press));
                this.tvNoStream.setVisibility(4);
            }
            if (!(this.tvNoStream == null || this.favouriteStreams == null || this.favouriteStreams.size() != 0)) {
                if (this.myRecyclerView != null) {
                    this.myRecyclerView.setAdapter(this.vodAdapter);
                }
                this.tvNoStream.setText(getResources().getString(R.string.no_series_found));
                this.tvNoStream.setVisibility(0);
            }
            if (this.pbLoader != null) {
                this.pbLoader.setVisibility(8);
            }
        } catch (Exception unused) {
        }
    }

    public void AllAdapterSet() {
        try {
            if (this.liveListDetailAvailable != null && this.myRecyclerView != null && this.liveListDetailAvailable.size() != 0) {
                this.vodAdapter = new SeriesAdapter(this.liveListDetailAvailable, this.context);
                this.myRecyclerView.setAdapter(this.vodAdapter);
                Utils.showToastLong(this.context, getResources().getString(R.string.use_long_press));
            } else if (this.tvNoStream != null) {
                this.tvNoStream.setVisibility(0);
            }
            if (this.pbLoader != null) {
                this.pbLoader.setVisibility(8);
            }
        } catch (Exception unused) {
        }
    }

    private void setUpdatabaseResult() {
        try {
            if (this.context != null) {
                new LiveStreamDBHandler(this.context);
                String str = this.getActiveLiveStreamCategoryId;
                char c = 65535;
                int hashCode = str.hashCode();
                if (hashCode != 1444) {
                    if (hashCode == 1447) {
                        if (str.equals("-4")) {
                            c = 1;
                        }
                    }
                } else if (str.equals("-1")) {
                    c = 0;
                }
                switch (c) {
                    case 0:
                        new AsyncTaskSeries().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "get_fav");
                        break;
                    case 1:
                        new AsyncTaskSeries().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "get_recent_watch");
                        break;
                    default:
                        new AsyncTaskSeries().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "get_all", this.getActiveLiveStreamCategoryId);
                        break;
                }
            }
            if (this.progressDialog != null) {
                this.progressDialog.dismiss();
            }
        } catch (Exception unused) {
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

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity
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
        if (this.mAdapter != null) {
            this.mAdapter.setVisibiltygone(pbPagingLoader1);
        }
        if (this.myRecyclerView != null) {
            this.myRecyclerView.setClickable(true);
        }
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
        if (!(this.mAdapter == null || pbPagingLoader1 == null)) {
            this.mAdapter.setVisibiltygone(pbPagingLoader1);
        }
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

    public void atStart() {
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(0);
        }
    }

    public void onFinish() {
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(4);
        }
    }

    public void progressBar(ProgressBar progressBar) {
        pbPagingLoader1 = progressBar;
    }

    private ArrayList<FavouriteDBModel> getfavUnlovked(ArrayList<FavouriteDBModel> arrayList, ArrayList<String> arrayList2) {
        this.favliveListDetailUnlckedDetail = new ArrayList<>();
        Iterator<FavouriteDBModel> it = arrayList.iterator();
        while (it.hasNext()) {
            FavouriteDBModel next = it.next();
            boolean z = false;
            Iterator<String> it2 = arrayList2.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                if (next.getCategoryID().equals(it2.next())) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                this.favliveListDetailUnlckedDetail.add(next);
            }
        }
        return this.favliveListDetailUnlckedDetail;
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

    private ArrayList<SeriesDBModel> getUnlockedCategories(ArrayList<SeriesDBModel> arrayList, ArrayList<String> arrayList2) {
        Iterator<SeriesDBModel> it = arrayList.iterator();
        while (it.hasNext()) {
            SeriesDBModel next = it.next();
            boolean z = false;
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
            if (!z) {
                this.liveListDetailUnlcked.add(next);
            }
        }
        return this.liveListDetailUnlcked;
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

    public String getRecentWatchData() {
        this.listPassword = new ArrayList<>();
        new ArrayList();
        this.episdoeDetailsList = new ArrayList();
        this.seriesRecentWatchDatabase = new SeriesRecentWatchDatabase(this.context);
        ArrayList<GetEpisdoeDetailsCallback> allSeriesRecentWatch = this.seriesRecentWatchDatabase.getAllSeriesRecentWatch("getalldata");
        if (this.liveStreamDBHandler == null) {
            this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        }
        if (this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context)) > 0) {
            this.listPassword = getPasswordSetCategories();
            Iterator<GetEpisdoeDetailsCallback> it = allSeriesRecentWatch.iterator();
            while (it.hasNext()) {
                GetEpisdoeDetailsCallback next = it.next();
                boolean z = false;
                Iterator<String> it2 = this.listPassword.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    if (next.getCategoryId().equals(it2.next())) {
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    this.episdoeDetailsList.add(next);
                }
            }
            return "get_recent_watch";
        }
        this.episdoeDetailsList = allSeriesRecentWatch;
        return "get_recent_watch";
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
     arg types: [android.widget.Button, com.nst.yourname.view.activity.SeriesActivitNewFlowSubCat]
     candidates:
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.content.Context):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.CheckAppupdateActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.LoginActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.MultiUserActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.NewDashboardActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RateUsActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RecordingActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.SplashActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.VodActivityNewFlowSubCategories):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void */
    private void showDeleteallRecentwatch() {
        try {
            View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate((int) R.layout.delete_recording_popup, (RelativeLayout) findViewById(R.id.rl_password_verification));
            this.changeSortPopUp = new PopupWindow(this);
            this.changeSortPopUp.setContentView(inflate);
            this.changeSortPopUp.setWidth(-1);
            this.changeSortPopUp.setHeight(-1);
            this.changeSortPopUp.setFocusable(true);
            this.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
            ((TextView) inflate.findViewById(R.id.tv_delete_recording)).setText(getResources().getString(R.string.delete_all_series_this_recording));
            Button button = (Button) inflate.findViewById(R.id.bt_start_recording);
            Button button2 = (Button) inflate.findViewById(R.id.bt_close);
            if (button2 != null) {
                button2.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button2, (Activity) this));
            }
            if (button != null) {
                button.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button, (Activity) this));
            }
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeriesActivitNewFlowSubCat.AnonymousClass13 */

                public void onClick(View view) {
                    new AsyntaskClassDeleteAllRecentWatch().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                    SeriesActivitNewFlowSubCat.this.changeSortPopUp.dismiss();
                }
            });
            if (button2 != null) {
                button2.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.activity.SeriesActivitNewFlowSubCat.AnonymousClass14 */

                    public void onClick(View view) {
                        SeriesActivitNewFlowSubCat.this.changeSortPopUp.dismiss();
                    }
                });
            }
        } catch (Exception unused) {
        }
    }

    @SuppressLint({"StaticFieldLeak"})
    private class AsyntaskClassDeleteAllRecentWatch extends AsyncTask<Void, Void, Boolean> {
        private AsyntaskClassDeleteAllRecentWatch() {
        }

        public void onPreExecute() {
            super.onPreExecute();
            if (SeriesActivitNewFlowSubCat.this.pbLoader != null) {
                SeriesActivitNewFlowSubCat.this.pbLoader.setVisibility(0);
            }
        }

        public Boolean doInBackground(Void... voidArr) {
            try {
                return SeriesActivitNewFlowSubCat.this.deleteAllRecentWatch();
            } catch (Exception unused) {
                return false;
            }
        }

        public void onPostExecute(Boolean bool) {
            super.onPostExecute((Boolean) bool);
            if (SeriesActivitNewFlowSubCat.this.pbLoader != null) {
                SeriesActivitNewFlowSubCat.this.pbLoader.setVisibility(8);
            }
            if (bool.booleanValue()) {
                Toast.makeText(SeriesActivitNewFlowSubCat.this.context, SeriesActivitNewFlowSubCat.this.getResources().getString(R.string.all_series_deleted_successfully), 0).show();
                SeriesActivitNewFlowSubCat.this.onFinished(false);
                return;
            }
            SeriesActivitNewFlowSubCat.this.episdoeDetailsList.clear();
            SeriesActivitNewFlowSubCat.this.myRecyclerView.setVisibility(8);
            SeriesActivitNewFlowSubCat.this.tvNoRecordFound.setText(SeriesActivitNewFlowSubCat.this.getResources().getString(R.string.no_episode_found));
            SeriesActivitNewFlowSubCat.this.tvNoRecordFound.setVisibility(0);
        }
    }

    public Boolean deleteAllRecentWatch() {
        if (this.episdoeDetailsList == null || this.episdoeDetailsList.size() <= 0) {
            return false;
        }
        if (this.seriesRecentWatchDatabase == null) {
            this.seriesRecentWatchDatabase = new SeriesRecentWatchDatabase(this.context);
        }
        for (int i = 0; i < this.episdoeDetailsList.size(); i++) {
            this.seriesRecentWatchDatabase.deleteSeriesRecentwatch(this.episdoeDetailsList.get(i).getId());
        }
        return true;
    }

    public void onFinished(boolean z) {
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(8);
        }
        if (z) {
            this.tvNoStream.setVisibility(8);
            this.myRecyclerView.setVisibility(0);
            return;
        }
        this.myRecyclerView.setVisibility(8);
        this.tvNoStream.setText(getResources().getString(R.string.no_episode_found));
        this.tvNoStream.setVisibility(0);
    }

    @SuppressLint({"StaticFieldLeak"})
    class AsyncTaskSeries extends AsyncTask<String, Void, String> {
        AsyncTaskSeries() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        /* JADX WARNING: Removed duplicated region for block: B:20:0x003a A[Catch:{ Exception -> 0x0054 }] */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x003b A[Catch:{ Exception -> 0x0054 }] */
        /* JADX WARNING: Removed duplicated region for block: B:23:0x0042 A[Catch:{ Exception -> 0x0054 }] */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x004b A[Catch:{ Exception -> 0x0054 }] */
        public String doInBackground(String... strArr) {
            char c = 0;
            //try {
                String str = strArr[0];
                int hashCode = str.hashCode();
                if (hashCode != -74801864) {
                    if (hashCode != -74797390) {
                        if (hashCode == 1997009972) {
                            if (str.equals("get_recent_watch")) {
                                c = 2;
                                switch (c) {
                                    case 0:
                                        return SeriesActivitNewFlowSubCat.this.getFavouritesAsync();
                                    case 1:
                                        return SeriesActivitNewFlowSubCat.this.getALLAsync(strArr[1]);
                                    case 2:
                                        return SeriesActivitNewFlowSubCat.this.getRecentWatchData();
                                    default:
                                        return null;
                                }
                            }
                        }
                    } else if (str.equals("get_fav")) {
                        switch (c) {
                        }
                    }
                } else if (str.equals("get_all")) {
                    c = 1;
                    switch (c) {
                    }
                }
                c = 65535;
                switch (c) {
                }
            /*} catch (Exception unused) {
                return "error";
            }
            return null;*/
            //ToDo: return statement...
            return str;
        }

        /* JADX WARNING: Removed duplicated region for block: B:17:0x003a  */
        /* JADX WARNING: Removed duplicated region for block: B:18:0x0040  */
        /* JADX WARNING: Removed duplicated region for block: B:19:0x0046  */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x004c  */
        public void onPostExecute(String str) {
            char c;
            super.onPostExecute((String) str);
            int hashCode = str.hashCode();
            if (hashCode != -74801864) {
                if (hashCode != -74797390) {
                    if (hashCode == 1997009972 && str.equals("get_recent_watch")) {
                        c = 2;
                        switch (c) {
                            case 0:
                                SeriesActivitNewFlowSubCat.this.favAdapterSet();
                                return;
                            case 1:
                                SeriesActivitNewFlowSubCat.this.AllAdapterSet();
                                return;
                            case 2:
                                SeriesActivitNewFlowSubCat.this.SetRecentWatchDataOnAdapter();
                                return;
                            default:
                                SeriesActivitNewFlowSubCat.this.onFinished(false);
                                return;
                        }
                    }
                } else if (str.equals("get_fav")) {
                    c = 0;
                    switch (c) {
                    }
                }
            } else if (str.equals("get_all")) {
                c = 1;
                switch (c) {
                }
            }
            c = 65535;
            switch (c) {
            }
        }
    }

    public void SetRecentWatchDataOnAdapter() {
        if (this.episdoeDetailsList == null || this.episdoeDetailsList.size() <= 0) {
            onFinished(false);
            return;
        }
        onFinished(true);
        Intent intent = getIntent();
        Utils.showToastLong(this.context, getResources().getString(R.string.use_long_press));
        this.episodeDetailAdapter = new EpisodeDetailAdapter(this.episdoeDetailsList, this.context, intent.getStringExtra(AppConst.SERIES_COVER), AppConst.ACTION_COME_FROM_SERIES_DIRECT, this, new SeriesStreamsDatabaseHandler(this.context).getAllSeriesStreamsWithCategoryId(this.getActiveLiveStreamCategoryId));
        this.myRecyclerView.setAdapter(this.episodeDetailAdapter);
    }

    @Override // com.nst.yourname.view.adapter.EpisodeDetailAdapter.EpisodeItemClickListener
    public void episodeitemClicked() {
        new AsyncTaskSeries().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "get_recent_watch");
    }
}
