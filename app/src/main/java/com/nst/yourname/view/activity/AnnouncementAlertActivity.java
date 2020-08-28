package com.nst.yourname.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.Utils;
import java.util.Calendar;

public class AnnouncementAlertActivity extends Activity {
    AnnouncementAlertActivity context;
    @BindView(R.id.date)
    TextView date;
    private String description = "";
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.time)
    TextView time;
    private String title = "";
    private TextView tv_announcement_description;
    private TextView tv_announcement_title;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_announcement_alert);
        this.context = this;
        ButterKnife.bind(this);
        this.tv_announcement_description = (TextView) findViewById(R.id.tv_announcement_description);
        this.tv_announcement_title = (TextView) findViewById(R.id.tv_announcement_title);
        Intent intent = getIntent();
        this.title = intent.getStringExtra("Title");
        this.description = intent.getStringExtra("Description");
        this.tv_announcement_title.setText(this.title);
        this.tv_announcement_description.setText(this.description);
        this.logo.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.AnnouncementAlertActivity.AnonymousClass1 */

            public void onClick(View view) {
                Utils.Redrirect_to_Home(AnnouncementAlertActivity.this.context);
            }
        });
        new Thread(new CountDownRunner()).start();
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    AnnouncementAlertActivity.this.doWork();
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
            /* class com.nst.yourname.view.activity.AnnouncementAlertActivity.AnonymousClass2 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(AnnouncementAlertActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (AnnouncementAlertActivity.this.time != null) {
                        AnnouncementAlertActivity.this.time.setText(time);
                    }
                    if (AnnouncementAlertActivity.this.date != null) {
                        AnnouncementAlertActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }
}
