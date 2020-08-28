package com.nst.yourname.WebServiceHandler;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v17.preference.LeanbackPreferenceDialogFragment;
import com.nst.yourname.miscelleneious.common.AppConst;

public class RavSharedPrefrences {
    static Boolean boolValue = false;
    static SharedPreferences.Editor editor = null;
    static SharedPreferences preferences = null;
    static String value = "";

    public static void set_authurl(Context context, String str) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putString("url", str);
        editor.commit();
    }

    public static String get_authurl(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        value = preferences.getString("url", "");
        return value.toLowerCase();
    }

    public static void set_salt(Context context, String str) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putString("salt", str);
        editor.commit();
    }

    public static String get_salt(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        value = preferences.getString("salt", "");
        return value;
    }

    public static void set_clientkey(Context context, String str) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putString(LeanbackPreferenceDialogFragment.ARG_KEY, str);
        editor.commit();
    }

    public static String get_clientkey(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        value = preferences.getString(LeanbackPreferenceDialogFragment.ARG_KEY, "");
        return value;
    }

    public static void set_clientNotificationkey(Context context, String str) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putString("notificationkey", str);
        editor.commit();
    }

    public static String get_clientNotificationkey(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        value = preferences.getString("notificationkey", "");
        return value;
    }

    public static void set_user_email(Context context, String str) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putString("user_email", str);
        editor.commit();
    }

    public static String get_user_email(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        value = preferences.getString("user_email", "");
        return value;
    }

    public static void set_user_pass(Context context, String str) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putString("user_pass", str);
        editor.commit();
    }

    public static String get_user_pass(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        value = preferences.getString("user_pass", "");
        return value;
    }

    public static void set_user_type(Context context, String str) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putString("user_type", str);
        editor.commit();
    }

    public static String get_user_type(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        value = preferences.getString("user_type", "");
        return value;
    }

    public static void set_user_token(Context context, String str) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putString("user_token", str);
        editor.commit();
    }

    public static String get_user_token(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        value = preferences.getString("user_token", "");
        return value;
    }

    public static void set_loginbool(Context context, Boolean bool) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putBoolean("loginbool", bool.booleanValue());
        editor.commit();
    }

    public static Boolean get_loginbool(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolValue = Boolean.valueOf(preferences.getBoolean("loginbool", false));
        return boolValue;
    }

    public static void set_rememberbool(Context context, Boolean bool) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putBoolean("rememberbool", bool.booleanValue());
        editor.commit();
    }

    public static Boolean get_rememberbool(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolValue = Boolean.valueOf(preferences.getBoolean("rememberbool", false));
        return boolValue;
    }

    public static void set_fbconnect(Context context, Boolean bool) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putBoolean("fbconnect", bool.booleanValue());
        editor.commit();
    }

    public static Boolean get_fbconnect(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolValue = Boolean.valueOf(preferences.getBoolean("fbconnect", false));
        return boolValue;
    }

    public static void set_gcmid(Context context, String str) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putString("gcmid", str);
        editor.commit();
    }

    public static String get_gcmid(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("gcmid", AppConst.PASSWORD_UNSET);
    }

    public static void set_logindata(Context context, String str) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putString("logind", str);
        editor.commit();
    }

    public static String get_logindata(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("logind", "");
    }

    public static void set_notifydata(Context context, String str) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putString("notifydata", str);
        editor.commit();
    }

    public static String get_notifydata(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("notifydata", "");
    }

    public static void set_phone(Context context, String str) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putString("lat", str);
        editor.commit();
    }

    public static String get_phone(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("lat", "");
    }

    public static void set_current_long(Context context, String str) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putString("long", str);
        editor.commit();
    }

    public static String get_current_long(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("long", AppConst.PASSWORD_UNSET);
    }

    public static void set_all(Context context, Boolean bool) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putBoolean("nall", bool.booleanValue());
        editor.commit();
    }

    public static Boolean get_all(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolValue = Boolean.valueOf(preferences.getBoolean("nall", false));
        return boolValue;
    }

    public static void set_invited(Context context, Boolean bool) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putBoolean("invited", bool.booleanValue());
        editor.commit();
    }

    public static Boolean get_invited(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolValue = Boolean.valueOf(preferences.getBoolean("invited", false));
        return boolValue;
    }

    public static void set_accepted(Context context, Boolean bool) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putBoolean("accepted", bool.booleanValue());
        editor.commit();
    }

    public static Boolean get_accepted(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolValue = Boolean.valueOf(preferences.getBoolean("accepted", false));
        return boolValue;
    }

    public static void set_rejected(Context context, Boolean bool) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putBoolean("rejected", bool.booleanValue());
        editor.commit();
    }

    public static Boolean get_rejected(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolValue = Boolean.valueOf(preferences.getBoolean("rejected", false));
        return boolValue;
    }

    public static void set_new_review(Context context, Boolean bool) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putBoolean("new_review", bool.booleanValue());
        editor.commit();
    }

    public static Boolean get_new_review(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolValue = Boolean.valueOf(preferences.getBoolean("new_review", false));
        return boolValue;
    }
}
