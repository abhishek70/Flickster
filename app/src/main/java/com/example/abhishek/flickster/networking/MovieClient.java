package com.example.abhishek.flickster.networking;

import com.example.abhishek.flickster.utils.Config;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


/**
 * Created by abhishek on 10/12/16.
 * Helper methods related to requesting and receiving Movies data from themoviedb.org.
 */
public class MovieClient {


    /** Tag for the log messages */
    private static final String LOG_TAG = MovieClient.class.getSimpleName();



    /** AsyncHttpClient for API Call */
    private AsyncHttpClient client;


    /**
     * Constructor
     */
    public MovieClient() {
        this.client = new AsyncHttpClient();
    }

    /**
     * Get Movies Lists in JSON Format
     * @param handler
     */
    public void getMovies(JsonHttpResponseHandler handler) {

        String url = Config.MOVIE_API_URL;

        // Setting URL parameters
        RequestParams params = new RequestParams("api_key", Config.MOVIE_API_KEY);

        // Prep and executing API Call
        client.get(url, params, handler);
    }


    /**
     * Get Movie Play Source in JSON Format
     * @param handler
     */
    public void getMovieVideoSource(int movieId, JsonHttpResponseHandler handler) {

        String url = String.format(Config.MOVIE_VIDEO_API_URL,movieId);

        // Setting URL parameters
        RequestParams params = new RequestParams("api_key", Config.MOVIE_API_KEY);

        // Prep and executing API Call
        client.get(url, params, handler);
    }


}
