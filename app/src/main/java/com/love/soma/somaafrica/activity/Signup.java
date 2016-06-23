package com.love.soma.somaafrica.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.love.soma.somaafrica.R;

/**
 * Created by Nelson.Kanyali on 17/05/2016.
 */
public class Signup extends AppCompatActivity implements View.OnClickListener {
    Button enter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        enter = (Button)findViewById(R.id.bssubmit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar.setTitle("Sign UP");
        getSupportActionBar().setTitle("SIGN UP");
        enter.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(Signup.this, Login.class);
        startActivity(i);
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
}
