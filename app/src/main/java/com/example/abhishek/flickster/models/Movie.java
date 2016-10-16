package com.example.abhishek.flickster.models;

import com.example.abhishek.flickster.networking.MovieClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by abhishek on 10/11/16.
 */

public class Movie implements Serializable {

    /** Tag for the log messages */
    private static final String LOG_TAG = Movie.class.getSimpleName();

    // Movie Id
    private int mId;

    // Movie Title
    private String mTitle;

    // Movie Description
    private String mOverview;

    // Movie Vote Average
    private double mVoteAverage;

    // Movie Poster Url
    private String mPosterPath;

    // Movie Backdrop Url
    private String mBackdropPath;


    // Movie Release Date
    private String mReleaseDate;


    // Movie Popularity
    private double mPopularity;

    /**
     * Getter for Movie Id
     * @return
     */
    public int getId() {
        return mId;
    }

    /**
     * Getter for Movie Title
     * @return
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Getter for Movie Overview
     * @return
     */
    public String getOverview() {
        return mOverview;
    }

    /**
     * Getter for Movie Vote Average
     * @return
     */
    public double getVoteAverage() {
        return mVoteAverage;
    }


    /**
     * Getter for Movie Poster Path
     * @return
     */
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w500/%s", mPosterPath);
    }


    /**
     * Getter for Movie Backdrop Path
     * @return
     */
    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w780/%s", mBackdropPath);
    }


    public String getReleaseDate() {
        return String.format("Release Date: %s", mReleaseDate);
    }


    public double getPopularity() {
        return mPopularity;
    }

    /**
     * Returns a Movie given the expected JSON
     * @param jsonObject
     * @return
     */
    public static Movie fromJson(JSONObject jsonObject) {

        Movie movie = new Movie();

        try {

            movie.mId           = jsonObject.getInt("id");
            movie.mOverview     = jsonObject.getString("overview");
            movie.mTitle        = jsonObject.getString("title");
            movie.mVoteAverage  = jsonObject.getDouble("vote_average");
            movie.mPosterPath   = jsonObject.getString("poster_path");
            movie.mBackdropPath = jsonObject.getString("backdrop_path");
            movie.mReleaseDate  = jsonObject.getString("release_date");
            movie.mPopularity   = jsonObject.getDouble("popularity");

        } catch (JSONException e) {

            e.printStackTrace();
            return null;

        }

        // return the movie object
        return movie;
    }


    /**
     * Decodes array of movie json results into business model objects
     * @param jsonArray
     * @return
     */
    public static ArrayList<Movie> fromJson(JSONArray jsonArray) {

        ArrayList<Movie> movies = new ArrayList<Movie>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject movieJson = null;

            try {
                movieJson = jsonArray.getJSONObject(i);

            } catch (JSONException e) {

                e.printStackTrace();
                continue;
            }

            Movie movie = Movie.fromJson(movieJson);

            if(movie != null){
                movies.add(movie);
            }

        }

        return movies;

    }
}
