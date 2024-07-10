package com.example.nyanyanko;

import android.util.Log;

public class ShopManager {
    private static ShopManager instance;
    private ShopActivity shopActivity;

    private ShopManager(){}
    public static synchronized ShopManager getInstance() {
        if (instance == null) {
            instance = new ShopManager();
        }
        return instance;
    }

    public void setShopActivity(ShopActivity shopActivity) {
        this.shopActivity = shopActivity;
    }

    public void clearShopActivity() {
        this.shopActivity = null;
    }

    public void startCoinIncrementTask() {
        if (shopActivity != null) {
            shopActivity.startPassiveIncome();
            Log.d("shopManager", "shopActivity is not null");
        }else{
            Log.e("shopManager", "shopActivity is null");
        }
    }
}
