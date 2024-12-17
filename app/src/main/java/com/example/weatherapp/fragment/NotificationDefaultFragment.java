package com.example.weatherapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.alarm.Alarm;
import com.example.weatherapp.alarm.AlarmDatabase;
import com.example.weatherapp.alarm.AlarmScheduler;

public class NotificationDefaultFragment extends Fragment {

    private Button edit1;
    private TextView alarmTimeTextView, alarmMessageTextView;
    private Switch alarmSwitch;
    private static final String TAG = "NotificationDefaultFragment";

    public NotificationDefaultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_default, container, false);
        loadAlarmData();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // UI 요소 초기화
        edit1 = view.findViewById(R.id.btnAlarmEdit1);
        alarmTimeTextView = view.findViewById(R.id.tvSelectedTime);
        alarmMessageTextView = view.findViewById(R.id.tvAlarmMessage);
        alarmSwitch = view.findViewById(R.id.switchAlarm);

        edit1.setOnClickListener(v -> replaceFragment(NotificationSettingFragment1.class.getSimpleName()));

        // 알람 상태 변경 리스너
        alarmSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                String time = alarmTimeTextView.getText().toString();
                int hour = Integer.parseInt(time.split("시")[0].trim());
                int minute = Integer.parseInt(time.split("분")[0].split(" ")[1].trim());
                String message = alarmMessageTextView.getText().toString().replace("알림 메시지: ", "").trim();

                updateAlarmStatus(hour, minute, message, isChecked); // 상태 db 즉시 업데이트
            } catch (Exception e) {
                Log.e(TAG, "알람 상태 변경 오류: " + e.getMessage());
            }
        });

        // Room Database에서 알람 정보 불러오기
        loadAlarmData();

    }

    private void loadAlarmData() {
        new Thread(() -> {
            AlarmDatabase db = AlarmDatabase.getInstance(requireContext());
            Alarm alarm = db.alarmDao().getFirstAlarm();

            // UI 업데이트 (UI 스레드에서 실행)
            requireActivity().runOnUiThread(() -> {
                if (alarm != null) {
                    alarmTimeTextView.setText(alarm.hour + "시 " + alarm.minute + "분");
                    alarmMessageTextView.setText("알림 메시지: " + alarm.message);
                    alarmSwitch.setChecked(alarm.isOn);

                    Log.d(TAG, "불러온 알람 정보 - 시간: " + alarm.hour + ", 메시지: " + alarm.message + ", 상태: " + (alarm.isOn ? "ON" : "OFF"));

                    // 알람이 ON 상태라면 AlarmScheduler를 통해 알람 예약
                    if (alarm.isOn) {
                        AlarmScheduler.scheduleAlarm(requireContext(), alarm);
                        Log.d(TAG, "알람 예약된 시간(시): " + alarm.hour);
                    }
                } else {
                    Log.d(TAG, "저장된 알람 정보가 없습니다.");
                }
            });
        }).start();
    }

    private void replaceFragment(String tag) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // NotificationSettingFragment1이라는 태그로 저장된 프래그먼트를 찾아서 fragment에 저장
        Fragment fragment = fragmentManager.findFragmentByTag(tag);

        if (fragment == null) {
            if (tag.equals(NotificationSettingFragment1.class.getSimpleName())) {
                fragment = new NotificationSettingFragment1();
            }
        }

        transaction.replace(R.id.fragmentNotificationContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void updateAlarmStatus(int hour, int minute, String message, boolean isOn) {
        new Thread(() -> {
            AlarmDatabase db = AlarmDatabase.getInstance(requireContext());
            Alarm alarm = db.alarmDao().getFirstAlarm(); // 예: 첫 번째 알람을 가져옴

            if (alarm != null) {
                // 기존 알람 객체의 필드를 수정
                alarm.hour = hour;
                alarm.minute = minute;
                alarm.message = message;
                alarm.isOn = isOn;
                alarm.updatedAt = System.currentTimeMillis(); // 업데이트 시간 갱신

                db.alarmDao().updateAlarm(alarm); // 알람 업데이트

                requireActivity().runOnUiThread(() -> {
                    Log.d(TAG, "알람 상태 업데이트 완료: " + hour + "시 " + minute + "분, 상태: " + (isOn ? "ON" : "OFF"));
                    loadAlarmData(); // UI에 최신 데이터 반영
                });
            } else {
                Log.d(TAG, "업데이트할 알람이 존재하지 않습니다.");
            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume 호출됨 - 알람 데이터 다시 불러오기");
        loadAlarmData(); // 화면이 다시 보여질 때 알람 데이터 불러오기
    }

}
