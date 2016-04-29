package com.ruigoncalo.marvin.ui.profiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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
    @Bind(R.id.view_group_description) View groupDescription;
    @Bind(R.id.image_header) ImageView imageHeader;
    @Bind(R.id.text_description_content) TextView descriptionText;
    @Bind(R.id.view_group_comics) View groupComics;
    @Bind(R.id.list_comics) RecyclerView recyclerViewComics;
    @Bind(R.id.view_group_series) View groupSeries;
    @Bind(R.id.list_series) RecyclerView recyclerViewSeries;
    @Bind(R.id.view_group_stories) View groupStories;
    @Bind(R.id.list_stories) RecyclerView recyclerViewStories;
    @Bind(R.id.view_group_events) View groupEvents;
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

        if (id != -1) {
            presenter.getCharacterProfile(id);
            presenter.getCharacterComics(id);
            presenter.getCharacterSeries(id);
            presenter.getCharacterStories(id);
            presenter.getCharacterEvents(id);
        } else {
            // show error
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
        updateToolbarTitle(profile.getName());
        ImageLoaderManager.load(this, profile.getImageUrl(), imageHeader);

        if(profile.getDescription() != null && !profile.getDescription().isEmpty()) {
            descriptionText.setText(profile.getDescription());
        } else {
            groupDescription.setVisibility(View.GONE);
        }
    }

    @Override
    public void showCharacterProfileError(String message) {
        String log = "Show profile error: " + message;
        Timber.d(log);
    }

    @Override
    public void isLoadingCharacterComics(boolean loading) {
        String log = "Loading comics " + (loading ? "START" : "END");
        Timber.d(log);
    }

    @Override
    public void showCharacterComics(List<CollectionViewModel> items) {
        if(!items.isEmpty()) {
            comicsAdapter.setItemList(items);
        } else {
            groupComics.setVisibility(View.GONE);
        }
    }

    @Override
    public void showCharacterComicsError(String message) {
        String log = "Show comics error: " + message;
        Timber.d(log);
    }

    @Override
    public void isLoadingCharacterSeries(boolean loading) {
        String log = "Loading series " + (loading ? "START" : "END");
        Timber.d(log);
    }

    @Override
    public void showCharacterSeries(List<CollectionViewModel> items) {
        if(!items.isEmpty()) {
            seriesAdapter.setItemList(items);
        } else {
            groupSeries.setVisibility(View.GONE);
        }
    }

    @Override
    public void showCharacterSeriesError(String message) {
        String log = "Show series error: " + message;
        Timber.d(log);
    }

    @Override
    public void isLoadingCharacterStories(boolean loading) {
        String log = "Loading stories " + (loading ? "START" : "END");
        Timber.d(log);
    }

    @Override
    public void showCharacterStories(List<CollectionViewModel> items) {
        if (!items.isEmpty()) {
            storiesAdapter.setItemList(items);
        } else {
            groupStories.setVisibility(View.GONE);
        }
    }

    @Override
    public void showCharacterStoriesError(String message) {
        String log = "Show stories error: " + message;
        Timber.d(log);
    }

    @Override
    public void isLoadingCharacterEvents(boolean loading) {
        String log = "Loading events " + (loading ? "START" : "END");
        Timber.d(log);
    }

    @Override
    public void showCharacterEvents(List<CollectionViewModel> items) {
        if (!items.isEmpty()) {
            eventsAdapter.setItemList(items);
        } else {
            groupEvents.setVisibility(View.GONE);
        }
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

}
