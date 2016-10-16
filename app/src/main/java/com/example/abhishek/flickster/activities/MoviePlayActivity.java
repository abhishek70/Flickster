package com.example.abhishek.flickster.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.abhishek.flickster.R;
import com.example.abhishek.flickster.models.Movie;
import com.example.abhishek.flickster.models.MoviePlay;
import com.example.abhishek.flickster.networking.MovieClient;
import com.example.abhishek.flickster.utils.Config;
import com.example.abhishek.flickster.utils.Constant;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.abhishek.flickster.R.id.swipeRefresh;
import static com.example.abhishek.flickster.utils.Config.YT_API_KEY;

/**
 * Class for showing movie video or trailer from the external source
 */
public class MoviePlayActivity extends YouTubeBaseActivity {

    /** Tag for the log messages */
    private static final String LOG_TAG = MoviePlayActivity.class.getSimpleName();

    // Movie client for building url for fetching movie data
    private MovieClient movieClient;

    // Movie Id
    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_play);

        // Fetching the data from the Movie Detail Activity through Intent
        Intent intent = getIntent();
        movieId = intent.getIntExtra(Constant.MOVIE_ID, -1);

        // Load and play the movie video
        playMovieVideo();

    }


    /**
     * AsynClient HTTP Request to get the movie video source
     * OnSuccess pass video source to youtube player
     */
    private void playMovieVideo() {

        // AsyncHttp Client
        movieClient = new MovieClient();

        // AsyncHttp Client response handler
        movieClient.getMovieVideoSource(movieId, new JsonHttpResponseHandler(){

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

                    // Get movie video source json array
                    resultSet = response.getJSONArray("youtube");

                    // parse into array of MoviePlay objects
                    ArrayList<MoviePlay> moviePlay = MoviePlay.fromJson(resultSet);

                    if (moviePlay.size() > 0) {

                        // Load youtube player view with the video
                        loadYouTubePlayer(moviePlay.get(0).getMovieVideoSrc());
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
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

            }

        });
    }

    /**
     * Load Youtube Player with Video
     * @param movieSource
     */
    private void loadYouTubePlayer(final String movieSource){

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);

        youTubePlayerView.initialize(Config.YT_API_KEY,
            new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                    YouTubePlayer youTubePlayer, boolean b) {

                    // do any work here to cue video, play video, etc.
                    youTubePlayer.cueVideo(movieSource);
                    // or to play immediately
                    // youTubePlayer.loadVideo("5xVh-7ywKpE");
                }
                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                    YouTubeInitializationResult youTubeInitializationResult) {
                    Toast.makeText(MoviePlayActivity.this, "Youtube Failed!", Toast.LENGTH_SHORT).show();
                }
            });

    }
}
