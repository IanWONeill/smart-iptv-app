package com.nst.yourname.WebServiceHandler;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import com.google.android.exoplayer2.C;
import com.nst.yourname.R;
import java.util.List;

public class MainAsyncTask extends AsyncTask<String, Void, String> {
    Context context;
    Dialog dialogBox;
    List<ParamsGetter> getterList;
    Boolean isProgress;
    boolean isSuccess = false;
    MainAsynListener<String> listener;
    int receivedId;
    public CommonFunctions sSetconnection;
    String tag;
    String url;

    public MainAsyncTask(Context context2, String str, int i, MainAsynListener<String> mainAsynListener, String str2, List<ParamsGetter> list, Boolean bool) {
        this.context = context2;
        this.url = str;
        this.receivedId = i;
        this.listener = mainAsynListener;
        this.tag = str2;
        this.getterList = list;
        this.isProgress = bool;
    }

    public void onPreExecute() {
        this.sSetconnection = new CommonFunctions();
        if (this.isProgress.booleanValue()) {
            try {
                if (this.dialogBox != null && this.dialogBox.isShowing()) {
                    cancelCommonDialog();
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    /* class com.nst.yourname.WebServiceHandler.MainAsyncTask.AnonymousClass1 */

                    public void run() {
                        MainAsyncTask.this.showCommonDialog(MainAsyncTask.this.context, "Loading data...");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String doInBackground(String... strArr) {
        String str;
        Exception e;
        try {
            str = CommonFunctions.getOkHttpClient(this.context, this.url, this.receivedId, this.tag, this.getterList);
            if (str != null) {
                try {
                    this.isSuccess = true;
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    return str;
                }
            } else {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    /* class com.nst.yourname.WebServiceHandler.MainAsyncTask.AnonymousClass2 */

                    public void run() {
                    }
                });
            }
        } catch (Exception e3) {
            str = null;
            e = e3;
            e.printStackTrace();
            return str;
        }
        return str;
    }

    @TargetApi(11)
    public void onPostExecute(String str) {
        try {
            if (this.isSuccess) {
                if (str == null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        /* class com.nst.yourname.WebServiceHandler.MainAsyncTask.AnonymousClass3 */

                        public void run() {
                        }
                    });
                }
                Log.e("result><><><>", "" + str);
                this.listener.onPostSuccess(str, this.receivedId, this.isSuccess);
            } else {
                this.listener.onPostError(this.receivedId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.isProgress.booleanValue()) {
            try {
                cancelCommonDialog();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void onCancelled() {
        super.onCancelled();
    }

    public boolean getConnectivityStatus(Context context2) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context2.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return false;
        }
        if (activeNetworkInfo.getType() == 1 || activeNetworkInfo.getType() == 0) {
            return true;
        }
        return false;
    }

    public void openInternetDialog(final Context context2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context2, R.style.DialogThemee);
        builder.setTitle("Internet Alert!");
        builder.setMessage("You are not connected to Internet..Please Enable Internet!").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            /* class com.nst.yourname.WebServiceHandler.MainAsyncTask.AnonymousClass5 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
                intent.addCategory("android.intent.category.LAUNCHER");
                intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings"));
                intent.setFlags(C.ENCODING_PCM_MU_LAW);
                context2.startActivity(intent);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            /* class com.nst.yourname.WebServiceHandler.MainAsyncTask.AnonymousClass4 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }

    public void showCommonDialog(Context context2, String str) {
        this.dialogBox = new Dialog(context2, 16973840);
        this.dialogBox.setContentView((int) R.layout.layout_progress_bar);
        this.dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.dialogBox.setCancelable(false);
        ((TextView) this.dialogBox.findViewById(R.id.message)).setText(str);
        try {
            if (!((Activity) this.context).isFinishing()) {
                this.dialogBox.show();
            } else {
                Log.e("FINISHED", "FINISHED");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelCommonDialog() {
        try {
            if (this.dialogBox != null && this.dialogBox.isShowing()) {
                this.dialogBox.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
