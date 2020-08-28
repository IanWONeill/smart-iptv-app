package com.nst.yourname.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SearchTMDBMoviesResultPojo {
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    public Integer getVoteCount() {
        return this.voteCount;
    }

    public void setVoteCount(Integer num) {
        this.voteCount = num;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }

    public Boolean getVideo() {
        return this.video;
    }

    public void setVideo(Boolean bool) {
        this.video = bool;
    }

    public Double getVoteAverage() {
        return this.voteAverage;
    }

    public void setVoteAverage(Double d) {
        this.voteAverage = d;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public Double getPopularity() {
        return this.popularity;
    }

    public void setPopularity(Double d) {
        this.popularity = d;
    }

    public String getPosterPath() {
        return this.posterPath;
    }

    public void setPosterPath(String str) {
        this.posterPath = str;
    }

    public String getOriginalLanguage() {
        return this.originalLanguage;
    }

    public void setOriginalLanguage(String str) {
        this.originalLanguage = str;
    }

    public String getOriginalTitle() {
        return this.originalTitle;
    }

    public void setOriginalTitle(String str) {
        this.originalTitle = str;
    }

    public List<Integer> getGenreIds() {
        return this.genreIds;
    }

    public void setGenreIds(List<Integer> list) {
        this.genreIds = list;
    }

    public String getBackdropPath() {
        return this.backdropPath;
    }

    public void setBackdropPath(String str) {
        this.backdropPath = str;
    }

    public Boolean getAdult() {
        return this.adult;
    }

    public void setAdult(Boolean bool) {
        this.adult = bool;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String str) {
        this.overview = str;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(String str) {
        this.releaseDate = str;
    }
}
