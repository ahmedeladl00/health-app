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