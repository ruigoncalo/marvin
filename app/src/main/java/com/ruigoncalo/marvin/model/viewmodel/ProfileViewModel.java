package com.ruigoncalo.marvin.model.viewmodel;

/**
 * Created by ruigoncalo on 25/04/16.
 */
public class ProfileViewModel {

    private final int id;
    private final String name;
    private final String imageUrl;
    private final String description;

    public ProfileViewModel() {
        this(new Builder());
    }

    private ProfileViewModel(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.imageUrl = builder.imageUrl;
        this.description = builder.description;
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

    public static class Builder {
        private int id;
        private String name = "";
        private String imageUrl = "";
        private String description = "";

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

        public ProfileViewModel build() {
            return new ProfileViewModel(this);
        }
    }

}
