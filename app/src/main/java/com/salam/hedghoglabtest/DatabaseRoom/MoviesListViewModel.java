package com.salam.hedghoglabtest.DatabaseRoom;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class MoviesListViewModel extends AndroidViewModel {

    //using Live Data to populate fav recyclerview
    private final LiveData<List<MoviesModel>> AllMoviesList;

    private DatabaseMovies appDatabase;

    public MoviesListViewModel(Application application) {
        super(application);

        appDatabase = DatabaseMovies.getDatabase(this.getApplication());

        AllMoviesList = appDatabase.ItemsModel().getAllMovies();
    }


    public LiveData<List<MoviesModel>> getAllMovies() {
        return AllMoviesList;
    }

    public void deleteItem(MoviesModel borrowModel) {
        new deleteAsyncTask(appDatabase).execute(borrowModel);
    }

    private static class deleteAsyncTask extends AsyncTask<MoviesModel, Void, Void> {

        private DatabaseMovies db;

        deleteAsyncTask(DatabaseMovies appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final MoviesModel... params) {
            db.ItemsModel().deleteBorrow(params[0]);
            return null;
        }

    }

}
