package com.ruigoncalo.marvin.ui.profiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruigoncalo.marvin.R;
import com.ruigoncalo.marvin.ui.BaseAdapter;

/**
 * Created by ruigoncalo on 25/04/16.
 */
public class CollectionAdapter extends BaseAdapter<CollectionViewModel, CollectionViewHolder> {

    private OnCollectionItemClickListener listener;

    public CollectionAdapter(Context context) {
        super(context);
    }

    public void registerItemClickListener(OnCollectionItemClickListener listener) {
        this.listener = listener;
    }

    public void unregisterItemClickListener() {
        this.listener = null;
    }

    @Override
    public CollectionViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_adapter_collection, viewGroup, false);
        return new CollectionViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(CollectionViewHolder holder, int position) {
        holder.render(getItem(position), position);

        if(position < getItemList().size() - 1){
            holder.prepareNext(getItem(position + 1), position + 1);
        }
    }

}
