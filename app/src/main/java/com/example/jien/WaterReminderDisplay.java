package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WaterReminderDisplay extends AppCompatActivity {
    FloatingActionButton addButton ;
    FloatingActionButton addGoal ;
    EditText recordEditText;
    EditText recordGoal;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_reminder_display);
        addButton=findViewById(R.id.add);
        recordEditText=findViewById(R.id.record);
        addGoal=findViewById(R.id.ptntarget);
        recordGoal=findViewById(R.id.targetxt);
        final int[] clickCount = {0};
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addButton.setPressed(true);
                recordEditText.setEnabled(true);
                recordEditText.requestFocus();
                clickCount[0]++; // Increment the click count

                // Update the record EditText with the click count
                recordEditText.setText(String.valueOf(clickCount[0]));

            }
        });
        addGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGoal.setPressed(true);  // Set the pressed state to display the click effect
               recordGoal.setEnabled(true);  // Enable the EditText for text input
                recordGoal.requestFocus();

            }
        });
    }


}