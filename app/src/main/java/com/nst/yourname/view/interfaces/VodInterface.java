package com.nst.yourname.view.interfaces;

import com.nst.yourname.model.callback.VodInfoCallback;

public interface VodInterface extends BaseInterface {
    void vodInfo(VodInfoCallback vodInfoCallback);

    void vodInfoError(String str);
}
