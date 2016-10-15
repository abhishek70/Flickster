package com.example.abhishek.flickster.activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.abhishek.flickster.R;
import com.example.abhishek.flickster.adapters.MoviesAdapter;
import com.example.abhishek.flickster.models.Movie;
import com.example.abhishek.flickster.networking.MovieClient;
import com.example.abhishek.flickster.utils.Constant;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.example.abhishek.flickster.R.id.linlaHeaderProgress;
import static com.example.abhishek.flickster.R.id.swipeRefresh;
import static com.example.abhishek.flickster.utils.Constant.MOVIE_DETAIL_KEY;

/**
 * Class for showing movie collection loading from external source
 */
public class MoviesActivity extends AppCompatActivity {

    /** Tag for the log messages */
    private static final String LOG_TAG = MoviesActivity.class.getSimpleName();

    // Movie client for building url for fetching movie data
    private MovieClient movieClient;

    // Movies Collection
    private ArrayList<Movie> movies;

    // Movies Custom Adapter for loading Movies in list view
    private MoviesAdapter moviesAdapter;

    // ButterKnife View Annotations
    @BindView(R.id.lvMovies) ListView lvMovies;
    @BindView(R.id.swipeRefresh) SwipeRefreshLayout swipeRefresh;
    @BindColor(android.R.color.holo_blue_bright) int blueBright;
    @BindColor(android.R.color.holo_green_light) int greenLight;
    @BindColor(android.R.color.holo_orange_light) int orangeLight;
    @BindColor(android.R.color.holo_red_light) int redLight;
    @BindView(linlaHeaderProgress) LinearLayout movieLoader;


    /**
     * Method called when the activity is initially loaded
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        // ButterKnife View Binding
        ButterKnife.bind(this);

        // Loading swipe container
        loadSwipeRefresh();

        // Fetch the movie data remotely and asynchronously
        fetchMovies();

        //Setup onClick Listener for the list view
        setupMovieSelectedListener();
    }

    /**
     * Swipe Refresh Method
     */
    private void loadSwipeRefresh() {

        // Called when the user swipe
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Called fetching movies method
                fetchMovies();
            }
        });

        // Setting Swipe Color Scheme
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

        // Binding list view to the adapter
        lvMovies.setAdapter(moviesAdapter);

        // AsyncHttp Client
        movieClient = new MovieClient();

        // AsyncHttp Client response handler
        movieClient.getMovies(new JsonHttpResponseHandler(){

            @Override
            public void onStart() {
                super.onStart();
                movieLoader.setVisibility(View.VISIBLE);

            }

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

                    // Adding movies set to the adapter
                    moviesAdapter.addAll(movies);

                    // Notifying adapter for the list view changes
                    moviesAdapter.notifyDataSetChanged();

                    Log.d(LOG_TAG, movies.get(0).getOverview());

                } catch (JSONException e) {

                    e.printStackTrace();
                }

                movieLoader.setVisibility(View.GONE);

                // After loading movies successfully remove swipe refresh
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
                movieLoader.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);

            }

            @Override
            public void onCancel() {
                super.onCancel();
                movieLoader.setVisibility(View.GONE);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                movieLoader.setVisibility(View.GONE);
            }
        });

    }


    /**
     * This method is called when the user click on the individual list item to view detail
     */
    public void setupMovieSelectedListener() {
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {

                // Creating intent for passing movie detail to movie detail activity
                Intent i = new Intent(MoviesActivity.this, MovieDetailActivity.class);
                Movie movie = movies.get(position);
                i.putExtra(Constant.MOVIE_DETAIL_KEY, movie);
                startActivity(i);
            }
        });
    }
}
