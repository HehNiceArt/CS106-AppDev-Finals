package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
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
        List<InventoryItem> inventoryItems = nyankoAI.getInventory();
        adapter = new InventoryAdapter(this, inventoryItems, this);
        inventoryListView.setAdapter(adapter);
    }
    @Override
    public void onItemClick(InventoryItem item){
        Toast.makeText(this, "Clicked on " + item.getName(), Toast.LENGTH_LONG).show();
    }
}