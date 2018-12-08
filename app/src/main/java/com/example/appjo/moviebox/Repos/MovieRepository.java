package com.example.appjo.moviebox.Repos;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.example.appjo.moviebox.Models.MovieModel;
import com.example.appjo.moviebox.Models.SearchModel;
import com.example.appjo.moviebox.Utils.GsonRequest;
import com.example.appjo.moviebox.Utils.VolleySingleton;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieRepository {
    private static MovieRepository mInstance;private static Context context;
    private RequestQueue mRequestQueue;
    private static final String BASE_URL = "https://api.themoviedb.org/3/search/movie?api_key=4d68386fdfbb718a8c36cc1c1053c82e&language=en-US";

    public MovieRepository(Context context){
        mRequestQueue = VolleySingleton.getInstance(context).getRequestQueue();
    }
    public MovieRepository(Application application){
        mRequestQueue = VolleySingleton.getInstance(application.getApplicationContext()).getRequestQueue();
        context = application.getApplicationContext();
    }

    public static MovieRepository getInstance(Application application){
        if(mInstance == null){
            mInstance = new MovieRepository(application);
        }
        return mInstance;
    }

    public static MovieRepository getInstanceTest(Context context){
        if(mInstance == null){
            mInstance = new MovieRepository(context);
        }
        return mInstance;
    }
    public LiveData<List<MovieModel>> getMovies(String url){
        final MutableLiveData<List<MovieModel>> movies= new MutableLiveData<>();
        GsonRequest mJsonObjectRequest = new GsonRequest(Request.Method.GET,
                url,
                SearchModel.class,
                null,
                null,
                new Response.Listener<SearchModel>() {
                    @Override
                    public void onResponse(SearchModel response) {
                        if(response != null) {
                            movies.postValue(Arrays.asList((MovieModel[]) response.getMovies()));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("TAG", "Error:" + error);
            }
        });
        mJsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        mRequestQueue.add(mJsonObjectRequest);
        return movies;
    }

    //DataSource class
    public class MovieDataSource extends PageKeyedDataSource<Long, MovieModel> {
        private String search;
        private Integer page;

        public MovieDataSource(String mSearch, Integer mPage) {
            this.search = mSearch;
            this.page = mPage;
        }

        @Override
        public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, MovieModel> callback) {
            String Url = BASE_URL + "&query=" + search + "&page="  + page + "&include_adult=false";
            GsonRequest mJsonObjectRequest = new GsonRequest(Request.Method.GET,
                    Url,
                    SearchModel.class,
                    null,
                    null,
                    null,
                    new Response.Listener<SearchModel>() {
                        @Override
                        public void onResponse(SearchModel response) {
                            if(response != null) {
                                try {
                                    callback.onResult(Arrays.asList((MovieModel[])response.getMovies()), null, 2l);
                                }catch (IllegalStateException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("TAG", "Error:" + error);
                }
            });
            mJsonObjectRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            //mJsonObjectRequest.setShouldCache(false);
            mRequestQueue.add(mJsonObjectRequest);
        }

        @Override
        public void loadAfter(@NonNull LoadParams<Long> params, @NonNull final LoadCallback<Long, MovieModel> callback) {
            String Url = BASE_URL + "&query=" + search + "&page="  + params.key + "&include_adult=false";
            GsonRequest mJsonObjectRequest = new GsonRequest(Request.Method.GET,
                    Url,
                    SearchModel.class,
                    null,
                    null,
                    null,
                    new Response.Listener<SearchModel>() {
                        @Override
                        public void onResponse(SearchModel response) {
                            if(response != null) {
                                Log.v("Error: ", "Key: " + params.key);
                                long nextKey = (params.key == Long.valueOf(response.getResultCount())) ? null : params.key + 1;

                                try {
                                    callback.onResult(Arrays.asList((MovieModel[])response.getMovies()), nextKey);
                                }catch(IllegalStateException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("TAG", "Error:" + error);
                }
            });
            mJsonObjectRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            //mJsonObjectRequest.setShouldCache(false);
            mRequestQueue.add(mJsonObjectRequest);
        }

        @Override
        public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, MovieModel> callback) {

        }
    }

}
