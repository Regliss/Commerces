package com.example.maps;

import android.content.Intent;
import android.os.Bundle;

import com.example.maps.AppActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppActivity {
    //declaration
    private Timer myTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO : lancement de homeActivity
                Intent myIntent = new Intent(com.example.maps.MainActivity.this, InformationActivity.class);
                startActivity(myIntent);
                finish();
            }
        }, 2000);
    }
}