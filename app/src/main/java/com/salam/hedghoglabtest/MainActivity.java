package com.salam.hedghoglabtest;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.salam.hedghoglabtest.Adapter.NewMoviePosterAdapter;
import com.salam.hedghoglabtest.DatabaseRoom.MoviesListViewModel;
import com.salam.hedghoglabtest.DatabaseRoom.MoviesModel;
import com.salam.hedghoglabtest.InternetState.InternetState;
import com.salam.hedghoglabtest.model.PosterParceable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author Muhammad AbdulSalam
 * MainActivty to load movies from MOVIEDB API
 * Updates the Movie List From Menu Selection
 * Select Top Rated or Most Popular
 * See the list of your saved videos
 */


public class MainActivity extends AppCompatActivity {

    // to load movies info from API
    private List<PosterParceable> moviePosters2 = new ArrayList<>();

    //To save current list in saved state
    private List<PosterParceable> moviePosters3 = new ArrayList<>();

    //to pass and check for savedState
    String LIST_STATE_KEY = "Pass";

    //update Recycler View
    private RecyclerView recyclerView;
    private NewMoviePosterAdapter newADB;
    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //if internet is connected then show the list and check for instant states
        if (InternetState.isAvailable(MainActivity.this)) {
            moviePosters2.clear();
            recyclerView = findViewById(R.id.recyclerView);
            newADB = new NewMoviePosterAdapter(MainActivity.this, moviePosters2);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            if(savedInstanceState == null) {
                ShowVideoList();
            }
            if (savedInstanceState != null) {
                moviePosters2.addAll(Objects.requireNonNull(savedInstanceState.<PosterParceable>getParcelableArrayList(LIST_STATE_KEY)));
                recyclerView.setAdapter(newADB);
                newADB.notifyDataSetChanged();
            }

        //if no internet then show error message
        }else{
            Toast.makeText(getApplicationContext(), "Please Connect To Internet", Toast.LENGTH_LONG).show();
        }
    }


    //MovieList by popularity
    private void ShowVideoList() {
        //API request to get Vidio List by popularity as stated in API documentation
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(getString(R.string.https))
                .authority(getString(R.string.Host))
                .appendPath("3")
                .appendPath(getString(R.string.movie))
                .appendPath(getString(R.string.popular_txt))
                .appendQueryParameter(getString(R.string.api_key_t), getString(R.string.API_KEY))
                .appendQueryParameter(getString(R.string.language), getString(R.string.en_US))
                .appendQueryParameter(getString(R.string.page), "1");
        String url2 = builder.toString();
        RequestQueue Video_Queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //get JsonObject and save the value to movieList
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(getString(R.string.results));
                    for(int i =0; i<20; i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        PosterParceable VL = new PosterParceable(jsonObject1.getString(getString(R.string.ID_TXT)),jsonObject1.getString(getString(R.string.poster_path)));
                        VL.setId(jsonObject1.getString(getString(R.string.ID_TXT)));
                        VL.setURL(jsonObject1.getString(getString(R.string.poster_path)));
                        moviePosters2.add(VL);
                        recyclerView.setAdapter(newADB);
                    }
                    newADB.notifyDataSetChanged();
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
            }
        });
        Video_Queue.add(stringRequest);
    }

    //background query for top rated
    private void ShowVideoListTopRated() {
        //get the top rated movies by making r
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(getString(R.string.https))
                .authority(getString(R.string.Host))
                .appendPath("3")
                .appendPath(getString(R.string.movie))
                .appendPath(getString(R.string.top_rated_txt))
                .appendQueryParameter(getString(R.string.api_key_t), getString(R.string.API_KEY))
                .appendQueryParameter(getString(R.string.language), getString(R.string.en_US))
                .appendQueryParameter(getString(R.string.page), "1");
        String url2 = builder.toString();
        RequestQueue Video_Queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //load data of json object into the recycler list
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(getString(R.string.results));
                    for(int i =0; i<20; i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        PosterParceable VL = new PosterParceable(jsonObject1.getString(getString(R.string.ID_TXT)),jsonObject1.getString(getString(R.string.poster_path)));
                        VL.setId(jsonObject1.getString(getString(R.string.ID_TXT)));
                        VL.setURL(jsonObject1.getString(getString(R.string.poster_path)));
                        moviePosters2.add(VL);
                        recyclerView.setAdapter(newADB);
                    }
                    newADB.notifyDataSetChanged();
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
            }
        });
        Video_Queue.add(stringRequest);
    }

    //Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    //item selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.popular) {
            //clear list to store new data otherwise list will be added to previous list
            moviePosters2.clear();
            ShowVideoList();
            return true;
        }
        if (id == R.id.top_rated) {
            //clear list to store new data otherwise list will be added to previous list
            moviePosters2.clear();
            BackgroundTask backgroundTask = new BackgroundTask();
            backgroundTask.execute();
            return true;
        }
        if (id == R.id.Fav_menu){

            onFavSelected();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Background query
    @SuppressLint("StaticFieldLeak")
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        BackgroundTask() {
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(Void... params) {
            moviePosters2.clear();
            //run the method in background so delays wont cause UI to cause ANR
            ShowVideoListTopRated();
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
        }
    }


    public void updateUI() {
        newADB.notifyDataSetChanged();
    }


    //on Fav Movies Selected
    private void onFavSelected () {
        //MVVM model to view favriote movies so it wont be loaded everytime user goes to fav list
        MoviesListViewModel viewModel = ViewModelProviders.of(this).get(MoviesListViewModel.class);

        //obser to observe data change in the current list, makes loading efficient
        viewModel.getAllMovies().observe(this, new Observer<List<MoviesModel>>() {
            @Override
            public void onChanged(@Nullable List<MoviesModel> Movies) {
                moviePosters2.clear();
                assert Movies != null;
                for(int i = 0; i<Movies.size(); i++){
                    PosterParceable VL = new PosterParceable((Movies.get(i).getVideoid()),Movies.get(i).getPoster_path());
                    VL.setId(Movies.get(i).getVideoid());
                    VL.setURL(Movies.get(i).getPoster_path());
                    moviePosters2.add(VL);
                    recyclerView.setAdapter(newADB);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI();
                    }
                });
            }
        });
    }

    //States change information
    protected void onSaveInstanceState(Bundle state) {
        //save current state of list so it can be repopulated once user comes back from details activity,
        //will bring user to the list he had seen last, i.e most popular or top rated
        super.onSaveInstanceState(state);
        state.putParcelableArrayList(LIST_STATE_KEY, (ArrayList<? extends Parcelable>) moviePosters2);

    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

    }

    @SuppressLint("Assert")
    @Override
    protected void onStart() {
        super.onStart();
        //if movieposters are null then load from  saved state list.
        if (moviePosters3 == null){
            assert false;
            moviePosters2.addAll(moviePosters3);
        }
    }

    @Override
    protected void onResume() {
        //if movieposters are null then load from  saved state list.
        super.onResume();
        if (moviePosters3 == null){
            moviePosters2.addAll(moviePosters3);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //if movieposters are null then load from  saved state list.
        moviePosters3 = moviePosters2;
    }


}
