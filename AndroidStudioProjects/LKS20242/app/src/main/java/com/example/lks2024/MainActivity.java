package com.example.lks2024;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.lks2024.API.ApiClient;
import com.example.lks2024.API.ApiService;
import com.example.lks2024.model.Cart;
import com.example.lks2024.model.Product;
import com.example.lks2024.model.cartItem;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static Cart carts = Cart.getInstance();
    private ApiService apiService;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set item Home sebagai aktif
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Set listener untuk navigasi item
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                return true;
            } else if (id == R.id.cart) {
                startActivity(new Intent(MainActivity.this, CardActivity.class));
                return true;
            } else if (id == R.id.history) {
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                return true;
            }
            return false;
        });
        recyclerView = findViewById(R.id.recyclerView);
        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
         productList = new ArrayList<Product>();
        loadProduct();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Productid")) {
           String nama = getIntent().getStringExtra("ProductName");
          int  harg = getIntent().getIntExtra("Productharga",0);
           String img = getIntent().getStringExtra("Productimage");
           int id = getIntent().getIntExtra("Productid",0);
            String des = getIntent().getStringExtra("Productdes");
           int stk = getIntent().getIntExtra("ProductStock",0);
           int count = getIntent().getIntExtra("count",0);
            Product product = new Product(id,nama,des,harg,stk);
            carts.addToCart(product,count);
            updateCartCount();
        }
    }
    private void loadProduct() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Product>> call = apiService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList = response.body();
                    for (Product product : productList) {
                        String imageUrl = "http://10.0.2.2:5000/api/Home/Item/Photo/" + product.getId();
                        product.setImageUrl(imageUrl);
                    }
                    productAdapter = new ProductAdapter(productList, product -> {
                        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                        intent.putExtra("ProductName", product.getName());
                        intent.putExtra("Productdes", product.getDescription());
                        intent.putExtra("ProductStock", product.getStock());
                        intent.putExtra("Productimage", product.getImageUrl());
                        intent.putExtra("Productid", product.getId());
                        intent.putExtra("Productharga", product.getPrice());
                        startActivity(intent);
                    });
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    recyclerView.setAdapter(productAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCartCount() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.cart);
        badgeDrawable.setNumber(carts.getItemCount());
        badgeDrawable.setVisible(carts.getItemCount() > 0);
    }

    public static List<cartItem> getCartItems() {
        return carts.getItems();
    }
    public static int getCounting(){return carts.getItemCount();}
    public static void setCartItems(List<cartItem> data){
        carts.setItems((ArrayList<cartItem>) data);
    }
}