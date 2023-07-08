package com.example.jien;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;


public class WaterReminderActivity extends AppCompatActivity {
//    FloatingActionButton addButton ;
    FloatingActionButton addGoal ;
    private Water water;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_reminder);
        water = Water.getInstance();

        addGoal=findViewById(R.id.ptntarget);

//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });
        addGoal.setOnClickListener(view -> showGoalDialog());
    }



    private void showGoalDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Set Goal");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_set_goal, null);
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






}