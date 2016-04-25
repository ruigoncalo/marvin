package com.ruigoncalo.marvin;

import android.content.Context;

import com.ruigoncalo.marvin.api.ApiManager;
import com.ruigoncalo.marvin.repository.CharactersStore;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ruigoncalo on 23/04/16.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        ServicesModule.class})
public interface AppComponent {
    void inject(App app);

    Context getAppContext();
    ApiManager getApiManager();
    CharactersStore getCharactersStore();
}