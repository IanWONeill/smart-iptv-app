package com.nst.yourname.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.nst.yourname.miscelleneious.AdapterSectionRecycler;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.PasswordStatusDBModel;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.presenter.XMLTVPresenter;
import com.nst.yourname.view.adapter.VodAdapter;
import com.nst.yourname.view.adapter.VodAdapterNewFlow;
import com.nst.yourname.view.adapter.VodSubCatAdpaterNew;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
public class VodActivityNewFlowSecondSubCategories extends AppCompatActivity implements View.OnClickListener {
    static final boolean $assertionsDisabled = false;
    private static final String JSON = "";
    static ProgressBar pbPagingLoader1;
    private static ArrayList<LiveStreamCategoryIdDBModel> subCategoryList = new ArrayList<>();
    private static ArrayList<LiveStreamCategoryIdDBModel> subCategoryListFinal = new ArrayList<>();
    private static ArrayList<LiveStreamCategoryIdDBModel> subCategoryListFinal_menu = new ArrayList<>();
    private SharedPreferences SharedPreferencesSort;
    public SharedPreferences.Editor SharedPreferencesSortEditor;
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
    private ArrayList<LiveStreamsDBModel> favouriteStreams = new ArrayList<>();
    private String getActiveLiveStreamCategoryId = "";
    private String getActiveLiveStreamCategoryName = "";
    GridLayoutManager gridLayoutManager;
    private boolean isSubcaetgroy = false;
    boolean isSubcaetgroyAvail = false;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> listPassword = new ArrayList<>();
    private ArrayList<LiveStreamsDBModel> liveListDetailAvailable;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlcked;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlckedDetail;
    private LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences loginPreferencesSharedPref;
    @BindView(R.id.logo)
    ImageView logo;
    private VodAdapterNewFlow mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    public SharedPreferences pref;
    private ProgressDialog progressDialog;
    @BindView(R.id.rl_sub_cat)
    RelativeLayout rl_sub_cat;
    SearchView searchView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    TypedValue f34tv;
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
    private XMLTVPresenter xmltvPresenter;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
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
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.VodActivityNewFlowSecondSubCategories.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(VodActivityNewFlowSecondSubCategories.this.context);
            }
        });
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        subCategoryListFinal = this.liveStreamDBHandler.getAllMovieCategoriesHavingParentIdNotZero(this.getActiveLiveStreamCategoryId);
        setContentView((int) R.layout.activity_vod_new_flow_subcategories);
        ButterKnife.bind(this);
        atStart();
        this.isSubcaetgroyAvail = true;
        setSubCategoryLayout(subCategoryListFinal);
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
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (this.isSubcaetgroyAvail) {
            this.toolbar.inflateMenu(R.menu.menu_search);
        } else {
            this.toolbar.inflateMenu(R.menu.menu_search_text_icon);
        }
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
        this.toolbar.collapseActionView();
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
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSecondSubCategories.AnonymousClass3 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(VodActivityNewFlowSecondSubCategories.this.context);
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSecondSubCategories.AnonymousClass2 */

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
                    /* class com.nst.yourname.view.activity.VodActivityNewFlowSecondSubCategories.AnonymousClass4 */

                    @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                    public boolean onQueryTextSubmit(String str) {
                        return false;
                    }

                    @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                    public boolean onQueryTextChange(String str) {
                        VodActivityNewFlowSecondSubCategories.this.tvNoRecordFound.setVisibility(8);
                        if (VodActivityNewFlowSecondSubCategories.this.vodSubCatAdpaterNew == null || VodActivityNewFlowSecondSubCategories.this.tvNoStream == null || VodActivityNewFlowSecondSubCategories.this.tvNoStream.getVisibility() == 0) {
                            return false;
                        }
                        VodActivityNewFlowSecondSubCategories.this.vodSubCatAdpaterNew.filter(str, VodActivityNewFlowSecondSubCategories.this.tvNoRecordFound);
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
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSecondSubCategories.AnonymousClass5 */

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextSubmit(String str) {
                    return false;
                }

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextChange(String str) {
                    VodActivityNewFlowSecondSubCategories.this.tvNoRecordFound.setVisibility(8);
                    if (VodActivityNewFlowSecondSubCategories.this.vodAdapter == null || VodActivityNewFlowSecondSubCategories.this.tvNoStream == null || VodActivityNewFlowSecondSubCategories.this.tvNoStream.getVisibility() == 0) {
                        return false;
                    }
                    VodActivityNewFlowSecondSubCategories.this.vodAdapter.filter(str, VodActivityNewFlowSecondSubCategories.this.tvNoRecordFound);
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
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSecondSubCategories.AnonymousClass6 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadChannelsAndVod(VodActivityNewFlowSecondSubCategories.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSecondSubCategories.AnonymousClass7 */

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
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSecondSubCategories.AnonymousClass8 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadTvGuid(VodActivityNewFlowSecondSubCategories.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSecondSubCategories.AnonymousClass9 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder2.show();
        }
        if (itemId == R.id.layout_view_grid) {
            if (this.getActiveLiveStreamCategoryId.equals(AppConst.PASSWORD_UNSET)) {
                this.editor.putInt(AppConst.VOD, 0);
                this.editor.commit();
                initialize();
                getAllMovies();
            } else if (this.getActiveLiveStreamCategoryId.equals("-1")) {
                this.editor.putInt(AppConst.VOD, 0);
                this.editor.commit();
                initialize();
            } else {
                subCategoryListFinal_menu.clear();
                subCategoryListFinal_menu = this.liveStreamDBHandler.getAllMovieCategoriesHavingParentIdNotZero(this.getActiveLiveStreamCategoryId);
                if (subCategoryListFinal_menu.size() <= 0) {
                    this.editor.putInt(AppConst.VOD, 0);
                    this.editor.commit();
                    initialize();
                }
            }
        }
        if (itemId == R.id.layout_view_linear) {
            if (this.getActiveLiveStreamCategoryId.equals(AppConst.PASSWORD_UNSET)) {
                this.editor.putInt(AppConst.VOD, 1);
                this.editor.commit();
                getAllMovies();
                initialize1();
            } else if (this.getActiveLiveStreamCategoryId.equals("-1")) {
                this.editor.putInt(AppConst.VOD, 1);
                this.editor.commit();
                initialize1();
            } else {
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
        return super.onOptionsItemSelected(menuItem);
    }

    private void showSortPopup(Activity activity) {
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
            RadioButton radioButton2 = (RadioButton) inflate.findViewById(R.id.rb_lastadded);
            RadioButton radioButton3 = (RadioButton) inflate.findViewById(R.id.rb_atoz);
            RadioButton radioButton4 = (RadioButton) inflate.findViewById(R.id.rb_ztoa);
            String string = this.SharedPreferencesSort.getString(AppConst.LOGIN_PREF_SORT, "");
            if (string.equals("1")) {
                radioButton2.setChecked(true);
            } else if (string.equals("2")) {
                radioButton3.setChecked(true);
            } else if (string.equals("3")) {
                radioButton4.setChecked(true);
            } else {
                radioButton.setChecked(true);
            }
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSecondSubCategories.AnonymousClass10 */

                public void onClick(View view) {
                    VodActivityNewFlowSecondSubCategories.this.changeSortPopUp.dismiss();
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.VodActivityNewFlowSecondSubCategories.AnonymousClass11 */

                public void onClick(View view) {
                    RadioButton radioButton = (RadioButton) inflate.findViewById(radioGroup.getCheckedRadioButtonId());
                    if (radioButton.getText().toString().equals(VodActivityNewFlowSecondSubCategories.this.getResources().getString(R.string.sort_last_added))) {
                        VodActivityNewFlowSecondSubCategories.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "1");
                        VodActivityNewFlowSecondSubCategories.this.SharedPreferencesSortEditor.commit();
                    } else if (radioButton.getText().toString().equals(VodActivityNewFlowSecondSubCategories.this.getResources().getString(R.string.sort_atoz))) {
                        VodActivityNewFlowSecondSubCategories.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "2");
                        VodActivityNewFlowSecondSubCategories.this.SharedPreferencesSortEditor.commit();
                    } else if (radioButton.getText().toString().equals(VodActivityNewFlowSecondSubCategories.this.getResources().getString(R.string.sort_ztoa))) {
                        VodActivityNewFlowSecondSubCategories.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "3");
                        VodActivityNewFlowSecondSubCategories.this.SharedPreferencesSortEditor.commit();
                    } else {
                        VodActivityNewFlowSecondSubCategories.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, AppConst.PASSWORD_UNSET);
                        VodActivityNewFlowSecondSubCategories.this.SharedPreferencesSortEditor.commit();
                    }
                    SharedPreferences unused = VodActivityNewFlowSecondSubCategories.this.pref = VodActivityNewFlowSecondSubCategories.this.getSharedPreferences("listgridview", 0);
                    SharedPreferences.Editor unused2 = VodActivityNewFlowSecondSubCategories.this.editor = VodActivityNewFlowSecondSubCategories.this.pref.edit();
                    AppConst.LIVE_FLAG_VOD = VodActivityNewFlowSecondSubCategories.this.pref.getInt(AppConst.VOD, 0);
                    if (AppConst.LIVE_FLAG_VOD == 1) {
                        VodActivityNewFlowSecondSubCategories.this.initialize1();
                    } else {
                        VodActivityNewFlowSecondSubCategories.this.initialize();
                    }
                    VodActivityNewFlowSecondSubCategories.this.changeSortPopUp.dismiss();
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
                edit.commit();
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

    public void getFavourites() {
        new ArrayList();
        this.favouriteStreams.clear();
        if (this.myRecyclerView != null) {
            this.myRecyclerView.setAdapter(this.vodAdapter);
        }
        if (this.context != null) {
            this.database = new DatabaseHandler(this.context);
            ArrayList<FavouriteDBModel> allFavourites = this.database.getAllFavourites(AppConst.VOD, SharepreferenceDBHandler.getUserID(this.context));
            if (this.listPassword != null) {
                this.listPassword = getPasswordSetCategories();
            }
            if (this.listPassword != null && this.listPassword.size() > 0 && allFavourites != null && allFavourites.size() > 0) {
                getfavUnlovked(allFavourites, this.listPassword);
            }
            Iterator<FavouriteDBModel> it = allFavourites.iterator();
            while (it.hasNext()) {
                FavouriteDBModel next = it.next();
                LiveStreamsDBModel liveStreamFavouriteRow = new LiveStreamDBHandler(this.context).getLiveStreamFavouriteRow(next.getCategoryID(), String.valueOf(next.getStreamID()));
                if (liveStreamFavouriteRow != null) {
                    this.favouriteStreams.add(liveStreamFavouriteRow);
                }
            }
            onFinish();
            if (!(this.myRecyclerView == null || this.favouriteStreams == null || this.favouriteStreams.size() == 0)) {
                this.vodAdapter = new VodAdapter(this.favouriteStreams, this.context, true);
                this.myRecyclerView.setAdapter(this.vodAdapter);
                this.vodAdapter.notifyDataSetChanged();
                this.tvNoStream.setVisibility(4);
            }
            if (this.tvNoStream != null && this.favouriteStreams != null && this.favouriteStreams.size() == 0) {
                if (this.myRecyclerView != null) {
                    this.myRecyclerView.setAdapter(this.vodAdapter);
                }
                this.tvNoStream.setText(getResources().getString(R.string.no_fav_vod_found));
                this.tvNoStream.setVisibility(0);
            }
        }
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
            this.loginPreferencesSharedPref.getString("username", "");
            this.loginPreferencesSharedPref.getString("password", "");
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
            this.loginPreferencesSharedPref.getString("username", "");
            this.loginPreferencesSharedPref.getString("password", "");
            setUpdatabaseResult();
        }
    }

    private void setUpdatabaseResult() {
        try {
            if (this.context != null) {
                LiveStreamDBHandler liveStreamDBHandler2 = new LiveStreamDBHandler(this.context);
                if (!this.getActiveLiveStreamCategoryId.equals("-1")) {
                    if (this.getActiveLiveStreamCategoryId.equals(AppConst.PASSWORD_UNSET)) {
                        this.categoryWithPasword = new ArrayList<>();
                        this.liveListDetailUnlcked = new ArrayList<>();
                        this.liveListDetailUnlckedDetail = new ArrayList<>();
                        this.liveListDetailAvailable = new ArrayList<>();
                        ArrayList<LiveStreamsDBModel> allLiveStreasWithCategoryId = liveStreamDBHandler2.getAllLiveStreasWithCategoryId(this.getActiveLiveStreamCategoryId, AppConst.EVENT_TYPE_MOVIE);
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
                        if (this.progressDialog != null) {
                            this.progressDialog.dismiss();
                        }
                        if (this.liveListDetailAvailable != null && this.myRecyclerView != null && this.liveListDetailAvailable.size() != 0) {
                            this.vodAdapter = new VodAdapter(this.liveListDetailAvailable, this.context, true);
                            this.myRecyclerView.setAdapter(this.vodAdapter);
                            Utils.showToastLong(this.context, getResources().getString(R.string.use_long_press));
                        } else if (this.tvNoStream != null) {
                            this.tvNoStream.setVisibility(0);
                        }
                    } else {
                        ArrayList<LiveStreamsDBModel> allLiveStreasWithCategoryId2 = liveStreamDBHandler2.getAllLiveStreasWithCategoryId(this.getActiveLiveStreamCategoryId, AppConst.EVENT_TYPE_MOVIE);
                        onFinish();
                        if (this.progressDialog != null) {
                            this.progressDialog.dismiss();
                        }
                        if (allLiveStreasWithCategoryId2 != null && this.myRecyclerView != null && allLiveStreasWithCategoryId2.size() != 0) {
                            this.vodAdapter = new VodAdapter(allLiveStreasWithCategoryId2, this.context, true);
                            this.myRecyclerView.setAdapter(this.vodAdapter);
                        } else if (this.tvNoStream != null) {
                            this.tvNoStream.setVisibility(0);
                        }
                    }
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
            this.pbLoader.setVisibility(4);
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
}
