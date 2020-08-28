package com.nst.yourname.model.callback;

public class SeriesDBModel {
    private String backdrop;
    private String cast;
    private String categoryId;
    private String cover;
    private String director;
    private String genre;
    private int idAuto;
    private String last_modified;
    private String loginType;
    private String name;
    private String num;
    private String plot;
    private String rating;
    private String releaseDate;
    private String seasons;
    private int seriesID;
    private String streamType;
    private String youTubeTrailer;

    public String getBackdrop() {
        return this.backdrop;
    }

    public void setBackdrop(String str) {
        this.backdrop = str;
    }

    public String getYouTubeTrailer() {
        return this.youTubeTrailer;
    }

    public void setYouTubeTrailer(String str) {
        this.youTubeTrailer = str;
    }

    public String getlast_modified() {
        return this.last_modified;
    }

    public void setlast_modified(String str) {
        this.last_modified = str;
    }

    public String getrating() {
        return this.rating;
    }

    public void setrating(String str) {
        this.rating = str;
    }

    public SeriesDBModel() {
    }

    public SeriesDBModel(String str, String str2, String str3, int i, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13) {
        this.num = str;
        this.name = str2;
        this.streamType = str3;
        this.seriesID = i;
        this.cover = str4;
        this.plot = str5;
        this.categoryId = str6;
        this.cast = str7;
        this.director = str8;
        this.genre = str9;
        this.releaseDate = str10;
        this.last_modified = str11;
        this.rating = str12;
        this.youTubeTrailer = str13;
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

    public int getseriesID() {
        return this.seriesID;
    }

    public void setseriesID(int i) {
        this.seriesID = i;
    }

    public String getcover() {
        return this.cover;
    }

    public void setcover(String str) {
        this.cover = str;
    }

    public String getplot() {
        return this.plot;
    }

    public void setplot(String str) {
        this.plot = str;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String str) {
        this.categoryId = str;
    }

    public String getcast() {
        return this.cast;
    }

    public void setcast(String str) {
        this.cast = str;
    }

    public String getdirector() {
        return this.director;
    }

    public void setdirector(String str) {
        this.director = str;
    }

    public String getgenre() {
        return this.genre;
    }

    public void setgenre(String str) {
        this.genre = str;
    }

    public String getreleaseDate() {
        return this.releaseDate;
    }

    public void setreleaseDate(String str) {
        this.releaseDate = str;
    }

    public void setSeasons(String str) {
        this.seasons = str;
    }

    public String getSeasons() {
        return this.seasons;
    }

    public void setloginType(String str) {
        this.loginType = str;
    }

    public String getLoginType() {
        return this.loginType;
    }
}
