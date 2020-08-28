package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class ServerInfoCallback {
    @SerializedName("https_port")
    @Expose
    private String httpsPort;
    @SerializedName(IjkMediaPlayer.OnNativeInvokeListener.ARG_PORT)
    @Expose
    private String port;
    @SerializedName("rtmp_port")
    @Expose
    private String rtmpPort;
    @SerializedName("server_protocol")
    @Expose
    private String serverProtocal;
    @SerializedName("timestamp_now")
    @Expose
    private String timeCurrent;
    @SerializedName("time_now")
    @Expose
    private String timeNow;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("url")
    @Expose
    private String url;

    public String getHttpsPort() {
        return this.httpsPort;
    }

    public void setHttpsPort(String str) {
        this.httpsPort = str;
    }

    public String getServerProtocal() {
        return this.serverProtocal;
    }

    public void setServerProtocal(String str) {
        this.serverProtocal = str;
    }

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

    public String getRtmpPort() {
        return this.rtmpPort;
    }

    public void setRtmpPort(String str) {
        this.rtmpPort = str;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public void setTimezone(String str) {
        this.timezone = str;
    }

    public String getTimeNow() {
        return this.timeNow;
    }

    public void setTimeNow(String str) {
        this.timeNow = str;
    }

    public String getTimeCurrent() {
        return this.timeCurrent;
    }

    public void setTimeCurrent(String str) {
        this.timeCurrent = str;
    }
}
