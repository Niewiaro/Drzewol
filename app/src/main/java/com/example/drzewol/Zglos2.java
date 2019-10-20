package com.example.drzewol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Zglos2 extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zglos2);

        button = (Button) findViewById(R.id.zaczynajmy);

        configurateButton();
    }

    private void configurateButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGaleria();
            }
        });
    }

    private void openGaleria() {
        Intent intent = new Intent(this, Opis2.class);
        startActivity(intent);
    }

}
