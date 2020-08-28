package com.nst.yourname.WHMCSClientapp.activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.ybq.android.spinkit.SpinKitView;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.Clientdatabase.ClientSharepreferenceHandler;
import com.nst.yourname.WHMCSClientapp.adapters.TicketMessageAdapter;
import com.nst.yourname.WHMCSClientapp.interfaces.ApiService;
import com.nst.yourname.WHMCSClientapp.interfaces.ApiclientRetrofit;
import com.nst.yourname.WHMCSClientapp.modelclassess.TickedMessageModelClass;
import com.nst.yourname.WHMCSClientapp.modelclassess.TicketModelClass;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTicketActivity extends AppCompatActivity {
    AlertDialog alertDialog;
    @BindView(R.id.bt_reply)
    Button btReply;
    @BindView(R.id.bt_cancel_reply)
    Button bt_cancel_reply;
    public Context context;
    @BindView(R.id.date)
    TextView date;
    public Bundle getBundle = null;
    @BindView(R.id.ll_replay)
    LinearLayout ll_replay;
    public RecyclerView.Adapter mAdapter;
    @BindView(R.id.progress)
    SpinKitView progress;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<TicketModelClass.Tickets.Ticket> replylist;
    @BindView(R.id.text_not_found)
    TextView textNotFound;
    List<TickedMessageModelClass.Replies.Reply> ticketMessageList;
    String ticketid = "";
    @BindView(R.id.time)
    TextView time;
    String title = "";
    @BindView(R.id.tv_title)
    TextView tv_title;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
     arg types: [android.widget.Button, com.nst.yourname.WHMCSClientapp.activities.ViewTicketActivity]
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
        setContentView((int) R.layout.activity_view_ticket);
        ButterKnife.bind(this);
        this.context = this;
        new Thread(new CountDownRunner()).start();
        this.btReply.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) this.btReply, (Activity) this));
        this.bt_cancel_reply.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) this.bt_cancel_reply, (Activity) this));
        Intent intent = getIntent();
        this.ticketid = intent.getStringExtra("ticketid");
        this.title = intent.getStringExtra("Title");
        if (this.title == null || this.title.equalsIgnoreCase("")) {
            this.tv_title.setVisibility(8);
        } else {
            TextView textView = this.tv_title;
            textView.setText("#" + this.title);
        }
        GetTicketMessages();
    }

    class CountDownRunner implements Runnable {
        CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    ViewTicketActivity.this.doWork();
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
            /* class com.nst.yourname.WHMCSClientapp.activities.ViewTicketActivity.AnonymousClass1 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(ViewTicketActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (ViewTicketActivity.this.time != null) {
                        ViewTicketActivity.this.time.setText(time);
                    }
                    if (ViewTicketActivity.this.date != null) {
                        ViewTicketActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    @OnClick({R.id.bt_reply, R.id.bt_cancel_reply})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.bt_cancel_reply) {
            onBackPressed();
        } else if (id == R.id.bt_reply) {
            showReplyPopup();
        }
    }

    public void hitReplyApi(String str, final AlertDialog alertDialog2) {
        if (this.context != null) {
            Utils.showProgressDialog(this);
            ((ApiService) ApiclientRetrofit.getApiRetrofit().create(ApiService.class)).getreply(AppConst.BillingAPI_Username, AppConst.BillingAPI_Password, "AddTicketReply", "no", str, ClientSharepreferenceHandler.getClientId(this.context), this.ticketid).enqueue(new Callback<TicketModelClass>() {
                /* class com.nst.yourname.WHMCSClientapp.activities.ViewTicketActivity.AnonymousClass2 */

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<TicketModelClass> call, @NonNull Response<TicketModelClass> response) {
                    Utils.hideProgressDialog();
                    if (!response.isSuccessful() || response.body() == null) {
                        Toast.makeText(ViewTicketActivity.this.context, "Response Error", 0).show();
                    } else if (response.body().getResult().equals("success")) {
                        Toast.makeText(ViewTicketActivity.this.getApplicationContext(), "Your ticket added successfully", 0).show();
                        if (alertDialog2 != null) {
                            alertDialog2.dismiss();
                        }
                        ViewTicketActivity.this.GetTicketMessages();
                    } else {
                        Toast.makeText(ViewTicketActivity.this.context, "Error", 0).show();
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<TicketModelClass> call, @NonNull Throwable th) {
                    Toast.makeText(ViewTicketActivity.this.context, AppConst.NETWORK_ERROR_OCCURED, 0).show();
                    Utils.hideProgressDialog();
                }
            });
        }
    }

    public void GetTicketMessages() {
        ((ApiService) ApiclientRetrofit.getApiRetrofit().create(ApiService.class)).getAllTicketMessage(AppConst.BillingAPI_Username, AppConst.BillingAPI_Password, "GetTicket", "no", this.ticketid).enqueue(new Callback<TickedMessageModelClass>() {
            /* class com.nst.yourname.WHMCSClientapp.activities.ViewTicketActivity.AnonymousClass3 */

            @Override // retrofit2.Callback
            public void onResponse(@NonNull Call<TickedMessageModelClass> call, @NonNull Response<TickedMessageModelClass> response) {
                if (!response.isSuccessful() || response.body() == null || !response.body().getResult().equals("success")) {
                    ViewTicketActivity.this.onFinish(false);
                    return;
                }
                ViewTicketActivity.this.onFinish(true);
                ViewTicketActivity.this.ticketMessageList = response.body().getReplies().getReply();
                if (ViewTicketActivity.this.ticketMessageList != null && ViewTicketActivity.this.ticketMessageList.size() > 0) {
                    ViewTicketActivity.this.recyclerView.setLayoutManager(new LinearLayoutManager(ViewTicketActivity.this));
                    RecyclerView.Adapter unused = ViewTicketActivity.this.mAdapter = new TicketMessageAdapter(ViewTicketActivity.this.context, ViewTicketActivity.this.ticketMessageList);
                    ViewTicketActivity.this.recyclerView.setAdapter(ViewTicketActivity.this.mAdapter);
                    ViewTicketActivity.this.mAdapter.notifyDataSetChanged();
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(@NonNull Call<TickedMessageModelClass> call, @NonNull Throwable th) {
                ViewTicketActivity.this.onFinish(false);
            }
        });
    }

    public void onFinish(Boolean bool) {
        if (bool.booleanValue()) {
            this.recyclerView.setVisibility(0);
            this.btReply.setVisibility(0);
            this.ll_replay.setVisibility(0);
        } else {
            this.textNotFound.setVisibility(0);
            this.btReply.setVisibility(8);
            this.ll_replay.setVisibility(8);
        }
        this.progress.setVisibility(8);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.nst.yourname.miscelleneious.common.Utils.OnFocusChangeAccountListener.<init>(android.view.View, android.app.Activity):void
     arg types: [android.widget.Button, com.nst.yourname.WHMCSClientapp.activities.ViewTicketActivity]
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
    public void showReplyPopup() {
        if (this.context != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
            View inflate = LayoutInflater.from(this).inflate((int) R.layout.replay_alertbox, (ViewGroup) null);
            Button button = (Button) inflate.findViewById(R.id.btn_send);
            Button button2 = (Button) inflate.findViewById(R.id.bt_cancel_reply);
            button.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button, (Activity) this));
            button2.setOnFocusChangeListener(new Utils.OnFocusChangeAccountListener((View) button2, (Activity) this));
            final EditText editText = (EditText) inflate.findViewById(R.id.et_message);
            button.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.WHMCSClientapp.activities.ViewTicketActivity.AnonymousClass4 */

                public void onClick(View view) {
                    String obj = editText.getText().toString();
                    if (obj == null || !obj.equalsIgnoreCase("")) {
                        ViewTicketActivity.this.hitReplyApi(obj, ViewTicketActivity.this.alertDialog);
                    } else {
                        Utils.showToast(ViewTicketActivity.this.context, "Please enter message");
                    }
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.WHMCSClientapp.activities.ViewTicketActivity.AnonymousClass5 */

                public void onClick(View view) {
                    ViewTicketActivity.this.alertDialog.dismiss();
                }
            });
            builder.setView(inflate);
            this.alertDialog = builder.create();
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(this.alertDialog.getWindow().getAttributes());
            layoutParams.width = -1;
            layoutParams.height = -2;
            this.alertDialog.show();
            this.alertDialog.getWindow().setAttributes(layoutParams);
            this.alertDialog.setCancelable(false);
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        super.onBackPressed();
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
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
            } else if (!z) {
                if (z) {
                    f = 1.09f;
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
