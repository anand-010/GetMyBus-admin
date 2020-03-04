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
import com.kaithavalappil.getmybus_admin.DataIntermediate.RouteId;
import com.kaithavalappil.getmybus_admin.activities.NewrideConformation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseRideConfomed {
    DocumentReference database_refferance;
    DocumentReference route_refferance;
    FirebaseFirestore db;
    public FirebaseRideConfomed() {
        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID


//        todo add where equal to query to find the all bus from star point to end point
    }
    public void setupRide(Context context, String doc_id){
        Toast.makeText(context,"inside function",Toast.LENGTH_SHORT).show();
//        todo need to be downloading the road, stops data points
//        database_refferance.set();
        database_refferance = db.collection("users").document(doc_id);
        Toast.makeText(context,"funtion called",Toast.LENGTH_SHORT).show();
        database_refferance.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Toast.makeText(context,"enter into database",Toast.LENGTH_SHORT).show();
                List<GeoPoint> routes = (List<GeoPoint>) documentSnapshot.get("route");
                List<GeoPoint> stops = (List<GeoPoint>) documentSnapshot.get("stops");
                String src = documentSnapshot.getString("source_name");
                String dest = documentSnapshot.getString("dest_name");
                Map<String, Object> mydata = new HashMap<>();
                mydata.put("source_name",src);
                mydata.put("dest_name",dest);
                mydata.put("route_id",doc_id);
                mydata.put("stops",stops);
                mydata.put("route",routes);
                mydata.put("bus_id",BusDetails.getBusId());
                mydata.put("bus_number",BusDetails.getBusNumber());
                mydata.put("bus_type",BusDetails.getBusType());
                if (NewrideConformation.isFirst){
                    mydata.put("starting_point",routes.get(0));
                }
                else {
                    mydata.put("starting_point", routes.get(routes.size()-1));
                }
                route_refferance = db.collection("rides").document(String.valueOf(BusDetails.getBusId()));
                route_refferance.set(mydata);

//                adding new to
                HashMap<String, Object> mydt = new HashMap<>();
                db.collection("bus_test").document(RouteId.getRote_id()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        List<String> stopnames = (List<String>) documentSnapshot.get("routes");
                        mydt.put("route_id",RouteId.getRote_id());
                        mydt.put("stopnames",stopnames);
                        mydt.put("percent",0);
                        mydt.put("position", new GeoPoint(routes.get(0).getLatitude(),routes.get(0).getLongitude()));
                        db.collection("ride").document(String.valueOf(BusDetails.getBusId())).set(mydt);
                    }
                });

            }
        });


    }
}
