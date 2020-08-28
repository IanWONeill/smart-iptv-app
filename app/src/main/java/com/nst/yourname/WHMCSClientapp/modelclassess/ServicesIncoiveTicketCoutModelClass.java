package com.nst.yourname.WHMCSClientapp.modelclassess;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServicesIncoiveTicketCoutModelClass {
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("result")
    @Expose
    private String result;

    public String getResult() {
        return this.result;
    }

    public void setResult(String str) {
        this.result = str;
    }

    public Data getData() {
        return this.data;
    }

    public void setData(Data data2) {
        this.data = data2;
    }

    public class Data {
        @SerializedName("invoicescount")
        @Expose
        private Invoicescount invoicescount;
        @SerializedName("servicescount")
        @Expose
        private Servicescount servicescount;
        @SerializedName("ticketscount")
        @Expose
        private Ticketscount ticketscount;

        public Data() {
        }

        public Servicescount getServicescount() {
            return this.servicescount;
        }

        public void setServicescount(Servicescount servicescount2) {
            this.servicescount = servicescount2;
        }

        public Invoicescount getInvoicescount() {
            return this.invoicescount;
        }

        public void setInvoicescount(Invoicescount invoicescount2) {
            this.invoicescount = invoicescount2;
        }

        public Ticketscount getTicketscount() {
            return this.ticketscount;
        }

        public void setTicketscount(Ticketscount ticketscount2) {
            this.ticketscount = ticketscount2;
        }

        public class Invoicescount {
            @SerializedName("Cancelled")
            @Expose
            private Integer cancelled;
            @SerializedName("Collects")
            @Expose
            private Integer collects;
            @SerializedName("Draft")
            @Expose
            private Integer draft;
            @SerializedName("Overdue")
            @Expose
            private Integer overdue;
            @SerializedName("Paid")
            @Expose
            private Integer paid;
            @SerializedName("Refunded")
            @Expose
            private Integer refunded;
            @SerializedName("totalresults")
            @Expose
            private String totalresults;
            @SerializedName("Unpaid")
            @Expose
            private Integer unpaid;

            public Invoicescount() {
            }

            public Integer getPaid() {
                return this.paid;
            }

            public void setPaid(Integer num) {
                this.paid = num;
            }

            public Integer getUnpaid() {
                return this.unpaid;
            }

            public void setUnpaid(Integer num) {
                this.unpaid = num;
            }

            public Integer getDraft() {
                return this.draft;
            }

            public void setDraft(Integer num) {
                this.draft = num;
            }

            public Integer getOverdue() {
                return this.overdue;
            }

            public void setOverdue(Integer num) {
                this.overdue = num;
            }

            public Integer getCancelled() {
                return this.cancelled;
            }

            public void setCancelled(Integer num) {
                this.cancelled = num;
            }

            public Integer getRefunded() {
                return this.refunded;
            }

            public void setRefunded(Integer num) {
                this.refunded = num;
            }

            public Integer getCollects() {
                return this.collects;
            }

            public void setCollects(Integer num) {
                this.collects = num;
            }

            public String getTotalresults() {
                return this.totalresults;
            }

            public void setTotalresults(String str) {
                this.totalresults = str;
            }
        }

        public class Servicescount {
            @SerializedName("Active")
            @Expose
            private Integer active;
            @SerializedName("Cancelled")
            @Expose
            private Integer cancelled;
            @SerializedName("Fraud")
            @Expose
            private Integer fraud;
            @SerializedName("Pending")
            @Expose
            private Integer pending;
            @SerializedName("Suspended")
            @Expose
            private Integer suspended;
            @SerializedName("Terminated")
            @Expose
            private Integer terminated;
            @SerializedName("totalresults")
            @Expose
            private String totalresults;

            public Servicescount() {
            }

            public Integer getActive() {
                return this.active;
            }

            public void setActive(Integer num) {
                this.active = num;
            }

            public Integer getPending() {
                return this.pending;
            }

            public void setPending(Integer num) {
                this.pending = num;
            }

            public Integer getSuspended() {
                return this.suspended;
            }

            public void setSuspended(Integer num) {
                this.suspended = num;
            }

            public Integer getCancelled() {
                return this.cancelled;
            }

            public void setCancelled(Integer num) {
                this.cancelled = num;
            }

            public Integer getFraud() {
                return this.fraud;
            }

            public void setFraud(Integer num) {
                this.fraud = num;
            }

            public Integer getTerminated() {
                return this.terminated;
            }

            public void setTerminated(Integer num) {
                this.terminated = num;
            }

            public String getTotalresults() {
                return this.totalresults;
            }

            public void setTotalresults(String str) {
                this.totalresults = str;
            }
        }

        public class Ticketscount {
            @SerializedName("totalresults")
            @Expose
            private String totalresults;

            public Ticketscount() {
            }

            public String getTotalresults() {
                return this.totalresults;
            }

            public void setTotalresults(String str) {
                this.totalresults = str;
            }
        }
    }
}
