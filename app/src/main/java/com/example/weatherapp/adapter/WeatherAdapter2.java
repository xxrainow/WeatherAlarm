package com.example.weatherapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.data.ModelWeather;
import com.example.weatherapp.R;

public class WeatherAdapter2 extends RecyclerView.Adapter<WeatherAdapter2.ViewHolder> {

    private ModelWeather[] items;

    // 생성자
    public WeatherAdapter2(ModelWeather[] items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_weather2, parent, false);
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
            TextView tvTime = itemView.findViewById(R.id.tvTime);           // 시각
            TextView tvRainType = itemView.findViewById(R.id.tvRainType);   // 강수 형태
            TextView tvHumidity = itemView.findViewById(R.id.tvHumidity);   // 습도
            TextView tvSky = itemView.findViewById(R.id.tvSky);             // 하늘 상태
            TextView tvTemp = itemView.findViewById(R.id.tvTemp);           // 온도
            TextView tvRecommends = itemView.findViewById(R.id.tvRecommends); // 옷 추천

            tvTime.setText(item.getFcstTime());
            tvRainType.setText(getRainType(item.getRainType()));
            tvHumidity.setText(item.getHumidity());
            tvSky.setText(getSky(item.getSky()));
            tvTemp.setText(item.getTemp() + "°");

            try {
                tvRecommends.setText(getRecommends(Integer.parseInt(item.getTemp())));
            } catch (NumberFormatException e) {
                tvRecommends.setText("온도 데이터 오류");
            }
        }
    }

    // 강수 형태
    private String getRainType(String rainType) {
        switch (rainType) {
            case "0":
                return "없음";
            case "1":
                return "비";
            case "2":
                return "비/눈";
            case "3":
                return "눈";
            default:
                return "오류 rainType: " + rainType;
        }
    }

    // 하늘 상태
    private String getSky(String sky) {
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

    // 옷 추천
    private String getRecommends(int temp) {
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