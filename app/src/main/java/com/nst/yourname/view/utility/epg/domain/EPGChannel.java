package com.nst.yourname.view.utility.epg.domain;

import com.google.common.collect.Lists;
import java.util.List;

public class EPGChannel {
    private final String catID;
    private final int channelID;
    private final String epgChannelID;
    private List<EPGEvent> events = Lists.newArrayList();
    private final String imageURL;
    private final String name;
    private EPGChannel nextChannel;
    private final String num;
    private final String openedCategoryID;
    private EPGChannel previousChannel;
    private final String streamID;
    private final String videoUrl;

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public String getOpenedCategoryID() {
        return this.openedCategoryID;
    }

    public void setEvents(List<EPGEvent> list) {
        this.events = list;
    }

    public EPGChannel(String str, String str2, int i, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.imageURL = str;
        this.name = str2;
        this.channelID = i;
        this.streamID = str3;
        this.catID = str6;
        this.num = str4;
        this.epgChannelID = str5;
        this.videoUrl = str7;
        this.openedCategoryID = str8;
    }

    public String getVideoURL() {
        return this.videoUrl;
    }

    public int getChannelID() {
        return this.channelID;
    }

    public String getStreamID() {
        return this.streamID;
    }

    public String getName() {
        return this.name;
    }

    public String getCatID() {
        return this.catID;
    }

    public String getEpgChannelID() {
        return this.epgChannelID;
    }

    public String getNum() {
        return this.num;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public List<EPGEvent> getEvents() {
        return this.events;
    }

    public EPGChannel getPreviousChannel() {
        return this.previousChannel;
    }

    public void setPreviousChannel(EPGChannel ePGChannel) {
        this.previousChannel = ePGChannel;
    }

    public EPGChannel getNextChannel() {
        return this.nextChannel;
    }

    public void setNextChannel(EPGChannel ePGChannel) {
        this.nextChannel = ePGChannel;
    }

    public EPGEvent addEvent(EPGEvent ePGEvent) {
        this.events.add(ePGEvent);
        return ePGEvent;
    }
}
