package com.example.movieapp;

import androidx.annotation.NonNull;
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
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import com.codewaves.youtubethumbnailview.ThumbnailLoader;
import com.codewaves.youtubethumbnailview.ThumbnailView;
import com.example.movieapp.Adapter.ExtraVideosRecyclerAdapter;
import com.example.movieapp.Model.MovieVideosResults;
import com.example.movieapp.Utils.FullScreenHelper;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class VideoPlayActivity extends YouTubeBaseActivity {
    private ThumbnailView thumbnailView;
    private YouTubePlayerView playerView;
    private ProgressBar progressBar;
    private RecyclerView otherVideosRecyclerView;
    private FullScreenHelper fullScreenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        Intent intent = getIntent();
        ThumbnailLoader.initialize(BuildConfig.GOOGLE_CLOUD_API_KEY);
        fullScreenHelper = new FullScreenHelper(this);
        thumbnailView = findViewById(R.id.video_thumbnail_view);
        playerView = findViewById(R.id.video_player_view);
//        getLifecycle().addObserver(playerView);

        AppCompatTextView videoTitle = findViewById(R.id.play_video_title);
        AppCompatTextView noResultsFound = findViewById(R.id.no_result_found);

        otherVideosRecyclerView = findViewById(R.id.other_videos_recycler_view);
        otherVideosRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

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
                if (title != null) {
                    videoTitle.setText(title);
                }

                if (videoId != null) {
                    String baseUrl = "https://www.youtube.com/watch?v=";
                    thumbnailView.loadThumbnail(baseUrl + videoId);
                    playerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(YouTubePlayer youTubePlayer) {
                            super.onReady(youTubePlayer);
//                        When video is ready to play hide the thumbnail and progress bar
                            thumbnailView.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            //show the youtube player view
                            playerView.setVisibility(View.VISIBLE);
//                            if (getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {
                                youTubePlayer.loadVideo(videoId, 0);
//                            } else {
//                                youTubePlayer.cueVideo(videoId, 0);
//                            }
                        }
                    });
//                    playerView.getYouTubePlayerWhenReady(youTubePlayer -> {
////                        When video is ready to play hide the thumbnail and progress bar
//                            thumbnailView.setVisibility(View.GONE);
//                            progressBar.setVisibility(View.GONE);
//                            //show the youtube player view
//                            playerView.setVisibility(View.VISIBLE);
//                            if (getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {
//                                youTubePlayer.loadVideo(videoId, 0);
//                            } else {
//                                youTubePlayer.cueVideo(videoId, 0);
//                            }
//                    });
//                    playerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//                        @Override
//                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                            //When video is ready to play hide the thumbnail and progress bar
//                            thumbnailView.setVisibility(View.GONE);
//                            progressBar.setVisibility(View.GONE);
//                            //show the youtube player view
//                            playerView.setVisibility(View.VISIBLE);
//                            if (getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {
//                                youTubePlayer.loadVideo(videoId, 0);
//                            } else {
//                                youTubePlayer.cueVideo(videoId, 0);
//                            }
//                        }
//                    });
//                    playerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
//                        @Override
//                        public void onYouTubePlayerEnterFullScreen() {
//                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
//                            fullScreenHelper.enterFullScreen();
//                        }
//
//                        @Override
//                        public void onYouTubePlayerExitFullScreen() {
//                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
//                            fullScreenHelper.enterFullScreen();
//                        }
//                    });

                    //Load other videos in recycler view
                    ArrayList<MovieVideosResults> movieVideosResultsList = new ArrayList<>(movieVideosResultsArrayList);
                    //removie current video from list
                    movieVideosResultsList.remove(position);
                    if (movieVideosResultsList.size() > 0) {
                        noResultsFound.setVisibility(View.GONE);
                        ExtraVideosRecyclerAdapter adapter = new ExtraVideosRecyclerAdapter(VideoPlayActivity.this,movieVideosResultsList);
                        otherVideosRecyclerView.setAdapter(adapter);
                        otherVideosRecyclerView.setVisibility(View.VISIBLE);
                        //create amination for the loading items
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_slide_bottom);
                        otherVideosRecyclerView.setLayoutAnimation(controller);
                        otherVideosRecyclerView.scheduleLayoutAnimation();
                    } else {
                        noResultsFound.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }
    //exit fullscreen  on back pressed

    @Override
    public void onBackPressed()
    {
        if (playerView.isFullScreen())
        {
            playerView.exitFullScreen();
        }
        else
            {
            otherVideosRecyclerView.setVisibility(View.GONE);
            playerView.setVisibility(View.GONE);
            thumbnailView.setVisibility(View.VISIBLE);
            super.onBackPressed();
        }
    }
}
