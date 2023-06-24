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

import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;

public class NotificationActivity extends AppCompatActivity {
    Button morningBtn;
    Button noonBtn;
    Button nightBtn;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        morningBtn = findViewById(R.id.morningBtn);
        noonBtn = findViewById(R.id.noonBtn);
        nightBtn = findViewById(R.id.nightBtn);

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
                PendingIntent.FLAG_IMMUTABLE
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

    public void morningTimePicker (View view){

            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int selectdHour, int selectedMinute) {
                    hour = selectdHour;
                    minute = selectedMinute;

                    if (selectdHour >= 7 && selectdHour <= 10 && selectedMinute >= 0 && selectedMinute <= 59) {
                        morningBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                        scheduleNotification(hour,minute);
                    } else if (selectdHour == 11 && selectedMinute == 0) {
                        morningBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                        scheduleNotification(hour,minute);
                    } else {
                        Toast.makeText(NotificationActivity.this, "please pick a time between 7:00 PM and 11:00 PM", Toast.LENGTH_LONG).show();
                    }
                }
            };

            //TODO: add style in the timePickerdialoge objekt
            // int style = AlertDialog.THEME_HOLO_DARK;

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);

            timePickerDialog.setTitle("Select Time");
            timePickerDialog.show();

        }

        public void noonTimePicker (View view){

            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int selectdHour, int selectedMinute) {
                    hour = selectdHour;
                    minute = selectedMinute;
                    if (selectdHour >= 12 && selectdHour <= 13 && selectedMinute >= 0 && selectedMinute <= 59) {
                        noonBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                        scheduleNotification(hour,minute);
                    } else if (selectdHour == 14 && selectedMinute == 0) {
                        noonBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                        scheduleNotification(hour,minute);
                    } else {
                        Toast.makeText(NotificationActivity.this, "please pick a time between 12:00 PM and 14:00 PM", Toast.LENGTH_LONG).show();
                    }

                }


            };

            //TODO: add style in the timePickerdialoge objekt
            // int style = AlertDialog.THEME_HOLO_DARK;
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
            timePickerDialog.setTitle("Select Time");
            timePickerDialog.show();

        }

        public void nightTimePicker (View view){
            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int selectdHour, int selectedMinute) {
                    hour = selectdHour;
                    minute = selectedMinute;
                    if (selectdHour >= 17 && selectdHour <= 18 && selectedMinute >= 0 && selectedMinute <= 59) {
                        nightBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                        scheduleNotification(hour,minute);
                    } else if (selectdHour == 19 && selectedMinute == 0) {
                        nightBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                        scheduleNotification(hour,minute);

                    } else {
                        Toast.makeText(NotificationActivity.this, "please pick a time between 17:00 PM and 19:00 PM", Toast.LENGTH_LONG).show();
                    }

                }
            };

            //TODO: add style in the timePickerdialoge objekt
            // int style = AlertDialog.THEME_HOLO_DARK;
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
            timePickerDialog.setTitle("Select Time");
            timePickerDialog.show();
    }
    }