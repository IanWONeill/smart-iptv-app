package com.nst.yourname.model;

public class MultiUserDBModel {
    private String date;
    private String m3uType;
    private String magportal;
    private String magportal2;
    private String name;
    private String password;
    private String serverUrl;
    private String type;
    private String username;

    public MultiUserDBModel() {
    }

    public MultiUserDBModel(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.name = str;
        this.username = str2;
        this.password = str3;
        this.magportal = str4;
        this.magportal2 = str7;
        this.type = str5;
        this.m3uType = str6;
    }

    public String getname() {
        return this.name;
    }

    public void setname(String str) {
        this.name = str;
    }

    public String getusername() {
        return this.username;
    }

    public String getpassword() {
        return this.password;
    }

    public void setusername(String str) {
        this.username = str;
    }

    public void setpassword(String str) {
        this.password = str;
    }

    public String getmagportal() {
        return this.magportal;
    }

    public String getmagportal2() {
        return this.magportal2;
    }

    public void setmagportal(String str) {
        this.magportal = str;
    }

    public void setmagportal2(String str) {
        this.magportal2 = str;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getType() {
        return this.type;
    }

    public void setM3uType(String str) {
        this.m3uType = str;
    }

    public String getM3uType() {
        return this.m3uType;
    }

    public void setServerUrl(String str) {
        this.serverUrl = str;
    }

    public String getServerUrl() {
        return this.serverUrl;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public String getDate() {
        return this.date;
    }
}
