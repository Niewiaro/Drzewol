package com.example.drzewol;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.drzewol.MainActivity.Lat;

public class Opis2 extends AppCompatActivity {

    TextView textView;
    LocationManager locationManager;
    LocationListener locationListener;
    EditText editText, editText2, x, y;
    ImageView imageView;
    Button button, button2;
    String x1, y1;
    double x2, y2;
    Boolean Raz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opis2);

        button = (Button) findViewById(R.id.wyslij);
        button2 = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.zglosOpis);
        editText2 = (EditText) findViewById(R.id.zglosTytul);
        x = (EditText) findViewById(R.id.szerokosc);
        y = (EditText) findViewById(R.id.dlugosc);
        imageView = findViewById(R.id.imageView2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Raz = false;
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if(!Raz){
                            x2 = location.getLatitude();
                            x1 = String.valueOf(x2);
                            x.setText(x1);

                            y2 = location.getLongitude();
                            y1 = String.valueOf(y2);
                            y.setText(y1);

                            Raz= true;
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

                configurateButton();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText2.length()==0){
                    editText2.setError(" To pole nie może być puste! ");
                }

                else if(x.length()==0){
                    x.setError(" Musisz podać współrzędną! ");
                }

                else if(y.length()==0){
                    y.setError(" Musisz podać współrzędną! ");
                }

                else{
                    x2 = Double.parseDouble(x.getText().toString());
                    Lat = x2;

                    y2 = Double.parseDouble(y.getText().toString());
                    MainActivity.Long = y2;

                    String textOut2 = editText2.getText().toString();
                    MainActivity.Title = textOut2;


                    String textOut = editText.getText().toString();
                    MainActivity.Description = textOut;

                    //uploadClass.sendMessage();

                    Toast.makeText(Opis2.this, "Zgłoszenie wysłane!", Toast.LENGTH_SHORT).show();

                    openMainActivity();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configurateButton();
                return;
        }
    }

    private void configurateButton() {
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
    }

    private void openZglos() {
        Intent intent = new Intent(this, Zglos.class);
        startActivity(intent);
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
