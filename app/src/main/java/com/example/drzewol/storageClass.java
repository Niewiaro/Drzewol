package com.example.drzewol;

import android.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class storageClass {

/*    private static final String ILE = "ile.txt";

    if(ILE = null to znaczy, ze jeszcze nie ma zgloszen)

    public static void main(String[] args){

        int arrInt[] = new int[1000];

        int i = 1;
       // while (i<=4){
           // arrInt[i]
       // }
    }

    public void save(View v) {
        FileOutputStream fos = null;

        try {
            fos =
        } catch (FileNotFoundException e ) {
            e.printStackTrace();
        }
    }*/

    public static List IDList = new ArrayList(1);
    public static List latList = new ArrayList(1);
    public static List longList = new ArrayList(1);
    public static List titleList = new ArrayList(1);
    public static List descriptionList = new ArrayList(1);
    public static List URLList = new ArrayList(1);

    public static void clearLists(){
        IDList.clear();
        latList.clear();
        longList.clear();
        titleList.clear();
        descriptionList.clear();
        URLList.clear();
    }
}
