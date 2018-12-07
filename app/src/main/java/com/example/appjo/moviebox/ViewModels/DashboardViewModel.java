package com.example.appjo.moviebox.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.appjo.moviebox.Models.MovieModel;
import com.example.appjo.moviebox.Repos.MovieRepository;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {
    private LiveData<List<MovieModel>> movies;
    private MovieRepository movieRepository;

    public DashboardViewModel(@NonNull Application application){
        super(application);
        movieRepository = MovieRepository.getInstance(application);
    }

    public void init(String url) {
        movies = movieRepository.getMovies(url);
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movies;
    }
}
