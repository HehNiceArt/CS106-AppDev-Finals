package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button next_Activity_button = (Button) findViewById(R.id.first_activity_button);
        Animation blinkAnim = AnimationUtils.loadAnimation(this, R.anim.blink);
        next_Activity_button.startAnimation(blinkAnim);

        next_Activity_button.setOnClickListener(v ->
        {
            Intent intent = new Intent(MainActivity.this, Registration.class);
            startActivity(intent);
        });
    }
}