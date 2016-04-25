package com.ruigoncalo.marvin.ui.profiles;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ruigoncalo.marvin.AppComponent;
import com.ruigoncalo.marvin.R;
import com.ruigoncalo.marvin.repository.CharactersStore;
import com.ruigoncalo.marvin.ui.BaseActivity;
import com.ruigoncalo.marvin.ui.BaseAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ruigoncalo on 25/04/16.
 */
public class ProfileActivity extends BaseActivity implements ProfileView {

    @Bind(R.id.text_description_content) TextView descriptionText;
    @Bind(R.id.list_comics) RecyclerView recyclerViewComics;
    @Bind(R.id.list_series) RecyclerView recyclerViewSeries;
    @Bind(R.id.list_stories) RecyclerView recyclerViewStories;
    @Bind(R.id.list_events) RecyclerView recyclerViewEvents;

    private CollectionAdapter comicsAdapter;
    private CollectionAdapter seriesAdapter;
    private CollectionAdapter storiesAdapter;
    private CollectionAdapter eventsAdapter;

    private String id;

    @Override
    protected void setupComponent(AppComponent appComponent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_screen_profile);
        ButterKnife.bind(this);
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

        if(id != null){

        }
    }

    @Override
    public void showCharacterProfile(ProfileViewModel profile) {

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
        id = getIntent().getStringExtra(CharactersStore.CHARACTER_ID);
    }



}
