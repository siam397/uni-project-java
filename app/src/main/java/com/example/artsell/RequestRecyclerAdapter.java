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
import com.example.artsell.models.SearchResult;

import java.util.List;

public class RequestRecyclerAdapter extends RecyclerView.Adapter<RequestRecyclerAdapter.AnotherViewHolder> {

    Context mContext;
    List<SearchResult> mData;

    public RequestRecyclerAdapter(Context mContext, List<SearchResult> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RequestRecyclerAdapter.AnotherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_request, parent, false);
        AnotherViewHolder vHolder = new AnotherViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestRecyclerAdapter.AnotherViewHolder holder, int position) {
        System.out.println(mData.get(position).getUsername());
        holder.tv_name.setText(mData.get(position).getUsername());
        holder.img.setImageResource(mData.get(position).getProfilePicture());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




    public class AnotherViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name;
        private ImageView img;

        public AnotherViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.name_request);
            img = (ImageView) itemView.findViewById(R.id.dp_request);
        }
    }
}
