package com.nst.yourname.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.nst.yourname.model.MultiUserDBModel;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.MultiUserDBHandler;
import com.nst.yourname.model.database.SeriesStreamsDatabaseHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.presenter.PlayerApiPresenter;
import com.nst.yourname.presenter.XMLTVPresenter;
import com.nst.yourname.presenter.XtreamPanelAPIPresenter;
import com.nst.yourname.view.muparse.M3UParser;
import com.nst.yourname.view.utility.LoadingGearSpinner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

@SuppressWarnings("ALL")
public class ImportM3uActivity extends AppCompatActivity {
    private static final int RECORDING_REQUEST_CODE = 101;
    public static final String REDIRECT_CATCHUP = "redirect_catchup";
    public static final String REDIRECT_LIVE_TV = "redirect_live_tv";
    public static final String REDIRECT_LIVE_TV_EPG_EXPIRED = "redirect_live_tv_epg_expired";
    public static final String REDIRECT_SERIES = "redirect_series";
    public static final String REDIRECT_VOD = "redirect_vod";
    Context context;
    InputStream is;
    @BindView(R.id.iv_gear_loader)
    LoadingGearSpinner ivGearLoader;
    LiveStreamDBHandler liveStreamDBHandler;
    private SharedPreferences loginPreferencesAfterLogin;
    public MultiUserDBHandler multiuserdbhandler;
    final M3UParser parser = new M3UParser();
    private PlayerApiPresenter playerApiPresenter;
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

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_import_m3u);
        ButterKnife.bind(this);
        changeStatusBarColor();
        getWindow().setFlags(1024, 1024);
        this.context = this;
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.seriesStreamsDatabaseHandler = new SeriesStreamsDatabaseHandler(this.context);
        this.multiuserdbhandler = new MultiUserDBHandler(this.context);
        deleteTables();
        initialize();
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
            addDatabaseStatusOnSetup();
            ArrayList<MultiUserDBModel> userDetails = this.multiuserdbhandler.getUserDetails(SharepreferenceDBHandler.getUserID(this.context));
            if (userDetails == null || userDetails.size() <= 0) {
                Utils.showToast(this.context, this.context.getResources().getString(R.string.user_not_found));
                startActivity(new Intent(this.context, NewDashboardActivity.class));
                finish();
                return;
            }
            String str = userDetails.get(0).getmagportal();
            File file = new File(Environment.getExternalStorageDirectory(), AppConst.RECORDING_DIRECTORY);
            if (!file.exists()) {
                file.mkdirs();
            }
            if (userDetails.get(0).getM3uType().equals(AppConst.TYPE_M3U_FILE)) {
                if (!str.equals("")) {
                    if (this.tvImportingStreams != null) {
                        this.tvImportingStreams.setText(getResources().getString(R.string.importign_all_channels));
                    }
                    new _loadFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, str);
                    return;
                }
                Utils.showToast(this.context, this.context.getResources().getString(R.string.m3u_file_not_found));
                startActivity(new Intent(this.context, NewDashboardActivity.class));
                finish();
            } else if (!str.equals("")) {
                new DwnloadFileFromUrl().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, str);
            } else {
                Utils.showToast(this.context, this.context.getResources().getString(R.string.m3u_line_not_found));
                startActivity(new Intent(this.context, NewDashboardActivity.class));
                finish();
            }
        }
    }

    public void deleteTables() {
        if (this.liveStreamDBHandler != null) {
            this.liveStreamDBHandler.makeEmptyAllTablesRecordsM3U();
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

    private void addDBStatus(LiveStreamDBHandler liveStreamDBHandler2, String str) {
        DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModel = new DatabaseUpdatedStatusDBModel();
        databaseUpdatedStatusDBModel.setDbUpadatedStatusState("");
        databaseUpdatedStatusDBModel.setDbLastUpdatedDate(str);
        databaseUpdatedStatusDBModel.setDbCategory(AppConst.DB_CHANNELS);
        databaseUpdatedStatusDBModel.setDbCategoryID("1");
        liveStreamDBHandler2.addDBUpdatedStatus(databaseUpdatedStatusDBModel);
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

    @SuppressLint({"StaticFieldLeak"})
    private class DwnloadFileFromUrl extends AsyncTask<String, String, String> {
        public void onProgressUpdate(String... strArr) {
        }

        private DwnloadFileFromUrl() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public String doInBackground(String... strArr) {
            try {
                URL url = new URL(strArr[0]);
                ImportM3uActivity.this.isStoragePermissionGranted();
                File file = new File(Environment.getExternalStorageDirectory(), AppConst.RECORDING_DIRECTORY);
                if (!file.exists()) {
                    file.mkdirs();
                }
                new File(file, "data.m3u");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + AppConst.RECORDING_DIRECTORY + "/data.m3u")));
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        sb.append(readLine);
                        sb.append(10);
                    } else {
                        bufferedWriter.write(sb.toString());
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        return null;
                    }
                }
            } catch (Exception e) {
                Log.d("Google", "DownloadFileFromUrl " + e.getMessage());
                return null;
            }
        }

        public void onPostExecute(String str) {
            if (ImportM3uActivity.this.tvImportingStreams != null) {
                ImportM3uActivity.this.tvImportingStreams.setText(ImportM3uActivity.this.getResources().getString(R.string.importign_all_channels));
            }
            new _loadFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, String.valueOf(Environment.getExternalStoragePublicDirectory("JuiceTVSmarters/data.m3u")));
        }
    }

    @SuppressLint({"StaticFieldLeak"})
    class _loadFile extends AsyncTask<String, Void, Boolean> {
        _loadFile() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public Boolean doInBackground(String... strArr) {
            try {
                ImportM3uActivity.this.is = new FileInputStream(new File(strArr[0]));
                return Boolean.valueOf(ImportM3uActivity.this.parser.parseFileNew(ImportM3uActivity.this.is, ImportM3uActivity.this.context));
            } catch (OutOfMemoryError unused) {
                return false;
            } catch (Exception unused2) {
                return false;
            }
        }

        public void onPostExecute(Boolean bool) {
            super.onPostExecute((Boolean) bool);
            if (bool.booleanValue()) {
                SharedPreferences sharedPreferences = ImportM3uActivity.this.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                sharedPreferences.getString("username", "");
                sharedPreferences.getString("password", "");
                String string = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_URL, "");
                sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_M3U_LINE, "");
                sharedPreferences.getString(AppConst.LOGIN_PREF_ANY_NAME, "M3ULine");
                if (!string.startsWith("http://") && !string.startsWith("https://")) {
                    String str = "http://" + string;
                }
                SharepreferenceDBHandler.setCurrentAPPType(AppConst.TYPE_M3U, ImportM3uActivity.this.context);
                MultiUserDBHandler unused = ImportM3uActivity.this.multiuserdbhandler = new MultiUserDBHandler(ImportM3uActivity.this.context);
                if (ImportM3uActivity.this.context != null) {
                    String action = ImportM3uActivity.this.getIntent().getAction();
                    if ("redirect_live_tv".equals(action)) {
                        ImportM3uActivity.this.startActivity(new Intent(ImportM3uActivity.this.context, LiveActivityNewFlow.class));
                        ImportM3uActivity.this.finish();
                    } else if ("redirect_vod".equals(action)) {
                        ImportM3uActivity.this.startActivity(new Intent(ImportM3uActivity.this.context, VodActivityNewFlow.class));
                        ImportM3uActivity.this.finish();
                    } else if ("redirect_catchup".equals(action)) {
                        ImportM3uActivity.this.startActivity(new Intent(ImportM3uActivity.this.context, PlaylistsCategoriesActivity.class));
                        ImportM3uActivity.this.finish();
                    } else if ("redirect_series".equals(action)) {
                        ImportM3uActivity.this.startActivity(new Intent(ImportM3uActivity.this.context, SeriesActivityNewFlowM3U.class));
                        ImportM3uActivity.this.finish();
                    } else if ("redirect_live_tv_epg_expired".equals(action)) {
                        Intent intent = new Intent(ImportM3uActivity.this.context, ImportEPGXMLActivity.class);
                        intent.setAction("redirect_live_tv_epg_expired");
                        ImportM3uActivity.this.startActivity(intent);
                        ImportM3uActivity.this.finish();
                    } else {
                        ImportM3uActivity.this.startActivity(new Intent(ImportM3uActivity.this.context, NewDashboardActivity.class));
                        ImportM3uActivity.this.finish();
                    }
                }
            } else {
                String access$200 = ImportM3uActivity.this.currentDateValue();
                if (!(access$200 == null || ImportM3uActivity.this.liveStreamDBHandler == null || ImportM3uActivity.this.seriesStreamsDatabaseHandler == null)) {
                    ImportM3uActivity.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_CHANNELS, "1", AppConst.DB_UPDATED_STATUS_FAILED, access$200);
                    ImportM3uActivity.this.seriesStreamsDatabaseHandler.updateSeriesStreamsCatDBStatusAndDate(AppConst.DB_SERIES_STREAMS_CAT, AppConst.DB_SERIES_STREAMS_CAT_ID, AppConst.DB_UPDATED_STATUS_FAILED, access$200);
                    ImportM3uActivity.this.seriesStreamsDatabaseHandler.updateSeriesStreamsCatDBStatusAndDate(AppConst.DB_SERIES_STREAMS, AppConst.DB_SERIES_STREAMS_ID, AppConst.DB_UPDATED_STATUS_FAILED, access$200);
                }
                if (ImportM3uActivity.this.context != null) {
                    String action2 = ImportM3uActivity.this.getIntent().getAction();
                    if ("redirect_live_tv".equals(action2)) {
                        ImportM3uActivity.this.startActivity(new Intent(ImportM3uActivity.this.context, LiveActivityNewFlow.class));
                        ImportM3uActivity.this.finish();
                    } else if ("redirect_vod".equals(action2)) {
                        ImportM3uActivity.this.startActivity(new Intent(ImportM3uActivity.this.context, VodActivityNewFlow.class));
                        ImportM3uActivity.this.finish();
                    } else if ("redirect_series".equals(action2)) {
                        ImportM3uActivity.this.startActivity(new Intent(ImportM3uActivity.this.context, SeriesActivityNewFlowM3U.class));
                        ImportM3uActivity.this.finish();
                    } else if ("redirect_catchup".equals(action2)) {
                        ImportM3uActivity.this.startActivity(new Intent(ImportM3uActivity.this.context, PlaylistsCategoriesActivity.class));
                        ImportM3uActivity.this.finish();
                    } else if ("redirect_live_tv_epg_expired".equals(action2)) {
                        Intent intent2 = new Intent(ImportM3uActivity.this.context, ImportEPGActivity.class);
                        intent2.setAction("redirect_live_tv_epg_expired");
                        ImportM3uActivity.this.startActivity(intent2);
                        ImportM3uActivity.this.finish();
                    } else {
                        ImportM3uActivity.this.startActivity(new Intent(ImportM3uActivity.this.context, NewDashboardActivity.class));
                        ImportM3uActivity.this.finish();
                    }
                }
            }
        }
    }

    public void isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT < 23) {
            Log.v("TAG", "Permission is granted");
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGEandroid.permission.READ_EXTERNAL_STORAGE") == 0) {
            Log.v("TAG", "Permission is granted");
        } else {
            Log.v("TAG", "Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 101);
        }
    }

    public String currentDateValue() {
        return Utils.parseDateToddMMyyyy(Calendar.getInstance().getTime().toString());
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
}
