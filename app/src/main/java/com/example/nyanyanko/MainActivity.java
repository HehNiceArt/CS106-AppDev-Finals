package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent musicIntent = new Intent(this, MusicService.class);
        startService(musicIntent);
        Button next_Activity_button = (Button) findViewById(R.id.first_activity_button);
        Animation blinkAnim = AnimationUtils.loadAnimation(this, R.anim.blink);
        next_Activity_button.startAnimation(blinkAnim);

        setBG();
        next_Activity_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Registration.class);
            startActivity(intent);
        });
    }
    private void setBG(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth= displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.startscreen_bg);
        background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, false);

        ImageView backgroundImageView = findViewById(R.id.imageView);
        backgroundImageView.setBackground(new BitmapDrawable(getResources(), background));
    }
}