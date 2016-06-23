package com.love.soma.somaafrica.activity;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.love.soma.somaafrica.R;
import com.love.soma.somaafrica.adapter.CustomItemClickListener;
import com.love.soma.somaafrica.adapter.Schools;
import com.love.soma.somaafrica.adapter.SchoolsAdapter;
import com.love.soma.somaafrica.provider.SchoolsDBProvider;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * interface
 * to handle interaction events.
 * Use the {@link Primary#} factory method to
 * create an instance of this fragment.
 */
public class Primary extends Fragment {
    private LinearLayoutManager lLayout;
    String message;
    private List<Schools> schoolList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SchoolsAdapter mAdapter;

    public Primary() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("search_primary"));
        addsch();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_primary, container, false);

        //List<ItemObject> rowListItem = getAllItemList();
        //lLayout = new LinearLayoutManager(getActivity());

        recyclerView = (RecyclerView)rootview.findViewById(R.id.recycler_view);
        mAdapter = new SchoolsAdapter(schoolList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Schools school = schoolList.get(position);
                Intent i = new Intent(getActivity(), SchoolDetails.class);
                i.putExtra("photo", school.getPhotoId());
                i.putExtra("school", school.getTitle());
                startActivity(i);
                Toast.makeText(getActivity(), school.getPhotoId() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        list();
        //prepareMovieData();

        //rView.setLayoutManager(lLayout);

       // RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(getActivity(), rowListItem);
       // rView.setAdapter(rcAdapter);

        return rootview;
    }
/*
    private void prepareMovieData() {

        Schools movie = new Schools("SomaAfrica Primary School", "Exploring the limits of technology", R.mipmap.somaafrica);
        movieList.add(movie);

        movie = new Schools("Anodel Primary School", "Education our pride", R.drawable.ic_menu_slideshow);
        movieList.add(movie);

        movie = new Schools("Infants Primary School", "Strive for success",R.drawable.ic_menu_gallery);
        movieList.add(movie);

        movie = new Schools("Kireka Primary School", "Working for excellence",R.drawable.ic_menu_gallery);
        movieList.add(movie);

        movie = new Schools("Kibuli Primary", "We believe in education", R.drawable.ic_menu_share);
        movieList.add(movie);

        movie = new Schools("Kampala Parents School", "Invest for the future",R.drawable.ic_menu_slideshow);
        movieList.add(movie);

        movie = new Schools("Lubiri Primary School", "We learn by doing",R.drawable.ic_menu_send);
        movieList.add(movie);

        movie = new Schools("Kireka Academy", "Investing in science and technology", R.drawable.ic_menu_manage);
        movieList.add(movie);

        movie = new Schools("The LEGO Movie", "Animation", R.drawable.ic_menu_gallery);
        movieList.add(movie);

        movie = new Schools("Iron Man", "Action & Adventure", R.drawable.ic_menu_camera);
        movieList.add(movie);

        movie = new Schools("Aliens", "Science Fiction", R.drawable.ic_menu_camera);
        movieList.add(movie);

        mAdapter.notifyDataSetChanged();

    }
    */

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

        @Override

        public void onReceive(Context context, Intent intent) {
            message = intent.getStringExtra("message");
            //Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            autocomplete();
        }

    };

    private void list() {

        String[] projection = {SchoolsDB.COLUMN_ID,
                SchoolsDB.COLUMN_NAME, SchoolsDB.COLUMN_MOTTO, SchoolsDB.COLUMN_ABOUT, SchoolsDB.COLUMN_STATUS, SchoolsDB.COLUMN_LOGO, SchoolsDB.COLUMN_LEVEL};
        String selection = SchoolsDB.COLUMN_LEVEL + " = ?";
        String[] args = {"P"};
        Cursor cursor = getActivity().getContentResolver().query(SchoolsDBProvider.CONTENT_URI, projection, selection, args,
                null);
        schoolList.clear();

        if (cursor.moveToFirst()) {

            do {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(SchoolsDB.COLUMN_NAME));
                    String motto = cursor.getString(cursor.getColumnIndexOrThrow(SchoolsDB.COLUMN_MOTTO));
                    int logo = cursor.getInt(cursor.getColumnIndexOrThrow(SchoolsDB.COLUMN_LOGO));
                    Schools movie = new Schools(name, motto, logo);
                    schoolList.add(movie);

            } while (cursor.moveToNext());
            mAdapter.notifyDataSetChanged();
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
    }

    private void addsch() {
        String[] projection = { SchoolsDB.COLUMN_ID,
                SchoolsDB.COLUMN_NAME, SchoolsDB.COLUMN_MOTTO, SchoolsDB.COLUMN_ABOUT, SchoolsDB.COLUMN_STATUS, SchoolsDB.COLUMN_LOGO, SchoolsDB.COLUMN_LEVEL };
        String selection = SchoolsDB.COLUMN_LEVEL + " = ?";
        String[] args = {"P"};
        Cursor cursor = getActivity().getContentResolver().query(SchoolsDBProvider.CONTENT_URI, projection, selection, args,
                null);


        if(cursor == null){
            Toast.makeText(getActivity(), "Error occurred during query", Toast.LENGTH_LONG).show();
        }else if(cursor.getCount() <1){
            Toast.makeText(getActivity(), "No schools were in db", Toast.LENGTH_LONG).show();

            for (int i = 0; i < 7; i++) {

                String[] school = {"SomaAfrica Primary School", "Anodel Primary School", "Infants Primary School", "Kireka Primary School", "Kibuli Primary", "Kampala Parents School","Lubiri Primary School"};
                String[] motto = {"Exploring the limits of technology", "Education our pride", "Strive for success", "Working for excellence", "Strive for success", "Invest for the future","Investing in science and technology" };
                String[] about = {"2nd", "1st", "3rd", "4th", "10th", "20th", "12th"};
                String[] status = {"Admission on", "Admission on", "Admission off", "Admission on","Admission on", "Admission on", "Admission off"};
                int[] logo = {R.mipmap.somaafrica, R.mipmap.kasawoss, R.mipmap.oakland, R.mipmap.kitanteps, R.mipmap.nakasero, R.mipmap.namirembeparents, R.mipmap.elianajs};
                String[] level = {"P", "P","P","P","P","P","P"};
                Uri newUri;

// Defines an object to contain the new values to insert
                ContentValues newValues = new ContentValues();

// Sets the values of each column and inserts the word.
                newValues.put(SchoolsDB.COLUMN_NAME, school[i]);
                newValues.put(SchoolsDB.COLUMN_MOTTO, motto[i]);
                newValues.put(SchoolsDB.COLUMN_ABOUT, about[i]);
                newValues.put(SchoolsDB.COLUMN_STATUS, status[i]);
                newValues.put(SchoolsDB.COLUMN_LOGO, logo[i]);
                newValues.put(SchoolsDB.COLUMN_LEVEL, level[i]);


                newUri = getActivity().getContentResolver().insert(
                        SchoolsDBProvider.CONTENT_URI,   // the user dictionary content URI
                        newValues                          // the values to insert
                );

            }

        }if (!cursor.isClosed()) {
            cursor.close();
        }

    }

    private void autocomplete() {
        String[] projection = { SchoolsDB.COLUMN_ID,
                SchoolsDB.COLUMN_NAME, SchoolsDB.COLUMN_MOTTO, SchoolsDB.COLUMN_ABOUT, SchoolsDB.COLUMN_STATUS, SchoolsDB.COLUMN_LOGO, SchoolsDB.COLUMN_LEVEL };
        String selection = SchoolsDB.COLUMN_LEVEL + " = ?";
        String[] args = {"P"};
        Cursor cursor = getActivity().getContentResolver().query(SchoolsDBProvider.CONTENT_URI, projection, selection, args,
                null);


        schoolList.clear();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            String name = cursor.getString(cursor.getColumnIndexOrThrow(SchoolsDB.COLUMN_NAME));

            if(name.toLowerCase().contains(message.toLowerCase().trim())){
                String motto = cursor.getString(cursor.getColumnIndexOrThrow(SchoolsDB.COLUMN_MOTTO));
                int logo = cursor.getInt(cursor.getColumnIndexOrThrow(SchoolsDB.COLUMN_LOGO));
                Schools movie = new Schools(name, motto, logo);
                schoolList.add(movie);
            }else{
                // Toast.makeText(getActivity(), message + " "+ "Search not found", Toast.LENGTH_LONG).show();
            }
            cursor.moveToNext();
        }
        mAdapter.notifyDataSetChanged();

        if (!cursor.isClosed()) {
            cursor.close();
        }


    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Primary.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Primary.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }



}
