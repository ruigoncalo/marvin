package com.ruigoncalo.marvin.model.raw;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ruigoncalo on 25/04/16.
 */
public class Item {

    @SerializedName("resourceURI")
    @Expose
    private String collectionURI;

    @SerializedName("name")
    @Expose
    private String name;

    public String getCollectionURI() {
        return collectionURI;
    }

    public String getName() {
        return name;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public void setName(String name) {
        this.name = name;
    }
}
