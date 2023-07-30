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

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextEmail2);
        Button logInBtn = findViewById(R.id.logInBtn);
        FloatingActionButton homeBtn = findViewById(R.id.homeBtn);

        homeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,StartPageActivity.class);
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
                    Toast.makeText(MainActivity.this, (R.string.you_haven_t_signed_up_yet_please_sign_up_first), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, (R.string.invalid_credentials_please_try_again), Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}