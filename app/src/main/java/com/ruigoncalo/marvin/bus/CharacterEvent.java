package com.ruigoncalo.marvin.bus;

import com.ruigoncalo.marvin.model.raw.Character;

/**
 * Created by ruigoncalo on 26/04/16.
 */
public class CharacterEvent {

    private final Character character;

    public CharacterEvent(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }
}
