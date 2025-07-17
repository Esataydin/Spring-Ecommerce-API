package com.esataydin.order.dto;

import java.math.BigDecimal;

public class OrderItemResponse {
    
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private String productCategory;
    private Integer quantity;
    private BigDecimal totalPrice;
    
    // Constructors
    public OrderItemResponse() {}
    
    public OrderItemResponse(Long productId, String productName, BigDecimal productPrice, 
                           String productCategory, Integer quantity, BigDecimal totalPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
    
    // Getters and Setters
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public BigDecimal getProductPrice() {
        return productPrice;
    }
    
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
    
    public String getProductCategory() {
        return productCategory;
    }
    
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
