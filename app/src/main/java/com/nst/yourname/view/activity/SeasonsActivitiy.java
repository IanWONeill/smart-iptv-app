package com.nst.yourname.view.activity;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.JsonElement;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.EpisodesUsingSinglton;
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.model.callback.GetEpisdoeDetailsCallback;
import com.nst.yourname.model.callback.SeasonsDetailCallback;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.PasswordStatusDBModel;
import com.nst.yourname.model.database.SeriesStreamsDatabaseHandler;
import com.nst.yourname.presenter.SeriesPresenter;
import com.nst.yourname.presenter.XMLTVPresenter;
import com.nst.yourname.view.adapter.EpisodeDetailAdapter;
import com.nst.yourname.view.adapter.SeasonsAdapter;
import com.nst.yourname.view.interfaces.SeriesInterface;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SeasonsActivitiy extends AppCompatActivity implements SeriesInterface, View.OnClickListener {
    static final boolean $assertionsDisabled = false;
    static ProgressBar pbPagingLoader1;
    int actionBarHeight;
    @BindView(R.id.main_layout)
    LinearLayout activityLogin;
    @BindView(R.id.appbar_toolbar)
    AppBarLayout appbarToolbar;
    ArrayList<LiveStreamCategoryIdDBModel> categoriesList;
    private ArrayList<PasswordStatusDBModel> categoryWithPasword;
    TextView clientNameTv;
    public Context context;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelLive = new DatabaseUpdatedStatusDBModel();
    private ArrayList<GetEpisdoeDetailsCallback> episdoeDetailsCallbacksList = new ArrayList<>();
    private EpisodeDetailAdapter episodeDetailAdapter;
    @BindView(R.id.fl_frame)
    FrameLayout frameLayout;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetail;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailAvailable;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailUnlcked;
    private ArrayList<LiveStreamCategoryIdDBModel> liveListDetailUnlckedDetail;
    private LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesAfterLogin;
    @BindView(R.id.logo)
    ImageView logo;
    public SeasonsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.pb_loader)
    ProgressBar pbLoader;
    @BindView(R.id.pb_paging_loader)
    ProgressBar pbPagingLoader;
    @BindView(R.id.rl_vod_layout)
    RelativeLayout rl_vod_layout;
    SearchView searchView;
    private ArrayList<SeasonsDetailCallback> seasonsDetailCallbacks = new ArrayList<>();
    @BindView(R.id.tv_settings)
    TextView seasonsName;
    private String seriesCover = "";
    private String seriesId = "";
    private String seriesName = "";
    private SeriesPresenter seriesPresenter;
    private SeriesStreamsDatabaseHandler seriesStreamsDatabaseHandler;
    ArrayList<LiveStreamCategoryIdDBModel> subCategoryList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    /* renamed from: tv  reason: collision with root package name */
    TypedValue f16tv;
    @BindView(R.id.empty_view)
    TextView tvNoRecordFound;
    private XMLTVPresenter xmltvPresenter;

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void atStart() {
    }

    public void onClick(View view) {
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFailed(String str) {
    }

    @Override // com.nst.yourname.view.interfaces.SeriesInterface
    public void seriesError(String str) {
    }

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_seasons_activitiy);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        if (this.appbarToolbar != null) {
            this.appbarToolbar.setBackground(getResources().getDrawable(R.drawable.vod_backgound));
        }
        this.context = this;
        changeStatusBarColor();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getWindow().setFlags(1024, 1024);
        initializeV();
        this.frameLayout.setVisibility(8);
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.SeasonsActivitiy.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(SeasonsActivitiy.this.context);
            }
        });
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

    private void initializeV() {
        this.seriesPresenter = new SeriesPresenter(this.context, this);
        Intent intent = getIntent();
        if (intent != null) {
            this.seriesId = intent.getStringExtra(AppConst.SERIES_SERIES_ID);
            this.seriesCover = intent.getStringExtra(AppConst.SERIES_COVER);
            this.seriesName = intent.getStringExtra(AppConst.SERIES_NAME);
            if (this.seriesName != null && !this.seriesName.isEmpty()) {
                this.seasonsName.setText(this.seriesName);
                this.seasonsName.setSelected(true);
            }
        }
        this.loginPreferencesAfterLogin = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        String string = this.loginPreferencesAfterLogin.getString("username", "");
        String string2 = this.loginPreferencesAfterLogin.getString("password", "");
        if (this.seriesId != null && !this.seriesId.isEmpty() && this.seriesPresenter != null && !string.isEmpty() && !string2.isEmpty()) {
            this.seriesPresenter.getSeriesEpisode(string, string2, this.seriesId);
        }
    }

    @Override // com.nst.yourname.view.interfaces.SeriesInterface
    public void getSeriesEpisodeInfo(JsonElement jsonElement) {
        if (jsonElement != null) {
            try {
                JSONObject jSONObject = new JSONObject(jsonElement.toString());
                String string = jSONObject.getString("seasons");
                if (string.equals("null")) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("episodes") != null ? jSONObject.getJSONObject("episodes") : null;
                    this.episdoeDetailsCallbacksList.clear();
                    if (jSONObject2 != null) {
                        Iterator<String> keys = jSONObject2.keys();
                        while (keys.hasNext()) {
                            String next = keys.next();
                            if (jSONObject2.get(next) instanceof JSONArray) {
                                JSONArray jSONArray = new JSONArray(jSONObject2.get(next).toString());
                                int length = jSONArray.length();
                                boolean z = true;
                                for (int i = 0; i < length; i++) {
                                    JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                                    GetEpisdoeDetailsCallback getEpisdoeDetailsCallback = new GetEpisdoeDetailsCallback();
                                    SeasonsDetailCallback seasonsDetailCallback = new SeasonsDetailCallback();
                                    if (jSONObject3.getString("id") == null || jSONObject3.getString("id").isEmpty()) {
                                        getEpisdoeDetailsCallback.setId("");
                                    } else {
                                        getEpisdoeDetailsCallback.setId(jSONObject3.getString("id"));
                                    }
                                    if (jSONObject3.getInt("season") != -1) {
                                        getEpisdoeDetailsCallback.setSeasonNumber(Integer.valueOf(jSONObject3.getInt("season")));
                                    } else {
                                        getEpisdoeDetailsCallback.setSeasonNumber(-1);
                                    }
                                    if (z) {
                                        if (jSONObject3.getInt("season") == -1 || jSONObject3.getInt("season") == 0) {
                                            seasonsDetailCallback.setSeasonNumber(-1);
                                        } else {
                                            seasonsDetailCallback.setSeasonNumber(Integer.valueOf(jSONObject3.getInt("season")));
                                        }
                                        seasonsDetailCallback.setAirDate("");
                                        seasonsDetailCallback.setEpisodeCount(-1);
                                        seasonsDetailCallback.setId(-1);
                                        seasonsDetailCallback.setName("");
                                        seasonsDetailCallback.setOverview("");
                                        seasonsDetailCallback.setCoverBig("");
                                        seasonsDetailCallback.setCoverBig("");
                                        this.seasonsDetailCallbacks.add(seasonsDetailCallback);
                                        z = false;
                                    }
                                    if (jSONObject3.getString("title") == null || jSONObject3.getString("title").isEmpty()) {
                                        getEpisdoeDetailsCallback.setTitle("");
                                    } else {
                                        getEpisdoeDetailsCallback.setTitle(jSONObject3.getString("title"));
                                    }
                                    if (jSONObject3.getString("direct_source") == null || jSONObject3.getString("direct_source").isEmpty()) {
                                        getEpisdoeDetailsCallback.setDirectSource("");
                                    } else {
                                        getEpisdoeDetailsCallback.setDirectSource(jSONObject3.getString("direct_source"));
                                    }
                                    if (jSONObject3.getString("added") == null || jSONObject3.getString("added").isEmpty()) {
                                        getEpisdoeDetailsCallback.setAdded("");
                                    } else {
                                        getEpisdoeDetailsCallback.setAdded(jSONObject3.getString("added"));
                                    }
                                    if (jSONObject3.getString("custom_sid") == null || jSONObject3.getString("custom_sid").isEmpty()) {
                                        getEpisdoeDetailsCallback.setCustomSid("");
                                    } else {
                                        getEpisdoeDetailsCallback.setCustomSid(jSONObject3.getString("custom_sid"));
                                    }
                                    if (jSONObject3.getString("container_extension") == null || jSONObject3.getString("container_extension").isEmpty()) {
                                        getEpisdoeDetailsCallback.setContainerExtension("");
                                    } else {
                                        getEpisdoeDetailsCallback.setContainerExtension(jSONObject3.getString("container_extension"));
                                    }
                                    this.episdoeDetailsCallbacksList.add(getEpisdoeDetailsCallback);
                                }
                            }
                        }
                    }
                } else if (string.equals("[]")) {
                    JSONObject jSONObject4 = jSONObject.getJSONObject("episodes") != null ? jSONObject.getJSONObject("episodes") : null;
                    this.episdoeDetailsCallbacksList.clear();
                    if (jSONObject4 != null) {
                        Iterator<String> keys2 = jSONObject4.keys();
                        while (keys2.hasNext()) {
                            String next2 = keys2.next();
                            if (jSONObject4.get(next2) instanceof JSONArray) {
                                JSONArray jSONArray2 = new JSONArray(jSONObject4.get(next2).toString());
                                int length2 = jSONArray2.length();
                                boolean z2 = true;
                                for (int i2 = 0; i2 < length2; i2++) {
                                    JSONObject jSONObject5 = jSONArray2.getJSONObject(i2);
                                    GetEpisdoeDetailsCallback getEpisdoeDetailsCallback2 = new GetEpisdoeDetailsCallback();
                                    SeasonsDetailCallback seasonsDetailCallback2 = new SeasonsDetailCallback();
                                    if (jSONObject5.getString("id") == null || jSONObject5.getString("id").isEmpty()) {
                                        getEpisdoeDetailsCallback2.setId("");
                                    } else {
                                        getEpisdoeDetailsCallback2.setId(jSONObject5.getString("id"));
                                    }
                                    if (jSONObject5.getInt("season") != -1) {
                                        getEpisdoeDetailsCallback2.setSeasonNumber(Integer.valueOf(jSONObject5.getInt("season")));
                                    } else {
                                        getEpisdoeDetailsCallback2.setSeasonNumber(-1);
                                    }
                                    if (z2) {
                                        if (jSONObject5.getInt("season") == -1 || jSONObject5.getInt("season") == 0) {
                                            seasonsDetailCallback2.setSeasonNumber(-1);
                                        } else {
                                            seasonsDetailCallback2.setSeasonNumber(Integer.valueOf(jSONObject5.getInt("season")));
                                        }
                                        seasonsDetailCallback2.setAirDate("");
                                        seasonsDetailCallback2.setEpisodeCount(-1);
                                        seasonsDetailCallback2.setId(-1);
                                        seasonsDetailCallback2.setName("");
                                        seasonsDetailCallback2.setOverview("");
                                        seasonsDetailCallback2.setCoverBig("");
                                        seasonsDetailCallback2.setCoverBig("");
                                        this.seasonsDetailCallbacks.add(seasonsDetailCallback2);
                                        z2 = false;
                                    }
                                    if (jSONObject5.getString("title") == null || jSONObject5.getString("title").isEmpty()) {
                                        getEpisdoeDetailsCallback2.setTitle("");
                                    } else {
                                        getEpisdoeDetailsCallback2.setTitle(jSONObject5.getString("title"));
                                    }
                                    if (jSONObject5.getString("direct_source") == null || jSONObject5.getString("direct_source").isEmpty()) {
                                        getEpisdoeDetailsCallback2.setDirectSource("");
                                    } else {
                                        getEpisdoeDetailsCallback2.setDirectSource(jSONObject5.getString("direct_source"));
                                    }
                                    if (jSONObject5.getString("added") == null || jSONObject5.getString("added").isEmpty()) {
                                        getEpisdoeDetailsCallback2.setAdded("");
                                    } else {
                                        getEpisdoeDetailsCallback2.setAdded(jSONObject5.getString("added"));
                                    }
                                    if (jSONObject5.getString("custom_sid") == null || jSONObject5.getString("custom_sid").isEmpty()) {
                                        getEpisdoeDetailsCallback2.setCustomSid("");
                                    } else {
                                        getEpisdoeDetailsCallback2.setCustomSid(jSONObject5.getString("custom_sid"));
                                    }
                                    if (jSONObject5.getString("container_extension") == null || jSONObject5.getString("container_extension").isEmpty()) {
                                        getEpisdoeDetailsCallback2.setContainerExtension("");
                                    } else {
                                        getEpisdoeDetailsCallback2.setContainerExtension(jSONObject5.getString("container_extension"));
                                    }
                                    this.episdoeDetailsCallbacksList.add(getEpisdoeDetailsCallback2);
                                }
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONObject jSONObject6 = new JSONObject(jsonElement.toString());
                JSONObject jSONObject7 = jSONObject6.getJSONObject("episodes") != null ? jSONObject6.getJSONObject("seasons") : null;
                JSONObject jSONObject8 = jSONObject6.getJSONObject("episodes") != null ? jSONObject6.getJSONObject("episodes") : null;
                this.episdoeDetailsCallbacksList.clear();
                Iterator<String> keys3 = jSONObject7.keys();
                while (keys3.hasNext()) {
                    String next3 = keys3.next();
                    if (jSONObject7.get(next3) instanceof JSONObject) {
                        SeasonsDetailCallback seasonsDetailCallback3 = new SeasonsDetailCallback();
                        if (((JSONObject) jSONObject7.get(next3)).get("air_date").toString().equals("null")) {
                            seasonsDetailCallback3.setAirDate("");
                        } else if (((JSONObject) jSONObject7.get(next3)).get("air_date") == null || ((String) ((JSONObject) jSONObject7.get(next3)).get("air_date")).isEmpty()) {
                            seasonsDetailCallback3.setAirDate("");
                        } else {
                            seasonsDetailCallback3.setAirDate((String) ((JSONObject) jSONObject7.get(next3)).get("air_date"));
                        }
                        if (((Integer) ((JSONObject) jSONObject7.get(next3)).get("episode_count")) == null || ((Integer) ((JSONObject) jSONObject7.get(next3)).get("episode_count")).intValue() == -1 || ((Integer) ((JSONObject) jSONObject7.get(next3)).get("episode_count")).intValue() == 0) {
                            seasonsDetailCallback3.setEpisodeCount(-1);
                        } else {
                            seasonsDetailCallback3.setEpisodeCount((Integer) ((JSONObject) jSONObject7.get(next3)).get("episode_count"));
                        }
                        if (((Integer) ((JSONObject) jSONObject7.get(next3)).get("id")) == null || ((Integer) ((JSONObject) jSONObject7.get(next3)).get("id")).intValue() == -1 || ((Integer) ((JSONObject) jSONObject7.get(next3)).get("id")).intValue() == 0) {
                            seasonsDetailCallback3.setId(-1);
                        } else {
                            seasonsDetailCallback3.setId((Integer) ((JSONObject) jSONObject7.get(next3)).get("id"));
                        }
                        if (((String) ((JSONObject) jSONObject7.get(next3)).get("name")) == null || ((String) ((JSONObject) jSONObject7.get(next3)).get("name")).isEmpty()) {
                            seasonsDetailCallback3.setName("");
                        } else {
                            seasonsDetailCallback3.setName((String) ((JSONObject) jSONObject7.get(next3)).get("name"));
                        }
                        if (((String) ((JSONObject) jSONObject7.get(next3)).get("overview")) == null || ((String) ((JSONObject) jSONObject7.get(next3)).get("overview")).isEmpty()) {
                            seasonsDetailCallback3.setOverview("");
                        } else {
                            seasonsDetailCallback3.setOverview((String) ((JSONObject) jSONObject7.get(next3)).get("overview"));
                        }
                        if (((Integer) ((JSONObject) jSONObject7.get(next3)).get(AppConst.SEASON_NUMBER)) == null || ((Integer) ((JSONObject) jSONObject7.get(next3)).get(AppConst.SEASON_NUMBER)).intValue() == -1 || ((Integer) ((JSONObject) jSONObject7.get(next3)).get(AppConst.SEASON_NUMBER)).intValue() == 0) {
                            seasonsDetailCallback3.setSeasonNumber(-1);
                        } else {
                            seasonsDetailCallback3.setSeasonNumber((Integer) ((JSONObject) jSONObject7.get(next3)).get(AppConst.SEASON_NUMBER));
                        }
                        if (((String) ((JSONObject) jSONObject7.get(next3)).get("cover")) == null || ((String) ((JSONObject) jSONObject7.get(next3)).get("cover")).isEmpty()) {
                            seasonsDetailCallback3.setCover("");
                        } else {
                            seasonsDetailCallback3.setCover((String) ((JSONObject) jSONObject7.get(next3)).get("cover"));
                        }
                        if (((String) ((JSONObject) jSONObject7.get(next3)).get("cover_big")) == null || ((String) ((JSONObject) jSONObject7.get(next3)).get("cover_big")).isEmpty()) {
                            seasonsDetailCallback3.setCoverBig("");
                        } else {
                            seasonsDetailCallback3.setCoverBig((String) ((JSONObject) jSONObject7.get(next3)).get("cover_big"));
                        }
                        this.seasonsDetailCallbacks.add(seasonsDetailCallback3);
                    }
                }
                if (jSONObject8 != null) {
                    Iterator<String> keys4 = jSONObject8.keys();
                    while (keys4.hasNext()) {
                        String next4 = keys4.next();
                        if (jSONObject8.get(next4) instanceof JSONArray) {
                            JSONArray jSONArray3 = new JSONArray(jSONObject8.get(next4).toString());
                            for (int i3 = 0; i3 < jSONArray3.length(); i3++) {
                                JSONObject jSONObject9 = jSONArray3.getJSONObject(i3);
                                GetEpisdoeDetailsCallback getEpisdoeDetailsCallback3 = new GetEpisdoeDetailsCallback();
                                if (jSONObject9.getString("id") == null || jSONObject9.getString("id").isEmpty()) {
                                    getEpisdoeDetailsCallback3.setId("");
                                } else {
                                    getEpisdoeDetailsCallback3.setId(jSONObject9.getString("id"));
                                }
                                if (jSONObject9.getInt("season") == -1 || jSONObject9.getInt("season") == 0) {
                                    getEpisdoeDetailsCallback3.setId("");
                                } else {
                                    getEpisdoeDetailsCallback3.setSeasonNumber(Integer.valueOf(jSONObject9.getInt("season")));
                                }
                                if (jSONObject9.getString("title") == null || jSONObject9.getString("title").isEmpty()) {
                                    getEpisdoeDetailsCallback3.setTitle("");
                                } else {
                                    getEpisdoeDetailsCallback3.setTitle(jSONObject9.getString("title"));
                                }
                                if (jSONObject9.getString("direct_source") == null || jSONObject9.getString("direct_source").isEmpty()) {
                                    getEpisdoeDetailsCallback3.setDirectSource("");
                                } else {
                                    getEpisdoeDetailsCallback3.setDirectSource(jSONObject9.getString("direct_source"));
                                }
                                if (jSONObject9.getString("added") == null || jSONObject9.getString("added").isEmpty()) {
                                    getEpisdoeDetailsCallback3.setAdded("");
                                } else {
                                    getEpisdoeDetailsCallback3.setAdded(jSONObject9.getString("added"));
                                }
                                if (jSONObject9.getString("custom_sid") == null || jSONObject9.getString("custom_sid").isEmpty()) {
                                    getEpisdoeDetailsCallback3.setCustomSid("");
                                } else {
                                    getEpisdoeDetailsCallback3.setCustomSid(jSONObject9.getString("custom_sid"));
                                }
                                if (jSONObject9.getString("container_extension") == null || jSONObject9.getString("container_extension").isEmpty()) {
                                    getEpisdoeDetailsCallback3.setContainerExtension("");
                                } else {
                                    getEpisdoeDetailsCallback3.setContainerExtension(jSONObject9.getString("container_extension"));
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
                JSONObject jSONObject10 = new JSONObject(jsonElement.toString());
                JSONArray jSONArray4 = jSONObject10.getJSONArray("seasons");
                JSONObject jSONObject11 = jSONObject10.getJSONObject("episodes") != null ? jSONObject10.getJSONObject("episodes") : null;
                if (jSONObject11.equals(jSONArray4)) {
                    int length3 = jSONArray4.length();
                    this.episdoeDetailsCallbacksList.clear();
                    for (int i4 = 0; i4 < length3; i4++) {
                        SeasonsDetailCallback seasonsDetailCallback4 = new SeasonsDetailCallback();
                        ((JSONObject) jSONArray4.get(i4)).getString("air_date");
                        if (((JSONObject) jSONArray4.get(i4)).getString("air_date") == null || ((JSONObject) jSONArray4.get(i4)).getString("air_date").isEmpty()) {
                            seasonsDetailCallback4.setAirDate("");
                        } else {
                            seasonsDetailCallback4.setAirDate(((JSONObject) jSONArray4.get(i4)).getString("air_date"));
                        }
                        if (((JSONObject) jSONArray4.get(i4)).getString("episode_count") == null || Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt("episode_count")).intValue() == -1 || Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt("episode_count")).intValue() == 0) {
                            seasonsDetailCallback4.setEpisodeCount(-1);
                        } else {
                            seasonsDetailCallback4.setEpisodeCount(Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt("episode_count")));
                        }
                        if (Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt("id")) == null || Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt("id")).intValue() == -1 || Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt("id")).intValue() == 0) {
                            seasonsDetailCallback4.setId(-1);
                        } else {
                            seasonsDetailCallback4.setId(Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt("id")));
                        }
                        if (((JSONObject) jSONArray4.get(i4)).getString("name") == null || ((JSONObject) jSONArray4.get(i4)).getString("name").isEmpty()) {
                            seasonsDetailCallback4.setName("");
                        } else {
                            seasonsDetailCallback4.setName(((JSONObject) jSONArray4.get(i4)).getString("name"));
                        }
                        if (((JSONObject) jSONArray4.get(i4)).getString("overview") == null || ((JSONObject) jSONArray4.get(i4)).getString("overview").isEmpty()) {
                            seasonsDetailCallback4.setOverview("");
                        } else {
                            seasonsDetailCallback4.setOverview(((JSONObject) jSONArray4.get(i4)).getString("overview"));
                        }
                        if (Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt(AppConst.SEASON_NUMBER)) == null || Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt(AppConst.SEASON_NUMBER)).intValue() == -1 || Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt(AppConst.SEASON_NUMBER)).intValue() == 0) {
                            seasonsDetailCallback4.setSeasonNumber(-1);
                        } else {
                            seasonsDetailCallback4.setSeasonNumber(Integer.valueOf(((JSONObject) jSONArray4.get(i4)).getInt(AppConst.SEASON_NUMBER)));
                        }
                        if (((JSONObject) jSONArray4.get(i4)).getString("cover") == null || ((JSONObject) jSONArray4.get(i4)).getString("cover").isEmpty()) {
                            seasonsDetailCallback4.setCover("");
                        } else {
                            seasonsDetailCallback4.setCover(((JSONObject) jSONArray4.get(i4)).getString("cover"));
                        }
                        if (((JSONObject) jSONArray4.get(i4)).getString("cover_big") == null || ((JSONObject) jSONArray4.get(i4)).getString("cover_big").isEmpty()) {
                            seasonsDetailCallback4.setCoverBig("");
                        } else {
                            seasonsDetailCallback4.setCoverBig(((JSONObject) jSONArray4.get(i4)).getString("cover_big"));
                        }
                        this.seasonsDetailCallbacks.add(seasonsDetailCallback4);
                    }
                }
                if (jSONObject11.equals(jSONArray4) && jSONObject11 != null) {
                    Iterator<String> keys5 = jSONObject11.keys();
                    while (keys5.hasNext()) {
                        String next5 = keys5.next();
                        if (jSONObject11.get(next5) instanceof JSONArray) {
                            JSONArray jSONArray5 = new JSONArray(jSONObject11.get(next5).toString());
                            int length4 = jSONArray5.length();
                            for (int i5 = 0; i5 < length4; i5++) {
                                JSONObject jSONObject12 = jSONArray5.getJSONObject(i5);
                                GetEpisdoeDetailsCallback getEpisdoeDetailsCallback4 = new GetEpisdoeDetailsCallback();
                                if (jSONObject12.getString("id") == null || jSONObject12.getString("id").isEmpty()) {
                                    getEpisdoeDetailsCallback4.setId("");
                                } else {
                                    getEpisdoeDetailsCallback4.setId(jSONObject12.getString("id"));
                                }
                                if (jSONObject12.getInt("season") == -1 || jSONObject12.getInt("season") == 0) {
                                    getEpisdoeDetailsCallback4.setId("");
                                } else {
                                    getEpisdoeDetailsCallback4.setSeasonNumber(Integer.valueOf(jSONObject12.getInt("season")));
                                }
                                if (jSONObject12.getString("title") == null || jSONObject12.getString("title").isEmpty()) {
                                    getEpisdoeDetailsCallback4.setTitle("");
                                } else {
                                    getEpisdoeDetailsCallback4.setTitle(jSONObject12.getString("title"));
                                }
                                if (jSONObject12.getString("direct_source") == null || jSONObject12.getString("direct_source").isEmpty()) {
                                    getEpisdoeDetailsCallback4.setDirectSource("");
                                } else {
                                    getEpisdoeDetailsCallback4.setDirectSource(jSONObject12.getString("direct_source"));
                                }
                                if (jSONObject12.getString("added") == null || jSONObject12.getString("added").isEmpty()) {
                                    getEpisdoeDetailsCallback4.setAdded("");
                                } else {
                                    getEpisdoeDetailsCallback4.setAdded(jSONObject12.getString("added"));
                                }
                                if (jSONObject12.getString("custom_sid") == null || jSONObject12.getString("custom_sid").isEmpty()) {
                                    getEpisdoeDetailsCallback4.setCustomSid("");
                                } else {
                                    getEpisdoeDetailsCallback4.setCustomSid(jSONObject12.getString("custom_sid"));
                                }
                                if (jSONObject12.getString("container_extension") == null || jSONObject12.getString("container_extension").isEmpty()) {
                                    getEpisdoeDetailsCallback4.setContainerExtension("");
                                } else {
                                    getEpisdoeDetailsCallback4.setContainerExtension(jSONObject12.getString("container_extension"));
                                }
                                this.episdoeDetailsCallbacksList.add(getEpisdoeDetailsCallback4);
                            }
                        }
                    }
                }
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
            try {
                JSONObject jSONObject13 = new JSONObject(jsonElement.toString());
                JSONArray jSONArray6 = jSONObject13.getJSONArray("seasons");
                String string2 = jSONObject13.getString("seasons");
                if (!jSONArray6.equals("null")) {
                    JSONObject jSONObject14 = jSONObject13.getJSONObject("episodes") != null ? jSONObject13.getJSONObject("episodes") : null;
                    if (!jSONObject14.equals(jSONArray6) && !string2.equals("[]")) {
                        this.episdoeDetailsCallbacksList.clear();
                        if (jSONObject14 != null) {
                            Iterator<String> keys6 = jSONObject14.keys();
                            while (keys6.hasNext()) {
                                int parseInt = Integer.parseInt(keys6.next());
                                String valueOf = String.valueOf(parseInt);
                                if (jSONObject14.get(valueOf) instanceof JSONArray) {
                                    JSONArray jSONArray7 = new JSONArray(jSONObject14.get(valueOf).toString());
                                    if (!jSONObject14.equals(jSONArray6)) {
                                        int length5 = jSONArray7.length();
                                        boolean z3 = true;
                                        for (int i6 = 0; i6 < length5; i6++) {
                                            JSONObject jSONObject15 = jSONArray7.getJSONObject(i6);
                                            GetEpisdoeDetailsCallback getEpisdoeDetailsCallback5 = new GetEpisdoeDetailsCallback();
                                            SeasonsDetailCallback seasonsDetailCallback5 = new SeasonsDetailCallback();
                                            if (jSONObject15.getString("id") == null || jSONObject15.getString("id").isEmpty()) {
                                                getEpisdoeDetailsCallback5.setId("");
                                            } else {
                                                getEpisdoeDetailsCallback5.setId(jSONObject15.getString("id"));
                                            }
                                            if (jSONObject15.getInt("season") != -1) {
                                                getEpisdoeDetailsCallback5.setSeasonNumber(Integer.valueOf(parseInt));
                                            } else {
                                                getEpisdoeDetailsCallback5.setSeasonNumber(-1);
                                            }
                                            if (z3) {
                                                if (jSONObject15.getInt("season") == -1 || jSONObject15.getInt("season") == 0) {
                                                    seasonsDetailCallback5.setSeasonNumber(-1);
                                                } else {
                                                    seasonsDetailCallback5.setSeasonNumber(Integer.valueOf(jSONObject15.getInt("season")));
                                                }
                                                seasonsDetailCallback5.setAirDate("");
                                                seasonsDetailCallback5.setEpisodeCount(-1);
                                                seasonsDetailCallback5.setId(-1);
                                                seasonsDetailCallback5.setName("");
                                                seasonsDetailCallback5.setOverview("");
                                                if (this.seriesCover == null || this.seriesCover.isEmpty() || jSONArray6.isNull(parseInt)) {
                                                    seasonsDetailCallback5.setCoverBig("");
                                                    this.seasonsDetailCallbacks.add(seasonsDetailCallback5);
                                                } else {
                                                    if (((JSONObject) jSONArray6.get(parseInt)).getString("cover") == null || ((JSONObject) jSONArray6.get(parseInt)).getString("cover").isEmpty()) {
                                                        seasonsDetailCallback5.setCover("");
                                                    } else {
                                                        seasonsDetailCallback5.setCover(((JSONObject) jSONArray6.get(parseInt)).getString("cover"));
                                                    }
                                                    if (((JSONObject) jSONArray6.get(parseInt)).getString("cover_big") == null || ((JSONObject) jSONArray6.get(parseInt)).getString("cover_big").isEmpty()) {
                                                        seasonsDetailCallback5.setCoverBig("");
                                                    } else {
                                                        seasonsDetailCallback5.setCoverBig(((JSONObject) jSONArray6.get(parseInt)).getString("cover_big"));
                                                    }
                                                    this.seasonsDetailCallbacks.add(seasonsDetailCallback5);
                                                }
                                                z3 = false;
                                            }
                                            if (jSONObject15.getString("title") == null || jSONObject15.getString("title").isEmpty()) {
                                                getEpisdoeDetailsCallback5.setTitle("");
                                            } else {
                                                getEpisdoeDetailsCallback5.setTitle(jSONObject15.getString("title"));
                                            }
                                            if (jSONObject15.getString("direct_source") == null || jSONObject15.getString("direct_source").isEmpty()) {
                                                getEpisdoeDetailsCallback5.setDirectSource("");
                                            } else {
                                                getEpisdoeDetailsCallback5.setDirectSource(jSONObject15.getString("direct_source"));
                                            }
                                            if (jSONObject15.getString("added") == null || jSONObject15.getString("added").isEmpty()) {
                                                getEpisdoeDetailsCallback5.setAdded("");
                                            } else {
                                                getEpisdoeDetailsCallback5.setAdded(jSONObject15.getString("added"));
                                            }
                                            if (jSONObject15.getString("custom_sid") == null || jSONObject15.getString("custom_sid").isEmpty()) {
                                                getEpisdoeDetailsCallback5.setCustomSid("");
                                            } else {
                                                getEpisdoeDetailsCallback5.setCustomSid(jSONObject15.getString("custom_sid"));
                                            }
                                            if (jSONObject15.getString("container_extension") == null || jSONObject15.getString("container_extension").isEmpty()) {
                                                getEpisdoeDetailsCallback5.setContainerExtension("");
                                            } else {
                                                getEpisdoeDetailsCallback5.setContainerExtension(jSONObject15.getString("container_extension"));
                                            }
                                            this.episdoeDetailsCallbacksList.add(getEpisdoeDetailsCallback5);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (jSONArray6.equals("[]")) {
                    JSONObject jSONObject16 = jSONObject13.getJSONObject("episodes") != null ? jSONObject13.getJSONObject("episodes") : null;
                    this.episdoeDetailsCallbacksList.clear();
                    if (jSONObject16 != null) {
                        Iterator<String> keys7 = jSONObject16.keys();
                        while (keys7.hasNext()) {
                            String next6 = keys7.next();
                            if (jSONObject16.get(next6) instanceof JSONArray) {
                                JSONArray jSONArray8 = new JSONArray(jSONObject16.get(next6).toString());
                                int length6 = jSONArray8.length();
                                boolean z4 = true;
                                for (int i7 = 0; i7 < length6; i7++) {
                                    JSONObject jSONObject17 = jSONArray8.getJSONObject(i7);
                                    GetEpisdoeDetailsCallback getEpisdoeDetailsCallback6 = new GetEpisdoeDetailsCallback();
                                    SeasonsDetailCallback seasonsDetailCallback6 = new SeasonsDetailCallback();
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
                                    if (z4) {
                                        if (jSONObject17.getInt("season") == -1 || jSONObject17.getInt("season") == 0) {
                                            seasonsDetailCallback6.setSeasonNumber(-1);
                                        } else {
                                            seasonsDetailCallback6.setSeasonNumber(Integer.valueOf(jSONObject17.getInt("season")));
                                        }
                                        seasonsDetailCallback6.setAirDate("");
                                        seasonsDetailCallback6.setEpisodeCount(-1);
                                        seasonsDetailCallback6.setId(-1);
                                        seasonsDetailCallback6.setName("");
                                        seasonsDetailCallback6.setOverview("");
                                        seasonsDetailCallback6.setCoverBig("");
                                        seasonsDetailCallback6.setCoverBig("");
                                        this.seasonsDetailCallbacks.add(seasonsDetailCallback6);
                                        z4 = false;
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
                                    this.episdoeDetailsCallbacksList.add(getEpisdoeDetailsCallback6);
                                }
                            }
                        }
                    }
                }
            } catch (JSONException e4) {
                e4.printStackTrace();
            }
            try {
                JSONObject jSONObject18 = new JSONObject(jsonElement.toString());
                String string3 = jSONObject18.getString("seasons");
                JSONArray jSONArray9 = jSONObject18.getJSONArray("episodes") != null ? jSONObject18.getJSONArray("episodes") : null;
                int length7 = jSONArray9.length();
                this.episdoeDetailsCallbacksList.clear();
                if (string3.equals("[]") || string3.equals("null")) {
                    for (int i8 = 0; i8 < length7; i8++) {
                        JSONArray jSONArray10 = new JSONArray(jSONArray9.get(i8).toString());
                        int length8 = jSONArray10.length();
                        boolean z5 = true;
                        for (int i9 = 0; i9 < length8; i9++) {
                            JSONObject jSONObject19 = jSONArray10.getJSONObject(i9);
                            GetEpisdoeDetailsCallback getEpisdoeDetailsCallback7 = new GetEpisdoeDetailsCallback();
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
                                this.seasonsDetailCallbacks.add(seasonsDetailCallback7);
                                z5 = false;
                            }
                        }
                    }
                }
            } catch (JSONException e5) {
                e5.printStackTrace();
            }
            try {
                JSONObject jSONObject20 = new JSONObject(jsonElement.toString());
                JSONArray jSONArray11 = jSONObject20.getJSONArray("episodes");
                jSONObject20.getJSONObject("seasons");
                if (jSONObject20.getJSONArray("episodes") != null) {
                    jSONArray11 = jSONObject20.getJSONArray("episodes");
                }
                int length9 = jSONArray11.length();
                this.episdoeDetailsCallbacksList.clear();
                if (jSONArray11 != null) {
                    for (int i10 = 0; i10 < length9; i10++) {
                        JSONArray jSONArray12 = new JSONArray(jSONArray11.get(i10).toString());
                        int length10 = jSONArray12.length();
                        boolean z6 = true;
                        for (int i11 = 0; i11 < length10; i11++) {
                            JSONObject jSONObject21 = jSONArray12.getJSONObject(i11);
                            GetEpisdoeDetailsCallback getEpisdoeDetailsCallback8 = new GetEpisdoeDetailsCallback();
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
                            this.episdoeDetailsCallbacksList.add(getEpisdoeDetailsCallback8);
                            SeasonsDetailCallback seasonsDetailCallback8 = new SeasonsDetailCallback();
                            if (z6) {
                                if (jSONObject21.getInt("season") != -1) {
                                    seasonsDetailCallback8.setSeasonNumber(Integer.valueOf(jSONObject21.getInt("season")));
                                } else {
                                    seasonsDetailCallback8.setSeasonNumber(-1);
                                }
                                seasonsDetailCallback8.setAirDate("");
                                seasonsDetailCallback8.setEpisodeCount(-1);
                                seasonsDetailCallback8.setId(-1);
                                seasonsDetailCallback8.setName("");
                                seasonsDetailCallback8.setOverview("");
                                seasonsDetailCallback8.setCoverBig("");
                                this.seasonsDetailCallbacks.add(seasonsDetailCallback8);
                                z6 = false;
                            }
                        }
                    }
                }
            } catch (JSONException e6) {
                e6.printStackTrace();
            }
            try {
                JSONObject jSONObject22 = new JSONObject(jsonElement.toString());
                jSONObject22.getJSONArray("seasons");
                JSONArray jSONArray13 = jSONObject22.getJSONArray("episodes") != null ? jSONObject22.getJSONArray("episodes") : null;
                int length11 = jSONArray13.length();
                this.episdoeDetailsCallbacksList.clear();
                if (jSONArray13 != null) {
                    for (int i12 = 0; i12 < length11; i12++) {
                        JSONArray jSONArray14 = new JSONArray(jSONArray13.get(i12).toString());
                        int length12 = jSONArray14.length();
                        boolean z7 = true;
                        for (int i13 = 0; i13 < length12; i13++) {
                            JSONObject jSONObject23 = jSONArray14.getJSONObject(i13);
                            GetEpisdoeDetailsCallback getEpisdoeDetailsCallback9 = new GetEpisdoeDetailsCallback();
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
                            this.episdoeDetailsCallbacksList.add(getEpisdoeDetailsCallback9);
                            SeasonsDetailCallback seasonsDetailCallback9 = new SeasonsDetailCallback();
                            if (z7) {
                                if (jSONObject23.getInt("season") != -1) {
                                    seasonsDetailCallback9.setSeasonNumber(Integer.valueOf(jSONObject23.getInt("season")));
                                } else {
                                    seasonsDetailCallback9.setSeasonNumber(-1);
                                }
                                seasonsDetailCallback9.setAirDate("");
                                seasonsDetailCallback9.setEpisodeCount(-1);
                                seasonsDetailCallback9.setId(-1);
                                seasonsDetailCallback9.setName("");
                                seasonsDetailCallback9.setOverview("");
                                seasonsDetailCallback9.setCoverBig("");
                                seasonsDetailCallback9.setCoverBig("");
                                this.seasonsDetailCallbacks.add(seasonsDetailCallback9);
                                z7 = false;
                            }
                        }
                    }
                }
            } catch (JSONException e7) {
                e7.printStackTrace();
            }
        }
        if (this.context != null) {
            onFinish();
            this.myRecyclerView.setHasFixedSize(true);
            this.mLayoutManager = new GridLayoutManager(this, 2);
            this.myRecyclerView.setLayoutManager(this.mLayoutManager);
            this.myRecyclerView.setVisibility(0);
            if (this.episdoeDetailsCallbacksList == null || this.seasonsDetailCallbacks == null || this.seasonsDetailCallbacks.size() <= 0) {
                this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
                this.myRecyclerView.setAdapter(this.mAdapter);
                this.tvNoRecordFound.setVisibility(0);
                this.tvNoRecordFound.setText(getResources().getString(R.string.no_season_dound));
                return;
            }
            EpisodesUsingSinglton.getInstance().setEpisodeList(this.episdoeDetailsCallbacksList);
            this.mAdapter = new SeasonsAdapter(this.episdoeDetailsCallbacksList, this.seasonsDetailCallbacks, this.context, this.seriesName, this.seriesCover);
            this.myRecyclerView.setItemAnimator(new DefaultItemAnimator());
            this.myRecyclerView.setAdapter(this.mAdapter);
        }
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFinish() {
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(4);
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        if (this.frameLayout != null) {
            this.frameLayout.setVisibility(8);
        }
        if (this.myRecyclerView != null) {
            this.myRecyclerView.setClickable(true);
        }
        if (!(this.mAdapter == null || pbPagingLoader1 == null)) {
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
        if (this.frameLayout != null) {
            this.frameLayout.setVisibility(8);
        }
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
        this.toolbar.inflateMenu(R.menu.menu_search);
        TypedValue typedValue = new TypedValue();
        menu.getItem(2).getSubMenu().findItem(R.id.menu_sort).setVisible(false);
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
                /* class com.nst.yourname.view.activity.SeasonsActivitiy.AnonymousClass3 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.logoutUser(SeasonsActivitiy.this.context);
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeasonsActivitiy.AnonymousClass2 */

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
                /* class com.nst.yourname.view.activity.SeasonsActivitiy.AnonymousClass4 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadChannelsAndVod(SeasonsActivitiy.this.context);
                }
            });
            builder.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeasonsActivitiy.AnonymousClass5 */

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
                /* class com.nst.yourname.view.activity.SeasonsActivitiy.AnonymousClass6 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.loadTvGuid(SeasonsActivitiy.this.context);
                }
            });
            builder2.setNegativeButton(this.context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                /* class com.nst.yourname.view.activity.SeasonsActivitiy.AnonymousClass7 */

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder2.show();
        }
        if (itemId == R.id.action_search) {
            this.searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
            this.searchView.setQueryHint(getResources().getString(R.string.search_seasons));
            this.searchView.setIconifiedByDefault(false);
            this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                /* class com.nst.yourname.view.activity.SeasonsActivitiy.AnonymousClass8 */

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextSubmit(String str) {
                    return false;
                }

                @Override // android.support.v7.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextChange(String str) {
                    SeasonsActivitiy.this.tvNoRecordFound.setVisibility(8);
                    if (SeasonsActivitiy.this.mAdapter == null || SeasonsActivitiy.this.tvNoRecordFound == null || SeasonsActivitiy.this.tvNoRecordFound.getVisibility() == 0) {
                        return false;
                    }
                    SeasonsActivitiy.this.mAdapter.filter(str, SeasonsActivitiy.this.tvNoRecordFound);
                    return false;
                }
            });
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
