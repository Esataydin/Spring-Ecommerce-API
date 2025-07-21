package com.esataydin.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private String productCategory;
    private Integer quantity;
    private BigDecimal totalPrice;
}
