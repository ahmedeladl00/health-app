package com.example.jien;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextEmail2);
        Button logInBtn = findViewById(R.id.logInBtn);
        FloatingActionButton signUpBtn = findViewById(R.id.signUpBtn);

        signUpBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
            startActivity(intent);
        });

        logInBtn.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            User user = User.getInstance();

            if (user.isValidLogin(email, password)) {
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
            } else {
                if (TextUtils.isEmpty(user.getEmail()) || TextUtils.isEmpty(user.getPassword())) {
                    Toast.makeText(MainActivity.this, "You haven't signed up yet. Please sign up first.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}