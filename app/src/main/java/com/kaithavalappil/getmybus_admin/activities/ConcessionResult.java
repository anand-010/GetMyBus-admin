package com.kaithavalappil.getmybus_admin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.kaithavalappil.getmybus_admin.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ConcessionResult extends AppCompatActivity {
    TextView from ,to , name;
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
        Intent intent = getIntent();
        name.setText("NAME :"+ intent.getStringExtra("name"));
        from.setText("FROM :"+ intent.getStringExtra("from"));
        to.setText("TO :"+ intent.getStringExtra("to"));
    }
}
