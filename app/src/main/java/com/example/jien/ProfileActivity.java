package com.example.jien;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView notifBtn = findViewById(R.id.notifBtn);
        ImageView settingsBtn = findViewById(R.id.settingsBtn);
        ImageView logoutBtn = findViewById(R.id.logoutBtn);
        TextView profileName = findViewById(R.id.profileName);
        TextView profileEmail = findViewById(R.id.profileEmail);

        User user = User.getInstance();
        user.loadUserData(this);
        String userName = user.getName();
        String userEmail = user.getEmail();

        profileName.setText(userName);
        profileEmail.setText(userEmail);


        notifBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, NotificationActivity.class);
            startActivity(intent);
        });

        settingsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        logoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, StartPageActivity.class);
            startActivity(intent);
        });
    }
}