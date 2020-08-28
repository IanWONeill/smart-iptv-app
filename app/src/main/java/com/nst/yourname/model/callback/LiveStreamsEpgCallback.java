package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nst.yourname.model.pojo.EpgListingPojo;
import java.io.Serializable;
import java.util.List;

public class LiveStreamsEpgCallback implements Serializable {
    @SerializedName("epg_listings")
    @Expose
    private List<EpgListingPojo> epgListingPojos = null;

    public List<EpgListingPojo> getEpgListingPojos() {
        return this.epgListingPojos;
    }

    public void setEpgListingPojos(List<EpgListingPojo> list) {
        this.epgListingPojos = list;
    }
}
