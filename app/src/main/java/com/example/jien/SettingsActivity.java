/*
 * MIT License
 *
 * Copyright (c) 2023 RUB-SE-LAB
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example.jien;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    String[] settingsList1 = {"Language", "Profile"," Homebutton"};
    String[] settingsList2 = {"Notifications", "Data Collection"};
    String[] settingsList3 = {"Delete Account", "Logout"};

    int[] settingsImg1 = {R.drawable.ic_language, R.drawable.ic_profile, R.drawable.homes};
    int[] settingsImg2 = {R.drawable.ic_notification, R.drawable.ic_data};
    int[] settingsImg3 = {R.drawable.ic_delete, R.drawable.ic_logout};

    ListView listView1;
    ListView listView2;
    ListView listView3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        new TopBarHelper(this);

        listView1 = (ListView) findViewById(R.id.settingsList1);
        SettingsBaseAdapter settingsBaseAdapter1 = new SettingsBaseAdapter(getApplicationContext(), settingsList1, settingsImg1);
        listView1.setAdapter(settingsBaseAdapter1);

        listView2 = (ListView) findViewById(R.id.settingsList2);
        SettingsBaseAdapter settingsBaseAdapter2 = new SettingsBaseAdapter(getApplicationContext(), settingsList2, settingsImg2);
        listView2.setAdapter(settingsBaseAdapter2);

        listView3 = (ListView) findViewById(R.id.settingsList3);
        SettingsBaseAdapter settingsBaseAdapter3 = new SettingsBaseAdapter(getApplicationContext(), settingsList3, settingsImg3);
        listView3.setAdapter(settingsBaseAdapter3);


        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    User user = User.getInstance();
                    user.loadUserData(SettingsActivity.this);
                    String currentLanguageCode = user.getLanguage();
                    if (currentLanguageCode.equals("en")) {
                        setLanguage("de");
                        user.setLanguage("de");
                        user.saveUserData(SettingsActivity.this);
                        showToast("Language updated to German");
                    } else {
                        setLanguage("en");
                        user.setLanguage("en");
                        user.saveUserData(SettingsActivity.this);
                        showToast("Language updated to English");
                    }
                } else if (position == 1) {
                    Intent intent = new Intent(SettingsActivity.this,ProfileActivity.class);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(SettingsActivity.this,DashboardActivity.class);
                    startActivity(intent);
                }
            }
        });
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(SettingsActivity.this,NotificationActivity.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(SettingsActivity.this,DataCollectionActivity.class);
                    startActivity(intent);
                }
            }
        });
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    new MaterialAlertDialogBuilder(SettingsActivity.this)
                            .setTitle("Delete the account")
                            .setMessage("Are you sure the you want to delete the account")
                            .setPositiveButton("DELETE", (dialog, which) -> {
                                User.getInstance().deleteUserData(SettingsActivity.this);

                                // Clear all activities and navigate to HomeActivity
                                Intent intent = new Intent(SettingsActivity.this, StartPageActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            })
                            .setNegativeButton((R.string.cancel), (dialog, which) -> {

                            })
                            .show();
                } else if (position == 1) {
                    Intent intent = new Intent(SettingsActivity.this, StartPageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void setLanguage(String languageCode) {
        setLocal(SettingsActivity.this, languageCode);
        finish();
        Intent intent = new Intent(SettingsActivity.this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void setLocal(Activity activity, String langCode){
        Locale locale= new Locale(langCode);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config,resources.getDisplayMetrics());
    }

}