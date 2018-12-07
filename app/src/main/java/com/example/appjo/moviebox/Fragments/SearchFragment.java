package com.example.appjo.moviebox.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appjo.moviebox.Adapters.SearchAdapter;
import com.example.appjo.moviebox.R;
import com.example.appjo.moviebox.ViewModels.SearchViewModel;

public class SearchFragment extends Fragment {
    private static final String TAG = SearchFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private SearchViewModel mViewModel;
    private String searchTerm = "";
    private SearchAdapter mAdapter;
    public SearchFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        searchTerm = getArguments().getString("searchTerm");
        final View rootView = inflater.inflate(R.layout.search_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        mRecyclerView = rootView.findViewById(R.id.search_rv);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new SearchAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mViewModel.searchMovie(searchTerm).observe(this, pagedList -> {
            Log.v(TAG, "PagedList " + pagedList);
            mAdapter.submitList(pagedList);
        });
        return rootView;
    }

    public void setSearchTerm(String data){
        mViewModel.searchMovie(data).observe(this, pagedList -> {
            Log.v(TAG, "PagedList " + pagedList);
            mAdapter.submitList(pagedList);
        });
    }
}
