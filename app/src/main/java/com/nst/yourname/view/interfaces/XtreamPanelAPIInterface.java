package com.nst.yourname.view.interfaces;

import com.nst.yourname.model.callback.XtreamPanelAPICallback;

public interface XtreamPanelAPIInterface extends BaseInterface {
    void panelAPI(XtreamPanelAPICallback xtreamPanelAPICallback, String str);

    void panelApiFailed(String str);
}
