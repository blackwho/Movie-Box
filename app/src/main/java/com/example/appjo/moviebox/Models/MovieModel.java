package com.example.appjo.moviebox.Models;

import com.google.gson.annotations.SerializedName;

public class MovieModel {
    @SerializedName("id")
    private String id;

    @SerializedName("original_title")
    private String original_title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("vote_average")
    private String vote_average;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getVote_average() {
        return vote_average;
    }
}
