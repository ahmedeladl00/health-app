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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class ActiPhysList extends AppCompatActivity implements InterActAdapter.OnItemClickListener{


    private List<IABase> activities;
    private InterActAdapter adapter;
    private PhysicalsDatabaseHelper physicalsData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_phys_list);

        RecyclerView recyclerView = findViewById(R.id.interActItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        physicalsData = new PhysicalsDatabaseHelper(this);
        PhysicalActivities activity = new PhysicalActivities();
        activities = activity.fetchDataFromDatabase(physicalsData,"Physical_Data","activity");
        adapter = new InterActAdapter(activities, this);

        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onDeleteClick(int position) {
        IABase activity = activities.get(position);
        activity.removeInterventionFromDatabase(physicalsData,"Physical_Data",activity.getId());
        activities.remove(position);
        adapter.notifyItemRemoved(position);
    }
}