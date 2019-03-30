package com.example.myinterface.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseApiresponse<T> {

    @SerializedName("collections")
    private List<T> list;

    private long hasMore;
    private String shareURL;
    private String displayText;
    private long hasTotal;

    public long getHasMore() {
        return hasMore;
    }

    public void setHasMore(long hasMore) {
        this.hasMore = hasMore;
    }

    public String getShareURL() {
        return shareURL;
    }

    public void setShareURL(String shareURL) {
        this.shareURL = shareURL;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public long getHasTotal() {
        return hasTotal;
    }

    public void setHasTotal(long hasTotal) {
        this.hasTotal = hasTotal;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
