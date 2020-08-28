package com.nst.yourname.view.interfaces;

import com.nst.yourname.model.callback.XMLTVCallback;

public interface XMLTVInterface extends BaseInterface {
    void epgXMLTV(XMLTVCallback xMLTVCallback);

    void epgXMLTVUpdateFailed(String str);
}
