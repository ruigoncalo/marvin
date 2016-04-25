package com.ruigoncalo.marvin.ui;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * ImageLoaderManager that wraps up an image loader lib
 *
 * Currently using picasso
 *
 * Created by ruigoncalo on 20/04/16.
 */
public class ImageLoaderManager {

    private ImageLoaderManager(){

    }

    public static void load(Context context, String url, ImageView target) {
        Picasso.with(context).load(url).into(target);
    }

    public static void fetch(Context context, String url){
        Picasso.with(context).load(url).fetch();
    }
}
