package com.example.jien;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class VideoPlayerActivity extends AppCompatActivity implements  Player.Listener{

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
                player.play();
            }
        }
        player.addListener(this);


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

    @Override
    public void onIsPlayingChanged(boolean isPlaying) {
        if (!isPlaying) {
            new MaterialAlertDialogBuilder(VideoPlayerActivity.this)
                    .setTitle("Intervention Recording")
                    .setMessage("Are you ready to record this intervention ?")
                    .setPositiveButton("START", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(VideoPlayerActivity.this, InterventionRecordActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
    }



}