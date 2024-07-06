package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ShopActivity extends AppCompatActivity {
    private Shop shop;
    private NyankoAI nyankoAI;
    private int playerCoins = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        nyankoAI = NyankoManager.getInstance(this);

        shop = new Shop();
        List<ShopItem> shopItems = shop.getShopItems();

        ListView shopListView = findViewById(R.id.shop_list);
        ArrayAdapter<ShopItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shop.getShopItems());
        shopListView.setAdapter(adapter);

        shopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShopItem item = (ShopItem) parent.getItemAtPosition(position);
                if(playerCoins >= item.getCost()){
                    playerCoins -= item.getCost();

                    InventoryItem inventoryItem = new InventoryItem(item.getName(), 1);
                    nyankoAI.addItemToInventory(inventoryItem);

                    Log.d("Shop", "item purchased! " + item.getName());
                    Toast.makeText(ShopActivity.this, "Purchased: " + item.getName(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ShopActivity.this, "Not enough coins!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button backBTN = findViewById(R.id.backBTN);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ShopActivity.this, Gameplay.class);
                startActivity(intent);
                finish();
            }

        });
    }
}