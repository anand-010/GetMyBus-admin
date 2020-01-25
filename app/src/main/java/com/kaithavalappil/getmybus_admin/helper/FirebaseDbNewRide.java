package com.kaithavalappil.getmybus_admin.helper;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.mapbox.geojson.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDbNewRide {
    CollectionReference database_refferance;
    List<GeoPoint> geoPoints = new ArrayList<>();
    Context context;
    public FirebaseDbNewRide(Context context){
        this.context = context;
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

// Add a new document with a generated ID
        database_refferance = db.collection("users");
    }
    public void putData(List<Point> points){
        geoPoints.clear();
        for (Point p: points){
            geoPoints.add(new GeoPoint(p.latitude(),p.longitude()));
        }
        Map<String, Object> user = new HashMap<>();
        user.put("first", geoPoints);
        database_refferance.add(user).addOnSuccessListener(documentReference -> {
            Toast.makeText(context,"database added successfully",Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(context,"database adding failse",Toast.LENGTH_SHORT).show();
        });

    }
}
