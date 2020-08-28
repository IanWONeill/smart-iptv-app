package com.nst.yourname.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.nst.yourname.R;
import com.nst.yourname.WebServiceHandler.Globals;
import com.nst.yourname.WebServiceHandler.MainAsynListener;
import com.nst.yourname.WebServiceHandler.RavSharedPrefrences;
import com.nst.yourname.WebServiceHandler.Webservices;
import com.nst.yourname.miscelleneious.FileChooserDialog;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.MultiUserDBModel;
import com.nst.yourname.model.callback.LoginCallback;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.MultiUserDBHandler;
import com.nst.yourname.model.database.RecentWatchDBHandler;
import com.nst.yourname.model.database.SeriesRecentWatchDatabase;
import com.nst.yourname.model.database.SeriesStreamsDatabaseHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.presenter.EditUserPresenter;
import com.nst.yourname.presenter.LoginPresenter;
import com.nst.yourname.view.activity.ImportM3uActivity;
import com.nst.yourname.view.activity.ImportStreamsActivity;
import com.nst.yourname.view.activity.MultiUserActivity;
import com.nst.yourname.view.activity.NewDashboardActivity;
import com.nst.yourname.view.adapter.AddedExternalPlayerAdapter;
import com.nst.yourname.view.interfaces.EditUserInterface;
import com.nst.yourname.view.interfaces.LoginInterface;
import com.nst.yourname.view.muparse.M3UParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

@SuppressWarnings("all")
public class MultiUserAdapter extends RecyclerView.Adapter<MultiUserAdapter.MyViewHolder> implements LoginInterface, EditUserInterface, MainAsynListener<String> {
    static final boolean $assertionsDisabled = false;
    private static final String EXT_INF = "#EXTINF";
    private static final String EXT_M3U = "#EXTM3U";
    private static final String EXT_URL = "http://";
    private static final String EXT_URL_HTTPS = "https://";
    public static PopupWindow changeSortPopUp;
    static int urlCountValue;
    String FirstMdkey;
    String SecondMdkey;
    MultiUserActivity activity;
    private Button browseBT;
    private TextView browseErrorTV;
    Button bt_browse;
    public String chosenDIR = "";
    Button closedBT;
    public Context context;
    String currentDate1 = currentDateValue();
    String currentname;
    String currentpassword;
    String currentusername;
    long dateDifference = 0;
    public EditUserPresenter editUserPresenter;
    public String editanyname = "";
    public String editpassword = "";
    public String editusername = "";
    EditText etM3uLine;
    EditText etM3uLineFile;
    EditText etName;
    private DatabaseHandler favDBHandler;
    private TextView filePathTV;
    private RadioButton fileRB;
    private boolean firstTimeFlag = true;
    InputStream is;
    private boolean isDataload = true;
    boolean isEditing = false;
    private boolean isdataloadOrnot = true;
    String key;
    int listPosition = 0;
    public LiveStreamDBHandler liveStreamDBHandler;
    public LinearLayout ll_add_new_user;
    private SharedPreferences loginPreferencesAfterLoginChannels;
    private SharedPreferences loginPreferencesAfterLoginEPG;
    public SharedPreferences loginPreferencesRemember;
    public SharedPreferences loginPreferencesServerURl;
    public SharedPreferences.Editor loginPreferencesServerURlPut;
    public SharedPreferences loginPreferencesSharedPref_allowed_format;
    private SharedPreferences loginPreferencesSharedPref_epg_channel_update;
    public SharedPreferences loginPreferencesSharedPref_time_format;
    public SharedPreferences.Editor loginPrefsEditorBeforeLogin;
    private SharedPreferences.Editor loginPrefsEditorChannels;
    private SharedPreferences.Editor loginPrefsEditorEPG;
    private SharedPreferences.Editor loginPrefsEditor_epgchannelupdate;
    public SharedPreferences.Editor loginPrefsEditor_fomat;
    public SharedPreferences.Editor loginPrefsEditor_timefomat;
    private LoginPresenter loginPresenter;
    String m3URL = "";
    private String m3ulineFile = "";
    public MultiUserDBHandler multiUserDBHandler;
    MultiUserDBHandler multiuserdbhandler;
    String name1 = "";
    final M3UParser parser = new M3UParser();
    String password2 = "";
    private ProgressDialog progressDialog;
    private RadioGroup radioGroup;
    int random;
    RadioButton rbFile;
    RadioButton rbM3U;
    String reqString;
    String salt;
    Button saveBT;
    Button savePasswordBT;
    private String selected_language = "";
    private SeriesStreamsDatabaseHandler seriesStreamsDatabaseHandler;
    ArrayList<String> serverList = new ArrayList<>();
    ArrayList<String> serverList_panel = new ArrayList<>();
    String serverurl;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    TextView tv_browse_error;
    public TextView tv_file_path;
    public TextView tv_list_options;
    TextView tv_servername;
    String type;
    private RadioButton urlRB;
    String urls = "";
    public List<MultiUserDBModel> userlist;
    String username1 = "";
    String version;

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void reValidateLogin(LoginCallback loginCallback, String str, int i, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
    }

    /*public class MyViewHolder_ViewBinding implements Unbinder {
        private MyViewHolder target;

        @UiThread
        public MyViewHolder_ViewBinding(MyViewHolder myViewHolder, View view) {
            this.target = myViewHolder;
            myViewHolder.tvMovieCategoryName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_movie_category_name, "field 'tvMovieCategoryName'", TextView.class);
            myViewHolder.tvServerName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_servername, "field 'tvServerName'", TextView.class);
            myViewHolder.ivUserimg = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_user_img, "field 'ivUserimg'", ImageView.class);
            myViewHolder.tvUserName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_username, "field 'tvUserName'", TextView.class);
            myViewHolder.pbPagingLoader = (ProgressBar) Utils.findRequiredViewAsType(view, R.id.pb_paging_loader, "field 'pbPagingLoader'", ProgressBar.class);
            myViewHolder.rlOuter = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_outer, "field 'rlOuter'", RelativeLayout.class);
            myViewHolder.rlListOfCategories = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_list_of_categories, "field 'rlListOfCategories'", RelativeLayout.class);
            myViewHolder.testing = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.testing, "field 'testing'", RelativeLayout.class);
            myViewHolder.rlDelete = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.delete, "field 'rlDelete'", RelativeLayout.class);
            myViewHolder.tvXubCount = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_sub_cat_count, "field 'tvXubCount'", TextView.class);
        }

        @Override // butterknife.Unbinder
        @CallSuper
        public void unbind() {
            MyViewHolder myViewHolder = this.target;
            if (myViewHolder != null) {
                this.target = null;
                myViewHolder.tvMovieCategoryName = null;
                myViewHolder.tvServerName = null;
                myViewHolder.ivUserimg = null;
                myViewHolder.tvUserName = null;
                myViewHolder.pbPagingLoader = null;
                myViewHolder.rlOuter = null;
                myViewHolder.rlListOfCategories = null;
                myViewHolder.testing = null;
                myViewHolder.rlDelete = null;
                myViewHolder.tvXubCount = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }*/

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void atStart() {
        if (this.progressDialog != null) {
            this.progressDialog.show();
        }
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFinish() {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
    }

    @Override // com.nst.yourname.view.interfaces.BaseInterface
    public void onFailed(String str) {
        onFinish();
        com.nst.yourname.miscelleneious.common.Utils.showToast(this.context, str);
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void validateLogin(LoginCallback loginCallback, String str) {
        if (loginCallback == null || loginCallback.getUserLoginInfo() == null) {
            onFinish();
            onFailed(this.context.getResources().getString(R.string.invalid_server_response));
        } else if (loginCallback.getUserLoginInfo().getAuth().intValue() == 1) {
            String status = loginCallback.getUserLoginInfo().getStatus();
            if (status.equals("Active")) {
                String username = loginCallback.getUserLoginInfo().getUsername();
                String password = loginCallback.getUserLoginInfo().getPassword();
                String port = loginCallback.getServerInfo().getPort();
                String url = loginCallback.getServerInfo().getUrl();
                String expDate = loginCallback.getUserLoginInfo().getExpDate();
                String isTrial = loginCallback.getUserLoginInfo().getIsTrial();
                String activeCons = loginCallback.getUserLoginInfo().getActiveCons();
                String createdAt = loginCallback.getUserLoginInfo().getCreatedAt();
                String maxConnections = loginCallback.getUserLoginInfo().getMaxConnections();
                SharedPreferences.Editor edit = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).edit();
                SharedPreferences sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0);
                SharedPreferences.Editor edit2 = sharedPreferences.edit();
                String string = sharedPreferences.getString("name", "");
                String string2 = sharedPreferences.getString("username", "");
                String string3 = sharedPreferences.getString("password", "");
                String string4 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "");
                edit2.putString("name", this.currentname);
                edit2.putString("username", username);
                edit2.putString("password", password);
                edit2.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, url);
                new MultiUserDBHandler(this.context).updateMultiUser(SharepreferenceDBHandler.getUserID(this.context), url);
                edit.putString("username", username);
                edit.putString("password", password);
                edit.putString(AppConst.LOGIN_PREF_SERVER_PORT, port);
                edit.putString(AppConst.LOGIN_PREF_SERVER_URL, url);
                edit.putString(AppConst.LOGIN_PREF_EXP_DATE, expDate);
                edit.putString(AppConst.LOGIN_PREF_IS_TRIAL, isTrial);
                edit.putString(AppConst.LOGIN_PREF_ACTIVE_CONS, activeCons);
                edit.putString(AppConst.LOGIN_PREF_CREATE_AT, createdAt);
                edit.putString(AppConst.LOGIN_PREF_MAX_CONNECTIONS, maxConnections);
                edit.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, url);
                edit.apply();
                edit2.apply();
                this.loginPreferencesSharedPref_allowed_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
                this.loginPrefsEditor_fomat = this.loginPreferencesSharedPref_allowed_format.edit();
                this.loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
                this.loginPrefsEditor_timefomat = this.loginPreferencesSharedPref_time_format.edit();
                String string5 = this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
                if (string5 != null && string5.equals("")) {
                    if (com.nst.yourname.miscelleneious.common.Utils.getIsXtream1_06(this.context)) {
                        this.loginPrefsEditor_fomat.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "ts");
                    } else {
                        this.loginPrefsEditor_fomat.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "default");
                    }
                    this.loginPrefsEditor_fomat.apply();
                }
                String string6 = this.loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "");
                if (string6 != null && string6.equals("")) {
                    this.loginPrefsEditor_timefomat.putString(AppConst.LOGIN_PREF_TIME_FORMAT, "HH:mm");
                    this.loginPrefsEditor_timefomat.apply();
                }
                this.loginPreferencesRemember = this.context.getSharedPreferences(AppConst.SHARED_PREFERENCE_REMEBER_ME, 0);
                this.loginPrefsEditorBeforeLogin = this.loginPreferencesRemember.edit();
                this.loginPrefsEditorBeforeLogin.putBoolean(AppConst.PREF_SAVE_LOGIN, true);
                this.loginPrefsEditorBeforeLogin.apply();
                onFinish();
                Toast.makeText(this.context, this.context.getResources().getString(R.string.logged_in), 0).show();
                if (this.context != null && this.currentname.equals(string) && this.currentusername.equals(string2) && this.currentpassword.equals(string3) && url.equals(string4)) {
                    this.context.startActivity(new Intent(this.context, NewDashboardActivity.class));
                    ((Activity) this.context).finish();
                } else if (this.context != null) {
                    if (this.liveStreamDBHandler.getEPGCount() > 0 && this.liveStreamDBHandler != null) {
                        String currentDateValue = currentDateValue();
                        this.liveStreamDBHandler.makeEmptyEPG();
                        this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_EPG, "2", "", currentDateValue);
                    }
                    this.context.startActivity(new Intent(this.context, ImportStreamsActivity.class));
                    ((Activity) this.context).finish();
                }
            } else {
                onFinish();
                Context context2 = this.context;
                Toast.makeText(context2, this.context.getResources().getString(R.string.invalid_status) + status, 0).show();
            }
        } else if (str == AppConst.VALIDATE_LOGIN) {
            onFinish();
            Toast.makeText(this.context, this.context.getResources().getString(R.string.invalid_details), 0).show();
        }
    }

    @SuppressLint({"StaticFieldLeak"})
    private class _checkNetworkAvailable extends AsyncTask<String, Void, Boolean> {
        String checkingTypeOnClickCNA = "";
        String currentnameCNA = "";
        boolean isEditingCNA = false;
        String serverurlCNA = "";
        String typeCNA = "";
        int userIDCNA = -1;

        public void onPreExecute() {
        }

        public _checkNetworkAvailable() {
        }

        public _checkNetworkAvailable(String str, String str2, int i, String str3, String str4, boolean z) {
            this.currentnameCNA = str;
            this.serverurlCNA = str2;
            this.userIDCNA = i;
            this.checkingTypeOnClickCNA = str3;
            this.typeCNA = str4;
            this.isEditingCNA = z;
        }

        public Boolean doInBackground(String... strArr) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(strArr[0]).openConnection();
                boolean z = true;
                httpURLConnection.setInstanceFollowRedirects(true);
                httpURLConnection.setConnectTimeout(30000);
                httpURLConnection.setReadTimeout(30000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode != 200) {
                    if (responseCode != 405) {
                        z = false;
                    }
                }
                return Boolean.valueOf(z);
            } catch (Exception e) {
                Log.e("Google", e.toString());
                return false;
            }
        }

        public void onPostExecute(Boolean bool) {
            if (bool.booleanValue()) {
                new Handler().postDelayed(new Runnable() {
                    /* class com.nst.yourname.view.adapter.MultiUserAdapter._checkNetworkAvailable.AnonymousClass1 */

                    public void run() {
                        new DwnloadFileFromUrl().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MultiUserAdapter.this.serverurl);
                    }
                }, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
                if (MultiUserAdapter.this.serverurl != null && MultiUserAdapter.this.isEditing) {
                    MultiUserAdapter.this.isEditing = false;
                    MultiUserAdapter.this.multiUserDBHandler.updatemultiusersM3U(this.userIDCNA, this.currentnameCNA, this.checkingTypeOnClickCNA, this.serverurlCNA);
                    MultiUserAdapter.this.onFinish();
                    MultiUserAdapter.changeSortPopUp.dismiss();
                    MultiUserAdapter.this.compareUrl();
                    return;
                }
                return;
            }
            MultiUserAdapter.this.isEditing = false;
            MultiUserAdapter.this.onFinish();
            com.nst.yourname.miscelleneious.common.Utils.showToast(MultiUserAdapter.this.context, MultiUserAdapter.this.context.getResources().getString(R.string.file_url_not_valid));
        }
    }

    public String currentDateValue() {
        return com.nst.yourname.miscelleneious.common.Utils.parseDateToddMMyyyy(Calendar.getInstance().getTime().toString());
    }

    public void compareUrl() {
        URL url;
        try {
            url = new URL(this.serverurl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            url = null;
        }
        if (url.getPort() != -1) {
            url.getPort();
        }
        SharedPreferences.Editor edit = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).edit();
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0);
        SharedPreferences.Editor edit2 = sharedPreferences.edit();
        String string = sharedPreferences.getString("name", "");
        String string2 = sharedPreferences.getString("username", "");
        String string3 = sharedPreferences.getString("password", "");
        String string4 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "");
        edit2.putString("name", this.currentname);
        edit2.putString("username", "playlist");
        edit2.putString("password", "playlist");
        edit2.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, this.serverurl);
        edit.putString("username", "playlist");
        edit.putString("password", "playlist");
        edit.putString(AppConst.LOGIN_PREF_SERVER_PORT, "");
        edit.putString(AppConst.LOGIN_PREF_SERVER_URL, this.serverurl);
        edit.putString(AppConst.LOGIN_PREF_SERVER_M3U_LINE, this.serverurl);
        edit.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, this.serverurl);
        edit.apply();
        edit2.apply();
        this.loginPreferencesSharedPref_allowed_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
        this.loginPrefsEditor_fomat = this.loginPreferencesSharedPref_allowed_format.edit();
        this.loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
        this.loginPrefsEditor_timefomat = this.loginPreferencesSharedPref_time_format.edit();
        String string5 = this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
        if (string5 != null && string5.equals("")) {
            if (com.nst.yourname.miscelleneious.common.Utils.getIsXtream1_06(this.context)) {
                this.loginPrefsEditor_fomat.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "ts");
            } else {
                this.loginPrefsEditor_fomat.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "default");
            }
            this.loginPrefsEditor_fomat.apply();
        }
        String string6 = this.loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "");
        if (string6 != null && string6.equals("")) {
            this.loginPrefsEditor_timefomat.putString(AppConst.LOGIN_PREF_TIME_FORMAT, "HH:mm");
            this.loginPrefsEditor_timefomat.apply();
        }
        this.loginPreferencesRemember = this.context.getSharedPreferences(AppConst.SHARED_PREFERENCE_REMEBER_ME, 0);
        this.loginPrefsEditorBeforeLogin = this.loginPreferencesRemember.edit();
        this.loginPrefsEditorBeforeLogin.putBoolean(AppConst.PREF_SAVE_LOGIN, true);
        this.loginPrefsEditorBeforeLogin.apply();
        onFinish();
        Toast.makeText(this.context, this.context.getResources().getString(R.string.logged_in), 0).show();
        if (this.context != null && this.currentname.equals(string) && this.currentusername.equals(string2) && this.currentpassword.equals(string3) && this.serverurl.equals(string4)) {
            this.context.startActivity(new Intent(this.context, NewDashboardActivity.class));
            ((Activity) this.context).finish();
        } else if (this.context != null) {
            if (this.liveStreamDBHandler.getEPGCount() > 0 && this.liveStreamDBHandler != null) {
                String currentDateValue = currentDateValue();
                this.liveStreamDBHandler.makeEmptyEPG();
                this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_EPG, "2", "", currentDateValue);
            }
            this.context.startActivity(new Intent(this.context, ImportM3uActivity.class));
            ((Activity) this.context).finish();
        }
    }

    @Override // com.nst.yourname.view.interfaces.EditUserInterface
    public void validateLogin(LoginCallback loginCallback, String str, String str2, String str3, String str4, String str5, MultiUserDBModel multiUserDBModel) {
        String str6 = str2;
        if (loginCallback != null) {
            try {
                if (loginCallback.getUserLoginInfo() != null) {
                    if (loginCallback.getUserLoginInfo().getAuth().intValue() == 1) {
                        String status = loginCallback.getUserLoginInfo().getStatus();
                        if (status.equals("Active")) {
                            String username = loginCallback.getUserLoginInfo().getUsername();
                            String password = loginCallback.getUserLoginInfo().getPassword();
                            String port = loginCallback.getServerInfo().getPort();
                            String url = loginCallback.getServerInfo().getUrl();
                            String expDate = loginCallback.getUserLoginInfo().getExpDate();
                            String isTrial = loginCallback.getUserLoginInfo().getIsTrial();
                            String activeCons = loginCallback.getUserLoginInfo().getActiveCons();
                            String createdAt = loginCallback.getUserLoginInfo().getCreatedAt();
                            String maxConnections = loginCallback.getUserLoginInfo().getMaxConnections();
                            String serverProtocal = loginCallback.getServerInfo().getServerProtocal();
                            String httpsPort = loginCallback.getServerInfo().getHttpsPort();
                            String rtmpPort = loginCallback.getServerInfo().getRtmpPort();
                            SharedPreferences.Editor edit = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).edit();
                            SharedPreferences sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0);
                            SharedPreferences.Editor edit2 = sharedPreferences.edit();
                            String string = sharedPreferences.getString("name", "");
                            String string2 = sharedPreferences.getString("username", "");
                            String string3 = sharedPreferences.getString("password", "");
                            String string4 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "");
                            edit2.putString("name", str6);
                            edit2.putString("username", username);
                            edit2.putString("password", password);
                            edit2.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, url);
                            MultiUserDBHandler multiUserDBHandler2 = new MultiUserDBHandler(this.context);
                            int userID = SharepreferenceDBHandler.getUserID(this.context);
                            SharedPreferences.Editor editor = edit2;
                            String str7 = string;
                            String str8 = rtmpPort;
                            String str9 = httpsPort;
                            int i = userID;
                            String str10 = string4;
                            String str11 = serverProtocal;
                            String str12 = string2;
                            String str13 = string3;
                            String str14 = maxConnections;
                            String str15 = createdAt;
                            String str16 = activeCons;
                            String str17 = isTrial;
                            multiUserDBHandler2.updateEditMultiUserdetails(userID, str2, str3, str4, AppConst.SERVER_URL_FOR_MULTI_USER, url);
                            if (this.isdataloadOrnot) {
                                new DatabaseHandler(this.context).deleteDataForUser(i);
                                new RecentWatchDBHandler(this.context).deletRecentWatchForThisUser(i);
                                new LiveStreamDBHandler(this.context).deletePasswordDataForUser(i);
                            }
                            edit.putString("username", username);
                            edit.putString("password", password);
                            edit.putString(AppConst.LOGIN_PREF_SERVER_PORT, port);
                            edit.putString(AppConst.LOGIN_PREF_SERVER_URL, url);
                            edit.putString(AppConst.LOGIN_PREF_EXP_DATE, expDate);
                            edit.putString(AppConst.LOGIN_PREF_IS_TRIAL, str17);
                            edit.putString(AppConst.LOGIN_PREF_ACTIVE_CONS, str16);
                            edit.putString(AppConst.LOGIN_PREF_CREATE_AT, str15);
                            edit.putString(AppConst.LOGIN_PREF_MAX_CONNECTIONS, str14);
                            edit.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, url);
                            edit.putString(AppConst.LOGIN_PREF_SERVER_PROTOCOL, str11);
                            edit.putString(AppConst.LOGIN_PREF_SERVER_HTTPS_PORT, str9);
                            edit.putString(AppConst.LOGIN_PREF_SERVER_RTMP_PORT, str8);
                            edit.apply();
                            editor.apply();
                            this.loginPreferencesSharedPref_allowed_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
                            this.loginPrefsEditor_fomat = this.loginPreferencesSharedPref_allowed_format.edit();
                            this.loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
                            this.loginPrefsEditor_timefomat = this.loginPreferencesSharedPref_time_format.edit();
                            String string5 = this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
                            if (string5 != null && string5.equals("")) {
                                if (com.nst.yourname.miscelleneious.common.Utils.getIsXtream1_06(this.context)) {
                                    this.loginPrefsEditor_fomat.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "ts");
                                } else {
                                    this.loginPrefsEditor_fomat.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "default");
                                }
                                this.loginPrefsEditor_fomat.apply();
                            }
                            String string6 = this.loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "");
                            if (string6 != null && string6.equals("")) {
                                this.loginPrefsEditor_timefomat.putString(AppConst.LOGIN_PREF_TIME_FORMAT, "HH:mm");
                                this.loginPrefsEditor_timefomat.apply();
                            }
                            this.loginPreferencesRemember = this.context.getSharedPreferences(AppConst.SHARED_PREFERENCE_REMEBER_ME, 0);
                            this.loginPrefsEditorBeforeLogin = this.loginPreferencesRemember.edit();
                            this.loginPrefsEditorBeforeLogin.putBoolean(AppConst.PREF_SAVE_LOGIN, true);
                            this.loginPrefsEditorBeforeLogin.apply();
                            onFinish();
                            Toast.makeText(this.context, this.context.getResources().getString(R.string.logged_in), 0).show();
                            if (this.context != null && !str2.isEmpty() && str2.equals(str7) && !str3.isEmpty() && str3.equals(str12) && !str4.isEmpty() && str4.equals(str13) && !url.isEmpty() && url.equals(str10)) {
                                changeSortPopUp.dismiss();
                                onFinish();
                                this.context.startActivity(new Intent(this.context, NewDashboardActivity.class));
                                ((Activity) this.context).finish();
                                return;
                            } else if (this.context != null) {
                                if (this.liveStreamDBHandler.getEPGCount() > 0 && this.liveStreamDBHandler != null) {
                                    String currentDateValue = currentDateValue();
                                    this.liveStreamDBHandler.makeEmptyEPG();
                                    this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_EPG, "2", "", currentDateValue);
                                }
                                changeSortPopUp.dismiss();
                                onFinish();
                                if (this.isdataloadOrnot) {
                                    this.context.startActivity(new Intent(this.context, ImportStreamsActivity.class));
                                    ((Activity) this.context).finish();
                                    return;
                                }
                                this.context.startActivity(new Intent(this.context, NewDashboardActivity.class));
                                ((Activity) this.context).finish();
                                return;
                            } else {
                                return;
                            }
                        } else {
                            onFinish();
                            Context context2 = this.context;
                            Toast.makeText(context2, this.context.getResources().getString(R.string.invalid_status) + status, 0).show();
                            return;
                        }
                    } else if (str == AppConst.VALIDATE_LOGIN) {
                        onFinish();
                        Toast.makeText(this.context, this.context.getResources().getString(R.string.invalid_details), 0).show();
                        return;
                    } else {
                        return;
                    }
                }
            } catch (Exception unused) {
                return;
            }
        }
        onFinish();
        onFailed(this.context.getResources().getString(R.string.invalid_server_response));
    }

    private boolean isAnyNameEditedM3U(String str, String str2, MultiUserDBModel multiUserDBModel, String str3) {
        if (str != null && !str.isEmpty() && str.equals(multiUserDBModel.getname()) && str2 != null && !str2.isEmpty() && str2.contains(multiUserDBModel.getmagportal())) {
            return true;
        }
        if (str == null || str.isEmpty() || str.equals(multiUserDBModel.getname())) {
            return false;
        }
        return true;
    }

    private boolean isSameFile(String str, String str2) {
        return !str.equals(str2);
    }

    private boolean isAnyNameEdited(String str, String str2, String str3, String str4, String str5, MultiUserDBModel multiUserDBModel) {
        Context context2 = this.context;
        Toast.makeText(context2, "userID Inside isAnyNameValidation:" + SharepreferenceDBHandler.getUserID(this.context), 1).show();
        boolean equals = str.equals(multiUserDBModel.getname()) ^ true;
        boolean equals2 = str2.equals(multiUserDBModel.getusername());
        boolean equals3 = str3.equals(multiUserDBModel.getpassword());
        boolean contains = str4.contains(multiUserDBModel.getmagportal());
        boolean contains2 = str4.contains(multiUserDBModel.getmagportal2());
        if (!equals || !equals2 || !equals3) {
            return false;
        }
        if (contains || contains2) {
            return true;
        }
        return false;
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface, com.nst.yourname.view.interfaces.EditUserInterface
    public void stopLoader(String str) {
        onFinish();
        com.nst.yourname.miscelleneious.common.Utils.showToast(this.context, str);
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void magFailedtoLogin(String str, String str2, String str3, Context context2) {
        if (!com.nst.yourname.miscelleneious.common.Utils.getIsXtream1_06(context2)) {
            AppConst.isXTREAM_1_0_6 = true;
            com.nst.yourname.miscelleneious.common.Utils.setXtream1_06(context2);
            this.loginPresenter.validateLoginUsingPanelAPI(str2, str3);
        } else if (com.nst.yourname.miscelleneious.common.Utils.getIsXtream1_06(context2)) {
            try {
                onFinish();
                com.nst.yourname.miscelleneious.common.Utils.showToast(context2, str);
            } catch (Exception unused) {
            }
        }
    }

    @Override // com.nst.yourname.view.interfaces.EditUserInterface
    public void magFailedtoLogin2(String str, String str2, String str3, Context context2) {
        if (!com.nst.yourname.miscelleneious.common.Utils.getIsXtream1_06(context2)) {
            AppConst.isXTREAM_1_0_6 = true;
            com.nst.yourname.miscelleneious.common.Utils.setXtream1_06(context2);
            this.loginPresenter.validateLoginUsingPanelAPI(str2, str3);
        } else if (com.nst.yourname.miscelleneious.common.Utils.getIsXtream1_06(context2)) {
            try {
                onFinish();
                com.nst.yourname.miscelleneious.common.Utils.showToast(context2, str);
            } catch (Exception unused) {
            }
        }
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void validateloginMultiDNS(LoginCallback loginCallback, String str, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        String str10;
        if (loginCallback.getUserLoginInfo() == null || loginCallback.getServerInfo() == null) {
            if (arrayList.size() == 0) {
                onFinish();
                com.nst.yourname.miscelleneious.common.Utils.showToast(this.context, "Your Account is invalid or has expired !");
            }
            if (arrayList.size() > 0) {
                storeServerUrls(arrayList, this.urls);
            }
        }
        if (loginCallback != null && loginCallback.getUserLoginInfo() != null && loginCallback.getServerInfo() != null) {
            if (loginCallback.getUserLoginInfo().getAuth().intValue() == 1) {
                String status = loginCallback.getUserLoginInfo().getStatus();
                if (status.equals("Active")) {
                    String username = loginCallback.getUserLoginInfo().getUsername();
                    String password = loginCallback.getUserLoginInfo().getPassword();
                    String port = loginCallback.getServerInfo().getPort();
                    String url = loginCallback.getServerInfo().getUrl();
                    String expDate = loginCallback.getUserLoginInfo().getExpDate();
                    String isTrial = loginCallback.getUserLoginInfo().getIsTrial();
                    String activeCons = loginCallback.getUserLoginInfo().getActiveCons();
                    String createdAt = loginCallback.getUserLoginInfo().getCreatedAt();
                    String maxConnections = loginCallback.getUserLoginInfo().getMaxConnections();
                    List<String> allowedOutputFormats = loginCallback.getUserLoginInfo().getAllowedOutputFormats();
                    if (allowedOutputFormats.size() != 0) {
                        allowedOutputFormats.get(0);
                    }
                    SharedPreferences sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0);
                    String string = sharedPreferences.getString("name", "");
                    String string2 = sharedPreferences.getString("username", "");
                    String string3 = sharedPreferences.getString("password", "");
                    String string4 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "");
                    if (this.isEditing) {
                        this.isEditing = false;
                        this.isDataload = true;
                        if (!this.serverurl.equalsIgnoreCase(AppConst.SERVER_URL_FOR_MULTI_USER)) {
                            int userID = SharepreferenceDBHandler.getUserID(this.context);
                            str10 = string2;
                            new DatabaseHandler(this.context).deleteDataForUser(userID);
                            new RecentWatchDBHandler(this.context).deletRecentWatchForThisUser(userID);
                            new LiveStreamDBHandler(this.context).deletePasswordDataForUser(userID);
                        } else {
                            str10 = string2;
                        }
                        str6 = string4;
                        str5 = string3;
                        str4 = str10;
                        str9 = createdAt;
                        str3 = string;
                        str8 = activeCons;
                        str2 = maxConnections;
                        str7 = isTrial;
                        this.multiUserDBHandler.updateEditMultiUserdetails(SharepreferenceDBHandler.getUserID(this.context), this.editanyname, this.editusername, this.editpassword, AppConst.SERVER_URL_FOR_MULTI_USER, url);
                        SharedPreferences.Editor edit = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0).edit();
                        edit.putString("name", this.editanyname);
                        edit.putString("username", this.editusername);
                        edit.putString("password", this.editpassword);
                        edit.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, url);
                        edit.apply();
                    } else {
                        str6 = string4;
                        str5 = string3;
                        str4 = string2;
                        str3 = string;
                        str2 = maxConnections;
                        str9 = createdAt;
                        str8 = activeCons;
                        str7 = isTrial;
                        SharedPreferences.Editor edit2 = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0).edit();
                        edit2.putString("name", this.currentname);
                        edit2.putString("username", this.currentusername);
                        edit2.putString("password", this.currentpassword);
                        edit2.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, url);
                        edit2.apply();
                    }
                    this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0).getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "").toLowerCase();
                    SharedPreferences.Editor edit3 = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).edit();
                    edit3.putString("username", username);
                    edit3.putString("password", password);
                    edit3.putString(AppConst.LOGIN_PREF_SERVER_PORT, port);
                    edit3.putString(AppConst.LOGIN_PREF_SERVER_URL, url);
                    edit3.putString(AppConst.LOGIN_PREF_EXP_DATE, expDate);
                    edit3.putString(AppConst.LOGIN_PREF_IS_TRIAL, str7);
                    edit3.putString(AppConst.LOGIN_PREF_ACTIVE_CONS, str8);
                    edit3.putString(AppConst.LOGIN_PREF_CREATE_AT, str9);
                    edit3.putString(AppConst.LOGIN_PREF_MAX_CONNECTIONS, str2);
                    String str11 = AppConst.LOGIN_PREF_SERVER_URL_MAG;
                    edit3.putString(str11, url + ":" + port);
                    edit3.apply();
                    this.loginPreferencesSharedPref_allowed_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
                    this.loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
                    this.loginPreferencesSharedPref_epg_channel_update = this.context.getSharedPreferences(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, 0);
                    this.loginPreferencesAfterLoginChannels = this.context.getSharedPreferences(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, 0);
                    this.loginPreferencesAfterLoginEPG = this.context.getSharedPreferences(AppConst.LOGIN_PREF_AUTOMATION_EPG, 0);
                    this.loginPrefsEditor_fomat = this.loginPreferencesSharedPref_allowed_format.edit();
                    this.loginPrefsEditor_timefomat = this.loginPreferencesSharedPref_time_format.edit();
                    this.loginPrefsEditor_epgchannelupdate = this.loginPreferencesSharedPref_epg_channel_update.edit();
                    this.loginPrefsEditorChannels = this.loginPreferencesAfterLoginChannels.edit();
                    this.loginPrefsEditorEPG = this.loginPreferencesAfterLoginEPG.edit();
                    if (this.loginPreferencesAfterLoginChannels.getString(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, "").equals("")) {
                        this.loginPrefsEditorChannels.putString(AppConst.LOGIN_PREF_AUTOMATION_CHANNELS, "checked");
                        this.loginPrefsEditorChannels.apply();
                    }
                    if (this.loginPreferencesAfterLoginEPG.getString(AppConst.LOGIN_PREF_AUTOMATION_EPG, "").equals("")) {
                        this.loginPrefsEditorEPG.putString(AppConst.LOGIN_PREF_AUTOMATION_EPG, "checked");
                        this.loginPrefsEditorEPG.apply();
                    }
                    AppConst.FROM_MULTIUSER_TO_LOGIN = false;
                    if (this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "").equals("")) {
                        this.loginPrefsEditor_fomat.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "ts");
                        this.loginPrefsEditor_fomat.apply();
                    }
                    if (this.loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "").equals("")) {
                        this.loginPrefsEditor_timefomat.putString(AppConst.LOGIN_PREF_TIME_FORMAT, "HH:mm");
                        this.loginPrefsEditor_timefomat.apply();
                    }
                    this.loginPreferencesRemember = this.context.getSharedPreferences(AppConst.SHARED_PREFERENCE_REMEBER_ME, 0);
                    this.loginPrefsEditorBeforeLogin = this.loginPreferencesRemember.edit();
                    this.loginPrefsEditorBeforeLogin.putBoolean(AppConst.PREF_SAVE_LOGIN, true);
                    this.loginPrefsEditorBeforeLogin.apply();
                    if (this.loginPreferencesSharedPref_epg_channel_update.getString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "").equals("")) {
                        this.loginPrefsEditor_epgchannelupdate.putString(AppConst.LOGIN_PREF_EPG_CHANNEL_UPDATE, "all");
                        this.loginPrefsEditor_epgchannelupdate.apply();
                    }
                    this.loginPrefsEditorBeforeLogin.putBoolean(AppConst.PREF_SAVE_LOGIN, true);
                    this.loginPrefsEditorBeforeLogin.apply();
                    this.multiUserDBHandler = new MultiUserDBHandler(this.context);
                    if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
                        onFinish();
                        if (this.context != null && this.currentname.equals(str3) && this.currentusername.equals(str4) && this.currentpassword.equals(str5) && url.equals(str6)) {
                            this.context.startActivity(new Intent(this.context, NewDashboardActivity.class));
                            ((Activity) this.context).finish();
                        } else if (this.context != null) {
                            if (this.liveStreamDBHandler.getEPGCount() > 0 && this.liveStreamDBHandler != null) {
                                String currentDateValue = currentDateValue();
                                this.liveStreamDBHandler.makeEmptyEPG();
                                this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_EPG, "2", "", currentDateValue);
                            }
                            this.context.startActivity(new Intent(this.context, ImportStreamsActivity.class));
                            ((Activity) this.context).finish();
                        }
                    } else if (this.context != null && this.liveStreamDBHandler != null && this.liveStreamDBHandler.getAvailableChannelsCount() > 0) {
                        onFinish();
                        this.context.startActivity(new Intent(this.context, NewDashboardActivity.class));
                        ((Activity) this.context).finish();
                    } else if (this.context != null) {
                        onFinish();
                        this.context.startActivity(new Intent(this.context, ImportStreamsActivity.class));
                        ((Activity) this.context).finish();
                    }
                } else {
                    onFinish();
                    Context context2 = this.context;
                    Toast.makeText(context2, this.context.getResources().getString(R.string.invalid_status) + status, 0).show();
                }
            } else if (str.equals(AppConst.VALIDATE_LOGIN)) {
                onFinish();
                Toast.makeText(this.context, this.context.getResources().getString(R.string.invalid_details), 0).show();
            }
        }
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void stopLoaderMultiDNS(ArrayList<String> arrayList, String str) {
        if (this.progressDialog != null && arrayList.size() == 0) {
            this.progressDialog.dismiss();
            Context context2 = this.context;
            Toast.makeText(context2, this.context.getResources().getString(R.string.error_code_2) + this.context.getResources().getString(R.string.network_error), 0).show();
        }
        if (arrayList.size() > 0) {
            storeServerUrls(arrayList, this.urls);
        }
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void magFailedtoLoginMultiDNS(ArrayList<String> arrayList, ArrayList<String> arrayList2, String str, String str2, String str3) {
        if (arrayList.size() == 0 && arrayList2.size() == 0) {
            onFinish();
            if (!str.equals("")) {
                com.nst.yourname.miscelleneious.common.Utils.showToast(this.context, str);
            } else {
                com.nst.yourname.miscelleneious.common.Utils.showToast(this.context, "Your Account is invalid or expired !");
            }
        }
        if (arrayList.size() > 0) {
            storeServerUrls(arrayList, this.urls);
        } else if (arrayList.size() == 0 && arrayList2.size() > 0) {
            AppConst.isXTREAM_1_0_6 = true;
            com.nst.yourname.miscelleneious.common.Utils.setXtream1_06(this.context);
            storeServerUrls_panel(arrayList2, this.urls);
        }
    }

    @Override // com.nst.yourname.view.interfaces.LoginInterface
    public void magFailedtoLoginMultiDNS2(ArrayList<String> arrayList, ArrayList<String> arrayList2, String str, String str2, String str3) {
        if (arrayList.size() == 0 && arrayList2.size() == 0) {
            onFinish();
            if (!str.equals("")) {
                com.nst.yourname.miscelleneious.common.Utils.showToast(this.context, str);
            } else {
                com.nst.yourname.miscelleneious.common.Utils.showToast(this.context, "Your Account is invalid or expired !");
            }
        }
        if (arrayList.size() > 0) {
            storeServerUrls(arrayList, this.urls);
        } else if (arrayList.size() == 0 && arrayList2.size() > 0) {
            AppConst.isXTREAM_1_0_6 = true;
            com.nst.yourname.miscelleneious.common.Utils.setXtream1_06(this.context);
            storeServerUrls_panel(arrayList2, this.urls);
        }
    }

    public void storeServerUrls(ArrayList<String> arrayList, String str) {
        this.serverList_panel = new ArrayList<>(Arrays.asList(str.split(",")));
        if (str != null && !str.equals("") && !str.isEmpty()) {
            if (urlCountValue == 0) {
                urlCountValue++;
                this.serverList = new ArrayList<>(Arrays.asList(str.split(",")));
                this.serverList_panel = new ArrayList<>(Arrays.asList(str.split(",")));
            } else {
                this.serverList = arrayList;
            }
        }
        if (this.serverList != null && this.serverList.size() >= 2) {
            try {
                this.loginPreferencesServerURlPut.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, this.serverList.get(0).trim());
                this.loginPreferencesServerURlPut.commit();
                this.serverList.remove(0);
                boolean isXtream1_06 = com.nst.yourname.miscelleneious.common.Utils.getIsXtream1_06(this.context);
                if (this.isEditing) {
                    if (isXtream1_06) {
                        try {
                            this.loginPresenter.validateLoginMultiDnsUsingPanelapi(this.editusername, this.editpassword, this.serverList, this.serverList_panel);
                        } catch (Exception unused) {
                        }
                    } else {
                        this.loginPresenter.validateLoginMultiDns(this.editusername, this.editpassword, this.serverList, this.serverList_panel);
                    }
                } else if (isXtream1_06) {
                    this.loginPresenter.validateLoginMultiDnsUsingPanelapi(this.currentusername, this.currentpassword, this.serverList, this.serverList_panel);
                } else {
                    this.loginPresenter.validateLoginMultiDns(this.currentusername, this.currentpassword, this.serverList, this.serverList_panel);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (this.serverList != null && this.serverList.size() == 1) {
            try {
                this.loginPreferencesServerURlPut.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, this.serverList.get(0).trim());
                this.loginPreferencesServerURlPut.commit();
                this.serverList.remove(0);
                boolean isXtream1_062 = com.nst.yourname.miscelleneious.common.Utils.getIsXtream1_06(this.context);
                if (this.isEditing) {
                    if (isXtream1_062) {
                        this.loginPresenter.validateLoginMultiDnsUsingPanelapi(this.editusername, this.editpassword, this.serverList, this.serverList_panel);
                    } else {
                        this.loginPresenter.validateLoginMultiDns(this.editusername, this.editpassword, this.serverList, this.serverList_panel);
                    }
                } else if (isXtream1_062) {
                    this.loginPresenter.validateLoginMultiDnsUsingPanelapi(this.currentusername, this.currentpassword, this.serverList, this.serverList_panel);
                } else {
                    this.loginPresenter.validateLoginMultiDns(this.currentusername, this.currentpassword, this.serverList, this.serverList_panel);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else if (this.serverList != null && this.serverList.size() == 0) {
            onFinish();
            Toast.makeText(this.context, this.context.getResources().getString(R.string.please_check_portal), 0).show();
        }
    }

    private void storeServerUrls_panel(ArrayList<String> arrayList, String str) {
        if (str != null && !str.equals("") && !str.isEmpty()) {
            if (urlCountValue == 0) {
                urlCountValue++;
                this.serverList = new ArrayList<>(Arrays.asList(str.split(",")));
                this.serverList_panel = new ArrayList<>(Arrays.asList(str.split(",")));
            } else {
                this.serverList_panel = arrayList;
            }
        }
        if (this.serverList_panel != null && this.serverList_panel.size() >= 2) {
            try {
                this.loginPreferencesServerURlPut.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, this.serverList_panel.get(0).trim());
                this.loginPreferencesServerURlPut.commit();
                this.serverList_panel.remove(0);
                boolean isXtream1_06 = com.nst.yourname.miscelleneious.common.Utils.getIsXtream1_06(this.context);
                if (this.isEditing) {
                    if (isXtream1_06) {
                        try {
                            this.loginPresenter.validateLoginMultiDnsUsingPanelapi(this.editusername, this.editpassword, this.serverList_panel, this.serverList_panel);
                        } catch (Exception unused) {
                        }
                    } else {
                        this.loginPresenter.validateLoginMultiDns(this.editusername, this.editpassword, this.serverList_panel, this.serverList_panel);
                    }
                } else if (isXtream1_06) {
                    this.loginPresenter.validateLoginMultiDnsUsingPanelapi(this.currentusername, this.currentpassword, this.serverList, this.serverList_panel);
                } else {
                    this.loginPresenter.validateLoginMultiDns(this.currentusername, this.currentpassword, this.serverList, this.serverList_panel);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (this.serverList_panel != null && this.serverList_panel.size() == 1) {
            try {
                this.loginPreferencesServerURlPut.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, this.serverList_panel.get(0).trim());
                this.loginPreferencesServerURlPut.commit();
                this.serverList_panel.remove(0);
                boolean isXtream1_062 = com.nst.yourname.miscelleneious.common.Utils.getIsXtream1_06(this.context);
                if (this.isEditing) {
                    if (isXtream1_062) {
                        this.loginPresenter.validateLoginMultiDnsUsingPanelapi(this.editusername, this.editpassword, this.serverList, this.serverList_panel);
                    } else {
                        this.loginPresenter.validateLoginMultiDns(this.editusername, this.editpassword, this.serverList, this.serverList_panel);
                    }
                } else if (isXtream1_062) {
                    this.loginPresenter.validateLoginMultiDnsUsingPanelapi(this.currentusername, this.currentpassword, this.serverList_panel, this.serverList_panel);
                } else {
                    this.loginPresenter.validateLoginMultiDns(this.currentusername, this.currentpassword, this.serverList_panel, this.serverList_panel);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else if (this.serverList_panel != null && this.serverList_panel.size() == 0) {
            onFinish();
            Toast.makeText(this.context, this.context.getResources().getString(R.string.please_check_portal), 0).show();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_user_img)
        ImageView ivUserimg;
        @BindView(R.id.pb_paging_loader)
        ProgressBar pbPagingLoader;
        @BindView(R.id.delete)
        RelativeLayout rlDelete;
        @BindView(R.id.rl_list_of_categories)
        RelativeLayout rlListOfCategories;
        @BindView(R.id.rl_outer)
        RelativeLayout rlOuter;
        @BindView(R.id.testing)
        RelativeLayout testing;
        @BindView(R.id.tv_movie_category_name)
        TextView tvMovieCategoryName;
        @BindView(R.id.tv_servername)
        TextView tvServerName;
        @BindView(R.id.tv_username)
        TextView tvUserName;
        @BindView(R.id.tv_sub_cat_count)
        TextView tvXubCount;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            setIsRecyclable(false);
        }
    }

    public MultiUserAdapter(MultiUserActivity multiUserActivity, List<MultiUserDBModel> list, Context context2, LinearLayout linearLayout, TextView textView) {
        this.userlist = list;
        this.activity = multiUserActivity;
        this.context = context2;
        this.tv_list_options = textView;
        this.ll_add_new_user = linearLayout;
        this.loginPresenter = new LoginPresenter(this, context2);
        this.editUserPresenter = new EditUserPresenter(this, context2);
        this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
        this.seriesStreamsDatabaseHandler = new SeriesStreamsDatabaseHandler(context2);
        this.favDBHandler = new DatabaseHandler(context2);
        this.multiUserDBHandler = new MultiUserDBHandler(context2);
        this.selected_language = context2.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, 0).getString(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, "English");
        AppConst.isXTREAM_1_0_6 = false;
        com.nst.yourname.miscelleneious.common.Utils.setXtream1_06(context2);
        Webservices.getWebservices = new Webservices(context2);
        appVersionName();
        DEviceVersion();
        getDeviceName();
        GetRandomNumber();
        this.multiuserdbhandler = new MultiUserDBHandler(context2);
        if (context2 != null) {
            this.progressDialog = new ProgressDialog(context2);
            this.progressDialog.setMessage(context2.getResources().getString(R.string.please_wait));
            this.progressDialog.setCanceledOnTouchOutside(false);
            this.progressDialog.setCancelable(false);
            this.progressDialog.setProgressStyle(0);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [?, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @Override // android.support.v7.widget.RecyclerView.Adapter
    @SuppressLint({"RtlHardcoded"})
    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.layout_multiuser_list_item, viewGroup, false);
        this.tv_servername = (TextView) inflate.findViewById(R.id.tv_servername);
        if (this.selected_language.equalsIgnoreCase("Arabic")) {
            this.tv_servername.setGravity(21);
        }
        return new MyViewHolder(inflate);
    }

    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        MyViewHolder myViewHolder2 = myViewHolder;
        int i2 = i;
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0);
        String string = sharedPreferences.getString("name", "");
        String string2 = sharedPreferences.getString("username", "");
        String string3 = sharedPreferences.getString("password", "");
        String string4 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "");
        if (this.userlist != null && this.userlist.size() > 0) {
            final MultiUserDBModel multiUserDBModel = this.userlist.get(i2);
            myViewHolder2.tvMovieCategoryName.setSelected(true);
            this.loginPreferencesServerURl = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0);
            this.loginPreferencesRemember = this.context.getSharedPreferences(AppConst.SHARED_PREFERENCE_REMEBER_ME, 0);
            this.loginPrefsEditorBeforeLogin = this.loginPreferencesRemember.edit();
            final String str = multiUserDBModel.getname();
            final String str2 = multiUserDBModel.getusername();
            final String str3 = multiUserDBModel.getpassword();
            final String str4 = multiUserDBModel.getmagportal();
            String str5 = multiUserDBModel.getmagportal2();
            String m3uType = multiUserDBModel.getM3uType();
            if ((m3uType == null || !m3uType.equals(AppConst.TYPE_M3U_FILE)) && (m3uType == null || !m3uType.equals("url"))) {
                this.type = AppConst.TYPE_API;
            } else {
                this.type = AppConst.TYPE_M3U;
            }
            myViewHolder2.rlDelete.setFocusable(false);
            String str6 = str5 == null ? "" : str5;
            final int autoIdLoggedInUser = this.multiUserDBHandler.getAutoIdLoggedInUser(str, str2, str3, str4, this.type, str6);
            if (this.type == null || !this.type.equals(AppConst.TYPE_M3U) || !AppConst.M3U_LINE_ACTIVE.booleanValue()) {
                myViewHolder2.tvServerName.setVisibility(8);
            } else {
                myViewHolder2.tvServerName.setVisibility(0);
            }
            if (str != null) {
                myViewHolder2.tvMovieCategoryName.setText(str);
            }
            if (this.type.equals(AppConst.TYPE_M3U)) {
                if (str == null || str2 == null || str3 == null || str4 == null || !str.equals(string) || !str4.contains(string4) || !str2.equals(string2) || !str3.equals(string3)) {
                    myViewHolder2.ivUserimg.setImageResource(R.drawable.ic_playlist);
                } else {
                    myViewHolder2.ivUserimg.setImageResource(R.drawable.ic_playlistwg);
                }
                if (multiUserDBModel.getM3uType().equals(AppConst.TYPE_M3U_FILE)) {
                    if (str4 != null) {
                        this.m3ulineFile = str4;
                        TextView textView = myViewHolder2.tvServerName;
                        textView.setText("File : " + str4);
                        myViewHolder2.tvServerName.setSelected(true);
                    }
                } else if (str4 != null) {
                    TextView textView2 = myViewHolder2.tvServerName;
                    textView2.setText("URL : " + str4);
                    myViewHolder2.tvServerName.setSelected(true);
                }
                if (this.multiUserDBHandler != null) {
                    String userEPG = this.multiUserDBHandler.getUserEPG(autoIdLoggedInUser);
                    if (userEPG == null || userEPG.equals("")) {
                        myViewHolder2.tvUserName.setVisibility(8);
                    } else {
                        TextView textView3 = myViewHolder2.tvUserName;
                        textView3.setText("EPG URL : " + userEPG);
                        myViewHolder2.tvUserName.setSelected(true);
                    }
                } else {
                    myViewHolder2.tvUserName.setVisibility(8);
                }
            } else {
                if (str != null && str2 != null && str3 != null && str4 != null && str.equals(string) && str4.contains(string4) && str2.equals(string2) && str3.equals(string3)) {
                    myViewHolder2.ivUserimg.setImageResource(R.drawable.new_user_img_active);
                } else if (str == null || str2 == null || str3 == null || !str.equals(string) || !str6.contains(string4) || !str2.equals(string2) || !str3.equals(string3)) {
                    myViewHolder2.ivUserimg.setImageResource(R.drawable.new_user_img);
                } else {
                    myViewHolder2.ivUserimg.setImageResource(R.drawable.new_user_img_active);
                }
                if (str4 != null) {
                    TextView textView4 = myViewHolder2.tvServerName;
                    textView4.setText("URL : " + str4);
                    myViewHolder2.tvServerName.setSelected(true);
                }
                if (str2 != null) {
                    TextView textView5 = myViewHolder2.tvUserName;
                    textView5.setText(this.context.getResources().getString(R.string.username) + " : " + str2);
                    myViewHolder2.tvUserName.setSelected(true);
                }
            }
            myViewHolder2.rlOuter.setOnLongClickListener(new View.OnLongClickListener() {
                /* class com.nst.yourname.view.adapter.MultiUserAdapter.AnonymousClass1 */

                public boolean onLongClick(View view) {
                    MultiUserAdapter.this.currentname = str;
                    MultiUserAdapter.this.currentusername = str2;
                    MultiUserAdapter.this.currentpassword = str3;
                    MultiUserAdapter.this.serverurl = str4;
                    MultiUserAdapter.this.popmenu(myViewHolder, i, str, autoIdLoggedInUser, view);
                    return true;
                }
            });
            myViewHolder2.rlOuter.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.MultiUserAdapter.AnonymousClass2 */

                public void onClick(View view) {
                    SharedPreferences.Editor unused = MultiUserAdapter.this.loginPreferencesServerURlPut = MultiUserAdapter.this.loginPreferencesServerURl.edit();
                    MultiUserAdapter.this.atStart();
                    MultiUserAdapter.this.loginPrefsEditorBeforeLogin.putString("username", str2);
                    MultiUserAdapter.this.loginPrefsEditorBeforeLogin.putString("password", str3);
                    MultiUserAdapter.this.loginPrefsEditorBeforeLogin.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, str4);
                    MultiUserAdapter.this.loginPrefsEditorBeforeLogin.putString("activationCode", "");
                    MultiUserAdapter.this.loginPrefsEditorBeforeLogin.putString(AppConst.PREF_LOGIN_WITH, AppConst.LOGIN_WITH_DETAILS);
                    MultiUserAdapter.this.loginPrefsEditorBeforeLogin.apply();
                    MultiUserAdapter.this.currentname = str;
                    MultiUserAdapter.this.currentusername = str2;
                    MultiUserAdapter.this.currentpassword = str3;
                    MultiUserAdapter.this.serverurl = str4;
                    MultiUserAdapter.this.loginPreferencesServerURlPut.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, str4);
                    MultiUserAdapter.this.loginPreferencesServerURlPut.apply();
                    String m3uType = multiUserDBModel.getM3uType();
                    if ((m3uType == null || !m3uType.equals(AppConst.TYPE_M3U_FILE)) && (m3uType == null || !m3uType.equals("url"))) {
                        MultiUserAdapter.this.type = AppConst.TYPE_API;
                    } else {
                        MultiUserAdapter.this.type = AppConst.TYPE_M3U;
                    }
                    if (!AppConst.M3U_LINE_ACTIVE.booleanValue() || !MultiUserAdapter.this.type.equals(AppConst.TYPE_M3U)) {
                        SharepreferenceDBHandler.setCurrentAPPType(AppConst.TYPE_API, MultiUserAdapter.this.context);
                        try {
                            SharepreferenceDBHandler.setUserID(autoIdLoggedInUser, MultiUserAdapter.this.context);
                            MultiUserAdapter.this.checkLoginStatus(str2, str3);
                        } catch (Exception unused2) {
                        }
                    } else {
                        SharepreferenceDBHandler.setCurrentAPPType(AppConst.TYPE_M3U, MultiUserAdapter.this.context);
                        if (!multiUserDBModel.getM3uType().equals(AppConst.TYPE_M3U_FILE)) {
                            SharepreferenceDBHandler.setUserID(autoIdLoggedInUser, MultiUserAdapter.this.context);
                            MultiUserAdapter.this.checkLoginStatus("playlist", "");
                            new _checkNetworkAvailable().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MultiUserAdapter.this.serverurl);
                        } else if (new File(MultiUserAdapter.this.serverurl).exists()) {
                            SharepreferenceDBHandler.setUserID(autoIdLoggedInUser, MultiUserAdapter.this.context);
                            MultiUserAdapter.this.checkLoginStatus("playlist", "");
                            new _loadFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MultiUserAdapter.this.serverurl);
                        } else {
                            Toast.makeText(MultiUserAdapter.this.context, MultiUserAdapter.this.context.getResources().getString(R.string.file_not_found), 0).show();
                            MultiUserAdapter.this.onFinish();
                        }
                    }
                }
            });
            myViewHolder2.rlOuter.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder2.rlOuter));
            if (i2 == 0 && this.firstTimeFlag) {
                myViewHolder2.rlOuter.requestFocus();
                this.firstTimeFlag = false;
            }
            myViewHolder2.rlDelete.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder2.rlDelete));
            if (i2 == 0 && this.firstTimeFlag) {
                myViewHolder2.rlDelete.requestFocus();
                this.firstTimeFlag = false;
            }
        }
    }

    public void checkLoginStatus(String str, String str2) {
        if (this.multiuserdbhandler == null || this.multiuserdbhandler.getSaveLoginDate() == null) {
            SequrityApi(str);
            return;
        }
        ArrayList<MultiUserDBModel> saveLoginDate = this.multiuserdbhandler.getSaveLoginDate();
        if (saveLoginDate.size() == 0) {
            SequrityApi(str);
        } else if (saveLoginDate.get(0).getDate() != null) {
            String date = saveLoginDate.get(0).getDate();
            String serverUrl = saveLoginDate.get(0).getServerUrl();
            this.urls = serverUrl;
            this.dateDifference = getDateDiff(this.simpleDateFormat, date, this.currentDate1);
            if (this.dateDifference > 14) {
                this.multiuserdbhandler.deleteSaveLogin();
                this.multiuserdbhandler.saveLoginData(serverUrl, this.currentDate1);
                AppConst.CHECK_STATUS = true;
                atStart();
                SequrityApi(str);
                return;
            }
            try {
                if (!this.type.equalsIgnoreCase(AppConst.TYPE_M3U)) {
                    this.serverList = new ArrayList<>(Arrays.asList(serverUrl.replaceAll("\"", "").split(",")));
                    storeServerUrls(this.serverList, serverUrl);
                }
            } catch (Exception unused) {
            }
        } else {
            SequrityApi(str);
        }
    }

    @SuppressLint({"StaticFieldLeak"})
    class _loadFile extends AsyncTask<String, Void, String> {
        String checkingTypeOnClickCNA = "";
        String currentnameCNA = "";
        boolean isEditingCNA = false;
        String serverurlCNA = "";
        String typeCNA = "";
        int userIDCNA = -1;

        public _loadFile(String str, String str2, int i, String str3, String str4, boolean z) {
            this.currentnameCNA = str;
            this.serverurlCNA = str2;
            this.userIDCNA = i;
            this.checkingTypeOnClickCNA = str3;
            this.typeCNA = str4;
            this.isEditingCNA = z;
        }

        public _loadFile() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public String doInBackground(String... strArr) {
            try {
                MultiUserAdapter.this.is = new FileInputStream(new File(strArr[0]));
                return MultiUserAdapter.this.parser.parseFileForM3uFile(MultiUserAdapter.this.is, MultiUserAdapter.this.context);
            } catch (Exception unused) {
                return "";
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:23:0x0094 A[Catch:{ Exception -> 0x010c }] */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x00e8 A[Catch:{ Exception -> 0x010c }] */
        public void onPostExecute(String str) {
            //ToDo: initialized...
            boolean z = false;
            super.onPostExecute((String) str);
            try {
                new ArrayList();
                if (!str.equals("") && MultiUserAdapter.this.urls != null && !MultiUserAdapter.this.urls.isEmpty()) {
                    ArrayList arrayList = new ArrayList(Arrays.asList(MultiUserAdapter.this.urls.split(",")));
                    if (arrayList.size() > 0) {
                        int i = 0;
                        while (true) {
                            if (i >= arrayList.size()) {
                                break;
                            }
                            String str2 = str.split("/")[2];
                            if (str2.contains(":")) {
                                str2 = str2.substring(0, str2.lastIndexOf(":"));
                            }
                            if (((String) arrayList.get(i)).contains(str2)) {
                                z = true;
                                break;
                            }
                            i++;
                        }
                        if (z) {
                            MultiUserAdapter.this.onFinish();
                            Toast.makeText(MultiUserAdapter.this.context, MultiUserAdapter.this.context.getResources().getString(R.string.unable_to_add_user), 1).show();
                            return;
                        } else if (!str.equals("")) {
                            if (MultiUserAdapter.this.isEditing) {
                                MultiUserAdapter.this.isEditing = false;
                                MultiUserAdapter.this.multiUserDBHandler.updatemultiusersM3U(this.userIDCNA, this.currentnameCNA, this.checkingTypeOnClickCNA, this.serverurlCNA);
                                MultiUserAdapter.this.onFinish();
                                MultiUserAdapter.changeSortPopUp.dismiss();
                            }
                            loadFiledata();
                            return;
                        } else {
                            MultiUserAdapter.this.onFinish();
                            Toast.makeText(MultiUserAdapter.this.context, MultiUserAdapter.this.context.getResources().getString(R.string.please_add_correct), 1).show();
                            return;
                        }
                    } else {
                        MultiUserAdapter.this.onFinish();
                        Toast.makeText(MultiUserAdapter.this.context, MultiUserAdapter.this.context.getResources().getString(R.string.please_add_correct), 1).show();
                    }
                    z = false;
                    if (z) {
                    }
                }
            } catch (Exception e) {
                Log.e("gknsadg", "gnjsdg" + e);
            }
        }

        public void loadFiledata() {
            try {
                SharedPreferences.Editor edit = MultiUserAdapter.this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).edit();
                SharedPreferences sharedPreferences = MultiUserAdapter.this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0);
                SharedPreferences.Editor edit2 = sharedPreferences.edit();
                String string = sharedPreferences.getString("name", "");
                String string2 = sharedPreferences.getString("username", "");
                String string3 = sharedPreferences.getString("password", "");
                String string4 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "");
                edit2.putString("name", MultiUserAdapter.this.currentname);
                edit2.putString("username", "playlist");
                edit2.putString("password", "playlist");
                edit2.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, MultiUserAdapter.this.serverurl);
                edit.putString("username", "playlist");
                edit.putString("password", "playlist");
                edit.putString(AppConst.LOGIN_PREF_SERVER_PORT, "");
                edit.putString(AppConst.LOGIN_PREF_SERVER_URL, MultiUserAdapter.this.serverurl);
                edit.putString(AppConst.LOGIN_PREF_SERVER_M3U_LINE, MultiUserAdapter.this.serverurl);
                edit.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, MultiUserAdapter.this.serverurl);
                edit.apply();
                edit2.apply();
                SharedPreferences unused = MultiUserAdapter.this.loginPreferencesSharedPref_allowed_format = MultiUserAdapter.this.context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
                SharedPreferences.Editor unused2 = MultiUserAdapter.this.loginPrefsEditor_fomat = MultiUserAdapter.this.loginPreferencesSharedPref_allowed_format.edit();
                SharedPreferences unused3 = MultiUserAdapter.this.loginPreferencesSharedPref_time_format = MultiUserAdapter.this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
                SharedPreferences.Editor unused4 = MultiUserAdapter.this.loginPrefsEditor_timefomat = MultiUserAdapter.this.loginPreferencesSharedPref_time_format.edit();
                String string5 = MultiUserAdapter.this.loginPreferencesSharedPref_allowed_format.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
                if (string5 != null && string5.equals("")) {
                    if (com.nst.yourname.miscelleneious.common.Utils.getIsXtream1_06(MultiUserAdapter.this.context)) {
                        MultiUserAdapter.this.loginPrefsEditor_fomat.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "ts");
                    } else {
                        MultiUserAdapter.this.loginPrefsEditor_fomat.putString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "default");
                    }
                    MultiUserAdapter.this.loginPrefsEditor_fomat.apply();
                }
                String string6 = MultiUserAdapter.this.loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, "");
                if (string6 != null && string6.equals("")) {
                    MultiUserAdapter.this.loginPrefsEditor_timefomat.putString(AppConst.LOGIN_PREF_TIME_FORMAT, "HH:mm");
                    MultiUserAdapter.this.loginPrefsEditor_timefomat.apply();
                }
                SharedPreferences unused5 = MultiUserAdapter.this.loginPreferencesRemember = MultiUserAdapter.this.context.getSharedPreferences(AppConst.SHARED_PREFERENCE_REMEBER_ME, 0);
                SharedPreferences.Editor unused6 = MultiUserAdapter.this.loginPrefsEditorBeforeLogin = MultiUserAdapter.this.loginPreferencesRemember.edit();
                MultiUserAdapter.this.loginPrefsEditorBeforeLogin.putBoolean(AppConst.PREF_SAVE_LOGIN, true);
                MultiUserAdapter.this.loginPrefsEditorBeforeLogin.apply();
                MultiUserAdapter.this.onFinish();
                Toast.makeText(MultiUserAdapter.this.context, MultiUserAdapter.this.context.getResources().getString(R.string.logged_in), 0).show();
                if (MultiUserAdapter.this.context != null && MultiUserAdapter.this.currentname.equals(string) && MultiUserAdapter.this.currentusername.equals(string2) && MultiUserAdapter.this.currentpassword.equals(string3) && MultiUserAdapter.this.serverurl.equals(string4)) {
                    MultiUserAdapter.this.context.startActivity(new Intent(MultiUserAdapter.this.context, NewDashboardActivity.class));
                    ((Activity) MultiUserAdapter.this.context).finish();
                } else if (MultiUserAdapter.this.context != null) {
                    if (MultiUserAdapter.this.liveStreamDBHandler.getEPGCount() > 0 && MultiUserAdapter.this.liveStreamDBHandler != null) {
                        String access$1400 = MultiUserAdapter.this.currentDateValue();
                        MultiUserAdapter.this.liveStreamDBHandler.makeEmptyEPG();
                        MultiUserAdapter.this.liveStreamDBHandler.updateDBStatusAndDate(AppConst.DB_EPG, "2", "", access$1400);
                    }
                    MultiUserAdapter.this.context.startActivity(new Intent(MultiUserAdapter.this.context, ImportM3uActivity.class));
                    ((Activity) MultiUserAdapter.this.context).finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint({"RestrictedApi"})
    public void popmenu(final MyViewHolder myViewHolder, final int i, final String str, final int i2, View view) {
        final MultiUserDBModel multiUserDBModel = this.userlist.get(i);
        String m3uType = multiUserDBModel.getM3uType();
        if ((m3uType == null || !m3uType.equals(AppConst.TYPE_M3U_FILE)) && (m3uType == null || !m3uType.equals("url"))) {
            this.type = AppConst.TYPE_API;
        } else {
            this.type = AppConst.TYPE_M3U;
        }
        PopupMenu popupMenu = new PopupMenu(this.context, myViewHolder.testing);
        try {
            Field declaredField = PopupMenu.class.getDeclaredField("mPopup");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(popupMenu);
            obj.getClass().getDeclaredMethod("setForceShowIcon", Boolean.TYPE).invoke(obj, true);
        } catch (Exception unused) {
        }
        popupMenu.getMenuInflater().inflate(R.menu.menu_card_multiuser, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            /* class com.nst.yourname.view.adapter.MultiUserAdapter.AnonymousClass3 */
            static final boolean $assertionsDisabled = false;

            /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
             method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.MultiUserActivity):void
             arg types: [android.widget.Button, com.nst.yourname.view.activity.MultiUserActivity]
             candidates:
              com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
              com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.content.Context):void
              com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.CheckAppupdateActivity):void
              com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.LoginActivity):void
              com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.NewDashboardActivity):void
              com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RateUsActivity):void
              com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RecordingActivity):void
              com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.SplashActivity):void
              com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.VodActivityNewFlowSubCategories):void
              com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity):void
              com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.MultiUserActivity):void */
            @Override // android.support.v7.widget.PopupMenu.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId != R.id.delete_user) {
                    if (itemId != R.id.edit_user) {
                        if (itemId == R.id.login_user) {
                            myViewHolder.rlOuter.performClick();
                        }
                    } else if (MultiUserAdapter.this.type.equals(AppConst.TYPE_M3U)) {
                        MultiUserAdapter.this.editM3Udetails(MultiUserAdapter.this.type, MultiUserAdapter.this.userlist, i, i2);
                    } else {
                        MultiUserAdapter.this.editUserPopUP(MultiUserAdapter.this.activity, multiUserDBModel.getname(), multiUserDBModel.getusername(), multiUserDBModel.getpassword(), multiUserDBModel.getmagportal(), multiUserDBModel.getM3uType(), i2, i);
                    }
                } else if (MultiUserAdapter.this.context != null) {
                    View inflate = ((LayoutInflater) MultiUserAdapter.this.activity.getSystemService("layout_inflater")).inflate((int) R.layout.delete_recording_popup, (RelativeLayout) MultiUserAdapter.this.activity.findViewById(R.id.rl_password_verification));
                    PopupWindow unused = MultiUserAdapter.changeSortPopUp = new PopupWindow(MultiUserAdapter.this.activity);
                    MultiUserAdapter.changeSortPopUp.setContentView(inflate);
                    MultiUserAdapter.changeSortPopUp.setWidth(-1);
                    MultiUserAdapter.changeSortPopUp.setHeight(-1);
                    MultiUserAdapter.changeSortPopUp.setFocusable(true);
                    MultiUserAdapter.changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
                    TextView textView = (TextView) inflate.findViewById(R.id.tv_delete_recording);
                    Button button = (Button) inflate.findViewById(R.id.bt_start_recording);
                    Button button2 = (Button) inflate.findViewById(R.id.bt_close);
                    if (textView != null) {
                        textView.setText(MultiUserAdapter.this.context.getResources().getString(R.string.delete_message));
                    }
                    if (button != null) {
                        button.setOnFocusChangeListener(new com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener((View) button, MultiUserAdapter.this.activity));
                    }
                    if (button2 != null) {
                        button2.setOnFocusChangeListener(new com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener((View) button2, MultiUserAdapter.this.activity));
                    }
                    button2.setOnClickListener(new View.OnClickListener() {
                        /* class com.nst.yourname.view.adapter.MultiUserAdapter.AnonymousClass3.AnonymousClass1 */

                        public void onClick(View view) {
                            MultiUserAdapter.changeSortPopUp.dismiss();
                        }
                    });
                    if (button != null) {
                        button.setOnClickListener(new View.OnClickListener() {
                            /* class com.nst.yourname.view.adapter.MultiUserAdapter.AnonymousClass3.AnonymousClass2 */

                            public void onClick(View view) {
                                if (MultiUserAdapter.this.context != null) {
                                    if (MultiUserAdapter.this.type.equals(AppConst.TYPE_M3U)) {
                                        MultiUserAdapter.this.liveStreamDBHandler.makeEmptyAllTablesRecordsM3U(i2);
                                        MultiUserAdapter.this.liveStreamDBHandler.deletePasswordDataForUser(i2);
                                        MultiUserAdapter.this.multiUserDBHandler.deleteUserM3U(i2);
                                    } else {
                                        new DatabaseHandler(MultiUserAdapter.this.context).deleteDataForUser(i2);
                                        new RecentWatchDBHandler(MultiUserAdapter.this.context).deletRecentWatchForThisUser(i2);
                                        new LiveStreamDBHandler(MultiUserAdapter.this.context).deletePasswordDataForUser(i2);
                                        new SeriesRecentWatchDatabase(MultiUserAdapter.this.context).deleteALLSeriesRecentwatch();
                                        MultiUserAdapter.this.multiUserDBHandler.deleteUserAPI(i2);
                                    }
                                    MultiUserAdapter.this.userlist.remove(i);
                                    MultiUserAdapter.this.notifyItemRemoved(i);
                                    MultiUserAdapter.this.notifyItemRangeChanged(i, MultiUserAdapter.this.userlist.size());
                                    MultiUserAdapter.this.notifyDataSetChanged();
                                    Context access$300 = MultiUserAdapter.this.context;
                                    Toast.makeText(access$300, MultiUserAdapter.this.context.getResources().getString(R.string.item_deleted) + "  " + str, 0).show();
                                    if (MultiUserAdapter.this.userlist.size() == 0 && MultiUserAdapter.this.ll_add_new_user != null) {
                                        MultiUserAdapter.this.ll_add_new_user.setVisibility(0);
                                        MultiUserAdapter.this.tv_list_options.setVisibility(8);
                                    }
                                    MultiUserAdapter.changeSortPopUp.dismiss();
                                }
                            }
                        });
                    }
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void showConfirmationforEditPopup(final Activity activity2, final String str, final String str2, final String str3, final String str4, final String str5, int i, final int i2, final int i3) {
        try {
            View inflate = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate((int) R.layout.edit_user_detail_confirmation_alert, (RelativeLayout) activity2.findViewById(R.id.rl_password_prompt));
            changeSortPopUp = new PopupWindow(this.context);
            changeSortPopUp.setContentView(inflate);
            changeSortPopUp.setWidth(-1);
            changeSortPopUp.setHeight(-1);
            changeSortPopUp.setFocusable(true);
            changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
            Button button = (Button) inflate.findViewById(R.id.btn_no);
            Button button2 = (Button) inflate.findViewById(R.id.btn_yes);
            final EditText editText = (EditText) inflate.findViewById(R.id.et_password);
            button2.setText(this.context.getResources().getString(R.string.yes));
            button2.setFocusable(true);
            button.setText(this.context.getResources().getString(R.string.no));
            button.setFocusable(true);
            button.setOnFocusChangeListener(new AddedExternalPlayerAdapter.OnFocusChangeAccountListener(button, this.context));
            button2.setOnFocusChangeListener(new AddedExternalPlayerAdapter.OnFocusChangeAccountListener(button2, this.context));
            button2.requestFocus();
            button2.requestFocusFromTouch();
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.MultiUserAdapter.AnonymousClass4 */

                public void onClick(View view) {
                    if (MultiUserAdapter.changeSortPopUp != null && MultiUserAdapter.changeSortPopUp.isShowing()) {
                        MultiUserAdapter.changeSortPopUp.dismiss();
                    }
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.MultiUserAdapter.AnonymousClass5 */

                public void onClick(View view) {
                    String obj = editText.getText().toString();
                    if (obj != null && obj.isEmpty()) {
                        Toast.makeText(activity2, MultiUserAdapter.this.context.getResources().getString(R.string.please_enter_password), 0).show();
                    } else if (obj.equals(str3)) {
                        if (MultiUserAdapter.changeSortPopUp != null && MultiUserAdapter.changeSortPopUp.isShowing()) {
                            MultiUserAdapter.changeSortPopUp.dismiss();
                        }
                        MultiUserAdapter.this.editUserPopUP(activity2, str, str2, str3, str4, str5, i3, i2);
                    } else {
                        com.nst.yourname.miscelleneious.common.Utils.showToast(MultiUserAdapter.this.context, "Invalid password");
                    }
                }
            });
        } catch (Exception unused) {
        }
    }

    public void editM3Udetails(String str, final List<MultiUserDBModel> list, final int i, final int i2) {
        try {
            View inflate = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate((int) R.layout.edit_m3u_user_prompt, (RelativeLayout) this.activity.findViewById(R.id.rl_password_prompt));
            changeSortPopUp = new PopupWindow(this.context);
            changeSortPopUp.setContentView(inflate);
            changeSortPopUp.setWidth(-1);
            changeSortPopUp.setHeight(-1);
            changeSortPopUp.setFocusable(true);
            changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
            this.saveBT = (Button) inflate.findViewById(R.id.import_m3u);
            this.closedBT = (Button) inflate.findViewById(R.id.rl_view_log);
            if (this.saveBT != null) {
                this.saveBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.saveBT));
            }
            if (this.closedBT != null) {
                this.closedBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.closedBT));
            }
            this.etName = (EditText) inflate.findViewById(R.id.et_name);
            this.rbFile = (RadioButton) inflate.findViewById(R.id.rb_file);
            this.etM3uLineFile = (EditText) inflate.findViewById(R.id.et_import_m3u_file);
            this.rbM3U = (RadioButton) inflate.findViewById(R.id.rb_m3u);
            this.etM3uLine = (EditText) inflate.findViewById(R.id.et_import_m3u);
            this.tv_browse_error = (TextView) inflate.findViewById(R.id.tv_browse_error);
            this.tv_file_path = (TextView) inflate.findViewById(R.id.tv_file_path);
            this.bt_browse = (Button) inflate.findViewById(R.id.bt_browse);
            this.radioGroup = (RadioGroup) inflate.findViewById(R.id.rg_radio);
            this.etName.setText(list.get(i).getname());
            String m3uType = list.get(i).getM3uType();
            if (m3uType != null && m3uType.equals(AppConst.TYPE_M3U_FILE)) {
                this.tv_file_path.setVisibility(0);
                this.bt_browse.setVisibility(0);
                this.etM3uLine.setVisibility(8);
                this.tv_browse_error.setVisibility(8);
                this.tv_file_path.setText(list.get(i).getmagportal());
                this.rbFile.setChecked(true);
            } else if (m3uType != null && m3uType.equals("url")) {
                this.rbM3U.setChecked(true);
                this.tv_file_path.setVisibility(8);
                this.bt_browse.setVisibility(8);
                this.tv_browse_error.setVisibility(8);
                this.etM3uLine.setVisibility(0);
                this.etM3uLine.setText(list.get(i).getmagportal());
            }
            this.rbFile.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.MultiUserAdapter.AnonymousClass6 */

                public void onClick(View view) {
                    MultiUserAdapter.this.tv_file_path.setVisibility(0);
                    MultiUserAdapter.this.bt_browse.setVisibility(0);
                    MultiUserAdapter.this.etM3uLine.setVisibility(8);
                    MultiUserAdapter.this.tv_browse_error.setVisibility(8);
                    if (MultiUserAdapter.this.chosenDIR != null && !MultiUserAdapter.this.chosenDIR.isEmpty()) {
                        MultiUserAdapter.this.tv_file_path.setText(MultiUserAdapter.this.chosenDIR);
                    }
                }
            });
            this.rbM3U.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.MultiUserAdapter.AnonymousClass7 */

                public void onClick(View view) {
                    MultiUserAdapter.this.rbM3U.setChecked(true);
                    MultiUserAdapter.this.tv_file_path.setVisibility(8);
                    MultiUserAdapter.this.bt_browse.setVisibility(8);
                    MultiUserAdapter.this.tv_browse_error.setVisibility(8);
                    MultiUserAdapter.this.etM3uLine.setVisibility(0);
                    MultiUserAdapter.this.m3URL = MultiUserAdapter.this.etM3uLine.getText().toString();
                    if (!MultiUserAdapter.this.m3URL.isEmpty()) {
                        MultiUserAdapter.this.etM3uLine.setText(MultiUserAdapter.this.m3URL);
                    }
                }
            });
            this.bt_browse.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.MultiUserAdapter.AnonymousClass8 */

                public void onClick(View view) {
                    if (MultiUserAdapter.this.isStoragePermissionGranted()) {
                        MultiUserAdapter.this.chooseM3UFile();
                    } else {
                        Toast.makeText(MultiUserAdapter.this.context, MultiUserAdapter.this.context.getResources().getString(R.string.permission_is_reqd), 1).show();
                    }
                }
            });
            this.closedBT.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.MultiUserAdapter.AnonymousClass9 */

                public void onClick(View view) {
                    MultiUserAdapter.changeSortPopUp.dismiss();
                }
            });
            this.saveBT.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.MultiUserAdapter.AnonymousClass10 */

                public void onClick(View view) {
                    MultiUserAdapter.this.currentname = MultiUserAdapter.this.etName.getText().toString();
                    if (MultiUserAdapter.this.currentname == null || MultiUserAdapter.this.currentname.trim().isEmpty()) {
                        Toast.makeText(MultiUserAdapter.this.context, MultiUserAdapter.this.context.getResources().getString(R.string.enter_any_name), 0).show();
                        return;
                    }
                    MultiUserAdapter.this.isEditing = true;
                    SharepreferenceDBHandler.setUserID(i2, MultiUserAdapter.this.context);
                    SharedPreferences.Editor unused = MultiUserAdapter.this.loginPreferencesServerURlPut = MultiUserAdapter.this.loginPreferencesServerURl.edit();
                    MultiUserAdapter.this.atStart();
                    MultiUserAdapter.this.loginPrefsEditorBeforeLogin.putString("username", "playlist");
                    MultiUserAdapter.this.loginPrefsEditorBeforeLogin.putString("password", "playlist");
                    MultiUserAdapter.this.loginPrefsEditorBeforeLogin.putString("activationCode", "");
                    MultiUserAdapter.this.loginPrefsEditorBeforeLogin.putString(AppConst.PREF_LOGIN_WITH, AppConst.LOGIN_WITH_DETAILS);
                    MultiUserAdapter.this.loginPrefsEditorBeforeLogin.apply();
                    MultiUserAdapter.this.currentusername = "playlist";
                    MultiUserAdapter.this.currentpassword = "playlist";
                    if (MultiUserAdapter.this.rbFile.isChecked()) {
                        if (MultiUserAdapter.this.chosenDIR != null && MultiUserAdapter.this.chosenDIR.isEmpty()) {
                            String unused2 = MultiUserAdapter.this.chosenDIR = ((MultiUserDBModel) list.get(i)).getmagportal();
                        }
                        MultiUserAdapter.this.serverurl = MultiUserAdapter.this.chosenDIR;
                    } else if (MultiUserAdapter.this.rbM3U.isChecked()) {
                        MultiUserAdapter.this.m3URL = MultiUserAdapter.this.etM3uLine.getText().toString();
                        MultiUserAdapter.this.serverurl = MultiUserAdapter.this.m3URL;
                    }
                    SharedPreferences unused3 = MultiUserAdapter.this.loginPreferencesServerURl = MultiUserAdapter.this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0);
                    MultiUserAdapter.this.loginPreferencesServerURlPut.apply();
                    String m3uType = ((MultiUserDBModel) list.get(i)).getM3uType();
                    if (MultiUserAdapter.this.rbFile != null && MultiUserAdapter.this.rbFile.isChecked()) {
                        m3uType = AppConst.TYPE_M3U_FILE;
                    } else if (MultiUserAdapter.this.rbM3U != null && MultiUserAdapter.this.rbM3U.isChecked()) {
                        m3uType = "url";
                    }
                    String str = m3uType;
                    if ((str == null || !str.equals(AppConst.TYPE_M3U_FILE)) && (str == null || !str.equals("url"))) {
                        MultiUserAdapter.this.type = AppConst.TYPE_API;
                    } else {
                        MultiUserAdapter.this.type = AppConst.TYPE_M3U;
                    }
                    if (new MultiUserDBHandler(MultiUserAdapter.this.context).checkregistration(MultiUserAdapter.this.currentname, "playlist", "playlist", MultiUserAdapter.this.serverurl, AppConst.TYPE_M3U, "")) {
                        MultiUserAdapter.this.isEditing = false;
                        MultiUserAdapter.this.onFinish();
                        MultiUserAdapter.changeSortPopUp.dismiss();
                        Toast.makeText(MultiUserAdapter.this.context, "User Already Exists ", 0).show();
                    } else if (AppConst.M3U_LINE_ACTIVE.booleanValue() && MultiUserAdapter.this.type.equals(AppConst.TYPE_M3U)) {
                        SharepreferenceDBHandler.setCurrentAPPType(AppConst.TYPE_M3U, MultiUserAdapter.this.context);
                        if (str == null || !str.equals(AppConst.TYPE_M3U_FILE)) {
                            SharepreferenceDBHandler.setUserID(i2, MultiUserAdapter.this.context);
                            MultiUserAdapter.this.checkLoginStatus("playList", "playList");
                            new _checkNetworkAvailable(MultiUserAdapter.this.currentname, MultiUserAdapter.this.serverurl, i2, str, MultiUserAdapter.this.type, MultiUserAdapter.this.isEditing).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MultiUserAdapter.this.serverurl);
                        } else if (new File(MultiUserAdapter.this.serverurl).exists()) {
                            SharepreferenceDBHandler.setUserID(i2, MultiUserAdapter.this.context);
                            MultiUserAdapter.this.checkLoginStatus("playList", "playList");
                            new _loadFile(MultiUserAdapter.this.currentname, MultiUserAdapter.this.serverurl, i2, str, MultiUserAdapter.this.type, MultiUserAdapter.this.isEditing).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MultiUserAdapter.this.serverurl);
                            MultiUserAdapter.this.onFinish();
                        } else {
                            Toast.makeText(MultiUserAdapter.this.context, MultiUserAdapter.this.context.getResources().getString(R.string.file_not_found), 0).show();
                            MultiUserAdapter.this.onFinish();
                        }
                    }
                }
            });
        } catch (Exception unused) {
        }
    }

    private boolean isFileUpdated(MultiUserDBModel multiUserDBModel, String str) {
        String str2 = "";
        String str3 = "";
        if (str != null && !str.isEmpty() && !str.equals("")) {
            str2 = str.substring(str.lastIndexOf("/") + 1);
        }
        if (!(multiUserDBModel == null || multiUserDBModel.getmagportal() == null)) {
            str3 = multiUserDBModel.getmagportal().substring(multiUserDBModel.getmagportal().lastIndexOf("/") + 1);
        }
        return isSameFile(str2, str3);
    }

    public void chooseM3UFile() {
        final String[] strArr = {""};
        new FileChooserDialog(this.context, new FileChooserDialog.ChosenDirectoryListener() {
            /* class com.nst.yourname.view.adapter.MultiUserAdapter.AnonymousClass11 */

            @Override // com.nst.yourname.miscelleneious.FileChooserDialog.ChosenDirectoryListener
            public void onChosenDir(String str) {
                MultiUserAdapter.this.chosenDIR = str;
                strArr[0] = str;
                MultiUserAdapter.this.etM3uLineFile.setText(str);
                MultiUserAdapter.this.tv_browse_error.setVisibility(8);
                MultiUserAdapter.this.tv_file_path.setVisibility(0);
                MultiUserAdapter.this.tv_file_path.setText(str);
            }
        }).chooseDirectory("");
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT < 23) {
            Log.v("TAG", "Permission is granted");
            return true;
        } else if (this.context.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
            Log.v("TAG", "Permission is granted");
            return true;
        } else {
            Log.v("TAG", "Permission is revoked");
            ActivityCompat.requestPermissions((Activity) this.context, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 1);
            return false;
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.userlist.size();
    }

    private class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private final View view;

        public OnFocusChangeAccountListener(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            float f = 1.0f;
            if (z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                Log.e("id is", "" + this.view.getTag());
                this.view.setBackgroundResource(R.drawable.shape_list_multidns_focused);
                if (this.view.getTag() != null && this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID)) {
                    MultiUserAdapter.this.saveBT.setBackgroundResource(R.drawable.back_btn_effect);
                }
                if (this.view.getTag() != null && this.view.getTag().equals("9")) {
                    MultiUserAdapter.this.closedBT.setBackgroundResource(R.drawable.logout_btn_effect);
                }
            } else if (!z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                performAlphaAnimation(z);
                this.view.setBackgroundResource(R.drawable.shape_list_multidns);
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID))) {
                    MultiUserAdapter.this.saveBT.setBackgroundResource(R.drawable.black_button_dark);
                }
                if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("9")) {
                    MultiUserAdapter.this.closedBT.setBackgroundResource(R.drawable.black_button_dark);
                }
            }
        }

        private void performScaleXAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleX", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        private void performScaleYAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleY", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        private void performAlphaAnimation(boolean z) {
            if (z) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "alpha", z ? 0.6f : 0.5f);
                ofFloat.setDuration(150L);
                ofFloat.start();
            }
        }
    }

    @SuppressLint({"RtlHardcoded"})
    public void editUserPopUP(final Activity activity2, String str, String str2, String str3, String str4, String str5, final int i, int i2) {
        final EditText editText;
        try {
            this.multiUserDBHandler = new MultiUserDBHandler(this.context);
            SharepreferenceDBHandler.setUserID(i, this.context);
            View inflate = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate((int) R.layout.edit_user_prompt, (RelativeLayout) activity2.findViewById(R.id.rl_password_prompt));
            changeSortPopUp = new PopupWindow(this.context);
            changeSortPopUp.setContentView(inflate);
            changeSortPopUp.setWidth(-1);
            changeSortPopUp.setHeight(-1);
            changeSortPopUp.setFocusable(true);
            changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
            this.saveBT = (Button) inflate.findViewById(R.id.bt_save);
            this.closedBT = (Button) inflate.findViewById(R.id.bt_close);
            if (this.saveBT != null) {
                this.saveBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.saveBT));
            }
            if (this.closedBT != null) {
                this.closedBT.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.closedBT));
            }
            final EditText editText2 = (EditText) inflate.findViewById(R.id.tv_any_name);
            final EditText editText3 = (EditText) inflate.findViewById(R.id.tv_username);
            final EditText editText4 = (EditText) inflate.findViewById(R.id.tv_password);
            EditText editText5 = (EditText) inflate.findViewById(R.id.tv_server_url);
            //new int[1][0] = -1;
            EditText editText6 = editText5;
            setEditext(str, str2, str3, str4, editText2, editText3, editText4, editText5);
            if (this.context.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, 0).getString(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, "English").equalsIgnoreCase("Arabic")) {
                editText2.setGravity(21);
                editText3.setGravity(21);
                editText4.setGravity(21);
                editText = editText6;
                editText.setGravity(21);
            } else {
                editText = editText6;
            }
            this.closedBT.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.MultiUserAdapter.AnonymousClass12 */

                public void onClick(View view) {
                    MultiUserAdapter.changeSortPopUp.dismiss();
                }
            });
            this.saveBT.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.MultiUserAdapter.AnonymousClass13 */

                public void onClick(View view) {
                    String obj = editText2.getText().toString();
                    String obj2 = editText3.getText().toString();
                    String obj3 = editText4.getText().toString();
                    String obj4 = editText.getText().toString();
                    if (obj != null && obj.trim().isEmpty()) {
                        Toast.makeText(activity2, MultiUserAdapter.this.context.getResources().getString(R.string.enter_any_name), 0).show();
                    } else if (obj2 != null && obj2.trim().isEmpty()) {
                        Toast.makeText(activity2, MultiUserAdapter.this.context.getResources().getString(R.string.please_enter_username), 0).show();
                    } else if (obj3 == null || !obj3.trim().isEmpty()) {
                        if (!obj4.startsWith(MultiUserAdapter.EXT_URL) && !obj4.startsWith(MultiUserAdapter.EXT_URL_HTTPS)) {
                            obj4 = MultiUserAdapter.EXT_URL + obj4;
                        }
                        if (!obj4.endsWith("/")) {
                            obj4 = obj4 + "/";
                        }
                        String str = obj4;
                        MultiUserAdapter.this.editanyname = obj;
                        String unused2 = MultiUserAdapter.this.editusername = obj2;
                        String unused3 = MultiUserAdapter.this.editpassword = obj3;
                        AppConst.SERVER_URL_FOR_MULTI_USER = str;
                        if (!Boolean.valueOf(new MultiUserDBHandler(MultiUserAdapter.this.context).checkregistration(obj, obj2, obj3, AppConst.SERVER_URL_FOR_MULTI_USER, AppConst.TYPE_API, str)).booleanValue()) {
                            SharedPreferences.Editor unused4 = MultiUserAdapter.this.loginPreferencesServerURlPut = MultiUserAdapter.this.loginPreferencesServerURl.edit();
                            MultiUserAdapter.this.atStart();
                            MultiUserAdapter.this.loginPrefsEditorBeforeLogin.putString("username", obj2);
                            MultiUserAdapter.this.loginPrefsEditorBeforeLogin.putString("password", obj3);
                            MultiUserAdapter.this.loginPrefsEditorBeforeLogin.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, str);
                            MultiUserAdapter.this.loginPrefsEditorBeforeLogin.putString("activationCode", "");
                            MultiUserAdapter.this.loginPrefsEditorBeforeLogin.putString(AppConst.PREF_LOGIN_WITH, AppConst.LOGIN_WITH_DETAILS);
                            MultiUserAdapter.this.loginPrefsEditorBeforeLogin.apply();
                            SharedPreferences unused5 = MultiUserAdapter.this.loginPreferencesServerURl = MultiUserAdapter.this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0);
                            MultiUserAdapter.this.loginPreferencesServerURlPut.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, str);
                            MultiUserAdapter.this.loginPreferencesServerURlPut.apply();
                            MultiUserAdapter.this.type = AppConst.TYPE_API;
                            if (MultiUserAdapter.this.context != null) {
                                SharepreferenceDBHandler.setCurrentAPPType(AppConst.TYPE_API, MultiUserAdapter.this.context);
                                try {
                                    SharepreferenceDBHandler.setUserID(i, MultiUserAdapter.this.context);
                                    if (MultiUserAdapter.this.editUserPresenter != null && MultiUserAdapter.this.userlist != null) {
                                        MultiUserAdapter.this.isEditing = true;
                                        if (MultiUserAdapter.this.multiuserdbhandler == null || MultiUserAdapter.this.multiuserdbhandler.getSaveLoginDate() == null) {
                                            MultiUserAdapter.this.SequrityApi(obj2);
                                            return;
                                        }
                                        ArrayList<MultiUserDBModel> saveLoginDate = MultiUserAdapter.this.multiuserdbhandler.getSaveLoginDate();
                                        if (saveLoginDate.size() == 0) {
                                            MultiUserAdapter.this.SequrityApi(obj2);
                                        } else if (saveLoginDate.get(0).getDate() != null) {
                                            String date = saveLoginDate.get(0).getDate();
                                            String serverUrl = saveLoginDate.get(0).getServerUrl();
                                            MultiUserAdapter.this.dateDifference = MultiUserAdapter.getDateDiff(MultiUserAdapter.this.simpleDateFormat, date, MultiUserAdapter.this.currentDate1);
                                            if (MultiUserAdapter.this.dateDifference > 14) {
                                                MultiUserAdapter.this.multiuserdbhandler.deleteSaveLogin();
                                                MultiUserAdapter.this.multiuserdbhandler.saveLoginData(serverUrl, MultiUserAdapter.this.currentDate1);
                                                AppConst.CHECK_STATUS = true;
                                                MultiUserAdapter.this.atStart();
                                                MultiUserAdapter.this.SequrityApi(obj2);
                                                return;
                                            }
                                            MultiUserAdapter.this.serverList = new ArrayList<>(Arrays.asList(RavSharedPrefrences.get_authurl(MultiUserAdapter.this.context).replaceAll("\"", "").split(",")));
                                            MultiUserAdapter.this.storeServerUrls(MultiUserAdapter.this.serverList, MultiUserAdapter.this.urls);
                                        } else {
                                            MultiUserAdapter.this.SequrityApi(obj2);
                                        }
                                    }
                                } catch (Exception unused6) {
                                }
                            }
                        } else {
                            MultiUserAdapter.this.atStart();
                            MultiUserAdapter.this.SequrityApi(obj2);
                        }
                    } else {
                        Toast.makeText(activity2, MultiUserAdapter.this.context.getResources().getString(R.string.please_enter_password), 0).show();
                    }
                }
            });
        } catch (Exception unused) {
        }
    }

    private void setEditext(String str, String str2, String str3, String str4, EditText editText, EditText editText2, EditText editText3, EditText editText4) {
        if (editText != null && str != null && !str.isEmpty() && !str.equals("")) {
            editText.setText(str);
        }
        if (editText2 != null && str2 != null && !str2.isEmpty() && !str2.equals("")) {
            editText2.setText(str2);
        }
        if (editText3 != null && str3 != null && !str3.isEmpty() && !str3.equals("")) {
            editText3.setText(str3);
        }
        if (editText4 != null && str4 != null && !str4.isEmpty() && !str4.equals("")) {
            editText4.setText(str4);
        }
    }

    public boolean checkFieldsM3U() {
        if (this.etName.getText().toString().trim().length() == 0) {
            this.etName.requestFocus();
            this.etName.setError(this.context.getResources().getString(R.string.enter_any_name));
            return false;
        } else if (this.etM3uLine.getText().toString().trim().length() != 0) {
            return true;
        } else {
            this.etM3uLine.requestFocus();
            this.etM3uLine.setError(this.context.getResources().getString(R.string.enter_m3u_error));
            return false;
        }
    }

    public boolean checkFieldsFile() {
        if (this.etName.getText().toString().trim().length() == 0) {
            this.etName.requestFocus();
            this.etName.setError(this.context.getResources().getString(R.string.enter_any_name));
            return false;
        } else if (this.etM3uLineFile.getText().toString().trim().length() != 0) {
            return true;
        } else {
            this.tv_browse_error.setVisibility(0);
            this.tv_browse_error.requestFocus();
            this.tv_browse_error.setError(this.context.getResources().getString(R.string.choose_any_playlist_file));
            return false;
        }
    }

    public void SequrityApi(String str) {
        this.FirstMdkey = md5(RavSharedPrefrences.get_clientkey(this.context) + "*" + RavSharedPrefrences.get_salt(this.context) + "-" + str + "-" + Globals.RandomNumber + "-" + this.version + "-unknown-" + getDeviceName() + "-" + this.reqString);
        Webservices.getterList = new ArrayList();
        Webservices.getterList.add(Webservices.P("m", "gu"));
        Webservices.getterList.add(Webservices.P("k", RavSharedPrefrences.get_clientkey(this.context)));
        Webservices.getterList.add(Webservices.P("sc", this.FirstMdkey));
        Webservices.getterList.add(Webservices.P("u", str));
        Webservices.getterList.add(Webservices.P("pw", "no_password"));
        Webservices.getterList.add(Webservices.P("r", Globals.RandomNumber));
        Webservices.getterList.add(Webservices.P("av", this.version));
        Webservices.getterList.add(Webservices.P("dt", "unknown"));
        Webservices.getterList.add(Webservices.P("d", getDeviceName()));
        Webservices.getterList.add(Webservices.P("do", this.reqString));
        Webservices.getWebservices.SequrityLink(this);
    }

    public void appVersionName() {
        try {
            this.version = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void DEviceVersion() {
        this.reqString = Build.VERSION.RELEASE + " " + Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName();
    }

    public static String getDeviceName() {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        if (str2.startsWith(str)) {
            return capitalize(str2);
        }
        return capitalize(str) + " " + str2;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] charArray = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (char c : charArray) {
            if (!z || !Character.isLetter(c)) {
                if (Character.isWhitespace(c)) {
                    z = true;
                }
                sb.append(c);
            } else {
                sb.append(Character.toUpperCase(c));
                z = false;
            }
        }
        return sb.toString();
    }

    public void GetRandomNumber() {
        this.random = new Random().nextInt(8378600) + 10000;
        Globals.RandomNumber = String.valueOf(this.random);
    }

    public static String md5(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                String hexString = Integer.toHexString(b & -1);
                while (hexString.length() < 2) {
                    hexString = AppConst.PASSWORD_UNSET + hexString;
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void onPostSuccess(String str, int i, boolean z) {
        if (z && i == 1) {
            try {
                Globals.jsonObj = new JSONObject(str);
                if (Globals.jsonObj.getString(NotificationCompat.CATEGORY_STATUS).equalsIgnoreCase("true")) {
                    AppConst.ACTIVE_STATUS = true;
                    this.urls = Globals.jsonObj.getString("su");
                    this.multiuserdbhandler.deleteSaveLogin();
                    RavSharedPrefrences.set_authurl(this.context, Globals.jsonObj.optString("su"));
                    this.SecondMdkey = md5(Globals.jsonObj.optString("su") + "*" + RavSharedPrefrences.get_salt(this.context) + "*" + Globals.RandomNumber);
                    this.loginPreferencesServerURl = this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0);
                    this.loginPreferencesServerURlPut = this.loginPreferencesServerURl.edit();
                    this.loginPreferencesServerURlPut.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, RavSharedPrefrences.get_authurl(this.context));
                    this.loginPreferencesServerURlPut.apply();
                    this.multiuserdbhandler.saveLoginData(RavSharedPrefrences.get_authurl(this.context), currentDateValue());
                    if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
                        urlCountValue = 0;
                        String str2 = RavSharedPrefrences.get_authurl(this.context);
                        this.serverList = new ArrayList<>(Arrays.asList(str2.substring(1, str2.length() - 1).replaceAll("\"", "").split(",")));
                        storeServerUrls(this.serverList, this.urls);
                    } else if (com.nst.yourname.miscelleneious.common.Utils.getIsXtream1_06(this.context)) {
                        this.loginPresenter.validateLoginUsingPanelAPI(this.username1, this.password2);
                    } else {
                        this.loginPresenter.validateLogin(this.username1, this.password2);
                    }
                } else {
                    AppConst.REFRESH_BUTTON = false;
                    AppConst.CHECK_STATUS = false;
                    AppConst.ACTIVE_STATUS = false;
                    onFinish();
                    this.multiuserdbhandler.deleteSaveLogin();
                    Toast.makeText(this.context, this.context.getResources().getString(R.string.status_suspend), 0).show();
                }
            } catch (Exception unused) {
            }
        }
    }

    @Override // com.nst.yourname.WebServiceHandler.MainAsynListener
    public void onPostError(int i) {
        onFinish();
        Toast.makeText(this.context, this.context.getResources().getString(R.string.could_not_connect), 0).show();
    }

    public static long getDateDiff(SimpleDateFormat simpleDateFormat2, String str, String str2) {
        try {
            return TimeUnit.DAYS.convert(simpleDateFormat2.parse(str2).getTime() - simpleDateFormat2.parse(str).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @SuppressLint({"StaticFieldLeak"})
    private class DwnloadFileFromUrl extends AsyncTask<String, String, Boolean> {
        public void onProgressUpdate(String... strArr) {
        }

        private DwnloadFileFromUrl() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:27:0x008e, code lost:
            r0 = true;
         */
        public Boolean doInBackground(String... strArr) {
            boolean z = false;
            try {
                URL url = new URL(strArr[0]);
                MultiUserAdapter.this.isStoragePermissionGranted();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                int i = 0;
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    i++;
                    if (i == 10) {
                        break;
                    } else if (!readLine.equals("") && !readLine.contains(MultiUserAdapter.EXT_M3U)) {
                        if (readLine.contains(MultiUserAdapter.EXT_URL)) {
                            try {
                                String str = readLine.substring(readLine.lastIndexOf(MultiUserAdapter.EXT_URL)).replace("\n", "").replace("\r", "").split("/")[2];
                                if (str != null && str.contains(":")) {
                                    if (MultiUserAdapter.this.urls.contains(str.substring(0, str.lastIndexOf(":")))) {
                                        break;
                                    }
                                } else if (MultiUserAdapter.this.urls.contains(str)) {
                                    break;
                                }
                            } catch (Exception unused) {
                                continue;
                            }
                        } else if (readLine.contains(MultiUserAdapter.EXT_URL_HTTPS)) {
                            String str2 = readLine.substring(readLine.lastIndexOf(MultiUserAdapter.EXT_URL_HTTPS)).replace("\n", "").replace("\r", "").split("/")[2];
                            if (str2 != null && str2.contains(":")) {
                                if (MultiUserAdapter.this.urls.contains(str2.substring(0, str2.lastIndexOf(":")))) {
                                    break;
                                }
                            } else if (MultiUserAdapter.this.urls.contains(str2)) {
                                break;
                            }
                        } else {
                            continue;
                        }
                    }
                }
            } catch (MalformedURLException e) {
                Log.d("Suman", "DownloadFileFromUrl " + e.getMessage());
            } catch (IOException e2) {
                Log.d("Suman", "DownloadEXep " + e2.getMessage());
                e2.printStackTrace();
            }
            return Boolean.valueOf(z);
        }

        public void onPostExecute(Boolean bool) {
            com.nst.yourname.miscelleneious.common.Utils.hideProgressDialog();
            if (bool.booleanValue()) {
                MultiUserAdapter.this.compareUrl();
                return;
            }
            MultiUserAdapter.this.onFinish();
            com.nst.yourname.miscelleneious.common.Utils.showToast(MultiUserAdapter.this.context, MultiUserAdapter.this.context.getResources().getString(R.string.file_url_not_valid));
        }
    }
}
