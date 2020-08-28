package com.nst.yourname.view.activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import com.nst.yourname.R;

public class VLCPlayerRecordingActivity extends AppCompatActivity {
    private static final String _VLC_PLAYER_CLASS_NAME = "org.videolan.vlc.gui.video.VideoPlayerActivity";
    private static final String _VLC_PLAYER_PACKAGE_NAME = "org.videolan.vlc";
    public Context context;
    private String mFilePath;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        changeStatusBarColor();
        initialize();
        getWindow().setFlags(1024, 1024);
    }

    private void initialize() {
        this.context = this;
        this.mFilePath = getIntent().getStringExtra("VIDEO_PATH");
        try {
            Uri parse = Uri.parse(this.mFilePath);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setPackage(_VLC_PLAYER_PACKAGE_NAME);
            intent.setDataAndTypeAndNormalize(parse, "video/*");
            startActivityForResult(intent, 42);
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
            finish();
        } catch (ActivityNotFoundException unused) {
            vlcPlayerNotFoundDialogBox();
        }
    }

    public void vlcPlayerNotFoundDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.media_player));
        builder.setMessage(getResources().getString(R.string.alert_vlc_player));
        builder.setPositiveButton(getResources().getString(R.string.install_it), new DialogInterface.OnClickListener() {
            /* class com.nst.yourname.view.activity.VLCPlayerRecordingActivity.AnonymousClass1 */

            /* JADX WARNING: Can't wrap try/catch for region: R(5:0|1|2|3|8) */
            /* JADX WARNING: Code restructure failed: missing block: B:4:0x0026, code lost:
                r3 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:5:0x0027, code lost:
                com.nst.yourname.miscelleneious.common.Utils.showToast(com.nst.yourname.view.activity.VLCPlayerRecordingActivity.access$000(r2.this$0), java.lang.String.valueOf(r3));
             */
            /* JADX WARNING: Code restructure failed: missing block: B:6:?, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
                return;
             */
            /* JADX WARNING: Failed to process nested try/catch */
            /* JADX WARNING: Missing exception handler attribute for start block: B:2:0x0013 */
            public void onClick(DialogInterface dialogInterface, int i) {
                VLCPlayerRecordingActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=org.videolan.vlc")));
                VLCPlayerRecordingActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=org.videolan.vlc")));
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel_small), new DialogInterface.OnClickListener() {
            /* class com.nst.yourname.view.activity.VLCPlayerRecordingActivity.AnonymousClass2 */

            public void onClick(DialogInterface dialogInterface, int i) {
                VLCPlayerRecordingActivity.this.finish();
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
