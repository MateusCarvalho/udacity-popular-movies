package com.mateus.popularmovies.main.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mateuscarvalho on 29/11/16.
 */

public class MovieReviews {

    @SerializedName("id")
    private String id;
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;
    @SerializedName("url")
    private String url;

    public MovieReviews(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public class MovieReviewResponse {
        @SerializedName("results")
        public List<MovieReviews> reviews;
    }
}
