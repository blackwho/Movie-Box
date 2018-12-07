package com.example.appjo.moviebox.Models;

import com.google.gson.annotations.SerializedName;

public class SearchModel {
    @SerializedName("page")
    public Integer pageNumber;

    @SerializedName("results")
    public MovieModel[] movies;

    @SerializedName("total_pages")
    public Integer totalPages;

    @SerializedName("total_results")
    public Integer resultCount;


    public Integer getResultCount() {
        return resultCount;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public MovieModel[] getMovies() {
        return movies;
    }

    public Integer getTotalPages() {
        return totalPages;
    }
}
