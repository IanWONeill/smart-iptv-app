package com.nst.yourname.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.CustomUIController;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;

public class YouTubePlayerActivity extends AppCompatActivity implements View.OnClickListener {
    public boolean buffering = false;
    private CustomUIController customUIController;
    Handler handler;
    public boolean isPlaying = false;
    @BindView(R.id.controls)
    RelativeLayout llcontrol;
    @BindView(R.id.exo_pause)
    AppCompatImageView pauseBT;
    private AppCompatImageView pauseButton;
    @BindView(R.id.exo_play)
    AppCompatImageView playBT;
    private AppCompatImageView playButton;
    public YouTubePlayer youTubePlayer;
    private YouTubePlayerView youTubePlayerView;
    public String youTubeTrailer = "";

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_you_tube_player);
        ButterKnife.bind(this);
        this.youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);
        this.youTubePlayerView.addView(getLayoutInflater().inflate((int) R.layout.youtubecustonui, (ViewGroup) null));
        this.playBT = (AppCompatImageView) findViewById(R.id.exo_play);
        this.pauseBT = (AppCompatImageView) findViewById(R.id.exo_pause);
        findViewById(R.id.exo_play).setOnClickListener(this);
        findViewById(R.id.exo_pause).setOnClickListener(this);
        intialize();
    }

    @SuppressLint({"ResourceType"})
    private void intialize() {
        this.handler = new Handler();
        this.youTubePlayerView.getPlayerUIController();
        Intent intent = getIntent();
        if (intent != null) {
            this.youTubeTrailer = intent.getStringExtra(AppConst.YOUTUBE_TRAILER);
        }
        this.youTubePlayerView.getPlayerUIController().showFullscreenButton(false);
        if ((getResources().getConfiguration().screenLayout & 15) == 3) {
            this.youTubePlayerView.getPlayerUIController().showPlayPauseButton(false);
        } else {
            this.youTubePlayerView.getPlayerUIController().showPlayPauseButton(true);
        }
        this.youTubePlayerView.getPlayerUIController().showYouTubeButton(false);
        this.youTubePlayerView.getPlayerUIController().showVideoTitle(true);
        this.youTubePlayerView.initialize(new YouTubePlayerInitListener() {
            /* class com.nst.yourname.view.activity.YouTubePlayerActivity.AnonymousClass1 */

            @Override // com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerInitListener
            public void onInitSuccess(final YouTubePlayer youTubePlayer) {
                youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    /* class com.nst.yourname.view.activity.YouTubePlayerActivity.AnonymousClass1.AnonymousClass1 */

                    @Override // com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener, com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerListener
                    public void onError(int i) {
                    }

                    @Override // com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener, com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerListener
                    public void onReady() {
                        String access$000 = YouTubePlayerActivity.this.youTubeTrailer;
                        YouTubePlayer unused = YouTubePlayerActivity.this.youTubePlayer = youTubePlayer;
                        if (access$000 != null && !access$000.isEmpty()) {
                            YouTubePlayerActivity.this.findViewById(R.id.exo_play).setVisibility(4);
                            YouTubePlayerActivity.this.findViewById(R.id.exo_pause).setVisibility(0);
                            youTubePlayer.loadVideo(access$000, 0.0f);
                            boolean unused2 = YouTubePlayerActivity.this.isPlaying = true;
                        }
                    }

                    @Override // com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener, com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerListener
                    public void onStateChange(int i) {
                        if (i == 0) {
                            YouTubePlayerActivity.this.finish();
                        }
                        if (i == 1) {
                            boolean unused = YouTubePlayerActivity.this.isPlaying = true;
                            boolean unused2 = YouTubePlayerActivity.this.buffering = false;
                        }
                        if (i == 2) {
                            boolean unused3 = YouTubePlayerActivity.this.buffering = false;
                            boolean unused4 = YouTubePlayerActivity.this.isPlaying = false;
                        }
                    }
                });
            }
        }, true);
        this.handler.removeCallbacksAndMessages(null);
        this.handler.postDelayed(new Runnable() {
            /* class com.nst.yourname.view.activity.YouTubePlayerActivity.AnonymousClass2 */

            public void run() {
                YouTubePlayerActivity.this.findViewById(R.id.controls).setVisibility(4);
            }
        }, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exo_pause:
                if (this.youTubePlayer != null) {
                    this.youTubePlayer.pause();
                    findViewById(R.id.exo_play).setVisibility(0);
                    findViewById(R.id.exo_pause).setVisibility(4);
                    this.pauseBT.clearFocus();
                    this.playBT.requestFocus();
                    return;
                }
                return;
            case R.id.exo_play:
                if (this.youTubePlayer != null) {
                    this.youTubePlayer.play();
                    findViewById(R.id.exo_play).setVisibility(4);
                    findViewById(R.id.exo_pause).setVisibility(0);
                    this.playBT.clearFocus();
                    this.pauseBT.requestFocus();
                    return;
                }
                return;
            default:
                return;
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onPause() {
        super.onPause();
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity
    public void onDestroy() {
        super.onDestroy();
        if (this.youTubePlayerView != null) {
            this.youTubePlayerView.release();
        }
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        keyEvent.getRepeatCount();
        if (i != 23 && i != 66) {
            return super.onKeyUp(i, keyEvent);
        }
        findViewById(R.id.controls).setVisibility(0);
        if (findViewById(R.id.controls).getVisibility() == 0) {
            if (this.isPlaying && this.youTubePlayer != null) {
                this.isPlaying = false;
                this.youTubePlayer.pause();
                findViewById(R.id.exo_play).setVisibility(0);
                findViewById(R.id.exo_pause).setVisibility(4);
                this.playBT.requestFocus();
                this.pauseBT.clearFocus();
            } else if (this.youTubePlayer != null) {
                this.isPlaying = true;
                this.youTubePlayer.play();
                findViewById(R.id.exo_play).setVisibility(4);
                findViewById(R.id.exo_pause).setVisibility(0);
                this.pauseBT.requestFocus();
                this.playBT.clearFocus();
            }
        }
        this.handler.removeCallbacksAndMessages(null);
        this.handler.postDelayed(new Runnable() {
            /* class com.nst.yourname.view.activity.YouTubePlayerActivity.AnonymousClass3 */

            public void run() {
                YouTubePlayerActivity.this.findViewById(R.id.controls).setVisibility(4);
            }
        }, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
        return true;
    }

    @Override // android.support.v7.app.AppCompatActivity
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        keyEvent.getRepeatCount();
        keyEvent.getAction();
        if (i == 23 || i == 66) {
            return true;
        }
        switch (i) {
            case 166:
                return true;
            case 167:
                return true;
            default:
                return super.onKeyDown(i, keyEvent);
        }
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        boolean z = keyEvent.getAction() == 0;
        if (keyCode == 23) {
            return z ? onKeyDown(keyCode, keyEvent) : onKeyUp(keyCode, keyEvent);
        }
        return super.dispatchKeyEvent(keyEvent);
    }
}
