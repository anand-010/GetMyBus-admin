package com.kaithavalappil.getmybus_admin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaithavalappil.getmybus_admin.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcessionResult extends AppCompatActivity {
    TextView from ,to , name, age, institution, journey;
    ImageView profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concession_result);
//        List<EventDay> events = new ArrayList<>();
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2020,1,15);
//        events.add(new EventDay(calendar, R.drawable.contact));
//
////or if you want to specify event label color
//        events.add(new EventDay(calendar, R.drawable.bg_gradient, Color.parseColor("#228B22")));
//
//        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
//        calendarView.setEvents(events);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        journey = findViewById(R.id.journey);
        institution = findViewById(R.id.institution);
        profile = findViewById(R.id.profile);
        Intent intent = getIntent();
        name.setText("NAME :"+ intent.getStringExtra("name"));
        from.setText("FROM :"+ intent.getStringExtra("from"));
        to.setText("TO :"+ intent.getStringExtra("to"));
        age.setText("AGE :"+ intent.getStringExtra("age"));
        long number = intent.getLongExtra("tripno",1);
        String document = intent.getStringExtra("document_id");
        DocumentReference db = FirebaseFirestore.getInstance().collection("concession").document(document);
        if (number == 1){

        }
        else if (number == 2){
            journey.setText("Mark backward Journey");
        }
        else if (number > 2){
            journey.setText("No Journey Available");
        }

        journey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number == 1){
                    Toast.makeText(ConcessionResult.this,"Mark forward journey",Toast.LENGTH_SHORT).show();
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("tripno",2);
                    db.update(hashMap);
                }
                else if (number == 2){
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("tripno",3);
                    db.update(hashMap);
                    journey.setText("Mark backward Journey");
                }
                else if (number > 2){
                    journey.setText("No Journey Available");
                }
            }
        });
        institution.setText("INSTITUTION :"+ intent.getStringExtra("institution"));
        Glide
                .with(ConcessionResult.this)
                .load(intent.getStringExtra("url"))
                .into(profile);
    }
}
