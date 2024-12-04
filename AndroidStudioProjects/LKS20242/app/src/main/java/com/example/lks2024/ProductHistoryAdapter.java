package com.example.lks2024;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lks2024.model.OnItemClickListener;
import com.example.lks2024.model.Product;
import com.example.lks2024.model.ResOrder;

import java.util.List;


public class ProductHistoryAdapter extends RecyclerView.Adapter<ProductHistoryAdapter.ProductViewHolder> {
    private List<ResOrder> productList;

    public ProductHistoryAdapter(List<ResOrder> productList) {
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2_history, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        ResOrder product = productList.get(position);
        holder.productNameTextView.setText(product.getItem().getName());
        holder.productCountTextView.setText("Count: " + product.getCount());
        holder.total.setText(CartAdapter.getConvertUang(calculate(product)));
        holder.productPriceTextView.setText(CartAdapter.getConvertUang(product.getItem().getPrice()));
        Glide.with(holder.productImageView.getContext())
                .load("http://10.0.2.2:5000/api/Home/Item/Photo/" + product.getItem().getId())
                .into(holder.productImageView);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageView;
        TextView productNameTextView;
        TextView productCountTextView;
        TextView productPriceTextView;
        TextView total;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productImageView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productCountTextView = itemView.findViewById(R.id.productCountTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            total = itemView.findViewById(R.id.totalAmountTextView);
        }
    }
    private int calculate(ResOrder product){
        return product.getCount() * product.getItem().getPrice();
    }
}
