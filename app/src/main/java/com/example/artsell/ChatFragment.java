package com.example.artsell;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.artsell.models.Chatx;
import com.example.artsell.models.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    View v;

    private RecyclerView myRecyclerView;
    private List<Chatx> listTexts;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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

        // T E X T S
        listTexts = new ArrayList<>();
        listTexts.add(new Chatx("1", "Noman", R.drawable.dp1, "bruh"));
        listTexts.add(new Chatx("2", "Mamun", R.drawable.dp2, "lmao"));
        listTexts.add(new Chatx("3", "Kalam", R.drawable.dp3, "no"));
        listTexts.add(new Chatx("4", "Dr Strange", R.drawable.dp4, "A quick brown fox jumped over the lazy dog"));
        listTexts.add(new Chatx("5", "Rahim Rahman", R.drawable.dp5, "bruhbruhbruhbruh"));
        listTexts.add(new Chatx("6", "Karim Kahman", R.drawable.dp6, "How r u"));
        listTexts.add(new Chatx("7", "Boring", R.drawable.dp7, "Computer Architecture"));
        listTexts.add(new Chatx("8", "DJ", R.drawable.dp8, "SRE bad"));
        listTexts.add(new Chatx("9", "RJ", R.drawable.dp9, "Android Studio good"));
        listTexts.add(new Chatx("10", "CJ", R.drawable.dp10, "ok"));
        listTexts.add(new Chatx("11", "Bro fist", R.drawable.dp11, "asos"));
        SharedPreferences preferences = getActivity().getSharedPreferences("USER_INFO", Activity.MODE_PRIVATE);//Frequent to get SharedPreferences need to add a step getActivity () method
        String id = preferences.getString("user", "");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(id);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                GenericTypeIndicator<List<Chatx>> genericTypeIndicator =new GenericTypeIndicator<List<Chatx>>(){};
//                List<Chatx> taskDesList=dataSnapshot.getValue(genericTypeIndicator);
//                for(int i=0;i<taskDesList.size();i++){
//                    System.out.println(taskDesList.get(i).toString());
//                }
                System.out.println(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_chat, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.chat_recyclerview);
        ChatRecyclerViewAdapter recyclerViewAdapter = new ChatRecyclerViewAdapter(getContext(),listTexts);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }


}