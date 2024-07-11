package com.example.nyanyanko.Toy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.nyanyanko.Gameplay;
import com.example.nyanyanko.NyankoAI;
import com.example.nyanyanko.NyankoManager;
import com.example.nyanyanko.R;
import com.example.nyanyanko.ShopAct.CoinManager;

import java.util.List;

public class ToyActivity extends AppCompatActivity {

    Button backBTN;
    private GridView toyGridView;
    private ToyAdapter adapter;
    private NyankoAI nyankoAI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toy);

        toyGridView = findViewById(R.id.toyGrid);
        nyankoAI = NyankoManager.getInstance(this);

        CoinManager.getInstance().startCoinIncrement();
        Log.d("ToyActivity", "Starting coin increment in ToyActivity");

        updateToy();
        backBTN = findViewById(R.id.backBTN);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }
    private void goBack(){
        Intent intent = new Intent(ToyActivity.this, Gameplay.class);
        startActivity(intent);
    }
    private void updateToy(){
        List<ToyItem> toyItems = nyankoAI.getToy();
        adapter = new ToyAdapter(this, toyItems);
        toyGridView.setAdapter(adapter);
    }
    @Override
    protected void onPause(){
        super.onPause();
        CoinManager.getInstance().stopCoinIncrement();
        Log.d("ToyActivity", "Pausing coin increment in ToyActivity");
    }
}