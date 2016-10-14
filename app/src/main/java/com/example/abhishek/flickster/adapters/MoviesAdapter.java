package com.example.abhishek.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhishek.flickster.R;
import com.example.abhishek.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static android.R.attr.resource;
import static com.example.abhishek.flickster.R.id.ivMovieImage;

/**
 * Created by abhishek on 10/12/16.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {


    static class ViewHolder {

        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvOverview) TextView tvOverview;
        @BindView(R.id.ivMovieImage) ImageView ivMovieImage;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        super(context, R.layout.adapter_item_movie, movies);
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Movie movie = getItem(position);

        ViewHolder holder;

        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item_movie, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        holder.ivMovieImage.setImageResource(0);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());

        String imagePath = movie.getPosterPath();

        int orientation = getContext().getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imagePath = movie.getBackdropPath();
        }


        Picasso.with(getContext()).load(imagePath).fit().centerInside()
                .transform(new RoundedCornersTransformation(10,10))
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_placeholder)
                .into(holder.ivMovieImage);


        return view;

    }
}
