package com.ruigoncalo.marvin.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ruigoncalo.marvin.R;
import com.ruigoncalo.marvin.model.raw.Characters;
import com.ruigoncalo.marvin.model.raw.CollectionItem;
import com.ruigoncalo.marvin.utils.Utils;

import java.io.IOException;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ruigoncalo on 21/04/16.
 */

public class ApiManager {

    public static final String QUERY_PARAM_TS = "ts";
    public static final String QUERY_PARAM_APIKEY = "apikey";
    public static final String QUERY_PARAM_HASH = "hash";

    private MarvelApiService marvelApiService;
    private Context context;

    public ApiManager(Context context) {
        this.context = context;
        Retrofit retrofit = build(Endpoints.BASE_URL);
        marvelApiService = retrofit.create(MarvelApiService.class);
    }

    public MarvelApiService getMarvelApiService(){
        return marvelApiService;
    }

    private Retrofit build(String baseUrl) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(getAuthQueryParams())
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private Interceptor getAuthQueryParams() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                String[] params = generateAuthParams();

                HttpUrl url = request
                        .url()
                        .newBuilder()
                        .addQueryParameter(QUERY_PARAM_TS, params[0])
                        .addQueryParameter(QUERY_PARAM_APIKEY, params[1])
                        .addQueryParameter(QUERY_PARAM_HASH, params[2])
                        .build();

                request = request
                        .newBuilder()
                        .url(url)
                        .build();

                return chain.proceed(request);
            }
        };
    }

    private String[] generateAuthParams() {
        String ts = generateTs();
        String apiKey = context.getString(R.string.api_public_key);
        String prvtKey = context.getString(R.string.api_private_key);
        String hash = generateHash(ts + prvtKey + apiKey);

        return new String[]{ts, apiKey, hash};
    }

    private String generateTs() {
        return String.valueOf(Utils.getTimestamp());
    }

    private String generateHash(String raw) {
        return Utils.md5(raw);
    }

    public void getCharacters(@NonNull Map<String, String> map, Callback<Characters> callback){
        Call<Characters> call = marvelApiService.getCharacters(map);
        call.enqueue(callback);
    }

    public void getCharacterProfile(@NonNull String id, Callback<Characters> callback){
        Call<Characters> call = marvelApiService.getCharacterProfile(id);
        call.enqueue(callback);
    }

    public void getCharacterComics(@NonNull String id, Callback<CollectionItem> callback){
        Call<CollectionItem> call = marvelApiService.getCharacterComics(id);
        call.enqueue(callback);
    }

    public void getCharacterSeries(@NonNull String id, Callback<CollectionItem> callback){
        Call<CollectionItem> call = marvelApiService.getCharacterSeries(id);
        call.enqueue(callback);
    }

    public void getCharacterStories(@NonNull String id, Callback<CollectionItem> callback){
        Call<CollectionItem> call = marvelApiService.getCharacterStories(id);
        call.enqueue(callback);
    }

    public void getCharacterEvents(@NonNull String id, Callback<CollectionItem> callback){
        Call<CollectionItem> call = marvelApiService.getCharacterEvents(id);
        call.enqueue(callback);
    }
}
