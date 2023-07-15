package com.example.jien;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Objects;

public class WaterReminderActivity extends AppCompatActivity {

    FloatingActionButton notificationBtn;
    int hour, minute;
    private Water water;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_reminder);
        water = Water.getInstance(this);
        notificationBtn = findViewById(R.id.notification);


        FloatingActionButton addButton = findViewById(R.id.add);
        addButton.setOnClickListener(view -> showAddWaterDialog());

        FloatingActionButton addGoal = findViewById(R.id.ptntarget);
        addGoal.setOnClickListener(view -> showGoalDialog());

        updateRemainingTextView();

        notificationBtn.setOnClickListener(v -> {

            TimePickerDialog.OnTimeSetListener onTimeSetListener = (view, selectedHour, selectedMinute) -> {
                hour = selectedHour;
                minute = selectedMinute;
                scheduleNotification(hour, minute);
            };
            TimePickerDialog timePickerDialog = new TimePickerDialog(WaterReminderActivity.this,R.style.TimePickerDialogTheme, onTimeSetListener,hour,minute,true);

            timePickerDialog.setTitle("Select Reminder");
            timePickerDialog.show();

        });
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
    }

    private void showGoalDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Set Goal");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_water, null);
        builder.setView(dialogView);

        final TextInputEditText input = dialogView.findViewById(R.id.inputEditText);
        input.setText(String.valueOf(water.getGoal()));

        final AlertDialog dialog = builder.create();

        Button positiveButton = dialogView.findViewById(R.id.positiveButton);
        Button negativeButton = dialogView.findViewById(R.id.negativeButton);

        positiveButton.setOnClickListener(view -> {
            String goalString = Objects.requireNonNull(input.getText()).toString();
            if (!goalString.isEmpty()) {
                int goal = Integer.parseInt(goalString);
                water.setGoal(goal);
                updateRemainingTextView();
                dialog.dismiss();
            } else {
                input.setError("Please enter a valid goal");
            }
        });

        negativeButton.setOnClickListener(view -> dialog.dismiss());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.GONE);
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setVisibility(View.GONE);
        });

        dialog.show();
    }

    private void showAddWaterDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Add Water");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_water, null);
        builder.setView(dialogView);

        final TextInputEditText input = dialogView.findViewById(R.id.inputEditText);

        final AlertDialog dialog = builder.create();

        Button positiveButton = dialogView.findViewById(R.id.positiveButton);
        Button negativeButton = dialogView.findViewById(R.id.negativeButton);

        positiveButton.setOnClickListener(view -> {
            String amountString = Objects.requireNonNull(input.getText()).toString();
            if (!amountString.isEmpty()) {
                int amount = Integer.parseInt(amountString);
                water.addWater(WaterReminderActivity.this,amount);
                updateRemainingTextView();
                Toast.makeText(WaterReminderActivity.this, "Water added successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(WaterReminderActivity.this, "Please enter a valid amount", Toast.LENGTH_SHORT).show();
            }
        });

        negativeButton.setOnClickListener(view -> dialog.dismiss());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.GONE);
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setVisibility(View.GONE);
        });

        dialog.show();
    }

    private void updateRemainingTextView() {
        int remaining = water.getRemaining();
        TextView remainingTextView = findViewById(R.id.remainingTextView);
        TextView goaltxt = findViewById(R.id.goaltxt);
        TextView consumedTxt = findViewById(R.id.consumedTxt);

        goaltxt.setText("My Goal: " + String.valueOf(water.getGoal()) + " ml");
        consumedTxt.setText(String.valueOf(water.getConsumed()) + " ml");
        remainingTextView.setText("Remaining " + remaining + " ml");
    }

    private void scheduleNotification ( int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        Intent notificationIntent = new Intent(this, WRNotificationReciever.class);
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
}
