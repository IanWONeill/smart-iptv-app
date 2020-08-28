package com.nst.yourname.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.gson.JsonObject;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.callback.ActivationCallBack;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.model.webrequest.RetrofitPost;
import com.nst.yourname.view.interfaces.ActivationInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivationPresenter {
    ActivationInterface activationInterface;
    Context context;
    String username;

    public ActivationPresenter(ActivationInterface activationInterface2, Context context2) {
        this.context = context2;
        this.activationInterface = activationInterface2;
    }

    public void validateAct(String str) {
        Retrofit activationRetrofit = Utils.getActivationRetrofit(this.context);
        if (activationRetrofit != null) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("api_username", AppConst.API_USERNAME);
            jsonObject.addProperty("api_password", AppConst.API_PASSWORD);
            jsonObject.addProperty("activation_code", str);
            ((RetrofitPost) activationRetrofit.create(RetrofitPost.class)).validateAct(jsonObject).enqueue(new Callback<ActivationCallBack>() {
                /* class com.nst.yourname.presenter.ActivationPresenter.AnonymousClass1 */

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<ActivationCallBack> call, @NonNull Response<ActivationCallBack> response) {
                    if (!response.isSuccessful() || response.body() == null) {
                        ActivationPresenter.this.activationInterface.msgFailedtoLogin(ActivationPresenter.this.context.getResources().getString(R.string.something_wrong));
                        return;
                    }
                    if (response.body().getResult() != null && response.body().getResult().equalsIgnoreCase("success")) {
                        if (response.body().getLogindetails() != null) {
                            SharepreferenceDBHandler.setUserPassword(response.body().getLogindetails().getPassword(), ActivationPresenter.this.context);
                            SharepreferenceDBHandler.setUserName(response.body().getLogindetails().getUsername(), ActivationPresenter.this.context);
                            ActivationPresenter.this.activationInterface.validateActiveLogin(response.body(), AppConst.VALIDATE_LOGIN);
                            Log.e("ActivationPresenter", "Respone is successfull");
                        } else if (response.body().getMessage() != null) {
                            Utils.showToast(ActivationPresenter.this.context, response.body().getMessage());
                        }
                    }
                    if (response.body().getResult().equalsIgnoreCase("error")) {
                        ActivationPresenter.this.activationInterface.msgFailedtoLogin(response.body().getMessage());
                        Log.e("ActivationPresenter", "Response is not sucessfull");
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<ActivationCallBack> call, @NonNull Throwable th) {
                    ActivationPresenter.this.activationInterface.msgFailedtoLogin(ActivationPresenter.this.context.getResources().getString(R.string.something_wrong));
                }
            });
        }
    }
}
