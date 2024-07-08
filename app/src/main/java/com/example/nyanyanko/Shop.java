package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    private List<ShopItem> shopItems;

    public Shop() {
        shopItems = new ArrayList<>();
        shopItems.add(new ShopItem("Dry Food", 2, 3, 2));
        shopItems.add(new ShopItem("Wet Food", 4, 6, 7));
        shopItems.add(new ShopItem("Tuna", 6, 8, 12));

        shopItems.add(new ShopItem("Mouse Plush", 0, 0, 5));
        shopItems.add(new ShopItem("Feather", 0, 0, 7));

        shopItems.add(new ShopItem("Shark Costume", 0, 0, 15));
        shopItems.add(new ShopItem("Shrimp Costume", 0, 0, 25));
    }
    public List<ShopItem> getShopItems(){
        return  shopItems;
    }
}