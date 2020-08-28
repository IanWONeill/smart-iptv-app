package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginCallback {
    @SerializedName("server_info")
    @Expose
    private ServerInfoCallback serverInfo;
    @SerializedName("user_info")
    @Expose
    private UserLoginInfoCallback userLoginInfo;

    public UserLoginInfoCallback getUserLoginInfo() {
        return this.userLoginInfo;
    }

    public void setUserInfo(UserLoginInfoCallback userLoginInfoCallback) {
        this.userLoginInfo = userLoginInfoCallback;
    }

    public ServerInfoCallback getServerInfo() {
        return this.serverInfo;
    }

    public void setServerInfo(ServerInfoCallback serverInfoCallback) {
        this.serverInfo = serverInfoCallback;
    }
}
