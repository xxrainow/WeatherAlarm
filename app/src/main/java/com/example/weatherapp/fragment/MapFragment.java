package com.example.weatherapp.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "MapFragment";

    private MapView mapView;
    private GoogleMap googleMap;
    private Marker currentMarker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        requestUserLocationAndSetupMarker();
        return rootView;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Default Marker 설정 (서울역)
        currentMarker = setupMarker(new LatLngEntity(37.5562, 126.9724));
        if (currentMarker != null) {
            currentMarker.showInfoWindow();
        }
    }

    private Marker setupMarker(LatLngEntity locationLatLngEntity) {
        LatLng positionLatLng = new LatLng(locationLatLngEntity.latitude, locationLatLngEntity.longitude);
        MarkerOptions markerOption = new MarkerOptions()
                .position(positionLatLng)
                .title("내 위치")
                .snippet("현재 위치를 기반으로 표시된 마커입니다.");

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionLatLng, 15f));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f));

        return googleMap.addMarker(markerOption);
    }


    private void requestUserLocationAndSetupMarker() {
        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        try {
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(60 * 1000); // 요청 간격 (1분)

            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    for (android.location.Location location : locationResult.getLocations()) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        Log.d("UserLocation", "위도: " + latitude + ", 경도: " + longitude);

                        // 현재 위치를 기반으로 마커 설정
                        LatLngEntity userLocation = new LatLngEntity(latitude, longitude);
                        setupMarker(userLocation);

                        // 필요 시 위치 업데이트를 중지
                        locationClient.removeLocationUpdates(this);
                    }
                }
            };

            locationClient.requestLocationUpdates(locationRequest, locationCallback, null);

        } catch (SecurityException e) {
            Log.e("LocationError", "위치 권한이 필요합니다: " + e.getMessage());
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

/**
 * LatLngEntity class
 *
 * @property latitude 위도
 * * @property longitude 경도
 * @property longitude 경도
*/
    public static class LatLngEntity {
        public double latitude;
        public double longitude;

        public LatLngEntity(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}


