package com.ruigoncalo.marvin.repository;

import android.support.annotation.Nullable;

import com.ruigoncalo.marvin.api.ApiManager;
import com.ruigoncalo.marvin.api.Endpoints;
import com.ruigoncalo.marvin.bus.CharacterComicsEvent;
import com.ruigoncalo.marvin.bus.CharacterProfileEvent;
import com.ruigoncalo.marvin.bus.CharacterEventsEvent;
import com.ruigoncalo.marvin.bus.CharacterSeriesEvent;
import com.ruigoncalo.marvin.bus.CharacterStoriesEvent;
import com.ruigoncalo.marvin.bus.CharactersMoreResultsErrorEvent;
import com.ruigoncalo.marvin.bus.CharactersMoreResultsEvent;
import com.ruigoncalo.marvin.bus.CharactersResultErrorEvent;
import com.ruigoncalo.marvin.bus.CharactersResultEvent;
import com.ruigoncalo.marvin.bus.SearchResultErrorEvent;
import com.ruigoncalo.marvin.bus.SearchResultEvent;
import com.ruigoncalo.marvin.model.raw.Character;
import com.ruigoncalo.marvin.model.raw.Characters;
import com.ruigoncalo.marvin.model.raw.CollectionItem;
import com.ruigoncalo.marvin.utils.Errors;

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

    private static final int ITEMS_PER_PAGE = 20;

    private ApiManager apiManager;

    // save characters in memory
    // this can be replaced by database storage
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
    public void searchCharacters(String query) {
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
                    EventBus.getDefault().post(new SearchResultErrorEvent(Errors.ERROR_INVALID_DATA));
                }
            }

            @Override
            public void onFailure(Call<Characters> call, Throwable t) {
                EventBus.getDefault().post(new SearchResultErrorEvent(Errors.ERROR_SERVER));
            }
        });
    }

    /**
     * Request list of characters from ApiManager
     */
    public void getCharacters(@Nullable Map<String, String> params) {
        if (params == null) {
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
                    EventBus.getDefault().post(new CharactersResultErrorEvent(Errors.ERROR_INVALID_DATA));
                }
            }

            @Override
            public void onFailure(Call<Characters> call, Throwable t) {
                EventBus.getDefault().post(new CharactersResultErrorEvent(Errors.ERROR_SERVER));
            }
        });
    }

    public void getMoreCharacters(int page){
        Map<String, String> params = new HashMap<>();
        int offsetValue = page * ITEMS_PER_PAGE;
        params.put(Endpoints.PARAM_OFFSET, String.valueOf(offsetValue));

        apiManager.getCharacters(params, new Callback<Characters>() {
            @Override
            public void onResponse(Call<Characters> call, Response<Characters> response) {
                if (response.body() != null &&
                        response.body().getData() != null &&
                        response.body().getData().getCharacters() != null) {
                    List<Character> list = response.body().getData().getCharacters();
                    saveListToRepo(list);
                    CharactersMoreResultsEvent event = new CharactersMoreResultsEvent(list);
                    EventBus.getDefault().post(event);
                } else {
                    EventBus.getDefault().post(new CharactersMoreResultsErrorEvent(Errors.ERROR_INVALID_DATA));
                }
            }

            @Override
            public void onFailure(Call<Characters> call, Throwable t) {
                EventBus.getDefault().post(new CharactersMoreResultsErrorEvent(Errors.ERROR_SERVER));
            }
        });
    }

    /**
     * Request a single character from ApiManager
     * @param id character id
     */
    public void getCharacterProfile(int id) {
        if (charactersRepo.containsKey(id)) {
            EventBus.getDefault().post(new CharacterProfileEvent(charactersRepo.get(id)));
        } else {
            // fetch from server
            apiManager.getCharacterProfile(String.valueOf(id), new Callback<Characters>() {
                @Override
                public void onResponse(Call<Characters> call, Response<Characters> response) {
                    if (response.body() != null &&
                            response.body().getData() != null &&
                            response.body().getData().getCharacters() != null) {
                        List<Character> list = response.body().getData().getCharacters();
                        if (list.size() == 1) {
                            Character character = list.get(0);
                            addToRepo(character);
                            CharacterProfileEvent event = new CharacterProfileEvent(character);
                            EventBus.getDefault().post(event);
                        }
                    } else {
                        EventBus.getDefault().post(new CharactersResultErrorEvent(Errors.ERROR_INVALID_DATA));
                    }
                }

                @Override
                public void onFailure(Call<Characters> call, Throwable t) {
                    EventBus.getDefault().post(new CharactersResultErrorEvent(Errors.ERROR_SERVER));
                }
            });
        }
    }

    public void getCharacterComics(int id) {
        apiManager.getCharacterComics(String.valueOf(id), new Callback<CollectionItem>() {
            @Override
            public void onResponse(Call<CollectionItem> call, Response<CollectionItem> response) {
                if (response.body() != null && response.body().getData() != null) {
                    EventBus.getDefault().post(new CharacterComicsEvent(response.body().getData()));
                } else {
                    EventBus.getDefault().post(new CharactersResultErrorEvent(Errors.ERROR_INVALID_DATA));
                }
            }

            @Override
            public void onFailure(Call<CollectionItem> call, Throwable t) {
                EventBus.getDefault().post(new CharactersResultErrorEvent(Errors.ERROR_SERVER));
            }
        });
    }

    public void getCharacterSeries(int id) {
        apiManager.getCharacterSeries(String.valueOf(id), new Callback<CollectionItem>() {
            @Override
            public void onResponse(Call<CollectionItem> call, Response<CollectionItem> response) {
                if (response.body() != null && response.body().getData() != null) {
                    EventBus.getDefault().post(new CharacterSeriesEvent(response.body().getData()));
                } else {
                    EventBus.getDefault().post(new CharactersResultErrorEvent(Errors.ERROR_INVALID_DATA));
                }
            }

            @Override
            public void onFailure(Call<CollectionItem> call, Throwable t) {
                EventBus.getDefault().post(new CharactersResultErrorEvent(Errors.ERROR_SERVER));
            }
        });
    }

    public void getCharacterStories(int id) {
        apiManager.getCharacterStories(String.valueOf(id), new Callback<CollectionItem>() {
            @Override
            public void onResponse(Call<CollectionItem> call, Response<CollectionItem> response) {
                if (response.body() != null && response.body().getData() != null) {
                    EventBus.getDefault().post(new CharacterStoriesEvent(response.body().getData()));
                } else {
                    EventBus.getDefault().post(new CharactersResultErrorEvent(Errors.ERROR_INVALID_DATA));
                }
            }

            @Override
            public void onFailure(Call<CollectionItem> call, Throwable t) {
                EventBus.getDefault().post(new CharactersResultErrorEvent(Errors.ERROR_SERVER));
            }
        });
    }

    public void getCharacterEvents(int id) {
        apiManager.getCharacterEvents(String.valueOf(id), new Callback<CollectionItem>() {
            @Override
            public void onResponse(Call<CollectionItem> call, Response<CollectionItem> response) {
                if (response.body() != null && response.body().getData() != null) {
                    EventBus.getDefault().post(new CharacterEventsEvent(response.body().getData()));
                } else {
                    EventBus.getDefault().post(new CharactersResultErrorEvent(Errors.ERROR_INVALID_DATA));
                }
            }

            @Override
            public void onFailure(Call<CollectionItem> call, Throwable t) {
                EventBus.getDefault().post(new CharactersResultErrorEvent(Errors.ERROR_SERVER));
            }
        });
    }

    // repo modifiers

    private void saveListToRepo(List<Character> list) {
        for (Character character : list) {
            addToRepo(character);
        }
    }

    private void addToRepo(Character character) {
        charactersRepo.put(character.getId(), character);
    }

    private void removeFromRepo(int id) {
        charactersRepo.remove(id);
    }
}
