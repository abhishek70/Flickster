package com.example.abhishek.flickster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhishek.flickster.R;
import com.example.abhishek.flickster.activities.MoviePlayActivity;
import com.example.abhishek.flickster.models.Movie;
import com.example.abhishek.flickster.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static android.R.attr.resource;
import static android.R.attr.type;
import static com.example.abhishek.flickster.R.id.ivMovieImage;

/**
 * Created by abhishek on 10/12/16.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {

    /**
     * View Holder Pattern
     */

    static class ViewHolder {

        // Movie Title
        @BindView(R.id.tvTitle) TextView tvTitle;

        // Movie Overview
        @BindView(R.id.tvOverview) TextView tvOverview;

        // Movie Image
        @BindView(R.id.ivMovieImage) ImageView ivMovieImage;

        // Binding views
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    /**
     * View Holder Pattern for popular movie layout for faster performance
     */

    static class PopularViewHolder {

        // Added tvTitle Nullable (Optional) for the popular movie
        @Nullable
        @BindView(R.id.tvTitle) TextView tvTitle;

        // Added tvOverview Nullable (Optional) for the popular movie
        @Nullable
        @BindView(R.id.tvOverview) TextView tvOverview;

        // Movie Image
        @BindView(R.id.ivMovieImage) ImageView ivMovieImage;

        // Binding views
        PopularViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    /**
     * Constructor
     * @param context
     * @param movies
     */
    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        super(context, R.layout.adapter_item_movie, movies);
    }

    /**
     * Defines the no. different types of layout views
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }


    /**
     * TODO : Needs to refactor and add the logic in Movie model
     * Defines the layout for specified item based on the conditional logic
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int val = (int) getItem(position).getPopularity();

        int type = 0;

        if(val > 10)
            type = 1;

        return type;
    }

    /**
     * Generating and return the single list item view
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Fetching single movie details
        Movie movie = getItem(position);

        // Fetching movie type layout to load
        int movieType = getItemViewType(position);


        if (movieType == 0) {

            // Load movie view
            convertView = loadMovieView(movie, convertView, parent);

        } else {

            // Load popular movie view
            convertView = loadPopularMovieView(movie, convertView, parent);
        }

        return convertView;
    }


    /**
     * Load movie view
     * @param movie
     * @param convertView
     * @param parent
     * @return
     */
    private View loadMovieView(Movie movie, View convertView, ViewGroup parent) {

        // Initialize ViewHolder
        ViewHolder viewHolder = null;

        String imagePath;
        int defaultPlaceholder;


        if (convertView == null) {

            // Loading actual item movie xml layout
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item_movie, parent, false);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.ivMovieImage.setImageResource(0);

        // Getting orientation
        int orientation = getContext().getResources().getConfiguration().orientation;

        // Load poster image for portrait orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            // Fetching poster image
            imagePath = movie.getPosterPath();

            // Setting image placeholder for portrait orientation
            defaultPlaceholder = R.drawable.poster_placeholder;

        } else {

            // Fetching backdrop image
            imagePath = movie.getBackdropPath();

            // Setting image placeholder for landscape orientation
            defaultPlaceholder = R.drawable.landscape_placeholder;
        }

        // Setting image with Picasso
        Picasso.with(getContext()).load(imagePath).fit().centerInside()
                .transform(new RoundedCornersTransformation(10, 10))
                .placeholder(defaultPlaceholder)
                .error(defaultPlaceholder)
                .into(viewHolder.ivMovieImage);

        // Setting movie title
        if(viewHolder.tvTitle != null){
            viewHolder.tvTitle.setText(movie.getTitle());
        }

        // Setting movie overview
        if(viewHolder.tvOverview != null) {
            viewHolder.tvOverview.setText(movie.getOverview());
        }


        return convertView;
    }


    /**
     * Load popular movie view
     * @param movie
     * @param convertView
     * @param parent
     * @return
     */
    private View loadPopularMovieView(Movie movie, View convertView, ViewGroup parent) {

        // Initialize ViewHolder
        PopularViewHolder viewHolder = null;

        // Fetching backdrop image
        String imagePath = movie.getBackdropPath();

        int defaultPlaceholder;

        if (convertView == null) {

            // Loading popular item movie xml layout
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item_popular_movie, parent, false);

            viewHolder = new PopularViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (PopularViewHolder) convertView.getTag();
        }

        viewHolder.ivMovieImage.setImageResource(0);


        // Getting orientation
        int orientation = getContext().getResources().getConfiguration().orientation;


        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            // Setting image placeholder for portrait orientation
            defaultPlaceholder = R.drawable.poster_placeholder;

        } else {

            // Setting image placeholder for landscape orientation
            defaultPlaceholder = R.drawable.landscape_placeholder;
        }

        // Setting image with Picasso
        Picasso.with(getContext()).load(imagePath).fit().centerInside()
                .transform(new RoundedCornersTransformation(10, 10))
                .placeholder(defaultPlaceholder)
                .error(defaultPlaceholder)
                .into(viewHolder.ivMovieImage);


        // Setting movie title
        if (viewHolder.tvTitle != null) {
            viewHolder.tvTitle.setText(movie.getTitle());
        }

        // Setting movie overview
        if (viewHolder.tvOverview != null) {
            viewHolder.tvOverview.setText(movie.getOverview());
        }

        // Getting movie Id
        final int movieId = movie.getId();

        // Called when the user click on the ivMovieImage View only for  popular movie view
        viewHolder.ivMovieImage.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize intent and pass the movie Id
                Intent intent = new Intent(getContext(), MoviePlayActivity.class);
                intent.putExtra(Constant.MOVIE_ID, movieId);

                // Initiate MoviePlay Activity for loading movie video
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
