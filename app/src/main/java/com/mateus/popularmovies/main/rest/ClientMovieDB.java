package com.mateus.popularmovies.main.rest;

import com.mateus.popularmovies.main.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mateuscarvalho on 28/09/16.
 */
public class ClientMovieDB {


    private static Retrofit instance;

    public static Retrofit getInstance() {
        if (instance==null){
            instance = new Retrofit.Builder()
                    .baseUrl(Constants.API_BASE_MOVIEDB)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}
