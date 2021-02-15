package com.example.artsell;

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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter{
    private static final int TYPE_MESSAGE_SENT=0;
    private static final int TYPE_MESSAGE_RECEIVED=1;
    private static final int IMAGE_SENT=2;
    private static final int IMAGE_RECEIVED=3;

    private LayoutInflater inflater;
    private List<JSONObject> messages=new ArrayList<>();
    public MessageAdapter(LayoutInflater inflater){
        this.inflater=inflater;
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder{
        TextView messagetext;
        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);
            messagetext=itemView.findViewById(R.id.sentText);
        }
    }

    private class SentImageHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public SentImageHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.sentImage);
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView receivedText,name;
        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            receivedText=itemView.findViewById(R.id.receivedText);
        }
    }

    private class ReceivedImageHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        public ReceivedImageHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            image=itemView.findViewById(R.id.receivedImage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        JSONObject message=messages.get(position);
        try{
            if(message.getBoolean("isSent")){
                if(message.has("message")){
                    return TYPE_MESSAGE_SENT;
                }else{
                    return IMAGE_SENT;
                }
            }else{
                if(message.has("message")){
                    return TYPE_MESSAGE_RECEIVED;
                }else{
                    return IMAGE_RECEIVED;
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case TYPE_MESSAGE_SENT:
                view=inflater.inflate(R.layout.item_sent_message,parent,false);
                return new SentMessageHolder(view);
            case TYPE_MESSAGE_RECEIVED:
                view=inflater.inflate(R.layout.item_received_message,parent,false);
                return new ReceivedMessageHolder(view);
            case IMAGE_SENT:
                view=inflater.inflate(R.layout.item_sent_image,parent,false);
                return new SentImageHolder(view);
            case IMAGE_RECEIVED:
                view=inflater.inflate(R.layout.item_received_image,parent,false);
                return new ReceivedImageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        JSONObject message=messages.get(position);
        try{
            if(message.getBoolean("isSent")){
                if(message.has("message")){
                    SentMessageHolder sentMessageHolder=(SentMessageHolder) holder;
                    sentMessageHolder.messagetext.setText(message.getString("message"));
                }else{
                    SentImageHolder sentImageHolder=(SentImageHolder) holder;
                    Bitmap bitmap=getBitmapfromString(message.getString("image"));
                    sentImageHolder.imageView.setImageBitmap(bitmap);
                }
            }else{
                if(message.has("message")){
                    ReceivedMessageHolder messageHolder=(ReceivedMessageHolder) holder;
                    messageHolder.name.setText(message.getString("name"));
                    messageHolder.receivedText.setText(message.getString("message"));
                }else{
                    ReceivedImageHolder imageHolder=(ReceivedImageHolder) holder;
                    imageHolder.name.setText(message.getString("name"));
                    Bitmap bitmap=getBitmapfromString(message.getString("image"));
                    imageHolder.image.setImageBitmap(bitmap);
                }
            }
        }catch(Exception e){

        }
    }

    private Bitmap getBitmapfromString(String image) {
        byte[] bytes= Base64.decode(image,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
    public void addItem(JSONObject jsonObject){
        messages.add(jsonObject);
        notifyDataSetChanged();
    }
}
