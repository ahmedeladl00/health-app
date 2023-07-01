package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.util.DateInterval;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jien.databinding.ActivityMainBinding;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class NotificationActivity extends AppCompatActivity {
    Button morningBtn;
    Button noonBtn;
    Button nightBtn;
    int hour, minute;
   private static final String MORNING_TIME_KEY = "MorningTime";
    private static final String NOON_TIME_KEY = "NoonTime";
    private static final String NIGHT_TIME_KEY = "NightTime";
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        morningBtn = findViewById(R.id.morningBtn);
        noonBtn = findViewById(R.id.noonBtn);
        nightBtn = findViewById(R.id.nightBtn);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(NotificationActivity.this,
                    Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(NotificationActivity.this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);

            }
        }
       sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        int morningHour = sharedPreferences.getInt(MORNING_TIME_KEY + "_hour", -1);
        int morningMinute = sharedPreferences.getInt(MORNING_TIME_KEY + "_minute", -1);
        int noonHour = sharedPreferences.getInt(NOON_TIME_KEY + "_hour", -1);
        int noonMinute = sharedPreferences.getInt(NOON_TIME_KEY + "_minute", -1);
        int nightHour = sharedPreferences.getInt(NIGHT_TIME_KEY + "_hour", -1);
        int nightMinute = sharedPreferences.getInt(NIGHT_TIME_KEY + "_minute", -1);

// Update button texts
        if (morningHour != -1 && morningMinute != -1) {
            morningBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", morningHour, morningMinute));
        }
        if (noonHour != -1 && noonMinute != -1) {
            noonBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", noonHour, noonMinute));
        }
        if (nightHour != -1 && nightMinute != -1) {
            nightBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", nightHour, nightMinute));
        }

    }


    private void scheduleNotification(int hour, int minute) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        // Check if the selected time is in the past. If yes, add a day to the calendar.
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }


        // Create an intent to be triggered when the alarm fires
        Intent notificationIntent = new Intent(this, NotificationBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Set up the alarm manager to schedule the notification
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            // Set the alarm to repeat daily
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        }
    }

    private void saveSelectedTime(String key, int hour, int minute) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key + "_hour", hour);
        editor.putInt(key + "_minute", minute);
        editor.apply();
    }


    public void morningTimePicker (View view){

            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;

                    saveSelectedTime(MORNING_TIME_KEY, selectedHour, selectedMinute);


                    if (selectedHour >= 7 && selectedHour <= 10 && selectedMinute >= 0 && selectedMinute <= 59) {
                        morningBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                        scheduleNotification(selectedHour,selectedMinute);

                    } else if (selectedHour == 11 && selectedMinute ==0 ) {
                        morningBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                        scheduleNotification(selectedHour,selectedMinute);

                    } else {
                        Toast.makeText(NotificationActivity.this, "please pick a time between 7:00 PM and 11:00 PM", Toast.LENGTH_LONG).show();
                    }

                }
            };

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
            timePickerDialog.setTitle("Select Time");
            timePickerDialog.show();

        }

        public void noonTimePicker (View view){

            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;

                    if (selectedHour >= 12 && selectedHour <= 13 && selectedMinute >= 0 && selectedMinute <= 59) {
                        noonBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                        scheduleNotification(selectedHour,selectedMinute);
                        saveSelectedTime(NOON_TIME_KEY, selectedHour, selectedMinute);

                    } else if (selectedHour == 14 && selectedMinute == 0) {
                        noonBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                        scheduleNotification(selectedHour,selectedMinute);
                        saveSelectedTime(NOON_TIME_KEY, selectedHour, selectedMinute);

                    } else {
                        Toast.makeText(NotificationActivity.this, "please pick a time between 12:00 PM and 14:00 PM", Toast.LENGTH_LONG).show();
                    }

                }
            };
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
            timePickerDialog.setTitle("Select Time");
            timePickerDialog.show();

        }

        public void nightTimePicker (View view){
            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;


                    if (selectedHour >= 17 && selectedHour <= 21 && selectedMinute >= 0 && selectedMinute <= 59) {
                        nightBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                        scheduleNotification(selectedHour,selectedMinute);
                        saveSelectedTime(NIGHT_TIME_KEY, selectedHour, selectedMinute);

                    } else if (selectedHour == 19 && selectedMinute == 0) {
                        nightBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                        scheduleNotification(selectedHour,selectedMinute);
                        saveSelectedTime(NIGHT_TIME_KEY, selectedHour, selectedMinute);

                    } else {
                        Toast.makeText(NotificationActivity.this, "please pick a time between 17:00 PM and 19:00 PM", Toast.LENGTH_LONG).show();
                    }

                }
            };

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
            timePickerDialog.setTitle("Select Time");
            timePickerDialog.show();
    }
    }