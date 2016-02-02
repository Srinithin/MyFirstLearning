package com.example.sony.designui;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sony on 2/2/2016.
 */
public class OAuthFragment extends DialogFragment implements View.OnClickListener {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private LoginManager loginManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_login, null);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        loginButton = (LoginButton) view.findViewById(R.id.facebook_login_button);
        loginButton.setOnClickListener(this);
        callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
        loginManager.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("CHANDRA", "logged in");
                    }

                    @Override
                    public void onCancel() {
                        Log.d("CHANDRA", "cancelled");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("CHANDRA", "error:" + exception.getMessage());
                    }
                });
        setCancelable(true);
        return view;
    }


    @Override
    public void onClick(View v) {
        ArrayList<String> permissions = new ArrayList<>();
        permissions.add("user_status");
//        loginManager.setDefaultAudience(loginManager.getDefaultAudience());
//        loginManager.setLoginBehavior(loginManager.getLoginBehavior());
//        loginManager.logOut();
        loginManager.logInWithReadPermissions(this, permissions);
        Log.d("CHANDRA", "Hello onCLick");
    }

    private FacebookCallback<LoginResult> mcallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            if (profile != null) {
                Log.d("check", "profile:" + profile.getName());
                Context context = getActivity();
                SharedPreferences sharedpref = context.getSharedPreferences(getString(R.string.sharedPref_details), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpref.edit();
                editor.putString("Facebook_UserName", profile.getName());
                editor.putString("Facebook_ProfilePic", profile.getProfilePictureUri(100, 100).toString());
                editor.commit();
                Toast.makeText(getActivity().getApplicationContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };
}
