package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;



public class PhyDevelopmentActivity extends AppCompatActivity {


    //initialize variable
    BarChart barChart;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phy_development);

        //Assing variable
        barChart = findViewById(R.id.bar_chart);
        pieChart = findViewById(R.id.pie_chart);



    }
}