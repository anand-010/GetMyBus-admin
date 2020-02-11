package com.kaithavalappil.getmybus_admin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kaithavalappil.getmybus_admin.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.king.zxing.CaptureActivity;
import com.king.zxing.Intents;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout scan_id,contact,create_ride,my_list,next_stop;
    int requestCode = 106;
    final Context context = MainActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            }
        }
    }
}
