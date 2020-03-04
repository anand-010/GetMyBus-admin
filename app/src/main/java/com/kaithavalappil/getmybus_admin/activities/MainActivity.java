package com.kaithavalappil.getmybus_admin.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaithavalappil.getmybus_admin.DataIntermediate.BusDetails;
import com.kaithavalappil.getmybus_admin.DataIntermediate.MainActicityContext;
import com.kaithavalappil.getmybus_admin.DataIntermediate.SharedPrefData;
import com.kaithavalappil.getmybus_admin.R;
import com.kaithavalappil.getmybus_admin.activities.LoginActivities.LoginActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.king.zxing.CaptureActivity;
import com.king.zxing.Intents;
import com.mapbox.api.directions.v5.MapboxDirections.Builder;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout scan_id,contact,create_ride,my_list,next_stop;
    int requestCode = 106;
    final Context context = MainActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Long i = getIntent().getLongExtra("value",1);
        scan_id = findViewById(R.id.scan_id);
        contact = findViewById(R.id.contact);
        create_ride = findViewById(R.id.create_ride);
        my_list = findViewById(R.id.my_list);
        next_stop = findViewById(R.id.next_stop);
        scan_id.setOnClickListener(this);
        contact.setOnClickListener(this);
        create_ride.setOnClickListener(this);
        my_list.setOnClickListener(this);
        next_stop.setOnClickListener(this);
//        adding shared prefference to main activity
        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        int bus_id = sharedPref.getInt("busid", 1);
        String bus_name = sharedPref.getString("bus_name", "testname");
        String email = sharedPref.getString("email", "testmail");
        boolean first = sharedPref.getBoolean("first", true);
        if (bus_id==1){
            if (i == 1){
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
            else {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("value", i.intValue());
                editor.putBoolean("first", true);
                editor.putInt("busid",i.intValue());
//            todo test value
                BusDetails.setBusId(i.intValue());
                BusDetails.setBusType(2);
                BusDetails.setBusNumber("dfsdf");
                SharedPrefData.setBusname("testname");
                SharedPrefData.setEmail("testmail");
                editor.commit();
            }

        }
        else {
            Toast.makeText(MainActivity.this,"already exist",Toast.LENGTH_SHORT).show();
//            SharedPrefData.setBusid(bus_id);
            BusDetails.setBusId(bus_id);
            BusDetails.setBusType(2);
            BusDetails.setBusNumber("dfsdf");
            SharedPrefData.setBusname(bus_name);
            SharedPrefData.setEmail(email);
        }
////    todo delete it test down
//        FirebaseFirestore d = FirebaseFirestore.getInstance();
//        d.collection("test").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                Toast.makeText(MainActivity.this,String.valueOf(queryDocumentSnapshots.getDocuments().size()),Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        if (R.id.scan_id == v.getId()) {
            Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            startActivityForResult(new Intent(context, CaptureActivity.class),requestCode);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            Toast.makeText(MainActivity.this,"Enable permissions to scan",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                        }
                    }).check();
        } else if (R.id.contact == v.getId()) {
            startActivity(new Intent(MainActivity.this,ContactActivity.class));

        } else if (R.id.create_ride == v.getId()) {
            startActivity(new Intent(MainActivity.this,Serch_Ride.class));

        } else if (R.id.my_list == v.getId()) {
            startActivity(new Intent(MainActivity.this, MyRide.class));

        } else if (R.id.next_stop == v.getId()) {
            startActivity(new Intent(MainActivity.this , Next_stop_activity.class));
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == this.requestCode) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                // get String data from Intent
                String returnString = data.getStringExtra(Intents.Scan.RESULT);
                Toast.makeText(this,returnString,Toast.LENGTH_SHORT).show();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("concession").whereEqualTo("id",returnString).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                Toast.makeText(MainActivity.this,"found",Toast.LENGTH_SHORT).show();
                                List<DocumentSnapshot> ds = queryDocumentSnapshots.getDocuments();
                                Toast.makeText(MainActivity.this,String.valueOf(ds.size()),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, ConcessionResult.class);
                                for(DocumentSnapshot dps: ds){
                                    intent.putExtra("name",dps.getString("name"));
                                    intent.putExtra("from",dps.getString("from"));
                                    intent.putExtra("to",dps.getString("to"));
                                    intent.putExtra("age",dps.getString("age"));
                                    intent.putExtra("institution",dps.getString("institution"));
                                    intent.putExtra("url",dps.getString("url"));
                                    intent.putExtra("tripno",dps.getLong("tripno"));
                                    intent.putExtra("document_id",dps.getId());
                                    startActivity(intent);
                                }

                            }
                        });
            }
        }
    }
}
