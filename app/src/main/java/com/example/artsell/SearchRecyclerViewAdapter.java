package com.example.artsell;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artsell.models.Chatx;
import com.example.artsell.models.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.aViewHolder> {

    Context mContext;
    List<SearchResult> mData;
    List<SearchResult> mDataCopy;

    public SearchRecyclerViewAdapter(Context mContext, List<SearchResult> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataCopy = new ArrayList<>();
        mDataCopy.addAll(mData);
    }



    @NonNull
    @Override
    public SearchRecyclerViewAdapter.aViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_search_result, parent, false);
        SearchRecyclerViewAdapter.aViewHolder vHolder = new SearchRecyclerViewAdapter.aViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewAdapter.aViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getUsername());
        holder.tv_bio.setText(mData.get(position).getBio());
        holder.img.setImageResource(mData.get(position).getProfilePicture());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void filter(CharSequence charSequence){
        List<SearchResult> tempArrayList = new ArrayList<>();
        if (!TextUtils.isEmpty(charSequence)){
            for (SearchResult searchResult: mData){
                if (searchResult.getUsername().toLowerCase().contains(charSequence)){
                    tempArrayList.add(searchResult);
                }
            }
        }
        else{
            tempArrayList.addAll(mDataCopy);
        }

        mData.clear();
        mData.addAll(tempArrayList);
        notifyDataSetChanged();
        tempArrayList.clear();
    }



    public static class aViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_bio;
        private ImageView img;

        public aViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.name_search_result);
            tv_bio = (TextView) itemView.findViewById(R.id.bio_search_result);
            img = (ImageView) itemView.findViewById(R.id.dp_search_result);
        }
    }
}
