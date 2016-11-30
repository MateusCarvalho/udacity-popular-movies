package com.mateus.popularmovies.main.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by mateus on 27/09/16.
 */
public class Movie {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("overview")
    private String description;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("release_date")
    private Date dateRelease;
    @SerializedName("poster_path")
    private String pathThumb;


    public Movie(String id, String title, String description, Double voteAverage, Date dateRelease, String pathThumb) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.voteAverage = voteAverage;
        this.dateRelease = dateRelease;
        this.pathThumb = pathThumb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Date getDateRelease() {
        return dateRelease;
    }

    public void setDateRelease(Date dateRelease) {
        this.dateRelease = dateRelease;
    }

    public String getPathThumb() {
        return pathThumb;
    }

    public void setPathThumb(String pathThumb) {
        this.pathThumb = pathThumb;
    }


    public class MovieResponse {

        @SerializedName("results")
        public List<Movie> results;

    }
}
