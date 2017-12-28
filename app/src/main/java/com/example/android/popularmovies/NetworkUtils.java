package com.example.android.popularmovies;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Suraz Verma on 12/20/2017.
 */

public class NetworkUtils {

    private static ArrayList<Movie> extractMovieDetailsFromJsonData(String movieJson){
        if (TextUtils.isEmpty(movieJson)){
            return null;
        }

        ArrayList<Movie> movies = new ArrayList<>();

        try{
            JSONObject baseJsonObject = new JSONObject(movieJson);
            JSONArray resultArray = baseJsonObject.getJSONArray("results");
            for (int i = 0;i<resultArray.length();i++){
                JSONObject currentMovie = resultArray.getJSONObject(i);
                String movieTitle = currentMovie.getString("original_title");
                String moviePosterPath = currentMovie.getString("poster_path");
                String moviePlotSynopsis = currentMovie.getString("overview");
                double movieUserRating = currentMovie.getDouble("vote_average");
                String movieReleaseDate = currentMovie.getString("release_date");


                Movie movie = new Movie(movieTitle,moviePosterPath,moviePlotSynopsis,movieUserRating,movieReleaseDate);
                movies.add(movie);
            }
        }catch (JSONException e){
            Log.e("NetworkUtils","Issue parsing the movie data.",e);
        }
        return movies;
    }
    private static URL createURL (String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e("NetworkUtils","Error in forming Url",e);

        }
        return url;
    }
    private static String makeHttpRequest(URL url)throws IOException{
        String jsonResponse = null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            }
        }catch (IOException e){
            Log.e("NetworkUtils","Cannot make URL request",e);
        }finally {
            if (urlConnection!=null){
                urlConnection.disconnect();
            }
            if (inputStream!=null){
                inputStream.close();
            }
        }



        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream)throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null){
                output.append(line);
                line = reader.readLine();
            }

        }

        return output.toString();
    }
    public static ArrayList<Movie> fetchMovieData(String requestUrl){
        URL url = createURL(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e("NetworkUtils","Error in making Http Request",e);
        }

        return extractMovieDetailsFromJsonData(jsonResponse);
    }
}
