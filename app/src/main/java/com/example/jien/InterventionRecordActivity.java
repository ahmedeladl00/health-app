package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InterventionRecordActivity extends AppCompatActivity {

    private TextView timerTextView;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private Button startBtn;
    private long timerInterval = 1000;
    private long elapsedTime = 0;
    private boolean isTimerRunning = false;
    private Spinner interventionNames;
    private Intervention intervention;
    private LocalDate currentDate;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervention_record);

        timerTextView = findViewById(R.id.timerTextView);
        startBtn = findViewById(R.id.startBtn);
        interventionNames = findViewById(R.id.interventionNames);

        List<String> namesList = loadDataFromRaw();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, namesList);
        interventionNames.setAdapter(adapter);
        interventionNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedIntervention = parent.getItemAtPosition(position).toString();
                currentDate = LocalDate.now();
                intervention = new Intervention(selectedIntervention,currentDate);
                startBtn.setText("Start Recording");
                startBtn.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {

                long seconds = elapsedTime / 1000;
                timerTextView.setText(String.format(Locale.getDefault(), "Elapsed time: %02d:%02d", seconds / 60, seconds % 60));
                elapsedTime += timerInterval;
                timerHandler.postDelayed(this, timerInterval);

            }
        };

        startBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (isTimerRunning){
                    stopTimer();
                    isTimerRunning = false;
                } else {
                    startTimer();
                    isTimerRunning = true;
                }

            }
        });


    }

    private void startTimer() {
        elapsedTime = 0;
        timerHandler.postDelayed(timerRunnable, timerInterval);
        isTimerRunning = true;
        startBtn.setText("Stop Recording");
        intervention.setTimeFrom();
    }

    private void stopTimer() {
        timerHandler.removeCallbacks(timerRunnable);
        isTimerRunning = false;
        startBtn.setText("Start Recording Again");
        intervention.setTimeTo();
        dbHelper = new DatabaseHelper(this);
        dbHelper.insertIntervention(intervention);
    }

    private List<String> loadDataFromRaw() {
        List<String> namesList = new ArrayList<>();
        Field[] fields = R.raw.class.getFields();

        for (int i = 0; i < fields.length; i++){
            try{
                namesList.add(fields[i].getName());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return namesList;
    }
}