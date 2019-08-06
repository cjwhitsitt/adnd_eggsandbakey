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

    private SimpleExoPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent intent = getIntent();
        if (!intent.hasExtra(EXTRA_VIDEO_URL)) {
            throw new InvalidParameterException("EXTRA_VIDEO_URL missing but required");
        }
        String videoUrl = intent.getStringExtra(EXTRA_VIDEO_URL);

        mPlayer = ExoPlayerFactory.newSimpleInstance(this);
        PlayerView view = findViewById(R.id.player);
        if (view == null) {
            throw new RuntimeException("Unable to find player view");
        }
        view.setPlayer(mPlayer);

        DefaultDataSourceFactory factory = new DefaultDataSourceFactory(
                this,
                Util.getUserAgent(this, getString(R.string.app_name)));
        MediaSource source = new ProgressiveMediaSource.Factory(factory)
                .createMediaSource(Uri.parse(videoUrl));


        if (savedInstanceState != null) {
            mPlayer.setPlayWhenReady(savedInstanceState.getBoolean(OUT_STATE_PLAYING, true));
            mPlayer.seekTo(savedInstanceState.getLong(OUT_STATE_CURRENT_POSITION, 0));
            mPlayer.prepare(source, false, false);
        } else {
            mPlayer.setPlayWhenReady(true);
            mPlayer.prepare(source);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayer.setPlayWhenReady(false);
    }

    private static final String OUT_STATE_PLAYING = "playing";
    private static final String OUT_STATE_CURRENT_POSITION = "position";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(OUT_STATE_PLAYING, mPlayer.getPlayWhenReady());
        outState.putLong(OUT_STATE_CURRENT_POSITION, mPlayer.getCurrentPosition());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();
    }
}
