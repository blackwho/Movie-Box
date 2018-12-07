package com.example.appjo.moviebox.Adapters;

import android.arch.paging.AsyncPagedListDiffer;
import android.arch.paging.PagedList;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.example.appjo.moviebox.Models.MovieModel;
import com.example.appjo.moviebox.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> implements ListPreloader.PreloadModelProvider<String> {

    Context context;
    private ViewPreloadSizeProvider<Integer> preloadSizeProvider;
    private List<MovieModel> movies = new ArrayList<MovieModel>();
    public DashboardAdapter(Context context, ViewPreloadSizeProvider preloadSizeProvider) {
        this.context = context;
        this.preloadSizeProvider = preloadSizeProvider;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        ViewHolder vh = new ViewHolder(v);

        preloadSizeProvider.setView(vh.movieImage);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieModel movie = movies.get(position);
        if (movie != null){
            Glide.with(context)
                    .load("http://image.tmdb.org/t/p/w780" + movie.getPoster_path())
                    .into(holder.movieImage);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void submitList(List<MovieModel> data){
        movies = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public List<String> getPreloadItems(int position) {
        return Collections.singletonList("http://image.tmdb.org/t/p/w780" + movies.get(position).getPoster_path());
    }

    @Nullable
    @Override
    public RequestBuilder<?> getPreloadRequestBuilder(@NonNull String item) {
        return Glide.with(context)
                .load(item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView movieImage;

        public ViewHolder(View v){
            super(v);
            movieImage = v.findViewById(R.id.movieImage);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
