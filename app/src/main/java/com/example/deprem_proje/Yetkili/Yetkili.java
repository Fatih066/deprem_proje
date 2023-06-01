package com.example.deprem_proje.Yetkili;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.deprem_proje.Firabase.FireStore;
import com.example.deprem_proje.R;
import com.example.deprem_proje.Yetkili.Location.GetLocation;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;
import  com.google.android.gms.maps.OnMapReadyCallback;

public class Yetkili extends AppCompatActivity implements OnMapReadyCallback {

    private FireStore fireStore ;
    private  GoogleMap map;

    private  GetLocation getLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yetkili);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fireStore = new FireStore();
        getLocation = new GetLocation();


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        getLocation.setMarkers(markerOptions -> {
            for (MarkerOptions markerOption : markerOptions) {
                map.addMarker(markerOption);
            }
        });

        map.setOnInfoWindowClickListener(marker -> {
        });

    }
}