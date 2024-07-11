package com.example.nyanyanko.ShopAct;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.nyanyanko.R;
import com.example.nyanyanko.ShopAct.ShopItem;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    private List<ShopItem> shopItems;

    public Shop(Context context) {
        shopItems = new ArrayList<>();

        shopItems.add(new ShopItem("Dry Food", 2, 1, 5, getItemIcon(context, "Dry Food")));
        shopItems.add(new ShopItem("Wet Food", 4, 3, 8, getItemIcon(context, "Wet Food")));
        shopItems.add(new ShopItem("Tuna", 6, 6, 14, getItemIcon(context, "Tuna")));

        shopItems.add(new ShopItem("Mouse Plush", 1, 0, 5, getItemIcon(context, "Mouse Plush")));
        shopItems.add(new ShopItem("Feather", 3, 0, 10, getItemIcon(context, "Feather")));

        shopItems.add(new ShopItem("Shark Costume", 0, 0, 25, getItemIcon(context, "Shark Costume")));
        shopItems.add(new ShopItem("Shrimp Costume", 0, 0, 4, getItemIcon(context, "Shrimp Costume")));
    }
    private Bitmap getItemIcon(Context context, String itemName){
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
                //resId = R.drawable.empty_icon;
                break;
        }
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }
    public List<ShopItem> getShopItems(){
        return  shopItems;
    }
}