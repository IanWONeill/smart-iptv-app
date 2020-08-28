package com.nst.yourname.WHMCSClientapp.activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
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

public class MySerivcesActiviy extends AppCompatActivity {
    public Context context;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.ll_active)
    LinearLayout ll_active;
    @BindView(R.id.ll_cancelled)
    LinearLayout ll_cancelled;
    @BindView(R.id.ll_fraud)
    LinearLayout ll_fraud;
    @BindView(R.id.ll_pending)
    LinearLayout ll_pending;
    @BindView(R.id.ll_suspended)
    LinearLayout ll_suspended;
    @BindView(R.id.ll_terminated)
    LinearLayout ll_terminated;
    @BindView(R.id.pb_loader_active)
    AVLoadingIndicatorView pb_loader_active;
    @BindView(R.id.pb_loader_cancelled)
    AVLoadingIndicatorView pb_loader_cancelled;
    @BindView(R.id.pb_loader_fraud)
    AVLoadingIndicatorView pb_loader_fraud;
    @BindView(R.id.pb_loader_pending)
    AVLoadingIndicatorView pb_loader_pending;
    @BindView(R.id.pb_loader_suspended)
    AVLoadingIndicatorView pb_loader_suspended;
    @BindView(R.id.pb_loader_terminated)
    AVLoadingIndicatorView pb_loader_terminated;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.tv_active_count)
    TextView tv_active_count;
    @BindView(R.id.tv_cancelled_count)
    TextView tv_cancelled_count;
    @BindView(R.id.tv_fraud_count)
    TextView tv_fraud_count;
    @BindView(R.id.tv_pending_count)
    TextView tv_pending_count;
    @BindView(R.id.tv_suspended_count)
    TextView tv_suspended_count;
    @BindView(R.id.tv_terminated_count)
    TextView tv_terminated_count;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_my_serivces_activiy);
        ButterKnife.bind(this);
        this.context = this;
        new Thread(new CountDownRunner()).start();
        this.ll_active.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.ll_active));
        this.ll_cancelled.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.ll_cancelled));
        this.ll_pending.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.ll_pending));
        this.ll_suspended.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.ll_suspended));
        this.ll_terminated.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.ll_terminated));
        this.ll_fraud.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.ll_fraud));
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        hitSerive_Invoice_ticket_countAPI();
    }

    public void hitSerive_Invoice_ticket_countAPI() {
        ((ApiService) ApiclientRetrofit.getApiRetrofit().create(ApiService.class)).getServiceInvoiceTicketCount(AppConst.BillingAPI_Username, AppConst.BillingAPI_Password, "sitcount", "yes", ClientSharepreferenceHandler.getClientId(this.context)).enqueue(new Callback<ServicesIncoiveTicketCoutModelClass>() {
            /* class com.nst.yourname.WHMCSClientapp.activities.MySerivcesActiviy.AnonymousClass1 */

            @Override // retrofit2.Callback
            public void onResponse(@NonNull Call<ServicesIncoiveTicketCoutModelClass> call, @NonNull Response<ServicesIncoiveTicketCoutModelClass> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Context access$000 = MySerivcesActiviy.this.context;
                    Toast.makeText(access$000, "" + response.code() + " | Error", 0).show();
                } else if (!response.body().getResult().equalsIgnoreCase("success")) {
                    Toast.makeText(MySerivcesActiviy.this.context, AppConst.DB_UPDATED_STATUS_FAILED, 0).show();
                } else if (response.body().getData().getServicescount() != null && response.body().getData().getInvoicescount() != null && response.body().getData().getTicketscount() != null) {
                    int intValue = response.body().getData().getServicescount().getActive().intValue();
                    int intValue2 = response.body().getData().getServicescount().getCancelled().intValue();
                    int intValue3 = response.body().getData().getServicescount().getPending().intValue();
                    int intValue4 = response.body().getData().getServicescount().getSuspended().intValue();
                    int intValue5 = response.body().getData().getServicescount().getTerminated().intValue();
                    int intValue6 = response.body().getData().getServicescount().getFraud().intValue();
                    MySerivcesActiviy.this.tv_active_count.setText(String.valueOf(intValue));
                    MySerivcesActiviy.this.pb_loader_active.hide();
                    MySerivcesActiviy.this.tv_active_count.setVisibility(0);
                    MySerivcesActiviy.this.tv_cancelled_count.setText(String.valueOf(intValue2));
                    MySerivcesActiviy.this.pb_loader_cancelled.hide();
                    MySerivcesActiviy.this.tv_cancelled_count.setVisibility(0);
                    MySerivcesActiviy.this.tv_pending_count.setText(String.valueOf(intValue3));
                    MySerivcesActiviy.this.pb_loader_pending.hide();
                    MySerivcesActiviy.this.tv_pending_count.setVisibility(0);
                    MySerivcesActiviy.this.tv_suspended_count.setText(String.valueOf(intValue4));
                    MySerivcesActiviy.this.pb_loader_suspended.hide();
                    MySerivcesActiviy.this.tv_suspended_count.setVisibility(0);
                    MySerivcesActiviy.this.tv_terminated_count.setText(String.valueOf(intValue5));
                    MySerivcesActiviy.this.pb_loader_terminated.hide();
                    MySerivcesActiviy.this.tv_terminated_count.setVisibility(0);
                    MySerivcesActiviy.this.tv_fraud_count.setText(String.valueOf(intValue6));
                    MySerivcesActiviy.this.pb_loader_fraud.hide();
                    MySerivcesActiviy.this.tv_fraud_count.setVisibility(0);
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(@NonNull Call<ServicesIncoiveTicketCoutModelClass> call, @NonNull Throwable th) {
                MySerivcesActiviy.this.hitSerive_Invoice_ticket_countAPI();
            }
        });
    }

    @OnClick({R.id.ll_active, R.id.ll_cancelled, R.id.ll_pending, R.id.ll_suspended, R.id.ll_terminated, R.id.ll_fraud})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.ll_active:
                startActivity(new Intent(this, ActiveServiceActivity.class));
                return;
            case R.id.ll_cancelled:
                startActivity(new Intent(this, CancelledSerivceActivity.class));
                return;
            case R.id.ll_fraud:
                startActivity(new Intent(this, FraudServiceActivity.class));
                return;
            case R.id.ll_pending:
                startActivity(new Intent(this, PendingServiceActivity.class));
                return;
            case R.id.ll_suspended:
                startActivity(new Intent(this, SuspendedServiceActivity.class));
                return;
            case R.id.ll_terminated:
                startActivity(new Intent(this, TerminatedServiceActivity.class));
                return;
            default:
                return;
        }
    }

    private class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private final View view;

        public OnFocusChangeAccountListener(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            float f = 1.09f;
            float f2 = 2.0f;
            if (z) {
                Log.e("id is", "" + this.view.getTag());
                if (this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_CAT_ID)) {
                    if (!z) {
                        f2 = 1.0f;
                    }
                    performScaleXAnimation(f2);
                    performScaleYAnimation(f2);
                    return;
                }
                if (!z) {
                    f = 1.0f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                if (this.view.getTag().equals("1")) {
                    this.view.setBackgroundResource(R.drawable.active_drawable_box_focus);
                } else if (this.view.getTag().equals("2")) {
                    this.view.setBackgroundResource(R.drawable.cancel_box_focus);
                } else if (this.view.getTag().equals("3")) {
                    this.view.setBackgroundResource(R.drawable.ticket_focus_dashboard_drawable);
                } else if (this.view.getTag().equals("4")) {
                    this.view.setBackgroundResource(R.drawable.suspended_focus_dashboard_drawable);
                } else if (this.view.getTag().equals("5")) {
                    this.view.setBackgroundResource(R.drawable.terminated_focus_dashboard_drawable);
                } else if (this.view.getTag().equals("6")) {
                    this.view.setBackgroundResource(R.drawable.fraud_focus_dashboard_drawable);
                }
            } else if (z) {
            } else {
                if (this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_CAT_ID)) {
                    if (!z) {
                        f2 = 1.0f;
                    }
                    performScaleXAnimation(f2);
                    performScaleYAnimation(f2);
                    performAlphaAnimation(z);
                    return;
                }
                if (!z) {
                    f = 1.0f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                performAlphaAnimation(z);
                if (this.view.getTag().equals("1")) {
                    this.view.setBackgroundResource(R.drawable.active_drawable);
                } else if (this.view.getTag().equals("2")) {
                    this.view.setBackgroundResource(R.drawable.cancel_box);
                } else if (this.view.getTag().equals("3")) {
                    this.view.setBackgroundResource(R.drawable.ticket_dashboard_drawable);
                } else if (this.view.getTag().equals("4")) {
                    this.view.setBackgroundResource(R.drawable.suspended_dashboard_drawable);
                } else if (this.view.getTag().equals("5")) {
                    this.view.setBackgroundResource(R.drawable.terminated_dashboard_drawable);
                } else if (this.view.getTag().equals("6")) {
                    this.view.setBackgroundResource(R.drawable.fraud_dashboard_drawable);
                }
            }
        }

        private void performScaleXAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleX", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        private void performScaleYAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleY", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        private void performAlphaAnimation(boolean z) {
            if (z) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "alpha", z ? 0.6f : 0.5f);
                ofFloat.setDuration(150L);
                ofFloat.start();
            }
        }
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    MySerivcesActiviy.this.doWork();
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
            /* class com.nst.yourname.WHMCSClientapp.activities.MySerivcesActiviy.AnonymousClass2 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(MySerivcesActiviy.this.context);
                    String date2 = Utils.getDate(date);
                    if (MySerivcesActiviy.this.time != null) {
                        MySerivcesActiviy.this.time.setText(time);
                    }
                    if (MySerivcesActiviy.this.date != null) {
                        MySerivcesActiviy.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }
}
