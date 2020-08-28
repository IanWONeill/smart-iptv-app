package com.nst.yourname.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TMDBCrewPojo {
    @SerializedName("credit_id")
    @Expose
    private String creditId;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("job")
    @Expose
    private String job;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    public String getCreditId() {
        return this.creditId;
    }

    public void setCreditId(String str) {
        this.creditId = str;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String str) {
        this.department = str;
    }

    public Integer getGender() {
        return this.gender;
    }

    public void setGender(Integer num) {
        this.gender = num;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }

    public String getJob() {
        return this.job;
    }

    public void setJob(String str) {
        this.job = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getProfilePath() {
        return this.profilePath;
    }

    public void setProfilePath(String str) {
        this.profilePath = str;
    }
}
