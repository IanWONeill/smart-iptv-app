package com.nst.yourname.WHMCSClientapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.Clientdatabase.ClientSharepreferenceHandler;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import java.util.Calendar;

public class BuyNowActivity extends AppCompatActivity {
    private String action = "";
    Context context;
    @BindView(R.id.date)
    TextView date;
    private String invoice = "";
    ProgressDialog progressDialog;
    String service_id = "";
    String service_url = "";
    private String status = "";
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.webview)
    WebView webview;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_buy_now);
        ButterKnife.bind(this);
        this.tv_title.setText(getResources().getString(R.string.Service_Buy_Now));
        Intent intent = getIntent();
        this.action = intent.getAction();
        new Thread(new CountDownRunner()).start();
        this.context = this;
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage(this.context.getResources().getString(R.string.please_wait_invoice));
        this.progressDialog.show();
        this.progressDialog.setCancelable(false);
        if (this.context != null) {
            this.progressDialog.setMessage(this.context.getResources().getString(R.string.please_wait_invoice));
        }
        this.webview.getSettings().setJavaScriptEnabled(true);
        this.webview.getSettings().setLoadWithOverviewMode(true);
        this.webview.getSettings().setUseWideViewPort(true);
        this.webview.setWebViewClient(new WebViewClient() {
            /* class com.nst.yourname.WHMCSClientapp.activities.BuyNowActivity.AnonymousClass1 */

            public void onPageFinished(WebView webView, String str) {
                BuyNowActivity.this.progressDialog.dismiss();
                BuyNowActivity.this.webview.setVisibility(0);
            }
        });
        if (this.action == null || !this.action.equalsIgnoreCase(AppConst.ACTION_INVOICE)) {
            this.service_id = intent.getStringExtra("service_id");
            SericePayment();
            return;
        }
        this.status = intent.getStringExtra(NotificationCompat.CATEGORY_STATUS);
        this.invoice = intent.getStringExtra("invoice_id");
        InvoicePayment();
    }

    private void SericePayment() {
        WebView webView = this.webview;
        webView.loadUrl("http://tvplushd.com/billing/upgrade.php?type=package&id=" + this.service_id + "&loginemail=" + ClientSharepreferenceHandler.getUserEmaiId(this) + "&api_username=" + AppConst.BillingAPI_Username + "&gotourl=" + AppConst.BUY_NOW_GOTO_URL);
    }

    private void InvoicePayment() {
        if (this.invoice != null) {
            WebView webView = this.webview;
            webView.loadUrl("http://tvplushd.com/billing/viewinvoice.php?id=" + this.invoice + "&loginemail=" + ClientSharepreferenceHandler.getUserEmaiId(this.context) + "&api_username=" + AppConst.BillingAPI_Username + "&gotourl=viewinvoice.php?id=" + this.invoice);
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        Intent intent = new Intent(this, ServicesDashboardActivity.class);
        intent.setFlags(67141632);
        intent.setAction(AppConst.ACTION_CHECK_BUY_NOW);
        startActivity(intent);
        finish();
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    BuyNowActivity.this.doWork();
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
            /* class com.nst.yourname.WHMCSClientapp.activities.BuyNowActivity.AnonymousClass2 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(BuyNowActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (BuyNowActivity.this.time != null) {
                        BuyNowActivity.this.time.setText(time);
                    }
                    if (BuyNowActivity.this.date != null) {
                        BuyNowActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }
}
