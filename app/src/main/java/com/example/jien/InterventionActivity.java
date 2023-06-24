package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

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