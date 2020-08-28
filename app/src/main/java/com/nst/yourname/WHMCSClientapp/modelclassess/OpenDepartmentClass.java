package com.nst.yourname.WHMCSClientapp.modelclassess;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpenDepartmentClass {
    @SerializedName("c")
    @Expose
    private String c;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("tid")
    @Expose
    private String tid;

    public String getResult() {
        return this.result;
    }

    public void setResult(String str) {
        this.result = str;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer num) {
        this.id = num;
    }

    public String getTid() {
        return this.tid;
    }

    public void setTid(String str) {
        this.tid = str;
    }

    public String getC() {
        return this.c;
    }

    public void setC(String str) {
        this.c = str;
    }
}
