package com.ruigoncalo.marvin.ui;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by ruigoncalo on 20/04/16.
 */
public interface ImageLoader {

    void load(Context context, String url, ImageView target);
}
