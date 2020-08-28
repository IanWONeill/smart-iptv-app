package com.nst.yourname.view.interfaces;

import android.content.Context;
import com.nst.yourname.model.MultiUserDBModel;
import com.nst.yourname.model.callback.LoginCallback;

public interface EditUserInterface extends BaseInterface {
    void magFailedtoLogin2(String str, String str2, String str3, Context context);

    void stopLoader(String str);

    void validateLogin(LoginCallback loginCallback, String str, String str2, String str3, String str4, String str5, MultiUserDBModel multiUserDBModel);
}
