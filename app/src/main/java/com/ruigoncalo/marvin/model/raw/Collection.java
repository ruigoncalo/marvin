package com.ruigoncalo.marvin.model.raw;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruigoncalo on 25/04/16.
 */
public class Collection {

    @SerializedName("available")
    @Expose
    private int available;

    @SerializedName("collectionURI")
    @Expose
    private String collectionURI;

    @SerializedName("items")
    @Expose
    private List<Item> items = new ArrayList<>();

    @SerializedName("returned")
    @Expose
    private int returned;

    public int getAvailable() {
        return available;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getReturned() {
        return returned;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setReturned(int returned) {
        this.returned = returned;
    }
}
