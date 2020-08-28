package com.nst.yourname.view.utility.epg.misc;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class EPGUtil {
    private static final String TAG = "EPGUtil";
    private static Context context1;
    private static DateTimeFormatter dtfShortTime;
    private static SharedPreferences loginPreferencesSharedPref_time_format;
    private static Picasso picasso;

    public static String getShortTime(Context context, long j) {
        context1 = context;
        loginPreferencesSharedPref_time_format = context1.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
        dtfShortTime = DateTimeFormat.forPattern(loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, ""));
        return dtfShortTime.print(j);
    }

    public static String getWeekdayName(long j) {
        return new LocalDate(j).dayOfWeek().getAsText();
    }

    public static String getEPGdayName(long j) {
        LocalDate localDate = new LocalDate(j);
        return localDate.dayOfWeek().getAsShortText() + " " + localDate.getDayOfMonth() + "/" + localDate.getMonthOfYear();
    }

    public static void loadImageInto(Context context, String str, int i, int i2, Target target) {
        initPicasso(context);
        if (str == null || str.equals("")) {
            picasso.load((int) R.drawable.logo_placeholder_white).into(target);
        } else {
            picasso.load(str).resize(i, i2).centerInside().into(target);
        }
    }

    private static void initPicasso(Context context) {
        if (picasso == null) {
            picasso = new Picasso.Builder(context).downloader(new OkHttpDownloader(new OkHttpClient())).listener(new Picasso.Listener() {
                /* class com.nst.yourname.view.utility.epg.misc.EPGUtil.AnonymousClass1 */

                @Override // com.squareup.picasso.Picasso.Listener
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exc) {
                    Log.e(EPGUtil.TAG, exc.getMessage());
                }
            }).build();
        }
    }
}
