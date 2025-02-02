package com.example.nyanyanko.ShopAct;

import android.content.Context;
import android.util.Log;

import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

import java.util.logging.LogRecord;

public class CoinManager {
    private static CoinManager instance;
    private int coins = 20;
    private Handler handler;
    private Runnable coinIncrementTask;
    private Context context;

    private CoinManager(){
        handler = new Handler();
        coinIncrementTask = new Runnable() {
            @Override
            public void run() {
                increaseCoins();
                handler.postDelayed(this, 20000);
            }
        };
    }
    public static synchronized CoinManager getInstance(){
        if(instance == null){
            instance = new CoinManager();
        }
        return instance;
    }
    public void setContext(Context context){
        this.context = context.getApplicationContext();
    }
    public void startCoinIncrement(){
        handler.postDelayed(coinIncrementTask, 13000);
    }
    public void stopCoinIncrement(){
        handler.removeCallbacks(coinIncrementTask);
    }
    private void increaseCoins(){
        coins += 1;
        Log.d("CoinManager", "Coins: " + coins);
    }
    public void toySum(int plus){
        coins += plus;
        Log.d("CoinManager", "Coins: " + coins);
    }
    public int decreaseCoins(int cost){
        coins -= cost;
        return coins;
    }
    public int getCoins(){
        return coins;
    }

}
