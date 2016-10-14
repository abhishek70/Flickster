package com.example.abhishek.flickster.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.abhishek.flickster.R;
import com.example.abhishek.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    /** Tag for the log messages */
    private static final String LOG_TAG = MovieDetailActivity.class.getSimpleName();

    private static final String MOVIE_ID = "movie_id";

    @BindView(R.id.rbMovieRating) RatingBar rbMovieRating;
    @BindView(R.id.tvMovieOverview) TextView tvMovieOverview;
    @BindView(R.id.tvMovieTitle) TextView tvMovieTitle;
    @BindView(R.id.ivMovieImage) ImageView ivMovieImage;
    @BindView(R.id.tvMovieReleaseDate) TextView tvMovieReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        final Movie movie = (Movie) getIntent().getSerializableExtra(MoviesActivity.MOVIE_DETAIL_KEY);

        Picasso.with(this)
                .load(movie.getBackdropPath())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_placeholder)
                .into(ivMovieImage);

        tvMovieTitle.setText(movie.getTitle());

        tvMovieReleaseDate.setText(movie.getReleaseDate());

        rbMovieRating.setRating((float) movie.getVoteAverage());

        tvMovieOverview.setText(movie.getOverview());
        tvMovieOverview.setMovementMethod(new ScrollingMovementMethod());

        ivMovieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setIntent = new Intent(getApplicationContext(), VideoPlayActivity.class);
                setIntent.putExtra(MOVIE_ID, movie.getId());
                startActivity(setIntent);
            }
        });
    }


}
