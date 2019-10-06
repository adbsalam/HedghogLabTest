package com.salam.hedghoglabtest.DatabaseRoom;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DAO {
    //SELECT ALL MOVIES
    @Query("select * from MoviesModel")
    LiveData<List<MoviesModel>> getAllMovies();

    //SELECT BY ID
    @Query("select * from MoviesModel where videoid = :id")
    MoviesModel getItembyId(String id);

    //REPLACE IN CASE BUT NOT USED
    @Insert(onConflict = REPLACE)
    void addMovie(MoviesModel borrowModel);

    //DELETE A MOVIE
    @Delete
    void deleteBorrow(MoviesModel borrowModel);

}