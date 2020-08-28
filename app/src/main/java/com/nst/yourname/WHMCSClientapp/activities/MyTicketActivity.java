package com.nst.yourname.WHMCSClientapp.activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.ybq.android.spinkit.SpinKitView;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.Clientdatabase.ClientSharepreferenceHandler;
import com.nst.yourname.WHMCSClientapp.adapters.TicketAdapter;
import com.nst.yourname.WHMCSClientapp.interfaces.ApiService;
import com.nst.yourname.WHMCSClientapp.interfaces.ApiclientRetrofit;
import com.nst.yourname.WHMCSClientapp.modelclassess.TicketModelClass;
import com.nst.yourname.miscelleneious.common.AppConst;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTicketActivity extends AppCompatActivity {
    public Context context;
    boolean ishit = false;
    @BindView(R.id.iv_fab)
    ImageView iv_fab;
    @BindView(R.id.ll_floating_button)
    LinearLayout llFloatingButton;
    @BindView(R.id.ll_recycleview)
    LinearLayout llRecycleview;
    public RecyclerView.Adapter mAdapter;
    @BindView(R.id.progress)
    SpinKitView progress;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_no_support_ticket)
    TextView tvNoSupportTicket;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_my_ticket);
        ButterKnife.bind(this);
        this.context = this;
        this.iv_fab.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.iv_fab));
        String action = getIntent().getAction();
        if (action != null && action.equalsIgnoreCase("updateticket") && !this.ishit) {
            countTickets();
            this.ishit = true;
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        if (!this.ishit) {
            countTickets();
            this.ishit = true;
        }
    }

    private void countTickets() {
        ((ApiService) ApiclientRetrofit.getApiRetrofit().create(ApiService.class)).gettickets(AppConst.BillingAPI_Username, AppConst.BillingAPI_Password, "GetTickets", "no", ClientSharepreferenceHandler.getClientId(this.context)).enqueue(new Callback<TicketModelClass>() {
            /* class com.nst.yourname.WHMCSClientapp.activities.MyTicketActivity.AnonymousClass1 */

            @Override // retrofit2.Callback
            public void onResponse(@NonNull Call<TicketModelClass> call, @NonNull Response<TicketModelClass> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    MyTicketActivity.this.onFinish(false);
                } else if (response.body() == null || !response.body().getResult().equalsIgnoreCase("success")) {
                    MyTicketActivity.this.onFinish(false);
                } else {
                    MyTicketActivity.this.onFinish(true);
                    if (response.body().getTickets() != null) {
                        List<TicketModelClass.Tickets.Ticket> ticket = response.body().getTickets().getTicket();
                        MyTicketActivity.this.recyclerView.setLayoutManager(new GridLayoutManager(MyTicketActivity.this, 2));
                        if (ticket != null && ticket.size() > 0) {
                            RecyclerView.Adapter unused = MyTicketActivity.this.mAdapter = new TicketAdapter(ticket, MyTicketActivity.this.context);
                            MyTicketActivity.this.recyclerView.setAdapter(MyTicketActivity.this.mAdapter);
                            MyTicketActivity.this.mAdapter.notifyDataSetChanged();
                            return;
                        }
                        return;
                    }
                    MyTicketActivity.this.onFinish(false);
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(@NonNull Call<TicketModelClass> call, @NonNull Throwable th) {
                Toast.makeText(MyTicketActivity.this, AppConst.NETWORK_ERROR_OCCURED, 0).show();
                MyTicketActivity.this.onFinish(false);
            }
        });
    }

    @OnClick({R.id.iv_fab})
    public void onViewClicked() {
        startActivity(new Intent(this, OpenTicketActivity.class));
    }

    public void onFinish(Boolean bool) {
        if (bool.booleanValue()) {
            this.recyclerView.setVisibility(0);
        } else {
            this.tvNoSupportTicket.setVisibility(0);
        }
        this.progress.setVisibility(8);
    }

    private class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private final View view;

        public OnFocusChangeAccountListener(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            float f = 1.0f;
            if (z) {
                Log.e("id is", "" + this.view.getTag());
                if (this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_CAT_ID)) {
                    if (z) {
                        f = 1.25f;
                    }
                    performScaleXAnimation(f);
                    performScaleYAnimation(f);
                }
            } else if (!z && this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_CAT_ID)) {
                if (z) {
                    f = 1.25f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                performAlphaAnimation(z);
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
}
