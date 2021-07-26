package com.example.artsell;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artsell.models.Chatx;
import com.example.artsell.models.FriendID;
import com.example.artsell.models.GetFriends;
import com.example.artsell.models.Profile;
import com.example.artsell.models.SearchResult;
import com.example.artsell.models.User;
import com.example.artsell.models.UserID;
import com.example.artsell.utilities.Variables;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.aViewHolder> {

    Context mContext;
    List<User> mData;
    List<User> mDataCopy;
    //    OnItemClickListener onItemClickListener;
    Dialog myDialog;
    List<User>xxx;

    public SearchRecyclerViewAdapter(Context mContext, List<User> mData, List<User>mDataCopy) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataCopy = mDataCopy;
        this.xxx = new ArrayList<>();
        xxx.addAll(mData);
    }


    @NonNull
    @Override
    public SearchRecyclerViewAdapter.aViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_search_result, parent, false);
        aViewHolder vHolder = new SearchRecyclerViewAdapter.aViewHolder(v);



        //Dialog initialization
        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialogbox_searchresult);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btn = myDialog.findViewById(R.id.btn_dialogSearch);
        vHolder.item_search_result.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              TextView dialog_name = (TextView) myDialog.findViewById(R.id.name_dialogSearch);
                                                              TextView dialog_bio = (TextView) myDialog.findViewById(R.id.bio_dialogSearch);
                                                              ImageView dialog_dp = (ImageView) myDialog.findViewById(R.id.dp_dialogSearch);
                                                              dialog_name.setText(mData.get(vHolder.getAdapterPosition()).getUsername());
                                                              dialog_bio.setText(mData.get(vHolder.getAdapterPosition()).getBio());
                                                              byte[] image= Base64.decode(mData.get(vHolder.getAdapterPosition()).getProfilePicture(),Base64.DEFAULT);
                                                              Bitmap bitmap= BitmapFactory.decodeByteArray(image,0,image.length);
                                                              dialog_dp.setImageBitmap(bitmap);
                                                              SharedPreferences sharedPreferences=mContext.getSharedPreferences("USER_INFO", Activity.MODE_PRIVATE);//Frequent to get SharedPreferences need to add a step getActivity () method
                                                              String id = sharedPreferences.getString("user", "");

//                Toast.makeText(mContext, "Test Click"+String.valueOf(vHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                                                              myDialog.show();
                                                          }
                                                      }

        );
        //..............

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewAdapter.aViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getUsername());
        holder.tv_bio.setText(mData.get(position).getBio());
        byte[] image=Base64.decode(mData.get(position).getProfilePicture(),Base64.DEFAULT);
        Bitmap bitmap=BitmapFactory.decodeByteArray(image,0,image.length);
        holder.img.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void filter(CharSequence charSequence) {
        List<User> tempArrayList = new ArrayList<>();
        if (!TextUtils.isEmpty(charSequence)) {
            for (User profile : mDataCopy) {
                if (profile.getUsername().toLowerCase().contains(charSequence)) {
                    tempArrayList.add(profile);
                }
            }
        } else {
            tempArrayList.addAll(xxx);
        }
        mData.clear();
        mData.addAll(tempArrayList);
        notifyDataSetChanged();
        tempArrayList.clear();
    }


    //.............
//    public interface OnItemClickListener{
//        void onClick(int position);
//    }
    //.............
    //.............
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
//        this.onItemClickListener = onItemClickListener;
//    }
    //.............


    public class aViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout item_search_result;
        private TextView tv_name;
        private TextView tv_bio;
        private ImageView img;

        public aViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.name_search_result);
            tv_bio = (TextView) itemView.findViewById(R.id.bio_search_result);
            img = (ImageView) itemView.findViewById(R.id.dp_search_result);
            item_search_result = (RelativeLayout) itemView.findViewById(R.id.search_item_id);
            //.......
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClickListener.onClick(getAdapterPosition());
//                }
//            });
            //.......
        }
    }
}
