package com.example.weatherapp.alarm;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarms")
public class Alarm {
    @PrimaryKey(autoGenerate = true) // 자동 증가하는 키 설정
    public int id;

    public boolean isOn;      // 알람 ON/OFF 상태
    public int hour;          // 알람 시간 (시간)
    public int minute;        // 알람 시간 (분)
    public String message;    // 알람 메시지
    public long updatedAt;

    // 생성자
    public Alarm(boolean isOn, int hour, int minute, String message) {
        this.isOn = isOn;
        this.hour = hour;
        this.minute = minute;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Alarm{id=" + id + ", isOn=" + isOn + ", hour=" + hour + ", minute=" + minute +
                ", message='" + message + "', updatedAt=" + updatedAt + "}";
    }
}

