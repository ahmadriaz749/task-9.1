package com.example.task91p;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class ShowAllMapsMarkLoaction extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // // Getting the data from the databaseAdvert
        DatabaseAdvert databaseAdvert = new DatabaseAdvert(this);
        List<InformationOfAdvertData> informationOfAdvertDataList = databaseAdvert.getAllAdverts();

        // Adding markers
        for (InformationOfAdvertData informationOfAdvertData : informationOfAdvertDataList) {
            String location = informationOfAdvertData.getLocation();
            String[] latLng = location.split(",");
            if (latLng.length == 2) {
                double latitude = Double.parseDouble(latLng[0]);
                double longitude = Double.parseDouble(latLng[1]);
                LatLng advertLocation = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(advertLocation));
            }
        }
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Showing the last location
        if (!informationOfAdvertDataList.isEmpty()) {
            InformationOfAdvertData lastInformationOfAdvertData = informationOfAdvertDataList.get(informationOfAdvertDataList.size() - 1);
            String lastLocation = lastInformationOfAdvertData.getLocation();
            String[] lastLatLng = lastLocation.split(",");

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_maps_mark_location);

        // Initialize the map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }
}
