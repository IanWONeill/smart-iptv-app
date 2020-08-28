package com.nst.yourname.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.callback.LiveStreamsEpgCallback;
import com.nst.yourname.model.webrequest.RetrofitPost;
import com.nst.yourname.view.interfaces.LiveStreamsEpgInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TvArchivePresenter {
    private Context context;
    public LiveStreamsEpgInterface liveStreamsEpgInterface;

    public TvArchivePresenter(LiveStreamsEpgInterface liveStreamsEpgInterface2, Context context2) {
        this.liveStreamsEpgInterface = liveStreamsEpgInterface2;
        this.context = context2;
    }

    public void getTvArchive(String str, String str2, int i, final String str3, final String str4, final String str5, final String str6, final String str7, final String str8) {
        this.liveStreamsEpgInterface.atStart();
        Retrofit retrofitObject = Utils.retrofitObject(this.context);
        if (retrofitObject != null) {
            ((RetrofitPost) retrofitObject.create(RetrofitPost.class)).getTVArchive(AppConst.CONTENT_TYPE, str, str2, "get_simple_data_table", i).enqueue(new Callback<LiveStreamsEpgCallback>() {
                /* class com.nst.yourname.presenter.TvArchivePresenter.AnonymousClass1 */

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<LiveStreamsEpgCallback> call, @NonNull Response<LiveStreamsEpgCallback> response) {
                    TvArchivePresenter.this.liveStreamsEpgInterface.onFinish();
                    if (response.isSuccessful()) {
                        TvArchivePresenter.this.liveStreamsEpgInterface.liveStreamsEpg(response.body(), str3, str4, str5, str6, str7, str8);
                    } else if (response.body() == null) {
                        TvArchivePresenter.this.liveStreamsEpgInterface.onFailed(AppConst.INVALID_REQUEST);
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<LiveStreamsEpgCallback> call, @NonNull Throwable th) {
                    TvArchivePresenter.this.liveStreamsEpgInterface.onFinish();
                    TvArchivePresenter.this.liveStreamsEpgInterface.onFailed(th.getMessage());
                }
            });
        }
    }
}
