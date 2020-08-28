package com.nst.yourname.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import java.io.IOException;
import java.util.Calendar;
import org.jsoup.Jsoup;

@SuppressWarnings("ALL")
public class CheckAppupdateActivity extends AppCompatActivity {
    @BindView(R.id.btn_later)
    Button btn_later;
    @BindView(R.id.btn_update)
    Button btn_update;
    Context context;
    private String currentVersion = "";
    @BindView(R.id.date)
    TextView date;
    boolean isupdate = false;
    @BindView(R.id.ll_marginLayout)
    LinearLayout ll_marginLayout;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.tv_check_app_update_message)
    TextView message;
    @BindView(R.id.nested_checkupdate)
    NestedScrollView nested_checkupdate;
    @BindView(R.id.pb_paging_loader)
    ProgressBar pbLoader;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.tv_check_app_update_title)
    TextView title;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.CheckAppupdateActivity):void
     arg types: [android.widget.Button, com.nst.yourname.view.activity.CheckAppupdateActivity]
     candidates:
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.content.Context):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.LoginActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.MultiUserActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.NewDashboardActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RateUsActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RecordingActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.SplashActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.VodActivityNewFlowSubCategories):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.CheckAppupdateActivity):void */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_check_appupdate);
        ButterKnife.bind(this);
        this.context = this;
        new Thread(new CountDownRunner()).start();
        try {
            this.currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        this.btn_update.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) this.btn_update, this));
        this.btn_update.requestFocus();
        this.btn_update.requestFocusFromTouch();
        this.btn_later.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) this.btn_later, this));
        new CheckAppUpdate(this, this.currentVersion).execute(new Void[0]);
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.CheckAppupdateActivity.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(CheckAppupdateActivity.this.context);
            }
        });
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    CheckAppupdateActivity.this.doWork();
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
            /* class com.nst.yourname.view.activity.CheckAppupdateActivity.AnonymousClass2 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(CheckAppupdateActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (CheckAppupdateActivity.this.time != null) {
                        CheckAppupdateActivity.this.time.setText(time);
                    }
                    if (CheckAppupdateActivity.this.date != null) {
                        CheckAppupdateActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    public void onFinish() {
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(8);
        }
        this.nested_checkupdate.setVisibility(0);
    }

    public void atStart() {
        if (this.pbLoader != null) {
            this.pbLoader.setVisibility(0);
        }
        this.nested_checkupdate.setVisibility(8);
    }

    private class CheckAppUpdate extends AsyncTask<Void, String, String> {
        private Context context;
        private String currentVersion = "";

        public CheckAppUpdate(Context context2, String str) {
            this.context = context2;
            this.currentVersion = str;
        }

        public void onPreExecute() {
            super.onPreExecute();
            CheckAppupdateActivity.this.atStart();
        }

        public String doInBackground(Void... voidArr) {
            try {
                return Jsoup.connect("https://play.google.com/store/apps/details?id=com.nst.yourname&hl=en").timeout(5000).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").referrer("http://www.google.com").get().select(" div.hAyfc:nth-child(4) > span:nth-child(2) >div:nth-child(1) > span:nth-child(1)").first().ownText();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void onPostExecute(String str) {
            int i;
            int i2;
            super.onPostExecute((String) str);
            CheckAppupdateActivity.this.onFinish();
            if (str == null || str.isEmpty()) {
                CheckAppupdateActivity.this.setdata(false);
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
                    CheckAppupdateActivity.this.setdata(true);
                } else {
                    CheckAppupdateActivity.this.setdata(false);
                }
            } else {
                CheckAppupdateActivity.this.setdata(false);
            }
        }

        public String Removespeciallycharater(String str) {
            return str.replaceAll("[\\-\\+\\.\\^:,]", "");
        }
    }

    public void setdata(boolean z) {
        if (z) {
            this.nested_checkupdate.setVisibility(0);
            return;
        }
        TextView textView = this.message;
        textView.setText(getResources().getString(R.string.up_to_date) + this.currentVersion + " " + getResources().getString(R.string.up_to_date2));
        this.title.setText(getResources().getString(R.string.no_update));
        this.btn_update.setVisibility(8);
        this.btn_later.setText(getResources().getString(R.string.back_button));
        this.ll_marginLayout.setVisibility(8);
        this.btn_later.requestFocus();
        this.btn_later.requestFocusFromTouch();
    }

    @OnClick({R.id.btn_update, R.id.btn_later})
    public void onclickView(View view) {
        int id = view.getId();
        if (id == R.id.btn_later) {
            onBackPressed();
        } else if (id == R.id.btn_update) {
            String packageName = getApplicationContext().getPackageName();
            try {
                startActivityForResult(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)), 101);
            } catch (Exception unused) {
                Toast.makeText(this.context, getResources().getString(R.string.device_not_supported), 0).show();
            }
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 101) {
            try {
                this.currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            new CheckAppUpdate(this, this.currentVersion).execute(new Void[0]);
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }
}
