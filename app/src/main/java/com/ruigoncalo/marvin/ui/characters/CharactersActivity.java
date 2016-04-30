package com.ruigoncalo.marvin.ui.characters;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.ruigoncalo.marvin.AppComponent;
import com.ruigoncalo.marvin.R;
import com.ruigoncalo.marvin.model.viewmodel.CharacterViewModel;
import com.ruigoncalo.marvin.repository.CharactersStore;
import com.ruigoncalo.marvin.ui.AdapterListSizeListener;
import com.ruigoncalo.marvin.ui.BaseActivity;
import com.ruigoncalo.marvin.ui.profiles.ProfileActivity;
import com.ruigoncalo.marvin.utils.InfiniteScrollListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * Created by ruigoncalo on 20/04/16.
 */
public class CharactersActivity extends BaseActivity implements CharactersView,
        OnCharacterItemClickListener, SwipeRefreshLayout.OnRefreshListener, AdapterListSizeListener {

    @Inject CharactersPresenter presenter;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.recycler_view_characters) RecyclerView charactersRecyclerView;
    @Bind(R.id.swipe_refresh_layout_characters) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.text_info) TextView textInfo;

    private Subscription subscription;
    private CharactersAdapter charactersAdapter;
    private SearchesAdapter searchesAdapter;

    private boolean isSearchMode;
    private boolean hasItems;

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
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        ButterKnife.bind(this);
        setupToolbar();
        setupSwipeRefreshLayout();
        setupAdapter();
        setupRecyclerView();
        setupBlankState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        setupSearchView(searchMenuItem);

        return super.onCreateOptionsMenu(menu);
    }

    private void setupSearchView(MenuItem menuItem) {
        final SearchView searchView = (SearchView) menuItem.getActionView();

        MenuItemCompat.setActionView(menuItem, searchView);
        searchView.setQueryHint(getString(R.string.action_search));
        MenuItemCompat.setOnActionExpandListener(menuItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        replaceCharatersBySearchLayout();
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        restoreOriginalLayout();
                        return true;
                    }
                });

        // Get the search close button image view
        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);

        if(closeButton != null){
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchView.setQuery("", false);
                    searchesAdapter.clear();
                }
            });
        }

        subscription = RxSearchView.queryTextChanges(searchView)
                .debounce(175, TimeUnit.MILLISECONDS)
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return !charSequence.toString().isEmpty();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSearchObserver());
    }

    @Override
    protected void onStart() {
        super.onStart();
        charactersAdapter.registerItemClickListener(this);
        searchesAdapter.registerItemClickListener(this);
        presenter.onStart();

        if (!hasItems) {
            presenter.getItems();
        }
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        charactersAdapter.unregisterItemClickListener();
        searchesAdapter.unregisterItemClickListener();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        subscription.unsubscribe();
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void isLoadingCharacters(boolean loading) {
        showLoading(loading);
    }

    @Override
    public void showCharacters(List<CharacterViewModel> list) {
        hasItems = true;
        charactersAdapter.setItemList(list);
    }

    @Override
    public void showCharactersError(String message) {
        String errorLog = "Characters error: " + message;
        Timber.d(errorLog);
        // show ui error
    }

    @Override
    public void isLoadingMoreCharacters(boolean loading) {
        if (loading) {
            // show footer loading while waiting for more ads
            charactersAdapter.showFooterLoading();
        } else {
            // hide footer loading
            charactersAdapter.hideFooterLoading();
        }
    }

    @Override
    public void showMoreCharacters(List<CharacterViewModel> list) {
        charactersAdapter.appendItems(list);
    }

    @Override
    public void showMoreCharactersError(String message) {
        String errorLog = "More characters error: " + message;
        Timber.d(errorLog);
        // show ui error
    }

    @Override
    public void isLoadingResults(boolean loading) {
        showLoading(loading);
    }

    @Override
    public void showSearchResults(List<CharacterViewModel> list) {
        textInfo.setVisibility(View.GONE);
        searchesAdapter.setItemList(list);
        if(list.isEmpty()){
            textInfo.setVisibility(View.VISIBLE);
            textInfo.setText(getString(R.string.text_no_results));
        }
    }

    @Override
    public void showSearchResultsError(String message) {
        String errorLog = "Search error: " + message;
        Timber.d(errorLog);
        // show ui error
    }

    @Override
    public void onCharacterItemClick(int position) {
        CharacterViewModel item = isSearchMode ?
                searchesAdapter.getItem(position) :
                charactersAdapter.getItem(position);

        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(CharactersStore.CHARACTER_ID, item.getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        if (isSearchMode) {
            showLoading(false);
        } else {
            presenter.getItems();
        }
    }

    @Override
    public void isAdapterListEmpty() {
        Timber.d("Empty list");
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            ViewCompat.setElevation(toolbar, 16);
            actionBar.setTitle(getString(R.string.marvel_characters));
        }
    }

    private void setupBlankState() {
        textInfo.setVisibility(View.GONE);
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        charactersRecyclerView.setLayoutManager(layoutManager);
        charactersRecyclerView.setHasFixedSize(true);
        charactersRecyclerView.addOnScrollListener(
                new InfiniteScrollListener(layoutManager, presenter) {
                    @Override
                    public void onLoadMore() {
                        if (!isSearchMode) {
                            presenter.getMoreItems();
                        }
                    }
                });
        charactersRecyclerView.setAdapter(charactersAdapter);
    }

    private void setupAdapter() {
        charactersAdapter = new CharactersAdapter(this);
        searchesAdapter = new SearchesAdapter(this);
    }

    private void setupSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_dark,
                android.R.color.holo_red_light,
                android.R.color.holo_red_dark,
                android.R.color.holo_red_light);

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void replaceCharatersBySearchLayout() {
        isSearchMode = true;
        charactersRecyclerView.setAdapter(searchesAdapter);
    }

    private void restoreOriginalLayout() {
        searchesAdapter.getItemList().clear();

        isSearchMode = false;
        charactersRecyclerView.setAdapter(charactersAdapter);
    }

    private Observer<CharSequence> getSearchObserver() {
        return new Observer<CharSequence>() {
            @Override
            public void onCompleted() {
                // empty
            }

            @Override
            public void onError(Throwable e) {
                // show ui error
            }

            @Override
            public void onNext(CharSequence charSequence) {
                presenter.searchCharacters(charSequence.toString());
            }
        };
    }

    // TODO: check task running when activity is finishing

    private void showLoading(final boolean loading) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(loading);
            }
        });
    }


}