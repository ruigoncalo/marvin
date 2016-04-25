package com.ruigoncalo.marvin.ui;

import android.support.annotation.NonNull;

/**
 * Created by ruigoncalo on 20/04/16.
 */
public interface ViewHolderRenderer<T> {
    void render(@NonNull T item, int position);
    void prepareNext(@NonNull T item, int position);
}
