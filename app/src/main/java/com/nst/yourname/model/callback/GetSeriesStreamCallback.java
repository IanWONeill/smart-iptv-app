package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nst.yourname.miscelleneious.common.AppConst;
import java.util.ArrayList;

public class GetSeriesStreamCallback {
    @SerializedName("backdrop_path")
    @Expose
    public transient ArrayList<String> backdropPath = null;
    @SerializedName("cast")
    @Expose
    public String cast;
    @SerializedName(AppConst.CATEGORY_ID)
    @Expose
    public String categoryId;
    @SerializedName("cover")
    @Expose
    public String cover;
    @SerializedName("director")
    @Expose
    public String director;
    @SerializedName("genre")
    @Expose
    public String genre;
    @SerializedName("last_modified")
    @Expose
    public String lastModified;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName(AppConst.LOGIN_PREF_OPENED_VIDEO_ID_NUM)
    @Expose
    public Integer num;
    @SerializedName("plot")
    @Expose
    public String plot;
    @SerializedName("rating")
    @Expose
    public String rating;
    @SerializedName("releaseDate")
    @Expose
    public String releaseDate;
    @SerializedName("series_id")
    @Expose
    public Integer seriesId;
    @SerializedName("stream_type")
    @Expose
    public Object streamType;
    @SerializedName("youtube_trailer")
    @Expose
    public String youtubTrailer;

    public String getYoutubTrailer() {
        return this.youtubTrailer;
    }

    public void setYoutubTrailer(String str) {
        this.youtubTrailer = str;
    }

    public Integer getNum() {
        return this.num;
    }

    public void setNum(Integer num2) {
        this.num = num2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public Object getStreamType() {
        return this.streamType;
    }

    public void setStreamType(Object obj) {
        this.streamType = obj;
    }

    public Integer getSeriesId() {
        return this.seriesId;
    }

    public void setSeriesId(Integer num2) {
        this.seriesId = num2;
    }

    public String getCover() {
        return this.cover;
    }

    public void setCover(String str) {
        this.cover = str;
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

    public String getDirector() {
        return this.director;
    }

    public void setDirector(String str) {
        this.director = str;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String str) {
        this.genre = str;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(String str) {
        this.releaseDate = str;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(String str) {
        this.lastModified = str;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String str) {
        this.rating = str;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String str) {
        this.categoryId = str;
    }

    public ArrayList<String> getBackdropPath() {
        return this.backdropPath;
    }

    public void setBackdropPath(ArrayList<String> arrayList) {
        this.backdropPath = arrayList;
    }
}
