package com.example.jien;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InterventionListActivity extends AppCompatActivity implements InterventionAdapter.OnItemClickListener {

    private InterventionAdapter adapter;
    private DatabaseHelper dbHelper;
    private List<Intervention> interventions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervention_list);

        RecyclerView recyclerView = findViewById(R.id.interActItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DatabaseHelper(this);
        Intervention intervention = new Intervention();
        interventions = intervention.fetchDataFromDatabase(dbHelper,"Intervention","intervention");
        adapter = new InterventionAdapter(interventions, this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDeleteClick(int position) {
        Intervention intervention = interventions.get(position);
        intervention.removeInterventionFromDatabase(dbHelper,"Intervention",intervention.getId());
        interventions.remove(position);
        adapter.notifyItemRemoved(position);
    }

}