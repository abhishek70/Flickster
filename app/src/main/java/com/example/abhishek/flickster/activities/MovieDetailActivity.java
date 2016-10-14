package com.example.abhishek.flickster.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.abhishek.flickster.R;
import com.example.abhishek.flickster.models.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Movie movie = (Movie) getIntent().getSerializableExtra(MoviesActivity.MOVIE_DETAIL_KEY);

        Log.d("Movie Detail", movie.getTitle());
    }
}
