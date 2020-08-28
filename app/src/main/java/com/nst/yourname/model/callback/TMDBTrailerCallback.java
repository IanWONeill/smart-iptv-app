package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nst.yourname.model.pojo.TMDBTrailerPojo;
import java.util.List;

public class TMDBTrailerCallback {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<TMDBTrailerPojo> results = null;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }

    public List<TMDBTrailerPojo> getResults() {
        return this.results;
    }

    public void setResults(List<TMDBTrailerPojo> list) {
        this.results = list;
    }
}
