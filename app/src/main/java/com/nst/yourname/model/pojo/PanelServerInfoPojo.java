package com.nst.yourname.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class PanelServerInfoPojo {
    @SerializedName(IjkMediaPlayer.OnNativeInvokeListener.ARG_PORT)
    @Expose
    private String port;
    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String str) {
        this.port = str;
    }
}
