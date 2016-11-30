package com.mateus.popularmovies.main.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mateus.popularmovies.R;
import com.mateus.popularmovies.main.model.Movie;
import com.mateus.popularmovies.main.ui.MovieDetailActivity;
import com.mateus.popularmovies.main.utils.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by mateus on 27/09/16.
 */
public class AdapterListMovies extends RecyclerView.Adapter<AdapterListMovies.ViewHolder> implements View.OnClickListener {

    private static List<Movie> listItens;
    private Context mCtx;

    @Override
    public void onClick(View v) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public ImageView thumb;
        public ProgressBar progressBar;
        public ViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressImage);
            //title = (TextView) view.findViewById(R.id.titleMovie);
            thumb = (ImageView) view.findViewById(R.id.thumb);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int itemPosition = getAdapterPosition();
            Movie temp = listItens.get(itemPosition);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(temp.getDateRelease());

            String yearMovie = String.valueOf(calendar.get(Calendar.YEAR));

            Intent i = new Intent(mCtx, MovieDetailActivity.class);
            i.putExtra(Constants.MOVIE_ID,temp.getId());
            i.putExtra(Constants.MOVIE_TITLE,temp.getTitle());
            i.putExtra(Constants.MOVIE_OVERVIEW,temp.getDescription());
            i.putExtra(Constants.MOVIE_IMAGE_PATH,temp.getPathThumb());
            i.putExtra(Constants.MOVIE_DATE_RELEASED,yearMovie);
            i.putExtra(Constants.MOVIE_AVERAGE_USER,Double.toString(temp.getVoteAverage()));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(i);
        }
    }


    //create the constructor and force pass the data
    public AdapterListMovies(List<Movie> listItens,Context ctx) {
        this.listItens = listItens;
        this.mCtx = ctx;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //get item
        Movie item = listItens.get(position);

        //set data to view
        //holder.title.setText(item.getTitle());
        //get image
        Picasso.with(mCtx).load(Constants.API_BASE_IMAGES_MOVIEDB+item.getPathThumb()).into(holder.thumb, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                holder.progressBar.setVisibility(View.GONE);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listItens.size();
    }

    public void clearData() {
        int size = this.listItens.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.listItens.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public void setData(List<Movie> data) {
        this.listItens = data;
        this.notifyDataSetChanged();
    }

}
