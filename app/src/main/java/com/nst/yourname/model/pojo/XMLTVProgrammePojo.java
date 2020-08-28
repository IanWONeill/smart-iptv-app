package com.nst.yourname.model.pojo;

import java.io.Serializable;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "programme", strict = false)
public class XMLTVProgrammePojo implements Serializable {
    @Path("category")
    @Text(required = false)
    private String category;
    @Attribute(name = "channel", required = false)
    private String channel;
    @Path("country")
    @Text(required = false)
    private String country;
    @Path("date")
    @Text(required = false)
    private String date;
    @Path("desc")
    @Text(required = false)
    private String desc;
    private String endTimeStamp;
    @Path("episode-num")
    @Text(required = false)
    private String episode_num;
    @Path("icon")
    @Text(required = false)
    private String icon;
    @Attribute(name = "start", required = false)
    private String start;
    private String startTimeStamp;
    @Attribute(name = "stop", required = false)
    private String stop;
    @Path("sub-title")
    @Text(required = false)
    private String sub_title;
    @Path("title")
    @Text(required = false)
    private String title;

    public String getStartTimeStamp() {
        return this.startTimeStamp;
    }

    public void setStartTimeStamp(String str) {
        this.startTimeStamp = str;
    }

    public String getEndTimeStamp() {
        return this.endTimeStamp;
    }

    public void setEndTimeStamp(String str) {
        this.endTimeStamp = str;
    }

    public String getSub_title() {
        return this.sub_title;
    }

    public void setSub_title(String str) {
        this.sub_title = str;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String str) {
        this.icon = str;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String str) {
        this.country = str;
    }

    public String getStop() {
        return this.stop;
    }

    public void setStop(String str) {
        this.stop = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String str) {
        this.start = str;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String str) {
        this.channel = str;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String str) {
        this.category = str;
    }

    public String getEpisodeNum() {
        return this.episode_num;
    }

    public void setEpisodeNum(String str) {
        this.episode_num = str;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public String toString() {
        return "ClassPojo [stop = " + this.stop + ",  title = " + this.title + ", category = " + this.category + ", episode-num = " + this.episode_num + ", date = " + this.date + ", country = " + this.country + ", icon = " + this.icon + ", sub-title = " + this.sub_title + ",desc = " + this.desc + ", start = " + this.start + ", channel = " + this.channel + "]";
    }
}
