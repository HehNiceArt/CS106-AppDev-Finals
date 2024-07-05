package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button next_Activity_button = (Button) findViewById(R.id.first_activity_button);

        next_Activity_button.setOnClickListener(v ->
        {
            Intent intent = new Intent(MainActivity.this, Gameplay.class);
            startActivity(intent);
        });
    }
}