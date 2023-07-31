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
import com.github.mikephil.charting.formatter.ValueFormatter;
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
        new TopBarHelper(this);

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

        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.2f min", value); // Format the value with "min"
            }
        });

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
        barChart.getAxisLeft().setEnabled(true);
        barChart.getAxisLeft().setTextColor(Color.WHITE);
        barChart.getAxisLeft().setDrawTopYLabelEntry(true);
        barChart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.2f min", value); // Format the value with "min"

            }
        });
        barChart.getAxisRight().setEnabled(false);
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
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.2f min", value); // Format the value with "min"
            }
        });
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

        return (timeMillis / (1000 * 60 )) ;
    }

}