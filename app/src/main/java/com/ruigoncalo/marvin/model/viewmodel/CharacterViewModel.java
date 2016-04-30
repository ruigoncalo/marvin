package com.ruigoncalo.marvin.model.viewmodel;

/**
 * Created by ruigoncalo on 20/04/16.
 */
public class CharacterViewModel {

    private final int id;
    private final String title;
    private final String imageUrl;
    private final boolean isLoader;

    public CharacterViewModel() {
        this(new Builder());
    }

    private CharacterViewModel(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.imageUrl = builder.imageUrl;
        this.isLoader = builder.isLoader;
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

    public boolean isLoader() {
        return isLoader;
    }

    public static class Builder {
        private int id;
        private String title = "";
        private String imageUrl = "";
        private boolean isLoader;

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

        public Builder isLoader(boolean isLoader){
            this.isLoader = isLoader;
            return this;
        }

        public CharacterViewModel build() {
            return new CharacterViewModel(this);
        }
    }
}
