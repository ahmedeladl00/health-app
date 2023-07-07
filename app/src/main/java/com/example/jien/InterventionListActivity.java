package com.example.jien;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InterventionListActivity extends AppCompatActivity implements InterActAdapter.OnItemClickListener {

    private InterActAdapter adapter;
    private DatabaseHelper dbHelper;
    private List<IABase> interventions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervention_list);

        RecyclerView recyclerView = findViewById(R.id.interActItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DatabaseHelper(this);
        Intervention intervention = new Intervention();
        interventions = intervention.fetchDataFromDatabase(dbHelper,"Intervention","intervention");
        adapter = new InterActAdapter(interventions, this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDeleteClick(int position) {
        IABase intervention = interventions.get(position);
        intervention.removeInterventionFromDatabase(dbHelper,"Intervention",intervention.getId());
        interventions.remove(position);
        adapter.notifyItemRemoved(position);
    }

}