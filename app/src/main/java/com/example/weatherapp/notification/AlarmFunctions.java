package com.example.weatherapp.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class AlarmFunctions {
    private static final String TAG = "AlarmFunctions";
    private static final int ALARM_REQUEST_CODE = 100;

    public static void setAlarm(Context context, Calendar alarmTime, String message) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("message", message);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), alarmIntent);
            Log.d(TAG, "Alarm set for: " + alarmTime.getTime() + " with message: " + message);
        } else {
            Log.e(TAG, "AlarmManager is null. Cannot set alarm.");
        }
    }

    public static void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            alarmManager.cancel(alarmIntent);
            Log.d(TAG, "Alarm canceled.");
        } else {
            Log.e(TAG, "AlarmManager is null. Cannot cancel alarm.");
        }
    }
}
