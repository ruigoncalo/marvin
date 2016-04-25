package com.ruigoncalo.marvin.ui.characters;

import java.util.List;

/**
 * Created by ruigoncalo on 24/04/16.
 */
public interface CharactersView {
    void isLoading(boolean loading);
    void showCharacters(List<CharacterViewModel> list);
}
