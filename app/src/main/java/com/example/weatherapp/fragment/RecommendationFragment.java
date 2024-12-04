package com.example.weatherapp.fragment;

import static com.example.weatherapp.utils.WeatherFormatter.getClothesDescription;
import static com.example.weatherapp.utils.WeatherFormatter.getRainType;
import static com.example.weatherapp.utils.WeatherFormatter.getSky;
import static com.example.weatherapp.utils.WeatherFormatter.getWeatherImage;

import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.api.WeatherApiService;
import com.example.weatherapp.api.WeatherCallback;
import com.example.weatherapp.data.ModelWeather;
import com.example.weatherapp.utils.WeatherCoordinateConverter;
import com.example.weatherapp.utils.WeatherFormatter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RecommendationFragment extends Fragment implements WeatherCallback {

    private ViewPager2 viewPager;
    private View rootView;

    private TextView todayDate; // 오늘 날짜 텍스트뷰
    private ImageView imgWeather; // 날씨 이미지뷰
    private TextView tvTemperature; // 온도 텍스트뷰
    private TextView tvRainType; // 강수 형태 텍스트뷰
    private TextView tvSky; // 하늘 상태 텍스트뷰

    private ImageView imgCloth1; // 옷 이미지뷰 1
    private ImageView imgCloth2; // 옷 이미지뷰 2
    private ImageView imgCloth3; // 옷 이미지뷰 3
    private TextView tvCloth1; // 옷 텍스트뷰 1
    private TextView tvCloth2; // 옷 텍스트뷰 2
    private TextView tvCloth3; // 옷 텍스트뷰 3
    private TextView tvWeatherDesription;

    // 발표 시각
    private Point curPoint;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recommendation, container, false);

        todayDate = rootView.findViewById(R.id.todayDate); // 오늘 날짜 텍스트뷰
        imgWeather = rootView.findViewById(R.id.imgWeather);
        tvTemperature = rootView.findViewById(R.id.tvTemperature);
        tvRainType = rootView.findViewById(R.id.tvRainType); // 강수 형태 텍스트뷰
        tvSky = rootView.findViewById(R.id.tvSky); // 하늘 상태 텍스트뷰

        imgCloth1 = rootView.findViewById(R.id.imgCloth1); // 옷 이미지뷰 1
        imgCloth2 = rootView.findViewById(R.id.imgCloth2); // 옷 이미지뷰 2
        imgCloth3 = rootView.findViewById(R.id.imgCloth3); // 옷 이미지뷰 3
        tvCloth1 = rootView.findViewById(R.id.tvCloth1); // 옷 텍스트뷰 1
        tvCloth2 = rootView.findViewById(R.id.tvCloth2); // 옷 텍스트뷰 2
        tvCloth3 = rootView.findViewById(R.id.tvCloth3); // 옷 텍스트뷰 3

        tvWeatherDesription = rootView.findViewById(R.id.tvWeatherDesription);

        requestLocation();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void updateWeatherView(String rainType, String temp, String humidity, String sky) {
        imgWeather.setImageResource(getWeatherImage(sky));
        tvTemperature.setText(temp + "°C"); // 온도
        tvSky.setText(getSky(sky)); // 하늘 상태
        tvRainType.setText(getRainType(rainType)); // 강수 형태
        todayDate.setText(new SimpleDateFormat("MM월 dd일의", Locale.getDefault()).format(Calendar.getInstance().getTime()) + " 날씨");

        // 온도에 따른 옷 추천 이미지 설정
        int temperature = Integer.parseInt(temp); // 온도를 정수로 변환
        setClothesImages(temperature);
        setClothesItems(temperature);

        tvWeatherDesription.setText("✔\uFE0F 오늘의 날씨에는 " + getClothesDescription(temperature) + "(이)가 적당해요!");

    }

    private void setClothesImages(int temp) {
        int[] clothesImages = WeatherFormatter.getClothesImage(temp); // 온도에 따른 옷 이미지 배열 가져오기

        // 이미지뷰에 이미지 설정
        imgCloth1.setImageResource(clothesImages[0]);
        imgCloth2.setImageResource(clothesImages[1]);
        imgCloth3.setImageResource(clothesImages[2]);
    }

    private void setClothesItems(int temp) {
        String[] clothesItems = WeatherFormatter.getClothesItem(temp); // 온도에 따른 옷 이미지 배열 가져오기

        // 이미지뷰에 이미지 설정
        tvCloth1.setText(clothesItems[0]);
        tvCloth2.setText(clothesItems[1]);
        tvCloth3.setText(clothesItems[2]);
    }

    @Override
    public void updateRecyclerView(List<ModelWeather> weatherList) {
        // Adapter가 없으므로 데이터만 UI에 직접 반영하는 로직 작성
        // 예: 텍스트뷰 업데이트 등
    }

    private void requestLocation() {
        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        try {
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(60 * 1000); // 1분 간격

            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    for (android.location.Location location : locationResult.getLocations()) {
                        curPoint = WeatherCoordinateConverter.dfs_xy_conv(location.getLatitude(), location.getLongitude());
                        Log.d("LocationResult", "위도: " + location.getLatitude() + ", 경도: " + location.getLongitude());

                        // 현재 위치의 날씨 데이터 가져오기
                        WeatherApiService.fetchWeather(getContext(), String.valueOf(curPoint.x), String.valueOf(curPoint.y), RecommendationFragment.this
                        );
                    }
                }
            };

            locationClient.requestLocationUpdates(locationRequest, locationCallback, null);

        } catch (SecurityException e) {
            Log.e("RequestLocation", "위치 권한 오류: " + e.getMessage());
        }

    }
}
