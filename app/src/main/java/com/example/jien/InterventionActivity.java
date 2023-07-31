/*
 * MIT License
 *
 * Copyright (c) 2023 RUB-SE-LAB
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example.jien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

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
        new TopBarHelper(this);

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