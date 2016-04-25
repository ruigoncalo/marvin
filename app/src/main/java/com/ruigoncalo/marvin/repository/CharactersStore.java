package com.ruigoncalo.marvin.repository;

import com.ruigoncalo.marvin.api.ApiManager;
import com.ruigoncalo.marvin.model.raw.Character;
import com.ruigoncalo.marvin.model.raw.Characters;
import com.ruigoncalo.marvin.utils.Callback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ruigoncalo on 24/04/16.
 */
public class CharactersStore {

    private ApiManager apiManager;

    private List<Character> list;

    public CharactersStore(ApiManager apiManager) {
        this.apiManager = apiManager;
        this.list = new ArrayList<>();
    }

    public void getList(final Callback<List<Character>> callback) {
        apiManager.getCharacters(new retrofit2.Callback<Characters>() {
            @Override
            public void onResponse(Call<Characters> call, Response<Characters> response) {
                callback.onSuccess(response.body().getData().getCharacters());
            }

            @Override
            public void onFailure(Call<Characters> call, Throwable t) {
                callback.onFailure("Error");
            }
        });
    }
}
