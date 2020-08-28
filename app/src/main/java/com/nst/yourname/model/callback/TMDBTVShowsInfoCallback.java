package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nst.yourname.model.pojo.TMDBTVShowsCreatedByPojo;
import com.nst.yourname.model.pojo.TMDBTVShowsGenrePojo;
import java.util.List;

public class TMDBTVShowsInfoCallback {
    @SerializedName("created_by")
    @Expose
    private List<TMDBTVShowsCreatedByPojo> createdBy = null;
    @SerializedName("genres")
    @Expose
    private List<TMDBTVShowsGenrePojo> genres = null;

    public List<TMDBTVShowsCreatedByPojo> getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(List<TMDBTVShowsCreatedByPojo> list) {
        this.createdBy = list;
    }

    public List<TMDBTVShowsGenrePojo> getGenres() {
        return this.genres;
    }

    public void setGenres(List<TMDBTVShowsGenrePojo> list) {
        this.genres = list;
    }
}
