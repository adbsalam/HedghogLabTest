package com.salam.hedghoglabtest.DatabaseRoom;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

public class AddMoviesListViewModel extends AndroidViewModel {

    private DatabaseMovies appDatabase;

    public AddMoviesListViewModel(Application application) {
        super(application);

        appDatabase = DatabaseMovies.getDatabase(this.getApplication());

    }

    public void addMovie(final MoviesModel borrowModel) {
        new addAsyncTask(appDatabase).execute(borrowModel);
    }

    private static class addAsyncTask extends AsyncTask<MoviesModel, Void, Void> {

        private DatabaseMovies db;

        addAsyncTask(DatabaseMovies appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final MoviesModel... params) {
            db.ItemsModel().addMovie(params[0]);
            return null;
        }

    }
}