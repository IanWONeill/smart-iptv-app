package com.nst.yourname.miscelleneious.common;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.graphics.drawable.PathInterpolatorCompat;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import java.io.IOException;
import org.jsoup.Jsoup;

public class CheckAppUpdate extends AsyncTask<Void, String, String> {
    private Context context;
    private String currentVersion = "";

    public CheckAppUpdate(Context context2) {
        this.context = context2;
        try {
            this.currentVersion = context2.getPackageManager().getPackageInfo(context2.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String doInBackground(Void... voidArr) {
        try {
            return Jsoup.connect("https://play.google.com/store/apps/details?id=com.nst.yourname&hl=en").timeout(PathInterpolatorCompat.MAX_NUM_POINTS).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").referrer("http://www.google.com").get().select(" div.hAyfc:nth-child(4) > span:nth-child(2) >div:nth-child(1) > span:nth-child(1)").first().ownText();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onPostExecute(String str) {
        int i;
        int i2;
        super.onPostExecute((String) str);
        if (str == null || str.isEmpty()) {
            SharepreferenceDBHandler.setIsupdate(false, this.context);
            SharepreferenceDBHandler.setIsAppExistOnPlayStore(false, this.context);
            return;
        }
        SharepreferenceDBHandler.setIsAppExistOnPlayStore(true, this.context);
        if (str.matches("\\d\\.\\d") || str.matches("\\d\\.\\d\\.\\d") || str.matches("\\d\\.\\d\\.\\d\\.\\d")) {
            String Removespeciallycharater = Removespeciallycharater(str);
            String Removespeciallycharater2 = Removespeciallycharater(this.currentVersion);
            if (Removespeciallycharater.length() > Removespeciallycharater2.length()) {
                int length = Removespeciallycharater.length() - Removespeciallycharater2.length();
                StringBuffer stringBuffer = new StringBuffer(Removespeciallycharater2);
                for (int i3 = 0; i3 < length; i3++) {
                    stringBuffer.append(0);
                }
                Removespeciallycharater2 = stringBuffer.toString();
            } else if (Removespeciallycharater.length() < Removespeciallycharater2.length()) {
                int length2 = Removespeciallycharater2.length() - Removespeciallycharater.length();
                StringBuffer stringBuffer2 = new StringBuffer(Removespeciallycharater);
                for (int i4 = 0; i4 < length2; i4++) {
                    stringBuffer2.append(0);
                }
                Removespeciallycharater = stringBuffer2.toString();
            }
            try {
                i = Integer.parseInt(Removespeciallycharater);
                i2 = Integer.parseInt(Removespeciallycharater2);
            } catch (NumberFormatException unused) {
                i = 1;
                i2 = 1;
            }
            if (i > i2) {
                SharepreferenceDBHandler.setIsupdate(true, this.context);
            } else {
                SharepreferenceDBHandler.setIsupdate(false, this.context);
            }
        } else {
            SharepreferenceDBHandler.setIsupdate(false, this.context);
        }
    }

    public String Removespeciallycharater(String str) {
        return str.replaceAll("[\\-\\+\\.\\^:,]", "");
    }
}
