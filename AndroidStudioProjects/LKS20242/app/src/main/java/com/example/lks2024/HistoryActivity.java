package com.example.lks2024;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lks2024.API.ApiClient;
import com.example.lks2024.API.ApiService;
import com.example.lks2024.model.Order;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recyclerView);
        loadOrderHistory();
        updateCartCount();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set item Home sebagai aktif
        bottomNavigationView.setSelectedItemId(R.id.history);

        // Set listener untuk navigasi item
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                startActivity(new Intent(HistoryActivity.this, MainActivity.class));
                return true;
            } else if (id == R.id.cart) {
                startActivity(new Intent(HistoryActivity.this, CardActivity.class));
                return true;
            } else if (id == R.id.history) {

                return true;
            }
            return false;
        });
    }
    private void loadOrderHistory() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Order>> call = apiService.getOrderById(1);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Order> orders = response.body();
                    for (Order order : orders) {
                        Log.d("HistoryActivity", "Order details: " + order.toString());
                    }

                    HistoryAdapter adapter = new HistoryAdapter(HistoryActivity.this, orders);
                    recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e("HistoryActivity", "Response not successful or empty body");
                    Toast.makeText(HistoryActivity.this, "Failed to load history", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.e("HistoryActivity", "onFailure: " + t.getMessage());
                Toast.makeText(HistoryActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void updateCartCount() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.cart);
        badgeDrawable.setNumber(MainActivity.getCounting());
        badgeDrawable.setVisible(MainActivity.getCounting() > 0);
    }

}
