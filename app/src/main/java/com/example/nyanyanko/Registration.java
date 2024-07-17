package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Button registerButton = (Button) findViewById(R.id.registerBTN);
        EditText petText = (EditText) findViewById(R.id.petName);

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
}