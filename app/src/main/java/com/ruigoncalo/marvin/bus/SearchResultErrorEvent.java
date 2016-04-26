package com.ruigoncalo.marvin.bus;

/**
 * Created by ruigoncalo on 26/04/16.
 */
public class SearchResultErrorEvent {

    private final String message;

    public SearchResultErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
