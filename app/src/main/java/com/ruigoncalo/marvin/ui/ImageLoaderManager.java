package com.ruigoncalo.marvin.ui;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * ImageLoaderManager that wrapps an image loader lib
 * Currently using picasso
 *
 * Created by ruigoncalo on 20/04/16.
 */
public class ImageLoaderManager implements ImageLoader {

    private static ImageLoaderManager instance;

    private ImageLoaderManager(){

    }

    public static ImageLoaderManager getInstance(){
        if(instance == null){
            instance = new ImageLoaderManager();
        }

        return instance;
    }

    @Override
    public void load(Context context, String url, ImageView target) {
        Picasso.with(context).load(url).into(target);
    }
}
