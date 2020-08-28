package com.nst.yourname.model;

public class VODDBModel {
    private String added;
    private String categoryId;
    private String containerExtension;
    private String customSid;
    private String directSource;
    private int idAutoVOD;
    private String name;
    private String num;
    private String seriesNo;
    private String streamIcon;
    private String streamId;
    private String streamType;

    public int getIdAutoVOD() {
        return this.idAutoVOD;
    }

    public void setIdAutoVOD(int i) {
        this.idAutoVOD = i;
    }

    public VODDBModel() {
    }

    public String getNum() {
        return this.num;
    }

    public void setNum(String str) {
        this.num = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getStreamType() {
        return this.streamType;
    }

    public void setStreamType(String str) {
        this.streamType = str;
    }

    public String getStreamId() {
        return this.streamId;
    }

    public void setStreamId(String str) {
        this.streamId = str;
    }

    public String getStreamIcon() {
        return this.streamIcon;
    }

    public void setStreamIcon(String str) {
        this.streamIcon = str;
    }

    public String getAdded() {
        return this.added;
    }

    public void setAdded(String str) {
        this.added = str;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String str) {
        this.categoryId = str;
    }

    public String getSeriesNo() {
        return this.seriesNo;
    }

    public void setSeriesNo(String str) {
        this.seriesNo = str;
    }

    public String getContainerExtension() {
        return this.containerExtension;
    }

    public void setContainerExtension(String str) {
        this.containerExtension = str;
    }

    public String getCustomSid() {
        return this.customSid;
    }

    public void setCustomSid(String str) {
        this.customSid = str;
    }

    public String getDirectSource() {
        return this.directSource;
    }

    public void setDirectSource(String str) {
        this.directSource = str;
    }

    public VODDBModel(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11) {
        this.num = str;
        this.name = str2;
        this.streamType = str3;
        this.streamId = str4;
        this.streamIcon = str5;
        this.added = str6;
        this.categoryId = str7;
        this.seriesNo = str8;
        this.containerExtension = str9;
        this.customSid = str10;
        this.directSource = str11;
    }
}
