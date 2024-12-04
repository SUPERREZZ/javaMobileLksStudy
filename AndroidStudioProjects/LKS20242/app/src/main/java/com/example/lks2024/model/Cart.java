package com.example.lks2024.model;

import java.util.ArrayList;

public class Cart {
    private ArrayList<cartItem> items = new ArrayList<>();
    private static Cart instance;

    private Cart() {}

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }
    public void addToCart(Product product,int count) {
        for (cartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + count);
                return;
            }
        }
        items.add(new cartItem(product, count));
    }

    public int getItemCount() {
        return items.size();
    }

    public ArrayList<cartItem> getItems() {
        return items;
    }
    public void setItems(ArrayList<cartItem> data){
        items = data ;
    }
}
