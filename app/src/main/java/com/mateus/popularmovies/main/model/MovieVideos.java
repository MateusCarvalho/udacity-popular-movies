package com.mateus.popularmovies.main.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mateuscarvalho on 29/11/16.
 */

public class MovieVideos {

    @SerializedName("id")
    private String id;
    @SerializedName("key")
    private String key;
    @SerializedName("site")
    private String site;

    public MovieVideos(String id, String key, String site) {
        this.id = id;
        this.key = key;
        this.site = site;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }


    public class MovieVideosResponse {
        @SerializedName("results")
        public List<MovieVideos> results;
    }
}
