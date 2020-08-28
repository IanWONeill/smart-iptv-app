package com.nst.yourname.WHMCSClientapp.notifications;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Patterns;
import com.nst.yourname.R;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NotificationUtils {
    static final boolean $assertionsDisabled = false;
    private static String TAG = "NotificationUtils";
    private Context context;
    private Context mContext;

    public NotificationUtils(Context context2) {
        this.mContext = context2;
    }

    public void showNotificationMessage(String str, String str2, String str3, Intent intent) {
        showNotificationMessage(str, str2, str3, intent, null);
    }

    public void showNotificationMessage(String str, String str2, String str3, Intent intent, String str4) {
        if (!TextUtils.isEmpty(str2)) {
            intent.setFlags(603979776);
            PendingIntent activity = PendingIntent.getActivity(this.mContext, 0, intent, 134217728);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this.mContext);
            Uri parse = Uri.parse("android.resource://" + this.mContext.getPackageName() + "/raw/notification");
            if (TextUtils.isEmpty(str4)) {
                showSmallNotification(builder, R.mipmap.ic_launcher, str, str2, str3, activity, parse);
                playNotificationSound();
            } else if (str4 != null && str4.length() > 4 && Patterns.WEB_URL.matcher(str4).matches()) {
                Bitmap bitmapFromURL = getBitmapFromURL(str4);
                if (bitmapFromURL != null) {
                    showBigNotification(bitmapFromURL, builder, R.mipmap.ic_launcher, str, str2, str3, activity, parse);
                } else {
                    showSmallNotification(builder, R.mipmap.ic_launcher, str, str2, str3, activity, parse);
                }
            }
        }
    }

    private void showSmallNotification(NotificationCompat.Builder builder, int i, String str, String str2, String str3, PendingIntent pendingIntent, Uri uri) {
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this.mContext);
        if (Build.VERSION.SDK_INT >= 26) {
            new NotificationCompat.InboxStyle().addLine(str2);
            NotificationChannel notificationChannel = new NotificationChannel("ksjadf87", this.mContext.getResources().getString(R.string.recording_dots), 2);
            Notification build = new Notification.Builder(this.mContext).setContentTitle(str).setContentText(str2).setAutoCancel(true).setSound(uri).setContentIntent(pendingIntent).setSmallIcon((int) R.mipmap.ic_launcher).setChannelId("ksjadf87").build();
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.notify(234231, build);
            return;
        }
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(str2);
        builder2.setContentTitle(str).setContentText(str2).setAutoCancel(true).setSound(uri).setStyle(inboxStyle).setContentIntent(pendingIntent).setSmallIcon(R.mipmap.ic_launcher);
        notificationManager.notify(234231, builder2.build());
    }

    private void showBigNotification(Bitmap bitmap, NotificationCompat.Builder builder, int i, String str, String str2, String str3, PendingIntent pendingIntent, Uri uri) {
        new NotificationCompat.BigPictureStyle();
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this.mContext);
        if (Build.VERSION.SDK_INT >= 26) {
            new NotificationCompat.InboxStyle().addLine(str2);
            NotificationChannel notificationChannel = new NotificationChannel("ksjadf87", this.mContext.getResources().getString(R.string.recording_dots), 2);
            Notification build = new Notification.Builder(this.mContext).setContentTitle(str).setContentText(str2).setAutoCancel(true).setSound(uri).setContentIntent(pendingIntent).setSmallIcon((int) R.mipmap.ic_launcher).setChannelId("ksjadf87").build();
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.notify(234231, build);
            return;
        }
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(str2);
        builder2.setContentTitle(str).setContentText(str2).setAutoCancel(true).setSound(uri).setStyle(inboxStyle).setContentIntent(pendingIntent).setSmallIcon(R.mipmap.ic_launcher);
        notificationManager.notify(234231, builder2.build());
    }

    public Bitmap getBitmapFromURL(String str) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            return BitmapFactory.decodeStream(httpURLConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void playNotificationSound() {
        try {
            RingtoneManager.getRingtone(this.mContext, Uri.parse("android.resource://" + this.mContext.getPackageName() + "/raw/notification")).play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isAppIsInBackground(Context context2) {
        ActivityManager activityManager = (ActivityManager) context2.getSystemService("activity");
        boolean z = true;
        if (Build.VERSION.SDK_INT > 20) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
                if (runningAppProcessInfo.importance == 100) {
                    boolean z2 = z;
                    for (String str : runningAppProcessInfo.pkgList) {
                        if (str.equals(context2.getPackageName())) {
                            z2 = false;
                        }
                    }
                    z = z2;
                }
            }
            return z;
        } else if (activityManager.getRunningTasks(1).get(0).topActivity.getPackageName().equals(context2.getPackageName())) {
            return false;
        } else {
            return true;
        }
    }

    public static void clearNotifications(Context context2) {
        ((NotificationManager) context2.getSystemService("notification")).cancelAll();
    }

    public static long getTimeMilliSec(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void setBadge(Context context2, int i) {
        String launcherClassName = getLauncherClassName(context2);
        if (launcherClassName != null) {
            Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
            intent.putExtra("badge_count", i);
            intent.putExtra("badge_count_package_name", context2.getPackageName());
            intent.putExtra("badge_count_class_name", launcherClassName);
            context2.sendBroadcast(intent);
        }
    }

    public static String getLauncherClassName(Context context2) {
        PackageManager packageManager = context2.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(intent, 0)) {
            if (resolveInfo.activityInfo.applicationInfo.packageName.equalsIgnoreCase(context2.getPackageName())) {
                return resolveInfo.activityInfo.name;
            }
        }
        return null;
    }
}
