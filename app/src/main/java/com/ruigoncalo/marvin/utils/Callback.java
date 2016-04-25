package com.ruigoncalo.marvin.utils;

/**
 * Created by ruigoncalo on 24/04/16.
 */
public interface Callback<T> {

    void onSuccess(T t);
    void onFailure(String message);
}
