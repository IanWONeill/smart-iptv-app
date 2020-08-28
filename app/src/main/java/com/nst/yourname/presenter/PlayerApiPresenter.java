package com.nst.yourname.presenter;

import android.content.Context;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.callback.GetSeriesStreamCallback;
import com.nst.yourname.model.callback.GetSeriesStreamCategoriesCallback;
import com.nst.yourname.model.callback.LiveStreamCategoriesCallback;
import com.nst.yourname.model.callback.LiveStreamsCallback;
import com.nst.yourname.model.callback.VodCategoriesCallback;
import com.nst.yourname.model.callback.VodStreamsCallback;
import com.nst.yourname.model.webrequest.RetrofitPost;
import com.nst.yourname.view.interfaces.PlayerApiInterface;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PlayerApiPresenter {
    private Context context;
    public PlayerApiInterface playerApiInterface;

    public PlayerApiPresenter(Context context2, PlayerApiInterface playerApiInterface2) {
        this.context = context2;
        this.playerApiInterface = playerApiInterface2;
    }

    public void getLiveStreamCat(String str, String str2) {
        Retrofit retrofitObject = Utils.retrofitObject(this.context);
        if (retrofitObject != null) {
            ((RetrofitPost) retrofitObject.create(RetrofitPost.class)).liveStreamCategories(AppConst.CONTENT_TYPE, str, str2, AppConst.ACTION_GET_LIVE_CATEGORIES).enqueue(new Callback<List<LiveStreamCategoriesCallback>>() {
                /* class com.nst.yourname.presenter.PlayerApiPresenter.AnonymousClass1 */

                @Override // retrofit2.Callback
                public void onResponse(Call<List<LiveStreamCategoriesCallback>> call, Response<List<LiveStreamCategoriesCallback>> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        PlayerApiPresenter.this.playerApiInterface.getLiveStreamCategories(response.body());
                    } else if (response.body() == null) {
                        PlayerApiPresenter.this.playerApiInterface.getLiveStreamCatFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                        PlayerApiPresenter.this.playerApiInterface.onFinish();
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(Call<List<LiveStreamCategoriesCallback>> call, Throwable th) {
                    PlayerApiPresenter.this.playerApiInterface.getLiveStreamCatFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                    PlayerApiPresenter.this.playerApiInterface.onFinish();
                }
            });
        }
    }

    public void getVODStreamCat(String str, String str2) {
        Retrofit retrofitObject = Utils.retrofitObject(this.context);
        if (retrofitObject != null) {
            ((RetrofitPost) retrofitObject.create(RetrofitPost.class)).vodCategories(AppConst.CONTENT_TYPE, str, str2, AppConst.ACTION_GET_VOD_CATEGORIES).enqueue(new Callback<List<VodCategoriesCallback>>() {
                /* class com.nst.yourname.presenter.PlayerApiPresenter.AnonymousClass2 */

                @Override // retrofit2.Callback
                public void onResponse(Call<List<VodCategoriesCallback>> call, Response<List<VodCategoriesCallback>> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        PlayerApiPresenter.this.playerApiInterface.getVODStreamCategories(response.body());
                    } else if (response.body() == null) {
                        PlayerApiPresenter.this.playerApiInterface.getVODStreamCatFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                        PlayerApiPresenter.this.playerApiInterface.onFinish();
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(Call<List<VodCategoriesCallback>> call, Throwable th) {
                    PlayerApiPresenter.this.playerApiInterface.getVODStreamCatFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                    PlayerApiPresenter.this.playerApiInterface.onFinish();
                }
            });
        }
    }

    public void getSeriesStreamCat(String str, String str2) {
        Retrofit retrofitObject = Utils.retrofitObject(this.context);
        if (retrofitObject != null) {
            ((RetrofitPost) retrofitObject.create(RetrofitPost.class)).seriesCategories(AppConst.CONTENT_TYPE, str, str2, AppConst.ACTION_GET_SERIES_CATEGORIES).enqueue(new Callback<List<GetSeriesStreamCategoriesCallback>>() {
                /* class com.nst.yourname.presenter.PlayerApiPresenter.AnonymousClass3 */

                @Override // retrofit2.Callback
                public void onResponse(Call<List<GetSeriesStreamCategoriesCallback>> call, Response<List<GetSeriesStreamCategoriesCallback>> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        PlayerApiPresenter.this.playerApiInterface.getSeriesCategories(response.body());
                    } else if (response.body() == null) {
                        PlayerApiPresenter.this.playerApiInterface.getSeriesStreamCatFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                        PlayerApiPresenter.this.playerApiInterface.onFinish();
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(Call<List<GetSeriesStreamCategoriesCallback>> call, Throwable th) {
                    PlayerApiPresenter.this.playerApiInterface.getSeriesStreamCatFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                    PlayerApiPresenter.this.playerApiInterface.onFinish();
                }
            });
        }
    }

    public void getLiveStreams(String str, String str2) {
        Retrofit retrofitObject = Utils.retrofitObject(this.context);
        if (retrofitObject != null) {
            ((RetrofitPost) retrofitObject.create(RetrofitPost.class)).liveStreams(AppConst.CONTENT_TYPE, str, str2, AppConst.ACTION_GET_LIVE_STREAMS).enqueue(new Callback<List<LiveStreamsCallback>>() {
                /* class com.nst.yourname.presenter.PlayerApiPresenter.AnonymousClass4 */

                @Override // retrofit2.Callback
                public void onResponse(Call<List<LiveStreamsCallback>> call, Response<List<LiveStreamsCallback>> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        PlayerApiPresenter.this.playerApiInterface.getLiveStreams(response.body());
                    } else if (response.body() == null) {
                        PlayerApiPresenter.this.playerApiInterface.getLiveStreamFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                        PlayerApiPresenter.this.playerApiInterface.onFinish();
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(Call<List<LiveStreamsCallback>> call, Throwable th) {
                    PlayerApiPresenter.this.playerApiInterface.getLiveStreamFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                    PlayerApiPresenter.this.playerApiInterface.onFinish();
                }
            });
        }
    }

    public void getVODStreams(String str, String str2) {
        Retrofit retrofitObject = Utils.retrofitObject(this.context);
        if (retrofitObject != null) {
            ((RetrofitPost) retrofitObject.create(RetrofitPost.class)).allVODStreams(AppConst.CONTENT_TYPE, str, str2, AppConst.ACTION_GET_VOD_STREAMS).enqueue(new Callback<List<VodStreamsCallback>>() {
                /* class com.nst.yourname.presenter.PlayerApiPresenter.AnonymousClass5 */

                @Override // retrofit2.Callback
                public void onResponse(Call<List<VodStreamsCallback>> call, Response<List<VodStreamsCallback>> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        PlayerApiPresenter.this.playerApiInterface.getVODStreams(response.body());
                    } else if (response.body() == null) {
                        PlayerApiPresenter.this.playerApiInterface.getVODStreamsFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                        PlayerApiPresenter.this.playerApiInterface.onFinish();
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(Call<List<VodStreamsCallback>> call, Throwable th) {
                    PlayerApiPresenter.this.playerApiInterface.getVODStreamsFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                    PlayerApiPresenter.this.playerApiInterface.onFinish();
                }
            });
        }
    }

    public void getSeriesStream(String str, String str2) {
        Retrofit retrofitObject = Utils.retrofitObject(this.context);
        if (retrofitObject != null) {
            ((RetrofitPost) retrofitObject.create(RetrofitPost.class)).allSeriesStreams(AppConst.CONTENT_TYPE, str, str2, AppConst.ACTION_GET_SERIES_STREAMS).enqueue(new Callback<List<GetSeriesStreamCallback>>() {
                /* class com.nst.yourname.presenter.PlayerApiPresenter.AnonymousClass6 */

                @Override // retrofit2.Callback
                public void onResponse(Call<List<GetSeriesStreamCallback>> call, Response<List<GetSeriesStreamCallback>> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        PlayerApiPresenter.this.playerApiInterface.getSeriesStreams(response.body());
                    } else if (response.body() == null) {
                        PlayerApiPresenter.this.playerApiInterface.getSeriesStreamsFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                        PlayerApiPresenter.this.playerApiInterface.onFinish();
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(Call<List<GetSeriesStreamCallback>> call, Throwable th) {
                    PlayerApiPresenter.this.playerApiInterface.getSeriesStreamsFailed(AppConst.DB_UPDATED_STATUS_FAILED);
                    PlayerApiPresenter.this.playerApiInterface.onFinish();
                }
            });
        }
    }
}
