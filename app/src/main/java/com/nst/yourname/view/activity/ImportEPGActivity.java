package com.nst.yourname.view.activity;

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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.callback.XMLTVCallback;
import com.nst.yourname.model.database.DatabaseUpdatedStatusDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.pojo.XMLTVProgrammePojo;
import com.nst.yourname.presenter.XMLTVPresenter;
import com.nst.yourname.view.interfaces.XMLTVInterface;
import com.nst.yourname.view.utility.LoadingGearSpinner;
import com.nst.yourname.view.utility.XMLHelper;
import java.util.ArrayList;
import java.util.Calendar;

@SuppressWarnings("ALL")
public class ImportEPGActivity extends AppCompatActivity implements XMLTVInterface {
    public static final String REDIRECT_EPG_CATEGORY = "redirect_epg_category";
    public static final String REDIRECT_LIVE_TV_EPG_EXPIRED = "redirect_live_tv_epg_expired";
    public static final String REDIRECT_LIVE_TV_UPDATE_DISABLE = "redirect_live_tv_update_disabls";
    Context context;
    private DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModelEPG = new DatabaseUpdatedStatusDBModel();
    @BindView(R.id.iv_gear_loader)
    LoadingGearSpinner ivGearLoader;
    LiveStreamDBHandler liveStreamDBHandler;
    public SharedPreferences loginPreferencesAfterLogin;
    private SharedPreferences.Editor loginPrefsEditor;
    public ArrayList<XMLTVProgrammePojo> postValueArrayList;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rl_import_layout)
    RelativeLayout rlImportLayout;
    @BindView(R.id.rl_import_process)
    RelativeLayout rlImportProcess;
    @BindView(R.id.tv_countings)
    TextView tvCountings;
    @BindView(R.id.tv_importing_epg)
    TextView tvImportingEpg;
    @BindView(R.id.tv_percentage)
    TextView tvPercentage;
    @BindView(R.id.tv_setting_streams)
    TextView tvSettingStreams;
    private XMLTVPresenter xmlTvPresenter;

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void atStart() {
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFinish() {
    }

    @Override // android.support.v4.app.SupportActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_import_epg_new);
        ButterKnife.bind(this);
        changeStatusBarColor();
        this.context = this;
        this.liveStreamDBHandler = new LiveStreamDBHandler(this.context);
        initialize();
        getWindow().setFlags(1024, 1024);
    }

    private void initialize() {
        if (this.context != null) {
            this.loginPreferencesAfterLogin = getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            this.loginPreferencesAfterLogin.getString("username", "");
            this.loginPreferencesAfterLogin.getString("password", "");
            this.liveStreamDBHandler.getEPGCount();
            this.databaseUpdatedStatusDBModelEPG = this.liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, "2");
            if (this.databaseUpdatedStatusDBModelEPG != null) {
                addDatabaseStatusOnSetup(this.databaseUpdatedStatusDBModelEPG.getDbUpadatedStatusState());
            }
            this.xmlTvPresenter = new XMLTVPresenter(this, this.context);
            this.liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG, "2", AppConst.DB_UPDATED_STATUS_PROCESSING);
            new PostAsync().execute(new Void[0]);
        }
    }

    class PostAsync extends AsyncTask<Void, Boolean, Boolean> {
        XMLHelper helper;
        ProgressDialog pd;

        public void onPreExecute() {
        }

        PostAsync() {
        }

        public Boolean doInBackground(Void... voidArr) {
            if (ImportEPGActivity.this.liveStreamDBHandler != null) {
                ImportEPGActivity.this.liveStreamDBHandler.makeEmptyEPG();
            }
            this.helper = new XMLHelper();
            this.helper.get(ImportEPGActivity.this.context);
            ArrayList unused = ImportEPGActivity.this.postValueArrayList = this.helper.getPostsList();
            if (ImportEPGActivity.this.postValueArrayList == null || ImportEPGActivity.this.postValueArrayList.size() <= 0) {
                return false;
            }
            return true;
        }

        public void onPostExecute(Boolean bool) {
            if (bool.booleanValue()) {
                try {
                    if (ImportEPGActivity.this.tvImportingEpg != null) {
                        ImportEPGActivity.this.tvImportingEpg.setText(ImportEPGActivity.this.getResources().getString(R.string.importing_epg));
                    }
                    if (Build.VERSION.SDK_INT >= 11) {
                        new AsyncTask<String, Integer, Boolean>() {
                            /* class com.nst.yourname.view.activity.ImportEPGActivity.PostAsync.AnonymousClass1NewAsyncTask */
                            final int ITERATIONS = ImportEPGActivity.this.postValueArrayList.size();
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
                                if (ImportEPGActivity.this.liveStreamDBHandler != null) {
                                    ImportEPGActivity.this.liveStreamDBHandler.addEPG(ImportEPGActivity.this.postValueArrayList);
                                }
                                return true;
                            }

                            public void onPostExecute(Boolean bool) {
                                int size = ImportEPGActivity.this.postValueArrayList.size();
                                SharedPreferences unused = ImportEPGActivity.this.loginPreferencesAfterLogin = ImportEPGActivity.this.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                                ImportEPGActivity.this.loginPreferencesAfterLogin.getString(AppConst.SKIP_BUTTON_PREF, "");
                                Context context = ImportEPGActivity.this.context;
                                Utils.showToast(context, ImportEPGActivity.this.getResources().getString(R.string.epg_imported) + " (" + size + ")");
                                if (ImportEPGActivity.this.liveStreamDBHandler != null) {
                                    ImportEPGActivity.this.liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG, "2", AppConst.DB_UPDATED_STATUS_FINISH);
                                }
                                ImportEPGActivity.this.liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, "2");
                                if (ImportEPGActivity.this.context != null) {
                                    String action = ImportEPGActivity.this.getIntent().getAction();
                                    if ("redirect_epg_category".equals(action)) {
                                        ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, NewEPGCategoriesActivity.class));
                                        ImportEPGActivity.this.finish();
                                    } else if ("redirect_live_tv_epg_expired".equals(action)) {
                                        ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, LiveActivityNewFlow.class));
                                        ImportEPGActivity.this.finish();
                                    } else if ("redirect_live_tv_update_disabls".equals(action)) {
                                        ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, LiveActivityNewFlow.class));
                                        ImportEPGActivity.this.finish();
                                    } else {
                                        ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, NewDashboardActivity.class));
                                        ImportEPGActivity.this.finish();
                                    }
                                }
                            }

                            public void onCancelled() {
                                this.running = false;
                            }
                        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                    } else {
                        new AsyncTask<String, Integer, Boolean>() {
                            /* class com.nst.yourname.view.activity.ImportEPGActivity.PostAsync.AnonymousClass1NewAsyncTask */
                            final int ITERATIONS = ImportEPGActivity.this.postValueArrayList.size();
                            Context mcontext = null;
                            private volatile boolean running = true;

                            public void onPreExecute() {
                            }

                            public void onProgressUpdate(Integer... numArr) {
                            }

                            {
                                this.mcontext = ImportEPGActivity.this.context;
                            }

                            public Boolean doInBackground(String... strArr) {
                                publishProgress(0);
                                if (ImportEPGActivity.this.liveStreamDBHandler != null) {
                                    ImportEPGActivity.this.liveStreamDBHandler.addEPG(ImportEPGActivity.this.postValueArrayList);
                                }
                                return true;
                            }

                            public void onPostExecute(Boolean bool) {
                                int size = ImportEPGActivity.this.postValueArrayList.size();
                                SharedPreferences unused = ImportEPGActivity.this.loginPreferencesAfterLogin = ImportEPGActivity.this.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                                ImportEPGActivity.this.loginPreferencesAfterLogin.getString(AppConst.SKIP_BUTTON_PREF, "");
                                Context context = ImportEPGActivity.this.context;
                                Utils.showToast(context, ImportEPGActivity.this.getResources().getString(R.string.epg_imported) + " (" + size + ")");
                                if (ImportEPGActivity.this.liveStreamDBHandler != null) {
                                    ImportEPGActivity.this.liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG, "2", AppConst.DB_UPDATED_STATUS_FINISH);
                                }
                                ImportEPGActivity.this.liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, "2");
                                if (ImportEPGActivity.this.context != null) {
                                    String action = ImportEPGActivity.this.getIntent().getAction();
                                    if ("redirect_epg_category".equals(action)) {
                                        ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, NewEPGCategoriesActivity.class));
                                        ImportEPGActivity.this.finish();
                                    } else if ("redirect_live_tv_epg_expired".equals(action)) {
                                        ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, LiveActivityNewFlow.class));
                                        ImportEPGActivity.this.finish();
                                    } else if ("redirect_live_tv_update_disabls".equals(action)) {
                                        ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, LiveActivityNewFlow.class));
                                        ImportEPGActivity.this.finish();
                                    } else {
                                        ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, NewDashboardActivity.class));
                                        ImportEPGActivity.this.finish();
                                    }
                                }
                            }

                            public void onCancelled() {
                                this.running = false;
                            }
                        }.execute(new String[0]);
                    }
                } catch (Exception unused) {
                    if (ImportEPGActivity.this.liveStreamDBHandler != null) {
                        ImportEPGActivity.this.liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG, "2", AppConst.DB_UPDATED_STATUS_FINISH);
                    }
                    if (ImportEPGActivity.this.context != null) {
                        String action = ImportEPGActivity.this.getIntent().getAction();
                        if ("redirect_epg_category".equals(action)) {
                            ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, NewEPGCategoriesActivity.class));
                            ImportEPGActivity.this.finish();
                        } else if ("redirect_live_tv_epg_expired".equals(action)) {
                            ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, LiveActivityNewFlow.class));
                            ImportEPGActivity.this.finish();
                        } else if ("redirect_live_tv_update_disabls".equals(action)) {
                            ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, LiveActivityNewFlow.class));
                            ImportEPGActivity.this.finish();
                        } else {
                            ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, NewDashboardActivity.class));
                            ImportEPGActivity.this.finish();
                        }
                    }
                }
            } else {
                if (ImportEPGActivity.this.liveStreamDBHandler != null) {
                    ImportEPGActivity.this.liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG, "2", AppConst.DB_UPDATED_STATUS_FINISH);
                }
                if (ImportEPGActivity.this.context != null) {
                    String action2 = ImportEPGActivity.this.getIntent().getAction();
                    if ("redirect_epg_category".equals(action2)) {
                        ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, NewEPGCategoriesActivity.class));
                        ImportEPGActivity.this.finish();
                    } else if ("redirect_live_tv_epg_expired".equals(action2)) {
                        ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, LiveActivityNewFlow.class));
                        ImportEPGActivity.this.finish();
                    } else if ("redirect_live_tv_update_disabls".equals(action2)) {
                        ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, LiveActivityNewFlow.class));
                        ImportEPGActivity.this.finish();
                    } else {
                        ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, NewDashboardActivity.class));
                        ImportEPGActivity.this.finish();
                    }
                }
            }
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

    private String currentDateValue() {
        return Utils.parseDateToddMMyyyy(Calendar.getInstance().getTime().toString());
    }

    private void addDBStatus(LiveStreamDBHandler liveStreamDBHandler2, String str) {
        DatabaseUpdatedStatusDBModel databaseUpdatedStatusDBModel = new DatabaseUpdatedStatusDBModel();
        databaseUpdatedStatusDBModel.setDbUpadatedStatusState("");
        databaseUpdatedStatusDBModel.setDbLastUpdatedDate(str);
        databaseUpdatedStatusDBModel.setDbCategory(AppConst.DB_EPG);
        databaseUpdatedStatusDBModel.setDbCategoryID("2");
        liveStreamDBHandler2.addDBUpdatedStatus(databaseUpdatedStatusDBModel);
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

    @Override // com.nst.yourname.view.interfaces.XMLTVInterface
    public void epgXMLTV(XMLTVCallback xMLTVCallback) {
        if (xMLTVCallback != null && this.context != null && xMLTVCallback.programmePojos != null) {
            if (this.liveStreamDBHandler != null) {
                this.liveStreamDBHandler.makeEmptyEPG();
            }
            if (this.tvImportingEpg != null) {
                this.tvImportingEpg.setText(getResources().getString(R.string.importing_epg));
            }
            if (Build.VERSION.SDK_INT >= 11) {
                new AsyncTask<String, Integer, Boolean>() {
                    /* class com.nst.yourname.view.activity.ImportEPGActivity.AnonymousClass1NewAsyncTask */
                    Context mcontext = null;
                    private volatile boolean running = true;
                    XMLTVCallback val$xmltvCallback;
                    final int ITERATIONS = val$xmltvCallback.programmePojos.size();

                    public void onPreExecute() {
                    }

                    public void onProgressUpdate(Integer... numArr) {
                    }

                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportEPGActivity, android.content.Context */
                    {
                        this.val$xmltvCallback = xMLTVCallback;
                        this.mcontext = context;
                    }

                    public Boolean doInBackground(String... strArr) {
                        publishProgress(0);
                        if (ImportEPGActivity.this.liveStreamDBHandler != null) {
                            ImportEPGActivity.this.liveStreamDBHandler.addEPG(this.val$xmltvCallback.programmePojos);
                        }
                        return true;
                    }

                    public void onPostExecute(Boolean bool) {
                        int size = this.val$xmltvCallback.programmePojos.size();
                        SharedPreferences unused = ImportEPGActivity.this.loginPreferencesAfterLogin = ImportEPGActivity.this.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                        ImportEPGActivity.this.loginPreferencesAfterLogin.getString(AppConst.SKIP_BUTTON_PREF, "");
                        Context context = ImportEPGActivity.this.context;
                        Utils.showToast(context, ImportEPGActivity.this.getResources().getString(R.string.epg_imported) + " (" + size + ")");
                        if (ImportEPGActivity.this.liveStreamDBHandler != null) {
                            ImportEPGActivity.this.liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG, "2", AppConst.DB_UPDATED_STATUS_FINISH);
                        }
                        ImportEPGActivity.this.liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, "2");
                        if (ImportEPGActivity.this.context != null) {
                            String action = ImportEPGActivity.this.getIntent().getAction();
                            if ("redirect_epg_category".equals(action)) {
                                ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, NewEPGCategoriesActivity.class));
                                ImportEPGActivity.this.finish();
                            } else if ("redirect_live_tv_epg_expired".equals(action)) {
                                ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, LiveActivityNewFlow.class));
                                ImportEPGActivity.this.finish();
                            } else if ("redirect_live_tv_update_disabls".equals(action)) {
                                ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, LiveActivityNewFlow.class));
                                ImportEPGActivity.this.finish();
                            } else {
                                ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, NewDashboardActivity.class));
                                ImportEPGActivity.this.finish();
                            }
                        }
                    }

                    public void onCancelled() {
                        this.running = false;
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
            } else {
                new AsyncTask<String, Integer, Boolean>() {
                    /* class com.nst.yourname.view.activity.ImportEPGActivity.AnonymousClass1NewAsyncTask */
                    Context mcontext = null;
                    private volatile boolean running = true;
                    XMLTVCallback val$xmltvCallback;
                    final int ITERATIONS = this.val$xmltvCallback.programmePojos.size();

                    public void onPreExecute() {
                    }

                    public void onProgressUpdate(Integer... numArr) {
                    }

                    /* Incorrect method signature, types: com.nst.yourname.view.activity.ImportEPGActivity, android.content.Context */
                    {
                        this.val$xmltvCallback = xMLTVCallback;
                        this.mcontext = context;
                    }

                    public Boolean doInBackground(String... strArr) {
                        publishProgress(0);
                        if (ImportEPGActivity.this.liveStreamDBHandler != null) {
                            ImportEPGActivity.this.liveStreamDBHandler.addEPG(this.val$xmltvCallback.programmePojos);
                        }
                        return true;
                    }

                    public void onPostExecute(Boolean bool) {
                        int size = this.val$xmltvCallback.programmePojos.size();
                        SharedPreferences unused = ImportEPGActivity.this.loginPreferencesAfterLogin = ImportEPGActivity.this.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
                        ImportEPGActivity.this.loginPreferencesAfterLogin.getString(AppConst.SKIP_BUTTON_PREF, "");
                        Context context = ImportEPGActivity.this.context;
                        Utils.showToast(context, ImportEPGActivity.this.getResources().getString(R.string.epg_imported) + " (" + size + ")");
                        if (ImportEPGActivity.this.liveStreamDBHandler != null) {
                            ImportEPGActivity.this.liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG, "2", AppConst.DB_UPDATED_STATUS_FINISH);
                        }
                        ImportEPGActivity.this.liveStreamDBHandler.getdateDBStatus(AppConst.DB_EPG, "2");
                        if (ImportEPGActivity.this.context != null) {
                            String action = ImportEPGActivity.this.getIntent().getAction();
                            if ("redirect_epg_category".equals(action)) {
                                ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, NewEPGCategoriesActivity.class));
                                ImportEPGActivity.this.finish();
                            } else if ("redirect_live_tv_epg_expired".equals(action)) {
                                ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, LiveActivityNewFlow.class));
                                ImportEPGActivity.this.finish();
                            } else if ("redirect_live_tv_update_disabls".equals(action)) {
                                ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, LiveActivityNewFlow.class));
                                ImportEPGActivity.this.finish();
                            } else {
                                ImportEPGActivity.this.startActivity(new Intent(ImportEPGActivity.this.context, NewDashboardActivity.class));
                                ImportEPGActivity.this.finish();
                            }
                        }
                    }

                    public void onCancelled() {
                        this.running = false;
                    }
                }.execute(new String[0]);
            }
        } else if (this.context != null) {
            if (this.liveStreamDBHandler != null) {
                this.liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG, "2", AppConst.DB_UPDATED_STATUS_FINISH);
            }
            String action = getIntent().getAction();
            if ("redirect_epg_category".equals(action)) {
                startActivity(new Intent(this.context, NewEPGCategoriesActivity.class));
                finish();
            } else if ("redirect_live_tv_epg_expired".equals(action)) {
                startActivity(new Intent(this.context, LiveActivityNewFlow.class));
                finish();
            } else if ("redirect_live_tv_update_disabls".equals(action)) {
                startActivity(new Intent(this.context, LiveActivityNewFlow.class));
                finish();
            } else {
                startActivity(new Intent(this.context, NewDashboardActivity.class));
                finish();
            }
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        Utils.appResume(this.context);
        getWindow().setFlags(1024, 1024);
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFailed(String str) {
        Utils.showToast(this.context, getResources().getString(R.string.network_error));
    }

    @Override // com.nst.yourname.view.interfaces.XMLTVInterface
    public void epgXMLTVUpdateFailed(String str) {
        if (str.equals(AppConst.DB_UPDATED_STATUS_FAILED) && this.liveStreamDBHandler != null) {
            this.liveStreamDBHandler.updateDBStatus(AppConst.DB_EPG, "2", AppConst.DB_UPDATED_STATUS_FAILED);
        }
        startActivity(new Intent(this, NewDashboardActivity.class));
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, NewDashboardActivity.class));
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        finish();
    }
}
