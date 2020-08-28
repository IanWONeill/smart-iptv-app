package com.nst.yourname.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class VodInfoPojo {
    @SerializedName("backdrop_path")
    @Expose
    private List<String> backdropPath = null;
    @SerializedName("cast")
    @Expose
    private String cast;
    @SerializedName("director")
    @Expose
    private String director;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;
    @SerializedName("movie_image")
    @Expose
    private String movieImage;
    @SerializedName("plot")
    @Expose
    private String plot;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("releasedate")
    @Expose
    private String releasedate;
    @SerializedName("youtube_trailer")
    @Expose
    private String youTubeTrailer;

    public String getYouTubeTrailer() {
        return this.youTubeTrailer;
    }

    public void setYouTubeTrailer(String str) {
        this.youTubeTrailer = str;
    }

    public String getImdbId() {
        return this.imdbId;
    }

    public void setImdbId(String str) {
        this.imdbId = str;
    }

    public String getMovieImage() {
        return this.movieImage;
    }

    public void setMovieImage(String str) {
        this.movieImage = str;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String str) {
        this.genre = str;
    }

    public String getPlot() {
        return this.plot;
    }

    public void setPlot(String str) {
        this.plot = str;
    }

    public String getCast() {
        return this.cast;
    }

    public void setCast(String str) {
        this.cast = str;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String str) {
        this.rating = str;
    }

    public String getDirector() {
        return this.director;
    }

    public void setDirector(String str) {
        this.director = str;
    }

    public String getReleasedate() {
        return this.releasedate;
    }

    public void setReleasedate(String str) {
        this.releasedate = str;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String str) {
        this.duration = str;
    }

    public List<String> getBackdropPath() {
        return this.backdropPath;
    }

    public void setBackdropPath(List<String> list) {
        this.backdropPath = list;
    }
}
