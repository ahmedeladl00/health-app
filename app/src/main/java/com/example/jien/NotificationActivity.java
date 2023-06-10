package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

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

    public void morningTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectdHour, int selectedMinute) {
                hour = selectdHour;
                minute = selectedMinute;
                morningBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));

            }
        };

        //TODO: add style in the timePickerdialoge objekt
        // int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void noonTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectdHour, int selectedMinute) {
                hour = selectdHour;
                minute = selectedMinute;
                noonBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));

            }
        };

        //TODO: add style in the timePickerdialoge objekt
        // int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,  onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void nightTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectdHour, int selectedMinute) {
                hour = selectdHour;
                minute = selectedMinute;
                nightBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));

            }
        };

        //TODO: add style in the timePickerdialoge objekt
        // int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}
//TODO: alternativ method: we have to check the setMax and setMin methods
   /* public void morningTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                morningBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");

        // Set the minimum and maximum time range
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        timePickerDialog.updateTime(currentHour, currentMinute); // Set the current time as the default

        // Set the minimum time (e.g., 7:00 AM)
        timePickerDialog.setMin(currentHour, currentMinute);

        // Set the maximum time (e.g., 11:00 PM)
        int maxHour = 11;
        int maxMinute = 0;

        timePickerDialog.setMax(maxHour, maxMinute);

        timePickerDialog.show();
    }*/
