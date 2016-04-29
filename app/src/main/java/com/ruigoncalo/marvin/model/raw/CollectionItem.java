package com.ruigoncalo.marvin.model.raw;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ruigoncalo on 28/04/16.
 */
public class CollectionItem {

    @SerializedName("data")
    @Expose
    private CollectionItemResults data;

    public CollectionItemResults getData() {
        return data;
    }

    public void setData(CollectionItemResults data) {
        this.data = data;
    }
}
