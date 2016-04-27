package com.ruigoncalo.marvin.ui.profiles;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruigoncalo.marvin.AppComponent;
import com.ruigoncalo.marvin.R;
import com.ruigoncalo.marvin.model.viewmodel.ProfileViewModel;
import com.ruigoncalo.marvin.repository.CharactersStore;
import com.ruigoncalo.marvin.ui.BaseActivity;
import com.ruigoncalo.marvin.ui.BaseAdapter;
import com.ruigoncalo.marvin.ui.ImageLoaderManager;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ruigoncalo on 25/04/16.
 */
public class ProfileActivity extends BaseActivity implements ProfileView {

    @Inject ProfilePresenter presenter;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.image_header) ImageView imageHeader;
    @Bind(R.id.text_description_content) TextView descriptionText;
    @Bind(R.id.list_comics) RecyclerView recyclerViewComics;
    @Bind(R.id.list_series) RecyclerView recyclerViewSeries;
    @Bind(R.id.list_stories) RecyclerView recyclerViewStories;
    @Bind(R.id.list_events) RecyclerView recyclerViewEvents;

    private CollectionAdapter comicsAdapter;
    private CollectionAdapter seriesAdapter;
    private CollectionAdapter storiesAdapter;
    private CollectionAdapter eventsAdapter;

    private int id;

    @Override
    protected void setupComponent(AppComponent appComponent) {
        DaggerProfilesComponent.builder()
                .appComponent(appComponent)
                .profilesModule(new ProfilesModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_screen_profile);
        ButterKnife.bind(this);
        setupToolbar();
        setupAdapters();
        setupRecyclerView(recyclerViewComics, comicsAdapter);
        setupRecyclerView(recyclerViewSeries, seriesAdapter);
        setupRecyclerView(recyclerViewStories, storiesAdapter);
        setupRecyclerView(recyclerViewEvents, eventsAdapter);
        resolveIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();

        if(id != -1){
            presenter.getCharacterProfile(id);
        }
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    public void showCharacterProfile(ProfileViewModel profile) {
        updateToolbarTitle(profile.getName());
        ImageLoaderManager.load(this, profile.getImageUrl(), imageHeader);
        descriptionText.setText(profile.getDescription());

        comicsAdapter.setItemList(profile.getComics());
        seriesAdapter.setItemList(profile.getSeries());
        storiesAdapter.setItemList(profile.getStories());
        eventsAdapter.setItemList(profile.getEvents());
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void updateToolbarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    private void setupAdapters(){
        comicsAdapter = new CollectionAdapter(this);
        seriesAdapter = new CollectionAdapter(this);
        storiesAdapter = new CollectionAdapter(this);
        eventsAdapter = new CollectionAdapter(this);
    }

    private void setupRecyclerView(RecyclerView recyclerView, BaseAdapter adapter){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void resolveIntent(){
        id = getIntent().getIntExtra(CharactersStore.CHARACTER_ID, -1);
    }

}
