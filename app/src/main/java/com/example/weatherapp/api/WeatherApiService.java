package com.example.weatherapp.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.weatherapp.data.ModelWeather;
import com.example.weatherapp.fragment.HomeFragment;
import com.example.weatherapp.utils.WeatherCoordinateConverter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherApiService {

    // 날씨 정보를 가져오는 메서드
    public static void fetchWeather(Context context, String nx, String ny, WeatherCallback callback) {
        String base_date = "20241130";  // 발표 일자
        String base_time = "2000";      // 발표 시각

        // 날짜와 시간 계산
        Calendar cal = Calendar.getInstance();
        String timeH = new SimpleDateFormat("HH", Locale.getDefault()).format(cal.getTime());   // 현재 시각 (시)
        String timeM = new SimpleDateFormat("mm", Locale.getDefault()).format(cal.getTime());   // 현재 시각 (분)

        base_date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.getTime()); // 현재 날짜
        base_time = WeatherCoordinateConverter.getBaseTime(timeH, timeM);  // base_time 계산

        if (timeH.equals("00") && base_time.equals("2330")) {
            cal.add(Calendar.DATE, -1);
            base_date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.getTime());
        }

        // 날씨 정보 API 호출
        Call<WEATHER> call = ApiObject.retrofitService.getWeather(60, 1, "JSON", base_date, base_time, nx, ny);
        Log.d("API Request", "Request URL: " + call.request().url());

        call.enqueue(new Callback<WEATHER>() {
            @Override
            public void onResponse(Call<WEATHER> call, Response<WEATHER> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BODY body = response.body().getResponse().getBody();
                    if (body == null) {
                        Log.e("API Error", "Response body is null");
                        Toast.makeText(context, "날씨 정보를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ITEMS items = body.getItems();
                    if (items == null || items.getItem() == null) {
                        Toast.makeText(context, "날씨 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    List<ITEM> itemList = items.getItem();
                    List<ModelWeather> weatherList = new ArrayList<>();
                    for (int i = 0; i < 6; i++) {
                        weatherList.add(new ModelWeather());
                    }

                    int index = 0;
                    int totalCount = response.body().getResponse().getBody().getTotalCount();
                    for (int i = 0; i < totalCount; i++) {
                        index %= 6;
                        ITEM item = itemList.get(i);

                        switch (item.getCategory()) {
                            case "PTY":
                                weatherList.get(index).setRainType(item.getFcstValue());
                                break;
                            case "REH":
                                weatherList.get(index).setHumidity(item.getFcstValue());
                                break;
                            case "SKY":
                                weatherList.get(index).setSky(item.getFcstValue());
                                break;
                            case "T1H":
                                weatherList.get(index).setTemp(item.getFcstValue());
                                break;
                            case "RN1":
                                weatherList.get(index).setRain(item.getFcstValue());
                                break;
                            default:
                                continue;
                        }
                        index++;
                    }

                    if (!weatherList.isEmpty()) {
                        ModelWeather firstWeather = weatherList.get(0);
                        String rainType = firstWeather.getRainType();
                        String temp = firstWeather.getTemp();
                        String humidity = firstWeather.getHumidity();
                        String sky = firstWeather.getSky();
                        callback.updateWeatherView(rainType, temp, humidity,sky);  // 날씨 정보 업데이트 메서드 호출
                        for (int i = 0; i < 6; i++) {
                            weatherList.get(i).setFcstTime(itemList.get(i).getFcstTime());
                        }
                    }

                    callback.updateRecyclerView(weatherList);
                    Toast.makeText(context, itemList.get(0).getFcstDate() + ", " + itemList.get(0).getFcstTime() + "의 날씨 정보입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("API Error", "응답 실패: " + response.code() + " - " + response.message());
                    Toast.makeText(context, "API 호출에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WEATHER> call, Throwable t) {
                Log.e("API Fail", "네트워크 오류: " + t.getMessage());
                Toast.makeText(context, "API 호출 실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
