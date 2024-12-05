package com.example.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.weatherapp.fragment.HomeFragment;
import com.example.weatherapp.fragment.MapFragment;
import com.example.weatherapp.fragment.NotificationFragment;
import com.example.weatherapp.fragment.RecommendationFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM Log", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get new FCM registration token
                    String token = task.getResult();
                    Log.d("FCM Log", "FCM 토큰: " + token);
                    Toast.makeText(MainActivity.this, "FCM 토큰: " + token, Toast.LENGTH_SHORT).show();
                });


        // 기본으로 HomeFragment 로드
        replaceFragment(new HomeFragment());

        // 하단 네비게이션 바 버튼들
        ImageButton navHome = findViewById(R.id.navHome);
        ImageButton navClothes = findViewById(R.id.navClothes);
        ImageButton navNotification = findViewById(R.id.navNotification);
        ImageButton navMap = findViewById(R.id.navMap);

        // Home 버튼 클릭 시 HomeFragment 로드
        navHome.setOnClickListener(v -> replaceFragment(new HomeFragment()));

        // 다른 Fragment로 전환 가능 (예시)
        navClothes.setOnClickListener(v -> replaceFragment(new RecommendationFragment()));
        navNotification.setOnClickListener(v -> replaceFragment(new NotificationFragment()));
        navMap.setOnClickListener(v -> replaceFragment(new MapFragment()));


    }

    // Fragment 교체 메서드
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentHomeContainer, fragment);
        transaction.commit();
    }


}
