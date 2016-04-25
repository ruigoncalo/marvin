package com.ruigoncalo.marvin.ui.characters;

import com.ruigoncalo.marvin.AppComponent;
import com.ruigoncalo.marvin.ui.ActivityScope;

import dagger.Component;

/**
 * Created by ruigoncalo on 24/04/16.
 */

@ActivityScope
@Component(dependencies = AppComponent.class,
        modules = CharactersModule.class)
public interface CharactersComponent {
    void inject(CharactersActivity activity);

    CharactersPresenter getPresenter();
}
