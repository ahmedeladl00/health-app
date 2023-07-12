package com.example.jien;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class PhysicalActivity extends AppCompatActivity implements SensorEventListener {
    private Spinner spinnersactivities;
    private EditText mEditTextTimerDuration;
    private PhysicalActivities physicalActivities ;
    private static final long START_TIME_IN_MILLIS=0;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mcountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis=START_TIME_IN_MILLIS;
    private PhysicalsDatabaseHelper physicalsDatabaseHelper;
    LocalDate currentsdate;

    private SensorManager sensorManager;
    private Sensor accelerometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical);


        spinnersactivities=findViewById(R.id.spinnersactivities);
        mEditTextTimerDuration = findViewById(R.id.edit_text_timer_duration);
        physicalsDatabaseHelper = new PhysicalsDatabaseHelper(this);
        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        ArrayList<String> activities = new ArrayList<>();
        activities.add("Running or Jogging");
        activities.add("Walking");
        activities.add("Swimming");
        activities.add("Yoga");
        activities.add("Dancing");
        ArrayAdapter<String> activitiesAdapter=new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                activities
        );
        spinnersactivities.setAdapter(activitiesAdapter);
        spinnersactivities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedActivity= adapterView.getItemAtPosition(i).toString();
                currentsdate=LocalDate.now();
                physicalActivities=new PhysicalActivities(selectedActivity,currentsdate);


                Toast.makeText(PhysicalActivity.this,activities.get(i)+"selectes",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mTextViewCountDown=findViewById(R.id.text_view_countdown);
        mButtonStartPause=findViewById(R.id.button_start_pause);
        mButtonReset=findViewById(R.id.button_reset);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mTimerRunning){
                    pauseTimer();

                }else{
                    startTimer();
                }

                if (mTimerRunning) {
                    sensorManager.registerListener(PhysicalActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                } else {
                    sensorManager.unregisterListener(PhysicalActivity.this);
                }
            }

        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
        updateCountDownText();
    }
    private void startTimer(){

        spinnersactivities.setEnabled(false);
        String durationText = mEditTextTimerDuration.getText().toString();
        physicalActivities.setTimeFrom();
        if (durationText.isEmpty()) {
            Toast.makeText(PhysicalActivity.this, "Please enter a timer duration", Toast.LENGTH_SHORT).show();
            return;
        }

        int durationMinutes = Integer.parseInt(durationText);
        long durationMillis = durationMinutes * 60 * 1000;
        mcountDownTimer=new CountDownTimer(durationMillis,1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis=l;

                updateCountDownText();

            }

            @Override
            public void onFinish() {
                mTimerRunning=false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);


            }
        }.start();
        mTimerRunning=true;
        mButtonStartPause.setText("Pause");
        mButtonReset.setVisibility(View.INVISIBLE);

    }
    private void pauseTimer(){
        spinnersactivities.setEnabled(true);
        mcountDownTimer.cancel();
        mTimerRunning=false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
        physicalActivities.setTimeTo();
        physicalsDatabaseHelper=new PhysicalsDatabaseHelper(this);
        physicalsDatabaseHelper.insertActivity(physicalActivities);

    }
    private void resetTimer(){
        mTimeLeftInMillis=START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void  updateCountDownText(){
        int minutes=(int) (mTimeLeftInMillis/1000)/60;
        int seconds=(int) (mTimeLeftInMillis/1000)%60;
        String timeLeftFormatted=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("timerRunning", mTimerRunning);
        outState.putLong("timeLeftInMillis", mTimeLeftInMillis);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTimerRunning = savedInstanceState.getBoolean("timerRunning");
        mTimeLeftInMillis = savedInstanceState.getLong("timeLeftInMillis");
        updateCountDownText();
        if (mTimerRunning) {
            startTimer();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (physicalActivities != null) {
            Accelerometer<PhysicalActivities> accelerometer = new Accelerometer<>(physicalActivities, event.values[0], event.values[1], event.values[2]);
            physicalsDatabaseHelper.insertAccelerometer(accelerometer);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}