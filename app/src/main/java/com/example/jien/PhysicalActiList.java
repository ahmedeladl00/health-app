package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class PhysicalActiList extends AppCompatActivity {
    PhysicalActivities physicalActivities;
   // private ArrayList<String> activitiesList;
  //  private ArrayAdapter<String> activitiesAdapter;
    private PhysicalsDatabaseHelper physicalsDatabaseHelper;
    private SQLiteDatabase database;
    ListView listViewActivities;
    TextView listName;
    TextView activityName;
    TextView timeFrOm;
    TextView timeTO;
    TextView dayy;

    PhysicalBaseAdapter adapter;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_acti_list);
        listViewActivities= findViewById(R.id.physicalacti);
        listName=findViewById(R.id.textView3);
        activityName=findViewById(R.id.activityname);
        timeFrOm=findViewById(R.id.timefrom);
        timeTO=findViewById(R.id.timeto);
        dayy=findViewById(R.id.day);


       // activitiesList = new ArrayList<>();
        //activitiesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, activitiesList);
        listViewActivities.setAdapter(adapter);
        physicalsDatabaseHelper=new PhysicalsDatabaseHelper(this);

         database = physicalsDatabaseHelper.getReadableDatabase();
      /* Cursor cursor = database.query("Physical_Data", null, null, null, null, null, null);

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
        database.close();*/

        // Create and set the adapter for the list view
        //adapter = new PhysicalBaseAdapter(this, listActivities, listInfos);
        //activitiesList.setAdapter(adapter);

        loadActivitiesFromDatabase();

    }

    private void loadActivitiesFromDatabase() {
      /*  Cursor cursor = database.query("Physical_Data", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String activity = cursor.getString(cursor.getColumnIndex("activity"));
            @SuppressLint("Range") long timeFrom = cursor.getLong(cursor.getColumnIndex("timeFrom"));
            @SuppressLint("Range") long timeTo = cursor.getLong(cursor.getColumnIndex("timeTo"));

            String activityWithTime = activity + ": " + formatTime(timeFrom) + " - " + formatTime(timeTo);
            activitiesList.add(activityWithTime);
        }

        cursor.close();
        activitiesAdapter.notifyDataSetChanged();*/
      /*  Cursor cursor = database.query("Physical_Data", null, null, null, null, null, null);

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
        database.close();

        // Create and set the adapter for the list view
        adapter = new PhysicalBaseAdapter(this, listActivities, listInfos);
        listViewActivities.setAdapter(adapter);*/

            Cursor cursor = database.query("Physical_Data", null, null, null, null, null, null);

            ArrayList<String> activityNames = new ArrayList<>();
            ArrayList<String> activityInfos = new ArrayList<>();

            while (cursor.moveToNext()) {
                @SuppressLint("Range") String activityName = cursor.getString(cursor.getColumnIndex("activity"));
                @SuppressLint("Range") long timeFrom = cursor.getLong(cursor.getColumnIndex("timeFrom"));
                @SuppressLint("Range") long timeTo = cursor.getLong(cursor.getColumnIndex("timeTo"));
                @SuppressLint("Range") long dayInMillis = cursor.getLong(cursor.getColumnIndex("day"));
                Date day = new Date(dayInMillis);

                String timeFromFormatted = formatTime(timeFrom);
                String timeToFormatted = formatTime(timeTo);
                String dayFormatted = day.toString();

                activityNames.add(activityName);
                activityInfos.add("From: " + timeFromFormatted + " To: " + timeToFormatted + " Day: " + dayFormatted);
            }

            cursor.close();
            database.close();

            // Convert ArrayLists to arrays
            String[] listActivities = activityNames.toArray(new String[0]);
            String[] listInfos = activityInfos.toArray(new String[0]);

            // Create and set the adapter for the list view
            adapter = new PhysicalBaseAdapter(this, listActivities, listInfos);
            listViewActivities.setAdapter(adapter);
        }


    private String formatTime(long timeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date(timeMillis));
    }
    }
