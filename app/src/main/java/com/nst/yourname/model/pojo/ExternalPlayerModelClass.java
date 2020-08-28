package com.nst.yourname.model.pojo;

public class ExternalPlayerModelClass {
    private String appicon = "";
    private String appname = "";
    private int id = 0;
    private String packagename = "";

    public String getAppname() {
        return this.appname;
    }

    public void setAppname(String str) {
        this.appname = str;
    }

    public String getPackagename() {
        return this.packagename;
    }

    public void setPackagename(String str) {
        this.packagename = str;
    }

    public String getAppicon() {
        return this.appicon;
    }

    public void setAppicon(String str) {
        this.appicon = str;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }
}
