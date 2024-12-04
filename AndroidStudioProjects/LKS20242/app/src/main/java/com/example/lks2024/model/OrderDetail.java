package com.example.lks2024.model;

public class OrderDetail {
    private int itemId;
    private int count;

    public OrderDetail(int itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
