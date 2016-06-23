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
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {
    private List<Events> eventsList;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, date;

        public Events currentevent;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            mContext = view.getContext();

            date.setOnClickListener(this);
            title.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(mContext, SchoolDetails.class);
            i.putExtra("event", title.getText().toString());
            mContext.startActivity(i);
        }
    }

    public EventsAdapter(List<Events> eventsList) {
        this.eventsList = eventsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schevents, parent, false);
        final MyViewHolder mViewHolder = new MyViewHolder(itemView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Events event = eventsList.get(position);
        holder.title.setText(event.getTitle());
        holder.date.setText(event.getDate());
        holder.currentevent = event;
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }
}
