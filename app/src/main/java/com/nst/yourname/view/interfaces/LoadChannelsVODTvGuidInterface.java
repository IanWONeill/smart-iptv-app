package com.nst.yourname.view.interfaces;

import com.nst.yourname.model.callback.XMLTVCallback;
import com.nst.yourname.model.callback.XtreamPanelAPICallback;

public interface LoadChannelsVODTvGuidInterface {
    void laodTvGuideFailed(String str, String str2);

    void loadChannelsAndVOD(XtreamPanelAPICallback xtreamPanelAPICallback, String str);

    void loadChannelsAndVodFailed(String str, String str2);

    void loadTvGuide(XMLTVCallback xMLTVCallback);
}
