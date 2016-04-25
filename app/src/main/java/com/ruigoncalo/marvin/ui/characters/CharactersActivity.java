package com.ruigoncalo.marvin.ui.characters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ruigoncalo.marvin.AppComponent;
import com.ruigoncalo.marvin.R;
import com.ruigoncalo.marvin.repository.CharactersStore;
import com.ruigoncalo.marvin.ui.BaseActivity;
import com.ruigoncalo.marvin.ui.profiles.ProfileActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ruigoncalo on 20/04/16.
 */
public class CharactersActivity extends BaseActivity implements CharactersView,
        OnCharacterItemClickListener {

    @Inject CharactersPresenter presenter;

    @Bind(R.id.recycler_view_characters) RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout_characters) SwipeRefreshLayout swipeRefreshLayout;

    private CharactersAdapter adapter;

    @Override
    protected void setupComponent(AppComponent appComponent) {
        DaggerCharactersComponent.builder()
                .appComponent(appComponent)
                .charactersModule(new CharactersModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_screen_characters);
        ButterKnife.bind(this);
        setupSwipeRefreshLayout();
        setupAdapter();
        setupRecyclerView();
        setupBlankState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.registerItemClickListener(this);
        presenter.getItems();
    }

    @Override
    protected void onStop() {
        adapter.unregisterItemClickListener();
        super.onStop();
    }

    @Override
    public void showCharacters(List<CharacterViewModel> list) {
        adapter.setItemList(list);
    }

    @Override
    public void isLoading(boolean loading) {

    }

    @Override
    public void onCharacterItemClick(int position) {
        CharacterViewModel item = adapter.getItem(position);
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(CharactersStore.CHARACTER_ID, item.getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void setupBlankState() {
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void setupAdapter() {
        adapter = new CharactersAdapter(this);
    }

    private void setupSwipeRefreshLayout() {

    }


}
