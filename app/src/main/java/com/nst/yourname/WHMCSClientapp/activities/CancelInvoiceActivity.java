package com.nst.yourname.WHMCSClientapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.ybq.android.spinkit.SpinKitView;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.CallBacks.InvoiceData;
import com.nst.yourname.WHMCSClientapp.adapters.UnpaidAdapter;
import com.nst.yourname.WHMCSClientapp.interfaces.InvoicesApiHitClass;
import com.nst.yourname.WHMCSClientapp.modelclassess.InvoicesModelClass;
import com.nst.yourname.miscelleneious.common.Utils;
import java.util.Calendar;
import java.util.List;

public class CancelInvoiceActivity extends AppCompatActivity implements InvoiceData {
    Context context;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.progress)
    SpinKitView progress;
    @BindView(R.id.recy_view)
    RecyclerView recyclerView;
    @BindView(R.id.text_not_found)
    TextView textNotFound;
    @BindView(R.id.time)
    TextView time;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_cancel_invoice);
        ButterKnife.bind(this);
        new Thread(new CountDown()).start();
        this.context = this;
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        new InvoicesApiHitClass(this, this.context, "Cancelled").InvoicesHitApi();
    }

    @Override // com.nst.yourname.WHMCSClientapp.CallBacks.InvoiceData
    public void getAllInvoiceResponse(List<InvoicesModelClass.Invoices.Invoice> list) {
        if (list == null || list.isEmpty() || list.size() <= 0) {
            this.progress.setVisibility(8);
            this.recyclerView.setVisibility(8);
            this.textNotFound.setVisibility(0);
            this.textNotFound.setText(getResources().getString(R.string.no_record));
            return;
        }
        onFinish();
        this.recyclerView.setAdapter(new UnpaidAdapter(this.context, list));
    }

    @Override // com.nst.yourname.WHMCSClientapp.CallBacks.InvoiceData
    public void getResultFailed(String str) {
        this.progress.setVisibility(8);
        this.recyclerView.setVisibility(8);
        this.textNotFound.setVisibility(0);
        this.textNotFound.setText(getResources().getString(R.string.no_record));
    }

    public void onFinish() {
        this.progress.setVisibility(8);
        this.recyclerView.setVisibility(0);
        this.textNotFound.setVisibility(8);
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        super.onBackPressed();
    }

    class CountDown implements Runnable {
        CountDown() {
        }

        public void run() {
            if (!Thread.currentThread().isInterrupted()) {
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }

        private void doWork() {
            CancelInvoiceActivity.this.runOnUiThread(new Runnable() {
                /* class com.nst.yourname.WHMCSClientapp.activities.CancelInvoiceActivity.CountDown.AnonymousClass1 */

                public void run() {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(CancelInvoiceActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (CancelInvoiceActivity.this.time != null) {
                        CancelInvoiceActivity.this.time.setText(time);
                    }
                    if (CancelInvoiceActivity.this.date != null) {
                        CancelInvoiceActivity.this.date.setText(date2);
                    }
                }
            });
        }
    }
}
