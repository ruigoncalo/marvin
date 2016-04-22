package com.ruigoncalo.marvin.model.raw;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ruigoncalo on 21/04/16.
 */
public class Characters {

    @SerializedName("code")
    @Expose
    private Integer code;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("copyright")
    @Expose
    private String copyright;

    @SerializedName("attributionText")
    @Expose
    private String attributionText;

    @SerializedName("attributionHTML")
    @Expose
    private String attributionHTML;

    @SerializedName("etag")
    @Expose
    private String etag;

    @SerializedName("data")
    @Expose
    private Data data;

    public Integer getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public String getAttributionHTML() {
        return attributionHTML;
    }

    public String getEtag() {
        return etag;
    }

    public Data getData() {
        return data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }

    public void setAttributionHTML(String attributionHTML) {
        this.attributionHTML = attributionHTML;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
