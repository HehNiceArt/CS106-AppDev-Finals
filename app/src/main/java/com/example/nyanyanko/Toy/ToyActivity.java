package com.example.nyanyanko.Toy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.nyanyanko.Gameplay;
import com.example.nyanyanko.NyankoAI;
import com.example.nyanyanko.NyankoManager;
import com.example.nyanyanko.R;
import com.example.nyanyanko.ShopAct.CoinManager;

import java.util.List;

public class ToyActivity extends AppCompatActivity {

    Button backBTN;
    private ListView toyListView;
    private ToyAdapter adapter;
    private NyankoAI nyankoAI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toy);

        toyListView = findViewById(R.id.toyList);
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
        toyListView.setAdapter(adapter);
    }
    @Override
    protected void onPause(){
        super.onPause();
        CoinManager.getInstance().stopCoinIncrement();
        Log.d("ToyActivity", "Pausing coin increment in ToyActivity");
    }
}