package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;



public class PhysicalActiList extends AppCompatActivity {

    PhysicalsDatabaseHelper physicalsDatabaseHelper;
    ListView activitiesList;
    ArrayList<PhysicalActivities> activitiesArrayList;


    PhysicalBaseAdapter adapter;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_acti_list);
        activitiesList= findViewById(R.id.physicalacti);
        activitiesArrayList=new ArrayList<>();
        physicalsDatabaseHelper=new PhysicalsDatabaseHelper(this);
        activitiesList=(ListView) findViewById(R.id.physicalacti);
        SQLiteDatabase db = physicalsDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.query("Physical_Data", null, null, null, null, null, null);

        String[] listActivities = new String[cursor.getCount()];
        String[] listInfos = new String[cursor.getCount()];

        int i = 0;
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String activity = cursor.getString(cursor.getColumnIndex("activity"));
            @SuppressLint("Range") long timeFrom = cursor.getLong(cursor.getColumnIndex("timeFrom"));
            @SuppressLint("Range") long timeTo = cursor.getLong(cursor.getColumnIndex("timeTo"));
            @SuppressLint("Range") long dayInMillis = cursor.getLong(cursor.getColumnIndex("day"));
            Date day = new Date(dayInMillis);

            listActivities[i] = activity;
            listInfos[i] = "From: " + timeFrom + " To: " + timeTo + " Day: " + day.toString();
            i++;
        }

        cursor.close();
        db.close();

        // Create and set the adapter for the list view
        adapter = new PhysicalBaseAdapter(this, listActivities, listInfos);
        activitiesList.setAdapter(adapter);


    }
}