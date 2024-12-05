package com.example.weatherapp.notification;

import static androidx.fragment.app.FragmentManager.TAG;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.weatherapp.R;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "WeatherAlarmChannel";
    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");

        Log.d(TAG, "Alarm triggered with message: " + message);

        // AlarmService 호출
        Intent serviceIntent = new Intent(context, AlarmService.class);
        serviceIntent.putExtra("message", message);
        context.startService(serviceIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Weather Alarm", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Weather Alarm")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManager.notify(1, notificationBuilder.build());
    }
}
