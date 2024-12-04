package com.example.weatherapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.weatherapp.R;

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
