package com.nst.yourname.WHMCSClientapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.Clientdatabase.ClientSharepreferenceHandler;
import com.nst.yourname.miscelleneious.common.Utils;
import java.util.Calendar;

public class ShowserviceInformationActivity extends AppCompatActivity {
    String Registration_date = "";
    String billing_cycle = "";
    @BindView(R.id.btn_back)
    Button btn_back;
    @BindView(R.id.btn_service_home)
    Button btn_service_home;
    Context context;
    @BindView(R.id.date)
    TextView date;
    String first_time_payment = "";
    String next_due_date = "";
    String payment_method = "";
    String products = "";
    String recurring_amount = "";
    String status = "";
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.tv_billing_cycle)
    TextView tv_billing_cycle;
    @BindView(R.id.tv_first_time_payment)
    TextView tv_first_time_payment;
    @BindView(R.id.tv_next_due_date)
    TextView tv_next_due_date;
    @BindView(R.id.tv_payment_method)
    TextView tv_payment_method;
    @BindView(R.id.tv_product)
    TextView tv_product;
    @BindView(R.id.tv_recurring_amount)
    TextView tv_recurring_amount;
    @BindView(R.id.tv_registration_date)
    TextView tv_registration_date;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_title)
    TextView tv_title;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
     arg types: [android.widget.Button, com.nst.yourname.WHMCSClientapp.activities.ShowserviceInformationActivity]
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
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_showservice_information);
        ButterKnife.bind(this);
        this.context = this;
        new Thread(new CountDownRunner()).start();
        Intent intent = getIntent();
        this.products = intent.getStringExtra("product");
        this.status = intent.getStringExtra(NotificationCompat.CATEGORY_STATUS);
        this.Registration_date = intent.getStringExtra("Registration_date");
        this.next_due_date = intent.getStringExtra("next_due_date");
        this.recurring_amount = intent.getStringExtra("recurring_amount");
        this.billing_cycle = intent.getStringExtra("billing_cycle");
        this.payment_method = intent.getStringExtra("payment_method");
        this.first_time_payment = intent.getStringExtra("first_time_payment");
        if (this.billing_cycle.equalsIgnoreCase("Free Account")) {
            this.tv_next_due_date.setText("- - -");
            this.tv_recurring_amount.setText("- - -");
        } else {
            if (this.next_due_date == null || this.next_due_date.equalsIgnoreCase("")) {
                this.tv_next_due_date.setText(getResources().getString(R.string.N_A));
            } else {
                this.tv_next_due_date.setText(this.next_due_date);
            }
            if (this.recurring_amount == null || this.recurring_amount.equalsIgnoreCase("")) {
                this.tv_recurring_amount.setText(getResources().getString(R.string.N_A));
            } else {
                TextView textView = this.tv_recurring_amount;
                textView.setText(ClientSharepreferenceHandler.getUserPrefix(this.context) + this.recurring_amount + ClientSharepreferenceHandler.getUserSuffix(this.context));
            }
        }
        if (this.products == null || this.products.equalsIgnoreCase("")) {
            this.tv_product.setText(getResources().getString(R.string.N_A));
        } else {
            this.tv_product.setText(this.products);
        }
        if (this.status == null || this.status.equalsIgnoreCase("")) {
            this.tv_status.setText(getResources().getString(R.string.N_A));
            this.tv_title.setVisibility(8);
        } else {
            this.tv_status.setText(this.status);
            TextView textView2 = this.tv_title;
            textView2.setText(this.status + " Service Information");
        }
        if (this.Registration_date == null || this.Registration_date.equalsIgnoreCase("")) {
            this.tv_registration_date.setText(getResources().getString(R.string.N_A));
        } else {
            this.tv_registration_date.setText(this.Registration_date);
        }
        if (this.billing_cycle == null || this.billing_cycle.equalsIgnoreCase("")) {
            this.tv_billing_cycle.setText(getResources().getString(R.string.N_A));
        } else {
            this.tv_billing_cycle.setText(this.billing_cycle);
        }
        if (this.payment_method == null || this.payment_method.equalsIgnoreCase("")) {
            this.tv_payment_method.setText(getResources().getString(R.string.N_A));
        } else {
            this.tv_payment_method.setText(this.payment_method);
        }
        if (this.first_time_payment == null || this.first_time_payment.equalsIgnoreCase("")) {
            this.tv_first_time_payment.setText(getResources().getString(R.string.N_A));
        } else {
            TextView textView3 = this.tv_first_time_payment;
            textView3.setText(ClientSharepreferenceHandler.getUserPrefix(this.context) + this.first_time_payment + ClientSharepreferenceHandler.getUserSuffix(this.context));
        }
        this.btn_back.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.WHMCSClientapp.activities.ShowserviceInformationActivity.AnonymousClass1 */

            public void onClick(View view) {
                ShowserviceInformationActivity.this.onBackPressed();
            }
        });
        this.btn_service_home.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.WHMCSClientapp.activities.ShowserviceInformationActivity.AnonymousClass2 */

            public void onClick(View view) {
                Intent intent = new Intent(ShowserviceInformationActivity.this, ServicesDashboardActivity.class);
                intent.setFlags(67141632);
                ShowserviceInformationActivity.this.startActivity(intent);
                ShowserviceInformationActivity.this.finish();
            }
        });
        this.btn_service_home.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) this.btn_service_home, (Activity) this));
        this.btn_back.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) this.btn_back, (Activity) this));
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    ShowserviceInformationActivity.this.doWork();
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
            /* class com.nst.yourname.WHMCSClientapp.activities.ShowserviceInformationActivity.AnonymousClass3 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(ShowserviceInformationActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (ShowserviceInformationActivity.this.time != null) {
                        ShowserviceInformationActivity.this.time.setText(time);
                    }
                    if (ShowserviceInformationActivity.this.date != null) {
                        ShowserviceInformationActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }
}
