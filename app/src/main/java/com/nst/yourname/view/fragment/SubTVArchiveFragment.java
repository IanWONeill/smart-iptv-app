package com.nst.yourname.view.fragment;

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
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.callback.LiveStreamCategoriesCallback;
import com.nst.yourname.model.callback.LiveStreamsCallback;
import com.nst.yourname.model.callback.LiveStreamsEpgCallback;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.pojo.EpgListingPojo;
import com.nst.yourname.model.pojo.XMLTVProgrammePojo;
import com.nst.yourname.view.activity.NewDashboardActivity;
import com.nst.yourname.view.activity.SettingsActivity;
import com.nst.yourname.view.adapter.SubTVArchiveAdapter;
import com.nst.yourname.view.interfaces.LiveStreamsInterface;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SubTVArchiveFragment extends Fragment implements LiveStreamsInterface, NavigationView.OnNavigationItemSelectedListener {
    public static final String ACTIVE_LIVE_STREAM_CATEGORY_ID = "";
    public static final String ACTIVE_LIVE_STREAM_CHANNEL_ID = "";
    public static final String ACTIVE_LIVE_STREAM_ICON = "";
    public static final String ACTIVE_LIVE_STREAM_NAME = "";
    public static final String ACTIVE_LIVE_STREAM_NUM = "";
    public static final String LIVE_STREAMS_EPG = "";
    private SubTVArchiveAdapter LiveStreamsEpgAdapter;
    int actionBarHeight;
    public Context context;
    private List<EpgListingPojo> dataSet;
    DatabaseHandler database;
    private ArrayList<LiveStreamsCallback> favouriteStreams = new ArrayList<>();
    private String getActiveLiveStreamCategoryId;
    private String getActiveLiveStreamChannelDuration;
    private String getActiveLiveStreamChannelId;
    private String getActiveLiveStreamIcon;
    private String getActiveLiveStreamId;
    private String getActiveLiveStreamName;
    private String getActiveLiveStreamNum;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences loginPreferencesSharedPref;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    SearchView searchView;
    private Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    TypedValue f39tv;
    @BindView(R.id.tv_no_record_found)
    TextView tvNoRecordFound;
    @BindView(R.id.tv_noStream)
    TextView tvNoStream;
    Unbinder unbinder;

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void atStart() {
    }

    @Override // com.nst.yourname.view.interfaces.LiveStreamsInterface
    public void liveStreamCategories(List<LiveStreamCategoriesCallback> list) {
    }

    @Override // com.nst.yourname.view.interfaces.LiveStreamsInterface
    public void liveStreams(List<LiveStreamsCallback> list, ArrayList<FavouriteDBModel> arrayList) {
    }

    @Override // com.nst.yourname.view.interfaces.LiveStreamsInterface
    public void liveStreamsEpg(LiveStreamsEpgCallback liveStreamsEpgCallback, TextView textView, TextView textView2) {
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFinish() {
    }

    @Override // android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    public static SubTVArchiveFragment newInstance(String str, ArrayList<XMLTVProgrammePojo> arrayList, String str2, String str3, String str4, String str5, String str6, String str7) {
        Bundle bundle = new Bundle();
        bundle.putString("ACTIVE_LIVE_STREAM_CATEGORY_ID", str);
        bundle.putString("ACTIVE_LIVE_STREAM_ID", str2);
        bundle.putString("ACTIVE_LIVE_STREAM_NUM", str3);
        bundle.putString("ACTIVE_LIVE_STREAM_NAME", str4);
        bundle.putString("ACTIVE_LIVE_STREAM_ICON", str5);
        bundle.putString("ACTIVE_LIVE_STREAM_CHANNEL_ID", str6);
        bundle.putString("ACTIVE_LIVE_STREAM_CHANNEL_DURATION", str7);
        bundle.putSerializable("LIVE_STREAMS_EPG", arrayList);
        SubTVArchiveFragment subTVArchiveFragment = new SubTVArchiveFragment();
        subTVArchiveFragment.setArguments(bundle);
        return subTVArchiveFragment;
    }

    @Override // android.support.v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void setEpgtoAdapter() {
        this.getActiveLiveStreamCategoryId = getArguments().getString("ACTIVE_LIVE_STREAM_CATEGORY_ID");
        this.getActiveLiveStreamId = getArguments().getString("ACTIVE_LIVE_STREAM_ID");
        this.getActiveLiveStreamNum = getArguments().getString("ACTIVE_LIVE_STREAM_NUM");
        this.getActiveLiveStreamName = getArguments().getString("ACTIVE_LIVE_STREAM_NAME");
        this.getActiveLiveStreamIcon = getArguments().getString("ACTIVE_LIVE_STREAM_ICON");
        this.getActiveLiveStreamChannelId = getArguments().getString("ACTIVE_LIVE_STREAM_CHANNEL_ID");
        this.getActiveLiveStreamChannelDuration = getArguments().getString("ACTIVE_LIVE_STREAM_CHANNEL_DURATION");
        Serializable serializable = getArguments().getSerializable("LIVE_STREAMS_EPG");
        if (this.getActiveLiveStreamCategoryId != null && serializable != null) {
            ArrayList arrayList = (ArrayList) serializable;
            ArrayList arrayList2 = new ArrayList();
            for (int i = 0; i < arrayList.size(); i++) {
                String start = ((XMLTVProgrammePojo) arrayList.get(i)).getStart();
                String[] split = start.split("\\s+");
                Long.parseLong(((XMLTVProgrammePojo) arrayList.get(i)).getStartTimeStamp());
                Long.parseLong(((XMLTVProgrammePojo) arrayList.get(i)).getEndTimeStamp());
                ((XMLTVProgrammePojo) arrayList.get(i)).getStop();
                start.split("\\s+");
                ((XMLTVProgrammePojo) arrayList.get(i)).getTitle();
                ((XMLTVProgrammePojo) arrayList.get(i)).getDesc();
                String str = "";
                try {
                    str = new SimpleDateFormat("dd MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(split[0]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (str != null && str.equals(this.getActiveLiveStreamCategoryId)) {
                    arrayList2.add(arrayList.get(i));
                }
            }
            this.LiveStreamsEpgAdapter = new SubTVArchiveAdapter(arrayList2, 0, false, this.getActiveLiveStreamCategoryId, this.getActiveLiveStreamId, this.getActiveLiveStreamNum, this.getActiveLiveStreamName, this.getActiveLiveStreamIcon, this.getActiveLiveStreamChannelId, this.getActiveLiveStreamChannelDuration, getContext());
            this.myRecyclerView.setAdapter(this.LiveStreamsEpgAdapter);
            initialize(1);
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
        View inflate = layoutInflater.inflate((int) R.layout.fragment_epg, viewGroup, false);
        this.unbinder = ButterKnife.bind(this, inflate);
        ActivityCompat.invalidateOptionsMenu(getActivity());
        setHasOptionsMenu(true);
        setToolbarLogoImagewithSearchView();
        setEpgtoAdapter();
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
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(this.context, NewDashboardActivity.class));
        }
        if (itemId == R.id.nav_settings) {
            startActivity(new Intent(this.context, SettingsActivity.class));
        }
        if (itemId != R.id.action_logout1 || this.context == null) {
            return false;
        }
        new AlertDialog.Builder(this.context, R.style.AlertDialogCustom).setTitle(getResources().getString(R.string.logout_title)).setMessage(getResources().getString(R.string.logout_message)).setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            /* class com.nst.yourname.view.fragment.SubTVArchiveFragment.AnonymousClass2 */

            public void onClick(DialogInterface dialogInterface, int i) {
                Utils.logoutUser(SubTVArchiveFragment.this.context);
            }
        }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            /* class com.nst.yourname.view.fragment.SubTVArchiveFragment.AnonymousClass1 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
        return false;
    }

    private void initialize(int i) {
        this.context = getContext();
        if (this.myRecyclerView != null && this.context != null) {
            this.myRecyclerView.setHasFixedSize(true);
            this.layoutManager = new LinearLayoutManager(getContext());
            this.myRecyclerView.setLayoutManager(this.layoutManager);
            this.myRecyclerView.smoothScrollToPosition(i);
            this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFailed(String str) {
        Utils.showToast(this.context, AppConst.NETWORK_ERROR_OCCURED);
    }
}
