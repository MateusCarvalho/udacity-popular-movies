package com.mateus.popularmovies.main.rest;

import com.mateus.popularmovies.main.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mateuscarvalho on 28/09/16.
 */
public interface APIMovieDB {


    //get the popular movies
    @GET("popular")
    Call<MovieResponse> getPopularMovies(
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("page") String qtd
    );


}
