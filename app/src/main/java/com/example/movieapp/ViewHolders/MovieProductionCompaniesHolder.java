package com.example.movieapp.ViewHolders;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

public class MovieProductionCompaniesHolder extends RecyclerView.ViewHolder
{
    private KenBurnsView productionCompanyImageView;
    public AppCompatTextView productionCompanyName;

    public MovieProductionCompaniesHolder(@NonNull View itemView)
    {
        super(itemView);
        productionCompanyImageView =itemView.findViewById(R.id.production_company_image_view);
        productionCompanyName = itemView.findViewById(R.id.production_company_name);
    }

    public void setproductionCompanyImageView(Context context, String imageUrl)
    {
        Picasso.with(context).load(imageUrl).into(productionCompanyImageView);
    }
}
