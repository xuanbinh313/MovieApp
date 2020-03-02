package com.example.movieapp.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.Model.PersonResponseResults;
import com.example.movieapp.R;
import com.example.movieapp.ViewHolders.SearchAdapterHolder;

import java.util.List;

public class PersonSearchAdapter extends RecyclerView.Adapter<SearchAdapterHolder> {
    private Activity activity;
    private List<PersonResponseResults> results;

    public PersonSearchAdapter(Activity activity, List<PersonResponseResults> results) {
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
        PersonResponseResults responseResults = results.get(position);
        holder.setPosterImageView(activity,responseResults.getProfile_path());
        String title = responseResults.getName();
        if (title != null){
            holder.posterTitle.setVisibility(View.VISIBLE);
            holder.posterTitle.setText(title);
        }
        else {
            holder.posterTitle.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
