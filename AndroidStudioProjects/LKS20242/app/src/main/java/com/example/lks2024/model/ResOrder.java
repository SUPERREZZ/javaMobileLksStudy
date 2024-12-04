package com.example.lks2024.model;

public class ResOrder {
    private Items item;
    private int count;

    // Getters and Setters
    public String toString() {
        return "ResOrder{" +
                "item=" + item +
                ", count=" + count +
                '}';
    }

    public Items getItem() { return item; }
    public void setItem(Items item) { this.item = item; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
}
