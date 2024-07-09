package com.example.nyanyanko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    private List<ShopItem> shopItems;

    public Shop() {
        shopItems = new ArrayList<>();

        shopItems.add(new ShopItem("Dry Food", 2, 1, 5));
        shopItems.add(new ShopItem("Wet Food", 4, 3, 8));
        shopItems.add(new ShopItem("Tuna", 6, 6, 14));

        shopItems.add(new ShopItem("Mouse Plush", 1, 0, 5));
        shopItems.add(new ShopItem("Feather", 3, 0, 10));

        shopItems.add(new ShopItem("Shark Costume", 0, 0, 25));
        shopItems.add(new ShopItem("Shrimp Costume", 0, 0, 45));
    }
    public List<ShopItem> getShopItems(){
        return  shopItems;
    }
}