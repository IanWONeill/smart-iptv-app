package com.nst.yourname.view.fragment;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ActionMenuView;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.view.activity.ImportEPGActivity;
import com.nst.yourname.view.activity.NewDashboardActivity;
import com.nst.yourname.view.activity.ParentalControlActivitity;
import com.nst.yourname.view.activity.SettingsActivity;
import com.nst.yourname.view.adapter.ParentalControlLiveCatgoriesAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ParentalControlCategoriesFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Context context;
    int countUncat = -1;
    ParentalControlActivitity dashboardActivity;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive = new DatabaseUpdatedStatusDBModel();
    @BindView(R.id.empty_view)
    TextView emptyView;
    private Typeface fontOPenSansBold;
    LiveStreamDBHandler liveStreamDBHandler;
    public ParentalControlLiveCatgoriesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    private FragmentActivity myContext;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    public ProgressDialog progressDialog;
    private SearchView searchView;
    private Toolbar toolbar;
    Unbinder unbinder;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override // android.support.v4.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
    }

    public static ParentalControlCategoriesFragment newInstance(String str, String str2) {
        ParentalControlCategoriesFragment parentalControlCategoriesFragment = new ParentalControlCategoriesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, str);
        bundle.putString(ARG_PARAM2, str2);
        parentalControlCategoriesFragment.setArguments(bundle);
        return parentalControlCategoriesFragment;
    }

    @Override // android.support.v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [?, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        setHasOptionsMenu(false);
        View inflate = layoutInflater.inflate((int) R.layout.fragment_parental_control_categories, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        initializeData();
        setMenuBar();
        return inflate;
    }

    private void initializeData() {
        this.context = getContext();
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.fontOPenSansBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans.ttf");
        this.dashboardActivity = (ParentalControlActivitity) this.context;
        this.myRecyclerView.setHasFixedSize(true);
        this.mLayoutManager = new LinearLayoutManager(getContext());
        this.myRecyclerView.setLayoutManager(this.mLayoutManager);
        LiveStreamDBHandler liveStreamDBHandler2 = new LiveStreamDBHandler(this.context);
        ArrayList<LiveStreamCategoryIdDBModel> allliveCategories = liveStreamDBHandler2.getAllliveCategories();
        this.countUncat = liveStreamDBHandler2.getUncatCount("-2", "live");
        LiveStreamCategoryIdDBModel liveStreamCategoryIdDBModel = new LiveStreamCategoryIdDBModel();
        if (this.countUncat != 0 && this.countUncat > 0) {
            liveStreamCategoryIdDBModel.setLiveStreamCategoryID("-2");
            liveStreamCategoryIdDBModel.setLiveStreamCategoryName(getResources().getString(R.string.uncategories));
            allliveCategories.add(allliveCategories.size(), liveStreamCategoryIdDBModel);
        }
        HashMap hashMap = new HashMap();
        if (allliveCategories != null) {
            Iterator<LiveStreamCategoryIdDBModel> it = allliveCategories.iterator();
            while (it.hasNext()) {
                LiveStreamCategoryIdDBModel next = it.next();
                hashMap.put(next.getLiveStreamCategoryID(), next.getLiveStreamCategoryName());
            }
        }
        String[] strArr = (String[]) hashMap.values().toArray(new String[0]);
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(4);
        }
        if (allliveCategories != null && allliveCategories.size() > 0 && this.myRecyclerView != null && this.emptyView != null) {
            this.myRecyclerView.setVisibility(0);
            this.emptyView.setVisibility(8);
            this.mAdapter = new ParentalControlLiveCatgoriesAdapter(allliveCategories, getContext(), this.dashboardActivity, this.fontOPenSansBold);
            this.myRecyclerView.setAdapter(this.mAdapter);
        } else if (this.myRecyclerView != null && this.emptyView != null) {
            this.myRecyclerView.setVisibility(8);
            this.emptyView.setVisibility(0);
            this.emptyView.setText(getResources().getString(R.string.no_live_cat_found));
        }
    }

    private void setMenuBar() {
        setHasOptionsMenu(true);
        this.toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    public void onButtonPressed(Uri uri) {
        if (this.mListener != null) {
            this.mListener.onFragmentInteraction(uri);
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onAttach(Context context2) {
        super.onAttach(context2);
        if (context2 instanceof OnFragmentInteractionListener) {
            this.mListener = (OnFragmentInteractionListener) context2;
            return;
        }
        throw new RuntimeException(context2.toString() + " must implement OnFragmentInteractionListener");
    }

    @Override // android.support.v4.app.Fragment
    public void onDetach() {
        super.onDetach();
        setHasOptionsMenu(false);
        this.mListener = null;
    }

    @Override // android.support.v4.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        if (menu != null) {
            menu.clear();
        }
        this.toolbar.inflateMenu(R.menu.menu_search);
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

    @Override // android.support.v4.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        menuItem.getItemId();
        switch (menuItem.getItemId()) {
            case R.id.action_logout1:
                if (this.context != null) {
                    Utils.logoutUser(this.context);
                }
                return false;
            case R.id.action_search:
                this.searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
                this.searchView.setQueryHint(getResources().getString(R.string.search_live_categories));
                SearchManager searchManager = (SearchManager) this.context.getSystemService("search");
                this.searchView.setIconifiedByDefault(false);
                this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    /* class com.nst.yourname.view.fragment.ParentalControlCategoriesFragment.AnonymousClass1 */

                    @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                    public boolean onQueryTextSubmit(String str) {
                        return false;
                    }

                    @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                    public boolean onQueryTextChange(String str) {
                        if (ParentalControlCategoriesFragment.this.pbLoader != null) {
                            ParentalControlCategoriesFragment.this.pbLoader.setVisibility(4);
                        }
                        if (ParentalControlCategoriesFragment.this.progressDialog != null) {
                            ParentalControlCategoriesFragment.this.progressDialog.dismiss();
                        }
                        if (ParentalControlCategoriesFragment.this.emptyView == null || ParentalControlCategoriesFragment.this.mAdapter == null) {
                            return true;
                        }
                        ParentalControlCategoriesFragment.this.emptyView.setVisibility(8);
                        ParentalControlCategoriesFragment.this.mAdapter.filter(str, ParentalControlCategoriesFragment.this.emptyView, ParentalControlCategoriesFragment.this.progressDialog);
                        return true;
                    }
                });
                return true;
            case R.id.menu_load_channels_vod1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
                builder.setTitle(this.context.getResources().getString(R.string.confirm_to_refresh));
                builder.setMessage(this.context.getResources().getString(R.string.do_you_want_toproceed));
                builder.setIcon((int) R.drawable.questionmark);
                builder.setPositiveButton(this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    /* class com.nst.yourname.view.fragment.ParentalControlCategoriesFragment.AnonymousClass2 */

                    public void onClick(DialogInterface dialogInterface, int i) {
                        Utils.loadChannelsAndVod(ParentalControlCategoriesFragment.this.context);
                    }
                });
                builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    /* class com.nst.yourname.view.fragment.ParentalControlCategoriesFragment.AnonymousClass3 */

                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
                return true;
            case R.id.menu_load_tv_guide1:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this.context);
                builder2.setTitle(this.context.getResources().getString(R.string.confirm_to_refresh));
                builder2.setMessage(this.context.getResources().getString(R.string.do_you_want_toproceed));
                builder2.setIcon((int) R.drawable.questionmark);
                builder2.setPositiveButton(this.context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    /* class com.nst.yourname.view.fragment.ParentalControlCategoriesFragment.AnonymousClass4 */

                    public void onClick(DialogInterface dialogInterface, int i) {
                        Utils.loadTvGuid(ParentalControlCategoriesFragment.this.context);
                    }
                });
                builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    /* class com.nst.yourname.view.fragment.ParentalControlCategoriesFragment.AnonymousClass5 */

                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder2.show();
                return true;
            case R.id.nav_home:
                startActivity(new Intent(this.context, NewDashboardActivity.class));
                startActivity(new Intent(this.context, SettingsActivity.class));
                this.searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
                this.searchView.setQueryHint(getResources().getString(R.string.search_live_categories));
                SearchManager searchManager2 = (SearchManager) this.context.getSystemService("search");
                this.searchView.setIconifiedByDefault(false);
                this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    /* class com.nst.yourname.view.fragment.ParentalControlCategoriesFragment.AnonymousClass1 */

                    @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                    public boolean onQueryTextSubmit(String str) {
                        return false;
                    }

                    @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                    public boolean onQueryTextChange(String str) {
                        if (ParentalControlCategoriesFragment.this.pbLoader != null) {
                            ParentalControlCategoriesFragment.this.pbLoader.setVisibility(4);
                        }
                        if (ParentalControlCategoriesFragment.this.progressDialog != null) {
                            ParentalControlCategoriesFragment.this.progressDialog.dismiss();
                        }
                        if (ParentalControlCategoriesFragment.this.emptyView == null || ParentalControlCategoriesFragment.this.mAdapter == null) {
                            return true;
                        }
                        ParentalControlCategoriesFragment.this.emptyView.setVisibility(8);
                        ParentalControlCategoriesFragment.this.mAdapter.filter(str, ParentalControlCategoriesFragment.this.emptyView, ParentalControlCategoriesFragment.this.progressDialog);
                        return true;
                    }
                });
                return true;
            case R.id.nav_settings:
                startActivity(new Intent(this.context, SettingsActivity.class));
                this.searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
                this.searchView.setQueryHint(getResources().getString(R.string.search_live_categories));
                SearchManager searchManager22 = (SearchManager) this.context.getSystemService("search");
                this.searchView.setIconifiedByDefault(false);
                this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    /* class com.nst.yourname.view.fragment.ParentalControlCategoriesFragment.AnonymousClass1 */

                    @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                    public boolean onQueryTextSubmit(String str) {
                        return false;
                    }

                    @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                    public boolean onQueryTextChange(String str) {
                        if (ParentalControlCategoriesFragment.this.pbLoader != null) {
                            ParentalControlCategoriesFragment.this.pbLoader.setVisibility(4);
                        }
                        if (ParentalControlCategoriesFragment.this.progressDialog != null) {
                            ParentalControlCategoriesFragment.this.progressDialog.dismiss();
                        }
                        if (ParentalControlCategoriesFragment.this.emptyView == null || ParentalControlCategoriesFragment.this.mAdapter == null) {
                            return true;
                        }
                        ParentalControlCategoriesFragment.this.emptyView.setVisibility(8);
                        ParentalControlCategoriesFragment.this.mAdapter.filter(str, ParentalControlCategoriesFragment.this.emptyView, ParentalControlCategoriesFragment.this.progressDialog);
                        return true;
                    }
                });
                return true;
            default:
                return false;
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
}
