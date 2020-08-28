package com.nst.yourname.model.database;

public class PasswordDBModel {
    private int id;
    private String userDetail;
    private int userId;
    private String userPassword;

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getUserDetail() {
        return this.userDetail;
    }

    public void setUserDetail(String str) {
        this.userDetail = str;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public void setUserPassword(String str) {
        this.userPassword = str;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int i) {
        this.userId = i;
    }

    public PasswordDBModel() {
    }

    public PasswordDBModel(String str, String str2) {
        this.userDetail = str;
        this.userPassword = str2;
    }
}
