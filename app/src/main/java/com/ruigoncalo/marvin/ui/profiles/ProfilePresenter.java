package com.ruigoncalo.marvin.ui.profiles;

import com.ruigoncalo.marvin.bus.CharacterEvent;
import com.ruigoncalo.marvin.model.raw.Character;
import com.ruigoncalo.marvin.model.viewmodel.ProfileViewModel;
import com.ruigoncalo.marvin.repository.CharactersStore;
import com.ruigoncalo.marvin.ui.ImageLoaderManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
                .build();
    }
}
