package com.kaithavalappil.getmybus_admin.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.kaithavalappil.getmybus_admin.R;
import com.kaithavalappil.getmybus_admin.Recyclerview_elements.RecyclerviewAdapter;
import com.kaithavalappil.getmybus_admin.Recyclerview_elements.TimelineData;

import java.util.ArrayList;
import java.util.List;

public class Next_stop_activity extends AppCompatActivity {
    RecyclerView recyclerView;
    private static final String TAG = Next_stop_activity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_stop_activity);
        final List<TimelineData> list = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager lm = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        //TODO recycler view calculation from the database
        final RecyclerviewAdapter adapter = new RecyclerviewAdapter(list);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("bus_test").document("WsCdTluxXcgIITYBxaWe")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException e) {
                        List<String> stops = (List<String>) document.get("routes");
                        List<String> times = (List<String>) document.get("times");

                        Log.d(TAG, "onComplete: "+document.getLong("id"));
                        long js = document.getLong("percent");
                        int num = Integer.parseInt(String.valueOf(js));
                        num = num+100;
                        list.clear();
                        int place=0;
                        for(place=0;place<num/100;place++){
                            if (place==0){
                                if (1 == num/100){
                                    list.add(new TimelineData(stops.get(place),times.get(place), num%100,0));
                                }
                                else {
                                    list.add(new TimelineData(stops.get(place),times.get(place), 100,0));
                                }
                            }

                            else if (place==(num/100)-1){
                                if (num%100+1>80) {
                                    Toast.makeText(Next_stop_activity.this,String.valueOf(num),Toast.LENGTH_SHORT).show();
                                    list.add(new TimelineData(stops.get(place),times.get(place), 100,1));
                                    place++;
                                    num= num+20;
                                    Toast.makeText(Next_stop_activity.this,String.valueOf(num%100),Toast.LENGTH_SHORT).show();
                                    list.add(new TimelineData(stops.get(place),times.get(place), (num%100)/2,3));
                                } else{
                                    list.add(new TimelineData(stops.get(place),times.get(place), (num%100)+20,2));
                                }
                            }
                            else {
                                list.add(new TimelineData(stops.get(place),times.get(place), 100,1));
                            }
                        }
                        for (place = place;place<stops.size();place++){
                            list.add(new TimelineData(stops.get(place),times.get(place) ,100,4));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
