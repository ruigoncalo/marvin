package com.ruigoncalo.marvin.model.raw;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruigoncalo on 21/04/16.
 */
public class Character {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("modified")
    @Expose
    private String modified;

    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;

    @SerializedName("resourceURI")
    @Expose
    private String resourceURI;

    @SerializedName("comics")
    @Expose
    private Comics comics;

    @SerializedName("series")
    @Expose
    private Series series;

    @SerializedName("stories")
    @Expose
    private Stories stories;

    @SerializedName("events")
    @Expose
    private Events events;

    @SerializedName("urls")
    @Expose
    private List<Url> urls = new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getModified() {
        return modified;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public Comics getComics() {
        return comics;
    }

    public Series getSeries() {
        return series;
    }

    public Stories getStories() {
        return stories;
    }

    public Events getEvents() {
        return events;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public void setComics(Comics comics) {
        this.comics = comics;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public void setStories(Stories stories) {
        this.stories = stories;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }
}
