package com.example.movieapp.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.Model.MovieResponseResults;
import com.example.movieapp.MovieDetailActivity;
import com.example.movieapp.R;
import com.example.movieapp.ViewHolders.SearchAdapterHolder;

import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<SearchAdapterHolder> {
    private Activity activity;
    private List<MovieResponseResults> results;

    public MovieSearchAdapter(Activity activity, List<MovieResponseResults> results) {
        this.activity = activity;
        this.results = results;
    }

    @NonNull
    @Override
    public SearchAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.search_layout_items,parent,false);
        return new SearchAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapterHolder holder, int position) {
        MovieResponseResults responseResults = results.get(position);
        holder.setPosterImageView(activity,responseResults.getPoster_path());
        String title = responseResults.getTitle();
        if (title != null){
            holder.posterTitle.setVisibility(View.VISIBLE);
            holder.posterTitle.setText(title);
        }
        else {
            holder.posterTitle.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MovieDetailActivity.class);
                intent.putExtra("id",String.valueOf(responseResults.getId()));
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
