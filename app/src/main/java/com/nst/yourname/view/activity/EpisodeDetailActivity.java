package com.nst.yourname.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.MediaRouteButton;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.CastStateListener;
import com.google.android.gms.cast.framework.IntroductoryOverlay;
import com.google.android.gms.cast.framework.SessionManager;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.gson.JsonElement;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.AdapterSectionRecycler;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.EpisodesUsingSinglton;
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.callback.GetEpisdoeDetailsCallback;
import com.nst.yourname.model.callback.SeasonsDetailCallback;
import com.nst.yourname.model.callback.SeriesDBModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.ExternalPlayerDataBase;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.presenter.SeriesPresenter;
import com.nst.yourname.presenter.XMLTVPresenter;
import com.nst.yourname.view.adapter.EpisodeDetailAdapter;
import com.nst.yourname.view.adapter.SeasonsAdapter;
import com.nst.yourname.view.adapter.VodSubCatAdpaterNew;
import com.nst.yourname.view.interfaces.SeriesInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("ALL")
public class EpisodeDetailActivity extends AppCompatActivity implements View.OnClickListener, SeriesInterface, EpisodeDetailAdapter.EpisodeItemClickListener {
    static final boolean $assertionsDisabled = false;
    private static final int DOWNLOAD_REQUEST_CODE = 101;
    static ProgressBar pbPagingLoader1;
    private static ArrayList<LiveStreamCategoryIdDBModel> subCategoryList = new ArrayList<>();
    private static ArrayList<LiveStreamCategoryIdDBModel> subCategoryListFinal = new ArrayList<>();
    private static ArrayList<LiveStreamCategoryIdDBModel> subCategoryListFinal_menu = new ArrayList<>();
    private SharedPreferences SharedPreferencesSort;
    public SharedPreferences.Editor SharedPreferencesSortEditor;
    int actionBarHeight;
    @BindView(R.id.main_layout)
    LinearLayout activityLogin;
    private AdapterSectionRecycler adapterRecycler;
    AlertDialog alertDialog;
    @BindView(R.id.appbar_toolbar)
    AppBarLayout appbarToolbar;
    ArrayList<LiveStreamCategoryIdDBModel> categoriesList;
    public PopupWindow changeSortPopUp;
    TextView clientNameTv;
    public Context context;
    int count = 0;
    DatabaseHandler database;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive = new DatabaseUpdatedStatusDBModel();
    String downloadMoviename = "";
    String downloadcontainerExtension = "mp4";
    int downloadstreamId = 0;
    private SharedPreferences.Editor editor;
    private ArrayList<GetEpisdoeDetailsCallback> episdoeDetailsCallbacksList = new ArrayList<>();
    private final List<GetEpisdoeDetailsCallback> episdoeDetailsCallbacksListDefault_seasons = new ArrayList();
    private ArrayList<GetEpisdoeDetailsCallback> episdoeDetailsCallbacksListRefined = new ArrayList<>();
    private List<GetEpisdoeDetailsCallback> episdoeDetailsCallbacksListRefined_seasons = new ArrayList();
    private List<GetEpisdoeDetailsCallback> episdoeDetailsList = new ArrayList();
    private List<GetEpisdoeDetailsCallback> episdoeDetailsListFinal = new ArrayList();
    String episodeCover = "";
    public EpisodeDetailAdapter episodeDetailAdapter;
    private ArrayList<LiveStreamsDBModel> favouriteStreams = new ArrayList<>();
    private String getActiveLiveStreamCategoryId = "";
    private String getActiveLiveStreamCategoryName = "";
    GridLayoutManager gridLayoutManager;
    boolean isEpisode = true;
    private boolean isSubcaetgroy = false;
    boolean isSubcaetgroyAvail = false;
    private RecyclerView.LayoutManager layoutManager;
    private LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences loginPreferencesSharedPref;
    @BindView(R.id.logo)
    ImageView logo;
    private SeasonsAdapter mAdapter;
    private CastContext mCastContext;
    public CastSession mCastSession;
    private CastStateListener mCastStateListener;
    private IntroductoryOverlay mIntroductoryOverlay;
    private RecyclerView.LayoutManager mLayoutManager;
    private MediaRouteButton mMediaRouteButton;
    private MenuItem mQueueMenuItem;
    private SessionManager mSessionManager;
    private final SessionManagerListener<CastSession> mSessionManagerListener = new MySessionManagerListener();
    private MenuItem mediaRouteMenuItem;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    private SharedPreferences pref;
    private ProgressDialog progressDialog;
    @BindView(R.id.rl_sub_cat)
    RelativeLayout rl_sub_cat;
    SearchView searchView;
    private String seasonCaverBig = "";
    private int seasonNumber = -1;
    private List<SeriesDBModel> seasonsDetailCallbacks = new ArrayList();
    private ArrayList<SeasonsDetailCallback> seasonsDetailCallbacks1 = new ArrayList<>();
    private String seriesCover = "";
    private String seriesId = "";
    private String seriesName = "";
    @BindView(R.id.tv_settings)
    TextView seriesNameTV;
    private SeriesPresenter seriesPresenter;
    private String series_categoryID = "";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    TypedValue f5tv;
    @BindView(R.id.tv_no_record_found)
    TextView tvNoRecordFound;
    @BindView(R.id.tv_noStream)
    TextView tvNoStream;
    @BindView(R.id.tv_view_provider)
    TextView tvViewProvider;
    private String userName = "";
    private String userPassword = "";
    private VodSubCatAdpaterNew vodSubCatAdpaterNew;
    private XMLTVPresenter xmltvPresenter;

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void atStart() {
    }

    @Override // com.nst.yourname.view.adapter.EpisodeDetailAdapter.EpisodeItemClickListener
    public void episodeitemClicked() {
    }

    public void onClick(View view) {
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFailed(String str) {
    }

    @Override // com.nst.yourname.view.interfaces.SeriesInterface
    public void seriesError(String str) {
    }

    private class MySessionManagerListener implements SessionManagerListener<CastSession> {
        public void onSessionEnding(CastSession castSession) {
        }

        public void onSessionResumeFailed(CastSession castSession, int i) {
        }

        public void onSessionResuming(CastSession castSession, String str) {
        }

        public void onSessionStartFailed(CastSession castSession, int i) {
        }

        public void onSessionStarting(CastSession castSession) {
        }

        public void onSessionSuspended(CastSession castSession, int i) {
        }

        private MySessionManagerListener() {
        }

        public void onSessionEnded(CastSession castSession, int i) {
            if (EpisodeDetailActivity.this.mCastSession != null) {
                if (castSession == EpisodeDetailActivity.this.mCastSession) {
                    CastSession unused = EpisodeDetailActivity.this.mCastSession = null;
                }
                EpisodeDetailActivity.this.invalidateOptionsMenu();
            }
        }

        public void onSessionResumed(CastSession castSession, boolean z) {
            if (castSession != null) {
                CastSession unused = EpisodeDetailActivity.this.mCastSession = castSession;
                EpisodeDetailActivity.this.invalidateOptionsMenu();
            }
        }

        public void onSessionStarted(CastSession castSession, String str) {
            if (castSession != null) {
                CastSession unused = EpisodeDetailActivity.this.mCastSession = castSession;
                EpisodeDetailActivity.this.invalidateOptionsMenu();
            }
        }
    }

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_episode_detail);
        ButterKnife.bind(this);
        getWindow().setFlags(1024, 1024);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        try {
            this.mSessionManager = CastContext.getSharedInstance(this).getSessionManager();
            this.mCastContext = CastContext.getSharedInstance(this);
            this.mCastStateListener = new CastStateListener() {
                /* class com.nst.yourname.view.activity.EpisodeDetailActivity.AnonymousClass1 */

                @Override // com.google.android.gms.cast.framework.CastStateListener
                public void onCastStateChanged(int i) {
                }
            };
        } catch (Exception e) {
            Log.e("", "" + e);
        }
        if (this.appbarToolbar != null) {
            this.appbarToolbar.setBackground(getResources().getDrawable(R.drawable.vod_backgound));
        }
        this.SharedPreferencesSort = getSharedPreferences(AppConst.LOGIN_PREF_SORT_EPISODES, 0);
        this.SharedPreferencesSortEditor = this.SharedPreferencesSort.edit();
        if (this.SharedPreferencesSort.getString(AppConst.LOGIN_PREF_SORT, "").equals("")) {
            this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, AppConst.PASSWORD_UNSET);
            this.SharedPreferencesSortEditor.apply();
        }
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.EpisodeDetailActivity.AnonymousClass2 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(EpisodeDetailActivity.this.context);
            }
        });
        this.count = new ExternalPlayerDataBase(this).getExternalPlayercount();
        setGridView();
        initializeSideDrawer();
        initializeV();
    }

    private void setGridView() {
        if (this.myRecyclerView != null) {
            this.context = this;
            this.myRecyclerView.setHasFixedSize(true);
            this.layoutManager = new GridLayoutManager(this.context, Utils.getNumberOfColumns(this.context) + 1);
            this.myRecyclerView.setLayoutManager(this.layoutManager);
            this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }

    private void initializeSideDrawer() {
        changeStatusBarColor();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    private void initializeV() {
        this.context = this;
        this.seriesPresenter = new SeriesPresenter(this.context, this);
        Intent intent = getIntent();
        if (intent != null) {
            this.seriesId = intent.getStringExtra(AppConst.SERIES_SERIES_ID);
            this.seriesCover = intent.getStringExtra(AppConst.SERIES_COVER);
            this.series_categoryID = intent.getStringExtra(AppConst.SERIES_CATEGORY_ID);
            this.seriesName = intent.getStringExtra(AppConst.SERIES_NAME);
            this.seasonCaverBig = intent.getStringExtra(AppConst.SEASON_COVER_BIG);
            this.seasonNumber = intent.getIntExtra(AppConst.SEASON_NUMBER, -1);
            this.episdoeDetailsList = EpisodesUsingSinglton.getInstance().getEpisodeList();
            if (!(this.seriesNameTV == null || this.seriesName == null || this.seriesName.isEmpty())) {
                this.seriesNameTV.setText(this.seriesName);
                this.seriesNameTV.setSelected(true);
            }
            if (this.seasonNumber != -1 && this.episdoeDetailsList != null && this.episdoeDetailsList.size() > 0) {
                if (this.pbLoader != null) {
                    this.pbLoader.setVisibility(4);
                }
                this.isEpisode = false;
                setEpisode(this.episdoeDetailsList, this.seasonNumber, this.seasonCaverBig);
            } else if (this.seriesId == null) {
                onFinish();
                if (this.tvNoStream != null) {
                    this.tvNoStream.setVisibility(0);
                }
            }
        }
        this.loginPreferencesAfterLogin = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        String string = this.loginPreferencesAfterLogin.getString("username", "");
        String string2 = this.loginPreferencesAfterLogin.getString("password", "");
        if (this.seriesId != null && !this.seriesId.isEmpty() && this.seriesPresenter != null && string != null && !string.isEmpty() && string2 != null && !string2.isEmpty()) {
            this.isEpisode = true;
            this.seriesPresenter.getSeriesEpisode(string, string2, this.seriesId);
        }
    }

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.clear();
    }

    private void setEpisode(List<GetEpisdoeDetailsCallback> list, int i, String str) {
        if (this.episdoeDetailsListFinal != null) {
            this.episdoeDetailsListFinal.clear();
        }
        for (GetEpisdoeDetailsCallback getEpisdoeDetailsCallback : list) {
            if (getEpisdoeDetailsCallback.getSeasonNumber().intValue() == i) {
                this.episdoeDetailsListFinal.add(getEpisdoeDetailsCallback);
                this.episdoeDetailsCallbacksListDefault_seasons.add(getEpisdoeDetailsCallback);
            }
        }
        setSeasonsList();
    }

    public void setSeasonsList() {
        String string = this.SharedPreferencesSort.getString(AppConst.LOGIN_PREF_SORT, "");
        if (string.equals("1")) {
            AppConst.SORT_EPISODES = AppConst.SORT_EPISODES_LASTADDED;
            Collections.sort(this.episdoeDetailsListFinal, GetEpisdoeDetailsCallback.episodeComparator);
            this.episdoeDetailsCallbacksListRefined_seasons = this.episdoeDetailsListFinal;
        } else if (string.equals("2")) {
            AppConst.SORT_EPISODES = AppConst.SORT_EPISODES_A_TO_Z;
            Collections.sort(this.episdoeDetailsListFinal, GetEpisdoeDetailsCallback.episodeComparator);
            this.episdoeDetailsCallbacksListRefined_seasons = this.episdoeDetailsListFinal;
        } else if (string.equals("3")) {
            AppConst.SORT_EPISODES = AppConst.SORT_EPISODES_Z_TO_A;
            Collections.sort(this.episdoeDetailsListFinal, GetEpisdoeDetailsCallback.episodeComparator);
            this.episdoeDetailsCallbacksListRefined_seasons = this.episdoeDetailsListFinal;
        } else {
            this.episdoeDetailsCallbacksListRefined_seasons = this.episdoeDetailsCallbacksListDefault_seasons;
        }
        if (this.episdoeDetailsCallbacksListRefined_seasons == null || this.myRecyclerView == null || this.episdoeDetailsCallbacksListRefined_seasons.size() == 0) {
            onFinish();
            if (this.tvNoStream != null) {
                this.tvNoStream.setVisibility(0);
                return;
            }
            return;
        }
        onFinish();
        if (this.count > 0) {
            Utils.showToastLong(this.context, getResources().getString(R.string.use_long_press));
        }
        EpisodesUsingSinglton.getInstance().setEpisodeList(this.episdoeDetailsCallbacksListRefined_seasons);
        this.episodeDetailAdapter = new EpisodeDetailAdapter(this.episdoeDetailsCallbacksListRefined_seasons, this.context, this.seasonCaverBig, null, this, this.seasonsDetailCallbacks);
        this.myRecyclerView.setAdapter(this.episodeDetailAdapter);
        this.episodeDetailAdapter.notifyDataSetChanged();
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

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFinish() {
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(4);
        }
    }


    /* JADX WARNING: Removed duplicated region for block: B:802:0x145a A[Catch:{ JSONException -> 0x1607 }] */
    @Override // com.nst.yourname.view.interfaces.SeriesInterface
    public void getSeriesEpisodeInfo(JsonElement jsonElement) {
        //ToDo: initialized...
        JSONArray jSONArray = null;
        JSONObject jSONObject;
        JSONArray jSONArray2;
        int length;
        int i;
        JSONArray jSONArray3;
        JSONArray jSONArray4;
        JSONObject jSONObject2;
        Iterator<String> keys;
        JSONObject jSONObject3;
        Iterator<String> keys2;
        if (jsonElement != null) {
            try {
                JSONObject jSONObject4 = new JSONObject(jsonElement.toString());
                String string = jSONObject4.getString("seasons");
                if (string.equals("null")) {
                    JSONObject jSONObject5 = jSONObject4.getJSONObject("episodes") != null ? jSONObject4.getJSONObject("episodes") : null;
                    this.episdoeDetailsCallbacksList.clear();
                    if (jSONObject5 != null) {
                        Iterator<String> keys3 = jSONObject5.keys();
                        while (keys3.hasNext()) {
                            String next = keys3.next();
                            if (jSONObject5.get(next) instanceof JSONArray) {
                                JSONArray jSONArray5 = new JSONArray(jSONObject5.get(next).toString());
                                int length2 = jSONArray5.length();
                                int i2 = 0;
                                boolean z = true;
                                while (i2 < length2) {
                                    JSONObject jSONObject6 = jSONArray5.getJSONObject(i2);
                                    GetEpisdoeDetailsCallback getEpisdoeDetailsCallback = new GetEpisdoeDetailsCallback();
                                    SeasonsDetailCallback seasonsDetailCallback = new SeasonsDetailCallback();
                                    if (jSONObject6.getString("id") == null || jSONObject6.getString("id").isEmpty()) {
                                        getEpisdoeDetailsCallback.setId("");
                                    } else {
                                        getEpisdoeDetailsCallback.setId(jSONObject6.getString("id"));
                                    }
                                    if (jSONObject6.getInt("season") != -1) {
                                        getEpisdoeDetailsCallback.setSeasonNumber(Integer.valueOf(jSONObject6.getInt("season")));
                                    } else {
                                        getEpisdoeDetailsCallback.setSeasonNumber(-1);
                                    }
                                    if (z) {
                                        if (jSONObject6.getInt("season") == -1 || jSONObject6.getInt("season") == 0) {
                                            seasonsDetailCallback.setSeasonNumber(-1);
                                        } else {
                                            seasonsDetailCallback.setSeasonNumber(Integer.valueOf(jSONObject6.getInt("season")));
                                        }
                                        seasonsDetailCallback.setAirDate("");
                                        seasonsDetailCallback.setEpisodeCount(-1);
                                        seasonsDetailCallback.setId(-1);
                                        seasonsDetailCallback.setName("");
                                        seasonsDetailCallback.setOverview("");
                                        seasonsDetailCallback.setCoverBig("");
                                        seasonsDetailCallback.setCoverBig("");
                                        this.seasonsDetailCallbacks1.add(seasonsDetailCallback);
                                        z = false;
                                    }
                                    getEpisdoeDetailsCallback.setImage(this.seriesCover);
                                    if (jSONObject6.getString("title") == null || jSONObject6.getString("title").isEmpty()) {
                                        getEpisdoeDetailsCallback.setTitle("");
                                    } else {
                                        getEpisdoeDetailsCallback.setTitle(jSONObject6.getString("title"));
                                    }
                                    if (jSONObject6.getString("direct_source") == null || jSONObject6.getString("direct_source").isEmpty()) {
                                        getEpisdoeDetailsCallback.setDirectSource("");
                                    } else {
                                        getEpisdoeDetailsCallback.setDirectSource(jSONObject6.getString("direct_source"));
                                    }
                                    if (jSONObject6.getString("added") == null || jSONObject6.getString("added").isEmpty()) {
                                        getEpisdoeDetailsCallback.setAdded("");
                                    } else {
                                        getEpisdoeDetailsCallback.setAdded(jSONObject6.getString("added"));
                                    }
                                    if (jSONObject6.getString("custom_sid") == null || jSONObject6.getString("custom_sid").isEmpty()) {
                                        getEpisdoeDetailsCallback.setCustomSid("");
                                    } else {
                                        getEpisdoeDetailsCallback.setCustomSid(jSONObject6.getString("custom_sid"));
                                    }
                                    if (jSONObject6.getString("container_extension") == null || jSONObject6.getString("container_extension").isEmpty()) {
                                        getEpisdoeDetailsCallback.setContainerExtension("");
                                    } else {
                                        getEpisdoeDetailsCallback.setContainerExtension(jSONObject6.getString("container_extension"));
                                    }
                                    if (this.series_categoryID != null) {
                                        getEpisdoeDetailsCallback.setCategoryId(this.series_categoryID);
                                    }
                                    try {
                                        if (jSONObject6.getJSONObject("info") == null || jSONObject6.getJSONObject("info").getString("movie_image") == null) {
                                            getEpisdoeDetailsCallback.setMovieImage("");
                                            this.episdoeDetailsCallbacksList.add(getEpisdoeDetailsCallback);
                                            i2++;
                                        } else {
                                            getEpisdoeDetailsCallback.setMovieImage(jSONObject6.getJSONObject("info").getString("movie_image"));
                                            this.episdoeDetailsCallbacksList.add(getEpisdoeDetailsCallback);
                                            i2++;
                                        }
                                    } catch (Exception unused) {
                                        getEpisdoeDetailsCallback.setMovieImage("");
                                    }
                                }
                            }
                        }
                    }
                    try {
                        JSONObject jSONObject7 = new JSONObject(jsonElement.toString());
                        JSONObject jSONObject8 = jSONObject7.getJSONObject("episodes") == null ? jSONObject7.getJSONObject("seasons") : null;
                        jSONObject3 = jSONObject7.getJSONObject("episodes") == null ? jSONObject7.getJSONObject("episodes") : null;
                        this.episdoeDetailsCallbacksList.clear();
                        keys2 = jSONObject8.keys();
                        while (keys2.hasNext()) {
                            String next2 = keys2.next();
                            if (jSONObject8.get(next2) instanceof JSONObject) {
                                SeasonsDetailCallback seasonsDetailCallback2 = new SeasonsDetailCallback();
                                if (((JSONObject) jSONObject8.get(next2)).get("air_date").toString().equals("null")) {
                                    seasonsDetailCallback2.setAirDate("");
                                } else if (((JSONObject) jSONObject8.get(next2)).get("air_date") == null || ((String) ((JSONObject) jSONObject8.get(next2)).get("air_date")).isEmpty()) {
                                    seasonsDetailCallback2.setAirDate("");
                                } else {
                                    seasonsDetailCallback2.setAirDate((String) ((JSONObject) jSONObject8.get(next2)).get("air_date"));
                                }
                                if (((Integer) ((JSONObject) jSONObject8.get(next2)).get("episode_count")) == null || ((Integer) ((JSONObject) jSONObject8.get(next2)).get("episode_count")).intValue() == -1 || ((Integer) ((JSONObject) jSONObject8.get(next2)).get("episode_count")).intValue() == 0) {
                                    seasonsDetailCallback2.setEpisodeCount(-1);
                                } else {
                                    seasonsDetailCallback2.setEpisodeCount((Integer) ((JSONObject) jSONObject8.get(next2)).get("episode_count"));
                                }
                                if (((Integer) ((JSONObject) jSONObject8.get(next2)).get("id")) == null || ((Integer) ((JSONObject) jSONObject8.get(next2)).get("id")).intValue() == -1 || ((Integer) ((JSONObject) jSONObject8.get(next2)).get("id")).intValue() == 0) {
                                    seasonsDetailCallback2.setId(-1);
                                } else {
                                    seasonsDetailCallback2.setId((Integer) ((JSONObject) jSONObject8.get(next2)).get("id"));
                                }
                                if (((String) ((JSONObject) jSONObject8.get(next2)).get("name")) == null || ((String) ((JSONObject) jSONObject8.get(next2)).get("name")).isEmpty()) {
                                    seasonsDetailCallback2.setName("");
                                } else {
                                    seasonsDetailCallback2.setName((String) ((JSONObject) jSONObject8.get(next2)).get("name"));
                                }
                                if (((String) ((JSONObject) jSONObject8.get(next2)).get("overview")) == null || ((String) ((JSONObject) jSONObject8.get(next2)).get("overview")).isEmpty()) {
                                    seasonsDetailCallback2.setOverview("");
                                } else {
                                    seasonsDetailCallback2.setOverview((String) ((JSONObject) jSONObject8.get(next2)).get("overview"));
                                }
                                if (((Integer) ((JSONObject) jSONObject8.get(next2)).get(AppConst.SEASON_NUMBER)) == null || ((Integer) ((JSONObject) jSONObject8.get(next2)).get(AppConst.SEASON_NUMBER)).intValue() == -1 || ((Integer) ((JSONObject) jSONObject8.get(next2)).get(AppConst.SEASON_NUMBER)).intValue() == 0) {
                                    seasonsDetailCallback2.setSeasonNumber(-1);
                                } else {
                                    seasonsDetailCallback2.setSeasonNumber((Integer) ((JSONObject) jSONObject8.get(next2)).get(AppConst.SEASON_NUMBER));
                                }
                                if (((JSONObject) jSONObject8.get(next2)).getString("cover") == null || ((JSONObject) jSONObject8.get(next2)).getString("cover").isEmpty()) {
                                    seasonsDetailCallback2.setCover(this.seriesCover);
                                } else {
                                    this.episodeCover = ((JSONObject) jSONObject8.get(next2)).getString("cover");
                                    seasonsDetailCallback2.setCover(this.episodeCover);
                                }
                                if (((JSONObject) jSONObject8.get(next2)).getString("cover_big") == null || ((JSONObject) jSONObject8.get(next2)).getString("cover_big").isEmpty()) {
                                    seasonsDetailCallback2.setCoverBig(this.seriesCover);
                                } else {
                                    this.episodeCover = ((JSONObject) jSONObject8.get(next2)).getString("cover_big");
                                    seasonsDetailCallback2.setCoverBig(this.episodeCover);
                                }
                                this.seasonsDetailCallbacks1.add(seasonsDetailCallback2);
                                this.seasonsDetailCallbacks1.add(seasonsDetailCallback2);
                            }
                        }
                        if (jSONObject3 != null) {
                            Iterator<String> keys4 = jSONObject3.keys();
                            while (keys4.hasNext()) {
                                String next3 = keys4.next();
                                if (jSONObject3.get(next3) instanceof JSONArray) {
                                    JSONArray jSONArray6 = new JSONArray(jSONObject3.get(next3).toString());
                                    for (int i3 = 0; i3 < jSONArray6.length(); i3++) {
                                        JSONObject jSONObject9 = jSONArray6.getJSONObject(i3);
                                        GetEpisdoeDetailsCallback getEpisdoeDetailsCallback2 = new GetEpisdoeDetailsCallback();
                                        if (!this.episodeCover.equals("")) {
                                            getEpisdoeDetailsCallback2.setImage(this.episodeCover);
                                        } else {
                                            getEpisdoeDetailsCallback2.setImage(this.seriesCover);
                                        }
                                        if (jSONObject9.getString("id") == null || jSONObject9.getString("id").isEmpty()) {
                                            getEpisdoeDetailsCallback2.setId("");
                                        } else {
                                            getEpisdoeDetailsCallback2.setId(jSONObject9.getString("id"));
                                        }
                                        if (jSONObject9.getInt("season") == -1 || jSONObject9.getInt("season") == 0) {
                                            getEpisdoeDetailsCallback2.setId("");
                                        } else {
                                            getEpisdoeDetailsCallback2.setSeasonNumber(Integer.valueOf(jSONObject9.getInt("season")));
                                        }
                                        if (jSONObject9.getString("title") == null || jSONObject9.getString("title").isEmpty()) {
                                            getEpisdoeDetailsCallback2.setTitle("");
                                        } else {
                                            getEpisdoeDetailsCallback2.setTitle(jSONObject9.getString("title"));
                                        }
                                        if (jSONObject9.getString("direct_source") == null || jSONObject9.getString("direct_source").isEmpty()) {
                                            getEpisdoeDetailsCallback2.setDirectSource("");
                                        } else {
                                            getEpisdoeDetailsCallback2.setDirectSource(jSONObject9.getString("direct_source"));
                                        }
                                        if (jSONObject9.getString("added") == null || jSONObject9.getString("added").isEmpty()) {
                                            getEpisdoeDetailsCallback2.setAdded("");
                                        } else {
                                            getEpisdoeDetailsCallback2.setAdded(jSONObject9.getString("added"));
                                        }
                                        if (jSONObject9.getString("custom_sid") == null || jSONObject9.getString("custom_sid").isEmpty()) {
                                            getEpisdoeDetailsCallback2.setCustomSid("");
                                        } else {
                                            getEpisdoeDetailsCallback2.setCustomSid(jSONObject9.getString("custom_sid"));
                                        }
                                        if (jSONObject9.getString("container_extension") == null || jSONObject9.getString("container_extension").isEmpty()) {
                                            getEpisdoeDetailsCallback2.setContainerExtension("");
                                        } else {
                                            getEpisdoeDetailsCallback2.setContainerExtension(jSONObject9.getString("container_extension"));
                                        }
                                        if (this.series_categoryID != null) {
                                            getEpisdoeDetailsCallback2.setCategoryId(this.series_categoryID);
                                        }
                                        try {
                                            if (jSONObject9.getJSONObject("info") == null || jSONObject9.getJSONObject("info").getString("movie_image") == null) {
                                                getEpisdoeDetailsCallback2.setMovieImage("");
                                            } else {
                                                getEpisdoeDetailsCallback2.setMovieImage(jSONObject9.getJSONObject("info").getString("movie_image"));
                                            }
                                        } catch (Exception unused2) {
                                            getEpisdoeDetailsCallback2.setMovieImage("");
                                        }
                                        this.episdoeDetailsCallbacksList.add(getEpisdoeDetailsCallback2);
                                    }
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONObject jSONObject10 = new JSONObject(jsonElement.toString());
                        jSONArray4 = jSONObject10.getJSONArray("seasons");
                        jSONObject2 = jSONObject10.getJSONObject("episodes") == null ? jSONObject10.getJSONObject("episodes") : null;
                        if (jSONObject2.equals(jSONArray4)) {
                            int length3 = jSONArray4.length();
                            this.episdoeDetailsCallbacksList.clear();
                            for (int i4 = 0; i4 < length3; i4++) {
                                SeasonsDetailCallback seasonsDetailCallback3 = new SeasonsDetailCallback();
                                ((JSONObject) jSONArray4.get(i4)).getString("air_date");
                                if (((JSONObject) jSONArray4.get(i4)).getString("air_date") == null || ((JSONObject) jSONArray4.get(i4)).getString("air_date").isEmpty()) {
                                    seasonsDetailCallback3.setAirDate("");
                                } else {
                                    seasonsDetailCallback3.setAirDate(((JSONObject) jSONArray4.get(i4)).getString("air_date"));
                                }
                                if (((JSONObject) jSONArray4.get(i4)).getString("episode_count") == null || Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt("episode_count")).intValue() == -1 || Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt("episode_count")).intValue() == 0) {
                                    seasonsDetailCallback3.setEpisodeCount(-1);
                                } else {
                                    seasonsDetailCallback3.setEpisodeCount(Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt("episode_count")));
                                }
                                if (Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt("id")) == null || Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt("id")).intValue() == -1 || Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt("id")).intValue() == 0) {
                                    seasonsDetailCallback3.setId(-1);
                                } else {
                                    seasonsDetailCallback3.setId(Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt("id")));
                                }
                                if (((JSONObject) jSONArray4.get(i4)).getString("name") == null || ((JSONObject) jSONArray4.get(i4)).getString("name").isEmpty()) {
                                    seasonsDetailCallback3.setName("");
                                } else {
                                    seasonsDetailCallback3.setName(((JSONObject) jSONArray4.get(i4)).getString("name"));
                                }
                                if (((JSONObject) jSONArray4.get(i4)).getString("overview") == null || ((JSONObject) jSONArray4.get(i4)).getString("overview").isEmpty()) {
                                    seasonsDetailCallback3.setOverview("");
                                } else {
                                    seasonsDetailCallback3.setOverview(((JSONObject) jSONArray4.get(i4)).getString("overview"));
                                }
                                if (Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt(AppConst.SEASON_NUMBER)) == null || Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt(AppConst.SEASON_NUMBER)).intValue() == -1 || Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt(AppConst.SEASON_NUMBER)).intValue() == 0) {
                                    seasonsDetailCallback3.setSeasonNumber(-1);
                                } else {
                                    seasonsDetailCallback3.setSeasonNumber(Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt(AppConst.SEASON_NUMBER)));
                                }
                                if (((JSONObject) jSONArray4.get(i4)).getString("cover") == null || ((JSONObject) jSONArray4.get(i4)).getString("cover").isEmpty()) {
                                    seasonsDetailCallback3.setCover(this.seriesCover);
                                } else {
                                    this.episodeCover = ((JSONObject) jSONArray4.get(i4)).getString("cover");
                                    seasonsDetailCallback3.setCover(this.episodeCover);
                                }
                                if (((JSONObject) jSONArray4.get(i4)).getString("cover_big") == null || ((JSONObject) jSONArray4.get(i4)).getString("cover_big").isEmpty()) {
                                    seasonsDetailCallback3.setCoverBig(this.seriesCover);
                                } else {
                                    this.episodeCover = ((JSONObject) jSONArray4.get(i4)).getString("cover_big");
                                    seasonsDetailCallback3.setCoverBig(this.episodeCover);
                                }
                                this.seasonsDetailCallbacks1.add(seasonsDetailCallback3);
                            }
                        }
                        if (jSONObject2.equals(jSONArray4) && jSONObject2 != null) {
                            keys = jSONObject2.keys();
                            while (keys.hasNext()) {
                                String next4 = keys.next();
                                if (jSONObject2.get(next4) instanceof JSONArray) {
                                    JSONArray jSONArray7 = new JSONArray(jSONObject2.get(next4).toString());
                                    int length4 = jSONArray7.length();
                                    for (int i5 = 0; i5 < length4; i5++) {
                                        JSONObject jSONObject11 = jSONArray7.getJSONObject(i5);
                                        GetEpisdoeDetailsCallback getEpisdoeDetailsCallback3 = new GetEpisdoeDetailsCallback();
                                        if (!this.episodeCover.equals("")) {
                                            getEpisdoeDetailsCallback3.setImage(this.episodeCover);
                                        } else {
                                            getEpisdoeDetailsCallback3.setImage(this.seriesCover);
                                        }
                                        if (jSONObject11.getString("id") == null || jSONObject11.getString("id").isEmpty()) {
                                            getEpisdoeDetailsCallback3.setId("");
                                        } else {
                                            getEpisdoeDetailsCallback3.setId(jSONObject11.getString("id"));
                                        }
                                        if (jSONObject11.getInt("season") == -1 || jSONObject11.getInt("season") == 0) {
                                            getEpisdoeDetailsCallback3.setId("");
                                        } else {
                                            getEpisdoeDetailsCallback3.setSeasonNumber(Integer.valueOf(jSONObject11.getInt("season")));
                                        }
                                        if (jSONObject11.getString("title") == null || jSONObject11.getString("title").isEmpty()) {
                                            getEpisdoeDetailsCallback3.setTitle("");
                                        } else {
                                            getEpisdoeDetailsCallback3.setTitle(jSONObject11.getString("title"));
                                        }
                                        if (jSONObject11.getString("direct_source") == null || jSONObject11.getString("direct_source").isEmpty()) {
                                            getEpisdoeDetailsCallback3.setDirectSource("");
                                        } else {
                                            getEpisdoeDetailsCallback3.setDirectSource(jSONObject11.getString("direct_source"));
                                        }
                                        if (jSONObject11.getString("added") == null || jSONObject11.getString("added").isEmpty()) {
                                            getEpisdoeDetailsCallback3.setAdded("");
                                        } else {
                                            getEpisdoeDetailsCallback3.setAdded(jSONObject11.getString("added"));
                                        }
                                        if (jSONObject11.getString("custom_sid") == null || jSONObject11.getString("custom_sid").isEmpty()) {
                                            getEpisdoeDetailsCallback3.setCustomSid("");
                                        } else {
                                            getEpisdoeDetailsCallback3.setCustomSid(jSONObject11.getString("custom_sid"));
                                        }
                                        if (jSONObject11.getString("container_extension") == null || jSONObject11.getString("container_extension").isEmpty()) {
                                            getEpisdoeDetailsCallback3.setContainerExtension("");
                                        } else {
                                            getEpisdoeDetailsCallback3.setContainerExtension(jSONObject11.getString("container_extension"));
                                        }
                                        if (this.series_categoryID != null) {
                                            getEpisdoeDetailsCallback3.setCategoryId(this.series_categoryID);
                                        }
                                        try {
                                            if (jSONObject11.getJSONObject("info") == null || jSONObject11.getJSONObject("info").getString("movie_image") == null) {
                                                getEpisdoeDetailsCallback3.setMovieImage("");
                                            } else {
                                                getEpisdoeDetailsCallback3.setMovieImage(jSONObject11.getJSONObject("info").getString("movie_image"));
                                            }
                                        } catch (Exception unused3) {
                                            getEpisdoeDetailsCallback3.setMovieImage("");
                                        }
                                        this.episdoeDetailsCallbacksList.add(getEpisdoeDetailsCallback3);
                                    }
                                }
                            }
                        }
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    try {
                        JSONObject jSONObject12 = new JSONObject(jsonElement.toString());
                        jSONArray3 = jSONObject12.getJSONArray("seasons");
                        String string2 = jSONObject12.getString("seasons");
                        if (jSONArray3.equals("null")) {
                            JSONObject jSONObject13 = jSONObject12.getJSONObject("episodes") != null ? jSONObject12.getJSONObject("episodes") : null;
                            if (!jSONObject13.equals(jSONArray3) && !string2.equals("[]")) {
                                this.episdoeDetailsCallbacksList.clear();
                                if (jSONObject13 != null) {
                                    Iterator<String> keys5 = jSONObject13.keys();
                                    while (keys5.hasNext()) {
                                        int parseInt = Integer.parseInt(keys5.next());
                                        String valueOf = String.valueOf(parseInt);
                                        if (jSONObject13.get(valueOf) instanceof JSONArray) {
                                            JSONArray jSONArray8 = new JSONArray(jSONObject13.get(valueOf).toString());
                                            if (!jSONObject13.equals(jSONArray3)) {
                                                int length5 = jSONArray8.length();
                                                int i6 = 0;
                                                boolean z2 = true;
                                                while (i6 < length5) {
                                                    JSONObject jSONObject14 = jSONArray8.getJSONObject(i6);
                                                    GetEpisdoeDetailsCallback getEpisdoeDetailsCallback4 = new GetEpisdoeDetailsCallback();
                                                    SeasonsDetailCallback seasonsDetailCallback4 = new SeasonsDetailCallback();
                                                    if (jSONObject14.getString("id") == null || jSONObject14.getString("id").isEmpty()) {
                                                        getEpisdoeDetailsCallback4.setId("");
                                                    } else {
                                                        getEpisdoeDetailsCallback4.setId(jSONObject14.getString("id"));
                                                    }
                                                    if (jSONObject14.getInt("season") != -1) {
                                                        getEpisdoeDetailsCallback4.setSeasonNumber(Integer.valueOf(parseInt));
                                                    } else {
                                                        getEpisdoeDetailsCallback4.setSeasonNumber(-1);
                                                    }
                                                    if (z2) {
                                                        if (jSONObject14.getInt("season") == -1 || jSONObject14.getInt("season") == 0) {
                                                            seasonsDetailCallback4.setSeasonNumber(-1);
                                                        } else {
                                                            seasonsDetailCallback4.setSeasonNumber(Integer.valueOf(jSONObject14.getInt("season")));
                                                        }
                                                        seasonsDetailCallback4.setAirDate("");
                                                        seasonsDetailCallback4.setEpisodeCount(-1);
                                                        seasonsDetailCallback4.setId(-1);
                                                        seasonsDetailCallback4.setName("");
                                                        seasonsDetailCallback4.setOverview("");
                                                        if (!jSONArray3.isNull(parseInt)) {
                                                            if (((JSONObject) jSONArray3.get(parseInt)).getString("cover") == null || ((JSONObject) jSONArray3.get(parseInt)).getString("cover").isEmpty()) {
                                                                seasonsDetailCallback4.setCover(this.seriesCover);
                                                            } else {
                                                                this.episodeCover = ((JSONObject) jSONArray3.get(parseInt)).getString("cover");
                                                                seasonsDetailCallback4.setCover(this.episodeCover);
                                                            }
                                                            if (((JSONObject) jSONArray3.get(parseInt)).getString("cover_big") == null || ((JSONObject) jSONArray3.get(parseInt)).getString("cover_big").isEmpty()) {
                                                                seasonsDetailCallback4.setCoverBig(this.seriesCover);
                                                            } else {
                                                                this.episodeCover = ((JSONObject) jSONArray3.get(parseInt)).getString("cover_big");
                                                                seasonsDetailCallback4.setCoverBig(this.episodeCover);
                                                            }
                                                            this.seasonsDetailCallbacks1.add(seasonsDetailCallback4);
                                                        } else {
                                                            this.episodeCover = "";
                                                            seasonsDetailCallback4.setCoverBig(this.seriesCover);
                                                            this.seasonsDetailCallbacks1.add(seasonsDetailCallback4);
                                                        }
                                                        z2 = false;
                                                    }
                                                    if (!this.episodeCover.equals("")) {
                                                        getEpisdoeDetailsCallback4.setImage(this.episodeCover);
                                                    } else {
                                                        getEpisdoeDetailsCallback4.setImage(this.seriesCover);
                                                    }
                                                    if (jSONObject14.getString("title") == null || jSONObject14.getString("title").isEmpty()) {
                                                        getEpisdoeDetailsCallback4.setTitle("");
                                                    } else {
                                                        getEpisdoeDetailsCallback4.setTitle(jSONObject14.getString("title"));
                                                    }
                                                    if (jSONObject14.getString("direct_source") == null || jSONObject14.getString("direct_source").isEmpty()) {
                                                        getEpisdoeDetailsCallback4.setDirectSource("");
                                                    } else {
                                                        getEpisdoeDetailsCallback4.setDirectSource(jSONObject14.getString("direct_source"));
                                                    }
                                                    if (jSONObject14.getString("added") == null || jSONObject14.getString("added").isEmpty()) {
                                                        getEpisdoeDetailsCallback4.setAdded("");
                                                    } else {
                                                        getEpisdoeDetailsCallback4.setAdded(jSONObject14.getString("added"));
                                                    }
                                                    if (jSONObject14.getString("custom_sid") == null || jSONObject14.getString("custom_sid").isEmpty()) {
                                                        getEpisdoeDetailsCallback4.setCustomSid("");
                                                    } else {
                                                        getEpisdoeDetailsCallback4.setCustomSid(jSONObject14.getString("custom_sid"));
                                                    }
                                                    if (jSONObject14.getString("container_extension") == null || jSONObject14.getString("container_extension").isEmpty()) {
                                                        getEpisdoeDetailsCallback4.setContainerExtension("");
                                                    } else {
                                                        getEpisdoeDetailsCallback4.setContainerExtension(jSONObject14.getString("container_extension"));
                                                    }
                                                    if (this.series_categoryID != null) {
                                                        getEpisdoeDetailsCallback4.setCategoryId(this.series_categoryID);
                                                    }
                                                    try {
                                                        if (jSONObject14.getJSONObject("info") == null || jSONObject14.getJSONObject("info").getString("movie_image") == null) {
                                                            getEpisdoeDetailsCallback4.setMovieImage("");
                                                            this.episdoeDetailsCallbacksList.add(getEpisdoeDetailsCallback4);
                                                            i6++;
                                                        } else {
                                                            getEpisdoeDetailsCallback4.setMovieImage(jSONObject14.getJSONObject("info").getString("movie_image"));
                                                            this.episdoeDetailsCallbacksList.add(getEpisdoeDetailsCallback4);
                                                            i6++;
                                                        }
                                                    } catch (Exception unused4) {
                                                        getEpisdoeDetailsCallback4.setMovieImage("");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            try {
                                JSONObject jSONObject15 = new JSONObject(jsonElement.toString());
                                String string3 = jSONObject15.getString("seasons");
                                JSONArray jSONArray9 = jSONObject15.getJSONArray("episodes") == null ? jSONObject15.getJSONArray("episodes") : null;
                                length = jSONArray9.length();
                                this.episdoeDetailsCallbacksList.clear();
                                if (string3.equals("[]") || string3.equals("null")) {
                                    for (i = 0; i < length; i++) {
                                        JSONArray jSONArray10 = new JSONArray(jSONArray9.get(i).toString());
                                        int length6 = jSONArray10.length();
                                        boolean z3 = true;
                                        for (int i7 = 0; i7 < length6; i7++) {
                                            JSONObject jSONObject16 = jSONArray10.getJSONObject(i7);
                                            GetEpisdoeDetailsCallback getEpisdoeDetailsCallback5 = new GetEpisdoeDetailsCallback();
                                            getEpisdoeDetailsCallback5.setImage(this.seriesCover);
                                            if (jSONObject16.getString("id") == null || jSONObject16.getString("id").isEmpty()) {
                                                getEpisdoeDetailsCallback5.setId("");
                                            } else {
                                                getEpisdoeDetailsCallback5.setId(jSONObject16.getString("id"));
                                            }
                                            if (jSONObject16.getInt("season") != -1) {
                                                getEpisdoeDetailsCallback5.setSeasonNumber(Integer.valueOf(jSONObject16.getInt("season")));
                                            } else {
                                                getEpisdoeDetailsCallback5.setSeasonNumber(-1);
                                            }
                                            if (jSONObject16.getString("title") == null || jSONObject16.getString("title").isEmpty()) {
                                                getEpisdoeDetailsCallback5.setTitle("");
                                            } else {
                                                getEpisdoeDetailsCallback5.setTitle(jSONObject16.getString("title"));
                                            }
                                            if (jSONObject16.getString("direct_source") == null || jSONObject16.getString("direct_source").isEmpty()) {
                                                getEpisdoeDetailsCallback5.setDirectSource("");
                                            } else {
                                                getEpisdoeDetailsCallback5.setDirectSource(jSONObject16.getString("direct_source"));
                                            }
                                            if (jSONObject16.getString("added") == null || jSONObject16.getString("added").isEmpty()) {
                                                getEpisdoeDetailsCallback5.setAdded("");
                                            } else {
                                                getEpisdoeDetailsCallback5.setAdded(jSONObject16.getString("added"));
                                            }
                                            if (jSONObject16.getString("custom_sid") == null || jSONObject16.getString("custom_sid").isEmpty()) {
                                                getEpisdoeDetailsCallback5.setCustomSid("");
                                            } else {
                                                getEpisdoeDetailsCallback5.setCustomSid(jSONObject16.getString("custom_sid"));
                                            }
                                            if (jSONObject16.getString("container_extension") == null || jSONObject16.getString("container_extension").isEmpty()) {
                                                getEpisdoeDetailsCallback5.setContainerExtension("");
                                            } else {
                                                getEpisdoeDetailsCallback5.setContainerExtension(jSONObject16.getString("container_extension"));
                                            }
                                            if (this.series_categoryID != null) {
                                                getEpisdoeDetailsCallback5.setCategoryId(this.series_categoryID);
                                            }
                                            try {
                                                if (jSONObject16.getJSONObject("info") == null || jSONObject16.getJSONObject("info").getString("movie_image") == null) {
                                                    getEpisdoeDetailsCallback5.setMovieImage("");
                                                } else {
                                                    getEpisdoeDetailsCallback5.setMovieImage(jSONObject16.getJSONObject("info").getString("movie_image"));
                                                }
                                            } catch (Exception unused5) {
                                                getEpisdoeDetailsCallback5.setMovieImage("");
                                            }
                                            this.episdoeDetailsCallbacksList.add(getEpisdoeDetailsCallback5);
                                            SeasonsDetailCallback seasonsDetailCallback5 = new SeasonsDetailCallback();
                                            if (z3) {
                                                if (jSONObject16.getInt("season") != -1) {
                                                    seasonsDetailCallback5.setSeasonNumber(Integer.valueOf(jSONObject16.getInt("season")));
                                                } else {
                                                    seasonsDetailCallback5.setSeasonNumber(-1);
                                                }
                                                seasonsDetailCallback5.setAirDate("");
                                                seasonsDetailCallback5.setEpisodeCount(-1);
                                                seasonsDetailCallback5.setId(-1);
                                                seasonsDetailCallback5.setName("");
                                                seasonsDetailCallback5.setOverview("");
                                                seasonsDetailCallback5.setCoverBig("");
                                                seasonsDetailCallback5.setCoverBig("");
                                                this.seasonsDetailCallbacks1.add(seasonsDetailCallback5);
                                                z3 = false;
                                            }
                                        }
                                    }
                                }
                            } catch (JSONException e3) {
                                e3.printStackTrace();
                            }
                            try {
                                jSONObject = new JSONObject(jsonElement.toString());
                                jSONArray2 = jSONObject.getJSONArray("episodes");
                                jSONObject.getJSONObject("seasons");
                                if (jSONObject.getJSONArray("episodes") != null) {
                                    jSONArray2 = jSONObject.getJSONArray("episodes");
                                }
                                int length7 = jSONArray2.length();
                                this.episdoeDetailsCallbacksList.clear();
                                if (jSONArray2 != null) {
                                    for (int i8 = 0; i8 < length7; i8++) {
                                        JSONArray jSONArray11 = new JSONArray(jSONArray2.get(i8).toString());
                                        int length8 = jSONArray11.length();
                                        boolean z4 = true;
                                        for (int i9 = 0; i9 < length8; i9++) {
                                            JSONObject jSONObject17 = jSONArray11.getJSONObject(i9);
                                            GetEpisdoeDetailsCallback getEpisdoeDetailsCallback6 = new GetEpisdoeDetailsCallback();
                                            getEpisdoeDetailsCallback6.setImage(this.seriesCover);
                                            if (jSONObject17.getString("id") == null || jSONObject17.getString("id").isEmpty()) {
                                                getEpisdoeDetailsCallback6.setId("");
                                            } else {
                                                getEpisdoeDetailsCallback6.setId(jSONObject17.getString("id"));
                                            }
                                            if (jSONObject17.getInt("season") != -1) {
                                                getEpisdoeDetailsCallback6.setSeasonNumber(Integer.valueOf(jSONObject17.getInt("season")));
                                            } else {
                                                getEpisdoeDetailsCallback6.setSeasonNumber(-1);
                                            }
                                            if (jSONObject17.getString("title") == null || jSONObject17.getString("title").isEmpty()) {
                                                getEpisdoeDetailsCallback6.setTitle("");
                                            } else {
                                                getEpisdoeDetailsCallback6.setTitle(jSONObject17.getString("title"));
                                            }
                                            if (jSONObject17.getString("direct_source") == null || jSONObject17.getString("direct_source").isEmpty()) {
                                                getEpisdoeDetailsCallback6.setDirectSource("");
                                            } else {
                                                getEpisdoeDetailsCallback6.setDirectSource(jSONObject17.getString("direct_source"));
                                            }
                                            if (jSONObject17.getString("added") == null || jSONObject17.getString("added").isEmpty()) {
                                                getEpisdoeDetailsCallback6.setAdded("");
                                            } else {
                                                getEpisdoeDetailsCallback6.setAdded(jSONObject17.getString("added"));
                                            }
                                            if (jSONObject17.getString("custom_sid") == null || jSONObject17.getString("custom_sid").isEmpty()) {
                                                getEpisdoeDetailsCallback6.setCustomSid("");
                                            } else {
                                                getEpisdoeDetailsCallback6.setCustomSid(jSONObject17.getString("custom_sid"));
                                            }
                                            if (jSONObject17.getString("container_extension") == null || jSONObject17.getString("container_extension").isEmpty()) {
                                                getEpisdoeDetailsCallback6.setContainerExtension("");
                                            } else {
                                                getEpisdoeDetailsCallback6.setContainerExtension(jSONObject17.getString("container_extension"));
                                            }
                                            if (this.series_categoryID != null) {
                                                getEpisdoeDetailsCallback6.setCategoryId(this.series_categoryID);
                                            }
                                            try {
                                                if (jSONObject17.getJSONObject("info") == null || jSONObject17.getJSONObject("info").getString("movie_image") == null) {
                                                    getEpisdoeDetailsCallback6.setMovieImage("");
                                                } else {
                                                    getEpisdoeDetailsCallback6.setMovieImage(jSONObject17.getJSONObject("info").getString("movie_image"));
                                                }
                                            } catch (Exception unused6) {
                                                getEpisdoeDetailsCallback6.setMovieImage("");
                                            }
                                            this.episdoeDetailsCallbacksList.add(getEpisdoeDetailsCallback6);
                                            SeasonsDetailCallback seasonsDetailCallback6 = new SeasonsDetailCallback();
                                            if (z4) {
                                                if (jSONObject17.getInt("season") != -1) {
                                                    seasonsDetailCallback6.setSeasonNumber(Integer.valueOf(jSONObject17.getInt("season")));
                                                } else {
                                                    seasonsDetailCallback6.setSeasonNumber(-1);
                                                }
                                                seasonsDetailCallback6.setAirDate("");
                                                seasonsDetailCallback6.setEpisodeCount(-1);
                                                seasonsDetailCallback6.setId(-1);
                                                seasonsDetailCallback6.setName("");
                                                seasonsDetailCallback6.setOverview("");
                                                seasonsDetailCallback6.setCoverBig("");
                                                this.seasonsDetailCallbacks1.add(seasonsDetailCallback6);
                                                z4 = false;
                                            }
                                        }
                                    }
                                }
                            } catch (JSONException e4) {
                                e4.printStackTrace();
                            }
                            try {
                                JSONObject jSONObject18 = new JSONObject(jsonElement.toString());
                                jSONObject18.getJSONArray("seasons");
                                jSONArray = jSONObject18.getJSONArray("episodes") == null ? jSONObject18.getJSONArray("episodes") : null;
                                int length9 = jSONArray.length();
                                this.episdoeDetailsCallbacksList.clear();
                                if (jSONArray != null) {
                                    for (int i10 = 0; i10 < length9; i10++) {
                                        JSONArray jSONArray12 = new JSONArray(jSONArray.get(i10).toString());
                                        int length10 = jSONArray12.length();
                                        boolean z5 = true;
                                        for (int i11 = 0; i11 < length10; i11++) {
                                            JSONObject jSONObject19 = jSONArray12.getJSONObject(i11);
                                            GetEpisdoeDetailsCallback getEpisdoeDetailsCallback7 = new GetEpisdoeDetailsCallback();
                                            getEpisdoeDetailsCallback7.setImage(this.seriesCover);
                                            if (jSONObject19.getString("id") == null || jSONObject19.getString("id").isEmpty()) {
                                                getEpisdoeDetailsCallback7.setId("");
                                            } else {
                                                getEpisdoeDetailsCallback7.setId(jSONObject19.getString("id"));
                                            }
                                            if (jSONObject19.getInt("season") != -1) {
                                                getEpisdoeDetailsCallback7.setSeasonNumber(Integer.valueOf(jSONObject19.getInt("season")));
                                            } else {
                                                getEpisdoeDetailsCallback7.setSeasonNumber(-1);
                                            }
                                            if (jSONObject19.getString("title") == null || jSONObject19.getString("title").isEmpty()) {
                                                getEpisdoeDetailsCallback7.setTitle("");
                                            } else {
                                                getEpisdoeDetailsCallback7.setTitle(jSONObject19.getString("title"));
                                            }
                                            if (jSONObject19.getString("direct_source") == null || jSONObject19.getString("direct_source").isEmpty()) {
                                                getEpisdoeDetailsCallback7.setDirectSource("");
                                            } else {
                                                getEpisdoeDetailsCallback7.setDirectSource(jSONObject19.getString("direct_source"));
                                            }
                                            if (jSONObject19.getString("added") == null || jSONObject19.getString("added").isEmpty()) {
                                                getEpisdoeDetailsCallback7.setAdded("");
                                            } else {
                                                getEpisdoeDetailsCallback7.setAdded(jSONObject19.getString("added"));
                                            }
                                            if (jSONObject19.getString("custom_sid") == null || jSONObject19.getString("custom_sid").isEmpty()) {
                                                getEpisdoeDetailsCallback7.setCustomSid("");
                                            } else {
                                                getEpisdoeDetailsCallback7.setCustomSid(jSONObject19.getString("custom_sid"));
                                            }
                                            if (jSONObject19.getString("container_extension") == null || jSONObject19.getString("container_extension").isEmpty()) {
                                                getEpisdoeDetailsCallback7.setContainerExtension("");
                                            } else {
                                                getEpisdoeDetailsCallback7.setContainerExtension(jSONObject19.getString("container_extension"));
                                            }
                                            if (this.series_categoryID != null) {
                                                getEpisdoeDetailsCallback7.setCategoryId(this.series_categoryID);
                                            }
                                            try {
                                                if (jSONObject19.getJSONObject("info") == null || jSONObject19.getJSONObject("info").getString("movie_image") == null) {
                                                    getEpisdoeDetailsCallback7.setMovieImage("");
                                                } else {
                                                    getEpisdoeDetailsCallback7.setMovieImage(jSONObject19.getJSONObject("info").getString("movie_image"));
                                                }
                                            } catch (Exception unused7) {
                                                getEpisdoeDetailsCallback7.setMovieImage("");
                                            }
                                            this.episdoeDetailsCallbacksList.add(getEpisdoeDetailsCallback7);
                                            SeasonsDetailCallback seasonsDetailCallback7 = new SeasonsDetailCallback();
                                            if (z5) {
                                                if (jSONObject19.getInt("season") != -1) {
                                                    seasonsDetailCallback7.setSeasonNumber(Integer.valueOf(jSONObject19.getInt("season")));
                                                } else {
                                                    seasonsDetailCallback7.setSeasonNumber(-1);
                                                }
                                                seasonsDetailCallback7.setAirDate("");
                                                seasonsDetailCallback7.setEpisodeCount(-1);
                                                seasonsDetailCallback7.setId(-1);
                                                seasonsDetailCallback7.setName("");
                                                seasonsDetailCallback7.setOverview("");
                                                seasonsDetailCallback7.setCoverBig("");
                                                seasonsDetailCallback7.setCoverBig("");
                                                this.seasonsDetailCallbacks1.add(seasonsDetailCallback7);
                                                z5 = false;
                                            }
                                        }
                                    }
                                }
                            } catch (JSONException e5) {
                                e5.printStackTrace();
                            }
                        } else {
                            if (jSONArray3.equals("[]")) {
                                JSONObject jSONObject20 = jSONObject12.getJSONObject("episodes") != null ? jSONObject12.getJSONObject("episodes") : null;
                                this.episdoeDetailsCallbacksList.clear();
                                if (jSONObject20 != null) {
                                    Iterator<String> keys6 = jSONObject20.keys();
                                    while (keys6.hasNext()) {
                                        String next5 = keys6.next();
                                        if (jSONObject20.get(next5) instanceof JSONArray) {
                                            JSONArray jSONArray13 = new JSONArray(jSONObject20.get(next5).toString());
                                            int length11 = jSONArray13.length();
                                            boolean z6 = true;
                                            for (int i12 = 0; i12 < length11; i12++) {
                                                JSONObject jSONObject21 = jSONArray13.getJSONObject(i12);
                                                GetEpisdoeDetailsCallback getEpisdoeDetailsCallback8 = new GetEpisdoeDetailsCallback();
                                                SeasonsDetailCallback seasonsDetailCallback8 = new SeasonsDetailCallback();
                                                getEpisdoeDetailsCallback8.setImage(this.seriesCover);
                                                if (jSONObject21.getString("id") == null || jSONObject21.getString("id").isEmpty()) {
                                                    getEpisdoeDetailsCallback8.setId("");
                                                } else {
                                                    getEpisdoeDetailsCallback8.setId(jSONObject21.getString("id"));
                                                }
                                                if (jSONObject21.getInt("season") != -1) {
                                                    getEpisdoeDetailsCallback8.setSeasonNumber(Integer.valueOf(jSONObject21.getInt("season")));
                                                } else {
                                                    getEpisdoeDetailsCallback8.setSeasonNumber(-1);
                                                }
                                                if (z6) {
                                                    if (jSONObject21.getInt("season") == -1 || jSONObject21.getInt("season") == 0) {
                                                        seasonsDetailCallback8.setSeasonNumber(-1);
                                                    } else {
                                                        seasonsDetailCallback8.setSeasonNumber(Integer.valueOf(jSONObject21.getInt("season")));
                                                    }
                                                    seasonsDetailCallback8.setAirDate("");
                                                    seasonsDetailCallback8.setEpisodeCount(-1);
                                                    seasonsDetailCallback8.setId(-1);
                                                    seasonsDetailCallback8.setName("");
                                                    seasonsDetailCallback8.setOverview("");
                                                    seasonsDetailCallback8.setCoverBig("");
                                                    seasonsDetailCallback8.setCoverBig("");
                                                    this.seasonsDetailCallbacks1.add(seasonsDetailCallback8);
                                                    z6 = false;
                                                }
                                                if (jSONObject21.getString("title") == null || jSONObject21.getString("title").isEmpty()) {
                                                    getEpisdoeDetailsCallback8.setTitle("");
                                                } else {
                                                    getEpisdoeDetailsCallback8.setTitle(jSONObject21.getString("title"));
                                                }
                                                if (jSONObject21.getString("direct_source") == null || jSONObject21.getString("direct_source").isEmpty()) {
                                                    getEpisdoeDetailsCallback8.setDirectSource("");
                                                } else {
                                                    getEpisdoeDetailsCallback8.setDirectSource(jSONObject21.getString("direct_source"));
                                                }
                                                if (jSONObject21.getString("added") == null || jSONObject21.getString("added").isEmpty()) {
                                                    getEpisdoeDetailsCallback8.setAdded("");
                                                } else {
                                                    getEpisdoeDetailsCallback8.setAdded(jSONObject21.getString("added"));
                                                }
                                                if (jSONObject21.getString("custom_sid") == null || jSONObject21.getString("custom_sid").isEmpty()) {
                                                    getEpisdoeDetailsCallback8.setCustomSid("");
                                                } else {
                                                    getEpisdoeDetailsCallback8.setCustomSid(jSONObject21.getString("custom_sid"));
                                                }
                                                if (jSONObject21.getString("container_extension") == null || jSONObject21.getString("container_extension").isEmpty()) {
                                                    getEpisdoeDetailsCallback8.setContainerExtension("");
                                                } else {
                                                    getEpisdoeDetailsCallback8.setContainerExtension(jSONObject21.getString("container_extension"));
                                                }
                                                if (this.series_categoryID != null) {
                                                    getEpisdoeDetailsCallback8.setCategoryId(this.series_categoryID);
                                                }
                                                try {
                                                    if (jSONObject21.getJSONObject("info") == null || jSONObject21.getJSONObject("info").getString("movie_image") == null) {
                                                        getEpisdoeDetailsCallback8.setMovieImage("");
                                                    } else {
                                                        getEpisdoeDetailsCallback8.setMovieImage(jSONObject21.getJSONObject("info").getString("movie_image"));
                                                    }
                                                } catch (Exception unused8) {
                                                    getEpisdoeDetailsCallback8.setMovieImage("");
                                                }
                                                this.episdoeDetailsCallbacksList.add(getEpisdoeDetailsCallback8);
                                            }
                                        }
                                    }
                                }
                            }
                            JSONObject jSONObject152 = new JSONObject(jsonElement.toString());
                            String string32 = jSONObject152.getString("seasons");
                            if (jSONObject152.getJSONArray("episodes") == null) {
                            }
                            //ToDO: jsonArray...
                            length = jSONArray.length();
                            this.episdoeDetailsCallbacksList.clear();
                            /*while (i < length) {
                            }*/
                            jSONObject = new JSONObject(jsonElement.toString());
                            jSONArray2 = jSONObject.getJSONArray("episodes");
                            jSONObject.getJSONObject("seasons");
                            if (jSONObject.getJSONArray("episodes") != null) {
                            }
                            int length72 = jSONArray2.length();
                            this.episdoeDetailsCallbacksList.clear();
                            if (jSONArray2 != null) {
                            }
                            JSONObject jSONObject182 = new JSONObject(jsonElement.toString());
                            jSONObject182.getJSONArray("seasons");
                            if (jSONObject182.getJSONArray("episodes") == null) {
                            }
                            int length92 = jSONArray.length();
                            this.episdoeDetailsCallbacksList.clear();
                            if (jSONArray != null) {
                            }
                        }
                    } catch (JSONException e6) {
                        e6.printStackTrace();
                    }
                } else {
                    if (string.equals("[]")) {
                        JSONObject jSONObject22 = jSONObject4.getJSONObject("episodes") != null ? jSONObject4.getJSONObject("episodes") : null;
                        this.episdoeDetailsCallbacksList.clear();
                        if (jSONObject22 != null) {
                            Iterator<String> keys7 = jSONObject22.keys();
                            while (keys7.hasNext()) {
                                String next6 = keys7.next();
                                if (jSONObject22.get(next6) instanceof JSONArray) {
                                    JSONArray jSONArray14 = new JSONArray(jSONObject22.get(next6).toString());
                                    int length12 = jSONArray14.length();
                                    boolean z7 = true;
                                    for (int i13 = 0; i13 < length12; i13++) {
                                        JSONObject jSONObject23 = jSONArray14.getJSONObject(i13);
                                        GetEpisdoeDetailsCallback getEpisdoeDetailsCallback9 = new GetEpisdoeDetailsCallback();
                                        SeasonsDetailCallback seasonsDetailCallback9 = new SeasonsDetailCallback();
                                        if (jSONObject23.getString("id") == null || jSONObject23.getString("id").isEmpty()) {
                                            getEpisdoeDetailsCallback9.setId("");
                                        } else {
                                            getEpisdoeDetailsCallback9.setId(jSONObject23.getString("id"));
                                        }
                                        if (jSONObject23.getInt("season") != -1) {
                                            getEpisdoeDetailsCallback9.setSeasonNumber(Integer.valueOf(jSONObject23.getInt("season")));
                                        } else {
                                            getEpisdoeDetailsCallback9.setSeasonNumber(-1);
                                        }
                                        if (z7) {
                                            if (jSONObject23.getInt("season") == -1 || jSONObject23.getInt("season") == 0) {
                                                seasonsDetailCallback9.setSeasonNumber(-1);
                                            } else {
                                                seasonsDetailCallback9.setSeasonNumber(Integer.valueOf(jSONObject23.getInt("season")));
                                            }
                                            seasonsDetailCallback9.setAirDate("");
                                            seasonsDetailCallback9.setEpisodeCount(-1);
                                            seasonsDetailCallback9.setId(-1);
                                            seasonsDetailCallback9.setName("");
                                            seasonsDetailCallback9.setOverview("");
                                            seasonsDetailCallback9.setCoverBig("");
                                            seasonsDetailCallback9.setCoverBig("");
                                            this.seasonsDetailCallbacks1.add(seasonsDetailCallback9);
                                            z7 = false;
                                        }
                                        getEpisdoeDetailsCallback9.setImage(this.seriesCover);
                                        if (jSONObject23.getString("title") == null || jSONObject23.getString("title").isEmpty()) {
                                            getEpisdoeDetailsCallback9.setTitle("");
                                        } else {
                                            getEpisdoeDetailsCallback9.setTitle(jSONObject23.getString("title"));
                                        }
                                        if (jSONObject23.getString("direct_source") == null || jSONObject23.getString("direct_source").isEmpty()) {
                                            getEpisdoeDetailsCallback9.setDirectSource("");
                                        } else {
                                            getEpisdoeDetailsCallback9.setDirectSource(jSONObject23.getString("direct_source"));
                                        }
                                        if (jSONObject23.getString("added") == null || jSONObject23.getString("added").isEmpty()) {
                                            getEpisdoeDetailsCallback9.setAdded("");
                                        } else {
                                            getEpisdoeDetailsCallback9.setAdded(jSONObject23.getString("added"));
                                        }
                                        if (jSONObject23.getString("custom_sid") == null || jSONObject23.getString("custom_sid").isEmpty()) {
                                            getEpisdoeDetailsCallback9.setCustomSid("");
                                        } else {
                                            getEpisdoeDetailsCallback9.setCustomSid(jSONObject23.getString("custom_sid"));
                                        }
                                        if (jSONObject23.getString("container_extension") == null || jSONObject23.getString("container_extension").isEmpty()) {
                                            getEpisdoeDetailsCallback9.setContainerExtension("");
                                        } else {
                                            getEpisdoeDetailsCallback9.setContainerExtension(jSONObject23.getString("container_extension"));
                                        }
                                        if (this.series_categoryID != null) {
                                            getEpisdoeDetailsCallback9.setCategoryId(this.series_categoryID);
                                        }
                                        try {
                                            if (jSONObject23.getJSONObject("info") == null || jSONObject23.getJSONObject("info").getString("movie_image") == null) {
                                                getEpisdoeDetailsCallback9.setMovieImage("");
                                            } else {
                                                getEpisdoeDetailsCallback9.setMovieImage(jSONObject23.getJSONObject("info").getString("movie_image"));
                                            }
                                        } catch (Exception unused9) {
                                            getEpisdoeDetailsCallback9.setMovieImage("");
                                        }
                                        this.episdoeDetailsCallbacksList.add(getEpisdoeDetailsCallback9);
                                    }
                                }
                            }
                        }
                    }
                    //ToDo: Serious issue..
                    JSONObject jSONObject72 = new JSONObject(jsonElement.toString());
                    if (jSONObject72.getJSONObject("episodes") == null) {
                    }
                    if (jSONObject72.getJSONObject("episodes") == null) {
                    }
                    this.episdoeDetailsCallbacksList.clear();
                  //  keys2 = jSONObject8.keys();
                   /* while (keys2.hasNext()) {
                    }*/
                   /* if (jSONObject3 != null) {
                    }*/
                    JSONObject jSONObject102 = new JSONObject(jsonElement.toString());
                    jSONArray4 = jSONObject102.getJSONArray("seasons");
                    if (jSONObject102.getJSONObject("episodes") == null) {
                    }
                    /*if (jSONObject2.equals(jSONArray4)) {
                    }*/
                   /* keys = jSONObject2.keys();
                    while (keys.hasNext()) {
                    }*/
                    JSONObject jSONObject122 = new JSONObject(jsonElement.toString());
                    jSONArray3 = jSONObject122.getJSONArray("seasons");
                    String string22 = jSONObject122.getString("seasons");
                    if (jSONArray3.equals("null")) {
                    }
                }
            } catch (JSONException e7) {
                e7.printStackTrace();
            }
        }
        set_episode_data();
    }

    public void set_episode_data() {
        onFinish();
        String string = this.SharedPreferencesSort.getString(AppConst.LOGIN_PREF_SORT, "");
        if (string.equals("1")) {
            AppConst.SORT_EPISODES = AppConst.SORT_EPISODES_LASTADDED;
            Collections.sort(this.episdoeDetailsCallbacksList, GetEpisdoeDetailsCallback.episodeComparator);
            this.episdoeDetailsCallbacksListRefined = this.episdoeDetailsCallbacksList;
        } else if (string.equals("2")) {
            AppConst.SORT_EPISODES = AppConst.SORT_EPISODES_A_TO_Z;
            Collections.sort(this.episdoeDetailsCallbacksList, GetEpisdoeDetailsCallback.episodeComparator);
            this.episdoeDetailsCallbacksListRefined = this.episdoeDetailsCallbacksList;
        } else if (string.equals("3")) {
            AppConst.SORT_EPISODES = AppConst.SORT_EPISODES_Z_TO_A;
            Collections.sort(this.episdoeDetailsCallbacksList, GetEpisdoeDetailsCallback.episodeComparator);
            this.episdoeDetailsCallbacksListRefined = this.episdoeDetailsCallbacksList;
        } else {
            this.episdoeDetailsCallbacksListRefined = this.episdoeDetailsCallbacksList;
        }
        if (this.episdoeDetailsCallbacksListRefined != null && this.myRecyclerView != null && this.episdoeDetailsCallbacksListRefined.size() != 0) {
            if (this.count > 0) {
                Utils.showToastLong(this.context, getResources().getString(R.string.use_long_press));
            }
            EpisodesUsingSinglton.getInstance().setEpisodeList(this.episdoeDetailsCallbacksListRefined);
            this.episodeDetailAdapter = new EpisodeDetailAdapter(this.episdoeDetailsCallbacksListRefined, this.context, this.seriesCover, null, this, this.seasonsDetailCallbacks);
            this.myRecyclerView.setAdapter(this.episodeDetailAdapter);
            onFinish();
        } else if (this.tvNoStream != null) {
            this.tvNoStream.setVisibility(0);
        }
    }

    public void progressBar(ProgressBar progressBar) {
        pbPagingLoader1 = progressBar;
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        this.myRecyclerView.setClickable(true);
        this.mAdapter = new SeasonsAdapter();
        if (this.mAdapter != null) {
            this.mAdapter.setVisibiltygone(pbPagingLoader1);
        }
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        Utils.appResume(this.context);
        getWindow().setFlags(1024, 1024);
        try {
            this.mCastContext.addCastStateListener(this.mCastStateListener);
            this.mCastContext.getSessionManager().addSessionManagerListener(this.mSessionManagerListener, CastSession.class);
            if (this.mCastSession == null) {
                this.mCastSession = CastContext.getSharedInstance(this).getSessionManager().getCurrentCastSession();
            }
        } catch (Exception e) {
            Log.e("sdng", "" + e);
        }
        this.mAdapter = new SeasonsAdapter();
        if (this.mAdapter != null) {
            this.mAdapter.setVisibiltygone(pbPagingLoader1);
        }
        this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        if (!this.loginPreferencesAfterLogin.getString("username", "").equals("") || !this.loginPreferencesAfterLogin.getString("password", "").equals("")) {
            Context context2 = this.context;
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.toolbar.inflateMenu(R.menu.menu_search_episodes);
        try {
            this.mediaRouteMenuItem = CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), menu, (int) R.id.media_route_menu_item);
        } catch (Exception e) {
            Log.e("sdng", "" + e);
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
                /* class com.nst.yourname.view.activity.EpisodeDetailActivity.AnonymousClass4 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(EpisodeDetailActivity.this.context);
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.EpisodeDetailActivity.AnonymousClass3 */

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
                /* class com.nst.yourname.view.activity.EpisodeDetailActivity.AnonymousClass5 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadChannelsAndVod(EpisodeDetailActivity.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.EpisodeDetailActivity.AnonymousClass6 */

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
                /* class com.nst.yourname.view.activity.EpisodeDetailActivity.AnonymousClass7 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadTvGuid(EpisodeDetailActivity.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.EpisodeDetailActivity.AnonymousClass8 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder2.show();
        }
        if (itemId == R.id.action_search) {
            this.searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
            this.searchView.setQueryHint(getResources().getString(R.string.search_episodes));
            this.searchView.setIconifiedByDefault(false);
            this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                /* class com.nst.yourname.view.activity.EpisodeDetailActivity.AnonymousClass9 */

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextSubmit(String str) {
                    return false;
                }

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextChange(String str) {
                    EpisodeDetailActivity.this.tvNoRecordFound.setVisibility(8);
                    if (EpisodeDetailActivity.this.episodeDetailAdapter == null || EpisodeDetailActivity.this.tvNoRecordFound == null || EpisodeDetailActivity.this.tvNoRecordFound.getVisibility() == 0) {
                        return false;
                    }
                    EpisodeDetailActivity.this.episodeDetailAdapter.filter(str, EpisodeDetailActivity.this.tvNoRecordFound);
                    return false;
                }
            });
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
            ((RadioButton) inflate.findViewById(R.id.rb_channel_asc)).setVisibility(8);
            ((RadioButton) inflate.findViewById(R.id.rb_channel_desc)).setVisibility(8);
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
                /* class com.nst.yourname.view.activity.EpisodeDetailActivity.AnonymousClass10 */

                public void onClick(View view) {
                    EpisodeDetailActivity.this.changeSortPopUp.dismiss();
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.EpisodeDetailActivity.AnonymousClass11 */

                public void onClick(View view) {
                    RadioButton radioButton = (RadioButton) inflate.findViewById(radioGroup.getCheckedRadioButtonId());
                    if (radioButton.getText().toString().equals(EpisodeDetailActivity.this.getResources().getString(R.string.sort_last_added))) {
                        EpisodeDetailActivity.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "1");
                        EpisodeDetailActivity.this.SharedPreferencesSortEditor.commit();
                    } else if (radioButton.getText().toString().equals(EpisodeDetailActivity.this.getResources().getString(R.string.sort_atoz))) {
                        EpisodeDetailActivity.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "2");
                        EpisodeDetailActivity.this.SharedPreferencesSortEditor.commit();
                    } else if (radioButton.getText().toString().equals(EpisodeDetailActivity.this.getResources().getString(R.string.sort_ztoa))) {
                        EpisodeDetailActivity.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, "3");
                        EpisodeDetailActivity.this.SharedPreferencesSortEditor.commit();
                    } else {
                        EpisodeDetailActivity.this.SharedPreferencesSortEditor.putString(AppConst.LOGIN_PREF_SORT, AppConst.PASSWORD_UNSET);
                        EpisodeDetailActivity.this.SharedPreferencesSortEditor.commit();
                    }
                    if (EpisodeDetailActivity.this.isEpisode) {
                        EpisodeDetailActivity.this.set_episode_data();
                    } else {
                        EpisodeDetailActivity.this.setSeasonsList();
                    }
                    EpisodeDetailActivity.this.changeSortPopUp.dismiss();
                }
            });
        } catch (Exception unused) {
        }
    }

    private void loadTvGuid() {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor edit;
        if (this.context != null && (edit = (sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0)).edit()) != null) {
            edit.putString(AppConst.SKIP_BUTTON_PREF, "autoLoad");
            edit.commit();
            sharedPreferences.getString(AppConst.SKIP_BUTTON_PREF, "");
            new LiveStreamDBHandler(this.context).makeEmptyEPG();
            startActivity(new Intent(this.context, ImportEPGActivity.class));
        }
    }

    public void hideSystemUi() {
        this.activityLogin.setSystemUiVisibility(4871);
    }

    public void goForDownload() {
        String userName2 = getUserName();
        String userPassword2 = getUserPassword();
        String string = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).getString(AppConst.LOGIN_PREF_SERVER_URL, "");
        if (!string.startsWith("http://") && !string.startsWith("https://")) {
            string = "http://" + string;
        }
        Utils.showDownloadingPopUP(this, this.context, this.downloadMoviename, string + "series/" + userName2 + "/" + userPassword2 + "/" + this.downloadstreamId + "." + this.downloadcontainerExtension, this.downloadcontainerExtension);
    }

    public void checkingForDownload(int i, String str, String str2) {
        this.downloadcontainerExtension = str;
        this.downloadMoviename = str2;
        this.downloadstreamId = i;
        isStoragePermissionGranted();
    }

    public void isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT < 23) {
            goForDownload();
        } else if (ActivityCompat.checkSelfPermission(this.context, "android.permission.READ_EXTERNAL_STORAGEandroid.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            goForDownload();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 101);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
     arg types: [android.widget.Button, com.nst.yourname.view.activity.EpisodeDetailActivity]
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
    @Override // android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback, android.support.v4.app.FragmentActivity
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i == 101) {
            if (iArr.length > 0 && iArr[0] == 0) {
                goForDownload();
            } else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(strArr[0])) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
                View inflate = LayoutInflater.from(this).inflate((int) R.layout.permission_alertbox, (ViewGroup) null);
                Button button = (Button) inflate.findViewById(R.id.btn_grant);
                Button button2 = (Button) inflate.findViewById(R.id.btn_cancel);
                button.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button, (Activity) this));
                button2.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button2, (Activity) this));
                button.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.activity.EpisodeDetailActivity.AnonymousClass12 */

                    public void onClick(View view) {
                        try {
                            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.fromParts("package", EpisodeDetailActivity.this.getPackageName(), null));
                            EpisodeDetailActivity.this.startActivityForResult(intent, 101);
                            Toast.makeText(EpisodeDetailActivity.this, EpisodeDetailActivity.this.context.getResources().getString(R.string.grant_the_permission), 1).show();
                        } catch (Exception unused) {
                        }
                        EpisodeDetailActivity.this.alertDialog.dismiss();
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.view.activity.EpisodeDetailActivity.AnonymousClass13 */

                    public void onClick(View view) {
                        EpisodeDetailActivity.this.alertDialog.dismiss();
                    }
                });
                builder.setView(inflate);
                this.alertDialog = builder.create();
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                Window window = this.alertDialog.getWindow();
                window.getClass();
                layoutParams.copyFrom(window.getAttributes());
                layoutParams.width = -1;
                layoutParams.height = -2;
                this.alertDialog.show();
                this.alertDialog.getWindow().setAttributes(layoutParams);
                this.alertDialog.setCancelable(false);
                this.alertDialog.show();
            }
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 101) {
            isStoragePermissionGranted();
        }
    }

    public String getUserName() {
        if (this.context != null) {
            return this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).getString("username", "");
        }
        return "";
    }

    public String getUserPassword() {
        if (this.context != null) {
            return this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).getString("password", "");
        }
        return "";
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onStop() {
        try {
            CastContext.getSharedInstance(this.context).getSessionManager().removeSessionManagerListener(this.mSessionManagerListener, CastSession.class);
        } catch (Exception unused) {
        }
        super.onStop();
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onStart() {
        try {
            CastContext.getSharedInstance(this.context).getSessionManager().addSessionManagerListener(this.mSessionManagerListener, CastSession.class);
        } catch (Exception unused) {
        }
        super.onStart();
    }
}
