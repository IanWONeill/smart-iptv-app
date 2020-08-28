package com.nst.yourname.WHMCSClientapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.Clientdatabase.ClientSharepreferenceHandler;
import com.nst.yourname.WHMCSClientapp.modelclassess.InvoicesModelClass;
import com.nst.yourname.miscelleneious.common.AppConst;
import java.util.List;

public class InvoicePaidDetailActivity extends AppCompatActivity {
    @BindView(R.id.back)
    Button back;
    private String clientEmail = "";
    Context context;
    FragmentActivity fragmentActivity;
    private String invoice = "";
    InvoicesModelClass.Invoices.Invoice invoice1;
    List<InvoicesModelClass.Invoices.Invoice> invoiceList;
    private String status = "";
    @BindView(R.id.webview)
    WebView webview;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_invoice_paid_detail);
        ButterKnife.bind(this);
        this.context = this;
        Intent intent = getIntent();
        this.status = intent.getStringExtra(NotificationCompat.CATEGORY_STATUS);
        this.invoice = intent.getStringExtra("invoice_id");
        showData();
    }

    private void showData() {
        if (this.invoice != null) {
            this.webview = (WebView) findViewById(R.id.webview);
            WebView webView = this.webview;
            webView.loadUrl("http://tvplushd.com/billing/viewinvoice.php?id=" + this.invoice + "&loginemail=" + ClientSharepreferenceHandler.getUserEmaiId(this.context) + "&api_username=" + AppConst.BillingAPI_Username + "&gotourl=viewinvoice.php?id=" + this.invoice);
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MyInvoiceActivity.class));
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            if (this.status.equalsIgnoreCase("paid")) {
                startActivity(new Intent(this, PaidInvoiceActivity.class));
            } else if (this.status.equalsIgnoreCase("Unpaid")) {
                startActivity(new Intent(this, UnpiadInvoiceActivity.class));
            } else if (this.status.equalsIgnoreCase("Cancelled")) {
                startActivity(new Intent(this, CancelInvoiceActivity.class));
            } else if (this.status.equalsIgnoreCase("Refund")) {
                startActivity(new Intent(this, InvoiceRefundedActivity.class));
            } else {
                startActivity(new Intent(this, MyInvoiceActivity.class));
            }
        }
    }
}
