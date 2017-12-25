package com.example.android.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>{


    private ProgressBar mLoadingIndicator;
    private static final int MOVIE_LOADER_ID = 1;
    private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY = ""; //Please Enter API_KEY.
    private RecyclerView moviesRecyclerView;
    private ImageAdapter imageAdapter;
    private TextView mOfflineTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        if (AP)

        mLoadingIndicator = (ProgressBar) findViewById(R.id.loading_circle);
        mOfflineTextView = (TextView) findViewById(R.id.internet_connection_error_text);
        mOfflineTextView.setVisibility(View.INVISIBLE);

        ConnectivityManager check = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = check.getActiveNetworkInfo();


        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            initViews();
        } else{
            mOfflineTextView.setVisibility(View.VISIBLE);
        }

    }

    private void initViews(){
        moviesRecyclerView = (RecyclerView) findViewById(R.id.poster_recycler_view);
        moviesRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        moviesRecyclerView.setLayoutManager(layoutManager);


        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(MOVIE_LOADER_ID,null,this);
    }



    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        String sortOrder = "popular";

        Uri baseUri = Uri.parse(TMDB_BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendPath(sortOrder);
        uriBuilder.appendQueryParameter("api_key",API_KEY);
        final String finalUrl = uriBuilder.toString();

        return new AsyncTaskLoader<ArrayList<Movie>>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public ArrayList<Movie> loadInBackground() {
                if (finalUrl==null){
                    return null;
                }

                return NetworkUtils.fetchMovieData(finalUrl);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        mLoadingIndicator = (ProgressBar) findViewById(R.id.loading_circle);
        mLoadingIndicator.setVisibility(View.INVISIBLE);

        if(data != null && !data.isEmpty()){
            imageAdapter = new ImageAdapter(this,data);
            moviesRecyclerView.setAdapter(imageAdapter);
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

    }
}
