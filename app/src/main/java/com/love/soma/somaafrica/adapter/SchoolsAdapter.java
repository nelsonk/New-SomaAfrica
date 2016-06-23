package com.love.soma.somaafrica.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.love.soma.somaafrica.R;
import com.love.soma.somaafrica.activity.SchoolDetails;

import java.util.List;

/**
 * Created by Neli'e on 07/02/2016.
 */
public class SchoolsAdapter extends RecyclerView.Adapter<SchoolsAdapter.MyViewHolder> {
    private List<Schools> moviesList;
    CustomItemClickListener listener;
    Context mContext;
/*
    public interface SchoolActionListener {
        void onSchoolClick(Schools school, int index);
    }
    */

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, genre;
        ImageView personPhoto;
        //SchoolActionListener schoolActionListener;
        public Schools currentSchool;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            personPhoto = (ImageView) view.findViewById(R.id.year);
            mContext = view.getContext();

            //personPhoto.setOnClickListener(this);
           // genre.setOnClickListener(this);
            //title.setOnClickListener(this);
/*
            try {
               schoolActionListener = (SchoolActionListener) mContext;
            } catch (ClassCastException e) {
                throw new ClassCastException(mContext.toString() + "must implement SchoolActionListener");
            }
            */
        }
/*
        @Override
        public void onClick(View v) {
            //schoolActionListener.onSchoolClick(currentSchool, getAdapterPosition());
            Intent i = new Intent(mContext, SchoolDetails.class);
            i.putExtra("school", title.getText().toString());
            mContext.startActivity(i);
        }*/
    }

    public SchoolsAdapter(List<Schools> moviesList) {
        this.moviesList = moviesList;
        //this.mContext = mContext;
        //this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schools, parent, false);
        final MyViewHolder mViewHolder = new MyViewHolder(itemView);
              return mViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Schools movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.personPhoto.setImageResource(movie.getPhotoId());
        holder.currentSchool = movie;
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
