package com.example.weatherapp.data;

public class ModelWeather {
    private String rainType;   // 강수 형태
    private String humidity;   // 습도
    private String sky;        // 하늘 상태
    private String temp;       // 기온
    private String fcstTime;   // 예보 시각
    private String rain;       // 강수량

    // 기본 생성자
    public ModelWeather() {
        this.rainType = "";
        this.humidity = "";
        this.sky = "";
        this.temp = "";
        this.fcstTime = "";
        this.rain = "";
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

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getRain() {
        return rainType;
    }

    public String getFcstTime() {
        // 예보 시각을 "HHmm"에서 "HH:mm" 형식으로 변환
        if (fcstTime != null && fcstTime.length() == 4) {
            return fcstTime.substring(0, 2) + "시";
        }
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
                ", rain='" + rain + '\'' +
                '}';
    }
}

