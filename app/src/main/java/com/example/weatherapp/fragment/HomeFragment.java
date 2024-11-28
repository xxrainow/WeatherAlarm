package com.example.weatherapp.fragment;

import static com.example.weatherapp.currentlocation.Common.getBaseTime;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.adapter.WeatherAdapter;
import com.example.weatherapp.adapter.WeatherAdapter2;
import com.example.weatherapp.api.ApiObject;
import com.example.weatherapp.api.BODY;
import com.example.weatherapp.api.ITEM;
import com.example.weatherapp.api.ITEMS;
import com.example.weatherapp.api.WEATHER;
import com.example.weatherapp.currentlocation.Common;
import com.example.weatherapp.data.ModelWeather;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private RecyclerView weatherRecyclerView1; // 날씨 리사이클러 뷰(가로 슬라이드)
    private RecyclerView weatherRecyclerView2; // 날씨 리사이클러 뷰(세로 슬라이드)
    private TextView todayDate;

    private String base_date = "20241128";  // 발표 일자
    private String base_time = "1400";      // 발표 시각
    private Point curPoint;

    private View rootView;
    private Button btnRefresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // fragment_home.xml 레이아웃 inflate
        rootView = inflater.inflate(R.layout.fragment_home, container, false);


        todayDate = rootView.findViewById(R.id.todayDate); // 오늘 날짜 텍스트뷰
        weatherRecyclerView1 = rootView.findViewById(R.id.weatherRecyclerView1); // 날씨 리사이클러 뷰1
        weatherRecyclerView2 = rootView.findViewById(R.id.weatherRecyclerView2); // 날씨 리사이클러 뷰2
        btnRefresh = rootView.findViewById(R.id.btnRefresh); // 새로고침 버튼

        // 리사이클러 뷰 레이아웃 매니저 설정
        weatherRecyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        weatherRecyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        // 내 위치 위경도 가져와서 날씨 정보 설정 setWeather(nx, ny)와 동일
        requestLocation();

        // 새로고침 버튼 클릭 시 날씨 정보 다시 가져오기
        btnRefresh.setOnClickListener(v ->
                {
                    requestLocation();
                    Log.d("RefreshButton", "새로고침 버튼 클릭됨");
                    Toast.makeText(getContext(), "날씨 정보를 새로고침합니다.", Toast.LENGTH_SHORT).show();
                });

        return rootView;
    }

    // 날씨 정보 가져와서 설정하기
    private void setWeather(String nx, String ny) {
        // 준비 단계: base_date(발표 일자), base_time(발표 시각)
        Calendar cal = Calendar.getInstance();
        base_date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.getTime()); // 현재 날짜
        String timeH = new SimpleDateFormat("HH", Locale.getDefault()).format(cal.getTime());   // 현재 시각 (시)
        String timeM = new SimpleDateFormat("mm", Locale.getDefault()).format(cal.getTime());   // 현재 시각 (분)

        // API에 적합한 시간 변환
        base_time = getBaseTime(timeH, timeM);

        // 현재 시각이 00시이고 45분 이하면 base_time이 2330으로 어제 정보 가져오기
        if (timeH.equals("00") && base_time.equals("2330")) {
            cal.add(Calendar.DATE, -1);
            base_date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.getTime());
        }

        // 로그 출력: base_date, base_time, nx, ny 값 확인
        Log.d("API Request", "base_date: " + base_date + ", base_time: " + base_time + ", nx: " + nx + ", ny: " + ny);

        // 날씨 정보 API 호출
        Call<WEATHER> call = ApiObject.retrofitService.getWeather(60, 1, "JSON", base_date, base_time, nx, ny);

        // 로그 출력: 요청 URL 확인
        Log.d("API Request", "Request URL: " + call.request().url());

        // 비동기적으로 API 호출
        call.enqueue(new Callback<WEATHER>() {
            // API 호출 성공 시
            @Override
            public void onResponse(@NonNull Call<WEATHER> call, @NonNull Response<WEATHER> response) {
                if (response.isSuccessful() && response.body() != null) { // response.body(): 응답 본체, 여기서 날씨 데이터가 포함된 ITEM 객체 리스트
                    // 응답 본체를 JSON 형식으로 로그에 출력
                    Log.d("API Response", new Gson().toJson(response.body()));

                    // List<ITEM> items = response.body().getResponse().getBody().getItems().getItem();

                    // API 응답을 안전하게 가져오기
                    BODY body = response.body().getResponse().getBody();
                    if (body == null) {
                        Log.e("API Error", "Response body is null");
                        Toast.makeText(getContext(), "날씨 정보를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                        return; // 더 이상 진행하지 않고 종료
                    }

                    // items가 null인지 확인
                    ITEMS items = body.getItems();
                    if (items == null || items.getItem() == null) {
                        Log.e("API Error", "Items are null");
                        Toast.makeText(getContext(), "날씨 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                        return; // 더 이상 진행하지 않고 종료
                    }

                    // 날씨 데이터 처리
                    List<ITEM> itemList = items.getItem();

                    // weatherList : 현재 시각부터 1시간 뒤의 날씨 6개를 담을 리스트
                    List<ModelWeather> weatherList = new ArrayList<>();
                    for (int i = 0; i < 6; i++) {
                        weatherList.add(new ModelWeather());
                    }

                    // 리스트 데이터 채우기
                    int index = 0;
                    int totalCount = response.body().getResponse().getBody().getTotalCount();
                    for (int i = 0; i < totalCount; i++) {
                        index %= 6; // 인덱스를 0~5로 제한
                        ITEM item = itemList.get(i);

                        switch (item.getCategory()) {
                            case "PTY": // 강수 형태
                                weatherList.get(index).setRainType(item.getFcstValue());
                                break;
                            case "REH": // 습도
                                weatherList.get(index).setHumidity(item.getFcstValue());
                                break;
                            case "SKY": // 하늘 상태
                                weatherList.get(index).setSky(item.getFcstValue());
                                break;
                            case "T1H": // 기온
                                weatherList.get(index).setTemp(item.getFcstValue());
                                break;
                            default:
                                continue;
                        }
                        index++;
                    }

                    // 각 날짜 배열 시간 설정
                    for (int i = 0; i < 6; i++) {
                        weatherList.get(i).setFcstTime(itemList.get(i).getFcstTime());
                    }

                    // RecyclerView에 어댑터 연결
                    weatherRecyclerView1.setAdapter(new WeatherAdapter(weatherList.toArray(new ModelWeather[0])));
                    weatherRecyclerView2.setAdapter(new WeatherAdapter2(weatherList.toArray(new ModelWeather[0])));

                    // 토스트 메시지 표시
                    Toast.makeText(getContext(),
                            itemList.get(0).getFcstDate() + ", " + itemList.get(0).getFcstTime() + "의 날씨 정보입니다.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("API Error", "Response is not successful");
                    Toast.makeText(getContext(), "API 호출에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            // API 호출 실패 시
            @Override
            public void onFailure(@NonNull Call<WEATHER> call, @NonNull Throwable t) {
                TextView todayError = rootView.findViewById(R.id.todayError);
                todayError.setText("API 호출 실패: " + t.getMessage() + "\n 다시 시도해주세요.");
                todayError.setVisibility(View.VISIBLE);
                Log.e("API Fail", t.getMessage());
            }
        });
    }

    // 내 현재 위치의 위경도를 격자 좌표로 변환하여 해당 위치의 날씨정보 설정하기
    private void requestLocation() {
        // 사용자 위치 정보 계산
        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        try{
            // 현재 위치 요청
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(60*1000); // 요청 간격 1분

            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    for (android.location.Location location : locationResult.getLocations()) {
                        // 현재 위치의 위경도를 격자좌표로 변환
                        curPoint = Common.dfs_xy_conv(location.getLatitude(), location.getLongitude());
                        Log.d("LocationResult", "위도: " + location.getLatitude() + ", 경도: " + location.getLongitude());

                        // 오늘 날짜 텍스트뷰 설정
                        todayDate.setText(new SimpleDateFormat("MM월 dd일", Locale.getDefault()).format(Calendar.getInstance().getTime()) + " 날씨");

                        // nx, ny 지점의 날씨 가져오기
                        setWeather(String.valueOf(curPoint.x), String.valueOf(curPoint.y));
                    }
                }
            };
            // 위치 요청 시작
            locationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } catch (SecurityException e) {
            Log.e("RequestLocation", "위치 권한 오류: " + e.getMessage());
        }
    }
}
