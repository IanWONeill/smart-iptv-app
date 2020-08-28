package com.nst.yourname.model.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nst.yourname.model.pojo.SearchTMDBTVShowsResultPojo;
import java.util.List;

public class SearchTMDBTVShowsCallback {
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<SearchTMDBTVShowsResultPojo> results = null;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer num) {
        this.page = num;
    }

    public Integer getTotalResults() {
        return this.totalResults;
    }

    public void setTotalResults(Integer num) {
        this.totalResults = num;
    }

    public Integer getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(Integer num) {
        this.totalPages = num;
    }

    public List<SearchTMDBTVShowsResultPojo> getResults() {
        return this.results;
    }

    public void setResults(List<SearchTMDBTVShowsResultPojo> list) {
        this.results = list;
    }
}
