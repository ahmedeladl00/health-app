package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InterventionActivity extends AppCompatActivity {

    private ListView videoListView;
    private List<Integer> videoResourceIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervention);

        videoListView = findViewById(R.id.videoList);
        videoResourceIds = new ArrayList<>();
        videoResourceIds.add(R.raw.stretching);
        videoResourceIds.add(R.raw.warmup);

        List<String> videoNames = new ArrayList<>();
        for (int resourceId : videoResourceIds) {
            String videoName = getResources().getResourceEntryName(resourceId);
            videoNames.add(videoName);
        }

        Intent intent = getIntent();
        if (intent != null) {
            String videoName = intent.getStringExtra("videoName");

            if (videoName != null) {
                Random random = new Random();
                int randomIndex = random.nextInt(videoNames.size());
                
                String randomVideo = videoNames.get(randomIndex);

                intent = new Intent(InterventionActivity.this, VideoPlayerActivity.class);
                intent.putExtra("videoName", randomVideo);
                startActivity(intent);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, videoNames);
        videoListView.setAdapter(adapter);

        videoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedVideoResourceId = videoResourceIds.get(position);
                String selectedVideoName = videoNames.get(position);
                Intent intent = new Intent(InterventionActivity.this, VideoPlayerActivity.class);
                intent.putExtra("videoName", selectedVideoName);
                startActivity(intent);
            }
        });

    }
}