package com.nst.yourname.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import java.util.Calendar;

public class RateUsActivity extends AppCompatActivity {
    @BindView(R.id.btn_later)
    Button btn_later;
    @BindView(R.id.btn_rateus)
    Button btn_rateus;
    Context context;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.time)
    TextView time;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RateUsActivity):void
     arg types: [android.widget.Button, com.nst.yourname.view.activity.RateUsActivity]
     candidates:
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.content.Context):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.CheckAppupdateActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.LoginActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.MultiUserActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.NewDashboardActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RecordingActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.SplashActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.VodActivityNewFlowSubCategories):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerSkyActivity):void
      com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, com.nst.yourname.view.activity.RateUsActivity):void */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_rate_us);
        ButterKnife.bind(this);
        this.context = this;
        this.btn_rateus.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) this.btn_rateus, this));
        this.btn_rateus.requestFocus();
        this.btn_rateus.requestFocusFromTouch();
        this.btn_later.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) this.btn_later, this));
        new Thread(new CountDownRunner()).start();
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.RateUsActivity.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(RateUsActivity.this.context);
            }
        });
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    @OnClick({R.id.btn_rateus, R.id.btn_later})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.btn_later) {
            SharepreferenceDBHandler.setRateUsCount(0, this.context);
            onBackPressed();
        } else if (id == R.id.btn_rateus) {
            try {
                String packageName = getApplicationContext().getPackageName();
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                SharepreferenceDBHandler.setRateUsDontaskagain(true, this.context);
                startActivity(intent);
            } catch (Exception unused) {
                Toast.makeText(this.context, getResources().getString(R.string.device_not_supported), 0).show();
            }
        }
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    RateUsActivity.this.doWork();
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
            /* class com.nst.yourname.view.activity.RateUsActivity.AnonymousClass2 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(RateUsActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (RateUsActivity.this.time != null) {
                        RateUsActivity.this.time.setText(time);
                    }
                    if (RateUsActivity.this.date != null) {
                        RateUsActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }
}
