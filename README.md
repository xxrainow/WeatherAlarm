# 날씨 알라미 WeatherAlarmi
Do you need an app with all the weather-related features? 
This app provides weather information, weather alerts, and clothing recommendations.

## Library 
- Retrofit + Gson (Gson converter for Retrofit)
- gms:play-services-location (위치 가져오는 라이브러리)
- io.github.ParkSangGwon:tedpermission:2.3.0 (위치 찾기 권한 설정을 위한 라이브러리)
- Gson
- GOOGLE MAP 라이브러리
- ROOM

## API
- 공공 데이터 기상청 초단기예보 API
- google map api
- geo coder api

## Tech stack and Device
- language : JAVA
- IDE : android studio
- API VERSION: 28 ~ 34
- DEVICE : PIXEL 5  API 28

## Permission
- 날씨 API 가져오기 위한 권한 추가 : INTERNET, ACCESS_NETWORK_STATE
- 위치 정보 가져오기 권한 추가: ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION
- 실시간 위치 정보 액세스 권한 추가 : ACCESS_BACKGROUND_LOCATION
- 앱에서 사용자에게 알림을 보내기 위한 권한 : POST_NOTIFICATIONS
- 그외 기기 설정 : WAKE_LOCK, RECEIVE_BOOT_COMPLETED, FOREGROUND_SERVICE

## Screen
- 날씨 정보 홈화면
<div style="display: flex; flex-wrap: wrap; justify-content: space-between; gap: 10px;">

  <img src="https://github.com/user-attachments/assets/2c707b6b-0df3-4dee-8101-0e7dfc1012d5" alt="jeju" style="width: 30%; max-width: 300px; height: auto; border: 1px solid #ccc; border-radius: 5px; box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);">

  <img src="https://github.com/user-attachments/assets/e7c2f3a7-22c4-42e2-82e9-9c76cb6e6cb6" alt="home" style="width: 30%; max-width: 300px; height: auto; border: 1px solid #ccc; border-radius: 5px; box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);">

  <img src="https://github.com/user-attachments/assets/7d7e6e38-2177-4cb2-a14e-014414da582b" alt="daegu" style="width: 30%; max-width: 300px; height: auto; border: 1px solid #ccc; border-radius: 5px; box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);">

  <img src="https://github.com/user-attachments/assets/6704294b-c2f1-4bbe-bc3d-1362a0fd34eb" alt="gwangju" style="width: 30%; max-width: 300px; height: auto; border: 1px solid #ccc; border-radius: 5px; box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);">

  <img src="https://github.com/user-attachments/assets/40355eba-3872-4c73-8c00-9480b752ab44" alt="busan" style="width: 30%; max-width: 300px; height: auto; border: 1px solid #ccc; border-radius: 5px; box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);">

</div>


- 옷 추천
<div style="display: flex; flex-wrap: wrap; justify-content: space-between; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/249b1b9e-8876-4fd5-876a-4174a448e5b3" alt="recommend" style="width: 30%; max-width: 300px; height: auto; border: 1px solid #ccc; border-radius: 5px; box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);">
</div>

- 알림 서비스
<div style="display: flex; flex-wrap: wrap; justify-content: space-between; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/91f64c16-5a70-495c-89b3-531ad9bef355" alt="alarm" style="width: 30%; max-width: 300px; height: auto; border: 1px solid #ccc; border-radius: 5px; box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);">
</div>

- 지도
<div style="display: flex; flex-wrap: wrap; justify-content: space-between; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/0a7e9126-7706-435d-8416-5e08ad0af421" alt="map" style="width: 30%; max-width: 300px; height: auto; border: 1px solid #ccc; border-radius: 5px; box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);">
</div>


- 시연 영상

https://github.com/user-attachments/assets/4449456e-6d6b-4be8-a989-ff97b063fa55




ref.
https://min-wachya.tistory.com/163
https://min-wachya.tistory.com/164?category=900504
https://velog.io/@byeongju/%EA%B3%B5%EA%B3%B5-%EB%8D%B0%EC%9D%B4%ED%84%B0-%ED%8F%AC%ED%84%B8-%EB%8B%A8%EA%B8%B0%EC%98%88%EB%B3%B4-api-%EC%97%90%EB%9F%AC-%EC%B2%98%EB%A6%AC
https://developer.android.com/reference/android/location/Address
https://tekken5953.tistory.com/17
https://developer.android.com/training/data-storage/shared-preferences?hl=ko
https://velog.io/@kkominl/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%95%B1%EA%B0%9C%EB%B0%9C-%EC%8B%AC%ED%99%94-1
