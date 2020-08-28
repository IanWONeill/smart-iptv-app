package com.nst.yourname.model.pojo;

import com.anjlab.android.iab.v3.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EpgListingPojo {
    @SerializedName("channel_id")
    @Expose
    private String channelId;
    @SerializedName(Constants.RESPONSE_DESCRIPTION)
    @Expose
    private String description;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("epg_id")
    @Expose
    private String epgId;
    @SerializedName("has_archive")
    @Expose
    private Integer hasArchive;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("now_playing")
    @Expose
    private Integer nowPlaying;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("start_timestamp")
    @Expose
    private String startTimestamp;
    @SerializedName("stop_timestamp")
    @Expose
    private String stopTimestamp;
    @SerializedName("title")
    @Expose
    private String title;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getEpgId() {
        return this.epgId;
    }

    public void setEpgId(String str) {
        this.epgId = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getLang() {
        return this.lang;
    }

    public void setLang(String str) {
        this.lang = str;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String str) {
        this.start = str;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String str) {
        this.end = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public String getStartTimestamp() {
        return this.startTimestamp;
    }

    public void setStartTimestamp(String str) {
        this.startTimestamp = str;
    }

    public String getStopTimestamp() {
        return this.stopTimestamp;
    }

    public void setStopTimestamp(String str) {
        this.stopTimestamp = str;
    }

    public Integer getNowPlaying() {
        return this.nowPlaying;
    }

    public void setNowPlaying(Integer num) {
        this.nowPlaying = num;
    }

    public Integer getHasArchive() {
        return this.hasArchive;
    }

    public void setHasArchive(Integer num) {
        this.hasArchive = num;
    }
}
