package com.example.weatherapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherapp.R;

public class NotificationSettingFragment1 extends Fragment {
    private ImageButton backBtn;
    private static final String ARG_TITLE = "title";
    private static final String ARG_MESSAGE = "message";

    public static NotificationSettingFragment1 newInstance(String title, String message) {
        NotificationSettingFragment1 fragment = new NotificationSettingFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    /*public NotificationSettingFragment1() {
        // Required empty public constructor
    }*/

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification_setting1, container, false);

        // Back button click listener
        backBtn = view.findViewById(R.id.btnBack);
        backBtn.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        return view;
    }
}
