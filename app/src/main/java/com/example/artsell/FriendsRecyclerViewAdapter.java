package com.example.artsell;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FriendsRecyclerViewAdapter extends RecyclerView.Adapter<FriendsRecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<Profile> mData;
    Dialog myDialog;

    public FriendsRecyclerViewAdapter(Context mContext, List<Profile> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_friend, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        //Dialog initialization
        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialogbox_friend);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vHolder.item_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_name = (TextView) myDialog.findViewById(R.id.name_dialogbox);
                TextView dialog_bio = (TextView) myDialog.findViewById(R.id.bio_dialogbox);
                ImageView dialog_dp = (ImageView) myDialog.findViewById(R.id.dp_dialogbox);
                dialog_name.setText(mData.get(vHolder.getAdapterPosition()).getUsername());
                dialog_bio.setText(mData.get(vHolder.getAdapterPosition()).getBio());
                dialog_dp.setImageResource(mData.get(vHolder.getAdapterPosition()).getProfilePicture());

//                Toast.makeText(mContext, "Test Click"+String.valueOf(vHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                myDialog.show();
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getUsername());
        holder.img.setImageResource(mData.get(position).getProfilePicture());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout item_friend;
        private TextView tv_name;
        private ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item_friend = (RelativeLayout) itemView.findViewById(R.id.friend_item_id);
            tv_name = (TextView) itemView.findViewById(R.id.name_friend);
            img = (ImageView) itemView.findViewById(R.id.dp_friend);
        }
    }
}

