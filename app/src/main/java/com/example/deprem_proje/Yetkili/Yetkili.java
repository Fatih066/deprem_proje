package com.example.deprem_proje.Yetkili;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.deprem_proje.Firabase.FireStore;
import com.example.deprem_proje.R;
import com.example.deprem_proje.Yetkili.Location.GetLocation;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.maps.SupportMapFragment;
import  com.google.android.gms.maps.OnMapReadyCallback;

public class Yetkili extends AppCompatActivity implements OnMapReadyCallback {

    private FireStore fireStore ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yetkili);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fireStore = new FireStore();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}