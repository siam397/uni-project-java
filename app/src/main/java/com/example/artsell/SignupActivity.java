package com.example.artsell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.artsell.models.GetUser;
import com.example.artsell.models.SignUser;

import org.conscrypt.Conscrypt;

import java.io.IOException;
import java.security.Security;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Security.insertProviderAt(Conscrypt.newProvider(), 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button signupbtn=findViewById(R.id.signupbtn);
        TextView gotologin=findViewById(R.id.GoToLogin);
        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.0.104:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestApiPost restApiPost=retrofit.create(RestApiPost.class);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG1", "onClick: ");
                EditText email=findViewById(R.id.email);
                EditText username=findViewById(R.id.username);
                EditText password=findViewById(R.id.password);
                String getEmail=email.getText().toString();
                String getUsername=username.getText().toString();
                String getPassword=password.getText().toString();
                SignUser signUser=new SignUser(getEmail,getUsername,getPassword);
                Call<GetUser>call=restApiPost.signupUser(signUser);
                call.enqueue(new Callback<GetUser>() {
                    @Override
                    public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                        if(!response.isSuccessful()){
                            try {
                                System.out.println(response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        SharedPreferences sharedPreferences=getBaseContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.clear();
                        Log.i("TAG", "onResponse: hoise");
                        assert response.body() != null;
                        editor.putString("user",response.body().get_Id());
                        editor.putString("username",response.body().getUsername());
                        editor.apply();
                        System.out.println(sharedPreferences.getString("user","notFound"));
                        Intent intent=new Intent(getApplicationContext(),LandingPageActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<GetUser> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
            }
        });
    }
}