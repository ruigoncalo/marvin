package com.ruigoncalo.marvin.ui.profiles;

import android.support.annotation.Nullable;

import com.ruigoncalo.marvin.bus.CharacterComicsErrorEvent;
import com.ruigoncalo.marvin.bus.CharacterComicsEvent;
import com.ruigoncalo.marvin.bus.CharacterEventsErrorEvent;
import com.ruigoncalo.marvin.bus.CharacterProfileErrorEvent;
import com.ruigoncalo.marvin.bus.CharacterProfileEvent;
import com.ruigoncalo.marvin.bus.CharacterEventsEvent;
import com.ruigoncalo.marvin.bus.CharacterSeriesErrorEvent;
import com.ruigoncalo.marvin.bus.CharacterSeriesEvent;
import com.ruigoncalo.marvin.bus.CharacterStoriesErrorEvent;
import com.ruigoncalo.marvin.bus.CharacterStoriesEvent;
import com.ruigoncalo.marvin.model.raw.Character;
import com.ruigoncalo.marvin.model.raw.CollectionItemResult;
import com.ruigoncalo.marvin.model.raw.CollectionItemResults;
import com.ruigoncalo.marvin.model.raw.Url;
import com.ruigoncalo.marvin.model.viewmodel.ProfileViewModel;
import com.ruigoncalo.marvin.repository.CharactersStore;
import com.ruigoncalo.marvin.ui.ImageLoaderManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruigoncalo on 25/04/16.
 */
public class ProfilePresenter {

    private ProfileView view;
    private CharactersStore store;

    public ProfilePresenter(ProfileView view, CharactersStore store) {
        this.view = view;
        this.store = store;
    }

    public void onStart(){
        EventBus.getDefault().register(this);
    }

    public void onStop(){
        EventBus.getDefault().unregister(this);
    }


    public void getCharacterProfile(int id){
        view.isLoadingProfile(true);
        store.getCharacterProfile(id);
    }

    public void getCharacterComics(int id){
        view.isLoadingCharacterComics(true);
        store.getCharacterComics(id);
    }

    public void getCharacterSeries(int id){
        view.isLoadingCharacterSeries(true);
        store.getCharacterSeries(id);
    }

    public void getCharacterStories(int id){
        view.isLoadingCharacterStories(true);
        store.getCharacterStories(id);
    }

    public void getCharacterEvents(int id){
        view.isLoadingCharacterEvents(true);
        store.getCharacterEvents(id);
    }

    @Subscribe
    public void onCharacterProfileEvent(CharacterProfileEvent event){
        ProfileViewModel viewModel = createViewModel(event.getCharacter(),
                ImageLoaderManager.IMAGE_STANDARD);
        view.showCharacterProfile(viewModel);
        view.isLoadingProfile(false);
    }

    @Subscribe
    public void onCharacterProfileErrorEvent(CharacterProfileErrorEvent event){
        view.showCharacterProfileError(event.getMessage());
        view.isLoadingProfile(false);
    }

    @Subscribe
    public void onCharacterComicsEvent(CharacterComicsEvent event){
        List<CollectionViewModel> items = createCollectionViewModelList(event.getItems());
        view.showCharacterComics(items);
        view.isLoadingCharacterComics(false);
    }

    @Subscribe
    public void onCharacterComicsErrorEvent(CharacterComicsErrorEvent event){
        view.showCharacterComicsError(event.getMessage());
        view.isLoadingProfile(false);
    }

    @Subscribe
    public void onCharacterSeriesEvent(CharacterSeriesEvent event){
        List<CollectionViewModel> items = createCollectionViewModelList(event.getItems());
        view.showCharacterSeries(items);
        view.isLoadingCharacterSeries(false);
    }

    @Subscribe
    public void onCharacterSeriesErrorEvent(CharacterSeriesErrorEvent event){
        view.showCharacterSeriesError(event.getMessage());
        view.isLoadingProfile(false);
    }

    @Subscribe
    public void onCharacterStoriesEvent(CharacterStoriesEvent event){
        List<CollectionViewModel> items = createCollectionViewModelList(event.getItems());
        view.showCharacterStories(items);
        view.isLoadingCharacterStories(false);
    }

    @Subscribe
    public void onCharacterStoriesErrorEvent(CharacterStoriesErrorEvent event){
        view.showCharacterStoriesError(event.getMessage());
        view.isLoadingProfile(false);
    }

    @Subscribe
    public void onCharacterEventsEvent(CharacterEventsEvent event){
        List<CollectionViewModel> items = createCollectionViewModelList(event.getItems());
        view.showCharacterEvents(items);
        view.isLoadingCharacterEvents(false);
    }

    @Subscribe
    public void onCharacterEventsErrorEvent(CharacterEventsErrorEvent event){
        view.showCharacterEventsError(event.getMessage());
        view.isLoadingProfile(false);
    }

    /**
     * Convert Character raw object into correspondent view model
     *
     * @param character raw object
     * @param imageFormat format for image
     * @return character view model to be drawn on ui
     */
    private ProfileViewModel createViewModel(Character character, int imageFormat){
        return new ProfileViewModel.Builder()
                .id(character.getId())
                .name(character.getName())
                .imageUrl(ImageLoaderManager.buildImageUrl(character.getThumbnail(),
                        imageFormat))
                .description(character.getDescription())
                .detail(getRelatedLink(Url.TYPE_DETAIL, character.getUrls()))
                .wiki(getRelatedLink(Url.TYPE_WIKI, character.getUrls()))
                .comicLink(getRelatedLink(Url.TYPE_COMICLINK, character.getUrls()))
                .build();
    }

    /**
     * Convert collection results raw objects into correspondent view model
     *
     * @param results raw object
     * @return view model to be drawn on ui
     */
    private List<CollectionViewModel> createCollectionViewModelList(CollectionItemResults results){
        List<CollectionViewModel> list = new ArrayList<>();
        for(CollectionItemResult result : results.getResults()){
            CollectionViewModel collectionViewModel = new CollectionViewModel.Builder()
                    .id(result.getId())
                    .title(result.getTitle())
                    .imageUrl(ImageLoaderManager.buildImageUrl(result.getThumbnail(),
                            ImageLoaderManager.IMAGE_PORTRAIT))
                    .build();

            list.add(collectionViewModel);
        }

        return list;
    }

    private String getRelatedLink(String type, @Nullable List<Url> urls){
        if(urls != null) {
            for (Url url : urls) {
                if (type.equals(url.getType())) {
                    return url.getUrl();
                }
            }
        }

        return null;
    }
}
