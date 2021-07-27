package com.example.artsell;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.TextView;

import android.widget.LinearLayout;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artsell.models.Chatx;
import com.example.artsell.models.People;
import com.example.artsell.models.Profile;
import com.example.artsell.models.SearchResult;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import com.example.artsell.models.User;
import com.example.artsell.models.UserID;
import com.example.artsell.utilities.Variables;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DiscoverFragment extends Fragment {

    View v;
    private RecyclerView myRecyclerView;
    private List<SearchResult> listSearchResult;
    private EditText searchBar;

    // Notification Count
    private TextView countString;

    // Button
    private ImageView notificationBtn;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DiscoverFragment() {
        // Required empty public constructor
    }

    
    public static DiscoverFragment newInstance(String param1, String param2) {
        DiscoverFragment fragment = new DiscoverFragment();
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

        // S E A R C H  R E S U L T
//        listSearchResult = new ArrayList<>();
//        listSearchResult.add(new SearchResult("1", "Noman", R.drawable.dp1, "bruh"));
//        listSearchResult.add(new SearchResult("1", "Abdul", R.drawable.dp2, "fghfhfhgfh"));
//        listSearchResult.add(new SearchResult("1", "Bokor", R.drawable.dp3, "sdfsfdsfdsfd"));
//        listSearchResult.add(new SearchResult("1", "Chomon", R.drawable.dp4, "   WOW"));
//        listSearchResult.add(new SearchResult("1", "Dakaat", R.drawable.dp5, "llllllllll"));
//        listSearchResult.add(new SearchResult("1", "EEE", R.drawable.dp6, "BBBBB"));
//        listSearchResult.add(new SearchResult("1", "REEEEE", R.drawable.dp7, "RRRRR"));
//        listSearchResult.add(new SearchResult("1", "SHEEESH", R.drawable.dp8, "UUUUU"));
//        listSearchResult.add(new SearchResult("1", "Nantu", R.drawable.dp9, "HHHHH"));
//        listSearchResult.add(new SearchResult("1", "Najibullah", R.drawable.dp10, "HHHHH"));
//        listSearchResult.add(new SearchResult("1", "Nhentai", R.drawable.dp11, "yes"));
//        listSearchResult.add(new SearchResult("1", "Y Combinator", R.drawable.dp1, "no"));
//        listSearchResult.add(new SearchResult("1", "Z dragon ball", R.drawable.dp2, "very good"));
//        listSearchResult.add(new SearchResult("1", "Kulasekara", R.drawable.dp3, "Hustle and Grind 24/7"));
//        listSearchResult.add(new SearchResult("1", "Tamim Iqbal", R.drawable.dp4, "Wish me on 29th Feb | Pisces"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_discover, container,  false);
        LinearLayout linlaHeaderProgress = (LinearLayout) v.findViewById(R.id.linlaHeaderProgress);
        linlaHeaderProgress.setVisibility(View.VISIBLE);
        Retrofit retrofit= Variables.initializeRetrofit();
        RestApiPost restApiPost=retrofit.create(RestApiPost.class);
        SharedPreferences preferences = getActivity().getSharedPreferences("USER_INFO", Activity.MODE_PRIVATE);//Frequent to get SharedPreferences need to add a step getActivity () method
        String id = preferences.getString("user", "");
        UserID userID=new UserID(id);
        RestApiGet restApiGet=retrofit.create(RestApiGet.class);
        Call<People> call=restApiPost.getUsers(userID);
        call.enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {
                if(!response.isSuccessful()){
                    System.out.println("hoilo na re");
                    return;
                }
                int notificationCount=response.body().getNumberOfRequests();
                countString=getView().findViewById(R.id.notificationNumber);
                countString.setText(String.format("%02d", notificationCount));
                if (notificationCount==0 || countString.getText().equals("0")){
                    countString.setVisibility(View.INVISIBLE);
                }else{
                    countString.setVisibility(View.VISIBLE);
                }
                System.out.println(response.body().geteveryoneList().get(0).getUsername());
                myRecyclerView = (RecyclerView) v.findViewById(R.id.discover_recyclerview);
                SearchRecyclerViewAdapter recyclerViewAdapter = new SearchRecyclerViewAdapter(getContext(),response.body().getsuggestedPeople(),response.body().geteveryoneList());
                myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                linlaHeaderProgress.setVisibility(View.INVISIBLE);

                myRecyclerView.setAdapter(recyclerViewAdapter);

                // SEARCH BAR
                searchBar = v.findViewById(R.id.search_bar);
                searchBar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        recyclerViewAdapter.filter(s);

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {

            }
        });


        // CLICK ON ITEM
//        recyclerViewAdapter.setOnItemClickListener(new SearchRecyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(int position) {
//                Toast.makeText(getActivity(), "Position is: "+position, Toast.LENGTH_SHORT).show();
//            }
//        });

        //Notification badge activity


        //Notification Button
        notificationBtn = (ImageView) v.findViewById(R.id.bell_icon);
        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestFragment requestFragment = new RequestFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.activityLanding, requestFragment);
                transaction.addToBackStack(null).commit();
            }
        });
        return v;
    }




}
