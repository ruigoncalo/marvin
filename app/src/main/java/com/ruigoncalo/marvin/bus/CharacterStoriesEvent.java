package com.ruigoncalo.marvin.bus;

import com.ruigoncalo.marvin.model.raw.CollectionItemResults;

/**
 * Created by ruigoncalo on 29/04/16.
 */
public class CharacterStoriesEvent extends CharacterCollectionEvent {

    public CharacterStoriesEvent(CollectionItemResults items) {
        super(items);
    }
}