package com.nst.yourname.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.google.android.exoplayer2.C;
import com.nst.yourname.miscelleneious.common.AppConst;

public class BootStartupActivity extends BroadcastReceiver {
    private SharedPreferences loginPreferencesSharedPref_auto_start;

    public void onReceive(Context context, Intent intent) {
        this.loginPreferencesSharedPref_auto_start = context.getSharedPreferences(AppConst.LOGIN_PREF_AUTO_START, 0);
        if (this.loginPreferencesSharedPref_auto_start.getBoolean(AppConst.LOGIN_PREF_AUTO_START, false) && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Intent intent2 = new Intent(context, SplashActivity.class);
            intent2.addFlags(C.ENCODING_PCM_MU_LAW);
            context.startActivity(intent2);
        }
    }
}
