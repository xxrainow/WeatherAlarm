package com.example.weatherapp.api;

public class ITEM {
    private String category;   // 자료 구분 코드
    private String fcstDate;   // 예측 날짜
    private String fcstTime;   // 예측 시간
    private String fcstValue;  // 예보 값

    public ITEM(String category, String fcstDate, String fcstTime, String fcstValue) {
        this.category = category;
        this.fcstDate = fcstDate;
        this.fcstTime = fcstTime;
        this.fcstValue = fcstValue;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFcstDate() {
        return fcstDate;
    }

    public void setFcstDate(String fcstDate) {
        this.fcstDate = fcstDate;
    }

    public String getFcstTime() {
        return fcstTime;
    }

    public void setFcstTime(String fcstTime) {
        this.fcstTime = fcstTime;
    }

    public String getFcstValue() {
        return fcstValue;
    }

    public void setFcstValue(String fcstValue) {
        this.fcstValue = fcstValue;
    }
}
