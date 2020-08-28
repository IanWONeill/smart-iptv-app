package com.nst.yourname.WHMCSClientapp.interfaces;

import android.content.Context;
import android.support.annotation.NonNull;
import com.nst.yourname.R;
import com.nst.yourname.WHMCSClientapp.CallBacks.AllServiceApiCallBack;
import com.nst.yourname.WHMCSClientapp.CallBacks.InvoiceData;
import com.nst.yourname.WHMCSClientapp.Clientdatabase.ClientSharepreferenceHandler;
import com.nst.yourname.WHMCSClientapp.modelclassess.InvoicesModelClass;
import com.nst.yourname.miscelleneious.common.AppConst;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoicesApiHitClass {
    private static ApiService apiService;
    private AllServiceApiCallBack allServiceApiCallBack;
    public Context context;
    public InvoiceData invoicesData;
    private String status;

    public InvoicesApiHitClass(InvoiceData invoiceData, Context context2, String str) {
        this.context = context2;
        this.status = str;
        this.invoicesData = invoiceData;
    }

    public void InvoicesHitApi() {
        ((ApiService) ApiclientRetrofit.getApiRetrofit().create(ApiService.class)).getInvoices(AppConst.BillingAPI_Username, AppConst.BillingAPI_Password, "GetInvoices", "no", ClientSharepreferenceHandler.getClientId(this.context), this.status).enqueue(new Callback<InvoicesModelClass>() {
            /* class com.nst.yourname.WHMCSClientapp.interfaces.InvoicesApiHitClass.AnonymousClass1 */

            @Override // retrofit2.Callback
            public void onResponse(@NonNull Call<InvoicesModelClass> call, @NonNull Response<InvoicesModelClass> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    InvoicesApiHitClass.this.invoicesData.getResultFailed(InvoicesApiHitClass.this.context.getResources().getString(R.string.something_wrong));
                    return;
                }
                InvoicesApiHitClass.this.invoicesData.getAllInvoiceResponse(response.body().getInvoices().getInvoice());
            }

            @Override // retrofit2.Callback
            public void onFailure(@NonNull Call<InvoicesModelClass> call, @NonNull Throwable th) {
                InvoicesApiHitClass.this.invoicesData.getResultFailed(InvoicesApiHitClass.this.context.getResources().getString(R.string.something_wrong));
            }
        });
    }
}
