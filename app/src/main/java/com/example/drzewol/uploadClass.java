package com.example.drzewol;

import android.app.Activity;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class uploadClass extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    //it has to be called when data has to be sent to the server

    double index = 0;
    public void sendMessage(View view){


        Toast.makeText(getApplicationContext(), "Sending", Toast.LENGTH_SHORT).show();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        //creating docRef which asks for document INDEX in reports
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
                    Toast.makeText(getApplicationContext(), "INDEX received",
                                                            Toast.LENGTH_SHORT).show();

                    Map<String, Object> report = new HashMap<>();
                    //put all values entered to HashMap
                    report.put("Title", MainActivity.Title);
                    report.put("Description", MainActivity.Description);
                    report.put("lat", MainActivity.Lat);
                    report.put("Long", MainActivity.Long);
                    index+=1;   //increment index
                    report.put("ID", index);

                    //post to Firebase with incremented name
                    db.collection("reports").document("ZGL"+index).set(report);

                    //update INDEX with incremented value in proper directory
                    Map<String, Object> INDEX = new HashMap<>();
                    INDEX.put("created", INDEX);
                    db.collection("reports").document("INDEX").set(index);
                }
            }
        });
    }

    //TODO: server read
}
