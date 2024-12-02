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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification_default, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);

        // Initialize the button
        edit1 = view.findViewById(R.id.btnAlarmEdit1);
        // Replace NotificationFragment with NotificationSettingFragment1 on button click
        edit1.setOnClickListener(v -> replaceFragment(NotificationSettingFragment1.class.getSimpleName()));
    }

    private void replaceFragment(String tag) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Check if the Fragment exists in FragmentManager by its tag
        Fragment fragment = fragmentManager.findFragmentByTag(tag);

        if (fragment == null) {
            // If not found, create a new instance and add it
            if (tag.equals(NotificationSettingFragment1.class.getSimpleName())) {
                fragment = new NotificationSettingFragment1();
            }
        }

        // Replace the container's current Fragment with the new Fragment
        transaction.add(R.id.fragmentNotificationContainer, fragment, tag);

        // Add the transaction to the back stack to enable back navigation
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}
