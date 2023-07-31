package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;

import java.util.Locale;

public class LoadingScreenActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User.getInstance().loadUserData(this);
        String savedLanguage = User.getInstance().getLanguage();

        // Apply the saved language if it exists
        if (!savedLanguage.isEmpty()) {
            setLocal(this, savedLanguage);
        }

        setContentView(R.layout.activity_start_page);
        setContentView(R.layout.activity_loading_screen);

        handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(LoadingScreenActivity.this, StartPageActivity.class);
            startActivity(intent);
            finish();
        },2000);
    }
    public void setLocal(Activity activity, String langCode){
        Locale locale= new Locale(langCode);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config,resources.getDisplayMetrics());
    }
}