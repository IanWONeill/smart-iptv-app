package com.nst.yourname.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.callback.XtreamPanelAPICallback;
import com.nst.yourname.model.webrequest.RetrofitPost;
import com.nst.yourname.view.interfaces.XtreamPanelAPIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class XtreamPanelAPIPresenter {
    public Context context;
    public XtreamPanelAPIInterface xtreamPanelAPIInterface;

    public XtreamPanelAPIPresenter(XtreamPanelAPIInterface xtreamPanelAPIInterface2, Context context2) {
        this.xtreamPanelAPIInterface = xtreamPanelAPIInterface2;
        this.context = context2;
    }

    public void panelAPI(final String str, String str2) {
        Retrofit retrofitObject = Utils.retrofitObject(this.context);
        if (retrofitObject != null) {
            ((RetrofitPost) retrofitObject.create(RetrofitPost.class)).panelAPI(AppConst.CONTENT_TYPE, str, str2).enqueue(new Callback<XtreamPanelAPICallback>() {
                /* class com.nst.yourname.presenter.XtreamPanelAPIPresenter.AnonymousClass1 */

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<XtreamPanelAPICallback> call, @NonNull Response<XtreamPanelAPICallback> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        XtreamPanelAPIPresenter.this.xtreamPanelAPIInterface.panelAPI(response.body(), str);
                    } else if (response.body() == null) {
                        XtreamPanelAPIPresenter.this.xtreamPanelAPIInterface.panelApiFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                        XtreamPanelAPIPresenter.this.xtreamPanelAPIInterface.onFinish();
                        if (XtreamPanelAPIPresenter.this.context != null) {
                            XtreamPanelAPIPresenter.this.xtreamPanelAPIInterface.onFailed(XtreamPanelAPIPresenter.this.context.getResources().getString(R.string.invalid_request));
                        }
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<XtreamPanelAPICallback> call, @NonNull Throwable th) {
                    XtreamPanelAPIPresenter.this.xtreamPanelAPIInterface.panelApiFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                    XtreamPanelAPIPresenter.this.xtreamPanelAPIInterface.onFinish();
                    XtreamPanelAPIPresenter.this.xtreamPanelAPIInterface.onFailed(th.getMessage());
                }
            });
        }
    }
}
