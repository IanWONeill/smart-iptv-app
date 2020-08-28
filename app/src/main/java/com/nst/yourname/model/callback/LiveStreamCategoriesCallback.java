package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nst.yourname.miscelleneious.common.AppConst;

public class LiveStreamCategoriesCallback {
    @SerializedName(AppConst.CATEGORY_ID)
    @Expose
    private String categoryId;
    @SerializedName(AppConst.CATEGORY_NAME)
    @Expose
    private String categoryName;
    @SerializedName("parent_id")
    @Expose
    private Integer parentId;

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String str) {
        this.categoryId = str;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String str) {
        this.categoryName = str;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer num) {
        this.parentId = num;
    }
}
