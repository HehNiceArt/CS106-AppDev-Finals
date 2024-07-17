package com.example.nyanyanko.Toy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nyanyanko.Gameplay;
import com.example.nyanyanko.ImageDialog;
import com.example.nyanyanko.NyankoAI;
import com.example.nyanyanko.NyankoManager;
import com.example.nyanyanko.R;
import com.example.nyanyanko.ShopAct.CoinManager;

import java.util.List;
import java.util.Objects;

public class ToyActivity extends Activity implements ToyAdapter.OnItemInteractionListener{

    Button backBTN;
    private GridView toyGridView;
    private ToyAdapter adapter;
    private NyankoAI nyankoAI;
    private List<ToyItem> toyItems;
    private Bitmap playing;
    String petName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toy);

        petName = getIntent().getStringExtra("PET_NAME");

        Bitmap nyankoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nyanko);
        toyGridView = findViewById(R.id.toyGrid);

        nyankoAI = NyankoManager.getExistingInstance();
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
    @Override
    public void onItemClick(ToyItem item){
        int currentQuantity = item.getQuantity();
        playing = BitmapFactory.decodeResource(getResources(), R.drawable.splash_playing);
        if(currentQuantity > 0){
            showImageDialog();
            item.setQuantity(currentQuantity - 1);
            checkToy(item);
            if(item.getQuantity() == 0){
                toyItems.remove(item);
                Toast.makeText(this, item.getName() + " is now empty!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Used "+ item.getName() + " x " + item.getQuantity(), Toast.LENGTH_SHORT).show();
            }
            adapter.notifyDataSetChanged();
        }
    }
    private void checkToy(ToyItem item){
       if(item.getName().equals("Mouse Plush")){
           int plus = 2;
           CoinManager.getInstance().toySum(plus);
           Log.d("ToyAct", "Mouse Plush used!");
       } else if (item.getName().equals("Feather")){
           int plus = 6;
           CoinManager.getInstance().toySum(plus);
           Log.d("ToyAct", "Feather used!");
       }
    }

    private void showImageDialog() {
        if(!isFinishing()){
            ImageDialog dialog = new ImageDialog(this, playing);
            dialog.show();
        }
    }

    private void goBack(){
        Intent intent = new Intent(ToyActivity.this, Gameplay.class);
        intent.putExtra("PET_NAME", petName);
        NyankoManager.releaseInstance();
        startActivity(intent);
    }
    private void updateToy(){
        toyItems = nyankoAI.getToy();
        adapter = new ToyAdapter(this, toyItems, this);
        toyGridView.setAdapter(adapter);
    }
    @Override
    protected void onPause(){
        super.onPause();
        CoinManager.getInstance().stopCoinIncrement();
        Log.d("ToyActivity", "Pausing coin increment in ToyActivity");
    }
}