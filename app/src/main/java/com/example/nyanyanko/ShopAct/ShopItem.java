package com.example.nyanyanko.ShopAct;

import android.graphics.Bitmap;

public class ShopItem {
    private String name;
    private Bitmap icon;
    private int hp;
    private int hunger;
    private int cost;

    public ShopItem(String name, int hp, int hunger, int cost, Bitmap icon){
        this.name = name;
        this.hp = hp;
        this.hunger = hunger;
        this.cost = cost;
        this.icon = icon;
    }
    public Bitmap getIcon(){
        return icon;
    }
    public void setIcon(Bitmap icon){
        this.icon = icon;
    }
    public String getName(){
        return name;
    }
    public int getHP(){
        return hp;
    }
    public int getHunger(){
        return hunger;
    }
    public int getCost(){
        return cost;
    }
    @Override
    public String toString(){
        return name + "(Cost: " + cost + " coins)";
    }
}
