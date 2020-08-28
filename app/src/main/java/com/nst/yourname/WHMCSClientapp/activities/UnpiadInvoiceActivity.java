package com.nst.yourname.WHMCSClientapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
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

public class UnpiadInvoiceActivity extends AppCompatActivity implements InvoiceData {
    private Animation animationDown;
    private Animation animationUp;
    Context context;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.no_record_found)
    TextView noRecordFound;
    @BindView(R.id.progress)
    SpinKitView progress;
    @BindView(R.id.recyl_view)
    RecyclerView recyclerView;
    @BindView(R.id.time)
    TextView time;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_unpiad_invoice);
        ButterKnife.bind(this);
        this.context = this;
        new Thread(new countDown()).start();
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        new InvoicesApiHitClass(this, this.context, "UnPaid").InvoicesHitApi();
    }

    @Override // com.nst.yourname.WHMCSClientapp.CallBacks.InvoiceData
    public void getAllInvoiceResponse(List<InvoicesModelClass.Invoices.Invoice> list) {
        if (list == null || list.isEmpty() || list.size() <= 0) {
            this.recyclerView.setVisibility(8);
            this.noRecordFound.setVisibility(0);
            this.progress.setVisibility(8);
            this.noRecordFound.setText(getResources().getString(R.string.no_record));
            return;
        }
        onFinish();
        this.recyclerView.setAdapter(new UnpaidAdapter(this.context, list));
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override // com.nst.yourname.WHMCSClientapp.CallBacks.InvoiceData
    public void getResultFailed(String str) {
        this.recyclerView.setVisibility(8);
        this.noRecordFound.setVisibility(0);
        this.progress.setVisibility(8);
        this.noRecordFound.setText(getResources().getString(R.string.no_record));
    }

    public void onFinish() {
        this.progress.setVisibility(8);
        this.recyclerView.setVisibility(0);
        this.noRecordFound.setVisibility(8);
    }

    public class countDown implements Runnable {
        public countDown() {
        }

        public void run() {
            if (!Thread.currentThread().isInterrupted()) {
                try {
                    find();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }

        private void find() {
            UnpiadInvoiceActivity.this.runOnUiThread(new Runnable() {
                /* class com.nst.yourname.WHMCSClientapp.activities.UnpiadInvoiceActivity.countDown.AnonymousClass1 */

                public void run() {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(UnpiadInvoiceActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (UnpiadInvoiceActivity.this.time != null) {
                        UnpiadInvoiceActivity.this.time.setText(time);
                    }
                    if (UnpiadInvoiceActivity.this.date != null) {
                        UnpiadInvoiceActivity.this.date.setText(date2);
                    }
                }
            });
        }
    }
}
