package com.nst.yourname.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.callback.LiveStreamCategoriesCallback;
import com.nst.yourname.model.callback.VodCategoriesCallback;
import java.util.ArrayList;

public class PanelCategoriesPojo {
    @SerializedName("live")
    @Expose
    private ArrayList<LiveStreamCategoriesCallback> live = null;
    @SerializedName(AppConst.EVENT_TYPE_MOVIE)
    @Expose
    private ArrayList<VodCategoriesCallback> movie = null;

    public ArrayList<VodCategoriesCallback> getMovie() {
        return this.movie;
    }

    public void setMovie(ArrayList<VodCategoriesCallback> arrayList) {
        this.movie = arrayList;
    }

    public ArrayList<LiveStreamCategoriesCallback> getLive() {
        return this.live;
    }

    public void setLive(ArrayList<LiveStreamCategoriesCallback> arrayList) {
        this.live = arrayList;
    }
}
