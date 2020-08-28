package com.nst.yourname.model;

public class PlayerSelectedSinglton {
    private static PlayerSelectedSinglton myObj;
    private String playerType;
    private boolean playedWithExternalPlayer;

    private PlayerSelectedSinglton() {
    }

    public static PlayerSelectedSinglton getInstance() {
        if (myObj == null) {
            myObj = new PlayerSelectedSinglton();
        }
        return myObj;
    }

    public void setPlayerType(String str) {
        this.playerType = str;
    }

    public String getPlayerType() {
        return this.playerType;
    }

    //ToDo: property created...
    public void setPlayedWithExternalPlayer(boolean playedWithExternalPlayer) {
        this.playedWithExternalPlayer = playedWithExternalPlayer;
    }

    public boolean getPlayedWithExternalPlayer() {
        return playedWithExternalPlayer;
    }
}
