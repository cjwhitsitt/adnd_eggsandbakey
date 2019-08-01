package com.jaywhitsitt.eggsandbakey.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.material.snackbar.Snackbar;
import com.jaywhitsitt.eggsandbakey.StepDetailActivity;
import com.jaywhitsitt.eggsandbakey.VideoActivity;
import com.jaywhitsitt.eggsandbakey.data.Step;

import java.security.InvalidParameterException;

public class PlaybackUtils {

    public static void setupPlayFab(Context context, Step step, View view) {
        boolean hasVideo = step != null && step.videoUrl != null && step.videoUrl.length() > 0;
        view.setVisibility(hasVideo ? View.VISIBLE : View.GONE);
        if (hasVideo) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startVideo(context, step.videoUrl);
                }
            });
        }
    }

    public static void startVideo(Context context, String url) {
        // TODO: verify valid url
        if (url == null || url.length() <= 0) {
            throw new InvalidParameterException("url parameter is required but invalid");
        }

        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(VideoActivity.EXTRA_VIDEO_URL, url);
        context.startActivity(intent);
    }

}
