package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class WriteNotizenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_notizen);
        new TopBarHelper(this);
    }
}