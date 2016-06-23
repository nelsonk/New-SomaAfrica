package com.love.soma.somaafrica.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;

import com.facebook.share.widget.ShareDialog;
import com.love.soma.somaafrica.R;

import com.facebook.FacebookSdk;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    int position;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;
    private ImageView imageView;
    private int[] tabIcons = {
            R.drawable.profile,
            R.drawable.ic_menu_camera,
            R.drawable.ic_menu_gallery
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){

            public void onTabSelected(TabLayout.Tab tab){

               // position = tab.getPosition();
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }

        });







        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      /*  final FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab_1);
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
                    fab1.setClickable(true);

                    FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
                    layoutParams2.rightMargin += (int) (fab2.getWidth() * 1.5);
                    layoutParams2.bottomMargin += (int) (fab2.getHeight() * 1.5);
                    fab2.setLayoutParams(layoutParams2);
                    fab2.startAnimation(show_fab_2);
                    fab2.setClickable(true);

                    FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
                    layoutParams3.rightMargin += (int) (fab3.getWidth() * 0.25);
                    layoutParams3.bottomMargin += (int) (fab3.getHeight() * 1.7);
                    fab3.setLayoutParams(layoutParams3);
                    fab3.startAnimation(show_fab_3);
                    fab3.setClickable(true);
                } else{
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

*/


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //nackbar snackbar = Snackbar.make(view, "Exploring the limits of technology", Snackbar.LENGTH_LONG)
                Snackbar.make(view, "Explore the limits of technology with SomaAfrica", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar snackbar1 = Snackbar.make(view, "Happy to have you here", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                            }
                        }).show();
                       // .setActionTextColor(Color.RED);

                // Changing action button text color
               // View sbView = snackbar.getView();
                //TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
               // textView.setTextColor(Color.RED);
                //snackbar.show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        bitmap=getThumbnail("profilepic.png");
        if (bitmap != null){

            View header = navigationView.getHeaderView(0);
            CircleImageView imageView = (CircleImageView) header.findViewById(R.id.profile_image);
                imageView.setImageBitmap(bitmap);

    }


    }


    private void setupTabIcons() {

        TextView tabO = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabO.setText("NURSERY");
        tabO.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.nursery, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabO);

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("PRIMARY");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.primary, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("SECONDARY");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.secondary, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("TERTIARY");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.university, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabThree);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Nursery(), "ZERO");
        adapter.addFrag(new Primary(), "ONE");
        adapter.addFrag(new Highskul(), "TWO");
        adapter.addFrag(new Tertiary(), "THREE");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {

            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        for(int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            spanString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spanString.length(), 0); //fix the color to white
            item.setTitle(spanString);
        }

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                switch (position)
                {
                    case 0:
                        Intent intent = new Intent("search_nursery");
                        intent.putExtra("message", query);
                        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
                        break;
                    case 1:
                        Intent intentp = new Intent("search_primary");
                        intentp.putExtra("message", query);
                        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intentp);
                        break;
                    case 2:
                        Intent intenth = new Intent("search_highskul");
                        intenth.putExtra("message", query);
                        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intenth);
                        break;
                    case 3:
                        Intent intentt = new Intent("search_tertiary");
                        intentt.putExtra("message", query);
                        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intentt);
                        break;
                }
                //FragmentManager manager = getSupportFragmentManager();
                //Fragment fragment = manager.findFragmentById(R.id.my_fragment);
                //fragment.highskulsearch();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                switch (position)
                {
                    case 0:
                        Intent intent = new Intent("search_nursery");
                        intent.putExtra("message", newText);
                        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
                        break;
                    case 1:
                        Intent intentp = new Intent("search_primary");
                        intentp.putExtra("message", newText);
                        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intentp);
                        break;
                    case 2:
                        Intent intenth = new Intent("search_highskul");
                        intenth.putExtra("message", newText);
                        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intenth);
                        break;
                    case 3:
                        Intent intentt = new Intent("search_tertiary");
                        intentt.putExtra("message", newText);
                        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intentt);
                        break;
                }

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            return true;
       }


        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            pickImage(imageView);

        } else if (id == R.id.nav_gallery) {
            Toast.makeText(getApplicationContext(), "Yet to be setup", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_slideshow) {
            //Intent i = new Intent(MainActivity.this, Progress.class);
            //startActivity(i);
            Toast.makeText(getApplicationContext(), "Yet to be setup", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_manage) {
            shareonfb();

        } else if (id == R.id.nav_share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cool online student admission system, www.somaafrica.com");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

            //shareonfb();

        } else if (id == R.id.nav_send) {
            //Intent i = new Intent(MainActivity.this, Contact_us.class);
            //startActivity(i);

            Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Hi SomaAfrica Team");
            intent.putExtra(Intent.EXTRA_TEXT, "Enter your message here");
            intent.setData(Uri.parse("mailto:somaafricaltd@gmail.com")); // or just "mailto:" for blank
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void shareonfb() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("SomaAfrica")
                    .setContentDescription(
                            "Apply to any schools in africa from the comfort of wherever you are")
                    .setContentUrl(Uri.parse("http://somaafrica.com"))
                    .build();

            shareDialog.show(linkContent);
        }
    }

    public void pickImage(View View) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
            try {
                // We need to recyle unused bitmaps
                if (bitmap != null) {
                    bitmap.recycle();
                }
                InputStream stream = getContentResolver().openInputStream(
                        data.getData());
                bitmap = BitmapFactory.decodeStream(stream);
                stream.close();
                saveImageToInternalStorage(bitmap);
                bitmap=getThumbnail("profilepic.png");

                CircleImageView imageView = (CircleImageView) findViewById(R.id.profile_image);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public boolean saveImageToInternalStorage(Bitmap image) {

        try {
// Use the compress method on the Bitmap object to write image to
// the OutputStream
            FileOutputStream fos = this.openFileOutput("profilepic.png", Context.MODE_WORLD_READABLE);

// Writing the bitmap to the output stream
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            return true;
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
            return false;
        }
    }

    public Bitmap getThumbnail(String filename) {


        Bitmap thumbnail = null;

// If no file on external storage, look in internal storage
        if (thumbnail == null) {
            try {

                File filePath = this.getFileStreamPath(filename);
                FileInputStream fi = new FileInputStream(filePath);
                thumbnail = BitmapFactory.decodeStream(fi);
            } catch (Exception ex) {
                Log.e("image retrieval:", ex.getMessage());
            }
        }
        return thumbnail;
    }

}
