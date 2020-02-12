package com.kaithavalappil.getmybus_admin.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaithavalappil.getmybus_admin.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.king.zxing.CaptureActivity;
import com.king.zxing.Intents;
import com.king.zxing.util.CodeUtils;

public class Qrcode_activity extends AppCompatActivity {
    int requestCode = 106;
    ImageView imageView;
    Button bt1,bt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_activity);

        imageView = findViewById(R.id.show_here);
        bt1 = findViewById(R.id.gen_btn);
        bt2 = findViewById(R.id.scan_btn);



        final Context context = Qrcode_activity.this;

        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Toast.makeText(Qrcode_activity.this,"permission granted",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
        bt1.setOnClickListener(v -> startActivityForResult(new Intent(context, CaptureActivity.class),requestCode));
        bt2.setOnClickListener(v -> {
            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.contact);
            Bitmap b=bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 200, 200, false);
            Bitmap bitmap = CodeUtils.createQRCode("Hai there",600,smallMarker);
            imageView.setImageBitmap(bitmap);
        });

        //跳转的默认扫码界面


        //生成二维码

//        //生成条形码
//        CodeUtils.createBarCode(content, BarcodeFormat.CODE_128,800,200);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == this.requestCode) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                // get String data from Intent
                String returnString = data.getStringExtra(Intents.Scan.RESULT);
                Toast.makeText(this,returnString,Toast.LENGTH_SHORT).show();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("concession").whereEqualTo("id",1000).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                Toast.makeText(Qrcode_activity.this,"oh good",Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        }
    }
}

