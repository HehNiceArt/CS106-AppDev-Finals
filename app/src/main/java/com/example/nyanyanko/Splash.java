package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Handler;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            public void run(){
                Intent intent = new Intent(Splash.this, Gameplay.class);
                startActivity(intent);
                finish();
            }
        }, 10);
    }
}