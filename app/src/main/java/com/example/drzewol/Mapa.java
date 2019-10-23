package com.example.drzewol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback {
//    zmienilem FragmentActivity na AppCompatActivity, by dzialala animacja


    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    Button button;
    Boolean Raz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        button = (Button) findViewById(R.id.button);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Raz = false;
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if(!Raz){
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

                            Raz=true;
                        }

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                };

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.INTERNET
                        }, 10);
                        return;
                    }
                }

                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;   //
        int zoomLevel = 5; //zoom level

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(true);

        LatLng Cracow = new LatLng(50.06, 19.94); //new object holding cracows' coords
        mMap.addMarker(new MarkerOptions().position(Cracow).title("Cracow"));   //place new marker
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Cracow, zoomLevel)); //set the cameraa nd zoom



        //uploadClass.readMessage();

        Toast.makeText(getApplicationContext(), "Connecting with server", Toast.LENGTH_SHORT).show();


        for (double n = 1; n <= MainActivity.index; n += 1) {

            if(storageClass.titleList.isEmpty()) {
                DocumentReference docRef = db.collection("reports")
                        .document("ZGL" + n);

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                mMap = googleMap;

                                LatLng newLoc = new LatLng(document.getDouble("Lat"),
                                        document.getDouble("Long")); //new object holding coords
                                mMap.addMarker(new MarkerOptions().position(newLoc)
                                        .title(document.getString("Title")));

                                storageClass.latList.add(document.getDouble("Lat"));//document.getDouble("ID").intValue(),
                                storageClass.longList.add(document.getDouble("Long"));
                                storageClass.titleList.add(document.getString("Title"));
                                storageClass.descriptionList.add(document.getString("Description"));
                                storageClass.URLList.add(document.getString("URL"));
                                storageClass.IDList.add(document.getDouble("ID"));
                                storageClass.isCleanedList.add(document.getBoolean("isCleaned"));


                            } else {

                            }
                        }
                    /*
                    else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }*/
                    }
                });

            }

        }

    }
}