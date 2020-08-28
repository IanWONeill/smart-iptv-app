package com.nst.yourname.WHMCSClientapp.interfaces;

import android.content.Context;
import android.support.annotation.NonNull;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.CallBacks.AllServiceApiCallBack;
import com.nst.yourname.WHMCSClientapp.Clientdatabase.ClientSharepreferenceHandler;
import com.nst.yourname.WHMCSClientapp.modelclassess.ActiveServiceModelClass;
import com.nst.yourname.miscelleneious.common.AppConst;
import java.util.ArrayList;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommanApiHitClass {
    private static ApiService apiService;
    public AllServiceApiCallBack allServiceApiCallBack;
    public Context context;
    private String status;

    public CommanApiHitClass(AllServiceApiCallBack allServiceApiCallBack2, Context context2, String str) {
        this.allServiceApiCallBack = allServiceApiCallBack2;
        this.context = context2;
        this.status = str;
    }

    public CommanApiHitClass() {
    }

    public void HitAllServicesApi() {
        ((ApiService) ApiclientRetrofit.getApiRetrofit().create(ApiService.class)).getAllActiveservices(AppConst.BillingAPI_Username, AppConst.BillingAPI_Password, "GetClientproductwithStatus", "yes", ClientSharepreferenceHandler.getClientId(this.context), this.status).enqueue(new Callback<ArrayList<ActiveServiceModelClass>>() {
            /* class com.nst.yourname.WHMCSClientapp.interfaces.CommanApiHitClass.AnonymousClass1 */

            @Override // retrofit2.Callback
            public void onResponse(@NonNull Call<ArrayList<ActiveServiceModelClass>> call, @NonNull Response<ArrayList<ActiveServiceModelClass>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    CommanApiHitClass.this.allServiceApiCallBack.getAllServiceFailled("Network Error");
                } else {
                    CommanApiHitClass.this.allServiceApiCallBack.getAllServiceResponse(response.body());
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(@NonNull Call<ArrayList<ActiveServiceModelClass>> call, @NonNull Throwable th) {
                CommanApiHitClass.this.allServiceApiCallBack.getAllServiceFailled(CommanApiHitClass.this.context.getResources().getString(R.string.something_wrong));
            }
        });
    }

    public void HitLogoutNotificaionStopAPI(Context context2) {
        String token = FirebaseInstanceId.getInstance().getToken();
        ((ApiService) ApiclientRetrofit.getApiRetrofit().create(ApiService.class)).getAdduser(AppConst.BillingAPI_Username, AppConst.BillingAPI_Password, "loggedout", ClientSharepreferenceHandler.getClientId(context2), token, "yes").enqueue(new Callback<HashMap>() {
            /* class com.nst.yourname.WHMCSClientapp.interfaces.CommanApiHitClass.AnonymousClass2 */

            @Override // retrofit2.Callback
            public void onFailure(@NonNull Call<HashMap> call, @NonNull Throwable th) {
            }

            @Override // retrofit2.Callback
            public void onResponse(@NonNull Call<HashMap> call, @NonNull Response<HashMap> response) {
                response.isSuccessful();
            }
        });
    }
}
