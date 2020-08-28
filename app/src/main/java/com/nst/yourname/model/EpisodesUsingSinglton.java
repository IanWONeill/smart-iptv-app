package com.nst.yourname.model;

import com.nst.yourname.model.callback.GetEpisdoeDetailsCallback;
import java.util.List;

public class EpisodesUsingSinglton {
    private static EpisodesUsingSinglton myObj;
    private List<GetEpisdoeDetailsCallback> episodeList;

    private EpisodesUsingSinglton() {
    }

    public static EpisodesUsingSinglton getInstance() {
        if (myObj == null) {
            myObj = new EpisodesUsingSinglton();
        }
        return myObj;
    }

    public void setEpisodeList(List<GetEpisdoeDetailsCallback> list) {
        this.episodeList = list;
    }

    public List<GetEpisdoeDetailsCallback> getEpisodeList() {
        return this.episodeList;
    }
}
