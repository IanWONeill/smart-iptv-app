package com.nst.yourname.WHMCSClientapp.notifications;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.nst.yourname.miscelleneious.common.AppConst;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseInstanceIDService";

    @Override // com.google.firebase.iid.FirebaseInstanceIdService
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        storeRegIdInPref(token);
        sendRegistrationToServer(token);
        Intent intent = new Intent(AppConst.REGISTRATION_COMPLETE);
        intent.putExtra("token", token);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendRegistrationToServer(String str) {
        String str2 = TAG;
        Log.e(str2, "sendRegistrationToServer: " + str);
    }

    private void storeRegIdInPref(String str) {
        SharedPreferences.Editor edit = getApplicationContext().getSharedPreferences(AppConst.SHARED_PREF, 0).edit();
        edit.putString("regId", str);
        edit.commit();
    }
}
