package com.example.appjo.moviebox.Adapters;

import android.arch.paging.AsyncPagedListDiffer;
import android.arch.paging.PagedList;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appjo.moviebox.Models.MovieModel;
import com.example.appjo.moviebox.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchItemViewHolder> {
    private Context context;
    private final AsyncPagedListDiffer<MovieModel> mDiffer
            = new AsyncPagedListDiffer(this, DIFF_CALLBACK);

    public SearchAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);
        return new SearchItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
        MovieModel movie = mDiffer.getItem(position);
        if (movie != null){
            holder.searchMovieTitle.setText(movie.getOriginal_title());
            holder.searchMovieRating.setText(movie.getVote_average());
            Glide.with(context)
                    .load("http://image.tmdb.org/t/p/w780" + movie.getPoster_path())
                    .into(holder.searchMovieImage);
        }
    }

    @Override
    public int getItemCount() {
        return mDiffer.getItemCount();
    }

    public void submitList(PagedList<MovieModel> pagedList) {
        mDiffer.submitList(pagedList);
        notifyDataSetChanged();
    }

    public static class SearchItemViewHolder extends RecyclerView.ViewHolder{
        ImageView searchMovieImage;
        TextView searchMovieTitle;
        TextView searchMovieRating;

        public SearchItemViewHolder(View v){
            super(v);
            searchMovieImage = v.findViewById(R.id.search_movie_image);
            searchMovieTitle = v.findViewById(R.id.search_movie_text);
            searchMovieRating = v.findViewById(R.id.search_movie_rating);
        }
    }

    public static final DiffUtil.ItemCallback<MovieModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MovieModel>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull MovieModel oldMovie, @NonNull MovieModel newMovie) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldMovie.getId() == newMovie.getId();
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull MovieModel oldMovie, @NonNull MovieModel newMovie) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldMovie.equals(newMovie);
                }
            };
}
