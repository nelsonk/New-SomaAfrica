package com.love.soma.somaafrica.activity;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.love.soma.somaafrica.R;
import com.love.soma.somaafrica.adapter.Events;
import com.love.soma.somaafrica.adapter.EventsAdapter;
import com.love.soma.somaafrica.adapter.Grid;
import com.love.soma.somaafrica.adapter.GridAdapter;
import com.love.soma.somaafrica.adapter.ScalingUtilities;
import com.love.soma.somaafrica.adapter.SchoolsGrid;
import com.love.soma.somaafrica.adapter.SchoolsGridAdapter;
import com.love.soma.somaafrica.provider.SchoolsDBProvider;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Neli'e on 13/03/2016.
 */
public class SchoolDetails extends AppCompatActivity {
    RecyclerView recyclerView, eventsview;
    private List<Grid> photoList = new ArrayList<>();
    private List<Events> eventsList = new ArrayList<>();
    private List<Events> feesList = new ArrayList<>();
    private List<Events> reqList = new ArrayList<>();
    private GridAdapter mAdapter;
    private EventsAdapter eventsAdapter;
    private EventsAdapter feesAdapter;
    private EventsAdapter reqAdapter;
    CollapsingToolbarLayout collapsingToolbarLayout;
    String sch;
    int photo;
    ImageView image;
    int measuredWidth = 0;
    int measuredHeight = 0;
    /** Wanted width of decoded image */
    private int mDstWidth;

    /** Wanted height of decoded image */
    private int mDstHeight;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schapp_bar_main);
        addsch();
        addevents();
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        image = (ImageView)findViewById(R.id.expandedImage);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        recyclerView = (RecyclerView) findViewById(R.id.gallery);
        eventsview = (RecyclerView) findViewById(R.id.events);
        Intent intent = getIntent();
        if (intent.hasExtra("school")) {
            sch = intent.getStringExtra("school");
            photo = intent.getIntExtra("photo",R.mipmap.somaafrica );
        }

        //getWindowManager().getDefaultDisplay();

        WindowManager w = getWindowManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            w.getDefaultDisplay().getSize(size);
            measuredWidth = size.x;
            measuredHeight = size.y;
        } else {
            Display d = w.getDefaultDisplay();
            measuredWidth = d.getWidth();
            measuredHeight = d.getHeight();
        }

        //mDstWidth = getResources().getDimensionPixelSize(R.dimen.max_width);
       // mDstHeight = getResources().getDimensionPixelSize(R.dimen.max_height);

        mDstWidth = measuredWidth;
        mDstHeight = measuredHeight/2;
        Bitmap imagebmp = BitmapFactory.decodeResource(getResources(), photo);

        //image.setImageBitmap(getResizedBitmap(imagebmp, mDstHeight, mDstWidth));
        image.setImageBitmap(imagebmp);

       // scaleimage();
        //image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        collapsingToolbarLayout.setTitle(sch);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

       /* mAdapter = new GridAdapter(photoList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Grid photo = photoList.get(position);
                Intent i = new Intent(SchoolDetails.this, FullPhoto.class);
                i.putExtra("photo", photo.getPhotoId());
                startActivity(i);
                Toast.makeText(getApplicationContext(), photo.getPhotoId() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
*/
        fees();
        //list();
        requirements();
        //events();
        fabmenus();



    }
/*
    protected void scaleimage() {
        final long startTime = SystemClock.uptimeMillis();

        // Part 1: Decode image
        Bitmap unscaledBitmap = ScalingUtilities.decodeResource(getResources(), photo,
                mDstWidth, mDstHeight, ScalingUtilities.ScalingLogic.FIT);

        // Part 2: Scale image
        Bitmap scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, mDstWidth,
                mDstHeight, ScalingUtilities.ScalingLogic.FIT);
        unscaledBitmap.recycle();

        // Calculate memory usage and performance statistics
        final int memUsageKb = (unscaledBitmap.getRowBytes() * unscaledBitmap.getHeight()) / 1024;
        final long stopTime = SystemClock.uptimeMillis();

        // Publish results
        //mResultView.setText("Time taken: " + (stopTime - startTime)
        //        + " ms. Memory used for scaling: " + memUsageKb + " kb.");
        image.setImageBitmap(scaledBitmap);
    }
*/

    private void addevents() {
        String[] projection = {SchoolsDB.EVENTS_ID,
                SchoolsDB.EVENTS_SCH, SchoolsDB.EVENTS_NAME, SchoolsDB.EVENTS_DATE, SchoolsDB.EVENTS_DETAILS};

        Cursor cursor = getContentResolver().query(SchoolsDBProvider.EVENTS_CONTENT_URI, projection, null, null,
                null);


        if (cursor == null) {
            Toast.makeText(this, "Error occurred during query", Toast.LENGTH_LONG).show();
        } else if (cursor.getCount() < 1) {
            Toast.makeText(this, "No Events were in db", Toast.LENGTH_LONG).show();

            for (int i = 0; i < 7; i++) {

                String[] school = {"SomaAfrica Secondary School", "Anodel High School", "Infants Secondary School", "SomaAfrica Secondary School", "Kibuli High School", "SomaAfrica Secondary School", "Lubiri Secondary School"};
                String[] event = {"Visiting Day", "Open Day", "MDD", "MDD", "Music Gala", "Sports Day", "Visiting Day"};
                String[] date = {"2nd May 2016", "1st Jun 2016", "3rd June 2016", "4th July 2016", "10th July 2016", "20th July 2016", "12th Aug 2016"};
                String[] details = {"Admission on", "Admission on", "Admission off", "Admission on", "Admission on", "Admission on", "Admission off"};

                Uri newUri;

// Defines an object to contain the new values to insert
                ContentValues newValues = new ContentValues();

// Sets the values of each column and inserts the word.
                newValues.put(SchoolsDB.EVENTS_SCH, school[i]);
                newValues.put(SchoolsDB.EVENTS_NAME, event[i]);
                newValues.put(SchoolsDB.EVENTS_DATE, date[i]);
                newValues.put(SchoolsDB.EVENTS_DETAILS, details[i]);


                 getContentResolver().insert(
                        SchoolsDBProvider.EVENTS_CONTENT_URI,   // the user dictionary content URI
                        newValues                          // the values to insert
                );

            }

        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
    }


    private void fees() {
        feesAdapter = new EventsAdapter(feesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(feesAdapter);

        String[] projection = {SchoolsDB.COLUMN_ID,
                SchoolsDB.COLUMN_NAME, SchoolsDB.COLUMN_MOTTO, SchoolsDB.COLUMN_ABOUT, SchoolsDB.COLUMN_STATUS, SchoolsDB.COLUMN_LOGO, SchoolsDB.COLUMN_LEVEL,SchoolsDB.COLUMN_FEES, SchoolsDB.COLUMN_FEESNAME, SchoolsDB.COLUMN_REQUIREMENT, SchoolsDB.COLUMN_REQUIREMENTNAME};

        String selection = SchoolsDB.COLUMN_NAME + " = ?";
        //String schs = "SomaAfrica Secondary School";
        String[] args = {"SomaAfrica Secondary School"};

        Cursor cursor = getContentResolver().query(SchoolsDBProvider.CONTENT_URI, projection, selection, args,
                null);
        feesList.clear();
        if (cursor == null) {
            Toast.makeText(this, "Error occurred during query", Toast.LENGTH_LONG).show();
        } else if (cursor.getCount() < 1) {
            Toast.makeText(this, "No Events were in db", Toast.LENGTH_LONG).show();
        } else {

            if (cursor.moveToFirst()) {

                do {

                    String name = cursor.getString(cursor.getColumnIndexOrThrow(SchoolsDB.COLUMN_FEESNAME));
                    int fees = cursor.getInt(cursor.getColumnIndexOrThrow(SchoolsDB.COLUMN_FEES));
                    Events fee = new Events(name, String.valueOf(fees));
                    feesList.add(fee);

                } while (cursor.moveToNext());
                feesAdapter.notifyDataSetChanged();
            }

            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    private void requirements() {
        reqAdapter = new EventsAdapter(reqList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        eventsview.setLayoutManager(mLayoutManager);
        eventsview.setItemAnimator(new DefaultItemAnimator());
        eventsview.setHasFixedSize(true);
        eventsview.setAdapter(reqAdapter);

        String[] projection = {SchoolsDB.COLUMN_ID,
                SchoolsDB.COLUMN_NAME, SchoolsDB.COLUMN_MOTTO, SchoolsDB.COLUMN_ABOUT, SchoolsDB.COLUMN_STATUS, SchoolsDB.COLUMN_LOGO, SchoolsDB.COLUMN_LEVEL,SchoolsDB.COLUMN_FEES, SchoolsDB.COLUMN_FEESNAME, SchoolsDB.COLUMN_REQUIREMENT, SchoolsDB.COLUMN_REQUIREMENTNAME};

        String selection = SchoolsDB.COLUMN_NAME + " = ?";
        String schs = "SomaAfrica Secondary School";
        String[] args = {schs};

        Cursor cursor = getContentResolver().query(SchoolsDBProvider.CONTENT_URI, projection, selection, args,
                null);
        reqList.clear();
        if (cursor == null) {
            Toast.makeText(this, "Error occurred during query", Toast.LENGTH_LONG).show();
        } else if (cursor.getCount() < 1) {
            Toast.makeText(this, "No Events were in db", Toast.LENGTH_LONG).show();
        } else {

            if (cursor.moveToFirst()) {

                do {

                    String name = cursor.getString(cursor.getColumnIndexOrThrow(SchoolsDB.COLUMN_REQUIREMENTNAME));
                    int fees = cursor.getInt(cursor.getColumnIndexOrThrow(SchoolsDB.COLUMN_REQUIREMENT));
                    Events fee = new Events(name, Integer.toString(fees));
                    reqList.add(fee);

                } while (cursor.moveToNext());
                reqAdapter.notifyDataSetChanged();
            }

            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    private void events() {
        eventsAdapter = new EventsAdapter(eventsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        eventsview.setLayoutManager(mLayoutManager);
        eventsview.setItemAnimator(new DefaultItemAnimator());
        eventsview.setHasFixedSize(true);
        eventsview.setAdapter(eventsAdapter);

        String[] projection = {SchoolsDB.EVENTS_ID,
                SchoolsDB.EVENTS_SCH, SchoolsDB.EVENTS_NAME, SchoolsDB.EVENTS_DATE, SchoolsDB.EVENTS_DETAILS};

        String selection = SchoolsDB.EVENTS_SCH + " = ?";
        String schs = "SomaAfrica Secondary School";
        String[] args = {schs};

        Cursor cursor = getContentResolver().query(SchoolsDBProvider.EVENTS_CONTENT_URI, projection, selection, args,
                null);
        eventsList.clear();
        if (cursor == null) {
            Toast.makeText(this, "Error occurred during query", Toast.LENGTH_LONG).show();
        } else if (cursor.getCount() < 1) {
                Toast.makeText(this, "No Events were in db", Toast.LENGTH_LONG).show();
            } else {

                if (cursor.moveToFirst()) {

                    do {

                        String name = cursor.getString(cursor.getColumnIndexOrThrow(SchoolsDB.EVENTS_NAME));
                        String date = cursor.getString(cursor.getColumnIndexOrThrow(SchoolsDB.EVENTS_DATE));
                        Events event = new Events(name, date);
                        eventsList.add(event);

                    } while (cursor.moveToNext());
                    eventsAdapter.notifyDataSetChanged();
                }

                if (!cursor.isClosed()) {
                    cursor.close();
                }
            }
        }



    private void fabmenus() {

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab_1);
        final FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab_2);
        final FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.fab_3);

        //Animations
        final Animation show_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        final Animation hide_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
        final Animation show_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);
        final Animation hide_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);
        final Animation show_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);
        final Animation hide_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!fab1.isClickable()) {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
                    layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
                    layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
                    fab1.setLayoutParams(layoutParams);
                    fab1.startAnimation(show_fab_1);
                    //fab1.setClickable(true);
                    fab1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
                            layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
                            layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
                            fab1.setLayoutParams(layoutParams);
                            fab1.startAnimation(hide_fab_1);
                            fab1.setClickable(false);

                            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
                            layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);
                            layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.5);
                            fab2.setLayoutParams(layoutParams2);
                            fab2.startAnimation(hide_fab_2);
                            fab2.setClickable(false);

                            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
                            layoutParams3.rightMargin -= (int) (fab3.getWidth() * 0.25);
                            layoutParams3.bottomMargin -= (int) (fab3.getHeight() * 1.7);
                            fab3.setLayoutParams(layoutParams3);
                            fab3.startAnimation(hide_fab_3);
                            fab3.setClickable(false);

                            Intent i = new Intent(SchoolDetails.this, Login.class);
                            startActivity(i);
                                        }
                                    });

                    FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
                    layoutParams2.rightMargin += (int) (fab2.getWidth() * 1.5);
                    layoutParams2.bottomMargin += (int) (fab2.getHeight() * 1.5);
                    fab2.setLayoutParams(layoutParams2);
                    fab2.startAnimation(show_fab_2);
                    fab2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
                            layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
                            layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
                            fab1.setLayoutParams(layoutParams);
                            fab1.startAnimation(hide_fab_1);
                            fab1.setClickable(false);

                            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
                            layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);
                            layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.5);
                            fab2.setLayoutParams(layoutParams2);
                            fab2.startAnimation(hide_fab_2);
                            fab2.setClickable(false);

                            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
                            layoutParams3.rightMargin -= (int) (fab3.getWidth() * 0.25);
                            layoutParams3.bottomMargin -= (int) (fab3.getHeight() * 1.7);
                            fab3.setLayoutParams(layoutParams3);
                            fab3.startAnimation(hide_fab_3);
                            fab3.setClickable(false);

                            //Intent i = new Intent(SchoolDetails.this, Login.class);
                            //startActivity(i);
                        }
                    });

                    FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
                    layoutParams3.rightMargin += (int) (fab3.getWidth() * 0.25);
                    layoutParams3.bottomMargin += (int) (fab3.getHeight() * 1.7);
                    fab3.setLayoutParams(layoutParams3);
                    fab3.startAnimation(show_fab_3);
                    fab3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
                            layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
                            layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
                            fab1.setLayoutParams(layoutParams);
                            fab1.startAnimation(hide_fab_1);
                            fab1.setClickable(false);

                            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
                            layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);
                            layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.5);
                            fab2.setLayoutParams(layoutParams2);
                            fab2.startAnimation(hide_fab_2);
                            fab2.setClickable(false);

                            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
                            layoutParams3.rightMargin -= (int) (fab3.getWidth() * 0.25);
                            layoutParams3.bottomMargin -= (int) (fab3.getHeight() * 1.7);
                            fab3.setLayoutParams(layoutParams3);
                            fab3.startAnimation(hide_fab_3);
                            fab3.setClickable(false);

                            //Intent i = new Intent(SchoolDetails.this, Login.class);
                           // startActivity(i);
                        }
                    });
                } else {
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
                    layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
                    layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
                    fab1.setLayoutParams(layoutParams);
                    fab1.startAnimation(hide_fab_1);
                    fab1.setClickable(false);

                    FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
                    layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);
                    layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.5);
                    fab2.setLayoutParams(layoutParams2);
                    fab2.startAnimation(hide_fab_2);
                    fab2.setClickable(false);

                    FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
                    layoutParams3.rightMargin -= (int) (fab3.getWidth() * 0.25);
                    layoutParams3.bottomMargin -= (int) (fab3.getHeight() * 1.7);
                    fab3.setLayoutParams(layoutParams3);
                    fab3.startAnimation(hide_fab_3);
                    fab3.setClickable(false);
                }

            }
        });


    }

    private void list() {

        String[] projection = {SchoolsDB.COLUMN_ID,
                SchoolsDB.COLUMN_NAME, SchoolsDB.COLUMN_MOTTO, SchoolsDB.COLUMN_ABOUT, SchoolsDB.COLUMN_STATUS, SchoolsDB.COLUMN_LOGO, SchoolsDB.COLUMN_LEVEL};
        String selection = SchoolsDB.COLUMN_LEVEL + " = ?";
        String[] args = {"H"};
        Cursor cursor = getContentResolver().query(SchoolsDBProvider.CONTENT_URI, projection, selection, args,
                null);
        photoList.clear();

        if (cursor.moveToFirst()) {

            do {

                String name = cursor.getString(cursor.getColumnIndexOrThrow(SchoolsDB.COLUMN_NAME));
                // String motto = cursor.getString(cursor.getColumnIndexOrThrow(SchoolsDB.COLUMN_MOTTO));
                int logo = cursor.getInt(cursor.getColumnIndexOrThrow(SchoolsDB.COLUMN_LOGO));
                Grid photo = new Grid(logo);
                photoList.add(photo);

            } while (cursor.moveToNext());
            mAdapter.notifyDataSetChanged();
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
    }


    private void addsch() {
        String[] projection = {SchoolsDB.COLUMN_ID,
                SchoolsDB.COLUMN_NAME, SchoolsDB.COLUMN_MOTTO, SchoolsDB.COLUMN_ABOUT, SchoolsDB.COLUMN_STATUS, SchoolsDB.COLUMN_LOGO, SchoolsDB.COLUMN_LEVEL,SchoolsDB.COLUMN_FEES, SchoolsDB.COLUMN_FEESNAME, SchoolsDB.COLUMN_REQUIREMENT, SchoolsDB.COLUMN_REQUIREMENTNAME};
        String selection = SchoolsDB.COLUMN_LEVEL + " = ?";
        String[] args = {"H"};
        Cursor cursor = getContentResolver().query(SchoolsDBProvider.CONTENT_URI, projection, selection, args,
                null);


        if (cursor == null) {
            Toast.makeText(this, "Error occurred during query", Toast.LENGTH_LONG).show();
        } else if (cursor.getCount() < 1) {
            Toast.makeText(this, "No schools were in db", Toast.LENGTH_LONG).show();

            for (int i = 0; i < 7; i++) {

                String[] school = {"SomaAfrica Secondary School", "Anodel High School", "SomaAfrica Secondary School", "Kireka Secondary School", "SomaAfrica Secondary School", "Kampala High School", "Kampala Parents School"};
                String[] motto = {"Exploring the limits of technology", "Education our pride", "Strive for success", "Working for excellence", "Strive for success", "Invest for the future", "Investing in science and technology"};
                String[] about = {"2nd", "1st", "3rd", "4th", "10th", "20th", "12th"};
                String[] status = {"Admission on", "Admission on", "Admission off", "Admission on", "Admission on", "Admission on", "Admission off"};
                int[] logo = {R.mipmap.somaafrica, R.mipmap.kasawoss, R.mipmap.somaafrica, R.mipmap.kitanteps, R.mipmap.somaafrica, R.mipmap.namirembeparents, R.mipmap.somaafrica};
                String[] level = {"H", "H", "H", "H", "H", "H", "H"};
                int[] fees = {20000, 30000, 25000,25000,20000,15000, 30000};
                String[] feesname = {"Application Fee", "Application Fee", "Admission Fee", "Application Fee", "Tution Fee", "Application Fee", "Uniform fee"};
                int[] req = {20000, 30000, 25000,25000,20000,15000, 30000};
                String[] reqname = {"Math Text book", "Physics seminar", "Physics seminar", "Physics seminar", "Science club subscription", "Physics seminar", "MDD"};
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
                newValues.put(SchoolsDB.COLUMN_FEES, fees[i]);
                newValues.put(SchoolsDB.COLUMN_FEESNAME, feesname[i]);
                newValues.put(SchoolsDB.COLUMN_REQUIREMENT, req[i]);
                newValues.put(SchoolsDB.COLUMN_REQUIREMENTNAME, reqname[i]);


                 getContentResolver().insert(
                        SchoolsDBProvider.CONTENT_URI,   // the user dictionary content URI
                        newValues                          // the values to insert
                );

            }

        }
        if (!cursor.isClosed()) {
            cursor.close();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private SchoolDetails.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final SchoolDetails.ClickListener clickListener) {
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

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();

        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;

// create a matrix for the manipulation

        Matrix matrix = new Matrix();

// resize the bit map

        matrix.postScale(scaleWidth, scaleHeight);

// recreate the new Bitmap

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;

    }

}
