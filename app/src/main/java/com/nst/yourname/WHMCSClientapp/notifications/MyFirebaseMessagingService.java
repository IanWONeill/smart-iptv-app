package com.nst.yourname.WHMCSClientapp.notifications;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nst.yourname.WHMCSClientapp.activities.MyTicketActivity;
import com.nst.yourname.miscelleneious.common.AppConst;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("ALL")
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingService";
    private NotificationUtils notificationUtils;

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String str = TAG;
        Log.e(str, "From: " + remoteMessage.getFrom());
        if (remoteMessage != null) {
            if (remoteMessage.getNotification() != null) {
                String str2 = TAG;
                Log.e(str2, "Notification Body: " + remoteMessage.getNotification().getBody());
                handleNotification(remoteMessage.getNotification().getBody());
            }
            if (remoteMessage.getData().size() > 0) {
                String str3 = TAG;
                Log.e(str3, "Data Payload: " + remoteMessage.getData().toString());
                try {
                    handleDataMessage(new JSONObject(remoteMessage.getData().toString()));
                } catch (Exception e) {
                    String str4 = TAG;
                    Log.e(str4, "Exception: " + e.getMessage());
                }
            }
        }
    }

    private void handleNotification(String str) {
        /*if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            Intent intent = new Intent(AppConst.PUSH_NOTIFICATION);
            intent.putExtra("message", str);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            new NotificationUtils(getApplicationContext()).playNotificationSound();
        }*/
    }

    private void handleDataMessage(JSONObject jSONObject) {
        String str = TAG;
        Log.e(str, "push json: " + jSONObject.toString());
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            String string = jSONObject2.getString("title");
            String string2 = jSONObject2.getString("message");
            boolean z = jSONObject2.getBoolean("is_background");
            String string3 = jSONObject2.getString("image");
            String string4 = jSONObject2.getString(AppMeasurement.Param.TIMESTAMP);
            JSONObject jSONObject3 = jSONObject2.getJSONObject("payload");
            String str2 = TAG;
            Log.e(str2, "title: " + string);
            String str3 = TAG;
            Log.e(str3, "message: " + string2);
            String str4 = TAG;
            Log.e(str4, "isBackground: " + z);
            String str5 = TAG;
            Log.e(str5, "payload: " + jSONObject3.toString());
            String str6 = TAG;
            Log.e(str6, "imageUrl: " + string3);
            String str7 = TAG;
            Log.e(str7, "timestamp: " + string4);
            /*Intent intent = new Intent(getApplicationContext(), MyTicketActivity.class);
            intent.setAction(AppConst.ACTION_NOTIFICATION);
            intent.putExtra("ticketid", "4");
            if (TextUtils.isEmpty(string3)) {
                showNotificationMessage(getApplicationContext(), string, string2, string4, intent);
            } else {
                showNotificationMessageWithBigImage(getApplicationContext(), string, string2, string4, intent, string3);
            }*/
        } catch (JSONException e) {
            String str8 = TAG;
            Log.e(str8, "Json Exception: " + e.getMessage());
        } catch (Exception e2) {
            String str9 = TAG;
            Log.e(str9, "Exception: " + e2.getMessage());
        }
    }

    private void showNotificationMessage(Context context, String str, String str2, String str3, Intent intent) {
        this.notificationUtils = new NotificationUtils(context);
        intent.setFlags(268468224);
        this.notificationUtils.showNotificationMessage(str, str2, str3, intent);
    }

    private void showNotificationMessageWithBigImage(Context context, String str, String str2, String str3, Intent intent, String str4) {
        this.notificationUtils = new NotificationUtils(context);
        intent.setFlags(268468224);
        this.notificationUtils.showNotificationMessage(str, str2, str3, intent, str4);
    }
}
