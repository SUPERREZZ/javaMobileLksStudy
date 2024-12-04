package com.example.lks2024.API;




import com.example.lks2024.model.CheckoutReq;
import com.example.lks2024.model.Delivery;
import com.example.lks2024.model.LoginReq;
import com.example.lks2024.model.LoginResponse;
import com.example.lks2024.model.Order;
import com.example.lks2024.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("Login")
    Call<LoginResponse> login(@Body LoginReq loginRequest);

    @GET("Home/item")
    Call<List<Product>> getProducts();

    @GET("Checkout/Service")
    Call<List<Delivery>>getDelivery();

    @POST("Checkout/Transaction")
    Call<Void> checkout(@Body CheckoutReq checkoutReq);

    @GET("History/Transaction/{id}")
    Call<List<Order>> getOrderById(@Path("id") int orderId);
}
