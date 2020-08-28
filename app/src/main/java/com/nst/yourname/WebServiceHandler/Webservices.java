package com.nst.yourname.WebServiceHandler;

import android.content.Context;
import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.List;

public class Webservices {
    public static Webservices getWebservices;
    public static List<ParamsGetter> getterList = new ArrayList();
    Context context;

    public static class getWebNames {
       public static String Url = "http://panels.teevmedia.com/smarters_user session/api/home.php?";
        //public static String Url = "http://panels.teevmedia.com/smarters_panel_2muchtv/api/home.php?";


        public static String announcement = "action=note";
        public static String resetpswd2 = "reset_password";
        public static String sequrity = "action=dns";
    }

    public Webservices(Context context2) {
        this.context = context2;
    }

    public void SequrityLink(MainAsynListener<String> mainAsynListener) {
        new MainAsyncTask(this.context, getWebNames.sequrity, 1, mainAsynListener, "Form", getterList, false).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
    }

    public void getAnnouncementLink(MainAsynListener<String> mainAsynListener) {
        new MainAsyncTask(this.context, getWebNames.announcement, 1, mainAsynListener, "Form", getterList, false).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
    }

    public static ParamsGetter P(String str, String str2) {
        return new ParamsGetter(str, str2);
    }
}
