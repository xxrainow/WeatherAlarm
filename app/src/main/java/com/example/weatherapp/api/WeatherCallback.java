package com.example.weatherapp.api;
//Fragment가 구현해야 할 메서드 정리

import com.example.weatherapp.data.ModelWeather;
import java.util.List;

public interface WeatherCallback {
    void updateWeatherView(String rainType, String temp, String humidity, String sky); // 날씨 정보 업데이트
    void updateRecyclerView(List<ModelWeather> weatherList); // RecyclerView 데이터 업데이트
}
