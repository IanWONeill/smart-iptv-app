package com.nst.yourname.model;

import android.view.View;
import android.widget.Button;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;

public class CustomUIController {
    private Button playPauseButton;
    private View playerUI;
    private boolean playing = false;
    private YouTubePlayer youTubePlayer;
    private YouTubePlayerView youTubePlayerView;

    private void initViews(View view) {
    }

    public CustomUIController(View view, YouTubePlayer youTubePlayer2, YouTubePlayerView youTubePlayerView2) {
        this.playerUI = view;
        this.youTubePlayer = youTubePlayer2;
        this.youTubePlayerView = youTubePlayerView2;
        initViews(view);
    }
}
