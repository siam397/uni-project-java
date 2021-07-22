package com.example.artsell;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artsell.models.Chatx;
import com.example.artsell.models.Profile;

import java.util.List;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.AnotherViewHolder> {

    Context mContext;
    List<Chatx> mData;
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public ChatRecyclerViewAdapter(Context mContext, List<Chatx> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public AnotherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_chat, parent, false);
        AnotherViewHolder vHolder = new AnotherViewHolder(v,mListener);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnotherViewHolder holder, int position) {

        holder.tv_name.setText(mData.get(position).getToPerson());
        if(mData.get(position).getMessage().equals("faludaBoizzz9252image")){
            if(mData.get(position).getName().equals(mData.get(position).getToPerson())){
                holder.tv_text.setText("Image Received");
            }else{
                holder.tv_text.setText("Image Sent");
            }
        } else{
            if(mData.get(position).getName().equals(mData.get(position).getToPerson())){
                holder.tv_text.setText(mData.get(position).getMessage());
            }else{
                String text="You: "+mData.get(position).getMessage();
                holder.tv_text.setText(text);
            }
        }
        byte[] image= Base64.decode(mData.get(position).getProfilePicture(),Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(image,0,image.length);
        holder.img.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class AnotherViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_text;
        private ImageView img;

        public AnotherViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.name_chat);
            tv_text = (TextView) itemView.findViewById(R.id.text_chat);
            img = (ImageView) itemView.findViewById(R.id.dp_chat);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
