package com.nst.yourname.WHMCSClientapp.modelclassess;

import android.support.v4.app.NotificationCompat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class InvoicesModelClass {
    @SerializedName("invoices")
    @Expose
    private Invoices invoices;
    @SerializedName("numreturned")
    @Expose
    private Integer numreturned;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("startnumber")
    @Expose
    private Integer startnumber;
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

    public Invoices getInvoices() {
        return this.invoices;
    }

    public void setInvoices(Invoices invoices2) {
        this.invoices = invoices2;
    }

    public class Invoices {
        @SerializedName("invoice")
        @Expose
        private List<Invoice> invoice = null;

        public Invoices() {
        }

        public List<Invoice> getInvoice() {
            return this.invoice;
        }

        public void setInvoice(List<Invoice> list) {
            this.invoice = list;
        }

        public class Invoice {
            @SerializedName("companyname")
            @Expose
            private String companyname;
            @SerializedName("credit")
            @Expose
            private String credit;
            @SerializedName("currencycode")
            @Expose
            private String currencycode;
            @SerializedName("currencyprefix")
            @Expose
            private String currencyprefix;
            @SerializedName("currencysuffix")
            @Expose
            private String currencysuffix;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("datepaid")
            @Expose
            private String datepaid;
            @SerializedName("duedate")
            @Expose
            private String duedate;
            @SerializedName("firstname")
            @Expose
            private String firstname;
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("invoicenum")
            @Expose
            private String invoicenum;
            @SerializedName("last_capture_attempt")
            @Expose
            private String lastCaptureAttempt;
            @SerializedName("lastname")
            @Expose
            private String lastname;
            @SerializedName("notes")
            @Expose
            private String notes;
            @SerializedName("paymentmethod")
            @Expose
            private String paymentmethod;
            @SerializedName(NotificationCompat.CATEGORY_STATUS)
            @Expose
            private String status;
            @SerializedName("subtotal")
            @Expose
            private String subtotal;
            @SerializedName("tax")
            @Expose
            private String tax;
            @SerializedName("tax2")
            @Expose
            private String tax2;
            @SerializedName("taxrate")
            @Expose
            private String taxrate;
            @SerializedName("taxrate2")
            @Expose
            private String taxrate2;
            @SerializedName("total")
            @Expose
            private String total;
            @SerializedName("userid")
            @Expose
            private String userid;

            public Invoice() {
            }

            public String getId() {
                return this.id;
            }

            public void setId(String str) {
                this.id = str;
            }

            public String getUserid() {
                return this.userid;
            }

            public void setUserid(String str) {
                this.userid = str;
            }

            public String getFirstname() {
                return this.firstname;
            }

            public void setFirstname(String str) {
                this.firstname = str;
            }

            public String getLastname() {
                return this.lastname;
            }

            public void setLastname(String str) {
                this.lastname = str;
            }

            public String getCompanyname() {
                return this.companyname;
            }

            public void setCompanyname(String str) {
                this.companyname = str;
            }

            public String getInvoicenum() {
                return this.invoicenum;
            }

            public void setInvoicenum(String str) {
                this.invoicenum = str;
            }

            public String getDate() {
                return this.date;
            }

            public void setDate(String str) {
                this.date = str;
            }

            public String getDuedate() {
                return this.duedate;
            }

            public void setDuedate(String str) {
                this.duedate = str;
            }

            public String getDatepaid() {
                return this.datepaid;
            }

            public void setDatepaid(String str) {
                this.datepaid = str;
            }

            public String getLastCaptureAttempt() {
                return this.lastCaptureAttempt;
            }

            public void setLastCaptureAttempt(String str) {
                this.lastCaptureAttempt = str;
            }

            public String getSubtotal() {
                return this.subtotal;
            }

            public void setSubtotal(String str) {
                this.subtotal = str;
            }

            public String getCredit() {
                return this.credit;
            }

            public void setCredit(String str) {
                this.credit = str;
            }

            public String getTax() {
                return this.tax;
            }

            public void setTax(String str) {
                this.tax = str;
            }

            public String getTax2() {
                return this.tax2;
            }

            public void setTax2(String str) {
                this.tax2 = str;
            }

            public String getTotal() {
                return this.total;
            }

            public void setTotal(String str) {
                this.total = str;
            }

            public String getTaxrate() {
                return this.taxrate;
            }

            public void setTaxrate(String str) {
                this.taxrate = str;
            }

            public String getTaxrate2() {
                return this.taxrate2;
            }

            public void setTaxrate2(String str) {
                this.taxrate2 = str;
            }

            public String getStatus() {
                return this.status;
            }

            public void setStatus(String str) {
                this.status = str;
            }

            public String getPaymentmethod() {
                return this.paymentmethod;
            }

            public void setPaymentmethod(String str) {
                this.paymentmethod = str;
            }

            public String getNotes() {
                return this.notes;
            }

            public void setNotes(String str) {
                this.notes = str;
            }

            public String getCurrencycode() {
                return this.currencycode;
            }

            public void setCurrencycode(String str) {
                this.currencycode = str;
            }

            public String getCurrencyprefix() {
                return this.currencyprefix;
            }

            public void setCurrencyprefix(String str) {
                this.currencyprefix = str;
            }

            public String getCurrencysuffix() {
                return this.currencysuffix;
            }

            public void setCurrencysuffix(String str) {
                this.currencysuffix = str;
            }
        }
    }
}
