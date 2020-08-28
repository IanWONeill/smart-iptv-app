package com.nst.yourname.WHMCSClientapp.modelclassess;

import android.support.v4.app.NotificationCompat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ActiveServiceModelClass {
    @SerializedName("assignedips")
    @Expose
    private String assignedips;
    @SerializedName("billingcycle")
    @Expose
    private String billingcycle;
    @SerializedName("bwlimit")
    @Expose
    private String bwlimit;
    @SerializedName("bwusage")
    @Expose
    private String bwusage;
    @SerializedName("clientid")
    @Expose
    private String clientid;
    @SerializedName("configoptions")
    @Expose
    private Configoptions configoptions;
    @SerializedName("customfields")
    @Expose
    private Customfields customfields;
    @SerializedName("dedicatedip")
    @Expose
    private String dedicatedip;
    @SerializedName("disklimit")
    @Expose
    private String disklimit;
    @SerializedName("diskusage")
    @Expose
    private String diskusage;
    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("firstpaymentamount")
    @Expose
    private String firstpaymentamount;
    @SerializedName("groupname")
    @Expose
    private String groupname;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lastupdate")
    @Expose
    private String lastupdate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nextduedate")
    @Expose
    private String nextduedate;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("ns1")
    @Expose
    private String ns1;
    @SerializedName("ns2")
    @Expose
    private String ns2;
    @SerializedName("orderid")
    @Expose
    private String orderid;
    @SerializedName("overideautosuspend")
    @Expose
    private String overideautosuspend;
    @SerializedName("overidesuspenduntil")
    @Expose
    private String overidesuspenduntil;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("paymentmethod")
    @Expose
    private String paymentmethod;
    @SerializedName("paymentmethodname")
    @Expose
    private String paymentmethodname;
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("promoid")
    @Expose
    private String promoid;
    @SerializedName("recurringamount")
    @Expose
    private String recurringamount;
    @SerializedName("regdate")
    @Expose
    private String regdate;
    @SerializedName("serverhostname")
    @Expose
    private String serverhostname;
    @SerializedName("serverid")
    @Expose
    private String serverid;
    @SerializedName("serverip")
    @Expose
    private String serverip;
    @SerializedName("servername")
    @Expose
    private String servername;
    @SerializedName(NotificationCompat.CATEGORY_STATUS)
    @Expose
    private String status;
    @SerializedName("subscriptionid")
    @Expose
    private String subscriptionid;
    @SerializedName("suspensionreason")
    @Expose
    private String suspensionreason;
    @SerializedName("translated_groupname")
    @Expose
    private String translatedGroupname;
    @SerializedName("translated_name")
    @Expose
    private String translatedName;
    @SerializedName("username")
    @Expose
    private String username;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getClientid() {
        return this.clientid;
    }

    public void setClientid(String str) {
        this.clientid = str;
    }

    public String getOrderid() {
        return this.orderid;
    }

    public void setOrderid(String str) {
        this.orderid = str;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String str) {
        this.pid = str;
    }

    public String getRegdate() {
        return this.regdate;
    }

    public void setRegdate(String str) {
        this.regdate = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getTranslatedName() {
        return this.translatedName;
    }

    public void setTranslatedName(String str) {
        this.translatedName = str;
    }

    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String str) {
        this.groupname = str;
    }

    public String getTranslatedGroupname() {
        return this.translatedGroupname;
    }

    public void setTranslatedGroupname(String str) {
        this.translatedGroupname = str;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String str) {
        this.domain = str;
    }

    public String getDedicatedip() {
        return this.dedicatedip;
    }

    public void setDedicatedip(String str) {
        this.dedicatedip = str;
    }

    public String getServerid() {
        return this.serverid;
    }

    public void setServerid(String str) {
        this.serverid = str;
    }

    public String getServername() {
        return this.servername;
    }

    public void setServername(String str) {
        this.servername = str;
    }

    public String getServerip() {
        return this.serverip;
    }

    public void setServerip(String str) {
        this.serverip = str;
    }

    public String getServerhostname() {
        return this.serverhostname;
    }

    public void setServerhostname(String str) {
        this.serverhostname = str;
    }

    public String getSuspensionreason() {
        return this.suspensionreason;
    }

    public void setSuspensionreason(String str) {
        this.suspensionreason = str;
    }

    public String getFirstpaymentamount() {
        return this.firstpaymentamount;
    }

    public void setFirstpaymentamount(String str) {
        this.firstpaymentamount = str;
    }

    public String getRecurringamount() {
        return this.recurringamount;
    }

    public void setRecurringamount(String str) {
        this.recurringamount = str;
    }

    public String getPaymentmethod() {
        return this.paymentmethod;
    }

    public void setPaymentmethod(String str) {
        this.paymentmethod = str;
    }

    public String getPaymentmethodname() {
        return this.paymentmethodname;
    }

    public void setPaymentmethodname(String str) {
        this.paymentmethodname = str;
    }

    public String getBillingcycle() {
        return this.billingcycle;
    }

    public void setBillingcycle(String str) {
        this.billingcycle = str;
    }

    public String getNextduedate() {
        return this.nextduedate;
    }

    public void setNextduedate(String str) {
        this.nextduedate = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String str) {
        this.username = str;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String str) {
        this.password = str;
    }

    public String getSubscriptionid() {
        return this.subscriptionid;
    }

    public void setSubscriptionid(String str) {
        this.subscriptionid = str;
    }

    public String getPromoid() {
        return this.promoid;
    }

    public void setPromoid(String str) {
        this.promoid = str;
    }

    public String getOverideautosuspend() {
        return this.overideautosuspend;
    }

    public void setOverideautosuspend(String str) {
        this.overideautosuspend = str;
    }

    public String getOveridesuspenduntil() {
        return this.overidesuspenduntil;
    }

    public void setOveridesuspenduntil(String str) {
        this.overidesuspenduntil = str;
    }

    public String getNs1() {
        return this.ns1;
    }

    public void setNs1(String str) {
        this.ns1 = str;
    }

    public String getNs2() {
        return this.ns2;
    }

    public void setNs2(String str) {
        this.ns2 = str;
    }

    public String getAssignedips() {
        return this.assignedips;
    }

    public void setAssignedips(String str) {
        this.assignedips = str;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String str) {
        this.notes = str;
    }

    public String getDiskusage() {
        return this.diskusage;
    }

    public void setDiskusage(String str) {
        this.diskusage = str;
    }

    public String getDisklimit() {
        return this.disklimit;
    }

    public void setDisklimit(String str) {
        this.disklimit = str;
    }

    public String getBwusage() {
        return this.bwusage;
    }

    public void setBwusage(String str) {
        this.bwusage = str;
    }

    public String getBwlimit() {
        return this.bwlimit;
    }

    public void setBwlimit(String str) {
        this.bwlimit = str;
    }

    public String getLastupdate() {
        return this.lastupdate;
    }

    public void setLastupdate(String str) {
        this.lastupdate = str;
    }

    public Customfields getCustomfields() {
        return this.customfields;
    }

    public void setCustomfields(Customfields customfields2) {
        this.customfields = customfields2;
    }

    public Configoptions getConfigoptions() {
        return this.configoptions;
    }

    public void setConfigoptions(Configoptions configoptions2) {
        this.configoptions = configoptions2;
    }

    public class Configoptions {
        @SerializedName("configoption")
        @Expose
        private List<Object> configoption = null;

        public Configoptions() {
        }

        public List<Object> getConfigoption() {
            return this.configoption;
        }

        public void setConfigoption(List<Object> list) {
            this.configoption = list;
        }
    }

    public class Customfields {
        @SerializedName("customfield")
        @Expose
        private List<Customfield> customfield = null;

        public Customfields() {
        }

        public List<Customfield> getCustomfield() {
            return this.customfield;
        }

        public void setCustomfield(List<Customfield> list) {
            this.customfield = list;
        }

        public class Customfield {
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("translated_name")
            @Expose
            private String translatedName;
            @SerializedName("value")
            @Expose
            private String value;

            public Customfield() {
            }

            public String getId() {
                return this.id;
            }

            public void setId(String str) {
                this.id = str;
            }

            public String getName() {
                return this.name;
            }

            public void setName(String str) {
                this.name = str;
            }

            public String getTranslatedName() {
                return this.translatedName;
            }

            public void setTranslatedName(String str) {
                this.translatedName = str;
            }

            public String getValue() {
                return this.value;
            }

            public void setValue(String str) {
                this.value = str;
            }
        }
    }
}
