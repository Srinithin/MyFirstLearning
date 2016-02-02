package com.example.sony.designui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Sony on 1/29/2016.
 */
public class SplashActivity extends Activity {
    private static long SLEEP_TIME = 3;	// Sleep for some time


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar.

        setContentView(R.layout.activity_splash);
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(SLEEP_TIME*1000);
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
