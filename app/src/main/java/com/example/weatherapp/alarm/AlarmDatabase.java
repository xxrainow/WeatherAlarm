package com.example.weatherapp.alarm;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Alarm.class}, version = 2)
public abstract class AlarmDatabase extends RoomDatabase {
    private static volatile AlarmDatabase INSTANCE;

    public abstract AlarmDao alarmDao(); // DAO 정의

    // 싱글톤 패턴으로 데이터베이스 객체 생성
    public static AlarmDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AlarmDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AlarmDatabase.class,
                                    "alarm_db"
                            ).fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

