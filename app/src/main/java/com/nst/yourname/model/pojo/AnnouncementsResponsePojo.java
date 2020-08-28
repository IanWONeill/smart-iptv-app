package com.nst.yourname.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnnouncementsResponsePojo {
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Title")
    @Expose
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(String str) {
        this.createDate = str;
    }
}
