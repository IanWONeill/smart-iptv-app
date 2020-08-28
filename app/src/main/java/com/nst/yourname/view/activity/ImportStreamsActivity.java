package com.nst.yourname.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.LiveStreamCategoryIdDBModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.callback.GetSeriesStreamCallback;
import com.nst.yourname.model.callback.GetSeriesStreamCategoriesCallback;
import com.nst.yourname.model.callback.LiveStreamCategoriesCallback;
import com.nst.yourname.model.callback.LiveStreamsCallback;
import com.nst.yourname.model.callback.VodCategoriesCallback;
import com.nst.yourname.model.callback.VodStreamsCallback;
import com.nst.yourname.model.callback.XtreamPanelAPICallback;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.SeriesStreamsDatabaseHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.model.pojo.PanelAvailableChannelsPojo;
import com.nst.yourname.presenter.PlayerApiPresenter;
import com.nst.yourname.presenter.XMLTVPresenter;
import com.nst.yourname.presenter.XtreamPanelAPIPresenter;
import com.nst.yourname.view.interfaces.PlayerApiInterface;
import com.nst.yourname.view.interfaces.XtreamPanelAPIInterface;
import com.nst.yourname.view.utility.LoadingGearSpinner;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public class ImportStreamsActivity extends AppCompatActivity implements PlayerApiInterface, XtreamPanelAPIInterface {
    public static final String REDIRECT_CATCHUP = "redirect_catchup";
    public static final String REDIRECT_LIVE_TV = "redirect_live_tv";
    public static final String REDIRECT_LIVE_TV_EPG_EXPIRED = "redirect_live_tv_epg_expired";
    public static final String REDIRECT_SERIES = "redirect_series";
    public static final String REDIRECT_VOD = "redirect_vod";
    private static String usedcall;
    private static String userdata;
    Context context;
    DatabaseHandler databaseHandler;
    public int errors = 0;
    ArrayList<FavouriteDBModel> favouritlist;
    @BindView(R.id.iv_gear_loader)
    LoadingGearSpinner ivGearLoader;
    LiveStreamDBHandler liveStreamDBHandler;
    LiveStreamDBHandler liveStreamDBHandler1;
    public SharedPreferences loginPreferencesAfterLogin;
    public PlayerApiPresenter playerApiPresenter;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rl_import_layout)
    RelativeLayout rlImportLayout;
    @BindView(R.id.rl_import_process)
    LinearLayout rlImportProcess;
    public SeriesStreamsDatabaseHandler seriesStreamsDatabaseHandler;
    @BindView(R.id.tv_countings)
    TextView tvCountings;
    @BindView(R.id.tv_importing_streams)
    TextView tvImportingStreams;
    @BindView(R.id.tv_percentage)
    TextView tvPercentage;
    @BindView(R.id.tv_setting_streams)
    TextView tvSettingStreams;
    private XMLTVPresenter xmlTvPresenter;
    private XtreamPanelAPIPresenter xtreamPanelAPIPresenter;

    @Override // com.nst.yourname.view.interfaces.BaseInterfaceV2
    public void atProcess() {
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterfaceV2, com.nst.yourname.view.interfaces.BaseInterface
    public void atStart() {
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterfaceV2, com.nst.yourname.view.interfaces.BaseInterface
    public void onFinish() {
    }

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView((int) R.layout.activity_import_m3u);
        ButterKnife.bind(this);
        changeStatusBarColor();
        getWindow().setFlags(1024, 1024);
        this.context = this;
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.seriesStreamsDatabaseHandler = new SeriesStreamsDatabaseHandler(this.context);
        this.xtreamPanelAPIPresenter = new XtreamPanelAPIPresenter(this, this.context);
        this.playerApiPresenter = new PlayerApiPresenter(this.context, this);
        initialize();
    }

    private String decodeUserDat(String str) {
        byte[] decode = Base64.decode(str, 0);
        if (Build.VERSION.SDK_INT >= 19) {
            return new String(decode, StandardCharsets.UTF_8);
        }
        return "";
    }

    private String encodeUserData(String str) {
        byte[] bArr = new byte[0];
        if (Build.VERSION.SDK_INT >= 19) {
            bArr = str.getBytes(StandardCharsets.UTF_8);
        }
        return Base64.encodeToString(bArr, 0);
    }

    public static String getApplicationName(Context context2) {
        return String.valueOf(context2.getApplicationInfo().loadLabel(context2.getPackageManager()));
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        Utils.appResume(this.context);
        getWindow().setFlags(1024, 1024);
    }

    private void initialize() {
        if (this.context != null) {
            this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            String string = this.loginPreferencesAfterLogin.getString("username", "");
            String string2 = this.loginPreferencesAfterLogin.getString("password", "");
            addDatabaseStatusOnSetup();
            getChannelsCategories(this.context, this.liveStreamDBHandler, string, string2);
        }
    }

    private void addDatabaseStatusOnSetup() {
        int dBStatusCount;
        String currentDateValue = currentDateValue();
        if (!(this.liveStreamDBHandler == null || (dBStatusCount = this.liveStreamDBHandler.getDBStatusCount()) == -1 || dBStatusCount != 0)) {
            if (currentDateValue != null) {
                addDBStatus(this.liveStreamDBHandler, currentDateValue);
            } else {
                Utils.showToast(this.context, this.context.getResources().getString(R.string.invalid_current_date));
            }
        }
        addSeriesStreamCatStatus(currentDateValue);
        addSeriesStreamStatus(currentDateValue);
    }

    public String currentDateValue() {
        return Utils.parseDateToddMMyyyy(Calendar.getInstance().getTime().toString());
    }

    private void addDBStatus(LiveStreamDBHandler liveStreamDBHandler2, String str) {
        DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModel = new DatabaseUpdatedStatusDBModel();
        databaseUpdatedStatusDBModel.setDbUpadatedStatusState("");
        databaseUpdatedStatusDBModel.setDbLastUpdatedDate(str);
        databaseUpdatedStatusDBModel.setDbCategory(AppConst.DB_CHANNELS);
        databaseUpdatedStatusDBModel.setDbCategoryID("1");
        liveStreamDBHandler2.addDBUpdatedStatus(databaseUpdatedStatusDBModel);
    }

    private void getChannelsCategories(Context context2, LiveStreamDBHandler liveStreamDBHandler2, String str, String str2) {
        if (str != null && str2 != null && !str.isEmpty() && !str2.isEmpty() && !str.equals("") && !str2.equals("")) {
            liveStreamDBHandler2.updateDBStatus(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_PROCESSING);
            if (Utils.getIsXtream1_06(context2)) {
                this.xtreamPanelAPIPresenter.panelAPI(str, str2);
            } else {
                this.playerApiPresenter.getLiveStreamCat(str, str2);
            }
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

    @Override // com.nst.yourname.view.interfaces.BaseInterfaceV2, com.nst.yourname.view.interfaces.BaseInterface
    public void onFailed(String str) {
        Utils.showToast(this.context, getResources().getString(R.string.network_error));
    }

    @Override // com.nst.yourname.view.interfaces.PlayerApiInterface
    public void getSeriesCategories(List<GetSeriesStreamCategoriesCallback> list) {
        if (list != null) {
            if (this.seriesStreamsDatabaseHandler != null) {
                this.seriesStreamsDatabaseHandler.deleteAndRecreateSeriesCategories();
            }
            if (Build.VERSION.SDK_INT >= 17) {
                new AsyncTask<String, Integer, Boolean>() {
                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1SeriesStreamCat */
                    Context mcontext = null;
                    final List val$getSeriesStreamCategoriesCallback;

                    public void onPreExecute() {
                    }

                    public void onProgressUpdate(Integer... numArr) {
                    }

                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                    {
                        this.val$getSeriesStreamCategoriesCallback = list;
                        this.mcontext = context;
                    }

                    public Boolean doInBackground(String... strArr) {
                        publishProgress(0);
                        if (ImportStreamsActivity.this.seriesStreamsDatabaseHandler != null) {
                            ImportStreamsActivity.this.seriesStreamsDatabaseHandler.addSeriesCategories((ArrayList) this.val$getSeriesStreamCategoriesCallback);
                        }
                        return true;
                    }

                    public void onPostExecute(Boolean bool) {
                        if (ImportStreamsActivity.this.context != null) {
                            String access$100 = ImportStreamsActivity.this.currentDateValue();
                            if (!(access$100 == null || ImportStreamsActivity.this.seriesStreamsDatabaseHandler == null)) {
                                ImportStreamsActivity.this.seriesStreamsDatabaseHandler.updateSeriesStreamsCatDBStatusAndDate(AppConst.DB_SERIES_STREAMS_CAT, AppConst.DB_SERIES_STREAMS_CAT_ID, AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                            }
                            SharedPreferences unused = ImportStreamsActivity.this.loginPreferencesAfterLogin = ImportStreamsActivity.this.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                            final String string = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("username", "");
                            final String string2 = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("password", "");
                            new Handler().postDelayed(new Runnable() {
                                /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1SeriesStreamCat.AnonymousClass1 */

                                public void run() {
                                    ImportStreamsActivity.this.playerApiPresenter.getSeriesStream(string, string2);
                                }
                            }, 1000);
                        }
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
            } else {
                new AsyncTask<String, Integer, Boolean>() {
                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1SeriesStreamCat */
                    Context mcontext = null;
                    final List val$getSeriesStreamCategoriesCallback;

                    public void onPreExecute() {
                    }

                    public void onProgressUpdate(Integer... numArr) {
                    }

                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                    {
                        this.val$getSeriesStreamCategoriesCallback = list;
                        this.mcontext = context;
                    }

                    public Boolean doInBackground(String... strArr) {
                        publishProgress(0);
                        if (ImportStreamsActivity.this.seriesStreamsDatabaseHandler != null) {
                            ImportStreamsActivity.this.seriesStreamsDatabaseHandler.addSeriesCategories((ArrayList) this.val$getSeriesStreamCategoriesCallback);
                        }
                        return true;
                    }

                    public void onPostExecute(Boolean bool) {
                        if (ImportStreamsActivity.this.context != null) {
                            String access$100 = ImportStreamsActivity.this.currentDateValue();
                            if (!(access$100 == null || ImportStreamsActivity.this.seriesStreamsDatabaseHandler == null)) {
                                ImportStreamsActivity.this.seriesStreamsDatabaseHandler.updateSeriesStreamsCatDBStatusAndDate(AppConst.DB_SERIES_STREAMS_CAT, AppConst.DB_SERIES_STREAMS_CAT_ID, AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                            }
                            SharedPreferences unused = ImportStreamsActivity.this.loginPreferencesAfterLogin = ImportStreamsActivity.this.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                            final String string = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("username", "");
                            final String string2 = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("password", "");
                            new Handler().postDelayed(new Runnable() {
                                /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1SeriesStreamCat.AnonymousClass1 */

                                public void run() {
                                    ImportStreamsActivity.this.playerApiPresenter.getSeriesStream(string, string2);
                                }
                            }, 1000);
                        }
                    }
                }.execute(new String[0]);
            }
        } else {
            this.errors++;
            if (this.context != null) {
                String currentDateValue = currentDateValue();
                if (!(currentDateValue == null || this.seriesStreamsDatabaseHandler == null)) {
                    this.seriesStreamsDatabaseHandler.updateSeriesStreamsCatDBStatusAndDate(AppConst.DB_SERIES_STREAMS_CAT, AppConst.DB_SERIES_STREAMS_CAT_ID, AppConst.DB_UPDATED_STATUS_FAILED, currentDateValue);
                }
                this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                final String string = this.loginPreferencesAfterLogin.getString("username", "");
                final String string2 = this.loginPreferencesAfterLogin.getString("password", "");
                new Handler().postDelayed(new Runnable() {
                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1 */

                    public void run() {
                        ImportStreamsActivity.this.playerApiPresenter.getSeriesStream(string, string2);
                    }
                }, 1000);
            }
        }
    }

    @Override // com.nst.yourname.view.interfaces.PlayerApiInterface
    public void getSeriesStreamCatFailed(String str) {
        this.errors++;
        if (this.context != null) {
            String currentDateValue = currentDateValue();
            if (!(currentDateValue == null || this.seriesStreamsDatabaseHandler == null)) {
                this.seriesStreamsDatabaseHandler.updateSeriesStreamsCatDBStatusAndDate(AppConst.DB_SERIES_STREAMS_CAT, AppConst.DB_SERIES_STREAMS_CAT_ID, AppConst.DB_UPDATED_STATUS_FAILED, currentDateValue);
            }
            this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            final String string = this.loginPreferencesAfterLogin.getString("username", "");
            final String string2 = this.loginPreferencesAfterLogin.getString("password", "");
            new Handler().postDelayed(new Runnable() {
                /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass2 */

                public void run() {
                    ImportStreamsActivity.this.playerApiPresenter.getSeriesStream(string, string2);
                }
            }, 1000);
        }
    }

    @Override // com.nst.yourname.view.interfaces.PlayerApiInterface
    public void getSeriesStreams(List<GetSeriesStreamCallback> list) {
        if (list != null) {
            if (this.seriesStreamsDatabaseHandler != null) {
                this.seriesStreamsDatabaseHandler.deleteAndRecreateSeriesStreams();
            }
            if (Build.VERSION.SDK_INT >= 17) {
                new AsyncTask<String, Integer, Boolean>() {
                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1SeriesStream */
                    Context mcontext = null;
                    final List val$getSeriesStreamCallback;

                    public void onPreExecute() {
                    }

                    public void onProgressUpdate(Integer... numArr) {
                    }

                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                    {
                        this.val$getSeriesStreamCallback = list;
                        this.mcontext = context;
                    }

                    public Boolean doInBackground(String... strArr) {
                        publishProgress(0);
                        if (ImportStreamsActivity.this.seriesStreamsDatabaseHandler != null) {
                            ImportStreamsActivity.this.seriesStreamsDatabaseHandler.addAllSeriesStreams((ArrayList) this.val$getSeriesStreamCallback);
                        }
                        return true;
                    }

                    public void onPostExecute(Boolean bool) {
                        String access$100 = ImportStreamsActivity.this.currentDateValue();
                        if (!(access$100 == null || ImportStreamsActivity.this.seriesStreamsDatabaseHandler == null)) {
                            ImportStreamsActivity.this.seriesStreamsDatabaseHandler.updateseriesStreamsDBStatusAndDate(AppConst.DB_SERIES_STREAMS, AppConst.DB_SERIES_STREAMS_ID, AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                        }
                        if (ImportStreamsActivity.this.errors > 0) {
                            Utils.showToast(ImportStreamsActivity.this.context, ImportStreamsActivity.this.getResources().getString(R.string.network_error));
                        }
                        if (ImportStreamsActivity.this.context != null) {
                            String action = ImportStreamsActivity.this.getIntent().getAction();
                            if ("redirect_live_tv".equals(action)) {
                                ImportStreamsActivity.this.CheckFavouriteData();
                                ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, LiveActivityNewFlow.class));
                                ImportStreamsActivity.this.finish();
                            } else if ("redirect_vod".equals(action)) {
                                ImportStreamsActivity.this.CheckFavouriteData();
                                ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, VodActivityNewFlow.class));
                                ImportStreamsActivity.this.finish();
                            } else if ("redirect_catchup".equals(action)) {
                                ImportStreamsActivity.this.CheckFavouriteData();
                                ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, TVArchiveActivityNewFlow.class));
                                ImportStreamsActivity.this.finish();
                            } else if ("redirect_series".equals(action)) {
                                ImportStreamsActivity.this.CheckFavouriteData();
                                ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, SeriesActivtyNewFlow.class));
                                ImportStreamsActivity.this.finish();
                            } else if ("redirect_live_tv_epg_expired".equals(action)) {
                                ImportStreamsActivity.this.CheckFavouriteData();
                                Intent intent = new Intent(ImportStreamsActivity.this.context, ImportEPGActivity.class);
                                intent.setAction("redirect_live_tv_epg_expired");
                                ImportStreamsActivity.this.startActivity(intent);
                                ImportStreamsActivity.this.finish();
                            } else {
                                ImportStreamsActivity.this.CheckFavouriteData();
                                ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, NewDashboardActivity.class));
                                ImportStreamsActivity.this.finish();
                            }
                        }
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
            } else {
                new AsyncTask<String, Integer, Boolean>() {
                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1SeriesStream */
                    Context mcontext = null;
                    final List val$getSeriesStreamCallback;

                    public void onPreExecute() {
                    }

                    public void onProgressUpdate(Integer... numArr) {
                    }

                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                    {
                        this.val$getSeriesStreamCallback = list;
                        this.mcontext = context;
                    }

                    public Boolean doInBackground(String... strArr) {
                        publishProgress(0);
                        if (ImportStreamsActivity.this.seriesStreamsDatabaseHandler != null) {
                            ImportStreamsActivity.this.seriesStreamsDatabaseHandler.addAllSeriesStreams((ArrayList) this.val$getSeriesStreamCallback);
                        }
                        return true;
                    }

                    public void onPostExecute(Boolean bool) {
                        String access$100 = ImportStreamsActivity.this.currentDateValue();
                        if (!(access$100 == null || ImportStreamsActivity.this.seriesStreamsDatabaseHandler == null)) {
                            ImportStreamsActivity.this.seriesStreamsDatabaseHandler.updateseriesStreamsDBStatusAndDate(AppConst.DB_SERIES_STREAMS, AppConst.DB_SERIES_STREAMS_ID, AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                        }
                        if (ImportStreamsActivity.this.errors > 0) {
                            Utils.showToast(ImportStreamsActivity.this.context, ImportStreamsActivity.this.getResources().getString(R.string.network_error));
                        }
                        if (ImportStreamsActivity.this.context != null) {
                            String action = ImportStreamsActivity.this.getIntent().getAction();
                            if ("redirect_live_tv".equals(action)) {
                                ImportStreamsActivity.this.CheckFavouriteData();
                                ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, LiveActivityNewFlow.class));
                                ImportStreamsActivity.this.finish();
                            } else if ("redirect_vod".equals(action)) {
                                ImportStreamsActivity.this.CheckFavouriteData();
                                ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, VodActivityNewFlow.class));
                                ImportStreamsActivity.this.finish();
                            } else if ("redirect_catchup".equals(action)) {
                                ImportStreamsActivity.this.CheckFavouriteData();
                                ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, TVArchiveActivityNewFlow.class));
                                ImportStreamsActivity.this.finish();
                            } else if ("redirect_series".equals(action)) {
                                ImportStreamsActivity.this.CheckFavouriteData();
                                ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, SeriesActivtyNewFlow.class));
                                ImportStreamsActivity.this.finish();
                            } else if ("redirect_live_tv_epg_expired".equals(action)) {
                                ImportStreamsActivity.this.CheckFavouriteData();
                                Intent intent = new Intent(ImportStreamsActivity.this.context, ImportEPGActivity.class);
                                intent.setAction("redirect_live_tv_epg_expired");
                                ImportStreamsActivity.this.startActivity(intent);
                                ImportStreamsActivity.this.finish();
                            } else {
                                ImportStreamsActivity.this.CheckFavouriteData();
                                ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, NewDashboardActivity.class));
                                ImportStreamsActivity.this.finish();
                            }
                        }
                    }
                }.execute(new String[0]);
            }
        } else {
            this.errors++;
            if (this.context != null) {
                Intent intent = getIntent();
                if (this.errors > 0) {
                    Utils.showToast(this.context, getResources().getString(R.string.network_error));
                }
                this.seriesStreamsDatabaseHandler.updateSeriesStreamsCatDBStatusAndDate(AppConst.DB_SERIES_STREAMS, AppConst.DB_SERIES_STREAMS_ID, AppConst.DB_UPDATED_STATUS_FAILED, currentDateValue());
                String action = intent.getAction();
                CheckFavouriteData();
                if ("redirect_live_tv".equals(action)) {
                    startActivity(new Intent(this.context, LiveActivityNewFlow.class));
                    finish();
                } else if ("redirect_vod".equals(action)) {
                    startActivity(new Intent(this.context, VodActivityNewFlow.class));
                    finish();
                } else if ("redirect_series".equals(action)) {
                    startActivity(new Intent(this.context, SeriesActivtyNewFlow.class));
                    finish();
                } else if ("redirect_catchup".equals(action)) {
                    startActivity(new Intent(this.context, TVArchiveActivityNewFlow.class));
                    finish();
                } else if ("redirect_live_tv_epg_expired".equals(action)) {
                    Intent intent2 = new Intent(this.context, ImportEPGActivity.class);
                    intent2.setAction("redirect_live_tv_epg_expired");
                    startActivity(intent2);
                    finish();
                } else {
                    startActivity(new Intent(this.context, NewDashboardActivity.class));
                    finish();
                }
            }
        }
    }

    @Override // com.nst.yourname.view.interfaces.PlayerApiInterface
    public void getSeriesStreamsFailed(String str) {
        this.errors++;
        if (this.context != null) {
            Intent intent = getIntent();
            if (this.errors > 0) {
                Utils.showToast(this.context, getResources().getString(R.string.network_error));
            }
            this.seriesStreamsDatabaseHandler.updateSeriesStreamsCatDBStatusAndDate(AppConst.DB_SERIES_STREAMS, AppConst.DB_SERIES_STREAMS_ID, AppConst.DB_UPDATED_STATUS_FAILED, currentDateValue());
            String action = intent.getAction();
            CheckFavouriteData();
            if ("redirect_live_tv".equals(action)) {
                startActivity(new Intent(this.context, LiveActivityNewFlow.class));
                finish();
            } else if ("redirect_vod".equals(action)) {
                startActivity(new Intent(this.context, VodActivityNewFlow.class));
                finish();
            } else if ("redirect_series".equals(action)) {
                startActivity(new Intent(this.context, SeriesActivtyNewFlow.class));
                finish();
            } else if ("redirect_catchup".equals(action)) {
                startActivity(new Intent(this.context, TVArchiveActivityNewFlow.class));
                finish();
            } else if ("redirect_live_tv_epg_expired".equals(action)) {
                Intent intent2 = new Intent(this.context, ImportEPGActivity.class);
                intent2.setAction("redirect_live_tv_epg_expired");
                startActivity(intent2);
                finish();
            } else {
                startActivity(new Intent(this.context, NewDashboardActivity.class));
                finish();
            }
        }
    }

    @Override // com.nst.yourname.view.interfaces.PlayerApiInterface
    public void getLiveStreamCategories(List<LiveStreamCategoriesCallback> list) {
        if (list != null) {
            if (this.liveStreamDBHandler != null) {
                this.liveStreamDBHandler.makeEmptyLiveCategory();
            }
            int size = list.size();
            if (Build.VERSION.SDK_INT >= 17) {
                new AsyncTask<String, Integer, Boolean>() {
                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1LiveCategoriesAsyncTask */
                    int val$finalTotalLiveCategories;

                    final int ITERATIONS = this.val$finalTotalLiveCategories;
                    Context mcontext = null;
                    final List val$liveStreamCategoriesCallback;

                    public void onPreExecute() {
                    }

                    public void onProgressUpdate(Integer... numArr) {
                    }

                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                    {
                        this.val$finalTotalLiveCategories = size;
                        this.val$liveStreamCategoriesCallback = list;
                        this.mcontext = context;
                    }

                    public Boolean doInBackground(String... strArr) {
                        publishProgress(0);
                        if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                            ImportStreamsActivity.this.liveStreamDBHandler.addLiveCategories(this.val$liveStreamCategoriesCallback);
                        }
                        return true;
                    }

                    public void onPostExecute(Boolean bool) {
                        if (ImportStreamsActivity.this.context != null) {
                            SharedPreferences unused = ImportStreamsActivity.this.loginPreferencesAfterLogin = ImportStreamsActivity.this.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                            final String string = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("username", "");
                            final String string2 = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("password", "");
                            if (string != null && !string.isEmpty() && string2 != null && !string2.isEmpty()) {
                                new Handler().postDelayed(new Runnable() {
                                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1LiveCategoriesAsyncTask.AnonymousClass1 */

                                    public void run() {
                                        ImportStreamsActivity.this.playerApiPresenter.getLiveStreams(string, string2);
                                    }
                                }, 1000);
                            }
                        }
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
            } else {
                new AsyncTask<String, Integer, Boolean>() {
                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1LiveCategoriesAsyncTask */
                    int val$finalTotalLiveCategories;

                    final int ITERATIONS = this.val$finalTotalLiveCategories;
                    Context mcontext = null;
                    final List val$liveStreamCategoriesCallback;

                    public void onPreExecute() {
                    }

                    public void onProgressUpdate(Integer... numArr) {
                    }

                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                    {
                        this.val$finalTotalLiveCategories = size;
                        this.val$liveStreamCategoriesCallback = list;
                        this.mcontext = context;
                    }

                    public Boolean doInBackground(String... strArr) {
                        publishProgress(0);
                        if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                            ImportStreamsActivity.this.liveStreamDBHandler.addLiveCategories(this.val$liveStreamCategoriesCallback);
                        }
                        return true;
                    }

                    public void onPostExecute(Boolean bool) {
                        if (ImportStreamsActivity.this.context != null) {
                            SharedPreferences unused = ImportStreamsActivity.this.loginPreferencesAfterLogin = ImportStreamsActivity.this.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                            final String string = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("username", "");
                            final String string2 = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("password", "");
                            if (string != null && !string.isEmpty() && string2 != null && !string2.isEmpty()) {
                                new Handler().postDelayed(new Runnable() {
                                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1LiveCategoriesAsyncTask.AnonymousClass1 */

                                    public void run() {
                                        ImportStreamsActivity.this.playerApiPresenter.getLiveStreams(string, string2);
                                    }
                                }, 1000);
                            }
                        }
                    }
                }.execute(new String[0]);
            }
        } else {
            this.errors++;
            if (this.context != null) {
                this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                final String string = this.loginPreferencesAfterLogin.getString("username", "");
                final String string2 = this.loginPreferencesAfterLogin.getString("password", "");
                if (string != null && !string.isEmpty() && string2 != null && !string2.isEmpty()) {
                    new Handler().postDelayed(new Runnable() {
                        /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass3 */

                        public void run() {
                            ImportStreamsActivity.this.playerApiPresenter.getLiveStreams(string, string2);
                        }
                    }, 1000);
                }
            }
        }
    }

    @Override // com.nst.yourname.view.interfaces.PlayerApiInterface
    public void getLiveStreamCatFailed(String str) {
        this.errors++;
        if (this.context != null) {
            this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            final String string = this.loginPreferencesAfterLogin.getString("username", "");
            final String string2 = this.loginPreferencesAfterLogin.getString("password", "");
            if (string != null && !string.isEmpty() && string2 != null && !string2.isEmpty()) {
                new Handler().postDelayed(new Runnable() {
                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass4 */

                    public void run() {
                        ImportStreamsActivity.this.playerApiPresenter.getLiveStreams(string, string2);
                    }
                }, 1000);
            }
        }
    }

    @Override // com.nst.yourname.view.interfaces.PlayerApiInterface
    public void getVODStreamCategories(List<VodCategoriesCallback> list) {
        if (list != null) {
            if (this.liveStreamDBHandler != null) {
                this.liveStreamDBHandler.makeEmptyMovieCategory();
            }
            int size = list.size();
            if (Build.VERSION.SDK_INT >= 17) {
                new AsyncTask<String, Integer, Boolean>() {
                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1VodCategoriesAsyncTask */
                    int val$finalTotalLiveCategories;

                    final int ITERATIONS = this.val$finalTotalLiveCategories;
                    Context mcontext = null;
                    final List val$vodCategoriesCallback;

                    public void onPreExecute() {
                    }

                    public void onProgressUpdate(Integer... numArr) {
                    }

                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                    {
                        this.val$finalTotalLiveCategories = size;
                        this.val$vodCategoriesCallback = list;
                        this.mcontext = context;
                    }

                    public Boolean doInBackground(String... strArr) {
                        publishProgress(0);
                        if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                            ImportStreamsActivity.this.liveStreamDBHandler.addMovieCategories(this.val$vodCategoriesCallback);
                        }
                        return true;
                    }

                    public void onPostExecute(Boolean bool) {
                        if (ImportStreamsActivity.this.context != null) {
                            SharedPreferences unused = ImportStreamsActivity.this.loginPreferencesAfterLogin = ImportStreamsActivity.this.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                            final String string = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("username", "");
                            final String string2 = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("password", "");
                            if (string != null && !string.isEmpty() && string2 != null && !string2.isEmpty()) {
                                new Handler().postDelayed(new Runnable() {
                                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1VodCategoriesAsyncTask.AnonymousClass1 */

                                    public void run() {
                                        ImportStreamsActivity.this.playerApiPresenter.getVODStreams(string, string2);
                                    }
                                }, 1000);
                            }
                        }
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
            } else {
                new AsyncTask<String, Integer, Boolean>() {
                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1VodCategoriesAsyncTask */
                    int val$finalTotalLiveCategories;

                    final int ITERATIONS = this.val$finalTotalLiveCategories;
                    Context mcontext = null;
                    final List val$vodCategoriesCallback;

                    public void onPreExecute() {
                    }

                    public void onProgressUpdate(Integer... numArr) {
                    }

                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                    {
                        this.val$finalTotalLiveCategories = size;
                        this.val$vodCategoriesCallback = list;
                        this.mcontext = context;
                    }

                    public Boolean doInBackground(String... strArr) {
                        publishProgress(0);
                        if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                            ImportStreamsActivity.this.liveStreamDBHandler.addMovieCategories(this.val$vodCategoriesCallback);
                        }
                        return true;
                    }

                    public void onPostExecute(Boolean bool) {
                        if (ImportStreamsActivity.this.context != null) {
                            SharedPreferences unused = ImportStreamsActivity.this.loginPreferencesAfterLogin = ImportStreamsActivity.this.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                            final String string = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("username", "");
                            final String string2 = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("password", "");
                            if (string != null && !string.isEmpty() && string2 != null && !string2.isEmpty()) {
                                new Handler().postDelayed(new Runnable() {
                                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1VodCategoriesAsyncTask.AnonymousClass1 */

                                    public void run() {
                                        ImportStreamsActivity.this.playerApiPresenter.getVODStreams(string, string2);
                                    }
                                }, 1000);
                            }
                        }
                    }
                }.execute(new String[0]);
            }
        } else {
            this.errors++;
            if (this.context != null) {
                this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                final String string = this.loginPreferencesAfterLogin.getString("username", "");
                final String string2 = this.loginPreferencesAfterLogin.getString("password", "");
                if (string != null && !string.isEmpty() && string2 != null && !string2.isEmpty()) {
                    new Handler().postDelayed(new Runnable() {
                        /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass5 */

                        public void run() {
                            ImportStreamsActivity.this.playerApiPresenter.getVODStreams(string, string2);
                        }
                    }, 1000);
                }
            }
        }
    }

    @Override // com.nst.yourname.view.interfaces.PlayerApiInterface
    public void getVODStreamCatFailed(String str) {
        this.errors++;
        if (this.context != null) {
            this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            final String string = this.loginPreferencesAfterLogin.getString("username", "");
            final String string2 = this.loginPreferencesAfterLogin.getString("password", "");
            if (string != null && !string.isEmpty() && string2 != null && !string2.isEmpty()) {
                new Handler().postDelayed(new Runnable() {
                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass6 */

                    public void run() {
                        ImportStreamsActivity.this.playerApiPresenter.getVODStreams(string, string2);
                    }
                }, 1000);
            }
        }
    }

    @Override // com.nst.yourname.view.interfaces.PlayerApiInterface
    public void getLiveStreams(List<LiveStreamsCallback> list) {
        if (list != null) {
            if (this.liveStreamDBHandler != null) {
                this.liveStreamDBHandler.makeEmptyLiveStreams();
            }
            int size = list.size();
            if (Build.VERSION.SDK_INT >= 17) {
                new AsyncTask<String, Integer, Boolean>() {
                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1LiveStreamsAsyncTask */
                    int val$finalTotalLiveStreams;

                    int ITERATIONS = this.val$finalTotalLiveStreams;
                    Context mcontext = null;
                    final List val$liveStreamsCallbacks;

                    public void onPreExecute() {
                    }

                    public void onProgressUpdate(Integer... numArr) {
                    }

                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                    {
                        this.val$finalTotalLiveStreams = size;
                        this.val$liveStreamsCallbacks = list;
                        this.mcontext = context;
                    }

                    public Boolean doInBackground(String... strArr) {
                        publishProgress(0);
                        if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                            ImportStreamsActivity.this.liveStreamDBHandler.addAllAvailableChannel(this.val$liveStreamsCallbacks);
                        }
                        return true;
                    }

                    public void onPostExecute(Boolean bool) {
                        if (ImportStreamsActivity.this.context != null) {
                            String access$100 = ImportStreamsActivity.this.currentDateValue();
                            if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                            }
                            final String string = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("username", "");
                            final String string2 = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("password", "");
                            if (string != null && !string.isEmpty() && string2 != null && !string2.isEmpty()) {
                                new Handler().postDelayed(new Runnable() {
                                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1LiveStreamsAsyncTask.AnonymousClass1 */

                                    public void run() {
                                        ImportStreamsActivity.this.playerApiPresenter.getVODStreamCat(string, string2);
                                    }
                                }, 1000);
                            }
                        }
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
            } else {
                new AsyncTask<String, Integer, Boolean>() {
                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1LiveStreamsAsyncTask */
                    int val$finalTotalLiveStreams;

                    final int ITERATIONS = this.val$finalTotalLiveStreams;
                    Context mcontext = null;
                    final List val$liveStreamsCallbacks;

                    public void onPreExecute() {
                    }

                    public void onProgressUpdate(Integer... numArr) {
                    }

                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                    {
                        this.val$finalTotalLiveStreams = size;
                        this.val$liveStreamsCallbacks = list;
                        this.mcontext = context;
                    }

                    public Boolean doInBackground(String... strArr) {
                        publishProgress(0);
                        if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                            ImportStreamsActivity.this.liveStreamDBHandler.addAllAvailableChannel(this.val$liveStreamsCallbacks);
                        }
                        return true;
                    }

                    public void onPostExecute(Boolean bool) {
                        if (ImportStreamsActivity.this.context != null) {
                            String access$100 = ImportStreamsActivity.this.currentDateValue();
                            if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                            }
                            final String string = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("username", "");
                            final String string2 = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("password", "");
                            if (string != null && !string.isEmpty() && string2 != null && !string2.isEmpty()) {
                                new Handler().postDelayed(new Runnable() {
                                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1LiveStreamsAsyncTask.AnonymousClass1 */

                                    public void run() {
                                        ImportStreamsActivity.this.playerApiPresenter.getVODStreamCat(string, string2);
                                    }
                                }, 1000);
                            }
                        }
                    }
                }.execute(new String[0]);
            }
        } else {
            this.errors++;
            if (this.context != null) {
                String currentDateValue = currentDateValue();
                if (!(currentDateValue == null || this.liveStreamDBHandler == null)) {
                    this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FAILED, currentDateValue);
                }
                final String string = this.loginPreferencesAfterLogin.getString("username", "");
                final String string2 = this.loginPreferencesAfterLogin.getString("password", "");
                if (string != null && !string.isEmpty() && string2 != null && !string2.isEmpty()) {
                    new Handler().postDelayed(new Runnable() {
                        /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass7 */

                        public void run() {
                            ImportStreamsActivity.this.playerApiPresenter.getVODStreamCat(string, string2);
                        }
                    }, 1000);
                }
            }
        }
    }

    @Override // com.nst.yourname.view.interfaces.PlayerApiInterface
    public void getLiveStreamFailed(String str) {
        this.errors++;
        if (this.context != null) {
            String currentDateValue = currentDateValue();
            if (!(currentDateValue == null || this.liveStreamDBHandler == null)) {
                this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FAILED, currentDateValue);
            }
            final String string = this.loginPreferencesAfterLogin.getString("username", "");
            final String string2 = this.loginPreferencesAfterLogin.getString("password", "");
            if (string != null && !string.isEmpty() && string2 != null && !string2.isEmpty()) {
                new Handler().postDelayed(new Runnable() {
                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass8 */

                    public void run() {
                        ImportStreamsActivity.this.playerApiPresenter.getVODStreamCat(string, string2);
                    }
                }, 1000);
            }
        }
    }

    @Override // com.nst.yourname.view.interfaces.PlayerApiInterface
    public void getVODStreams(List<VodStreamsCallback> list) {
        if (list != null) {
            if (this.liveStreamDBHandler != null) {
                this.liveStreamDBHandler.makeEmptyVODStreams();
            }
            int size = list.size();
            if (Build.VERSION.SDK_INT >= 17) {
                new AsyncTask<String, Integer, Boolean>() {
                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1VodStreamsAsyncTask */
                    int val$finalTotalVODStreams;

                    final int ITERATIONS = this.val$finalTotalVODStreams;
                    Context mcontext = null;
                    final List val$vodStreamsCallbacks;

                    public void onPreExecute() {
                    }

                    public void onProgressUpdate(Integer... numArr) {
                    }

                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                    {
                        this.val$finalTotalVODStreams = size;
                        this.val$vodStreamsCallbacks = list;
                        this.mcontext = context;
                    }

                    public Boolean doInBackground(String... strArr) {
                        publishProgress(0);
                        if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                            ImportStreamsActivity.this.liveStreamDBHandler.addAllAvailableMovies(this.val$vodStreamsCallbacks);
                        }
                        return true;
                    }

                    public void onPostExecute(Boolean bool) {
                        if (ImportStreamsActivity.this.context != null) {
                            String access$100 = ImportStreamsActivity.this.currentDateValue();
                            if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                            }
                            SharedPreferences unused = ImportStreamsActivity.this.loginPreferencesAfterLogin = ImportStreamsActivity.this.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                            String string = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("username", "");
                            String string2 = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("password", "");
                            if (string != null && !string.isEmpty() && string2 != null && !string2.isEmpty()) {
                                ImportStreamsActivity.this.playerApiPresenter.getSeriesStreamCat(string, string2);
                            }
                        }
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
            } else {
                new AsyncTask<String, Integer, Boolean>() {
                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1VodStreamsAsyncTask */
                    int val$finalTotalVODStreams;
                    final int ITERATIONS = this.val$finalTotalVODStreams;
                    Context mcontext = null;
                    final List val$vodStreamsCallbacks;

                    public void onPreExecute() {
                    }

                    public void onProgressUpdate(Integer... numArr) {
                    }

                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                    {
                        this.val$finalTotalVODStreams = size;
                        this.val$vodStreamsCallbacks = list;
                        this.mcontext = context;
                    }

                    public Boolean doInBackground(String... strArr) {
                        publishProgress(0);
                        if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                            ImportStreamsActivity.this.liveStreamDBHandler.addAllAvailableMovies(this.val$vodStreamsCallbacks);
                        }
                        return true;
                    }

                    public void onPostExecute(Boolean bool) {
                        if (ImportStreamsActivity.this.context != null) {
                            String access$100 = ImportStreamsActivity.this.currentDateValue();
                            if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                            }
                            SharedPreferences unused = ImportStreamsActivity.this.loginPreferencesAfterLogin = ImportStreamsActivity.this.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                            String string = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("username", "");
                            String string2 = ImportStreamsActivity.this.loginPreferencesAfterLogin.getString("password", "");
                            if (string != null && !string.isEmpty() && string2 != null && !string2.isEmpty()) {
                                ImportStreamsActivity.this.playerApiPresenter.getSeriesStreamCat(string, string2);
                            }
                        }
                    }
                }.execute(new String[0]);
            }
        } else {
            this.errors++;
            if (this.context != null) {
                String currentDateValue = currentDateValue();
                if (!(currentDateValue == null || this.liveStreamDBHandler == null)) {
                    this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FAILED, currentDateValue);
                }
                this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                String string = this.loginPreferencesAfterLogin.getString("username", "");
                String string2 = this.loginPreferencesAfterLogin.getString("password", "");
                if (string != null && !string.isEmpty() && string2 != null && !string2.isEmpty()) {
                    this.playerApiPresenter.getSeriesStreamCat(string, string2);
                }
            }
        }
    }

    @Override // com.nst.yourname.view.interfaces.PlayerApiInterface
    public void getVODStreamsFailed(String str) {
        this.errors++;
        if (this.context != null) {
            String currentDateValue = currentDateValue();
            if (!(currentDateValue == null || this.liveStreamDBHandler == null)) {
                this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FAILED, currentDateValue);
            }
            this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            String string = this.loginPreferencesAfterLogin.getString("username", "");
            String string2 = this.loginPreferencesAfterLogin.getString("password", "");
            if (string != null && !string.isEmpty() && string2 != null && !string2.isEmpty()) {
                this.playerApiPresenter.getSeriesStreamCat(string, string2);
            }
        }
    }

    private void addSeriesStreamCatStatus(String str) {
        int seriesStreamsCatDBStatusCount;
        if (this.seriesStreamsDatabaseHandler != null && (seriesStreamsCatDBStatusCount = this.seriesStreamsDatabaseHandler.getSeriesStreamsCatDBStatusCount()) != -1 && seriesStreamsCatDBStatusCount == 0) {
            if (str != null) {
                addSeriesStreamCatDBStatus(this.seriesStreamsDatabaseHandler, str);
            } else {
                Utils.showToast(this.context, this.context.getResources().getString(R.string.invalid_current_date));
            }
        }
    }

    private void addSeriesStreamStatus(String str) {
        int seriesStreamsDBStatusCount;
        if (this.seriesStreamsDatabaseHandler != null && (seriesStreamsDBStatusCount = this.seriesStreamsDatabaseHandler.getSeriesStreamsDBStatusCount()) != -1 && seriesStreamsDBStatusCount == 0) {
            if (str != null) {
                addSeriesStreamDBStatus(this.seriesStreamsDatabaseHandler, str);
            } else {
                Utils.showToast(this.context, this.context.getResources().getString(R.string.invalid_current_date));
            }
        }
    }

    private void addSeriesStreamCatDBStatus(SeriesStreamsDatabaseHandler seriesStreamsDatabaseHandler2, String str) {
        DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModel = new DatabaseUpdatedStatusDBModel();
        databaseUpdatedStatusDBModel.setDbUpadatedStatusState("");
        databaseUpdatedStatusDBModel.setDbLastUpdatedDate(str);
        databaseUpdatedStatusDBModel.setDbCategory(AppConst.DB_SERIES_STREAMS_CAT);
        databaseUpdatedStatusDBModel.setDbCategoryID(AppConst.DB_SERIES_STREAMS_CAT_ID);
        seriesStreamsDatabaseHandler2.addSeriesStreamsCatStatus(databaseUpdatedStatusDBModel);
    }

    private void addSeriesStreamDBStatus(SeriesStreamsDatabaseHandler seriesStreamsDatabaseHandler2, String str) {
        DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModel = new DatabaseUpdatedStatusDBModel();
        databaseUpdatedStatusDBModel.setDbUpadatedStatusState("");
        databaseUpdatedStatusDBModel.setDbLastUpdatedDate(str);
        databaseUpdatedStatusDBModel.setDbCategory(AppConst.DB_SERIES_STREAMS);
        databaseUpdatedStatusDBModel.setDbCategoryID(AppConst.DB_SERIES_STREAMS_ID);
        seriesStreamsDatabaseHandler2.addSeriesStreamsCatStatus(databaseUpdatedStatusDBModel);
    }

    public void CheckFavouriteData() {
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.databaseHandler = new DatabaseHandler(this.context);
        this.favouritlist = this.databaseHandler.getAllFavourites("live", SharepreferenceDBHandler.getUserID(this.context));
        if (this.favouritlist != null && this.favouritlist.size() > 0) {
            for (int i = 0; i < this.favouritlist.size(); i++) {
                int streamID = this.favouritlist.get(i).getStreamID();
                if (!Boolean.valueOf(this.liveStreamDBHandler.isExistFavourite(streamID)).booleanValue()) {
                    this.databaseHandler.deleteFavourite(streamID, "", "live", "", SharepreferenceDBHandler.getUserID(this.context));
                }
            }
        }
    }

    @Override // com.nst.yourname.view.interfaces.XtreamPanelAPIInterface
    public void panelAPI(XtreamPanelAPICallback xtreamPanelAPICallback, String str) {
        Map<String, PanelAvailableChannelsPojo> map;
        if (xtreamPanelAPICallback != null && this.context != null) {
            ArrayList<VodCategoriesCallback> arrayList = new ArrayList<>();
            ArrayList<LiveStreamCategoriesCallback> arrayList2 = new ArrayList<>();
            if (xtreamPanelAPICallback.getCategories() != null) {
                arrayList = xtreamPanelAPICallback.getCategories().getMovie();
                arrayList2 = xtreamPanelAPICallback.getCategories().getLive();
            }
            ArrayList<VodCategoriesCallback> arrayList3 = arrayList;
            ArrayList arrayList4 = new ArrayList();
            ArrayList arrayList5 = new ArrayList();
            if (xtreamPanelAPICallback.getAvailableChannels() != null) {
                if (this.liveStreamDBHandler != null) {
                    this.liveStreamDBHandler.makeEmptyLiveCategory();
                    this.liveStreamDBHandler.makeEmptyLiveStreams();
                    this.liveStreamDBHandler.makeEmptyMovieCategory();
                    this.liveStreamDBHandler.makeEmptyVODStreams();
                }
                if (this.seriesStreamsDatabaseHandler != null) {
                    this.seriesStreamsDatabaseHandler.deleteAndRecreateSeriesCategories();
                    this.seriesStreamsDatabaseHandler.deleteAndRecreateSeriesStreams();
                }
                Map<String, PanelAvailableChannelsPojo> availableChannels = xtreamPanelAPICallback.getAvailableChannels();
                if (xtreamPanelAPICallback.getCategories() == null && xtreamPanelAPICallback.getAvailableChannels().size() > 0) {
                    int i = 0;
                    int i2 = 0;
                    for (PanelAvailableChannelsPojo panelAvailableChannelsPojo : availableChannels.values()) {
                        String typeName = panelAvailableChannelsPojo.getTypeName() != null ? panelAvailableChannelsPojo.getTypeName() : null;
                        String categoryName = (panelAvailableChannelsPojo.getCategoryName() == null || panelAvailableChannelsPojo.getCategoryName() == "null") ? "uksda2323rik23" : panelAvailableChannelsPojo.getCategoryName();
                        String categoryId = panelAvailableChannelsPojo.getCategoryId() != null ? panelAvailableChannelsPojo.getCategoryId() : null;
                        if ((typeName == null || !typeName.contains("Live")) && (typeName == null || !typeName.contains("Live Streams"))) {
                            if (((typeName != null && typeName.contains("Movies")) || (typeName != null && typeName.contains("Movies Streams"))) && !arrayList5.contains(categoryName)) {
                                VodCategoriesCallback vodCategoriesCallback = new VodCategoriesCallback();
                                if (categoryName.equals("uksda2323rik23")) {
                                    vodCategoriesCallback.setCategoryName("");
                                } else if (categoryName == null || categoryName == "null") {
                                    vodCategoriesCallback.setCategoryName("");
                                } else {
                                    vodCategoriesCallback.setCategoryName(categoryName);
                                }
                                if (categoryName.equals("uksda2323rik23")) {
                                    vodCategoriesCallback.setCategoryId("-2");
                                } else if (categoryId == null || categoryId == "null") {
                                    vodCategoriesCallback.setCategoryId("-2");
                                } else {
                                    vodCategoriesCallback.setCategoryId(categoryId);
                                }
                                vodCategoriesCallback.setParentId(0);
                                arrayList3.add(vodCategoriesCallback);
                                arrayList5.add(i2, categoryName);
                                i2++;
                            }
                        } else if (!arrayList4.contains(categoryName)) {
                            LiveStreamCategoriesCallback liveStreamCategoriesCallback = new LiveStreamCategoriesCallback();
                            if (categoryName == null || categoryName == "null") {
                                liveStreamCategoriesCallback.setCategoryName("");
                            } else {
                                liveStreamCategoriesCallback.setCategoryName(categoryName);
                            }
                            if (categoryId == null || categoryId == "null") {
                                liveStreamCategoriesCallback.setCategoryId("-2");
                            } else {
                                liveStreamCategoriesCallback.setCategoryId(categoryId);
                            }
                            liveStreamCategoriesCallback.setParentId(0);
                            arrayList2.add(liveStreamCategoriesCallback);
                            arrayList4.add(i, categoryName);
                            i++;
                        }
                    }
                }
                map = availableChannels;
            } else {
                map = null;
            }
            int size = arrayList3 != null ? arrayList3.size() : 0;
            int size2 = arrayList2 != null ? arrayList2.size() : 0;
            int size3 = map != null ? map.size() : 0;
            if (arrayList3 != null && arrayList3.size() > 0) {
                size = arrayList3.size();
            }
            int i3 = size;
            int size4 = (arrayList2 == null || arrayList2.size() <= 0) ? size2 : arrayList2.size();
            new LiveStreamCategoryIdDBModel();
            new LiveStreamCategoryIdDBModel();
            new LiveStreamsDBModel();
            if (i3 != 0) {
                if (Build.VERSION.SDK_INT >= 11) {
                    ArrayList<LiveStreamCategoriesCallback> finalArrayList = arrayList2;
                    new AsyncTask<String, Integer, Boolean>() {
                        /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1VodAsyncTask */
                        int val$finalTotalVod;

                        final int ITERATIONS = this.val$finalTotalVod;
                        Context mcontext = null;
                        final Map val$finalAvailableChanelsList;
                        final ArrayList val$finalLiveList;
                        final ArrayList val$finalMovieList;
                        final int val$finalTotalLive;
                        final int val$finalTotalLiveAndVod;

                        public void onPreExecute() {
                        }

                        public void onProgressUpdate(Integer... numArr) {
                        }

                        /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                        {
                            this.val$finalTotalVod = i3;
                            this.val$finalMovieList = arrayList3;
                            this.val$finalTotalLiveAndVod = size3;
                            this.val$finalAvailableChanelsList = map;
                            this.val$finalLiveList = finalArrayList;
                            this.val$finalTotalLive = size4;
                            this.mcontext = context;
                        }

                        public Boolean doInBackground(String... strArr) {
                            publishProgress(0);
                            if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                                ImportStreamsActivity.this.liveStreamDBHandler.addMovieCategories(this.val$finalMovieList);
                            }
                            return true;
                        }

                        public void onPostExecute(Boolean bool) {
                            if (ImportStreamsActivity.this.context == null) {
                                return;
                            }
                            if (Build.VERSION.SDK_INT >= 11) {
                                new AsyncTask<String, Integer, Boolean>() {
                                    int val$finalTotalLive;

                                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1LiveAsyncTask */
                                    final int ITERATIONS = this.val$finalTotalLive;
                                    Context mcontext = null;
                                    Map val$finalAvailableChanelsList;
                                    ArrayList val$finalLiveList;
                                    int val$finalTotalLiveAndVod;

                                    public void onPreExecute() {
                                    }

                                    public void onProgressUpdate(Integer... numArr) {
                                    }

                                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                                    {
                                        this.val$finalTotalLive = val$finalTotalLive;
                                        this.val$finalLiveList = val$finalLiveList;
                                        this.val$finalAvailableChanelsList = val$finalAvailableChanelsList;
                                        this.val$finalTotalLiveAndVod = val$finalTotalLiveAndVod;
                                        this.mcontext = context;
                                    }

                                    public Boolean doInBackground(String... strArr) {
                                        publishProgress(0);
                                        if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                                            ImportStreamsActivity.this.liveStreamDBHandler.addLiveCategories(this.val$finalLiveList);
                                        }
                                        return true;
                                    }

                                    public void onPostExecute(Boolean bool) {
                                        if (ImportStreamsActivity.this.context == null) {
                                            return;
                                        }
                                        if (Build.VERSION.SDK_INT >= 11) {
                                            new AsyncTask<String, Integer, Boolean>() {
                                                /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1AllChannelsAndVodAsyncTask */
                                                int val$finalTotalLiveAndVod;
                                                final int ITERATIONS = this.val$finalTotalLiveAndVod;
                                                Context mcontext = null;
                                                Map val$finalAvailableChanelsList;

                                                public void onPreExecute() {
                                                }

                                                public void onProgressUpdate(Integer... numArr) {
                                                }

                                                /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                                                {
                                                    this.val$finalTotalLiveAndVod = val$finalTotalLiveAndVod;
                                                    this.val$finalAvailableChanelsList = val$finalAvailableChanelsList;
                                                    this.mcontext = context;
                                                }

                                                public Boolean doInBackground(String... strArr) {
                                                    publishProgress(0);
                                                    if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.addAllAvailableChannel(this.val$finalAvailableChanelsList);
                                                    }
                                                    return true;
                                                }

                                                public void onPostExecute(Boolean bool) {
                                                    String access$100 = ImportStreamsActivity.this.currentDateValue();
                                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                                    }
                                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                                    }
                                                    if (ImportStreamsActivity.this.context != null) {
                                                        ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, NewDashboardActivity.class));
                                                        ImportStreamsActivity.this.finish();
                                                    }
                                                }
                                            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                                        } else {
                                            new AsyncTask<String, Integer, Boolean>() {
                                                /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1AllChannelsAndVodAsyncTask */
                                                int val$finalTotalLiveAndVod;
                                                final int ITERATIONS = this.val$finalTotalLiveAndVod;
                                                Context mcontext = null;
                                                Map val$finalAvailableChanelsList;

                                                public void onPreExecute() {
                                                }

                                                public void onProgressUpdate(Integer... numArr) {
                                                }

                                                /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                                                {
                                                    this.val$finalTotalLiveAndVod = val$finalTotalLiveAndVod;
                                                    this.val$finalAvailableChanelsList = val$finalAvailableChanelsList;
                                                    this.mcontext = context;
                                                }

                                                public Boolean doInBackground(String... strArr) {
                                                    publishProgress(0);
                                                    if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.addAllAvailableChannel(this.val$finalAvailableChanelsList);
                                                    }
                                                    return true;
                                                }

                                                public void onPostExecute(Boolean bool) {
                                                    String access$100 = ImportStreamsActivity.this.currentDateValue();
                                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                                    }
                                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                                    }
                                                    if (ImportStreamsActivity.this.context != null) {
                                                        ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, NewDashboardActivity.class));
                                                        ImportStreamsActivity.this.finish();
                                                    }
                                                }
                                            }.execute(new String[0]);
                                        }
                                    }
                                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                            } else {
                                new AsyncTask<String, Integer, Boolean>() {
                                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1LiveAsyncTask */
                                    int val$finalTotalLive;
                                    final int ITERATIONS = this.val$finalTotalLive;
                                    Context mcontext = null;
                                    Map val$finalAvailableChanelsList;
                                    ArrayList val$finalLiveList;
                                    int val$finalTotalLiveAndVod;

                                    public void onPreExecute() {
                                    }

                                    public void onProgressUpdate(Integer... numArr) {
                                    }

                                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                                    {
                                        this.val$finalTotalLive = val$finalTotalLive;
                                        this.val$finalLiveList = val$finalLiveList;
                                        this.val$finalAvailableChanelsList = val$finalAvailableChanelsList;
                                        this.val$finalTotalLiveAndVod = val$finalTotalLiveAndVod;
                                        this.mcontext = context;
                                    }

                                    public Boolean doInBackground(String... strArr) {
                                        publishProgress(0);
                                        if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                                            ImportStreamsActivity.this.liveStreamDBHandler.addLiveCategories(this.val$finalLiveList);
                                        }
                                        return true;
                                    }

                                    public void onPostExecute(Boolean bool) {
                                        if (ImportStreamsActivity.this.context == null) {
                                            return;
                                        }
                                        if (Build.VERSION.SDK_INT >= 11) {
                                            new AsyncTask<String, Integer, Boolean>() {
                                                /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1AllChannelsAndVodAsyncTask */
                                                int val$finalTotalLiveAndVod;
                                                final int ITERATIONS = this.val$finalTotalLiveAndVod;
                                                Context mcontext = null;
                                                Map val$finalAvailableChanelsList;

                                                public void onPreExecute() {
                                                }

                                                public void onProgressUpdate(Integer... numArr) {
                                                }

                                                /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                                                {
                                                    this.val$finalTotalLiveAndVod = val$finalTotalLiveAndVod;
                                                    this.val$finalAvailableChanelsList = val$finalAvailableChanelsList;
                                                    this.mcontext = context;
                                                }

                                                public Boolean doInBackground(String... strArr) {
                                                    publishProgress(0);
                                                    if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.addAllAvailableChannel(this.val$finalAvailableChanelsList);
                                                    }
                                                    return true;
                                                }

                                                public void onPostExecute(Boolean bool) {
                                                    String access$100 = ImportStreamsActivity.this.currentDateValue();
                                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                                    }
                                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                                    }
                                                    if (ImportStreamsActivity.this.context != null) {
                                                        ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, NewDashboardActivity.class));
                                                        ImportStreamsActivity.this.finish();
                                                    }
                                                }
                                            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                                        } else {
                                            new AsyncTask<String, Integer, Boolean>() {
                                                /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1AllChannelsAndVodAsyncTask */
                                                int val$finalTotalLiveAndVod;
                                                final int ITERATIONS = this.val$finalTotalLiveAndVod;
                                                Context mcontext = null;
                                                Map val$finalAvailableChanelsList;

                                                public void onPreExecute() {
                                                }

                                                public void onProgressUpdate(Integer... numArr) {
                                                }

                                                /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                                                {
                                                    this.val$finalTotalLiveAndVod = val$finalTotalLiveAndVod;
                                                    this.val$finalAvailableChanelsList = val$finalAvailableChanelsList;
                                                    this.mcontext = context;
                                                }

                                                public Boolean doInBackground(String... strArr) {
                                                    publishProgress(0);
                                                    if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.addAllAvailableChannel(this.val$finalAvailableChanelsList);
                                                    }
                                                    return true;
                                                }

                                                public void onPostExecute(Boolean bool) {
                                                    String access$100 = ImportStreamsActivity.this.currentDateValue();
                                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                                    }
                                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                                    }
                                                    if (ImportStreamsActivity.this.context != null) {
                                                        ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, NewDashboardActivity.class));
                                                        ImportStreamsActivity.this.finish();
                                                    }
                                                }
                                            }.execute(new String[0]);
                                        }
                                    }
                                }.execute(new String[0]);
                            }
                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                } else {
                    ArrayList<LiveStreamCategoriesCallback> finalArrayList1 = arrayList2;
                    new AsyncTask<String, Integer, Boolean>() {
                        /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1VodAsyncTask */
                        int val$finalTotalVod;

                        final int ITERATIONS = this.val$finalTotalVod;
                        Context mcontext = null;
                        final Map val$finalAvailableChanelsList;
                        final ArrayList val$finalLiveList;
                        final ArrayList val$finalMovieList;
                        final int val$finalTotalLive;
                        final int val$finalTotalLiveAndVod;

                        public void onPreExecute() {
                        }

                        public void onProgressUpdate(Integer... numArr) {
                        }

                        /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                        {
                            this.val$finalTotalVod = i3;
                            this.val$finalMovieList = arrayList3;
                            this.val$finalTotalLiveAndVod = size3;
                            this.val$finalAvailableChanelsList = map;
                            this.val$finalLiveList = finalArrayList1;
                            this.val$finalTotalLive = size4;
                            this.mcontext = context;
                        }

                        public Boolean doInBackground(String... strArr) {
                            publishProgress(0);
                            if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                                ImportStreamsActivity.this.liveStreamDBHandler.addMovieCategories(this.val$finalMovieList);
                            }
                            return true;
                        }

                        public void onPostExecute(Boolean bool) {
                            if (ImportStreamsActivity.this.context == null) {
                                return;
                            }
                            if (Build.VERSION.SDK_INT >= 11) {
                                new AsyncTask<String, Integer, Boolean>() {
                                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1LiveAsyncTask */
                                    int val$finalTotalLive;

                                    final int ITERATIONS = this.val$finalTotalLive;
                                    Context mcontext = null;
                                    Map val$finalAvailableChanelsList;
                                    ArrayList val$finalLiveList;
                                    int val$finalTotalLiveAndVod;

                                    public void onPreExecute() {
                                    }

                                    public void onProgressUpdate(Integer... numArr) {
                                    }

                                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                                    {
                                        this.val$finalTotalLive = val$finalTotalLive;
                                        this.val$finalLiveList = val$finalLiveList;
                                        this.val$finalAvailableChanelsList = val$finalAvailableChanelsList;
                                        this.val$finalTotalLiveAndVod = val$finalTotalLiveAndVod;
                                        this.mcontext = context;
                                    }

                                    public Boolean doInBackground(String... strArr) {
                                        publishProgress(0);
                                        if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                                            ImportStreamsActivity.this.liveStreamDBHandler.addLiveCategories(this.val$finalLiveList);
                                        }
                                        return true;
                                    }

                                    public void onPostExecute(Boolean bool) {
                                        if (ImportStreamsActivity.this.context == null) {
                                            return;
                                        }
                                        if (Build.VERSION.SDK_INT >= 11) {
                                            new AsyncTask<String, Integer, Boolean>() {
                                                /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1AllChannelsAndVodAsyncTask */
                                                int val$finalTotalLiveAndVod;

                                                final int ITERATIONS = this.val$finalTotalLiveAndVod;
                                                Context mcontext = null;
                                                Map val$finalAvailableChanelsList;

                                                public void onPreExecute() {
                                                }

                                                public void onProgressUpdate(Integer... numArr) {
                                                }

                                                /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                                                {
                                                    this.val$finalTotalLiveAndVod = val$finalTotalLiveAndVod;
                                                    this.val$finalAvailableChanelsList = val$finalAvailableChanelsList;
                                                    this.mcontext = context;
                                                }

                                                public Boolean doInBackground(String... strArr) {
                                                    publishProgress(0);
                                                    if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.addAllAvailableChannel(this.val$finalAvailableChanelsList);
                                                    }
                                                    return true;
                                                }

                                                public void onPostExecute(Boolean bool) {
                                                    String access$100 = ImportStreamsActivity.this.currentDateValue();
                                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                                    }
                                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                                    }
                                                    if (ImportStreamsActivity.this.context != null) {
                                                        ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, NewDashboardActivity.class));
                                                        ImportStreamsActivity.this.finish();
                                                    }
                                                }
                                            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                                        } else {
                                            new AsyncTask<String, Integer, Boolean>() {
                                                /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1AllChannelsAndVodAsyncTask */
                                                int val$finalTotalLiveAndVod;

                                                final int ITERATIONS = this.val$finalTotalLiveAndVod;
                                                Context mcontext = null;
                                                Map val$finalAvailableChanelsList;

                                                public void onPreExecute() {
                                                }

                                                public void onProgressUpdate(Integer... numArr) {
                                                }

                                                /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                                                {
                                                    this.val$finalTotalLiveAndVod = val$finalTotalLiveAndVod;
                                                    this.val$finalAvailableChanelsList = val$finalAvailableChanelsList;
                                                    this.mcontext = context;
                                                }

                                                public Boolean doInBackground(String... strArr) {
                                                    publishProgress(0);
                                                    if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.addAllAvailableChannel(this.val$finalAvailableChanelsList);
                                                    }
                                                    return true;
                                                }

                                                public void onPostExecute(Boolean bool) {
                                                    String access$100 = ImportStreamsActivity.this.currentDateValue();
                                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                                    }
                                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                                    }
                                                    if (ImportStreamsActivity.this.context != null) {
                                                        ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, NewDashboardActivity.class));
                                                        ImportStreamsActivity.this.finish();
                                                    }
                                                }
                                            }.execute(new String[0]);
                                        }
                                    }
                                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                            } else {
                                new AsyncTask<String, Integer, Boolean>() {
                                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1LiveAsyncTask */
                                    int val$finalTotalLive;

                                    final int ITERATIONS = this.val$finalTotalLive;
                                    Context mcontext = null;
                                    Map val$finalAvailableChanelsList;
                                    ArrayList val$finalLiveList;
                                    int val$finalTotalLiveAndVod;

                                    public void onPreExecute() {
                                    }

                                    public void onProgressUpdate(Integer... numArr) {
                                    }

                                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                                    {
                                        this.val$finalTotalLive = val$finalTotalLive;
                                        this.val$finalLiveList = val$finalLiveList;
                                        this.val$finalAvailableChanelsList = val$finalAvailableChanelsList;
                                        this.val$finalTotalLiveAndVod = val$finalTotalLiveAndVod;
                                        this.mcontext = context;
                                    }

                                    public Boolean doInBackground(String... strArr) {
                                        publishProgress(0);
                                        if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                                            ImportStreamsActivity.this.liveStreamDBHandler.addLiveCategories(this.val$finalLiveList);
                                        }
                                        return true;
                                    }

                                    public void onPostExecute(Boolean bool) {
                                        if (ImportStreamsActivity.this.context == null) {
                                            return;
                                        }
                                        if (Build.VERSION.SDK_INT >= 11) {
                                            new AsyncTask<String, Integer, Boolean>() {
                                                /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1AllChannelsAndVodAsyncTask */
                                                int val$finalTotalLiveAndVod;

                                                final int ITERATIONS = this.val$finalTotalLiveAndVod;
                                                Context mcontext = null;
                                                Map val$finalAvailableChanelsList;

                                                public void onPreExecute() {
                                                }

                                                public void onProgressUpdate(Integer... numArr) {
                                                }

                                                /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                                                {
                                                    this.val$finalTotalLiveAndVod = val$finalTotalLiveAndVod;
                                                    this.val$finalAvailableChanelsList = val$finalAvailableChanelsList;
                                                    this.mcontext = context;
                                                }

                                                public Boolean doInBackground(String... strArr) {
                                                    publishProgress(0);
                                                    if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.addAllAvailableChannel(this.val$finalAvailableChanelsList);
                                                    }
                                                    return true;
                                                }

                                                public void onPostExecute(Boolean bool) {
                                                    String access$100 = ImportStreamsActivity.this.currentDateValue();
                                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                                    }
                                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                                    }
                                                    if (ImportStreamsActivity.this.context != null) {
                                                        ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, NewDashboardActivity.class));
                                                        ImportStreamsActivity.this.finish();
                                                    }
                                                }
                                            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                                        } else {
                                            new AsyncTask<String, Integer, Boolean>() {
                                                /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1AllChannelsAndVodAsyncTask */
                                                int val$finalTotalLiveAndVod;

                                                final int ITERATIONS = this.val$finalTotalLiveAndVod;
                                                Context mcontext = null;
                                                Map val$finalAvailableChanelsList;

                                                public void onPreExecute() {
                                                }

                                                public void onProgressUpdate(Integer... numArr) {
                                                }

                                                /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                                                {
                                                    this.val$finalTotalLiveAndVod = val$finalTotalLiveAndVod;
                                                    this.val$finalAvailableChanelsList = val$finalAvailableChanelsList;
                                                    this.mcontext = context;
                                                }

                                                public Boolean doInBackground(String... strArr) {
                                                    publishProgress(0);
                                                    if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.addAllAvailableChannel(this.val$finalAvailableChanelsList);
                                                    }
                                                    return true;
                                                }

                                                public void onPostExecute(Boolean bool) {
                                                    String access$100 = ImportStreamsActivity.this.currentDateValue();
                                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                                    }
                                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                                    }
                                                    if (ImportStreamsActivity.this.context != null) {
                                                        ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, NewDashboardActivity.class));
                                                        ImportStreamsActivity.this.finish();
                                                    }
                                                }
                                            }.execute(new String[0]);
                                        }
                                    }
                                }.execute(new String[0]);
                            }
                        }
                    }.execute(new String[0]);
                }
            } else if (Build.VERSION.SDK_INT >= 11) {
                ArrayList<LiveStreamCategoriesCallback> finalArrayList2 = arrayList2;
                new AsyncTask<String, Integer, Boolean>() {
                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1LiveAsyncTask */
                    int val$finalTotalLive;
                    final int ITERATIONS = this.val$finalTotalLive;
                    Context mcontext = null;
                    final Map val$finalAvailableChanelsList;
                    final ArrayList val$finalLiveList;
                    final int val$finalTotalLiveAndVod;

                    public void onPreExecute() {
                    }

                    public void onProgressUpdate(Integer... numArr) {
                    }

                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                    {
                        this.val$finalTotalLive = size4;
                        this.val$finalLiveList = finalArrayList2;
                        this.val$finalAvailableChanelsList = map;
                        this.val$finalTotalLiveAndVod = size3;
                        this.mcontext = context;
                    }

                    public Boolean doInBackground(String... strArr) {
                        publishProgress(0);
                        if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                            ImportStreamsActivity.this.liveStreamDBHandler.addLiveCategories(this.val$finalLiveList);
                        }
                        return true;
                    }

                    public void onPostExecute(Boolean bool) {
                        if (ImportStreamsActivity.this.context == null) {
                            return;
                        }
                        if (Build.VERSION.SDK_INT >= 11) {
                            new AsyncTask<String, Integer, Boolean>() {
                                /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1AllChannelsAndVodAsyncTask */
                                int val$finalTotalLiveAndVod;
                                final int ITERATIONS = this.val$finalTotalLiveAndVod;
                                Context mcontext = null;
                                Map val$finalAvailableChanelsList;

                                public void onPreExecute() {
                                }

                                public void onProgressUpdate(Integer... numArr) {
                                }

                                /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                                {
                                    this.val$finalTotalLiveAndVod = val$finalTotalLiveAndVod;
                                    this.val$finalAvailableChanelsList = val$finalAvailableChanelsList;
                                    this.mcontext = context;
                                }

                                public Boolean doInBackground(String... strArr) {
                                    publishProgress(0);
                                    if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                                        ImportStreamsActivity.this.liveStreamDBHandler.addAllAvailableChannel(this.val$finalAvailableChanelsList);
                                    }
                                    return true;
                                }

                                public void onPostExecute(Boolean bool) {
                                    String access$100 = ImportStreamsActivity.this.currentDateValue();
                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                    }
                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                    }
                                    if (ImportStreamsActivity.this.context != null) {
                                        ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, NewDashboardActivity.class));
                                        ImportStreamsActivity.this.finish();
                                    }
                                }
                            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                        } else {
                            new AsyncTask<String, Integer, Boolean>() {
                                /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1AllChannelsAndVodAsyncTask */
                                int val$finalTotalLiveAndVod;
                                final int ITERATIONS = this.val$finalTotalLiveAndVod;
                                Context mcontext = null;
                                Map val$finalAvailableChanelsList;

                                public void onPreExecute() {
                                }

                                public void onProgressUpdate(Integer... numArr) {
                                }

                                /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                                {
                                    this.val$finalTotalLiveAndVod = val$finalTotalLiveAndVod;
                                    this.val$finalAvailableChanelsList = val$finalAvailableChanelsList;
                                    this.mcontext = context;
                                }

                                public Boolean doInBackground(String... strArr) {
                                    publishProgress(0);
                                    if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                                        ImportStreamsActivity.this.liveStreamDBHandler.addAllAvailableChannel(this.val$finalAvailableChanelsList);
                                    }
                                    return true;
                                }

                                public void onPostExecute(Boolean bool) {
                                    String access$100 = ImportStreamsActivity.this.currentDateValue();
                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                    }
                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                    }
                                    if (ImportStreamsActivity.this.context != null) {
                                        ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, NewDashboardActivity.class));
                                        ImportStreamsActivity.this.finish();
                                    }
                                }
                            }.execute(new String[0]);
                        }
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
            } else {
                ArrayList<LiveStreamCategoriesCallback> finalArrayList3 = arrayList2;
                new AsyncTask<String, Integer, Boolean>() {
                    /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1LiveAsyncTask */
                    int val$finalTotalLive;
                    final int ITERATIONS = this.val$finalTotalLive;
                    Context mcontext = null;
                    final Map val$finalAvailableChanelsList;
                    final ArrayList val$finalLiveList;
                    final int val$finalTotalLiveAndVod;

                    public void onPreExecute() {
                    }

                    public void onProgressUpdate(Integer... numArr) {
                    }

                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                    {
                        this.val$finalTotalLive = size4;
                        this.val$finalLiveList = finalArrayList3;
                        this.val$finalAvailableChanelsList = map;
                        this.val$finalTotalLiveAndVod = size3;
                        this.mcontext = context;
                    }

                    public Boolean doInBackground(String... strArr) {
                        publishProgress(0);
                        if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                            ImportStreamsActivity.this.liveStreamDBHandler.addLiveCategories(this.val$finalLiveList);
                        }
                        return true;
                    }

                    public void onPostExecute(Boolean bool) {
                        if (ImportStreamsActivity.this.context == null) {
                            return;
                        }
                        if (Build.VERSION.SDK_INT >= 11) {
                            new AsyncTask<String, Integer, Boolean>() {
                                /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1AllChannelsAndVodAsyncTask */
                                int val$finalTotalLiveAndVod;
                                final int ITERATIONS = this.val$finalTotalLiveAndVod;
                                Context mcontext = null;
                                Map val$finalAvailableChanelsList;

                                public void onPreExecute() {
                                }

                                public void onProgressUpdate(Integer... numArr) {
                                }

                                /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                                {
                                    this.val$finalTotalLiveAndVod = val$finalTotalLiveAndVod;
                                    this.val$finalAvailableChanelsList = val$finalAvailableChanelsList;
                                    this.mcontext = context;
                                }

                                public Boolean doInBackground(String... strArr) {
                                    publishProgress(0);
                                    if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                                        ImportStreamsActivity.this.liveStreamDBHandler.addAllAvailableChannel(this.val$finalAvailableChanelsList);
                                    }
                                    return true;
                                }

                                public void onPostExecute(Boolean bool) {
                                    String access$100 = ImportStreamsActivity.this.currentDateValue();
                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                    }
                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                    }
                                    if (ImportStreamsActivity.this.context != null) {
                                        ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, NewDashboardActivity.class));
                                        ImportStreamsActivity.this.finish();
                                    }
                                }
                            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                        } else {
                            new AsyncTask<String, Integer, Boolean>() {
                                /* class com.nst.yourname.view.activity.ImportStreamsActivity.AnonymousClass1AllChannelsAndVodAsyncTask */
                                int val$finalTotalLiveAndVod;
                                final int ITERATIONS = this.val$finalTotalLiveAndVod;
                                Context mcontext = null;
                                Map val$finalAvailableChanelsList;

                                public void onPreExecute() {
                                }

                                public void onProgressUpdate(Integer... numArr) {
                                }

                                /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportStreamsActivity, android.content.Context */
                                {
                                    this.val$finalTotalLiveAndVod = val$finalTotalLiveAndVod;
                                    this.val$finalAvailableChanelsList = val$finalAvailableChanelsList;
                                    this.mcontext = context;
                                }

                                public Boolean doInBackground(String... strArr) {
                                    publishProgress(0);
                                    if (ImportStreamsActivity.this.liveStreamDBHandler != null) {
                                        ImportStreamsActivity.this.liveStreamDBHandler.addAllAvailableChannel(this.val$finalAvailableChanelsList);
                                    }
                                    return true;
                                }

                                public void onPostExecute(Boolean bool) {
                                    String access$100 = ImportStreamsActivity.this.currentDateValue();
                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                    }
                                    if (!(access$100 == null || ImportStreamsActivity.this.liveStreamDBHandler == null)) {
                                        ImportStreamsActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FINISH, access$100);
                                    }
                                    if (ImportStreamsActivity.this.context != null) {
                                        ImportStreamsActivity.this.startActivity(new Intent(ImportStreamsActivity.this.context, NewDashboardActivity.class));
                                        ImportStreamsActivity.this.finish();
                                    }
                                }
                            }.execute(new String[0]);
                        }
                    }
                }.execute(new String[0]);
            }
        }
    }

    @Override // com.nst.yourname.view.interfaces.XtreamPanelAPIInterface
    public void panelApiFailed(String str) {
        String currentDateValue = currentDateValue();
        if (currentDateValue != null && this.liveStreamDBHandler != null) {
            this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FAILED, currentDateValue);
        }
    }

    public static void printMap(Map map) {
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            PrintStream printStream = System.out;
            printStream.println(entry.getKey() + " = " + entry.getValue());
            entry.getValue();
            it.remove();
        }
    }
}
