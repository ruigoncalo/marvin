package com.ruigoncalo.marvin.model.raw;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ruigoncalo on 21/04/16.
 */
public class Url {

    public static final String TYPE_DETAIL = "detail";
    public static final String TYPE_WIKI = "wiki";
    public static final String TYPE_COMICLINK = "comiclink";

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("url")
    @Expose
    private String url;

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
