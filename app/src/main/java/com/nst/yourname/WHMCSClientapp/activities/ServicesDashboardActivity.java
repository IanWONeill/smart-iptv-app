package com.nst.yourname.WHMCSClientapp.activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.CallBacks.AllServiceApiCallBack;
import com.nst.yourname.WHMCSClientapp.Clientdatabase.ClientSharepreferenceHandler;
import com.nst.yourname.WHMCSClientapp.interfaces.ApiService;
import com.nst.yourname.WHMCSClientapp.interfaces.ApiclientRetrofit;
import com.nst.yourname.WHMCSClientapp.interfaces.CommanApiHitClass;
import com.nst.yourname.WHMCSClientapp.modelclassess.ActiveServiceModelClass;
import com.nst.yourname.WHMCSClientapp.modelclassess.BuyNowModelClass;
import com.nst.yourname.WHMCSClientapp.modelclassess.ServicesIncoiveTicketCoutModelClass;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.view.activity.AnnouncementsActivity;
import com.nst.yourname.view.activity.NewDashboardActivity;
import com.nst.yourname.view.activity.SettingsActivity;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.ArrayList;
import java.util.Calendar;
import org.joda.time.DateTimeConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class ServicesDashboardActivity extends AppCompatActivity implements AllServiceApiCallBack {
    static final boolean $assertionsDisabled = false;
    private static final int TIME_INTERVAL = 2000;
    public static PopupWindow logoutPopUp;
    @BindView(R.id.account_info)
    ImageView account_info;
    private String action = "";
    @BindView(R.id.btn_buy_now)
    Button btn_buy_now;
    Button closedBT;
    Context context;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.switch_user)
    ImageView ivSwitchUser;
    @BindView(R.id.iv_notification)
    ImageView iv_notification;
    @BindView(R.id.iv_settings)
    ImageView iv_settings;
    @BindView(R.id.ll_buy_now)
    LinearLayout ll_buy_now;
    @BindView(R.id.ll_invoices)
    LinearLayout ll_invoices;
    @BindView(R.id.ll_services)
    LinearLayout ll_services;
    @BindView(R.id.ll_tickets)
    LinearLayout ll_tickets;
    private long mBackPressed;
    @BindView(R.id.pb_loader_invoice)
    AVLoadingIndicatorView pb_loader_invoice;
    @BindView(R.id.pb_loader_service)
    AVLoadingIndicatorView pb_loader_service;
    @BindView(R.id.pb_loader_ticket)
    AVLoadingIndicatorView pb_loader_ticket;
    Button savePasswordBT;
    private String service_id = "";
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.tv_account_info_button)
    TextView tvAccountinfoButton;
    TextView tvLogoutAlert;
    TextView tvLogoutMessage;
    @BindView(R.id.tv_settings_button)
    TextView tvSettingsButton;
    @BindView(R.id.tv_switch_user_button)
    TextView tvSwitchUserButton;
    @BindView(R.id.tv_free_trial_title)
    TextView tv_free_trial_title;
    @BindView(R.id.tv_freetrai_time)
    TextView tv_freetrai_time;
    @BindView(R.id.tv_invoice_count)
    TextView tv_invoice_count;
    @BindView(R.id.tv_notification)
    TextView tv_notification;
    @BindView(R.id.tv_service_count)
    TextView tv_service_count;
    @BindView(R.id.tv_ticket_count)
    TextView tv_ticket_count;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_services_dashboard);
        ButterKnife.bind(this);
        this.context = this;
        new Thread(new CountDownRunner()).start();
        this.btn_buy_now.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.btn_buy_now));
        this.account_info.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.account_info));
        this.iv_settings.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.iv_settings));
        this.ivSwitchUser.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.ivSwitchUser));
        this.ll_tickets.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.ll_tickets));
        this.iv_notification.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.iv_notification));
        this.ll_invoices.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.ll_invoices));
        this.ll_services.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.ll_services));
        this.ll_services.requestFocus();
        if (!AppConst.MULTIUSER_ACTIVE.booleanValue()) {
            this.ivSwitchUser.setImageResource(R.drawable.logout_user);
            this.tvSwitchUserButton.setText(getResources().getString(R.string.menu_logout));
        }
        if (!ClientSharepreferenceHandler.getIsFreeTrail(this.context).contains("free trial")) {
            HitAPiForCheckBuyNow();
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        hitSerive_Invoice_ticket_countAPI();
        this.action = getIntent().getAction();
        if (this.action != null && !this.action.isEmpty() && this.action.equalsIgnoreCase(AppConst.ACTION_CHECK_BUY_NOW)) {
            Utils.hideProgressDialog();
            HitAPiForCheckBuyNow();
        }
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    ServicesDashboardActivity.this.doWork();
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
            /* class com.nst.yourname.WHMCSClientapp.activities.ServicesDashboardActivity.AnonymousClass1 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(ServicesDashboardActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (ServicesDashboardActivity.this.time != null) {
                        ServicesDashboardActivity.this.time.setText(time);
                    }
                    if (ServicesDashboardActivity.this.date != null) {
                        ServicesDashboardActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    public void hitSerive_Invoice_ticket_countAPI() {
        ((ApiService) ApiclientRetrofit.getApiRetrofit().create(ApiService.class)).getServiceInvoiceTicketCount(AppConst.BillingAPI_Username, AppConst.BillingAPI_Password, "sitcount", "yes", ClientSharepreferenceHandler.getClientId(this.context)).enqueue(new Callback<ServicesIncoiveTicketCoutModelClass>() {
            /* class com.nst.yourname.WHMCSClientapp.activities.ServicesDashboardActivity.AnonymousClass2 */

            @Override // retrofit2.Callback
            public void onFailure(@NonNull Call<ServicesIncoiveTicketCoutModelClass> call, @NonNull Throwable th) {
            }

            @Override // retrofit2.Callback
            public void onResponse(@NonNull Call<ServicesIncoiveTicketCoutModelClass> call, @NonNull Response<ServicesIncoiveTicketCoutModelClass> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Context context = ServicesDashboardActivity.this.context;
                    Toast.makeText(context, "" + response.code() + " | Error", 0).show();
                } else if (!response.body().getResult().equalsIgnoreCase("success")) {
                    Toast.makeText(ServicesDashboardActivity.this.context, AppConst.DB_UPDATED_STATUS_FAILED, 0).show();
                } else if (response.body().getData().getServicescount() != null && response.body().getData().getInvoicescount() != null && response.body().getData().getTicketscount() != null) {
                    int intValue = response.body().getData().getServicescount().getActive().intValue();
                    int intValue2 = response.body().getData().getInvoicescount().getUnpaid().intValue();
                    String totalresults = response.body().getData().getTicketscount().getTotalresults();
                    if (totalresults == null) {
                        totalresults = String.valueOf(0);
                    }
                    ServicesDashboardActivity.this.tv_service_count.setText(String.valueOf(intValue));
                    ServicesDashboardActivity.this.pb_loader_service.hide();
                    ServicesDashboardActivity.this.tv_service_count.setVisibility(0);
                    ServicesDashboardActivity.this.tv_invoice_count.setText(String.valueOf(intValue2));
                    ServicesDashboardActivity.this.pb_loader_invoice.hide();
                    ServicesDashboardActivity.this.tv_invoice_count.setVisibility(0);
                    ServicesDashboardActivity.this.tv_ticket_count.setText(totalresults);
                    ServicesDashboardActivity.this.pb_loader_ticket.hide();
                    ServicesDashboardActivity.this.tv_ticket_count.setVisibility(0);
                }
            }
        });
    }

    @OnClick({R.id.iv_notification, R.id.account_info, R.id.switch_user, R.id.ll_services, R.id.ll_invoices, R.id.ll_tickets, R.id.iv_settings, R.id.btn_buy_now})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.account_info:
                startActivity(new Intent(this, NewDashboardActivity.class));
                finish();
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                return;
            case R.id.btn_buy_now:
                HitServiceAPIFirst();
                return;
            case R.id.iv_notification:
                startActivity(new Intent(this, AnnouncementsActivity.class));
                return;
            case R.id.iv_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return;
            case R.id.ll_invoices:
                startActivity(new Intent(this, MyInvoiceActivity.class));
                return;
            case R.id.ll_services:
                startActivity(new Intent(this, MySerivcesActiviy.class));
                return;
            case R.id.ll_tickets:
                startActivity(new Intent(this, MyTicketActivity.class));
                return;
            case R.id.switch_user:
                if (AppConst.MULTIUSER_ACTIVE.booleanValue() && SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_API)) {
                    finish();
                    AppConst.SWITCH_FROM_DASHBOARD_TO_MULTIUSER = true;
                    Utils.logoutUser(this.context);
                    overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                    return;
                } else if (!AppConst.M3U_LINE_ACTIVE.booleanValue() || !SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                    showLogoutPopup();
                    return;
                } else {
                    finish();
                    AppConst.SWITCH_FROM_DASHBOARD_TO_MULTIUSER = true;
                    Utils.logoutUser(this.context);
                    overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                    return;
                }
            default:
                return;
        }
    }

    private void HitServiceAPIFirst() {
        Utils.showProgressDialog(this);
        new CommanApiHitClass(this, this.context, "Active").HitAllServicesApi();
    }

    private void HitAPiForCheckBuyNow() {
        Utils.showProgressDialog(this);
        int clientId = ClientSharepreferenceHandler.getClientId(this.context);
        String userName = SharepreferenceDBHandler.getUserName(this.context);
        String userPassword = SharepreferenceDBHandler.getUserPassword(this.context);
        ApiService apiService = (ApiService) ApiclientRetrofit.getApiRetrofit().create(ApiService.class);
        final String trial = SharepreferenceDBHandler.getTrial(this.context);
        apiService.getButNow(AppConst.BillingAPI_Username, AppConst.BillingAPI_Password, userName, userPassword, "buyNowButton", "yes", clientId).enqueue(new Callback<BuyNowModelClass>() {
            /* class com.nst.yourname.WHMCSClientapp.activities.ServicesDashboardActivity.AnonymousClass3 */

            @Override // retrofit2.Callback
            public void onResponse(@NonNull Call<BuyNowModelClass> call, @NonNull Response<BuyNowModelClass> response) {
                Utils.hideProgressDialog();
                if (!response.isSuccessful() || response.body() == null) {
                    ServicesDashboardActivity.this.ll_buy_now.setVisibility(8);
                } else if (response.body().getShowButton() == null) {
                    ServicesDashboardActivity.this.ll_buy_now.setVisibility(8);
                } else if (response.body().getShowButton().equalsIgnoreCase("yes")) {
                    ServicesDashboardActivity.this.ll_buy_now.setVisibility(0);
                    if (trial.equals("1")) {
                        ServicesDashboardActivity.this.reverseTimer(ClientSharepreferenceHandler.getFreeTrialTime(ServicesDashboardActivity.this.context), ServicesDashboardActivity.this.tv_freetrai_time);
                        return;
                    }
                    ServicesDashboardActivity.this.tv_free_trial_title.setVisibility(8);
                    ServicesDashboardActivity.this.tv_freetrai_time.setVisibility(8);
                } else {
                    if (trial.equals("1")) {
                        ServicesDashboardActivity.this.reverseTimer(ClientSharepreferenceHandler.getFreeTrialTime(ServicesDashboardActivity.this.context), ServicesDashboardActivity.this.tv_freetrai_time);
                    } else {
                        ServicesDashboardActivity.this.tv_free_trial_title.setVisibility(8);
                        ServicesDashboardActivity.this.tv_freetrai_time.setVisibility(8);
                    }
                    ClientSharepreferenceHandler.setIsFreeTrail("free trail", ServicesDashboardActivity.this.context);
                    ServicesDashboardActivity.this.ll_buy_now.setVisibility(8);
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(@NonNull Call<BuyNowModelClass> call, @NonNull Throwable th) {
                ServicesDashboardActivity.this.ll_buy_now.setVisibility(8);
            }
        });
    }

    @Override // com.nst.yourname.WHMCSClientapp.CallBacks.AllServiceApiCallBack
    public void getAllServiceResponse(ArrayList<ActiveServiceModelClass> arrayList) {
        Utils.hideProgressDialog();
        if (arrayList != null && arrayList.size() > 0 && arrayList.get(0) != null) {
            this.service_id = arrayList.get(0).getId();
            try {
                Intent intent = new Intent(this, BuyNowActivity.class);
                intent.putExtra("service_id", this.service_id);
                startActivity(intent);
            } catch (Exception unused) {
            }
        }
    }

    @Override // com.nst.yourname.WHMCSClientapp.CallBacks.AllServiceApiCallBack
    public void getAllServiceFailled(String str) {
        try {
            Utils.hideProgressDialog();
        } catch (Exception unused) {
            Utils.showToast(this.context, str);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
     arg types: [android.widget.Button, com.nst.yourname.WHMCSClientapp.activities.ServicesDashboardActivity]
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
    public void showLogoutPopup() {
        if (this.context != null) {
            View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate((int) R.layout.delete_recording_popup, (RelativeLayout) findViewById(R.id.rl_password_verification));
            logoutPopUp = new PopupWindow(this);
            logoutPopUp.setContentView(inflate);
            logoutPopUp.setWidth(-1);
            logoutPopUp.setHeight(-1);
            logoutPopUp.setFocusable(true);
            logoutPopUp.showAtLocation(inflate, 17, 0, 0);
            this.tvLogoutAlert = (TextView) inflate.findViewById(R.id.tv_parental_password);
            this.tvLogoutMessage = (TextView) inflate.findViewById(R.id.tv_delete_recording);
            this.savePasswordBT = (Button) inflate.findViewById(R.id.bt_start_recording);
            this.closedBT = (Button) inflate.findViewById(R.id.bt_close);
            if (this.tvLogoutAlert != null) {
                this.tvLogoutAlert.setText(getResources().getString(R.string.logout_title));
            }
            if (this.tvLogoutMessage != null) {
                this.tvLogoutMessage.setText(getResources().getString(R.string.logout_message));
            }
            if (this.savePasswordBT != null) {
                this.savePasswordBT.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) this.savePasswordBT, (Activity) this));
            }
            if (this.closedBT != null) {
                this.closedBT.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) this.closedBT, (Activity) this));
            }
            this.closedBT.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.WHMCSClientapp.activities.ServicesDashboardActivity.AnonymousClass4 */

                public void onClick(View view) {
                    ServicesDashboardActivity.logoutPopUp.dismiss();
                }
            });
            if (this.savePasswordBT != null) {
                this.savePasswordBT.setOnClickListener(new View.OnClickListener() {
                    /* class com.nst.yourname.WHMCSClientapp.activities.ServicesDashboardActivity.AnonymousClass5 */

                    public void onClick(View view) {
                        ServicesDashboardActivity.this.finish();
                        Utils.logoutUser(ServicesDashboardActivity.this.context);
                        ServicesDashboardActivity.this.overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                    }
                });
            }
        }
    }

    public void reverseTimer(int i, final TextView textView) {
        new CountDownTimer((long) ((i * 1000) + 1000), 1000) {
            /* class com.nst.yourname.WHMCSClientapp.activities.ServicesDashboardActivity.AnonymousClass6 */

            public void onTick(long j) {
                int i = (int) (j / 1000);
                int i2 = i / DateTimeConstants.SECONDS_PER_HOUR;
                int i3 = i - ((i2 * 60) * 60);
                int i4 = i3 / 60;
                //TextView textView = textView;
                textView.setText(" " + String.format("%02d", Integer.valueOf(i2)) + ":" + String.format("%02d", Integer.valueOf(i4)) + ":" + String.format("%02d", Integer.valueOf(i3 - (i4 * 60))) + " Hrs");
                if (i2 <= 3) {
                    ServicesDashboardActivity.this.tv_free_trial_title.setTextColor(ServicesDashboardActivity.this.getResources().getColor(R.color.red));
                    ServicesDashboardActivity.this.tv_freetrai_time.setTextColor(ServicesDashboardActivity.this.getResources().getColor(R.color.red));
                    AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
                    alphaAnimation.setDuration(1000);
                    alphaAnimation.setStartOffset(20);
                    alphaAnimation.setRepeatMode(2);
                    alphaAnimation.setRepeatCount(-1);
                    ServicesDashboardActivity.this.tv_free_trial_title.startAnimation(alphaAnimation);
                    ServicesDashboardActivity.this.tv_freetrai_time.startAnimation(alphaAnimation);
                }
            }

            public void onFinish() {
                textView.setText((int) R.string.end_now);
                ServicesDashboardActivity.this.tv_free_trial_title.setText((int) R.string.after_time_expried);
                ServicesDashboardActivity.this.tv_free_trial_title.setTextColor(ServicesDashboardActivity.this.getResources().getColor(R.color.red));
                ServicesDashboardActivity.this.tv_freetrai_time.setTextColor(ServicesDashboardActivity.this.getResources().getColor(R.color.red));
            }
        }.start();
    }

    private class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private final View view;

        public OnFocusChangeAccountListener(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            if (ServicesDashboardActivity.this.iv_settings.hasFocus()) {
                ServicesDashboardActivity.this.tvSettingsButton.setVisibility(0);
                ServicesDashboardActivity.this.tvSwitchUserButton.setVisibility(8);
                ServicesDashboardActivity.this.tvAccountinfoButton.setVisibility(8);
                ServicesDashboardActivity.this.tv_notification.setVisibility(8);
            } else if (ServicesDashboardActivity.this.ivSwitchUser.hasFocus()) {
                ServicesDashboardActivity.this.tvSwitchUserButton.setVisibility(0);
                ServicesDashboardActivity.this.tvSettingsButton.setVisibility(8);
                ServicesDashboardActivity.this.tvAccountinfoButton.setVisibility(8);
                ServicesDashboardActivity.this.tv_notification.setVisibility(8);
            } else if (ServicesDashboardActivity.this.account_info.hasFocus()) {
                ServicesDashboardActivity.this.tvAccountinfoButton.setVisibility(0);
                ServicesDashboardActivity.this.tvSwitchUserButton.setVisibility(8);
                ServicesDashboardActivity.this.tvSettingsButton.setVisibility(8);
                ServicesDashboardActivity.this.tv_notification.setVisibility(8);
            } else if (ServicesDashboardActivity.this.iv_notification.hasFocus()) {
                ServicesDashboardActivity.this.tvAccountinfoButton.setVisibility(8);
                ServicesDashboardActivity.this.tvSwitchUserButton.setVisibility(8);
                ServicesDashboardActivity.this.tvSettingsButton.setVisibility(8);
                ServicesDashboardActivity.this.tv_notification.setVisibility(0);
            } else {
                ServicesDashboardActivity.this.tvAccountinfoButton.setVisibility(8);
                ServicesDashboardActivity.this.tvSwitchUserButton.setVisibility(8);
                ServicesDashboardActivity.this.tvSettingsButton.setVisibility(8);
                ServicesDashboardActivity.this.tv_notification.setVisibility(8);
            }
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
                    this.view.setBackgroundResource(R.drawable.services_focus_dashboard_drawable);
                } else if (this.view.getTag().equals("2")) {
                    this.view.setBackgroundResource(R.drawable.invoice_focus_dashboard_drawable);
                } else if (this.view.getTag().equals("3")) {
                    this.view.setBackgroundResource(R.drawable.ticket_focus_dashboard_drawable);
                } else if (this.view.getTag().equals("4")) {
                    this.view.setBackgroundResource(R.drawable.green_focused);
                } else if (this.view.getTag().equals("11")) {
                    this.view.setBackgroundResource(R.drawable.buy_now_box_focus_drawable);
                }
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID))) {
                    ServicesDashboardActivity.this.savePasswordBT.setBackgroundResource(R.drawable.back_btn_effect);
                }
                if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("9")) {
                    ServicesDashboardActivity.this.closedBT.setBackgroundResource(R.drawable.logout_btn_effect);
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
                    this.view.setBackgroundResource(R.drawable.services_dashboard_drawable);
                } else if (this.view.getTag().equals("2")) {
                    this.view.setBackgroundResource(R.drawable.invoice_dashboard_drawable);
                } else if (this.view.getTag().equals("3")) {
                    this.view.setBackgroundResource(R.drawable.ticket_dashboard_drawable);
                } else if (this.view.getTag().equals("11")) {
                    this.view.setBackgroundResource(R.drawable.buy_now_box_drawable);
                }
                if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals(AppConst.DB_SERIES_STREAMS_ID))) {
                    ServicesDashboardActivity.this.savePasswordBT.setBackgroundResource(R.drawable.black_button_dark);
                }
                if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("9")) {
                    ServicesDashboardActivity.this.closedBT.setBackgroundResource(R.drawable.black_button_dark);
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
}
