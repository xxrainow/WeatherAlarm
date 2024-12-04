package com.example.weatherapp.adapter;

import static com.example.weatherapp.utils.WeatherFormatter.getClothesDescription;
import static com.example.weatherapp.utils.WeatherFormatter.getRainType;
import static com.example.weatherapp.utils.WeatherFormatter.getSky;

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
            TextView tvTime = itemView.findViewById(R.id.tvTime);               // 시각
            TextView tvRainType = itemView.findViewById(R.id.tvRainType);       // 강수 형태
            TextView tvHumidity = itemView.findViewById(R.id.tvHumidity);       // 습도
            TextView tvSky = itemView.findViewById(R.id.tvSky);                 // 하늘 상태
            TextView tvTemp = itemView.findViewById(R.id.tvTemp);               // 온도
            TextView tvRecommends = itemView.findViewById(R.id.tvRecommends);   // 옷 추천

            tvTime.setText(item.getFcstTime());
            tvRainType.setText(getRainType(item.getRainType()));
            tvHumidity.setText(item.getHumidity());
            tvSky.setText(getSky(item.getSky()));
            tvTemp.setText(item.getTemp() + "°");

            try {
                tvRecommends.setText(getClothesDescription(Integer.parseInt(item.getTemp())));
            } catch (NumberFormatException e) {
                tvRecommends.setText("온도 데이터 오류");
            }
        }
    }

}