package com.mateus.popularmovies.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mateus.popularmovies.R;
import com.mateus.popularmovies.main.model.Movie;

import java.util.ArrayList;

/**
 * Created by mateus on 27/09/16.
 */
public class AdapterListMovies extends RecyclerView.Adapter<AdapterListMovies.ViewHolder> {

    private ArrayList<Movie> listItens;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumb;
        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.titleMovie);
            thumb = (ImageView) view.findViewById(R.id.thumb);
        }
    }


    //create the constructor and force pass the data
    public AdapterListMovies(ArrayList<Movie> listItens) {
        this.listItens = listItens;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterListMovies.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_movie, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie item = listItens.get(position);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listItens.size();
    }

}
