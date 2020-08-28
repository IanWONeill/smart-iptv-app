package com.nst.yourname.view.activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.google.android.exoplayer2.C;

public class NotificationActivity extends Activity {
    static final boolean $assertionsDisabled = false;
    public static final String ASYNC_TASK = "ASYNC_TASK";
    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";
    final String CANCELLED = "CANCELLED";
    SharedPreferences preferenceManager;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = this.preferenceManager.edit();
        edit.putBoolean("CANCELLED", true);
        edit.apply();
        ((NotificationManager) getSystemService("notification")).cancel(getIntent().getIntExtra(NOTIFICATION_ID, -1));
        finish();
    }

    public static PendingIntent getDismissIntent(int i, Context context) {
        Intent intent = new Intent(context, NotificationActivity.class);
        intent.setFlags(268468224);
        intent.putExtra(NOTIFICATION_ID, i);
        return PendingIntent.getActivity(context, 0, intent, C.ENCODING_PCM_MU_LAW);
    }
}
