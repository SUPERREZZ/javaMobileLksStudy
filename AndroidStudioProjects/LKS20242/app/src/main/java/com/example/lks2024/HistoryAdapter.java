package com.example.lks2024;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lks2024.model.Order;
import com.example.lks2024.model.ResOrder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private Context context;
    private List<Order> orders;

    public HistoryAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_historyy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = orders.get(position);
        String formattedOrderDate = formatOrderDate(order.getOrderDate());
        holder.orderDateTextView.setText(formattedOrderDate);
        holder.totalAmountTextView.setText(CartAdapter.getConvertUang(Calculate(order.getDetail())));
        // Set up RecyclerView for products
        ProductHistoryAdapter productAdapter = new ProductHistoryAdapter(order.getDetail());
        holder.orderItemsRecyclerView.setLayoutManager(new LinearLayoutManager(holder.orderItemsRecyclerView.getContext()));
        holder.orderItemsRecyclerView.setAdapter(productAdapter);
    }

    private int Calculate(List<ResOrder> productList){
        int jumlah = 0 ;
        for (ResOrder product : productList) {
            jumlah += product.getCount() * product.getItem().getPrice();
        }
        return jumlah;
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderDateTextView;
        TextView totalAmountTextView;
        RecyclerView orderItemsRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            orderDateTextView = itemView.findViewById(R.id.orderDateTextView);
            totalAmountTextView = itemView.findViewById(R.id.totalAmountTextView);
            orderItemsRecyclerView = itemView.findViewById(R.id.orderItemsRecyclerView);
        }
    }
    private String formatOrderDate(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault());

        try {
            Date date = inputFormat.parse(dateString);
            return "Order Date: " + outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Order Date: Invalid date"; // Jika terjadi kesalahan
        }
    }

}

