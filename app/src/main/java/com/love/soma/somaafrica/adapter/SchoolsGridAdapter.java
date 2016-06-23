package com.love.soma.somaafrica.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.love.soma.somaafrica.R;

import java.util.List;

/**
 * Created by Neli'e on 07/02/2016.
 */
public class SchoolsGridAdapter extends RecyclerView.Adapter<SchoolsGridAdapter.MyViewHolder> {
    private List<SchoolsGrid> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView school;
        ImageView personPhoto;

        public MyViewHolder(View view) {
            super(view);
            school = (TextView) view.findViewById(R.id.school);
            personPhoto = (ImageView) view.findViewById(R.id.logo);
        }
    }

    public SchoolsGridAdapter(List<SchoolsGrid> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schoolsgrid, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SchoolsGrid movie = moviesList.get(position);
        holder.school.setText(movie.getSchool());
        holder.personPhoto.setImageResource(movie.getPhotoId());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
