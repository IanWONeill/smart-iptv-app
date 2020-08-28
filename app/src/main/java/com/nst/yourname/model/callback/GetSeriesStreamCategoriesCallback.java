package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nst.yourname.miscelleneious.common.AppConst;

public class GetSeriesStreamCategoriesCallback {
    @SerializedName(AppConst.CATEGORY_ID)
    @Expose
    public String categoryId;
    @SerializedName(AppConst.CATEGORY_NAME)
    @Expose
    public String categoryName;

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
}
