package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

public class MovieActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        GridView gridView = (GridView) findViewById(R.id.movie_poster_grid_view);
        //TODO (1) Set up an adapter to this grid view which will populate the screen with Movies Posters


    }
}
