package com.ruigoncalo.marvin.ui.profiles;

import com.ruigoncalo.marvin.repository.CharactersStore;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ruigoncalo on 26/04/16.
 */

@Module
public class ProfilesModule {

    private ProfileView view;

    public ProfilesModule(ProfileView view) {
        this.view = view;
    }

    @Provides
    public ProfileView provideView(){
        return view;
    }

    @Provides
    public ProfilePresenter providePresenter(ProfileView view, CharactersStore store){
        return new ProfilePresenter(view, store);
    }
}
