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
import android.util.Log;
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
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastSession;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.AdapterSectionRecycler;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.FavouriteM3UModel;
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.MoviesUsingSinglton;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.PasswordStatusDBModel;
import com.nst.yourname.model.database.RecentWatchDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.adapter.VodAdapter;
import com.nst.yourname.view.adapter.VodAdapterNewFlow;
import com.nst.yourname.view.adapter.VodSubCatAdpaterNew;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
public class VodActivityNewFlowSubCategories extends AppCompatActivity implements View.OnClickListener {
    static final boolean $assertionsDisabled = false;
    private static final String JSON = "";
    static ProgressBar pbPagingLoader1;
    private static ArrayList<LiveStreamCategoryIdDBModel> subCategoryList = new ArrayList<>();
    public static ArrayList<LiveStreamCategoryIdDBModel> subCategoryListFinal = new ArrayList<>();
    private static ArrayList<LiveStreamCategoryIdDBModel> subCategoryListFinal_menu = new ArrayList<>();
    private SharedPreferences SharedPreferencesSort;
    private SharedPreferences.Editor SharedPreferencesSortEditor;
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
    public SharedPreferences.Editor editor;
    private ArrayList<FavouriteDBModel> favliveListDetailAvailable;
    private ArrayList<FavouriteDBModel> favliveListDetailUnlcked;
    private ArrayList<FavouriteDBModel> favliveListDetailUnlckedDetail;
    private ArrayList<FavouriteM3UModel> favliveListDetailUnlckedDetailM3U;
    private ArrayList<LiveStreamsDBModel> favouriteStreams = new ArrayList<>();
    private String getActiveLiveStreamCategoryId = "";
    private String getActiveLiveStreamCategoryName = "";
    GridLayoutManager gridLayoutManager;
    Handler handler;
    boolean isRecentwatch = true;
    private boolean isSubcaetgroy = false;
    boolean isSubcaetgroyAvail = false;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> listPassword = new ArrayList<>();
    public ArrayList<LiveStreamsDBModel> liveListDetailAvailable;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlcked;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlckedDetail;
    public LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences loginPreferencesSharedPref;
    @BindView(R.id.logo)
    ImageView logo;
    private VodAdapterNewFlow mAdapter;
    private CastSession mCastSession;
    private RecyclerView.LayoutManager mLayoutManager;
    private MenuItem mediaRouteMenuItem;
    MenuItem menuItemSettings;
    Menu menuSelect;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    public SharedPreferences pref;
    private ProgressDialog progressDialog;
    RecentWatchDBHandler recentWatchDBHandler;
    @BindView(R.id.rl_sub_cat)
    RelativeLayout rl_sub_cat;
    SearchView searchView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    TypedValue f35tv;
    @BindView(R.id.tv_no_record_found)
    TextView tvNoRecordFound;
    @BindView(R.id.tv_noStream)
    TextView tvNoStream;
    @BindView(R.id.tv_view_provider)
    TextView tvViewProvider;
    private String userName = "";
    private String userPassword = "";
    public VodAdapter vodAdapter;
    @BindView(R.id.tv_settings)
    TextView vodCategoryName;
    public VodSubCatAdpaterNew vodSubCatAdpaterNew;

    public void onFinish() {
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    @SuppressLint({"ApplySharedPref"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        this.SharedPreferencesSort = getSharedPreferences(AppConst.LOGIN_PREF_SORT, 0);
        this.SharedPreferencesSortEditor = this.SharedPreferencesSort.edit();
        if (this.SharedPreferencesSort.getString(AppConst.LOGIN_PREF_SORT, "").equals("")) {
            this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, AppConst.PASSWORD_UNSET);
            this.SharedPreferencesSortEditor.commit();
        }
        Intent intent = getIntent();
        if (intent != null) {
            this.getActiveLiveStreamCategoryId = intent.getStringExtra(AppConst.CATEGORY_ID);
            this.getActiveLiveStreamCategoryName = intent.getStringExtra(AppConst.CATEGORY_NAME);
        }
        this.context = this;
        this.mAdapter = new VodAdapterNewFlow();
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.handler = new Handler();
        this.recentWatchDBHandler = new RecentWatchDBHandler(this.context);
        String str = this.getActiveLiveStreamCategoryId;
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 48) {
            if (hashCode == 1444 && str.equals("-1")) {
                c = 0;
            }
        } else if (str.equals(AppConst.PASSWORD_UNSET)) {
            c = 1;
        }
        switch (c) {
            case 0:
                setContentView((int) R.layout.activity_vod_layout);
                ButterKnife.bind(this);
                this.handler.removeCallbacksAndMessages(null);
                if (this.pbLoader != null) {
                    this.pbLoader.setVisibility(0);
                }
                setLayout();
                break;
            case 1:
                setContentView((int) R.layout.activity_vod_layout);
                ButterKnife.bind(this);
                atStart();
                this.handler.removeCallbacksAndMessages(null);
                if (this.pbLoader != null) {
                    this.pbLoader.setVisibility(0);
                }
                this.handler.postDelayed(new Runnable() {
                    /* class com.nst.yourname.view.activity.VodActivityNewFlowSubCategories.AnonymousClass1 */

                    public void run() {
                        VodActivityNewFlowSubCategories.this.setLayout();
                        if (VodActivityNewFlowSubCategories.this.pbLoader != null) {
                            VodActivityNewFlowSubCategories.this.pbLoader.setVisibility(8);
                        }
                    }
                }, 1000);
                break;
            default:
                subCategoryListFinal = this.liveStreamDBHandler.getAllMovieCategoriesHavingParentIdNotZero(this.getActiveLiveStreamCategoryId);
                if (subCategoryListFinal != null && subCategoryListFinal.size() == 0) {
                    setContentView((int) R.layout.activity_vod_layout);
                    ButterKnife.bind(this);
                    atStart();
                    this.handler.removeCallbacksAndMessages(null);
                    if (this.pbLoader != null) {
                        this.pbLoader.setVisibility(0);
                    }
                    this.handler.postDelayed(new Runnable() {
                        /* class com.nst.yourname.view.activity.VodActivityNewFlowSubCategories.AnonymousClass2 */

                        public void run() {
                            VodActivityNewFlowSubCategories.this.setLayout();
                            if (VodActivityNewFlowSubCategories.this.pbLoader != null) {
                                VodActivityNewFlowSubCategories.this.pbLoader.setVisibility(8);
                            }
                        }
                    }, 1000);
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
                    this.handler.postDelayed(new Runnable() {
                        /* class com.nst.yourname.view.activity.VodActivityNewFlowSubCategories.AnonymousClass3 */

                        public void run() {
                            VodActivityNewFlowSubCategories.this.setSubCategoryLayout(VodActivityNewFlowSubCategories.subCategoryListFinal);
                            if (VodActivityNewFlowSubCategories.this.pbLoader != null) {
                                VodActivityNewFlowSubCategories.this.pbLoader.setVisibility(8);
                            }
                        }
                    }, 1000);
                    break;
                }
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
        }
        this.vodCategoryName.setSelected(true);
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.VodActivityNewFlowSubCategories.AnonymousClass4 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(VodActivityNewFlowSubCategories.this.context);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        int i;
        super.onCreateOptionsMenu(menu);
        if (this.isSubcaetgroyAvail) {
            this.toolbar.inflateMenu(R.menu.menu_search);
            try {
                this.mediaRouteMenuItem = CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), menu, (int) R.id.media_route_menu_item);
            } catch (Exception e) {
                Log.e("sdf", "" + e);
            }
        } else {
            this.toolbar.inflateMenu(R.menu.menu_search_text_icon);
            try {
                this.mediaRouteMenuItem = CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), menu, (int) R.id.media_route_menu_item);
            } catch (Exception e2) {
                Log.e("sdf", "" + e2);
            }
            int userID = SharepreferenceDBHandler.getUserID(this.context);
            if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                i = this.liveStreamDBHandler.getRecentwatchCount(userID, AppConst.EVENT_TYPE_MOVIE);
            } else {
                i = this.recentWatchDBHandler.getLiveStreamsCount(userID);
            }
            if (i > 0 && this.getActiveLiveStreamCategoryId.equals("-4")) {
                menu.getItem(2).getSubMenu().findItem(R.id.nav_delete_all).setVisible(true);
            }
        }
        this.menuSelect = menu;
        this.menuItemSettings = menu.getItem(2).getSubMenu().findItem(R.id.empty);
        TypedValue typedValue = new TypedValue();
        if (getTheme().resolveAttribute(16843499, typedValue, true)) {
            TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics());
        }
        for (int i2 = 0; i2 < this.toolbar.getChildCount(); i2++) {
            if (this.toolbar.getChildAt(i2) instanceof ActionMenuView) {
                ((Toolbar.LayoutParams) this.toolbar.getChildAt(i2).getLayoutParams()).gravity = 16;
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
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSubCategories.AnonymousClass6 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(VodActivityNewFlowSubCategories.this.context);
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSubCategories.AnonymousClass5 */

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
                    /* class com.nst.yourname.view.activity.VodActivityNewFlowSubCategories.AnonymousClass7 */

                    @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                    public boolean onQueryTextSubmit(String str) {
                        return false;
                    }

                    @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                    public boolean onQueryTextChange(String str) {
                        VodActivityNewFlowSubCategories.this.tvNoRecordFound.setVisibility(8);
                        if (VodActivityNewFlowSubCategories.this.vodSubCatAdpaterNew == null || VodActivityNewFlowSubCategories.this.tvNoStream == null || VodActivityNewFlowSubCategories.this.tvNoStream.getVisibility() == 0) {
                            return false;
                        }
                        VodActivityNewFlowSubCategories.this.vodSubCatAdpaterNew.filter(str, VodActivityNewFlowSubCategories.this.tvNoRecordFound);
                        return false;
                    }
                });
                return true;
            }
        } else if (itemId == R.id.action_search) {
            this.searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
            this.searchView.setQueryHint(getResources().getString(R.string.search_vod));
            this.searchView.setIconifiedByDefault(false);
            this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSubCategories.AnonymousClass8 */

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextSubmit(String str) {
                    return false;
                }

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextChange(String str) {
                    VodActivityNewFlowSubCategories.this.tvNoRecordFound.setVisibility(8);
                    if (VodActivityNewFlowSubCategories.this.vodAdapter == null || VodActivityNewFlowSubCategories.this.tvNoStream == null || VodActivityNewFlowSubCategories.this.tvNoStream.getVisibility() == 0) {
                        return false;
                    }
                    VodActivityNewFlowSubCategories.this.vodAdapter.filter(str, VodActivityNewFlowSubCategories.this.tvNoRecordFound);
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
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSubCategories.AnonymousClass9 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadChannelsAndVod(VodActivityNewFlowSubCategories.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSubCategories.AnonymousClass10 */

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
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSubCategories.AnonymousClass11 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadTvGuid(VodActivityNewFlowSubCategories.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSubCategories.AnonymousClass12 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder2.show();
        }
        if (itemId == R.id.layout_view_grid && this.editor != null) {
            if (this.getActiveLiveStreamCategoryId != null && this.getActiveLiveStreamCategoryId.equals(AppConst.PASSWORD_UNSET)) {
                this.editor.putInt(AppConst.VOD, 0);
                this.editor.commit();
                initialize();
                getAllMovies();
            } else if (this.getActiveLiveStreamCategoryId != null && this.getActiveLiveStreamCategoryId.equals("-1")) {
                this.editor.putInt(AppConst.VOD, 0);
                this.editor.commit();
                initialize();
                if (!(this.favouriteStreams == null || this.favouriteStreams.size() == 0)) {
                    Utils.showToastLong(this.context, getResources().getString(R.string.use_long_press));
                }
            } else if (!(subCategoryListFinal_menu == null || this.liveStreamDBHandler == null)) {
                subCategoryListFinal_menu.clear();
                subCategoryListFinal_menu = this.liveStreamDBHandler.getAllMovieCategoriesHavingParentIdNotZero(this.getActiveLiveStreamCategoryId);
                if (subCategoryListFinal_menu.size() <= 0) {
                    this.editor.putInt(AppConst.VOD, 0);
                    this.editor.commit();
                    initialize();
                }
            }
        }
        if (itemId == R.id.layout_view_linear && this.editor != null) {
            if (this.getActiveLiveStreamCategoryId != null && this.getActiveLiveStreamCategoryId.equals(AppConst.PASSWORD_UNSET)) {
                this.editor.putInt(AppConst.VOD, 1);
                this.editor.commit();
                getAllMovies();
                initialize1();
            } else if (this.getActiveLiveStreamCategoryId != null && this.getActiveLiveStreamCategoryId.equals("-1")) {
                this.editor.putInt(AppConst.VOD, 1);
                this.editor.commit();
                initialize1();
                if (!(this.favouriteStreams == null || this.favouriteStreams.size() == 0)) {
                    Utils.showToastLong(this.context, getResources().getString(R.string.use_long_press));
                }
            } else if (!(subCategoryListFinal_menu == null || subCategoryListFinal_menu == null)) {
                subCategoryListFinal_menu = this.liveStreamDBHandler.getAllMovieCategoriesHavingParentIdNotZero(this.getActiveLiveStreamCategoryId);
                if (subCategoryListFinal_menu.size() <= 0) {
                    this.editor.putInt(AppConst.VOD, 1);
                    this.editor.commit();
                    initialize1();
                }
            }
        }
        if (itemId == R.id.menu_sort) {
            showSortPopup(this);
        }
        if (itemId == R.id.nav_delete_all && this.liveListDetailAvailable != null && this.liveListDetailAvailable.size() > 0) {
            showDeleteallRecentwatch();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.VodActivityNewFlowSubCategories):void
     arg types: [android.widget.Button, com.nst.yourname.view.activity.VodActivityNewFlowSubCategories]
     candidates:
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.content.Context):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.CheckAppupdateActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.LoginActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.MultiUserActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.NewDashboardActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RateUsActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RecordingActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.SplashActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.VodActivityNewFlowSubCategories):void */
    private void showDeleteallRecentwatch() {
        try {
            View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate((int) R.layout.delete_recording_popup, (RelativeLayout) findViewById(R.id.rl_password_verification));
            this.changeSortPopUp = new PopupWindow(this);
            this.changeSortPopUp.setContentView(inflate);
            this.changeSortPopUp.setWidth(-1);
            this.changeSortPopUp.setHeight(-1);
            this.changeSortPopUp.setFocusable(true);
            this.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
            ((TextView) inflate.findViewById(R.id.tv_delete_recording)).setText(getResources().getString(R.string.delete_this_recent_all_movies));
            Button button = (Button) inflate.findViewById(R.id.bt_start_recording);
            Button button2 = (Button) inflate.findViewById(R.id.bt_close);
            if (button != null) {
                button.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button, this));
            }
            if (button2 != null) {
                button2.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button2, this));
            }
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSubCategories.AnonymousClass13 */

                public void onClick(View view) {
                    VodActivityNewFlowSubCategories.this.changeSortPopUp.dismiss();
                }
            });
            if (button != null) {
                button.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.activity.VodActivityNewFlowSubCategories.AnonymousClass14 */

                    public void onClick(View view) {
                        if (SharepreferenceDBHandler.getCurrentAPPType(VodActivityNewFlowSubCategories.this.context).equals(AppConst.TYPE_M3U)) {
                            VodActivityNewFlowSubCategories.this.liveStreamDBHandler.deleteALLRecentwatch(AppConst.EVENT_TYPE_MOVIE, SharepreferenceDBHandler.getUserID(VodActivityNewFlowSubCategories.this.context));
                        } else {
                            new AsyntaskClassDeleteAllRecentWatch().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                        }
                        VodActivityNewFlowSubCategories.this.changeSortPopUp.dismiss();
                    }
                });
            }
        } catch (Exception unused) {
        }
    }

    public Boolean deleteAllRecentWatch() {
        if (this.liveListDetailAvailable == null || this.liveListDetailAvailable.size() <= 0) {
            return false;
        }
        for (int i = 0; i < this.liveListDetailAvailable.size(); i++) {
            this.recentWatchDBHandler.deleteRecentwatch(Utils.parseIntZero(this.liveListDetailAvailable.get(i).getStreamId()), AppConst.EVENT_TYPE_MOVIE);
        }
        return true;
    }

    @SuppressLint({"StaticFieldLeak"})
    private class AsyntaskClassDeleteAllRecentWatch extends AsyncTask<Void, Void, Boolean> {
        private AsyntaskClassDeleteAllRecentWatch() {
        }

        public void onPreExecute() {
            super.onPreExecute();
            if (VodActivityNewFlowSubCategories.this.pbLoader != null) {
                VodActivityNewFlowSubCategories.this.pbLoader.setVisibility(0);
            }
        }

        public Boolean doInBackground(Void... voidArr) {
            try {
                return VodActivityNewFlowSubCategories.this.deleteAllRecentWatch();
            } catch (Exception unused) {
                return false;
            }
        }

        public void onPostExecute(Boolean bool) {
            super.onPostExecute((Boolean) bool);
            if (VodActivityNewFlowSubCategories.this.pbLoader != null) {
                VodActivityNewFlowSubCategories.this.pbLoader.setVisibility(8);
            }
            if (bool.booleanValue()) {
                Toast.makeText(VodActivityNewFlowSubCategories.this.context, VodActivityNewFlowSubCategories.this.getResources().getString(R.string.all_movies_deleted_successfully), 0).show();
                VodActivityNewFlowSubCategories.this.initialize();
                return;
            }
            VodActivityNewFlowSubCategories.this.liveListDetailAvailable.clear();
            VodActivityNewFlowSubCategories.this.vodAdapter.notifyDataSetChanged();
            VodActivityNewFlowSubCategories.this.myRecyclerView.setVisibility(8);
            VodActivityNewFlowSubCategories.this.tvNoRecordFound.setVisibility(0);
        }
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
            Button button = (Button) inflate.findViewById(R.id.bt_save_password);
            Button button2 = (Button) inflate.findViewById(R.id.bt_close);
            final RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.rg_radio);
            RadioButton radioButton = (RadioButton) inflate.findViewById(R.id.rb_normal);
            RadioButton radioButton2 = (RadioButton) inflate.findViewById(R.id.rb_lastadded);
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
            if (SharepreferenceDBHandler.getCurrentAPPType(activity).equals(AppConst.TYPE_M3U)) {
                radioButton2.setVisibility(8);
            }
            String vODSubCatSort = SharepreferenceDBHandler.getVODSubCatSort(activity);
            switch (vODSubCatSort.hashCode()) {
                case 49:
                    if (vODSubCatSort.equals("1")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 50:
                    if (vODSubCatSort.equals("2")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 51:
                    if (vODSubCatSort.equals("3")) {
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
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSubCategories.AnonymousClass15 */

                public void onClick(View view) {
                    VodActivityNewFlowSubCategories.this.changeSortPopUp.dismiss();
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSubCategories.AnonymousClass16 */

                public void onClick(View view) {
                    RadioButton radioButton = (RadioButton) inflate.findViewById(radioGroup.getCheckedRadioButtonId());
                    if (radioButton.getText().toString().equals(VodActivityNewFlowSubCategories.this.getResources().getString(R.string.sort_last_added))) {
                        SharepreferenceDBHandler.setVODSubCatSort("1", activity2);
                    } else if (radioButton.getText().toString().equals(VodActivityNewFlowSubCategories.this.getResources().getString(R.string.sort_atoz))) {
                        SharepreferenceDBHandler.setVODSubCatSort("2", activity2);
                    } else if (radioButton.getText().toString().equals(VodActivityNewFlowSubCategories.this.getResources().getString(R.string.sort_ztoa))) {
                        SharepreferenceDBHandler.setVODSubCatSort("3", activity2);
                    } else {
                        SharepreferenceDBHandler.setVODSubCatSort(AppConst.PASSWORD_UNSET, activity2);
                    }
                    SharedPreferences unused = VodActivityNewFlowSubCategories.this.pref = VodActivityNewFlowSubCategories.this.getSharedPreferences("listgridview", 0);
                    SharedPreferences.Editor unused2 = VodActivityNewFlowSubCategories.this.editor = VodActivityNewFlowSubCategories.this.pref.edit();
                    AppConst.LIVE_FLAG_VOD = VodActivityNewFlowSubCategories.this.pref.getInt(AppConst.VOD, 0);
                    if (AppConst.LIVE_FLAG_VOD == 1) {
                        VodActivityNewFlowSubCategories.this.initialize1();
                    } else {
                        VodActivityNewFlowSubCategories.this.initialize();
                    }
                    VodActivityNewFlowSubCategories.this.changeSortPopUp.dismiss();
                }
            });
        } catch (Exception unused) {
        }
    }

    public void setSubCategoryLayout(ArrayList<LiveStreamCategoryIdDBModel> arrayList) {
        initializeSubCat(arrayList);
    }

    private void initializeSubCat(ArrayList<LiveStreamCategoryIdDBModel> arrayList) {
        if (this.myRecyclerView != null && this.context != null) {
            this.myRecyclerView.setHasFixedSize(true);
            if ((getResources().getConfiguration().screenLayout & 15) == 3) {
                this.gridLayoutManager = new GridLayoutManager(this, 2);
            } else {
                this.gridLayoutManager = new GridLayoutManager(this, 2);
            }
            this.myRecyclerView.setLayoutManager(this.gridLayoutManager);
            this.myRecyclerView.setHasFixedSize(true);
            onFinish();
            this.vodSubCatAdpaterNew = new VodSubCatAdpaterNew(arrayList, this.context, this.liveStreamDBHandler);
            this.myRecyclerView.setAdapter(this.vodSubCatAdpaterNew);
        }
    }

    public void setLayout() {
        this.pref = getSharedPreferences("listgridview", 0);
        this.editor = this.pref.edit();
        AppConst.LIVE_FLAG_VOD = this.pref.getInt(AppConst.VOD, 0);
        if (AppConst.LIVE_FLAG_VOD == 1) {
            initialize1();
        } else {
            initialize();
        }
    }

    private ArrayList<FavouriteDBModel> getfavUnlovked(ArrayList<FavouriteDBModel> arrayList, ArrayList<String> arrayList2) {
        this.favliveListDetailUnlckedDetail = new ArrayList<>();
        if (arrayList == null) {
            return null;
        }
        Iterator<FavouriteDBModel> it = arrayList.iterator();
        while (it.hasNext()) {
            FavouriteDBModel next = it.next();
            boolean z = false;
            if (arrayList2 != null) {
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
        }
        return this.favliveListDetailUnlckedDetail;
    }

    private ArrayList<FavouriteM3UModel> getfavUnlovkedM3U(ArrayList<FavouriteM3UModel> arrayList, ArrayList<String> arrayList2) {
        this.favliveListDetailUnlckedDetailM3U = new ArrayList<>();
        if (arrayList == null) {
            return null;
        }
        Iterator<FavouriteM3UModel> it = arrayList.iterator();
        while (it.hasNext()) {
            FavouriteM3UModel next = it.next();
            boolean z = false;
            if (arrayList2 != null) {
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
                    this.favliveListDetailUnlckedDetailM3U.add(next);
                }
            }
        }
        return this.favliveListDetailUnlckedDetailM3U;
    }

    public void getAllMovies() {
        try {
            atStart();
            this.pref = getSharedPreferences("listgridview", 0);
            this.editor = this.pref.edit();
            AppConst.LIVE_FLAG_VOD = this.pref.getInt(AppConst.VOD, 0);
            if (AppConst.LIVE_FLAG_VOD == 1) {
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
                ArrayList<LiveStreamsDBModel> allLiveStreasWithCategoryId = liveStreamDBHandler2.getAllLiveStreasWithCategoryId(AppConst.PASSWORD_UNSET, AppConst.EVENT_TYPE_MOVIE);
                this.categoryWithPasword = new ArrayList<>();
                this.liveListDetailUnlcked = new ArrayList<>();
                this.liveListDetailUnlckedDetail = new ArrayList<>();
                this.liveListDetailAvailable = new ArrayList<>();
                if (liveStreamDBHandler2.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context)) <= 0 || allLiveStreasWithCategoryId == null) {
                    this.liveListDetailAvailable = allLiveStreasWithCategoryId;
                } else {
                    this.listPassword = getPasswordSetCategories();
                    if (this.listPassword != null) {
                        this.liveListDetailUnlckedDetail = getUnlockedCategories(allLiveStreasWithCategoryId, this.listPassword);
                    }
                    this.liveListDetailAvailable = this.liveListDetailUnlckedDetail;
                }
                onFinish();
                if (this.pbLoader != null) {
                    this.pbLoader.setVisibility(4);
                }
                if (this.liveListDetailAvailable == null || this.myRecyclerView == null || this.liveListDetailAvailable.size() == 0) {
                    if (this.progressDialog != null) {
                        this.progressDialog.dismiss();
                    }
                    if (this.tvNoStream != null) {
                        this.tvNoStream.setVisibility(0);
                    }
                    if (this.pbLoader != null) {
                        this.pbLoader.setVisibility(4);
                    }
                } else {
                    if (this.progressDialog != null) {
                        this.progressDialog.dismiss();
                    }
                    this.vodAdapter = new VodAdapter(allLiveStreasWithCategoryId, this.context, true);
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

    public String getFavouritesM3UAsync() {
        new ArrayList();
        this.favouriteStreams.clear();
        ArrayList<FavouriteM3UModel> favouriteM3U = this.liveStreamDBHandler.getFavouriteM3U(AppConst.EVENT_TYPE_MOVIE);
        if (this.listPassword != null) {
            this.listPassword = getPasswordSetCategories();
        }
        if (this.listPassword != null && this.listPassword.size() > 0 && favouriteM3U != null && favouriteM3U.size() > 0) {
            favouriteM3U = getfavUnlovkedM3U(favouriteM3U, this.listPassword);
        }
        Iterator<FavouriteM3UModel> it = favouriteM3U.iterator();
        while (it.hasNext()) {
            FavouriteM3UModel next = it.next();
            ArrayList<LiveStreamsDBModel> m3UFavouriteRow = this.liveStreamDBHandler.getM3UFavouriteRow(next.getCategoryID(), next.getUrl());
            if (m3UFavouriteRow != null && m3UFavouriteRow.size() > 0) {
                this.favouriteStreams.add(m3UFavouriteRow.get(0));
            }
        }
        return "get_fav_m3u";
    }

    public String getFavouritesAsync() {
        new ArrayList();
        this.favouriteStreams.clear();
        this.database = new DatabaseHandler(this.context);
        ArrayList<FavouriteDBModel> allFavourites = this.database.getAllFavourites(AppConst.VOD, SharepreferenceDBHandler.getUserID(this.context));
        if (this.listPassword != null) {
            this.listPassword = getPasswordSetCategories();
        }
        if (this.listPassword != null && this.listPassword.size() > 0 && allFavourites != null && allFavourites.size() > 0) {
            allFavourites = getfavUnlovked(allFavourites, this.listPassword);
        }
        Iterator<FavouriteDBModel> it = allFavourites.iterator();
        while (it.hasNext()) {
            FavouriteDBModel next = it.next();
            LiveStreamsDBModel liveStreamFavouriteRow = new LiveStreamDBHandler(this.context).getLiveStreamFavouriteRow(next.getCategoryID(), String.valueOf(next.getStreamID()));
            if (liveStreamFavouriteRow != null) {
                this.favouriteStreams.add(liveStreamFavouriteRow);
            }
        }
        return "get_fav";
    }

    public String getALLAsync(String str) {
        this.categoryWithPasword = new ArrayList<>();
        this.liveListDetailUnlcked = new ArrayList<>();
        this.liveListDetailUnlckedDetail = new ArrayList<>();
        this.liveListDetailAvailable = new ArrayList<>();
        ArrayList<LiveStreamsDBModel> allLiveStreasWithCategoryId = this.liveStreamDBHandler.getAllLiveStreasWithCategoryId(str, AppConst.EVENT_TYPE_MOVIE);
        if (this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context)) <= 0 || allLiveStreasWithCategoryId == null) {
            this.liveListDetailAvailable = allLiveStreasWithCategoryId;
            return "get_all";
        }
        this.listPassword = getPasswordSetCategories();
        if (this.listPassword != null) {
            this.liveListDetailUnlckedDetail = getUnlockedCategories(allLiveStreasWithCategoryId, this.listPassword);
        }
        this.liveListDetailAvailable = this.liveListDetailUnlckedDetail;
        return "get_all";
    }

    public String getRecentWatchedAsync() {
        ArrayList<LiveStreamsDBModel> arrayList;
        this.categoryWithPasword = new ArrayList<>();
        this.liveListDetailUnlcked = new ArrayList<>();
        this.liveListDetailUnlckedDetail = new ArrayList<>();
        this.liveListDetailAvailable = new ArrayList<>();
        if (SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
            arrayList = this.liveStreamDBHandler.getAllLiveStreasWithCategoryId(AppConst.EVENT_TYPE_MOVIE, SharepreferenceDBHandler.getUserID(this.context), "getalldata");
        } else {
            arrayList = this.recentWatchDBHandler.getAllLiveStreasWithCategoryId(AppConst.EVENT_TYPE_MOVIE, SharepreferenceDBHandler.getUserID(this.context), "getalldata");
        }
        if (this.liveStreamDBHandler.getParentalStatusCount(SharepreferenceDBHandler.getUserID(this.context)) <= 0 || arrayList == null) {
            this.liveListDetailAvailable = arrayList;
            return "get_recent_watched";
        }
        this.listPassword = getPasswordSetCategories();
        if (this.listPassword != null) {
            this.liveListDetailUnlckedDetail = getUnlockedCategories(arrayList, this.listPassword);
        }
        this.liveListDetailAvailable = this.liveListDetailUnlckedDetail;
        return "get_recent_watched";
    }

    public void favAdapterSetM3U() {
        if (!(this.myRecyclerView == null || this.favouriteStreams == null || this.favouriteStreams.size() == 0)) {
            this.vodAdapter = new VodAdapter(this.favouriteStreams, this.context, true);
            MoviesUsingSinglton.getInstance().setMoviesList(this.favouriteStreams);
            this.myRecyclerView.setAdapter(this.vodAdapter);
            Utils.showToastLong(this.context, getResources().getString(R.string.use_long_press));
            this.tvNoStream.setVisibility(4);
        }
        if (!(this.tvNoStream == null || this.favouriteStreams == null || this.favouriteStreams.size() != 0)) {
            if (this.myRecyclerView != null) {
                this.myRecyclerView.setAdapter(this.vodAdapter);
            }
            this.tvNoStream.setText(getResources().getString(R.string.no_fav_vod_found));
            this.tvNoStream.setVisibility(0);
        }
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(8);
        }
    }

    public void favAdapterSet() {
        if (!(this.myRecyclerView == null || this.favouriteStreams == null || this.favouriteStreams.size() == 0)) {
            this.vodAdapter = new VodAdapter(this.favouriteStreams, this.context, true);
            MoviesUsingSinglton.getInstance().setMoviesList(this.favouriteStreams);
            this.myRecyclerView.setAdapter(this.vodAdapter);
            Utils.showToastLong(this.context, getResources().getString(R.string.use_long_press));
            this.tvNoStream.setVisibility(4);
        }
        if (!(this.tvNoStream == null || this.favouriteStreams == null || this.favouriteStreams.size() != 0)) {
            if (this.myRecyclerView != null) {
                this.myRecyclerView.setAdapter(this.vodAdapter);
            }
            this.tvNoStream.setText(getResources().getString(R.string.no_fav_vod_found));
            this.tvNoStream.setVisibility(0);
        }
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(8);
        }
    }

    public void AllAdapterSet() {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
        if (this.liveListDetailAvailable != null && this.myRecyclerView != null && this.liveListDetailAvailable.size() != 0) {
            this.isRecentwatch = true;
            this.vodAdapter = new VodAdapter(this.liveListDetailAvailable, this.context, true);
            MoviesUsingSinglton.getInstance().setMoviesList(this.liveListDetailAvailable);
            this.myRecyclerView.setAdapter(this.vodAdapter);
            Utils.showToastLong(this.context, getResources().getString(R.string.use_long_press));
        } else if (this.tvNoStream != null) {
            this.tvNoStream.setVisibility(0);
        }
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(8);
        }
    }

    public void recentWatchedAdapterSet() {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
        if (this.liveListDetailAvailable != null && this.myRecyclerView != null && this.liveListDetailAvailable.size() != 0) {
            this.isRecentwatch = false;
            this.vodAdapter = new VodAdapter(this.liveListDetailAvailable, this.context, false, this);
            MoviesUsingSinglton.getInstance().setMoviesList(this.liveListDetailAvailable);
            this.myRecyclerView.setAdapter(this.vodAdapter);
            Utils.showToastLong(this.context, getResources().getString(R.string.use_long_press));
        } else if (this.tvNoStream != null) {
            this.tvNoStream.setVisibility(0);
            this.myRecyclerView.setVisibility(8);
        }
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(8);
        }
    }

    @SuppressLint({"StaticFieldLeak"})
    class AsyncTaskVOD extends AsyncTask<String, Void, String> {
        AsyncTaskVOD() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        /* JADX WARNING: Removed duplicated region for block: B:25:0x0049 A[Catch:{ Exception -> 0x006a }] */
        /* JADX WARNING: Removed duplicated region for block: B:26:0x004a A[Catch:{ Exception -> 0x006a }] */
        /* JADX WARNING: Removed duplicated region for block: B:28:0x0051 A[Catch:{ Exception -> 0x006a }] */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x005a A[Catch:{ Exception -> 0x006a }] */
        /* JADX WARNING: Removed duplicated region for block: B:32:0x0061 A[Catch:{ Exception -> 0x006a }] */
        public String doInBackground(String... strArr) {
            char c = 0;
            //try {
                String str = strArr[0];
                int hashCode = str.hashCode();
                if (hashCode != -998452030) {
                    if (hashCode != -723794989) {
                        if (hashCode != -74801864) {
                            if (hashCode == -74797390) {
                                if (str.equals("get_fav")) {
                                    c = 1;
                                    switch (c) {
                                        case 0:
                                            return VodActivityNewFlowSubCategories.this.getFavouritesM3UAsync();
                                        case 1:
                                            return VodActivityNewFlowSubCategories.this.getFavouritesAsync();
                                        case 2:
                                            return VodActivityNewFlowSubCategories.this.getALLAsync(strArr[1]);
                                        case 3:
                                            return VodActivityNewFlowSubCategories.this.getRecentWatchedAsync();
                                        default:
                                            return null;
                                    }
                                }
                            }
                        } else if (str.equals("get_all")) {
                            c = 2;
                            switch (c) {
                            }
                        }
                    } else if (str.equals("get_recent_watched")) {
                        c = 3;
                        switch (c) {
                        }
                    }
                } else if (str.equals("get_fav_m3u")) {
                    switch (c) {
                    }
                }
                c = 65535;
                switch (c) {
                }
            /*} catch (Exception unused) {
                return "error";
            }*/
            //ToDo: return statement...
            return str;
        }

        /* JADX WARNING: Removed duplicated region for block: B:22:0x0049  */
        /* JADX WARNING: Removed duplicated region for block: B:23:0x004f  */
        /* JADX WARNING: Removed duplicated region for block: B:24:0x0055  */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x005b  */
        /* JADX WARNING: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
        public void onPostExecute(String str) {
            char c;
            super.onPostExecute((String) str);
            int hashCode = str.hashCode();
            if (hashCode != -998452030) {
                if (hashCode != -723794989) {
                    if (hashCode != -74801864) {
                        if (hashCode == -74797390 && str.equals("get_fav")) {
                            c = 1;
                            switch (c) {
                                case 0:
                                    VodActivityNewFlowSubCategories.this.favAdapterSetM3U();
                                    return;
                                case 1:
                                    VodActivityNewFlowSubCategories.this.favAdapterSet();
                                    return;
                                case 2:
                                    VodActivityNewFlowSubCategories.this.AllAdapterSet();
                                    return;
                                case 3:
                                    VodActivityNewFlowSubCategories.this.recentWatchedAdapterSet();
                                    return;
                                default:
                                    return;
                            }
                        }
                    } else if (str.equals("get_all")) {
                        c = 2;
                        switch (c) {
                        }
                    }
                } else if (str.equals("get_recent_watched")) {
                    c = 3;
                    switch (c) {
                    }
                }
            } else if (str.equals("get_fav_m3u")) {
                c = 0;
                switch (c) {
                }
            }
            c = 65535;
            switch (c) {
            }
        }
    }

    private void setUpdatabaseResult() {
        try {
            if (this.context != null) {
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
                        if (!SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                            new AsyncTaskVOD().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "get_fav");
                            break;
                        } else {
                            new AsyncTaskVOD().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "get_fav_m3u");
                            break;
                        }
                    case 1:
                        new AsyncTaskVOD().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "get_recent_watched", this.getActiveLiveStreamCategoryId);
                        break;
                    default:
                        new AsyncTaskVOD().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "get_all", this.getActiveLiveStreamCategoryId);
                        break;
                }
            }
            if (this.progressDialog != null) {
                this.progressDialog.dismiss();
            }
        } catch (Exception unused) {
        }
    }

    public void deleteCurrentItem(final int i, String str, final Context context2, final RecentWatchDBHandler recentWatchDBHandler2) {
        try {
            View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate((int) R.layout.delete_recording_popup, (RelativeLayout) findViewById(R.id.rl_password_verification));
            this.changeSortPopUp = new PopupWindow(this);
            this.changeSortPopUp.setContentView(inflate);
            this.changeSortPopUp.setWidth(-1);
            this.changeSortPopUp.setHeight(-1);
            this.changeSortPopUp.setFocusable(true);
            this.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
            ((TextView) inflate.findViewById(R.id.tv_delete_recording)).setText(getResources().getString(R.string.delete_this_recent_movies));
            Button button = (Button) inflate.findViewById(R.id.bt_start_recording);
            Button button2 = (Button) inflate.findViewById(R.id.bt_close);
            if (button != null) {
                button.setOnFocusChangeListener(new OnFocusChangeAccountListener(button));
            }
            if (button2 != null) {
                button2.setOnFocusChangeListener(new OnFocusChangeAccountListener(button2));
            }
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSubCategories.AnonymousClass17 */

                public void onClick(View view) {
                    VodActivityNewFlowSubCategories.this.changeSortPopUp.dismiss();
                }
            });
            if (button != null) {
                button.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.activity.VodActivityNewFlowSubCategories.AnonymousClass18 */

                    public void onClick(View view) {
                        if (recentWatchDBHandler2 != null) {
                            recentWatchDBHandler2.deleteRecentwatch(i, AppConst.EVENT_TYPE_MOVIE);
                        }
                        Toast.makeText(context2, VodActivityNewFlowSubCategories.this.getResources().getString(R.string.movie_deleted_successfully), 0).show();
                        if (VodActivityNewFlowSubCategories.this.vodAdapter != null) {
                            VodActivityNewFlowSubCategories.this.setLayout();
                            VodActivityNewFlowSubCategories.this.changeSortPopUp.dismiss();
                        }
                    }
                });
            }
        } catch (Exception unused) {
        }
    }

    private ArrayList<LiveStreamsDBModel> getUnlockedCategories(ArrayList<LiveStreamsDBModel> arrayList, ArrayList<String> arrayList2) {
        Iterator<LiveStreamsDBModel> it = arrayList.iterator();
        while (it.hasNext()) {
            LiveStreamsDBModel next = it.next();
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
                next.getCategoryId();
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
        if (!(this.mAdapter == null || pbPagingLoader1 == null)) {
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
        this.mAdapter.setVisibiltygone(pbPagingLoader1);
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

    public void atStart() {
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(0);
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
                if (this.view.getTag() != null && this.view.getTag().equals("1")) {
                    performScaleXAnimation(1.15f);
                    performScaleYAnimation(1.15f);
                    this.view.setBackgroundResource(R.drawable.back_btn_effect);
                } else if (this.view.getTag() == null || !this.view.getTag().equals("2")) {
                    performScaleXAnimation(1.15f);
                } else {
                    performScaleXAnimation(1.15f);
                    performScaleYAnimation(1.15f);
                    this.view.setBackgroundResource(R.drawable.logout_btn_effect);
                }
            } else if (!z) {
                performAlphaAnimation(z);
                if (this.view.getTag() != null && this.view.getTag().equals("1")) {
                    performScaleXAnimation(1.0f);
                    performScaleYAnimation(1.0f);
                    this.view.setBackgroundResource(R.drawable.black_button_dark);
                } else if (this.view.getTag() == null || !this.view.getTag().equals("2")) {
                    performScaleXAnimation(1.0f);
                    performScaleYAnimation(1.0f);
                } else {
                    performScaleXAnimation(1.0f);
                    performScaleYAnimation(1.0f);
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
}
