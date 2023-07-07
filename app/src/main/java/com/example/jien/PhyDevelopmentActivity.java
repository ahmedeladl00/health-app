package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PhyDevelopmentActivity extends AppCompatActivity {


    //initialize variable
   BarChart barChart;
    PieChart pieChart;

    PhysicalsDatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phy_development);

        //Assing variable
        barChart = findViewById(R.id.bar_chart);
        pieChart = findViewById(R.id.pie_chart);
        databaseHelper = new PhysicalsDatabaseHelper(this);


        // Daten aus der Datenbank abrufen
        List<IABase> activities = getPhysicalActivitiesFromDatabase();

        // Bar-Chart erstellen und aktualisieren
       createBarChart(activities);

        // Pie-Chart erstellen und aktualisieren
        createPieChart(activities);
    }


    private List<IABase> getPhysicalActivitiesFromDatabase() {
        // Datenbank abrufen und Cursor initialisieren
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Physical_Data", null);

        PhysicalActivities activity = new PhysicalActivities();
        List<IABase> activities = activity.fetchDataFromDatabase(databaseHelper,"Physical_Data","activity");



        // Cursor durchlaufen und Daten in eine Liste speichern
       /* while (cursor.moveToNext()) {
             Date day = new Date(cursor.getLong(cursor.getColumnIndex("day")));
             long timeFrom = cursor.getLong(cursor.getColumnIndex("timeFrom"));
             long timeTo = cursor.getLong(cursor.getColumnIndex("timeTo"));
             String activity = cursor.getString(cursor.getColumnIndex("activity"));

            PhysicalActivities physicalActivity = new PhysicalActivities(1,day, timeFrom, timeTo, activity);
            activities.add(physicalActivity);
        }*/


       /* PhysicalActivities physicalActivities1 = new PhysicalActivities(1, "jogging","10:10:05","12:13:00", LocalDate.now());
        PhysicalActivities physicalActivities2 = new PhysicalActivities(2, "tanzen","09:13:05","10:20:00",LocalDate.now());
        PhysicalActivities physicalActivities3 = new PhysicalActivities(3, "swimming","08:13:05","09:20:00",LocalDate.now());
        PhysicalActivities physicalActivities4 = new PhysicalActivities(4, "yoga","07:00:00","07:30:00",LocalDate.now());

        activities.add(physicalActivities1);
        activities.add(physicalActivities2);
        activities.add(physicalActivities3);
        activities.add(physicalActivities4);*/


        // Cursor und Datenbankverbindung schließen
        // cursor.close();
        // db.close();

        return activities;
    }

    private void createBarChart(List<IABase> activities) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> activityNames = new ArrayList<>();

        // Convert data from the list into BarEntries and collect activity names
        for (int i = 0; i < activities.size(); i++) {
            IABase activity = activities.get(i);
            String activityName = activity.getName();
            float timeDuration = convertTimeStringToMillis(activity.getTimeTo()) - convertTimeStringToMillis(activity.getTimeFrom());
            barEntries.add(new BarEntry(i, timeDuration));
            activityNames.add(activityName);
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Physical Activities");
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(12f);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.5f);

        // Create X-axis labels
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(activityNames));
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.WHITE);

        // Chart configuration
        barChart.setData(barData);
        barChart.animateXY(2400, 2400);
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getLegend().setEnabled(true);
        barChart.getLegend().setTextColor(Color.WHITE);
        barChart.setFitBars(true);
        barChart.invalidate();
        barChart.animate();
    }




    private void createPieChart(List<IABase> activities) {
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        // Daten aus der Liste in PieEntries umwandeln
        for (IABase activity : activities) {
            String activityName = activity.getName();
            float timeDuration = convertTimeStringToMillis(activity.getTimeTo()) - convertTimeStringToMillis(activity.getTimeFrom());
            pieEntries.add(new PieEntry(timeDuration, activityName));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Physical Activities");
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(12f);

        PieData pieData = new PieData(pieDataSet);

        // Chart konfigurieren
        pieChart.setData(pieData);
        pieChart.animateXY(2400,2400);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("");
        pieChart.setDrawEntryLabels(false);
        pieChart.getLegend().setEnabled(true);
        pieChart.getLegend().setTextColor(Color.WHITE);
        pieChart.invalidate();
        pieChart.animate();
    }

    private float convertTimeStringToMillis(String timeString) {
        // Assuming timeString is in the format "HH:mm:ss"
        String[] timeParts = timeString.split(":");

        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);
        int seconds = Integer.parseInt(timeParts[2]);

        // Convert hours, minutes, and seconds to milliseconds
        float timeMillis = hours * 3600000 + minutes * 60000 + seconds * 1000;

        return (timeMillis / (1000 * 60 )) / 60;
    }

}