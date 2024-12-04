package com.example.weatherapp.utils;

import com.example.weatherapp.R;

public class WeatherFormatter {

    // 강수 형태
    public static String getRainType(String rainType) {
        switch (rainType) {
            case "0":
                return "-";
            case "1":
                return "비";
            case "2":
                return "비/눈";
            case "3":
                return "눈";
            case "5":
                return "빗방울";
            case "6":
                return "빗방울/눈날림";
            case "7":
                return "눈날림";
            default:
                return "오류 rainType: " + rainType;
        }
    }

    public static int getRainTypeImage(String rainType) {
        // 강수 형태에 따라 이미지 반환
        switch (rainType) {
            case "0": // 없음
                return R.drawable.sun;
            case "1": // 비
                return R.drawable.rain;
            case "2": // 비/눈
                return R.drawable.rain_snow;
            case "3": // 눈
                return R.drawable.snow;
            case "5": // 빗방울
                return R.drawable.raindrop;
            case "6": // 빗방울/눈날림
                return R.drawable.raindrop_snow;
            case "7": // 눈날림
                return R.drawable.snowflake;
            default: // 오류
                return R.drawable.ic_launcher_foreground;
        }
    }

    // 하늘 상태
    public static String getSky(String sky) {
        switch (sky) {
            case "1":
                return "맑음";
            case "3":
                return "구름 많음";
            case "4":
                return "흐림";
            default:
                return "오류 sky: " + sky;
        }
    }

    public static int getWeatherImage(String sky) {
        // 하늘 상태에 따라 이미지 반환
        switch (sky) {
            case "1": // 맑음
                return R.drawable.sun_cloud;
            case "3": // 구름 많음
                return R.drawable.very_cloudy;
            case "4": // 흐림
                return R.drawable.cloudy;
            default: // 오류
                return R.drawable.ic_launcher_foreground;
        }
    }

    // 옷 추천
    public static String getRecommends(int temp) {
        if (temp >= 5 && temp <= 8) {
            return "울 코트, 가죽 옷, 기모";
        } else if (temp >= 9 && temp <= 11) {
            return "트렌치 코트, 야상, 점퍼";
        } else if (temp >= 12 && temp <= 16) {
            return "자켓, 가디건, 청자켓";
        } else if (temp >= 17 && temp <= 19) {
            return "니트, 맨투맨, 후드, 긴바지";
        } else if (temp >= 20 && temp <= 22) {
            return "블라우스, 긴팔 티, 슬랙스";
        } else if (temp >= 23 && temp <= 27) {
            return "얇은 셔츠, 반바지, 면바지";
        } else if (temp >= 28 && temp <= 50) {
            return "민소매, 반바지, 린넨 옷";
        } else {
            return "패딩, 누빔 옷, 목도리";
        }
    }
}
