package com.ruigoncalo.marvin.bus;

import com.ruigoncalo.marvin.model.raw.CollectionItemResults;

/**
 * Created by ruigoncalo on 29/04/16.
 */
public abstract class CharacterCollectionEvent {

    private final CollectionItemResults items;

    public CharacterCollectionEvent(CollectionItemResults items){
        this.items = items;
    }

    public CollectionItemResults getItems() {
        return items;
    }
}
