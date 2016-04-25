package com.ruigoncalo.marvin;

import android.content.Context;

import com.ruigoncalo.marvin.api.ApiManager;
import com.ruigoncalo.marvin.repository.CharactersStore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ruigoncalo on 23/04/16.
 */
@Module
public class ServicesModule {

    @Singleton
    @Provides
    ApiManager provideApiManager(Context context){
        return new ApiManager(context);
    }

    @Singleton
    @Provides
    public CharactersStore provideCharactersStore(ApiManager apiManager){
        return new CharactersStore(apiManager);
    }

}
