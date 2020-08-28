package com.nst.yourname.WHMCSClientapp.activities;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.Clientdatabase.ClientSharepreferenceHandler;
import com.nst.yourname.WHMCSClientapp.interfaces.ApiService;
import com.nst.yourname.WHMCSClientapp.interfaces.ApiclientRetrofit;
import com.nst.yourname.WHMCSClientapp.modelclassess.ServicesIncoiveTicketCoutModelClass;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyInvoiceActivity extends AppCompatActivity {
    @BindView(R.id.cancel)
    LinearLayout cancel_box;
    public Context context;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.loader_show)
    AVLoadingIndicatorView loader_show;
    @BindView(R.id.loader_showcnl)
    AVLoadingIndicatorView loader_showcnl;
    @BindView(R.id.loader_showrf)
    AVLoadingIndicatorView loader_showrf;
    @BindView(R.id.loader_showup)
    AVLoadingIndicatorView loader_showup;
    @BindView(R.id.paid)
    LinearLayout paid_box;
    @BindView(R.id.refound)
    LinearLayout refound_box;
    @BindView(R.id.sow_cnl)
    TextView sow_cnl;
    @BindView(R.id.sow_no)
    TextView sow_no;
    @BindView(R.id.sow_rf)
    TextView sow_rf;
    @BindView(R.id.sow_up)
    TextView sow_up;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.unpaid)
    LinearLayout unpaid_box;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_my_invoice);
        ButterKnife.bind(this);
        this.context = this;
        new Thread(new CountTime()).start();
        this.paid_box.setOnFocusChangeListener(new onFocusChange(this.paid_box));
        this.unpaid_box.setOnFocusChangeListener(new onFocusChange(this.unpaid_box));
        this.cancel_box.setOnFocusChangeListener(new onFocusChange(this.cancel_box));
        this.refound_box.setOnFocusChangeListener(new onFocusChange(this.refound_box));
        hitSerive_Invoice_count();
    }

    @OnClick({R.id.paid, R.id.unpaid, R.id.cancel, R.id.refound})
    public void click(View view) {
        int id = view.getId();
        if (id == R.id.cancel) {
            startActivity(new Intent(this, CancelInvoiceActivity.class));
        } else if (id == R.id.paid) {
            startActivity(new Intent(this, PaidInvoiceActivity.class));
        } else if (id == R.id.refound) {
            startActivity(new Intent(this, InvoiceRefundedActivity.class));
        } else if (id == R.id.unpaid) {
            startActivity(new Intent(this, UnpiadInvoiceActivity.class));
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void hitSerive_Invoice_count() {
        ((ApiService) ApiclientRetrofit.getApiRetrofit().create(ApiService.class)).getServiceInvoiceTicketCount(AppConst.BillingAPI_Username, AppConst.BillingAPI_Password, "sitcount", "yes", ClientSharepreferenceHandler.getClientId(this.context)).enqueue(new Callback<ServicesIncoiveTicketCoutModelClass>() {
            /* class com.nst.yourname.WHMCSClientapp.activities.MyInvoiceActivity.AnonymousClass1 */

            @Override // retrofit2.Callback
            public void onResponse(@NonNull Call<ServicesIncoiveTicketCoutModelClass> call, @NonNull Response<ServicesIncoiveTicketCoutModelClass> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    Context access$000 = MyInvoiceActivity.this.context;
                    Toast.makeText(access$000, "" + response.code() + " | Error", 0).show();
                } else if (response.body().getData().getInvoicescount() != null) {
                    int intValue = response.body().getData().getInvoicescount().getPaid().intValue();
                    int intValue2 = response.body().getData().getInvoicescount().getUnpaid().intValue();
                    int intValue3 = response.body().getData().getInvoicescount().getRefunded().intValue();
                    int intValue4 = response.body().getData().getInvoicescount().getCancelled().intValue();
                    MyInvoiceActivity.this.sow_no.setText(String.valueOf(intValue));
                    MyInvoiceActivity.this.loader_show.setVisibility(8);
                    MyInvoiceActivity.this.sow_no.setVisibility(0);
                    MyInvoiceActivity.this.sow_up.setText(String.valueOf(intValue2));
                    MyInvoiceActivity.this.loader_showup.setVisibility(8);
                    MyInvoiceActivity.this.sow_up.setVisibility(0);
                    MyInvoiceActivity.this.sow_rf.setText(String.valueOf(intValue3));
                    MyInvoiceActivity.this.loader_showrf.setVisibility(8);
                    MyInvoiceActivity.this.sow_rf.setVisibility(0);
                    MyInvoiceActivity.this.sow_cnl.setText(String.valueOf(intValue4));
                    MyInvoiceActivity.this.loader_showcnl.setVisibility(8);
                    MyInvoiceActivity.this.sow_cnl.setVisibility(0);
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(@NonNull Call<ServicesIncoiveTicketCoutModelClass> call, @NonNull Throwable th) {
                MyInvoiceActivity.this.paid_box.setVisibility(8);
                MyInvoiceActivity.this.cancel_box.setVisibility(8);
                MyInvoiceActivity.this.refound_box.setVisibility(8);
                MyInvoiceActivity.this.unpaid_box.setVisibility(8);
                Toast.makeText(MyInvoiceActivity.this.context, "No Response from server", 0).show();
            }
        });
    }

    public class onFocusChange implements View.OnFocusChangeListener {
        final View view;

        public onFocusChange(View view2) {
            this.view = view2;
        }

        public void onFocusChange(View view2, boolean z) {
            float f = 1.09f;
            float f2 = 2.0f;
            if (z) {
                Log.e("id is", "" + this.view.getTag());
                if (this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_CAT_ID)) {
                    float f3 = z ? 2.0f : 1.0f;
                    performScaleXAnimation(f3);
                    performScaleYAnimation(f3);
                } else {
                    float f4 = z ? 1.09f : 1.0f;
                    performScaleXAnimation(f4);
                    performScaleYAnimation(f4);
                    if (this.view.getTag().equals("1")) {
                        this.view.setBackgroundResource(R.drawable.paid_box_focus);
                    }
                    if (this.view.getTag().equals("2")) {
                        this.view.setBackgroundResource(R.drawable.un_paid_box_focus);
                    }
                    if (this.view.getTag().equals("3")) {
                        this.view.setBackgroundResource(R.drawable.refounded_box_focus);
                    }
                    if (this.view.getTag().equals("4")) {
                        this.view.setBackgroundResource(R.drawable.cancel_box_focus);
                    }
                }
            }
            if (z) {
                return;
            }
            if (this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_CAT_ID)) {
                if (!z) {
                    f2 = 1.0f;
                }
                performScaleXAnimation(f2);
                performScaleYAnimation(f2);
                performScaleAlphaAnimation(z);
                return;
            }
            if (!z) {
                f = 1.0f;
            }
            performScaleXAnimation(f);
            performScaleYAnimation(f);
            performScaleAlphaAnimation(z);
            if (this.view.getTag().equals("1")) {
                this.view.setBackgroundResource(R.drawable.paid_box);
            }
            if (this.view.getTag().equals("2")) {
                this.view.setBackgroundResource(R.drawable.un_paid_box);
            }
            if (this.view.getTag().equals("3")) {
                this.view.setBackgroundResource(R.drawable.refounded_box);
            }
            if (this.view.getTag().equals("4")) {
                this.view.setBackgroundResource(R.drawable.cancel_box);
            }
        }

        public void performScaleXAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleX", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        public void performScaleYAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleY", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        public void performScaleAlphaAnimation(boolean z) {
            if (z) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "alpha", z ? 0.6f : 0.5f);
                ofFloat.setDuration(150L);
                ofFloat.start();
            }
        }
    }

    class CountTime implements Runnable {
        CountTime() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    countAdd();
                    Thread.sleep(1000);
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        private void countAdd() {
            MyInvoiceActivity.this.runOnUiThread(new Runnable() {
                /* class com.nst.yourname.WHMCSClientapp.activities.MyInvoiceActivity.CountTime.AnonymousClass1 */

                public void run() {
                    try {
                        String date = Calendar.getInstance().getTime().toString();
                        String time = Utils.getTime(MyInvoiceActivity.this.context);
                        String date2 = Utils.getDate(date);
                        if (MyInvoiceActivity.this.time != null) {
                            MyInvoiceActivity.this.time.setText(time);
                        }
                        if (MyInvoiceActivity.this.date != null) {
                            MyInvoiceActivity.this.date.setText(date2);
                        }
                    } catch (Exception unused) {
                    }
                }
            });
        }
    }
}
