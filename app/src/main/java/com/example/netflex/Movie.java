package com.example.netflex;

import java.io.Serializable;

public class Movie implements Serializable {
    private String title;
    private String year;
    private String imdbID;
    private String type;
    private String posterUrl;

    // Constructor
    public Movie(String title, String year, String imdbID, String type, String posterUrl) {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.type = type;
        this.posterUrl = posterUrl;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getImdbID() { return imdbID; }
    public void setImdbID(String imdbID) { this.imdbID = imdbID; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
}