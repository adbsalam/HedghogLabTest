package com.salam.hedghoglabtest.DatabaseRoom;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class MoviesModel {
        @PrimaryKey()
        @NonNull
        private String videoid;
        private String title;
        private String fav;
        private String poster_path;

    public String getVideoid() {
        return videoid;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public MoviesModel(String videoid, String title, String fav, String poster_path) {
        this.videoid = videoid;
        this.title = title;
        this.fav = fav;
        this.poster_path = poster_path;


    }








}
