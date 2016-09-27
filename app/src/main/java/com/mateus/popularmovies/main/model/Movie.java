package com.mateus.popularmovies.main.model;

import java.util.Date;

/**
 * Created by mateus on 27/09/16.
 */
public class Movie {

    private String title;
    private String description;
    private Date dateRelease;
    private int averageUser;

    public Movie (String title, String description, Date date, int average) {
        this.title = title;
        this.description = description;
        this.dateRelease = date;
        this.averageUser = average;
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

    public Date getDateRelease() {
        return dateRelease;
    }

    public void setDateRelease(Date dateRelease) {
        this.dateRelease = dateRelease;
    }

    public int getAverageUser() {
        return averageUser;
    }

    public void setAverageUser(int averageUser) {
        this.averageUser = averageUser;
    }
}
