package com.ruigoncalo.marvin.ui.characters;

import com.ruigoncalo.marvin.repository.CharactersStore;
import com.ruigoncalo.marvin.model.raw.Character;
import com.ruigoncalo.marvin.utils.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruigoncalo on 24/04/16.
 */
public class CharactersPresenter implements Callback<List<Character>>{

    private CharactersView view;
    private CharactersStore store;

    public CharactersPresenter(CharactersView view, CharactersStore store) {
        this.view = view;
        this.store = store;
    }

    public void getItems(){
        view.isLoading(true);
        store.getList(this);
    }

    @Override
    public void onSuccess(List<Character> characters) {
        List<CharacterViewModel> viewModels = new ArrayList<>();
        for (Character character : characters) {
            CharacterViewModel viewModel = createViewModel(character);
            viewModels.add(viewModel);
        }

        view.showCharacters(viewModels);
        view.isLoading(false);
    }

    @Override
    public void onFailure(String message) {

    }

    private CharacterViewModel createViewModel(Character character){
        return new CharacterViewModel.Builder()
                .id(character.getId())
                .title(character.getName())
                .imageUrl(character.getThumbnail().getPath())
                .build();
    }
}