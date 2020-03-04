package com.kaithavalappil.getmybus_admin.activities.LoginActivities;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kaithavalappil.getmybus_admin.R;

public class SignupActivity extends AppCompatActivity {
    EditText editText;
    private FirebaseAuth mAuth;
    String TAG= "LoginActivity";
    EditText email,password,password1;
    Button goto_reg;
    boolean verifyed = false;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        goto_reg = findViewById(R.id.button3);
        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        password1 = findViewById(R.id.editText3);
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
        mAuth = FirebaseAuth.getInstance();
        goto_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1= email.getText().toString();
                String password_ = password1.getText().toString();
                String passwor = password.getText().toString();
                if (!verifyed) {
                    mAuth.createUserWithEmailAndPassword(email1, passwor)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(SignupActivity.this, "user added", Toast.LENGTH_SHORT).show();
                                    user = mAuth.getCurrentUser();
                                    user.sendEmailVerification()
                                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener() {
                                                @Override
                                                public void onComplete(@NonNull Task task) {

                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(SignupActivity.this,
                                                                "Verification email sent to " + user.getEmail() + user.isEmailVerified(),
                                                                Toast.LENGTH_SHORT).show();
                                                        goto_reg.setText("verify email ");
                                                        verifyed = true;
                                                    } else {
                                                        Log.e("tag", "sendEmailVerification", task.getException());
                                                        Toast.makeText(SignupActivity.this,
                                                                "Failed to send verification email.",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: "+e);
                                    Toast.makeText(SignupActivity.this, "user adding faild", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else {
                    user.reload();
                    if (user.isEmailVerified()){
                        Toast.makeText(SignupActivity.this,"yup email verified ",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(SignupActivity.this,"plese check your mail box",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
