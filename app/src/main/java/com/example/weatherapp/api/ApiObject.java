package com.example.weatherapp.api;

import static com.example.weatherapp.utils.Constants.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Retrofit을 사용하기 위한 빌더 생성
public class ApiObject {
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static final WeatherInterface retrofitService = retrofit.create(WeatherInterface.class);
}
