package com.nst.yourname.miscelleneious.common;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.DirectoryChooserDialog;
import com.nst.yourname.miscelleneious.DownloadDirectoryChooserDialog;
import com.nst.yourname.model.callback.GetEpisdoeDetailsCallback;
import com.nst.yourname.model.database.ExternalPlayerDataBase;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.activity.CheckAppupdateActivity;
import com.nst.yourname.view.activity.ImportEPGActivity;
import com.nst.yourname.view.activity.ImportEPGXMLActivity;
import com.nst.yourname.view.activity.ImportM3uActivity;
import com.nst.yourname.view.activity.ImportStreamsActivity;
import com.nst.yourname.view.activity.LiveActivityNewFlow;
import com.nst.yourname.view.activity.LoginActivity;
import com.nst.yourname.view.activity.MultiUserActivity;
import com.nst.yourname.view.activity.NewDashboardActivity;
import com.nst.yourname.view.activity.NotificationActivity;
import com.nst.yourname.view.activity.PlayExternalPlayerActivity;
import com.nst.yourname.view.activity.RateUsActivity;
import com.nst.yourname.view.activity.RecordingActivity;
import com.nst.yourname.view.activity.RoutingActivity;
import com.nst.yourname.view.activity.SplashActivity;
import com.nst.yourname.view.activity.VodActivityNewFlow;
import com.nst.yourname.view.activity.VodActivityNewFlowSubCategories;
import com.nst.yourname.view.adapter.RecordingAdapter;
import com.nst.yourname.view.exoplayer.NSTEXOPlayerSkyActivity;
import com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity;
import com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerEPGActivity;
import com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity;
import com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity;
import com.nst.yourname.view.ijkplayer.application.Settings;
import com.pierfrancescosoffritti.youtubeplayer.player.PlayerConstants;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDateTime;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@SuppressWarnings("all")
public class Utils {
    static final boolean $assertionsDisabled = false;
    private static SharedPreferences SharedPreferencesSort;
    private static SharedPreferences.Editor SharedPreferencesSortEditor;
    public static PopupWindow changeSortPopUp;
    private static SharedPreferences loginPrefXtream;
    private static SharedPreferences.Editor loginPrefXtreamEditor;
    private static SharedPreferences loginPreferencesAfterLogin;
    private static SharedPreferences loginPreferencesDownloadStatus;
    private static SharedPreferences loginPreferences_layout;
    private static Settings mSettings;
    private static Dialog progressDialog;
    String downloadingpath = "";
    private ArrayList<String> filelist = new ArrayList<>();
    public int maxRetry = 5;
    SharedPreferences preferenceManager;
    public int retryCount = 0;
    public boolean retrying = false;
    public int[] startingSeconds = {1};

    public static AlertDialog showAlertBox(Context context, String str) {
        if (context == null || str.isEmpty()) {
            return null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(str);
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            /* class com.nst.yourname.miscelleneious.common.Utils.AnonymousClass1 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog create = builder.create();
        create.show();
        return create;
    }

    public static void showToast(Context context, String str) {
        if (context != null && str != "" && !str.isEmpty()) {
            Toast.makeText(context, str, 0).show();
        }
    }

    public static void showToastLong(Context context, String str) {
        if (context != null && str != "" && !str.isEmpty()) {
            Toast.makeText(context, str, 1).show();
        }
    }

    public static Retrofit retrofitObject(Context context) {
        if (context == null) {
            return null;
        }
        try {
            String lowerCase = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0).getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "").toLowerCase();
            Log.e("URl from Back", ">>>>>>>>" + lowerCase);
            if (!lowerCase.startsWith("http://") && !lowerCase.startsWith("https://")) {
                lowerCase = "http://" + lowerCase;
            }
            if (!lowerCase.endsWith("/")) {
                lowerCase = lowerCase + "/";
            }
            AppConst.SERVER_URL_FOR_MULTI_USER = lowerCase;
            return new Retrofit.Builder().baseUrl(lowerCase).client(new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).followRedirects(false).build()).addConverterFactory(GsonConverterFactory.create()).build();
        } catch (NullPointerException unused) {
            return null;
        } catch (IllegalArgumentException unused2) {
            return null;
        } catch (Exception unused3) {
            return null;
        }
    }

    public static Retrofit retrofitObjectTMDB(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return new Retrofit.Builder().baseUrl(AppConst.TMDB_BASE_URL).client(new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).followRedirects(false).build()).addConverterFactory(GsonConverterFactory.create()).build();
        } catch (NullPointerException unused) {
            return null;
        } catch (IllegalArgumentException unused2) {
            return null;
        } catch (Exception unused3) {
            return null;
        }
    }

    public static Retrofit retrofitObjectXML(Context context) {
        if (context == null) {
            return null;
        }
        try {
            String string = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0).getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "");
            if (!string.startsWith("http://") && !string.startsWith("https://")) {
                string = "http://" + string;
            }
            if (!string.endsWith("/")) {
                string = string + "/";
            }
            return new Retrofit.Builder().baseUrl(string).client(new OkHttpClient.Builder().connectTimeout(600, TimeUnit.SECONDS).writeTimeout(600, TimeUnit.SECONDS).readTimeout(600, TimeUnit.SECONDS).build()).addConverterFactory(SimpleXmlConverterFactory.create()).build();
        } catch (NullPointerException unused) {
            return null;
        } catch (IllegalArgumentException unused2) {
            return null;
        } catch (Exception unused3) {
            return null;
        }
    }

    public static int getNumberOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((((float) displayMetrics.widthPixels) / displayMetrics.density) / 180.0f);
    }

    public static long epgTimeConverter(String str) {
        int i;
        if (str == null) {
            return 0;
        }
        try {
            if (str.length() >= 18) {
                int i2 = 15;
                if (str.charAt(15) == '+') {
                    i2 = 16;
                }
                i = Integer.parseInt(str.substring(i2, 18)) * 60;
            } else {
                i = 0;
            }
            if (str.length() >= 19) {
                i += Integer.parseInt(str.substring(18));
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return simpleDateFormat.parse(str.substring(0, 14)).getTime() - ((long) ((i * 60) * 1000));
        } catch (NumberFormatException e) {
            Log.e("XMLTVReader", "Exception", e);
            return 0;
        } catch (Throwable th) {
            Log.e("XMLTVReader", "Exception", th);
            return 0;
        }
    }

    public static void playWithVlcPlayer(Context context, String str, int i, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        Intent intent;
        if (context != null) {
            mSettings = new Settings(context);
            if (mSettings.getPlayer() == 3) {
                intent = new Intent(context, NSTEXOPlayerSkyActivity.class);
                intent.putExtra("PlayerType", "live");
            } else {
                intent = new Intent(context, NSTIJKPlayerSkyActivity.class);
            }
            intent.putExtra("OPENED_STREAM_ID", i);
            intent.putExtra("STREAM_TYPE", str2);
            int i2 = 0;
            try {
                i2 = Integer.parseInt(str3);
            } catch (NumberFormatException unused) {
            }
            intent.putExtra("VIDEO_NUM", i2);
            intent.putExtra("VIDEO_TITLE", str4);
            intent.putExtra("EPG_CHANNEL_ID", str5);
            intent.putExtra("EPG_CHANNEL_LOGO", str6);
            intent.putExtra("OPENED_CAT_ID", str7);
            intent.putExtra("VIDEO_URL", str8);
            intent.putExtra("OPENED_CAT_NAME", str9);
            context.startActivity(intent);
        }
    }

    public static void playWithVlcPlayerEPG(Context context, String str, int i, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        String str9;
        Intent intent;
        if (context != null) {
            String ePGPlayerAppName = SharepreferenceDBHandler.getEPGPlayerAppName(context);
            if (!SharepreferenceDBHandler.getEPGPlayerPKGName(context).equals("default") && !new ExternalPlayerDataBase(context).CheckPlayerExistense(ePGPlayerAppName)) {
                SharepreferenceDBHandler.setEPGPlayer("default", "default", context);
            }
            String ePGPlayerPKGName = SharepreferenceDBHandler.getEPGPlayerPKGName(context);
            if (ePGPlayerPKGName.equals("default")) {
                mSettings = new Settings(context);
                if (mSettings.getPlayer() == 3) {
                    intent = new Intent(context, NSTEXOPlayerSkyActivity.class);
                    intent.putExtra("PlayerType", "large_epg");
                } else {
                    intent = new Intent(context, NSTIJKPlayerEPGActivity.class);
                    intent.putExtra("MultiPlayer", "false");
                }
                intent.putExtra("OPENED_STREAM_ID", i);
                intent.putExtra("STREAM_TYPE", str2);
                int i2 = -1;
                if (!str3.equals("")) {
                    i2 = Integer.parseInt(str3);
                }
                intent.putExtra("VIDEO_NUM", i2);
                intent.putExtra("VIDEO_TITLE", str4);
                intent.putExtra("EPG_CHANNEL_ID", str5);
                intent.putExtra("EPG_CHANNEL_LOGO", str6);
                intent.putExtra("OPENED_CAT_ID", str7);
                intent.putExtra("VIDEO_URL", str8);
                context.startActivity(intent);
                return;
            }
            String string = context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0).getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
            if (string != null && !string.isEmpty() && !string.equals("") && string.equals("default")) {
                str9 = "";
            } else if (string == null || string.isEmpty() || string.equals("") || !string.equals("ts")) {
                str9 = (string == null || string.isEmpty() || string.equals("") || !string.equals("m3u8")) ? "" : ".m3u8";
            } else {
                str9 = ".ts";
            }
            if (!SharepreferenceDBHandler.getCurrentAPPType(context).equalsIgnoreCase(AppConst.TYPE_M3U)) {
                str8 = getUrlLive(context, i, str9, "live");
            }
            String ePGPlayerAppName2 = SharepreferenceDBHandler.getEPGPlayerAppName(context);
            Intent intent2 = new Intent(context, PlayExternalPlayerActivity.class);
            intent2.putExtra("url", str8);
            intent2.putExtra(AppConst.PACKAGE_NAME, ePGPlayerPKGName);
            intent2.putExtra(AppConst.APP_NAME, ePGPlayerAppName2);
            context.startActivity(intent2);
        }
    }

    public static void playWithPlayerVOD(Context context, String str, int i, String str2, String str3, String str4, String str5, String str6) {
        Intent intent;
        if (context != null) {
            String vODPlayerAppName = SharepreferenceDBHandler.getVODPlayerAppName(context);
            if (!SharepreferenceDBHandler.getVODPlayerPkgName(context).equals("default") && !new ExternalPlayerDataBase(context).CheckPlayerExistense(vODPlayerAppName)) {
                SharepreferenceDBHandler.setVODPlayer("default", "default", context);
            }
            String vODPlayerAppName2 = SharepreferenceDBHandler.getVODPlayerAppName(context);
            String vODPlayerPkgName = SharepreferenceDBHandler.getVODPlayerPkgName(context);
            if (vODPlayerPkgName.equals("default")) {
                mSettings = new Settings(context);
                if (mSettings.getPlayer() == 3) {
                    intent = new Intent(context, NSTEXOPlayerVODActivity.class);
                } else {
                    intent = new Intent(context, NSTIJKPlayerVODActivity.class);
                }
                intent.putExtra("type", "movies");
                intent.putExtra("OPENED_STREAM_ID", i);
                intent.putExtra("STREAM_TYPE", str2);
                int i2 = 0;
                try {
                    i2 = Integer.parseInt(str4);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                intent.putExtra("VIDEO_NUM", i2);
                intent.putExtra("VIDEO_TITLE", str5);
                intent.putExtra("CONTAINER_EXTENSION", str3);
                intent.putExtra("VIDEO_URL", str6);
                context.startActivity(intent);
                return;
            }
            if (!SharepreferenceDBHandler.getCurrentAPPType(context).equalsIgnoreCase(AppConst.TYPE_M3U)) {
                str6 = getUrl(context, i, str3, AppConst.EVENT_TYPE_MOVIE);
            }
            Intent intent2 = new Intent(context, PlayExternalPlayerActivity.class);
            intent2.putExtra("url", str6);
            intent2.putExtra(AppConst.PACKAGE_NAME, vODPlayerPkgName);
            intent2.putExtra(AppConst.APP_NAME, vODPlayerAppName2);
            context.startActivity(intent2);
        }
    }

    public static void playAllWithPlayerVOD(Context context, String str, int i, String str2, String str3, String str4, String str5, String str6) {
        Intent intent;
        if (context != null) {
            mSettings = new Settings(context);
            if (mSettings.getPlayer() == 3) {
                intent = new Intent(context, NSTEXOPlayerVODActivity.class);
            } else {
                intent = new Intent(context, NSTIJKPlayerVODActivity.class);
            }
            intent.putExtra("type", "movies");
            intent.putExtra("OPENED_STREAM_ID", i);
            intent.putExtra("STREAM_TYPE", str2);
            intent.putExtra("VIDEO_NUM", Integer.parseInt(str4));
            intent.putExtra("VIDEO_TITLE", str5);
            intent.putExtra("CONTAINER_EXTENSION", str3);
            intent.putExtra("VIDEO_URL", str6);
            context.startActivity(intent);
        }
    }

    public static boolean appInstalledOrNot(String str, Context context) {
        if (context == null) {
            return false;
        }
        try {
            context.getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static void playWithPlayerSeries(Context context, String str, int i, String str2, String str3, String str4, String str5, List<GetEpisdoeDetailsCallback> list, String str6) {
        Intent intent;
        if (context != null) {
            String seriesPlayerAppName = SharepreferenceDBHandler.getSeriesPlayerAppName(context);
            if (!SharepreferenceDBHandler.getSeriesPlayerPkgName(context).equals("default") && !new ExternalPlayerDataBase(context).CheckPlayerExistense(seriesPlayerAppName)) {
                SharepreferenceDBHandler.setSeriesPlayer("default", "default", context);
            }
            String seriesPlayerPkgName = SharepreferenceDBHandler.getSeriesPlayerPkgName(context);
            if (seriesPlayerPkgName.equals("default")) {
                mSettings = new Settings(context);
                String str7 = SharepreferenceDBHandler.getCurrentAPPType(context).equalsIgnoreCase(AppConst.TYPE_M3U) ? "movies" : "series";
                if (mSettings.getPlayer() == 3) {
                    intent = new Intent(context, NSTEXOPlayerVODActivity.class);
                } else {
                    intent = new Intent(context, NSTIJKPlayerVODActivity.class);
                }
                intent.putExtra("type", str7);
                intent.putExtra("OPENED_STREAM_ID", i);
                intent.putExtra("STREAM_TYPE", str2);
                intent.putExtra("VIDEO_NUM", Integer.parseInt(str4));
                intent.putExtra("VIDEO_TITLE", str5);
                intent.putExtra("CONTAINER_EXTENSION", str3);
                intent.putExtra("EPISODES", (Serializable) list);
                intent.putExtra("VIDEO_URL", str6);
                context.startActivity(intent);
                return;
            }
            if (!SharepreferenceDBHandler.getCurrentAPPType(context).equals(AppConst.TYPE_M3U)) {
                str6 = getUrl(context, i, str3, "series");
            }
            String seriesPlayerAppName2 = SharepreferenceDBHandler.getSeriesPlayerAppName(context);
            Intent intent2 = new Intent(context, PlayExternalPlayerActivity.class);
            intent2.putExtra("url", str6);
            intent2.putExtra(AppConst.PACKAGE_NAME, seriesPlayerPkgName);
            intent2.putExtra(AppConst.APP_NAME, seriesPlayerAppName2);
            context.startActivity(intent2);
        }
    }

    public static void playWithPlayerArchive(Context context, String str, int i, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        Intent intent;
        if (context != null) {
            try {
                String catchUpPlayerAppName = SharepreferenceDBHandler.getCatchUpPlayerAppName(context);
                if (!SharepreferenceDBHandler.getCatchUpPlayerAppName(context).equals("default") && !new ExternalPlayerDataBase(context).CheckPlayerExistense(catchUpPlayerAppName)) {
                    SharepreferenceDBHandler.setCatchUpPlayer("default", "default", context);
                }
                mSettings = new Settings(context);
                String catchUpPlayerPkgName = SharepreferenceDBHandler.getCatchUpPlayerPkgName(context);
                if (catchUpPlayerPkgName.equals("default")) {
                    if (mSettings.getPlayer() == 3) {
                        intent = new Intent(context, NSTEXOPlayerVODActivity.class);
                    } else {
                        intent = new Intent(context, NSTIJKPlayerVODActivity.class);
                    }
                    intent.putExtra("OPENED_STREAM_ID", i);
                    intent.putExtra("type", "catch_up");
                    intent.putExtra("VIDEO_NUM", Integer.parseInt(str2));
                    intent.putExtra("VIDEO_TITLE", str3);
                    intent.putExtra("STREAM_START_TIME", str6);
                    intent.putExtra("STREAM_STOP_TIME", str8);
                    context.startActivity(intent);
                    return;
                }
                String timeshiftUrl = getTimeshiftUrl(context, i, str6, str8);
                String catchUpPlayerAppName2 = SharepreferenceDBHandler.getCatchUpPlayerAppName(context);
                Intent intent2 = new Intent(context, PlayExternalPlayerActivity.class);
                intent2.putExtra("url", timeshiftUrl);
                intent2.putExtra(AppConst.PACKAGE_NAME, catchUpPlayerPkgName);
                intent2.putExtra(AppConst.APP_NAME, catchUpPlayerAppName2);
                context.startActivity(intent2);
            } catch (Exception unused) {
            }
        }
    }

    public static String parseDateToddMMyyyy(String str) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US).parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String parseDateToddMMyyyy1(String str) {
        Date date;
        try {
            date = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.US).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        System.out.println(date);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        String str2 = instance.get(5) + "/" + (instance.get(2) + 1) + "/" + instance.get(1);
        System.out.println("formatedDate : " + str2);
        return str2;
    }

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        return TextUtils.isEmpty(str.trim());
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0180 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x0182 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x0184 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x0186 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x0188 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x018a A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x018c A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:107:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x014e A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0151 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0154 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0157 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x015a A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x015d A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0160 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x0163 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0166 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0169 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x016c A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x016f A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x0172 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0175 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x0178 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x017b A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x017e A[RETURN, SYNTHETIC] */
    public static int getPositionOfEPG(String str) {
        char c = 0;
        String str2 = str;
        int hashCode = str.hashCode();
        if (hashCode != 48) {
            switch (hashCode) {
                case 1382:
                    if (str2.equals("+1")) {
                        c = 13;
                        break;
                    }
                    break;
                case 1383:
                    if (str2.equals("+2")) {
                        c = 14;
                        break;
                    }
                    break;
                case 1384:
                    if (str2.equals("+3")) {
                        c = 15;
                        break;
                    }
                    break;
                case 1385:
                    if (str2.equals("+4")) {
                        c = 16;
                        break;
                    }
                    break;
                case 1386:
                    if (str2.equals("+5")) {
                        c = 17;
                        break;
                    }
                    break;
                case 1387:
                    if (str2.equals("+6")) {
                        c = 18;
                        break;
                    }
                    break;
                case 1388:
                    if (str2.equals("+7")) {
                        c = 19;
                        break;
                    }
                    break;
                case 1389:
                    if (str2.equals("+8")) {
                        c = 20;
                        break;
                    }
                    break;
                case 1390:
                    if (str2.equals("+9")) {
                        c = 21;
                        break;
                    }
                    break;
                default:
                    switch (hashCode) {
                        case 1444:
                            if (str2.equals("-1")) {
                                c = 11;
                                break;
                            }
                            break;
                        case 1445:
                            if (str2.equals("-2")) {
                                c = 10;
                                break;
                            }
                            break;
                        case 1446:
                            if (str2.equals("-3")) {
                                c = 9;
                                break;
                            }
                            break;
                        case 1447:
                            if (str2.equals("-4")) {
                                c = 8;
                                break;
                            }
                            break;
                        case 1448:
                            if (str2.equals("-5")) {
                                c = 7;
                                break;
                            }
                            break;
                        case 1449:
                            if (str2.equals("-6")) {
                                c = 6;
                                break;
                            }
                            break;
                        case 1450:
                            if (str2.equals("-7")) {
                                c = 5;
                                break;
                            }
                            break;
                        case 1451:
                            if (str2.equals("-8")) {
                                c = 4;
                                break;
                            }
                            break;
                        case 1452:
                            if (str2.equals("-9")) {
                                c = 3;
                                break;
                            }
                            break;
                        default:
                            switch (hashCode) {
                                case 42890:
                                    if (str2.equals("+10")) {
                                        c = 22;
                                        break;
                                    }
                                    break;
                                case 42891:
                                    if (str2.equals("+11")) {
                                        c = 23;
                                        break;
                                    }
                                    break;
                                case 42892:
                                    if (str2.equals("+12")) {
                                        c = 24;
                                        break;
                                    }
                                    break;
                                default:
                                    switch (hashCode) {
                                        case 44812:
                                            if (str2.equals(PlayerConstants.PlaybackRate.UNKNOWN)) {
                                                c = 2;
                                                break;
                                            }
                                            break;
                                        case 44813:
                                            if (str2.equals("-11")) {
                                                c = 1;
                                                break;
                                            }
                                            break;
                                        case 44814:
                                            if (str2.equals("-12")) {
                                                c = 0;
                                                break;
                                            }
                                            break;
                                    }
                            }
                    }
            }
            switch (c) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
                case 4:
                    return 4;
                case 5:
                    return 5;
                case 6:
                    return 6;
                case 7:
                    return 7;
                case 8:
                    return 8;
                case 9:
                    return 9;
                case 10:
                    return 10;
                case 11:
                    return 11;
                case 12:
                default:
                    return 12;
                case 13:
                    return 13;
                case 14:
                    return 14;
                case 15:
                    return 15;
                case 16:
                    return 16;
                case 17:
                    return 17;
                case 18:
                    return 18;
                case 19:
                    return 19;
                case 20:
                    return 20;
                case 21:
                    return 21;
                case 22:
                    return 22;
                case 23:
                    return 23;
                case 24:
                    return 24;
            }
        } else if (str2.equals(AppConst.PASSWORD_UNSET)) {
            c = 12;
            switch (c) {
            }
        }
        c = 65535;
        switch (c) {
        }
        return hashCode;
    }

    public static int getMilliSeconds(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        if (str.contains("+")) {
            return Integer.parseInt(str.split("\\+")[1]) * 60 * 60 * 1000;
        }
        if (str.contains("-")) {
            return (-Integer.parseInt(str.split("\\-")[1])) * 60 * 60 * 1000;
        }
        return 0;
    }

    public static boolean isEventVisible(long j, long j2, Context context) {
        if (context == null) {
            return false;
        }
        long millis = LocalDateTime.now().toDateTime().getMillis() + getTimeShiftMilliSeconds(context);
        if (j > millis || j2 < millis) {
            return false;
        }
        return true;
    }

    public static long getTimeShiftMilliSeconds(Context context) {
        if (context == null) {
            return 0;
        }
        loginPreferencesAfterLogin = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        if (loginPreferencesAfterLogin != null) {
            return (long) getMilliSeconds(loginPreferencesAfterLogin.getString(AppConst.LOGIN_PREF_SELECTED_EPG_SHIFT, ""));
        }
        return 0;
    }

    public static int getPercentageLeft(long j, long j2, Context context) {
        if (context == null) {
            return 0;
        }
        try {
            long millis = LocalDateTime.now().toDateTime().getMillis() + getTimeShiftMilliSeconds(context);
            if (j < j2) {
                if (millis < j2) {
                    if (millis <= j) {
                        return 100;
                    }
                    return (int) (((j2 - millis) * 100) / (j2 - j));
                }
            }
            return 0;
        } catch (Exception unused) {
            return 0;
        }
    }

    public static String ukde(String str) {
        byte[] decode = Base64.decode(str, 0);
        if (Build.VERSION.SDK_INT >= 19) {
            return new String(decode, StandardCharsets.UTF_8);
        }
        return "";
    }

    public static void logoutUser(Context context) {
        Intent intent;
        if (context != null) {
            if (!AppConst.SWITCH_FROM_DASHBOARD_TO_MULTIUSER.booleanValue()) {
                Toast.makeText(context, context.getString(R.string.logged_out), 0).show();
            }
            AppConst.SWITCH_FROM_DASHBOARD_TO_MULTIUSER = false;
            if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
                intent = new Intent(context, MultiUserActivity.class);
            } else if (!AppConst.M3U_LINE_ACTIVE.booleanValue() || !AppConst.M3U_API_SINGLE_USER.booleanValue()) {
                intent = new Intent(context, LoginActivity.class);
            } else {
                intent = new Intent(context, RoutingActivity.class);
            }
            SharedPreferences sharedPreferences = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            String string = sharedPreferences.getString(AppConst.LOGIN_PREF_SELECTED_EPG_SHIFT, "");
            edit.clear();
            edit.apply();
            if (edit != null) {
                edit.putString(AppConst.LOGIN_PREF_SELECTED_EPG_SHIFT, string);
                edit.apply();
            }
            if (AppConst.MultiDNS_And_MultiUser.booleanValue()) {
                SharedPreferences.Editor edit2 = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_REMEBER_ME, 0).edit();
                edit2.clear();
                edit2.apply();
            }
            if (AppConst.M3U_LINE_ACTIVE.booleanValue() && AppConst.M3U_API_SINGLE_USER.booleanValue()) {
                SharedPreferences.Editor edit3 = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_REMEBER_ME, 0).edit();
                edit3.putBoolean(AppConst.PREF_SAVE_LOGIN, false);
                edit3.apply();
                if (SharepreferenceDBHandler.getCurrentAPPType(context).equalsIgnoreCase(AppConst.TYPE_M3U)) {
                    SharedPreferences.Editor edit4 = context.getSharedPreferences(AppConst.SHARED_PREFERENCE_REMEBER_ME, 0).edit();
                    edit4.clear();
                    edit4.apply();
                }
            }
            if (!AppConst.MULTIUSER_ACTIVE.booleanValue()) {
                intent.setFlags(335577088);
                context.startActivity(intent);
                ((Activity) context).finish();
            } else if (!AppConst.M3U_LINE_ACTIVE.booleanValue() || !AppConst.M3U_API_SINGLE_USER.booleanValue()) {
                context.startActivity(intent);
            } else {
                intent.setFlags(335577088);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        }
    }

    public static void set_layout_live(Context context) {
        context.startActivity(new Intent(context, LiveActivityNewFlow.class));
    }

    public static void set_layout_vod(Context context) {
        context.startActivity(new Intent(context, VodActivityNewFlow.class));
    }

    public static String getTime(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return new SimpleDateFormat(context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0).getString(AppConst.LOGIN_PREF_TIME_FORMAT, "HH:MM"), Locale.US).format(new Date());
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getDate(String str) {
        try {
            return new SimpleDateFormat(" MMMM dd,yyyy").format(new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US).parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void loadChannelsAndVod(Context context) {
        if (context == null) {
            return;
        }
        if (!AppConst.M3U_LINE_ACTIVE.booleanValue() || !SharepreferenceDBHandler.getCurrentAPPType(context).equals(AppConst.TYPE_M3U)) {
            context.startActivity(new Intent(context, ImportStreamsActivity.class));
        } else {
            context.startActivity(new Intent(context, ImportM3uActivity.class));
        }
    }

    public static void loadTvGuid(Context context) {
        if (context == null) {
            return;
        }
        if (!AppConst.M3U_LINE_ACTIVE.booleanValue() || !SharepreferenceDBHandler.getCurrentAPPType(context).equals(AppConst.TYPE_M3U)) {
            context.startActivity(new Intent(context, ImportEPGActivity.class));
        } else {
            context.startActivity(new Intent(context, ImportEPGXMLActivity.class));
        }
    }

    public static void setLocale(Context context, String str) {
        String str2;
        if (str.equals("English")) {
            str2 = "en";
        } else if (str.equals("Polish")) {
            str2 = "pl";
        } else if (str.equals("Portuguese")) {
            str2 = "pt";
        } else if (str.equals("Turkish")) {
            str2 = "tr";
        } else if (str.equals("Croatian")) {
            str2 = "hr";
        } else if (str.equals("Spanish")) {
            str2 = "es";
        } else if (str.equals("Arabic")) {
            str2 = "ar";
        } else if (str.equals("French")) {
            str2 = "fr";
        } else if (str.equals("German")) {
            str2 = "de";
        } else if (str.equals("Italian")) {
            str2 = "it";
        } else if (str.equals("Romanian")) {
            str2 = "ro";
        } else if (str.equals("Hungary")) {
            str2 = "hu";
        } else {
            str2 = str.equals("Albanian") ? "sq" : "en";
        }
        if (context != null) {
            Resources resources = context.getResources();
            Resources resources2 = context.getApplicationContext().getResources();
            Locale locale = new Locale(str2);
            Locale.setDefault(locale);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
            resources2.updateConfiguration(configuration, resources.getDisplayMetrics());
            Configuration configuration2 = resources.getConfiguration();
            configuration2.setLocale(locale);
            context.getApplicationContext().createConfigurationContext(configuration2);
            context.createConfigurationContext(configuration2);
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getLanguageSelected(String str) {
        char c;
        switch (str.hashCode()) {
            case 3121:
                if (str.equals("ar")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 3201:
                if (str.equals("de")) {
                    c = 8;
                    break;
                }
                c = 65535;
                break;
            case 3241:
                if (str.equals("en")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3246:
                if (str.equals("es")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 3276:
                if (str.equals("fr")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 3338:
                if (str.equals("hr")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 3341:
                if (str.equals("hu")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 3371:
                if (str.equals("it")) {
                    c = 9;
                    break;
                }
                c = 65535;
                break;
            case 3580:
                if (str.equals("pl")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3588:
                if (str.equals("pt")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3645:
                if (str.equals("ro")) {
                    c = 10;
                    break;
                }
                c = 65535;
                break;
            case 3678:
                if (str.equals("sq")) {
                    c = 12;
                    break;
                }
                c = 65535;
                break;
            case 3710:
                if (str.equals("tr")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            default:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            case 8:
                return 8;
            case 9:
                return 9;
            case 10:
                return 10;
            case 11:
                return 11;
            case 12:
                return 12;
        }
    }

    public static Retrofit retrofitObjectLogin(Context context) {
        if (context == null) {
            return null;
        }
        String magportal = new LiveStreamDBHandler(context).getMagportal();
        if (!magportal.equals("")) {
            if (!magportal.startsWith("http://") && !magportal.startsWith("https://")) {
                magportal = "http://" + magportal;
            }
            if (magportal.endsWith("/c")) {
                magportal = magportal.substring(0, magportal.length() - 2);
            }
            if (!magportal.endsWith("/")) {
                magportal = magportal + "/";
            }
        } else {
            magportal = context.getSharedPreferences(AppConst.LOGIN_PREF_SERVER_URL_MAG, 0).getString(AppConst.LOGIN_PREF_SERVER_URL_MAG, "");
        }
        if (!Patterns.WEB_URL.matcher(magportal).matches()) {
            return null;
        }
        return new Retrofit.Builder().baseUrl(magportal).client(new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build()).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static void appResume(Context context) {
        if (context != null) {
            String string = context.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, 0).getString(AppConst.LOGIN_PREF_SELECTED_LANGUAGE, "");
            if (!string.equals("")) {
                setLocale(context, string);
            }
        }
    }

    public static String getFormattedUrl(String str) {
        return str.replaceAll(" ", "%20");
    }

    public static void showcustomToastLong(Context context, String str) {
        if (context != null) {
            View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate((int) R.layout.custom_toast_layout, (ViewGroup) null);
            ((TextView) inflate.findViewById(R.id.toastTextView)).setText(str);
            ((ImageView) inflate.findViewById(R.id.toastImageView)).setImageResource(R.drawable.alert_icon);
            Toast toast = new Toast(context);
            toast.setDuration(0);
            toast.setView(inflate);
            toast.show();
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
     arg types: [android.widget.Button, android.app.Activity]
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
    public void showDownloadRunningPopUP(final Activity activity) {
        if (activity != null) {
            View inflate = ((LayoutInflater) activity.getSystemService("layout_inflater")).inflate((int) R.layout.download_running_popup, (RelativeLayout) activity.findViewById(R.id.rl_password_verification));
            changeSortPopUp = new PopupWindow(activity);
            changeSortPopUp.setContentView(inflate);
            changeSortPopUp.setWidth(-1);
            changeSortPopUp.setHeight(-1);
            changeSortPopUp.setFocusable(true);
            changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
            Button button = (Button) inflate.findViewById(R.id.bt_start_recording);
            Button button2 = (Button) inflate.findViewById(R.id.bt_close);
            if (button != null) {
                button.setOnFocusChangeListener(new OnFocusChangeAccountListener((View) button, activity));
            }
            if (button2 != null) {
                button2.setOnFocusChangeListener(new OnFocusChangeAccountListener((View) button2, activity));
            }
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.miscelleneious.common.Utils.AnonymousClass2 */

                public void onClick(View view) {
                    Utils.changeSortPopUp.dismiss();
                }
            });
            if (button != null) {
                button.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.miscelleneious.common.Utils.AnonymousClass3 */

                    public void onClick(View view) {
                        Utils.this.preferenceManager = PreferenceManager.getDefaultSharedPreferences(activity);
                        SharedPreferences.Editor edit = Utils.this.preferenceManager.edit();
                        edit.putBoolean("CANCELLED", true);
                        edit.apply();
                        Utils.this.updateDownloadStatus(activity, "stopped");
                        Toast.makeText(activity, activity.getResources().getString(R.string.download_stopped), 0).show();
                        new Handler().postDelayed(new Runnable() {
                            /* class com.nst.yourname.miscelleneious.common.Utils.AnonymousClass3.AnonymousClass1 */

                            public void run() {
                                Utils.changeSortPopUp.dismiss();
                            }
                        }, 500);
                    }
                });
            }
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
     arg types: [android.widget.Button, android.app.Activity]
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
    public void showRecordingPopUP(final Activity activity, String str, String str2, String str3, int i, String str4) {
        final String str5;
        String str6;
        String str7 = str3;
        int i2 = i;
        if (activity != null) {
            View inflate = ((LayoutInflater) activity.getSystemService("layout_inflater")).inflate((int) R.layout.recording_popup, (RelativeLayout) activity.findViewById(R.id.rl_password_verification));
            changeSortPopUp = new PopupWindow(activity);
            changeSortPopUp.setContentView(inflate);
            changeSortPopUp.setWidth(-1);
            changeSortPopUp.setHeight(-1);
            changeSortPopUp.setFocusable(true);
            Button button = (Button) inflate.findViewById(R.id.bt_start_recording);
            Button button2 = (Button) inflate.findViewById(R.id.bt_close);
            final EditText editText = (EditText) inflate.findViewById(R.id.et_file_name);
            final EditText editText2 = (EditText) inflate.findViewById(R.id.et_duration);
            Button button3 = (Button) inflate.findViewById(R.id.bt_browse);
            final EditText editText3 = (EditText) inflate.findViewById(R.id.et_browse);
            if (button != null) {
                button.setOnFocusChangeListener(new OnFocusChangeAccountListener((View) button, activity));
            }
            if (button2 != null) {
                button2.setOnFocusChangeListener(new OnFocusChangeAccountListener((View) button2, activity));
            }
            if (button3 != null) {
                button3.setOnFocusChangeListener(new OnFocusChangeAccountListener((View) button3, activity));
            }
            String str8 = str + ".ts";
            if (SharepreferenceDBHandler.getCurrentAPPType(activity).equals(AppConst.TYPE_M3U)) {
                str5 = str4;
            } else {
                if (str2.equals("")) {
                    str6 = str7 + i2;
                } else {
                    str6 = str7 + i2 + ".ts";
                }
                str5 = str6;
            }
            editText.setText(str8);
            editText.requestFocus();
            editText3.setEnabled(false);
            File file = new File(Environment.getExternalStorageDirectory(), AppConst.RECORDING_DIRECTORY);
            if (!file.exists()) {
                file.mkdirs();
            }
            final SharedPreferences sharedPreferences = activity.getSharedPreferences(AppConst.LOGIN_PREF_RECORDING_DIR, 0);
            editText3.setText(sharedPreferences.getString(AppConst.LOGIN_PREF_RECORDING_DIR, Environment.getExternalStorageDirectory().toString() + "/" + AppConst.RECORDING_DIRECTORY));
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.miscelleneious.common.Utils.AnonymousClass4 */

                public void onClick(View view) {
                    Utils.changeSortPopUp.dismiss();
                }
            });
            button3.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.miscelleneious.common.Utils.AnonymousClass5 */
                public String m_chosenDir = "";
                private boolean m_newFolderEnabled = true;

                public void onClick(View view) {
                    DirectoryChooserDialog directoryChooserDialog = new DirectoryChooserDialog(activity, new DirectoryChooserDialog.ChosenDirectoryListener() {
                        /* class com.nst.yourname.miscelleneious.common.Utils.AnonymousClass5.AnonymousClass1 */

                        @Override // com.nst.yourname.miscelleneious.DirectoryChooserDialog.ChosenDirectoryListener
                        public void onChosenDir(String str) {
                            m_chosenDir = str;
                            editText3.setText(str);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putString(AppConst.LOGIN_PREF_RECORDING_DIR, str);
                            edit.apply();
                            //Activity activity = activity;
                            Toast.makeText(activity, "Chosen directory: " + str, 1).show();
                        }
                    });
                    directoryChooserDialog.setNewFolderEnabled(this.m_newFolderEnabled);
                    directoryChooserDialog.chooseDirectory("");
                    this.m_newFolderEnabled = !this.m_newFolderEnabled;
                }
            });
            if (button != null) {
                button.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.miscelleneious.common.Utils.AnonymousClass6 */

                    public void onClick(View view) {
                        int i;
                        if (fieldsCheck()) {
                            try {
                                i = Integer.parseInt(editText2.getText().toString());
                            } catch (NumberFormatException unused) {
                                i = 0;
                            }
                            new DownloadTask(activity, str5, i, editText.getText().toString(), true, editText3.getText().toString());
                            new Handler().postDelayed(new Runnable() {
                                /* class com.nst.yourname.miscelleneious.common.Utils.AnonymousClass6.AnonymousClass1 */

                                public void run() {
                                    Utils.changeSortPopUp.dismiss();
                                }
                            }, 500);
                        }
                    }

                    private boolean fieldsCheck() {
                        if (editText.getText().toString().trim().length() == 0) {
                            Toast.makeText(activity, activity.getResources().getString(R.string.input_filename), 1).show();
                            return false;
                        } else if (editText2.getText().toString().trim().length() == 0) {
                            Toast.makeText(activity, activity.getResources().getString(R.string.input_duration), 1).show();
                            return false;
                        } else if (editText2.getText().toString().trim().length() == 0) {
                            return true;
                        } else {
                            try {
                                Integer.parseInt(editText2.getText().toString());
                                return true;
                            } catch (NumberFormatException unused) {
                                Toast.makeText(activity, activity.getResources().getString(R.string.enter_correct_duaration), 1).show();
                                return false;
                            }
                        }
                    }
                });
            }
            changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
        }
    }

    public static class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private Activity activity;
        private Context context;
        private View view;

        public OnFocusChangeAccountListener(View view2, NSTIJKPlayerSkyActivity nSTIJKPlayerSkyActivity) {
            this.view = view2;
            this.activity = nSTIJKPlayerSkyActivity;
        }

        public OnFocusChangeAccountListener(View view2, Activity activity2) {
            this.view = view2;
            this.activity = activity2;
        }

        public OnFocusChangeAccountListener(View view2, RateUsActivity rateUsActivity) {
            this.view = view2;
            this.activity = rateUsActivity;
        }

        public OnFocusChangeAccountListener(View view2, CheckAppupdateActivity checkAppupdateActivity) {
            this.view = view2;
            this.activity = checkAppupdateActivity;
        }

        public OnFocusChangeAccountListener(View view2, RecordingActivity recordingActivity) {
            this.view = view2;
            this.activity = recordingActivity;
        }

        public OnFocusChangeAccountListener(View view2, VodActivityNewFlowSubCategories vodActivityNewFlowSubCategories) {
            this.view = view2;
            this.activity = vodActivityNewFlowSubCategories;
        }

        public OnFocusChangeAccountListener(View view2, MultiUserActivity multiUserActivity) {
            this.view = view2;
            this.activity = multiUserActivity;
        }

        public OnFocusChangeAccountListener(View view2, LoginActivity loginActivity) {
            this.view = view2;
            this.activity = loginActivity;
        }

        public OnFocusChangeAccountListener(View view2, NewDashboardActivity newDashboardActivity) {
            this.view = view2;
            this.activity = newDashboardActivity;
        }

        public OnFocusChangeAccountListener(View view2, SplashActivity splashActivity) {
            this.view = view2;
            this.activity = splashActivity;
        }

        public OnFocusChangeAccountListener(View view2, Context context2) {
            this.view = view2;
            this.context = context2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            if (this.activity != null) {
                float f = 1.0f;
                if (z) {
                    if (z) {
                        f = 1.12f;
                    }
                    if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("1")) {
                        performScaleXAnimation(f);
                        performScaleYAnimation(f);
                        view2.setBackgroundResource(R.drawable.back_btn_effect);
                    } else if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("2")) {
                        performScaleXAnimation(f);
                        performScaleYAnimation(f);
                        view2.setBackgroundResource(R.drawable.logout_btn_effect);
                    } else if (this.view == null || this.view.getTag() == null || !this.view.getTag().equals("3")) {
                        view2.setBackground(this.activity.getResources().getDrawable(R.drawable.selector_checkbox));
                    } else {
                        performScaleXAnimation(f);
                        performScaleYAnimation(f);
                        view2.setBackgroundResource(R.drawable.blue_btn_effect);
                    }
                } else if (!z) {
                    performScaleXAnimation(1.0f);
                    performScaleYAnimation(1.0f);
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("1"))) {
                        view2.setBackgroundResource(R.drawable.black_button_dark);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("2"))) {
                        view2.setBackgroundResource(R.drawable.black_button_dark);
                    }
                    if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("3")) {
                        view2.setBackgroundResource(R.drawable.black_button_dark);
                    }
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

    public class DownloadTask {
        static final boolean $assertionsDisabled = false;
        private static final String TAG = "Download Task";
        final String CANCELLED = "CANCELLED";
        CountDownTimer CountDownTimer;
        CountDownTimer CountDownTimerInternal;
        public String FileNameWithTime = "";
        public String browsePath;
        public NotificationCompat.Builder build;
        public Notification build1;
        private NotificationCompat.Builder buildStatusUpdate;
        private Button buttonText;
        public boolean checkIfCancelled = false;
        public Context context;
        public String downloadFileName = "";
        public boolean downloadStarted = false;
        public boolean downloadStatusCompleted = false;
        public String downloadUrl = "";
        public boolean errorDownloading = false;
        public NotificationManager mNotifyManager;
        public NotificationManager mNotifyManagerStatusUpdate;
        int notificationID = 234231;
        private boolean startDownload = true;
        public int timeInMilliSeconds;
        public int timeInMilliSecondsWithoutUpdation;
        public int timeInSeconds;
        public boolean timeup = false;

        public DownloadTask(Activity activity, String str, int i, String str2, boolean z, String str3) {
            this.context = activity;
            this.downloadUrl = str;
            int i2 = i * 60;
            int i3 = i2 * 1000;
            this.timeInMilliSeconds = i3;
            this.timeInMilliSecondsWithoutUpdation = i3;
            this.timeInSeconds = i2;
            this.startDownload = z;
            this.browsePath = str3;
            this.downloadFileName = str2;
            Utils.this.preferenceManager = PreferenceManager.getDefaultSharedPreferences(activity);
            new DownloadingTask().execute(new Void[0]);
        }

        public void showNotificationBox() {
            if (this.mNotifyManager != null) {
                this.mNotifyManager.cancelAll();
            }
            if (this.mNotifyManagerStatusUpdate != null) {
                this.mNotifyManagerStatusUpdate.cancelAll();
            }
            if (!Utils.this.retrying) {
                Toast.makeText(this.context, this.context.getResources().getString(R.string.download_started), 0).show();
            }
            this.mNotifyManager = (NotificationManager) this.context.getSystemService("notification");
            this.build = new NotificationCompat.Builder(this.context);
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel notificationChannel = new NotificationChannel("ksjadf87", this.context.getResources().getString(R.string.recording_dots), 2);
                this.build1 = new Notification.Builder(this.context).setContentTitle(this.context.getResources().getString(R.string.live_recording)).setContentText(this.context.getResources().getString(R.string.recording_dots)).setSmallIcon((int) R.mipmap.ic_launcher).setChannelId("ksjadf87").setProgress(100, 0, true).build();
                this.mNotifyManager.createNotificationChannel(notificationChannel);
                this.mNotifyManager.notify(this.notificationID, this.build1);
            } else {
                this.build.setContentTitle(this.context.getResources().getString(R.string.live_recording)).setProgress(100, 0, true).setContentText(this.context.getResources().getString(R.string.recording_dots)).setSmallIcon(R.mipmap.ic_launcher);
                this.mNotifyManager.notify(this.notificationID, this.build.build());
            }
            if (this.CountDownTimer != null) {
                this.CountDownTimer.cancel();
            }
            this.CountDownTimer = new CountDownTimer(20000, 1000) {
                /* class com.nst.yourname.miscelleneious.common.Utils.DownloadTask.AnonymousClass1 */

                public void onTick(long j) {
                    Log.e("onTick", "Testing:" + j);
                    if (DownloadTask.this.downloadStarted) {
                        Log.e("download started", "Testing:" + j);
                        cancel();
                        DownloadTask.this.CountDownTimerInternal = new CountDownTimer((long) DownloadTask.this.timeInMilliSeconds, 1000) {
                            /* class com.nst.yourname.miscelleneious.common.Utils.DownloadTask.AnonymousClass1.AnonymousClass1 */

                            public void onTick(long j) {
                                if (!DownloadTask.this.checkIfCancelled) {
                                    int[] access$600 = Utils.this.startingSeconds;
                                    int i = access$600[0];
                                    access$600[0] = i + 1;
                                    if (Build.VERSION.SDK_INT >= 26) {
                                        Utils.this.preferenceManager = PreferenceManager.getDefaultSharedPreferences(DownloadTask.this.context);
                                        SharedPreferences.Editor edit = Utils.this.preferenceManager.edit();
                                        edit.putBoolean("CANCELLED", false);
                                        edit.apply();
                                        NotificationActivity.getDismissIntent(DownloadTask.this.notificationID, DownloadTask.this.context);
                                        DownloadTask downloadTask = DownloadTask.this;
                                        Notification.Builder builder = new Notification.Builder(DownloadTask.this.context);
                                        Notification unused = downloadTask.build1 = builder.setContentTitle(DownloadTask.this.context.getResources().getString(R.string.recording_dots) + Utils.this.secondsToHoursFormat(i) + " - " + Utils.this.secondsToHoursFormat(DownloadTask.this.timeInMilliSecondsWithoutUpdation / 1000)).setSmallIcon((int) R.mipmap.ic_launcher).setChannelId("ksjadf87").setSound((Uri) null, (AudioAttributes) null).build();
                                        DownloadTask.this.mNotifyManager.createNotificationChannel(new NotificationChannel("ksjadf87", DownloadTask.this.context.getResources().getString(R.string.recording_dots), 2));
                                        DownloadTask.this.mNotifyManager.notify(DownloadTask.this.notificationID, DownloadTask.this.build1);
                                    } else {
                                        NotificationCompat.Builder access$1000 = DownloadTask.this.build;
                                        access$1000.setContentText(DownloadTask.this.context.getResources().getString(R.string.recording_dots) + Utils.this.secondsToHoursFormat(i) + " - " + Utils.this.secondsToHoursFormat(DownloadTask.this.timeInMilliSecondsWithoutUpdation / 1000));
                                        DownloadTask.this.mNotifyManager.notify(DownloadTask.this.notificationID, DownloadTask.this.build.build());
                                    }
                                    if (DownloadTask.this.downloadStatusCompleted) {
                                        DownloadTask.this.mNotifyManager.cancelAll();
                                        DownloadTask.this.CountDownTimerInternal.cancel();
                                    }
                                    Log.e("Debugging:", "Seconds:" + Utils.this.secondsToHoursFormat(i) + "   Reverse Seconds:" + Utils.this.secondsToHoursFormat((int) (j / 1000)) + " asdf:" + DownloadTask.this.timeInMilliSeconds + " Timeinseconds:" + DownloadTask.this.timeInSeconds);
                                    Utils.this.updateDownloadStatus(DownloadTask.this.context, "processing");
                                } else if (Utils.this.retrying) {
                                    int unused2 = DownloadTask.this.timeInSeconds = DownloadTask.this.timeInSeconds - Utils.this.startingSeconds[0];
                                    int unused3 = DownloadTask.this.timeInMilliSeconds = DownloadTask.this.timeInSeconds * 1000;
                                    Log.e("Debugging:", "retrying: " + DownloadTask.this.timeInMilliSeconds);
                                    DownloadTask.this.CountDownTimerInternal.cancel();
                                } else {
                                    Log.e("Debugging:", "cancelled recording");
                                    DownloadTask.this.mNotifyManager.cancelAll();
                                    DownloadTask.this.CountDownTimerInternal.cancel();
                                    DownloadTask.this.updateNotification("stopped");
                                }
                            }

                            public void onFinish() {
                                DownloadTask.this.CountDownTimerInternal.cancel();
                                boolean unused = DownloadTask.this.timeup = true;
                            }
                        }.start();
                    }
                }

                public void onFinish() {
                    cancel();
                    if (!Utils.this.retrying && DownloadTask.this.errorDownloading) {
                        DownloadTask.this.updateNotification("failed");
                    }
                }
            }.start();
        }

        public void addDismissButton() {
            Utils.this.preferenceManager = PreferenceManager.getDefaultSharedPreferences(this.context);
            SharedPreferences.Editor edit = Utils.this.preferenceManager.edit();
            edit.putBoolean("CANCELLED", false);
            edit.apply();
            PendingIntent dismissIntent = NotificationActivity.getDismissIntent(this.notificationID, this.context);
            if (Build.VERSION.SDK_INT < 26) {
                this.build.addAction(R.drawable.stop_icon, "Stop", dismissIntent);
            }
        }

        public void updateNotification(String str) {
            this.mNotifyManager.cancelAll();
            if (this.CountDownTimerInternal != null) {
                this.CountDownTimerInternal.cancel();
            }
            this.buildStatusUpdate = new NotificationCompat.Builder(this.context).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(this.context.getResources().getString(R.string.live_recording));
            if (str.equals("completed")) {
                Utils.this.updateDownloadStatus(this.context, "completed");
                this.downloadStatusCompleted = true;
                this.buildStatusUpdate.setContentText(this.context.getResources().getString(R.string.download_completed));
                if (!Utils.this.retrying) {
                    Toast.makeText(this.context, this.context.getResources().getString(R.string.download_completed), 0).show();
                }
            } else if (str.equals("failed")) {
                Utils.this.updateDownloadStatus(this.context, "failed");
                this.buildStatusUpdate.setContentText(this.context.getResources().getString(R.string.download_failed));
                if (!Utils.this.retrying) {
                    Toast.makeText(this.context, this.context.getResources().getString(R.string.download_failed), 0).show();
                }
            } else if (str.equals("stopped")) {
                Utils.this.updateDownloadStatus(this.context, "stopped");
                this.buildStatusUpdate.setContentText(this.context.getResources().getString(R.string.download_stopped));
                if (!Utils.this.retrying) {
                    Toast.makeText(this.context, this.context.getResources().getString(R.string.download_stopped), 0).show();
                }
            }
            this.mNotifyManagerStatusUpdate = (NotificationManager) this.context.getSystemService("notification");
            this.mNotifyManagerStatusUpdate.notify(455, this.buildStatusUpdate.build());
        }

        @SuppressLint({"StaticFieldLeak"})
        private class DownloadingTask extends AsyncTask<Void, Void, Void> {
            File apkStorage;
            FileOutputStream outputFile;

            private DownloadingTask() {
                this.apkStorage = null;
                this.outputFile = null;
            }

            public void onPreExecute() {
                super.onPreExecute();
                DownloadTask.this.showNotificationBox();
                DownloadTask.this.addDismissButton();
            }

            public void onPostExecute(Void voidR) {
                try {
                    if (this.outputFile != null) {
                        if (!DownloadTask.this.checkIfCancelled) {
                            DownloadTask.this.updateNotification(DownloadTask.this.context.getResources().getString(R.string.completed));
                        } else if (Utils.this.retrying) {
                            if (Utils.this.retryCount >= Utils.this.maxRetry) {
                                Utils.showToast(DownloadTask.this.context, DownloadTask.this.context.getResources().getString(R.string.DownloadFailed));
                                Utils.this.retrying = false;
                                boolean unused = DownloadTask.this.errorDownloading = true;
                                DownloadTask.this.updateNotification(DownloadTask.this.context.getResources().getString(R.string.failed));
                            } else {
                                Utils.this.retrying = true;
                                new Handler().postDelayed(new Runnable() {
                                    /* class com.nst.yourname.miscelleneious.common.Utils.DownloadTask.DownloadingTask.AnonymousClass1 */

                                    public void run() {
                                        Utils.this.retryCount++;
                                        Utils.showToast(DownloadTask.this.context, "Retrying (" + Utils.this.retryCount + "/" + Utils.this.maxRetry + ")");
                                        new DownloadingTask().execute(new Void[0]);
                                    }
                                }, NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS);
                            }
                        }
                    } else if (Utils.this.retrying) {
                        if (Utils.this.retryCount >= Utils.this.maxRetry) {
                            Utils.this.retrying = false;
                            boolean unused2 = DownloadTask.this.errorDownloading = true;
                            DownloadTask.this.updateNotification(DownloadTask.this.context.getResources().getString(R.string.failed));
                        } else {
                            Utils.this.retrying = true;
                            new Handler().postDelayed(new Runnable() {
                                /* class com.nst.yourname.miscelleneious.common.Utils.DownloadTask.DownloadingTask.AnonymousClass2 */

                                public void run() {
                                    Utils.this.retryCount++;
                                    Utils.showToast(DownloadTask.this.context, "Retrying (" + Utils.this.retryCount + "/" + Utils.this.maxRetry + ")");
                                    new DownloadingTask().execute(new Void[0]);
                                }
                            }, NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    boolean unused3 = DownloadTask.this.errorDownloading = true;
                    DownloadTask.this.updateNotification(DownloadTask.this.context.getResources().getString(R.string.failed));
                }
                super.onPostExecute((Void) voidR);
            }

            /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
             method: ClspMth{java.io.FileOutputStream.<init>(java.io.File, boolean):void throws java.io.FileNotFoundException}
             arg types: [java.io.File, int]
             candidates:
              ClspMth{java.io.FileOutputStream.<init>(java.lang.String, boolean):void throws java.io.FileNotFoundException}
              ClspMth{java.io.FileOutputStream.<init>(java.io.File, boolean):void throws java.io.FileNotFoundException} */
            public Void doInBackground(Void... voidArr) {
                File file;
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(DownloadTask.this.downloadUrl).openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode() != 200) {
                        Log.e(DownloadTask.TAG, "Server returned HTTP " + httpURLConnection.getResponseCode() + " " + httpURLConnection.getResponseMessage());
                        SharedPreferences.Editor edit = Utils.this.preferenceManager.edit();
                        edit.putBoolean("CANCELLED", true);
                        edit.apply();
                        boolean unused = DownloadTask.this.checkIfCancelled = true;
                        Utils.this.retrying = true;
                        boolean unused2 = DownloadTask.this.downloadStarted = false;
                        return null;
                    }
                    if (DownloadTask.this.browsePath == null || DownloadTask.this.browsePath.equals("")) {
                        File file2 = new File(Environment.getExternalStorageDirectory(), AppConst.RECORDING_DIRECTORY);
                        if (!file2.exists()) {
                            file2.mkdirs();
                        }
                        DownloadTask downloadTask = DownloadTask.this;
                        String unused3 = downloadTask.browsePath = Environment.getExternalStorageDirectory() + "/" + AppConst.RECORDING_DIRECTORY;
                    }
                    if (DownloadTask.this.FileNameWithTime == null || DownloadTask.this.FileNameWithTime.equals("")) {
                        if (!DownloadTask.this.downloadFileName.contains(".ts")) {
                            DownloadTask downloadTask2 = DownloadTask.this;
                            String unused4 = downloadTask2.downloadFileName = DownloadTask.this.downloadFileName + ".ts";
                        }
                        file = new File(String.valueOf(DownloadTask.this.browsePath + "/" + DownloadTask.this.downloadFileName));
                        if (file.exists() && !Utils.this.retrying) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
                            Date date = new Date();
                            DownloadTask downloadTask3 = DownloadTask.this;
                            String unused5 = downloadTask3.FileNameWithTime = simpleDateFormat.format(date) + "_" + DownloadTask.this.downloadFileName;
                            file = new File(String.valueOf(DownloadTask.this.browsePath + "/" + DownloadTask.this.FileNameWithTime));
                        }
                    } else {
                        file = new File(String.valueOf(DownloadTask.this.browsePath + "/" + DownloadTask.this.FileNameWithTime));
                    }
                    this.outputFile = new FileOutputStream(file, true);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(this.outputFile);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                    byte[] bArr = new byte[1024];
                    long currentTimeMillis = (System.currentTimeMillis() / 1000) + ((long) DownloadTask.this.timeInSeconds);
                    boolean unused6 = DownloadTask.this.downloadStarted = true;
                    boolean unused7 = DownloadTask.this.checkIfCancelled = false;
                    while (true) {
                        int read = bufferedInputStream.read(bArr);
                        if (read != -1 && System.currentTimeMillis() / 1000 < currentTimeMillis) {
                            if (!DownloadTask.this.timeup) {
                                Utils.this.preferenceManager = PreferenceManager.getDefaultSharedPreferences(DownloadTask.this.context);
                                boolean unused8 = DownloadTask.this.checkIfCancelled = Utils.this.preferenceManager.getBoolean("CANCELLED", false);
                                if (DownloadTask.this.checkIfCancelled) {
                                    boolean unused9 = DownloadTask.this.checkIfCancelled = true;
                                    Utils.this.retryCount = 0;
                                    Utils.this.retrying = false;
                                    SharedPreferences.Editor edit2 = Utils.this.preferenceManager.edit();
                                    edit2.putBoolean("CANCELLED", true);
                                    edit2.apply();
                                    break;
                                }
                                Utils.this.retryCount = 0;
                                Utils.this.retrying = false;
                                bufferedOutputStream.write(bArr, 0, read);
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    bufferedOutputStream.close();
                    bufferedInputStream.close();
                    return null;
                } catch (Exception e) {
                    SharedPreferences.Editor edit3 = Utils.this.preferenceManager.edit();
                    edit3.putBoolean("CANCELLED", true);
                    edit3.apply();
                    boolean unused10 = DownloadTask.this.checkIfCancelled = true;
                    Utils.this.retrying = true;
                    boolean unused11 = DownloadTask.this.downloadStarted = false;
                    Log.e(DownloadTask.TAG, "Retrying " + e.getMessage());
                    return null;
                }
            }
        }
    }

    public void updateDownloadStatus(Context context, String str) {
        if (context != null) {
            loginPreferencesDownloadStatus = context.getSharedPreferences(AppConst.LOGIN_PREF_DOWNLOAD_STATUS, 0);
            SharedPreferences.Editor edit = loginPreferencesDownloadStatus.edit();
            edit.putString(AppConst.LOGIN_PREF_DOWNLOAD_STATUS, str);
            edit.apply();
        }
    }

    @SuppressLint({"DefaultLocale"})
    public String secondsToHoursFormat(int i) {
        return String.format("%02d:%02d:%02d", Integer.valueOf(i / DateTimeConstants.SECONDS_PER_HOUR), Integer.valueOf((i % DateTimeConstants.SECONDS_PER_HOUR) / 60), Integer.valueOf(i % 60));
    }

    public static File[] getRecordedFiles(Context context) {
        if (context == null) {
            return null;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConst.LOGIN_PREF_RECORDING_DIR, 0);
        return new File(sharedPreferences.getString(AppConst.LOGIN_PREF_RECORDING_DIR, Environment.getExternalStorageDirectory().toString() + "/" + AppConst.RECORDING_DIRECTORY)).listFiles();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RecordingActivity):void
     arg types: [android.widget.Button, com.nst.yourname.view.activity.RecordingActivity]
     candidates:
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.content.Context):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.CheckAppupdateActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.LoginActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.MultiUserActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.NewDashboardActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RateUsActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.SplashActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.VodActivityNewFlowSubCategories):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RecordingActivity):void */
    public void showDeleteRecordingPopUp(final RecordingActivity recordingActivity, final File file, final RecordingAdapter recordingAdapter, final ArrayList<File> arrayList, final TextView textView) {
        if (recordingActivity != null) {
            View inflate = ((LayoutInflater) recordingActivity.getSystemService("layout_inflater")).inflate((int) R.layout.delete_recording_popup, (RelativeLayout) recordingActivity.findViewById(R.id.rl_password_verification));
            changeSortPopUp = new PopupWindow(recordingActivity);
            changeSortPopUp.setContentView(inflate);
            changeSortPopUp.setWidth(-1);
            changeSortPopUp.setHeight(-1);
            changeSortPopUp.setFocusable(true);
            changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
            Button button = (Button) inflate.findViewById(R.id.bt_start_recording);
            Button button2 = (Button) inflate.findViewById(R.id.bt_close);
            if (button != null) {
                button.setOnFocusChangeListener(new OnFocusChangeAccountListener((View) button, recordingActivity));
                button.requestFocus();
                button.requestFocusFromTouch();
            }
            if (button2 != null) {
                button2.setOnFocusChangeListener(new OnFocusChangeAccountListener((View) button2, recordingActivity));
            }
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.miscelleneious.common.Utils.AnonymousClass7 */

                public void onClick(View view) {
                    Utils.changeSortPopUp.dismiss();
                }
            });
            if (button != null) {
                button.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.miscelleneious.common.Utils.AnonymousClass8 */

                    public void onClick(View view) {
                        if (file.exists() && file.delete()) {
                            Toast.makeText(recordingActivity, recordingActivity.getResources().getString(R.string.recording_deleted), 0).show();
                            File[] recordedFiles = Utils.getRecordedFiles(recordingActivity);
                            if (recordedFiles == null || recordedFiles.length <= 0) {
                                arrayList.clear();
                                recordingAdapter.notifyDataSetChanged();
                                textView.setVisibility(0);
                            } else {
                                arrayList.clear();
                                for (File file : recordedFiles) {
                                    if (file.toString().endsWith(".ts")) {
                                        arrayList.addAll(Arrays.asList(file));
                                    }
                                }
                                if (arrayList == null || arrayList.size() <= 0) {
                                    arrayList.clear();
                                    recordingAdapter.notifyDataSetChanged();
                                    textView.setVisibility(0);
                                } else {
                                    recordingAdapter.notifyDataSetChanged();
                                }
                            }
                            new Handler().postDelayed(new Runnable() {
                                /* class com.nst.yourname.miscelleneious.common.Utils.AnonymousClass8.AnonymousClass1 */

                                public void run() {
                                    Utils.changeSortPopUp.dismiss();
                                }
                            }, 500);
                        }
                    }
                });
            }
        }
    }

    public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        String[] split = url.getQuery().split("&");
        for (String str : split) {
            int indexOf = str.indexOf("=");
            linkedHashMap.put(URLDecoder.decode(str.substring(0, indexOf), "UTF-8"), URLDecoder.decode(str.substring(indexOf + 1), "UTF-8"));
        }
        return linkedHashMap;
    }

    public static int parseIntZero(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    public static int parseIntMinusOne(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
     arg types: [android.widget.Button, android.app.Activity]
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
    public static void showDownloadingPopUP(final Activity activity, final Context context, String str, final String str2, final String str3) {
        View inflate = ((LayoutInflater) activity.getSystemService("layout_inflater")).inflate((int) R.layout.recording_popup, (RelativeLayout) activity.findViewById(R.id.rl_password_verification));
        changeSortPopUp = new PopupWindow(activity);
        changeSortPopUp.setContentView(inflate);
        changeSortPopUp.setWidth(-1);
        changeSortPopUp.setHeight(-1);
        changeSortPopUp.setFocusable(true);
        String str4 = "";
        if (new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()).exists()) {
            str4 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        }
        Button button = (Button) inflate.findViewById(R.id.bt_start_recording);
        button.setText("Download");
        Button button2 = (Button) inflate.findViewById(R.id.bt_close);
        final EditText editText = (EditText) inflate.findViewById(R.id.et_file_name);
        editText.setText(str);
        ((EditText) inflate.findViewById(R.id.et_duration)).setVisibility(8);
        final Button button3 = (Button) inflate.findViewById(R.id.bt_browse);
        ((TextView) inflate.findViewById(R.id.tv_parental_password)).setText(AppConst.Download_Directory);
        final EditText editText2 = (EditText) inflate.findViewById(R.id.et_browse);
        if (button != null) {
            button.setOnFocusChangeListener(new OnFocusChangeAccountListener((View) button, activity));
        }
        if (button2 != null) {
            button2.setOnFocusChangeListener(new OnFocusChangeAccountListener((View) button2, activity));
        }
        if (button3 != null) {
            button3.setOnFocusChangeListener(new OnFocusChangeAccountListener((View) button3, activity));
        }
        editText.requestFocus();
        editText2.setEnabled(false);
        File file = new File(Environment.getExternalStorageDirectory(), AppConst.RECORDING_DIRECTORY);
        if (file.exists()) {
            str4 = file.getAbsolutePath();
            File file2 = new File(Environment.getExternalStorageDirectory() + "/" + AppConst.RECORDING_DIRECTORY, AppConst.Download_Directory);
            if (file2.exists()) {
                str4 = file2.getAbsolutePath();
            } else if (file2.mkdirs()) {
                str4 = file2.getAbsolutePath();
            }
        } else if (file.mkdirs()) {
            str4 = file.getAbsolutePath();
            File file3 = new File(Environment.getExternalStorageDirectory() + "/" + AppConst.RECORDING_DIRECTORY, AppConst.Download_Directory);
            if (!file3.exists() && file3.mkdirs()) {
                str4 = file3.getAbsolutePath();
            }
        }
        final String str5 = str4;
        SharepreferenceDBHandler.setDownloadingPath(str5, context);
        editText2.setText(str5);
        editText2.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.miscelleneious.common.Utils.AnonymousClass9 */
            static final boolean $assertionsDisabled = false;

            public void onClick(View view) {
                button3.performClick();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.miscelleneious.common.Utils.AnonymousClass10 */

            public void onClick(View view) {
                Utils.changeSortPopUp.dismiss();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.miscelleneious.common.Utils.AnonymousClass11 */
            public String m_chosenDir = "";
            private boolean m_newFolderEnabled = true;

            public void onClick(View view) {
                DownloadDirectoryChooserDialog downloadDirectoryChooserDialog = new DownloadDirectoryChooserDialog(activity, new DownloadDirectoryChooserDialog.ChosenDirectoryListener() {
                    /* class com.nst.yourname.miscelleneious.common.Utils.AnonymousClass11.AnonymousClass1 */

                    @Override // com.nst.yourname.miscelleneious.DownloadDirectoryChooserDialog.ChosenDirectoryListener
                    public void onChosenDir(String str) {
                        m_chosenDir = str;
                        editText2.setText(str);
                        SharepreferenceDBHandler.setDownloadingPath(m_chosenDir, context);
                        //Activity activity = activity;
                        Toast.makeText(activity, "Chosen directory: " + str, 1).show();
                    }
                });
                downloadDirectoryChooserDialog.setNewFolderEnabled(this.m_newFolderEnabled);
                downloadDirectoryChooserDialog.chooseDirectory("");
                this.m_newFolderEnabled = !this.m_newFolderEnabled;
            }
        });
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.miscelleneious.common.Utils.AnonymousClass12 */
                static final boolean $assertionsDisabled = false;

                public void onClick(View view) {
                    if (fieldsCheck()) {
                        String string = activity.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).getString(AppConst.LOGIN_PREF_SERVER_URL, "");
                        if (!string.startsWith("http://") && !string.startsWith("https://")) {
                            String str = "http://" + string;
                        }
                        try {
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str2));
                            request.setNotificationVisibility(1);
                            request.setDestinationInExternalPublicDir(str5, editText.getText().toString() + "." + str3);
                            ((DownloadManager) activity.getSystemService("download")).enqueue(request);
                        } catch (Exception unused) {
                            Toast.makeText(context, "Error : Sorry we can't Download this video", 0).show();
                        }
                        new Handler().postDelayed(new Runnable() {
                            /* class com.nst.yourname.miscelleneious.common.Utils.AnonymousClass12.AnonymousClass1 */

                            public void run() {
                                Utils.changeSortPopUp.dismiss();
                            }
                        }, 500);
                    }
                }

                private boolean fieldsCheck() {
                    if (editText.getText().toString().trim().length() != 0) {
                        return true;
                    }
                    Toast.makeText(context, context.getResources().getString(R.string.input_filename), 1).show();
                    return false;
                }
            });
        }
        changeSortPopUp.showAtLocation(inflate, 17, 0, 0);
    }

    public static void showProgressDialog(Activity activity) {
        progressDialog = new Dialog(activity);
        progressDialog.setContentView((int) R.layout.spinkitanimation);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        progressDialog.getWindow().setGravity(17);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x006d, code lost:
        if (r4.equals(com.nst.yourname.miscelleneious.common.AppConst.HTTP) != false) goto L_0x0071;
     */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00ed  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0143  */
    public static String getUrl(Context context, int i, String str, String str2) {
        char c = 0;
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        String string = sharedPreferences.getString("username", "");
        String string2 = sharedPreferences.getString("password", "");
        String string3 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_URL, "");
        String string4 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_PROTOCOL, "");
        String string5 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_HTTPS_PORT, "");
        String string6 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_PORT, "");
        String string7 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_RTMP_PORT, "");
        int hashCode = string4.hashCode();
        if (hashCode != 3213448) {
            if (hashCode != 3504631) {
                if (hashCode == 99617003 && string4.equals(AppConst.HTTPS)) {
                    c = 1;
                    switch (c) {
                        case 0:
                            if (!string3.startsWith("http://")) {
                                string3 = "http://" + string3;
                            }
                            return string3 + ":" + string6 + "/" + str2 + "/" + string + "/" + string2 + "/" + i + "." + str;
                        case 1:
                            if (!string3.startsWith("https://")) {
                                string3 = "https://" + string3;
                            }
                            return string3 + ":" + string5 + "/" + str2 + "/" + string + "/" + string2 + "/" + i + "." + str;
                        case 2:
                            if (!string3.startsWith("rmtp://")) {
                                string3 = "rmtp://" + string3;
                            }
                            return string3 + ":" + string7 + "/" + str2 + "/" + string + "/" + string2 + "/" + i + "." + str;
                        default:
                            if (!string3.startsWith("http://") && !string3.startsWith("https://")) {
                                string3 = "http://" + string3;
                            }
                            return string3 + ":" + string6 + "/" + str2 + "/" + string + "/" + string2 + "/" + i + "." + str;
                    }
                }
            } else if (string4.equals(AppConst.RMTP)) {
                c = 2;
                switch (c) {
                }
            }
        }
        c = 65535;
        switch (c) {
        }
        return string;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x006d, code lost:
        if (r4.equals(com.nst.yourname.miscelleneious.common.AppConst.HTTP) != false) goto L_0x0071;
     */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0120  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x01a9  */
    public static String getUrlLive(Context context, int i, String str, String str2) {
        char c = 0;
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        String string = sharedPreferences.getString("username", "");
        String string2 = sharedPreferences.getString("password", "");
        String string3 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_URL, "");
        String string4 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_PROTOCOL, "");
        String string5 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_HTTPS_PORT, "");
        String string6 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_PORT, "");
        String string7 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_RTMP_PORT, "");
        int hashCode = string4.hashCode();
        if (hashCode != 3213448) {
            if (hashCode != 3504631) {
                if (hashCode == 99617003 && string4.equals(AppConst.HTTPS)) {
                    c = 1;
                    switch (c) {
                        case 0:
                            if (!string3.startsWith("http://")) {
                                string3 = "http://" + string3;
                            }
                            if (str.equals("")) {
                                return string3 + ":" + string6 + "/" + string + "/" + string2 + "/" + i + str;
                            }
                            return string3 + ":" + string6 + "/" + str2 + "/" + string + "/" + string2 + "/" + i + str;
                        case 1:
                            if (!string3.startsWith("https://")) {
                                string3 = "https://" + string3;
                            }
                            if (str.equals("")) {
                                return string3 + ":" + string5 + "/" + string + "/" + string2 + "/" + i + str;
                            }
                            return string3 + ":" + string5 + "/" + str2 + "/" + string + "/" + string2 + "/" + i + str;
                        case 2:
                            if (!string3.startsWith("rmtp://")) {
                                string3 = "rmtp://" + string3;
                            }
                            if (str.equals("")) {
                                return string3 + ":" + string7 + "/" + string + "/" + string2 + "/" + i + str;
                            }
                            return string3 + ":" + string7 + "/" + str2 + "/" + string + "/" + string2 + "/" + i + str;
                        default:
                            if (!string3.startsWith("http://") && !string3.startsWith("https://")) {
                                string3 = "http://" + string3;
                            }
                            if (str.equals("")) {
                                return string3 + ":" + string6 + "/" + string + "/" + string2 + "/" + i + str;
                            }
                            return string3 + ":" + string6 + "/" + str2 + "/" + string + "/" + string2 + "/" + i + str;
                    }
                }
            } else if (string4.equals(AppConst.RMTP)) {
                c = 2;
                switch (c) {
                }
            }
        }
        c = 65535;
        switch (c) {
        }
        return string;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0086, code lost:
        if (r5.equals(com.nst.yourname.miscelleneious.common.AppConst.HTTP) != false) goto L_0x008a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x010e  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x016c  */
    public static String getTimeshiftUrl(Context context, int i, String str, String str2) {
        char c = 0;
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConst.LOGIN_PREF_ALLOWED_FORMAT, 0);
        SharedPreferences sharedPreferences2 = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        String string = sharedPreferences2.getString("username", "");
        String string2 = sharedPreferences2.getString("password", "");
        String string3 = sharedPreferences.getString(AppConst.LOGIN_PREF_ALLOWED_FORMAT, "");
        String string4 = sharedPreferences2.getString(AppConst.LOGIN_PREF_SERVER_URL, "");
        String string5 = sharedPreferences2.getString(AppConst.LOGIN_PREF_SERVER_PROTOCOL, "");
        String string6 = sharedPreferences2.getString(AppConst.LOGIN_PREF_SERVER_HTTPS_PORT, "");
        String string7 = sharedPreferences2.getString(AppConst.LOGIN_PREF_SERVER_PORT, "");
        String string8 = sharedPreferences2.getString(AppConst.LOGIN_PREF_SERVER_RTMP_PORT, "");
        if (string3.equals("default")) {
            string3 = "ts";
        }
        int hashCode = string5.hashCode();
        if (hashCode != 3213448) {
            if (hashCode != 3504631) {
                if (hashCode == 99617003 && string5.equals(AppConst.HTTPS)) {
                    c = 1;
                    switch (c) {
                        case 0:
                            if (!string4.startsWith("http://")) {
                                string4 = "http://" + string4;
                            }
                            return string4 + ":" + string7 + "/timeshift/" + string + "/" + string2 + "/" + str2 + "/" + str + "/" + i + "." + string3;
                        case 1:
                            if (!string4.startsWith("https://")) {
                                string4 = "https://" + string4;
                            }
                            return string4 + ":" + string6 + "/timeshift/" + string + "/" + string2 + "/" + str2 + "/" + str + "/" + i + "." + string3;
                        case 2:
                            if (!string4.startsWith("rmtp://")) {
                                string4 = "rmtp://" + string4;
                            }
                            return string4 + ":" + string8 + "/timeshift/" + string + "/" + string2 + "/" + str2 + "/" + str + "/" + i + "." + string3;
                        default:
                            if (!string4.startsWith("http://") && !string4.startsWith("https://")) {
                                string4 = "http://" + string4;
                            }
                            return string4 + ":" + string7 + "/timeshift/" + string + "/" + string2 + "/" + str2 + "/" + str + "/" + i + "." + string3;
                    }
                }
            } else if (string5.equals(AppConst.RMTP)) {
                c = 2;
                switch (c) {
                }
            }
        }
        c = 65535;
        switch (c) {
        }
        return string;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x006b, code lost:
        if (r2.equals(com.nst.yourname.miscelleneious.common.AppConst.HTTP) != false) goto L_0x006f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00fb  */
    public static String getUrlepg(Context context) {
        char c = 0;
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0);
        sharedPreferences.getString("username", "");
        sharedPreferences.getString("password", "");
        String string = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_URL, "");
        String string2 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_PROTOCOL, "");
        String string3 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_HTTPS_PORT, "");
        String string4 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_PORT, "");
        String string5 = sharedPreferences.getString(AppConst.LOGIN_PREF_SERVER_RTMP_PORT, "");
        int hashCode = string2.hashCode();
        if (hashCode != 3213448) {
            if (hashCode != 3504631) {
                if (hashCode == 99617003 && string2.equals(AppConst.HTTPS)) {
                    c = 1;
                    switch (c) {
                        case 0:
                            if (!string.startsWith("http://")) {
                                string = "http://" + string;
                            }
                            return string + ":" + string4 + "/";
                        case 1:
                            if (!string.startsWith("https://")) {
                                string = "https://" + string;
                            }
                            return string + ":" + string3 + "/";
                        case 2:
                            if (!string.startsWith("rmtp://")) {
                                string = "rmtp://" + string;
                            }
                            return string + ":" + string5 + "/";
                        default:
                            if (!string.startsWith("http://") && !string.startsWith("https://")) {
                                string = "http://" + string;
                            }
                            return string + ":" + string4 + "/";
                    }
                }
            } else if (string2.equals(AppConst.RMTP)) {
                c = 2;
                switch (c) {
                }
            }
        }
        c = 65535;
        switch (c) {
        }
        return string;
    }

    public static void Redrirect_to_Home(Context context) {
        context.startActivity(new Intent(context, NewDashboardActivity.class));
    }

    public static Retrofit getActivationRetrofit(Context context) {
        return new Retrofit.Builder().baseUrl("http://tvplushd.com/billing/").client(new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).followRedirects(false).build()).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static void playWithMultiPlayer(Context context, int i, String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        mSettings = new Settings(context);
        Intent intent = new Intent(context, NSTIJKPlayerEPGActivity.class);
        intent.putExtra("MultiPlayer", "true");
        intent.putExtra("OPENED_STREAM_ID", i);
        intent.putExtra("STREAM_TYPE", str);
        intent.putExtra("VIDEO_NUM", !str2.equals("") ? Integer.parseInt(str2) : -1);
        intent.putExtra("VIDEO_TITLE", str3);
        intent.putExtra("EPG_CHANNEL_ID", str4);
        intent.putExtra("EPG_CHANNEL_LOGO", str5);
        intent.putExtra("OPENED_CAT_ID", str6);
        intent.putExtra("VIDEO_URL", str7);
        context.startActivity(intent);
    }

    public static void setXtream1_06(Context context) {
        loginPrefXtream = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IS_XTREAM_1_06, 0);
        loginPrefXtreamEditor = loginPrefXtream.edit();
        loginPrefXtreamEditor.putBoolean(AppConst.IS_XTREAM_1_06, AppConst.isXTREAM_1_0_6.booleanValue());
        loginPrefXtreamEditor.apply();
    }

    public static boolean getIsXtream1_06(Context context) {
        loginPrefXtream = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_IS_XTREAM_1_06, 0);
        loginPrefXtream.getBoolean(AppConst.IS_XTREAM_1_06, false);
        return false;
    }
}
