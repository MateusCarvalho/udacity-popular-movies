package com.mateus.popularmovies.main.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mateuscarvalho on 28/09/16.
 */
public class MovieResponse {

    @SerializedName("results")
    public List<Movie> results;

}
