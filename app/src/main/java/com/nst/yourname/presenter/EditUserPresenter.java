package com.nst.yourname.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.miscelleneious.common.Utils;
import com.nst.yourname.model.MultiUserDBModel;
import com.nst.yourname.model.callback.LoginCallback;
import com.nst.yourname.model.webrequest.RetrofitPost;
import com.nst.yourname.view.interfaces.EditUserInterface;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditUserPresenter {
    public Context context;
    public EditUserInterface loginInteface;
    public SharedPreferences loginPreferencesServerURl;
    public SharedPreferences.Editor loginPreferencesServerURlPut;

    public EditUserPresenter(EditUserInterface editUserInterface, Context context2) {
        this.loginInteface = editUserInterface;
        this.context = context2;
    }

    public void validateLogin(final String str, final String str2, final String str3, final String str4, final MultiUserDBModel multiUserDBModel) throws IOException {
        Retrofit retrofitObject = Utils.retrofitObject(this.context);
        if (retrofitObject != null) {
            ((RetrofitPost) retrofitObject.create(RetrofitPost.class)).validateLogin(AppConst.CONTENT_TYPE, str, str2).enqueue(new Callback<LoginCallback>() {
                /* class com.nst.yourname.presenter.EditUserPresenter.AnonymousClass1 */

                @Override // retrofit2.Callback
                public void onResponse(Call<LoginCallback> call, Response<LoginCallback> response) {
                    if (response.isSuccessful()) {
                        EditUserPresenter.this.loginInteface.validateLogin(response.body(), AppConst.VALIDATE_LOGIN, str3, str, str2, str4, multiUserDBModel);
                    } else if (response.code() == 404) {
                        EditUserPresenter.this.loginInteface.magFailedtoLogin2(EditUserPresenter.this.context.getResources().getString(R.string.invalid_server_url), str, str2, EditUserPresenter.this.context);
                    } else if (response.code() == 301 || response.code() == 302) {
                        String header = response.raw().header("Location");
                        if (header != null) {
                            String[] split = header.split("/player_api.php");
                            SharedPreferences unused = EditUserPresenter.this.loginPreferencesServerURl = EditUserPresenter.this.context.getSharedPreferences(AppConst.LOGIN_SHARED_PREFERENCE_SERVER_URL, 0);
                            SharedPreferences.Editor unused2 = EditUserPresenter.this.loginPreferencesServerURlPut = EditUserPresenter.this.loginPreferencesServerURl.edit();
                            EditUserPresenter.this.loginPreferencesServerURlPut.putString(AppConst.LOGIN_PREF_SERVER_URL_MAG, split[0]);
                            EditUserPresenter.this.loginPreferencesServerURlPut.apply();
                            try {
                                EditUserPresenter.this.validateLogin(str, str2, str3, str4, multiUserDBModel);
                            } catch (IOException e) {
                                e.printStackTrace();
                                EditUserPresenter.this.loginInteface.magFailedtoLogin2("ERROR Code 301 || 302: Network error occured! Please try again", str, str2, EditUserPresenter.this.context);
                            }
                        } else {
                            EditUserPresenter.this.loginInteface.magFailedtoLogin2("ERROR Code 301 || 302: Network error occured! Please try again", str, str2, EditUserPresenter.this.context);
                        }
                    } else if (response.body() == null) {
                        EditUserPresenter.this.loginInteface.magFailedtoLogin2("No Response from server", str, str2, EditUserPresenter.this.context);
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(Call<LoginCallback> call, Throwable th) {
                    EditUserPresenter.this.loginInteface.magFailedtoLogin2(EditUserPresenter.this.context.getResources().getString(R.string.network_error_connection), str, str2, EditUserPresenter.this.context);
                }
            });
        } else if (retrofitObject == null && this.context != null) {
            this.loginInteface.stopLoader(this.context.getResources().getString(R.string.url_not_working));
        }
    }
}
