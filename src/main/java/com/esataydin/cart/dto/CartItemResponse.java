package com.esataydin.cart.dto;

import java.math.BigDecimal;

public record CartItemResponse(
    Long id,
    Long productId,
    String productName,
    BigDecimal productPrice,
    String productCategory,
    Integer quantity,
    BigDecimal totalPrice
) {
}
