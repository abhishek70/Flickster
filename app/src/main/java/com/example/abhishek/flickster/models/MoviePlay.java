package com.example.abhishek.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by abhishek on 10/14/16.
 */

public class MoviePlay implements Serializable {

    /** Tag for the log messages */
    private static final String LOG_TAG = MoviePlay.class.getSimpleName();

    // Movie Video Source
    private String mMovieVideoSrc;


    public String getMovieVideoSrc(){
        return mMovieVideoSrc;
    }

    /**
     * Returns a Movie Video Source the expected JSON
     * @param jsonObject
     * @return
     */
    public static MoviePlay fromJson(JSONObject jsonObject) {

        MoviePlay moviePlay = new MoviePlay();

        try {

            moviePlay.mMovieVideoSrc  = jsonObject.getString("source");


        } catch (JSONException e) {

            e.printStackTrace();
            return null;

        }

        // return the movie play object
        return moviePlay;
    }


    /**
     * Decodes array of movie json results into business model objects
     * @param jsonArray
     * @return
     */
    public static ArrayList<MoviePlay> fromJson(JSONArray jsonArray) {

        ArrayList<MoviePlay> moviePlay = new ArrayList<MoviePlay>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject moviePlayJson = null;

            try {
                moviePlayJson = jsonArray.getJSONObject(i);

            } catch (JSONException e) {

                e.printStackTrace();
                continue;
            }

            MoviePlay movieSrc = MoviePlay.fromJson(moviePlayJson);

            if(movieSrc != null){
                moviePlay.add(movieSrc);
            }

        }

        return moviePlay;

    }

}
