package com.nst.yourname.WHMCSClientapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.ybq.android.spinkit.SpinKitView;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.CallBacks.AllServiceApiCallBack;
import com.nst.yourname.WHMCSClientapp.adapters.MyAllServiceAdapter;
import com.nst.yourname.WHMCSClientapp.interfaces.CommanApiHitClass;
import com.nst.yourname.WHMCSClientapp.modelclassess.ActiveServiceModelClass;
import com.nst.yourname.miscelleneious.common.Utils;
import java.util.ArrayList;
import java.util.Calendar;

public class CancelledSerivceActivity extends AppCompatActivity implements AllServiceApiCallBack {
    Context context;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.progress)
    SpinKitView progress;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.tv_no_active_services)
    TextView tv_no_active_services;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_cancelled_serivce);
        ButterKnife.bind(this);
        this.tv_title.setText(getResources().getString(R.string.my_series_my_cancel_service));
        this.context = this;
        new Thread(new CountDownRunner()).start();
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        new CommanApiHitClass(this, this.context, "Cancelled").HitAllServicesApi();
    }

    @Override // com.nst.yourname.WHMCSClientapp.CallBacks.AllServiceApiCallBack
    public void getAllServiceResponse(ArrayList<ActiveServiceModelClass> arrayList) {
        if (arrayList == null || arrayList.size() <= 0) {
            this.recyclerView.setVisibility(8);
            this.tv_no_active_services.setVisibility(0);
            this.progress.setVisibility(8);
            return;
        }
        onFinish();
        this.recyclerView.setAdapter(new MyAllServiceAdapter(this.context, arrayList));
    }

    @Override // com.nst.yourname.WHMCSClientapp.CallBacks.AllServiceApiCallBack
    public void getAllServiceFailled(String str) {
        Context context2 = this.context;
        Toast.makeText(context2, "" + str, 0).show();
        this.progress.setVisibility(8);
        this.recyclerView.setVisibility(0);
        this.tv_no_active_services.setVisibility(0);
    }

    public void onFinish() {
        this.progress.setVisibility(8);
        this.recyclerView.setVisibility(0);
        this.tv_no_active_services.setVisibility(8);
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    CancelledSerivceActivity.this.doWork();
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
            /* class com.nst.yourname.WHMCSClientapp.activities.CancelledSerivceActivity.AnonymousClass1 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(CancelledSerivceActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (CancelledSerivceActivity.this.time != null) {
                        CancelledSerivceActivity.this.time.setText(time);
                    }
                    if (CancelledSerivceActivity.this.date != null) {
                        CancelledSerivceActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }
}
