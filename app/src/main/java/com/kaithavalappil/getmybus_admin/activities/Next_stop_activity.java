package com.kaithavalappil.getmybus_admin.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.kaithavalappil.getmybus_admin.R;

public class Next_stop_activity extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_stop_activity);
        recyclerView = findViewById(R.id.recycler_view);
//        todo add the algorithmn from the user application

    }
}
