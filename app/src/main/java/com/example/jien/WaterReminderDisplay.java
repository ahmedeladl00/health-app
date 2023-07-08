package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class WaterReminderDisplay extends AppCompatActivity {
    FloatingActionButton addButton ;
    FloatingActionButton addGoal ;
    TextView recordEditText;
    TextView recordGoal;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_reminder_display);
        addButton=findViewById(R.id.add);
        recordEditText=findViewById(R.id.record);
        addGoal=findViewById(R.id.ptntarget);
        recordGoal=findViewById(R.id.targetxt);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        addGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}