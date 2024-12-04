package com.example.lks2024;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lks2024.model.OnCartItemsChangedListener;
import com.example.lks2024.model.cartItem;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private static NumberFormat IdRp = NumberFormat.getCurrencyInstance(new Locale("id","ID"));
    private List<cartItem> cartItems;
    private OnCartItemsChangedListener listener;
    public CartAdapter(List<cartItem> cartItems,OnCartItemsChangedListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        cartItem cartItem = cartItems.get(position);
        holder.cartItemName.setText(cartItem.getProduct().getName());
        holder.cartItemPrice.setText(IdRp.format(cartItem.getProduct().getPrice()));
        holder.cartItemQuantity.setText("Qty: " + cartItem.getQuantity());
        Glide.with(holder.itemView.getContext())
                .load("http://10.0.2.2:5000/api/Home/Item/Photo/" + cartItem.getProduct().getId())
                .into(holder.cardproduct);
        holder.hps.setOnClickListener(v -> {
            if (!cartItems.isEmpty()){
                removeItem(position);
                MainActivity.setCartItems(cartItems);
                notifyItemRemoved(position);
                if (listener != null) {
                    listener.onCartItemsChanged(cartItems); // Panggil listener
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView cartItemName, cartItemPrice, cartItemQuantity;
        ImageView cardproduct,hps;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemName = itemView.findViewById(R.id.cartItemName);
            cartItemPrice = itemView.findViewById(R.id.cartItemPrice);
            cartItemQuantity = itemView.findViewById(R.id.cartItemQuantity);
            cardproduct = itemView.findViewById(R.id.fotoproduct);
            hps = itemView.findViewById(R.id.hps);
        }
    }

    private void removeItem(int position) {
        if (position >= 0 && position < cartItems.size()) {
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
           CardActivity.updateTotalPrice(CardActivity.getBiaya());
        } else {
            Log.e("CartAdapter", "Invalid position: " + position);
        }
    }
    public static String getConvertUang(int uang){
        return IdRp.format(uang);
    }
}
