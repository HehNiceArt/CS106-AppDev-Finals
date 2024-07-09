package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private int playerCoins = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        nyankoAI = NyankoManager.getInstance(this);

        shop = new Shop();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("playerCoins")) {
            playerCoins = intent.getIntExtra("playerCoins", 20);
        }
        ListView shopListView = findViewById(R.id.shop_list);
        TextView coins = findViewById(R.id.coinID);
        coins.setText(String.valueOf(playerCoins));
        ArrayAdapter<ShopItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shop.getShopItems());
        shopListView.setAdapter(adapter);

        shopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShopItem item = (ShopItem) parent.getItemAtPosition(position);
                if(playerCoins >= item.getCost()){
                    playerCoins -= item.getCost();
                    int hp = item.getHP();
                    int hunger = item.getHunger();

                    coins.setText(String.valueOf(playerCoins));

                    Bitmap itemIcon = getItemIcon(item.getName());
                    InventoryItem inventoryItem = new InventoryItem(item.getName(), 1, hp, hunger, itemIcon);
                    ToyItem toyItem = new ToyItem(item.getName(), 1, itemIcon);

                    if(item.getName().equals("Mouse Plush") || item.getName().equals("Feather")){
                        nyankoAI.addItemToToy(toyItem);
                    }else {
                        nyankoAI.addItemToInventory(inventoryItem);
                    }

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
                intent.putExtra("playerCoins", playerCoins);
                startActivity(intent);
                finish();
            }

        });
    }
    private Bitmap getItemIcon(String itemName) {
        int resId = 0;
        switch (itemName) {
            case "Dry Food":
                resId = R.drawable.dry_food_icon;
                break;
            case "Wet Food":
                resId = R.drawable.wet_food_icon;
                break;
            case "Tuna":
                //resId = R.drawable.tunaFood;
                break;
            case "Shark Costume":
                //resId = R.drawable.shark_costume_icon;
                break;
            case "Shrimp Costume":
                //resId = R.drawable.shrimp_costume_icon;
                break;
            case "Feather":
                //resId = R.drawable.feather_icon;
                break;
            case "Mouse Plush":
                //resId = R.drawable.mouse_plush_icon;
                break;
            default:
                //resId = R.drawable.default_item_icon;
                break;
        }
        return BitmapFactory.decodeResource(getResources(), resId);
    }
}