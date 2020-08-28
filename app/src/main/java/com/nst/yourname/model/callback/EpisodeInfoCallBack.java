package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EpisodeInfoCallBack {
    @SerializedName("movie_image")
    @Expose
    private String movieImage;

    public String getMovieImage() {
        return this.movieImage;
    }

    public void setMovieImage(String str) {
        this.movieImage = str;
    }
}
