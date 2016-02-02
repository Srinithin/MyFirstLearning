package com.example.sony.designui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by Sony on 1/25/2016.
 */
public class LoginActivity extends Activity {

    private TextView mTextDetails;
    private CallbackManager mcallbackmanager;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        mTextDetails = (TextView) findViewById(R.id.textView);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mcallbackmanager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        //loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(mcallbackmanager, mcallback);
    }

    private FacebookCallback<LoginResult> mcallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            if (profile != null) {
                Log.d("check", "profile:" + profile.getName());
                Context context = LoginActivity.this;
                SharedPreferences sharedpref = context.getSharedPreferences(getString(R.string.sharedPref_details),Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpref.edit();
                editor.putString("Facebook_UserName",profile.getName());
                editor.putString("Facebook_ProfilePic", profile.getProfilePictureUri(100, 100).toString());
                editor.commit();
                Toast.makeText(getApplicationContext(),"Logged in successfully",Toast.LENGTH_SHORT).show();
            }
            finish();
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };


    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        return super.onCreateView(parent, name, context, attrs);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mcallbackmanager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
}
