package com.example.jien;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

public class VideoPlayerActivity extends AppCompatActivity {

    private PlayerView playerView;
    private ExoPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        playerView = findViewById(R.id.player_view);

        Intent intent = getIntent();
        if (intent != null) {
            String videoName = intent.getStringExtra("videoName");
            if (videoName != null) {
                setTitle(videoName);
                MediaItem mediaItem = MediaItem.fromUri(getUri(videoName));

                player = new ExoPlayer.Builder(this).build();
                playerView.setPlayer(player);
                player.setMediaItem(mediaItem);
                player.prepare();
            }
        }


    }

    private Uri getUri(String videoName) {
        Uri videoUri = null;
        int videoResourceId = getResources().getIdentifier(videoName, "raw", getPackageName());
        if (videoResourceId != 0) {
            String videoPath = "android.resource://" + getPackageName() + "/" + videoResourceId;
            videoUri = Uri.parse(videoPath);
        }
        return videoUri;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }


}