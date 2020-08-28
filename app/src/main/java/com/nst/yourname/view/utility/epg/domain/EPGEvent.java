package com.nst.yourname.view.utility.epg.domain;

public class EPGEvent {
    private final EPGChannel channel;
    private final String desc;
    private final long end;
    private EPGEvent nextEvent;
    private EPGEvent previousEvent;
    private final String programUrl;
    public boolean selected;
    private final long start;
    private final String title;

    public EPGEvent(EPGChannel ePGChannel, long j, long j2, String str, String str2, String str3) {
        this.channel = ePGChannel;
        this.start = j;
        this.end = j2;
        this.title = str;
        this.programUrl = str2;
        this.desc = str3;
    }

    public EPGChannel getChannel() {
        return this.channel;
    }

    public long getStart() {
        return this.start;
    }

    public long getEnd() {
        return this.end;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getProgramUrl() {
        return this.programUrl;
    }

    public boolean isCurrent(int i) {
        long currentTimeMillis = System.currentTimeMillis() + ((long) i);
        return currentTimeMillis >= this.start && currentTimeMillis <= this.end;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setNextEvent(EPGEvent ePGEvent) {
        this.nextEvent = ePGEvent;
    }

    public EPGEvent getNextEvent() {
        return this.nextEvent;
    }

    public void setPreviousEvent(EPGEvent ePGEvent) {
        this.previousEvent = ePGEvent;
    }

    public EPGEvent getPreviousEvent() {
        return this.previousEvent;
    }
}
