package com.example.jien;


import static com.example.jien.PhysicalsDatabaseHelper.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PhysicalActivity extends AppCompatActivity {
    private Spinner spinnersactivities;
    private EditText mEditTextTimerDuration;
    private PhysicalActivities physicalActivities = new PhysicalActivities(null,0,0,null);;
    private static final long START_TIME_IN_MILLIS=0;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mcountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis=START_TIME_IN_MILLIS;
    private PhysicalsDatabaseHelper physicalsDatabaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical);
        spinnersactivities=findViewById(R.id.spinnersactivities);
        mEditTextTimerDuration = findViewById(R.id.edit_text_timer_duration);
        physicalsDatabaseHelper = new PhysicalsDatabaseHelper(this);
        database = physicalsDatabaseHelper.getWritableDatabase();
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
                if (physicalActivities != null) {
                    physicalActivities.setActivity(activities.get(i));
                    // المزيد من الأكواد
                } else {

                    physicalActivities = new PhysicalActivities(null, 0, 0, null);
                }

                physicalActivities.setDay(new Date());
                long startTime =System.currentTimeMillis();
                long stopTime=0;
                physicalActivities.setTimeFrom(startTime);
                physicalActivities.setTimeTo(stopTime);
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
                    saveActivityToDatabase();

                }else{
                    startTimer();
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
    private void saveActivityToDatabase() {
        ContentValues values = new ContentValues();
        values.put("activity", physicalActivities.getActivity());
        values.put("timeFrom", physicalActivities.getTimeFrom());
        values.put("timeTo", physicalActivities.getTimeTo());
        values.put("day", physicalActivities.getDay().getTime());
        database.insert("Physical_Data", null, values);
    }
    private void startTimer(){

        spinnersactivities.setEnabled(false);
        String durationText = mEditTextTimerDuration.getText().toString();
        if (durationText.isEmpty()) {
            Toast.makeText(PhysicalActivity.this, "Please enter a timer duration", Toast.LENGTH_SHORT).show();
            return;
        }

        int durationMinutes = Integer.parseInt(durationText);
        long durationMillis = durationMinutes * 60 * 1000;
        mcountDownTimer=new CountDownTimer(durationMillis,1000) {
            @Override
            public void onTick(long l) {
                physicalActivities.setTimeFrom(System.currentTimeMillis());
                mTimeLeftInMillis=l;

                updateCountDownText();

            }

            @Override
            public void onFinish() {
                mTimerRunning=false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
                physicalActivities.setTimeTo(System.currentTimeMillis());

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
}