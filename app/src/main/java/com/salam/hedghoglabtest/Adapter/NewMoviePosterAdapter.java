package com.salam.hedghoglabtest.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.salam.hedghoglabtest.Details;
import com.salam.hedghoglabtest.R;
import com.salam.hedghoglabtest.model.PosterParceable;
import com.squareup.picasso.Picasso;

import java.util.List;


public class NewMoviePosterAdapter extends RecyclerView.Adapter<NewMoviePosterAdapter.RecyclerViewHolder> {

    private List<PosterParceable> moviesList;
    private final Activity activity;

        public NewMoviePosterAdapter(Activity activity, List<PosterParceable> moviesList) {
            this.moviesList = moviesList;
            this.activity = activity;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.items, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, int position) {
            final PosterParceable movieurl = moviesList.get(position);
            Picasso.get().load("https://image.tmdb.org/t/p/w600_and_h900_bestv2"+movieurl.getURL()).into(holder.poster_image);
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    //pass video id to details to retrieve Details of movie by movieID request from API
                        Intent newintent = new Intent(activity, Details.class);
                        newintent.putExtra("videoId", movieurl.getId());
                        activity.startActivity(newintent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }

        public void addItems(List<PosterParceable> moviesList) {
            this.moviesList = moviesList;
            notifyDataSetChanged();
        }

        static class RecyclerViewHolder extends RecyclerView.ViewHolder {
            final TextView title;
            final ImageView poster_image;

            RecyclerViewHolder(View view) {
                super(view);
                title =  view.findViewById(R.id.movietitle);
                poster_image = view.findViewById(R.id.poster_image);
            }
        }
    }


