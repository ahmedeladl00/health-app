package com.example.jien;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class NotificationBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notification", "Notification", NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // Check if it is morning, noon, or night
        if ((hour >= 7 && hour <= 11) || (hour >= 12 && hour <= 14) || (hour >= 17 && hour <= 19)) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notification");

            builder.setSmallIcon(R.drawable.icon)
                    .setContentTitle("HI! How do you feel?")
                    .setContentText("It's time to answer some questions!")
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_SOUND)
                    .setPriority(NotificationCompat.PRIORITY_MAX);

            if (notificationManager != null) {
                notificationManager.notify(1, builder.build());
            }
        }
    }
}
