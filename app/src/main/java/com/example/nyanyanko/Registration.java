package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Button registerButton = (Button) findViewById(R.id.registerBTN);
        EditText petText = (EditText) findViewById(R.id.petName);

        setBG();
        registerButton.setOnClickListener(v -> {
            String petName = petText.getText().toString();
            if(TextUtils.isEmpty(petName)){
                Toast.makeText(Registration.this, "Please enter a pet name!", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(Registration.this, Splash.class);
                intent.putExtra("PET_NAME", petName);
                startActivity(intent);
            }

        });
    }

    private void setBG(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth= displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.registration_bg);
        background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, false);

        ImageView backgroundImageView = findViewById(R.id.imageView);
        backgroundImageView.setBackground(new BitmapDrawable(getResources(), background));
    }
}