package com.nst.yourname.miscelleneious;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Toast;
import com.nst.yourname.R;
import com.nst.yourname.WebServiceHandler.Globals;
import com.nst.yourname.WebServiceHandler.MainAsynListener;
import com.nst.yourname.WebServiceHandler.RavSharedPrefrences;
import com.nst.yourname.WebServiceHandler.Webservices;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.view.activity.LoginActivity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import org.json.JSONObject;

@SuppressWarnings("ALL")
public class LoginStatusCheck implements MainAsynListener<String> {
    String FirstMdkey;
    String SecondMdkey;
    String action;
    Activity activity;
    Context context;
    Handler handler;
    String key;
    LoginActivity loginActivity;
    private SharedPreferences loginPreferencesRemember;
    String model = Build.MODEL;
    int random;
    String reqString;
    String salt;
    private String username;
    String version;

    public LoginStatusCheck(Context context2, Activity activity2) {
        this.activity = activity2;
        this.context = context2;
        this.loginActivity = new LoginActivity();
        this.loginPreferencesRemember = context2.getSharedPreferences(AppConst.SHARED_PREFERENCE_REMEBER_ME, 0);
        this.username = this.loginPreferencesRemember.getString("username", "");
        try {
            this.version = context2.getPackageManager().getPackageInfo(context2.getPackageName(), 0).versionName;
            this.reqString = Build.VERSION.RELEASE + " " + Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void SequrityApi() {
        this.FirstMdkey = md5(RavSharedPrefrences.get_clientkey(this.activity) + "*" + RavSharedPrefrences.get_salt(this.activity) + "-" + this.username + "-" + Globals.RandomNumber + "-" + this.version + "-unknown-" + getDeviceName() + "-" + this.reqString);
        Webservices.getterList = new ArrayList();
        Webservices.getterList.add(Webservices.P("m", "gu"));
        Webservices.getterList.add(Webservices.P("k", RavSharedPrefrences.get_clientkey(this.activity)));
        Webservices.getterList.add(Webservices.P("sc", this.FirstMdkey));
        Webservices.getterList.add(Webservices.P("u", this.username));
        Webservices.getterList.add(Webservices.P("pw", "no_password"));
        Webservices.getterList.add(Webservices.P("r", Globals.RandomNumber));
        Webservices.getterList.add(Webservices.P("av", this.version));
        Webservices.getterList.add(Webservices.P("dt", "unknown"));
        Webservices.getterList.add(Webservices.P("d", getDeviceName()));
        Webservices.getterList.add(Webservices.P("do", this.reqString));
        Webservices.getWebservices.SequrityLink(this);
    }

    public static String md5(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                String hexString = Integer.toHexString(b & -1);
                while (hexString.length() < 2) {
                    hexString = AppConst.PASSWORD_UNSET + hexString;
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void onPostSuccess(String str, int i, boolean z) {
        if (z && i == 1) {
            try {
                Globals.jsonObj = new JSONObject(str);
                if (Globals.jsonObj.getString(NotificationCompat.CATEGORY_STATUS).equalsIgnoreCase("true")) {
                    AppConst.ACTIVE_STATUS = true;
                    if (!AppConst.MultiDNS_And_MultiUser.booleanValue()) {
                        this.loginActivity = new LoginActivity();
                        this.loginActivity.loginCheck();
                        return;
                    }
                    return;
                }
                AppConst.ACTIVE_STATUS = false;
                this.loginActivity.loginCheck();
            } catch (Exception unused) {
            }
        }
    }

    @Override // com.nst.yourname.WebServiceHandler.MainAsynListener
    public void onPostError(int i) {
        if (this.context != null) {
            Toast.makeText(this.context, this.context.getResources().getString(R.string.could_not_connect), 0).show();
        }
    }

    public static String getDeviceName() {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        if (str2.startsWith(str)) {
            return capitalize(str2);
        }
        return capitalize(str) + " " + str2;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] charArray = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (char c : charArray) {
            if (!z || !Character.isLetter(c)) {
                if (Character.isWhitespace(c)) {
                    z = true;
                }
                sb.append(c);
            } else {
                sb.append(Character.toUpperCase(c));
                z = false;
            }
        }
        return sb.toString();
    }
}
