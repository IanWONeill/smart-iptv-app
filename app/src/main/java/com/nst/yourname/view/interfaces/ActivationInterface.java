package com.nst.yourname.view.interfaces;

import com.nst.yourname.model.callback.ActivationCallBack;

public interface ActivationInterface {
    void msgFailedtoLogin(String str);

    void validateActiveLogin(ActivationCallBack activationCallBack, String str);
}
