package com.example.weatherapp.data;

public class WeatherRecommendation {
    private String location;  // 지역 정보
    private String date;      // 날짜 정보
    private String summary;   // 날씨 요약

    // 생성자
    public WeatherRecommendation(String location, String date, String summary) {
        this.location = location;
        this.date = date;
        this.summary = summary;
    }

    // Getter 메서드
    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getSummary() {
        return summary;
    }
}
