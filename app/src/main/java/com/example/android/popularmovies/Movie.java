package com.example.android.popularmovies;

/**
 * Created by Suraz Verma on 12/18/2017.
 */

public class Movie {
    private String mMovieTitle;
    private String mPosterPath;
    private String mPlotSynopsis;
    private String mUserRating;
    private String mReleaseDate;

    public Movie(String movieTitle,String posterPath, String plotSynopsis, String userRating,String releaseDate){
        this.mMovieTitle = movieTitle;
        this.mPosterPath = posterPath;
        this.mPlotSynopsis = plotSynopsis;
        this.mUserRating = userRating;
        this.mReleaseDate = releaseDate;
    }

    public String getMovieTitle (){
        return mMovieTitle;
    }
    public String getPosterPath(){
        return mPosterPath;
    }
    public String getPlotSynopsis(){
        return mPlotSynopsis;
    }
    public String getUserRating(){
        return mUserRating;
    }
    public String getReleaseDate(){
        return mReleaseDate;
    }

}
