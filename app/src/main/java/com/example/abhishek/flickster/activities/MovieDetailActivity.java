package com.example.abhishek.flickster.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.abhishek.flickster.R;
import com.example.abhishek.flickster.models.Movie;
import com.example.abhishek.flickster.utils.Constant;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.abhishek.flickster.utils.Constant.MOVIE_ID;

/**
 * Class for showing movie details
 */
public class MovieDetailActivity extends AppCompatActivity {

    /** Tag for the log messages */
    private static final String LOG_TAG = MovieDetailActivity.class.getSimpleName();

    // ButterKnife View Annotations
    @BindView(R.id.rbMovieRating) RatingBar rbMovieRating;
    @BindView(R.id.tvMovieOverview) TextView tvMovieOverview;
    @BindView(R.id.tvMovieTitle) TextView tvMovieTitle;
    @BindView(R.id.ivMovieImage) ImageView ivMovieImage;
    @BindView(R.id.tvMovieReleaseDate) TextView tvMovieReleaseDate;

    /**
     * Method called when the activity is initially loaded
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // ButterKnife View Binding
        ButterKnife.bind(this);

        // Movie Object Data
        final Movie movie = (Movie) getIntent().getSerializableExtra(Constant.MOVIE_DETAIL_KEY);

        // Loading Movie Image via Picasso
        Picasso.with(this)
                .load(movie.getBackdropPath())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_placeholder)
                .into(ivMovieImage);

        // Movie Title
        tvMovieTitle.setText(movie.getTitle());

        // Movie Release Date
        tvMovieReleaseDate.setText(movie.getReleaseDate());

        // Movie Rating
        rbMovieRating.setRating((float) movie.getVoteAverage());

        // Movie Overview
        tvMovieOverview.setText(movie.getOverview());
        tvMovieOverview.setMovementMethod(new ScrollingMovementMethod());

        // Method called when the user click on the Movie Image
        ivMovieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setIntent = new Intent(getApplicationContext(), MoviePlayActivity.class);
                setIntent.putExtra(Constant.MOVIE_ID, movie.getId());
                startActivity(setIntent);
            }
        });
    }


}
