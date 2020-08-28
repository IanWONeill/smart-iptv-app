package com.nst.yourname.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.database.ExternalPlayerDataBase;
import com.nst.yourname.view.adapter.ExternalPlayerAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SuppressWarnings("ALL")
public class ExternalPlayerActivity extends AppCompatActivity implements ExternalPlayerAdapter.ItemClickListener {
    static final boolean $assertionsDisabled = false;
    AlertDialog alertDialog = null;
    List<ApplicationInfo> appInfoList;
    List<ApplicationInfo> appList;
    public Context context;
    @BindView(R.id.date)
    TextView date;
    public ExternalPlayerDataBase externalPlayerDataBase;
    @BindView(R.id.ll_no_data_found)
    LinearLayout ll_no_data_found;
    @BindView(R.id.ll_progressbar)
    LinearLayout ll_progressbar;
    @BindView(R.id.logo)
    ImageView logo;
    PackageManager packageManager;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.time)
    TextView time;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_external_player);
        this.context = this;
        ButterKnife.bind(this);
        getWindow().setFlags(1024, 1024);
        changeStatusBarColor();
        new Thread(new CountDownRunner()).start();
        updateData();
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.ExternalPlayerActivity.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(ExternalPlayerActivity.this.context);
            }
        });
    }

    public Boolean initcontrol() {
        this.appList = new ArrayList();
        this.appInfoList = new ArrayList();
        this.packageManager = this.context.getPackageManager();
        this.appList = this.packageManager.getInstalledApplications(128);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(new File("content://media/internal/video/media")), "video/*");
        for (ResolveInfo resolveInfo : getPackageManager().queryIntentActivities(intent, 0)) {
            try {
                this.appInfoList.add(this.packageManager.getApplicationInfo(resolveInfo.activityInfo.packageName, 128));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public void updateData() {
        new LoadExternalPlayer().execute(new Boolean[0]);
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    @SuppressLint({"StaticFieldLeak"})
    private class LoadExternalPlayer extends AsyncTask<Boolean, Void, Boolean> {
        private LoadExternalPlayer() {
        }

        public void onPreExecute() {
            super.onPreExecute();
            ExternalPlayerActivity.this.ll_no_data_found.setVisibility(8);
            ExternalPlayerActivity.this.recyclerView.setVisibility(8);
            ExternalPlayerActivity.this.ll_progressbar.setVisibility(0);
        }

        public Boolean doInBackground(Boolean... boolArr) {
            return ExternalPlayerActivity.this.initcontrol();
        }

        public void onPostExecute(Boolean bool) {
            super.onPostExecute((Boolean) bool);
            ExternalPlayerActivity.this.ll_progressbar.setVisibility(8);
            if (!bool.booleanValue() || ExternalPlayerActivity.this.appInfoList == null || ExternalPlayerActivity.this.appInfoList.size() <= 0) {
                ExternalPlayerActivity.this.onfinish(false);
                return;
            }
            ExternalPlayerActivity.this.onfinish(true);
            ExternalPlayerActivity.this.recyclerView.setLayoutManager(new LinearLayoutManager(ExternalPlayerActivity.this.context, 1, false));
            ExternalPlayerActivity.this.recyclerView.setAdapter(new ExternalPlayerAdapter(ExternalPlayerActivity.this.context, ExternalPlayerActivity.this.appInfoList, ExternalPlayerActivity.this));
        }
    }

    public void onfinish(Boolean bool) {
        if (bool.booleanValue()) {
            this.ll_no_data_found.setVisibility(8);
            this.recyclerView.setVisibility(0);
            return;
        }
        this.ll_no_data_found.setVisibility(0);
        this.recyclerView.setVisibility(8);
    }

    @Override // com.nst.yourname.view.adapter.ExternalPlayerAdapter.ItemClickListener
    public void itemClicked(View view, String str, String str2) {
        showPlayeraddeddPopup(str, str2);
    }

    private void showConfirmationPopup(final String str, final String str2) {
        new AlertDialog.Builder(this).setTitle("Confirmation").setMessage("Are you sure you want add player").setPositiveButton("Add", new DialogInterface.OnClickListener() {
            /* class com.nst.yourname.view.activity.ExternalPlayerActivity.AnonymousClass3 */

            public void onClick(DialogInterface dialogInterface, int i) {
                ExternalPlayerDataBase unused = ExternalPlayerActivity.this.externalPlayerDataBase = new ExternalPlayerDataBase(ExternalPlayerActivity.this.context);
                if (!ExternalPlayerActivity.this.externalPlayerDataBase.CheckPlayerExistense(str)) {
                    ExternalPlayerActivity.this.externalPlayerDataBase.addExternalPlayer(str, str2);
                    ExternalPlayerActivity.this.onBackPressed();
                } else {
                    Utils.showToast(ExternalPlayerActivity.this.context, "Already Added");
                }
                dialogInterface.dismiss();
            }
        }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            /* class com.nst.yourname.view.activity.ExternalPlayerActivity.AnonymousClass2 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [?, ?[OBJECT, ARRAY], int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
     arg types: [android.widget.Button, com.nst.yourname.view.activity.ExternalPlayerActivity]
     candidates:
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.content.Context):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.CheckAppupdateActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.LoginActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.MultiUserActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.NewDashboardActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RateUsActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RecordingActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.SplashActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.VodActivityNewFlowSubCategories):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void */
    private void showPlayerAddConfirmationAlert(final String str, final String str2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            /* class com.nst.yourname.view.activity.ExternalPlayerActivity.AnonymousClass4 */

            public void onDismiss(DialogInterface dialogInterface) {
            }
        });
        View inflate = LayoutInflater.from(this).inflate((int) R.layout.playera_add_alert_box, (ViewGroup) null, false);
        Button button = (Button) inflate.findViewById(R.id.btn_no);
        Button button2 = (Button) inflate.findViewById(R.id.btn_yes);
        ((TextView) inflate.findViewById(R.id.tv_description)).setText(getResources().getString(R.string.are_you_sure_you_want_add_player));
        button.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button, (Activity) this));
        button2.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button2, (Activity) this));
        button.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.ExternalPlayerActivity.AnonymousClass5 */

            public void onClick(View view) {
                if (ExternalPlayerActivity.this.alertDialog != null && ExternalPlayerActivity.this.alertDialog.isShowing()) {
                    ExternalPlayerActivity.this.alertDialog.dismiss();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.ExternalPlayerActivity.AnonymousClass6 */

            public void onClick(View view) {
                ExternalPlayerDataBase unused = ExternalPlayerActivity.this.externalPlayerDataBase = new ExternalPlayerDataBase(ExternalPlayerActivity.this.context);
                if (!ExternalPlayerActivity.this.externalPlayerDataBase.CheckPlayerExistense(str)) {
                    ExternalPlayerActivity.this.externalPlayerDataBase.addExternalPlayer(str, str2);
                    ExternalPlayerActivity.this.onBackPressed();
                } else {
                    Utils.showToast(ExternalPlayerActivity.this.context, ExternalPlayerActivity.this.getString(R.string.already_addedd));
                }
                if (ExternalPlayerActivity.this.alertDialog != null && ExternalPlayerActivity.this.alertDialog.isShowing()) {
                    ExternalPlayerActivity.this.alertDialog.dismiss();
                }
            }
        });
        builder.setView(inflate);
        this.alertDialog = builder.create();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(this.alertDialog.getWindow().getAttributes());
        layoutParams.width = -1;
        layoutParams.height = -1;
        this.alertDialog.show();
        this.alertDialog.getWindow().setAttributes(layoutParams);
        this.alertDialog.setCancelable(false);
        this.alertDialog.show();
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    ExternalPlayerActivity.this.doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                } catch (Exception unused2) {
                }
            }
        }
    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            /* class com.nst.yourname.view.activity.ExternalPlayerActivity.AnonymousClass7 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(ExternalPlayerActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (ExternalPlayerActivity.this.time != null) {
                        ExternalPlayerActivity.this.time.setText(time);
                    }
                    if (ExternalPlayerActivity.this.date != null) {
                        ExternalPlayerActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
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

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
     arg types: [android.widget.Button, com.nst.yourname.view.activity.ExternalPlayerActivity]
     candidates:
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.content.Context):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.CheckAppupdateActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.LoginActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.MultiUserActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.NewDashboardActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RateUsActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RecordingActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.SplashActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.VodActivityNewFlowSubCategories):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void */
    @SuppressLint({"RtlHardcoded"})
    private void showPlayeraddeddPopup(final String str, final String str2) {
        try {
            View inflate = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate((int) R.layout.playera_add_alert_box, (RelativeLayout) findViewById(R.id.rl_outer));
            final PopupWindow popupWindow = new PopupWindow(this.context);
            popupWindow.setContentView(inflate);
            popupWindow.setWidth(-1);
            popupWindow.setHeight(-1);
            popupWindow.setFocusable(true);
            popupWindow.showAtLocation(inflate, 17, 0, 0);
            Button button = (Button) inflate.findViewById(R.id.btn_no);
            Button button2 = (Button) inflate.findViewById(R.id.btn_yes);
            ((TextView) inflate.findViewById(R.id.tv_description)).setText(getResources().getString(R.string.are_you_sure_you_want_add_player));
            button.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button, (Activity) this));
            button2.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button2, (Activity) this));
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.ExternalPlayerActivity.AnonymousClass8 */

                public void onClick(View view) {
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.activity.ExternalPlayerActivity.AnonymousClass9 */

                public void onClick(View view) {
                    ExternalPlayerDataBase unused = ExternalPlayerActivity.this.externalPlayerDataBase = new ExternalPlayerDataBase(ExternalPlayerActivity.this.context);
                    if (!ExternalPlayerActivity.this.externalPlayerDataBase.CheckPlayerExistense(str)) {
                        ExternalPlayerActivity.this.externalPlayerDataBase.addExternalPlayer(str, str2);
                        Context access$000 = ExternalPlayerActivity.this.context;
                        Utils.showToast(access$000, str + " " + ExternalPlayerActivity.this.getResources().getString(R.string.added_external_player));
                        ExternalPlayerActivity.this.onBackPressed();
                    } else {
                        Utils.showToast(ExternalPlayerActivity.this.context, "Already Added");
                    }
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
            });
        } catch (Exception unused) {
        }
    }
}
