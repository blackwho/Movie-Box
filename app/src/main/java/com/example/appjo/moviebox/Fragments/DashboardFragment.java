package com.example.appjo.moviebox.Fragments;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import com.example.appjo.moviebox.Adapters.DashboardAdapter;
import com.example.appjo.moviebox.R;
import com.example.appjo.moviebox.Ui.CustomGridLayoutManager;
import com.example.appjo.moviebox.ViewModels.DashboardViewModel;

public class DashboardFragment extends Fragment {
    private static final String TAG = DashboardFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private DashboardAdapter mAdapter;
    private FloatingActionButton mFavoriteButton;
    private DashboardViewModel mViewModel;

    public DashboardFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.dashboard_fragment, container, false);
        ViewPreloadSizeProvider sizeProvider = new ViewPreloadSizeProvider();
        mFavoriteButton = rootView.findViewById(R.id.fab);
        mViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        mViewModel.init("https://api.themoviedb.org/3/trending/all/day?api_key=4d68386fdfbb718a8c36cc1c1053c82e");
        viewModelAttach();

        mRecyclerView = rootView.findViewById(R.id.dashboard_rv);
        mRecyclerView.setHasFixedSize(true);

        CustomGridLayoutManager mLayoutManager = new CustomGridLayoutManager(getContext(), 250);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DashboardAdapter(getContext(), sizeProvider);

        mRecyclerView.setAdapter(mAdapter);
        RecyclerViewPreloader<String> preloader = new RecyclerViewPreloader<>(Glide.with(this), mAdapter, sizeProvider, 5);
        mRecyclerView.addOnScrollListener(preloader);
        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private void viewModelAttach(){
        mViewModel.getMovies().observe(DashboardFragment.this, list -> {
            Log.v("PagedList", "" + list);
            mAdapter.submitList(list);
        });
    }
}
