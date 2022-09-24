package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.whatsappclone.Signup_Activity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP )
        {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }

        Thread thread=new Thread()
        {

            public  void run(){

                try {
                    sleep(2000);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    Intent intent= new Intent(SplashScreen.this, Signup_Activity.class);
                    startActivity(intent);
                    finish();
                }


            }


        }; thread.start();




    }
}