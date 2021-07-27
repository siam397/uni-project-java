package com.example.artsell;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artsell.models.Chatx;
import com.example.artsell.models.FriendID;
import com.example.artsell.models.Profile;
import com.example.artsell.models.ResponseBody;
import com.example.artsell.models.SearchResult;
import com.example.artsell.utilities.Variables;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RequestRecyclerAdapter extends RecyclerView.Adapter<RequestRecyclerAdapter.AnotherViewHolder> {

    Context mContext;
    List<Profile> mData;
    Retrofit retrofit= Variables.initializeRetrofit();
    public RequestRecyclerAdapter(Context mContext, List<Profile> mData) {
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
        byte[] image= Base64.decode(mData.get(position).getProfilePicture(),Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(image,0,image.length);
        holder.img.setImageBitmap(bitmap);
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=mContext.getSharedPreferences("USER_INFO", Activity.MODE_PRIVATE);//Frequent to get SharedPreferences need to add a step getActivity () method
                String id = sharedPreferences.getString("user", "");
                RestApiPost restApiPost=retrofit.create(RestApiPost.class);
//                System.out.println("thissss"+id+" "+mData.get(position).getUser_id());
                FriendID friendID=new FriendID(id,mData.get(position).getUser_id());
                Call<ResponseBody> call=restApiPost.addFriend(friendID);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(!response.isSuccessful()){
                            System.out.println("something went wrong");
                            return;
                        }
                        mData.remove(mData.get(position));
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=mContext.getSharedPreferences("USER_INFO", Activity.MODE_PRIVATE);//Frequent to get SharedPreferences need to add a step getActivity () method
                String id = sharedPreferences.getString("user", "");
                RestApiPost restApiPost=retrofit.create(RestApiPost.class);
//                System.out.println("thissss"+id+" "+mData.get(position).getUser_id());
                FriendID friendID=new FriendID(id,mData.get(position).getUser_id());
                Call<ResponseBody> call=restApiPost.removeRequest(friendID);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(!response.isSuccessful()){
                            System.out.println("something went wrong");
                            return;
                        }
                        mData.remove(mData.get(position));
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




    public class AnotherViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name;
        private ImageView img;
        private Button accept;
        private Button reject;
        public AnotherViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.name_request);
            img = (ImageView) itemView.findViewById(R.id.dp_request);
            accept=(Button) itemView.findViewById(R.id.btn_accept);
            reject=(Button) itemView.findViewById(R.id.btn_reject);
        }
    }
}
