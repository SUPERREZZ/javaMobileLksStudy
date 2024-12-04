package com.example.lks2024.model;

import java.util.List;

public interface OnCartItemsChangedListener {
    void onCartItemsChanged(List<cartItem> updatedCartItems);
}
