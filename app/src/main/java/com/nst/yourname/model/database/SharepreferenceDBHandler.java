package com.nst.yourname.model.database;

import android.content.Context;
import android.content.SharedPreferences;
import com.nst.yourname.miscelleneious.common.AppConst;

public class SharepreferenceDBHandler {
    public static void setUserID(int i, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0).edit();
        edit.putInt("userID", i);
        edit.apply();
    }

    public static int getUserID(Context context) {
        return context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0).getInt("userID", -1);
    }

    public static void setTooltip(int i, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0).edit();
        edit.putInt("tooltip", i);
        edit.apply();
    }

    public static int getTooltip(Context context) {
        return context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0).getInt("tooltip", 0);
    }

    public static void setType(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0).edit();
        edit.putString("type", str);
        edit.apply();
    }

    public static String getType(Context context) {
        return context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0).getString("type", "");
    }

    public static void setCurrentAPPType(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0).edit();
        edit.putString("current_app_type", str);
        edit.apply();
    }

    public static String getCurrentAPPType(Context context) {
        return context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0).getString("current_app_type", AppConst.TYPE_API);
    }

    public static void setAsyncTaskStatus(int i, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0).edit();
        edit.putInt("async_status", i);
        edit.apply();
    }

    public static int getAsyncTaskStatus(Context context) {
        return context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0).getInt("async_status", 0);
    }

    public static void setDontaskagain(boolean z, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("SplashAppUpdate", 0).edit();
        edit.putBoolean("Dontask", z);
        edit.apply();
    }

    public static boolean getDontaskagain(Context context) {
        return context.getSharedPreferences("SplashAppUpdate", 0).getBoolean("Dontask", false);
    }

    public static void setRateUsDontaskagain(boolean z, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("SplashAppUpdate", 0).edit();
        edit.putBoolean("RateUsDontask", z);
        edit.apply();
    }

    public static boolean getRateUsDontaskagain(Context context) {
        return context.getSharedPreferences("SplashAppUpdate", 0).getBoolean("RateUsDontask", false);
    }

    public static void setRateUsCount(int i, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("SplashAppUpdate", 0).edit();
        edit.putInt("RateUsCount", i);
        edit.apply();
    }

    public static int getRateUsCount(Context context) {
        return context.getSharedPreferences("SplashAppUpdate", 0).getInt("RateUsCount", 0);
    }

    public static void setIsupdate(boolean z, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("SplashAppUpdate", 0).edit();
        edit.putBoolean("Update", z);
        edit.apply();
    }

    public static boolean getIsupdate(Context context) {
        return context.getSharedPreferences("SplashAppUpdate", 0).getBoolean("Update", false);
    }

    public static void setSeriesSubCatSort(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.Sorting, 0).edit();
        edit.putString("SeriesSort", str);
        edit.apply();
    }

    public static String getSeriesSubCatSort(Context context) {
        return context.getSharedPreferences(AppConst.Sorting, 0).getString("SeriesSort", AppConst.PASSWORD_UNSET);
    }

    public static void setLiveSubCatSort(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.Sorting, 0).edit();
        edit.putString("LiveSort", str);
        edit.apply();
    }

    public static String getLiveSubCatSort(Context context) {
        return context.getSharedPreferences(AppConst.Sorting, 0).getString("LiveSort", AppConst.PASSWORD_UNSET);
    }

    public static void setVODSubCatSort(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.Sorting, 0).edit();
        edit.putString("VODSort", str);
        edit.apply();
    }

    public static String getVODSubCatSort(Context context) {
        return context.getSharedPreferences(AppConst.Sorting, 0).getString("VODSort", AppConst.PASSWORD_UNSET);
    }

    public static void setPlaylistSort(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.Sorting, 0).edit();
        edit.putString("PlaylistSort", str);
        edit.apply();
    }

    public static String getPlaylistSort(Context context) {
        return context.getSharedPreferences(AppConst.Sorting, 0).getString("PlaylistSort", AppConst.PASSWORD_UNSET);
    }

    public static void setSelectedLanguage(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("LanguageSelectionPref", 0).edit();
        edit.putString("SelectedLanguage", str);
        edit.apply();
    }

    public static String getSelectedLanguage(Context context) {
        return context.getSharedPreferences("LanguageSelectionPref", 0).getString("SelectedLanguage", "English");
    }

    public static void setIsAppExistOnPlayStore(boolean z, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("MyPref", 0).edit();
        edit.putBoolean("AppExist", z);
        edit.apply();
    }

    public static boolean getIsAppExistOnPlayStore(Context context) {
        return context.getSharedPreferences("MyPref", 0).getBoolean("AppExist", false);
    }

    public static void setLiveActivitynewFlowSort(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.Sorting, 0).edit();
        edit.putString("livecatsort", str);
        edit.apply();
    }

    public static String getLiveActivitynewFlowSort(Context context) {
        return context.getSharedPreferences(AppConst.Sorting, 0).getString("livecatsort", AppConst.PASSWORD_UNSET);
    }

    public static void setPlayListCategorySort(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.Sorting, 0).edit();
        edit.putString("Playlistcatsort", str);
        edit.apply();
    }

    public static String getPlayListCategorySort(Context context) {
        return context.getSharedPreferences(AppConst.Sorting, 0).getString("Playlistcatsort", AppConst.PASSWORD_UNSET);
    }

    public static void setVodActivitynewFlowSort(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.Sorting, 0).edit();
        edit.putString("vodcatsort", str);
        edit.apply();
    }

    public static String getVodActivitynewFlowSort(Context context) {
        return context.getSharedPreferences(AppConst.Sorting, 0).getString("vodcatsort", AppConst.PASSWORD_UNSET);
    }

    public static void setseriesActivitynewFlowSort(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.Sorting, 0).edit();
        edit.putString("seriescatsort", str);
        edit.apply();
    }

    public static String getseriesActivitynewFlowSort(Context context) {
        return context.getSharedPreferences(AppConst.Sorting, 0).getString("seriescatsort", AppConst.PASSWORD_UNSET);
    }

    public static void setDownloadingPath(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("MyPref", 0).edit();
        edit.putString("downloadpath", str);
        edit.apply();
    }

    public static String getDownloadingPath(Context context) {
        return context.getSharedPreferences("MyPref", 0).getString("downloadpath", AppConst.PASSWORD_UNSET);
    }

    public static String getUserName(Context context) {
        return context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).getString("username", "");
    }

    public static void setUserName(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).edit();
        edit.putString("username", str);
        edit.apply();
    }

    public static void setUserPassword(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).edit();
        edit.putString("password", str);
        edit.apply();
    }

    public static String getUserPassword(Context context) {
        return context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).getString("password", "");
    }

    public static void setServerUrl(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).edit();
        edit.putString(AppConst.LOGIN_PREF_SERVER_URL, str);
        edit.apply();
    }

    public static String getServerUrl(Context context) {
        return context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).getString(AppConst.LOGIN_PREF_SERVER_URL, "");
    }

    public static void setFreeTrailStatus(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("MyPref", 0).edit();
        edit.putString("FreeTrailStatus", str);
        edit.apply();
    }

    public static String getFreeTrailStatus(Context context) {
        return context.getSharedPreferences("MyPref", 0).getString("FreeTrailStatus", AppConst.PASSWORD_UNSET);
    }

    public static void setLivePlayer(String str, String str2, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("MyPrefPlayer", 0).edit();
        edit.putString("LivePkgName", str);
        edit.putString("LiveAppName", str2);
        edit.apply();
    }

    public static String getLivePlayerPkgName(Context context) {
        return context.getSharedPreferences("MyPrefPlayer", 0).getString("LivePkgName", "default");
    }

    public static String getLivePlayerAppName(Context context) {
        return context.getSharedPreferences("MyPrefPlayer", 0).getString("LiveAppName", "");
    }

    public static void setVODPlayer(String str, String str2, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("MyPrefPlayer", 0).edit();
        edit.putString("VODPkgName", str);
        edit.putString("VODAppName", str2);
        edit.apply();
    }

    public static String getVODPlayerPkgName(Context context) {
        return context.getSharedPreferences("MyPrefPlayer", 0).getString("VODPkgName", "default");
    }

    public static String getVODPlayerAppName(Context context) {
        return context.getSharedPreferences("MyPrefPlayer", 0).getString("VODAppName", "");
    }

    public static void setSeriesPlayer(String str, String str2, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("MyPrefPlayer", 0).edit();
        edit.putString("SeriesPkgName", str);
        edit.putString("SeriesAppName", str2);
        edit.apply();
    }

    public static String getSeriesPlayerPkgName(Context context) {
        return context.getSharedPreferences("MyPrefPlayer", 0).getString("SeriesPkgName", "default");
    }

    public static String getSeriesPlayerAppName(Context context) {
        return context.getSharedPreferences("MyPrefPlayer", 0).getString("SeriesAppName", "");
    }

    public static void setCatchUpPlayer(String str, String str2, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("MyPrefPlayer", 0).edit();
        edit.putString("CatchUpPkgName", str);
        edit.putString("CatchUpAppName", str2);
        edit.apply();
    }

    public static String getCatchUpPlayerPkgName(Context context) {
        return context.getSharedPreferences("MyPrefPlayer", 0).getString("CatchUpPkgName", "default");
    }

    public static String getCatchUpPlayerAppName(Context context) {
        return context.getSharedPreferences("MyPrefPlayer", 0).getString("CatchUpAppName", "");
    }

    public static void setRecordingsPlayer(String str, String str2, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("MyPrefPlayer", 0).edit();
        edit.putString("RecordingsPkgName", str);
        edit.putString("RecordingsAppName", str2);
        edit.apply();
    }

    public static String getRecordingsPlayerPkgName(Context context) {
        return context.getSharedPreferences("MyPrefPlayer", 0).getString("RecordingsPkgName", "default");
    }

    public static String getRecordingsPlayerAppName(Context context) {
        return context.getSharedPreferences("MyPrefPlayer", 0).getString("RecordingsAppName", "");
    }

    public static void setEPGPlayer(String str, String str2, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("MyPrefPlayer", 0).edit();
        edit.putString("EPGPkgName", str);
        edit.putString("EPGAppName", str2);
        edit.apply();
    }

    public static String getEPGPlayerPKGName(Context context) {
        return context.getSharedPreferences("MyPrefPlayer", 0).getString("EPGPkgName", "default");
    }

    public static String getEPGPlayerAppName(Context context) {
        return context.getSharedPreferences("MyPrefPlayer", 0).getString("EPGAppName", "");
    }

    public static void setDemoAppMessageShow(boolean z, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("MyPref", 0).edit();
        edit.putBoolean("AppExist", z);
        edit.apply();
    }

    public static boolean getDemoAppMessageShow(Context context) {
        return context.getSharedPreferences("MyPref", 0).getBoolean("isShow", false);
    }

    public static void setisPlayerReleasedSmall(boolean z, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("isPlayerReleasedSmallPref", 0).edit();
        edit.putBoolean("isPlayerReleasedSmall", z);
        edit.apply();
    }

    public static boolean getisPlayerReleasedSmall(Context context) {
        return context.getSharedPreferences("isPlayerReleasedSmallPref", 0).getBoolean("isPlayerReleasedSmall", false);
    }

    public static void setisPlayerReleasedLarge(boolean z, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("isPlayerReleasedLargePref", 0).edit();
        edit.putBoolean("isPlayerReleasedLarge", z);
        edit.apply();
    }

    public static boolean getisPlayerReleasedLarge(Context context) {
        return context.getSharedPreferences("isPlayerReleasedLargePref", 0).getBoolean("isPlayerReleasedLarge", false);
    }

    public static void setisAutoPlayVideos(boolean z, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.LOGIN_PREF_AUTO_START, 0).edit();
        edit.putBoolean(AppConst.LOGIN_PREF_AUTOPLAY_VIDEOS, z);
        edit.apply();
    }

    public static boolean getisAutoPlayVideos(Context context) {
        return context.getSharedPreferences(AppConst.LOGIN_PREF_AUTO_START, 0).getBoolean(AppConst.LOGIN_PREF_AUTOPLAY_VIDEOS, true);
    }

    public static void setActivationCode(Context context, String str) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.LOGIN_PREF_AUTO_START, 0).edit();
        edit.putString("act_code", str);
        edit.apply();
    }

    public static String getActCode(Context context) {
        return context.getSharedPreferences(AppConst.LOGIN_PREF_AUTO_START, 0).getString("act_code", "");
    }

    public static String getTrial(Context context) {
        return context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).getString(AppConst.LOGIN_PREF_IS_TRIAL, "");
    }

    public static void setTrial(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE, 0).edit();
        edit.putString(AppConst.LOGIN_PREF_IS_TRIAL, str);
        edit.apply();
    }

    public static void setScreenTYPE(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0).edit();
        edit.putString("screenType", str);
        edit.apply();
    }

    public static String getScreenTYPE(Context context) {
        return context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_MULTIUSER, 0).getString("screenType", "default");
    }

    public static boolean getShowPopup(Context context) {
        return context.getSharedPreferences("showPopup", 0).getBoolean("popshow", true);
    }

    public static void setShowPop(Boolean bool, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("showPopup", 0).edit();
        edit.putBoolean("popshow", bool.booleanValue());
        edit.apply();
    }

    public static void setValueChecked(Boolean bool, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("showPopup", 0).edit();
        edit.putBoolean("check_box", bool.booleanValue());
        edit.apply();
    }

    public static boolean isChecked(Context context) {
        return context.getSharedPreferences("showPopup", 0).getBoolean("check_box", true);
    }
}
