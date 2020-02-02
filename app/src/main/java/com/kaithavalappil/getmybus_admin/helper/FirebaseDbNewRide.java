package com.kaithavalappil.getmybus_admin.helper;

import android.content.Context;
import android.widget.Toast;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firestore.v1.Document;
import com.mapbox.geojson.Point;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDbNewRide {
    DocumentReference database_refferance;
    List<GeoPoint> routes_array = new ArrayList<>();
    List<GeoPoint> stops_array = new ArrayList<>();
    Map<String, Object> user = new HashMap<>();
    Context context;
    public FirebaseDbNewRide(Context context, Point mysource, Point mydest){
        this.context = context;
        Double totoal = mysource.latitude()+mysource.longitude()+mydest.latitude()+mydest.longitude();
        String text = String.valueOf(totoal);
        final HashCode hashCode = Hashing.sha256().hashString(text, Charset.defaultCharset());
        Toast.makeText(context,hashCode.toString(),Toast.LENGTH_SHORT).show();
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();


// Add a new document with a generated ID
        database_refferance = db.collection("users").document(hashCode.toString());
    }

    public void putData(List<Point> points){
        routes_array.clear();
        for (Point p: points){
            routes_array.add(new GeoPoint(p.latitude(),p.longitude()));
        }
        user.put("route", routes_array);
//        todo uploading both at sametime same hashmap
    }
    public void putStops(List<Point> points){
        stops_array.clear();
        for (Point p: points){
            stops_array.add(new GeoPoint(p.latitude(),p.longitude()));
        }
        user.put("stops", stops_array);
        database_refferance.set(user).addOnSuccessListener(documentReference -> {
            Toast.makeText(context,"database added successfully",Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(context,"database adding failse",Toast.LENGTH_SHORT).show();
        });

    }
}
