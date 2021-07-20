package com.example.artsell;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.artsell.models.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    private RecyclerView myRecyclerView;
    private List<Profile> listFriend;

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

        // F R E N S
        listFriend = new ArrayList<>();
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                SharedPreferences sharedPreferences= Objects.requireNonNull(getActivity()).getBaseContext().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
//                String id=sharedPreferences.getString("user","");
//                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
//                Object s=map.get(id);
//                System.out.println(id+" "+s.toString());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
////                Log.i("TAG", "Failed to read value.", error.toException());
//            }
//        });
//        listFriend.add(new Profile("1", "Noman", "Shera", R.drawable.dp1));
//        listFriend.add(new Profile("2", "Mamun", "Not shera", R.drawable.dp2));
//        listFriend.add(new Profile("3", "Kalam", "Don't hurt me man", R.drawable.dp3));
//        listFriend.add(new Profile("4", "Dr Strange", "wot", R.drawable.dp4));
//        listFriend.add(new Profile("5", "Rahim Rahman", "no", R.drawable.dp5));
//        listFriend.add(new Profile("6", "Karim Kahman", "dekhi", R.drawable.dp6));
//        listFriend.add(new Profile("7", "Boring", "wut", R.drawable.dp7));
//        listFriend.add(new Profile("8", "DJ", "hm", R.drawable.dp8));
//        listFriend.add(new Profile("9", "RJ", "hmm", R.drawable.dp9));
//        listFriend.add(new Profile("10", "CJ", "gta", R.drawable.dp10));
//        listFriend.add(new Profile("11", "Bro fist", "pewpew", R.drawable.dp11));
        // F R E N S
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_chat, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.chat_recyclerview);
        ChatRecyclerViewAdapter recyclerViewAdapter = new ChatRecyclerViewAdapter(getContext(),listFriend);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }


}