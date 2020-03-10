package com.example.movieapp.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.ImageViewerActivity;
import com.example.movieapp.Model.MovieImages;
import com.example.movieapp.Model.MovieImagesBackDropsAndPosters;
import com.example.movieapp.Model.PersonImagesProfiles;
import com.example.movieapp.R;
import com.example.movieapp.ViewHolders.ImagesViewHolder;

import java.util.List;

public class MoviePostersImagesAdapter extends RecyclerView.Adapter<ImagesViewHolder>
{
    private Activity activity;
    private List<MovieImagesBackDropsAndPosters> movieImagesBackDropsAndPostersList;

    public MoviePostersImagesAdapter(Activity activity, List<MovieImagesBackDropsAndPosters> movieImagesBackDropsAndPostersList)
    {
        this.activity = activity;
        this.movieImagesBackDropsAndPostersList = movieImagesBackDropsAndPostersList;
    }

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(activity).inflate(R.layout.profile_images_layout,parent,false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position)
    {
        MovieImagesBackDropsAndPosters movieImagesBackDropsAndPosters = movieImagesBackDropsAndPostersList.get(position);
        holder.setProfileImage(activity,movieImagesBackDropsAndPosters.getFile_path());
        holder.profileImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(activity, ImageViewerActivity.class);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,holder.profileImage, ViewCompat.getTransitionName(holder.profileImage));
                intent.putExtra("image_url",movieImagesBackDropsAndPosters.getFile_path());
                activity.startActivity(intent,compat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return movieImagesBackDropsAndPostersList.size();
    }
}
