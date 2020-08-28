package com.nst.yourname.WHMCSClientapp.modelclassess;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuyNowModelClass {
    @SerializedName("showButton")
    @Expose
    private String showButton;

    public String getShowButton() {
        return this.showButton;
    }

    public void setShowButton(String str) {
        this.showButton = str;
    }
}
