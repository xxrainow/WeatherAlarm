package com.example.weatherapp.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.example.weatherapp.R;

public class NotificationHelper {

    // 알림을 보낼 때 사용할 채널을 생성
    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "weather_alert_channel";
            CharSequence name = "Rain Alerts";
            String description = "Weather rain and snow alerts";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // 강수 형태에 따른 알림을 생성하여 전송
    public static void sendRainNotification(Context context, String rainType) {
        // NotificationChannel 생성 (Android 8.0 이상에서 필요)
        createNotificationChannel(context);

        String contentText = "";
        String contentTitle = "기상 알림";

        if ("비".equals(rainType)) {
            contentText = "오늘은 비가 올 예정입니다. 우산을 챙기세요!";
        } else if ("눈/비".equals(rainType)) {
            contentText = "오늘은 눈과 비가 함께 내릴 예정입니다. 외출 시 주의하세요!";
        } else if ("눈".equals(rainType)) {
            contentText = "오늘은 눈이 올 예정입니다. 따뜻한 옷을 입고 외출하세요!";
        } else {
            contentText = "현재 강수 정보가 없습니다.";
        }

        Notification notification = new NotificationCompat.Builder(context, "weather_alert_channel")
                //.setSmallIcon(R.drawable.ic_weather_alert) // 알림 아이콘
                .setContentTitle(contentTitle) // 알림 제목
                .setContentText(contentText) // 알림 내용
                .setPriority(NotificationCompat.PRIORITY_HIGH) // 우선순위 설정
                .setAutoCancel(true) // 클릭 시 알림 자동 취소
                .build();

        // NotificationManager를 통해 알림을 보냄
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //notificationManager.notify(1, notification);  // 1은 알림 ID
    }
}
