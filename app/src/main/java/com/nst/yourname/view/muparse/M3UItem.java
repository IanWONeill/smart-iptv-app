package com.nst.yourname.view.muparse;

public class M3UItem {
    private String itemDuration;
    private String itemGroupTitle;
    private String itemIcon;
    private String itemId;
    private String itemName;
    private String itemNameNotReq;
    private String itemUrl;

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String str) {
        this.itemName = str;
    }

    public String getItemNameNotReq() {
        return this.itemNameNotReq;
    }

    public void setItemNameNotReq(String str) {
        this.itemNameNotReq = str;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String str) {
        this.itemId = str;
    }

    public String getItemGroupTitle() {
        return this.itemGroupTitle;
    }

    public void setItemGroupTitle(String str) {
        this.itemGroupTitle = str;
    }

    public String getItemUrl() {
        return this.itemUrl;
    }

    public void setItemUrl(String str) {
        this.itemUrl = str;
    }

    public String getItemIcon() {
        return this.itemIcon;
    }

    public void setItemIcon(String str) {
        this.itemIcon = str;
    }

    public String getItemDuration() {
        return this.itemDuration;
    }

    public void setItemDuration(String str) {
        this.itemDuration = str;
    }
}
