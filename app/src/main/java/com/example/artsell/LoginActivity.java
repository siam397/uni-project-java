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
import com.example.artsell.models.LoginUser;
import com.example.artsell.utilities.Variables;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn=findViewById(R.id.loginbtn);
        TextView gotosignup=findViewById(R.id.GoToSignUp);
        gotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
        Retrofit retrofit= Variables.initializeRetrofit();
        RestApiPost restApiPost=retrofit.create(RestApiPost.class);
        RestApiGet restApiGet=retrofit.create(RestApiGet.class);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Call<List<GetUser>> call=restApiGet.getPosts();
//                call.enqueue(new Callback<List<GetUser>>() {
//                    @Override
//                    public void onResponse(Call<List<GetUser>> call, Response<List<GetUser>> response) {
//                        if(!response.isSuccessful()){
//                            System.out.println("hoilo na re");
//                            return;
//                        }
//                        List<GetUser>users=response.body();
//                        for (GetUser getUser :users){
//                            System.out.println(getUser.getUsername()+"\n"+ getUser.get_Id()+"\n"+ getUser.getPassword());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<GetUser>> call, Throwable t) {
//                        System.out.println(t.getMessage());
//                    }
//                });
//            }
//        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email=findViewById(R.id.email);
                EditText password=findViewById(R.id.password);
                String userEmail=email.getText().toString();
                String userPassword=password.getText().toString();
                LoginUser loginUser=new LoginUser(userEmail,userPassword);

                Call<GetUser> call=restApiPost.loginUser(loginUser);
                call.enqueue(new Callback<GetUser>() {
                    @Override
                    public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                        if(!response.isSuccessful()){
                            Log.i("TAG", "onResponse:hoy nai ");
                            return;
                        }
                        int statusCode=response.code();
                        SharedPreferences sharedPreferences=getBaseContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.clear();
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