package com.example.weatherapp.notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;

public class AlarmPreferences {
    private static final String TAG = "AlarmPreferences";
    private static final String PREF_NAME = "AlarmPrefs";
    private static final String KEY_TIME = "time";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_ALARM_ON = "alarm_on";

    private final SharedPreferences prefs;

    public AlarmPreferences(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // 데이터 저장
    public void saveAlarm(String time, String message, boolean isAlarmOn) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_TIME, time);
        editor.putString(KEY_MESSAGE, message);
        editor.putBoolean(KEY_ALARM_ON, isAlarmOn);
        editor.apply();
    }

    // 데이터 불러오기
    public String getTime() {
        return prefs.getString(KEY_TIME, "00:00");
    }

    public String getMessage() {
        return prefs.getString(KEY_MESSAGE, "");
    }

    public boolean isAlarmOn() {
        return prefs.getBoolean(KEY_ALARM_ON, false);
    }

    // SharedPreferences에 저장된 값을 로그로 출력
    public void logStoredValues() {
        String time = getTime();
        String message = getMessage();
        boolean isAlarmOn = isAlarmOn();

        Log.d(TAG, "Stored Values: ");
        Log.d(TAG, "Time: " + time);
        Log.d(TAG, "Message: " + message);
        Log.d(TAG, "Alarm On: " + isAlarmOn);
    }

    // SharedPreferences에서 데이터 불러오고 캘린더와 데이터 반환
    public AlarmData loadStoredValues() {
        String time = getTime();
        String message = getMessage();
        boolean isAlarmOn = isAlarmOn();

        Log.d(TAG, "Loaded data - Time: " + time + ", Message: " + message + ", Alarm: " + (isAlarmOn ? "ON" : "OFF"));

        Calendar alarmTime = Calendar.getInstance();
        if (!time.equals("00:00")) {
            String[] timeParts = time.split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);
            alarmTime.set(Calendar.HOUR_OF_DAY, hour);
            alarmTime.set(Calendar.MINUTE, minute);
        }

        return new AlarmData(time, message, isAlarmOn, alarmTime);
    }

    // 알람 데이터를 저장하는 내부 클래스
    public static class AlarmData {
        public final String time;
        public final String message;
        public final boolean isAlarmOn;
        public final Calendar alarmTime;

        public AlarmData(String time, String message, boolean isAlarmOn, Calendar alarmTime) {
            this.time = time;
            this.message = message;
            this.isAlarmOn = isAlarmOn;
            this.alarmTime = alarmTime;
        }
    }
}

