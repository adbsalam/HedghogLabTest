package com.salam.hedghoglabtest.DatabaseRoom;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {MoviesModel.class}, version = 1, exportSchema = false)
public abstract class DatabaseMovies extends RoomDatabase {

    private static DatabaseMovies INSTANCE;

    public static DatabaseMovies getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), DatabaseMovies.class, "fav_new")
                            .build();
        }
        return INSTANCE;
    }

    public abstract DAO ItemsModel();

}