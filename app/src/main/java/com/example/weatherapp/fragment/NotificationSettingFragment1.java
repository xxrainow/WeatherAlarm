package com.example.weatherapp.fragment;

import android.app.TimePickerDialog;
import android.content.Intent;
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

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;
import com.example.weatherapp.notification.AlarmFunctions;
import com.example.weatherapp.notification.AlarmPreferences;

import java.util.Calendar;

public class NotificationSettingFragment1 extends Fragment {
    private ImageButton backBtn;
    private static final String TAG = "NotificationSettingFragment1";

    private Calendar alarmTime;
    private EditText tvAlarmMessage;
    private Switch switchAlarm;
    private TextView selectedTime;
    private Button btnSetting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_setting1, container, false);

        selectedTime = view.findViewById(R.id.selectedTime);
        tvAlarmMessage = view.findViewById(R.id.tvAlarmMessage);
        switchAlarm = view.findViewById(R.id.switchAlarm);
        btnSetting = view.findViewById(R.id.btnSetting);
        backBtn = view.findViewById(R.id.btnBack);

        alarmTime = Calendar.getInstance();

        // SharedPreferences에서 데이터를 불러와 UI 업데이트
        updateUIFromStoredValues();

        // 타임피커 설정
        selectedTime.setOnClickListener(v -> showTimePicker());

        // btnSetting 클릭 리스너
        btnSetting.setOnClickListener(v -> {
            String selectedTimeText = selectedTime.getText().toString();
            String message = tvAlarmMessage.getText().toString();
            boolean isAlarmOn = switchAlarm.isChecked();

            if (message.isEmpty() || selectedTimeText.equals("Select Time")) {
                Log.w(TAG, "Invalid input: Time or message is missing");
                return;
            }

            // 데이터를 SharedPreferences에 저장
            AlarmPreferences alarmPreferences = new AlarmPreferences(requireContext());
            alarmPreferences.saveAlarm(selectedTimeText, message, isAlarmOn);

            // 저장된 값 로깅
            alarmPreferences.logStoredValues();
            Log.d("세팅 버튼 눌러서 저장한 값", "Saved data - Time: " + selectedTimeText + ", Message: " + message + ", Alarm: " + (isAlarmOn ? "ON" : "OFF"));

            // DefaultFragment로 돌아가기
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        backBtn.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

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

    /**
     * SharedPreferences에서 데이터를 불러와 UI를 업데이트
     */
    private void updateUIFromStoredValues() {
        AlarmPreferences alarmPreferences = new AlarmPreferences(requireContext());
        AlarmPreferences.AlarmData alarmData = alarmPreferences.loadStoredValues();

        // 저장된 데이터 로그 출력
        Log.d(TAG, "Loaded data - Time: " + alarmData.time + ", Message: " + alarmData.message + ", Alarm: " + (alarmData.isAlarmOn ? "ON" : "OFF"));

        // UI 업데이트
        if (!alarmData.time.equals("00:00")) {
            selectedTime.setText(alarmData.time);
            alarmTime = alarmData.alarmTime;
        }

        tvAlarmMessage.setText(alarmData.message);
        switchAlarm.setChecked(alarmData.isAlarmOn);
    }
}

