package com.nst.yourname.model;

import java.util.ArrayList;

public class LiveStreamsDBModel extends ArrayList<LiveStreamsDBModel> {
    private String added;
    private String categoryId;
    private String categoryName;
    private String contaiinerExtension;
    private String customSid;
    private String directSource;
    private String epgChannelId;
    private String epgDesc;
    private Long epgEndDate;
    private int epgPercentage;
    private Long epgStartDate;
    private String epgTitle;
    private int idAuto;
    private String live;
    private long movieDuraton;
    private long movieElapsedTime;
    private String name;
    private String num;
    private String seriesNo;
    private String streamIcon;
    private String streamId;
    private String streamType;
    private String tvArchive;
    private String tvArchiveDuration;
    private String typeName;
    private String url;
    private int userIdReffered;

    public long getMovieElapsedTime() {
        return this.movieElapsedTime;
    }

    public void setMovieElapsedTime(long j) {
        this.movieElapsedTime = j;
    }

    public long getMovieDuraton() {
        return this.movieDuraton;
    }

    public void setMovieDuraton(long j) {
        this.movieDuraton = j;
    }

    public int getUserIdReffered() {
        return this.userIdReffered;
    }

    public void setUserIdReffered(int i) {
        this.userIdReffered = i;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String str) {
        this.typeName = str;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String str) {
        this.categoryName = str;
    }

    public String getSeriesNo() {
        return this.seriesNo;
    }

    public void setSeriesNo(String str) {
        this.seriesNo = str;
    }

    public String getLive() {
        return this.live;
    }

    public void setLive(String str) {
        this.live = str;
    }

    public String getContaiinerExtension() {
        return this.contaiinerExtension;
    }

    public void setContaiinerExtension(String str) {
        this.contaiinerExtension = str;
    }

    public LiveStreamsDBModel() {
    }

    public LiveStreamsDBModel(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, int i, Long l, Long l2, String str18, String str19, int i2, long j, long j2) {
        this.num = str;
        this.name = str2;
        this.streamType = str3;
        this.streamId = str4;
        this.streamIcon = str5;
        this.epgChannelId = str6;
        this.added = str7;
        this.categoryId = str8;
        this.customSid = str9;
        this.tvArchive = str10;
        this.directSource = str11;
        this.tvArchiveDuration = str12;
        this.typeName = str13;
        this.categoryName = str14;
        this.seriesNo = str15;
        this.live = str16;
        this.contaiinerExtension = str17;
        this.epgPercentage = i;
        this.epgStartDate = l;
        this.epgEndDate = l2;
        this.epgTitle = str18;
        this.epgDesc = str19;
        this.userIdReffered = i2;
        this.movieElapsedTime = j;
        this.movieDuraton = j2;
    }

    public int getEpgPercentage() {
        return this.epgPercentage;
    }

    public Long getEpgStartDate() {
        return this.epgStartDate;
    }

    public Long getEpgEndDate() {
        return this.epgEndDate;
    }

    public String getEpgTitle() {
        return this.epgTitle;
    }

    public String getEpgDesc() {
        return this.epgDesc;
    }

    public void setEpgPercentage(int i) {
        this.epgPercentage = i;
    }

    public void setEpgStartDate(Long l) {
        this.epgStartDate = l;
    }

    public void setEpgEndDate(Long l) {
        this.epgEndDate = l;
    }

    public void setEpgTitle(String str) {
        this.epgTitle = str;
    }

    public void setEpgDesc(String str) {
        this.epgDesc = str;
    }

    public int getIdAuto() {
        return this.idAuto;
    }

    public void setIdAuto(int i) {
        this.idAuto = i;
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

    public String getEpgChannelId() {
        return this.epgChannelId;
    }

    public void setEpgChannelId(String str) {
        this.epgChannelId = str;
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

    public String getCustomSid() {
        return this.customSid;
    }

    public void setCustomSid(String str) {
        this.customSid = str;
    }

    public String getTvArchive() {
        return this.tvArchive;
    }

    public void setTvArchive(String str) {
        this.tvArchive = str;
    }

    public String getDirectSource() {
        return this.directSource;
    }

    public void setDirectSource(String str) {
        this.directSource = str;
    }

    public String getTvArchiveDuration() {
        return this.tvArchiveDuration;
    }

    public void setTvArchiveDuration(String str) {
        this.tvArchiveDuration = str;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }
}
