package com.example.drzewol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    ImageView choinka, aparat, pineska, lista;
    Button zglos, listaa, mapa;
    Animation choinka_z_prawej, napis_z_lewej;

    static public String Title = "", Description = "";
    static public double Lat = 0.0000, Long = 0.0000;
    static public double index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        choinka = (ImageView) findViewById(R.id.choinka);
        aparat = (ImageView) findViewById(R.id.aparat);
        pineska = (ImageView) findViewById(R.id.pineska);
        lista = (ImageView) findViewById(R.id.lista);
        zglos = (Button) findViewById(R.id.zglos) ;
        listaa = (Button) findViewById(R.id.listaa) ;
        mapa = (Button) findViewById(R.id.mapa) ;

        choinka_z_prawej = AnimationUtils.loadAnimation(this,R.anim.choinka_z_prawej);
        napis_z_lewej = AnimationUtils.loadAnimation(this,R.anim.napis_z_lewej);

        choinka.setAnimation(choinka_z_prawej);
        zglos.setAnimation(napis_z_lewej);
        listaa.setAnimation(napis_z_lewej);
        mapa.setAnimation(napis_z_lewej);
        aparat.setAnimation(napis_z_lewej);
        lista.setAnimation(napis_z_lewej);
        pineska.setAnimation(napis_z_lewej);

        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharedIntent = new Intent(MainActivity.this, Mapa.class);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(pineska, "pineska123");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);

                startActivity(sharedIntent, options.toBundle());
            }
        });

        zglos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharedIntent = new Intent(MainActivity.this, Wybierz.class);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(aparat, "aparat123");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);

                startActivity(sharedIntent, options.toBundle());
            }
        });

        listaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharedIntent = new Intent(MainActivity.this, Lista.class);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(lista, "lista123");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);

                startActivity(sharedIntent, options.toBundle());
            }
        });

        //get the current INDEX from server (needed for map display)

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("reports")
                .document("INDEX");

        //send a data request and set listener
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document;
                if(task.isSuccessful()){
                    document = task.getResult();
                    index = document.getDouble("created");
                    //Toast.makeText(getApplicationContext(), "Index ready", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
