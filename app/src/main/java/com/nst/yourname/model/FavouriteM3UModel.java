package com.nst.yourname.model;

public class FavouriteM3UModel {
    private String categoryID;
    private int id;
    private String name;
    private String url;
    private int userID;

    public FavouriteM3UModel() {
    }

    public FavouriteM3UModel(String str, int i, String str2, String str3) {
        this.url = str;
        this.userID = i;
        this.name = str2;
        this.categoryID = str3;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int i) {
        this.userID = i;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    public void setCategoryID(String str) {
        this.categoryID = str;
    }

    public String getCategoryID() {
        return this.categoryID;
    }
}
