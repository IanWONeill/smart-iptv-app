package com.nst.yourname.view.activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import java.io.File;

public class PlayRecordingExternalPlayerActivity extends AppCompatActivity {
    private String app_name = "";
    private Uri contentUri;
    public Context context;
    private String mFilePath;
    private String package_class_name = "";
    public String packagename = "";

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        changeStatusBarColor();
        initialize();
        getWindow().setFlags(1024, 1024);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0083 */
    private void initialize() {
        this.context = this;
        this.packagename = getIntent().getStringExtra(AppConst.PACKAGE_NAME);
        this.package_class_name = this.packagename + ".ActivityScreen";
        this.app_name = getIntent().getStringExtra(AppConst.APP_NAME);
        this.mFilePath = getIntent().getStringExtra("url");
        if (!appInstalledOrNot(this.packagename)) {
            mxPlayerNotFoundDialogBox();
        } else if (this.context != null) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setPackage(this.packagename);
                intent.setDataAndType(Uri.parse("file://" + this.mFilePath), "video/*");
                intent.addFlags(1);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                finish();
            } catch (ActivityNotFoundException unused) {
                mxPlayerNotFoundDialogBox();
                return;
            } catch (Exception unknown) {
                File file = new File(this.mFilePath);
                Context context2 = this.context;
                Uri uriForFile = FileProvider.getUriForFile(context2, this.context.getApplicationContext().getPackageName() + ".provider", file);
                Intent intent2 = new Intent("android.intent.action.VIEW");
                intent2.setPackage(this.packagename);
                intent2.setDataAndType(uriForFile, "video/*");
                intent2.addFlags(1);
                startActivity(intent2);
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                finish();
            }
            try {
                File file2 = new File(this.mFilePath);
                Context context22 = this.context;
                Uri uriForFile2 = FileProvider.getUriForFile(context22, this.context.getApplicationContext().getPackageName() + ".provider", file2);
                Intent intent22 = new Intent("android.intent.action.VIEW");
                intent22.setPackage(this.packagename);
                intent22.setDataAndType(uriForFile2, "video/*");
                intent22.addFlags(1);
                startActivity(intent22);
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                finish();
            } catch (Exception unused2) {
                mxPlayerNotFoundDialogBox();
            }
        }
    }

    private boolean appInstalledOrNot(String str) {
        if (this.context == null) {
            return false;
        }
        try {
            this.context.getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public void mxPlayerNotFoundDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.media_player));
        builder.setMessage(this.app_name + " " + getResources().getString(R.string.alert_player));
        builder.setPositiveButton(getResources().getString(R.string.install_it), new DialogInterface.OnClickListener() {
            /* class com.nst.yourname.view.activity.PlayRecordingExternalPlayerActivity.AnonymousClass1 */

            /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
                return;
             */
            /* JADX WARNING: Failed to process nested try/catch */
            /* JADX WARNING: Missing exception handler attribute for start block: B:2:0x0028 */
            public void onClick(DialogInterface dialogInterface, int i) {
                PlayRecordingExternalPlayerActivity playRecordingExternalPlayerActivity = PlayRecordingExternalPlayerActivity.this;
                playRecordingExternalPlayerActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + PlayRecordingExternalPlayerActivity.this.packagename)));
                try {
                    PlayRecordingExternalPlayerActivity playRecordingExternalPlayerActivity2 = PlayRecordingExternalPlayerActivity.this;
                    playRecordingExternalPlayerActivity2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + PlayRecordingExternalPlayerActivity.this.packagename)));
                } catch (ActivityNotFoundException e) {
                    Utils.showToast(PlayRecordingExternalPlayerActivity.this.context, String.valueOf(e));
                }
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel_small), new DialogInterface.OnClickListener() {
            /* class com.nst.yourname.view.activity.PlayRecordingExternalPlayerActivity.AnonymousClass2 */

            public void onClick(DialogInterface dialogInterface, int i) {
                PlayRecordingExternalPlayerActivity.this.finish();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    private void changeStatusBarColor() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= 19) {
            window.clearFlags(67108864);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(Integer.MIN_VALUE);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }
}
