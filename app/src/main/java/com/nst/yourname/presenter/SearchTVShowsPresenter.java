package com.nst.yourname.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.callback.SearchTMDBTVShowsCallback;
import com.nst.yourname.model.callback.TMDBTVShowsInfoCallback;
import com.nst.yourname.model.callback.TMDBTrailerCallback;
import com.nst.yourname.model.webrequest.RetrofitPost;
import com.nst.yourname.view.interfaces.SearchTVShowsInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchTVShowsPresenter {
    private Context context;
    public SearchTVShowsInterface searchTvShowsInterface;

    public SearchTVShowsPresenter(SearchTVShowsInterface searchTVShowsInterface, Context context2) {
        this.searchTvShowsInterface = searchTVShowsInterface;
        this.context = context2;
    }

    public void getTVShowsInfo(String str) {
        this.searchTvShowsInterface.atStart();
        Retrofit retrofitObjectTMDB = Utils.retrofitObjectTMDB(this.context);
        if (retrofitObjectTMDB != null) {
            ((RetrofitPost) retrofitObjectTMDB.create(RetrofitPost.class)).getTVShowsInfo(AppConst.TMDB_API_KEY, str).enqueue(new Callback<SearchTMDBTVShowsCallback>() {
                /* class com.nst.yourname.presenter.SearchTVShowsPresenter.AnonymousClass1 */

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<SearchTMDBTVShowsCallback> call, @NonNull Response<SearchTMDBTVShowsCallback> response) {
                    SearchTVShowsPresenter.this.searchTvShowsInterface.onFinish();
                    if (response.isSuccessful()) {
                        SearchTVShowsPresenter.this.searchTvShowsInterface.getTVShowsInfo(response.body());
                    } else if (response.body() == null) {
                        SearchTVShowsPresenter.this.searchTvShowsInterface.onFailed(AppConst.INVALID_REQUEST);
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<SearchTMDBTVShowsCallback> call, @NonNull Throwable th) {
                    SearchTVShowsPresenter.this.searchTvShowsInterface.onFinish();
                    SearchTVShowsPresenter.this.searchTvShowsInterface.onFailed(th.getMessage());
                }
            });
        }
    }

    public void getTVShowsGenreAndDirector(int i) {
        this.searchTvShowsInterface.atStart();
        Retrofit retrofitObjectTMDB = Utils.retrofitObjectTMDB(this.context);
        if (retrofitObjectTMDB != null) {
            ((RetrofitPost) retrofitObjectTMDB.create(RetrofitPost.class)).getTVShowsGenreAndDirector(i, AppConst.TMDB_API_KEY).enqueue(new Callback<TMDBTVShowsInfoCallback>() {
                /* class com.nst.yourname.presenter.SearchTVShowsPresenter.AnonymousClass2 */

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<TMDBTVShowsInfoCallback> call, @NonNull Response<TMDBTVShowsInfoCallback> response) {
                    SearchTVShowsPresenter.this.searchTvShowsInterface.onFinish();
                    if (response.isSuccessful()) {
                        SearchTVShowsPresenter.this.searchTvShowsInterface.getTVShowsGenreAndDirector(response.body());
                    } else if (response.body() == null) {
                        SearchTVShowsPresenter.this.searchTvShowsInterface.onFailed(AppConst.INVALID_REQUEST);
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<TMDBTVShowsInfoCallback> call, @NonNull Throwable th) {
                    SearchTVShowsPresenter.this.searchTvShowsInterface.onFinish();
                    SearchTVShowsPresenter.this.searchTvShowsInterface.onFailed(th.getMessage());
                }
            });
        }
    }

    public void getTrailer(int i) {
        this.searchTvShowsInterface.atStart();
        Retrofit retrofitObjectTMDB = Utils.retrofitObjectTMDB(this.context);
        if (retrofitObjectTMDB != null) {
            ((RetrofitPost) retrofitObjectTMDB.create(RetrofitPost.class)).getTrailerTVShows(i, AppConst.TMDB_API_KEY).enqueue(new Callback<TMDBTrailerCallback>() {
                /* class com.nst.yourname.presenter.SearchTVShowsPresenter.AnonymousClass3 */

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<TMDBTrailerCallback> call, @NonNull Response<TMDBTrailerCallback> response) {
                    SearchTVShowsPresenter.this.searchTvShowsInterface.onFinish();
                    if (response.isSuccessful()) {
                        SearchTVShowsPresenter.this.searchTvShowsInterface.getTrailerTVShows(response.body());
                    } else if (response.body() == null) {
                        SearchTVShowsPresenter.this.searchTvShowsInterface.onFailed(AppConst.INVALID_REQUEST);
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<TMDBTrailerCallback> call, @NonNull Throwable th) {
                    SearchTVShowsPresenter.this.searchTvShowsInterface.onFinish();
                    SearchTVShowsPresenter.this.searchTvShowsInterface.onFailed(th.getMessage());
                }
            });
        }
    }
}
