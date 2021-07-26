package com.example.artsell;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Base64;
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
import com.example.artsell.models.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.aViewHolder> {

    Context mContext;
    List<SearchResult> mData;
    List<SearchResult> mDataCopy;
    //    OnItemClickListener onItemClickListener;
    Dialog myDialog;

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
                                                              dialog_dp.setImageResource(mData.get(vHolder.getAdapterPosition()).getProfilePicture());
//                byte[] image= Base64.decode(mData.get(vHolder.getAdapterPosition()).getProfilePicture(),Base64.DEFAULT);
//                Bitmap bitmap= BitmapFactory.decodeByteArray(image,0,image.length);
//
//                dialog_dp.setImageBitmap(bitmap);
                                                              Button btn = myDialog.findViewById(R.id.btn_dialogSearch);
                                                              btn.setOnClickListener(new View.OnClickListener() {
                                                                  @Override
                                                                  public void onClick(View v) {
                                                                      Intent intent = new Intent(mContext, ChatRoomActivity.class);
                                                                      intent.putExtra("id", mData.get(vHolder.getAdapterPosition()).getUser_id());
                                                                      intent.putExtra("username", mData.get(vHolder.getAdapterPosition()).getUsername());
                                                                      mContext.startActivity(intent);
                                                                  }
                                                              });
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
        holder.img.setImageResource(mData.get(position).getProfilePicture());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void filter(CharSequence charSequence) {
        List<SearchResult> tempArrayList = new ArrayList<>();
        if (!TextUtils.isEmpty(charSequence)) {
            for (SearchResult searchResult : mData) {
                if (searchResult.getUsername().toLowerCase().contains(charSequence)) {
                    tempArrayList.add(searchResult);
                }
            }
        } else {
            tempArrayList.addAll(mDataCopy);
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
