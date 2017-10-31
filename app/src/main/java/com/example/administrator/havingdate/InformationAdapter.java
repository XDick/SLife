package com.example.administrator.havingdate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Administrator on 2017/9/16 0016.
 */

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.ViewHolder> {
    private Context mContext;
    private List<Information> mInformationList;

    static class ViewHolder extends RecyclerView.ViewHolder{
            CardView cardView;
            ImageView informationImage;
            TextView informationName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            informationImage = view.findViewById(R.id.information_image);
            informationName =  view.findViewById(R.id.information_name);

        }
    }



    public InformationAdapter(List<Information> informationList){
        mInformationList = informationList;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.information_item, parent, false);

        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Information information = mInformationList.get(position);
                Intent intent = new Intent(mContext , InformationActivity.class);
                intent.putExtra(InformationActivity.INFORMATION_NAME,information.getName());
                intent.putExtra(InformationActivity.INFORMATION_IMAGE_ID,information.getImageId());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Information information= mInformationList.get(position);
        holder.informationName.setText(information.getName());
        Glide.with(mContext).load(information.getImageId()).into(holder.informationImage);

    }
    @Override
    public int getItemCount(){
        return mInformationList.size();
    }

}
