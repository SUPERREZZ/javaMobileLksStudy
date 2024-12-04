package com.example.lks2024;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.lks2024.API.ApiClient;
import com.example.lks2024.API.ApiService;
import com.example.lks2024.model.CheckoutReq;
import com.example.lks2024.model.Delivery;
import com.example.lks2024.model.OnCartItemsChangedListener;
import com.example.lks2024.model.OrderDetail;
import com.example.lks2024.model.cartItem;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardActivity extends AppCompatActivity implements OnCartItemsChangedListener {

    private  DeliveryAdapter deliveryAdap;
    private static Spinner listdel;
    private static int biaya,totalpembayaran,selectedServiceId;
    private List<Delivery> deliveryList;
    private static List<cartItem> cartItems = MainActivity.getCartItems();
    private static TextView textTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_lay);
        Log.i(TAG, "someMethod: Method is running");
        load();
        updateCartCount();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Set item Home sebagai aktif
        bottomNavigationView.setSelectedItemId(R.id.cart);

        // Set listener untuk navigasi item
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                startActivity(new Intent(CardActivity.this, MainActivity.class));
                return true;
            } else if (id == R.id.cart) {

                return true;
            } else if (id == R.id.history) {
                startActivity(new Intent(CardActivity.this, HistoryActivity.class));
                return true;
            }
            return false;
        });
    }

    @Override
    public void onCartItemsChanged(List<cartItem> updatedCartItems) {
        cartItems = updatedCartItems;
        MainActivity.setCartItems(cartItems);
        load();
    }
    public void load(){

        if (cartItems.isEmpty()){
            LinearLayout empty = findViewById(R.id.empty);
            empty.setVisibility(View.VISIBLE);
            LinearLayout ada = findViewById(R.id.ada);
            ada.setVisibility(View.GONE);
        }else{
            Button checkouts = findViewById(R.id.checkouts);
            checkouts.setOnClickListener(v -> {
                checkout();
            });
            RecyclerView recyclerView = findViewById(R.id.recyclerViewCart);
            listdel = findViewById(R.id.spinnerDeliveries);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            CartAdapter adapter = new CartAdapter(cartItems,this);
            recyclerView.setAdapter(adapter);
            textTotalPrice = findViewById(R.id.textTotalPrice);
            loadDelivery();

        }


    }

    public static void updateTotalPrice(int pengiriman) {
        int totalPrice = 0;
        for (cartItem item : cartItems) {
            totalPrice += item.getTotalPrice();
        }
        totalpembayaran = totalPrice + pengiriman;
        textTotalPrice.setText("Total: Rp " + CartAdapter.getConvertUang(totalPrice + pengiriman));
    }
    private void loadDelivery() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Delivery>> call = apiService.getDelivery();
        call.enqueue(new Callback<List<Delivery>>() {
            @Override
            public void onResponse(Call<List<Delivery>> call, Response<List<Delivery>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    deliveryList = response.body();
                    deliveryAdap = new DeliveryAdapter (CardActivity.this,deliveryList);
                    listdel.setAdapter(deliveryAdap);
                    listdel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Delivery selectedDelivery = deliveryList.get(position);
                            selectedServiceId = selectedDelivery.getId();
                            biaya = selectedDelivery.getPrice();
                            updateTotalPrice(selectedDelivery.getPrice());
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    Toast.makeText(CardActivity.this, "Failed to load products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Delivery>> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });
    }
    public static int getBiaya(){
        return biaya;
    }

    private void checkout() {
        List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
        for (cartItem item : cartItems) {
            orderDetails.add(new OrderDetail(item.getProduct().getId(), item.getQuantity()));
        }

        CheckoutReq checkoutRequest = new CheckoutReq(
                1,
                selectedServiceId,
                totalpembayaran,
                getCurrentDate(),
                getAcceptanceDate(),
                orderDetails
        );
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = apiService.checkout(checkoutRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CardActivity.this, "Checkout sukses!", Toast.LENGTH_SHORT).show();
                    onCartItemsChanged(new ArrayList<cartItem>());
                    load();
                } else {

                    Toast.makeText(CardActivity.this, "Checkout gagal!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Toast.makeText(CardActivity.this, "Kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    private String getAcceptanceDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }
    private void updateCartCount() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.cart);
        badgeDrawable.setNumber(MainActivity.getCounting());
        badgeDrawable.setVisible(MainActivity.getCounting() > 0);
    }
}

