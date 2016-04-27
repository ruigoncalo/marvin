package com.ruigoncalo.marvin.model.viewmodel;

import com.ruigoncalo.marvin.ui.profiles.CollectionViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruigoncalo on 25/04/16.
 */
public class ProfileViewModel {

    private final int id;
    private final String name;
    private final String imageUrl;
    private final String description;
    private final List<CollectionViewModel> comics;
    private final List<CollectionViewModel> series;
    private final List<CollectionViewModel> stories;
    private final List<CollectionViewModel> events;

    public ProfileViewModel() {
        this(new Builder());
    }

    private ProfileViewModel(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.imageUrl = builder.imageUrl;
        this.description = builder.description;
        this.comics = builder.comics;
        this.series = builder.series;
        this.stories = builder.stories;
        this.events = builder.events;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public List<CollectionViewModel> getComics() {
        return comics;
    }

    public List<CollectionViewModel> getSeries() {
        return series;
    }

    public List<CollectionViewModel> getStories() {
        return stories;
    }

    public List<CollectionViewModel> getEvents() {
        return events;
    }

    public static class Builder {
        private int id;
        private String name = "";
        private String imageUrl = "";
        private String description = "";
        private List<CollectionViewModel> comics = new ArrayList<>();
        private List<CollectionViewModel> series = new ArrayList<>();
        private List<CollectionViewModel> stories = new ArrayList<>();
        private List<CollectionViewModel> events = new ArrayList<>();

        public Builder id(int id){
            this.id = id;
            return this;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder imageUrl(String imageUrl){
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder comics(List<CollectionViewModel> comics){
            this.comics = comics;
            return this;
        }

        public Builder series(List<CollectionViewModel> series){
            this.series = series;
            return this;
        }

        public Builder stories(List<CollectionViewModel> stories){
            this.stories = stories;
            return this;
        }

        public Builder events(List<CollectionViewModel> events){
            this.events = events;
            return this;
        }

        public ProfileViewModel build() {
            return new ProfileViewModel(this);
        }
    }

}
