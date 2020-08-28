package com.nst.yourname.model.pojo;

import android.support.v17.preference.LeanbackPreferenceDialogFragment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TMDBTrailerPojo {
    @SerializedName(LeanbackPreferenceDialogFragment.ARG_KEY)
    @Expose
    private String key;
    @SerializedName("type")
    @Expose
    private String type;

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }
}
