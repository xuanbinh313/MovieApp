package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.codewaves.youtubethumbnailview.ThumbnailLoader;
import com.codewaves.youtubethumbnailview.ThumbnailView;
import com.example.movieapp.Model.MovieVideosResults;
import com.example.movieapp.Utils.FullScreenHelper;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class VideoPlayActivity extends AppCompatActivity
{
    private ThumbnailView thumbnailView;
    private YouTubePlayerView playerView;
    private ProgressBar progressBar;
    private RecyclerView otherVideosRecyclerView;
    private FullScreenHelper fullScreenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        Intent intent = getIntent();
        ThumbnailLoader.initialize(BuildConfig.GOOGLE_CLOUD_API_KEY);
        fullScreenHelper = new FullScreenHelper(this);
        thumbnailView = findViewById(R.id.video_thumbnail_view);

        AppCompatTextView videoTitle = findViewById(R.id.play_video_title);
        AppCompatTextView noResultsFound = findViewById(R.id.no_result_found);

        otherVideosRecyclerView = findViewById(R.id.other_videos_recycler_view);
        otherVideosRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        progressBar = findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().setColorFilter(0XFFFFFFFF, PorterDuff.Mode.MULTIPLY);

        //now get the arraylist and position

        if (intent != null & intent.getExtras() != null)
        {
            ArrayList<MovieVideosResults> movieVideosResultsArrayList = intent.getExtras().getParcelableArrayList("video");
            int position = Integer.parseInt(intent.getExtras().getString("position"));
            if (movieVideosResultsArrayList != null && movieVideosResultsArrayList.size() > 0)
            {
                String videoId = movieVideosResultsArrayList.get(position).getKey();
                String title = movieVideosResultsArrayList.get(position).getName();
                if (title != null)
                {
                    videoTitle.setText(title);
                }

                if (videoId != null)
                {
                    String baseUrl = "https://www.youtube.com/watch?v=";
                    thumbnailView.loadThumbnail(baseUrl + videoId);
                    playerView.initialize(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(YouTubePlayer youTubePlayer) {
                            super.onReady(youTubePlayer);
                            // when video is ready to play hide the thumbnail and progress bar
                            thumbnailView.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            //show the youtube player

                            playerView.setVisibility(View.VISIBLE);
                            if (getLifecycle().getCurrentState() == Lifecycle.State.RESUMED)
                            {
                                youTubePlayer.loadVideo(videoId,0);
                            }
                            else
                            {
                                youTubePlayer.cueVideo(videoId,0);
                            }
                        }
                    });
                }
            }
        }
    }
}
