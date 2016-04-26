package com.ruigoncalo.marvin.api;

import com.ruigoncalo.marvin.model.raw.Characters;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by ruigoncalo on 21/04/16.
 */
public interface MarvelApiService {

    @GET(Endpoints.CHARACTERS)
    Call<Characters> getCharacters(@QueryMap Map<String, String> params);

    @GET(Endpoints.CHARACTERS_ID)
    Call<Characters> getCharacterProfile(@Path("id") String id);

}
