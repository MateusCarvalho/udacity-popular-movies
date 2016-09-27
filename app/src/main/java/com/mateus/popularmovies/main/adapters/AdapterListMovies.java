package com.mateus.popularmovies.main.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mateus.popularmovies.main.model.Movie;

import java.util.ArrayList;

/**
 * Created by mateus on 27/09/16.
 */
public class AdapterListMovies extends BaseAdapter {

    private Context ctx;
    private ArrayList<Movie> listItens;

    public AdapterListMovies (Context ctx, ArrayList<Movie> data) {
        this.ctx = ctx;
        this.listItens = data;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
