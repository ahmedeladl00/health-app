package com.example.jien;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

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
        new TopBarHelper(this);

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
        setupButtonListeners();
        updateButtonLabels();
    }

    private void setupButtonListeners() {
        morningBtn.setOnClickListener(v -> onTimeButtonClick(MORNING_TIME_KEY, "Please pick a time between 7:00 AM and 10:59 AM"));

        noonBtn.setOnClickListener(v -> onTimeButtonClick(NOON_TIME_KEY, "Please pick a time between 12:00 PM and 1:59 PM"));

        nightBtn.setOnClickListener(v -> onTimeButtonClick(NIGHT_TIME_KEY, "Please pick a time between 5:00 PM and 6:59 PM"));
    }

    private void onTimeButtonClick(final String key, final String toastMessage) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;

                if ((Objects.equals(key, MORNING_TIME_KEY)) && (selectedHour >= 7 && selectedHour <= 10 && selectedMinute >= 0 && selectedMinute <= 59)) {
                    morningBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                    scheduleNotification(selectedHour, selectedMinute);
                    saveSelectedTime(key, selectedHour, selectedMinute);
                } else if ((Objects.equals(key, NOON_TIME_KEY)) && (selectedHour >= 12 && selectedHour <= 13 && selectedMinute >= 0 && selectedMinute <= 59)) {
                    noonBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                    scheduleNotification(selectedHour, selectedMinute);
                    saveSelectedTime(key, selectedHour, selectedMinute);
                } else if ((Objects.equals(key, NIGHT_TIME_KEY)) && (selectedHour >= 17 && selectedHour <= 18 && selectedMinute >= 0 && selectedMinute <= 59)) {
                    nightBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                    scheduleNotification(selectedHour, selectedMinute);
                    saveSelectedTime(key, selectedHour, selectedMinute);
                } else {
                    Toast.makeText(NotificationActivity.this, toastMessage, Toast.LENGTH_LONG).show();
                }
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,R.style.TimePickerDialogTheme,onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.darker_gray);
        timePickerDialog.show();
    }

    private void updateButtonLabels() {
        int morningHour = sharedPreferences.getInt(MORNING_TIME_KEY + "_hour", -1);
        int morningMinute = sharedPreferences.getInt(MORNING_TIME_KEY + "_minute", -1);
        int noonHour = sharedPreferences.getInt(NOON_TIME_KEY + "_hour", -1);
        int noonMinute = sharedPreferences.getInt(NOON_TIME_KEY + "_minute", -1);
        int nightHour = sharedPreferences.getInt(NIGHT_TIME_KEY + "_hour", -1);
        int nightMinute = sharedPreferences.getInt(NIGHT_TIME_KEY + "_minute", -1);

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

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        Intent notificationIntent = new Intent(this, NotificationBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        }
    }

    private void saveSelectedTime(String key, int hour, int minute) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key + "_hour", hour);
        editor.putInt(key + "_minute", minute);
        editor.apply();
    }
}
