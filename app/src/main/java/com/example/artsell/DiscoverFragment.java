package com.example.artsell;

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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artsell.models.Chatx;
import com.example.artsell.models.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DiscoverFragment extends Fragment {

    View v;
    private RecyclerView myRecyclerView;
    private List<SearchResult> listSearchResult;
    private EditText searchBar;




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
        listSearchResult = new ArrayList<>();
        listSearchResult.add(new SearchResult("1", "Noman", R.drawable.dp1, "bruh"));
        listSearchResult.add(new SearchResult("1", "Abdul", R.drawable.dp2, "fghfhfhgfh"));
        listSearchResult.add(new SearchResult("1", "Bokor", R.drawable.dp3, "sdfsfdsfdsfd"));
        listSearchResult.add(new SearchResult("1", "Chomon", R.drawable.dp4, "   WOW"));
        listSearchResult.add(new SearchResult("1", "Dakaat", R.drawable.dp5, "llllllllll"));
        listSearchResult.add(new SearchResult("1", "EEE", R.drawable.dp6, "BBBBB"));
        listSearchResult.add(new SearchResult("1", "REEEEE", R.drawable.dp7, "RRRRR"));
        listSearchResult.add(new SearchResult("1", "SHEEESH", R.drawable.dp8, "UUUUU"));
        listSearchResult.add(new SearchResult("1", "Nantu", R.drawable.dp9, "HHHHH"));
        listSearchResult.add(new SearchResult("1", "Najibullah", R.drawable.dp10, "HHHHH"));
        listSearchResult.add(new SearchResult("1", "Nhentai", R.drawable.dp11, "yes"));
        listSearchResult.add(new SearchResult("1", "Y Combinator", R.drawable.dp1, "no"));
        listSearchResult.add(new SearchResult("1", "Z dragon ball", R.drawable.dp2, "very good"));
        listSearchResult.add(new SearchResult("1", "Kulasekara", R.drawable.dp3, "Hustle and Grind 24/7"));
        listSearchResult.add(new SearchResult("1", "Tamim Iqbal", R.drawable.dp4, "Wish me on 29th Feb | Pisces"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_discover, container,  false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.discover_recyclerview);
        SearchRecyclerViewAdapter recyclerViewAdapter = new SearchRecyclerViewAdapter(getContext(),listSearchResult);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapter);

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


        return v;
    }
}
