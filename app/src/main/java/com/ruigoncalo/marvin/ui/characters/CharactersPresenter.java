package com.ruigoncalo.marvin.ui.characters;

import com.ruigoncalo.marvin.bus.CharactersResultErrorEvent;
import com.ruigoncalo.marvin.bus.CharactersResultEvent;
import com.ruigoncalo.marvin.bus.SearchResultErrorEvent;
import com.ruigoncalo.marvin.bus.SearchResultEvent;
import com.ruigoncalo.marvin.model.viewmodel.CharacterViewModel;
import com.ruigoncalo.marvin.repository.CharactersStore;
import com.ruigoncalo.marvin.model.raw.Character;
import com.ruigoncalo.marvin.ui.ImageLoaderManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruigoncalo on 24/04/16.
 */
public class CharactersPresenter {

    private CharactersView view;
    private CharactersStore store;

    public CharactersPresenter(CharactersView view, CharactersStore store) {
        this.view = view;
        this.store = store;
    }

    public void onStart(){
        EventBus.getDefault().register(this);
    }

    public void onStop(){
        EventBus.getDefault().unregister(this);
    }

    public void getItems(){
        view.isLoading(true);
        store.getList(null);
    }

    public void searchCharacters(String query){
        view.isLoading(true);
        store.searchCharacters(query);
    }

    @Subscribe
    public void onCharactersResultEvent(CharactersResultEvent event) {
        List<CharacterViewModel> viewModels = new ArrayList<>();

        for (Character character : event.getList()) {
            CharacterViewModel viewModel = createViewModel(character, true); // landscape
            viewModels.add(viewModel);
        }

        view.showCharacters(viewModels);
        view.isLoading(false);
    }

    @Subscribe
    public void onCharactersResultErrorEvent(CharactersResultErrorEvent event) {
        view.showCharactersError(event.getMessage());
        view.isLoading(false);
    }

    @Subscribe
    public void onSearchResultEvent(SearchResultEvent event) {
        List<CharacterViewModel> viewModels = new ArrayList<>();

        for (Character character : event.getList()) {
            CharacterViewModel viewModel = createViewModel(character, false); // standard
            viewModels.add(viewModel);
        }

        view.showSearchResults(viewModels);
        view.isLoading(false);
    }

    @Subscribe
    public void onSearchResultErrorEvent(SearchResultErrorEvent event) {
        view.showSearchResultsError(event.getMessage());
        view.isLoading(false);
    }

    /**
     * Convert Character raw object to correspondent view model
     *
     * @param character raw object
     * @param imageLandscape use landscape or standard image resolution
     * @return character view model to be drawn on ui
     */
    private CharacterViewModel createViewModel(Character character, boolean imageLandscape){
        return new CharacterViewModel.Builder()
                .id(character.getId())
                .title(character.getName())
                .imageUrl(ImageLoaderManager.buildImageUrl(character.getThumbnail(), imageLandscape))
                .build();
    }
}
