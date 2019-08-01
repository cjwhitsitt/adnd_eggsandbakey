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
        mPlayer.prepare(source);
        mPlayer.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();
    }
}
