package com.kaithavalappil.getmybus_admin.helper;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.kaithavalappil.getmybus_admin.DataIntermediate.BusDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseRideConfomed {
    DocumentReference database_refferance;
    DocumentReference route_refferance;

    public FirebaseRideConfomed() {
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID
        route_refferance = db.collection("rides").document(String.valueOf(BusDetails.getBusId()));
        database_refferance = db.collection("users").document(BusDetails.getHashcode());
    }
    public void setupRide(Context context){
//        todo need to be downloading the road, stops data points
//        database_refferance.set();
        Toast.makeText(context,"funtion called",Toast.LENGTH_SHORT).show();
        database_refferance.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Toast.makeText(context,"enter into database",Toast.LENGTH_SHORT).show();
                List<GeoPoint> routes = (List<GeoPoint>) documentSnapshot.get("route");
                List<GeoPoint> stops = (List<GeoPoint>) documentSnapshot.get("stops");
                Map<String, Object> mydata = new HashMap<>();
                mydata.put("stops",stops);
                mydata.put("route",routes);
                mydata.put("bus_id",BusDetails.getBusId());
                mydata.put("bus_number",BusDetails.getBusNumber());
                mydata.put("bus_type",BusDetails.getBusType());
                mydata.put("starting_point",BusDetails.getStartingPoint());
                route_refferance.set(mydata)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context,"upload success",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
