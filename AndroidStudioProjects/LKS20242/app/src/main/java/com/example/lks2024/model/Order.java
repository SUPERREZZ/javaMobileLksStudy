
package com.example.lks2024.model;

import java.util.List;

public class Order {
    private int totalPrice;
    private String orderDate;
    private String acceptanceDate;
    private List<ResOrder> detail;

    // Getters and Setters
    public String toString() {
        return "Order{" +
                "totalPrice=" + totalPrice +
                ", orderDate='" + orderDate + '\'' +
                ", acceptanceDate='" + acceptanceDate + '\'' +
                ", detail=" + detail +
                '}';
    }

    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }
    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
    public String getAcceptanceDate() { return acceptanceDate; }
    public void setAcceptanceDate(String acceptanceDate) { this.acceptanceDate = acceptanceDate; }
    public List<ResOrder> getDetail() { return detail; }
    public void setDetail(List<ResOrder> detail) { this.detail = detail; }
}



