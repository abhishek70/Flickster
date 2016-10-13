package com.example.abhishek.flickster.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhishek.flickster.R;
import com.example.abhishek.flickster.models.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.resource;
import static com.example.abhishek.flickster.R.id.ivPosterImage;

/**
 * Created by abhishek on 10/12/16.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {


    static class ViewHolder {

        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvOverview) TextView tvOverview;
        @BindView(R.id.ivPosterImage) ImageView ivPosterImage;

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

        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());


        return view;

    }
}
