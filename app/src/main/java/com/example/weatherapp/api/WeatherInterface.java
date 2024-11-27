package com.example.weatherapp.api;


import static com.example.weatherapp.utils.Constants.API_KEY;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface WeatherInterface {
    // getUltraSrtFcst : 초단기 예보 조회 + 인증키
    @GET("getUltraSrtFcst?serviceKey="+API_KEY)
    Call<WEATHER> getWeather(
            @Query("numOfRows") int numOfRows,     // 한 페이지 결과 수
            @Query("pageNo") int pageNo,           // 페이지 번호
            @Query("dataType") String dataType,    // 응답 자료 형식
            @Query("base_date") String baseDate,   // 발표 일자
            @Query("base_time") String baseTime,   // 발표 시각
            @Query("nx") String nx,                // 예보지점 X 좌표
            @Query("ny") String ny                 // 예보지점 Y 좌표
    );
}


