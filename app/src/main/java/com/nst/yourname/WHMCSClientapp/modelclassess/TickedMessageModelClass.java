package com.nst.yourname.WHMCSClientapp.modelclassess;

import android.support.v4.app.NotificationCompat;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TickedMessageModelClass {
    @SerializedName("admin")
    @Expose
    private String admin;
    @SerializedName("c")
    @Expose
    private String c;
    @SerializedName("cc")
    @Expose
    private String cc;
    @SerializedName("contactid")
    @Expose
    private String contactid;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("deptid")
    @Expose
    private String deptid;
    @SerializedName("deptname")
    @Expose
    private String deptname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("lastreply")
    @Expose
    private String lastreply;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("replies")
    @Expose
    private Replies replies;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName(NotificationCompat.CATEGORY_SERVICE)
    @Expose
    private String service;
    @SerializedName(NotificationCompat.CATEGORY_STATUS)
    @Expose
    private String status;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("ticketid")
    @Expose
    private String ticketid;
    @SerializedName("tid")
    @Expose
    private String tid;
    @SerializedName("userid")
    @Expose
    private String userid;

    public String getResult() {
        return this.result;
    }

    public void setResult(String str) {
        this.result = str;
    }

    public String getTicketid() {
        return this.ticketid;
    }

    public void setTicketid(String str) {
        this.ticketid = str;
    }

    public String getTid() {
        return this.tid;
    }

    public void setTid(String str) {
        this.tid = str;
    }

    public String getC() {
        return this.c;
    }

    public void setC(String str) {
        this.c = str;
    }

    public String getDeptid() {
        return this.deptid;
    }

    public void setDeptid(String str) {
        this.deptid = str;
    }

    public String getDeptname() {
        return this.deptname;
    }

    public void setDeptname(String str) {
        this.deptname = str;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String str) {
        this.userid = str;
    }

    public String getContactid() {
        return this.contactid;
    }

    public void setContactid(String str) {
        this.contactid = str;
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

    public Replies getReplies() {
        return this.replies;
    }

    public void setReplies(Replies replies2) {
        this.replies = replies2;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String str) {
        this.notes = str;
    }

    public class Replies {
        @SerializedName("reply")
        @Expose
        private List<Reply> reply = null;

        public Replies() {
        }

        public List<Reply> getReply() {
            return this.reply;
        }

        public void setReply(List<Reply> list) {
            this.reply = list;
        }

        public class Reply {
            @SerializedName("admin")
            @Expose
            private String admin;
            @SerializedName("attachment")
            @Expose
            private String attachment;
            @SerializedName("contactid")
            @Expose
            private String contactid;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("message")
            @Expose
            private String message;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("rating")
            @Expose
            private String rating;
            @SerializedName("userid")
            @Expose
            private String userid;

            public Reply() {
            }

            public String getUserid() {
                return this.userid;
            }

            public void setUserid(String str) {
                this.userid = str;
            }

            public String getContactid() {
                return this.contactid;
            }

            public void setContactid(String str) {
                this.contactid = str;
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

            public String getDate() {
                return this.date;
            }

            public void setDate(String str) {
                this.date = str;
            }

            public String getMessage() {
                return this.message;
            }

            public void setMessage(String str) {
                this.message = str;
            }

            public String getAttachment() {
                return this.attachment;
            }

            public void setAttachment(String str) {
                this.attachment = str;
            }

            public String getAdmin() {
                return this.admin;
            }

            public void setAdmin(String str) {
                this.admin = str;
            }

            public String getRating() {
                return this.rating;
            }

            public void setRating(String str) {
                this.rating = str;
            }
        }
    }
}
