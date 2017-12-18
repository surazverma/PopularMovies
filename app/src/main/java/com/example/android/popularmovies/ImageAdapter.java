package com.example.android.popularmovies;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Suraz Verma on 12/18/2017.
 */

public class ImageAdapter extends ArrayAdapter<Movie> {
    public ImageAdapter(Activity context, List<Movie> movies){
        super(context,0,movies);
    }
}
