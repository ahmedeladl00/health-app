package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActiPhysList extends AppCompatActivity implements InterventionAdapter.OnItemClickListener{


    private List<IABase> activities;
    private InterventionAdapter adapter;
    private PhysicalsDatabaseHelper physicalsData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_phys_list);

        RecyclerView recyclerView = findViewById(R.id.interActItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        physicalsData = new PhysicalsDatabaseHelper(this);
        Intervention intervention = new Intervention();
        activities = intervention.fetchDataFromDatabase(physicalsData,"Physical_Data","activity");
        adapter = new InterventionAdapter(activities, this);

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