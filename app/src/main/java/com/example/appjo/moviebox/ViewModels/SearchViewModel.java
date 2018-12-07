package com.example.appjo.moviebox.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.example.appjo.moviebox.DataSource.MovieDataSourceFactory;
import com.example.appjo.moviebox.Models.MovieModel;
import com.example.appjo.moviebox.Repos.MovieRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SearchViewModel extends AndroidViewModel {

    private Application mApplication;
    private Executor executor;

    public SearchViewModel(@NonNull Application application){
        super(application);
        this.mApplication = application;
        executor = Executors.newFixedThreadPool(5);
    }

    public LiveData<PagedList<MovieModel>> searchMovie(String searchTerm){
        MovieDataSourceFactory movieDataSourceFactory = new MovieDataSourceFactory(searchTerm, 1, mApplication);
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();
        return (new LivePagedListBuilder<>(movieDataSourceFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();

    }

}
