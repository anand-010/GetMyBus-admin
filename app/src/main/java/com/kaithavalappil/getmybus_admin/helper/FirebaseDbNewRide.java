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
import com.kaithavalappil.getmybus_admin.DataIntermediate.BusDetails;
import com.mapbox.geojson.Point;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDbNewRide {
    CollectionReference database_refferance;
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
        database_refferance = db.collection("users");
    }

    public void putData(List<Point> points, List<Point> stops, Point souce, Point dest,String name){
        routes_array.clear();
        for (Point p: points){
            routes_array.add(new GeoPoint(p.latitude(),p.longitude()));
        }
        user.put("route", routes_array);
        stops_array.clear();
        for (Point p: stops){
            stops_array.add(new GeoPoint(p.latitude(),p.longitude()));
        }
        user.put("start",new GeoPoint(souce.latitude(),souce.longitude()));
        user.put("end",new GeoPoint(dest.latitude(),dest.longitude()));
        user.put("stops", stops_array);
        user.put("name",name);
        if (BusDetails.getSource()!= null && BusDetails.getDest()!=null){
            user.put("source_name", BusDetails.getSource());
            user.put("dest_name",BusDetails.getDest());
        }
        database_refferance.add(user).addOnSuccessListener(documentReference -> {
            Toast.makeText(context,"database added successfully",Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(context,"database adding failed",Toast.LENGTH_SHORT).show();
        });
//        todo uploading both at sametime same hashmap
    }
    public void putStops(){


    }
}
