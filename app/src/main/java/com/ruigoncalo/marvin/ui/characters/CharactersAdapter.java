package com.ruigoncalo.marvin.ui.characters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruigoncalo.marvin.R;
import com.ruigoncalo.marvin.model.viewmodel.CharacterViewModel;
import com.ruigoncalo.marvin.ui.BaseAdapter;
import com.ruigoncalo.marvin.ui.LoadingViewHolder;

/**
 * Created by ruigoncalo on 20/04/16.
 */
public class CharactersAdapter extends BaseAdapter<CharacterViewModel, RecyclerView.ViewHolder> {

    private static final int VH_TYPE_LOADING = 0;
    private static final int VH_TYPE_CHARACTER = 1;

    private OnCharacterItemClickListener listener;

    public CharactersAdapter(Context context) {
        super(context);
    }

    public void registerItemClickListener(OnCharacterItemClickListener listener) {
        this.listener = listener;
    }

    public void unregisterItemClickListener() {
        this.listener = null;
    }

    @Override
    public int getItemViewType(int position) {
        CharacterViewModel viewModel = getItem(position);
        if (viewModel.isLoader()) {
            return VH_TYPE_LOADING;
        } else {
            return VH_TYPE_CHARACTER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case VH_TYPE_CHARACTER:
                return createViewHolderCharacter(viewGroup);

            default:
                return createViewHolderLoading(viewGroup);
        }
    }

    private LoadingViewHolder createViewHolderLoading(ViewGroup viewGroup){
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_adapter_loader, viewGroup, false);
        return new LoadingViewHolder(itemView);
    }

    public CharacterViewHolder createViewHolderCharacter(ViewGroup viewGroup) {
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_adapter_character, viewGroup, false);
        return new CharacterViewHolder(itemView, listener);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CharacterViewModel viewModel = getItem(position);
        if(viewModel.isLoader()){
            bindLoadingViewHolder((LoadingViewHolder) holder, position);
        } else {
            bindCharacterViewHolder((CharacterViewHolder) holder, position);
        }
    }

    private void bindLoadingViewHolder(LoadingViewHolder holder, int position){
        holder.render(position);
    }

    private void bindCharacterViewHolder(CharacterViewHolder holder, int position){
        holder.render(getItem(position), position);

        if(position < getItemList().size() - 1) {
            if (!(getItem(position + 1)).isLoader()) {
                holder.prepareNext(getItem(position + 1), position + 1);
            }
        }
    }

    // add item loader to the end of the list
    public void showFooterLoading() {
        addItem(new CharacterViewModel.Builder()
                .isLoader(true)
                .build());
    }

    // remove last item from the list
    public void hideFooterLoading() {
        removeItem(getItemList().size() - 1);
    }

}
