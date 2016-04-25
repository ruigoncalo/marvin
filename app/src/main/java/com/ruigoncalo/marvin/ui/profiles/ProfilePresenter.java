package com.ruigoncalo.marvin.ui.profiles;

import com.ruigoncalo.marvin.repository.CharactersStore;

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

    public void getCharacterProfile(String id){

    }
}
