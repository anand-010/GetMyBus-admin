package com.kaithavalappil.getmybus_admin.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kaithavalappil.getmybus_admin.DataIntermediate.BusDetails;
import com.kaithavalappil.getmybus_admin.DataIntermediate.SerchRecyclerLData;
import com.kaithavalappil.getmybus_admin.DataIntermediate.SoureDestPoint;
import com.kaithavalappil.getmybus_admin.R;
import com.kaithavalappil.getmybus_admin.adapters.SerchRecyclerviewAdapter;
import com.king.zxing.Intents;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;

import java.util.ArrayList;
import java.util.List;

public class Serch_Ride extends AppCompatActivity {
    Intent intent;
    int requestCode = 106;
    int requestCode2 = 107;
    TextView serch_start,serch_end,serch_result;
    ProgressBar progressBar;
    Point mysource,mydestination;
    RecyclerView recyclerView;
    List<SerchRecyclerLData> serchRecyclerLData = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serch_ride);
        setMapboxApi();
        initializeSerchIntent();
        setClickListeners();
        serch_result = findViewById(R.id.serch_result_textview);
        progressBar = findViewById(R.id.serch_progressbar);
//        todo need to do set the progressbar and the textview message
        serchRecyclerLData.add(new SerchRecyclerLData("Route name : anand","From : alappuzha ", "To : Cherthala"));
        serchRecyclerLData.add(new SerchRecyclerLData("Route name : anand","From : alappuzha ", "To : Cherthala"));
        serchRecyclerLData.add(new SerchRecyclerLData("Route name : anand","From : alappuzha ", "To : Cherthala"));
        serchRecyclerLData.add(new SerchRecyclerLData("Route name : anand","From : alappuzha ", "To : Cherthala"));
        serchRecyclerLData.add(new SerchRecyclerLData("Route name : anand","From : alappuzha ", "To : Cherthala"));
        serchRecyclerLData.add(new SerchRecyclerLData("Route name : anand","From : alappuzha ", "To : Cherthala"));
        serchRecyclerLData.add(new SerchRecyclerLData("Route name : anand","From : alappuzha ", "To : Cherthala"));
        serchRecyclerLData.add(new SerchRecyclerLData("Route name : anand","From : alappuzha ", "To : Cherthala"));
        serchRecyclerLData.add(new SerchRecyclerLData("Route name : anand","From : alappuzha ", "To : Cherthala"));
        serchRecyclerLData.add(new SerchRecyclerLData("Route name : anand","From : alappuzha ", "To : Cherthala"));
        SerchRecyclerviewAdapter serchRecyclerviewAdapter = new SerchRecyclerviewAdapter(this,serchRecyclerLData);
        LinearLayoutManager lm = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recyclerView = findViewById(R.id.route_recycler_view);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(serchRecyclerviewAdapter);
        if (serchRecyclerLData.size()>0){
            serch_result.setVisibility(View.GONE);
        }
    }

    private void setMapboxApi() {
        Mapbox.getInstance(this, "pk.eyJ1IjoiYW5hbmQ5Mjg4IiwiYSI6ImNrNHk2dHJpdDA3dHEzZm82Y2hnY252cjEifQ.W-3fm0taJg_noVA_zzJO7g");
    }

    private void setClickListeners() {
        serch_start = findViewById(R.id.serch_start);
        serch_end = findViewById(R.id.serch_end);
        serch_start.setOnClickListener(
                v -> {
                    startActivityForResult(intent,requestCode);
                }
        );
        serch_end.setOnClickListener(
                v -> {
                    startActivityForResult(intent,requestCode2);
                }
        );
        findViewById(R.id.create_route).setOnClickListener(
                v -> {
                    if (mydestination == null && mysource == null){
                        Toast.makeText(this,"Source and Destination is null",Toast.LENGTH_SHORT).show();
                    }
                    else if (mysource == null){
                        Toast.makeText(this,"Source location is null",Toast.LENGTH_SHORT).show();
                    }
                    else if (mydestination == null){
                        Toast.makeText(this,"Destination is null",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SoureDestPoint.setSourceDest(mysource,mydestination);
                        startActivity(new Intent(Serch_Ride.this,New_Ride_Activity.class));
                    }
                }
        );
    }

    private void initializeSerchIntent() {
        intent = new PlaceAutocomplete.IntentBuilder()
                .accessToken(Mapbox.getAccessToken() != null ? Mapbox.getAccessToken() : "pk.eyJ1IjoiYW5hbmQ5Mjg4IiwiYSI6ImNrNHk2dHJpdDA3dHEzZm82Y2hnY252cjEifQ.W-3fm0taJg_noVA_zzJO7g")
                .placeOptions(PlaceOptions.builder()
                        .backgroundColor(Color.parseColor("#EEEEEE"))
                        .limit(10)
                        .build(PlaceOptions.MODE_CARDS))
                .build(Serch_Ride.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check that it is the SecondActivity with an OK result
        if (resultCode == Activity.RESULT_OK && requestCode == this.requestCode) {

            // Retrieve selected location's CarmenFeature
            CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);
            mysource = Point.fromLngLat(((Point) selectedCarmenFeature.geometry()).longitude(),
                    ((Point) selectedCarmenFeature.geometry()).latitude());
            serch_start.setText(selectedCarmenFeature.placeName());
            BusDetails.setSrc(selectedCarmenFeature.placeName());

        }
        if (resultCode == Activity.RESULT_OK && requestCode == this.requestCode2) {

            // Retrieve selected location's CarmenFeature
            CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);
            mydestination = Point.fromLngLat(((Point) selectedCarmenFeature.geometry()).longitude(),
                    ((Point) selectedCarmenFeature.geometry()).latitude());
            serch_end.setText(selectedCarmenFeature.placeName());
            BusDetails.setDest(selectedCarmenFeature.placeName());

        }
    }
}
