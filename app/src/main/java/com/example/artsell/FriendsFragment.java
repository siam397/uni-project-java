package com.example.artsell;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.artsell.models.GetFriends;
import com.example.artsell.models.Profile;
import com.example.artsell.models.UserID;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FriendsFragment extends Fragment {

    View v;
    private RecyclerView myRecyclerView;
    private List<Profile> listFriend;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FriendsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FriendsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendsFragment newInstance(String param1, String param2) {
        FriendsFragment fragment = new FriendsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void setListFriend(List<Profile> listFriend) {
        this.listFriend = listFriend;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//
//        View dialog=(Dialog)
//
//
//        // F R E N S
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_friends, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(1160, TimeUnit.SECONDS)
                .connectTimeout(1160, TimeUnit.SECONDS)
                .build();
        System.out.println("dhukse");
        myRecyclerView = (RecyclerView) v.findViewById(R.id.friends_recyclerview);
        myRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));

        SharedPreferences sharedPreferences= Objects.requireNonNull(getActivity()).getBaseContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String id=sharedPreferences.getString("user","");
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.0.103:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestApiPost restApiPost=retrofit.create(RestApiPost.class);
        UserID userID=new UserID(id);
        Call<GetFriends> call=restApiPost.getFriends(userID);
        call.enqueue(new Callback<GetFriends>() {
            @Override
            public void onResponse(@NotNull Call<GetFriends> call, @NotNull Response<GetFriends> response) {
                if(!response.isSuccessful()){
                    Log.i("TAG", "onResponse:hoy nai ");
                    return;
                }
                GetFriends getFriends=response.body();


                System.out.println("response "+ response);
                myRecyclerView.setAdapter(new FriendsRecyclerViewAdapter(getActivity(),response.body().getFriends()));



                if(getFriends==null){
                    System.out.println("friend nai");
                }else {
                    assert response.body() != null;



                }
            }
            @Override
            public void onFailure(@NotNull Call<GetFriends> call, Throwable t) {
                System.out.println(t.getMessage());
            }

        });
    }
}