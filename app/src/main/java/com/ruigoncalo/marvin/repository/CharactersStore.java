package com.ruigoncalo.marvin.repository;

import android.support.annotation.Nullable;

import com.ruigoncalo.marvin.api.ApiManager;
import com.ruigoncalo.marvin.api.Endpoints;
import com.ruigoncalo.marvin.bus.CharacterEvent;
import com.ruigoncalo.marvin.bus.CharactersResultErrorEvent;
import com.ruigoncalo.marvin.bus.CharactersResultEvent;
import com.ruigoncalo.marvin.bus.SearchResultErrorEvent;
import com.ruigoncalo.marvin.bus.SearchResultEvent;
import com.ruigoncalo.marvin.model.raw.Character;
import com.ruigoncalo.marvin.model.raw.Characters;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ruigoncalo on 24/04/16.
 */
public class CharactersStore {

    public static final String CHARACTER_ID = "character-id";

    private ApiManager apiManager;

    // save characters in memory
    // this can be updated to a database storage, for instance
    // TODO: limit capacity
    private Map<Integer, Character> charactersRepo;

    public CharactersStore(ApiManager apiManager) {
        this.apiManager = apiManager;
        charactersRepo = new HashMap<>();
    }

    /**
     * Request list of characters according to query string
     *
     * @param query search query
     */
    public void searchCharacters(String query){
        Map<String, String> params = new HashMap<>();
        params.put(Endpoints.PARAM_NAME_STARTS_WITH, query);
        params.put(Endpoints.PARAM_LIMIT, "10"); // limit to 10 results to reduce response size

        apiManager.getCharacters(params, new Callback<Characters>() {
            @Override
            public void onResponse(Call<Characters> call, Response<Characters> response) {
                if (response.body() != null &&
                        response.body().getData() != null &&
                        response.body().getData().getCharacters() != null) {

                    SearchResultEvent event =
                            new SearchResultEvent(response.body().getData().getCharacters());
                    EventBus.getDefault().post(event);
                } else {
                    EventBus.getDefault().post(new SearchResultErrorEvent("Invalid data"));
                }
            }

            @Override
            public void onFailure(Call<Characters> call, Throwable t) {
                EventBus.getDefault().post(new SearchResultErrorEvent("Could not get data from server"));
            }
        });
    }

    /**
     * Request list of characters from ApiManager
     */
    public void getList(@Nullable Map<String, String> params) {
        if(params == null){
            params = new HashMap<>();
        }

        apiManager.getCharacters(params, new Callback<Characters>() {
            @Override
            public void onResponse(Call<Characters> call, Response<Characters> response) {
                if (response.body() != null &&
                        response.body().getData() != null &&
                        response.body().getData().getCharacters() != null) {
                    List<Character> list = response.body().getData().getCharacters();
                    saveListToRepo(list);
                    CharactersResultEvent event = new CharactersResultEvent(list);
                    EventBus.getDefault().post(event);
                } else {
                    EventBus.getDefault().post(new CharactersResultErrorEvent("Invalid data"));
                }
            }

            @Override
            public void onFailure(Call<Characters> call, Throwable t) {
                EventBus.getDefault().post(new CharactersResultErrorEvent("Could not get data from server"));
            }
        });
    }

    public void getCharacterProfile(int id){
        if(charactersRepo.containsKey(id)){
            EventBus.getDefault().post(new CharacterEvent(charactersRepo.get(id)));
        } else {
            // fetch from server
            apiManager.getCharacterProfile(String.valueOf(id), new Callback<Characters>() {
                @Override
                public void onResponse(Call<Characters> call, Response<Characters> response) {
                    if(response.body() != null &&
                            response.body().getData() != null &&
                            response.body().getData().getCharacters() != null){
                        List<Character> list = response.body().getData().getCharacters();
                        if(list.size() == 1) {
                            Character character = list.get(0);
                            addToRepo(character);
                            CharacterEvent event = new CharacterEvent(character);
                            EventBus.getDefault().post(event);
                        }
                    } else {
                        // post error event
                    }
                }

                @Override
                public void onFailure(Call<Characters> call, Throwable t) {
                    // post error event
                }
            });
        }
    }

    // repo modifiers

    private void saveListToRepo(List<Character> list){
        for (Character character : list) {
            addToRepo(character);
        }
    }

    private void addToRepo(Character character){
        charactersRepo.put(character.getId(), character);
    }

    private void removeFromRepo(int id){
        charactersRepo.remove(id);
    }
}
