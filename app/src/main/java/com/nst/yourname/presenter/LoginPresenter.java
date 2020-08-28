package com.nst.yourname.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.callback.LoginCallback;
import com.nst.yourname.model.webrequest.RetrofitPost;
import com.nst.yourname.view.interfaces.LoginInterface;
import java.io.IOException;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginPresenter {
    public Context context;
    public LoginInterface loginInteface;
    public SharedPreferences loginPreferencesServerURl;
    public SharedPreferences.Editor loginPreferencesServerURlPut;

    public LoginPresenter(LoginInterface loginInterface, Context context2) {
        this.loginInteface = loginInterface;
        this.context = context2;
    }

    public void validateLogin(final String str, final String str2) throws IOException {
        Retrofit retrofitObject = Utils.retrofitObject(this.context);
        if (retrofitObject != null) {
            ((RetrofitPost) retrofitObject.create(RetrofitPost.class)).validateLogin(AppConst.CONTENT_TYPE, str, str2).enqueue(new Callback<LoginCallback>() {
                /* class com.nst.yourname.presenter.LoginPresenter.AnonymousClass1 */

                @Override // retrofit2.Callback
                public void onResponse(Call<LoginCallback> call, Response<LoginCallback> response) {
                    if (response.isSuccessful()) {
                        LoginPresenter.this.loginInteface.validateLogin(response.body(), AppConst.VALIDATE_LOGIN);
                    } else if (response.code() == 404) {
                        LoginPresenter.this.loginInteface.magFailedtoLogin(LoginPresenter.this.context.getResources().getString(R.string.invalid_server_url), str, str2, LoginPresenter.this.context);
                    } else if (response.code() == 301 || response.code() == 302) {
                        String header = response.raw().header("Location");
                        if (header != null) {
                            String[] split = header.split("/player_api.php");
                            SharedPreferences unused = LoginPresenter.this.loginPreferencesServerURl = LoginPresenter.this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0);
                            SharedPreferences.Editor unused2 = LoginPresenter.this.loginPreferencesServerURlPut = LoginPresenter.this.loginPreferencesServerURl.edit();
                            LoginPresenter.this.loginPreferencesServerURlPut.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, split[0]);
                            LoginPresenter.this.loginPreferencesServerURlPut.apply();
                            try {
                                LoginPresenter.this.validateLogin(str, str2);
                            } catch (IOException e) {
                                e.printStackTrace();
                                LoginPresenter.this.loginInteface.magFailedtoLogin("ERROR Code 301 || 302: Network error occured! Please try again", str, str2, LoginPresenter.this.context);
                            }
                        } else {
                            LoginPresenter.this.loginInteface.magFailedtoLogin("ERROR Code 301 || 302: Network error occured! Please try again", str, str2, LoginPresenter.this.context);
                        }
                    } else if (response.body() == null) {
                        LoginPresenter.this.loginInteface.magFailedtoLogin("No Response from server", str, str2, LoginPresenter.this.context);
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(Call<LoginCallback> call, Throwable th) {
                    LoginPresenter.this.loginInteface.magFailedtoLogin(LoginPresenter.this.context.getResources().getString(R.string.network_error_connection), str, str2, LoginPresenter.this.context);
                }
            });
        } else if (retrofitObject == null && this.context != null) {
            this.loginInteface.stopLoader(this.context.getResources().getString(R.string.url_not_working));
        }
    }

    public void validateLoginUsingPanelAPI(String str, String str2) {
        Retrofit retrofitObject = Utils.retrofitObject(this.context);
        if (retrofitObject != null) {
            ((RetrofitPost) retrofitObject.create(RetrofitPost.class)).validateLoginUsingPanelApi(AppConst.CONTENT_TYPE, str, str2).enqueue(new Callback<LoginCallback>() {
                /* class com.nst.yourname.presenter.LoginPresenter.AnonymousClass2 */

                @Override // retrofit2.Callback
                public void onResponse(@NonNull Call<LoginCallback> call, @NonNull Response<LoginCallback> response) {
                    LoginPresenter.this.loginInteface.atStart();
                    if (response.isSuccessful()) {
                        LoginPresenter.this.loginInteface.validateLogin(response.body(), AppConst.VALIDATE_LOGIN);
                        LoginPresenter.this.loginInteface.onFinish();
                    } else if (response.code() == 404) {
                        LoginPresenter.this.loginInteface.onFinish();
                        LoginPresenter.this.loginInteface.onFailed(AppConst.NETWORK_ERROR_OCCURED);
                    } else if (response.body() == null) {
                        LoginPresenter.this.loginInteface.onFinish();
                        if (LoginPresenter.this.context != null) {
                            LoginPresenter.this.loginInteface.onFailed(LoginPresenter.this.context.getResources().getString(R.string.invalid_request));
                        }
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(@NonNull Call<LoginCallback> call, @NonNull Throwable th) {
                    if (th.getMessage() != null && th.getMessage().contains("Unable to resolve host")) {
                        LoginPresenter.this.loginInteface.onFinish();
                        LoginPresenter.this.loginInteface.onFailed(AppConst.NETWORK_ERROR_OCCURED);
                    } else if (th.getMessage() == null || !th.getMessage().contains("Failed to connect")) {
                        LoginPresenter.this.loginInteface.onFinish();
                        if (th.getMessage() != null) {
                            LoginPresenter.this.loginInteface.onFailed(th.getMessage());
                        } else {
                            LoginPresenter.this.loginInteface.onFailed(AppConst.NETWORK_ERROR_OCCURED);
                        }
                    } else {
                        LoginPresenter.this.loginInteface.onFinish();
                        LoginPresenter.this.loginInteface.onFailed(AppConst.FAILED_TO_CONNECT);
                    }
                }
            });
        } else if (retrofitObject == null) {
            this.loginInteface.stopLoader(this.context.getResources().getString(R.string.url_not_working));
        }
    }

    public void validateLoginMultiDns(final String str, final String str2, final ArrayList<String> arrayList, final ArrayList<String> arrayList2) throws IOException {
        Retrofit retrofitObject = Utils.retrofitObject(this.context);
        if (retrofitObject != null) {
            ((RetrofitPost) retrofitObject.create(RetrofitPost.class)).validateLogin(AppConst.CONTENT_TYPE, str, str2).enqueue(new Callback<LoginCallback>() {
                /* class com.nst.yourname.presenter.LoginPresenter.AnonymousClass3 */

                @Override // retrofit2.Callback
                public void onResponse(@NotNull Call<LoginCallback> call, @NotNull Response<LoginCallback> response) {
                    if (response.isSuccessful()) {
                        LoginPresenter.this.loginInteface.validateloginMultiDNS(response.body(), AppConst.VALIDATE_LOGIN, arrayList, arrayList2);
                    } else if (response.code() == 404) {
                        LoginPresenter.this.loginInteface.magFailedtoLoginMultiDNS(arrayList, arrayList2, LoginPresenter.this.context.getResources().getString(R.string.invalid_server_url), str, str2);
                    } else if (response.code() == 301 || response.code() == 302) {
                        String header = response.raw().header("Location");
                        if (header != null) {
                            String[] split = header.split("/player_api.php");
                            SharedPreferences unused = LoginPresenter.this.loginPreferencesServerURl = LoginPresenter.this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0);
                            SharedPreferences.Editor unused2 = LoginPresenter.this.loginPreferencesServerURlPut = LoginPresenter.this.loginPreferencesServerURl.edit();
                            LoginPresenter.this.loginPreferencesServerURlPut.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, split[0]);
                            LoginPresenter.this.loginPreferencesServerURlPut.apply();
                            try {
                                LoginPresenter.this.validateLoginMultiDns(str, str2, arrayList, arrayList2);
                            } catch (IOException e) {
                                e.printStackTrace();
                                LoginPresenter.this.loginInteface.magFailedtoLoginMultiDNS(arrayList, arrayList2, "ERROR Code 301 || 302: Network error occured! Please try again", str, str2);
                            }
                        } else {
                            LoginPresenter.this.loginInteface.magFailedtoLoginMultiDNS(arrayList, arrayList2, "ERROR Code 301 || 302: Network error occured! Please try again", str, str2);
                        }
                    } else if (response.body() == null) {
                        LoginPresenter.this.loginInteface.magFailedtoLoginMultiDNS(arrayList, arrayList2, "No Response from server", str, str2);
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(@NotNull Call<LoginCallback> call, @NotNull Throwable th) {
                    LoginPresenter.this.loginInteface.magFailedtoLoginMultiDNS(arrayList, arrayList2, LoginPresenter.this.context.getResources().getString(R.string.network_error_connection), str, str2);
                }
            });
        } else if (retrofitObject == null && this.context != null) {
            this.loginInteface.stopLoaderMultiDNS(arrayList, this.context.getResources().getString(R.string.url_not_working));
        }
    }

    public void validateLoginMultiDnsUsingPanelapi(final String str, final String str2, final ArrayList<String> arrayList, final ArrayList<String> arrayList2) throws IOException {
        Retrofit retrofitObject = Utils.retrofitObject(this.context);
        if (retrofitObject != null) {
            ((RetrofitPost) retrofitObject.create(RetrofitPost.class)).validateLoginUsingPanelApi(AppConst.CONTENT_TYPE, str, str2).enqueue(new Callback<LoginCallback>() {
                /* class com.nst.yourname.presenter.LoginPresenter.AnonymousClass4 */

                @Override // retrofit2.Callback
                public void onResponse(@NotNull Call<LoginCallback> call, @NotNull Response<LoginCallback> response) {
                    if (response.isSuccessful()) {
                        LoginPresenter.this.loginInteface.validateloginMultiDNS(response.body(), AppConst.VALIDATE_LOGIN, arrayList, arrayList2);
                    } else if (response.code() == 404) {
                        LoginPresenter.this.loginInteface.magFailedtoLoginMultiDNS(arrayList, arrayList2, LoginPresenter.this.context.getResources().getString(R.string.invalid_server_url), str, str2);
                    } else if (response.code() == 301 || response.code() == 302) {
                        String header = response.raw().header("Location");
                        if (header != null) {
                            String[] split = header.split("/panel_api.php");
                            SharedPreferences unused = LoginPresenter.this.loginPreferencesServerURl = LoginPresenter.this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0);
                            SharedPreferences.Editor unused2 = LoginPresenter.this.loginPreferencesServerURlPut = LoginPresenter.this.loginPreferencesServerURl.edit();
                            LoginPresenter.this.loginPreferencesServerURlPut.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, split[0]);
                            LoginPresenter.this.loginPreferencesServerURlPut.apply();
                            try {
                                LoginPresenter.this.validateLoginMultiDnsUsingPanelapi(str, str2, arrayList, arrayList2);
                            } catch (IOException e) {
                                e.printStackTrace();
                                LoginPresenter.this.loginInteface.magFailedtoLoginMultiDNS(arrayList, arrayList2, "ERROR Code 301 || 302: Network error occured! Please try again", str, str2);
                            }
                        } else {
                            LoginPresenter.this.loginInteface.magFailedtoLoginMultiDNS(arrayList, arrayList2, "ERROR Code 301 || 302: Network error occured! Please try again", str, str2);
                        }
                    } else if (response.body() == null) {
                        LoginPresenter.this.loginInteface.magFailedtoLoginMultiDNS(arrayList, arrayList2, "No Response from server", str, str2);
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(@NotNull Call<LoginCallback> call, @NotNull Throwable th) {
                    LoginPresenter.this.loginInteface.magFailedtoLoginMultiDNS(arrayList, arrayList2, LoginPresenter.this.context.getResources().getString(R.string.network_error_connection), str, str2);
                }
            });
        } else if (retrofitObject == null && this.context != null) {
            this.loginInteface.stopLoaderMultiDNS(arrayList, this.context.getResources().getString(R.string.url_not_working));
        }
    }

    public void reValidateLogin(final String str, final String str2, final ArrayList<String> arrayList, final int i, final ArrayList<String> arrayList2) {
        Retrofit retrofitObjectLogin = Utils.retrofitObjectLogin(this.context);
        if (retrofitObjectLogin != null) {
            ((RetrofitPost) retrofitObjectLogin.create(RetrofitPost.class)).validateLogin(AppConst.CONTENT_TYPE, str, str2).enqueue(new Callback<LoginCallback>() {
                /* class com.nst.yourname.presenter.LoginPresenter.AnonymousClass5 */

                @Override // retrofit2.Callback
                public void onResponse(@NotNull Call<LoginCallback> call, @NotNull Response<LoginCallback> response) {
                    if (response.isSuccessful()) {
                        LoginPresenter.this.loginInteface.reValidateLogin(response.body(), AppConst.VALIDATE_LOGIN, i, arrayList, arrayList2);
                    } else {
                        LoginPresenter.this.loginInteface.magFailedtoLoginMultiDNS(arrayList, arrayList2, "", str, str2);
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(@NotNull Call<LoginCallback> call, @NotNull Throwable th) {
                    LoginPresenter.this.loginInteface.magFailedtoLoginMultiDNS(arrayList, arrayList2, "Error Code : on Failure", str, str2);
                }
            });
        } else if (retrofitObjectLogin == null && this.context != null) {
            this.loginInteface.stopLoaderMultiDNS(arrayList, this.context.getResources().getString(R.string.url_not_working));
        }
    }

    public void reValidateLoginUisngPanelApi(final String str, final String str2, final ArrayList<String> arrayList, final int i, final ArrayList<String> arrayList2) {
        Retrofit retrofitObjectLogin = Utils.retrofitObjectLogin(this.context);
        if (retrofitObjectLogin != null) {
            ((RetrofitPost) retrofitObjectLogin.create(RetrofitPost.class)).validateLoginUsingPanelApi(AppConst.CONTENT_TYPE, str, str2).enqueue(new Callback<LoginCallback>() {
                /* class com.nst.yourname.presenter.LoginPresenter.AnonymousClass6 */

                @Override // retrofit2.Callback
                public void onResponse(@NotNull Call<LoginCallback> call, @NotNull Response<LoginCallback> response) {
                    if (response.isSuccessful()) {
                        LoginPresenter.this.loginInteface.reValidateLogin(response.body(), AppConst.VALIDATE_LOGIN, i, arrayList, arrayList2);
                    } else {
                        LoginPresenter.this.loginInteface.magFailedtoLoginMultiDNS2(arrayList, arrayList2, "", str, str2);
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(@NotNull Call<LoginCallback> call, @NotNull Throwable th) {
                    LoginPresenter.this.loginInteface.magFailedtoLoginMultiDNS2(arrayList, arrayList2, "Error Code : on Failure", str, str2);
                }
            });
        } else if (retrofitObjectLogin == null && this.context != null) {
            this.loginInteface.stopLoaderMultiDNS(arrayList, this.context.getResources().getString(R.string.url_not_working));
        }
    }
}
