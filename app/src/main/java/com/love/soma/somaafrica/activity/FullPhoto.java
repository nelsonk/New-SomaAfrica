package com.love.soma.somaafrica.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Gallery;
import android.widget.ImageView;

import com.love.soma.somaafrica.R;
import com.love.soma.somaafrica.adapter.GalleryImageAdapter;
import com.love.soma.somaafrica.adapter.Grid;
import com.love.soma.somaafrica.adapter.GridAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neli'e on 26/03/2016.
 */
public class FullPhoto extends Activity {
    private List<Grid> photoList = new ArrayList<>();
    GridAdapter imageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullphoto);
        Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        gallery.setSpacing(1);
       // gallery.setAdapter(new GalleryImageAdapter(this));

        // get intent data
        Intent i = getIntent();
        // Selected image id
        int position = i.getExtras().getInt("photo");
        imageView.setImageResource(position);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
