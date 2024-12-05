package com.example.weatherapp.fragment;

import static com.example.weatherapp.notification.AlarmFunctions.setAlarm;

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
import com.example.weatherapp.notification.AlarmFunctions;
import com.example.weatherapp.notification.AlarmPreferences;

public class NotificationFragment extends Fragment {

    private Button edit1;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_default, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        edit1 = view.findViewById(R.id.btnAlarmEdit1);
        edit1.setOnClickListener(v -> replaceFragment(NotificationSettingFragment1.class.getSimpleName()));


        // SharedPreferences에서 데이터 불러오기
        AlarmPreferences alarmPreferences = new AlarmPreferences(requireContext());
        AlarmPreferences.AlarmData alarmData = alarmPreferences.loadStoredValues();
        String time = alarmPreferences.getTime();
        String message = alarmPreferences.getMessage();
        boolean isAlarmOn = alarmPreferences.isAlarmOn();

        // 불러온 값 로깅
        alarmPreferences.logStoredValues();
        Log.d("불러온 값", "Loaded data - Time: " + time + ", Message: " + message + ", Alarm: " + (isAlarmOn ? "ON" : "OFF"));

        // 예약 알람 로그 출력
        // 알람 예약
        if (alarmData.isAlarmOn) {
            AlarmFunctions.setAlarm(requireContext(), alarmData.alarmTime, alarmData.message);
            Log.d("예약알람", "Alarm set for: " + alarmData.time + " with message: " + alarmData.message);
        } else {
            Log.d("예약알람", "Alarm is OFF. No alarm set.");
        }


        // 데이터를 UI에 적용
        TextView alarmTimeTextView = view.findViewById(R.id.tvSelectedTime);
        TextView alarmMessageTextView = view.findViewById(R.id.tvAlarmMessage);
        Switch alarmSwitch = view.findViewById(R.id.switchAlarm);

        alarmTimeTextView.setText(time); // 알림 시간
        alarmMessageTextView.setText("알림 메시지: " + message);
        alarmSwitch.setChecked(isAlarmOn);
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

        transaction.add(R.id.fragmentNotificationContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
