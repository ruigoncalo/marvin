package com.ruigoncalo.marvin.ui.characters;

import com.ruigoncalo.marvin.repository.CharactersStore;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ruigoncalo on 24/04/16.
 */
@Module
public class CharactersModule {

    private CharactersView view;

    public CharactersModule(CharactersView view) {
        this.view = view;
    }

    @Provides
    public CharactersView provideView(){
        return view;
    }

    @Provides
    public CharactersPresenter providePresenter(CharactersView view, CharactersStore store){
        return new CharactersPresenter(view, store);
    }
}
