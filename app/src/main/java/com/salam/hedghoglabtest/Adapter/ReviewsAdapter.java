package com.salam.hedghoglabtest.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.salam.hedghoglabtest.Details;
import com.salam.hedghoglabtest.R;
import com.salam.hedghoglabtest.model.ReviewModel;

import java.util.List;

public class ReviewsAdapter  extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {
    private final List<ReviewModel> reviewList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView authorName;
        final TextView content;


        MyViewHolder(View view) {
            super(view);
            authorName =  view.findViewById(R.id.author_name);
            content = view.findViewById(R.id.review_txt);
        }
    }
    public ReviewsAdapter(Details details, List<ReviewModel> reviewList) {
        this.reviewList = reviewList;
    }
    @NonNull
    @Override
    public ReviewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reviews_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.MyViewHolder holder, int position) {
        final ReviewModel reviews = reviewList.get(position);
        holder.authorName.setText(reviews.getAuthor());
        holder.content.setText(reviews.getContent());

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }





}
