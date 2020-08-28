package com.nst.yourname.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.callback.SearchTMDBMoviesCallback;
import com.nst.yourname.model.callback.TMDBCastsCallback;
import com.nst.yourname.model.callback.TMDBGenreCallback;
import com.nst.yourname.model.callback.TMDBTrailerCallback;
import com.nst.yourname.model.webrequest.RetrofitPost;
import com.nst.yourname.view.interfaces.SearchMoviesInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchMoviesPresenter {
    private Context context;
    public SearchMoviesInterface searchMoviesInterface;

    public SearchMoviesPresenter(SearchMoviesInterface searchMoviesInterface2, Context context2) {
        this.searchMoviesInterface = searchMoviesInterface2;
        this.context = context2;
    }

    public void getMovieInfo(String str) {
        this.searchMoviesInterface.atStart();
        Retrofit retrofitObjectTMDB = Utils.retrofitObjectTMDB(this.context);
        if (retrofitObjectTMDB != null) {
            ((RetrofitPost) retrofitObjectTMDB.create(RetrofitPost.class)).getMoviesInfo(AppConst.TMDB_API_KEY, str).enqueue(new Callback<SearchTMDBMoviesCallback>() {
                /* class com.nst.yourname.presenter.SearchMoviesPresenter.AnonymousClass1 */

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<SearchTMDBMoviesCallback> call, @NonNull Response<SearchTMDBMoviesCallback> response) {
                    SearchMoviesPresenter.this.searchMoviesInterface.onFinish();
                    if (response.isSuccessful()) {
                        SearchMoviesPresenter.this.searchMoviesInterface.getMovieInfo(response.body());
                    } else if (response.body() == null) {
                        SearchMoviesPresenter.this.searchMoviesInterface.onFailed(AppConst.INVALID_REQUEST);
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<SearchTMDBMoviesCallback> call, @NonNull Throwable th) {
                    SearchMoviesPresenter.this.searchMoviesInterface.onFinish();
                    SearchMoviesPresenter.this.searchMoviesInterface.onFailed(th.getMessage());
                }
            });
        }
    }

    public void getCasts(int i) {
        this.searchMoviesInterface.atStart();
        Retrofit retrofitObjectTMDB = Utils.retrofitObjectTMDB(this.context);
        if (retrofitObjectTMDB != null) {
            ((RetrofitPost) retrofitObjectTMDB.create(RetrofitPost.class)).getCasts(i, AppConst.TMDB_API_KEY).enqueue(new Callback<TMDBCastsCallback>() {
                /* class com.nst.yourname.presenter.SearchMoviesPresenter.AnonymousClass2 */

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<TMDBCastsCallback> call, @NonNull Response<TMDBCastsCallback> response) {
                    SearchMoviesPresenter.this.searchMoviesInterface.onFinish();
                    if (response.isSuccessful()) {
                        SearchMoviesPresenter.this.searchMoviesInterface.getCasts(response.body());
                    } else if (response.body() == null) {
                        SearchMoviesPresenter.this.searchMoviesInterface.onFailed(AppConst.INVALID_REQUEST);
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<TMDBCastsCallback> call, @NonNull Throwable th) {
                    SearchMoviesPresenter.this.searchMoviesInterface.onFinish();
                    SearchMoviesPresenter.this.searchMoviesInterface.onFailed(th.getMessage());
                }
            });
        }
    }

    public void getGenre(int i) {
        this.searchMoviesInterface.atStart();
        Retrofit retrofitObjectTMDB = Utils.retrofitObjectTMDB(this.context);
        if (retrofitObjectTMDB != null) {
            ((RetrofitPost) retrofitObjectTMDB.create(RetrofitPost.class)).getGenre(i, AppConst.TMDB_API_KEY).enqueue(new Callback<TMDBGenreCallback>() {
                /* class com.nst.yourname.presenter.SearchMoviesPresenter.AnonymousClass3 */

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<TMDBGenreCallback> call, @NonNull Response<TMDBGenreCallback> response) {
                    SearchMoviesPresenter.this.searchMoviesInterface.onFinish();
                    if (response.isSuccessful()) {
                        SearchMoviesPresenter.this.searchMoviesInterface.getGenre(response.body());
                    } else if (response.body() == null) {
                        SearchMoviesPresenter.this.searchMoviesInterface.onFailed(AppConst.INVALID_REQUEST);
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<TMDBGenreCallback> call, @NonNull Throwable th) {
                    SearchMoviesPresenter.this.searchMoviesInterface.onFinish();
                    SearchMoviesPresenter.this.searchMoviesInterface.onFailed(th.getMessage());
                }
            });
        }
    }

    public void getTrailer(int i) {
        this.searchMoviesInterface.atStart();
        Retrofit retrofitObjectTMDB = Utils.retrofitObjectTMDB(this.context);
        if (retrofitObjectTMDB != null) {
            ((RetrofitPost) retrofitObjectTMDB.create(RetrofitPost.class)).getTrailer(i, AppConst.TMDB_API_KEY).enqueue(new Callback<TMDBTrailerCallback>() {
                /* class com.nst.yourname.presenter.SearchMoviesPresenter.AnonymousClass4 */

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<TMDBTrailerCallback> call, @NonNull Response<TMDBTrailerCallback> response) {
                    SearchMoviesPresenter.this.searchMoviesInterface.onFinish();
                    if (response.isSuccessful()) {
                        SearchMoviesPresenter.this.searchMoviesInterface.getTrailer(response.body());
                    } else if (response.body() == null) {
                        SearchMoviesPresenter.this.searchMoviesInterface.onFailed(AppConst.INVALID_REQUEST);
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<TMDBTrailerCallback> call, @NonNull Throwable th) {
                    SearchMoviesPresenter.this.searchMoviesInterface.onFinish();
                    SearchMoviesPresenter.this.searchMoviesInterface.onFailed(th.getMessage());
                }
            });
        }
    }
}
