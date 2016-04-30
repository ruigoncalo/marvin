package com.ruigoncalo.marvin.model.viewmodel;

/**
 * Created by ruigoncalo on 25/04/16.
 */
public class ProfileViewModel {

    private final int id;
    private final String name;
    private final String imageUrl;
    private final String description;
    private final String detail;
    private final String wiki;
    private final String comicLink;

    public ProfileViewModel() {
        this(new Builder());
    }

    private ProfileViewModel(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.imageUrl = builder.imageUrl;
        this.description = builder.description;
        this.detail = builder.detail;
        this.wiki = builder.wiki;
        this.comicLink = builder.comicLink;
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

    public String getDetail() {
        return detail;
    }

    public String getWiki() {
        return wiki;
    }

    public String getComicLink() {
        return comicLink;
    }

    public static class Builder {
        private int id;
        private String name = "";
        private String imageUrl = "";
        private String description = "";
        private String detail = "";
        private String wiki = "";
        private String comicLink = "";

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

        public Builder detail(String detail){
            this.detail = detail;
            return this;
        }

        public Builder wiki(String wiki){
            this.wiki = wiki;
            return this;
        }

        public Builder comicLink(String comicLink){
            this.comicLink = comicLink;
            return this;
        }

        public ProfileViewModel build() {
            return new ProfileViewModel(this);
        }
    }

}
