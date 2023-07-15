package com.example.jien;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class WRNotificationReciever extends BroadcastReceiver {
    private Water water;
    @Override
    public void onReceive(Context context, Intent intent) {

        water = Water.getInstance(context);
        Intent notificationIntent = new Intent(context, WaterReminderDisplay.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Erstelle ein PendingIntent für die Intent
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_MUTABLE);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notification", "Water Reminder", NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notification");

        builder.setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("HI! How do you feel?")
                .setContentText("You still have " + water.getRemaining() + " ml to drink today")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_LIGHTS)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);


        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        }
    }
    }
