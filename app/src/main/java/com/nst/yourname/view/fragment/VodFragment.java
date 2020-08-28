package com.nst.yourname.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.AdapterSectionRecycler;
import com.nst.yourname.miscelleneious.Child;
import com.nst.yourname.miscelleneious.SectionHeader;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.VODDBModel;
import com.nst.yourname.model.callback.VodStreamsCallback;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.RecentWatchDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.activity.ImportEPGActivity;
import com.nst.yourname.view.activity.NewDashboardActivity;
import com.nst.yourname.view.activity.SettingsActivity;
import com.nst.yourname.view.adapter.SubCategoriesChildAdapter;
import com.nst.yourname.view.adapter.VodAdapter;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("ALL")
public class VodFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    static final boolean $assertionsDisabled = false;
    public static final String ACTIVE_LIVE_STREAM_CATEGORY_ID = "";
    public static final String ACTIVE_LIVE_STREAM_CATEGORY_NAME = "";
    private static ArrayList<LiveStreamCategoryIdDBModel> subCategoryList = new ArrayList<>();
    private static ArrayList<LiveStreamCategoryIdDBModel> subCategoryListFinal = new ArrayList<>();
    private static ArrayList<LiveStreamCategoryIdDBModel> subCategoryListFinal_menu = new ArrayList<>();
    private SharedPreferences SharedPreferencesSort;
    public SharedPreferences.Editor SharedPreferencesSortEditor;
    private AdapterSectionRecycler adapterRecycler;
    public PopupWindow changeSortPopUp;
    public Context context;
    DatabaseHandler database;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive = new DatabaseUpdatedStatusDBModel();
    public SharedPreferences.Editor editor;
    VODDBModel favouriteStream = new VODDBModel();
    private ArrayList<LiveStreamsDBModel> favouriteStreams = new ArrayList<>();
    private ArrayList<VodStreamsCallback> favouriteVOD = new ArrayList<>();
    private String getActiveLiveStreamCategoryId;
    private String getActiveLiveStreamCategoryName;
    private boolean isSubcaetgroy = false;
    private RecyclerView.LayoutManager layoutManager;
    LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesSharedPref;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    public SharedPreferences pref;
    private ProgressDialog progressDialog;
    SearchView searchView;
    private Toolbar toolbar;
    @BindView(R.id.tv_no_record_found)
    TextView tvNoRecordFound;
    @BindView(R.id.tv_noStream)
    TextView tvNoStream;
    @BindView(R.id.tv_view_provider)
    TextView tvViewProvider;
    Unbinder unbinder;
    public VodAdapter vodAdapter;

    @Override // android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    public static VodFragment newInstance(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString("", str);
        bundle.putString("cat_name", str2);
        VodFragment vodFragment = new VodFragment();
        vodFragment.setArguments(bundle);
        return vodFragment;
    }

    @Override // android.support.v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.getActiveLiveStreamCategoryId = getArguments().getString("");
        this.getActiveLiveStreamCategoryName = getArguments().getString("cat_name");
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [?, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00a4  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00cc  */
    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        char c;
        this.context = getContext();
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.SharedPreferencesSort = getActivity().getSharedPreferences(AppConst.LOGIN_PREF_SORT, 0);
        this.SharedPreferencesSortEditor = this.SharedPreferencesSort.edit();
        if (this.SharedPreferencesSort.getString(AppConst.LOGIN_PREF_SORT, "").equals("")) {
            this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, AppConst.PASSWORD_UNSET);
            this.SharedPreferencesSortEditor.commit();
        }
        String str = this.getActiveLiveStreamCategoryId;
        int hashCode = str.hashCode();
        if (hashCode != 48) {
            if (hashCode != 1444) {
                if (hashCode == 1447 && str.equals("-4")) {
                    c = 1;
                    switch (c) {
                        case 0:
                            View inflate = layoutInflater.inflate((int) R.layout.fragment_vod_streams, viewGroup, false);
                            this.unbinder = ButterKnife.bind(this, inflate);
                            atStart();
                            setLayout();
                            getFavourites();
                            return inflate;
                        case 1:
                            View inflate2 = layoutInflater.inflate((int) R.layout.fragment_vod_streams, viewGroup, false);
                            this.unbinder = ButterKnife.bind(this, inflate2);
                            atStart();
                            setLayout();
                            getRecentWatch();
                            return inflate2;
                        case 2:
                            View inflate3 = layoutInflater.inflate((int) R.layout.fragment_vod_streams, viewGroup, false);
                            this.unbinder = ButterKnife.bind(this, inflate3);
                            atStart();
                            setLayout();
                            getAllMovies();
                            return inflate3;
                        default:
                            subCategoryListFinal = this.liveStreamDBHandler.getAllMovieCategoriesHavingParentIdNotZero(this.getActiveLiveStreamCategoryId);
                            if (subCategoryListFinal == null || subCategoryListFinal.size() != 0) {
                                View inflate4 = layoutInflater.inflate((int) R.layout.fragment_vod_subcategories, viewGroup, false);
                                this.unbinder = ButterKnife.bind(this, inflate4);
                                atStart();
                                setSubCategoryLayout(subCategoryListFinal);
                                return inflate4;
                            }
                            View inflate5 = layoutInflater.inflate((int) R.layout.fragment_vod_streams, viewGroup, false);
                            this.unbinder = ButterKnife.bind(this, inflate5);
                            atStart();
                            setLayout();
                            return inflate5;
                    }
                }
            } else if (str.equals("-1")) {
                c = 0;
                switch (c) {
                }
            }
        } else if (str.equals(AppConst.PASSWORD_UNSET)) {
            c = 2;
            switch (c) {
            }
        }
        c = 65535;
        switch (c) {
        }
        //ToDo: return statement...
        return null;
    }

    private void getRecentWatch() {
        atStart();
        ActivityCompat.invalidateOptionsMenu(getActivity());
        setHasOptionsMenu(true);
        setToolbarLogoImagewithSearchView();
        this.pref = getActivity().getSharedPreferences("listgridview", 0);
        this.editor = this.pref.edit();
        AppConst.LIVE_FLAG_VOD = this.pref.getInt(AppConst.VOD, 0);
        if (AppConst.LIVE_FLAG_VOD == 1) {
            this.context = getContext();
            if (!(this.myRecyclerView == null || this.context == null)) {
                this.myRecyclerView.setHasFixedSize(true);
                this.layoutManager = new LinearLayoutManager(getContext());
                this.myRecyclerView.setLayoutManager(this.layoutManager);
                this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
            }
        } else {
            this.context = getContext();
            if (!(this.myRecyclerView == null || this.context == null)) {
                this.myRecyclerView.setHasFixedSize(true);
                this.layoutManager = new GridLayoutManager(getContext(), Utils.getNumberOfColumns(this.context) + 1);
                this.myRecyclerView.setLayoutManager(this.layoutManager);
                this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
            }
        }
        if (this.context != null) {
            ArrayList<LiveStreamsDBModel> allLiveStreasWithCategoryId = new RecentWatchDBHandler(this.context).getAllLiveStreasWithCategoryId(AppConst.EVENT_TYPE_MOVIE, SharepreferenceDBHandler.getUserID(this.context), "getalldata");
            onFinish();
            if (allLiveStreasWithCategoryId != null && this.myRecyclerView != null && allLiveStreasWithCategoryId.size() != 0) {
                this.vodAdapter = new VodAdapter(allLiveStreasWithCategoryId, getContext(), true);
                this.myRecyclerView.setAdapter(this.vodAdapter);
            } else if (this.tvNoStream != null) {
                this.tvNoStream.setVisibility(0);
            }
        }
    }

    private void setSubCategoryLayout(ArrayList<LiveStreamCategoryIdDBModel> arrayList) {
        ActivityCompat.invalidateOptionsMenu(getActivity());
        setHasOptionsMenu(true);
        setToolbarLogoImagewithSearchView();
        initializeSubCat(arrayList);
    }

    private void initializeSubCat(ArrayList<LiveStreamCategoryIdDBModel> arrayList) {
        if (this.myRecyclerView != null && this.context != null) {
            this.myRecyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.context);
            this.myRecyclerView.setLayoutManager(new LinearLayoutManager(this.context, 0, true));
            this.myRecyclerView.setLayoutManager(linearLayoutManager);
            this.myRecyclerView.setHasFixedSize(true);
            ArrayList arrayList2 = new ArrayList();
            ArrayList<LiveStreamsDBModel> allLiveStreasWithCategoryId = this.liveStreamDBHandler.getAllLiveStreasWithCategoryId(this.getActiveLiveStreamCategoryId, AppConst.EVENT_TYPE_MOVIE);
            RecyclerView recyclerView = new RecyclerView(this.context);
            SubCategoriesChildAdapter subCategoriesChildAdapter = new SubCategoriesChildAdapter(allLiveStreasWithCategoryId, this.context);
            recyclerView.setAdapter(subCategoriesChildAdapter);
            arrayList2.add(new Child("Bill Gates"));
            ArrayList arrayList3 = new ArrayList();
            Iterator<LiveStreamCategoryIdDBModel> it = arrayList.iterator();
            while (it.hasNext()) {
                LiveStreamCategoryIdDBModel next = it.next();
                new ArrayList();
                ArrayList<LiveStreamsDBModel> allLiveStreasWithCategoryId2 = this.liveStreamDBHandler.getAllLiveStreasWithCategoryId(next.getLiveStreamCategoryID(), AppConst.EVENT_TYPE_MOVIE);
                if (allLiveStreasWithCategoryId2 != null && allLiveStreasWithCategoryId2.size() > 0) {
                    arrayList3.add(new SectionHeader(recyclerView, next.getLiveStreamCategoryName(), this.liveStreamDBHandler.getAllLiveStreasWithCategoryId(next.getLiveStreamCategoryID(), AppConst.EVENT_TYPE_MOVIE), subCategoriesChildAdapter, arrayList2));
                }
            }
            new ArrayList();
            ArrayList<LiveStreamsDBModel> allLiveStreasWithCategoryId3 = this.liveStreamDBHandler.getAllLiveStreasWithCategoryId(this.getActiveLiveStreamCategoryId, AppConst.EVENT_TYPE_MOVIE);
            if (allLiveStreasWithCategoryId3 != null && allLiveStreasWithCategoryId3.size() > 0) {
                arrayList3.add(new SectionHeader(recyclerView, this.getActiveLiveStreamCategoryName, this.liveStreamDBHandler.getAllLiveStreasWithCategoryId(this.getActiveLiveStreamCategoryId, AppConst.EVENT_TYPE_MOVIE), subCategoriesChildAdapter, arrayList2));
            }
            onFinish();
            this.adapterRecycler = new AdapterSectionRecycler(this.context, arrayList3, allLiveStreasWithCategoryId, recyclerView);
            this.myRecyclerView.setAdapter(this.adapterRecycler);
        }
    }

    private void setLayout() {
        ActivityCompat.invalidateOptionsMenu(getActivity());
        setHasOptionsMenu(true);
        setToolbarLogoImagewithSearchView();
        this.pref = getActivity().getSharedPreferences("listgridview", 0);
        this.editor = this.pref.edit();
        AppConst.LIVE_FLAG_VOD = this.pref.getInt(AppConst.VOD, 0);
        if (AppConst.LIVE_FLAG_VOD == 1) {
            initialize1();
        } else {
            initialize();
        }
    }

    private void setToolbarLogoImagewithSearchView() {
        this.toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    @Override // android.support.v4.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (this.context != null && this.toolbar != null) {
            TypedValue typedValue = new TypedValue();
            if (this.context.getTheme().resolveAttribute(16843499, typedValue, true)) {
                TypedValue.complexToDimensionPixelSize(typedValue.data, this.context.getResources().getDisplayMetrics());
            }
            for (int i = 0; i < this.toolbar.getChildCount(); i++) {
                if (this.toolbar.getChildAt(i) instanceof ActionMenuView) {
                    ((Toolbar.LayoutParams) this.toolbar.getChildAt(i).getLayoutParams()).gravity = 16;
                }
            }
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        this.toolbar.inflateMenu(R.menu.menu_search_text_icon);
    }

    @Override // android.support.v4.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(this.context, NewDashboardActivity.class));
        }
        if (itemId == R.id.nav_settings) {
            startActivity(new Intent(this.context, SettingsActivity.class));
        }
        if (itemId == R.id.action_logout1) {
            if (this.context != null) {
                new AlertDialog.Builder(this.context, R.style.AlertDialogCustom).setTitle(getResources().getString(R.string.logout_title)).setMessage(getResources().getString(R.string.logout_message)).setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    /* class com.nst.yourname.view.fragment.VodFragment.AnonymousClass2 */

                    public void onClick(DialogInterface dialogInterface, int i) {
                        Utils.logoutUser(VodFragment.this.context);
                    }
                }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    /* class com.nst.yourname.view.fragment.VodFragment.AnonymousClass1 */

                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
            return true;
        } else if (itemId == R.id.action_search) {
            this.searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
            this.searchView.setQueryHint(getResources().getString(R.string.search_vod));
            this.searchView.setIconifiedByDefault(false);
            this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                /* class com.nst.yourname.view.fragment.VodFragment.AnonymousClass3 */

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextSubmit(String str) {
                    return false;
                }

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextChange(String str) {
                    VodFragment.this.tvNoRecordFound.setVisibility(8);
                    if (VodFragment.this.vodAdapter == null || VodFragment.this.tvNoStream == null || VodFragment.this.tvNoStream.getVisibility() == 0) {
                        return false;
                    }
                    VodFragment.this.vodAdapter.filter(str, VodFragment.this.tvNoRecordFound);
                    return false;
                }
            });
            return true;
        } else if (itemId == R.id.menu_load_channels_vod1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
            builder.setTitle(this.context.getResources().getString(R.string.confirm_to_refresh));
            builder.setMessage(this.context.getResources().getString(R.string.do_you_want_toproceed));
            builder.setIcon((int) R.drawable.questionmark);
            builder.setPositiveButton(this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.fragment.VodFragment.AnonymousClass4 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadChannelsAndVod(VodFragment.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.fragment.VodFragment.AnonymousClass5 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
            return true;
        } else if (itemId == R.id.menu_load_tv_guide1) {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this.context);
            builder2.setTitle(this.context.getResources().getString(R.string.confirm_to_refresh));
            builder2.setMessage(this.context.getResources().getString(R.string.do_you_want_toproceed));
            builder2.setIcon((int) R.drawable.questionmark);
            builder2.setPositiveButton(this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.fragment.VodFragment.AnonymousClass6 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadTvGuid(VodFragment.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.fragment.VodFragment.AnonymousClass7 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder2.show();
            return true;
        } else {
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
                showSortPopup(getActivity());
            }
            return super.onOptionsItemSelected(menuItem);
        }
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
                /* class com.nst.yourname.view.fragment.VodFragment.AnonymousClass8 */

                public void onClick(View view) {
                    VodFragment.this.changeSortPopUp.dismiss();
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.fragment.VodFragment.AnonymousClass9 */

                public void onClick(View view) {
                    RadioButton radioButton = (RadioButton) inflate.findViewById(radioGroup.getCheckedRadioButtonId());
                    if (radioButton.getText().toString().equals(VodFragment.this.getResources().getString(R.string.sort_last_added))) {
                        VodFragment.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "1");
                        VodFragment.this.SharedPreferencesSortEditor.commit();
                    } else if (radioButton.getText().toString().equals(VodFragment.this.getResources().getString(R.string.sort_atoz))) {
                        VodFragment.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "2");
                        VodFragment.this.SharedPreferencesSortEditor.commit();
                    } else if (radioButton.getText().toString().equals(VodFragment.this.getResources().getString(R.string.sort_ztoa))) {
                        VodFragment.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "3");
                        VodFragment.this.SharedPreferencesSortEditor.commit();
                    } else {
                        VodFragment.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, AppConst.PASSWORD_UNSET);
                        VodFragment.this.SharedPreferencesSortEditor.commit();
                    }
                    SharedPreferences unused = VodFragment.this.pref = activity.getSharedPreferences("listgridview", 0);
                    SharedPreferences.Editor unused2 = VodFragment.this.editor = VodFragment.this.pref.edit();
                    AppConst.LIVE_FLAG_VOD = VodFragment.this.pref.getInt(AppConst.VOD, 0);
                    if (AppConst.LIVE_FLAG_VOD == 1) {
                        VodFragment.this.initialize1();
                    } else {
                        VodFragment.this.initialize();
                    }
                    VodFragment.this.changeSortPopUp.dismiss();
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

    private boolean getChannelEPGUpdateStatus() {
        if (!(this.liveStreamDBHandler == null || this.databaseUpdatedStatusDBModelLive == null || this.databaseUpdatedStatusDBModelEPG == null)) {
            this.databaseUpdatedStatusDBModelLive = this.liveStreamDBHandler.getdateDBStatus(AppConst.DB_CHANNELS, "1");
            this.databaseUpdatedStatusDBModelEPG = this.liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, "2");
            if (!(this.databaseUpdatedStatusDBModelLive == null || this.databaseUpdatedStatusDBModelEPG == null)) {
                if (this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState() == null || this.databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState() == null) {
                    return true;
                }
                if ((!this.databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FINISH) || !this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FINISH)) && ((!this.databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FAILED) || !this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FAILED)) && ((!this.databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FINISH) || !this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FAILED)) && (!this.databaseUpdatedStatusDBModelLive.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FAILED) || !this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState().equals(AppConst.DB_UPDATED_STATUS_FINISH))))) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public void initialize() {
        this.context = getContext();
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        if (this.myRecyclerView != null && this.context != null) {
            this.myRecyclerView.setHasFixedSize(true);
            this.layoutManager = new GridLayoutManager(getContext(), Utils.getNumberOfColumns(this.context) + 1);
            this.myRecyclerView.setLayoutManager(this.layoutManager);
            this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
            Context context2 = this.context;
            Context context3 = this.context;
            this.loginPreferencesSharedPref = context2.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            this.loginPreferencesSharedPref.getString("username", "");
            this.loginPreferencesSharedPref.getString("password", "");
            setUpdatabaseResult();
        }
    }

    public void initialize1() {
        this.context = getContext();
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        if (this.myRecyclerView != null && this.context != null) {
            this.myRecyclerView.setHasFixedSize(true);
            this.layoutManager = new LinearLayoutManager(getContext());
            this.myRecyclerView.setLayoutManager(this.layoutManager);
            this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
            Context context2 = this.context;
            Context context3 = this.context;
            this.loginPreferencesSharedPref = context2.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            this.loginPreferencesSharedPref.getString("username", "");
            this.loginPreferencesSharedPref.getString("password", "");
            setUpdatabaseResult();
        }
    }

    private void setUpdatabaseResult() {
        if (this.context != null) {
            LiveStreamDBHandler liveStreamDBHandler2 = new LiveStreamDBHandler(this.context);
            if (!this.getActiveLiveStreamCategoryId.equals("-1")) {
                ArrayList<LiveStreamsDBModel> allLiveStreasWithCategoryId = liveStreamDBHandler2.getAllLiveStreasWithCategoryId(this.getActiveLiveStreamCategoryId, AppConst.EVENT_TYPE_MOVIE);
                onFinish();
                if (allLiveStreasWithCategoryId != null && this.myRecyclerView != null && allLiveStreasWithCategoryId.size() != 0) {
                    this.vodAdapter = new VodAdapter(allLiveStreasWithCategoryId, getContext(), true);
                    this.myRecyclerView.setAdapter(this.vodAdapter);
                } else if (this.tvNoStream != null) {
                    this.tvNoStream.setVisibility(0);
                }
            }
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    public void getFavourites() {
        this.favouriteStreams.clear();
        if (this.myRecyclerView != null) {
            this.myRecyclerView.setAdapter(this.vodAdapter);
        }
        if (this.context != null) {
            this.database = new DatabaseHandler(this.context);
            Iterator<FavouriteDBModel> it = this.database.getAllFavourites(AppConst.VOD, SharepreferenceDBHandler.getUserID(this.context)).iterator();
            while (it.hasNext()) {
                FavouriteDBModel next = it.next();
                LiveStreamsDBModel liveStreamFavouriteRow = new LiveStreamDBHandler(this.context).getLiveStreamFavouriteRow(next.getCategoryID(), String.valueOf(next.getStreamID()));
                if (liveStreamFavouriteRow != null) {
                    this.favouriteStreams.add(liveStreamFavouriteRow);
                }
            }
            onFinish();
            if (!(this.myRecyclerView == null || this.favouriteStreams == null || this.favouriteStreams.size() == 0)) {
                this.vodAdapter = new VodAdapter(this.favouriteStreams, getContext(), true);
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

    public void getAllMovies() {
        atStart();
        ActivityCompat.invalidateOptionsMenu(getActivity());
        setHasOptionsMenu(true);
        setToolbarLogoImagewithSearchView();
        this.pref = getActivity().getSharedPreferences("listgridview", 0);
        this.editor = this.pref.edit();
        AppConst.LIVE_FLAG_VOD = this.pref.getInt(AppConst.VOD, 0);
        if (AppConst.LIVE_FLAG_VOD == 1) {
            this.context = getContext();
            this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
            if (!(this.myRecyclerView == null || this.context == null)) {
                this.myRecyclerView.setHasFixedSize(true);
                this.layoutManager = new LinearLayoutManager(getContext());
                this.myRecyclerView.setLayoutManager(this.layoutManager);
                this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
            }
        } else {
            this.context = getContext();
            this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
            if (!(this.myRecyclerView == null || this.context == null)) {
                this.myRecyclerView.setHasFixedSize(true);
                this.layoutManager = new GridLayoutManager(getContext(), Utils.getNumberOfColumns(this.context) + 1);
                this.myRecyclerView.setLayoutManager(this.layoutManager);
                this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
            }
        }
        if (this.context != null) {
            ArrayList<LiveStreamsDBModel> allLiveStreasWithCategoryId = new LiveStreamDBHandler(this.context).getAllLiveStreasWithCategoryId(AppConst.PASSWORD_UNSET, AppConst.EVENT_TYPE_MOVIE);
            onFinish();
            if (allLiveStreasWithCategoryId != null && this.myRecyclerView != null && allLiveStreasWithCategoryId.size() != 0) {
                this.vodAdapter = new VodAdapter(allLiveStreasWithCategoryId, getContext(), true);
                this.myRecyclerView.setAdapter(this.vodAdapter);
            } else if (this.tvNoStream != null) {
                this.tvNoStream.setVisibility(0);
            }
        }
    }
}
