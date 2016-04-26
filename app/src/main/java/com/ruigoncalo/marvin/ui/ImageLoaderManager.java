package com.ruigoncalo.marvin.ui;

import android.content.Context;
import android.widget.ImageView;

import com.ruigoncalo.marvin.model.raw.Thumbnail;
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

    /**
     * Build image url path
     * Font: http://developer.marvel.com/documentation/images
     *
     * @param thumbnail image url info
     * @return full url to download image
     */
    public static String buildImageUrl(Thumbnail thumbnail, boolean landscape){
        String path = thumbnail.getPath();
        String extension = thumbnail.getExtension();
        String imageSize = landscape ? "landscape_amazing" : "standard_large";

        return path + "/" + imageSize + "." + extension;
    }
}
