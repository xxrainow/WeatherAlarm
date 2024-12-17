package com.example.weatherapp.fragment;

import static com.example.weatherapp.utils.WeatherFormatter.getRainType;
import static com.example.weatherapp.utils.WeatherFormatter.getRainTypeImage;
import static com.example.weatherapp.utils.WeatherFormatter.getSky;
import static com.example.weatherapp.utils.WeatherFormatter.getWeatherImage;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.adapter.WeatherAdapter;
import com.example.weatherapp.adapter.WeatherAdapter2;
import com.example.weatherapp.api.WeatherApiService;
import com.example.weatherapp.api.WeatherCallback;
import com.example.weatherapp.utils.WeatherCoordinateConverter;
import com.example.weatherapp.data.ModelWeather;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements WeatherCallback {
    private RecyclerView weatherRecyclerView1; // 날씨 리사이클러 뷰(가로 슬라이드)
    private RecyclerView weatherRecyclerView2; // 날씨 리사이클러 뷰(세로 슬라이드)

    private TextView todayDate;
    private ImageView imgWeather;
    private TextView tvTemperature;
    private TextView tvRainType; // 강수 형태
    private TextView tvHumidity;
    private TextView tvSky; // 하늘 상태
    private TextView tvAdminArea; // 행정구역
    private TextView tvAddress; // 전체 주소
    private ImageView imgRainType;

    private Point curPoint;

    private View rootView;
    private Button btnRefresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // fragment_home.xml 레이아웃 inflate
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        todayDate = rootView.findViewById(R.id.todayDate); // 오늘 날짜 텍스트뷰
        imgWeather = rootView.findViewById(R.id.imgWeather);
        imgRainType = rootView.findViewById(R.id.imgRainType); // 강수 형태 이미지뷰
        tvTemperature = rootView.findViewById(R.id.tvTemperature);
        tvRainType = rootView.findViewById(R.id.tvRainType); // 강수 형태 텍스트뷰
        tvHumidity = rootView.findViewById(R.id.tvHumidity); // 습도 텍스트뷰
        tvSky = rootView.findViewById(R.id.tvSky); // 하늘 상태 텍스트뷰
        tvAdminArea = rootView.findViewById(R.id.tvAdminArea); // 행정구역 텍스트뷰
        tvAddress = rootView.findViewById(R.id.tvAddress); // 전체 주소 텍스트뷰

        btnRefresh = rootView.findViewById(R.id.btnRefresh); // 새로고침 버튼
        weatherRecyclerView1 = rootView.findViewById(R.id.weatherRecyclerView1); // 날씨 리사이클러 뷰1
        weatherRecyclerView2 = rootView.findViewById(R.id.weatherRecyclerView2); // 날씨 리사이클러 뷰2

        // 비어 있는 데이터를 가진 기본 어댑터를 설정(No adapter attached; skipping layout 에러 해결)
        weatherRecyclerView1.setAdapter(new WeatherAdapter(new ModelWeather[0]));
        weatherRecyclerView2.setAdapter(new WeatherAdapter2(new ModelWeather[0]));

        // 리사이클러 뷰 레이아웃 설정
        weatherRecyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        weatherRecyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        // 내 위치 위경도 가져와서 날씨 정보 설정 setWeather(nx, ny)와 동일
        requestLocation();

        // 새로고침 버튼 클릭 시 날씨 정보 다시 가져오기
        btnRefresh.setOnClickListener(v -> {
            requestLocation();
            Toast.makeText(getContext(), "날씨 정보를 새로고침합니다.", Toast.LENGTH_SHORT).show();
        });
        return rootView;
    }

    private void setAddress(double latitude, double longitude) {
        try {
            Geocoder geocoder = new Geocoder(requireContext(), Locale.KOREA);
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String adminArea = address.getAdminArea() != null ? address.getAdminArea() : "정보 없음";
                String fullAddress = address.getAddressLine(0) != null ? address.getAddressLine(0) : "정보 없음";

                // 텍스트뷰에 주소 정보 설정
                tvAdminArea.setText(adminArea); // 행정구역
                tvAddress.setText(fullAddress); // 전체 주소

                Log.d("AddressInfo", "AdminArea: " + adminArea + ", FullAddress: " + fullAddress);
            } else {
                tvAdminArea.setText("행정구역 정보를 가져올 수 없습니다.");
                tvAddress.setText("전체 주소 정보를 가져올 수 없습니다.");
            }
        } catch (IOException e) {
            Toast.makeText(requireContext(), "주소를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            Log.e("GeoCoderError", "주소 가져오기 실패: " + e.getMessage());
        }
    }

    private void requestLocation() {
        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        try {
            // 현재 위치 요청
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(60 * 1000); // 요청 간격 1분

            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    for (android.location.Location location : locationResult.getLocations()) {
                        // 현재 위치의 위경도를 가져옴
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        Log.d("LocationResult", "위도: " + latitude + ", 경도: " + longitude);

                        // 주소 업데이트
                        setAddress(latitude, longitude);

                        // 현재 위치의 날씨 정보 가져오기
                        curPoint = WeatherCoordinateConverter.dfs_xy_conv(latitude, longitude);
                        WeatherApiService.fetchWeather(getContext(), String.valueOf(curPoint.x), String.valueOf(curPoint.y), HomeFragment.this);
                    }
                }
            };
            // 위치 요청 시작
            locationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } catch (SecurityException e) {
            Log.e("RequestLocation", "위치 권한 오류: " + e.getMessage());
        }
    }

    // 날씨 정보를 업데이트하는 메서드
    public void updateWeatherView(String rainType, String temp, String humidity, String sky) {
        //imgWeather.setImageResource(getWeatherImage(sky));
        imgRainType.setImageResource(getRainTypeImage(rainType));
        tvTemperature.setText(temp + "°");
        tvRainType.setText("강수 형태: " + getRainType(rainType));
        tvHumidity.setText("습도: " + humidity + "%");
        tvSky.setText("하늘 상태: " + getSky(sky));
        todayDate.setText(new SimpleDateFormat("MM월 dd일", Locale.getDefault()).format(Calendar.getInstance().getTime()) + " 날씨");
    }

    // RecyclerView에 어댑터 설정
    public void updateRecyclerView(List<ModelWeather> weatherList) {
        weatherRecyclerView1.setAdapter(new WeatherAdapter(weatherList.toArray(new ModelWeather[0])));
        weatherRecyclerView2.setAdapter(new WeatherAdapter2(weatherList.toArray(new ModelWeather[0])));
    }
}
