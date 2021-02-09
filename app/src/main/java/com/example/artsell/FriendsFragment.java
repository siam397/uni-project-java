package com.example.artsell;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // F R E N S
        listFriend = new ArrayList<>();

        listFriend.add(new Profile("1", "Noman", "Shera", R.drawable.dp1));
        listFriend.add(new Profile("2", "Mamun", "Not shera", R.drawable.dp2));
        listFriend.add(new Profile("3", "Kalam", "Don't hurt me man", R.drawable.dp3));
        listFriend.add(new Profile("4", "Dr Strange", "wot", R.drawable.dp4));
        listFriend.add(new Profile("5", "Rahim Rahman", "no", R.drawable.dp5));
        listFriend.add(new Profile("6", "Karim Kahman", "dekhi", R.drawable.dp6));
        listFriend.add(new Profile("7", "Boring", "wut", R.drawable.dp7));
        listFriend.add(new Profile("8", "DJ", "hm", R.drawable.dp8));
        listFriend.add(new Profile("9", "RJ", "hmm", R.drawable.dp9));
        listFriend.add(new Profile("10", "CJ", "gta", R.drawable.dp10));
        listFriend.add(new Profile("11", "Bro fist", "pewpew", R.drawable.dp11));
        // F R E N S
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_friends, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.friends_recyclerview);
        FriendsRecyclerViewAdapter recyclerViewAdapter = new FriendsRecyclerViewAdapter(getContext(),listFriend);
        myRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        myRecyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }
}