package com.example.artsell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artsell.models.Chatx;
import com.example.artsell.models.Profile;
import com.example.artsell.models.SearchResult;

import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.RecyclerviewHolder> {

    Context context;
    List<SearchResult> searchData;

    public SearchRecyclerViewAdapter(Context context, List<SearchResult> searchData) {
        this.context = context;
        this.searchData = searchData;
    }

    static class RecyclerviewHolder extends RecyclerView.ViewHolder{

        ImageView search_dp;
        TextView search_name, search_bio;

        public RecyclerviewHolder(@NonNull View itemView){
            super(itemView);

            search_dp = itemView.findViewById(R.id.dp_search_result);
            search_name = itemView.findViewById(R.id.name_search_result);
            search_bio = itemView.findViewById(R.id.bio_search_result);
        }
    }



    @NonNull
    @Override
    public RecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_discover, parent, false);
        return new RecyclerviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewHolder holder, int position) {

        holder.search_name.setText(searchData.get(position).getUsername());
        holder.search_bio.setText(searchData.get(position).getBio());
        holder.search_dp.setImageResource(searchData.get(position).getProfilePicture());
    }

    @Override
    public int getItemCount() {
        return searchData.size();
    }


}
