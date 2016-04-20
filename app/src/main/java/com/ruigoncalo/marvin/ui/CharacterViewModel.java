package com.ruigoncalo.marvin.ui;

/**
 * Created by ruigoncalo on 20/04/16.
 */
public class CharacterViewModel {

    private final String id;
    private final String title;
    private final String imageUrl;

    public CharacterViewModel() {
        this(new Builder());
    }

    private CharacterViewModel(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.imageUrl = builder.imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static class Builder {
        private String id = "";
        private String title = "";
        private String imageUrl = "";

        public Builder id(String id){
            this.id = id;
            return this;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder imageUrl(String imageUrl){
            this.imageUrl = imageUrl;
            return this;
        }


        public CharacterViewModel build() {
            return new CharacterViewModel(this);
        }
    }
}
