package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GetSeasonsEpisodeCallback {
    @SerializedName("episodes")
    @Expose
    public GetEpisodesPojo episodes;
    @SerializedName("seasons")
    @Expose
    public List<Integer> seasons = null;

    public GetEpisodesPojo getEpisodes() {
        return this.episodes;
    }

    public void setEpisodes(GetEpisodesPojo getEpisodesPojo) {
        this.episodes = getEpisodesPojo;
    }

    public List<Integer> getSeasons() {
        return this.seasons;
    }

    public void setSeasons(List<Integer> list) {
        this.seasons = list;
    }
}
