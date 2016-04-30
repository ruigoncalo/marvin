package com.ruigoncalo.marvin.ui.characters;

import com.ruigoncalo.marvin.model.viewmodel.CharacterViewModel;

import java.util.List;

/**
 * Created by ruigoncalo on 24/04/16.
 */
public interface CharactersView {
    void isLoadingCharacters(boolean loading);
    void showCharacters(List<CharacterViewModel> list);
    void showCharactersError(String message);
    void isLoadingMoreCharacters(boolean loading);
    void showMoreCharacters(List<CharacterViewModel> list);
    void showMoreCharactersError(String message);
    void isLoadingResults(boolean loading);
    void showSearchResults(List<CharacterViewModel> list);
    void showSearchResultsError(String message);
}
