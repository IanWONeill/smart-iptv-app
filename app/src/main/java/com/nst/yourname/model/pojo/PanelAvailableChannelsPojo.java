package com.nst.yourname.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nst.yourname.miscelleneious.common.AppConst;

public class PanelAvailableChannelsPojo {
    @SerializedName("added")
    @Expose
    private String added;
    @SerializedName(AppConst.CATEGORY_ID)
    @Expose
    private String categoryId;
    @SerializedName(AppConst.CATEGORY_NAME)
    @Expose
    private String categoryName;
    @SerializedName("container_extension")
    @Expose
    private Object containerExtension;
    @SerializedName("custom_sid")
    @Expose
    private String customSid;
    @SerializedName("direct_source")
    @Expose
    private String directSource;
    @SerializedName("epg_channel_id")
    @Expose
    private String epgChannelId;
    @SerializedName("live")
    @Expose
    private String live;
    private long movieDuration;
    private long movieElapsedTime;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_NUM)
    @Expose
    private Integer num;
    @SerializedName("series_no")
    @Expose
    private Object seriesNo;
    @SerializedName("stream_icon")
    @Expose
    private String streamIcon;
    @SerializedName("stream_id")
    @Expose
    private String streamId;
    @SerializedName("stream_type")
    @Expose
    private String streamType;
    @SerializedName("tv_archive")
    @Expose
    private Integer tvArchive;
    @SerializedName("tv_archive_duration")
    @Expose
    private String tvArchiveDuration;
    @SerializedName("type_name")
    @Expose
    private String typeName;
    private String url;
    private int userIdReferred;

    public long getMovieElapsedTime() {
        return this.movieElapsedTime;
    }

    public void setMovieElapsedTime(long j) {
        this.movieElapsedTime = j;
    }

    public long getMovieDuration() {
        return this.movieDuration;
    }

    public void setMovieDuration(long j) {
        this.movieDuration = j;
    }

    public int getUserIdReferred() {
        return this.userIdReferred;
    }

    public void setUserIdReferred(int i) {
        this.userIdReferred = i;
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

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String str) {
        this.typeName = str;
    }

    public String getStreamId() {
        return this.streamId;
    }

    public void setStreamId(String str) {
        this.streamId = str;
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

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String str) {
        this.categoryName = str;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String str) {
        this.categoryId = str;
    }

    public Object getSeriesNo() {
        return this.seriesNo;
    }

    public void setSeriesNo(Object obj) {
        this.seriesNo = obj;
    }

    public String getLive() {
        return this.live;
    }

    public void setLive(String str) {
        this.live = str;
    }

    public Object getContainerExtension() {
        return this.containerExtension;
    }

    public void setContainerExtension(Object obj) {
        this.containerExtension = obj;
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

    public String getTvArchiveDuration() {
        return this.tvArchiveDuration;
    }

    public void setTvArchiveDuration(String str) {
        this.tvArchiveDuration = str;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }
}
