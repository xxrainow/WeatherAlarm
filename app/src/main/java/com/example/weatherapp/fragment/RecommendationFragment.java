package com.example.weatherapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.adapter.WeatherAdapter;
import com.example.weatherapp.data.ModelWeather;
import com.example.weatherapp.data.WeatherRecommendation;

import java.util.ArrayList;
import java.util.List;

public class RecommendationFragment extends Fragment {

    private ViewPager2 viewPager;

    private View rootView;

    private TextView todayDate; // 오늘 날짜 텍스트뷰
    private ImageView imgWeather; // 날씨 이미지뷰
    private ImageView imgRainType; // 강수 형태 이미지뷰
    private TextView tvTemperature; // 온도 텍스트뷰
    private TextView tvRainType; // 강수 형태 텍스트뷰
    private TextView tvHumidity; // 습도 텍스트뷰
    private TextView tvSky; // 하늘 상태 텍스트뷰

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recommendation, container, false);
        todayDate = rootView.findViewById(R.id.todayDate); // 오늘 날짜 텍스트뷰
        imgWeather = rootView.findViewById(R.id.imgWeather);


        imgRainType = rootView.findViewById(R.id.imgRainType); // 강수 형태 이미지뷰

        tvTemperature = rootView.findViewById(R.id.tvTemperature);
        tvRainType = rootView.findViewById(R.id.tvRainType); // 강수 형태 텍스트뷰
        tvHumidity = rootView.findViewById(R.id.tvHumidity); // 습도 텍스트뷰
        tvSky = rootView.findViewById(R.id.tvSky); // 하늘 상태 텍스트뷰

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Prepare data for ViewPager2
        List<WeatherRecommendation> weatherData = new ArrayList<>();
        weatherData.add(new WeatherRecommendation("서울, 한국", "11월 3일", "✔️ 얇은 외투와 긴팔, 긴바지가 적당해요."));
        weatherData.add(new WeatherRecommendation("부산, 한국", "11월 4일", "✔️ 따뜻한 옷차림을 추천합니다."));
        weatherData.add(new WeatherRecommendation("제주, 한국", "11월 5일", "✔️ 가벼운 외투를 준비하세요."));
    }
}
