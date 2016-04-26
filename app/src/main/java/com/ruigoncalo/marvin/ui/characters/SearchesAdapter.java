package com.ruigoncalo.marvin.ui.characters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruigoncalo.marvin.R;
import com.ruigoncalo.marvin.model.viewmodel.CharacterViewModel;
import com.ruigoncalo.marvin.ui.BaseAdapter;

/**
 * Created by ruigoncalo on 26/04/16.
 */
public class SearchesAdapter extends BaseAdapter<CharacterViewModel, CharacterViewHolder> {

    private OnCharacterItemClickListener listener;

    public SearchesAdapter(Context context) {
        super(context);
    }

    public void registerItemClickListener(OnCharacterItemClickListener listener) {
        this.listener = listener;
    }

    public void unregisterItemClickListener() {
        this.listener = null;
    }

    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_adapter_search, viewGroup, false);
        return new CharacterViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        holder.render(getItem(position), position);

        if(position < getItemList().size() - 1){
            holder.prepareNext(getItem(position + 1), position + 1);
        }
    }


}
