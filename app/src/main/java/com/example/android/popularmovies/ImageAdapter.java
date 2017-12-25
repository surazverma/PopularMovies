package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Suraz Verma on 12/22/2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{
    private ArrayList<Movie> movies;
    private Context context;


    public ImageAdapter(Context context, ArrayList<Movie> movies)
    {
        this.context = context;
        this.movies = movies;
    }
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w500" +movies.get(i).getPosterPath())
                .into(holder.posterImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView posterImage;
        public ViewHolder(View view){
            super(view);
            posterImage =  view.findViewById(R.id.movie_poster);
        }
    }
}
