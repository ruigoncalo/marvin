package com.ruigoncalo.marvin.bus;

import com.ruigoncalo.marvin.model.raw.Character;

/**
 * Created by ruigoncalo on 26/04/16.
 */
public class CharacterProfileEvent {

    private final Character character;

    public CharacterProfileEvent(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }
}
