package com.example.android.popularmovies;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;

public class MovieDetails extends AppCompatActivity {
    private Movie movie;
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private final String posterSize = "w500";



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        TextView mMovieTitle = (TextView) findViewById(R.id.current_movie_title);
        ImageView mMoviePoster = (ImageView) findViewById(R.id.current_movie_poster_image);
        TextView mPlotSynopsis = (TextView) findViewById(R.id.current_movie_plot_synopsis);
        TextView mReleaseDate = (TextView) findViewById(R.id.current_movie_release_date);
        TextView mUserRating = (TextView) findViewById(R.id.current_movie_rating);

        Bundle incomingBundle = getIntent().getExtras();
        if (incomingBundle != null){
            movie = incomingBundle.getParcelable("movie");
        } else
        {
            movie = savedInstanceState.getParcelable("movie");
        }
        Picasso.with(getApplicationContext())
                .load(IMAGE_BASE_URL+posterSize+movie.getPosterPath())
                .into(mMoviePoster);
        mMovieTitle.setText(movie.getMovieTitle());
        mPlotSynopsis.setText(movie.getPlotSynopsis());
        String releaseDate = movie.getReleaseDate();
        DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = inputDateFormat.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String correctDateFormat = outputDateFormat.format(date);

        mReleaseDate.setText(correctDateFormat);
        double userRating = movie.getUserRating();
        mUserRating.setText(String.valueOf(userRating));
    }

}
