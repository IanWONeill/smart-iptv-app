package com.nst.yourname.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TMDBTVShowsCreatedByPojo {
    @SerializedName("credit_id")
    @Expose
    private String creditId;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_path")
    @Expose
    private Object profilePath;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }

    public String getCreditId() {
        return this.creditId;
    }

    public void setCreditId(String str) {
        this.creditId = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public Integer getGender() {
        return this.gender;
    }

    public void setGender(Integer num) {
        this.gender = num;
    }

    public Object getProfilePath() {
        return this.profilePath;
    }

    public void setProfilePath(Object obj) {
        this.profilePath = obj;
    }
}
