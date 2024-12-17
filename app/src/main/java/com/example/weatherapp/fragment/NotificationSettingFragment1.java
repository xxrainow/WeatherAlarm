package com.example.weatherapp.fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherapp.R;
import com.example.weatherapp.alarm.Alarm;
import com.example.weatherapp.alarm.AlarmDatabase;
import com.example.weatherapp.alarm.AlarmScheduler;

import java.util.Calendar;

public class NotificationSettingFragment1 extends Fragment {
    private static final String TAG = "NotificationSetting";

    private Calendar alarmTime;
    private EditText tvAlarmMessage;
    private Switch switchAlarm;
    private TextView selectedTime;
    private Button btnSetting;
    private ImageButton btnBack; // 뒤로 가기 버튼

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_setting1, container, false);

        selectedTime = view.findViewById(R.id.selectedTime);
        tvAlarmMessage = view.findViewById(R.id.tvAlarmMessage);
        switchAlarm = view.findViewById(R.id.switchAlarm);
        btnSetting = view.findViewById(R.id.btnSetting);
        btnBack = view.findViewById(R.id.btnBack);

        alarmTime = Calendar.getInstance();
        loadAlarmFromDatabase();

        // 타임피커 설정
        selectedTime.setOnClickListener(v -> showTimePicker());

        // 설정 버튼 클릭 리스너
        btnSetting.setOnClickListener(v -> {
            // 입력값 가져오기
            String message = tvAlarmMessage.getText().toString();
            boolean isAlarmOn = switchAlarm.isChecked();
            int hour = alarmTime.get(Calendar.HOUR_OF_DAY);
            int minute = alarmTime.get(Calendar.MINUTE);

            if (message.isEmpty()) {
                Log.w(TAG, "메시지가 비어있습니다.");
                return;
            }

            saveAlarmToDatabase(isAlarmOn, hour, minute, message);
            // UI 스레드에서 이전 화면으로 이동
            loadAlarmFromDatabase();
            replaceFragment(new NotificationDefaultFragment());
        });

        // 뒤로 가기 버튼 클릭 리스너
        btnBack.setOnClickListener(v -> {
            Log.d(TAG, "뒤로 가기 버튼 클릭 - 데이터 저장 없이 뒤로 이동");
            requireActivity().getSupportFragmentManager().popBackStack(); // 데이터 저장 없이 이전 화면으로 이동
        });

        return view;
    }

    private void showTimePicker() {
        TimePickerDialog timePicker = new TimePickerDialog(requireContext(), (view, hourOfDay, minute) -> {
            alarmTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            alarmTime.set(Calendar.MINUTE, minute);
            selectedTime.setText(String.format("%02d:%02d", hourOfDay, minute));
        }, alarmTime.get(Calendar.HOUR_OF_DAY), alarmTime.get(Calendar.MINUTE), true);
        timePicker.show();
    }

    private void loadAlarmFromDatabase() {
        new Thread(() -> {
            AlarmDatabase db = AlarmDatabase.getInstance(requireContext());
            Alarm existingAlarm = db.alarmDao().getFirstAlarm();

            if (existingAlarm != null) {
                int hour = existingAlarm.hour;
                int minute = existingAlarm.minute;
                String message = existingAlarm.message;
                boolean isAlarmOn = existingAlarm.isOn;

                // UI 스레드에서 업데이트
                requireActivity().runOnUiThread(() -> {
                    selectedTime.setText(String.format("%02d:%02d", hour, minute));
                    tvAlarmMessage.setText(message);
                    switchAlarm.setChecked(isAlarmOn);
                    alarmTime.set(Calendar.HOUR_OF_DAY, hour);
                    alarmTime.set(Calendar.MINUTE, minute);

                    Log.d(TAG, "알람 정보 로드 완료 - 시간: " + hour + ":" + minute + ", 메시지: " + message + ", 상태: " + isAlarmOn);
                });
            } else {
                Log.d(TAG, "저장된 알람 정보가 없습니다.");
            }
        }).start();
    }

    private void saveAlarmToDatabase(boolean isAlarmOn, int hour, int minute, String message) {
        new Thread(() -> {
            AlarmDatabase db = AlarmDatabase.getInstance(requireContext());
            Alarm existingAlarm = db.alarmDao().getFirstAlarm();
            Alarm alarm;

            if (existingAlarm != null) {
                // 기존 알람 업데이트
                existingAlarm.hour = hour;
                existingAlarm.minute = minute;
                existingAlarm.message = message;
                existingAlarm.isOn = isAlarmOn;

                db.alarmDao().updateAlarm(existingAlarm);
                alarm = existingAlarm;
                Log.d(TAG, "알람 업데이트 완료");
            } else {
                // 새 알람 추가
                alarm = new Alarm(isAlarmOn, hour, minute, message);
                db.alarmDao().insertOrReplaceAlarm(alarm);
                Log.d(TAG, "새 알람 추가 완료");
            }

            // 알람 예약 설정
            if (isAlarmOn) {
                AlarmScheduler.scheduleAlarm(requireContext(), alarm);
                Log.d(TAG, "알람 예약됨: " + hour + ":" + minute);
            } else {
                Log.d(TAG, "알람 비활성화됨");
            }

            }).start();
    }

    private void replaceFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentNotificationContainer, fragment) // 해당 컨테이너 ID 사용
                .commit();
    }

}
