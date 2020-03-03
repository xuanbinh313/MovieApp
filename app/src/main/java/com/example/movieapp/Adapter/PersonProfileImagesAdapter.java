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
import com.example.movieapp.Model.PersonImagesProfiles;
import com.example.movieapp.R;
import com.example.movieapp.ViewHolders.PersonProfileImagesViewHolder;

import java.util.List;

public class PersonProfileImagesAdapter extends RecyclerView.Adapter<PersonProfileImagesViewHolder> {
    private Activity activity;
    private List<PersonImagesProfiles> profileImageList;

    public PersonProfileImagesAdapter(Activity activity, List<PersonImagesProfiles> profileImageList) {
        this.activity = activity;
        this.profileImageList = profileImageList;
    }

    @NonNull
    @Override
    public PersonProfileImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.profile_images_layout,parent,false);
        return new PersonProfileImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonProfileImagesViewHolder holder, int position) {
        PersonImagesProfiles personImagesProfiles = profileImageList.get(position);
        holder.setProfileImage(activity,personImagesProfiles.getFile_path());
        holder.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ImageViewerActivity.class);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,holder.profileImage, ViewCompat.getTransitionName(holder.profileImage));
                intent.putExtra("image_url",personImagesProfiles.getFile_path());
                activity.startActivity(intent,compat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return profileImageList.size();
    }
}
