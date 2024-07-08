package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
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

        updateToy();
        backBTN = findViewById(R.id.backBTN);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void updateToy(){
        List<ToyItem> toyItems = nyankoAI.getToy();
        adapter = new ToyAdapter(this, toyItems);
        toyListView.setAdapter(adapter);
    }
}