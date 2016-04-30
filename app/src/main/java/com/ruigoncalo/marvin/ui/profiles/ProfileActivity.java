package com.ruigoncalo.marvin.ui.profiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruigoncalo.marvin.AppComponent;
import com.ruigoncalo.marvin.R;
import com.ruigoncalo.marvin.model.viewmodel.ProfileViewModel;
import com.ruigoncalo.marvin.repository.CharactersStore;
import com.ruigoncalo.marvin.ui.BaseActivity;
import com.ruigoncalo.marvin.ui.BaseAdapter;
import com.ruigoncalo.marvin.ui.ImageLoaderManager;
import com.ruigoncalo.marvin.ui.collections.CollectionsActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by ruigoncalo on 25/04/16.
 */
public class ProfileActivity extends BaseActivity implements ProfileView {

    @Inject ProfilePresenter presenter;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.container) View container;
    @Bind(R.id.image_header) ImageView imageHeader;
    @Bind(R.id.text_description_content) TextView descriptionText;

    @Bind(R.id.container_comics) ViewGroup comicsContainer;
    @Bind(R.id.loading_comics) View loadingComics;
    @Bind(R.id.text_comics_info) TextView textComics;
    @Bind(R.id.list_comics) RecyclerView recyclerViewComics;

    @Bind(R.id.container_series) ViewGroup seriesContainer;
    @Bind(R.id.loading_series) View loadingSeries;
    @Bind(R.id.text_series_info) TextView textSeries;
    @Bind(R.id.list_series) RecyclerView recyclerViewSeries;

    @Bind(R.id.container_stories) ViewGroup storiesContainer;
    @Bind(R.id.loading_stories) View loadingStories;
    @Bind(R.id.text_stories_info) TextView textStories;
    @Bind(R.id.list_stories) RecyclerView recyclerViewStories;

    @Bind(R.id.container_events) ViewGroup eventsContainer;
    @Bind(R.id.loading_events) View loadingEvents;
    @Bind(R.id.text_events_info) TextView textEvents;
    @Bind(R.id.list_events) RecyclerView recyclerViewEvents;

    private CollectionAdapter comicsAdapter;
    private CollectionAdapter seriesAdapter;
    private CollectionAdapter storiesAdapter;
    private CollectionAdapter eventsAdapter;

    private int id;
    private boolean hasProfile, hasComics, hasSeries, hasStories, hasEvents;

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

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

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

        if (id != -1) {
            if(!hasProfile) {
                presenter.getCharacterProfile(id);
            }

            if(!hasComics) {
                presenter.getCharacterComics(id);
            }

            if(!hasSeries) {
                presenter.getCharacterSeries(id);
            }

            if(!hasStories) {
                presenter.getCharacterStories(id);
            }

            if(!hasEvents) {
                presenter.getCharacterEvents(id);
            }
        } else {
            Snackbar.make(container, getString(R.string.error_default), Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        comicsAdapter.unregisterItemClickListener();
        seriesAdapter.unregisterItemClickListener();
        storiesAdapter.unregisterItemClickListener();
        eventsAdapter.unregisterItemClickListener();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void isLoadingProfile(boolean loading) {
        String log = "Loading profile " + (loading ? "START" : "END");
        Timber.d(log);
    }

    @Override
    public void showCharacterProfile(ProfileViewModel profile) {
        hasProfile = true;
        updateToolbarTitle(profile.getName());
        ImageLoaderManager.load(this, profile.getImageUrl(), imageHeader);

        if(profile.getDescription() != null && !profile.getDescription().isEmpty()) {
            descriptionText.setText(profile.getDescription());
        } else {
            descriptionText.setText(getString(R.string.error_data_not_found));
        }
    }

    @Override
    public void showCharacterProfileError(String message) {
        String log = "Show profile error: " + message;
        Timber.d(log);
    }

    @Override
    public void isLoadingCharacterComics(boolean loading) {
        showLoadingCollection(loading, loadingComics);
    }

    @Override
    public void showCharacterComics(List<CollectionViewModel> items) {
        hasComics = true;
        showCollectionItems(items, recyclerViewComics, comicsAdapter, textComics);
    }

    @Override
    public void showCharacterComicsError(String message) {
        String log = "Show comics error: " + message;
        Timber.d(log);
    }

    @Override
    public void isLoadingCharacterSeries(boolean loading) {
        showLoadingCollection(loading, loadingSeries);
    }

    @Override
    public void showCharacterSeries(List<CollectionViewModel> items) {
        hasSeries = true;
        showCollectionItems(items, recyclerViewSeries, seriesAdapter, textSeries);
    }

    @Override
    public void showCharacterSeriesError(String message) {
        String log = "Show series error: " + message;
        Timber.d(log);
    }

    @Override
    public void isLoadingCharacterStories(boolean loading) {
        showLoadingCollection(loading, loadingStories);
    }

    @Override
    public void showCharacterStories(List<CollectionViewModel> items) {
        hasStories = true;
        showCollectionItems(items, recyclerViewStories, storiesAdapter, textStories);
    }

    @Override
    public void showCharacterStoriesError(String message) {
        String log = "Show stories error: " + message;
        Timber.d(log);
    }

    @Override
    public void isLoadingCharacterEvents(boolean loading) {
        showLoadingCollection(loading, loadingEvents);
    }

    @Override
    public void showCharacterEvents(List<CollectionViewModel> items) {
        hasEvents = true;
        showCollectionItems(items, recyclerViewEvents, eventsAdapter, textEvents);
    }

    @Override
    public void showCharacterEventsError(String message) {
        String log = "Show events error: " + message;
        Timber.d(log);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
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

    private void setupAdapters() {
        comicsAdapter = new CollectionAdapter(this);
        seriesAdapter = new CollectionAdapter(this);
        storiesAdapter = new CollectionAdapter(this);
        eventsAdapter = new CollectionAdapter(this);

        comicsAdapter.registerItemClickListener(new OnCollectionItemClickListener() {
            @Override
            public void onClick(int position) {
                onItemClick(comicsAdapter, position);
            }
        });

        seriesAdapter.registerItemClickListener(new OnCollectionItemClickListener() {
            @Override
            public void onClick(int position) {
                onItemClick(seriesAdapter, position);
            }
        });

        storiesAdapter.registerItemClickListener(new OnCollectionItemClickListener() {
            @Override
            public void onClick(int position) {
                onItemClick(storiesAdapter, position);
            }
        });

        eventsAdapter.registerItemClickListener(new OnCollectionItemClickListener() {
            @Override
            public void onClick(int position) {
                onItemClick(eventsAdapter, position);
            }
        });
    }

    private void setupRecyclerView(RecyclerView recyclerView, BaseAdapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        // get nestedscrollview vertical scroll working well with horizontal recyclerview
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void resolveIntent() {
        id = getIntent().getIntExtra(CharactersStore.CHARACTER_ID, -1);
    }

    private void onItemClick(CollectionAdapter adapter, int position) {
        ArrayList<CharSequence> titles = new ArrayList<>();
        ArrayList<CharSequence> images = new ArrayList<>();

        for (CollectionViewModel item : adapter.getItemList()) {
            titles.add(item.getTitle());
            images.add(item.getImageUrl());
        }

        startCollectionsActivity(titles, images, position);
    }

    private void startCollectionsActivity(ArrayList<CharSequence> titles,
                                          ArrayList<CharSequence> images, int position) {

        Intent intent = new Intent(this, CollectionsActivity.class);
        intent.putCharSequenceArrayListExtra(CollectionsActivity.EXTRA_LIST_TITLES, titles);
        intent.putCharSequenceArrayListExtra(CollectionsActivity.EXTRA_LIST_IMAGES, images);
        intent.putExtra(CollectionsActivity.EXTRA_POSITION, position);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void showLoadingCollection(boolean loading, View view){
        view.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    private void showCollectionItems(List<CollectionViewModel> items, RecyclerView recyclerView,
                                     CollectionAdapter adapter, TextView textInfo){
        if(!items.isEmpty()) {
            textInfo.setVisibility(View.GONE);
            adapter.setItemList(items);
        } else {
            recyclerView.setVisibility(View.GONE);
            textInfo.setText(getString(R.string.error_data_not_found));
        }

    }

}
