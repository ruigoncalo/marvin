package com.ruigoncalo.marvin.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruigoncalo on 20/04/16.
 */
public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected Context context;
    protected List<T> itemList ;
    private AdapterListSizeListener adapterListSizeListener;

    public BaseAdapter(Context context){
        super();
        this.context = context;
        itemList = new ArrayList<>();
    }

    public void registerListSizeListener(AdapterListSizeListener adapterListSizeListener){
        this.adapterListSizeListener = adapterListSizeListener;
    }

    public void unregisterListSizeListener(){
        this.adapterListSizeListener = null;
    }

    public Context getContext(){
        return context;
    }

    public void setItemList(List<T> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();

        if(itemList.isEmpty() && adapterListSizeListener != null){
            adapterListSizeListener.isAdapterListEmpty();
        }
    }

    public void appendItems(List<T> itemList){
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    public List<T> getItemList(){
        return itemList;
    }

    public abstract VH onCreateViewHolder(ViewGroup viewGroup, int viewType);

    public abstract void onBindViewHolder(VH holder, int position);

    @Override
    public int getItemCount() {
        if(itemList != null) {
            return itemList.size();
        }

        return 0;
    }

    public T getItem(int position){
        return itemList.get(position);
    }

    public void addItem(T item){
        itemList.add(item);
        notifyItemInserted(itemList.size() - 1);
    }

    public void addItem(int position, T item){
        itemList.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount()); // update positions

        if(itemList.isEmpty() && adapterListSizeListener != null){
            adapterListSizeListener.isAdapterListEmpty();
        }
    }

    public void updateItem(int position){
        notifyItemChanged(position);
    }

    public void removeAll(){
        itemList.clear();
        notifyDataSetChanged();

        if(adapterListSizeListener != null){
            adapterListSizeListener.isAdapterListEmpty();
        }
    }
}