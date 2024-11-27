package com.example.weatherapp.data;

public class ModelWeather {
    private String rainType;   // 강수 형태
    private String humidity;   // 습도
    private String sky;        // 하늘 상태
    private String temp;       // 기온
    private String fcstTime;   // 예보 시각

    // 기본 생성자
    public ModelWeather() {
        this.rainType = "";
        this.humidity = "";
        this.sky = "";
        this.temp = "";
        this.fcstTime = "";
    }

    // Getter 및 Setter
    public String getRainType() {
        return rainType;
    }

    public void setRainType(String rainType) {
        this.rainType = rainType;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getSky() {
        return sky;
    }

    public void setSky(String sky) {
        this.sky = sky;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getFcstTime() {
        return fcstTime;
    }

    public void setFcstTime(String fcstTime) {
        this.fcstTime = fcstTime;
    }

    @Override
    public String toString() {
        return "ModelWeather{" +
                "rainType='" + rainType + '\'' +
                ", humidity='" + humidity + '\'' +
                ", sky='" + sky + '\'' +
                ", temp='" + temp + '\'' +
                ", fcstTime='" + fcstTime + '\'' +
                '}';
    }
}

