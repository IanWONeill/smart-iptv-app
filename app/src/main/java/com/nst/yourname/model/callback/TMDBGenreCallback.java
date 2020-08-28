package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TMDBGenreCallback {
    @SerializedName("genres")
    @Expose
    private List<Object> genres = null;
    @SerializedName("runtime")
    @Expose
    private Object runtime;

    public List<Object> getGenres() {
        return this.genres;
    }

    public void setGenres(List<Object> list) {
        this.genres = list;
    }

    public Object getRuntime() {
        return this.runtime;
    }

    public void setRuntime(Object obj) {
        this.runtime = obj;
    }
}
