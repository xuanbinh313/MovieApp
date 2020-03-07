package com.example.movieapp.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.Model.MovieDetailsProductionCompanies;
import com.example.movieapp.PersonDetailActivity;
import com.example.movieapp.R;
import com.example.movieapp.ViewHolders.MovieProductionCompaniesHolder;

import java.util.List;

public class MovieProductionCompaniesAdapter extends RecyclerView.Adapter<MovieProductionCompaniesHolder>
{
    private Activity activity;
    private List<MovieDetailsProductionCompanies> movieDetailsProductionCompaniesList;

    public MovieProductionCompaniesAdapter(Activity activity, List<MovieDetailsProductionCompanies> movieDetailsProductionCompaniesList) {
        this.activity = activity;
        this.movieDetailsProductionCompaniesList = movieDetailsProductionCompaniesList;
    }

    @NonNull
    @Override
    public MovieProductionCompaniesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(activity).inflate(R.layout.production_company_layout,parent,false);
        return new MovieProductionCompaniesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieProductionCompaniesHolder holder, int position)
    {
        final MovieDetailsProductionCompanies movieDetailsProductionCompanies = movieDetailsProductionCompaniesList.get(position);
        holder.setproductionCompanyImageView(activity,movieDetailsProductionCompanies.getLogo_path());
        holder.productionCompanyName.setText(movieDetailsProductionCompanies.getName());
    }

    @Override
    public int getItemCount() {
        return movieDetailsProductionCompaniesList.size();
    }
}
