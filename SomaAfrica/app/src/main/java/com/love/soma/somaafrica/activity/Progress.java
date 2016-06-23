package com.love.soma.somaafrica.activity;

/**
 * Created by Nelson.Kanyali on 22/06/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnGroupExpandListener;


import com.love.soma.somaafrica.R;
import com.love.soma.somaafrica.adapter.MyExpandableListAdapter;
import com.love.soma.somaafrica.adapter.Schools;
import com.love.soma.somaafrica.adapter.SchoolsAdapter;
import com.love.soma.somaafrica.provider.SchoolsDBProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nelson on 24/03/2015.
 */
public class Progress extends Activity implements View.OnClickListener {


    HashMap<String, List<String>> reg_category;
    HashMap<String, List<String>> sch_details = new HashMap<String, List<String>>();
    ExpandableListView exp_list;
    List<String> sch_list;
    MyExpandableListAdapter adapter;
    String username;
    String[] selectionArgs = null;
    SharedPreferences pref;
    Button back;
    private List<Schools> schoolList = new ArrayList<>();
    private SchoolsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_main);
        exp_list = (ExpandableListView)findViewById(R.id.expandableListView);
        back = (Button)findViewById(R.id.bback);
        back.setOnClickListener(this);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        sch_list = new ArrayList<String>(sch_details.keySet());
        adapter = new MyExpandableListAdapter(this, sch_details, sch_list);
        exp_list.setAdapter(adapter);

        exp_list.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getBaseContext(), sch_list.get(groupPosition) + "is expanded", Toast.LENGTH_LONG).show();
            }
        });

        exp_list.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getBaseContext(), sch_list.get(groupPosition) + "is collapsed", Toast.LENGTH_LONG).show();
            }
        });


        exp_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Toast.makeText(getBaseContext(), reg_category.get(sch_list.get(groupPosition)).get(childPosition) + "from category" + sch_list.get(groupPosition) + " is selected ", Toast.LENGTH_LONG).show();
                return false;
            }
        });


    }

    private void expandablelist() {


        setContentView(R.layout.progress_main);



    }

    private void list() {

        String[] projection = {SchoolsDB.COLUMN_ID,
                SchoolsDB.COLUMN_NAME, SchoolsDB.COLUMN_MOTTO, SchoolsDB.COLUMN_ABOUT, SchoolsDB.COLUMN_STATUS, SchoolsDB.COLUMN_LOGO, SchoolsDB.COLUMN_LEVEL};
        String selection = SchoolsDB.COLUMN_LEVEL + " = ?";
        String[] args = {"P"};
        Cursor cursor = this.getContentResolver().query(SchoolsDBProvider.CONTENT_URI, projection, selection, args,
                null);
        schoolList.clear();

        if (cursor.moveToFirst()) {

            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(SchoolsDB.COLUMN_NAME));
                String motto = cursor.getString(cursor.getColumnIndexOrThrow(SchoolsDB.COLUMN_MOTTO));
               String status = cursor.getString(cursor.getColumnIndexOrThrow(SchoolsDB.COLUMN_STATUS));
                //Schools movie = new Schools(name, motto, logo);
                //schoolList.add(movie);

                List<String> schs = new ArrayList<String>();
                schs.add(motto);
                schs.add(status);

                sch_details.put(name, schs);


            } while (cursor.moveToNext());
            mAdapter.notifyDataSetChanged();
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        if (item.getItemId() == R.id.home) {
            Intent i = new Intent(Progress.this, MainActivity.class);
            startActivity(i);
        }

        return super.onMenuItemSelected(featureId, item);
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putString("username", username);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        if (pref.contains("username")) {
            username = (pref.getString("username",""));
        }
    }


    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putString("username", username);
        editor.commit();



    }


    @Override
    protected void onRestart() {
        super.onRestart();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        if (pref.contains("username")) {
            username = (pref.getString("username",""));
        }
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(Progress.this, MainActivity.class);
        startActivity(i);
    }
}