package com.example.weatherapp.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "MapFragment";

    private MapView mapView;
    private GoogleMap googleMap;
    private Marker currentMarker;

    private TextView tvAddress;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        tvAddress = rootView.findViewById(R.id.tvAddress);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Places API 초기화 (API 키를 정확히 입력)
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), "AIzaSyB-BNi6cHeQqP4jMpHV8ShkOOm3lTqYLsY");
        }
        // Initialize the FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        // Get location and update address
        requestUserLocationAndSetupMarker();

        // AutoCompleteSupportFragment 초기화
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // 반환할 필드 설정 (이름과 위도/경도)
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setHint("장소를 검색하세요");

        // 장소 선택 리스너
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.d("PlaceSelected", "Place: " + place.getName() + ", " + place.getLatLng());

                // 선택된 장소에 마커 추가
                LatLng latLng = place.getLatLng();
                if (latLng != null) {
                    addMarkerToMap(latLng, place.getName());
                }
            }

            @Override
            public void onError(@NonNull com.google.android.gms.common.api.Status status) {
                Log.e("PlaceError", "An error occurred: " + status);
                Toast.makeText(requireContext(), "장소 검색 오류: " + status, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addMarkerToMap(LatLng latLng, String title) {
        if (googleMap != null) {
            googleMap.clear(); // 기존 마커 제거
            googleMap.addMarker(new MarkerOptions().position(latLng).title(title));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
        }
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
        // 기존 마커 제거
        if (currentMarker != null) {
            currentMarker.remove();
        }

        // 새로운 위치로 마커 추가
        LatLng positionLatLng = new LatLng(locationLatLngEntity.latitude, locationLatLngEntity.longitude);
        MarkerOptions markerOption = new MarkerOptions()
                .position(positionLatLng)
                .title("내 위치")
                .snippet("현재 위치를 기반으로 표시된 마커입니다.");

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionLatLng, 15f));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f));

        // 새 마커를 currentMarker에 저장
        currentMarker = googleMap.addMarker(markerOption);
        return currentMarker;
    }


    private void requestUserLocationAndSetupMarker() {
        try {
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(60 * 1000); // 요청 간격 (1분)

            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    for (Location location : locationResult.getLocations()) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        /*double latitude = 37.5665;
                        double longitude = 126.9780;*/

                        Log.d("UserLocation", "위도: " + latitude + ", 경도: " + longitude);

                        // 현재 위치를 기반으로 마커 설정
                        LatLngEntity userLocation = new LatLngEntity(latitude, longitude);
                        setupMarker(userLocation);

                        // 주소 설정
                        setAddressFromLocation(latitude, longitude);

                        // 필요 시 위치 업데이트를 중지
                        fusedLocationProviderClient.removeLocationUpdates(this);
                    }
                }
            };

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

        } catch (SecurityException e) {
            Log.e("LocationError", "위치 권한이 필요합니다: " + e.getMessage());
        }
    }


    private void setAddressFromLocation(double latitude, double longitude) {
        try {
            Geocoder geocoder = new Geocoder(requireContext(), Locale.KOREA);
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String adminArea = address.getAdminArea() != null ? address.getAdminArea() : "";
                String fullAddress = address.getAddressLine(0) != null ? address.getAddressLine(0) : "정보 없음";

                tvAddress.setText(fullAddress);
                Log.d("AddressDebug", "Admin Area: " + adminArea);       // 시/도
                //Log.d("AddressDebug", "Locality: " + locality);         // 시/군/구
                //Log.d("AddressDebug", "Thoroughfare: " + thoroughfare); // 도로명
                Log.d("AddressDebug", "Full Address: " + fullAddress);  // 전체 주소
            } else {
                tvAddress.setText("주소를 가져올 수 없습니다.");
            }
        } catch (IOException e) {
            Toast.makeText(requireContext(), "주소를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            Log.e("GeoCoderError", "주소 가져오기 실패: " + e.getMessage());
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

    public static class LatLngEntity {
        public double latitude;
        public double longitude;

        public LatLngEntity(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
