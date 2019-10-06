package com.salam.hedghoglabtest.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.salam.hedghoglabtest.R;
import com.salam.hedghoglabtest.model.TrailerMode;

import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.MyViewHolder>  {

    private final List<TrailerMode> trailerList;
    private final Activity activity;

    class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView titleTrailer;
        MyViewHolder(View view) {
            super(view);
            titleTrailer =  view.findViewById(R.id.trailer_name);

        }
    }
    public TrailersAdapter(Activity activity, List<TrailerMode> trailerList) {
        this.trailerList = trailerList;
        this.activity = activity;
    }
    @NonNull
    @Override
    public TrailersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersAdapter.MyViewHolder holder, int position) {
        final TrailerMode title = trailerList.get(position);
            holder.titleTrailer.setText(title.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //start youtube app to play the trailer
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + title.getKey()));
                    activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }



}
