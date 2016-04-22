package com.ruigoncalo.marvin;

import com.ruigoncalo.marvin.api.ApiManager;
import com.ruigoncalo.marvin.api.ApiProvider;

import javax.inject.Singleton;

import dagger.Provides;

/**
 * Created by ruigoncalo on 23/04/16.
 */
public class ServicesModule {

    @Singleton
    @Provides
    ApiProvider provideApi(){
        return new ApiProvider();
    }

    @Singleton
    @Provides
    ApiManager provideApiManager(ApiProvider provider){
        return new ApiManager(provider);
    }

}
