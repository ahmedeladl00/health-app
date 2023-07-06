package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActiPhysList extends AppCompatActivity {

    TextView listOf;
    private RecyclerView activitysList;
    // private ArrayList<PhysicalActivities> activities;
    private LocalDate currentday;
    private ActivityAdapter adapter;
    private SQLiteDatabase database;
    PhysicalsDatabaseHelper physicalsData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_phys_list);
        listOf=findViewById(R.id.listof);
        activitysList=findViewById(R.id.activityList);
        List<PhysicalActivities> activities=new ArrayList<>();
        activities = new ArrayList<PhysicalActivities>();
        activities.add(new PhysicalActivities("Running or Jogging", currentday));
        activities.add(new PhysicalActivities("Walking", currentday));
        activities.add(new PhysicalActivities("Swimming", currentday));
        adapter= new ActivityAdapter(activities,getApplicationContext());
        activitysList.setAdapter(adapter);
        activitysList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        physicalsData=new PhysicalsDatabaseHelper(this);
        database= physicalsData.getReadableDatabase();
        // loadActivitiesFromDatabase();
         /* private void loadActivitiesFromDatabase(){
        List<PhysicalActivities> activitiess=new ArrayList<>();
        Cursor cursor = database.query("Physical_Data", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String activityName = cursor.getString(cursor.getColumnIndex("activity"));
            @SuppressLint("Range") String timeFrom = String.valueOf(cursor.getLong(cursor.getColumnIndex("timeFrom")));
            @SuppressLint("Range") String timeTo = String.valueOf(cursor.getLong(cursor.getColumnIndex("timeTo")));
            @SuppressLint("Range") LocalDate dayInMillis = LocalDate.ofEpochDay(cursor.getLong(cursor.getColumnIndex("day")));
            LocalDate date=dayInMillis;
            activitiess.add(new PhysicalActivities(activityName,"From"+timeFrom,"To"+timeTo,date));


        }
        cursor.close();
        database.close();
        adapter=new ActivityAdapter(activitiess,this);
        activitysList.setAdapter(adapter);
   */

    }

}