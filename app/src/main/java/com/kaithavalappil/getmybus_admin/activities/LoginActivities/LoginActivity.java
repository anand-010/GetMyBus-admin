package com.kaithavalappil.getmybus_admin.activities.LoginActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaithavalappil.getmybus_admin.DataIntermediate.MainActicityContext;
import com.kaithavalappil.getmybus_admin.R;
import com.kaithavalappil.getmybus_admin.activities.MainActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    EditText email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.btnSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        email = findViewById(R.id.editText);
        pass = findViewById(R.id.editText2);
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(LoginActivity.this, "Logined ", Toast.LENGTH_SHORT).show();
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                Context context;
                                db.collection("uid").document(authResult.getUser().getUid()).get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                Long bus_id = null;
                                                bus_id = documentSnapshot.getLong("bus_id");
                                                Toast.makeText(LoginActivity.this, String.valueOf(bus_id), Toast.LENGTH_SHORT).show();
                                                if (bus_id == null) {
                                                    Toast.makeText(LoginActivity.this, "bus_id null ", Toast.LENGTH_SHORT).show();
                                                    Map<String, Object> params = new HashMap<>();
                                                    Random r = new Random();
                                                    int i1 = r.nextInt(9999 - 0) + 0;
                                                    params.put("bus_id", i1);
                                                    db.collection("uid").document(authResult.getUser().getUid()).set(params);
                                                    startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("value", i1));
                                                } else {
                                                    Toast.makeText(LoginActivity.this, String.valueOf(bus_id), Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("value", bus_id));
                                                }
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Logine failed ", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }


}