package com.nst.yourname.presenter;

import android.content.Context;
import com.google.gson.JsonElement;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.webrequest.RetrofitPost;
import com.nst.yourname.view.interfaces.SeriesInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SeriesPresenter {
    private Context context;
    public SeriesInterface seriesInterface;

    public SeriesPresenter(Context context2, SeriesInterface seriesInterface2) {
        this.context = context2;
        this.seriesInterface = seriesInterface2;
    }

    public void getSeriesEpisode(String str, String str2, String str3) {
        Retrofit retrofitObject = Utils.retrofitObject(this.context);
        if (retrofitObject != null) {
            ((RetrofitPost) retrofitObject.create(RetrofitPost.class)).seasonsEpisode(AppConst.CONTENT_TYPE, str, str2, AppConst.ACTION_GET_SERIES_INFO, str3).enqueue(new Callback<JsonElement>() {
                /* class com.nst.yourname.presenter.SeriesPresenter.AnonymousClass1 */

                @Override // retrofit2.Callback
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    if (response != null && response.body() != null) {
                        SeriesPresenter.this.seriesInterface.getSeriesEpisodeInfo(response.body());
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(Call<JsonElement> call, Throwable th) {
                    SeriesPresenter.this.seriesInterface.onFinish();
                    SeriesPresenter.this.seriesInterface.onFailed(th.getMessage());
                    SeriesPresenter.this.seriesInterface.seriesError(th.getMessage());
                }
            });
        }
    }
}
