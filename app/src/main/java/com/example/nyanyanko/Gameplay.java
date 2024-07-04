package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Gameplay extends AppCompatActivity {
    private GameView gameView;
    private Button toyBTN, shopBTN, inventoryBTN, menuBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiButtons();
    }
    public void uiButtons()
    {
        RelativeLayout relativeLayout = new RelativeLayout(this);
        gameView = new GameView(this);

        toyBTN = new Button(this);
        toyBTN.setId(View.generateViewId());
        toyBTN.setText("Toys");

        menuBTN= new Button(this);
        menuBTN.setId(View.generateViewId());
        menuBTN.setText("Menu");

        shopBTN= new Button(this);
        shopBTN.setId(View.generateViewId());
        shopBTN.setText("Shop");

        inventoryBTN= new Button(this);
        inventoryBTN.setId(View.generateViewId());
        inventoryBTN.setText("Inventory");

        RelativeLayout.LayoutParams toyParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        toyParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        toyParams.addRule(RelativeLayout.LEFT_OF, menuBTN.getId());
        toyBTN.setLayoutParams(toyParams);

        RelativeLayout.LayoutParams menuParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        menuParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        menuParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        menuBTN.setLayoutParams(menuParams);

        RelativeLayout.LayoutParams shopParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        shopParams.addRule(RelativeLayout.BELOW, toyBTN.getId());
        shopParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        shopBTN.setLayoutParams(shopParams);

        RelativeLayout.LayoutParams inventoryParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        inventoryParams.addRule(RelativeLayout.BELOW, shopBTN.getId());
        inventoryParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        inventoryBTN.setLayoutParams(inventoryParams);

        // Add GameView and RelativeLayouts to the main RelativeLayout
        relativeLayout.addView(gameView);
        relativeLayout.addView(toyBTN);
        relativeLayout.addView(menuBTN);
        relativeLayout.addView(shopBTN);
        relativeLayout.addView(inventoryBTN);

        setContentView(relativeLayout);

        toyBTN.setOnClickListener(view -> gameView.handleToyBTNClick());
        menuBTN.setOnClickListener(view -> gameView.handleMenuBTNClick());
        shopBTN.setOnClickListener(view -> gameView.handleShopBTNClick());
        inventoryBTN.setOnClickListener(view -> gameView.handleInventoryBTNClick());
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        gameView.resume();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        gameView.pause();
    }

}