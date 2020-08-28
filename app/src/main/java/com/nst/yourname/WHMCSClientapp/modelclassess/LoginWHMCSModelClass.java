package com.nst.yourname.WHMCSClientapp.modelclassess;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginWHMCSModelClass {
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;
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

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public class Data {
        @SerializedName("clientid")
        @Expose
        private Integer clientid;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("exp_time")
        @Expose
        private int exp_time;
        @SerializedName("prefix")
        @Expose
        private String prefix;
        @SerializedName("suffix")
        @Expose
        private String suffix;
        @SerializedName("username")
        @Expose
        private String username;

        public Data() {
        }

        public int getExp_time() {
            return this.exp_time;
        }

        public void setExp_time(int i) {
            this.exp_time = i;
        }

        public Integer getClientid() {
            return this.clientid;
        }

        public void setClientid(Integer num) {
            this.clientid = num;
        }

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String str) {
            this.username = str;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String str) {
            this.email = str;
        }

        public String getPrefix() {
            return this.prefix;
        }

        public void setPrefix(String str) {
            this.prefix = str;
        }

        public String getSuffix() {
            return this.suffix;
        }

        public void setSuffix(String str) {
            this.suffix = str;
        }
    }
}
