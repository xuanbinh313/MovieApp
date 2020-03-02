package com.example.movieapp.ViewHolders;

import android.content.Context;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.squareup.picasso.Picasso;

public class SearchAdapterHolder extends RecyclerView.ViewHolder {
    private KenBurnsView posterImageView;
    public AppCompatTextView posterTitle;

    public SearchAdapterHolder(@NonNull View itemView) {
        super(itemView);
        posterImageView = itemView.findViewById(R.id.poster_image_view);
        posterTitle = itemView.findViewById(R.id.poster_title);

        RandomTransitionGenerator generator = new RandomTransitionGenerator(1000, new DecelerateInterpolator());
        posterImageView.setTransitionGenerator(generator);
    }

    public void setPosterImageView(Context context, String posterUrl) {
        Picasso.with(context).load(posterUrl).into(posterImageView);
    }
}
