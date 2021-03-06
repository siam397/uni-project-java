package com.example.artsell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artsell.models.Profile;

import java.util.List;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.AnotherViewHolder> {

    Context mContext;
    List<Profile> mData;


    public ChatRecyclerViewAdapter(Context nContext, List<Profile> nData) {
        this.mContext = nContext;
        this.mData = nData;
    }

    @NonNull
    @Override
    public AnotherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_chat, parent, false);
        AnotherViewHolder vHolder = new AnotherViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnotherViewHolder holder, int position) {

        holder.tv_name.setText(mData.get(position).getUsername());
//        holder.img.setImageResource(mData.get(position).getProfilePicture());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class AnotherViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private ImageView img;

        public AnotherViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.name_chat);
            img = (ImageView) itemView.findViewById(R.id.dp_chat);
        }
    }
}
