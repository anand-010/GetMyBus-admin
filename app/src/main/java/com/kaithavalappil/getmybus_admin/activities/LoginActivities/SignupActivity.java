package com.kaithavalappil.getmybus_admin.activities.LoginActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kaithavalappil.getmybus_admin.R;

public class SignupActivity extends AppCompatActivity {
    EditText email,pass1,pass2;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
        email = findViewById(R.id.editText);
        pass1 = findViewById(R.id.editText2);
        pass2 = findViewById(R.id.editText3);
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().matches("")){
                    Toast.makeText(SignupActivity.this,"enter an email id",Toast.LENGTH_SHORT).show();
                }
                else if (pass1.getText().toString().matches("")){
                    Toast.makeText(SignupActivity.this,"enter a password ",Toast.LENGTH_SHORT).show();
                }
                else if (pass2.getText().toString().matches("")){
                    Toast.makeText(SignupActivity.this,"varify password ",Toast.LENGTH_SHORT).show();
                }
                else if (!pass1.getText().toString().matches(pass2.getText().toString())){
                    Toast.makeText(SignupActivity.this,"passwords are not match ",Toast.LENGTH_SHORT).show();
                }
                else {
//                    todo need to be authenticated

                    mAuth.createUserWithEmailAndPassword(email.getText().toString(),pass1.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(SignupActivity.this,"user added",Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    user.sendEmailVerification()
//                                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener() {
//                                                @Override
//                                                public void onComplete(@NonNull Task task) {
//
//                                                    if (task.isSuccessful()) {
//                                                        Toast.makeText(SignupActivity.this,
//                                                                "Verification email sent to " + user.getEmail() + user.isEmailVerified(),
//                                                                Toast.LENGTH_SHORT).show();
//                                                    } else {
//                                                        Log.e("tag", "sendEmailVerification", task.getException());
//                                                        Toast.makeText(SignupActivity.this,
//                                                                "Failed to send verification email.",
//                                                                Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("eror", "onFailure: "+e.getMessage());
                                    Log.d("eror", "onFailure: "+e.toString());
                                    Toast.makeText(SignupActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    Toast.makeText(SignupActivity.this,email.getText().toString()+pass1.getText().toString(),Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}
