package com.example.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.adapter.WeatherAdapter;
import com.example.weatherapp.api.ApiObject;
import com.example.weatherapp.api.ITEM;
import com.example.weatherapp.api.WEATHER;
import com.example.weatherapp.data.ModelWeather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView weatherRecyclerView;

    private String base_date = "20210510";  // 발표 일자
    private String base_time = "1400";      // 발표 시각
    private String nx = "55";               // 예보지점 X 좌표
    private String ny = "127";              // 예보지점 Y 좌표

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView todayDate = findViewById(R.id.todayDate); // 오늘 날짜 텍스트뷰
        weatherRecyclerView = findViewById(R.id.weatherRecyclerView); // 날씨 리사이클러 뷰
        Button btnRefresh = findViewById(R.id.btnRefresh); // 새로고침 버튼

        // 리사이클러 뷰 레이아웃 매니저 설정
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 오늘 날짜 텍스트뷰 설정
        todayDate.setText(new SimpleDateFormat("MM월 dd일", Locale.getDefault()).format(Calendar.getInstance().getTime()) + " 날씨");

        // nx, ny 지점의 날씨 가져와서 설정하기
        setWeather(nx, ny);

        // 새로고침 버튼 클릭 시 날씨 정보 다시 가져오기
        btnRefresh.setOnClickListener(v -> setWeather(nx, ny));
    }

    // 날씨 정보 가져와서 설정하기
    private void setWeather(String nx, String ny) {
        // 준비 단계: base_date(발표 일자), base_time(발표 시각)
        Calendar cal = Calendar.getInstance();
        base_date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.getTime()); // 현재 날짜
        String timeH = new SimpleDateFormat("HH", Locale.getDefault()).format(cal.getTime());   // 현재 시각 (시)
        String timeM = new SimpleDateFormat("mm", Locale.getDefault()).format(cal.getTime());   // 현재 시각 (분)

        // API 시간 변환
        base_time = getBaseTime(timeH, timeM);

        // 현재 시각이 00시이고 45분 이하면 base_time이 2330으로 어제 정보 가져오기
        if (timeH.equals("00") && base_time.equals("2330")) {
            cal.add(Calendar.DATE, -1);
            base_date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.getTime());
        }

        // 날씨 정보 API 호출
        Call<WEATHER> call = ApiObject.retrofitService.getWeather(60, 1, "JSON", base_date, base_time, nx, ny);

        // 비동기적으로 실행
        call.enqueue(new Callback<WEATHER>() {
            @Override
            public void onResponse(@NonNull Call<WEATHER> call, @NonNull Response<WEATHER> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ITEM> items = response.body().getResponse().getBody().getItems().getItem();

                    // 현재 시각부터 1시간 뒤의 날씨 6개를 담을 리스트
                    List<ModelWeather> weatherList = new ArrayList<>();
                    for (int i = 0; i < 6; i++) {
                        weatherList.add(new ModelWeather());
                    }

                    // 리스트 데이터 채우기
                    int index = 0;
                    int totalCount = response.body().getResponse().getBody().getTotalCount();
                    for (int i = 0; i < totalCount; i++) {
                        index %= 6;
                        ITEM item = items.get(i);

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
                        weatherList.get(i).setFcstTime(items.get(i).getFcstTime());
                    }

                    // RecyclerView에 데이터 연결
                    weatherRecyclerView.setAdapter(new WeatherAdapter(weatherList.toArray(new ModelWeather[0])));

                    // 토스트 메시지 표시
                    Toast.makeText(getApplicationContext(),
                            items.get(0).getFcstDate() + ", " + items.get(0).getFcstTime() + "의 날씨 정보입니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<WEATHER> call, @NonNull Throwable t) {
                TextView tvError = findViewById(R.id.todayError);
                tvError.setText("API 호출 실패: " + t.getMessage() + "\n 다시 시도해주세요.");
                tvError.setVisibility(View.VISIBLE);
                Log.e("API Fail", t.getMessage());
            }
        });
    }

    // baseTime 설정하기
    private String getBaseTime(String h, String m) {
        String result;

        // 45분 전이면
        if (Integer.parseInt(m) < 45) {
            // 0시면 2330
            if (h.equals("00")) {
                result = "2330";
            } else {
                // 1시간 전 시간 계산
                int resultH = Integer.parseInt(h) - 1;
                result = (resultH < 10 ? "0" : "") + resultH + "30"; // 2자리로 맞추기
            }
        } else {
            // 45분 이후 바로 정보 가져오기
            result = h + "30";
        }

        return result;
    }
}
