package com.esataydin.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    
    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
    private BigDecimal totalAmount;
    private Integer totalItems;
    
    // Constructors
    public OrderResponse() {}
    
    public OrderResponse(Long id, Long userId, LocalDateTime createdAt, 
                        List<OrderItemResponse> items, BigDecimal totalAmount, Integer totalItems) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.items = items;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<OrderItemResponse> getItems() {
        return items;
    }
    
    public void setItems(List<OrderItemResponse> items) {
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
