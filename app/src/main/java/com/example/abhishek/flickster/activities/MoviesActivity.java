package com.example.abhishek.flickster.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.abhishek.flickster.R;
import com.example.abhishek.flickster.adapters.MoviesAdapter;
import com.example.abhishek.flickster.models.Movie;
import com.example.abhishek.flickster.networking.MovieClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.example.abhishek.flickster.R.id.swipeRefresh;

public class MoviesActivity extends AppCompatActivity {

    /** Tag for the log messages */
    private static final String LOG_TAG = MoviesActivity.class.getSimpleName();

    private MovieClient movieClient;
    private ArrayList<Movie> movies;
    private MoviesAdapter moviesAdapter;

    @BindView(R.id.lvMovies) ListView lvMovies;
    @BindView(R.id.swipeRefresh) SwipeRefreshLayout swipeRefresh;
    @BindColor(android.R.color.holo_blue_bright) int blueBright;
    @BindColor(android.R.color.holo_green_light) int greenLight;
    @BindColor(android.R.color.holo_orange_light) int orangeLight;
    @BindColor(android.R.color.holo_red_light) int redLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ButterKnife.bind(this);

        loadSwipeRefresh();

        // Fetch the movie data remotely and asynchronously
        fetchMovies();
    }

    private void loadSwipeRefresh() {

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMovies();
            }
        });

        swipeRefresh.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
    }

    /**
     * Fetch the movies list from themoviedb.org
     */
    private void fetchMovies(){

        movies = new ArrayList<Movie>();

        moviesAdapter = new MoviesAdapter(this, movies);

        lvMovies.setAdapter(moviesAdapter);

        // AsyncHttp Client
        movieClient = new MovieClient();

        // AsyncHttp Client response handler
        movieClient.getMovies(new JsonHttpResponseHandler(){


            /**
             * Success
             * @param statusCode
             * @param headers
             * @param response
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray resultSet = null;

                try {

                    // Get movies json array
                    resultSet = response.getJSONArray("results");

                    // parse into array of movie objects
                    ArrayList<Movie> movies = Movie.fromJson(resultSet);

                    moviesAdapter.addAll(movies);

                    Log.d(LOG_TAG, movies.get(0).getOverview());

                } catch (JSONException e) {

                    e.printStackTrace();
                }

                swipeRefresh.setRefreshing(false);

            }

            /**
             * Failure
             * @param statusCode
             * @param headers
             * @param throwable
             * @param errorResponse
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                super.onFailure(statusCode, headers, throwable, errorResponse);
                swipeRefresh.setRefreshing(false);

            }
        });

    }
}
