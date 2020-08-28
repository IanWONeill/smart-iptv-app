package com.nst.yourname.view.muparse;

import java.util.List;

public class M3UPlaylist {
    private List<M3UItem> playlistItems;
    private String playlistName;
    private String playlistParams;

    public List<M3UItem> getPlaylistItems() {
        return this.playlistItems;
    }

    /* access modifiers changed from: package-private */
    public void setPlaylistItems(List<M3UItem> list) {
        this.playlistItems = list;
    }

    public String getPlaylistName() {
        return this.playlistName;
    }

    public void setPlaylistName(String str) {
        this.playlistName = str;
    }

    public String getPlaylistParams() {
        return this.playlistParams;
    }

    public void setPlaylistParams(String str) {
        this.playlistParams = str;
    }

    public String getSingleParameter(String str) {
        String[] split = this.playlistParams.split(" ");
        for (String str2 : split) {
            if (str2.contains(str)) {
                return str2.substring(str2.indexOf(str) + str.length()).replace("=", "");
            }
        }
        return "";
    }
}
