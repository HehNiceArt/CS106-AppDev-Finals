package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class InventoryActivity extends Activity{
    private ListView inventoryListView;
    private ArrayAdapter<String> adapter;
    private NyankoAI nyankoAI;

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
        List<String> inventoryItems = new ArrayList<>();
        for(InventoryItem item : nyankoAI.getInventory()){
            inventoryItems.add(item.getName() + " x " + item.getQuantity());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, inventoryItems);
        inventoryListView.setAdapter(adapter);
    }
}