package com.example.nyanyanko.ShopAct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nyanyanko.Gameplay;
import com.example.nyanyanko.Inventory.InventoryItem;
import com.example.nyanyanko.NyankoAI;
import com.example.nyanyanko.NyankoManager;
import com.example.nyanyanko.R;
import com.example.nyanyanko.Toy.ToyItem;

import java.util.List;

public class ShopActivity extends Activity implements ShopItemAdapter.OnItemInteractionListener{
    private Shop shop;
    private NyankoAI nyankoAI;
    private int playerCoins;
    private Handler handler;
    private GridView shopGridView;
    private ShopItemAdapter adapter;
    private List<ShopItem> shopItems;
    TextView coins;
    String petName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        handler = new Handler();
        shop = new Shop(this);

        Intent intent = getIntent();
        petName = getIntent().getStringExtra("PET_NAME");

        if (intent != null && intent.hasExtra("playerCoins")) {
            playerCoins = intent.getIntExtra("playerCoins", 20);
        }
        shopGridView= findViewById(R.id.shopGrid);

        nyankoAI = NyankoManager.getExistingInstance();
        coins = findViewById(R.id.coinID);
        playerCoins = CoinManager.getInstance().getCoins();
        Log.d("ShopAct", "PlayerCoins: " + playerCoins);

        updateCoinDisplay();
        CoinManager.getInstance().startCoinIncrement();
        Log.d("ShopAct", "Starting coin increment in ShopAct ");
        updateShopItems();

        Button backBTN = findViewById(R.id.backBTN);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                goBack();
            }
        });
    }

    @Override
    public void onItemClick(ShopItem item){
       itemBuy(item);
    }
    private void updateShopItems(){
        shopItems = shop.getShopItems();
        adapter = new ShopItemAdapter(this, shopItems, this);
        shopGridView.setAdapter(adapter);
    }
    private void updateCoinDisplay(){
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                coins.setText(String.valueOf(CoinManager.getInstance().getCoins()));
                handler.postDelayed(this, 100);
            }
        };
        handler.post(runnable);
    }
    private void goBack(){
        Intent intent = new Intent(ShopActivity.this, Gameplay.class);
        intent.putExtra("playerCoins", playerCoins);
        intent.putExtra("PET_NAME", petName);
        NyankoManager.releaseInstance();
        startActivity(intent);
    }
    private void itemBuy(ShopItem item){
        if(playerCoins >= item.getCost()){
            playerCoins = CoinManager.getInstance().decreaseCoins(item.getCost());

            int hp = item.getHP();
            int hunger = item.getHunger();

            updateCoinDisplay();

            Bitmap itemIcon = getItemIcon(item.getName());
            InventoryItem inventoryItem = new InventoryItem(item.getName(), 1, hp, hunger, itemIcon);
            ToyItem toyItem = new ToyItem(item.getName(), 1, itemIcon);

            if(item.getName().equals("Mouse Plush") || item.getName().equals("Feather")){
                nyankoAI.addItemToToy(toyItem);
            }else {
                nyankoAI.addItemToInventory(inventoryItem);
            }

            Toast.makeText(ShopActivity.this, "Purchased: " + item.getName(), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ShopActivity.this, "Not enough coins!", Toast.LENGTH_SHORT).show();
        }
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
                resId = R.drawable.tuna_food_icon;
                break;
            case "Shark Costume":
                resId = R.drawable.shark_costume_icon;
                break;
            case "Shrimp Costume":
                resId = R.drawable.shrimp_costume_icon;
                break;
            case "Feather":
                resId = R.drawable.feather_icon;
                break;
            case "Mouse Plush":
                resId = R.drawable.mouse_plush_icon;
                break;
            default:
                resId = R.drawable.empty_icon;
                break;
        }
        return BitmapFactory.decodeResource(getResources(), resId);
    }
    @Override
    protected void onPause(){
        super.onPause();
        CoinManager.getInstance().stopCoinIncrement();
        Log.d("ShopAct", "Pausing coin increment in ShopActivity");
    }
}