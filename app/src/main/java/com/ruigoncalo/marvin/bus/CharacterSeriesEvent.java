package com.ruigoncalo.marvin.bus;

import com.ruigoncalo.marvin.model.raw.CollectionItemResults;

/**
 * Created by ruigoncalo on 29/04/16.
 */
public class CharacterSeriesEvent extends CharacterCollectionEvent {

    public CharacterSeriesEvent(CollectionItemResults items) {
        super(items);
    }
}