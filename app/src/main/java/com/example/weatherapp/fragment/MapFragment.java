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
                .title("위치")
                .snippet("서울역 위치");

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionLatLng, 15f));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f));

        return googleMap.addMarker(markerOption);
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


