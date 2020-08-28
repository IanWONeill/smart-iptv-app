package com.nst.yourname.view.interfaces;

import android.content.Context;
import com.nst.yourname.model.callback.LoginCallback;
import java.util.ArrayList;

public interface LoginInterface extends BaseInterface {
    void magFailedtoLogin(String str, String str2, String str3, Context context);

    void magFailedtoLoginMultiDNS(ArrayList<String> arrayList, ArrayList<String> arrayList2, String str, String str2, String str3);

    void magFailedtoLoginMultiDNS2(ArrayList<String> arrayList, ArrayList<String> arrayList2, String str, String str2, String str3);

    void reValidateLogin(LoginCallback loginCallback, String str, int i, ArrayList<String> arrayList, ArrayList<String> arrayList2);

    void stopLoader(String str);

    void stopLoaderMultiDNS(ArrayList<String> arrayList, String str);

    void validateLogin(LoginCallback loginCallback, String str);

    void validateloginMultiDNS(LoginCallback loginCallback, String str, ArrayList<String> arrayList, ArrayList<String> arrayList2);
}
