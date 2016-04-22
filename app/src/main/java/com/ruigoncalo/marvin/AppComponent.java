package com.ruigoncalo.marvin;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ruigoncalo on 23/04/16.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(App app);
}