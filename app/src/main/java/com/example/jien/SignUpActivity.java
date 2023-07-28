package com.example.jien;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SignUpActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextEmail2);
        Button signUpBtn = findViewById(R.id.signUpBtn);
        FloatingActionButton logInBtn = findViewById(R.id.logInBtn);

        User.getInstance().loadUserData(this);

        signUpBtn.setOnClickListener(v -> {

            String name = editTextName.getText().toString();
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            User user = User.getInstance();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);

            user.saveUserData(SignUpActivity.this);

            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
        });

        logInBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, StartPageActivity.class);
            startActivity(intent);
        });

    }
}