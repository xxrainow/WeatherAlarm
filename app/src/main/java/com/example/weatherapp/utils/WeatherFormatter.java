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
                return R.drawable.raintype_sun;
            case "1": // 비
                return R.drawable.raintype_rain;
            case "2": // 비/눈
                return R.drawable.raintype_rain_snow;
            case "3": // 눈
                return R.drawable.raintype_snow;
            case "5": // 빗방울
                return R.drawable.raintype_raindrop;
            case "6": // 빗방울/눈날림
                return R.drawable.raintype_raindrop_snow;
            case "7": // 눈날림
                return R.drawable.raintype_snowflake;
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
                return R.drawable.sky_1_sun_cloud;
            case "3": // 구름 많음
                return R.drawable.sky_3_very_cloudy;
            case "4": // 흐림
                return R.drawable.sky_4_cloudy;
            default: // 오류
                return R.drawable.ic_launcher_foreground;
        }
    }


    // 옷 추천
    public static String getClothesDescription(int temp) {
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
            return "패딩, 두꺼운 코드, 목도리";
        }
    }

    // 옷 추천
    public static String[] getClothesItem(int temp) {
        if (temp >= 5 && temp <= 8) {
            return new String[]{"패딩", "울 코트", "털모자"};
        } else if (temp >= 9 && temp <= 11) {
            return new String[]{"두꺼운 바지", "자켓", "트렌치 코트"};
        } else if (temp >= 12 && temp <= 16) {
            return new String[]{"청바지", "두꺼운 가디건", "자켓"};
        } else if (temp >= 17 && temp <= 19) {
            return new String[]{"얇은 가디건", "후드", "긴팔티"};
        } else if (temp >= 20 && temp <= 22) {
            return new String[]{"블라우스", "긴바지", "티셔츠"};
        } else if (temp >= 23 && temp <= 27) {
            return new String[]{"하와이안 셔츠", "반팔 티", "반바지"};
        } else if (temp >= 28 && temp <= 50) {
            return new String[]{"짧은 바지", "민소매", "나시 티"};
        } else {
            return new String[]{"패딩", "두꺼운 코트", "모자"};
        }
    }


    public static int[] getClothesImage(int temp) {
        // 온도에 따라 이미지 배열 반환
        if (temp >= 28) {
            return new int[]{R.drawable.temp8_shorts, R.drawable.temp8_sleeveless_shirt, R.drawable.temp8_sleeveless_shirt2};
        } else if (temp >= 23) {
            return new int[]{R.drawable.temp7_hawaiian_shirt, R.drawable.temp7_shirts, R.drawable.temp7_shorts};
        } else if (temp >= 20) {
            return new int[]{R.drawable.temp6_blouse, R.drawable.temp6_pants, R.drawable.temp6_tshirt};
        } else if (temp >= 17) {
            return new int[]{R.drawable.temp5_cardigan, R.drawable.temp5_hoodie, R.drawable.temp5_tshirt};
        } else if (temp >= 12) {
            return new int[]{R.drawable.temp4_blue_jeans, R.drawable.temp4_cardigan, R.drawable.temp4_jacket2};
        } else if (temp >= 9) {
            return new int[]{R.drawable.temp3_heavy_pants, R.drawable.temp3_jacket, R.drawable.temp3_trench_coat};
        } else if (temp >= 5) {
            return new int[]{R.drawable.temp2_coat, R.drawable.temp2_coat2, R.drawable.temp2_leather_jacket};
        } else {
            return new int[]{R.drawable.temp1_padding, R.drawable.temp1_heavycoat, R.drawable.temp1_hat};
        }
    }

}
