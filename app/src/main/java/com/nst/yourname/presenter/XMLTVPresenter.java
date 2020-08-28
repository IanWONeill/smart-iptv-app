package com.nst.yourname.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.callback.XMLTVCallback;
import com.nst.yourname.model.webrequest.RetrofitPost;
import com.nst.yourname.view.interfaces.XMLTVInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class XMLTVPresenter {
    public Context context;
    public XMLTVInterface xmlTvInterface;

    public XMLTVPresenter(XMLTVInterface xMLTVInterface, Context context2) {
        this.xmlTvInterface = xMLTVInterface;
        this.context = context2;
    }

    public void epgXMLTV(String str, String str2) {
        this.xmlTvInterface.atStart();
        Retrofit retrofitObjectXML = Utils.retrofitObjectXML(this.context);
        if (retrofitObjectXML != null) {
            ((RetrofitPost) retrofitObjectXML.create(RetrofitPost.class)).epgXMLTV(AppConst.CONTENT_TYPE, str, str2).enqueue(new Callback<XMLTVCallback>() {
                /* class com.nst.yourname.presenter.XMLTVPresenter.AnonymousClass1 */

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<XMLTVCallback> call, @NonNull Response<XMLTVCallback> response) {
                    if (response.isSuccessful()) {
                        XMLTVPresenter.this.xmlTvInterface.epgXMLTV(response.body());
                    } else if (response.body() == null) {
                        XMLTVPresenter.this.xmlTvInterface.epgXMLTVUpdateFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                        if (XMLTVPresenter.this.context != null) {
                            XMLTVPresenter.this.xmlTvInterface.onFailed(XMLTVPresenter.this.context.getResources().getString(R.string.invalid_request));
                        }
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<XMLTVCallback> call, @NonNull Throwable th) {
                    XMLTVPresenter.this.xmlTvInterface.epgXMLTVUpdateFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                    XMLTVPresenter.this.xmlTvInterface.onFinish();
                    XMLTVPresenter.this.xmlTvInterface.onFailed(th.getMessage());
                }
            });
        }
    }
}
