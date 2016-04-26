package com.ruigoncalo.marvin.ui.characters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruigoncalo.marvin.R;
import com.ruigoncalo.marvin.model.viewmodel.CharacterViewModel;
import com.ruigoncalo.marvin.ui.ImageLoaderManager;
import com.ruigoncalo.marvin.ui.ViewHolderRenderer;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ruigoncalo on 20/04/16.
 */
public class CharacterViewHolder extends RecyclerView.ViewHolder
        implements ViewHolderRenderer<CharacterViewModel>, View.OnClickListener {

    @Bind(R.id.image_item_character) ImageView imageView;
    @Bind(R.id.text_item_character) TextView textView;

    private OnCharacterItemClickListener listener;
    private Context context;

    public CharacterViewHolder(View itemView, OnCharacterItemClickListener listener) {
        super(itemView);
        this.context = itemView.getContext();
        this.listener = listener;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onCharacterItemClick(getAdapterPosition());
        }
    }

    @Override
    public void render(@NonNull CharacterViewModel item, int position) {
        textView.setText(item.getTitle());
        ImageLoaderManager.load(context, item.getImageUrl(), imageView);
    }

    @Override
    public void prepareNext(@NonNull CharacterViewModel item, int position) {
        ImageLoaderManager.fetch(context, item.getImageUrl());
    }
}
