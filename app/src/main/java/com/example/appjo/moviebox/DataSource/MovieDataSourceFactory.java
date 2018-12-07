package com.example.appjo.moviebox.DataSource;

import android.app.Application;
import android.arch.paging.DataSource;
import android.util.Log;

import com.example.appjo.moviebox.Repos.MovieRepository;

public class MovieDataSourceFactory extends DataSource.Factory {

    private static final String TAG = MovieDataSourceFactory.class.getSimpleName();
    private String search;
    private Integer page;
    private Application mApplication;

    public MovieDataSourceFactory(String mSearch, Integer mPage, Application application){
        this.search = mSearch;
        this.page = mPage;
        this.mApplication = application;
    }

    @Override
    public DataSource create() {
        Log.v(TAG, "I am here");
        return MovieRepository.getInstance(mApplication).new MovieDataSource(search, page);
    }
}
