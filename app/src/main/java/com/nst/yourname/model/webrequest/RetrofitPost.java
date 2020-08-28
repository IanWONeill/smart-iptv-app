package com.nst.yourname.model.webrequest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nst.yourname.model.callback.ActivationCallBack;
import com.nst.yourname.model.callback.GetSeriesStreamCallback;
import com.nst.yourname.model.callback.GetSeriesStreamCategoriesCallback;
import com.nst.yourname.model.callback.LiveStreamCategoriesCallback;
import com.nst.yourname.model.callback.LiveStreamsCallback;
import com.nst.yourname.model.callback.LiveStreamsEpgCallback;
import com.nst.yourname.model.callback.LoginCallback;
import com.nst.yourname.model.callback.SearchTMDBMoviesCallback;
import com.nst.yourname.model.callback.SearchTMDBTVShowsCallback;
import com.nst.yourname.model.callback.TMDBCastsCallback;
import com.nst.yourname.model.callback.TMDBGenreCallback;
import com.nst.yourname.model.callback.TMDBTVShowsInfoCallback;
import com.nst.yourname.model.callback.TMDBTrailerCallback;
import com.nst.yourname.model.callback.VodCategoriesCallback;
import com.nst.yourname.model.callback.VodInfoCallback;
import com.nst.yourname.model.callback.VodStreamsCallback;
import com.nst.yourname.model.callback.XMLTVCallback;
import com.nst.yourname.model.callback.XtreamPanelAPICallback;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitPost {
    @FormUrlEncoded
    @POST("player_api.php")
    Call<List<GetSeriesStreamCallback>> allSeriesStreams(@Header("Content-Type") String str, @Field("username") String str2, @Field("password") String str3, @Field("action") String str4);

    @FormUrlEncoded
    @POST("player_api.php")
    Call<List<VodStreamsCallback>> allVODStreams(@Header("Content-Type") String str, @Field("username") String str2, @Field("password") String str3, @Field("action") String str4);

    @FormUrlEncoded
    @POST("xmltv.php")
    Call<XMLTVCallback> epgXMLTV(@Header("Content-Type") String str, @Field("username") String str2, @Field("password") String str3);

    @GET("movie/{movie_id}/credits")
    Call<TMDBCastsCallback> getCasts(@Path("movie_id") int i, @Query("api_key") String str);

    @GET("movie/{movie_id}")
    Call<TMDBGenreCallback> getGenre(@Path("movie_id") int i, @Query("api_key") String str);

    @GET("search/movie")
    Call<SearchTMDBMoviesCallback> getMoviesInfo(@Query("api_key") String str, @Query("query") String str2);

    @FormUrlEncoded
    @POST("player_api.php")
    Call<LiveStreamsEpgCallback> getTVArchive(@Header("Content-Type") String str, @Field("username") String str2, @Field("password") String str3, @Field("action") String str4, @Field("stream_id") int i);

    @GET("tv/{show_id}")
    Call<TMDBTVShowsInfoCallback> getTVShowsGenreAndDirector(@Path("show_id") int i, @Query("api_key") String str);

    @GET("search/tv")
    Call<SearchTMDBTVShowsCallback> getTVShowsInfo(@Query("api_key") String str, @Query("query") String str2);

    @GET("movie/{movie_id}/videos")
    Call<TMDBTrailerCallback> getTrailer(@Path("movie_id") int i, @Query("api_key") String str);

    @GET("tv/{show_id}/videos")
    Call<TMDBTrailerCallback> getTrailerTVShows(@Path("show_id") int i, @Query("api_key") String str);

    @FormUrlEncoded
    @POST("player_api.php")
    Call<List<LiveStreamCategoriesCallback>> liveStreamCategories(@Header("Content-Type") String str, @Field("username") String str2, @Field("password") String str3, @Field("action") String str4);

    @FormUrlEncoded
    @POST("player_api.php")
    Call<List<LiveStreamsCallback>> liveStreams(@Header("Content-Type") String str, @Field("username") String str2, @Field("password") String str3, @Field("action") String str4);

    @FormUrlEncoded
    @POST("player_api.php")
    Call<List<LiveStreamsCallback>> liveStreams(@Header("Content-Type") String str, @Field("username") String str2, @Field("password") String str3, @Field("action") String str4, @Field("category_id") String str5);

    @FormUrlEncoded
    @POST("player_api.php")
    Call<LiveStreamsEpgCallback> liveStreamsEpg(@Header("Content-Type") String str, @Field("username") String str2, @Field("password") String str3, @Field("action") String str4, @Field("stream_id") Integer num);

    @FormUrlEncoded
    @POST("/panel_api.php")
    Call<XtreamPanelAPICallback> panelAPI(@Header("Content-Type") String str, @Field("username") String str2, @Field("password") String str3);

    @FormUrlEncoded
    @POST("player_api.php")
    Call<JsonElement> seasonsEpisode(@Header("Content-Type") String str, @Field("username") String str2, @Field("password") String str3, @Field("action") String str4, @Field("series_id") String str5);

    @FormUrlEncoded
    @POST("player_api.php")
    Call<List<GetSeriesStreamCategoriesCallback>> seriesCategories(@Header("Content-Type") String str, @Field("username") String str2, @Field("password") String str3, @Field("action") String str4);

    @POST("modules/addons/ActivationCoder/response.php")
    Call<ActivationCallBack> validateAct(@Body JsonObject jsonObject);

    @FormUrlEncoded
    @POST("player_api.php")
    Call<LoginCallback> validateLogin(@Header("Content-Type") String str, @Field("username") String str2, @Field("password") String str3);

    @FormUrlEncoded
    @POST("/panel_api.php")
    Call<LoginCallback> validateLoginUsingPanelApi(@Header("Content-Type") String str, @Field("username") String str2, @Field("password") String str3);

    @FormUrlEncoded
    @POST("player_api.php")
    Call<List<VodCategoriesCallback>> vodCategories(@Header("Content-Type") String str, @Field("username") String str2, @Field("password") String str3, @Field("action") String str4);

    @FormUrlEncoded
    @POST("player_api.php")
    Call<VodInfoCallback> vodInfo(@Header("Content-Type") String str, @Field("username") String str2, @Field("password") String str3, @Field("action") String str4, @Field("vod_id") int i);

    @FormUrlEncoded
    @POST("player_api.php")
    Call<List<VodStreamsCallback>> vodStreams(@Header("Content-Type") String str, @Field("username") String str2, @Field("password") String str3, @Field("action") String str4, @Field("category_id") String str5);
}
