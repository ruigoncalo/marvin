package com.ruigoncalo.marvin.ui.profiles;

import com.ruigoncalo.marvin.model.viewmodel.ProfileViewModel;

import java.util.List;

/**
 * Created by ruigoncalo on 25/04/16.
 */
public interface ProfileView {
    void showCharacterProfile(ProfileViewModel profile);
    void showCharacterProfileError(String message);
    void isLoadingProfile(boolean loading);
    void showCharacterComics(List<CollectionViewModel> items);
    void showCharacterComicsError(String message);
    void isLoadingCharacterComics(boolean loading);
    void showCharacterSeries(List<CollectionViewModel> items);
    void showCharacterSeriesError(String message);
    void isLoadingCharacterSeries(boolean loading);
    void showCharacterStories(List<CollectionViewModel> items);
    void showCharacterStoriesError(String message);
    void isLoadingCharacterStories(boolean loading);
    void showCharacterEvents(List<CollectionViewModel> items);
    void showCharacterEventsError(String message);
    void isLoadingCharacterEvents(boolean loading);
}
