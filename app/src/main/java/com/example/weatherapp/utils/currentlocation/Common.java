package com.example.weatherapp.utils.currentlocation;

import android.graphics.Point;
import android.util.Log;

public class Common {

    // baseTime 설정하기
    public static String getBaseTime(String currentHour, String currentMinute) {
        int hour = Integer.parseInt(currentHour);
        int minute = Integer.parseInt(currentMinute);

        // 45분 기준 이전 시간으로 설정
        if (minute < 45) {
            if (hour == 0) {
                return "2330"; // 자정 이전은 전날 23:30 데이터 사용
            }
            hour--; // 1시간 전으로 이동
            return String.format("%02d30", hour); // 두 자리 포맷
        }

        // 45분 이후는 현재 시간 30분 사용
        return String.format("%02d30", hour); // 두 자리 포맷
    }

    // 위경도를 기상청에서 사용하는 격자 좌표로 변환
    public static Point dfs_xy_conv(double latitude, double longitude) {
        // 입력값 로그 출력
        Log.d("Input log", "Input Latitude: " + latitude + ", Longitude: " + longitude);

        // 상수 값 정의
        final double RE = 6371.00877;     // 지구 반경(km)
        final double GRID = 5.0;          // 격자 간격(km)
        final double SLAT1 = 30.0;        // 투영 위도1(degree)
        final double SLAT2 = 60.0;        // 투영 위도2(degree)
        final double OLON = 126.0;        // 기준점 경도(degree)
        final double OLAT = 38.0;         // 기준점 위도(degree)
        final int XO = 43;                // 기준점 X좌표(GRID)
        final int YO = 136;               // 기준점 Y좌표(GRID)
        final double DEGRAD = Math.PI / 180.0;

        // 입력값 검증 (위도와 경도의 유효 범위 확인)
        if (latitude < 0 || latitude > 149.0 || longitude < 0 || longitude > 253.0) {
            Log.e("invalid input", "위도 또는 경도가 유효하지 않습니다. 위도는 33~43, 경도는 124~132 범위여야 합니다.");
        }

        // 격자 좌표 계산 준비
        double re = RE / GRID; // 지구 반경 / 격자 간격
        double slat1 = SLAT1 * DEGRAD; // 위도1 라디안 변환
        double slat2 = SLAT2 * DEGRAD; // 위도2 라디안 변환
        double olon = OLON * DEGRAD;   // 기준 경도 라디안 변환
        double olat = OLAT * DEGRAD;   // 기준 위도 라디안 변환

        // 투영 계수 계산
        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);

        // 입력된 위경도 계산
        double ra = Math.tan(Math.PI * 0.25 + (latitude) * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);
        double theta = longitude * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;

        // X, Y 좌표 계산
        int x = (int) (ra * Math.sin(theta) + XO + 0.5); // 반올림
        int y = (int) (ro - ra * Math.cos(theta) + YO + 0.5); // 반올림

        // 변환된 값 로그 출력
        Log.d("converted value", "Converted Grid Coordinates: nx = " + x + ", ny = " + y);

        // 유효 범위 확인
        if (x < 0 || x > 149 || y < 0 || y > 253) {
            Log.e("invalid value", "계산된 격자 좌표(nx, ny)가 유효 범위를 벗어났습니다. nx = " + x + ", ny = " + y);
        }

        return new Point(x, y);
    }
}
