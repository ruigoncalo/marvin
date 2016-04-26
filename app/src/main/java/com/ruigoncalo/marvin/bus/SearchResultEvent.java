package com.ruigoncalo.marvin.bus;

import com.ruigoncalo.marvin.model.raw.Character;

import java.util.List;

/**
 * Created by ruigoncalo on 26/04/16.
 */
public class SearchResultEvent {

    private final List<Character> list;

    public SearchResultEvent(List<Character> list) {
        this.list = list;
    }

    public List<Character> getList() {
        return list;
    }
}
