package com.ruigoncalo.marvin.ui.characters;

import com.ruigoncalo.marvin.bus.CharactersMoreResultsErrorEvent;
import com.ruigoncalo.marvin.bus.CharactersMoreResultsEvent;
import com.ruigoncalo.marvin.bus.CharactersResultErrorEvent;
import com.ruigoncalo.marvin.bus.CharactersResultEvent;
import com.ruigoncalo.marvin.bus.SearchResultErrorEvent;
import com.ruigoncalo.marvin.bus.SearchResultEvent;
import com.ruigoncalo.marvin.model.viewmodel.CharacterViewModel;
import com.ruigoncalo.marvin.repository.CharactersStore;
import com.ruigoncalo.marvin.model.raw.Character;
import com.ruigoncalo.marvin.ui.ImageLoaderManager;
import com.ruigoncalo.marvin.utils.DataLoadingSubject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ruigoncalo on 24/04/16.
 */
public class CharactersPresenter implements DataLoadingSubject {

    private CharactersView view;
    private CharactersStore store;

    // couting requests that are not yet completed
    private AtomicInteger loadingCount;

    // counting pages requests
    private int pageCounter;

    public CharactersPresenter(CharactersView view, CharactersStore store) {
        this.view = view;
        this.store = store;
        this.loadingCount = new AtomicInteger(0);
    }

    public void onStart(){
        EventBus.getDefault().register(this);
    }

    public void onStop(){
        EventBus.getDefault().unregister(this);
    }

    public void getItems(){
        loadingCount.incrementAndGet();
        view.isLoadingCharacters(true);
        store.getCharacters(null);
    }

    public void getMoreItems(){
        int page = ++pageCounter;
        loadingCount.incrementAndGet();
        view.isLoadingMoreCharacters(true);
        store.getMoreCharacters(page);
    }

    public void searchCharacters(String query){
        loadingCount.incrementAndGet();
        view.isLoadingResults(true);
        store.searchCharacters(query);
    }

    @Override
    public boolean isDataLoading() {
        return loadingCount.get() > 0;
    }

    @Subscribe
    public void onCharactersResultEvent(CharactersResultEvent event) {
        loadingCount.decrementAndGet();
        List<CharacterViewModel> viewModels = new ArrayList<>();

        for (Character character : event.getList()) {
            CharacterViewModel viewModel = createViewModel(character, ImageLoaderManager.IMAGE_LANDSCAPE);
            viewModels.add(viewModel);
        }

        view.showCharacters(viewModels);
        view.isLoadingCharacters(false);
    }

    @Subscribe
    public void onCharactersResultErrorEvent(CharactersResultErrorEvent event) {
        loadingCount.decrementAndGet();
        view.showCharactersError(event.getMessage());
        view.isLoadingCharacters(false);
    }

    @Subscribe
    public void onCharactersMoreResultsEvent(CharactersMoreResultsEvent event) {
        loadingCount.decrementAndGet();
        List<CharacterViewModel> viewModels = new ArrayList<>();

        for (Character character : event.getList()) {
            CharacterViewModel viewModel = createViewModel(character, ImageLoaderManager.IMAGE_LANDSCAPE);
            viewModels.add(viewModel);
        }

        view.isLoadingMoreCharacters(false); // before showMoreCharacters to remove loader
        view.showMoreCharacters(viewModels);
    }

    @Subscribe
    public void onCharactersMoreResultsErrorEvent(CharactersMoreResultsErrorEvent event){
        loadingCount.decrementAndGet();
        view.showMoreCharactersError(event.getMessage());
        view.isLoadingMoreCharacters(false);
    }

    @Subscribe
    public void onSearchResultEvent(SearchResultEvent event) {
        loadingCount.decrementAndGet();
        List<CharacterViewModel> viewModels = new ArrayList<>();

        for (Character character : event.getList()) {
            CharacterViewModel viewModel = createViewModel(character, ImageLoaderManager.IMAGE_STANDARD);
            viewModels.add(viewModel);
        }

        view.showSearchResults(viewModels);
        view.isLoadingResults(false);
    }

    @Subscribe
    public void onSearchResultErrorEvent(SearchResultErrorEvent event) {
        loadingCount.decrementAndGet();
        view.showSearchResultsError(event.getMessage());
        view.isLoadingResults(false);
    }

    /**
     * Convert Character raw object to correspondent view model
     *
     * @param character raw object
     * @param imageFormat image ratio
     * @return character view model to be drawn on ui
     */
    private CharacterViewModel createViewModel(Character character, int imageFormat){
        return new CharacterViewModel.Builder()
                .id(character.getId())
                .title(character.getName())
                .imageUrl(ImageLoaderManager.buildImageUrl(character.getThumbnail(), imageFormat))
                .build();
    }
}
