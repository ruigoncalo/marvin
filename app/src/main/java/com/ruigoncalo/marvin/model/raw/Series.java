package com.ruigoncalo.marvin.model.raw;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruigoncalo on 21/04/16.
 */
public class Series {

    @SerializedName("available")
    @Expose
    private Integer available;

    @SerializedName("collectionURI")
    @Expose
    private String collectionURI;

    @SerializedName("items")
    @Expose
    private List<Object> items = new ArrayList<Object>();

    @SerializedName("returned")
    @Expose
    private Integer returned;

    public Integer getAvailable() {
        return available;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public List<Object> getItems() {
        return items;
    }

    public Integer getReturned() {
        return returned;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

    public void setReturned(Integer returned) {
        this.returned = returned;
    }
}
