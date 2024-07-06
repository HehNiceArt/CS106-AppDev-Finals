package com.example.nyanyanko;

import android.graphics.Bitmap;

public class InventoryItem {
    private String name;
    private int quantity;
    private Bitmap icon;

    public InventoryItem(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
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
}
