package com.nst.yourname.model;

public class FavouriteDBModel {
    private String categoryID;
    private int id;
    private String name;
    private String num;
    private int streamID;
    private String type;
    private int userID;

    public FavouriteDBModel() {
    }

    public FavouriteDBModel(int i, String str, String str2, String str3, int i2) {
        this.streamID = i;
        this.categoryID = str;
        this.type = str2;
        this.name = str3;
        this.userID = i2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public int getStreamID() {
        return this.streamID;
    }

    public String getType() {
        return this.type;
    }

    public void setStreamID(int i) {
        this.streamID = i;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getCategoryID() {
        return this.categoryID;
    }

    public void setCategoryID(String str) {
        this.categoryID = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int i) {
        this.userID = i;
    }

    public String getNum() {
        return this.num;
    }

    public void setNum(String str) {
        this.num = str;
    }
}
