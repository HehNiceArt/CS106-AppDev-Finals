package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class Gameplay extends AppCompatActivity {
    private GameView gameView;
    private Button toyBTN, shopBTN, inventoryBTN, menuBTN;

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
    protected void onPause()
    {
        super.onPause();
        gameView.pause();
    }

}