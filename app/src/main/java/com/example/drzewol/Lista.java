package com.example.drzewol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Lista extends AppCompatActivity {

    Button button;
    ImageView dzban;
    Animation choinka_z_prawej;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        dzban = (ImageView) findViewById(R.id.dzban);
        button = (Button) findViewById(R.id.spin);
        textView = (TextView) findViewById(R.id.XDDD);

        choinka_z_prawej = AnimationUtils.loadAnimation(this,R.anim.choinka_z_prawej);

        dzban.setAnimation(choinka_z_prawej);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText ("↑\nJESTEŚ\nI FRAJER!!!");
            }
        });


    }
}
