package com.example.weatherapp.api;

import static com.example.weatherapp.utils.Constants.BASE_URL;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Retrofit을 사용하기 위한 빌더 생성
public class ApiObject {
    /*static OkHttpClient client = new OkHttpClient.Builder()
            .connectionPool(new ConnectionPool(0, 1, TimeUnit.NANOSECONDS)) // 커넥션 풀 비활성화
            .build();*/

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            //.client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static final WeatherInterface retrofitService = retrofit.create(WeatherInterface.class);


}
