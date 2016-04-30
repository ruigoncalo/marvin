package com.ruigoncalo.marvin.bus;

import com.ruigoncalo.marvin.model.raw.Character;

import java.util.List;

/**
 * Created by ruigoncalo on 30/04/16.
 */
public class CharactersMoreResultsEvent {

    private final List<Character> list;

    public CharactersMoreResultsEvent(List<Character> list) {
        this.list = list;
    }

    public List<Character> getList() {
        return list;
    }
}
