package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nst.yourname.model.pojo.PanelAvailableChannelsPojo;
import com.nst.yourname.model.pojo.PanelCategoriesPojo;
import com.nst.yourname.model.pojo.PanelServerInfoPojo;
import com.nst.yourname.model.pojo.PanelUserInfoPojo;
import java.util.Map;

public class XtreamPanelAPICallback {
    @SerializedName("available_channels")
    @Expose
    public Map<String, PanelAvailableChannelsPojo> availableChannels;
    @SerializedName("categories")
    @Expose
    private PanelCategoriesPojo categories;
    @SerializedName("server_info")
    @Expose
    private PanelServerInfoPojo serverInfo;
    @SerializedName("user_info")
    @Expose
    private PanelUserInfoPojo userInfo;

    public PanelUserInfoPojo getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(PanelUserInfoPojo panelUserInfoPojo) {
        this.userInfo = panelUserInfoPojo;
    }

    public PanelServerInfoPojo getServerInfo() {
        return this.serverInfo;
    }

    public void setServerInfo(PanelServerInfoPojo panelServerInfoPojo) {
        this.serverInfo = panelServerInfoPojo;
    }

    public PanelCategoriesPojo getCategories() {
        return this.categories;
    }

    public void setCategories(PanelCategoriesPojo panelCategoriesPojo) {
        this.categories = panelCategoriesPojo;
    }

    public Map<String, PanelAvailableChannelsPojo> getAvailableChannels() {
        return this.availableChannels;
    }

    public void setAvailableChannels(PanelAvailableChannelsPojo panelAvailableChannelsPojo) {
        this.availableChannels = (Map) panelAvailableChannelsPojo;
    }
}
