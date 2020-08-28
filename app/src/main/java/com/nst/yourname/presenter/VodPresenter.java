package com.nst.yourname.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.callback.VodInfoCallback;
import com.nst.yourname.model.webrequest.RetrofitPost;
import com.nst.yourname.view.interfaces.VodInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VodPresenter {
    private Context context;
    public VodInterface vodInteface;

    public VodPresenter(VodInterface vodInterface, Context context2) {
        this.vodInteface = vodInterface;
        this.context = context2;
    }

    public void vodInfo(String str, String str2, int i) {
        this.vodInteface.atStart();
        Retrofit retrofitObject = Utils.retrofitObject(this.context);
        if (retrofitObject != null) {
            ((RetrofitPost) retrofitObject.create(RetrofitPost.class)).vodInfo(AppConst.CONTENT_TYPE, str, str2, AppConst.ACTION_GET_VOD_INFO, i).enqueue(new Callback<VodInfoCallback>() {
                /* class com.nst.yourname.presenter.VodPresenter.AnonymousClass1 */

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<VodInfoCallback> call, @NonNull Response<VodInfoCallback> response) {
                    VodPresenter.this.vodInteface.onFinish();
                    if (response.isSuccessful()) {
                        VodPresenter.this.vodInteface.vodInfo(response.body());
                    } else if (response.body() == null) {
                        VodPresenter.this.vodInteface.onFailed(AppConst.INVALID_REQUEST);
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<VodInfoCallback> call, @NonNull Throwable th) {
                    VodPresenter.this.vodInteface.onFinish();
                    VodPresenter.this.vodInteface.onFailed(th.getMessage());
                    VodPresenter.this.vodInteface.vodInfoError(th.getMessage());
                }
            });
        }
    }
}
