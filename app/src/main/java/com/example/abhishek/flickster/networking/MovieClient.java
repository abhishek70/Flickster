package com.example.abhishek.flickster.networking;

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

    /** API KEY */
    private static final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    /** API BASE URL */
    private static final String API_BASE_URL = "https://api.themoviedb.org/3/movie/now_playing";

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
        String url = API_BASE_URL;
        RequestParams params = new RequestParams("api_key", API_KEY);
        client.get(url, params, handler);
    }


}
