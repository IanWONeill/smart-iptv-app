package com.nst.yourname.WebServiceHandler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.google.common.net.HttpHeaders;
import com.nst.yourname.WebServiceHandler.Webservices;
import com.nst.yourname.miscelleneious.common.AppConst;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

@SuppressLint({"SimpleDateFormat"})
public class CommonFunctions {
    private static String baseUrl = Webservices.getWebNames.Url;
    static MainAsynListener<String> listener;

    public static String getOkHttpClient(Context context, String str, int i, String str2, List<ParamsGetter> list) {
        Log.e("onclick", " " + baseUrl + str);
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.newBuilder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
            Request request = null;
            if (str2.equals("")) {
                Request.Builder builder = new Request.Builder();
                request = builder.url(baseUrl + str).header(HttpHeaders.USER_AGENT, AppConst.LOGIN_USER_AGENT).build();
            }
            if (str2.equalsIgnoreCase("GET")) {
                Request.Builder builder2 = new Request.Builder();
                builder2.url(baseUrl + str);
                builder2.header(HttpHeaders.USER_AGENT, AppConst.LOGIN_USER_AGENT);
                if (list != null) {
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        builder2.addHeader(list.get(i2).getKey(), list.get(i2).getValues());
                    }
                }
                builder2.addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
                request = builder2.build();
            }
            if (str2.equalsIgnoreCase("Form")) {
                FormBody.Builder builder3 = new FormBody.Builder();
                for (int i3 = 0; i3 < list.size(); i3++) {
                    Log.e("KEY VLAUES", ">>>>    " + list.get(i3).getKey() + "            " + list.get(i3).getValues());
                    builder3.add(list.get(i3).getKey(), list.get(i3).getValues());
                }
                FormBody build = builder3.build();
                Request.Builder builder4 = new Request.Builder();
                request = builder4.url(baseUrl + str).header(HttpHeaders.USER_AGENT, AppConst.LOGIN_USER_AGENT).addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8").post(build).build();
            }
            if (str2.equalsIgnoreCase("FormAPI")) {
                FormBody.Builder builder5 = new FormBody.Builder();
                for (int i4 = 0; i4 < list.size(); i4++) {
                    Log.e("KEY VLAUES", ">>>>    " + list.get(i4).getKey() + "            " + list.get(i4).getValues());
                    builder5.add(list.get(i4).getKey(), list.get(i4).getValues());
                }
                FormBody build2 = builder5.build();
                Request.Builder builder6 = new Request.Builder();
                request = builder6.url(baseUrl + str).header(HttpHeaders.USER_AGENT, AppConst.LOGIN_USER_AGENT).addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8").post(build2).build();
            }
            if (str2.equalsIgnoreCase("DEL")) {
                Request.Builder builder7 = new Request.Builder();
                builder7.url(baseUrl + str);
                builder7.header(HttpHeaders.USER_AGENT, AppConst.LOGIN_USER_AGENT);
                if (list != null) {
                    for (int i5 = 0; i5 < list.size(); i5++) {
                        builder7.addHeader(list.get(i5).getKey(), list.get(i5).getValues());
                    }
                }
                builder7.addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
                builder7.delete();
                request = builder7.build();
            }
            if (str2.equalsIgnoreCase("Multipart")) {
                MediaType parse = MediaType.parse("image/png");
                MediaType parse2 = MediaType.parse("video/*");
                MultipartBody.Builder builder8 = new MultipartBody.Builder();
                for (int i6 = 0; i6 < list.size(); i6++) {
                    if (list.get(i6).getFile() != null) {
                        if (list.get(i6).getFile().getAbsolutePath().endsWith(".png") || list.get(i6).getFile().getAbsolutePath().endsWith(".jpg") || list.get(i6).getFile().getAbsolutePath().endsWith(".jpeg")) {
                            Log.e("KEY VLAUES FILE", ">>>>    " + list.get(i6).getKey() + "            " + list.get(i6).getFile());
                            builder8.setType(MultipartBody.FORM).addFormDataPart(list.get(i6).getKey(), list.get(i6).getFile().getName(), RequestBody.create(parse, list.get(i6).getFile()));
                        }
                        if (list.get(i6).getFile().getAbsolutePath().endsWith(".mp4") || list.get(i6).getFile().getAbsolutePath().endsWith(".mpeg") || list.get(i6).getFile().getAbsolutePath().endsWith(".3gp") || list.get(i6).getFile().getAbsolutePath().endsWith(".avi")) {
                            builder8.setType(MultipartBody.FORM).addFormDataPart(list.get(i6).getKey(), list.get(i6).getFile().getName(), RequestBody.create(parse2, list.get(i6).getFile()));
                        }
                    } else {
                        Log.e("KEY VLAUES", ">>>>    " + list.get(i6).getKey() + "            " + list.get(i6).getValues());
                        builder8.setType(MultipartBody.FORM).addFormDataPart(list.get(i6).getKey(), list.get(i6).getValues());
                    }
                }
                MultipartBody build3 = builder8.build();
                Request.Builder builder9 = new Request.Builder();
                request = builder9.url(baseUrl + str).header(HttpHeaders.USER_AGENT, AppConst.LOGIN_USER_AGENT).post(build3).build();
            }
            return okHttpClient.newCall(request).execute().body().string();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            listener.onPostError(i);
            return "";
        } catch (IOException e2) {
            e2.printStackTrace();
            listener.onPostError(i);
            return "";
        } catch (Exception e3) {
            e3.printStackTrace();
            listener.onPostError(i);
            return "";
        }
    }
}
