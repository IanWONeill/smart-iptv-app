package com.nst.yourname.view;

import android.content.Context;
import android.content.SharedPreferences;
import com.nst.yourname.R;

public class AppPref {
    private static Context appContext;
    private static AppPref appPref;
    private SharedPreferences.Editor editor = this.preferences.edit();
    private SharedPreferences preferences = appContext.getSharedPreferences(appContext.getString(R.string.app_name), 0);

    public static AppPref getInstance(Context context) {
        appContext = context;
        if (appPref != null) {
            return appPref;
        }
        appPref = new AppPref();
        return appPref;
    }

    public int getResizeMode() {
        return this.preferences.getInt("resize_mode", 0);
    }

    public void setResizeMode(int i) {
        this.editor.putInt("resize_mode", i);
        this.editor.commit();
    }
}
