package com.example.drzewol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
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

import java.io.IOException;
import java.io.InputStream;

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

    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 4;

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

        pickFromGallery();

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

    private void pickFromGallery(){
        if (ContextCompat.checkSelfPermission(Opis2.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // textView.setText("permission not granted");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            // textView.setText("permission granted");

            //Create an Intent with action as ACTION_PICK
            Intent intent = new Intent(Intent.ACTION_PICK);
            // Sets the type as image/*. This ensures only components of type image are selected
            intent.setType("image/*");
            //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
            String[] mimeTypes = {"image/jpeg", "image/png"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            // Launching the Intent
            startActivityForResult(intent, GALLERY_REQUEST_CODE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            try {
                Uri selectedImage = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                imageView.setImageBitmap(BitmapFactory.decodeStream(imageStream));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
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
