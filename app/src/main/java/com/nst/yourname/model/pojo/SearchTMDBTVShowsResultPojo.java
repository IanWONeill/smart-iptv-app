package com.nst.yourname.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SearchTMDBTVShowsResultPojo {
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("origin_country")
    @Expose
    private List<String> originCountry = null;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_name")
    @Expose
    private String originalName;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    public String getOriginalName() {
        return this.originalName;
    }

    public void setOriginalName(String str) {
        this.originalName = str;
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

    public Integer getVoteCount() {
        return this.voteCount;
    }

    public void setVoteCount(Integer num) {
        this.voteCount = num;
    }

    public Double getVoteAverage() {
        return this.voteAverage;
    }

    public void setVoteAverage(Double d) {
        this.voteAverage = d;
    }

    public String getPosterPath() {
        return this.posterPath;
    }

    public void setPosterPath(String str) {
        this.posterPath = str;
    }

    public String getFirstAirDate() {
        return this.firstAirDate;
    }

    public void setFirstAirDate(String str) {
        this.firstAirDate = str;
    }

    public Double getPopularity() {
        return this.popularity;
    }

    public void setPopularity(Double d) {
        this.popularity = d;
    }

    public List<Integer> getGenreIds() {
        return this.genreIds;
    }

    public void setGenreIds(List<Integer> list) {
        this.genreIds = list;
    }

    public String getOriginalLanguage() {
        return this.originalLanguage;
    }

    public void setOriginalLanguage(String str) {
        this.originalLanguage = str;
    }

    public String getBackdropPath() {
        return this.backdropPath;
    }

    public void setBackdropPath(String str) {
        this.backdropPath = str;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String str) {
        this.overview = str;
    }

    public List<String> getOriginCountry() {
        return this.originCountry;
    }

    public void setOriginCountry(List<String> list) {
        this.originCountry = list;
    }
}
