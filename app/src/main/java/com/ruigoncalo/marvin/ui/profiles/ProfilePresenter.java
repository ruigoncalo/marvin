package com.ruigoncalo.marvin.ui.profiles;

import android.support.v4.util.Pair;

import com.ruigoncalo.marvin.bus.CharacterEvent;
import com.ruigoncalo.marvin.model.raw.Character;
import com.ruigoncalo.marvin.model.raw.Collection;
import com.ruigoncalo.marvin.model.raw.Item;
import com.ruigoncalo.marvin.model.viewmodel.ItemViewModel;
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
        store.getCharacterProfile(id);
    }

    @Subscribe
    public void onCharacterEvent(CharacterEvent event){
        ProfileViewModel viewModel = createViewModel(event.getCharacter(), true);
        view.showCharacterProfile(viewModel);
    }

    /**
     * Convert Character raw object to correspondent view model
     *
     * @param character raw object
     * @param imageLandscape use landscape or standard image resolution
     * @return character view model to be drawn on ui
     */
    private ProfileViewModel createViewModel(Character character, boolean imageLandscape){
        return new ProfileViewModel.Builder()
                .id(character.getId())
                .name(character.getName())
                .imageUrl(ImageLoaderManager.buildImageUrl(character.getThumbnail(), imageLandscape))
                .comics(createCollectionViewModelList(character.getComics()))
                .series(createCollectionViewModelList(character.getSeries()))
                .stories(createCollectionViewModelList(character.getStories()))
                .events(createCollectionViewModelList(character.getEvents()))
                .build();
    }


    private List<CollectionViewModel> createCollectionViewModelList(Collection collection){
        List<CollectionViewModel> list = new ArrayList<>();
        for(Item item : collection.getItems()){
            CollectionViewModel collectionViewModel = new CollectionViewModel.Builder()
                    .id(0)
                    .title(item.getName())
                    .imageUrl("http://i.annihil.us/u/prod/marvel/i/mg/c/b0/4bb852bf1fe78/portrait_xlarge.jpg")
                    .build();

            list.add(collectionViewModel);
        }

        return list;
    }

    private ItemViewModel createItemViewModel(Collection collection){
        int available = collection.getAvailable();
        int returned = collection.getReturned();

        List<Pair<String, String>> items = new ArrayList<>();
        for(Item item : collection.getItems()){
            items.add(new Pair<>(item.getName(), item.getCollectionURI()));
        }

        return new ItemViewModel.Builder()
                .available(available)
                .returned(returned)
                .items(items)
                .build();
    }
}
