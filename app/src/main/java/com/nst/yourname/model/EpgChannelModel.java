package com.nst.yourname.model;

public class EpgChannelModel {
    String next = "";
    String nowPlaying = "";

    public String getNowPlaying() {
        return this.nowPlaying;
    }

    public void setNowPlaying(String str) {
        this.nowPlaying = str;
    }

    public String getNext() {
        return this.next;
    }

    public void setNext(String str) {
        this.next = str;
    }
}
