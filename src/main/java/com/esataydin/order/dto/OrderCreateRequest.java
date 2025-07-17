package com.esataydin.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderCreateRequest {
    
    @NotEmpty(message = "Order items cannot be empty")
    @Valid
    private List<OrderItemRequest> items;
    
    // Constructors
    public OrderCreateRequest() {}
    
    public OrderCreateRequest(List<OrderItemRequest> items) {
        this.items = items;
    }
    
    // Getters and Setters
    public List<OrderItemRequest> getItems() {
        return items;
    }
    
    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
    
    // Inner class for order item request
    public static class OrderItemRequest {
        @NotNull(message = "Product ID is required")
        private Long productId;
        
        @NotNull(message = "Quantity is required")
        private Integer quantity;
        
        // Constructors
        public OrderItemRequest() {}
        
        public OrderItemRequest(Long productId, Integer quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }
        
        // Getters and Setters
        public Long getProductId() {
            return productId;
        }
        
        public void setProductId(Long productId) {
            this.productId = productId;
        }
        
        public Integer getQuantity() {
            return quantity;
        }
        
        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}
