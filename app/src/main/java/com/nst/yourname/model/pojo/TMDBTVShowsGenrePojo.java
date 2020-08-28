package com.nst.yourname.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TMDBTVShowsGenrePojo {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

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
}
