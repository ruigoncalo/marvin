package com.ruigoncalo.marvin.bus;


import com.ruigoncalo.marvin.model.raw.CollectionItemResults;

/**
 * Created by ruigoncalo on 29/04/16.
 */
public class CharacterEventsEvent extends CharacterCollectionEvent {

    public CharacterEventsEvent(CollectionItemResults items) {
        super(items);
    }
}