package com.nst.yourname.view.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.MultiUserDBHandler;
import com.nst.yourname.model.database.SeriesStreamsDatabaseHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.model.pojo.XMLTVProgrammePojo;
import com.nst.yourname.presenter.PlayerApiPresenter;
import com.nst.yourname.presenter.XMLTVPresenter;
import com.nst.yourname.presenter.XtreamPanelAPIPresenter;
import com.nst.yourname.view.muparse.M3UParser;
import com.nst.yourname.view.utility.LoadingGearSpinner;
import com.nst.yourname.view.utility.XMLHelper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

@SuppressWarnings("ALL")
public class ImportEPGXMLActivity extends AppCompatActivity {
    public static final String REDIRECT_EPG_CATEGORY = "redirect_epg_category";
    public static final String REDIRECT_LIVE_TV_EPG_EXPIRED = "redirect_live_tv_epg_expired";
    public static final String REDIRECT_LIVE_TV_UPDATE_DISABLE = "redirect_live_tv_update_disabls";
    Context context;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    InputStream is;
    @BindView(R.id.iv_gear_loader)
    LoadingGearSpinner ivGearLoader;
    LiveStreamDBHandler liveStreamDBHandler;
    public SharedPreferences loginPreferencesAfterLogin;
    private MultiUserDBHandler multiuserdbhandler;
    final M3UParser parser = new M3UParser();
    private PlayerApiPresenter playerApiPresenter;
    public ArrayList<XMLTVProgrammePojo> postValueArrayList;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rl_import_layout)
    RelativeLayout rlImportLayout;
    @BindView(R.id.rl_import_process)
    LinearLayout rlImportProcess;
    private SeriesStreamsDatabaseHandler seriesStreamsDatabaseHandler;
    @BindView(R.id.tv_countings)
    TextView tvCountings;
    @BindView(R.id.tv_importing_streams)
    TextView tvImportingStreams;
    @BindView(R.id.tv_percentage)
    TextView tvPercentage;
    @BindView(R.id.tv_setting_streams)
    TextView tvSettingStreams;
    private XMLTVPresenter xmlTvPresenter;
    ArrayList<XMLTVProgrammePojo> xmltvProgrammePojos;
    private XtreamPanelAPIPresenter xtreamPanelAPIPresenter;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_import_m3u);
        ButterKnife.bind(this);
        changeStatusBarColor();
        getWindow().setFlags(1024, 1024);
        this.context = this;
        if (this.tvImportingStreams != null) {
            this.tvImportingStreams.setText(getResources().getString(R.string.downloading_epg));
        }
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        this.seriesStreamsDatabaseHandler = new SeriesStreamsDatabaseHandler(this.context);
        this.multiuserdbhandler = new MultiUserDBHandler(this.context);
        deleteTables();
        initialize();
    }

    public void deleteTables() {
        if (this.liveStreamDBHandler != null) {
            this.liveStreamDBHandler.makeEmptyEPG();
        }
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
            this.databaseUpdatedStatusDBModelEPG = this.liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, "2");
            if (this.databaseUpdatedStatusDBModelEPG != null) {
                addDatabaseStatusOnSetup(this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState());
            }
            String userEPG = this.multiuserdbhandler.getUserEPG(SharepreferenceDBHandler.getUserID(this.context));
            if (userEPG == null || userEPG.equals("")) {
                Utils.showToast(this.context, this.context.getResources().getString(R.string.install_epg_first));
                startActivity(new Intent(this.context, NewDashboardActivity.class));
                finish();
                return;
            }
            new PostAsync().execute(new Void[0]);
        }
    }

    private void addDatabaseStatusOnSetup(String str) {
        String currentDateValue = currentDateValue();
        if (str != null && !str.equals("")) {
            return;
        }
        if (currentDateValue != null) {
            addDBStatus(this.liveStreamDBHandler, currentDateValue);
        } else {
            Utils.showToast(this.context, this.context.getResources().getString(R.string.invalid_current_date));
        }
    }

    private void addDBStatus(LiveStreamDBHandler liveStreamDBHandler2, String str) {
        DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModel = new DatabaseUpdatedStatusDBModel();
        databaseUpdatedStatusDBModel.setDbUpadatedStatusState("");
        databaseUpdatedStatusDBModel.setDbLastUpdatedDate(str);
        databaseUpdatedStatusDBModel.setDbCategory(AppConst.DB_EPG);
        databaseUpdatedStatusDBModel.setDbCategoryID("2");
        liveStreamDBHandler2.addDBUpdatedStatus(databaseUpdatedStatusDBModel);
    }

    private String currentDateValue() {
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

    @SuppressLint({"StaticFieldLeak"})
    class PostAsync extends AsyncTask<Void, Boolean, Boolean> {
        XMLHelper helper;
        ProgressDialog pd;

        public void onPreExecute() {
        }

        PostAsync() {
        }

        public Boolean doInBackground(Void... voidArr) {
            this.helper = new XMLHelper();
            this.helper.get(ImportEPGXMLActivity.this.context);
            ArrayList unused = ImportEPGXMLActivity.this.postValueArrayList = this.helper.getPostsList();
            return Boolean.valueOf(ImportEPGXMLActivity.this.postValueArrayList != null && ImportEPGXMLActivity.this.postValueArrayList.size() > 0);
        }

        public void onPostExecute(Boolean bool) {
            if (bool.booleanValue()) {
                try {
                    if (ImportEPGXMLActivity.this.tvImportingStreams != null) {
                        ImportEPGXMLActivity.this.tvImportingStreams.setText(ImportEPGXMLActivity.this.getResources().getString(R.string.importing_epg));
                    }
                    if (Build.VERSION.SDK_INT >= 11) {
                        new AsyncTask<String, Integer, Boolean>() {
                            /* class com.nst.yourname.view.activity.ImportEPGXMLActivity.PostAsync.AnonymousClass1NewAsyncTask */
                            final int ITERATIONS = ImportEPGXMLActivity.this.postValueArrayList.size();
                            Context mcontext = null;
                            private volatile boolean running = true;

                            public void onPreExecute() {
                            }

                            public void onProgressUpdate(Integer... numArr) {
                            }

                            {
                                this.mcontext = context;
                            }

                            public Boolean doInBackground(String... strArr) {
                                publishProgress(0);
                                if (ImportEPGXMLActivity.this.liveStreamDBHandler != null) {
                                    ImportEPGXMLActivity.this.liveStreamDBHandler.addEPG(ImportEPGXMLActivity.this.postValueArrayList);
                                }
                                return true;
                            }

                            public void onPostExecute(Boolean bool) {
                                int size = ImportEPGXMLActivity.this.postValueArrayList.size();
                                SharedPreferences unused = ImportEPGXMLActivity.this.loginPreferencesAfterLogin = ImportEPGXMLActivity.this.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                                ImportEPGXMLActivity.this.loginPreferencesAfterLogin.getString(AppConst.SKIP_BUTTON_PREF, "");
                                Context context = ImportEPGXMLActivity.this.context;
                                Utils.showToast(context, ImportEPGXMLActivity.this.getResources().getString(R.string.epg_imported) + " (" + size + ")");
                                if (ImportEPGXMLActivity.this.liveStreamDBHandler != null) {
                                    ImportEPGXMLActivity.this.liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG, "2", AppConst.DB_UPDATED_STATUS_FINISH);
                                }
                                ImportEPGXMLActivity.this.liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, "2");
                                if (ImportEPGXMLActivity.this.context != null) {
                                    String action = ImportEPGXMLActivity.this.getIntent().getAction();
                                    if ("redirect_epg_category".equals(action)) {
                                        ImportEPGXMLActivity.this.startActivity(new Intent(ImportEPGXMLActivity.this.context, NewEPGCategoriesActivity.class));
                                        ImportEPGXMLActivity.this.finish();
                                    } else if ("redirect_live_tv_epg_expired".equals(action)) {
                                        ImportEPGXMLActivity.this.startActivity(new Intent(ImportEPGXMLActivity.this.context, LiveActivityNewFlow.class));
                                        ImportEPGXMLActivity.this.finish();
                                    } else if ("redirect_live_tv_update_disabls".equals(action)) {
                                        ImportEPGXMLActivity.this.startActivity(new Intent(ImportEPGXMLActivity.this.context, LiveActivityNewFlow.class));
                                        ImportEPGXMLActivity.this.finish();
                                    } else {
                                        ImportEPGXMLActivity.this.startActivity(new Intent(ImportEPGXMLActivity.this.context, NewDashboardActivity.class));
                                        ImportEPGXMLActivity.this.finish();
                                    }
                                }
                            }

                            public void onCancelled() {
                                this.running = false;
                            }
                        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                    } else {
                        new AsyncTask<String, Integer, Boolean>() {
                            /* class com.nst.yourname.view.activity.ImportEPGXMLActivity.PostAsync.AnonymousClass1NewAsyncTask */
                            final int ITERATIONS = ImportEPGXMLActivity.this.postValueArrayList.size();
                            Context mcontext = null;
                            private volatile boolean running = true;

                            public void onPreExecute() {
                            }

                            public void onProgressUpdate(Integer... numArr) {
                            }

                            {
                                this.mcontext = context;
                            }

                            public Boolean doInBackground(String... strArr) {
                                publishProgress(0);
                                if (ImportEPGXMLActivity.this.liveStreamDBHandler != null) {
                                    ImportEPGXMLActivity.this.liveStreamDBHandler.addEPG(ImportEPGXMLActivity.this.postValueArrayList);
                                }
                                return true;
                            }

                            public void onPostExecute(Boolean bool) {
                                int size = ImportEPGXMLActivity.this.postValueArrayList.size();
                                SharedPreferences unused = ImportEPGXMLActivity.this.loginPreferencesAfterLogin = ImportEPGXMLActivity.this.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                                ImportEPGXMLActivity.this.loginPreferencesAfterLogin.getString(AppConst.SKIP_BUTTON_PREF, "");
                                Context context = ImportEPGXMLActivity.this.context;
                                Utils.showToast(context, ImportEPGXMLActivity.this.getResources().getString(R.string.epg_imported) + " (" + size + ")");
                                if (ImportEPGXMLActivity.this.liveStreamDBHandler != null) {
                                    ImportEPGXMLActivity.this.liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG, "2", AppConst.DB_UPDATED_STATUS_FINISH);
                                }
                                ImportEPGXMLActivity.this.liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, "2");
                                if (ImportEPGXMLActivity.this.context != null) {
                                    String action = ImportEPGXMLActivity.this.getIntent().getAction();
                                    if ("redirect_epg_category".equals(action)) {
                                        ImportEPGXMLActivity.this.startActivity(new Intent(ImportEPGXMLActivity.this.context, NewEPGCategoriesActivity.class));
                                        ImportEPGXMLActivity.this.finish();
                                    } else if ("redirect_live_tv_epg_expired".equals(action)) {
                                        ImportEPGXMLActivity.this.startActivity(new Intent(ImportEPGXMLActivity.this.context, LiveActivityNewFlow.class));
                                        ImportEPGXMLActivity.this.finish();
                                    } else if ("redirect_live_tv_update_disabls".equals(action)) {
                                        ImportEPGXMLActivity.this.startActivity(new Intent(ImportEPGXMLActivity.this.context, LiveActivityNewFlow.class));
                                        ImportEPGXMLActivity.this.finish();
                                    } else {
                                        ImportEPGXMLActivity.this.startActivity(new Intent(ImportEPGXMLActivity.this.context, NewDashboardActivity.class));
                                        ImportEPGXMLActivity.this.finish();
                                    }
                                }
                            }

                            public void onCancelled() {
                                this.running = false;
                            }
                        }.execute(new String[0]);
                    }
                } catch (Exception unused) {
                    if (ImportEPGXMLActivity.this.liveStreamDBHandler != null) {
                        ImportEPGXMLActivity.this.liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG, "2", AppConst.DB_UPDATED_STATUS_FINISH);
                    }
                    if (ImportEPGXMLActivity.this.context != null) {
                        String action = ImportEPGXMLActivity.this.getIntent().getAction();
                        if ("redirect_epg_category".equals(action)) {
                            ImportEPGXMLActivity.this.startActivity(new Intent(ImportEPGXMLActivity.this.context, NewEPGCategoriesActivity.class));
                            ImportEPGXMLActivity.this.finish();
                        } else if ("redirect_live_tv_epg_expired".equals(action)) {
                            ImportEPGXMLActivity.this.startActivity(new Intent(ImportEPGXMLActivity.this.context, LiveActivityNewFlow.class));
                            ImportEPGXMLActivity.this.finish();
                        } else if ("redirect_live_tv_update_disabls".equals(action)) {
                            ImportEPGXMLActivity.this.startActivity(new Intent(ImportEPGXMLActivity.this.context, LiveActivityNewFlow.class));
                            ImportEPGXMLActivity.this.finish();
                        } else {
                            ImportEPGXMLActivity.this.startActivity(new Intent(ImportEPGXMLActivity.this.context, NewDashboardActivity.class));
                            ImportEPGXMLActivity.this.finish();
                        }
                    }
                }
            } else {
                if (ImportEPGXMLActivity.this.liveStreamDBHandler != null) {
                    ImportEPGXMLActivity.this.liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG, "2", AppConst.DB_UPDATED_STATUS_FINISH);
                }
                if (ImportEPGXMLActivity.this.context != null) {
                    String action2 = ImportEPGXMLActivity.this.getIntent().getAction();
                    if ("redirect_epg_category".equals(action2)) {
                        ImportEPGXMLActivity.this.startActivity(new Intent(ImportEPGXMLActivity.this.context, NewEPGCategoriesActivity.class));
                        ImportEPGXMLActivity.this.finish();
                    } else if ("redirect_live_tv_epg_expired".equals(action2)) {
                        ImportEPGXMLActivity.this.startActivity(new Intent(ImportEPGXMLActivity.this.context, LiveActivityNewFlow.class));
                        ImportEPGXMLActivity.this.finish();
                    } else if ("redirect_live_tv_update_disabls".equals(action2)) {
                        ImportEPGXMLActivity.this.startActivity(new Intent(ImportEPGXMLActivity.this.context, LiveActivityNewFlow.class));
                        ImportEPGXMLActivity.this.finish();
                    } else {
                        ImportEPGXMLActivity.this.startActivity(new Intent(ImportEPGXMLActivity.this.context, NewDashboardActivity.class));
                        ImportEPGXMLActivity.this.finish();
                    }
                }
            }
        }
    }
}
