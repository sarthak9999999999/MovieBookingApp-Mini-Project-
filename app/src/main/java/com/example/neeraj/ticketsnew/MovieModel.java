package com.example.neeraj.ticketsnew;

public class MovieModel {
    private String movie_name;
    private String description;
    private String url;

    public MovieModel()
    {

    }

    public MovieModel(String movie_name, String description, String url) {
        this.movie_name = movie_name;
        this.description = description;
        this.url = url;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
