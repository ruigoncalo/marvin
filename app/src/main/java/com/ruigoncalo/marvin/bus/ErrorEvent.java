package com.ruigoncalo.marvin.bus;

/**
 * Created by ruigoncalo on 29/04/16.
 */
public abstract class ErrorEvent {

    private final String message;

    public ErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
