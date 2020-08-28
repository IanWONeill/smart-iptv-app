package com.nst.yourname.WHMCSClientapp.interfaces;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiclientRetrofit {
    private static Retrofit retrofit;

    public static Retrofit getApiRetrofit() {
        OkHttpClient build = new OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS).writeTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS).followRedirects(false).build();
        if (retrofit != null) {
            return retrofit;
        }
        retrofit = new Retrofit.Builder().baseUrl("http://tvplushd.com/billing/").addConverterFactory(GsonConverterFactory.create()).client(build).build();
        return retrofit;
    }

    public static Retrofit getFreeTrialApiRetrofit() {
        OkHttpClient build = new OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS).writeTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS).followRedirects(false).build();
        if (retrofit != null) {
            return retrofit;
        }
        retrofit = new Retrofit.Builder().baseUrl("http://tvplushd.com/billing/").addConverterFactory(GsonConverterFactory.create()).client(build).build();
        return retrofit;
    }
}
