package com.ruigoncalo.marvin.api;

import com.ruigoncalo.marvin.model.raw.Characters;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ruigoncalo on 21/04/16.
 */
public interface MarvelApiService {

    @GET(Endpoints.CHARACTERS)
    Call<Characters> getCharacters();

}
