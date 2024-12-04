package com.example.lks2024.model;


import java.util.List;

public class CheckoutReq {
    private int userId;
    private int serviceId;
    private int totalPrice;
    private String orderDate;
    private String acceptanceDate;
    private List<OrderDetail> detail;

    public CheckoutReq(int userId, int serviceId, int totalPrice, String orderDate, String acceptanceDate, List<OrderDetail> detail) {
        this.userId = userId;
        this.serviceId = serviceId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.acceptanceDate = acceptanceDate;
        this.detail = detail;
    }

}
