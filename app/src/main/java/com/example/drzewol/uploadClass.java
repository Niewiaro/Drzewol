package com.example.drzewol;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class uploadClass extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    static void postPhoto(final double ID){

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();

        final StorageReference photoRef = storageRef.child("ZGL"+ID);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Opis.imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = photoRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                       Uri downloadUrl = uri;
                       FirebaseFirestore db = FirebaseFirestore.getInstance();
                       final DocumentReference docRef = db.collection("reports")
                               .document("ZGL"+ID);

                       Map<String, Object> url = new HashMap<>();
                       docRef.update("URL", downloadUrl.toString());

                   }
               });
            }
        });
    }


    //it has to be called when data has to be sent to the server
     public static void sendMessage(){
        //Toast.makeText(uploadClass.this, "Sending", Toast.LENGTH_SHORT).show();

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
                    MainActivity.index = document.getDouble("created");

                    Map<String, Object> report = new HashMap<>();
                    //put all values entered to HashMap
                    report.put("Title", MainActivity.Title);
                    report.put("Description", MainActivity.Description);
                    report.put("Lat", MainActivity.Lat);
                    report.put("Long", MainActivity.Long);
                    report.put("URL", "");
                    MainActivity.index+=1;   //increment index
                    report.put("ID", MainActivity.index);

                    //post to Firebase with incremented name
                    db.collection("reports").document("ZGL"+MainActivity.index).set(report);

                    //update INDEX with incremented value in proper directory
                    Map<String, Object> INDEX = new HashMap<>();
                    INDEX.put("created", MainActivity.index);
                    db.collection("reports").document("INDEX").set(INDEX);

                    postPhoto(MainActivity.index);
                }
            }
        });
    }

    //function called whenever index value has to be updated
    public static void setIndex(){

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
                    MainActivity.index = document.getDouble("created");
                    //Toast.makeText(getApplicationContext(), "Index ready", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
