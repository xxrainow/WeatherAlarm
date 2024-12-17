package com.example.weatherapp.adapter;

import static com.example.weatherapp.utils.WeatherFormatter.getRainType;
import static com.example.weatherapp.utils.WeatherFormatter.getRainTypeImage;
import static com.example.weatherapp.utils.WeatherFormatter.getSky;
import static com.example.weatherapp.utils.WeatherFormatter.getWeatherImage;

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

    private ModelWeather[] items; // 시간대별 날씨 데이터 저장

    // 생성자
    public WeatherAdapter(ModelWeather[] items) {
        this.items = items; // 생성 시 ModelWeather[] items를 받아 items에 저장
    }

    // list_item_weather1.xml 레이아웃을 ViewHolder에 바인딩
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_weather1, parent, false);
        return new ViewHolder(itemView);
    }

    // position에 해당하는 데이터를 ViewHolder에 setItem 메서드에 전달
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
            //ImageView imgWeather = itemView.findViewById(R.id.imgWeather);
            ImageView imgRainType = itemView.findViewById(R.id.imgRainType);
            TextView tvTime = itemView.findViewById(R.id.tvTime);           // 시각
            TextView tvRainType = itemView.findViewById(R.id.tvRainType);   // 강수 형태
            TextView tvSky = itemView.findViewById(R.id.tvSky);             // 하늘 상태
            TextView tvTemp = itemView.findViewById(R.id.tvTemp);           // 온도

            //imgWeather.setImageResource(getWeatherImage(item.getSky()));
            imgRainType.setImageResource(getRainTypeImage(item.getRainType()));
            tvTime.setText(item.getFcstTime());
            tvRainType.setText(getRainType(item.getRainType()));
            tvSky.setText(getSky(item.getSky()));
            tvTemp.setText(item.getTemp() + "°");
        }
    }


}