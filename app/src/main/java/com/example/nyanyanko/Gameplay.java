package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import android.os.Handler;
import java.util.logging.LogRecord;

public class Gameplay extends AppCompatActivity {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        FrameLayout frameLayout = findViewById(R.id.act_gameplay);
        gameView = new GameView(this);
        frameLayout.addView(gameView, 0);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        gameView.resume();
    }
    @Override
    protected void onPause(){
        super.onPause();
        gameView.pause();
    }
}