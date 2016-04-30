package com.ruigoncalo.marvin.ui;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
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

    public static final String IMAGE_FORMAT_STANDARD = "standard_fantastic";
    public static final String IMAGE_FORMAT_LANDSCAPE = "landscape_amazing";
    public static final String IMAGE_FORMAT_PORTRAIT = "portrait_fantastic";
    public static final String IMAGE_FORMAT_PORTRAIT_BEST = "portrait_incredible";

    private static final String PLACEHOLDER_URL = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available";
    private static final String PLACEHOLDER_EXT = "jpg";

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
    public static String buildImageUrl(@Nullable Thumbnail thumbnail, int imageFormat){
        String path = thumbnail == null ? PLACEHOLDER_URL : thumbnail.getPath();
        String extension =  thumbnail == null? PLACEHOLDER_EXT : thumbnail.getExtension();
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


    public static String updateUrlImageRatio(String url, String newRatio){
        Uri uri = Uri.parse(url);
        String imageRatio = uri.getLastPathSegment();
        return url.replace(imageRatio, newRatio + "." + PLACEHOLDER_EXT);
    }
}
