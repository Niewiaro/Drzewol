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

public class Opis extends AppCompatActivity {

    TextView textView;
    LocationManager locationManager;
    LocationListener locationListener;
    EditText editText;
    ImageView imageView;
    Bitmap imageBitmap;

    Boolean Raz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opis);

        Button button = findViewById(R.id.wyslij);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.zglosOpis);
        imageView = findViewById(R.id.imageView2);
        Raz = false;


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textOut = editText.getText().toString();
                MainActivity.Description = textOut;

                //uploadClass.sendMessage();

                Toast.makeText(Opis.this, "Zgłoszenie wysłane!", Toast.LENGTH_SHORT).show();

                openMainActivity();
            }
        });

        dispatchTakePictureIntent(null);
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



    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if(!Raz) {
                        Lat = location.getLatitude();
                        MainActivity.Long = location.getLongitude();
                        textView.setText("x: " + Lat + "\ny: " + MainActivity.Long);
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

        else{
            openZglos();
        }
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
