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

    public static final int IMAGE_STANDARD = 0;
    public static final int IMAGE_LANDSCAPE = 1;
    public static final int IMAGE_PORTRAIT = 2;

    private static final String IMAGE_FORMAT_STANDARD = "standard_fantastic";
    private static final String IMAGE_FORMAT_LANDSCAPE = "landscape_amazing";
    private static final String IMAGE_FORMAT_PORTRAIT = "portrait_fantastic";


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
    public static String buildImageUrl(Thumbnail thumbnail, int imageFormat){
        String path = thumbnail.getPath();
        String extension = thumbnail.getExtension();
        String imageSize = getImageSize(imageFormat);

        return path + "/" + imageSize + "." + extension;
    }


    private static String getImageSize(int formart){
        String result;
        switch (formart){
            case IMAGE_LANDSCAPE:
                result = IMAGE_FORMAT_LANDSCAPE;
                break;

            case IMAGE_PORTRAIT:
                result = IMAGE_FORMAT_PORTRAIT;
                break;

            default: // standard
                result = IMAGE_FORMAT_STANDARD;
        }

        return result;
    }
}
