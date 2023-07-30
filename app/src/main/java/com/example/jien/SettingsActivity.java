package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    String[] settingsList1 ={"Profile Page ","Data Collection","Notifications"};
    String[] settingsList2 ={"Notifications","Sound and Touch","Screen and Brightness"};
    String[] settingsList3 ={"General","Battery","Privacy and Security","Accessibility","Dark Mode"};

    int[] settingsImg1 ={R.drawable.icons_useraccount,R.drawable.icons_save,R.drawable.icons_alarm};
    int[] settingsImg2 ={R.drawable.ic_notification, R.drawable.soundd,R.drawable.ic_screenbright};
    int[] settingsImg3 ={R.drawable.ic_general,R.drawable.ic_battery,R.drawable.ic_privacy,R.drawable.ic_accessibility,R.drawable.ic_darkmode};

    ListView listView1;
    ListView listView2;
    ListView listView3;
    ImageButton dashImageBtn;
    ImageButton logoutImBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
      //  new TopBarHelper(this);

        listView1= (ListView) findViewById(R.id.settingsList1);
        SettingsBaseAdapter settingsBaseAdapter1=new SettingsBaseAdapter(getApplicationContext(),settingsList1,settingsImg1);
        listView1.setAdapter(settingsBaseAdapter1);
        /*
       listView2=(ListView) findViewById(R.id.settingsList2);
        SettingsBaseAdapter settingsBaseAdapter2=new SettingsBaseAdapter(getApplicationContext(),settingsList2,settingsImg2);
       listView2.setAdapter(settingsBaseAdapter2);

        listView3=(ListView) findViewById(R.id.settingsList3);
        SettingsBaseAdapter settingsBaseAdapter3=new SettingsBaseAdapter(getApplicationContext(),settingsList3,settingsImg3);
        listView3.setAdapter(settingsBaseAdapter3);
        */
        ListView listView1 = findViewById(R.id.settingsList1);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                  else if (position == 1) {
                    Intent intent = new Intent(SettingsActivity.this, DataCollectionActivity.class);
                    startActivity(intent);
                } else if (position== 2) {

                    Intent intent = new Intent(SettingsActivity.this, NotificationActivity.class);
                    startActivity(intent);

                }
            }
        });

        dashImageBtn= findViewById(R.id.dashImageBtn);
        dashImageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, DashboardActivity.class);
            startActivity(intent);
        });

        logoutImBtn= findViewById(R.id.logoutImBtn);
        logoutImBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, StartPageActivity.class);
            startActivity(intent);
        });

    }
    };