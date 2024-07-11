package com.example.nyanyanko.Toy;

import android.graphics.Bitmap;

public class ToyItem {
    private String name;
    private int quantity;
    private Bitmap icon;
    public ToyItem(String name, int quantity, Bitmap itemIcon) {
        this.name = name;
        this.quantity = quantity;
        this.icon = itemIcon;
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
