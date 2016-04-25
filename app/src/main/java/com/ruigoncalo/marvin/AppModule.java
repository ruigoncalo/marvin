package com.ruigoncalo.marvin;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ruigoncalo on 23/04/16.
 */

@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Application provideApplication(){
        return app;
    }

    @Provides
    public Context provideApplicationContext(){
        return app.getApplicationContext();
    }
}
