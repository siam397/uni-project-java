package com.example.artsell;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.artsell.models.Profile;
import com.example.artsell.models.ProfilePicture;
import com.example.artsell.models.ResponseBody;
import com.example.artsell.models.UserID;
import com.example.artsell.utilities.Variables;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment{
    private Bitmap bitmap;
    private ImageView imageView;
    private final int GALLERY_REQUEST=1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    private Button logout;
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences sharedPreferences= Objects.requireNonNull(getActivity()).getBaseContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor=sharedPreferences.edit();
//                editor.clear();
//                editor.putString("user","");
//                editor.apply();
//                Intent intent=new Intent(Objects.requireNonNull(getContext()),LoginActivity.class);
//                startActivity(intent);
//            }
//        });

        imageView=view.findViewById(R.id.profile_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_REQUEST);
                    }
                    System.out.println("hello");
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, GALLERY_REQUEST);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        TextView username=view.findViewById(R.id.name);
        TextView bio=view.findViewById(R.id.bio);
        Button logout=view.findViewById(R.id.logout);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String id=sharedPreferences.getString("user","");
        Retrofit retrofit= Variables.initializeRetrofit();
        RestApiPost restApiPost=retrofit.create(RestApiPost.class);
        UserID userID=new UserID(id);
        Call<Profile> call=restApiPost.getProfileInfo(userID);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if(!response.isSuccessful()){
                    System.out.println(response.errorBody().toString());
                    return;
                }
                String profilePic=response.body().getProfilePicture();
                byte[] imageBytes = Base64.decode(profilePic, Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imageView.setImageBitmap(decodedImage);
                username.setText(response.body().getUsername());
                bio.setText(response.body().getBio());

            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences= getContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.putString("user","");
                editor.apply();
                Intent intent=new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void changeDP(String id, Bitmap image){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        imageView.setImageBitmap(image);
        image.compress(Bitmap.CompressFormat.JPEG,50,baos);
        String base64img=Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT);
        Retrofit retrofit= Variables.initializeRetrofit();
        RestApiPost restApiPost=retrofit.create(RestApiPost.class);
        ProfilePicture profilePicture=new ProfilePicture(id,base64img);
        Call<ResponseBody> call=restApiPost.changeDP(profilePicture);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    System.out.println("Something went wrong");
                    return;
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQUEST && resultCode==RESULT_OK){
            try{
                InputStream is = getActivity().getContentResolver().openInputStream(data.getData());
                Bitmap image = BitmapFactory.decodeStream(is);
                SharedPreferences preferences = getActivity().getSharedPreferences("USER_INFO", Activity.MODE_PRIVATE);//Frequent to get SharedPreferences need to add a step getActivity () method
                String id = preferences.getString("user", "");
                changeDP(id,image);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}