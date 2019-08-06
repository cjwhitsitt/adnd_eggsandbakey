package com.jaywhitsitt.eggsandbakey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.security.InvalidParameterException;

import javax.sql.DataSource;

public class VideoActivity extends AppCompatActivity {

    public static final String EXTRA_VIDEO_URL = "videoUrl";

    private static final String OUT_STATE_PLAYING = "playing";
    private static final String OUT_STATE_CURRENT_POSITION = "position";

    private SimpleExoPlayer mPlayer;
    private PlayerView mPlayerView;

    private String mVideoUrl;
    private boolean mSavedPlayingState = true;
    private long mSavedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent intent = getIntent();
        if (!intent.hasExtra(EXTRA_VIDEO_URL)) {
            throw new InvalidParameterException("EXTRA_VIDEO_URL missing but required");
        }
        mVideoUrl = intent.getStringExtra(EXTRA_VIDEO_URL);

        mPlayerView = findViewById(R.id.player);
        if (mPlayerView == null) {
            throw new RuntimeException("Unable to find player view");
        }

        if (savedInstanceState != null) {
            mSavedPlayingState = savedInstanceState.getBoolean(OUT_STATE_PLAYING, true);
            mSavedPosition = savedInstanceState.getLong(OUT_STATE_CURRENT_POSITION, 0);
        }
    }

    private void initializePlayer() {
        if (mPlayer == null) {
            mPlayer = ExoPlayerFactory.newSimpleInstance(this);
            mPlayer.setPlayWhenReady(mSavedPlayingState);
            mPlayerView.setPlayer(mPlayer);
        }

        boolean hasPosition = mSavedPosition > 0;
        if (hasPosition) {
            mPlayer.seekTo(mSavedPosition);
        }

        DefaultDataSourceFactory factory = new DefaultDataSourceFactory(
                this,
                Util.getUserAgent(this, getString(R.string.app_name)));
        MediaSource source = new ProgressiveMediaSource.Factory(factory)
                .createMediaSource(Uri.parse(mVideoUrl));

        mPlayer.prepare(source, !hasPosition, false);
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mSavedPosition = mPlayer.getCurrentPosition();
            mSavedPlayingState = mPlayer.getPlayWhenReady();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 && mPlayer == null) {
            initializePlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(OUT_STATE_PLAYING, mPlayer.getPlayWhenReady());
        outState.putLong(OUT_STATE_CURRENT_POSITION, mPlayer.getCurrentPosition());
    }
}
