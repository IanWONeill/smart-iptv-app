package com.nst.yourname.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FreeTrailModelClass {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private String result;

    public String getResult() {
        return this.result;
    }

    public void setResult(String str) {
        this.result = str;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }
}
