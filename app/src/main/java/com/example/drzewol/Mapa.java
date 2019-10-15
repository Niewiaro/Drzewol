package com.example.drzewol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback {
//    zmienilem FragmentActivity na AppCompatActivity, by dzialala animacja



    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;   //new mMap object
        int zoomLevel = 10; //zoom level

        LatLng Cracow = new LatLng(50.06, 19.94); //new object holding cracows' coords
        mMap.addMarker(new MarkerOptions().position(Cracow).title("Cracow"));   //place new marker
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Cracow, zoomLevel)); //set the cameraa nd zoom

    }

}