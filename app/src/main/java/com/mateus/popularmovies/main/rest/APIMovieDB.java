package com.mateus.popularmovies.main.rest;

import com.mateus.popularmovies.main.model.Movie;
import com.mateus.popularmovies.main.model.MovieVideos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mateuscarvalho on 28/09/16.
 */
public interface APIMovieDB {


    //get the popular movies
    @GET("popular")
    Call<Movie.MovieResponse> getPopularMovies(
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("page") String qtd
    );

    @GET("top_rated")
    Call<Movie.MovieResponse> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") String qtd
    );

    @GET("{id}")
    Call<Movie> getMovie(
            @Path("id") String movieID,
            @Query("api_key") String apiKey,
            @Query("append_to_response") String append
    );


}
