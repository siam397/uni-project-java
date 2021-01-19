package com.example.artsell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class onStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_start);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                SharedPreferences sharedPreferences=getBaseContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
                String userInfo=sharedPreferences.getString("user","");
                System.out.println(userInfo);
                if(userInfo.equals("")){
                    Intent intent=new Intent(onStartActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(onStartActivity.this,ProfileActivity.class);
                    startActivity(intent);
                }



            }

        }, 1000);
    }
}