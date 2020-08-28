package com.nst.yourname.WHMCSClientapp.Clientdatabase;

import android.content.Context;
import android.content.SharedPreferences;
import com.nst.yourname.miscelleneious.common.AppConst;
import org.joda.time.DateTimeConstants;

public class ClientSharepreferenceHandler {
    public static void setClientId(int i, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.SHARED_PREFERENCE, 0).edit();
        edit.putInt("client_id", i);
        edit.apply();
    }

    public static int getClientId(Context context) {
        return context.getSharedPreferences(AppConst.SHARED_PREFERENCE, 0).getInt("client_id", -1);
    }

    public static void setUserEmaiId(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.SHARED_PREFERENCE, 0).edit();
        edit.putString("email", str);
        edit.apply();
    }

    public static String getUserEmaiId(Context context) {
        return context.getSharedPreferences(AppConst.SHARED_PREFERENCE, 0).getString("email", "abc@gmail.com");
    }

    public static void setUserPrefix(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.SHARED_PREFERENCE, 0).edit();
        edit.putString("prefix", str);
        edit.apply();
    }

    public static String getUserPrefix(Context context) {
        return context.getSharedPreferences(AppConst.SHARED_PREFERENCE, 0).getString("prefix", "$");
    }

    public static void setUserSuffix(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.SHARED_PREFERENCE, 0).edit();
        edit.putString("Suffix", str);
        edit.apply();
    }

    public static String getUserSuffix(Context context) {
        return context.getSharedPreferences(AppConst.SHARED_PREFERENCE, 0).getString("Suffix", "USD");
    }

    public static void setFreeTrialTime(int i, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.SHARED_PREFERENCE, 0).edit();
        edit.putInt("freetrailtime", i);
        edit.apply();
    }

    public static int getFreeTrialTime(Context context) {
        return context.getSharedPreferences(AppConst.SHARED_PREFERENCE, 0).getInt("freetrailtime", DateTimeConstants.SECONDS_PER_DAY);
    }

    public static void setIsFreeTrail(String str, Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(AppConst.SHARED_PREFERENCE, 0).edit();
        edit.putString("isfreetrial", str);
        edit.apply();
    }

    public static String getIsFreeTrail(Context context) {
        return context.getSharedPreferences(AppConst.SHARED_PREFERENCE, 0).getString("isfreetrial", "");
    }
}
