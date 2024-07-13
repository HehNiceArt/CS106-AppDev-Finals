package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button backBTN;
    String petName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        petName = getIntent().getStringExtra("PET_NAME");
        backBTN = findViewById(R.id.backBTN);
        Log.d("MenuActivity", "at menuActivity");
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }
    private void goBack(){
        Intent intent = new Intent(MenuActivity.this, Gameplay.class);
        intent.putExtra("PET_NAME", petName);
        startActivity(intent);
    }
}