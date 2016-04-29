package com.ruigoncalo.marvin.model.raw;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruigoncalo on 28/04/16.
 */
public class CollectionItemResults {


    @SerializedName("results")
    @Expose
    private List<CollectionItemResult> results = new ArrayList<>();

    public List<CollectionItemResult> getResults() {
        return results;
    }

    public void setResults(List<CollectionItemResult> results) {
        this.results = results;
    }
}
