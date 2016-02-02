package com.example.sony.designui;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private CallbackManager mcallbackmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

      /*  SharedPreferences pref = getSharedPreferences(getString(R.string.sharedPref_details), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("Facebook_UserName");
        editor.remove("Facebook_ProfilePic");
        editor.clear();
        editor.commit();*/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FacebookSdk.sdkInitialize(getApplicationContext());


        NavigationView navigationHeader = (NavigationView) findViewById(R.id.nav_header);
        View headerview = navigationView.getHeaderView(0);
        LinearLayout header = (LinearLayout) headerview.findViewById(R.id.nav_header);
        header.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closeDrawer();
//                openLoginActivity();
                openLoginDialog();
            }
        });
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        int id = item.getItemId();
        switch (id) {
            case R.id.nav_recentlyViewed:
                return true;

            case R.id.nav_enableDisable:
                return true;

            case R.id.nav_interestPoints:
                return true;

            case R.id.nav_notificattions:
                return true;

            case R.id.nav_feedback:
                return true;

            case R.id.nav_aboutUs:
                return true;
        }


        return true;
    }

    private void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void openLoginDialog() {
        FragmentManager manager = getFragmentManager();
        OAuthFragment loginDialog = new OAuthFragment();
        loginDialog.show(manager,"dialog");
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();

        TextView tv = (TextView) findViewById(R.id.nav_textView);
        ImageView iv = (ImageView) findViewById(R.id.imageView);


        //Context context = MainActivity.this;
        SharedPreferences profileData = getSharedPreferences(getString(R.string.sharedPref_details), Context.MODE_PRIVATE);
        String Username = profileData.getString("Facebook_UserName", null);
        String Profile_pic = profileData.getString("Facebook_ProfilePic", null);
        //byte[] imageuri = Profile_pic.getBytes();
        //Log.d("Check :",Profile_pic);

        if (Username != null && Profile_pic != null) {
            //check whether it is logged in
            // set the profile pic and username to the header layout.
            Toast.makeText(getApplicationContext(), "username is: " + Username, Toast.LENGTH_SHORT).show();
            try {
                tv.setText(Username);
                //iv.setImageURI(Uri.parse(Profile_pic));
                Picasso.with(this)
                        .load(Profile_pic)
                        .resize(170, 170)
                        .centerCrop()
                        .into(iv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
/*
    //Facebook Login Callbacks
    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.d("SRINITHIN", "Login Success:" + loginResult.getAccessToken().getUserId());
    }

    @Override
    public void onCancel() {
        Log.d("SRINITHIN", "Login Canceled");
    }

    @Override
    public void onError(FacebookException error) {
        Log.d("SRINITHIN", "Login Error:" + error.getMessage());
    }*/
}
