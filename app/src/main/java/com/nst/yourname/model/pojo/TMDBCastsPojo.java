package com.nst.yourname.model.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TMDBCastsPojo {
    @SerializedName("cast_id")
    @Expose
    private Integer castId;
    @SerializedName("character")
    @Expose
    private String character;
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
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    public Integer getCastId() {
        return this.castId;
    }

    public void setCastId(Integer num) {
        this.castId = num;
    }

    public String getCharacter() {
        return this.character;
    }

    public void setCharacter(String str) {
        this.character = str;
    }

    public String getCreditId() {
        return this.creditId;
    }

    public void setCreditId(String str) {
        this.creditId = str;
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

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public Integer getOrder() {
        return this.order;
    }

    public void setOrder(Integer num) {
        this.order = num;
    }

    public String getProfilePath() {
        return this.profilePath;
    }

    public void setProfilePath(String str) {
        this.profilePath = str;
    }
}
