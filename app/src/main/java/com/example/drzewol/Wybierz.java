package com.example.drzewol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Wybierz extends AppCompatActivity {

    Button aparat, galeria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wybierz);

        aparat = (Button) findViewById(R.id.aparat);
        galeria = (Button) findViewById(R.id.galeria);

        aparat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openZglos();
            }
        });

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openZglos2();
            }
        });
    }

    private void openZglos() {
        Intent intent = new Intent(this, Zglos.class);
        startActivity(intent);
    }

    private void openZglos2() {
        Intent intent = new Intent(this, Zglos2.class);
        startActivity(intent);
    }
}
