package com.example.nyanyanko.Inventory;

import android.graphics.Bitmap;

public class InventoryItem {
    private final String name;
    private int quantity;
    private int hp;
    private int hunger;
    private Bitmap icon;

    public InventoryItem(String name, int quantity, int hp, int hunger, Bitmap itemIcon){
        this.name = name;
        this.hp = hp;
        this.hunger = hunger;
        this.quantity = quantity;
        this.icon = itemIcon;
    }
    public int getHunger(){
        return hunger;
    }
    public int getHp() {
        return hp;
    }
    public String getName(){
        return name;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public Bitmap getIcon(){
        return icon;
    }
    public void setIcon(Bitmap icon){
        this.icon = icon;
    }
}
