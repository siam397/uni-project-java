package com.example.artsell;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {
    private Bitmap bitmap;
    private ImageView imageView;
    private Button uploadbtn;
    private Button choosebtn;
    private Button takeImage;
    private final Button logout=findViewById(R.id.logoutbtn);;
    private final int Gallery_REQUEST = 21;
    private final int camera_request=22;
    private String currentImagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SharedPreferences sharedPreferences=getBaseContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String id=sharedPreferences.getString("user","");










        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getBaseContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.putString("user","");
                Intent intent=new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}