package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nst.yourname.miscelleneious.common.AppConst;

public class VodStreamsCallback {
    @SerializedName("added")
    @Expose
    private String added;
    @SerializedName(AppConst.CATEGORY_ID)
    @Expose
    private String categoryId;
    @SerializedName("container_extension")
    @Expose
    private String containerExtension;
    @SerializedName("custom_sid")
    @Expose
    private String customSid;
    @SerializedName("direct_source")
    @Expose
    private String directSource;
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
    private Integer streamId;
    @SerializedName("stream_type")
    @Expose
    private String streamType;

    public String getOriginalStreamType() {
        return AppConst.EVENT_TYPE_MOVIE;
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

    public Object getSeriesNo() {
        return this.seriesNo;
    }

    public void setSeriesNo(Object obj) {
        this.seriesNo = obj;
    }

    public String getContainerExtension() {
        return this.containerExtension;
    }

    public void setContainerExtension(String str) {
        this.containerExtension = str;
    }

    public String getCustomSid() {
        return this.customSid;
    }

    public void setCustomSid(String str) {
        this.customSid = str;
    }

    public String getDirectSource() {
        return this.directSource;
    }

    public void setDirectSource(String str) {
        this.directSource = str;
    }
}
