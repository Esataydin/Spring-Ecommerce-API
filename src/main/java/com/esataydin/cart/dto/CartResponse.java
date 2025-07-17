package com.esataydin.cart.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartResponse {
    
    private List<CartItemResponse> items;
    private BigDecimal totalAmount;
    private Integer totalItems;
    
    // Constructors
    public CartResponse() {}
    
    public CartResponse(List<CartItemResponse> items, BigDecimal totalAmount, Integer totalItems) {
        this.items = items;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
    }
    
    // Getters and Setters
    public List<CartItemResponse> getItems() {
        return items;
    }
    
    public void setItems(List<CartItemResponse> items) {
        this.items = items;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public Integer getTotalItems() {
        return totalItems;
    }
    
    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }
}
