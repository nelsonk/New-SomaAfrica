package com.love.soma.somaafrica.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.love.soma.somaafrica.R;
import com.love.soma.somaafrica.activity.FullPhoto;
import com.love.soma.somaafrica.activity.SchoolDetails;

import java.util.List;

/**
 * Created by Neli'e on 07/02/2016.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> {
    private List<Grid> photoList;
    Context mContext;
    Grid photo;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView personPhoto;

        public MyViewHolder(View view) {
            super(view);
            personPhoto = (ImageView) view.findViewById(R.id.logo);
            mContext = view.getContext();
           // personPhoto.setOnClickListener(this);
        }
/*
        @Override
        public void onClick(View v) {
            Intent i = new Intent(mContext, FullPhoto.class);
            i.putExtra("photo", photo.getPhotoId());
            Toast.makeText(mContext, String.valueOf(photo.getPhotoId()), Toast.LENGTH_LONG).show();
            mContext.startActivity(i);

        }*/
    }

    public GridAdapter(List<Grid> photolist) {
        this.photoList = photolist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photogrid, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        photo = photoList.get(position);
        holder.personPhoto.setImageResource(photo.getPhotoId());
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
}
