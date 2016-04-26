package com.ruigoncalo.marvin.ui.profiles;

import com.ruigoncalo.marvin.AppComponent;
import com.ruigoncalo.marvin.ui.ActivityScope;

import dagger.Component;

/**
 * Created by ruigoncalo on 26/04/16.
 */

@ActivityScope
@Component(dependencies = AppComponent.class,
        modules = ProfilesModule.class)
public interface ProfilesComponent {
    void inject(ProfileActivity activity);

    ProfilePresenter getPresenter();
}
