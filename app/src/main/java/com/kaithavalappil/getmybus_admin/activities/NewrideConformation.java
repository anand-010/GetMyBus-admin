package com.kaithavalappil.getmybus_admin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaithavalappil.getmybus_admin.DataIntermediate.BusDetails;
import com.kaithavalappil.getmybus_admin.DataIntermediate.SoureDestPoint;
import com.kaithavalappil.getmybus_admin.R;
import com.kaithavalappil.getmybus_admin.helper.FirebaseRideConfomed;

public class NewrideConformation extends AppCompatActivity {
    TextView src,dest,busId,busName,launcTime,bustype;
    ImageView continue_btn;
    String route_id="myid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newride_conformation);
        src = findViewById(R.id.src_act_main);
        dest = findViewById(R.id.dest_act_main);
        busId = findViewById(R.id.bus_id);
        busName = findViewById(R.id.bus_name);
        launcTime = findViewById(R.id.bus_time);
        bustype = findViewById(R.id.bus_Type);
        continue_btn = findViewById(R.id.continue_new_ride);
        src.setText(BusDetails.getSource());
        dest.setText(BusDetails.getDest());
//        todo only one value is get from the serch
        busId.setText("Bus id : "+BusDetails.getBusId());
        busName.setText("Bus Name : my bus");
        launcTime.setText("Launch Time : 14.0 pm");
        bustype.setText("Bus Type : "+BusDetails.getBusType());
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseRideConfomed db = new FirebaseRideConfomed();
                db.setupRide(NewrideConformation.this,route_id);
            }
        });
    }
}
