package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Gameplay extends AppCompatActivity {

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
}