package com.nst.yourname.model.database;

public class DatabaseUpdatedStatusDBModel {
    private String dbCategory;
    private String dbCategoryID;
    private String dbLastUpdatedDate;
    private String dbUpadatedStatusState;
    private int idAuto;

    public String getDbCategory() {
        return this.dbCategory;
    }

    public void setDbCategory(String str) {
        this.dbCategory = str;
    }

    public String getGetDbCategoryID() {
        return this.dbCategoryID;
    }

    public void setDbCategoryID(String str) {
        this.dbCategoryID = str;
    }

    public DatabaseUpdatedStatusDBModel() {
    }

    public DatabaseUpdatedStatusDBModel(String str, String str2, String str3, String str4) {
        this.dbUpadatedStatusState = str;
        this.dbLastUpdatedDate = str2;
        this.dbCategory = str3;
        this.dbCategoryID = str4;
    }

    public int getIdAuto() {
        return this.idAuto;
    }

    public void setIdAuto(int i) {
        this.idAuto = i;
    }

    public String getDbUpadatedStatusState() {
        return this.dbUpadatedStatusState;
    }

    public void setDbUpadatedStatusState(String str) {
        this.dbUpadatedStatusState = str;
    }

    public String getDbLastUpdatedDate() {
        return this.dbLastUpdatedDate;
    }

    public void setDbLastUpdatedDate(String str) {
        this.dbLastUpdatedDate = str;
    }
}
