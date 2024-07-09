package com.example.nyanyanko;

public class ShopItem {
    private String name;
    private int hp;
    private int hunger;
    private int cost;

    public ShopItem(String name, int hp, int hunger, int cost){
        this.name = name;
        this.hp = hp;
        this.hunger = hunger;
        this.cost = cost;
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
