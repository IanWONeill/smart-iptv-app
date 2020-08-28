package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivationCallBack {
    @SerializedName("logindetails")
    @Expose
    private Logindetails logindetails;
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

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public Logindetails getLogindetails() {
        return this.logindetails;
    }

    public void setLogindetails(Logindetails logindetails2) {
        this.logindetails = logindetails2;
    }

    public class Logindetails {
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("username")
        @Expose
        private String username;

        public Logindetails() {
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
    }
}
