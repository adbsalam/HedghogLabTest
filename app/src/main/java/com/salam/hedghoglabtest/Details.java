package com.salam.hedghoglabtest;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.salam.hedghoglabtest.Adapter.ReviewsAdapter;
import com.salam.hedghoglabtest.Adapter.TrailersAdapter;
import com.salam.hedghoglabtest.DatabaseRoom.AddMoviesListViewModel;
import com.salam.hedghoglabtest.DatabaseRoom.DAO;
import com.salam.hedghoglabtest.DatabaseRoom.DatabaseMovies;
import com.salam.hedghoglabtest.DatabaseRoom.MoviesListViewModel;
import com.salam.hedghoglabtest.DatabaseRoom.MoviesModel;
import com.salam.hedghoglabtest.model.ReviewModel;
import com.salam.hedghoglabtest.model.TrailerMode;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Details Activity Lists details of Movie
 * Click trailer to see trailes in youtube app
 * Requests are made using Volley Library
 * Add to favorites using fav/unfav button
 */

public class Details extends AppCompatActivity {

    private TextView title, overview, rating, releasedate;
    private String videoID;
    private ImageView thumbnail;
    private String imageurl = "";
    private String saveimageurl = "";
    RelativeLayout Favirote_button;
    RelativeLayout unFavirote_button;

    //Recycler Trailer
    private List<TrailerMode> trailersList = new ArrayList<>();
    RecyclerView trailerRecycler;
    private TrailersAdapter mAdapter;

    //Recycler Reviews
    private List<ReviewModel> reviewList = new ArrayList<>();
    RecyclerView reviewRecycler;
    private ReviewsAdapter rAdapter;

    private AddMoviesListViewModel addMovietoFavs;
    private MoviesListViewModel addMovietoFavs2;

    //LORD SAID CREATE AND THY WAS CREATED
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        title = findViewById(R.id.movie_title);
        overview = findViewById(R.id.overview);
        rating = findViewById(R.id.vote_average);
        releasedate = findViewById(R.id.release_date);
        thumbnail = findViewById(R.id.thumbnail);
        Favirote_button = findViewById(R.id.fav_la);
        unFavirote_button = findViewById(R.id.unfav_la);

        //Trailer Recycler
        trailerRecycler = findViewById(R.id.trailers);
        mAdapter = new TrailersAdapter(Details.this, trailersList);
        trailerRecycler.setLayoutManager(new LinearLayoutManager(this));
        trailerRecycler.setItemAnimator(new DefaultItemAnimator());
        trailerRecycler.setAdapter(mAdapter);

        //Reviews Recycler
        reviewRecycler = findViewById(R.id.reviews_Recycler);
        rAdapter = new ReviewsAdapter(Details.this, reviewList);
        reviewRecycler.setLayoutManager(new LinearLayoutManager(this));
        reviewRecycler.setItemAnimator(new DefaultItemAnimator());
        reviewRecycler.setAdapter(rAdapter);
        addMovietoFavs = ViewModelProviders.of(this).get(AddMoviesListViewModel.class);
        addMovietoFavs2 = ViewModelProviders.of(this).get(MoviesListViewModel.class);


        //get video ID
        Intent i = getIntent();
        videoID = i.getStringExtra(getString(R.string.VIDEO_ID_TXT));
        ShowVideoList();

        //TO CHECK IF VIDEO IS ALREADY A FAVORITE OR NOT
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute();

    }

    //SHOW VIDEO DETAILS
    private void ShowVideoList() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(getString(R.string.https))
                .authority(getString(R.string.Host))
                .appendPath("3")
                .appendPath(getString(R.string.movie))
                .appendPath(videoID)
                .appendQueryParameter(getString(R.string.api_key_t), getString(R.string.API_KEY))
                .appendQueryParameter(getString(R.string.language), getString(R.string.en_US));
        String url2 = builder.toString();
        RequestQueue Video_Queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    title.setText(jsonObject.getString(getString(R.string.origional_title)));
                    releasedate.setText(jsonObject.getString(getString(R.string.release_date_txt)));
                    rating.setText(jsonObject.getString(getString(R.string.vote_avg))+ getString(R.string.totalNum));
                    overview.setText(jsonObject.getString(getString(R.string.overview)));
                    saveimageurl = jsonObject.getString(getString(R.string.poster_path));
                    imageurl =getString(R.string.base_poster_url)+jsonObject.getString(getString(R.string.poster_path));
                    Picasso.get().load(imageurl).into(thumbnail);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Video_Queue.add(stringRequest);
    }

    //SHOW TEASERS AND TRAILER
    private void ShowTrailers() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(getString(R.string.https))
                .authority(getString(R.string.Host))
                .appendPath("3")
                .appendPath(getString(R.string.movie))
                .appendPath(videoID)
                .appendPath(getString(R.string.VIDEOS_TXT_Q))
                .appendQueryParameter(getString(R.string.api_key_t), getString(R.string.API_KEY))
                .appendQueryParameter(getString(R.string.language), getString(R.string.en_US));
        String url2 = builder.toString();
        RequestQueue Video_Queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(getString(R.string.results));
                    for(int i =0; i<=jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        TrailerMode TM = new TrailerMode();
                        TM.setName(jsonObject1.getString(getString(R.string.NAME_TXT)));
                        TM.setKey(jsonObject1.getString(getString(R.string.VIDEO_KEY)));
                        TM.setType(jsonObject1.getString(getString(R.string.VIDEO_TYPE)));
                        if (TM.getType().equals(getString(R.string.TEASER)) || TM.getType().equals(getString(R.string.TRAILER))) {
                            trailersList.add(TM);
                        }
                        trailerRecycler.setAdapter(mAdapter);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Video_Queue.add(stringRequest);
    }

    //ADD REVIEWS WITH AUTHOR NAME AND CONTENT
    private void ShowReviews() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(getString(R.string.https))
                .authority(getString(R.string.Host))
                .appendPath("3")
                .appendPath(getString(R.string.movie))
                .appendPath(videoID)
                .appendPath("reviews")
                .appendQueryParameter(getString(R.string.api_key_t), getString(R.string.API_KEY))
                .appendQueryParameter(getString(R.string.language), getString(R.string.en_US));
        String url2 = builder.toString();
        RequestQueue Video_Queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(getString(R.string.results));
                    for(int i =0; i<=jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        ReviewModel RM = new ReviewModel();
                        RM.setAuthor(jsonObject1.getString("author"));
                        RM.setContent(jsonObject1.getString("content"));
                        reviewList.add(RM);
                        reviewRecycler.setAdapter(rAdapter);

                    }


                }
                catch (JSONException e){
                    e.printStackTrace();
                    Log.e("MYAPP", "exception: " + e.getMessage());
                    Log.e("MYAPP", "exception: " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Video_Queue.add(stringRequest);
    }

    //ADD FAVORITE MOVIE
    public void SaveToFavs(View view) {
        //save data to room database
        addMovietoFavs.addMovie(new MoviesModel(
                videoID,
                title.getText().toString(),
                "true",
                saveimageurl
        ));
        Toast.makeText(this, getString(R.string.ADDED_FAV), Toast.LENGTH_LONG).show();
        Favirote_button.setVisibility(View.INVISIBLE);
        unFavirote_button.setVisibility(View.VISIBLE);
    }

    //DELETE FAVORITE MOVIE
    public void DeleteFav(View view) {
        //delete entry from room database
        BackgroundTask2 backgroundTask = new BackgroundTask2();
        backgroundTask.execute();
        Toast.makeText(this, getString(R.string.Deleted_entry), Toast.LENGTH_LONG).show();
        Favirote_button.setVisibility(View.VISIBLE);
        unFavirote_button.setVisibility(View.INVISIBLE);
    }

    //CHECK IF MOVIE IS ALREADY FAVORITE AND CHANGE BUTTONS
    @SuppressLint("StaticFieldLeak")
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        BackgroundTask() {
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(Void... params) {
            ShowTrailers();
            ShowReviews();
            DAO itemDAO = DatabaseMovies.getDatabase(getApplicationContext()).ItemsModel();


            if (itemDAO.getItembyId(videoID) != null ){
                if (itemDAO.getItembyId(videoID).getFav().equals("true")) {
                    Favirote_button.setVisibility(View.INVISIBLE);
                    unFavirote_button.setVisibility(View.VISIBLE);
                }
            }
            else {
                Favirote_button.setVisibility(View.VISIBLE);
                unFavirote_button.setVisibility(View.INVISIBLE);
            }



            return null;
        }
        @Override
        protected void onPostExecute(String result) {


        }
    }


    //DELETE DAO QUERY
    @SuppressLint("StaticFieldLeak")
    class BackgroundTask2 extends AsyncTask<Void, Void, String> {
        BackgroundTask2() {
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(Void... params) {
            DAO itemDAO = DatabaseMovies.getDatabase(getApplicationContext()).ItemsModel();
            addMovietoFavs2.deleteItem(itemDAO.getItembyId(videoID));
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
        }
    }


    public void onBackPressed(){
        this.finish();
    }

}
