package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import android.os.Handler;
import java.util.logging.LogRecord;

public class Gameplay extends AppCompatActivity {
    private GameView gameView;
    private Button openInventoryBTN;
    private int playerCoins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("playerCoins")) {
            playerCoins = intent.getIntExtra("playerCoins", 20); // Default value is 20
        } else {
            playerCoins = 20; // Default value if Intent does not contain playerCoins
        }
        FrameLayout frameLayout = findViewById(R.id.act_gameplay);
        gameView = new GameView(this);
        frameLayout.addView(gameView, 0);

        openInventoryBTN = findViewById(R.id.inventoryBTN);
        openInventoryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.setPause(true);
                Intent intent = new Intent(Gameplay.this, InventoryActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        Button openToyBTN = findViewById(R.id.toyBTN);
        openToyBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               openToy();
            }
        });
        Button menuBTN = findViewById(R.id.menuBTN);
        menuBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenu();
            }
        });
        Button openShopBTN = findViewById(R.id.shopBTN);
        openShopBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShop();
            }
        });
    }
    private void openMenu(){
        gameView.setPause(true);
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
    private void openToy(){
        gameView.setPause(true);
        Intent intent = new Intent(this, ToyActivity.class);
        startActivity(intent);
    }

    private void openShop() {
        gameView.setPause(true);
        Intent intent = new Intent(this, ShopActivity.class);
        intent.putExtra("playerCoins", playerCoins);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.setPause(false);
        gameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }
}