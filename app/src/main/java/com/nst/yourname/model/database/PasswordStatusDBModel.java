package com.nst.yourname.model.database;

public class PasswordStatusDBModel {
    private int idPaswordStaus;
    private String passwordStatus;
    private String passwordStatusCategoryId;
    private String passwordStatusUserDetail;
    private int userID;

    public int getIdPaswordStaus() {
        return this.idPaswordStaus;
    }

    public void setIdPaswordStaus(int i) {
        this.idPaswordStaus = i;
    }

    public String getPasswordStatusCategoryId() {
        return this.passwordStatusCategoryId;
    }

    public void setPasswordStatusCategoryId(String str) {
        this.passwordStatusCategoryId = str;
    }

    public String getPasswordStatusUserDetail() {
        return this.passwordStatusUserDetail;
    }

    public void setPasswordStatusUserDetail(String str) {
        this.passwordStatusUserDetail = str;
    }

    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int i) {
        this.userID = i;
    }

    public String getPasswordStatus() {
        return this.passwordStatus;
    }

    public void setPasswordStatus(String str) {
        this.passwordStatus = str;
    }

    public PasswordStatusDBModel() {
    }

    public PasswordStatusDBModel(String str, String str2, String str3) {
        this.passwordStatusCategoryId = str;
        this.passwordStatusUserDetail = str2;
        this.passwordStatus = str3;
    }
}
