package com.ruigoncalo.marvin.model.raw;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruigoncalo on 21/04/16.
 */
public class Data {

    @SerializedName("offset")
    @Expose
    private Integer offset;

    @SerializedName("limit")
    @Expose
    private Integer limit;

    @SerializedName("total")
    @Expose
    private Integer total;

    @SerializedName("count")
    @Expose
    private Integer count;

    @SerializedName("results")
    @Expose
    private List<Character> characters = new ArrayList<>();

    public Integer getOffset() {
        return offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getCount() {
        return count;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
}
