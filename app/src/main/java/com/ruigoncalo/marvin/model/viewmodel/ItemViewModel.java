package com.ruigoncalo.marvin.model.viewmodel;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruigoncalo on 27/04/16.
 */
public class ItemViewModel {

    private final int available;
    private final List<Pair<String, String>> items;
    private final int returned;

    public ItemViewModel() {
        this(new Builder());
    }

    private ItemViewModel(Builder builder) {
        this.available = builder.available;
        this.items = builder.items;
        this.returned = builder.returned;
    }

    public int getAvailable() {
        return available;
    }

    public List<Pair<String, String>> getItems() {
        return items;
    }

    public int getReturned() {
        return returned;
    }

    public static class Builder {
        private int available;
        private List<Pair<String, String>> items = new ArrayList<>();
        private int returned;

        public Builder available(int available){
            this.available = available;
            return this;
        }

        public Builder items(List<Pair<String, String>> items){
            this.items = items;
            return this;
        }

        public Builder returned(int returned){
            this.returned = returned;
            return this;
        }

        public ItemViewModel build() {
            return new ItemViewModel(this);
        }
    }

}
