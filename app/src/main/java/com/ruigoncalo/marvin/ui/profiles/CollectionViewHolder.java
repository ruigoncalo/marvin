package com.ruigoncalo.marvin.ui.profiles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruigoncalo.marvin.R;
import com.ruigoncalo.marvin.ui.ImageLoaderManager;
import com.ruigoncalo.marvin.ui.ViewHolderRenderer;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ruigoncalo on 25/04/16.
 */
public class CollectionViewHolder extends RecyclerView.ViewHolder
        implements ViewHolderRenderer<CollectionViewModel>, View.OnClickListener {

    @Bind(R.id.image_item_collection) ImageView imageView;
    @Bind(R.id.text_item_collection) TextView textView;

    private OnCollectionItemClickListener listener;
    private Context context;

    public CollectionViewHolder(View itemView, OnCollectionItemClickListener listener) {
        super(itemView);
        this.context = itemView.getContext();
        this.listener = listener;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(getAdapterPosition());
        }
    }

    @Override
    public void render(@NonNull CollectionViewModel item, int position) {
        textView.setText(item.getTitle());
        ImageLoaderManager.load(context, item.getImageUrl(), imageView);
    }

    @Override
    public void prepareNext(@NonNull CollectionViewModel item, int position) {
        ImageLoaderManager.fetch(context, item.getImageUrl());
    }
}