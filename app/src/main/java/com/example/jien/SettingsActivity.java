package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class SettingsActivity extends AppCompatActivity {
    String[] settingsList1 ={"Language","WiFi","Bluetooth","Phone"};
    String[] settingsList2 ={"Notifications","Sound and Touch","Screen and Brightness"};
    String[] settingsList3 ={"General","Battery","Privacy and Security","Accessibility","Dark Mode"};

    int[] settingsImg1 ={R.drawable.ic_language,R.drawable.ic_wifi,R.drawable.ic_blutooth,R.drawable.ic_phone};
    int[] settingsImg2 ={R.drawable.ic_notification, R.drawable.soundd,R.drawable.ic_screenbright};
    int[] settingsImg3 ={R.drawable.ic_general,R.drawable.ic_battery,R.drawable.ic_privacy,R.drawable.ic_accessibility,R.drawable.ic_darkmode};

    ListView listView1;
    ListView listView2;
    ListView listView3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        new TopBarHelper(this);

        listView1= (ListView) findViewById(R.id.settingsList1);
        SettingsBaseAdapter settingsBaseAdapter1=new SettingsBaseAdapter(getApplicationContext(),settingsList1,settingsImg1);
        listView1.setAdapter(settingsBaseAdapter1);

        listView2=(ListView) findViewById(R.id.settingsList2);
        SettingsBaseAdapter settingsBaseAdapter2=new SettingsBaseAdapter(getApplicationContext(),settingsList2,settingsImg2);
        listView2.setAdapter(settingsBaseAdapter2);

        listView3=(ListView) findViewById(R.id.settingsList3);
        SettingsBaseAdapter settingsBaseAdapter3=new SettingsBaseAdapter(getApplicationContext(),settingsList3,settingsImg3);
        listView3.setAdapter(settingsBaseAdapter3);
    }
}