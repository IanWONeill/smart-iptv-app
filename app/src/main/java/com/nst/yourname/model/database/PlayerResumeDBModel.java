package com.nst.yourname.model.database;

public class PlayerResumeDBModel {
    private String eventType;
    private int idAuto;
    private long streamDuration;
    private boolean streamFinish;
    private String streamId;
    private long timeElapsed;

    public long getStreamDuration() {
        return this.streamDuration;
    }

    public void setStreamDuration(long j) {
        this.streamDuration = j;
    }

    public PlayerResumeDBModel() {
    }

    public PlayerResumeDBModel(String str, String str2, boolean z, long j, long j2) {
        this.eventType = str;
        this.streamId = str2;
        this.streamFinish = z;
        this.timeElapsed = j;
        this.streamDuration = j2;
    }

    public int getIdAuto() {
        return this.idAuto;
    }

    public void setIdAuto(int i) {
        this.idAuto = i;
    }

    public String getEventType() {
        return this.eventType;
    }

    public void setEventType(String str) {
        this.eventType = str;
    }

    public String getStreamId() {
        return this.streamId;
    }

    public void setStreamId(String str) {
        this.streamId = str;
    }

    public boolean isStreamFinish() {
        return this.streamFinish;
    }

    public void setStreamFinish(boolean z) {
        this.streamFinish = z;
    }

    public long getTimeElapsed() {
        return this.timeElapsed;
    }

    public void setTimeElapsed(long j) {
        this.timeElapsed = j;
    }
}
