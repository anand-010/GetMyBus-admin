package com.kaithavalappil.getmybus_admin.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.kaithavalappil.getmybus_admin.DataIntermediate.BusDetails;
import com.kaithavalappil.getmybus_admin.R;

public class MyRide extends AppCompatActivity {
    Button cancel_ride;
    TextView src, dest, bus_id, bus_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ride);
        src = findViewById(R.id.src_myride);
        dest = findViewById(R.id.src_mydest);
        bus_id = findViewById(R.id.bus_id);
        bus_type = findViewById(R.id.bus_Type);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String doc= String.valueOf(BusDetails.getBusId());
        FirebaseFirestore.getInstance().collection("rides").document(String.valueOf(BusDetails.getBusId())).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                src.setText(documentSnapshot.getString("source_name"));
                dest.setText(documentSnapshot.getString("dest_name"));
                bus_id.setText(String.valueOf(documentSnapshot.getLong("bus_id")));
                bus_type.setText(String.valueOf(documentSnapshot.getLong("bus_type")));
            }
        });
        cancel_ride = findViewById(R.id.cancel_ride);
        cancel_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doc.isEmpty()){
//                    db.collection("rides").document(doc).addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                        @Override
//                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                            Toast.makeText(MyRide.this,"database called",Toast.LENGTH_SHORT).show();
////                    todo need to set the firebase database and reomove database when click cancel
//                            db.collection("rides").document(doc).delete();
//                        }
//                    });
                    Toast.makeText(MyRide.this,doc,Toast.LENGTH_SHORT).show();
                    db.collection("rides").document(doc).delete();
//                    todo need to be set bus id in shared prefferance
                }
            }
        });
    }
}
