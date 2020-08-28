package com.nst.yourname.WHMCSClientapp.activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.Clientdatabase.ClientSharepreferenceHandler;
import com.nst.yourname.WHMCSClientapp.interfaces.ApiService;
import com.nst.yourname.WHMCSClientapp.interfaces.ApiclientRetrofit;
import com.nst.yourname.WHMCSClientapp.modelclassess.DepartmentClass;
import com.nst.yourname.WHMCSClientapp.modelclassess.OpenDepartmentClass;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenTicketActivity extends AppCompatActivity {
    String SelectedDepartment;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.bt_close)
    Button bt_close;
    public Context context;
    @BindView(R.id.date)
    TextView date;
    public List<String> departmentNames = new ArrayList();
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.et_subject_value)
    EditText et_subject_value;
    String priority;
    @BindView(R.id.sp_department_value)
    Spinner spDepartmentValue;
    @BindView(R.id.sp_priority)
    Spinner sp_priority;
    String[] spinnerArray;
    HashMap<Integer, String> spinnerMap;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.tv_departement)
    TextView tvDepartement;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_priority)
    TextView tvPriority;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_open_ticket);
        ButterKnife.bind(this);
        this.context = this;
        this.btSubmit.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.btSubmit, this));
        this.bt_close.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.bt_close, this));
        new Thread(new CountDownRunner()).start();
        GetDepatments();
    }

    private void GetDepatments() {
        Utils.showProgressDialog(this);
        ((ApiService) ApiclientRetrofit.getApiRetrofit().create(ApiService.class)).getdeparments(AppConst.BillingAPI_Username, AppConst.BillingAPI_Password, "GetSupportDepartments", "no", ClientSharepreferenceHandler.getClientId(this)).enqueue(new Callback<DepartmentClass>() {
            /* class com.nst.yourname.WHMCSClientapp.activities.OpenTicketActivity.AnonymousClass1 */
            static final boolean $assertionsDisabled = false;

            @Override // retrofit2.Callback
            @SuppressLint({"UseSparseArrays"})
            public void onResponse(@NonNull Call<DepartmentClass> call, @NonNull Response<DepartmentClass> response) {
                Utils.hideProgressDialog();
                if (response.body() != null && response.body().getResult().equals("success")) {
                    try {
                        List<DepartmentClass.Departments.Department> department = response.body().getDepartments().getDepartment();
                        if (department != null) {
                            for (DepartmentClass.Departments.Department department2 : department) {
                                OpenTicketActivity.this.departmentNames.add(department2.getName());
                            }
                            OpenTicketActivity.this.spinnerArray = new String[department.size()];
                            OpenTicketActivity.this.spinnerMap = new HashMap<>();
                            for (int i = 0; i < department.size(); i++) {
                                OpenTicketActivity.this.spinnerMap.put(Integer.valueOf(i), String.valueOf(department.get(i).getId()));
                                OpenTicketActivity.this.spinnerArray[i] = String.valueOf(department.get(i).getName());
                            }
                            if (OpenTicketActivity.this.context != null && OpenTicketActivity.this.spDepartmentValue != null) {
                                ArrayAdapter arrayAdapter = new ArrayAdapter(OpenTicketActivity.this, 17367043, OpenTicketActivity.this.spinnerArray);
                                arrayAdapter.setDropDownViewResource(17367049);
                                OpenTicketActivity.this.spDepartmentValue.setAdapter((SpinnerAdapter) arrayAdapter);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (response.body() != null && response.body().getResult().equals("error")) {
                    Toast.makeText(OpenTicketActivity.this.getApplicationContext(), "Error", 0).show();
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(@NonNull Call<DepartmentClass> call, @NonNull Throwable th) {
                Utils.hideProgressDialog();
                Toast.makeText(OpenTicketActivity.this.context, AppConst.NETWORK_ERROR_OCCURED, 0).show();
            }
        });
    }

    @OnClick({R.id.bt_submit, R.id.bt_close})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.bt_close) {
            onBackPressed();
        } else if (id == R.id.bt_submit) {
            submit();
        }
    }

    private void OpenDepartment(String str, String str2, String str3, int i) {
        Utils.showProgressDialog(this);
        ((ApiService) ApiclientRetrofit.getApiRetrofit().create(ApiService.class)).Opendeparments(AppConst.BillingAPI_Username, AppConst.BillingAPI_Password, "OpenTicket", str, str3, i, str2).enqueue(new Callback<OpenDepartmentClass>() {
            /* class com.nst.yourname.WHMCSClientapp.activities.OpenTicketActivity.AnonymousClass2 */

            @Override // retrofit2.Callback
            public void onResponse(@NonNull Call<OpenDepartmentClass> call, @NonNull Response<OpenDepartmentClass> response) {
                Utils.hideProgressDialog();
                if (response.isSuccessful() && response.body() != null && response.body().getResult().equals("success")) {
                    Toast.makeText(OpenTicketActivity.this.getApplicationContext(), OpenTicketActivity.this.context.getResources().getString(R.string.ticket_submitted), 0).show();
                    Intent intent = new Intent(OpenTicketActivity.this, MyTicketActivity.class);
                    intent.setAction("updateticket");
                    intent.setFlags(67108864);
                    OpenTicketActivity.this.startActivity(intent);
                    OpenTicketActivity.this.finish();
                } else if (response.body().getResult().equals("error")) {
                    Toast.makeText(OpenTicketActivity.this.getApplicationContext(), "Error", 0).show();
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(@NonNull Call<OpenDepartmentClass> call, @NonNull Throwable th) {
                Utils.hideProgressDialog();
                Toast.makeText(OpenTicketActivity.this.context, AppConst.NETWORK_ERROR_OCCURED, 0).show();
            }
        });
    }

    public void submit() {
        int clientId = ClientSharepreferenceHandler.getClientId(this.context);
        String trim = this.etMessage.getText().toString().trim();
        this.SelectedDepartment = this.spDepartmentValue.getSelectedItem().toString();
        this.priority = this.sp_priority.getSelectedItem().toString();
        String trim2 = this.et_subject_value.getText().toString().trim();
        if (trim2.isEmpty()) {
            Toast.makeText(getApplicationContext(), this.context.getResources().getString(R.string.enter_subject_text), 0).show();
        } else if (this.SelectedDepartment != null && this.SelectedDepartment.equalsIgnoreCase("")) {
            Toast.makeText(this.context, "Please select Department", 0).show();
        } else if (this.priority != null && this.priority.equalsIgnoreCase("")) {
            Toast.makeText(this.context, "Please select priority", 0).show();
        } else if (trim == null || !trim.isEmpty()) {
            OpenDepartment(trim, trim2, this.spinnerMap.get(Integer.valueOf(this.spDepartmentValue.getSelectedItemPosition())), clientId);
        } else {
            Toast.makeText(getApplicationContext(), this.context.getResources().getString(R.string.enter_message), 0).show();
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private final Activity activity;
        private final View view;

        public OnFocusChangeAccountListener(View view2, Activity activity2) {
            this.view = view2;
            this.activity = activity2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            if (this.activity != null) {
                float f = 1.0f;
                if (z) {
                    if (z) {
                        f = 1.03f;
                    }
                    if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("1")) {
                        performScaleXAnimation(f);
                        performScaleYAnimation(f);
                        view2.setBackgroundResource(R.drawable.back_btn_effect);
                    } else if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("2")) {
                        performScaleXAnimation(f);
                        performScaleYAnimation(f);
                        view2.setBackgroundResource(R.drawable.logout_btn_effect);
                    } else if (this.view == null || this.view.getTag() == null || !this.view.getTag().equals("3")) {
                        view2.setBackground(this.activity.getResources().getDrawable(R.drawable.selector_checkbox));
                    } else {
                        performScaleXAnimation(f);
                        performScaleYAnimation(f);
                        view2.setBackgroundResource(R.drawable.blue_btn_effect);
                    }
                } else if (!z) {
                    performScaleXAnimation(1.0f);
                    performScaleYAnimation(1.0f);
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("1"))) {
                        view2.setBackgroundResource(R.drawable.black_button_dark);
                    }
                    if (!(this.view == null || this.view.getTag() == null || !this.view.getTag().equals("2"))) {
                        view2.setBackgroundResource(R.drawable.black_button_dark);
                    }
                    if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("3")) {
                        view2.setBackgroundResource(R.drawable.black_button_dark);
                    }
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
                    OpenTicketActivity.this.doWork();
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
            /* class com.nst.yourname.WHMCSClientapp.activities.OpenTicketActivity.AnonymousClass3 */

            public void run() {
                try {
                    String date = Calendar.getInstance().getTime().toString();
                    String time = Utils.getTime(OpenTicketActivity.this.context);
                    String date2 = Utils.getDate(date);
                    if (OpenTicketActivity.this.time != null) {
                        OpenTicketActivity.this.time.setText(time);
                    }
                    if (OpenTicketActivity.this.date != null) {
                        OpenTicketActivity.this.date.setText(date2);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }
}
