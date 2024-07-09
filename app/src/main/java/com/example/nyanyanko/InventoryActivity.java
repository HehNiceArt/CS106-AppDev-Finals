package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InventoryActivity extends Activity implements InventoryAdapter.OnItemInteractionListener{
    private ListView inventoryListView;
    private NyankoAI nyankoAI;
    private InventoryAdapter adapter;
    private List<InventoryItem> inventoryItems;
    private GameView gameView;
    private Bitmap eating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        inventoryListView = findViewById(R.id.inventoryList);

        nyankoAI = NyankoManager.getInstance(this);

        updateInventory();

        Button returnBTN = findViewById(R.id.returnBTN);
        returnBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
    private void updateInventory(){
        inventoryItems = nyankoAI.getInventory();
        adapter = new InventoryAdapter(this, inventoryItems, this);
        inventoryListView.setAdapter(adapter);
    }
    @Override
    public void onItemClick(InventoryItem item){
        int currentQuantity = item.getQuantity();
        eating = BitmapFactory.decodeResource(getResources(), R.drawable.nyanko);
        int hungerVal = item.getHunger();
        int hpVal = item.getHp();

        if(currentQuantity > 0){
            showImageDialog();
            nyankoAI.fillHunger(hungerVal);
            nyankoAI.fillHP(hpVal);
            item.setQuantity(currentQuantity - 1);
            if(item.getQuantity() == 0){
                inventoryItems.remove(item);
                Toast.makeText(this, item.getName() + " is now empty", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Used one" + item.getName() + " x " + item.getQuantity(), Toast.LENGTH_SHORT).show();
            }
            adapter.notifyDataSetChanged();
        }
    }
    private void showImageDialog(){
        if(!isFinishing()) {
            ImageDialog dialog = new ImageDialog(this, eating);
            dialog.show();
        }
    }
}