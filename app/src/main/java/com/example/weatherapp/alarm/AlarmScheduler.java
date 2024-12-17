package com.example.weatherapp.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class AlarmScheduler {
    public static void scheduleAlarm(Context context, Alarm alarm) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // 알람 시간 설정
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarm.hour);
        calendar.set(Calendar.MINUTE, alarm.minute);
        calendar.set(Calendar.SECOND, 0);

        // PendingIntent 설정
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("alarm_message", alarm.message);
        intent.putExtra("alarm_id", alarm.id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.d("AlarmScheduler", "알람 예약됨 - 시간: " + alarm.hour + ":" + alarm.minute + ", 메시지: " + alarm.message);
    }


    public static void cancelAlarm(Context context, int alarmId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        Log.d("AlarmScheduler", "알람 취소됨 - ID: " + alarmId);
    }
}

