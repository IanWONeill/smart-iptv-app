package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nst.yourname.miscelleneious.common.AppConst;
import java.io.Serializable;
import java.util.Comparator;

public class GetEpisdoeDetailsCallback implements Serializable {
    public static Comparator<GetEpisdoeDetailsCallback> episodeComparator = new Comparator<GetEpisdoeDetailsCallback>() {
        /* class com.nst.yourname.model.callback.GetEpisdoeDetailsCallback.AnonymousClass1 */

        public int compare(GetEpisdoeDetailsCallback getEpisdoeDetailsCallback, GetEpisdoeDetailsCallback getEpisdoeDetailsCallback2) {
            if (AppConst.SORT_EPISODES == AppConst.SORT_EPISODES_A_TO_Z) {
                return getEpisdoeDetailsCallback.getTitle().toUpperCase().compareTo(getEpisdoeDetailsCallback2.getTitle().toUpperCase());
            }
            if (AppConst.SORT_EPISODES == AppConst.SORT_EPISODES_Z_TO_A) {
                return getEpisdoeDetailsCallback2.getTitle().toUpperCase().compareTo(getEpisdoeDetailsCallback.getTitle().toUpperCase());
            } else if (AppConst.SORT_EPISODES != AppConst.SORT_EPISODES_LASTADDED) {
                return 0;
            } else {
                return getEpisdoeDetailsCallback2.getAdded().toUpperCase().compareTo(getEpisdoeDetailsCallback.getAdded().toUpperCase());
            }
        }
    };
    @SerializedName("added")
    @Expose
    public String added;
    private String categoryId;
    @SerializedName("container_extension")
    @Expose
    public String containerExtension;
    @SerializedName("custom_sid")
    @Expose
    public String customSid;
    @SerializedName("direct_source")
    @Expose
    public String directSource;
    private String elapsed_time;
    @SerializedName("id")
    @Expose
    public String id;
    public String image;
    @SerializedName("info")
    @Expose
    private EpisodeInfoCallBack infoCallBack;
    private String movieImage;
    @SerializedName("season")
    @Expose
    public Integer seasonNumber;
    private String seriesCover = "";
    private String seriesId;
    @SerializedName("title")
    @Expose
    public String title;

    public Integer getSeasonNumber() {
        return this.seasonNumber;
    }

    public void setSeasonNumber(Integer num) {
        this.seasonNumber = num;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getContainerExtension() {
        return this.containerExtension;
    }

    public void setContainerExtension(String str) {
        this.containerExtension = str;
    }

    public String getCustomSid() {
        return this.customSid;
    }

    public void setCustomSid(String str) {
        this.customSid = str;
    }

    public String getAdded() {
        return this.added;
    }

    public void setAdded(String str) {
        this.added = str;
    }

    public String getDirectSource() {
        return this.directSource;
    }

    public void setDirectSource(String str) {
        this.directSource = str;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public String getSeriesId() {
        return this.seriesId;
    }

    public void setSeriesId(String str) {
        this.seriesId = str;
    }

    public String getElapsed_time() {
        return this.elapsed_time;
    }

    public void setElapsed_time(String str) {
        this.elapsed_time = str;
    }

    public void setCategoryId(String str) {
        this.categoryId = str;
    }

    public String getSeriesCover() {
        return this.seriesCover;
    }

    public void setSeriesCover(String str) {
        this.seriesCover = str;
    }

    private EpisodeInfoCallBack getInfoCallBack() {
        return this.infoCallBack;
    }

    private void setInfoCallBack(EpisodeInfoCallBack episodeInfoCallBack) {
        this.infoCallBack = episodeInfoCallBack;
    }

    public String getMovieImage() {
        return this.movieImage;
    }

    public void setMovieImage(String str) {
        this.movieImage = str;
    }
}
