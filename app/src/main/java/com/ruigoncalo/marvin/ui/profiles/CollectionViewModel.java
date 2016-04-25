package com.ruigoncalo.marvin.ui.profiles;

/**
 * Created by ruigoncalo on 25/04/16.
 */
public class CollectionViewModel {

    private final int id;
    private final String title;
    private final String imageUrl;

    public CollectionViewModel() {
        this(new Builder());
    }

    private CollectionViewModel(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.imageUrl = builder.imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static class Builder {
        private int id;
        private String title = "";
        private String imageUrl = "";

        public Builder id(int id){
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


        public CollectionViewModel build() {
            return new CollectionViewModel(this);
        }
    }
}
