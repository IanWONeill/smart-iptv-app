package com.nst.yourname.WHMCSClientapp.modelclassess;

import android.support.v4.app.NotificationCompat;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TicketModelClass {
    @SerializedName("numreturned")
    @Expose
    private Integer numreturned;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("startnumber")
    @Expose
    private Integer startnumber;
    @SerializedName("tickets")
    @Expose
    private Tickets tickets;
    @SerializedName("totalresults")
    @Expose
    private String totalresults;

    public String getResult() {
        return this.result;
    }

    public void setResult(String str) {
        this.result = str;
    }

    public String getTotalresults() {
        return this.totalresults;
    }

    public void setTotalresults(String str) {
        this.totalresults = str;
    }

    public Integer getStartnumber() {
        return this.startnumber;
    }

    public void setStartnumber(Integer num) {
        this.startnumber = num;
    }

    public Integer getNumreturned() {
        return this.numreturned;
    }

    public void setNumreturned(Integer num) {
        this.numreturned = num;
    }

    public Tickets getTickets() {
        return this.tickets;
    }

    public void setTickets(Tickets tickets2) {
        this.tickets = tickets2;
    }

    public class Tickets {
        @SerializedName("ticket")
        @Expose
        private List<Ticket> ticket = null;

        public Tickets() {
        }

        public List<Ticket> getTicket() {
            return this.ticket;
        }

        public void setTicket(List<Ticket> list) {
            this.ticket = list;
        }

        public class Ticket {
            @SerializedName("admin")
            @Expose
            private String admin;
            @SerializedName("attachment")
            @Expose
            private String attachment;
            @SerializedName("c")
            @Expose
            private String c;
            @SerializedName("cc")
            @Expose
            private String cc;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("deptid")
            @Expose
            private String deptid;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("flag")
            @Expose
            private String flag;
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("lastreply")
            @Expose
            private String lastreply;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("priority")
            @Expose
            private String priority;
            @SerializedName(NotificationCompat.CATEGORY_SERVICE)
            @Expose
            private String service;
            @SerializedName(NotificationCompat.CATEGORY_STATUS)
            @Expose
            private String status;
            @SerializedName("subject")
            @Expose
            private String subject;
            @SerializedName("tid")
            @Expose
            private String tid;
            @SerializedName("userid")
            @Expose
            private String userid;

            public Ticket() {
            }

            public String getId() {
                return this.id;
            }

            public void setId(String str) {
                this.id = str;
            }

            public String getTid() {
                return this.tid;
            }

            public void setTid(String str) {
                this.tid = str;
            }

            public String getDeptid() {
                return this.deptid;
            }

            public void setDeptid(String str) {
                this.deptid = str;
            }

            public String getUserid() {
                return this.userid;
            }

            public void setUserid(String str) {
                this.userid = str;
            }

            public String getName() {
                return this.name;
            }

            public void setName(String str) {
                this.name = str;
            }

            public String getEmail() {
                return this.email;
            }

            public void setEmail(String str) {
                this.email = str;
            }

            public String getCc() {
                return this.cc;
            }

            public void setCc(String str) {
                this.cc = str;
            }

            public String getC() {
                return this.c;
            }

            public void setC(String str) {
                this.c = str;
            }

            public String getDate() {
                return this.date;
            }

            public void setDate(String str) {
                this.date = str;
            }

            public String getSubject() {
                return this.subject;
            }

            public void setSubject(String str) {
                this.subject = str;
            }

            public String getStatus() {
                return this.status;
            }

            public void setStatus(String str) {
                this.status = str;
            }

            public String getPriority() {
                return this.priority;
            }

            public void setPriority(String str) {
                this.priority = str;
            }

            public String getAdmin() {
                return this.admin;
            }

            public void setAdmin(String str) {
                this.admin = str;
            }

            public String getAttachment() {
                return this.attachment;
            }

            public void setAttachment(String str) {
                this.attachment = str;
            }

            public String getLastreply() {
                return this.lastreply;
            }

            public void setLastreply(String str) {
                this.lastreply = str;
            }

            public String getFlag() {
                return this.flag;
            }

            public void setFlag(String str) {
                this.flag = str;
            }

            public String getService() {
                return this.service;
            }

            public void setService(String str) {
                this.service = str;
            }
        }
    }
}
