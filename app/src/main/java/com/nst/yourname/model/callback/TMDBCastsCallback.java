package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nst.yourname.model.pojo.TMDBCastsPojo;
import com.nst.yourname.model.pojo.TMDBCrewPojo;
import java.util.List;

public class TMDBCastsCallback {
    @SerializedName("cast")
    @Expose
    private List<TMDBCastsPojo> cast = null;
    @SerializedName("crew")
    @Expose
    private List<TMDBCrewPojo> crew = null;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }

    public List<TMDBCastsPojo> getCast() {
        return this.cast;
    }

    public void setCast(List<TMDBCastsPojo> list) {
        this.cast = list;
    }

    public List<TMDBCrewPojo> getCrew() {
        return this.crew;
    }

    public void setCrew(List<TMDBCrewPojo> list) {
        this.crew = list;
    }
}
