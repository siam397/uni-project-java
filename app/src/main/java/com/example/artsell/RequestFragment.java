package com.example.artsell;

import android.app.Activity;
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
import com.example.artsell.models.SearchResult;
import com.example.artsell.models.UserID;
import com.example.artsell.utilities.Variables;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestFragment extends Fragment {

    View v;
    private RecyclerView myRecyclerView;
    private List<SearchResult> listRequest;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RequestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestFragment newInstance(String param1, String param2) {
        RequestFragment fragment = new RequestFragment();
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
        // R E Q U E S T S
        listRequest = new ArrayList<>();
        listRequest.add(new SearchResult("1", "Noman", R.drawable.dp1, "bruh"));
        listRequest.add(new SearchResult("1", "Abdul", R.drawable.dp2, "fghfhfhgfh"));
        listRequest.add(new SearchResult("1", "Bokor", R.drawable.dp3, "sdfsfdsfdsfd"));
        listRequest.add(new SearchResult("1", "Chomon", R.drawable.dp4, "   WOW"));
        listRequest.add(new SearchResult("1", "Dakaat", R.drawable.dp5, "llllllllll"));
        listRequest.add(new SearchResult("1", "EEE", R.drawable.dp6, "BBBBB"));
        listRequest.add(new SearchResult("1", "REEEEE", R.drawable.dp7, "RRRRR"));
        listRequest.add(new SearchResult("1", "SHEEESH", R.drawable.dp8, "UUUUU"));
        listRequest.add(new SearchResult("1", "Nantu", R.drawable.dp9, "HHHHH"));
        listRequest.add(new SearchResult("1", "Najibullah", R.drawable.dp10, "HHHHH"));
        listRequest.add(new SearchResult("1", "Nhentai", R.drawable.dp11, "yes"));
        listRequest.add(new SearchResult("1", "Y Combinator", R.drawable.dp1, "no"));
        listRequest.add(new SearchResult("1", "Z dragon ball", R.drawable.dp2, "very good"));
        listRequest.add(new SearchResult("1", "Kulasekara", R.drawable.dp3, "Hustle and Grind 24/7"));
        listRequest.add(new SearchResult("1", "Tamim Iqbal", R.drawable.dp4, "Wish me on 29th Feb | Pisces"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_request, container,  false);
        SharedPreferences preferences = getActivity().getSharedPreferences("USER_INFO", Activity.MODE_PRIVATE);//Frequent to get SharedPreferences need to add a step getActivity () method
        String id = preferences.getString("user", "");
        Retrofit retrofit= Variables.initializeRetrofit();
        RestApiPost restApiPost=retrofit.create(RestApiPost.class);
        UserID userID=new UserID(id);
        Call<List<Profile>> call=restApiPost.getRequests(userID);
        call.enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                if(!response.isSuccessful()){
                    System.out.println("Something went wrong");
                    return;
                }
                List<Profile>requests=response.body();
                myRecyclerView = (RecyclerView) v.findViewById(R.id.request_recyclerview);
                RequestRecyclerAdapter recyclerViewAdapter = new RequestRecyclerAdapter(getContext(),requests);
                myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                myRecyclerView.setAdapter(recyclerViewAdapter);

            }

            @Override
            public void onFailure(Call<List<Profile>> call, Throwable t) {

            }
        });


        return v;
    }
}