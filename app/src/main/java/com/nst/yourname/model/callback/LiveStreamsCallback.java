package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nst.yourname.miscelleneious.common.AppConst;
import java.io.Serializable;

public class LiveStreamsCallback implements Serializable {
    public String activeEpg = "";
    @SerializedName("added")
    @Expose
    private String added;
    @SerializedName(AppConst.CATEGORY_ID)
    @Expose
    private String categoryId;
    @SerializedName("custom_sid")
    @Expose
    private String customSid;
    @SerializedName("direct_source")
    @Expose
    private String directSource;
    @SerializedName("epg_channel_id")
    @Expose
    private String epgChannelId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_NUM)
    @Expose
    private Integer num;
    @SerializedName("stream_icon")
    @Expose
    private String streamIcon;
    @SerializedName("stream_id")
    @Expose
    private Integer streamId;
    @SerializedName("stream_type")
    @Expose
    private String streamType;
    @SerializedName("tv_archive")
    @Expose
    private Integer tvArchive;
    @SerializedName("tv_archive_duration")
    @Expose
    private Integer tvArchiveDuration;

    public String getOriginalStreamType() {
        return "live";
    }

    public Integer getNum() {
        return this.num;
    }

    public void setNum(Integer num2) {
        this.num = num2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getStreamType() {
        return this.streamType;
    }

    public void setStreamType(String str) {
        this.streamType = str;
    }

    public Integer getStreamId() {
        return this.streamId;
    }

    public void setStreamId(Integer num2) {
        this.streamId = num2;
    }

    public String getStreamIcon() {
        return this.streamIcon;
    }

    public void setStreamIcon(String str) {
        this.streamIcon = str;
    }

    public String getEpgChannelId() {
        return this.epgChannelId;
    }

    public void setEpgChannelId(String str) {
        this.epgChannelId = str;
    }

    public String getAdded() {
        return this.added;
    }

    public void setAdded(String str) {
        this.added = str;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String str) {
        this.categoryId = str;
    }

    public String getCustomSid() {
        return this.customSid;
    }

    public void setCustomSid(String str) {
        this.customSid = str;
    }

    public Integer getTvArchive() {
        return this.tvArchive;
    }

    public void setTvArchive(Integer num2) {
        this.tvArchive = num2;
    }

    public String getDirectSource() {
        return this.directSource;
    }

    public void setDirectSource(String str) {
        this.directSource = str;
    }

    public Integer getTvArchiveDuration() {
        return this.tvArchiveDuration;
    }

    public void setTvArchiveDuration(Integer num2) {
        this.tvArchiveDuration = num2;
    }

    public String getActiveEpg() {
        return this.activeEpg;
    }

    public void setActiveEpg(String str) {
        this.activeEpg = str;
    }
}
