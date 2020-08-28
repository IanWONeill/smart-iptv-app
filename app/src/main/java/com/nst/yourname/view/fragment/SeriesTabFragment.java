package com.nst.yourname.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.EpgChannelModel;
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.callback.SeriesDBModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.PasswordStatusDBModel;
import com.nst.yourname.model.database.SeriesStreamsDatabaseHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.activity.NewDashboardActivity;
import com.nst.yourname.view.activity.SettingsActivity;
import com.nst.yourname.view.adapter.LiveStreamListViewAdapter;
import com.nst.yourname.view.adapter.SeriesStreamsAdapter;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("ALL")
public class SeriesTabFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    static final boolean $assertionsDisabled = false;
    public static final String ACTIVE_SERIES_TAB_CATEGORY_ID = "";
    private SharedPreferences SharedPreferencesSort;
    public SharedPreferences.Editor SharedPreferencesSortEditor;
    int actionBarHeight;
    private ArrayList<PasswordStatusDBModel> categoryWithPasword;
    public PopupWindow changeSortPopUp;
    public Context context;
    DatabaseHandler database;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive = new DatabaseUpdatedStatusDBModel();
    public SharedPreferences.Editor editor;
    ArrayList<EpgChannelModel> epgChannelModelList = new ArrayList<>();
    LiveStreamsDBModel favouriteStream = new LiveStreamsDBModel();
    private ArrayList<SeriesDBModel> favouriteStreams = new ArrayList<>();
    private String getActiveLiveStreamCategoryId;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> listPassword = new ArrayList<>();
    private ArrayList<LiveStreamsDBModel> liveListDetail;
    private ArrayList<SeriesDBModel> liveListDetailAvailable;
    private ArrayList<LiveStreamsDBModel> liveListDetailAvailableNew;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlcked;
    private ArrayList<LiveStreamsDBModel> liveListDetailUnlckedDetail;
    LiveStreamDBHandler liveStreamDBHandler;
    private LiveStreamListViewAdapter liveStreamsListViewAdapter;
    private SharedPreferences loginPreferencesSharedPref;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    public SharedPreferences pref;
    SearchView searchView;
    private SeriesStreamsAdapter seriesStreamsAdapter;
    private Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    TypedValue f38tv;
    @BindView(R.id.tv_no_record_found)
    TextView tvNoRecordFound;
    @BindView(R.id.tv_noStream)
    TextView tvNoStream;
    Unbinder unbinder;

    @Override // android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    public static SeriesTabFragment newInstance(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("", str);
        SeriesTabFragment seriesTabFragment = new SeriesTabFragment();
        seriesTabFragment.setArguments(bundle);
        return seriesTabFragment;
    }

    @Override // android.support.v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.getActiveLiveStreamCategoryId = getArguments().getString("");
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [?, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate((int) R.layout.fragment_live_streams, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        this.SharedPreferencesSort = getActivity().getSharedPreferences(AppConst.LOGIN_PREF_SORT_SERIES, 0);
        this.SharedPreferencesSortEditor = this.SharedPreferencesSort.edit();
        if (this.SharedPreferencesSort.getString(AppConst.LOGIN_PREF_SORT, "").equals("")) {
            this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, AppConst.PASSWORD_UNSET);
            this.SharedPreferencesSortEditor.commit();
        }
        ActivityCompat.invalidateOptionsMenu(getActivity());
        setHasOptionsMenu(true);
        setToolbarLogoImagewithSearchView();
        this.pref = getActivity().getSharedPreferences("listgridview", 0);
        this.editor = this.pref.edit();
        AppConst.LIVE_FLAG = this.pref.getInt(AppConst.LIVE_STREAM, 0);
        if (AppConst.LIVE_FLAG == 1) {
            initialize();
        } else {
            initialize1();
        }
        if (this.getActiveLiveStreamCategoryId != null && this.getActiveLiveStreamCategoryId.equals("-1")) {
            getFavourites();
        }
        return inflate;
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
        if (itemId == R.id.action_logout1 && this.context != null) {
            new AlertDialog.Builder(this.context, R.style.AlertDialogCustom).setTitle(getResources().getString(R.string.logout_title)).setMessage(getResources().getString(R.string.logout_message)).setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.fragment.SeriesTabFragment.AnonymousClass2 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(SeriesTabFragment.this.context);
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.fragment.SeriesTabFragment.AnonymousClass1 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
        if (itemId == R.id.action_search || itemId == R.id.menu_load_channels_vod1 || itemId == R.id.menu_load_tv_guide1) {
            return true;
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
            showSortPopup(getActivity());
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
            radioButton2.setVisibility(8);
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
                /* class com.nst.yourname.view.fragment.SeriesTabFragment.AnonymousClass3 */

                public void onClick(View view) {
                    SeriesTabFragment.this.changeSortPopUp.dismiss();
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.fragment.SeriesTabFragment.AnonymousClass4 */

                public void onClick(View view) {
                    RadioButton radioButton = (RadioButton) inflate.findViewById(radioGroup.getCheckedRadioButtonId());
                    if (radioButton.getText().toString().equals(SeriesTabFragment.this.getResources().getString(R.string.sort_last_added))) {
                        SeriesTabFragment.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "1");
                        SeriesTabFragment.this.SharedPreferencesSortEditor.commit();
                    } else if (radioButton.getText().toString().equals(SeriesTabFragment.this.getResources().getString(R.string.sort_atoz))) {
                        SeriesTabFragment.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "2");
                        SeriesTabFragment.this.SharedPreferencesSortEditor.commit();
                    } else if (radioButton.getText().toString().equals(SeriesTabFragment.this.getResources().getString(R.string.sort_ztoa))) {
                        SeriesTabFragment.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "3");
                        SeriesTabFragment.this.SharedPreferencesSortEditor.commit();
                    } else {
                        SeriesTabFragment.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, AppConst.PASSWORD_UNSET);
                        SeriesTabFragment.this.SharedPreferencesSortEditor.commit();
                    }
                    SharedPreferences unused = SeriesTabFragment.this.pref = SeriesTabFragment.this.getActivity().getSharedPreferences("listgridview", 0);
                    SharedPreferences.Editor unused2 = SeriesTabFragment.this.editor = SeriesTabFragment.this.pref.edit();
                    AppConst.LIVE_FLAG = SeriesTabFragment.this.pref.getInt(AppConst.LIVE_STREAM, 0);
                    if (AppConst.LIVE_FLAG == 1) {
                        SeriesTabFragment.this.initialize();
                    } else {
                        SeriesTabFragment.this.initialize1();
                    }
                    SeriesTabFragment.this.changeSortPopUp.dismiss();
                }
            });
        } catch (Exception unused) {
        }
    }

    public void initialize() {
        this.context = getContext();
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        if (this.myRecyclerView != null && this.context != null) {
            this.myRecyclerView.setHasFixedSize(true);
            this.layoutManager = new GridLayoutManager(getContext(), Utils.getNumberOfColumns(this.context) + 1);
            this.myRecyclerView.setLayoutManager(this.layoutManager);
            this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
            this.loginPreferencesSharedPref = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
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
            this.loginPreferencesSharedPref = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            this.loginPreferencesSharedPref.getString("username", "");
            this.loginPreferencesSharedPref.getString("password", "");
            setUpdatabaseResult();
        }
    }

    private void setUpdatabaseResult() {
        atStart();
        if (this.context != null) {
            new LiveStreamDBHandler(this.context);
            SeriesStreamsDatabaseHandler seriesStreamsDatabaseHandler = new SeriesStreamsDatabaseHandler(this.context);
            this.categoryWithPasword = new ArrayList<>();
            this.liveListDetailUnlcked = new ArrayList<>();
            this.liveListDetailUnlckedDetail = new ArrayList<>();
            this.liveListDetailAvailable = new ArrayList<>();
            this.liveListDetailAvailableNew = new ArrayList<>();
            this.liveListDetail = new ArrayList<>();
            ArrayList<SeriesDBModel> allSeriesStreamsWithCategoryId = seriesStreamsDatabaseHandler.getAllSeriesStreamsWithCategoryId(this.getActiveLiveStreamCategoryId);
            this.liveListDetailAvailable = allSeriesStreamsWithCategoryId;
            if (this.liveListDetailAvailable == null || this.myRecyclerView == null || this.liveListDetailAvailable.size() == 0) {
                onFinish();
                return;
            }
            onFinish();
            this.liveListDetailAvailable = allSeriesStreamsWithCategoryId;
            this.seriesStreamsAdapter = new SeriesStreamsAdapter(this.liveListDetailAvailable, getContext());
            this.myRecyclerView.setAdapter(this.seriesStreamsAdapter);
            this.seriesStreamsAdapter.notifyDataSetChanged();
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

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    public void getFavourites() {
        this.favouriteStreams.clear();
        if (this.myRecyclerView != null) {
            this.myRecyclerView.setAdapter(this.seriesStreamsAdapter);
        }
        if (this.context != null) {
            this.database = new DatabaseHandler(this.context);
            Iterator<FavouriteDBModel> it = this.database.getAllFavourites("series", SharepreferenceDBHandler.getUserID(this.context)).iterator();
            while (it.hasNext()) {
                SeriesDBModel seriesStreamsWithSeriesId = new SeriesStreamsDatabaseHandler(this.context).getSeriesStreamsWithSeriesId(String.valueOf(it.next().getStreamID()));
                if (seriesStreamsWithSeriesId != null) {
                    this.favouriteStreams.add(seriesStreamsWithSeriesId);
                }
            }
            if (!(this.myRecyclerView == null || this.favouriteStreams == null || this.favouriteStreams.size() == 0)) {
                onFinish();
                this.seriesStreamsAdapter = new SeriesStreamsAdapter(this.favouriteStreams, getContext());
                this.myRecyclerView.setAdapter(this.seriesStreamsAdapter);
                this.seriesStreamsAdapter.notifyDataSetChanged();
                this.tvNoStream.setVisibility(4);
            }
            if (this.tvNoStream != null && this.favouriteStreams != null && this.favouriteStreams.size() == 0) {
                onFinish();
                if (this.myRecyclerView != null) {
                    this.myRecyclerView.setAdapter(this.seriesStreamsAdapter);
                }
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
}
