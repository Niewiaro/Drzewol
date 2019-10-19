package com.example.drzewol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Opis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opis);

        Button button = findViewById(R.id.wyslij);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Opis.this, "Zgłoszenie wysłane!", Toast.LENGTH_SHORT).show();

                openMainActivity();
            }
        });
    }


    private void openMainActivity() { // zmienione na Aparat
        Intent intent = new Intent(this, Aparat.class);
        startActivity(intent);
    }
}
