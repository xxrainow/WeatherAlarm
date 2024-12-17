package com.example.weatherapp.alarm;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmDao {
    @Insert
    long insertAlarm(Alarm alarm); // 알람 추가

    @Update
    void updateAlarm(Alarm alarm); // 알람 수정

    @Query("DELETE FROM alarms WHERE id = :id")
    void deleteAlarm(int id); // 알람 삭제

    @Query("SELECT * FROM alarms")
    List<Alarm> getAllAlarms(); // 모든 알람 가져오기

    @Query("SELECT * FROM alarms WHERE isOn = 1")
    List<Alarm> getActiveAlarms(); // 활성화된 알람만 가져오기

    @Insert(onConflict = OnConflictStrategy.REPLACE) // 충돌 시 덮어쓰기
    void insertOrReplaceAlarm(Alarm alarm);

    @Query("SELECT * FROM alarms LIMIT 1") // 하나의 알람만 가져옴
    Alarm getFirstAlarm();

}

