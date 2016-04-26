package com.ruigoncalo.marvin.bus;

/**
 * Created by ruigoncalo on 26/04/16.
 */
public class CharactersResultErrorEvent {

    private final String message;

    public CharactersResultErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
