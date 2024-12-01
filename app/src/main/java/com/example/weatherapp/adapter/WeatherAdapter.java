package com.example.weatherapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.data.ModelWeather;
import com.example.weatherapp.R;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private ModelWeather[] items;

    // 생성자
    public WeatherAdapter(ModelWeather[] items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_weather1, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelWeather item = items[position];
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    // ViewHolder 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setItem(ModelWeather item) {
            ImageView imgWeather = itemView.findViewById(R.id.imgWeather);
            ImageView imgRainType = itemView.findViewById(R.id.imgRainType);
            TextView tvTime = itemView.findViewById(R.id.tvTime);           // 시각
            TextView tvRainType = itemView.findViewById(R.id.tvRainType);   // 강수 형태
            TextView tvHumidity = itemView.findViewById(R.id.tvHumidity);   // 습도
            TextView tvSky = itemView.findViewById(R.id.tvSky);             // 하늘 상태
            TextView tvTemp = itemView.findViewById(R.id.tvTemp);           // 온도

            imgWeather.setImageResource(getWeatherImage(item.getSky()));
            imgRainType.setImageResource(getRainTypeImage(item.getRainType()));
            tvTime.setText(item.getFcstTime());
            tvRainType.setText(getRainType(item.getRainType()));
            //tvHumidity.setText(item.getHumidity());
            tvSky.setText(getSky(item.getSky()));
            tvTemp.setText(item.getTemp() + "°");
        }
    }

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
}