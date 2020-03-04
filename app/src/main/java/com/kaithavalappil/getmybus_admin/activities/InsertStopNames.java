package com.kaithavalappil.getmybus_admin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaithavalappil.getmybus_admin.DataIntermediate.BusDetails;
import com.kaithavalappil.getmybus_admin.DataIntermediate.RouteId;
import com.kaithavalappil.getmybus_admin.DataIntermediate.StopSize;
import com.kaithavalappil.getmybus_admin.R;
import com.kaithavalappil.getmybus_admin.adapters.BusNameAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertStopNames extends AppCompatActivity {
    RecyclerView recyclerView;
    Button btn;
    List<String> stops = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_stop_names);
        recyclerView = findViewById(R.id.bus_name_recycler);
        btn = findViewById(R.id.cbu);

        Toast.makeText(this,"the st size"+StopSize.getSize(),Toast.LENGTH_SHORT).show();
        BusNameAdapter adapter = new BusNameAdapter(InsertStopNames.this, StopSize.getSize());
        LinearLayoutManager lm = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < StopSize.getSize(); i++) {
                    View view = recyclerView.getChildAt(i);
                    EditText nameEditText = (EditText) view.findViewById(R.id.editTextStop);
                    String name = nameEditText.getText().toString();
                    stops.add(name);
                    Log.d("dfd", "onCreate: "+name);
                }
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> data = new HashMap<>();
                data.put("routes",stops);
                db.collection("bus_test").document(RouteId.getRote_id()).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(InsertStopNames.this, NewrideConformation.class).putExtra("newroute",true));
                    }
                });
            }
        });
    }
}
