package com.example.weatherapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import com.google.firebase.FirebaseApp;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        FirebaseApp.initializeApp(this);
        Log.d("FirebaseInit", "FirebaseApp initialized successfully");
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "default_channel_id";
            String channelName = "Default Channel";
            String channelDescription = "Channel for default notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
