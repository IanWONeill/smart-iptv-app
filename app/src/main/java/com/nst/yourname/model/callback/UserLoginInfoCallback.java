package com.nst.yourname.model.callback;

import android.support.v4.app.NotificationCompat;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class UserLoginInfoCallback {
    @SerializedName("active_cons")
    @Expose
    private String activeCons;
    @SerializedName("allowed_output_formats")
    @Expose
    private List<String> allowedOutputFormats = null;
    @SerializedName("auth")
    @Expose
    private Integer auth;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("exp_date")
    @Expose
    private String expDate;
    @SerializedName("is_trial")
    @Expose
    private String isTrial;
    @SerializedName("max_connections")
    @Expose
    private String maxConnections;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName(NotificationCompat.CATEGORY_STATUS)
    @Expose
    private String status;
    @SerializedName("username")
    @Expose
    private String username;

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

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public Integer getAuth() {
        return this.auth;
    }

    public void setAuth(Integer num) {
        this.auth = num;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String getExpDate() {
        return this.expDate;
    }

    public void setExpDate(String str) {
        this.expDate = str;
    }

    public String getIsTrial() {
        return this.isTrial;
    }

    public void setIsTrial(String str) {
        this.isTrial = str;
    }

    public String getActiveCons() {
        return this.activeCons;
    }

    public void setActiveCons(String str) {
        this.activeCons = str;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String str) {
        this.createdAt = str;
    }

    public String getMaxConnections() {
        return this.maxConnections;
    }

    public void setMaxConnections(String str) {
        this.maxConnections = str;
    }

    public List<String> getAllowedOutputFormats() {
        return this.allowedOutputFormats;
    }

    public void setAllowedOutputFormats(List<String> list) {
        this.allowedOutputFormats = list;
    }
}
