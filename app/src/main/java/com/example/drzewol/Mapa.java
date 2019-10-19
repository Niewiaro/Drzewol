package com.example.drzewol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
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
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;   //
        int zoomLevel = 5; //zoom level

        final FirebaseFirestore db = FirebaseFirestore.getInstance();


        LatLng Cracow = new LatLng(50.06, 19.94); //new object holding cracows' coords
        mMap.addMarker(new MarkerOptions().position(Cracow).title("Cracow"));   //place new marker
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Cracow, zoomLevel)); //set the cameraa nd zoom

        //uploadClass.readMessage();

        Toast.makeText(getApplicationContext(), "Connecting with server", Toast.LENGTH_SHORT).show();


        for (double n = 1; n <= MainActivity.index; n += 1) {
            DocumentReference docRef = db.collection("reports").document("ZGL" + n);

            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            mMap = googleMap;

                            LatLng newLoc = new LatLng(document.getDouble("lat"),
                                    document.getDouble("Lng")); //new object holding coords
                            mMap.addMarker(new MarkerOptions().position(newLoc)
                                    .title(document.getString("Title")));

                        } else {
                            //Log.d("TAG", "No such document");
                        }
                    }
                    /*
                    else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }*/
                }
            });

            Toast.makeText(getApplicationContext(), "data ready", Toast.LENGTH_LONG).show();

        }

    }
}