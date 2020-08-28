package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nst.yourname.miscelleneious.common.AppConst;

public class SeasonsDetailCallback {
    @SerializedName("air_date")
    @Expose
    public String airDate;
    @SerializedName("cover")
    @Expose
    public String cover;
    @SerializedName("cover_big")
    @Expose
    public String coverBig;
    @SerializedName("episode_count")
    @Expose
    public Integer episodeCount;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName(AppConst.SEASON_NUMBER)
    @Expose
    public Integer seasonNumber;

    public String getAirDate() {
        return this.airDate;
    }

    public void setAirDate(String str) {
        this.airDate = str;
    }

    public Integer getEpisodeCount() {
        return this.episodeCount;
    }

    public void setEpisodeCount(Integer num) {
        this.episodeCount = num;
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

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String str) {
        this.overview = str;
    }

    public Integer getSeasonNumber() {
        return this.seasonNumber;
    }

    public void setSeasonNumber(Integer num) {
        this.seasonNumber = num;
    }

    public String getCover() {
        return this.cover;
    }

    public void setCover(String str) {
        this.cover = str;
    }

    public String getCoverBig() {
        return this.coverBig;
    }

    public void setCoverBig(String str) {
        this.coverBig = str;
    }
}
