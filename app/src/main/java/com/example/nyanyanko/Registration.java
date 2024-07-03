package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Button registerButton = (Button) findViewById(R.id.registerBTN);

        registerButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(Registration.this, Splash.class);
            startActivity(intent);
        });
    }
}