package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class SettingsActivity extends AppCompatActivity {
    String settingsList1[]={"Language","WiFi","Biutooth","Phone"};
    String settingsList2[]={"Notifications","Sound and Touch","Screen and Brightness"};
    String settingsList3[]={"General","Batery","Privacy and Security","Accessibility","Dark Mode"};

    int settingsimag1[]={R.drawable.ic_language,R.drawable.ic_wifi,R.drawable.ic_blutooth,R.drawable.ic_phone};
    int settingsimag2[]={R.drawable.ic_notification, R.drawable.soundd,R.drawable.ic_screenbright};
    int settingsimage3[]={R.drawable.ic_general,R.drawable.ic_battery,R.drawable.ic_privacy,R.drawable.ic_accessibility,R.drawable.ic_darkmode};

    ListView listView1;
    ListView listView2;
    ListView listView3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        listView1= (ListView) findViewById(R.id.settingsList1);
        SettingsBaseAdapter settingsBaseAdapter1=new SettingsBaseAdapter(getApplicationContext(),settingsList1,settingsimag1);
        listView1.setAdapter(settingsBaseAdapter1);

        listView2=(ListView) findViewById(R.id.settingsList2);
        SettingsBaseAdapter2 settingsBaseAdapter2=new SettingsBaseAdapter2(getApplicationContext(),settingsList2,settingsimag2);
        listView2.setAdapter(settingsBaseAdapter2);

        listView3=(ListView) findViewById(R.id.settingsList3);
        SettingsBaseAdapter3 settingsBaseAdapter3=new SettingsBaseAdapter3(getApplicationContext(),settingsList3,settingsimage3);
        listView3.setAdapter(settingsBaseAdapter3);
    }
}